package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.npcs.NPCHandler;
import server.game.players.content.Books;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerAssistant;
import server.game.players.PlayerHandler;
import server.game.players.content.ClueScroll;
import server.game.players.content.random.MysteryBox;
import server.game.players.content.random.WeaponChest;
import server.game.players.content.skills.herblore.Herblore;
import server.util.Misc;
import server.game.players.ActionHandler;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (itemId == 6199)
                	if (c.getItems().playerHasItem(6199)) {
                	MysteryBox.Open(itemId, c);
                	return;
		}
		if (c.hasPin) {
			return;
		}

		if (!c.getItems().playerHasItem(itemId, 1)) { // , itemSlot
			return;

		}// should work
		if (c.chatreveal == 1) {
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client) PlayerHandler.players[j];
					if (c2.playerRights == 3) {
						c2.sendMessage("*" + c.playerName + ": clicked item "
								+ itemId + ".");
					}
				}
			}
		}
		if (itemId == 2692) {
			ClueScroll.openEasyClue(c);
		}
		if (itemId == 5509) {
			c.getPA().addSmallPouch();
		}
		if (itemId == 5510) {
			c.getPA().addMediumPouch();
		}
		if (itemId == 5511) {
			c.getPA().addMediumPouch();
		}
		if (itemId == 5512) {
			c.getPA().addLargePouch();
		}
		if (itemId == 5513) {
			c.getPA().addLargePouch();
		}
		if (itemId == 5514) {
			c.getPA().addGiantPouch();
		}
		if (itemId == 5515) {
			c.getPA().addGiantPouch();
		}
		if (itemId == 7968) {
			c.playerRights = 4;
			c.getItems().deleteItem(7968, 1);
			c.titleColor = 3;
			c.playerTitle = "Premium";
			c.sendMessage("You have just reedemed @gre@Premium rank.");
		}
		
		/**
		 * NPC Drop Gude
		 */
		if (itemId == 11656) {
			Books.openBook(c);
		}

		if (itemId == 8007) { //varrock
			c.getItems().deleteItem(8007, c.getItems().getItemSlot(8007), 1);
			c.getPA().teleTabTeleport(3214, 3425, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8008) { //lummy
			c.getItems().deleteItem(8008, c.getItems().getItemSlot(8008), 1);
			c.getPA().teleTabTeleport(3222, 3218, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8009) { //fally
			c.getItems().deleteItem(8009, c.getItems().getItemSlot(8009), 1);
			c.getPA().teleTabTeleport(2965, 3379, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8010) { //cammy
			c.getItems().deleteItem(8010, c.getItems().getItemSlot(8010), 1);
			c.getPA().teleTabTeleport(2757, 3477, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8011) { //ardougne 
			c.getItems().deleteItem(8011, c.getItems().getItemSlot(8011), 1);
			c.getPA().teleTabTeleport(2661, 3305, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8012) { //ardougne 
			c.getItems().deleteItem(8012, c.getItems().getItemSlot(8012), 1);
			c.getPA().teleTabTeleport(2549, 3112, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 8013) { //home 
			c.getItems().deleteItem(8013, c.getItems().getItemSlot(8013), 1);
			c.getPA().teleTabTeleport(3087, 3504, 0, "teleTab");
			c.sendMessage("You break the teleport tab.");
		}
		
		if (itemId == 6543) {//antique
			if(c.lampStart2 <= System.currentTimeMillis() - 1800000) {
				c.lampStart2 = System.currentTimeMillis();
				c.getItems().deleteItem2(6543, 1);
				c.sendMessage("You rub the mysterious lamp.");
				c.sendMessage("You will now have @blu@25% Bonus Drop rates@bla@ from monsters for 30 minutes.");
				c.sendMessage("The lamp turns into dust.");
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("@red@You already have a Drop rate lamp active.");
				c.getPA().closeAllWindows();
			}
		}
		if (itemId == 11157) {

			if(c.lampStart <= System.currentTimeMillis() - 3600000) {
				c.lampStart = System.currentTimeMillis();
				c.getItems().deleteItem2(11157, 1);
				c.sendMessage("You rub the mysterious lamp.");
				c.sendMessage("You will now gain @blu@50% Bonus experience@bla@ in all skills for 1 hour.");
				c.sendMessage("The lamp turns into dust.");
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("@red@You already have a Bonus experience lamp active.");
				c.getPA().closeAllWindows();
			}
		}

		/*if (itemId == 10025) {
			int mysteryReward = Misc.random(15); // Donor Box
			if (mysteryReward == 1) {
				c.getItems().addItemToBank(9921, 1);
				c.getItems().addItemToBank(9922, 1);
				c.getItems().addItemToBank(9923, 1);
				c.getItems().addItemToBank(9924, 1);
				c.getItems().addItemToBank(9925, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A completed full skeleton!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 2) {
				c.getItems().addItemToBank(11019, 1);
				c.getItems().addItemToBank(11020, 1);
				c.getItems().addItemToBank(11021, 1);
				c.getItems().addItemToBank(11022, 1);
				c.getItems().addItemToBank(4566, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A completed full chicken!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 3) {
				c.getItems().addItemToBank(6654, 1);
				c.getItems().addItemToBank(6655, 1);
				c.getItems().addItemToBank(6656, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A completed full camo!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 4) {
				c.getItems().addItemToBank(6666, 1);
				c.getItems().addItemToBank(7003, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A flippers and a camel mask!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 5) {
				c.getItems().addItemToBank(9920, 1);
				c.getItems().addItemToBank(10507, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A jack lantern hat and a reindeer hat!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 6) {
				c.getItems().addItemToBank(1037, 1);
				c.getItems().addItemToBank(1961, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A bunny ears and a easter egg!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 7) {
				c.getItems().addItemToBank(1419, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A scythe!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 8) {
				c.getItems().addItemToBank(4565, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A basket of eggs!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 9) {
				c.getItems().addItemToBank(5607, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A grain!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 10) {
				c.getItems().addItemToBank(10836, 1);
				c.getItems().addItemToBank(10837, 1);
				c.getItems().addItemToBank(10838, 1);
				c.getItems().addItemToBank(10839, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A completed full silly jester!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 11) {
				c.getItems().addItemToBank(6858, 1);
				c.getItems().addItemToBank(6859, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A jester hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 12) {
				c.getItems().addItemToBank(6856, 1);
				c.getItems().addItemToBank(6857, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A bobble hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 13) {
				c.getItems().addItemToBank(6860, 1);
				c.getItems().addItemToBank(6861, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A tri-jester hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 14) {
				c.getItems().addItemToBank(6862, 1);
				c.getItems().addItemToBank(6863, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A wolly hat and scarf!");
				c.sendMessage("@red@The items has been added to your bank.");
			} else if (mysteryReward == 15) {
				c.getItems().addItemToBank(9470, 1);
				c.getItems().addItemToBank(10394, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You've gained: @blu@A gnome scarf and a flared trousers!");
				c.sendMessage("@red@The items has been added to your bank.");
			}
		}*/

		if (itemId == 5733) {
			c.getItems().playerHasItem(5733, 1);
			c.getPA().showInterface(3200);
		}
		if (itemId == 10025) {
			int mysteryReward = Misc.random(4);
			if (mysteryReward == 1) {
				c.getItems().addItem(11694, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You received@blu@ Armadyl Godsword@bla@ from box!");
			} else if (mysteryReward == 2) {
				c.getItems().addItem(11696, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You received@blu@ Bandos Godsword@bla@ from box!");
			} else if (mysteryReward == 3) {
				c.getItems().addItem(11698, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You received@blu@ Saradomin Godsword@bla@ from box!");
			} else if (mysteryReward == 4) {
				c.getItems().addItem(11700, 1);
				c.getItems().deleteItem(10025, 1);
				c.sendMessage("You received@blu@ Zamorak Godsword@bla@ from box!");
			}
			
		}
		
		
		
		
		if (itemId == 2677) {
			ClueScroll.openEasyClue2(c);
		}
		if (itemId == 2678) {
			ClueScroll.openEasyClue3(c);
		}
		if (itemId == 2679) {
			ClueScroll.openEasyClue4(c);
		}
		if (itemId == 2680) {
			ClueScroll.openEasyClue5(c);
		}
		if (itemId == 2801) {
			ClueScroll.openMediumClue(c);
		}
		if (itemId == 2722) {
			ClueScroll.openHardClue(c);
		}
		if (itemId == 2714) { // Easy Clue Scroll Casket
			if (c.getItems().freeSlots() < 6) {
				c.sendMessage("Get more inventory space before opening this casket.");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			PlayerAssistant.addClueReward(c, 0);
		}
		if (itemId == 2802) { // Medium Clue Scroll Casket
			if (c.getItems().freeSlots() < 6) {
				c.sendMessage("Get more inventory space before opening this casket.");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			PlayerAssistant.addClueReward(c, 1);
		}
		if (itemId == 2775) { // Hard Clue Scroll Casket
			if (c.getItems().freeSlots() < 6) {
				c.sendMessage("Get more inventory space before opening this casket.");
				return;
			}
			c.getItems().deleteItem(itemId, 1);
			PlayerAssistant.addClueReward(c, 2);
		}
		if (itemId == 11740) {
			if (c.getItems().playerHasItem(11740, 1))
				c.dp += 10;
			c.sendMessage("You opened the Scroll of redirection and received 10 @red@Donator points.");
			c.sendMessage("You now have " + c.dp + " @red@Donator points.");
			c.getItems().deleteItem(11740, c.getItems().getItemSlot(11740), 1);
		}
		if (itemId == 13081) {
			if (c.mysteryBox > 0)
				return;
			c.mysteryBox = 2;
			if (c.getItems().playerHasItem(13081, 1)) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(13081,
							c.getItems().getItemSlot(13081), 1);
					c.getItems().addItem(Client.randomItem(), 1);
					c.sendMessage("@blu@The Mystery box takes your present and you pull out the treasure.");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
		}

		if (itemId == 13082) {
			if (c.mysteryBox > 0)
				return;
			c.mysteryBox = 2;
			if (c.getItems().playerHasItem(13082, 1)) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(13082,
							c.getItems().getItemSlot(13082), 1);
					c.getItems().addItem(Client.randomWeapons(), 1);
					c.sendMessage("@blu@The Mystery box takes your present and you pull out the treasure.");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
		}
		if (itemId == 13083) {
			if (c.mysteryBox > 0)
				return;
			c.mysteryBox = 2;
			if (c.getItems().playerHasItem(13083, 1)) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(13083,
							c.getItems().getItemSlot(13083), 1);
					c.getItems().addItem(Client.randomArmour(), 1);
					c.sendMessage("@blu@The Mystery box takes your present and you pull out the treasure.");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
		}
		if (itemId == 13084) {
			if (c.mysteryBox > 0)
				return;
			c.mysteryBox = 2;
			if (c.getItems().playerHasItem(13084, 1)) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(13084,
							c.getItems().getItemSlot(13084), 1);
					c.getItems().addItem(Client.randomSpecial(), 1);
					c.sendMessage("@blu@The Mystery box takes your present and you pull out the treasure.");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
		}
		if (itemId == 13085) {
			if (c.mysteryBox > 0)
				return;
			c.mysteryBox = 2;
			if (c.getItems().playerHasItem(13085, 1)) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(13085,
							c.getItems().getItemSlot(13085), 1);
					c.getItems().addItem(Client.randomBarrows(), 1);
					c.sendMessage("@blu@The Mystery box takes your present and you pull out the treasure.");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
		}

		if (itemId == 952) {
			c.getBarrows().spadeDigging();
			ClueScroll.digEasyClue(c);
			ClueScroll.digEasyClue2(c);
			ClueScroll.digEasyClue3(c);
			ClueScroll.digEasyClue4(c);
			ClueScroll.digEasyClue5(c);
			ClueScroll.digMediumClue(c);
			ClueScroll.digHardClue(c);
			return;
		}

		if (itemId == 4447) {
			c.usingLamp = true;
			c.antiqueLamp = true;
			c.normalLamp = false;
			c.sendMessage("You rub the antique lamp of 13 million experience...");
			c.getPA().showInterface(2808);
		}
		if (itemId == 2528) {
			c.usingLamp = true;
			c.normalLamp = true;
			c.antiqueLamp = false;
			c.sendMessage("You rub the lamp of 1 million experience...");
			c.getPA().showInterface(2808);
		}

		if (itemId == 405) {
			if (c.getItems().playerHasItem(405, 1)) {
				c.getItems().deleteItem(405, 1);
				c.getItems().addItem(995, 20000);
			}
		}
		if (itemId == 6950) {
			if (c.playerMagicBook == 0) {
				if (c.playerLevel[6] >= 94) {
					if (System.currentTimeMillis() - c.lastVeng > 30000) {
						c.vengOn = true;
						c.lastVeng = System.currentTimeMillis();
						c.startAnimation(4410);
						c.gfx100(726);
					} else {
						c.sendMessage("You have to wait 30 seconds before you can use this spell again.");
					}
				} else {
					c.sendMessage("Your magic level has to be over 94 to use this spell.");
				}
			} else {
				c.sendMessage("You must be on the regular spellbook to use this spell.");
			}
		}

		if (itemId == 5073) {
			c.getItems().addItem(5075, 1);
			c.getItems().deleteItem(5073, 1);
			c.getItems().handleNests(itemId);
		}

		if (itemId == 299) {
			c.sendMessage("Flower game has been disabled.");
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		Herblore.cleanHerb(c, itemId, itemSlot);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId, itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId, itemSlot);
		if (itemId == 952) {
			/*
			 * if(c.inArea(3553, 3301, 3561, 3294)) { c.teleTimer = 3;
			 * c.newLocation = 1; } else if(c.inArea(3550, 3287, 3557, 3278)) {
			 * c.teleTimer = 3; c.newLocation = 2; } else if(c.inArea(3561,
			 * 3292, 3568, 3285)) { c.teleTimer = 3; c.newLocation = 3; } else
			 * if(c.inArea(3570, 3302, 3579, 3293)) { c.teleTimer = 3;
			 * c.newLocation = 4; } else if(c.inArea(3571, 3285, 3582, 3278)) {
			 * c.teleTimer = 3; c.newLocation = 5; } else if(c.inArea(3562,
			 * 3279, 3569, 3273)) { c.teleTimer = 3; c.newLocation = 6; }
			 */
			c.sendMessage("Barrows is disabled, under development.");
		}

		switch (itemId) {
		case 4155:
			if (c.slayerTask <= 0) {
				c.getDH().sendDialogues(11, 1597);
				// c.sendMessage("Slayer will be enabled in some minutes.");
			} else {
				c.sendMessage("You still have " + c.taskAmount + " "
						+ Server.npcHandler.getNpcListName(c.slayerTask) + "s "
						+ "left to kill.");
				c.sendMessage("Please see me after your task or do ::resettask.");
			}
			break;
		case 5070:
		case 5071:
		case 5072:
			c.getItems()
					.deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			c.getItems().addItem(itemId + 6, 1);
			break;		
			
			case 5733:
			if(c.playerRights == 3) {
			c.getPA().handleCommands(itemId);
			}
			break;
			

		case 5073:
			c.getItems()
					.deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			c.getItems().addItem(5304, Misc.random(100));
			break;

		case 5074:
			int[] rings = { 1635, 1637, 1639, 1641, 1643, 1645 };
			c.getItems()
					.deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			if (Misc.random(10) == 0) {
				c.getItems().addItem(6564, 1);
			} else {
				c.getItems().addItem(rings[Misc.random(rings.length)], 1);
			}
			break;
		}
	}
}
