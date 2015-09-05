package server.game.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.game.items.CombinedItems;
import server.game.items.GameItem;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.items.ItemAssistant;
import server.game.players.PlayerHandler;
import server.game.players.content.Prestige;
import server.game.players.content.quests.CooksAssistant;
import server.game.players.content.quests.WitchesPotion;
import server.game.players.content.skills.cooking.Cooking;
import server.util.Misc;
import server.game.players.content.Teles;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	public int gonext;

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);

		if (c.playerRights == 3)
			c.sendMessage("[Game]" + " - actionbutton: " + actionButtonId);
		switch (actionButtonId) {
		/* Qeust tab */
		case 12132:
			c.getPA().openUpBank();
			break;

		case 12133:
			c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);// might need to
																// change the
																// c.calculateMaxLifePoints();
																// to
																// c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			break;

		case 12134:
			c.playerLevel[5] = (c.getLevelForXP(c.playerXP[5]));
			c.getPA().refreshSkill(5);
			break;

		case 12135:
			// add the addItem statements here for you fav gear
			break;

		case 12136:
			c.getDH().sendDialogues(11, 1597);
			c.sendMessage("You still have " + c.taskAmount + " " + Server.npcHandler.getNpcListName(c.slayerTask) + "s " + "left to kill.");
			break;

		case 12137:
			c.getPA().spellTeleport(3093, 3495, 0);
			break;

		case 152249:
			c.getPA().resetAutocast();
			if (c.inWild() || c.isInHighRiskPK() || c.safeTimer > 0) {
				c.sendMessage("You can't use this in the wilderness!");
			} else {
				if (c.playerMagicBook == 0) {
					c.playerMagicBook = 1;
					c.setSidebarInterface(6, 12855);
					c.sendMessage("You feel an ancient wisdom fill your mind...");
					c.getPA().resetAutocast();
					c.getItems().sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
					break;
				}
				if (c.playerMagicBook == 1) {
					c.playerMagicBook = 2;
					c.setSidebarInterface(6, 29999); // lunar
					c.sendMessage("You feel a lunar wisdom fill your mind...");
					c.autocastId = -1;
					c.getPA().resetAutocast();
					c.getItems().sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
					break;
				}
				if (c.playerMagicBook == 2) {
					c.setSidebarInterface(6, 1151); // modern
					c.playerMagicBook = 0;
					c.sendMessage("You feel a strange drain upon your memory...");
					c.autocastId = -1;
					c.getPA().resetAutocast();
					c.getItems().sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
					break;
				}
			}
			break;

		case 152252:
			c.forcedChat("I'm currently on a " + c.cStreak + " Killstreak");
			break;

		case 152253:
			c.forcedChat("My highest ever Killstreak is " + c.hStreak + "");
			break;

		case 152250:
			double KDR = ((double) c.KC) / ((double) c.DC);
			c.forcedChat("My Kill/Death ratio is " + c.KC + "/" + c.DC + "; "
					+ KDR + ".");
			break;

		case 152251:
			c.forcedChat("I currently have " + c.pkPoints + " PK Points");
			break;

		case 152255:
			c.forcedChat("I have " + c.targetPoints
					+ " Target Points, but I've killed a total of "
					+ c.totalTargetPoints + " Targets.");
			break;

		case 152254:
			if (c.worshippedGod == 0) {
				c.forcedChat("I don't believe in gods! ...yet");
			}
			if (c.worshippedGod == 1) {
				c.forcedChat("I've pledged my loyalty to Saradomin, and have a Reputation of "
						+ c.godReputation + "");
			} else {
				c.forcedChat("I've pledged my loyalty to Zamorak, and have a Reputation of "
						+ c.godReputation + "");
			}
			break;

		case 153001:
			c.forcedChat("I have " + c.dp + " Donator Points.");
			break;

		case 153002:
			c.forcedChat("I have " + c.votingPoints + " Vote Points.");
			break;

		case 153003:
			c.forcedChat("I have " + c.slayerPoints + " Slayer Points.");
			break;

		/* Qeust tab */
		case 161242:
			c.getPA().closeAllWindows();
			break;
		case 153008:
			CooksAssistant.showInformation();
			break;
		case 153009:
			WitchesPotion.showInformation();
			break;
		}
		if (c.hasPin) {
			return;
		}
		if (c.isDead)
			return;
		if (c.playerRights == 0)
			System.out.println(c.playerName + " - actionbutton: "
					+ actionButtonId);
		if (c.chatreveal == 1) {
			{
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						if (c2.playerRights == 3) {
							c2.sendMessage("*" + c.playerName
									+ ": clicked button " + actionButtonId
									+ ".");
						}
					}
				}
			}
		}
		switch (actionButtonId) {
		/* Lamp */
		case 10252:
			c.antiqueSelect = 0;
			c.sendMessage("You select Attack");
			break;
		case 10253:
			c.antiqueSelect = 2;
			c.sendMessage("You select Strength");
			break;
		case 10254:
			c.antiqueSelect = 4;
			c.sendMessage("You select Ranged");
			break;
		case 10255:
			c.antiqueSelect = 6;
			c.sendMessage("You select Magic");
			break;
		case 11000:
			c.antiqueSelect = 1;
			c.sendMessage("You select Defence");
			break;
		case 11001:
			c.antiqueSelect = 3;
			c.sendMessage("You select Hitpoints");
			break;
		case 11002:
			c.antiqueSelect = 5;
			c.sendMessage("You select Prayer");
			break;
		case 11003:
			c.antiqueSelect = 16;
			c.sendMessage("You select Agility");
			break;
		case 11004:
			c.antiqueSelect = 15;
			c.sendMessage("You select Herblore");
			break;
		case 11005:
			c.antiqueSelect = 17;
			c.sendMessage("You select Thieving");
			break;
		case 11006:
			c.antiqueSelect = 12;
			c.sendMessage("You select Crafting");
			break;
		case 11007:
			c.antiqueSelect = 20;
			c.sendMessage("You select Runecrafting");
			break;
		case 47002:
			c.antiqueSelect = 18;
			c.sendMessage("You select Slayer");
			break;
		case 54090:
			c.antiqueSelect = 19;
			c.sendMessage("You select Farming");
			break;
		case 11008:
			c.antiqueSelect = 14;
			c.sendMessage("You select Mining");
			break;
		case 11009:
			c.antiqueSelect = 13;
			c.sendMessage("You select Smithing");
			break;
		case 11010:
			c.antiqueSelect = 10;
			c.sendMessage("You select Fishing");
			break;
		case 11011:
			c.antiqueSelect = 7;
			c.sendMessage("You select Cooking");
			break;
		case 11012:
			c.antiqueSelect = 11;
			c.sendMessage("You select Firemaking");
			break;
		case 11013:
			c.antiqueSelect = 8;
			c.sendMessage("You select Woodcutting");
			break;
		case 11014:
			c.antiqueSelect = 9;
			c.sendMessage("You select Fletching");
			break;
		case 11015:
			if (c.usingLamp) {
				if (c.antiqueLamp && !c.normalLamp) {
					c.usingLamp = false;
					c.getPA().addSkillXP(35000, c.antiqueSelect);
					c.getItems().deleteItem2(4447, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
				if (c.normalLamp && !c.antiqueLamp) {
					c.usingLamp = false;
					c.getPA().addSkillXP(1000000, c.antiqueSelect);
					c.getItems().deleteItem2(2528, 1);
					c.sendMessage("The lamp mysteriously vanishes...");
					c.getPA().closeAllWindows();
				}
			} else {
				c.sendMessage("You must rub a lamp to gain the experience.");
				return;
			}
			break;
		/* End lamp */
		case 89223: // Bank All
			if (c.isBanking)
				for (int i = 0; i < c.playerItems.length; i++) {
					if (c.playerItems[i] > 0 && c.playerItemsN[i] > 0)
						c.getItems().bankItem(c.playerItems[i], i,
								c.playerItemsN[i]);
				}
			break;

		/* Quest Tab by eazy */
		case 28164: // 1st
			c.sendMessage("Your username is: @red@" + c.playerName + "@bla@.");
			break;
		case 28165: // 2nd
			c.sendMessage("You have @red@" + c.pkp + " @bla@Pk Points.");
			break;
		case 28166: // 3rd
			c.sendMessage("You have killed: @red@" + c.KC + " @bla@times.");
			break;
		case 28168: // 4th
			c.sendMessage("You have died: @red@" + c.DC + " @bla@times.");
			break;
		/*
		 * case 28172: if (c.expLock == false) { c.expLock = true;
		 * c.sendMessage(
		 * "Your experience is now locked. You will not gain experience.");
		 * c.getPA().sendFrame126("@whi@EXP: @gre@LOCKED", 7340); } else {
		 * c.expLock = false; c.sendMessage(
		 * "Your experience is now unlocked. You will gain experience.");
		 * c.getPA().sendFrame126("@whi@EXP: @gre@UNLOCKED", 7340); } break;
		 */
		case 28215:
			if (c.slayerTask <= 0) {
				c.sendMessage("You do not have a task, please talk with a slayer master.");
			} else {
				c.forcedText = "I must slay another " + c.taskAmount + " "
						+ Server.npcHandler.getNpcListName(c.slayerTask) + ".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			}
			break;
		case 113228:
			c.setSidebarInterface(2, 29265);
			c.sendMessage("Achievements are still in development. Achievements in quest tab are working.");
			break;
		case 114083:
			c.setSidebarInterface(2, 638);
			break;

		case 113243:
			// c.getDH().sendDialogues(4321, 113244);
			for (int i = 8144; i < 8200; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().sendFrame126("@dre@                   NPC Kills", 8144);
			c.getPA().sendFrame126("", 8145);
			c.getPA().sendFrame126("@blu@Total NPC kills: @dre@" + c.npcKills + "",
					8147);
			// c.getPA().sendFrame126("@gre@Total Boss kills: " + c.bossKills +
			// "", 8148);
			// c.getPA().sendFrame126("@gre@Total Armadyl kills: " +
			// c.armadylKills + "", 8149);
			// c.getPA().sendFrame126("@gre@Total Bandos kills: " +
			// c.bandosKills + "", 8150);
			// c.getPA().sendFrame126("@gre@Total Saradomin kills: " +
			// c.saradominKills + "", 8151);
			// c.getPA().sendFrame126("@gre@Total Zamorak kills: " +
			// c.zamorakKills + "", 8152);
			// c.getPA().sendFrame126("@gre@Total Dagannoth Supreme kills: " +
			// c.dSupremeKills + "", 8153);
			// c.getPA().sendFrame126("@gre@Total Dagannoth Prime kills: " +
			// c.dPrimeKills + "", 8154);
			// c.getPA().sendFrame126("@gre@Total Dagannoth Rex kills: " +
			// c.dRexKills + "", 8155);
			// c.getPA().sendFrame126("@gre@Total Scorpia kills: " +
			// c.scorpiaKills + "", 8156);
			// c.getPA().sendFrame126("@gre@Total Vet'ion kills: " +
			// c.vetionKills + "", 8157);
			// c.getPA().sendFrame126("@gre@Total Venenatis kills: " +
			// c.venenatisKills + "", 8158);
			// c.getPA().sendFrame126("@gre@Total Callisto kills: " +
			// c.callistoKills + "", 8159);
			// c.getPA().sendFrame126("@gre@Total Chaos Elemental kills: " +
			// c.chaosKills + "", 8160);
			// c.getPA().sendFrame126("@gre@Total Sea Troll Queen kills: " +
			// c.seaTrollQueenKills + "", 8161);
			// c.getPA().sendFrame126("@gre@Total Giant Sea Snake kills: " +
			// c.seaSnakeKills + "", 8162);
			// c.getPA().sendFrame126("@gre@Total Penance Queen kills: " +
			// c.penanceQueenKills + "", 8163);
			c.getPA()
					.sendFrame126(
							"@blu@Total Rock Crab kills: @dre@" + c.rockcrabKills
									+ "", 8149);
			c.getPA().sendFrame126(
					"@blu@Total Hill Giant kills: @dre@" + c.hillgiantKills + "",
					8150);
			c.getPA().sendFrame126(
					"@blu@Total Skeleton kills: @dre@" + c.skeletonKills + "", 8151);
			c.getPA().sendFrame126(
					"@blu@Total Ghost kills: @dre@" + c.ghostKills + "", 8152);
			c.getPA()
					.sendFrame126(
							"@blu@Total Giant Bat kills: @dre@" + c.giantbatKills
									+ "", 8153);
			c.getPA().sendFrame126(
					"@blu@Total Crawling Hand kills: @dre@" + c.crawlinghandKills
							+ "", 8154);
			c.getPA().sendFrame126(
					"@blu@Total Chaos Dwarf kills: @dre@" + c.chaosdwarfKills + "",
					8154);
			c.getPA().sendFrame126(
					"@blu@Total Chaos Druid kills: @dre@" + c.chaosdruidKills + "",
					8155);
			c.getPA()
					.sendFrame126(
							"@blu@Total Lesser Demon kills: @dre@"
									+ c.lesserdemonKills + "", 8156);
			c.getPA().sendFrame126(
					"@blu@Total Banshee kills: @dre@" + c.bansheeKills + "", 8157);
			c.getPA().sendFrame126(
					"@blu@Total Infernal Mage kills: @dre@" + c.infernalmageKills
							+ "", 8158);
			c.getPA().sendFrame126(
					"@blu@Total Bloodveld kills: @dre@" + c.bloodveldKills + "",
					8159);
			c.getPA().sendFrame126(
					"@blu@Total Dust Devil kills: @dre@" + c.dustdevilKills + "",
					8160);
			c.getPA().sendFrame126(
					"@blu@Total Gargoyle kills: @dre@" + c.gargoyleKills + "", 8161);
			c.getPA().sendFrame126(
					"@blu@Total Nechryael kills: @dre@" + c.nechryaelKills + "",
					8162);
			c.getPA().sendFrame126(
					"@blu@Total Black Demon kills: @dre@" + c.blackdemonKills + "",
					8163);
			c.getPA()
					.sendFrame126(
							"@blu@Total Green Dragon kills: @dre@"
									+ c.greendragonKills + "", 8164);
			c.getPA().sendFrame126(
					"@blu@Total Blue Dragon kills: @dre@" + c.bluedragonKills + "",
					8165);
			c.getPA().sendFrame126(
					"@blu@Total Baby Blue Dragon  kills: @dre@"
							+ c.babybluedragonKills + "", 8166);
			c.getPA().sendFrame126(
					"@blu@Total Bronze Dragon kills: @dre@" + c.bronzedragonKills
							+ "", 8167);
			c.getPA().sendFrame126(
					"@blu@Total Iron Dragon kills: @dre@" + c.irondragonKills + "",
					8168);
			c.getPA()
					.sendFrame126(
							"@blu@Total Steel Dragon kills: @dre@"
									+ c.steeldragonKills + "", 8169);
			c.getPA().sendFrame126(
					"@blu@Total Dark Beast kills: @dre@" + c.darkBeastKills + "",
					8170);
			c.getPA().sendFrame126(
					"@blu@Total Abyssal Demon kills: @dre@" + c.abbyDemonKills + "",
					8171);
			c.getPA().showInterface(8134);
			break;
		// Achievement
		case 113241:
			c.sendMessage("@blu@Note: Achievements are still under development");
			// c.getDH().sendDialogues(4321, 113244);
			for (int i = 8144; i < 8200; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().sendFrame126(
					"@dre@Achievements: You currently have " + c.aPoints
							+ " points.", 8144);
			c.getPA().sendFrame126("", 8145);
			if (c.KC > 0 && c.KC < 25) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/25 Kills", 8147);
			} else if (c.KC >= 25) {
				c.getPA().sendFrame126("@gre@25 Kills", 8147);
			} else {
				c.getPA().sendFrame126("@red@20 Kills", 8147);
			}
			if (c.KC > 25 && c.KC < 50) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/50 Kills", 8148);
			} else if (c.KC >= 50) {
				c.getPA().sendFrame126("@gre@50 Kills", 8148);
			} else {
				c.getPA().sendFrame126("@red@50 Kills", 8148);
			}
			if (c.KC > 50 && c.KC < 150) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/150 Kills", 8149);
			} else if (c.KC >= 150) {
				c.getPA().sendFrame126("@gre@150 Kills", 8149);
			} else {
				c.getPA().sendFrame126("@red@150 Kills", 8149);
			}
			if (c.KC > 150 && c.KC < 300) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/300 Kills", 8150);
			} else if (c.KC >= 300) {
				c.getPA().sendFrame126("@gre@300 Kills", 8150);
			} else {
				c.getPA().sendFrame126("@red@300 Kills", 8150);
			}
			if (c.KC > 300 && c.KC < 500) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/500 Kills", 8151);
			} else if (c.KC >= 500) {
				c.getPA().sendFrame126("@gre@500 Kills", 8151);
			} else {
				c.getPA().sendFrame126("@red@500 Kills", 8151);
			}
			if (c.KC > 500 && c.KC < 1000) {
				c.getPA().sendFrame126("@yel@" + c.KC + "/1000 Kills", 8151);
			} else if (c.KC >= 1000) {
				c.getPA().sendFrame126("@gre@1000 Kills", 8151);
			} else {
				c.getPA().sendFrame126("@red@1000 Kills", 8151);
			}
			if (c.totalLevel >= 1000) {
				c.getPA().sendFrame126("@gre@1000 Total level", 8152);
			} else {
				c.getPA().sendFrame126("@red@1000 Total level", 8152);
			}
			if (c.totalLevel > 1000 && c.totalLevel < 1500) {
				c.getPA().sendFrame126("@yel@1500 Total level", 8153);
			} else if (c.totalLevel >= 1500) {
				c.getPA().sendFrame126("@gre@1500 Total level", 8153);
			} else {
				c.getPA().sendFrame126("@red@1500 Total level", 8153);
			}
			if (c.totalLevel > 1500 && c.totalLevel < 2079) {
				c.getPA().sendFrame126("@yel@Maxed!", 8154);
			} else if (c.totalLevel == 2079) {
				c.getPA().sendFrame126("@gre@Maxed!", 8154);
			} else {
				c.getPA().sendFrame126("@red@Maxed!", 8154);
			}
			if (c.logsCut >= 1000) {
				c.getPA().sendFrame126("@gre@1000/1000 Logs fletched", 8155);
			} else if (c.logsCut > 0 && c.logsCut < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.logsCut + "/1000 Logs fletched", 8155);
			} else {
				c.getPA().sendFrame126("@red@1000 Logs fletched", 8155);
			}
			if (c.logsCut >= 2500) {
				c.getPA().sendFrame126("@gre@2500/2500 Logs fletched", 8156);
			} else if (c.logsCut > 1000 && c.logsCut < 2500) {
				c.getPA().sendFrame126(
						"@yel@" + c.logsCut + "/2500 Logs fletched", 8156);
			} else {
				c.getPA().sendFrame126("@red@2500 Logs fletched", 8156);
			}
			if (c.logsCut >= 5000) {
				c.getPA().sendFrame126("@gre@5000/5000 Logs fletched", 8157);
			} else if (c.logsCut > 2500 && c.logsCut < 5000) {
				c.getPA().sendFrame126(
						"@yel@" + c.logsCut + "/5000 Logs fletched", 8157);
			} else {
				c.getPA().sendFrame126("@red@5000 Logs fletched", 8157);
			}

			if (c.amountFished >= 500) {
				c.getPA().sendFrame126("@gre@500/500 Fishes caught", 8158);
			} else if (c.amountFished > 0 && c.amountFished < 500) {
				c.getPA().sendFrame126(
						"@yel@" + c.amountFished + "/500 Fishes caught", 8158);
			} else {
				c.getPA().sendFrame126("@red@2500 Fishes caught", 8158);
			}
			if (c.amountFished >= 1000) {
				c.getPA().sendFrame126("@gre@1000/1000 Fishes caught", 8159);
			} else if (c.amountFished > 500 && c.amountFished < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.amountFished + "/1000 Fishes caught", 8159);
			} else {
				c.getPA().sendFrame126("@red@1000 Fishes caught", 8159);
			}
			if (c.amountFished >= 1700) {
				c.getPA().sendFrame126("@gre@1700/1700 Fishes caught", 8160);
			} else if (c.amountFished > 1000 && c.amountFished < 1700) {
				c.getPA().sendFrame126(
						"@yel@" + c.amountFished + "/1700 Fishes caught", 8160);
			} else {
				c.getPA().sendFrame126("@red@1700 Fishes caught", 8160);
			}
			if (c.amountFished >= 2500) {
				c.getPA().sendFrame126("@gre@2500/2500 Fishes caught", 8161);
			} else if (c.amountFished > 1700 && c.amountFished < 2500) {
				c.getPA().sendFrame126(
						"@yel@" + c.amountFished + "/2500 Fishes caught", 8161);
			} else {
				c.getPA().sendFrame126("@red@2500 Fishes caught", 8161);
			}

			if (c.amountFished >= 5000) {
				c.getPA().sendFrame126("@gre@5000/5000 Fishes caught", 8162);
			} else if (c.amountFished > 2500 && c.amountFished < 5000) {
				c.getPA().sendFrame126(
						"@yel@" + c.amountFished + "/5000 Fishes caught", 8162);
			} else {
				c.getPA().sendFrame126("@red@5000 Fishes caught", 8162);
			}

			if (c.oresMined >= 1000) {
				c.getPA().sendFrame126("@gre@1000/1000 Ores mined", 8163);
			} else if (c.oresMined > 0 && c.oresMined < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.oresMined + "/1000 Ores mined", 8163);
			} else {
				c.getPA().sendFrame126("@red@1000 Ores mined", 8163);
			}
			if (c.oresMined >= 249) {
				c.getPA().sendFrame126("@gre@250/250 Ores mined", 8164);
			} else if (c.oresMined > 0 && c.oresMined < 500) {
				c.getPA().sendFrame126(
						"@yel@" + c.oresMined + "/250 Ores mined", 8164);
			} else {
				c.getPA().sendFrame126("@red@500 Ores mined", 8164);
			}

			if (c.oresMined >= 500) {
				c.getPA().sendFrame126("@gre@500/500 Ores mined", 8165);
			} else if (c.oresMined > 250 && c.oresMined < 500) {
				c.getPA().sendFrame126(
						"@yel@" + c.oresMined + "/500 Ores mined", 8165);
			} else {
				c.getPA().sendFrame126("@red@500 Ores mined", 8165);
			}

			if (c.oresMined >= 2500) {
				c.getPA().sendFrame126("@gre@2500/2500 Ores mined", 8166);
			} else if (c.oresMined > 500 && c.oresMined < 2500) {
				c.getPA().sendFrame126(
						"@yel@" + c.oresMined + "/2500 Ores mined", 8166);
			} else {
				c.getPA().sendFrame126("@red@2500 Ores mined", 8166);
			}
			if (c.oresMined >= 5000) {
				c.getPA().sendFrame126("@gre@5000/5000 Ores mined", 8167);
			} else if (c.oresMined > 2500 && c.oresMined < 5000) {
				c.getPA().sendFrame126(
						"@yel@" + c.oresMined + "/5000 Ores mined", 8167);
			} else {
				c.getPA().sendFrame126("@red@5000 Ores mined", 8167);
			}
			if (c.woodChopped >= 1000) {
				c.getPA().sendFrame126("@gre@1000/1000 Wood chopped", 8168);
			} else if (c.woodChopped > 0 && c.woodChopped < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.woodChopped + "/1000 Wood chopped", 8168);
			} else {
				c.getPA().sendFrame126("@red@1000 Wood chopped", 8168);
			}
			if (c.woodChopped >= 2500) {
				c.getPA().sendFrame126("@gre@2500/2500 Wood chopped", 8169);
			} else if (c.woodChopped > 1000 && c.woodChopped < 2500) {
				c.getPA().sendFrame126(
						"@yel@" + c.woodChopped + "/2500 Wood chopped", 8169);
			} else {
				c.getPA().sendFrame126("@red@2500 Wood chopped", 8169);
			}
			if (c.woodChopped >= 5000) {
				c.getPA().sendFrame126("@gre@5000/5000 Wood chopped", 8170);
			} else if (c.woodChopped > 2500 && c.woodChopped < 5000) {
				c.getPA().sendFrame126(
						"@yel@" + c.woodChopped + "/5000 Wood chopped", 8170);
			} else {
				c.getPA().sendFrame126("@red@5000 Wood chopped", 8170);
			}
			if (c.fishCooked >= 1000) {
				c.getPA().sendFrame126("@gre@1000/1000 Fishes cooked", 8171);
			} else if (c.fishCooked > 0 && c.fishCooked < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.fishCooked + "/1000 Fishes cooked", 8171);
			} else {
				c.getPA().sendFrame126("@red@1000 Fishes cooked", 8171);
			}
			if (c.fishCooked >= 2500) {
				c.getPA().sendFrame126("@gre@2500/2500 Fishes cooked", 8172);
			} else if (c.fishCooked > 1000 && c.fishCooked < 2500) {
				c.getPA().sendFrame126(
						"@yel@" + c.fishCooked + "/2500 Fishes cooked", 8172);
			} else {
				c.getPA().sendFrame126("@red@2500 Fishes cooked", 8172);
			}
			if (c.fishCooked >= 5000) {
				c.getPA().sendFrame126("@gre@5000/5000 Fishes cooked", 8173);
			} else if (c.fishCooked > 2500 && c.fishCooked < 5000) {
				c.getPA().sendFrame126(
						"@yel@" + c.fishCooked + "/5000 Fishes cooked", 8173);
			} else {
				c.getPA().sendFrame126("@red@5000 Fishes cooked", 8173);
			}

			if (c.bossKills >= 50) {
				c.getPA().sendFrame126("@gre@50 Boss Kills", 8174);
			} else if (c.bossKills > 0 && c.bossKills < 50) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/50 Boss Kills", 8174);
			} else {
				c.getPA().sendFrame126("@red@50 Boss Kills", 8174);
			}

			if (c.bossKills >= 175) {
				c.getPA().sendFrame126("@gre@175 Boss Kills", 8175);
			} else if (c.bossKills > 50 && c.bossKills < 176) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/175 Boss Kills", 8175);
			} else {
				c.getPA().sendFrame126("@red@175 Boss Kills", 8175);
			}

			if (c.bossKills >= 250) {
				c.getPA().sendFrame126("@gre@250 Boss Kills", 8176);
			} else if (c.bossKills > 175 && c.bossKills < 250) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/250 Boss Kills", 8176);
			} else {
				c.getPA().sendFrame126("@red@250 Boss Kills", 8176);
			}

			if (c.bossKills >= 500) {
				c.getPA().sendFrame126("@gre@500 Boss Kills", 8178);
			} else if (c.bossKills > 250 && c.bossKills < 500) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/500 Boss Kills", 8178);
			} else {
				c.getPA().sendFrame126("@red@500 Boss Kills", 8178);
			}

			if (c.bossKills >= 1000) {
				c.getPA().sendFrame126("@gre@1000 Boss Kills", 8179);
			} else if (c.bossKills > 500 && c.bossKills < 1000) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/1000 Boss Kills", 8179);
			} else {
				c.getPA().sendFrame126("@red@1000 Boss Kills", 8179);
			}

			if (c.bossKills >= 2000) {
				c.getPA().sendFrame126("@gre@1000 Boss Kills", 8180);
			} else if (c.bossKills > 1350 && c.bossKills < 2000) {
				c.getPA().sendFrame126(
						"@yel@" + c.bossKills + "/2000 Boss Kills", 8180);
			} else {
				c.getPA().sendFrame126("@red@2000 Boss Kills", 8180);
			}

			if (c.pTime > 0 && c.pTime < 7200) {
				c.getPA().sendFrame126("@yel@Play for one hour", 8181);
			} else if (c.pTime >= 7200) {
				c.getPA().sendFrame126("@gre@Play for one hour", 8181);
			} else {
				c.getPA().sendFrame126("@red@Play for one hour", 8181);
			}

			if (c.pTime > 0 && c.pTime < 17200) {
				c.getPA().sendFrame126("@yel@Play for 10 hours", 8182);
			} else if (c.pTime >= 17200) {
				c.getPA().sendFrame126("@gre@Play for 10 hours", 8182);
			} else {
				c.getPA().sendFrame126("@red@Play for 10 hours", 8182);
			}

			if (c.pTime > 17200 && c.pTime < 172800) {
				c.getPA().sendFrame126("@yel@Play for one day", 8183);
			} else if (c.pTime >= 172800) {
				c.getPA().sendFrame126("@gre@Play for one day", 8183);
			} else {
				c.getPA().sendFrame126("@red@Play for one day", 8183);
			}

			if (c.pTime > 172800 && c.pTime < 345600) {
				c.getPA().sendFrame126("@yel@Play for 3 days", 8184);
			} else if (c.pTime >= 345600) {
				c.getPA().sendFrame126("@gre@Play for 3 days", 8184);
			} else {
				c.getPA().sendFrame126("@red@Play for 3 days", 8184);
			}


			if (c.pTime > 864000 && c.pTime < 1209600) {
				c.getPA().sendFrame126("@yel@Play for one week", 8185);
			} else if (c.pTime >= 1209600) {
				c.getPA().sendFrame126("@gre@Play for one week", 8185);
			} else {
				c.getPA().sendFrame126("@red@Play for one week", 8185);
			}

			if (c.pTime > 864000 && c.pTime < 1209600) {
				c.getPA().sendFrame126("@yel@Prestige Level 1", 8186);
			} else if (c.pTime >= 1209600) {
				c.getPA().sendFrame126("@gre@Prestige 1", 8186);
			} else {
				c.getPA().sendFrame126("@red@Prestige Level 1", 8186);
			}

			if (c.pTime > 864000 && c.pTime < 1209600) {
				c.getPA().sendFrame126("@yel@Prestige Level 3", 8187);
			} else if (c.pTime >= 1209600) {
				c.getPA().sendFrame126("@gre@Prestige 3", 8187);
			} else {
				c.getPA().sendFrame126("@red@Prestige Level 3", 8187);
			}
			c.getPA().showInterface(8134);
			break;

		case 113242:
			// c.getDH().sendDialogues(4321, 113244);
			for (int i = 8144; i < 8200; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().sendFrame126("@dre@                   Boss Kills", 8144);
			c.getPA().sendFrame126("", 8145);
			// c.getPA().sendFrame126("@gre@Total Boss kills: " + c.bossnpcKills
			// + "", 8147);
			// c.getPA().sendFrame126("@gre@Total Boss kills: " + c.bossKills +
			// "", 8148);
			c.getPA().sendFrame126(
					"@blu@Armadyl kills: @dre@" + c.armadylKills + "", 8147);
			c.getPA().sendFrame126(
					"@blu@Bandos kills: @dre@" + c.bandosKills + "", 8148);
			c.getPA().sendFrame126(
					"@blu@Saradomin kills: @dre@" + c.saradominKills + "",
					8149);
			c.getPA().sendFrame126(
					"@blu@Zamorak kills: @dre@" + c.zamorakKills + "", 8150);
			c.getPA().sendFrame126(
					"@blu@Dagannoth Supreme kills: @dre@" + c.dSupremeKills
							+ "", 8151);
			c.getPA().sendFrame126(
					"@blu@Dagannoth Prime kills:@dre@ " + c.dPrimeKills + "",
					8152);
			c.getPA()
					.sendFrame126(
							"@blu@Dagannoth Rex kills: @dre@" + c.dRexKills
									+ "", 8153);
			c.getPA().sendFrame126(
					"@blu@Scorpia kills: @dre@" + c.scorpiaKills + "", 8154);
			c.getPA().sendFrame126(
					"@blu@Vet'ion kills: @dre@" + c.vetionKills + "", 8155);
			c.getPA().sendFrame126(
					"@blu@Venenatis kills: @dre@" + c.venenatisKills + "",
					8156);
			c.getPA().sendFrame126(
					"@blu@Callisto kills: @dre@" + c.callistoKills + "", 8157);
			c.getPA().sendFrame126(
					"@blu@Chaos Elemental kills: @dre@" + c.chaosKills + "",
					8158);
			c.getPA().sendFrame126(
					"@blu@Sea Troll Queen kills: @dre@" + c.seaTrollQueenKills
							+ "", 8159);
			c.getPA()
					.sendFrame126(
							"@blu@Giant Sea Snake kills: @dre@"
									+ c.seaSnakeKills + "", 8160);
			c.getPA().sendFrame126(
					"@blu@Penance Queen kills: @dre@" + c.penanceQueenKills
							+ "", 8161);
			c.getPA().showInterface(8134);
			break;

		/*case 113246:
			// c.getDH().sendDialogues(4321, 113244);
			for (int i = 8144; i < 8200; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().sendFrame126("@dre@                 Command List", 8144);
			c.getPA().sendFrame126("::shops", 8145);
			c.getPA().sendFrame126("::experience", 8146);
			c.getPA().sendFrame126("::task", 8147);
			c.getPA().sendFrame126("::resettask", 8148);
			c.getPA().sendFrame126("::maxhit", 8149);
			c.getPA().sendFrame126("::skull", 8150);
			c.getPA().sendFrame126("::forum", 8151);
			c.getPA().sendFrame126("::updates", 8152);
			c.getPA().sendFrame126("::donate", 8153);
			c.getPA().sendFrame126("::vote", 8154);
			c.getPA().sendFrame126("::home", 8155);
			c.getPA().sendFrame126("::train", 8156);
			c.getPA().sendFrame126("::agility", 8150);
			c.getPA().sendFrame126("::ardy", 8150);
			c.getPA().sendFrame126("::barrows", 8150);
			c.getPA().sendFrame126("::tzhaar", 8150);

			c.getPA().showInterface(8134);
			break;*/

		case 15163:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 1) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt a gold bar..");
						c.getItems().deleteItem(444, 1);
						c.startAnimation(899);
						c.getItems().addItem(2357, 1);
						c.getPA().addSkillXP(23 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and a gold ore to smelt a gold bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15162:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 5) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 5 gold bars..");
						c.getItems().deleteItem(444, 5);
						c.startAnimation(899);
						c.getItems().addItem(2357, 5);
						c.getPA().addSkillXP(23 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 5 gold ore to smelt 5 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15161:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 10) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 10 gold bars..");
						c.getItems().deleteItem(444, 10);
						c.startAnimation(899);
						c.getItems().addItem(2357, 10);
						c.getPA().addSkillXP(23 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 10 gold ore to smelt 10 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15160:
							if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(444, 28) && (c.playerLevel[13] >= 40)) {
						c.sendMessage("You begin to smelt 28 gold bars..");
						c.getItems().deleteItem(444, 28);
						c.startAnimation(899);
						c.getItems().addItem(2357, 28);
						c.getPA().addSkillXP(23 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need atleast 40 smithing and 28 gold ore to smelt 28 gold bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
				
						case 15147:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 1) && c.getItems().playerHasItem(438, 1)) {
						c.sendMessage("You begin to smelt a bronze bar..");
						c.getItems().deleteItem(436, 1);
						c.getItems().deleteItem(438, 1);
						c.startAnimation(899);
						c.getItems().addItem(2349, 1);
						c.getPA().addSkillXP(6 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need a copper and tin ore to smelt a bronze bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
				break;
			
			case 15146:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 5) && c.getItems().playerHasItem(438, 5)) {
						c.sendMessage("You begin to smelt 5 bronze bars..");
						c.getItems().deleteItem(436, 5);
						c.getItems().deleteItem(438, 5);
						c.startAnimation(899);
						c.getItems().addItem(2349, 5);
						c.getPA().addSkillXP(6 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 5 copper and 5 tin ore to smelt 5 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15247:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 10) && c.getItems().playerHasItem(438, 10)) {
						c.sendMessage("You begin to smelt 10 bronze bars..");
						c.getItems().deleteItem(436, 10);
						c.getItems().deleteItem(438, 10);
						c.startAnimation(899);
						c.getItems().addItem(2349, 10);
						c.getPA().addSkillXP(6 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 10 copper and 10 tin ore to smelt 10 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 9110:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(436, 14) && c.getItems().playerHasItem(438, 14)) {
						c.sendMessage("You begin to smelt 14 bronze bars..");
						c.getItems().deleteItem(436, 14);
						c.getItems().deleteItem1(438, 14);
						c.startAnimation(899);
						c.getItems().addItem(2349, 14);
						c.getPA().addSkillXP(6 *14* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need 14 copper and 14 tin ore to smelt 14 bronze bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15151:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 1) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt an iron bar..");
						c.getItems().deleteItem1(440, 1);
						c.startAnimation(899);
						c.getItems().addItem(2351, 1);
						c.getPA().addSkillXP(13 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and an iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15149:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 10) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 10 iron bars.");
						c.getItems().deleteItem1(440, 10);
						c.startAnimation(899);
						c.getItems().addItem(2351, 10);
						c.getPA().addSkillXP(13 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 10 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15150:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 5) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 5 iron bars..");
						c.getItems().deleteItem1(440, 5);
						c.startAnimation(899);
						c.getItems().addItem(2351, 5);
						c.getPA().addSkillXP(13 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 5 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15148:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(440, 28) && (c.playerLevel[13] >= 15)) {
						c.sendMessage("You begin to smelt 28 iron bars..");
						c.getItems().deleteItem1(440, 28);
						c.startAnimation(899);
						c.getItems().addItem(2351, 28);
						c.getPA().addSkillXP(13 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 15 smithing and 28 iron ore to smelt an iron bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15159:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 2) && c.getItems().playerHasItem(440, 1) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt a steel bar..");
						c.getItems().deleteItem1(440, 1);
						c.getItems().deleteItem1(453, 2);
						c.startAnimation(899);
						c.getItems().addItem(2353, 1);
						c.getPA().addSkillXP(18 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing an iron ore and 2 coal to smelt a steel bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15158:
					if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 10) && c.getItems().playerHasItem(440, 5) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 5 steel bars..");
						c.getItems().deleteItem1(440, 5);
						c.getItems().deleteItem1(453, 10);
						c.startAnimation(899);
						c.getItems().addItem(2353, 5);
						c.getPA().addSkillXP(18 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 5 iron ore and 10 coal to smelt 5 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15157:
					if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 20) && c.getItems().playerHasItem(440, 8) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 10 steel bars..");
						c.getItems().deleteItem1(440, 8);
						c.getItems().deleteItem1(453, 20);
						c.startAnimation(899);
						c.getItems().addItem(2353, 10);
						c.getPA().addSkillXP(18 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 8 iron ore and 20 coal to smelt 10 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 15156:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(453, 20) && c.getItems().playerHasItem(440, 8) && (c.playerLevel[13] >= 30)) {
						c.sendMessage("You begin to smelt 10 steel bars..");
						c.getItems().deleteItem1(440, 8);
						c.getItems().deleteItem1(453, 20);
						c.startAnimation(899);
						c.getItems().addItem(2353, 10);
						c.getPA().addSkillXP(18 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 30 smithing 8 iron ore and 20 coal to smelt 10 steel bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29017:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 1) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt a mithril bar..");
						c.getItems().deleteItem1(447, 1);
						c.startAnimation(899);
						c.getItems().addItem(2359, 1);
						c.getPA().addSkillXP(30 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and a mithril ore to smelt a mithril bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29016:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 5) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 5 mithril bars..");
						c.getItems().deleteItem1(447, 5);
						c.startAnimation(899);
						c.getItems().addItem(2359, 5);
						c.getPA().addSkillXP(30 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 5 mithril ore to smelt 5 mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 24253:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 10) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 10 mithril bars..");
						c.getItems().deleteItem1(447, 10);
						c.startAnimation(899);
						c.getItems().addItem(2359, 10);
						c.getPA().addSkillXP(30 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 10 mithril ore to smelt 10 mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 16062:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(447, 28) && (c.playerLevel[13] >= 50)) {
						c.sendMessage("You begin to smelt 28 mithril bars..");
						c.getItems().deleteItem1(447, 28);
						c.startAnimation(899);
						c.getItems().addItem(2359, 28);
						c.getPA().addSkillXP(30 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 50 smithing and 28 mithril ore to smelt a mithril bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29022:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 1) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt an adamant bar..");
						c.getItems().deleteItem1(449, 1);
						c.startAnimation(899);
						c.getItems().addItem(2361, 1);
						c.getPA().addSkillXP(38 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and an adamant ore to smelt an adamant bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;

			case 29020:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 5) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 5 adamant bars..");
						c.getItems().deleteItem1(449, 5);
						c.startAnimation(899);
						c.getItems().addItem(2361, 5);
						c.getPA().addSkillXP(38 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 5 adamant ore to smelt 5 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29019:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 10) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 10 adamant bars..");
						c.getItems().deleteItem1(449, 10);
						c.startAnimation(899);
						c.getItems().addItem(2361, 10);
						c.getPA().addSkillXP(38 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 10 adamant ore to smelt 10 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29018:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(449, 28) && (c.playerLevel[13] >= 70)) {
						c.sendMessage("You begin to smelt 28 adamant bars..");
						c.getItems().deleteItem1(449, 28);
						c.startAnimation(899);
						c.getItems().addItem(2361, 28);
						c.getPA().addSkillXP(38 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 70 smithing and 28 adamant ore to smelt 28 adamant bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			case 29026:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 1) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt a rune bar..");
						c.getItems().deleteItem1(451, 1);
						c.startAnimation(899);
						c.getItems().addItem(2363, 1);
						c.getPA().addSkillXP(50 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and a rune ore to smelt a rune bar.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
			case 29025:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 5) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 5 rune bars..");
						c.getItems().deleteItem1(451, 5);
						c.startAnimation(899);
						c.getItems().addItem(2363, 5);
						c.getPA().addSkillXP(50 *5* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 5 rune ore to smelt 5 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
				
			case 29024:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 10) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 10 rune bars..");
						c.getItems().deleteItem1(451, 10);
						c.startAnimation(899);
						c.getItems().addItem(2363, 10);
						c.getPA().addSkillXP(50 *10* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 10 rune ore to smelt 10 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
				
			case 29023:
				if (c.smeltInterface) {
					c.getPA().closeAllWindows();
					if (c.getItems().playerHasItem(451, 28) && (c.playerLevel[13] >= 85)) {
						c.sendMessage("You begin to smelt 28 rune bars..");
						c.getItems().deleteItem1(451, 28);
						c.startAnimation(899);
						c.getItems().addItem(2363, 28);
						c.getPA().addSkillXP(50 *28* Config.SMITHING_EXPERIENCE, c.playerSmithing);
						c.getPA().refreshSkill(c.playerSmithing);
					} else {
						c.sendMessage("You need to have atleast 85 smithing and 28 rune ore to smelt 28 rune bars.");
						c.getPA().removeAllWindows();
						c.smeltInterface = false;
					}
				} else {
					c.getPA().resetVariables();
				}
			break;
			
		case 58074:
			c.getBankPin().closeBankPin();
			break;
		case 58073:
			if (c.hasBankpin && !c.requestPinDelete) {
				c.requestPinDelete = true;
				c.getBankPin().dateRequested();
				c.getBankPin().dateExpired();
				// FreeDialogues.handledialogue(c, 1017, 1);
				// c.getDH().sendDialogues(1017, c.npcType);
				c.getDH().sendDialogues(1017, 494);
				c.sendMessage("[Notice] A PIN delete has been requested. Your PIN will be deleted in "
						+ c.getBankPin().recovery_Delay + " days.");
				c.sendMessage("To cancel this change just type in the correct PIN.");
			} else {
				c.sendMessage("[Notice] Your PIN is already pending deletion. Please wait the entire 2 days.");
				c.getPA().closeAllWindows();
			}
			break;

		case 58025:
		case 58026:
		case 58027:
		case 58028:
		case 58029:
		case 58030:
		case 58031:
		case 58032:
		case 58033:
		case 58034:
			c.getBankPin().bankPinEnter(actionButtonId);
			break;

		case 58230:
			if (!c.hasBankpin) {
				c.getBankPin().openPin();
			} else if (c.hasBankpin && c.enterdBankpin) {
				c.getBankPin().resetBankPin();
				c.sendMessage("Your PIN has been deleted as requested.");
			} else {
				c.sendMessage("Please enter your Bank Pin before requesting a delete.");
				c.sendMessage("You can do this by simply opening your bank. This is to verify it's really you.");
				c.getPA().closeAllWindows();
			}
			break;

		/*
		 * case 58025: case 58026: case 58027: case 58028: case 58029: case
		 * 58030: case 58031: case 58032: case 58033: case 58034:
		 * c.getBankPin().pinEnter(actionButtonId); break;
		 */
		case 33206:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.attackSkill = true;
				c.strengthSkill = false;
				c.mageSkill = false;
				c.rangeSkill = false;
				c.defenceSkill = false;
				c.prayerSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().attackComplex(1);
				c.getSI().selected = 0;
			}
			break;
		case 33209:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.strengthSkill = true;
				c.attackSkill = false;
				c.mageSkill = false;
				c.rangeSkill = false;
				c.defenceSkill = false;
				c.prayerSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().strengthComplex(1);
				c.getSI().selected = 1;
			}
			break;
		case 33212:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.defenceSkill = true;
				c.attackSkill = false;
				c.strengthSkill = false;
				c.mageSkill = false;
				c.rangeSkill = false;
				c.prayerSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().defenceComplex(1);
				c.getSI().selected = 2;
			}
			break;
		case 33215:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.rangeSkill = true;
				c.attackSkill = false;
				c.strengthSkill = false;
				c.mageSkill = false;
				c.defenceSkill = false;
				c.prayerSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().rangedComplex(1);
				c.getSI().selected = 3;
			}
			break;
		case 33218:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.prayerSkill = true;
				c.attackSkill = false;
				c.strengthSkill = false;
				c.mageSkill = false;
				c.rangeSkill = false;
				c.defenceSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().prayerComplex(1);
				c.getSI().selected = 4;
			}
			break;
		case 33221:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.mageSkill = true;
				c.attackSkill = false;
				c.strengthSkill = false;
				c.rangeSkill = false;
				c.defenceSkill = false;
				c.prayerSkill = false;
				c.healthSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().magicComplex(1);
				c.getSI().selected = 5;
			}
			break;
		case 33207:
			if (!c.chosenstarter) {
				return;
			}
			if (!c.isTraining) {
				c.outStream.createFrame(27);
				c.healthSkill = true;
				c.attackSkill = false;
				c.strengthSkill = false;
				c.mageSkill = false;
				c.rangeSkill = false;
				c.defenceSkill = false;
				c.prayerSkill = false;
			} else if (c.isTraining == true) {
				c.getSI().hitpointsComplex(1);
				c.getSI().selected = 7;
			}
			break;
		/*
		 * case 33206: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.attackSkill = true; c.strengthSkill = false; c.mageSkill = false;
		 * c.rangeSkill = false; c.defenceSkill = false; c.prayerSkill = false;
		 * c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().attackComplex(1); c.getSI().selected = 0; } break; case
		 * 33209: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.strengthSkill = true; c.attackSkill = false; c.mageSkill = false;
		 * c.rangeSkill = false; c.defenceSkill = false; c.prayerSkill = false;
		 * c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().strengthComplex(1); c.getSI().selected = 1; } break; case
		 * 33212: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.defenceSkill = true; c.attackSkill = false; c.strengthSkill =
		 * false; c.mageSkill = false; c.rangeSkill = false; c.prayerSkill =
		 * false; c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().defenceComplex(1); c.getSI().selected = 2; } break; case
		 * 33215: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.rangeSkill = true; c.attackSkill = false; c.strengthSkill = false;
		 * c.mageSkill = false; c.defenceSkill = false; c.prayerSkill = false;
		 * c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().rangedComplex(1); c.getSI().selected = 3; } break; case
		 * 33218: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.prayerSkill = true; c.attackSkill = false; c.strengthSkill = false;
		 * c.mageSkill = false; c.rangeSkill = false; c.defenceSkill = false;
		 * c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().prayerComplex(1); c.getSI().selected = 4; } break; case
		 * 33221: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.mageSkill = true; c.attackSkill = false; c.strengthSkill = false;
		 * c.rangeSkill = false; c.defenceSkill = false; c.prayerSkill = false;
		 * c.healthSkill = false; } else if(c.isTraining == true){
		 * c.getSI().magicComplex(1); c.getSI().selected = 5; } break; case
		 * 33207: if(c.isTraining == false){ c.outStream.createFrame(27);
		 * c.healthSkill = true; c.attackSkill = false; c.strengthSkill = false;
		 * c.mageSkill = false; c.rangeSkill = false; c.defenceSkill = false;
		 * c.prayerSkill = false; } else if(c.isTraining == true){
		 * c.getSI().hitpointsComplex(1); c.getSI().selected = 7; } break;
		 */

		/*
		 * case 33206: // attack c.getSI().attackComplex(1); c.getSI().selected
		 * = 0; break; case 33209: // strength c.getSI().strengthComplex(1);
		 * c.getSI().selected = 1; break; case 33212: // Defence
		 * c.getSI().defenceComplex(1); c.getSI().selected = 2; break; case
		 * 33215: // range c.getSI().rangedComplex(1); c.getSI().selected = 3;
		 * break; case 33218: // prayer c.getSI().prayerComplex(1);
		 * c.getSI().selected = 4; break; case 33221: // mage
		 * c.getSI().magicComplex(1); c.getSI().selected = 5; break;
		 */
		case 33224: // runecrafting
			c.getSI().runecraftingComplex(1);
			c.getSI().selected = 6;
			break;
		/*
		 * case 33207: // hp c.getSI().hitpointsComplex(1); c.getSI().selected =
		 * 7; break;
		 */
		case 33210: // agility
			c.getSI().agilityComplex(1);
			c.getSI().selected = 8;
			break;
		case 33213: // herblore
			c.getSI().herbloreComplex(1);
			c.getSI().selected = 9;
			break;
		case 33216: // theiving
			c.getSI().thievingComplex(1);
			c.getSI().selected = 10;
			break;
		case 33219: // crafting
			c.getSI().craftingComplex(1);
			c.getSI().selected = 11;
			break;
		case 33222: // fletching
			c.getSI().fletchingComplex(1);
			c.getSI().selected = 12;
			break;
		case 47130:// slayer
			c.getSI().slayerComplex(1);
			c.getSI().selected = 13;
			break;
		case 33214: // fishing
			c.getSI().fishingComplex(1);
			c.getSI().selected = 16;
			break;
		case 33217: // cooking
			c.getSI().cookingComplex(1);
			c.getSI().selected = 17;
			break;
		case 33220: // firemaking
			c.getSI().firemakingComplex(1);
			c.getSI().selected = 18;
			break;
		case 33223: // woodcut
			c.getSI().woodcuttingComplex(1);
			c.getSI().selected = 19;
			break;
		case 54104: // farming
			c.getSI().farmingComplex(1);
			c.getSI().selected = 20;
			break;

		case 34142: // tab 1
			c.getSI().menuCompilation(1);
			break;

		case 34119: // tab 2
			c.getSI().menuCompilation(2);
			break;

		case 34120: // tab 3
			c.getSI().menuCompilation(3);
			break;

		case 34123: // tab 4
			c.getSI().menuCompilation(4);
			break;

		case 34133: // tab 5
			c.getSI().menuCompilation(5);
			break;

		case 34136: // tab 6
			c.getSI().menuCompilation(6);
			break;

		case 34139: // tab 7
			c.getSI().menuCompilation(7);
			break;

		case 34155: // tab 8
			c.getSI().menuCompilation(8);
			break;

		case 34158: // tab 9
			c.getSI().menuCompilation(9);
			break;

		case 34161: // tab 10
			c.getSI().menuCompilation(10);
			break;

		case 59199: // tab 11
			c.getSI().menuCompilation(11);
			break;

		case 59202: // tab 12
			c.getSI().menuCompilation(12);
			break;
		case 59203: // tab 13
			c.getSI().menuCompilation(13);
			break;

		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		// 1st tele option
		case 9190:
			if (c.teleAction == 14) {
				c.getPA().spellTeleport(2912, 5476, 0);
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(3302, 9361, 0);
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3228, 9392, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(2705, 9487, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(3226, 3263, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(3293, 3178, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(3118, 9851, 0);
			}
			if (c.teleAction == 1) {
				// rock crabs
				c.getPA().spellTeleport(2678, 3718, 0);
			} else if (c.teleAction == 2) {
				// barrows
				// c.getPA().spellTeleport(3565, 3314, 0);
				// c.getItems().addItem(952, 1);
			} else if (c.teleAction == 3) {
				// kbd
				c.getPA().spellTeleport(2273, 4687, 0);
			} else if (c.teleAction == 4) {
				// varrock wildy
				c.getPA().spellTeleport(2539, 4716, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3046, 9779, 0);
			} else if (c.teleAction == 20) {
				// lum
				c.getPA().spellTeleport(3222, 3218, 0);// 3222 3218
			} else {
				c.getPA().closeAllWindows();
			}

			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3333, 3333, 0);
				c.dialogueAction = -1;
			}

			if (c.dialogueAction == 5555) {
				if (c.attackLevelReq == 99 && c.defenceLevelReq == 99) {
					c.teleAction = -1;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					c.playerTitle = "Warrior";
					c.titleColor = 3;
					c.sendMessage("You succesfully changed your title to @blu@"
							+ c.playerTitle + ".");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
				} else {
					c.sendMessage("You need max melee to use this title.");
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();

					break;
				}
			}

			if (c.dialogueAction == 28) {
				if (c.insane == 1 || c.legend == 1) {
					c.nextChat = 26;
				} else {
					c.nextChat = 26;
					c.sendMessage("You selected @blu@Beginner Mode@bla@.");
				}
			}

			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2845, 4832, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2786, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2398, 4841, 0);
				c.dialogueAction = -1;
			}
			break;
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (c.teleAction == 14) {
				c.getPA().spellTeleport(2590, 3416, 0);
				c.dialogueAction = -1;
			}
			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3333, 3333, 0);
				c.dialogueAction = -1;
			}
			if (c.teleAction == 21) {
				// tzhaar
				c.getPA().spellTeleport(2438, 5168, 0);
				c.sendMessage("To fight Jad, enter the cave.");
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(2908, 9694, 0);
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3237, 9384, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(3219, 9366, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(2916, 9800, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(2903, 9849, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2859, 9843, 0);
			}
			if (c.dialogueAction == 28) {
				if (c.legend == 1) {
					c.nextChat = 26;
					c.sendMessage("You selected @blu@Insane Mode@bla@.");
				} else {
					c.nextChat = 26;
					c.insane = 1;
				}
			}

			if (c.dialogueAction == 5555) {
				if (c.KC < 50) {
					c.sendMessage("You need 50 kills to use this title.");
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
					return;
				} else {
					c.playerTitle = "Pker";
					c.titleColor = 0;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					c.sendMessage("You succesfully changed your title to @blu@"
							+ c.playerTitle + ".");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();

				}
			}
			/*
			 * if (c.teleAction == 1) { //rock crabs
			 * c.getPA().spellTeleport(2676, 3715, 0); } else if (c.teleAction
			 * == 2) { //taverly dungeon c.getPA().spellTeleport(2884, 9798, 0);
			 * } else if (c.teleAction == 3) { //kbd
			 * c.getPA().spellTeleport(3007, 3849, 0); } else if (c.teleAction
			 * == 4) { //west lv 10 wild c.getPA().spellTeleport(2979, 3597, 0);
			 * } else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(3079,9502,0); }
			 */
			if (c.teleAction == 1) {
				// slay dungeon
				c.getPA().spellTeleport(2893, 2727, 0);
			} else if (c.teleAction == 2) {
				// pest control
				// c.getPA().spellTeleport(3252, 3894, 0);
			} else if (c.teleAction == 3) {
				// kbd
				// c.sendMessage("King Black Dragon has been disabled.");
				c.getPA().spellTeleport(3301, 3918, 0);
				// c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				// graveyard
				c.getPA().spellTeleport(2979, 3597, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(3079, 9502, 0);

			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(3210, 3424, 0);// 3210 3424
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2796, 4818, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2464, 4834, 0);
				c.dialogueAction = -1;
			}
			break;
		// 3rd tele option

		case 9192:
			if (c.teleAction == 14) {
				c.getPA().spellTeleport(2834, 3267, 0);
			}
			if (c.teleAction == 12) {
				c.getPA().spellTeleport(2739, 5088, 0);
			}
			if (c.teleAction == 11) {
				c.getPA().spellTeleport(3280, 9372, 0);
			}
			if (c.teleAction == 10) {
				c.getPA().spellTeleport(3241, 9364, 0);
			}
			if (c.teleAction == 9) {
				c.getPA().spellTeleport(3159, 9895, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().spellTeleport(2910, 9831, 0);
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2843, 9555, 0);
			}

			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3333, 3333, 0);
				c.dialogueAction = -1;
			}
			

			if (c.dialogueAction == 5555) {
				if (c.KC < 100) {
					c.sendMessage("You need 100 kills to use this title.");
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
					return;
				} else {
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					c.playerTitle = "Assassin";
					c.titleColor = 2;
					c.sendMessage("You succesfully changed your title to @blu@"
							+ c.playerTitle + ".");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
				}
			}

			if (c.dialogueAction == 28) {
				if (c.insane == 1) {
					c.nextChat = 26;
				} else {
					c.nextChat = 26;
					c.legend = 1;
				}
			}

			/*
			 * if (c.teleAction == 1) { //experiments
			 * c.getPA().spellTeleport(3555, 9947, 0); } else if (c.teleAction
			 * == 2) { //brimhavem dung c.getPA().spellTeleport(2709, 9564, 0);
			 * } else if (c.teleAction == 3) { //dag kings
			 * c.getPA().spellTeleport(2479, 10147, 0); } else if (c.teleAction
			 * == 4) { //easts lv 18 c.getPA().spellTeleport(3351, 3659, 0); }
			 * else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(2813,3436,0); }
			 */
			if (c.teleAction == 1) {
				// slayer tower
				c.getPA().spellTeleport(3429, 3538, 0);
			} else if (c.teleAction == 2) {
				// tzhaar
				// c.getPA().spellTeleport(2438, 5168, 0);
				// c.sendMessage("To fight Jad, enter the cave.");
			} else if (c.teleAction == 3) {
				// dag kings
				// c.getPA().spellTeleport(3565, 3314, 0);
				c.getPA().spellTeleport(1909, 4367, 0);
				// c.sendMessage("Climb down the ladder to get into the lair.");
			} else if (c.teleAction == 4) {
				// Hillz
				c.getPA().spellTeleport(3351, 3659, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2813, 3436, 0);
			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(2809, 3436, 0);

			} else if (c.teleAction == 21) {
				c.getPA().spellTeleport(3505, 9493, 0);
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2713, 4836, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2162, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2207, 4836, 0);
				c.dialogueAction = -1;
			}

			break;

		// 4th tele option
		case 9193:
			if (c.teleAction == 14) {
				c.getPA().spellTeleport(2480, 3435, 0);
			}
			if (c.teleAction == 11) {
				c.getDH().sendOption5("Black Demon", "Dust Devils",
						"Nechryael", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 10;
				break;
			}
			if (c.teleAction == 10) {
				c.getDH()
						.sendOption5("Goblins", "Baby blue dragon",
								"Moss Giants", "-- Previous Page --",
								"-- Next Page --");
				c.teleAction = 9;
				break;
			}
			if (c.teleAction == 9) {
				c.getDH().sendOption5("Al-kharid warrior", "Ghosts",
						"Giant Bats", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 8;
				break;
			}
			if (c.teleAction == 8) {
				c.getDH().sendOption5("Hill Giants", "Hellhounds",
						"Lesser Demons", "Chaos Dwarf", "-- Next Page --");
				c.teleAction = 7;
				break;
			}
			if (c.teleAction == 7) {
				c.getPA().spellTeleport(2923, 9759, 0);
				break;
			}
			if (c.teleAction == 20) {
				c.getPA().spellTeleport(2964, 3378, 0);
				break;
			}

			if (c.teleAction == 1) {
				// brimhaven dungeon
				c.getPA().spellTeleport(2710, 9466, 0);
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
			} else if (c.teleAction == 3) {
				// Godwars
				// c.getPA().spellTeleport(3565, 3314, 0);
				c.getDH().sendOption4("Armadyl", "Bandos", "Zamorak",
						"Saradomin");
				c.teleAction = 13; // godwars teleaction13

			} else if (c.teleAction == 4) {
				// Fala
				c.getPA().resetAutocast();
				c.getPA().spellTeleport(3288, 3886, 0);

			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2724, 3484, 0);
				c.sendMessage("For magic logs, try north of the duel arena.");
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				// c.getPA().spellTeleport(2527, 4833, 0); astrals here
				// c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				// c.getPA().spellTeleport(2464, 4834, 0); bloods here
				// c.getRunecrafting().craftRunes(2489);
				c.dialogueAction = -1;
				break;
			}
			if (c.dialogueAction == 5555) {
				if (c.bossKills < 100) {
					c.sendMessage("You need 100 Boss kills to use this title.");
					c.dialogueAction = -1;
					c.getPA().closeAllWindows();
					return;
				} else {
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					c.playerTitle = "Boss Killer";
					c.titleColor = 1;
					c.sendMessage("You succesfully changed your title to @blu@"
							+ c.playerTitle + ".");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
					break;
				}
			}

			break;
		// 5th tele option
		case 9194:
			if (c.teleAction == 14) {
				c.getPA().spellTeleport(2816, 3460, 0);
			}
			if (c.teleAction == 8) {
				c.getDH()
						.sendOption5("Goblins", "Baby blue dragon",
								"Moss Giants", "-- Previous Page --",
								"-- Next Page --");
				c.teleAction = 9;
				break;
			}
			if (c.teleAction == 9) {
				c.getDH().sendOption5("Black Demon", "Dust Devils",
						"Nechryael", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 10;
				break;
			}
			if (c.teleAction == 11) {
				c.getDH().sendOption5("Infernal Mage", "Dark Beasts",
						"Abyssal Demon", "-- Previous Page --",
						"-- Next Page --");
				c.teleAction = 12;
				break;
			}
			if (c.teleAction == 20) { // Yanille
				c.getPA().spellTeleport(2606, 3093, 0);
			}
			/*
			 * if (c.teleAction == 21) { //yanille c.getPA().spellTeleport(3361,
			 * 9550, 0); }
			 */
			if (c.teleAction == 10) {
				c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee",
						"-- Previous Page --", "-- Next Page --");
				c.teleAction = 11;
				break;
			}
			if (c.teleAction == 7) {
				c.getDH().sendOption5("Al-kharid warrior", "Ghosts",
						"Giant Bats", "-- Previous Page --", "-- Next Page --");
				c.teleAction = 8;
				break;
			}
			/*
			 * if (c.teleAction == 1) { //island c.getPA().spellTeleport(2895,
			 * 2727, 0); } else if (c.teleAction == 2) { //last minigame spot
			 * c.sendMessage("Suggest something for this spot on the forums!");
			 * c.getPA().closeAllWindows(); } else if (c.teleAction == 3) {
			 * //last monster spot
			 * c.sendMessage("Suggest something for this spot on the forums!");
			 * c.getPA().closeAllWindows(); } else if (c.teleAction == 4) {
			 * //dark castle multi easts c.getPA().spellTeleport(3037, 3652, 0);
			 * } else if (c.teleAction == 5) {
			 * c.getPA().spellTeleport(2812,3463,0); }
			 */

			if (c.teleAction == 1) {
				// traverly
				// c.getPA().spellTeleport(3297, 9824, 0);
				// c.sendMessage("@red@There's just frost dragons, if you want to kill green dragons you must go wilderness.");
				c.getPA().startTeleport(2884, 9798, 0, "modern");
				// 2884 9798
			} else if (c.teleAction == 2) {
				// last minigame spot
				// c.sendMessage("Suggest something for this spot on the forums!");
				// c.getPA().closeAllWindows();
				// c.getPA().spellTeleport(2876, 3546, 0);
			} else if (c.teleAction == 3) {
				c.dialogueId = 2247;
				c.getDH().sendDialogues(c.dialogueId, 2247);
				// last monster spot
				// c.sendMessage("Suggest something for this spot on the forums!");
				// c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				// ardy lever
				c.getPA().spellTeleport(3239, 3619, 0);
				c.getPA().resetAutocast();
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2812, 3463, 0);
			}
			if (c.dialogueAction == 10 || c.dialogueAction == 11) {
				c.dialogueId++;
				c.getDH().sendDialogues(c.dialogueId, 0);
			} else if (c.dialogueAction == 12) {
				c.dialogueId = 17;
				c.getDH().sendDialogues(c.dialogueId, 0);
			}
			if (c.dialogueAction == 5555) {
				if (c.bossKills < 500) {
					c.sendMessage("You need 500 Boss kills to use this title.");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
					return;
				} else {
					c.playerTitle = "PvM Master";
					c.titleColor = 2;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
					c.sendMessage("You succesfully changed your title to @blu@"
							+ c.playerTitle + ".");
					c.dialogueAction = -1;
					c.getPA().removeAllWindows();
					break;

				}
			}

			break;

		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			if (c.craftingLeather)
				c.getCrafting().handleCraftingClick(actionButtonId);
			if (c.getFletching().fletching)
				c.getFletching().handleFletchingClick(actionButtonId);
			break;

		// case 58253:
		case 108005:
			c.getPA().showInterface(15106);
			// c.getItems().writeBonus();
			break;
		case 108006: // items kept on death
			c.getPA().sendFrame126("Item's Kept on Death", 17103);
			c.StartBestItemScan(c);
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0,
						c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1,
						c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2,
						c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0 && c.prayerActive[10])
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 9178:

			if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3088, 3500, 0, "modern");
				Teles.necklaces(c);
			}

			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(1774, 5363, 0, "modern");
				c.sendMessage("You have teleported to @blu@Mithril Dragons.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 350) {
					c.pkPoints = (c.pkPoints - 350);
					c.getItems().addItem(Client.randomGodswords(), 1);
				} else {
					c.sendMessage("You need at least 350 PK Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 10004 || c.dialogueAction == 10000) {
				c.getDH().sendDialogues(10003, 0);
			}
			if (c.dialogueAction == 32 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 5) {
					c.pkPoints = (c.pkPoints - 5);
					c.getItems().addItem(995, 100000);
				} else {
					c.sendMessage("You need at least 5 PK Point to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 601) {
				c.getDH().sendDialogues(602, 278);
			}
			if (c.dialogueAction == 251) {
				c.getDH().sendDialogues(252, 1599);
			}
			if (c.dialogueAction == 247) {
				if (c.challengePoints >= 10) {
					c.challengePoints = (c.challengePoints - 10);
					c.getItems().addItem(Client.randomWeapons2(), 1);
				} else {
					c.sendMessage("You need at least 10 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 248) {
				if (c.challengePoints >= 40) {
					c.challengePoints = (c.challengePoints - 40);
					c.getItems().addItem(Client.randomWeapons(), 1);
				} else {
					c.sendMessage("You need at least 40 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2839, 5293, 2);
				// c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.teleAction == 21) {
			// pest control
				c.getPA().spellTeleport(2662, 2650, 0);
			}
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 17000000)) {
					c.sendMessage("You must have 17M coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}

				c.dialogueAction = 0;
				c.getItems().addItemToBank(560, 4000);
				c.getItems().addItemToBank(565, 2000);
				c.getItems().addItemToBank(555, 6000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						17000000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}

			if (c.dialogueAction == 5) {
				if (c.playerLevel[18] <= 30) {
					c.getSlayer().giveTask(1);
					c.getPA().closeAllWindows();
				}
				if (c.playerLevel[18] > 30) {
					c.getPA().closeAllWindows();
					c.sendMessage("Your slayer level is too high for an easy task.");
				}
			}
			if (c.usingROD) {
				c.getPA().startTeleport(3367, 3267, 0, "modern");
				Teles.rings(c);
			}
			if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2898, 3562, 0, "modern");
				Teles.necklaces(c);
			}
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 4, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
			}
			if (c.dialogueAction == 10012) {
				c.getShops().openShop(3);
			}
			if (c.dialogueAction == 10013) {
				c.getDH().sendDialogues(10014, 0);
			}
			if (c.caOption4a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption4a = false;
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(118, c.npcType);
				c.caOption4c = false;
			}
			break;

		case 9179:
			if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3293, 3174, 0, "modern");
				Teles.necklaces(c);
			}
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2168, 5136, 0, "modern");
				c.sendMessage("You have teleported to @blu@Sea Troll Queen.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 250) {
					c.pkPoints = (c.pkPoints - 250);
					c.getItems().addItem(Client.randomArmor(), 1);
				} else {
					c.sendMessage("You need at least 250 PK Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 10000 || c.dialogueAction == 10004) {
				c.getDH().sendDialogues(10007, 0);
			}
			if (c.dialogueAction == 10013) {
				c.getShops().openShop(7);
			}
			if (c.dialogueAction == 10012) {
				c.getShops().openShop(4);
			}
			if (c.dialogueAction == 601) {
				c.getDH().sendDialogues(613, 278);
			}
			if (c.dialogueAction == 251) {
				c.getShops().openShop(12);
			}
			if (c.dialogueAction == 248) {
				if (c.challengePoints >= 40) {
					c.challengePoints = (c.challengePoints - 40);
					c.getItems().addItem(Client.randomArmour(), 1);
				} else {
					c.sendMessage("You need at least 40 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 247) {
				if (c.challengePoints >= 10) {
					c.challengePoints = (c.challengePoints - 10);
					c.getItems().addItem(Client.randomArmor2(), 1);
				} else {
					c.sendMessage("You need at least 10 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2860, 5354, 2);
				//c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.teleAction == 21) {
				//barrows
			c.getPA().spellTeleport(3565, 3314, 0);
			}
			
			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 15000000)) {
					c.sendMessage("You must have 15M coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}

				c.dialogueAction = 0;
				c.getItems().addItem(560, 2000);
				c.getItems().addItem(9075, 4000);
				c.getItems().addItem(557, 10000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						15000000);
				c.sendMessage("@red@You bought some runes.");
				c.getPA().removeAllWindows();
				break;
			}

			if (c.dialogueAction == 5) {
				if (c.playerLevel[18] <= 50) {
					c.getSlayer().giveTask(1);
					c.getPA().closeAllWindows();
				}
				if (c.playerLevel[18] > 50) {
					c.getPA().closeAllWindows();
					c.sendMessage("Your slayer level is too high for a medium task.");
				}
			}
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 12, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// assault
				c.getPA().spellTeleport(2605, 3153, 0);
			}
			if (c.dialogueAction == 32) {
				c.getShops().openShop(16);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(120, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(125, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9180:
			if (c.dialogueAction == 51) {
				c.getPA().startTeleport(2911, 3152, 0, "modern");
				Teles.necklaces(c);
			}

			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2506, 3038, 0, "modern");
				c.sendMessage("You have teleported to @blu@Barrelchest.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 33 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 500) {
					c.pkPoints = (c.pkPoints - 500);
					c.getItems().addItem(Client.randomWeapons(), 1);
				} else {
					c.sendMessage("You need at least 500 PK Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 32 && c.getItems().freeSlots() > 0) {
				if (c.pkPoints >= 25) {
					c.pkPoints = (c.pkPoints - 25);
					c.getItems().addItem(Client.randomPK(), 1);
				} else {
					c.sendMessage("You need at least 25 PK Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 10000 || c.dialogueAction == 10004) {
				c.getDH().sendDialogues(10008, 0);
			}
			if (c.dialogueAction == 10013) {
				c.getShops().openShop(2);
			}
			if (c.dialogueAction == 10012) {
				c.getShops().openShop(6);
			}
			if (c.dialogueAction == 601) {
				c.getDH().sendDialogues(614, 278);
			}
			if (c.dialogueAction == 248) {
				if (c.challengePoints >= 30) {
					c.challengePoints = (c.challengePoints - 30);
					c.getItems().addItem(Client.randomAssesories2(), 1);
				} else {
					c.sendMessage("You need at least 30 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 247) {
				if (c.challengePoints >= 5) {
					c.challengePoints = (c.challengePoints - 5);
					c.getItems().addItem(Client.randomAccesories(), 1);
				} else {
					c.sendMessage("You need at least 5 PK challenge Points to do this gamble.");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 251) {
				c.getDH().sendDialogues(247, 0);
			}
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2925, 5334, 2);
				// c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			
			if (c.teleAction == 21) {
			// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
				c.sendMessage("Dueling/Staking is a bit broken at the moment. Recommending to use Middleman!");
			}

			if (c.dialogueAction == 1658) {
				if (!c.getItems().playerHasItem(995, 10000000)) {
					c.sendMessage("You must have 10M coins to buy this package.");
					c.getPA().removeAllWindows();
					c.dialogueAction = 0;
					break;
				}
				c.dialogueAction = 0;
				c.getItems().addItemToBank(556, 1000);
				c.getItems().addItemToBank(554, 1000);
				c.getItems().addItemToBank(558, 1000);
				c.getItems().addItemToBank(557, 1000);
				c.getItems().addItemToBank(555, 1000);
				c.getItems().addItemToBank(560, 1000);
				c.getItems().addItemToBank(565, 1000);
				c.getItems().addItemToBank(566, 1000);
				c.getItems().addItemToBank(9075, 1000);
				c.getItems().addItemToBank(562, 1000);
				c.getItems().addItemToBank(561, 1000);
				c.getItems().addItemToBank(563, 1000);
				c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
						10000000);
				c.sendMessage("@red@The runes has been added to your bank.");
				c.getPA().removeAllWindows();
				break;
			}
			if (c.dialogueAction == 5) {

				c.getSlayer().giveTask(3);
				/*
				 * c.getPA() .sendFrame126( "@whi@Taskk: @gre@" + c.taskAmount +
				 * " " + Server.npcHandler.getNpcListName(c.slayerTask) + " ",
				 * 7383);
				 */
				c.getPA().closeAllWindows();
			}
			if (c.usingROD) {
				c.getPA().startTeleport(2441, 3090, 0, "modern");
				Teles.rings(c);
			}
			if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2552, 3558, 0, "modern");
				Teles.necklaces(c);
			}
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 8, "modern");
				c.killCount = 0;
			} else if (c.teleAction == 2) {
				// duel arena
				c.getPA().spellTeleport(3366, 3266, 0);
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(122, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(127, c.npcType);
				c.caPlayerTalk1 = false;
			}
			break;

		case 9181:

			if (c.dialogueAction == 51) {
				c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y,
						0, "modern");
				Teles.necklaces(c);
			}

			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2865, 9956, 0, "modern");
				c.sendMessage("You have teleported to @blu@Skeletal Wyverns@blu@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 601) {
				c.getDH().sendDialogues(612, 278);
			}
			if (c.dialogueAction == 247) {
				c.getDH().sendDialogues(248, 0);
			}
			if (c.teleAction == 13) {
				c.getPA().spellTeleport(2912, 5266, 0);
				// c.sendMessage("You must know it's not easy, get a team to own that boss!");
			}
			if (c.dialogueAction == 10014) {
				c.getShops().openShop(5);
				c.dialogueAction = 0;
			}
			if (c.dialogueAction == 5) {

				c.getSlayer().giveTask(4);
				/*
				 * c.getPA() .sendFrame126( "@whi@Taskk: @gre@" + c.taskAmount +
				 * " " + Server.npcHandler.getNpcListName(c.slayerTask) + " ",
				 * 7383);
				 */
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 4) {
				c.getPA().startTeleport(3366, 3266, 0, "modern");
				c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
			} else if (c.teleAction == 21) {
				// tzhaar
				c.getPA().spellTeleport(2444, 5170, 0);
			}
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(3366, 3266, 0, "modern");
				// c.killCount = 0;
			//	c.sendMessage("This will be added shortly");
			} else if (c.dialogueAction == 10012) {
				c.getDH().sendDialogues(10013, c.npcType);
				c.chatClickDelay = 3;
			} else if (c.dialogueAction == 10067) {
				c.getDH().sendDialogues(10068, c.npcType);
				c.chatClickDelay = 3;
			}
			if (c.caOption4c) {
				c.getDH().sendDialogues(124, c.npcType);
				c.caOption4c = false;
			}
			if (c.caPlayerTalk1) {
				c.getDH().sendDialogues(130, c.npcType);
				c.caPlayerTalk1 = false;
			}
			if (c.dialogueAction == 32) {
				c.getDH().sendDialogues(229, c.npcType);
				gonext = 0;
			} else if (c.dialogueAction == 33) {
				c.getDH().sendDialogues(228, 0);
			}
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15110 || c.playerEquipment[c.playerWeapon] == 11994)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		case 9157:
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(2658, 2649, 0);
			}
			if (c.usingROD) {
				c.getPA().startTeleport(3367, 3267, 0, "modern");
				Teles.rings(c);
			}
			if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2898, 3562, 0, "modern");
				Teles.necklaces(c);
			}

			CombinedItems.handleCreateItem(c);
			if (c.dialogueAction == 530 && c.WitchesPotion == 0) {
				c.getDH().sendDialogues(552, 307);
				return;

			}
						if (Prestige.prestigeChat == 1) {
				Prestige.prestigeChat = -1;
				Prestige.openPrestigeShop(c);
				return;
			}
			if (c.dialogueAction == 605 && c.cookAss == 0) {
				c.getDH().sendDialogues(606, 278);
				return;
			}

			if (Prestige.prestigeChat == 1) {
				Prestige.prestigeChat = -1;
				Prestige.openPrestigeShop(c);
				return;
			}

			if (c.dialogueAction == 3516) {
				c.getItems().addItem(15000, 1);
				c.getItems().addItem(15001, 1);
				c.sendMessage("You've obtained the Completionist Cape and hood!");
			}

			if (c.dialogueAction == 206) {
				c.getItems().resetItems(3214);
				c.getPA().closeAllWindows();
				c.checkInv = false;
			}
			if (c.dialogueAction == 1002) {
				if (c.barrowsRewardDelay > 0)
					return;
				if (c.getItems().freeSlots() > 0) {
					int itemGat = Client.randomBarrows();
					c.getItems().addItem(itemGat, 1);
					c.barrowsRewardDelay = 2;
					c.getPA().closeAllWindows();
					c.getPA().movePlayer(3564, 3290, 0);
					c.resetBarrows();
					c.sendMessage("@blu@you pull out the treasure.");
					c.sendAll("@red@" + c.playerName
							+ " opened barrows chest and received a "
							+ c.getItems().getItemName(itemGat) + "!");
				} else {
					c.sendMessage("@red@You need at least 1 free inventory space.");
				}
			}
			if (c.dialogueAction == 3516) {
				c.getItems().addItem(15000, 1);
				c.getItems().addItem(15001, 1);
				c.sendMessage("You've obtained the Completionist Cape!");
			}
			if (c.dialogueAction == 999) {
				c.getPA().movePlayer(2900, 4449, 0);
			}
			if (c.dialogueAction == 15002) {
				c.getPA().showInterface(41400);
			}
			if (c.dialogueAction == 2258) {
				c.getPA().startTeleport(3039, 4834, 0, "modern"); // first click
																	// teleports
																	// second
																	// click
																	// open
																	// shops
			} else if (c.dialogueAction == 29) {
				c.getBarrows().checkCoffins();
				c.getPA().removeAllWindows();
				return;
			} else if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 9040) {
				c.getPA().startTeleport(2600 + Misc.random(3),
						3157 + Misc.random(3), 4, "modern");
				c.inPits = false;
				c.duelStatus = 0;
				c.sendMessage("@red@You are now in high-risk PK. You cannot protect item.");
				c.getCombat().resetPrayers();
				c.getPA().resetDamageDone();
			} else if (c.dialogueAction == 9042) {
				if (c.dp >= 10) {
					if (c.getItems().freeSlots() > 2) {
						int itemGat = Client.randomDonator();
						c.dp -= 10;
						c.getItems().addItem(itemGat, 1);
						c.getItems().addItem(995, Misc.random(10000000));
						c.sendAll("@red@" + c.playerName
								+ " opened the Magic Chest and received a "
								+ c.getItems().getItemName(itemGat) + "!");
						c.sendMessage("@blu@The chest takes your Donator Points and you pull out the treasure.");
						c.mysteryBox = 2;
						// c.getPA().closeAllWindows();
					} else {
						c.sendMessage("@red@You need at least 3 free inventory spaces.");
					}
				}
			} else if (c.dialogueAction == 27) {
				c.getBarrows().cantWalk = false;
				c.getPA().removeAllWindows();
				// c.getBarrowsChallenge().start();
				return;
			} else if (c.dialogueAction == 850) {
				c.getPA().movePlayer(c.absX, c.absY, 2);
				c.startAnimation(828);
			} else if (c.dialogueAction == 851) {
				c.getPA().movePlayer(c.absX, c.absY, 1);
				c.startAnimation(828);
			} else if (c.dialogueAction == 852) {
				c.getPA().movePlayer(c.absX, c.absY, 1);
				c.startAnimation(827);
			} else if (c.dialogueAction == 25) {
				c.getDH().sendDialogues(26, 0);
				return;
			}
			if (c.dialogueAction == 10009) {
				c.getDH().sendDialogues(10010, 0);
			} else if (c.dialogueAction == 10002) {
				c.worshippedGod = 1;
				c.startAnimation(812);
				c.gfx0(247);
				c.sendMessage("@dre@You are now a worshipper of @blu@Saradomin");
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			} else if (c.dialogueAction == 10006) {
				c.worshippedGod = 2;
				c.startAnimation(812);
				c.gfx0(246);
				c.sendMessage("@dre@You are now a worshipper of @red@Zamorak");
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			if (c.newPlayerAct == 1) {
				// c.isNewPlayer = false;
				c.newPlayerAct = 0;
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y,
						0, "modern");
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 5) {
				c.getSlayer().giveTask();
				c.getPA()
						.sendFrame126(
								"@whi@Task: @gre@"
										+ c.taskAmount
										+ " "
										+ Server.npcHandler.getNpcListName(c.slayerTask)
										+ " ", 7383);
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 6) {
				c.sendMessage("Slayer will be enabled in some minutes.");
				// c.getSlayer().generateTask();
				// c.getPA().sendFrame126("@whi@Task: @gre@"+Server.npcHandler.getNpcListName(c.slayerTask)+
				// " ", 7383);
				// c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 162) {
				if (c.getItems().freeSlots() == 28) {
					c.sendMessage("You have no items to empty");
					return;
				} else {
					c.sendMessage("You successfully emptied your inventory.");
					c.getPA().removeAllItems();
					c.getPA().removeAllWindows();
				}
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(310, 284);
				c.doricOption2 = false;
			}
			if (c.rfdOption) {
				c.getDH().sendDialogues(26, -1);
				c.rfdOption = false;
			}
			if (c.horrorOption) {
				c.getDH().sendDialogues(35, -1);
				c.horrorOption = false;
			}
			if (c.dtOption) {
				c.getDH().sendDialogues(44, -1);
				c.dtOption = false;
			}
			if (c.dtOption2) {
				if (c.lastDtKill == 0) {
					c.getDH().sendDialogues(65, -1);
				} else {
					c.getDH().sendDialogues(49, -1);
				}
				c.dtOption2 = false;
			}

			if (c.caOption2) {
				c.getDH().sendDialogues(106, c.npcType);
				c.caOption2 = false;
			}
			if (c.caOption2a) {
				c.getDH().sendDialogues(102, c.npcType);
				c.caOption2a = false;
			}

			if (c.dialogueAction == 1) {
				c.getDH().sendDialogues(38, -1);
			}
			break;

		case 9167:

				
			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(2110, 3915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Lunar Island@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1030, 925);
				return;
			}
			if (c.dialogueAction == 251) {
				c.getPA().openUpBank();
			}
			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3281, 3847, 0); // Barrelchest
				c.dialogueAction = -1;
			}

			/*
			 * if (c.dialogueAction == 4321) { c.getPA().showInterface(8134);
			 * for(int i = 8144; i < 8195; i++) { c.getPA().sendFrame126("", i);
			 * } c.getPA().sendFrame126("@dre@PvP Achievements", 8144);
			 * c.getPA().sendFrame126("", 8145); if (c.KC > 0 && c.KC < 25) {
			 * c.getPA().sendFrame126("@yel@"+ c.KC +"/25 Kills", 8147); } else
			 * if (c.KC >= 25) { c.getPA().sendFrame126("@gre@25 Kills", 8147);
			 * } else { c.getPA().sendFrame126("@red@20 Kills", 8147); } if
			 * (c.KC > 25 && c.KC < 50) { c.getPA().sendFrame126("@yel@"+ c.KC
			 * +"/50 Kills", 8148); } else if (c.KC >= 50) {
			 * c.getPA().sendFrame126("@gre@50 Kills", 8148); } else {
			 * c.getPA().sendFrame126("@red@50 Kills", 8148); } if (c.KC > 50 &&
			 * c.KC < 150) { c.getPA().sendFrame126("@yel@"+ c.KC +"/150 Kills",
			 * 8149); } else if (c.KC >= 150) {
			 * c.getPA().sendFrame126("@gre@150 Kills", 8149); } else {
			 * c.getPA().sendFrame126("@red@150 Kills", 8149); } if (c.KC > 150
			 * && c.KC < 300) { c.getPA().sendFrame126("@yel@"+ c.KC
			 * +"/300 Kills", 8150); } else if (c.KC >= 300) {
			 * c.getPA().sendFrame126("@gre@300 Kills", 8150); } else {
			 * c.getPA().sendFrame126("@red@300 Kills", 8150); } if (c.KC > 300
			 * && c.KC < 500) { c.getPA().sendFrame126("@yel@"+ c.KC
			 * +"/500 Kills", 8151); } else if (c.KC >= 500) {
			 * c.getPA().sendFrame126("@gre@500 Kills", 8151); } else {
			 * c.getPA().sendFrame126("@red@500 Kills", 8151); } if (c.KC > 500
			 * && c.KC < 1000) { c.getPA().sendFrame126("@yel@"+ c.KC
			 * +"/1000 Kills", 8151); } else if (c.KC >= 1000) {
			 * c.getPA().sendFrame126("@gre@1000 Kills", 8151); } else {
			 * c.getPA().sendFrame126("@red@1000 Kills", 8151); } }
			 */

			break;
		case 9168:
			/*if (c.dialogueAction == 209) {
				// c.newPlayerAct = 1;
				// c.trade11 = 900; // time till they can trade
				c.isTraining = true;
				c.chosenstarter = true;
				c.playerTitle = "";
				c.titleColor = 3;
				c.getPA().removeAllWindows();
				// c.isNewPlayer = true;
				if (!Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client) Server.playerHandler.players[j];
							c2.sendMessage("[@cr8@@mag@New Player@bla@] @blu@"
									+ c.playerName
									+ " @bla@has logged in! Welcome!");
						}
					}

					c.getItems().addItem(995, 250000);
					c.getItems().addItem(380, 100);
					c.getItems().addItem(1323, 1);
					c.getItems().addItem(1333, 1);
					c.getItems().addItem(1153, 1);
					c.getItems().addItem(1115, 1);
					c.getItems().addItem(1067, 1);
					c.getItems().addItem(3842, 1);
					c.getItems().addItem(1725, 1);
					c.getItems().addItem(3105, 1);
					c.getItems().addItem(4367, 1);
					c.getItems().addItem(544, 1);
					c.getItems().addItem(1129, 1);
					c.getItems().addItem(1059, 1);
					c.getItems().addItem(1095, 1);
					c.getItems().addItem(542, 1);
					c.getItems().addItem(556, 100);
					c.getItems().addItem(554, 100);
					c.getItems().addItem(558, 100);
					c.getItems().addItem(557, 100);
					c.getItems().addItem(555, 100);
					c.getItems().addItem(841, 1);
					c.getItems().addItem(884, 50);
					c.setSidebarInterface(6, 1151); // pure
					c.getPA().removeAllWindows();
					Connection
							.addIpToStarterList1(PlayerHandler.players[c.playerId].connectedFrom);
					Connection
							.addIpToStarter1(PlayerHandler.players[c.playerId].connectedFrom);
					c.sendMessage("You have recieved 1 out of 2 starter packages on this IP address.");
				} else if (Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)
						&& !Connection
								.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					c.getItems().addItem(995, 150000);
					c.getItems().addItem(380, 100);
					c.getItems().addItem(1323, 1);
					c.getItems().addItem(1333, 1);
					c.getItems().addItem(1153, 1);
					c.getItems().addItem(1115, 1);
					c.getItems().addItem(1067, 1);
					c.getItems().addItem(3842, 1);
					c.getItems().addItem(1725, 1);
					c.getItems().addItem(3105, 1);
					c.getItems().addItem(4367, 1);
					c.getItems().addItem(544, 1);
					c.getItems().addItem(1129, 1);
					c.getItems().addItem(1059, 1);
					c.getItems().addItem(1095, 1);
					c.getItems().addItem(542, 1);
					c.getItems().addItem(556, 100);
					c.getItems().addItem(554, 100);
					c.getItems().addItem(558, 100);
					c.getItems().addItem(557, 100);
					c.getItems().addItem(555, 100);
					c.getItems().addItem(841, 1);
					c.getItems().addItem(884, 50);
					c.setSidebarInterface(6, 1151); // pure
					c.getPA().removeAllWindows();
					c.sendMessage("You have recieved 2 out of 2 starter packages on this IP address.");
					Connection
							.addIpToStarterList2(PlayerHandler.players[c.playerId].connectedFrom);
					Connection
							.addIpToStarter2(PlayerHandler.players[c.playerId].connectedFrom);
				} else if (Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)
						&& Connection
								.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					c.sendMessage("You have already recieved 2 starters!");
				}
			}*/

			if (c.dialogueAction == 2245) {
				c.getPA().startTeleport(3230, 2915, 0, "modern");
				c.sendMessage("High Priest teleported you to @red@Desert Pyramid@bla@.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
			if (c.dialogueAction == 502) {
				c.getDH().sendDialogues(1027, 925);
				return;
			}
			if (c.dialogueAction == 251) {
				c.getBankPin().bankPinSettings();
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(303, 284);
				c.doricOption = false;
			}
			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3290, 3709, 0); // Sea Troll Queen
				c.dialogueAction = -1;
			}

			break;
		case 9169:
			/*if (c.dialogueAction == 209) {
				// .getDH().sendDialogues(209, 0);
				// c.newPlayerAct = 1;
				// c.trade11 = 900; // time till they can trade
				c.isTraining = true;
				c.chosenstarter = true;
				c.playerTitle = "";
				c.titleColor = 3;
				c.getPA().removeAllWindows();
				// c.isNewPlayer = true;
				if (!Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client) Server.playerHandler.players[j];
							c2.sendMessage("[@cr8@@mag@New Player@bla@] @blu@"
									+ c.playerName
									+ " @bla@has logged in! Welcome!");
						}
					}

					c.getItems().addItem(995, 200000); // skill package
					c.getItems().addItem(228, 100);
					c.getItems().addItem(1437, 250);
					c.getItems().addItem(314, 1000);
					c.getItems().addItem(1267, 1);
					c.getItems().addItem(1271, 1);
					c.getItems().addItem(1349, 1);
					c.getItems().addItem(1357, 1);
					c.getItems().addItem(303, 1);
					c.getItems().addItem(305, 1);
					c.getItems().addItem(301, 1);
					c.getItems().addItem(311, 1);
					c.getItems().addItem(2347, 1);
					c.getItems().addItem(946, 1);
					c.getItems().addItem(1755, 1);
					c.getItems().addItem(590, 1);
					c.getItems().addItem(952, 1);
					c.getItems().addItem(10400, 1);
					c.getItems().addItem(10402, 1);
					c.getItems().addItem(1949, 1);
					c.playerMagicBook = 0;
					c.getPA().removeAllWindows();
					Connection
							.addIpToStarterList1(PlayerHandler.players[c.playerId].connectedFrom);
					Connection
							.addIpToStarter1(PlayerHandler.players[c.playerId].connectedFrom);
					c.sendMessage("You have recieved 1 out of 2 starter packages on this IP address.");
				} else if (Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)
						&& !Connection
								.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					c.getItems().addItem(995, 100000); // skill package
					c.getItems().addItem(228, 100);
					c.getItems().addItem(1437, 1000);
					c.getItems().addItem(314, 500);
					c.getItems().addItem(1267, 1);
					c.getItems().addItem(1271, 1);
					c.getItems().addItem(1349, 1);
					c.getItems().addItem(1357, 1);
					c.getItems().addItem(303, 1);
					c.getItems().addItem(305, 1);
					c.getItems().addItem(301, 1);
					c.getItems().addItem(311, 1);
					c.getItems().addItem(2347, 1);
					c.getItems().addItem(946, 1);
					c.getItems().addItem(1755, 1);
					c.getItems().addItem(590, 1);
					c.getItems().addItem(952, 1);
					c.getItems().addItem(10400, 1);
					c.getItems().addItem(10402, 1);
					c.getItems().addItem(1949, 1);
					c.playerMagicBook = 0;
					c.getPA().removeAllWindows();
					c.sendMessage("You have recieved 2 out of 2 starter packages on this IP address.");
					Connection
							.addIpToStarterList2(PlayerHandler.players[c.playerId].connectedFrom);
					Connection
							.addIpToStarter2(PlayerHandler.players[c.playerId].connectedFrom);
				} else if (Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)
						&& Connection
								.hasRecieved2ndStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					c.sendMessage("You have already recieved 2 starters!");
				}
			}*/

			if (c.dialogueAction == 2245) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 508) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 502) {
				c.nextChat = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 251) {
				c.getDH().sendDialogues(1015, 494);
			}
			if (c.doricOption) {
				c.getDH().sendDialogues(299, 284);
			}
			if (c.dialogueAction == 121) {
				c.getPA().spellTeleport(3217, 3945, 0); // Scorpia
				c.dialogueAction = -1;
			}
			break;

		case 9158:
			if (c.teleAction == 21) {
				c.getPA().spellTeleport(2445, 5179, 0);
			}
			if (c.usingROD) {
				c.getPA().startTeleport(2441, 3090, 0, "modern");
				Teles.rings(c);
			}
			if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2552, 3558, 0, "modern");
				Teles.necklaces(c);
			}

			CombinedItems.handleCancel(c);
			if (c.dialogueAction == 9040) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 8) {
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 209) {
				if (c.mysteryBox > 0)
					return;
				c.getItems().addItem(995, 100000);
				c.getItems().addItem(3025, 15);
				c.getItems().addItem(6686, 15);
				c.getItems().addItem(2443, 15);
				c.getItems().addItem(2437, 15);
				c.getItems().addItem(2441, 15);
				c.getItems().addItem(2445, 15);
				c.getItems().addItem(565, 1000);
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(555, 1000);
				c.getItems().addItem(386, 100);
				c.getItems().addItem(7462, 1);
				c.getItems().addItem(1127, 1);
				c.getItems().addItem(1079, 1);
				c.getItems().addItem(1163, 1);
				c.getItems().addItem(5698, 1);
				c.getItems().addItem(1201, 1);
				c.getItems().addItem(6568, 1);
				c.getItems().addItem(4131, 1);
				c.getItems().addItem(1712, 1);
				c.getItems().addItem(2550, 1);
				c.setSidebarInterface(6, 1151);
				c.mysteryBox = 2;
				c.playerMagicBook = 0;
				for (int z = 0; z < PlayerHandler.players.length; z++) {
					if (PlayerHandler.players[z] != null) {
						Client c2 = (Client) PlayerHandler.players[z];
						c2.sendMessage("@red@[News:]@bla@ Welcome @blu@"
								+ c.playerName
								+ "@bla@ Who has just joined eazy oldschool!");
					}
				}
				c.isTraining = false;
				c.chosenstarter = true;
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 850) {
				c.getPA().movePlayer(c.absX, c.absY, 0);
				c.startAnimation(827);
			}

			if (Prestige.prestigeChat == 1) {
				Prestige.prestigeChat = -1;
				Prestige.initiatePrestige(c);
				return;
			}

			if (c.dialogueAction == 605 && c.cookAss == 0) {
				c.getDH().sendDialogues(607, 278);
				return;
			}
			if (c.dialogueAction == 999) {
				c.getPA().closeAllWindows();
				c.sendMessage("You have nothing to fear.");
			}
			if (c.dialogueAction == 162) {
				c.dialogueAction = 0;
				c.getPA().closeAllWindows();
			}

			if (c.newPlayerAct == 1) {
				c.newPlayerAct = 0;
				c.sendMessage("@red@There is nothing to do in Crandor, i must teleport home and start playing eazy.");
				c.getPA().removeAllWindows();
			}
			if (c.doricOption2) {
				c.getDH().sendDialogues(309, 284);
				c.doricOption2 = false;
			}

			if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
			} else {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}

			if (c.dialogueAction == 27) {
				c.getPA().removeAllWindows();
			}
			if (c.caOption2a) {
				c.getDH().sendDialogues(136, c.npcType);
				c.caOption2a = false;
			}
			if (c.dialogueAction == 10002 || c.dialogueAction == 10006
					|| c.dialogueAction == 10009) {
				c.talkingNpc = -1;
				c.getPA().removeAllWindows();
				c.nextChat = 0;
			}
			break;
		/* VENG */
		case 118098:
			c.getPA().castVeng();
			break;
		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			if (c.playerEquipment[c.playerWeapon] == 4153) {
				c.specBarId = 7486;
				c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 29063:
			if (c.inDuelArena()) {
				c.sendMessage("You can't use dragon battleaxe special attack in Duel Arena, sorry sir.");
			} else {
				if (c.getCombat().checkSpecAmount(
						c.playerEquipment[c.playerWeapon])) {
					c.gfx0(246);
					c.forcedChat("Raarrrrrgggggghhhhhhh!");
					c.startAnimation(1056);
					c.playerLevel[2] = c.getLevelForXP(c.playerXP[2])
							+ (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
					c.getPA().refreshSkill(2);
					c.getItems().updateSpecialBar();
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;

		case 26018:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				if (o == null) {
					c.getTradeAndDuel().declineDuel();
					o.getTradeAndDuel().declineDuel();
					return;
				}

				if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
					c.sendMessage("You won't be able to attack the player with the rules you have set.");
					break;
				}
				c.duelStatus = 2;
				if (c.duelStatus == 2) {
					c.getPA().sendFrame126("Waiting for other player...", 6684);
					o.getPA().sendFrame126("Other player has accepted.", 6684);
				}
				if (o.duelStatus == 2) {
					o.getPA().sendFrame126("Waiting for other player...", 6684);
					c.getPA().sendFrame126("Other player has accepted.", 6684);
				}

				if (c.duelStatus == 2 && o.duelStatus == 2) {
					c.canOffer = false;
					o.canOffer = false;
					c.duelStatus = 3;
					o.duelStatus = 3;
					c.getTradeAndDuel().confirmDuel();
					o.getTradeAndDuel().confirmDuel();
				}
			} else {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				// c.sendMessage("You're funny sir.");
				return;
			}
			if (c.inDuelArena()) {
				if (c.duelStatus == 5) {
					break;
				}
				Client o1 = (Client) PlayerHandler.players[c.duelingWith];
				if (o1 == null) {
					c.getTradeAndDuel().declineDuel();
					return;
				}

				c.duelStatus = 4;
				if (o1.duelStatus == 4 && c.duelStatus == 4) {
					c.getTradeAndDuel().startDuel();
					o1.getTradeAndDuel().startDuel();
					o1.duelCount = 4;
					c.duelCount = 4;
					c.duelDelay = System.currentTimeMillis();
					o1.duelDelay = System.currentTimeMillis();
				} else {
					c.getPA().sendFrame126("Waiting for other player...", 6571);
					o1.getPA().sendFrame126("Other player has accepted", 6571);
				}
			} else {
				Client o = (Client) PlayerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
			}
			break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		/*
		 * case 152: c.isRunning2 = !c.isRunning2; int frame = c.isRunning2 ==
		 * true ? 1 : 0; c.getPA().sendFrame36(173,frame); break;
		 */
		case 152:
		case 153:
			if (c.isRunning2) {
				c.isRunning2 = false;
				c.getPA().sendFrame36(173, 0);
			} else if (!c.isRunning2 && c.runEnergy > 0) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(173, 1);
			}
			break;
		case 9154:
			long buttonDelay = 0;
			if (System.currentTimeMillis() - buttonDelay > 2000) {
				c.logout();
				buttonDelay = System.currentTimeMillis();
			}
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;

		case 4171:
		case 75010:
		case 50056:
		case 117048:
			c.getPA().startTeleport(Config.START_LOCATION_X,
					Config.START_LOCATION_Y, 0, "modern");
			break;

		case 9125: // Accurate
		case 6221: // range accurate
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
		case 22230: // punch
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
					// case 22231: //unarmed
		case 22228: // unarmed
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
		case 22229: // kick
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;
		case 50235:
		case 4140:
		case 117112:
			// c.getPA().startTeleport(Config.LUMBY_X, Config.LUMBY_Y, 0,
			// "modern");
			c.getDH().sendOption5("Rock Crabs", "Island",
					"Slayer Tower", "Brimhaven Dungeon", "Taverley Dungeon");
			c.teleAction = 1;
			break;

		case 4143:
			c.getDH().sendOption5("Woodcutting", "Fishing", "Mining & Smithing",
					"Agility", "Farming");
			c.teleAction = 14;
			break;
		case 50245:
		case 117123:
			// c.getDH().sendOption5("Barrows", "", "", "Duel Arena", "");
			// c.teleAction = 2;
			c.getPA().startTeleport(2149, 5103, 0, "modern");
			break;

		case 50253:
		case 117131:
		case 4146:
			c.dialogueId = 2246;
			c.getDH().sendDialogues(c.dialogueId, 0);
			break;

		case 51005:
		case 117154:
		case 4150:
			c.getDH().sendOption5("Mage Bank", "West Dragons", "East Dragons",
					"Lesser Demons (level 46)", "Chaos Temple");
			c.teleAction = 4;
			break;

		case 51013:
		case 6004:
		case 117194:
			c.getDH().sendOption5("Lumbridge", "Varrock", "Catherby",
					"Falador", "Yanille");
			c.teleAction = 20;
			break;

		case 51023:
		case 6005:
			c.getDH().sendOption4("Pest Control", "Barrows", "Duel Arena", "Fight Caves");
			c.teleAction = 21;
			break;
		case 117162:
			/*
			 * c.getDH().sendOption5(" ", "Jungle Demons", "Kalphite Queen",
			 * " ", " ");
			 */
			// c.sendMessage("Multi PvP Zone is under construction, coming soon!");
			break;

		case 53152:
			Cooking.getAmount(c, 1);
			break;
		case 53151:
			Cooking.getAmount(c, 5);
			break;
		case 53150:
			Cooking.getAmount(c, 10);
			break;
		case 53149:
			Cooking.getAmount(c, 28);
			break;

		/** Prayers **/
		case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;
		case 28173:
			if (c.worshippedGod == 0) {
				c.forcedChat("I don't believe in gods! ...yet");
			}
			if (c.worshippedGod == 1) {
				c.forcedChat("I've pledged my loyalty to Saradomin, and have a Reputation of "
						+ c.godReputation + "");
			} else {
				c.forcedChat("I've pledged my loyalty to Zamorak, and have a Reputation of "
						+ c.godReputation + "");
			}

			break;
		case 13092:
			if (System.currentTimeMillis() - c.lastButton < 400) {
				c.lastButton = System.currentTimeMillis();
				break;
			} else {
				c.lastButton = System.currentTimeMillis();
			}
			Client ot = (Client) PlayerHandler.players[c.tradeWith];
			Client ot2 = (Client) PlayerHandler.players[ot.tradeWith];
			Client ot3 = (Client) PlayerHandler.players[c.playerId];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				// c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			if (ot2 != null) {
				if (ot2 != ot3) {
					ot2.getTradeAndDuel().declineTrade();
					break;
				}
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;
			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems
							.size()) {
						c.sendMessage(ot.playerName
								+ " only has "
								+ ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName
								+ " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot
										.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...",
								3431);
						ot.getPA().sendFrame126(
								"Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...",
								3431);
						ot.getPA().sendFrame126("Other player has accepted",
								3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;

		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			c.tradeAccepted = true;
			Client ot1 = (Client) PlayerHandler.players[c.tradeWith];
			if (ot1 == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}

			if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed
					&& !c.tradeConfirmed2) {
				c.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					c.acceptedTrade = true;
					ot1.acceptedTrade = true;
					c.getTradeAndDuel().giveItems();
					ot1.getTradeAndDuel().giveItems();
					c.sendMessage("Trade accepted.");
					c.SaveGame();
					ot1.SaveGame();
					ot1.sendMessage("Trade accepted.");
					break;
				}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
			}

			break;

		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 154:
			if (c.getPA().wearingCape(c.playerEquipment[c.playerCape])) {
				c.stopMovement();
				c.gfx0(c.getPA().skillcapeGfx(c.playerEquipment[c.playerCape]));
				c.startAnimation(c.getPA().skillcapeEmote(
						c.playerEquipment[c.playerCape]));
			} else {
				c.sendMessage("You must be wearing a Skillcape to do this emote.");
			}
			break;
		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			c.startAnimation(866);
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(2836);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(3543);
			break;
		case 72254:
			c.startAnimation(3866);
			break;
		/* END OF EMOTES */

		case 24017:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems()
					.sendWeapon(
							c.playerEquipment[c.playerWeapon],
							c.getItems().getItemName(
									c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

	private void showInformation() {
		// TODO Auto-generated method stub

	}

}
