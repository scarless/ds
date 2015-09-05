package server.net.login;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.net.InetSocketAddress;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import server.Config;
import server.Connection;
import server.Server;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.game.players.saving.PlayerSave;
import server.net.ChannelHandler;
import server.net.PacketBuilder;
import server.util.ISAACCipher;
import server.util.Misc;
import server.util.HostBlacklist;

public class RS2LoginProtocol extends FrameDecoder {

	private static final BigInteger RSA_MODULUS = new BigInteger("107795055263497987882574771473398004143656909388607035911731378324856546423008259980536978958793626014082434482454518540574577993715535529352598800296747608510861423288090522126558632815276400645537732959215578613175087322531081689837645041825293363271897528409212219581939996005083315900203306652231032105563");

	private static final BigInteger RSA_EXPONENT = new BigInteger("39151100911501023934097186099169823040900032259288848677952027072746088080120628230110040284971309031740912583515646197129814913474997210814958103719478810914778438000965436038590252626871006279732751082258527983092203756351789765604921997685424219632558734149511955173183053578256833967174566419428074865345");





        
        
        private static final int CONNECTED = 0;
        private static final int LOGGING_IN = 1;
        private int state = CONNECTED;

        @Override
        protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
                if(!channel.isConnected()) {
                        return null;
                }
                switch (state) {
                case CONNECTED:
                        if (buffer.readableBytes() < 2)
                                return null;
                        int request = buffer.readUnsignedByte();
                        if (request != 14) {
                                System.out.println("Invalid login request: " + request);
                                channel.close();
                                return null;
                        }
                        buffer.readUnsignedByte();
                        channel.write(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
                        state = LOGGING_IN;
                        return null;
                case LOGGING_IN:					                    
					@SuppressWarnings("unused")
					int loginType = -1, loginPacketSize = -1, loginEncryptPacketSize = -1;
					if(2 <= buffer.capacity()) {
						loginType = buffer.readByte() & 0xff; //should be 16 or 18
						loginPacketSize = buffer.readByte() & 0xff;
						loginEncryptPacketSize = loginPacketSize-(36+1+1+2);
						if(loginPacketSize <= 0 || loginEncryptPacketSize <= 0) {
							System.out.println("Zero or negative login size.");
							channel.close();
							return false;
						}
					}
					
					/**
					 * Read the magic id.
					 */
					if(loginPacketSize <= buffer.capacity()) {
						int magic = buffer.readByte() & 0xff;
						int version = buffer.readUnsignedShort();
						if(magic != 255) {
							System.out.println("Wrong magic id.");
							channel.close();
							return false;
						}
						if(version != 1) {
							//Dont Add Anything
						}
						@SuppressWarnings("unused")
						int lowMem = buffer.readByte() & 0xff;
						
						/**
						 * Pass the CRC keys.
						 */
						for(int i = 0; i < 9; i++) {
							buffer.readInt();
						}
						loginEncryptPacketSize--;
						if(loginEncryptPacketSize != (buffer.readByte() & 0xff)) {
							System.out.println("Encrypted size mismatch.");
							channel.close();
							return false;
						}
						
						/**
						 * Our RSA components. 
						 */
					ChannelBuffer rsaBuffer = buffer.readBytes(loginEncryptPacketSize);

						BigInteger bigInteger = new BigInteger(rsaBuffer.array());
						bigInteger = bigInteger.modPow(RSA_EXPONENT, RSA_MODULUS);
						rsaBuffer = ChannelBuffers.wrappedBuffer(bigInteger.toByteArray());
							if((rsaBuffer.readByte() & 0xff) != 10) {
								System.out.println("Encrypted id != 10.");
								channel.close();
								return false;
							}
                        final long clientHalf = rsaBuffer.readLong();
                        final long serverHalf = rsaBuffer.readLong();
                        
						int uid = rsaBuffer.readInt();
						
						if(uid == 0 || uid == 99735086) {
							channel.close();
							return false;
						}
                        final String name = Misc.formatPlayerName(Misc.getRS2String(rsaBuffer));
                        final String pass = Misc.getRS2String(rsaBuffer);
                        
                        final int[] isaacSeed = { (int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf };
                        final ISAACCipher inCipher = new ISAACCipher(isaacSeed);
                        for (int i = 0; i < isaacSeed.length; i++)
                                isaacSeed[i] += 50;
                        final ISAACCipher outCipher = new ISAACCipher(isaacSeed);
                        //final int version = buffer.readInt();
                        channel.getPipeline().replace("decoder", "decoder", new RS2Decoder(inCipher));
                        return login(channel, inCipher, outCipher, version, name, pass);
                }
                }
                return null;
                
        }

	private static Client login(Channel channel, ISAACCipher inCipher,
			ISAACCipher outCipher, int version, String name, String pass) {
		int returnCode = 2;
		if (Connection.isIpBanned(((InetSocketAddress) channel
				.getRemoteAddress()).getAddress().getHostAddress().toString())) {
			returnCode = 4;
		}
String hostName = ((InetSocketAddress) channel.getRemoteAddress())
    .getAddress().getHostName();

		if(HostBlacklist.isBlocked(hostName))
			returnCode = 11;
		if (!name.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		if (name.length() > 12) {
			returnCode = 8;
		}
		Client cl = new Client(channel, -1);
		cl.playerName = name;
		cl.playerName2 = cl.playerName;
		cl.playerPass = pass;
		cl.outStream.packetEncryption = outCipher;
		cl.saveCharacter = false;
		cl.isActive = true;
		if (Connection.isNamedBanned(cl.playerName)) {
			returnCode = 4;
		}
			if(cl.playerName.endsWith(" ")){
			returnCode = 4;
		}
		if(cl.playerName.startsWith(" ")){
			returnCode = 4;
		}
		if (PlayerHandler.isPlayerOn(name)) {
			returnCode = 5;
		}
		if (PlayerHandler.getPlayerCount() >= Config.MAX_PLAYERS) {
			returnCode = 7;
		}
		if (Server.UpdateServer) {
			returnCode = 14;
		}
		if (returnCode == 2) {
			int load = PlayerSave.loadGame(cl, cl.playerName, cl.playerPass);
			if (load == 0)
				cl.addStarter = true;
			if (load == 3) {
				returnCode = 3;
				cl.saveFile = false;
			} else {
				for (int i = 0; i < cl.playerEquipment.length; i++) {
					if (cl.playerEquipment[i] == 0) {
						cl.playerEquipment[i] = -1;
						cl.playerEquipmentN[i] = 0;
					}
				}
				if (!Server.playerHandler.newPlayerClient(cl)) {
					returnCode = 7;
					cl.saveFile = false;
				} else {
					cl.saveFile = true;
				}
			}
		}
		if (returnCode == 2) {
			cl.saveCharacter = true;
			cl.packetType = -1;
			cl.packetSize = 0;
			final PacketBuilder bldr = new PacketBuilder();
			bldr.put((byte) 2);
			if (cl.playerRights == 3) {
				bldr.put((byte) 2);
			} else {
				bldr.put((byte) cl.playerRights);
			}
			bldr.put((byte) 0);
			channel.write(bldr.toPacket());
		} else {
			System.out.println("returncode:" + returnCode);
			sendReturnCode(channel, returnCode);
			return null;
		}
		synchronized (PlayerHandler.lock) {
			cl.initialize();
			cl.initialized = true;
		}
		return cl;
	}

	public static void sendReturnCode(final Channel channel, final int code) {
		channel.write(new PacketBuilder().put((byte) code).toPacket())
				.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(final ChannelFuture arg0)
							throws Exception {
						arg0.getChannel().close();
					}
				});
	}

}
