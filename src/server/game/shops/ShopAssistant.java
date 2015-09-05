package server.game.shops;

import server.Config;
import server.Server;
import server.game.items.Item;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.world.ShopHandler;

public class ShopAssistant {

	private Client c;

	public ShopAssistant(Client client) {
		this.c = client;
	}

	/**
	 * Shops
	 **/

	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].isShopping == true
						&& PlayerHandler.players[i].myShopId == c.myShopId
						&& i != c.playerId) {
					// sh.getShops().openShop(sh.myShopId);
					PlayerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}

	public void openShop(int ShopID) {
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		synchronized (c) {
			int TotalItems = 0;
			for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[ShopID][i] > 0
						|| i <= ShopHandler.ShopItemsStandard[ShopID]) {
					if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(
								ShopHandler.ShopItemsN[ShopID][i]);
					} else {
						c.getOutStream().writeByte(
								ShopHandler.ShopItemsN[ShopID][i]);
					}
					if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT
							|| ShopHandler.ShopItems[ShopID][i] < 0) {
						ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(
							ShopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		double ShopValue = 1;
		double TotPrice = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}

		TotPrice = ShopValue;

		if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int) Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return 0;
	}

	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0,
				removeSlot));
		ShopValue *= 1;
		String ShopAdd = "";
		if (c.myShopId == 8) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getPkPoints(removeId)
					+ " PK points.");
			return;
		}
		if (c.myShopId == 20) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getAchievementPoints(removeId)
					+ " Achievement Points.");
			return;
		}
		if (c.myShopId == 10) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSlayerPoints(removeId)
					+ " slayer points.");
			return;
		}
		if (c.myShopId == 9) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getDonatorPoints(removeId)
					+ " Donator Tokens.");
			return;
		}
		if (c.myShopId == 15) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getPcPoints(removeId)
					+ " Pest Control Points.");
			return;
		}
		if (c.myShopId == 19) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getLevelPoints(removeId)
					+ " Level Points.");
			return;
		}
		if (c.myShopId == 14) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getvotingPoints(removeId)
					+ " Voting Points.");
			return;
		}

		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs "
				+ ShopValue + " coins" + ShopAdd);
	}

	/*
	 * Begin donator shop points
	 */
	public int getDonatorPoints(int id) {
		switch (id) {
			case 1053:
			case 1055:
			case 1057:
			case 11847:
			return 120;
			case 1038:
			case 1040:
			case 1042:
			case 1044:
			case 1046:
			case 1048:
			case 11862:
			return 150;
			case 11863:
			case 12399:
			return 165;
			case 1050:
			return 65;
			case 4565:
			return 40;
			case 4084:
			return 50;
			case 10348:
			case 10346:
			case 10350:
			case 10352:
			return 130;
			case 15107:
			return 120;
			case 15003:
			return 120;
			case 15110:
			return 120;
			case 11724:
			case 11726:
			return 85;
			case 10025:
			return 150;
			case 7968:
			return 100;
		}
		return 10000;
	}

	/*
	 * Begin voting shop points
	 */
	public int getvotingPoints(int id) {
		switch (id) {
		case 4722:
		case 4716:
		case 4718:
		case 4720:
			return 5;
		case 4151:
		return 20;
		case 6570:
		return 15;
		case 7462:
		return 12;
		case 6199:
		return 30;
		case 9470:
		return 10;
		case 6731:
		case 6733:
		case 6735:
		case 6737:
		case 6585:
		return 15;
		case 10460:
		case 10468:
		case 10458:
		case 10464:
		case 10462:
		case 10466:
		return 18;
		case 3481:
		case 3483:
		case 3485:
		case 3486:
		case 3488:
		case 12391:
		case 12389:
		return 8;
		}
		return 10000;
	}

	/*
	 * Begin slayer shop points
	 */
	public int getSlayerPoints(int id) {
		switch (id) {
		case 4155:
		return 1;
		case 8850:
		return 20;
		case 10548:
		return 25;
		case 10551:
		return 75;
		case 13263:
		return 400;
		case 6199:
		return 320;
		case 7053:
		return 30;
		case 7462:
		return 25;
		case 4153:
		return 45;
		case 13738:
		case 13742:
		case 13744:
		return 650;
		case 2572:
		return 30;
		case 11770:
		case 11771:
		case 11772:
		case 11773:
		return 100;
		case 6543:
		return 70;
		case 2528:
		return 50;
		
		
		}
		return 10000;
	}

	/*
	 * Begin achievement shop points
	 */
	public int getAchievementPoints(int id) {
		switch (id) {

		}
		return 10000;
	}

	/*
	 * Begin level shop points
	 */
	public int getLevelPoints(int id) {
		switch (id) {

		}
		return 10000;
	}

	/*
	 * Begin pest control shop points
	 */
	public int getPcPoints(int id) {
		switch (id) {
		case 7461:
		return 15;
		case 7462:
		return 20;
		case 10551:
		return 50;
		case 6889:
		return 20;
		case 6914:
		return 20;
		case 8839:
		case 8840:
		case 8842:
		case 11664:
		case 11663:
		case 11665:
		return 50;
		case 6918:
		case 6916:
		case 6924:
		case 6920:
		case 6922:
		return 25;
		case 2528:
		return 65;

		}
		return 10000;
	}

	/*
	 * Begin pk shop points
	 */
	public int getPkPoints(int id) {
		switch (id) {
		case 7462: // barrow gloves
			return 20;
		case 11694: //AGS
		return 200;
		case 11696: //bgs
		case 11698: //sgs
		case 11700: //zgs
		return 170;
		case 15003: //acb
		return 150;
		case 11235: //dbow
		return 70;
		case 15110:
		return 120;
		case 11994:
		return 120;
		case 15006:
		return 80;
		case 4151:
		return 75;
		case 12954:
		return 100;
		case 11730:
		return 120;
		case 4153:
		return 70;
		case 6914:
		return 40;
		case 6889:
		return 30;
		case 2577:
		case 2581:
		return 75;
		case 10551:
		return 80;
		case 10548:
		return 35;
		case 8849:
		return 5;
		case 8850:
		return 15;
		case 7460:
		return 5;
		case 7461:
		return 12;
		case 6570:
		return 40;
		
		}
		return 10000;
	}

	/**
	 * Sell item to shop (80% shop price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		for (int i : Config.ITEM_SELLABLE) {
			if (i == removeId) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(removeId).toLowerCase()
						+ ".");
				return;
			}
		}
		if (c.inTrade) {
			c.sendMessage("You can't sell items while trading!");
			return;
		}
		boolean IsIn = false;
		if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(removeId).toLowerCase()
					+ " to this store.");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1,
					removeSlot) * 0.8);
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": shop will buy for " + ShopValue + " coins" + ShopAdd);
		}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		if (c.myShopId == 25)
			return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(itemID).toLowerCase() + ".");
				return false;
			}
		}

		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "
							+ c.getItems().getItemName(itemID).toLowerCase()
							+ " to this store.");
					return false;
				}
			}

			if (amount > c.playerItemsN[fromSlot]
					&& (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID)
					&& Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false
					&& Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1,
						fromSlot) * 0.8);
				if (TotPrice2 >= 100000000) {
					c.sendMessage("Sorry, but the shop owner can't afford to buy this item from you!");
					break;
				}

				if (c.getItems().freeSlots() > 0
						|| c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID,
								c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					// addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
			if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				ShopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
				if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
					ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					ShopHandler.ShopItemsN[c.myShopId][i] = amount;
					ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if (c.myShopId == 25) {
			skillBuy(itemID);
			return false;
		}
		if (!shopSellsItem(itemID))
			return false;
		if (itemID == 1495 && c.KC < 100) {
			c.sendMessage("You need 100 kills to unlock this item.");
			return false;
		}
		if (itemID == 88 && c.KC < 250) {
			c.sendMessage("You need 250 kills to unlock this item.");
			return false;
		}
		if (itemID == 1724 && c.KC < 500) {
			c.sendMessage("You need 500 kills to unlock this item.");
			return false;
		}
		if (itemID == 1052 && c.KC < 1000) {
			c.sendMessage("You need 1000 kills to unlock this item.");
			return false;
		}
		if (itemID == 1635 && c.KC < 1000) {
			c.sendMessage("You need 1000 kills to unlock this item.");
			return false;
		}
		if (itemID == 6666 && c.KC < 1500) {
			c.sendMessage("You need 1500 kills to unlock this item.");
			return false;
		}
		if (itemID == 6665 && c.KC < 1700) {
			c.sendMessage("You need 1700 kills to unlock this item.");
			return false;
		}
		if (c.inTrade) {
			c.sendMessage("You can't sell items while trading!");
			return false;
		}
		if (amount > 0) {
			if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
			}
			if (ShopHandler.ShopItems[c.myShopId][fromSlot] != itemID + 1) {
				return false;
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			int Slot = 0;
			int Slot1 = 0;// Tokkul
			if (c.myShopId >= 211 && c.myShopId == 312 || c.myShopId == 8
					|| c.myShopId == 9 || c.myShopId == 15 || c.myShopId == 14
					|| c.myShopId == 19 || c.myShopId == 20 || c.myShopId == 10) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 22) { // prestige system
				handleOtherShop(itemID);
				return false;
			}
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
						fromSlot));
				Slot = c.getItems().getItemSlot(995);
				Slot1 = c.getItems().getItemSlot(6529);
				if (Slot == -1 && c.myShopId != 101 && c.myShopId != 102
						&& c.myShopId != 103 && c.myShopId != 104) {
					c.sendMessage("You don't have enough coins.");
					break;
				}
				if (Slot1 == -1 && c.myShopId == 100 || c.myShopId == 101
						|| c.myShopId == 102) {
					c.sendMessage("You don't have enough tokkul.");
					break;
				}
				if (TotPrice2 <= 1) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
							fromSlot));
					TotPrice2 *= 1.66;
				}
				if (c.myShopId != 100 || c.myShopId != 101 || c.myShopId != 102
						|| c.myShopId != 103) {
					if (c.playerItemsN[Slot] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough coins.");
						break;
					}
				} else if (c.myShopId == 101 || c.myShopId == 102
						|| c.myShopId == 103) {
					if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(6529,
									c.getItems().getItemSlot(6529), TotPrice2);
							c.getItems().addItem(itemID, 1);
							ShopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							ShopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > ShopHandler.ShopItemsStandard[c.myShopId]) {
								ShopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough tokkul.");
						break;
					}
				}

			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}

	public void handleOtherShop(int itemID) {
		if (c.myShopId == 8) {
			if (c.pkPoints >= getPkPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pkPoints -= getPkPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
					c.getPA().sendFrame126(
							"@whi@Pk Points: @gre@" + c.pkPoints + " ", 7333);
				}
			} else {
				c.sendMessage("You do not have enough PK points to buy this item.");
			}
		} else if (c.myShopId == 20) {
			if (c.aPoints >= getAchievementPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.aPoints -= getAchievementPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
					c.getPA().sendFrame126(
							"@whi@Achievement Points: @gre@" + c.aPoints + " ",
							7333);
				}
			} else {
				c.sendMessage("You do not have enough Achievement Points points to buy this item.");
			}
		} else if (c.myShopId == 15) {
			if (c.pcPoints >= getPcPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pcPoints -= getPcPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
					c.getPA().sendFrame126(
							"@whi@Pk Points: @gre@" + c.pcPoints + " ", 7333);
				}
			} else {
				c.sendMessage("You do not have enough Pest Control points to buy this item.");
			}
		} else if (c.myShopId == 19) {
			if (c.lvlPoints >= getLevelPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.lvlPoints -= getLevelPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);

				}
			} else {
				c.sendMessage("You do not have enough Level Points to buy this item.");
			}
		} else if (c.myShopId == 9) {
			if (c.dp >= getDonatorPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.dp -= getDonatorPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Donator Tokens to buy this item.");
			}
		} else if (c.myShopId == 13) {
			if (c.votingPoints >= getDonatorPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.dp -= getDonatorPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Donator Tokens to buy this item.");
			}
		} else if (c.myShopId == 14) {
			if (c.votingPoints >= getvotingPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.votingPoints -= getvotingPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Voting points to buy this item.");
			}
		} else if (c.myShopId == 10) {
			if (c.slayerPoints >= getSlayerPoints(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.slayerPoints -= getSlayerPoints(itemID);
					c.getPA().loadQuests();
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Slayer points to buy this item.");
			}
		}
	}

	public void openSkillCape() {
		int capes = get99Count();
		if (capes > 1)
			capes = 1;
		else
			capes = 0;
		c.myShopId = 25;
		setupSkillCapes(capes, get99Count());
	}

	/*
	 * public int[][] skillCapes =
	 * {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680
	 * ,4359,2682},{3,2701,4341,2703
	 * },{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
	 * {7,2737,4325,2733
	 * },{8,2734,4353,2736},{9,2716,4337,2718},{10,2728,4335,2730
	 * },{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
	 * {14,2722,4345
	 * ,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,
	 * 2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};
	 */
	public int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801,
			9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786,
			9810, 9765 };

	public int get99Count() {
		int count = 0;
		for (int j = 0; j < c.playerLevel.length; j++) {
			if (c.getLevelForXP(c.playerXP[j]) >= 99) {
				count++;
			}
		}
		return count;
	}

	public void setupSkillCapes(int capes, int capes2) {
		synchronized (c) {
			c.getItems().resetItems(3823);
			c.isShopping = true;
			c.myShopId = 25;
			c.getPA().sendFrame248(3824, 3822);
			c.getPA().sendFrame126("Skillcape Shop", 3901);

			int TotalItems = 0;
			TotalItems = capes2;
			if (TotalItems > ShopHandler.MaxShopItems) {
				TotalItems = ShopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			for (int i = 0; i < 21; i++) {
				if (c.getLevelForXP(c.playerXP[i]) < 99)
					continue;
				c.getOutStream().writeByte(1);
				c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void skillBuy(int item) {
		int nn = get99Count();
		if (nn > 1)
			nn = 1;
		else
			nn = 0;
		for (int j = 0; j < skillCapes.length; j++) {
			if (skillCapes[j] == item || skillCapes[j] + 1 == item) {
				if (c.getItems().freeSlots() > 1) {
					if (c.getItems().playerHasItem(995, 99000)) {
						if (c.getLevelForXP(c.playerXP[j]) >= 99) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), 99000);
							c.getItems().addItem(skillCapes[j] + nn, 1);
							c.getItems().addItem(skillCapes[j] + 2, 1);
						} else {
							c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
						}
					} else {
						c.sendMessage("You need 99k to buy this item.");
					}
				} else {
					c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
				}
			}
		}
		c.getItems().resetItems(3823);
	}

	public void openVoid() {
		/*
		 * synchronized(c) { c.getItems().resetItems(3823); c.isShopping = true;
		 * c.myShopId = 15; c.getPA().sendFrame248(3824, 3822);
		 * c.getPA().sendFrame126("Void Recovery", 3901);
		 * 
		 * int TotalItems = 5; c.getOutStream().createFrameVarSizeWord(53);
		 * c.getOutStream().writeWord(3900);
		 * c.getOutStream().writeWord(TotalItems); for (int i = 0; i <
		 * c.voidStatus.length; i++) {
		 * c.getOutStream().writeByte(c.voidStatus[i]);
		 * c.getOutStream().writeWordBigEndianA(2519 + i * 2); }
		 * c.getOutStream().endFrameVarSizeWord(); c.flushOutStream(); }
		 */
	}

	public void buyVoid(int item) {
		/*
		 * if (item > 2527 || item < 2518) return; //c.sendMessage("" + item);
		 * if (c.voidStatus[(item-2518)/2] > 0) { if (c.getItems().freeSlots()
		 * >= 1) { if
		 * (c.getItems().playerHasItem(995,c.getItems().getUntradePrice(item)))
		 * { c.voidStatus[(item-2518)/2]--;
		 * c.getItems().deleteItem(995,c.getItems().getItemSlot(995),
		 * c.getItems().getUntradePrice(item)); c.getItems().addItem(item,1);
		 * openVoid(); } else { c.sendMessage("This item costs " +
		 * c.getItems().getUntradePrice(item) + " coins to rebuy."); } } else {
		 * c.sendMessage("I should have a free inventory space."); } } else {
		 * c.sendMessage
		 * ("I don't need to recover this item from the void knights."); }
		 */
	}

}
