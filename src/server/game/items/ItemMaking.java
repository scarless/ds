package server.game.items;

import server.Config;
import server.game.players.Client;

public class ItemMaking {
	
	public static void makeGodsword(final Client c, int i) {
		if (c != null) {
			if (c.getItems().playerHasItem(11690) && c.getItems().playerHasItem(i)) {
				c.getItems().deleteItem(11690, 1);
				c.getItems().deleteItem(i, 1);
				c.getItems().addItem(i - 8, 1);
				c.sendMessage("You combine the hilt and the blade to make a godsword.");
			}
			c.sendMessage(""+i);
		}
	}
	
	public static void makeDFS(final Client c, int itemUsed, int useWith) {
		if (c.playerLevel[c.playerSmithing] >= 95) {
			c.getItems().deleteItem(1540, c.getItems().getItemSlot(1540), 1);
			c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286), 1);
			c.getItems().addItem(11284, 1);
			c.sendMessage("You combine the two materials to create a dragonfire shield.");
			c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE,
			c.playerSmithing);
		} else {
			c.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
		}
	}
	public static void makeTentacleWhip(final Client c, int itemUsed, int useWith) {
			c.getItems().deleteItem(4151, c.getItems().getItemSlot(4151), 1);
			c.getItems().deleteItem(15006, c.getItems().getItemSlot(15006), 1);
			c.getItems().addItem(15107, 1);
			c.sendMessage("You combine the two materials to create a Tentacle Whip.");
	}
	public static void makeMaleDiction(final Client c, int itemUsed, int useWith, int i) {
		c.getItems().deleteItem(11931, c.getItems().getItemSlot(11931), 1);
		c.getItems().deleteItem(11932, c.getItems().getItemSlot(11932), 1);
		c.getItems().deleteItem(11933, c.getItems().getItemSlot(11933), 1);
		c.getItems().addItem(11924, 1);
		c.sendMessage("You combine the three materials to create a Malediction ward.");
}
	public static void makeOdium(final Client c, int itemUsed, int useWith, int i) {
		c.getItems().deleteItem(11928, c.getItems().getItemSlot(11928), 1);
		c.getItems().deleteItem(11929, c.getItems().getItemSlot(11929), 1);
		c.getItems().deleteItem(11930, c.getItems().getItemSlot(11930), 1);
		c.getItems().addItem(11926, 1);
		c.sendMessage("You combine the three materials to create a Odium ward.");
}

}