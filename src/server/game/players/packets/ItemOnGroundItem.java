package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.content.skills.firemaking.Firemaking;
import server.util.Misc;

public class ItemOnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int a1 = c.getInStream().readSignedWordBigEndian();
		int itemUsed = c.getInStream().readSignedWordA();
		int groundItem = c.getInStream().readUnsignedWord();
		int gItemY = c.getInStream().readSignedWordA();
		int itemUsedSlot = c.getInStream().readSignedWordBigEndianA();
		int gItemX = c.getInStream().readUnsignedWord();
		if (!c.getItems().playerHasItem(itemUsed, 1)) {
			return;
		}
		if (!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
			return;
		}
        if(!c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
            return;
        }
        if(!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
            return;
        }
		switch (itemUsed) {
		case 590:
			Firemaking.attemptFire(c, itemUsed, groundItem, gItemX, gItemY, true);
			break;

		default:
			if (c.playerRights == 3)
				Misc.println("ItemUsed " + itemUsed + " on Ground Item "
						+ groundItem);
			break;
		}
	}

}
