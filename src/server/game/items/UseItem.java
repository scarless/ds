package server.game.items;

import server.Config;
import server.game.minigames.CrystalChest;
import server.game.minigames.WarriorsGuild;
import server.game.players.Client;
import server.game.players.content.FlourMill;
import server.game.players.content.skills.cooking.Cooking;
import server.game.players.content.skills.farming.herbs.HerbFarming;
import server.game.players.content.skills.farming.trees.TreeFarming;
import server.game.players.content.skills.firemaking.Firemaking;
import server.game.players.content.skills.herblore.Grinding;
import server.game.players.content.skills.herblore.Herblore;
import server.game.players.content.random.WeaponChest;
import server.game.players.content.random.ArmourChest;
import server.util.Misc;

/**
 * @author Sanity
 * @author Ryan
 * @author Lmctruck30 Revised by Shawn Notes by Shawn
 */

public class UseItem {

	/**
	 * Using items on an object.
	 * 
	 * @param c
	 * @param objectID
	 * @param objectX
	 * @param objectY
	 * @param itemId
	 */
	public static void ItemonObject(Client c, int objectID, int objectX,
			int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		if (c.hasPin) {
			return;
		}
		switch (objectID) {
		
		
				case 375:
			if(itemId == CrystalChest.KEY)
				CrystalChest.searchChest(c, objectID, objectX, objectY);
			break;
		case 2403:
			if(itemId == WeaponChest.KEY)
				WeaponChest.searchChest(c, objectID, objectX, objectY);
			break;
		case 4111:
			if(itemId == ArmourChest.KEY)
				ArmourChest.searchChest(c, objectID, objectX, objectY);
			break;
		
		case 2714:
			FlourMill.grainOnHopper(c, objectID, itemId);
			break;
		case 15621:
			WarriorsGuild.startArmourAnimation(c, itemId);
			break;
		case 2783:
			c.getSmithingInt().showSmithInterface(itemId);
			break;
		case 8689:
			if(c.getItems().playerHasItem(1925, 1) && c.objectId == 8689) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItems().addItem(1927 ,1);
					c.getItems().deleteItem(1925, 1);
					
				} else {
					   c.sendMessage("You need a bucket to milk a cow!");
					}
				break;
		case 8151:
		case 8389:
		case 7848:
			c.sendMessage("This should probably be raked first.");
			break;
		case 8392:
		//case 8151:
			if (c.getItems().playerHasItem(5343)) {
				if (TreeFarming.isTreeSeed(c, itemId)) {
					TreeFarming.farmTree(c, itemId);
				} else if (HerbFarming.isHerbSeed(c, itemId)) {
					HerbFarming.farmHerbs(c, itemId);
				} else {
					c.sendMessage("I need a tree seed or herb seed to plant in this patch.");
				}
			} else {
				c.sendMessage("I need a seed dibber in order to plant a seed.");
			}
			break;
		case 2728:
		case 12269:
		case 409:// here
			if (c.getPrayer().isBone(itemId))
			{
				c.getPrayer().bonesOnAltar2(itemId, 28);
				c.usingAltar = true;
			}
			break;// here//
		/*
		 * case 2728: case 12269: c.getCooking().itemOnObject(itemId); break;
		 */
		default:
			if (c.playerRights == 3)
				Misc.println("Player At Object id: " + objectID
						+ " with Item id: " + itemId);
			break;
		}

	}

	/**
	 * Using items on items.
	 * 
	 * @param c
	 * @param itemUsed
	 * @param useWith
	 */
	// public static void ItemonItem(Client c, int itemUsed, int useWith) {
	public static void ItemonItem(final Client c, final int itemUsed,
			final int useWith, final int itemUsedSlot, final int usedWithSlot) {
		Grinding.grindItem(c, itemUsed, useWith);
		Herblore.makePotions(c, useWith, itemUsed);
		if (c.getItems().getItemName(itemUsed).contains("(")
				&& c.getItems().getItemName(useWith).contains("("))
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		if (itemUsed == 1733 || useWith == 1733)
			c.getCrafting().handleLeather(itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755)
			c.getCrafting().handleChisel(itemUsed, useWith);
		if (itemUsed == 946 || useWith == 946)
			c.getFletching().handleLog(itemUsed, useWith);
		if (itemUsed == 53 || useWith == 53 || itemUsed == 52 || useWith == 52)
			c.getFletching().makeArrows(itemUsed, useWith);
		if ((itemUsed == 1540 && useWith == 11286) || (itemUsed == 11286 && useWith == 1540)) {
			c.getDH().sendItems(1, 11283);
		}
		//if ((itemUsed == 1800 && useWith == 4081) || (itemUsed == 4081 && useWith == 1800)) {
		//	c.getDH().sendItems(1, 10588);
		//}
		if ((itemUsed == 4151 && useWith == 15006) || (itemUsed == 15006 && useWith == 4151)) {
			c.getDH().sendItems(5, 15107);
		}
		if ((itemUsed == 11931 && useWith == 11932) || (itemUsed == 11931 && useWith == 11933) || (itemUsed == 11932 && useWith == 11931) || (itemUsed == 11932 && useWith == 11933) || (itemUsed == 11933 && useWith == 11931) || (itemUsed == 11933 && useWith == 11932)) {
			c.getDH().sendItems(7, 11924);
		}
		if ((itemUsed == 11928 && useWith == 11929) || (itemUsed == 11928 && useWith == 11930) || (itemUsed == 11929 && useWith == 11928) || (itemUsed == 11929 && useWith == 11930) || (itemUsed == 11930 && useWith == 11928) || (itemUsed == 11930 && useWith == 11929)) {
			c.getDH().sendItems(9, 11926);
		}
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (c.getItems().playerHasItem(11690) && c.getItems().playerHasItem(hilt)) {
				c.getDH().sendItems(3, (hilt - 8));
			}
		}
		if (itemUsed == 3188 && useWith == 1231 || itemUsed == 1231 && useWith == 3188) { //Removing posion
			c.startAnimation(885);		
			c.sendMessage("You remove the deadly posion from the blade!");
			c.getItems().deleteItem(1231, c.getItems().getItemSlot(1231),1);
			c.getItems().deleteItem(3188, c.getItems().getItemSlot(3188),1);
			c.getItems().addItem(1215,1);
		}	
		if (itemUsed == 4081 && useWith == 1800 || itemUsed == 1800 && useWith == 4081) { //making salve amulet (e)
			c.startAnimation(885);		
			c.sendMessage("You combine the two items together to make a salve amulet (e).");
			c.getItems().deleteItem(4081, c.getItems().getItemSlot(4081),1);
			c.getItems().deleteItem(1800, c.getItems().getItemSlot(1800),1);
			c.getItems().addItem(10588,1);
		}
		
		if (itemUsed == 1215 && useWith == 187 || itemUsed == 187 && useWith == 1215) { //Adding posion
			c.startAnimation(2246);		
			c.sendMessage("You add the posionous liquid to the blade!");
			c.getItems().deleteItem(187, c.getItems().getItemSlot(187),1);
			c.getItems().deleteItem(1215, c.getItems().getItemSlot(1215),1);			
			c.getItems().addItem(1231,1);
		}
		
				if (itemUsed == 269 && useWith == 2440 || itemUsed == 269 && useWith == 2436 || itemUsed == 269 && useWith == 2442) {
        if (c.getItems().playerHasItem(2440, 1) && c.getItems().playerHasItem(2436, 1) && c.getItems().playerHasItem(2442, 1) && c.getItems().playerHasItem(269, 1)) {
            if (c.playerLevel[c.playerHerblore] >= 90) {
            c.getItems().deleteItem(269, c.getItems().getItemSlot(269),1);
            c.getItems().deleteItem(2440, c.getItems().getItemSlot(2440),1);
            c.getItems().deleteItem(2436, c.getItems().getItemSlot(2436),1);
            c.getItems().deleteItem(2442, c.getItems().getItemSlot(2442),1);
            c.getItems().addItem(11517,1);
                c.sendMessage("You make a Super Combat Potion (4).");
                c.getPA().addSkillXP(500 * Config.HERBLORE_EXPERIENCE, c.playerHerblore);
            } else {
                c.sendMessage("You need a herblore level of 90 to make that potion.");
            }
            } else {
                c.sendMessage("You need all Combat Potions and Torstol to make a Super Combat Potion.");
            }
        }
		
		if (itemUsed == CrystalChest.toothHalf()
				&& useWith == CrystalChest.loopHalf()
				|| itemUsed == CrystalChest.loopHalf()
				&& useWith == CrystalChest.toothHalf()) {
			CrystalChest.makeKey(c);
		}
		
		if (itemUsed == 9142 && useWith == 9190 || itemUsed == 9190
				&& useWith == 9142) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9241, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 6 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		
		if (itemUsed == 9143 && useWith == 9191 || itemUsed == 9191
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9242, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 9143 && useWith == 9192 || itemUsed == 9192
				&& useWith == 9143) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9243, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 7 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9193 || itemUsed == 9193
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9244, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 10 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 9144 && useWith == 9194 || itemUsed == 9194
				&& useWith == 9144) {
			if (c.playerLevel[c.playerFletching] >= 58) {
				int boltsMade = c.getItems().getItemAmount(itemUsed) > c
						.getItems().getItemAmount(useWith) ? c.getItems()
						.getItemAmount(useWith) : c.getItems().getItemAmount(
						itemUsed);
				c.getItems().deleteItem(useWith,
						c.getItems().getItemSlot(useWith), boltsMade);
				c.getItems().deleteItem(itemUsed,
						c.getItems().getItemSlot(itemUsed), boltsMade);
				c.getItems().addItem(9245, boltsMade);
				c.getPA().addSkillXP(
						boltsMade * 13 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 58 to fletch this item.");
			}
		}
		if (itemUsed == 1601 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1601) {
			if (c.playerLevel[c.playerFletching] >= 63) {
				c.getItems()
						.deleteItem(1601, c.getItems().getItemSlot(1601), 1);
				c.getItems().addItem(9192, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 63 to fletch this item.");
			}
		}
		if ((itemUsed == 985 && useWith == 987) || (itemUsed == 987 && useWith == 985)) {
			c.sendMessage("You combine the two materials to create a Crystal key.");
		}
		if (itemUsed == 1607 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1607) {
			if (c.playerLevel[c.playerFletching] >= 65) {
				c.getItems()
						.deleteItem(1607, c.getItems().getItemSlot(1607), 1);
				c.getItems().addItem(9189, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 65 to fletch this item.");
			}
		}
		if (itemUsed == 1605 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1605) {
			if (c.playerLevel[c.playerFletching] >= 71) {
				c.getItems()
						.deleteItem(1605, c.getItems().getItemSlot(1605), 1);
				c.getItems().addItem(9190, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 71 to fletch this item.");
			}
		}
		if (itemUsed == 1603 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1603) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1603, c.getItems().getItemSlot(1603), 1);
				c.getItems().addItem(9191, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed == 590 || useWith == 590) {
			Firemaking.attemptFire(c, itemUsed, useWith, c.absX, c.absY, false);
		}
		if (itemUsed == 1615 && useWith == 1755 || itemUsed == 1755
				&& useWith == 1615) {
			if (c.playerLevel[c.playerFletching] >= 73) {
				c.getItems()
						.deleteItem(1615, c.getItems().getItemSlot(1615), 1);
				c.getItems().addItem(9193, 15);
				c.getPA().addSkillXP(8 * Config.FLETCHING_EXPERIENCE,
						c.playerFletching);
			} else {
				c.sendMessage("You need a fletching level of 73 to fletch this item.");
			}
		}
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710
				&& useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366
				&& useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368), 1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366), 1);
			c.getItems().addItem(1187, 1);
		}



		switch (itemUsed) {
		/*
		 * case 1511: case 1521: case 1519: case 1517: case 1515: case 1513:
		 * case 590: c.getFiremaking().checkLogType(itemUsed, useWith); break;
		 */

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemUsed
						+ " with Item id: " + useWith);
			break;
		}
	}

	/**
	 * Using items on NPCs.
	 * 
	 * @param c
	 * @param itemId
	 * @param npcId
	 * @param slot
	 */
	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch (itemId) {

		default:
			if (c.playerRights == 3)
				Misc.println("Player used Item id: " + itemId
						+ " with Npc id: " + npcId + " With Slot : " + slot);
			break;
		}

	}

}
