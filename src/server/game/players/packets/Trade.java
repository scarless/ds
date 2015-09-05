package server.game.players.packets;


import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Trading
 */
public class Trade implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int tradeId = c.getInStream().readSignedWordBigEndian();
		c.getPA().resetFollow();
        if(tradeId < 1)
            return;
		if (c.arenas()) {
			c.sendMessage("You can't trade inside the arena!");
			return;
		}
		if(c.inWild()) {  
			c.sendMessage("You can't trade in the wilderness!");
			return;
		}
		if(c.isBanking) {
		return;
		}
		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade(true);
		}
		if (tradeId != c.playerId)
			c.getTradeAndDuel().requestTrade(tradeId);
	}

}
