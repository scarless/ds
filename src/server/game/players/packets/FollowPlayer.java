package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;

public class FollowPlayer implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if(Server.playerHandler.players[followPlayer] == null) {
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;*/
		int toFollow = c.getInStream().readUnsignedWordBigEndian();
		c.follow2 = toFollow;
				if (Server.playerHandler.players[toFollow] == null)
					return;
					c.faceUpdate(32768+toFollow);
					c.follow(toFollow, 1, 1);
}}