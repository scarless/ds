package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.content.skills.LogData.logData;
import server.game.players.content.skills.firemaking.Firemaking;

public class ItemClick2OnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int itemY = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - "+ c.playerName +" - "+ itemId +" - "+ itemX +" - "+ itemY);
		for (logData l : logData.values()) {
			if (itemId == l.getLogId()) {
				Firemaking.attemptFire(c, 590, itemId, itemX, itemY, true);
			}
		}
	}
}