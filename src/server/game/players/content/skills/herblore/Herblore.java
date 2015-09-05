package server.game.players.content.skills.herblore;

import server.Config;
import server.game.players.Client;

public class Herblore extends HerbloreData {

	public static void cleanHerb(final Client player, final int herbId,
			int slotId) {
		for (int i = 0; i < CLEAN_DATA.length; i++) {
			if (herbId == CLEAN_DATA[i][0]) {
				if (player.getItems().playerHasItem(CLEAN_DATA[i][0])) {
					if (player
							.getLevelForXP(player.playerXP[player.playerHerblore]) < CLEAN_DATA[i][2]) {
						player.sendMessage("You need an herblore level of "
								+ CLEAN_DATA[i][2] + " to clean this herb.");
						return;
					}
					player.getItems().deleteItem(CLEAN_DATA[i][0], slotId, 1);
					player.getItems().addItem(CLEAN_DATA[i][1], 1);
					player.getPA().addSkillXP(CLEAN_DATA[i][3] * Config.HERBLORE_EXPERIENCE,player.playerHerblore);
					player.sendMessage("You clean the dirt from the herb.");
				}
			}
		}
	}

	public static void makePotions(final Client c, int useItem, int itemUsed) {
		for (final PotionData p : PotionData.values()) {
			if ((useItem == p.getPrimary() && itemUsed == p.getSecondary()) || (useItem == p.getSecondary() && itemUsed == p.getPrimary())) {
				if (c.playerLevel[c.playerHerblore] < p.getReq()) {
					c.sendMessage("You need an herblore level of " + p.getReq() + " to mix this potion.");
					return;
				}
				if (p.getPrimary() <= 0 || p.getSecondary() <= 0) {
					return;
				}
				if (!c.getItems().playerHasItem(p.getPrimary(), 1)
						|| !c.getItems().playerHasItem(p.getSecondary(),
								p.isTar() ? 15 : 1)) {
					c.sendMessage("You do not have the required ingredients to make this.");
					return;
				}
				c.startAnimation(ANIM);
				c.getItems().deleteItem(p.getPrimary(), 1);
				c.getItems().deleteItem2(p.getSecondary(), p.isTar() ? 15 : 1);
				c.getItems().addItem(p.getFinal(), p.isTar() ? 15 : 1);
				c.sendMessage(p.isTar() ? "You mix the " + c.getItems().getItemName(p.getPrimary()) + " into the swamp tar." : "You make a " + c.getItems().getItemName(p.getFinal()).toLowerCase() + ".");
				c.getPA().addSkillXP(p.getExp() * Config.HERBLORE_EXPERIENCE,
						c.playerHerblore);
			}
		}
	}

	public static boolean handleCoconut(Client player, int itemUsed,
			int usedWith) {
		if ((itemUsed == 2347 && usedWith == 5974)
				|| (itemUsed == 5974 && usedWith == 2347)) {
			player.sendMessage("You crush the coconut with a hammer.");
			player.getItems().deleteItem(5974, 1);
			player.getItems().addItem(5976, 1);
			return false;
		}
		if ((itemUsed == 229 && usedWith == 5976)
				|| (itemUsed == 5976 && usedWith == 229)) {
			player.getItems().deleteItem(229, 1);
			player.getItems().addItem(5935, 1);
			player.getItems().deleteItem(5976, 1);
			player.getItems().addItem(5978, 1);
			player.sendMessage("You overturn the coconut into a vial.");
			return true;
		}
		return false;
	}
}
