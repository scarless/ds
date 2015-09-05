package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inTrade) {
			if (!c.tradeConfirmed2) {
				Client o = (Client) PlayerHandler.players[c.tradeWith];
				c.getTradeAndDuel().declineTrade();
				o.getTradeAndDuel().declineTrade();
			}	
		}
		if (c.hasPin) {
			return;
		}
        if (c.isBanking)
            c.isBanking = false;
        if(c.isShopping)
            c.isShopping = false;
        if(c.openDuel && c.duelStatus >= 1 && c.duelStatus <= 4) {
            Client o = (Client) PlayerHandler.players[c.duelingWith];
            if(o != null)
                o.getTradeAndDuel().declineDuel();
            c.getTradeAndDuel().declineDuel();
        }
        if(c.duelStatus == 6)
            c.getTradeAndDuel().claimStakedItems();
        if (c.inTrade) {
            if(!c.acceptedTrade) {
                c.getTradeAndDuel().declineTrade();
            }
        }
		// if (c.isBanking)
		// c.isBanking = false;

		Client o = (Client) PlayerHandler.players[c.duelingWith];
		if (c.duelStatus == 5) {
			// c.sendMessage("You're funny sir.");
			return;
		}
		if (o != null) {
			if (c.duelStatus >= 1 && c.duelStatus <= 4) {
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
			}
		}

		if (c.duelStatus == 6) {
			c.getTradeAndDuel().claimStakedItems();
		}

	}

}
