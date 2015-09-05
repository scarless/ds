package server.game.items;

import server.game.items.ItemMaking;
import server.game.players.Client;

public class CombinedItems {

	public static void handleCreateItem(final Client c) {
		if (c.itemAction == 11284) {
			ItemMaking.makeDFS(c, 11286, 1540);
		}
		if (c.itemAction == 15107) {
			ItemMaking.makeTentacleWhip(c, 15006, 4151);
		}
		if (c.itemAction == 11924) {
			ItemMaking.makeMaleDiction(c, 11931, 11932, 11933);
		}
		if (c.itemAction == 11926) {
			ItemMaking.makeOdium(c, 11928, 11929, 11930);
		}
		if (c.itemAction == 11694) {
			ItemMaking.makeGodsword(c, (c.itemShown + 8));
		}
		c.getPA().removeAllWindows();
		c.itemAction = -1;
		return;
	}
	
	public static void handleCancel(final Client c) {
		c.getPA().removeAllWindows();
		c.itemAction = -1;
		return;
	}
}