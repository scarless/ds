package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;

public class MoveItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {

		int interfaceId = c.getInStream().readSignedWordBigEndianA();
		boolean insertMode = c.getInStream().readSignedByteC() == 1;
		int from = c.getInStream().readSignedWordBigEndianA();
		int to = c.getInStream().readSignedWordBigEndian();

		c.getItems().moveItems(from, to, interfaceId, insertMode);
		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if (c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if (c.duelStatus == 1) {
			c.getTradeAndDuel().declineDuel();
			return;
		}
	}
}