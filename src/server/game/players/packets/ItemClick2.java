package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.util.Misc;

/**
 * Item Click 2 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 *         Proper Streams
 */

public class ItemClick2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();

		if (!c.getItems().playerHasItem(itemId, 1))
			return;
        if(!c.getItems().playerHasItem(itemId, 1)) { //, itemSlot
            return;
        }
		switch (itemId) {
case 15004:
if (c.getItems().playerHasItem(15004, 1)) {
c.getItems().deleteItem2(15004, 1);
c.getItems().addItem(15000, 1);
c.getPA().removeAllWindows();
c.sendMessage("You change your cape back into a regular cape");
}
case 5733:
if (c.getItems().playerHasItem(5733, 1)) {
c.getPA().removeAllWindows();
c.sendMessage("You teleport home.");
c.getPA().teleTabTeleport(3094, 3496, 0, "teleTab");
}
break;
case 15005:
if (c.getItems().playerHasItem(15005, 1)) {
c.getItems().deleteItem2(15005, 1);
c.getItems().addItem(15000, 1);
c.getPA().removeAllWindows();
c.sendMessage("You change your cape back into a regular cape");
}
break;
case 15006:
if (c.getItems().playerHasItem(15006, 1)) {
c.getItems().deleteItem2(15006, 1);
c.getItems().addItem(15000, 1);
c.getPA().removeAllWindows();
c.sendMessage("You change your cape back into a regular cape");
}
break;
case 15007:
if (c.getItems().playerHasItem(15007, 1)) {
c.getItems().deleteItem2(15007, 1);
c.getItems().addItem(15000, 1);
c.getPA().removeAllWindows();
c.sendMessage("You change your cape back into a regular cape");
}
break;
case 15000:
c.getDH().sendDialogues(7123, 945);
break;
		case 11694:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11696:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11698:
			c.sendMessage("Dismantle has been disabled.");
			break;
		case 11700:
			c.sendMessage("Dismantle has been disabled.");
			break;
		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName + " - Item3rdOption: " + itemId);
			break;
		}

	}

}
