package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.content.HandleEmpty;
import server.util.Misc;
import server.game.players.content.Teles;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemClick3 implements PacketType {
	// i dont add gambling im removing his dice shit
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
		if (!c.getItems().playerHasItem(itemId, 1)) { // , itemSlot
			return;
		}
		if (c.getPotions().potionNames(itemId)) {
			HandleEmpty.handleEmptyItem(c, itemId, 229);
			return;
		}
		if (HandleEmpty.canEmpty(itemId)) {
			HandleEmpty.handleEmptyItem(c, itemId,
					HandleEmpty.filledToEmpty(itemId));
			return;
		}
		
		switch (itemId) {
		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.itemUsing = itemId;
			Teles.ROD(c);
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.itemUsing = itemId;
			Teles.AOG(c);
			break;

		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			c.itemUsing = itemId;
			Teles.GN(c);
			break;
			
		case 5733:
			if (c.getItems().playerHasItem(5733, 1)) {
			c.getPA().removeAllWindows();
			c.getPA().openUpBank();
			}
			break;

		case 15098:
			if (System.currentTimeMillis() - c.diceDelay >= 5000) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("Project-OS channel mate "
								+ Misc.ucFirst(c.playerName) + " rolled @red@"
								+ Misc.random(100)
								+ "@bla@ on the percentile dice.");
						c.diceDelay = System.currentTimeMillis();
					}
				}
			} else {
				c.sendMessage("You must wait 10 seconds to roll dice again.");
			}
			break;

		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName + " - Item3rdOption: " + itemId
						+ " : " + itemId11 + " : " + itemId1);
			break;
		}

	}

}
