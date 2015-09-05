package server.game.items;

import server.Config;
import server.Server;
import server.game.npcs.NPCHandler;
import server.game.npcs.pet.PetData;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.game.players.content.pvphighscores.HighScoresBoard;
import server.game.players.saving.PlayerSave;
import server.util.Misc;

/**
 * Indicates Several Usage Of Items
 * 
 * @author Sanity Revised by Shawn Notes by Shawn
 */
public class ItemAssistant {

	private Client c;

	public ItemAssistant(Client client) {
		this.c = client;
	}

	public boolean playerHasEquipped(int slot, int itemID) {
		return c.playerEquipment[slot] == itemID;
	}

	public boolean playerHasEquipped(int itemID) {
		itemID++;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] == itemID) {
				return true;
			}
		}
		return false;
	}

	public int[] Nests = { 5291, 5292, 5293, 5294, 5295, 5296, 5297, 5298,
			5299, 5300, 5301, 5302, 5303, 5304 };

	public void handleNests(int itemId) {
		int reward = Nests[Misc.random(14)];
		addItem(reward, 3 + Misc.random(5));
		deleteItem(itemId, 1);
		c.sendMessage("You search the nest");
	}

	/**
	 * Trimmed and untrimmed skillcapes.
	 */
	public int[][] skillcapes = { { 9747, 9748 }, // Attack
			{ 9753, 9754 }, // Defence
			{ 9750, 9751 }, // Strength
			{ 9768, 9769 }, // Hitpoints
			{ 9756, 9757 }, // Range
			{ 9759, 9760 }, // Prayer
			{ 9762, 9763 }, // Magic
			{ 9801, 9802 }, // Cooking
			{ 9807, 9808 }, // Woodcutting
			{ 9783, 9784 }, // Fletching
			{ 9798, 9799 }, // Fishing
			{ 9804, 9805 }, // Firemaking
			{ 9780, 9781 }, // Crafting
			{ 9795, 9796 }, // Smithing
			{ 9792, 9793 }, // Mining
			{ 9774, 9775 }, // Herblore
			{ 9771, 9772 }, // Agility
			{ 9777, 9778 }, // Thieving
			{ 9786, 9787 }, // Slayer
			{ 9810, 9811 }, // Farming
			{ 9765, 9766 } // Runecraft
	};

	/**
	 * Broken barrows items.
	 */
	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 },
			{ 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 },
			{ 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 },
			{ 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 },
			{ 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 },
			{ 4747, 4926 }, { 4749, 4968 }, { 4751, 4794 }, { 4753, 4980 },
			{ 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	/**
	 * Empties all of (a) player's items.
	 */
	public void resetItems(int WriteFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(WriteFrame);
				c.getOutStream().writeWord(c.playerItems.length);
				for (int i = 0; i < c.playerItems.length; i++) {
					if (c.playerItemsN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
					} else {
						c.getOutStream().writeByte(c.playerItemsN[i]);
					}
					c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}

	public void addItemToBank(int itemId, int amount) {
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0 || c.bankItems[i] == itemId + 1
					&& c.bankItemsN[i] + amount < Integer.MAX_VALUE) {
				c.bankItems[i] = itemId + 1;
				c.bankItemsN[i] += amount;
				resetBank();
				return;
			}
		}
	}

	/**
	 * Counts (a) player's items.
	 * 
	 * @param itemID
	 * @return count start
	 */
	public int getItemCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (c.playerItems[j] == itemID + 1) {
				count += c.playerItemsN[j];
			}
		}
		return count;
	}

	public void replaceItem(Client c, int i, int l) {
		for (int k = 0; k < c.playerItems.length; k++) {
			if (playerHasItem(i, 1)) {
				deleteItem(i, getItemSlot(i), 1);
				addItem(l, 1);
			}
		}
	}

	/**
	 * Gets the bonus' of an item.
	 */
	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < c.playerBonus.length; i++) {
			if (c.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + c.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -"
						+ java.lang.Math.abs(c.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			c.getPA().sendFrame126(send, (1675 + i + offset));
		}

	}

	/**
	 * Gets the total count of (a) player's items.
	 * 
	 * @param itemID
	 * @return
	 */
	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (Item.itemIsNote[itemID + 1]) {
				if (itemID + 2 == c.playerItems[j])
					count += c.playerItemsN[j];
			}
			if (!Item.itemIsNote[itemID + 1]) {
				if (itemID + 1 == c.playerItems[j]) {
					count += c.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] == itemID + 1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public void sendItemsKept4(int a, int length1) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(6963);
				c.getOutStream().writeWord(length1);
				for (int i = 0; i < length1; i++) {
					if (c.playerItemsN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(1);
					} else {
						c.getOutStream().writeByte(1);
					}
					if (a > 0) {
						c.getOutStream().writeWordBigEndianA(a + 1);
					} else {
						c.getOutStream().writeWordBigEndianA(0);
					}
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}

	/**
	 * Send the items kept on death.
	 */
	public void sendItemsKept() {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(6963);
				c.getOutStream().writeWord(c.itemKeptId.length);
				for (int i = 0; i < c.itemKeptId.length; i++) {
					if (c.playerItemsN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(1);
					} else {
						c.getOutStream().writeByte(1);
					}
					if (c.itemKeptId[i] > 0) {
						c.getOutStream().writeWordBigEndianA(
								c.itemKeptId[i] + 1);
					} else {
						c.getOutStream().writeWordBigEndianA(0);
					}
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}

	/**
	 * Item kept on death
	 **/
	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 > 0) {
				int inventoryItemValue = c.getShops().getItemShopValue(
						c.playerItems[i] - 1);
				if (inventoryItemValue > value && (!c.invSlot[i])) {
					value = inventoryItemValue;
					item = c.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			if (c.playerEquipment[i1] > 0) {
				int equipmentItemValue = c.getShops().getItemShopValue(
						c.playerEquipment[i1]);
				if (equipmentItemValue > value && (!c.equipSlot[i1])) {
					value = equipmentItemValue;
					item = c.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			c.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(c.playerItems[slotId] - 1,
						getItemSlot(c.playerItems[slotId] - 1), 1);
			}
		} else {
			c.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		c.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death.
	 **/
	public void resetKeepItems() {
		for (int i = 0; i < c.itemKeptId.length; i++) {
			c.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < c.invSlot.length; i1++) {
			c.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < c.equipSlot.length; i2++) {
			c.equipSlot[i2] = false;
		}
	}

	/**
	 * Deletes all of a player's items.
	 **/
	public void deleteAllItems() {
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			deleteEquipment(c.playerEquipment[i1], i1);
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			deleteItem(c.playerItems[i] - 1, getItemSlot(c.playerItems[i] - 1),
					c.playerItemsN[i]);
		}
	}

	public boolean isWearingItem(int itemID) {
		for (int i = 0; i < 12; i++) {
			if (c.playerEquipment[i] == itemID) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check all slots and determine the amount of said item worn in that slot
	 */
	public int getWornItemAmount(int itemID) {
		for (int i = 0; i < 12; i++) {
			if (c.playerEquipment[i] == itemID) {
				return c.playerEquipmentN[i];
			}
		}
		return 0;
	}

	/**
	 * Drops all items for a killer.
	 **/
	public void dropAllItems() {
		Client o = (Client) PlayerHandler.players[c.killerId];

		for (int i = 0; i < c.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(c.playerItems[i] - 1)) {
					Server.itemHandler.createGroundItem(o,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.killerId);
				} else {
					if (specialCase(c.playerItems[i] - 1))
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(),
								getUntradePrice(c.playerItems[i] - 1),
								c.killerId);
					Server.itemHandler.createGroundItem(c,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerItems[i] - 1,
						c.getX(), c.getY(), c.playerItemsN[i], c.playerId);
			}
		}
		for (int e = 0; e < c.playerEquipment.length; e++) {
			if (o != null) {
				if (tradeable(c.playerEquipment[e])) {
					Server.itemHandler.createGroundItem(o,
							c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.killerId);
				} else {
					if (specialCase(c.playerEquipment[e]))
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(),
								getUntradePrice(c.playerEquipment[e]),
								c.killerId);
					Server.itemHandler.createGroundItem(c,
							c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerEquipment[e],
						c.getX(), c.getY(), c.playerEquipmentN[e], c.playerId);
			}
		}
		if (o != null) {
			Server.itemHandler.createGroundItem(o, 526, c.getX(), c.getY(), 1,
					c.killerId);
			HighScoresBoard.getLeaderBoard().setOption(c); // Used to update the
															// kills and deaths
															// of the player.
			HighScoresBoard.getLeaderBoard().setOption(o); // Used to update the
															// kills and deaths
															// of the player.
		}
	}

	/**
	 * Untradable items with a special currency. (Tokkel, etc)
	 * 
	 * @param item
	 * @return amount
	 */
	public int getUntradePrice(int item) {
		switch (item) {
		case 2518:
		case 2524:
		case 2526:
			return 100000;
		case 2520:
		case 2522:
			return 150000;
		}
		return 0;
	}

	/**
	 * Special items with currency.
	 */
	public boolean specialCase(int itemId) {
		switch (itemId) {
		case 2518:
		case 2520:
		case 2522:
		case 2524:
		case 2526:
			return true;
		}
		return false;
	}
	
public void deleteItem1(int id, int amount) {
		if(id <= 0)
			return;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (amount <= 0)
				break;
			if (c.playerItems[j] == id+1) {
				c.playerItems[j] = 0;
				c.playerItemsN[j] = 0;
				amount--;			
			}	
		}
		PlayerSave.saveGame(c);
		//resetItems(3214);
	}

	/**
	 * Voided items. (Not void knight items..)
	 * 
	 * @param itemId
	 */
	public void addToVoidList(int itemId) {
		switch (itemId) {
		case 2518:
			c.voidStatus[0]++;
			break;
		case 2520:
			c.voidStatus[1]++;
			break;
		case 2522:
			c.voidStatus[2]++;
			break;
		case 2524:
			c.voidStatus[3]++;
			break;
		case 2526:
			c.voidStatus[4]++;
			break;
		}
	}

	/**
	 * Handles tradable items.
	 */
	public boolean tradeable(int itemId) {
		for (int j = 0; j < Config.ITEM_TRADEABLE.length; j++) {
			if (itemId == Config.ITEM_TRADEABLE[j]) {
				return false;
			}
		}
		for (int j = 0; j < PetData.petData.length; j++) {
			if (itemId == PetData.petData[j][1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adds an item to a player's inventory.
	 **/

	public boolean addItem(int item, int amount) {
		// synchronized(c) {
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((((freeSlots() >= 1) || playerHasItem(item, 1)) && Item.itemStackable[item])
				|| ((freeSlots() > 0) && !Item.itemStackable[item])) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if ((c.playerItems[i] == (item + 1))
						&& Item.itemStackable[item] && (c.playerItems[i] > 0)) {
					c.playerItems[i] = (item + 1);
					if (((c.playerItemsN[i] + amount) < Config.MAXITEM_AMOUNT)
							&& ((c.playerItemsN[i] + amount) > -1)) {
						c.playerItemsN[i] += amount;
					} else {
						c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
					}
					if (c.getOutStream() != null && c != null) {
						c.getOutStream().createFrameVarSizeWord(34);
						c.getOutStream().writeWord(3214);
						c.getOutStream().writeByte(i);
						c.getOutStream().writeWord(c.playerItems[i]);
						if (c.playerItemsN[i] > 254) {
							c.getOutStream().writeByte(255);
							c.getOutStream().writeDWord(c.playerItemsN[i]);
						} else {
							c.getOutStream().writeByte(c.playerItemsN[i]);
						}
						c.getOutStream().endFrameVarSizeWord();
						c.flushOutStream();
					}
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] <= 0) {
					c.playerItems[i] = item + 1;
					if ((amount < Config.MAXITEM_AMOUNT) && (amount > -1)) {
						c.playerItemsN[i] = 1;
						if (amount > 1) {
							c.getItems().addItem(item, amount - 1);
							return true;
						}
					} else {
						c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
					}
					resetItems(3214);
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			resetItems(3214);
			c.sendMessage("Not enough space in your inventory.");
			return false;
		}
		// }
	}

	/**
	 * Gets the item type.
	 */
	public String itemType(int item) {
		for (int i = 0; i < Item.capes.length; i++) {
			if (item == Item.capes[i])
				return "cape";
		}
		for (int i = 0; i < Item.hats.length; i++) {
			if (item == Item.hats[i])
				return "hat";
		}
		for (int i = 0; i < Item.boots.length; i++) {
			if (item == Item.boots[i])
				return "boots";
		}
		for (int i = 0; i < Item.gloves.length; i++) {
			if (item == Item.gloves[i])
				return "gloves";
		}
		for (int i = 0; i < Item.shields.length; i++) {
			if (item == Item.shields[i])
				return "shield";
		}
		for (int i = 0; i < Item.amulets.length; i++) {
			if (item == Item.amulets[i])
				return "amulet";
		}
		for (int i = 0; i < Item.arrows.length; i++) {
			if (item == Item.arrows[i])
				return "arrows";
		}
		for (int i = 0; i < Item.rings.length; i++) {
			if (item == Item.rings[i])
				return "ring";
		}
		for (int i = 0; i < Item.body.length; i++) {
			if (item == Item.body[i])
				return "body";
		}
		for (int i = 0; i < Item.legs.length; i++) {
			if (item == Item.legs[i])
				return "legs";
		}
		return "weapon";
	}

	/**
	 * Item bonuses.
	 **/
	public final String[] BONUS_NAMES = { "Stab", "Slash", "Crush", "Magic",
			"Range", "Stab", "Slash", "Crush", "Magic", "Range", "Strength",
			"Prayer" };

	/**
	 * Resets item bonuses.
	 */
	public void resetBonus() {
		for (int i = 0; i < c.playerBonus.length; i++) {
			c.playerBonus[i] = 0;
		}
	}

	/**
	 * Gets the item bonus from the item.cfg.
	 */
	public void getBonus() {
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > -1) {
				for (int j = 0; j < Config.ITEM_LIMIT; j++) {
					if (Server.itemHandler.ItemList[j] != null) {
						if (Server.itemHandler.ItemList[j].itemId == c.playerEquipment[i]) {
							for (int k = 0; k < c.playerBonus.length; k++) {
								c.playerBonus[k] += Server.itemHandler.ItemList[j].Bonuses[k];
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Weapon type.
	 **/
	public void sendWeapon(int Weapon, String WeaponName) {
		String WeaponName2 = WeaponName.replaceAll("Bronze", "");
		WeaponName2 = WeaponName2.replaceAll("Iron", "");
		WeaponName2 = WeaponName2.replaceAll("Steel", "");
		WeaponName2 = WeaponName2.replaceAll("Black", "");
		WeaponName2 = WeaponName2.replaceAll("Mithril", "");
		WeaponName2 = WeaponName2.replaceAll("Adamant", "");
		WeaponName2 = WeaponName2.replaceAll("Rune", "");
		WeaponName2 = WeaponName2.replaceAll("Granite", "");
		WeaponName2 = WeaponName2.replaceAll("Dragon", "");
		WeaponName2 = WeaponName2.replaceAll("Drag", "");
		WeaponName2 = WeaponName2.replaceAll("Crystal", "");
		WeaponName2 = WeaponName2.trim();
		/**
		 * Attack styles.
		 */
		if (WeaponName.equals("Unarmed")) {
			c.setSidebarInterface(0, 5855); // punch, kick, block
			c.getPA().sendFrame126(WeaponName, 5857);
		} else if (WeaponName.endsWith("whip")
				|| WeaponName2.contains("Abyssal tentacle")) {
			c.setSidebarInterface(0, 12290); // flick, lash, deflect
			c.getPA().sendFrame246(12291, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 12293);
		} else if (WeaponName.endsWith("bow") || WeaponName.endsWith("10")
				|| WeaponName.endsWith("full")
				|| WeaponName.startsWith("seercull")) {
			c.setSidebarInterface(0, 1764); // accurate, rapid, longrange
			c.getPA().sendFrame246(1765, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 1767);
		} else if (WeaponName.startsWith("Staff") || WeaponName.startsWith("Trident")
				|| WeaponName.endsWith("staff") || WeaponName.endsWith("wand")) {
			c.setSidebarInterface(0, 328); // spike, impale, smash, block
			c.getPA().sendFrame246(329, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 331);
		} else if (WeaponName2.startsWith("dart")
				|| WeaponName2.startsWith("knife")
				|| WeaponName2.startsWith("javelin")
				|| WeaponName.equalsIgnoreCase("toktz-xil-ul")) {
			c.setSidebarInterface(0, 4446); // accurate, rapid, longrange
			c.getPA().sendFrame246(4447, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4449);
		} else if (WeaponName2.startsWith("dagger")
				|| WeaponName2.contains("anchor")
				|| WeaponName2.contains("Abyssal tentacle")
				|| WeaponName2.contains("Fremennik blade")
				|| WeaponName2.contains("sword")) {
			c.setSidebarInterface(0, 2276); // stab, lunge, slash, block
			c.getPA().sendFrame246(2277, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2279);
		} else if (WeaponName2.startsWith("pickaxe")) {
			c.setSidebarInterface(0, 5570); // spike, impale, smash, block
			c.getPA().sendFrame246(5571, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 5573);
		} else if (WeaponName2.startsWith("axe")
				|| WeaponName2.startsWith("battleaxe")) {
			c.setSidebarInterface(0, 1698); // chop, hack, smash, block
			c.getPA().sendFrame246(1699, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 1701);
		} else if (WeaponName2.startsWith("halberd")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("Scythe")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("spear")) {
			c.setSidebarInterface(0, 4679); // lunge, swipe, pound, block
			c.getPA().sendFrame246(4680, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4682);
		} else if (WeaponName2.toLowerCase().contains("mace")) {
			c.setSidebarInterface(0, 3796);
			c.getPA().sendFrame246(3797, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 3799);

		} else if (c.playerEquipment[c.playerWeapon] == 4153) {
			c.setSidebarInterface(0, 425); // war hammer equip.
			c.getPA().sendFrame246(426, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 428);
		} else {
			c.setSidebarInterface(0, 2423); // chop, slash, lunge, block
			c.getPA().sendFrame246(2424, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2426);
		}

	}

	/**
	 * Weapon requirements.
	 **/
	public void getRequirements(String itemName, int itemId) {
		c.attackLevelReq = c.defenceLevelReq = c.strengthLevelReq = c.rangeLevelReq = c.magicLevelReq = 0;
		if (itemName.contains("mystic") || itemName.contains("nchanted")) {
			if (itemName.contains("staff")) {
				c.magicLevelReq = 20;
				c.attackLevelReq = 40;
			} else {
				c.magicLevelReq = 20;
				c.defenceLevelReq = 20;
			}
		}
		if (itemName.contains("slayer helmet")) {
			c.defenceLevelReq = 10;
		}
		if (itemName.contains("halo")) {
			c.defenceLevelReq = 1;
		}
		if (itemName.contains("initiate")) {
			c.defenceLevelReq = 20;
		}
		if (itemName.contains("infinity")) {
			c.magicLevelReq = 50;
			c.defenceLevelReq = 25;
		}
		if (itemName.contains("splitbark")) {
			c.magicLevelReq = 40;
			c.defenceLevelReq = 40;
		}
		if (itemName.contains("Zuriel's")) {
			c.magicLevelReq = 78;
			c.defenceLevelReq = 78;
		}
		if (itemName.contains("Morrigan's")) {
			c.rangeLevelReq = 78;
			c.defenceLevelReq = 78;
		}
		if (itemName.contains("Vesta's")) {
			c.defenceLevelReq = 78;
		}
		if (itemName.contains("Statiu's")) {
			c.defenceLevelReq = 78;
		}

		if (itemName.contains("Green d' hide")) {
			c.rangeLevelReq = 40;
			if (itemName.contains("body"))
				c.defenceLevelReq = 40;
			return;
		}
		if (itemName.contains("Blue d'hide")) {
			c.rangeLevelReq = 50;
			if (itemName.contains("body"))
				c.defenceLevelReq = 40;
			return;
		}
		if (itemName.contains("Red d'hide")) {
			c.rangeLevelReq = 60;
			if (itemName.contains("body"))
				c.defenceLevelReq = 40;
			return;
		}
		if (itemName.contains("Black d'hide")) {
			c.rangeLevelReq = 70;
			if (itemName.contains("body"))
				c.defenceLevelReq = 40;
			return;
		}
		if (itemName.contains("bronze")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("iron")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("steel")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 5;
			}
			return;
		}
		if (itemName.contains("attack cape(t)")) {
			c.attackLevelReq = 99;
		}
		if (itemName.contains("black")) {
			if (!itemName.contains("cavalier") && !itemName.contains("knife")
					&& !itemName.contains("ele'")
					&& !itemName.contains("elegant")
					&& !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("mithril")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 20;
			}
			return;
		}
		if (itemName.contains("adamant") || itemName.contains("adam")) {
			if (!itemName.contains("knife") && !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 30;
			}
			return;
		}
		if (itemName.contains("rock-shell")) {
			c.defenceLevelReq = 40;
		}

		if (itemName.contains("rune")) {
			if (!itemName.contains("gloves") && !itemName.contains("knife")
					&& !itemName.contains("dart")
					&& !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")
					&& !itemName.contains("'bow")) {
				c.attackLevelReq = c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("dragon")) {
			if (!itemName.contains("nti-") && !itemName.contains("fire")) {
				c.attackLevelReq = c.defenceLevelReq = 60;
				return;
			}
		}
		if (itemName.contains("crystal")) {
			if (itemName.contains("shield")) {
				c.defenceLevelReq = 70;
			} else {
				c.rangeLevelReq = 70;
			}
			return;
		}
		if (itemName.contains("ahrim")) {
			if (itemName.contains("staff")) {
				c.magicLevelReq = 70;
				c.attackLevelReq = 70;
			} else {
				c.magicLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("karil")) {
			if (itemName.contains("crossbow")) {
				c.rangeLevelReq = 70;
			} else {
				c.rangeLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("godsword")) {
			c.attackLevelReq = 75;
		}
		if (itemName.contains("3rd age") && !itemName.contains("amulet")) {
			c.defenceLevelReq = 60;
		}
		if (itemName.contains("Initiate")) {
			c.defenceLevelReq = 20;
		}
		if (itemName.contains("verac") || itemName.contains("guthan")
				|| itemName.contains("dharok") || itemName.contains("torag")) {

			if (itemName.contains("hammers")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("axe")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("warspear")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("flail")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else {
				c.defenceLevelReq = 70;
			}
		}

		switch (itemId) {
		case 12637:
		case 12638:
		case 12639:
			c.defenceLevelReq = 1;
			return;
		case 11694:
		case 11696:
		case 11698:
		case 11700:
			c.attackLevelReq = 75;
			return;
		case 1099:
			c.rangeLevelReq = 40;
			return;
		case 2493:
			c.rangeLevelReq = 50;
			return;
		case 2495:
			c.rangeLevelReq = 60;
			return;
		case 11720:
		case 11718:
		case 11722:
			c.defenceLevelReq = 70;
			c.rangeLevelReq = 70;
			return;
		case 12490:
		case 12492:
		case 12494:
		case 12496:
		case 12498:
		case 12500:
		case 12502:
		case 12504:
		case 12506:
		case 12508:
		case 12510:
		case 12512:
			c.defenceLevelReq = 40;
			c.rangeLevelReq = 40;
			return;
		case 10887:
			c.strengthLevelReq = 60;
			return;
			case 2491:
			case 2497:
			c.rangeLevelReq = 70;
			break;
			case 2503:
		c.rangeLevelReq = 70;
		c.defenceLevelReq = 40;
		break;
		case 12426:
		c.attackLevelReq = 65;
		break;
		case 12424:
		c.rangeLevelReq = 65;
		break;
		case 12422:
		c.magicLevelReq = 65;
		break;

		case 6528:
			c.strengthLevelReq = 60;
			return;
		case 8839:
		case 8840:
		case 8842:
		case 11663:
		case 11664:
		case 11665:
			c.attackLevelReq = 42;
			c.rangeLevelReq = 42;
			c.strengthLevelReq = 42;
			c.magicLevelReq = 42;
			c.defenceLevelReq = 42;
			return;
		case 868:
		case 10551:
		case 2501:
		case 2499:
		case 12460:
		case 12462:
		case 12464:
		case 12466:
		case 12468:
		case 12470:
		case 12472:
		case 12474:
		case 12476:
		case 12478:
		case 12480:
		case 12482:
		case 12484:
		case 12486:
		case 12488:
		case 13263:
			c.defenceLevelReq = 40;
			return;
		case 11235:
		case 6522:
			c.rangeLevelReq = 60;
			break;
		case 6524:
			c.defenceLevelReq = 60;
			break;
		case 6889:
		case 6914:
		case 12414:
		case 12415:
		case 12416:
		case 12417:
		case 12418:
			c.magicLevelReq = 60;
			break;
		case 861:
		case 14015:
			c.rangeLevelReq = 50;
			break;
		case 10828:
			c.defenceLevelReq = 55;
			break;
		case 11724:
		case 11726:
		case 11728:
			c.defenceLevelReq = 65;
			break;
		case 3751:
		case 3749:
		case 3755:
			c.defenceLevelReq = 40;
			break;

		case 859:
			c.rangeLevelReq = 50;
			break;
			
		case 11283:
		case 11284:
			c.defenceLevelReq = 75;
			break;

		case 4081:
			c.attackLevelReq = 50;
			break;

		case 10588:
			c.attackLevelReq = 50;
			c.strengthLevelReq = 50;
			break;

		case 851:
		case 853:
			c.rangeLevelReq = 30;
			break;

		case 847:
		case 849:
		case 12277:
		case 12279:
		case 12281:
		case 12283:
		case 12285:
		case 12287:
		case 12289:
		case 12291:
		case 12293:
		case 12295:
			c.rangeLevelReq = 20;
			break;

		case 845:
		case 843:
			c.rangeLevelReq = 5;
			break;
			
	//PVP shit
	case 13858:
	case 13861:
	case 13864:
	c.defenceLevelReq = 78;
	c.magicLevelReq = 78;
	break;
	case 13887:
	case 13893:
	c.defenceLevelReq = 78;
	break;
	case 13899:
	case 13902:
	c.attackLevelReq = 78;
	break;
	case 13870:
	case 13873:
	case 13876:
	c.defenceLevelReq = 78;
	c.rangeLevelReq = 78;
	break;
	case 13879:
	case 13883:
	c.rangeLevelReq = 78;
	break;
	case 13884:
	case 13890:
	case 13896:
	c.rangeLevelReq = 78;
	break;

		case 5698:
			c.attackLevelReq = 60;
			break;

		case 7462:
		case 7461:
			c.defenceLevelReq = 40;
			break;
		case 8846:
			c.defenceLevelReq = 5;
			break;
		case 8847:
			c.defenceLevelReq = 10;
			break;
		case 8848:
			c.defenceLevelReq = 20;
			break;
		case 8849:
			c.defenceLevelReq = 30;
			break;
		case 8850:
			c.defenceLevelReq = 40;
			break;

		case 7460:
			c.defenceLevelReq = 40;
			break;

		case 837:
			c.rangeLevelReq = 61;
			break;

		case 4151:
			c.attackLevelReq = 70;
			return;
		case 15107:
			c.attackLevelReq = 75;
			return;
		case 15110:
			c.attackLevelReq = 75;
			c.magicLevelReq = 75;
			return;
		case 11994:
		c.magicLevelReq = 75;
		return;
		case 15019:
		case 15023:
		case 15022:
			c.attackLevelReq = 80;
			return;
		case 6724:
			c.rangeLevelReq = 60;
			return;
		case 4153:
			c.attackLevelReq = 50;
			c.strengthLevelReq = 50;
			return;
		}
	}

	/**
	 * Two handed weapon check.
	 **/
	public boolean is2handed(String itemName, int itemId) {
		if (itemName.contains("ahrim") || itemName.contains("karil")
				|| itemName.contains("verac") || itemName.contains("guthan")
				|| itemName.contains("dharok") || itemName.contains("torag")) {
			return true;
		}
		if (itemName.contains("longbow") || itemName.contains("shortbow")
				|| itemName.contains("ark bow")) {
			return true;
		}
		if (itemName.contains("crystal")) {
			return true;
		}
		if (itemName.contains("magic shortbow(u)")) {
			return true;
		}
		if (itemName.contains("godsword")
				|| itemName.contains("aradomin sword")
				|| itemName.contains("2h") || itemName.contains("spear")) {
			return true;
		}
		switch (itemId) {

		case 6724:
		case 11730:
		case 4153:
		case 6528:
		case 10887:
		case 11777:
		case 13399:
			return true;
		}
		return false;
	}

	/**
	 * Adds special attack bar to special attack weapons. Removes special attack
	 * bar to weapons that do not have special attacks.
	 **/
	public void addSpecialBar(int weapon) {
		switch (weapon) {

		case 4151: // whip
		case 15107:
		case 15019:
		case 15023:
		case 15022:
			c.getPA().sendFrame171(0, 12323);
			specialAmount(weapon, c.specAmount, 12335);
			break;

		case 859: // magic bows
		case 861:
		case 11235:
		case 14015:
			c.getPA().sendFrame171(0, 7549);
			specialAmount(weapon, c.specAmount, 7561);
			break;

		case 4587: // dscimmy
			c.getPA().sendFrame171(0, 7599);
			specialAmount(weapon, c.specAmount, 7611);
			break;

		case 3204: // d hally
			c.getPA().sendFrame171(0, 8493);
			specialAmount(weapon, c.specAmount, 8505);
			break;

		case 1377: // d battleaxe
			c.getPA().sendFrame171(0, 7499);
			specialAmount(weapon, c.specAmount, 7511);
			break;

		case 4153: // gmaul
			c.getPA().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;

		case 1249: // dspear
			c.getPA().sendFrame171(0, 7674);
			specialAmount(weapon, c.specAmount, 7686);
			break;

		case 1215:// dragon dagger
		case 3757:
		case 1231:
		case 5680:
		case 5698:
			// case 15107:
		case 1305: // dragon long
		case 11694:
		case 11698:
		case 11700:
		case 11730:
		case 11696:
		case 10887:
			c.getPA().sendFrame171(0, 7574);
			specialAmount(weapon, c.specAmount, 7586);
			break;

		case 1434: // dragon mace
			c.getPA().sendFrame171(0, 7624);
			specialAmount(weapon, c.specAmount, 7636);
			break;

		default:
			c.getPA().sendFrame171(1, 7624); // mace interface
			c.getPA().sendFrame171(1, 7474); // hammer, gmaul
			c.getPA().sendFrame171(1, 7499); // axe
			c.getPA().sendFrame171(1, 7549); // bow interface
			c.getPA().sendFrame171(1, 7574); // sword interface
			c.getPA().sendFrame171(1, 7599); // scimmy sword interface, for most
			c.getPA().sendFrame171(1, 8493); // swords
			c.getPA().sendFrame171(1, 12323); // whip interface
			break;
		}
	}

	/**
	 * Special attack bar filling amount.
	 **/
	public void specialAmount(int weapon, double specAmount, int barId) {
		c.specBarId = barId;
		c.getPA().sendFrame70(specAmount >= 10 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 9 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 8 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 7 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 6 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 5 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 4 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 3 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 2 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 1 ? 500 : 0, 0, (--barId));
		updateSpecialBar();
		sendWeapon(weapon, getItemName(weapon));
	}

	/**
	 * Special attack text.
	 **/
	public void updateSpecialBar() {
		String percent = Double.toString(c.specAmount);
		if (percent.contains(".")) {
			percent = percent.replace(".", "");
		}
		if (percent.startsWith("0") && !percent.equals("00")) {
			percent = percent.replace("0", "");
		}
		if (percent.startsWith("0") && percent.equals("00")) {
			percent = percent.replace("00", "0");
		}
		c.getPA()
				.sendFrame126(
						c.usingSpecial ? "@yel@Special Attack (" + percent
								+ "%)" : "@bla@Special Attack (" + percent
								+ "%)", c.specBarId);
	}

	/**
	 * Wielding items.
	 **/
	public boolean wearItem(int wearID, int slot) {
		synchronized (c) {
			int targetSlot = 0;
			boolean canWearItem = true;
			if (c.playerItems[slot] == (wearID + 1)) {
				getRequirements(getItemName(wearID).toLowerCase(), wearID);
				targetSlot = Item.targetSlots[wearID];
				if (itemType(wearID).equalsIgnoreCase("cape")) {
					targetSlot = 1;
				} else if (itemType(wearID).equalsIgnoreCase("hat")) {
					targetSlot = 0;
				} else if (itemType(wearID).equalsIgnoreCase("amulet")) {
					targetSlot = 2;
				} else if (itemType(wearID).equalsIgnoreCase("arrows")) {
					targetSlot = 13;
				} else if (itemType(wearID).equalsIgnoreCase("body")) {
					targetSlot = 4;
				} else if (itemType(wearID).equalsIgnoreCase("shield")) {
					targetSlot = 5;
				} else if (itemType(wearID).equalsIgnoreCase("legs")) {
					targetSlot = 7;
				} else if (itemType(wearID).equalsIgnoreCase("gloves")) {
					targetSlot = 9;
				} else if (itemType(wearID).equalsIgnoreCase("boots")) {
					targetSlot = 10;
				} else if (itemType(wearID).equalsIgnoreCase("ring")) {
					targetSlot = 12;
				} else {
					targetSlot = 3;

				}
				switch (wearID) {
				case 2667:
				case 13738:
				case 13740:
				case 13742:
				case 13744:
				case 13734:
				case 13736:
				case 15025:
				case 15994:
					targetSlot = 5;
					break;
				case 13893:// Vestas Legs
					targetSlot = 7;
					break;
				case 10588: // salve amulet (e)
				case 10470:
				case 10472:
				case 10474:
					targetSlot = 2;
					break;
				case 9472:// Gnome goggles
					targetSlot = 0;
					break;
				case 11854:// gracefull top
					targetSlot = 4;
					break;
				// case 7053:
				// targetSlot = 3;
				// break;
				case 7927:// Easter ring
					targetSlot = 12;
					break;
				case 7114:// Pirate boots
					targetSlot = 10;
					break;
				case 5607:// grain
					targetSlot = 1;
					break;
				case 15330:
				case 15332:
					targetSlot = 12;
					break;
				case 13677:
				case 13678:
				case 13679:
					targetSlot = 1;
					break;
				case 12954:// d def
					targetSlot = 5;
					break;
				case 10615:
				case 10616:
				case 10617:
				case 10723:
				case 10721:
				case 10728:
				case 11282:
				case 10692:
				case 10629:
				case 10612:
					targetSlot = 0;
					break;
				case 12007:
					targetSlot = 5;
					break;
				case 11997:
					targetSlot = 10;
					break;
				case 13889:
				case 15041:
				case 15037:
					targetSlot = 3;
					break;
				case 13899:
					targetSlot = 3;
					break;
				case 13902:
				case 14997:
					targetSlot = 3;
					break;
				case 13870:// Morrigans Top
					targetSlot = 4;
					break;
				case 13887:// Vesta Top
					targetSlot = 4;
					break;
				case 13873:// Morrigans Legs
					targetSlot = 7;
					break;
				case 13884:// Statius Top
					targetSlot = 4;
					break;
				case 13890:// Statius Legs
					targetSlot = 7;
					break;
				case 14998:
					targetSlot = 2;
					break;
				/* Rings */
				case 11980:
				case 11982:
				case 11984:
				case 11986:
				case 11988:
				case 11770:
				case 11771:
				case 11772:
				case 11773:
				case 12601:
				case 12603:
				case 12605:
				case 11014:
					targetSlot = 12;
					break;
				/* Gloves */
				case 12490:
				case 12498:
				case 12506:
				case 10336:
				case 9922:
				case 8842:
					targetSlot = 9;
					break;
				/* Arrows */
				case 9144:
				case 9241:
				case 9242:
				case 9243:
					targetSlot = 13;
					break;
				/* Capes */
				case 15002:
				case 15000:
				case 15004:
				case 15005:
				case 15007:
				case 9813:
				case 9747:
				case 9748:
				case 9750:
				case 9790:
				case 9751:
				case 9753:
				case 9754:
				case 9756:
				case 9757:
				case 9759:
				case 9760:
				case 9762:
				case 9763:
				case 9765:
				case 9766:
				case 9768:
				case 9769:
				case 9771:
				case 9772:
				case 9774:
				case 9775:
				case 10446:
				case 10448:
				case 10450:
				case 9777:
				case 9778:
				case 9780:
				case 9781:
				case 9783:
				case 9784:
				case 9786:
				case 9787:
				case 9792:
				case 9793:
				case 9795:
				case 9796:
				case 9798:
				case 9799:
				case 9801:
				case 9802:
				case 9804:
				case 9805:
				case 9807:
				case 9808:
				case 9810:
				case 9811:
				case 10499:
					targetSlot = 1;
					break;

				/* Arrows */
				case 9244:
					targetSlot = 13;
					break;

				/* Boots */
				case 14605:
				case 11019:
				case 9921:
				case 11728:
				case 10839:
					targetSlot = 10;
					break;

				/* Legs */
				case 12415:
				case 12416:
				case 12295:
				case 12279:
				case 12285:
				case 12289:
				case 12510:
				case 12502:
				case 12494:
				case 12464:
				case 12474:
				case 12484:
				case 12462:
				case 12472:
				case 12482:
				case 12219:
				case 12217:
				case 12207:
				case 11726:
				case 11722:
				case 9678:
				case 9923:
				case 9676:
				case 10394:
				case 8840:
				case 15035:
				case 10332:
				case 14603:
				case 14938:
				case 14077:
				case 10346:
				case 10372:
				case 10838:
				case 11022:
				case 10388:
				case 10340:
				case 15425:
				case 13360:
				case 13352:
				case 13346:
				case 12395:
				case 10380:
					targetSlot = 7;
					break;

				/* Amulets */
				case 12436:
				case 15126:
				case 6861:
				case 6859:
				case 6863:
				case 9470:
				case 6857:
				case 10344:
				case 11128:
				case 15105:
				case 12002:
					targetSlot = 2;
					break;

				/* Shields */
				case 12418:
				case 12281:
				case 12291:
				case 12478:
				case 12488:
				case 12468:
				case 12213:
				case 12223:
				case 11924:
				case 11926:
				case 8850:
				case 8849:
				case 8848:
				case 8847:
				case 8846:
				case 8845:
				case 8844:
				case 11283:
				case 11284:
				case 10352:
					targetSlot = 5;
					break;

				/* Bodies */
				case 12414:
				case 12277:
				case 12287:
				case 10378:
				case 10792:
				case 12508:
				case 12500:
				case 12492:
				case 12460:
				case 12470:
				case 12480:
				case 12205:
				case 12215:
				case 12393:
				case 10551:
				case 10348:
				case 9674:
				case 10837:
				case 14936:
				case 15034:
				case 10386:
				case 10370:
				case 11720:
				case 10330:
				case 15423:
				case 14076:
				case 11020:
				case 14595:
				case 8839:
				case 10338:
				case 13348:
				case 13354:
				case 13358:
				case 9924:
				case 11724:
					targetSlot = 4;
					break;

				/* Helms */
				case 12518:
				case 12520:
				case 12522:
				case 12524:
				case 12417:
				case 12283:
				case 12293:
				case 12496:
				case 12504:
				case 12512:
				case 12466:
				case 12476:
				case 12486:
				case 12221:
				case 12637:
				case 12638:
				case 12639:
				case 12600:
				case 12397:
				case 11919:
				case 11862:
				case 12399:
				case 11863:
				case 11847:
				case 12068:
				case 12038:
				case 12040:
				case 12042:
				case 12044:
				case 12046:
				case 12056:
				case 12058:
				case 12060:
				case 12162:
				case 12064:
				case 12066:
				case 12070:
				case 12072:
				case 12074:
				case 12076:
				case 12086:
				case 12088:
				case 12090:
				case 12092:
				case 12084:
				case 12094:
				case 12096:
				case 12098:
				case 12100:
				case 12102:
				case 12104:
				case 12106:
				case 12430:
				case 15001:
				case 12211:
				case 13263:
				case 9920:
				case 10507:
				case 10836:
				case 10828:
				case 9672:
				case 10334:
				case 10350:
				case 10390:
				case 11718:
				case 10374:
				case 11021:
				case 15422:
				case 15033:
				case 9925:
				case 13362:
				case 11663:
				case 11664:
				case 11665:
				case 13355:
				case 13350:
				case 10342:
				case 1037:
				case 11335:
				case 10548:
				case 9749:
				case 9752:
				case 9755:
				case 9758:
				case 9761:
				case 9764:
				case 9767:
				case 9770:
				case 9773:
				case 9776:
				case 9779:
				case 9782:
				case 9785:
				case 9788:
				case 9791:
				case 9794:
				case 9797:
				case 9800:
				case 9803:
				case 9806:
				case 9809:
				case 9812:
				case 12363:
				case 12365:
				case 12367:
				case 12369:
				case 4502:
				case 13896:
				case 10452:
				case 10454:
				case 10456:
				case 10382:
				case 11277:
					targetSlot = 0;
					break;

				/* Boots */
				case 12598:
				case 11732:
				case 12391:
					targetSlot = 10;
					break;
				}

				if (c.playerRights >= 0 && c.playerRights <= 2) {
					if (wearID == 773) {
						c.sendMessage("@red@You must Developer to wear this ring.");
						return false;
					}
				}

				if (c.duelRule[11] && targetSlot == 0) {
					c.sendMessage("Wearing hats has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[12] && targetSlot == 1) {
					c.sendMessage("Wearing capes has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[13] && targetSlot == 2) {
					c.sendMessage("Wearing amulets has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[14] && targetSlot == 3) {
					c.sendMessage("Wielding weapons has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[15] && targetSlot == 4) {
					c.sendMessage("Wearing bodies has been disabled in this duel!");
					return false;
				}
				if ((c.duelRule[16] && targetSlot == 5)
						|| (c.duelRule[16] && is2handed(getItemName(wearID)
								.toLowerCase(), wearID))) {
					c.sendMessage("Wearing shield has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[17] && targetSlot == 7) {
					c.sendMessage("Wearing legs has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[18] && targetSlot == 9) {
					c.sendMessage("Wearing gloves has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[19] && targetSlot == 10) {
					c.sendMessage("Wearing boots has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[20] && targetSlot == 12) {
					c.sendMessage("Wearing rings has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[21] && targetSlot == 13) {
					c.sendMessage("Wearing arrows has been disabled in this duel!");
					return false;
				}

				if (Config.itemRequirements) {
					if (targetSlot == 10 || targetSlot == 7 || targetSlot == 5
							|| targetSlot == 4 || targetSlot == 0
							|| targetSlot == 9 || targetSlot == 10) {
						if (c.defenceLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[1]) < c.defenceLevelReq) {
								c.sendMessage("You need a defence level of "
										+ c.defenceLevelReq
										+ " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.rangeLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
								c.sendMessage("You need a range level of "
										+ c.rangeLevelReq
										+ " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.magicLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
								c.sendMessage("You need a magic level of "
										+ c.magicLevelReq
										+ " to wear this item.");
								canWearItem = false;
							}
						}
					}
					if (targetSlot == 3) {
						if (c.attackLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[0]) < c.attackLevelReq) {
								c.sendMessage("You need an attack level of "
										+ c.attackLevelReq
										+ " to wield this weapon.");
								canWearItem = false;
							}
						}
						if (c.rangeLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
								c.sendMessage("You need a range level of "
										+ c.rangeLevelReq
										+ " to wield this weapon.");
								canWearItem = false;
							}
						}
						if (c.magicLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
								c.sendMessage("You need a magic level of "
										+ c.magicLevelReq
										+ " to wield this weapon.");
								canWearItem = false;
							}
						}
					}
				}

				if (!canWearItem) {
					return false;
				}

				int wearAmount = c.playerItemsN[slot];
				if (wearAmount < 1) {
					return false;
				}

				if (targetSlot == c.playerWeapon) {
					c.autocasting = false;
					c.autocastId = 0;
					c.getPA().sendFrame36(108, 0);
				}

				if (slot >= 0 && wearID >= 0) {
					int toEquip = c.playerItems[slot];
					int toEquipN = c.playerItemsN[slot];
					int toRemove = c.playerEquipment[targetSlot];
					int toRemoveN = c.playerEquipmentN[targetSlot];
					if (toEquip == toRemove + 1 && Item.itemStackable[toRemove]) {
						deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
						c.playerEquipmentN[targetSlot] += toEquipN;
					} else if (targetSlot != 5 && targetSlot != 3) {
						c.playerItems[slot] = toRemove + 1;
						c.playerItemsN[slot] = toRemoveN;
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					} else if (targetSlot == 5) {
						boolean wearing2h = is2handed(
								getItemName(c.playerEquipment[c.playerWeapon])
										.toLowerCase(),
								c.playerEquipment[c.playerWeapon]);
						boolean wearingShield = c.playerEquipment[c.playerShield] > 0;
						if (wearing2h) {
							toRemove = c.playerEquipment[c.playerWeapon];
							toRemoveN = c.playerEquipmentN[c.playerWeapon];
							c.playerEquipment[c.playerWeapon] = -1;
							c.playerEquipmentN[c.playerWeapon] = 0;
							updateSlot(c.playerWeapon);
						}
						c.playerItems[slot] = toRemove + 1;
						c.playerItemsN[slot] = toRemoveN;
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					} else if (targetSlot == 3) {
						boolean is2h = is2handed(getItemName(wearID)
								.toLowerCase(), wearID);
						boolean wearingShield = c.playerEquipment[c.playerShield] > 0;
						boolean wearingWeapon = c.playerEquipment[c.playerWeapon] > 0;
						if (is2h) {
							if (wearingShield && wearingWeapon) {
								if (freeSlots() > 0) {
									c.playerItems[slot] = toRemove + 1;
									c.playerItemsN[slot] = toRemoveN;
									c.playerEquipment[targetSlot] = toEquip - 1;
									c.playerEquipmentN[targetSlot] = toEquipN;
									removeItem(
											c.playerEquipment[c.playerShield],
											c.playerShield);
								} else {
									c.sendMessage("You do not have enough inventory space to do this.");
									return false;
								}
							} else if (wearingShield && !wearingWeapon) {
								c.playerItems[slot] = c.playerEquipment[c.playerShield] + 1;
								c.playerItemsN[slot] = c.playerEquipmentN[c.playerShield];
								c.playerEquipment[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
								c.playerEquipment[c.playerShield] = -1;
								c.playerEquipmentN[c.playerShield] = 0;
								updateSlot(c.playerShield);
							} else {
								c.playerItems[slot] = toRemove + 1;
								c.playerItemsN[slot] = toRemoveN;
								c.playerEquipment[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
							}
						} else {
							c.playerItems[slot] = toRemove + 1;
							c.playerItemsN[slot] = toRemoveN;
							c.playerEquipment[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
						}
					}
					c.isFullHelm = Item
							.isFullHelm(c.playerEquipment[c.playerHat]);
					c.isFullMask = Item
							.isFullMask(c.playerEquipment[c.playerHat]);
					c.isFullBody = Item
							.isFullBody(c.playerEquipment[c.playerChest]);
					resetItems(3214);
				}
				if (targetSlot == 3) {
					c.usingSpecial = false;
					addSpecialBar(wearID);
				}
				if (c.getOutStream() != null && c != null) {
					c.getOutStream().createFrameVarSizeWord(34);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(targetSlot);
					c.getOutStream().writeWord(wearID + 1);

					if (c.playerEquipmentN[targetSlot] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord(
								c.playerEquipmentN[targetSlot]);
					} else {
						c.getOutStream().writeByte(
								c.playerEquipmentN[targetSlot]);
					}

					c.getOutStream().endFrameVarSizeWord();
					c.flushOutStream();
				}
				sendWeapon(c.playerEquipment[c.playerWeapon],
						getItemName(c.playerEquipment[c.playerWeapon]));
				resetBonus();
				getBonus();
				writeBonus();
				c.getCombat().getPlayerAnimIndex(
						c.getItems()
								.getItemName(c.playerEquipment[c.playerWeapon])
								.toLowerCase());
				c.getPA().requestUpdates();
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Indicates the action to wear an item.
	 * 
	 * @param wearID
	 * @param wearAmount
	 * @param targetSlot
	 */
	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(targetSlot);
				c.getOutStream().writeWord(wearID + 1);

				if (wearAmount > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(wearAmount);
				} else {
					c.getOutStream().writeByte(wearAmount);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipment[targetSlot] = wearID;
				c.playerEquipmentN[targetSlot] = wearAmount;
				c.getItems().sendWeapon(
						c.playerEquipment[c.playerWeapon],
						c.getItems().getItemName(
								c.playerEquipment[c.playerWeapon]));
				c.getItems().resetBonus();
				c.getItems().getBonus();
				c.getItems().writeBonus();
				c.getCombat().getPlayerAnimIndex(
						c.getItems()
								.getItemName(c.playerEquipment[c.playerWeapon])
								.toLowerCase());
				c.updateRequired = true;
				c.setAppearanceUpdateRequired(true);
			}
		}
	}

	/**
	 * Updates the slot when wielding an item.
	 * 
	 * @param slot
	 */
	public void updateSlot(int slot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(slot);
				c.getOutStream().writeWord(c.playerEquipment[slot] + 1);
				if (c.playerEquipmentN[slot] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[slot]);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[slot]);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}

	}

	/**
	 * Remove Item
	 **/
	public void removeItem(int wearID, int slot) {
		boolean godArmour = false;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (c.playerEquipment[slot] > -1) {
				if (addItem(c.playerEquipment[slot], c.playerEquipmentN[slot])) {
					if (c.hasGodArmour()) {
						godArmour = true;
					}
					c.playerEquipment[slot] = -1;
					c.playerEquipmentN[slot] = 0;
					sendWeapon(c.playerEquipment[c.playerWeapon],
							getItemName(c.playerEquipment[c.playerWeapon]));
					resetBonus();
					getBonus();
					writeBonus();
					c.getCombat().getPlayerAnimIndex(
							c.getItems()
									.getItemName(
											c.playerEquipment[c.playerWeapon])
									.toLowerCase());
					c.getOutStream().createFrame(34);
					c.getOutStream().writeWord(6);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(slot);
					c.getOutStream().writeWord(0);
					c.getOutStream().writeByte(0);
					c.flushOutStream();
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					if (godArmour
							&& c.playerLevel[3] > c.calculateMaxLifePoints()) {
						c.playerLevel[3] = c.calculateMaxLifePoints();
						c.getPA().refreshSkill(3);
					}
				}
			}
		}
		// }
	}

	/**
	 * Items in your bank.
	 */
	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (c.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (c.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							c.bankItems[j - spots] = c.bankItems[j];
							c.bankItemsN[j - spots] = c.bankItemsN[j];
							stop = true;
							c.bankItems[j] = 0;
							c.bankItemsN[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter)
			c.disconnected = true;
	}

	/**
	 * Items displayed on the armor interface.
	 * 
	 * @param id
	 * @param amount
	 */
	public void itemOnInterface(int id, int amount) {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(2274);
			c.getOutStream().writeWord(1);
			if (amount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(amount);
			} else {
				c.getOutStream().writeByte(amount);
			}
			c.getOutStream().writeWordBigEndianA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Reseting your bank.
	 */
	public void resetBank() {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(5382); // Bank
			c.getOutStream().writeWord(Config.BANK_SIZE);
			for (int i = 0; i < Config.BANK_SIZE; i++) {
				if (c.bankItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.bankItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.bankItemsN[i]);
				}
				if (c.bankItemsN[i] < 1) {
					c.bankItems[i] = 0;
				}
				if (c.bankItems[i] > Config.ITEM_LIMIT || c.bankItems[i] < 0) {
					c.bankItems[i] = Config.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(c.bankItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Resets temporary worn items. Used in minigames, etc
	 */
	public void resetTempItems() {
		synchronized (c) {
			int itemCount = 0;
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] > -1) {
					itemCount = i;
				}
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(5064);
			c.getOutStream().writeWord(itemCount + 1);
			for (int i = 0; i < itemCount + 1; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.playerItemsN[i]);
				}
				if (c.playerItems[i] > Config.ITEM_LIMIT
						|| c.playerItems[i] < 0) {
					c.playerItems[i] = Config.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Banking your item.
	 * 
	 * @param itemID
	 * @param fromSlot
	 * @param amount
	 * @return
	 */
	public boolean bankItem(int itemID, int fromSlot, int amount) {
		if (c.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!Item.itemIsNote[c.playerItems[fromSlot] - 1]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1]
					|| c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						if (c.playerItemsN[fromSlot] < amount)
							amount = c.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = c.playerItems[fromSlot];
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.sendMessage("Bank full!");
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.sendMessage("Bank full!");
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = c.playerItems[firstPossibleSlot];
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1),
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1),
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			}
		} else if (Item.itemIsNote[c.playerItems[fromSlot] - 1]
				&& !Item.itemIsNote[c.playerItems[fromSlot] - 2]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1]
					|| c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == (c.playerItems[fromSlot] - 1)) {
						if (c.playerItemsN[fromSlot] < amount)
							amount = c.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = (c.playerItems[fromSlot] - 1);
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == (c.playerItems[fromSlot] - 1)) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = (c.playerItems[firstPossibleSlot] - 1);
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1),
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1),
									firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			c.sendMessage("Item not supported " + (c.playerItems[fromSlot] - 1));
			return false;
		}
	}

	/**
	 * Checks if you have free bank slots.
	 */
	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	/**
	 * Getting items from your bank.
	 * 
	 * @param itemID
	 * @param fromSlot
	 * @param amount
	 */
	public void fromBank(int itemID, int fromSlot, int amm) {
		if (!c.isBanking) {
			c.getPA().closeAllWindows();
			c.isBanking = false;
			return;
		}
		int inInv = c.getItems().getItemAmount(c.bankItems[fromSlot] - 1);
		int amount = amm;
		if ((inInv + amm) < 0) {
			amount = Integer.MAX_VALUE - inInv;
		}

		if (amount > 0) {
			if (c.bankItems[fromSlot] > 0) {
				if (!c.takeAsNote) {
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem((c.bankItems[fromSlot] - 1), amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								c.getItems().resetItems(5064);
							}
						} else {
							if (addItem((c.bankItems[fromSlot] - 1),
									c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								c.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem((c.bankItems[fromSlot] - 1), 1)) {
									c.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						resetBank();
						c.getItems().resetItems(5064);
					}
				} else if (c.takeAsNote
						&& Item.itemIsNote[c.bankItems[fromSlot]]) {
					if (c.bankItemsN[fromSlot] > amount) {
						if (addItem(c.bankItems[fromSlot], amount)) {
							c.bankItemsN[fromSlot] -= amount;
							resetBank();
							c.getItems().resetItems(5064);
						}
					} else {
						if (addItem(c.bankItems[fromSlot],
								c.bankItemsN[fromSlot])) {
							c.bankItems[fromSlot] = 0;
							c.bankItemsN[fromSlot] = 0;
							resetBank();
							c.getItems().resetItems(5064);
						}
					}
				} else {
					c.sendMessage("This item can't be withdrawn as a note.");
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem((c.bankItems[fromSlot] - 1), amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								c.getItems().resetItems(5064);
							}
						} else {
							if (addItem((c.bankItems[fromSlot] - 1),
									c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								c.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem((c.bankItems[fromSlot] - 1), 1)) {
									c.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						resetBank();
						c.getItems().resetItems(5064);
					}
				}
			}
		}
	}

	/**
	 * Checking item amounts.
	 * 
	 * @param itemID
	 * @return
	 */
	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				tempAmount += c.playerItemsN[i];
			}
		}
		return tempAmount;
	}

	/**
	 * Checks if the item is stackable.
	 * 
	 * @param itemID
	 * @return
	 */
	public boolean isStackable(int itemID) {
		return Item.itemStackable[itemID];
	}

	/**
	 * Updates the equipment tab.
	 **/
	public void setEquipment(int wearID, int amount, int targetSlot) {
		c.isFullHelm = Item.isFullHelm(c.playerEquipment[c.playerHat]);
		c.isFullMask = Item.isFullMask(c.playerEquipment[c.playerHat]);
		c.isFullBody = Item.isFullBody(c.playerEquipment[c.playerChest]);
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(targetSlot);
			c.getOutStream().writeWord(wearID + 1);
			if (amount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(amount);
			} else {
				c.getOutStream().writeByte(amount);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipment[targetSlot] = wearID;
			c.playerEquipmentN[targetSlot] = amount;
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	/**
	 * Moving Items in your bag.
	 **/
	public void moveItems(int from, int to, int moveWindow, boolean insertMode) {
		if (moveWindow == 3214) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];
			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
		}

		if (moveWindow == 5382 && from >= 0 && to >= 0
				&& from < Config.BANK_SIZE && to < Config.BANK_SIZE
				&& to < Config.BANK_SIZE) {
			if (insertMode) {
				int tempFrom = from;
				for (int tempTo = to; tempFrom != tempTo;)
					if (tempFrom > tempTo) {
						swapBankItem(tempFrom, tempFrom - 1);
						tempFrom--;
					} else if (tempFrom < tempTo) {
						swapBankItem(tempFrom, tempFrom + 1);
						tempFrom++;
					}
			} else {
				swapBankItem(from, to);
			}
		}

		if (moveWindow == 5382) {
			resetBank();
		}
		if (moveWindow == 5064) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
			resetItems(3214);
		}
		resetTempItems();
		if (moveWindow == 3214) {
			resetItems(3214);
		}

	}

	public void swapBankItem(int from, int to) {
		int tempI = c.bankItems[from];
		int tempN = c.bankItemsN[from];
		c.bankItems[from] = c.bankItems[to];
		c.bankItemsN[from] = c.bankItemsN[to];
		c.bankItems[to] = tempI;
		c.bankItemsN[to] = tempN;
	}

	/**
	 * Delete item equipment.
	 **/
	public void deleteEquipment(int i, int j) {
		synchronized (c) {
			if (PlayerHandler.players[c.playerId] == null) {
				return;
			}
			if (i < 0) {
				return;
			}

			c.playerEquipment[j] = -1;
			c.playerEquipmentN[j] = c.playerEquipmentN[j] - 1;
			c.getOutStream().createFrame(34);
			c.getOutStream().writeWord(6);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(j);
			c.getOutStream().writeWord(0);
			c.getOutStream().writeByte(0);
			getBonus();
			if (j == c.playerWeapon) {
				sendWeapon(-1, "Unarmed");
			}
			resetBonus();
			getBonus();
			writeBonus();
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	/**
	 * Delete items.
	 * 
	 * @param id
	 * @param amount
	 */
	public void deleteItem(int id, int amount) {
		deleteItem(id, getItemSlot(id), amount);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (c.playerItems[slot] == (id + 1)) {
			if (c.playerItemsN[slot] > amount) {
				c.playerItemsN[slot] -= amount;
			} else {
				c.playerItemsN[slot] = 0;
				c.playerItems[slot] = 0;
			}
			PlayerSave.saveGame(c);
			resetItems(3214);
		}
	}

	public void deleteItem2(int id, int amount) {
		int am = amount;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (am == 0) {
				break;
			}
			if (c.playerItems[i] == (id + 1)) {
				if (c.playerItemsN[i] > amount) {
					c.playerItemsN[i] -= amount;
					break;
				} else {
					c.playerItems[i] = 0;
					c.playerItemsN[i] = 0;
					am--;
				}
			}
		}
		resetItems(3214);
	}

	/**
	 * Delete arrows.
	 **/
	public void deleteArrow() {
		synchronized (c) {
			if (c.playerEquipment[c.playerCape] == 10499 && Misc.random(5) != 1
					&& c.playerEquipment[c.playerArrows] != 4740)
				return;
			if (c.playerEquipmentN[c.playerArrows] == 1) {
				c.getItems().deleteEquipment(c.playerEquipment[c.playerArrows],
						c.playerArrows);
			}
			if (c.playerEquipmentN[c.playerArrows] != 0) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(c.playerArrows);
				c.getOutStream().writeWord(
						c.playerEquipment[c.playerArrows] + 1);
				if (c.playerEquipmentN[c.playerArrows] - 1 > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(
							c.playerEquipmentN[c.playerArrows] - 1);
				} else {
					c.getOutStream().writeByte(
							c.playerEquipmentN[c.playerArrows] - 1);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipmentN[c.playerArrows] -= 1;
			}
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	public void deleteEquipment() {
		synchronized (c) {
			if (c.playerEquipmentN[c.playerWeapon] == 1) {
				c.getItems().deleteEquipment(c.playerEquipment[c.playerWeapon],
						c.playerWeapon);
			}
			if (c.playerEquipmentN[c.playerWeapon] != 0) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(c.playerWeapon);
				c.getOutStream().writeWord(
						c.playerEquipment[c.playerWeapon] + 1);
				if (c.playerEquipmentN[c.playerWeapon] - 1 > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(
							c.playerEquipmentN[c.playerWeapon] - 1);
				} else {
					c.getOutStream().writeByte(
							c.playerEquipmentN[c.playerWeapon] - 1);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipmentN[c.playerWeapon] -= 1;
			}
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	/**
	 * Dropping arrows
	 **/
	public void dropArrowNpc() {
		if (c.playerEquipment[c.playerCape] == 10499)
			return;
		int enemyX = NPCHandler.npcs[c.oldNpcIndex].getX();
		int enemyY = NPCHandler.npcs[c.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed,
					enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName,
					c.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName,
						c.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	/**
	 * Ranging arrows.
	 */
	public void dropArrowPlayer() {
		int enemyX = PlayerHandler.players[c.oldPlayerIndex].getX();
		int enemyY = PlayerHandler.players[c.oldPlayerIndex].getY();
		if (c.playerEquipment[c.playerCape] == 10499)
			return;
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed,
					enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName,
					c.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName,
						c.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	/**
	 * Removes all items from player's equipment.
	 */
	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		resetItems(3214);
	}

	/**
	 * Checks if you have a free slot.
	 * 
	 * @return
	 */
	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	/**
	 * Finds the item.
	 * 
	 * @param id
	 * @param items
	 * @param amounts
	 * @return
	 */
	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if (((items[i] - 1) == id) && (amounts[i] > 0)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the item name from the item.cfg
	 * 
	 * @param ItemID
	 * @return
	 */
	public String getItemName(int ItemID) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					return Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		return "Unarmed";
	}

	/**
	 * Gets the item ID from the item.cfg
	 * 
	 * @param itemName
	 * @return
	 */
	public void bonesId(int id, int slot) {
		if (id == 0x181)
			wearItem(id, slot);
	}

	public int getItemId(String itemName) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName
						.equalsIgnoreCase(itemName)) {
					return Server.itemHandler.ItemList[i].itemId;
				}
			}
		}
		return -1;
	}

	/**
	 * Gets the item slot.
	 * 
	 * @param ItemID
	 * @return
	 */
	public int getItemSlot(int ItemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets the item amount.
	 * 
	 * @param ItemID
	 * @return
	 */
	public int getItemAmount(int ItemID) {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				itemCount += c.playerItemsN[i];
			}
		}
		return itemCount;
	}

	/**
	 * Checks if the player has the item.
	 * 
	 * @param itemID
	 * @param amt
	 * @param slot
	 * @return
	 */
	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (c.playerItems[slot] == (itemID)) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] == itemID) {
					if (c.playerItemsN[i] >= amt) {
						return true;
					} else {
						found++;
					}
				}
			}
			if (found >= amt) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean playerHasItem(int itemID) {
		itemID++;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID)
				return true;
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				if (c.playerItemsN[i] >= amt) {
					return true;
				} else {
					found++;
				}
			}
		}
		if (found >= amt) {
			return true;
		}
		return false;
	}

	/**
	 * Getting un-noted items.
	 * 
	 * @param ItemID
	 * @return
	 */
	public int getUnnotedItem(int ItemID) {
		int NewID = ItemID - 1;
		String NotedName = "";
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					NotedName = Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName == NotedName) {
					if (Server.itemHandler.ItemList[i].itemDescription
							.startsWith("Swap this note at any bank for a") == false) {
						NewID = Server.itemHandler.ItemList[i].itemId;
						break;
					}
				}
			}
		}
		return NewID;
	}

	/**
	 * Dropping items
	 **/
	public void createGroundItem(int itemID, int itemX, int itemY,
			int itemAmount) {
		synchronized (c) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
			c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
			c.getOutStream().createFrame(44);
			c.getOutStream().writeWordBigEndianA(itemID);
			c.getOutStream().writeWord(itemAmount);
			c.getOutStream().writeByte(0);
			c.flushOutStream();
		}
	}

	public void handleSpecialPickup(int itemId) {
		// c.sendMessage("My " + getItemName(itemId) +
		// " has been recovered. I should talk to the void knights to get it back.");
		// c.getItems().addToVoidList(itemId);
	}

	/**
	 * Pickup items from the ground.
	 **/
	public void removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
		synchronized (c) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
			c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
			c.getOutStream().createFrame(156);
			c.getOutStream().writeByteS(0);
			c.getOutStream().writeWord(itemID);
			c.flushOutStream();
		}
	}

	/**
	 * Checks if a player owns a cape.
	 * 
	 * @return
	 */
	public boolean ownsCape() {
		if (c.getItems().playerHasItem(2412, 1)
				|| c.getItems().playerHasItem(2413, 1)
				|| c.getItems().playerHasItem(2414, 1))
			return true;
		for (int j = 0; j < Config.BANK_SIZE; j++) {
			if (c.bankItems[j] == 2412 || c.bankItems[j] == 2413
					|| c.bankItems[j] == 2414)
				return true;
		}
		if (c.playerEquipment[c.playerCape] == 2413
				|| c.playerEquipment[c.playerCape] == 2414
				|| c.playerEquipment[c.playerCape] == 2415)
			return true;
		return false;
	}

	public boolean hasAllShards() {
		return playerHasItem(11712, 1) && playerHasItem(11712, 1)
				&& playerHasItem(11714, 1);
	}

	public void makeBlade() {
		deleteItem(11710, 1);
		deleteItem(11712, 1);
		deleteItem(11714, 1);
		addItem(11690, 1);
		c.sendMessage("You combine the shards to make a blade.");
	}

	public boolean isHilt(int i) {
		return i >= 11702 && i <= 11708 && i % 2 == 0;
	}

}