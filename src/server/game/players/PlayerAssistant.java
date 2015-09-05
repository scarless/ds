package server.game.players;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import server.Config;
import server.Connection;
import server.Server;
import server.clip.region.Region;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.event.Event;
import server.event.EventContainer;
import server.event.EventManager;
import server.game.items.Item;
import server.game.minigames.FightCaves;
import server.game.minigames.PestControl;
import server.game.npcs.NPCHandler;
import server.game.players.combat.melee.MeleeRequirements;
import server.game.players.content.*;
import server.game.players.saving.PlayerSave;
import server.util.Misc;
import server.world.Clan;

public class PlayerAssistant {

	private Client c;
	
	private final int RUNE_ESS = 1436;
	private final int PURE_ESS = 7936;

	public final int SMALL_POUCH = 5509;
	public final int MEDIUM_POUCH = 5510;
	public final int LARGE_POUCH = 5512;
	public final int GIANT_POUCH = 5514;

	public final int BROKEN_MEDIUM_POUCH = 5511;
	public final int BROKEN_LARGE_POUCH = 5513;
	public final int BROKEN_GIANT_POUCH = 5515;

	public int mediumPouchCapacity;
	public int largePouchCapacity;
	public int giantPouchCapacity;

	public PlayerAssistant(Client Client) {
		this.c = Client;
	}
	
	public void teleTabTeleport(int x, int y, int height, String teleportType) {
        if(c.inPits) {
                c.sendMessage("You can't teleport during Fight Pits.");
                return;
        }
        if(c.getPA().inPitsWait()) {
                c.sendMessage("You can't teleport during Fight Pits.");
                return;
        }
if(c.duelStatus == 5) {
	c.sendMessage("You can't teleport during a duel!");
	return;
}
if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
	c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
	return;
}
if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
	c.sendMessage("You are teleblocked and can't teleport.");
	return;
}
if(!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
	if (c.playerIndex > 0 || c.npcIndex > 0)
	    c.getCombat().resetPlayerAttack();
	    c.stopMovement();
	    removeAllWindows();			
	    c.teleX = x;
	    c.teleY = y;
	    c.npcIndex = 0;
	    c.playerIndex = 0;
	    c.faceUpdate(0);
	    c.teleHeight = height;
	    if(teleportType.equalsIgnoreCase("teleTab")) {
	       c.startAnimation(4731);
	       c.teleEndAnimation = 0;
	       c.teleTimer = 8;
	       c.gfx0(678);
	   } 
   }
}

	public void addPouches() {
		if (c.getItems().getItemCount(SMALL_POUCH) < 1 && c.getItems().getItemAmount(SMALL_POUCH) < 1) {
			if (c.getItems().freeSlots() > 0) {
				c.getItems().addItem(SMALL_POUCH, 1);
			} else {
				c.sendMessage("You have run out of inventory slots.");
				return;
			}
		}
		if ((c.getItems().getItemCount(MEDIUM_POUCH) < 1 && c.getItems().getItemAmount(MEDIUM_POUCH) < 1)) {
			if ((c.getItems().getItemCount(BROKEN_MEDIUM_POUCH) < 1 && c.getItems().getItemAmount(BROKEN_MEDIUM_POUCH) < 1)) {
				if (c.playerLevel[20] >= 25) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(MEDIUM_POUCH, 1);
						c.mediumPouchDecay = 45;
					} else {
						c.sendMessage("You have run out of inventory slots.");
						return;
					}
				}
			}
		}
		if ((c.getItems().getItemCount(LARGE_POUCH) < 1 && c.getItems().getItemAmount(LARGE_POUCH) < 1)) {
			if ((c.getItems().getItemCount(BROKEN_LARGE_POUCH) < 1 && c.getItems().getItemAmount(BROKEN_LARGE_POUCH) < 1)) {
				if (c.playerLevel[20] >= 50) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(LARGE_POUCH, 1);
						c.largePouchDecay = 29;
					} else {
						c.sendMessage("You have run out of inventory slots.");
						return;
					}
				}
			}
		}
		if ((c.getItems().getItemCount(GIANT_POUCH) < 1 && c.getItems().getItemAmount(GIANT_POUCH) < 1)) {
			if ((c.getItems().getItemCount(BROKEN_GIANT_POUCH) < 1 && c.getItems().getItemAmount(BROKEN_GIANT_POUCH) < 1)) {
				if (c.playerLevel[20] >= 75) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().addItem(GIANT_POUCH, 1);
						c.giantPouchDecay = 10;
					} else {
						c.sendMessage("You have run out of inventory slots.");
						return;
					}
				}
			}
		}
	}

	public void repairPouches() {
		if (c.getItems().playerHasItem(BROKEN_MEDIUM_POUCH, 1)) {
			c.getItems().deleteItem(BROKEN_MEDIUM_POUCH, 1);
			c.getItems().addItem(MEDIUM_POUCH, 1);
		}
		if (c.getItems().playerHasItem(BROKEN_LARGE_POUCH, 1)) {
			c.getItems().deleteItem(BROKEN_LARGE_POUCH, 1);
			c.getItems().addItem(LARGE_POUCH, 1);
		}
		if (c.getItems().playerHasItem(BROKEN_GIANT_POUCH, 1)) {
			c.getItems().deleteItem(BROKEN_GIANT_POUCH, 1);
			c.getItems().addItem(GIANT_POUCH, 1);
		}
		c.mediumPouchDecay = 45;
		c.largePouchDecay = 29;
		c.giantPouchDecay = 10;
	}

	public void addSmallPouch() {
		if (c.smallPouchP + c.smallPouchE >= 3)
			return;
		int pEss = c.getItems().getItemCount(PURE_ESS);
		int rEss = c.getItems().getItemCount(RUNE_ESS);
		if (pEss > 3)
			pEss = 3;
		if (rEss > 3)
			rEss = 3;
		for (int i = 0; i < pEss; i++) {
			c.smallPouchP++;
			c.getItems().deleteItem(PURE_ESS, 1);
			if (c.smallPouchP + c.smallPouchE >= 3)
				return;
		}
		for (int j = 0; j < rEss; j++) {
			c.smallPouchE++;
			c.getItems().deleteItem(RUNE_ESS, 1);
			if (c.smallPouchP + c.smallPouchE >= 3)
				return;
		}
	}

	public void removeSmallPouch() {
		if (c.smallPouchE == 0 && c.smallPouchP == 0)
			return;
		int pouchp = c.smallPouchP;
		int pouche = c.smallPouchE;
		for (int i = 0; i < c.smallPouchP; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(PURE_ESS, 1);
				pouchp--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.smallPouchP = pouchp;
				return;
			}
		}
		c.smallPouchP = pouchp;
		for (int i = 0; i < c.smallPouchE; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(RUNE_ESS, 1);
				pouche--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.smallPouchE = pouche;
				return;
			}
		}
		c.smallPouchE = pouche;
	}

	public void addMediumPouch() {
		if (c.mediumPouchDecay < 0)
			mediumPouchCapacity = 3;
		else
			mediumPouchCapacity = 6;
		if (c.mediumPouchP + c.mediumPouchE >= mediumPouchCapacity)
			return;
		if (c.mediumPouchDecay > 0)
			c.mediumPouchDecay--;
		if (c.mediumPouchDecay == 0) {
			c.getItems().deleteItem(MEDIUM_POUCH, 1);
			c.getItems().addItem(BROKEN_MEDIUM_POUCH, 1);
			mediumPouchCapacity = 3;
			c.mediumPouchDecay = -1;
		}
		if (c.mediumPouchP + c.mediumPouchE >= mediumPouchCapacity)
			return;
		int pEss = c.getItems().getItemCount(PURE_ESS);
		int rEss = c.getItems().getItemCount(RUNE_ESS);
		if (pEss > mediumPouchCapacity)
			pEss = mediumPouchCapacity;
		if (rEss > mediumPouchCapacity)
			rEss = mediumPouchCapacity;
		for (int i = 0; i < pEss; i++) {
			c.mediumPouchP++;
			c.getItems().deleteItem(PURE_ESS, 1);
			if (c.mediumPouchP + c.mediumPouchE >= mediumPouchCapacity)
				return;
		}
		for (int j = 0; j < rEss; j++) {
			c.mediumPouchE++;
			c.getItems().deleteItem(RUNE_ESS, 1);
			if (c.mediumPouchP + c.mediumPouchE >= mediumPouchCapacity)
				return;
		}
	}

	public void removeMediumPouch() {
		if (c.mediumPouchE == 0 && c.mediumPouchP == 0)
			return;
		int pouchp = c.mediumPouchP;
		int pouche = c.mediumPouchE;
		for (int i = 0; i < c.mediumPouchP; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(PURE_ESS, 1);
				pouchp--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.mediumPouchP = pouchp;
				return;
			}
		}
		c.mediumPouchP = pouchp;
		for (int i = 0; i < c.mediumPouchE; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(RUNE_ESS, 1);
				pouche--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.mediumPouchE = pouche;
				return;
			}
		}
		c.mediumPouchE = pouche;
	}

	public void addLargePouch() {
		if (c.largePouchDecay < 0)
			largePouchCapacity = 6;
		else
			largePouchCapacity = 9;
		if (c.largePouchP + c.largePouchE >= largePouchCapacity)
			return;
		if (c.largePouchDecay > 0)
			c.largePouchDecay--;
		if (c.largePouchDecay == 0) {
			c.getItems().deleteItem(LARGE_POUCH, 1);
			c.getItems().addItem(BROKEN_LARGE_POUCH, 1);
			largePouchCapacity = 6;
			c.largePouchDecay = -1;
		}
		if (c.largePouchP + c.largePouchE >= largePouchCapacity)
			return;
		int pEss = c.getItems().getItemCount(PURE_ESS);
		int rEss = c.getItems().getItemCount(RUNE_ESS);
		if (pEss > largePouchCapacity)
			pEss = largePouchCapacity;
		if (rEss > largePouchCapacity)
			rEss = largePouchCapacity;
		for (int i = 0; i < pEss; i++) {
			c.largePouchP++;
			c.getItems().deleteItem(PURE_ESS, 1);
			if (c.largePouchP + c.largePouchE >= largePouchCapacity)
				return;
		}
		for (int j = 0; j < rEss; j++) {
			c.largePouchE++;
			c.getItems().deleteItem(RUNE_ESS, 1);
			if (c.largePouchP + c.largePouchE >= largePouchCapacity)
				return;
		}
	}

	public void removeLargePouch() {
		if (c.largePouchE == 0 && c.largePouchP == 0)
			return;
		int pouchp = c.largePouchP;
		int pouche = c.largePouchE;
		for (int i = 0; i < c.largePouchP; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(PURE_ESS, 1);
				pouchp--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.largePouchP = pouchp;
				return;
			}
		}
		c.largePouchP = pouchp;
		for (int i = 0; i < c.largePouchE; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(RUNE_ESS, 1);
				pouche--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.largePouchE = pouche;
				return;
			}
		}
		c.largePouchE = pouche;
	}

	public void addGiantPouch() {
		if (c.giantPouchDecay < 0)
			giantPouchCapacity = 9;
		else
			giantPouchCapacity = 12;
		if (c.giantPouchP + c.giantPouchE >= giantPouchCapacity)
			return;
		if (c.giantPouchDecay > 0)
			c.giantPouchDecay--;
		if (c.giantPouchDecay == 0) {
			c.getItems().deleteItem(GIANT_POUCH, 1);
			c.getItems().addItem(BROKEN_GIANT_POUCH, 1);
			giantPouchCapacity = 9;
			c.giantPouchDecay = -1;
		}
		if (c.giantPouchP + c.giantPouchE >= giantPouchCapacity)
			return;
		int pEss = c.getItems().getItemCount(PURE_ESS);
		int rEss = c.getItems().getItemCount(RUNE_ESS);
		if (pEss > giantPouchCapacity)
			pEss = giantPouchCapacity;
		if (rEss > giantPouchCapacity)
			rEss = giantPouchCapacity;
		for (int i = 0; i < pEss; i++) {
			c.giantPouchP++;
			c.getItems().deleteItem(PURE_ESS, 1);
			if (c.giantPouchP + c.giantPouchE >= giantPouchCapacity)
				return;
		}
		for (int j = 0; j < rEss; j++) {
			c.giantPouchE++;
			c.getItems().deleteItem(RUNE_ESS, 1);
			if (c.giantPouchP + c.giantPouchE >= giantPouchCapacity)
				return;
		}
	}

	public void removeGiantPouch() {
		if (c.giantPouchE == 0 && c.giantPouchP == 0)
			return;
		int pouchp = c.giantPouchP;
		int pouche = c.giantPouchE;
		for (int i = 0; i < c.giantPouchP; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(PURE_ESS, 1);
				pouchp--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.giantPouchP = pouchp;
				return;
			}
		}
		c.giantPouchP = pouchp;
		for (int i = 0; i < c.giantPouchE; i++) {
			int invSlots = c.getItems().freeSlots();
			if (invSlots > 0) {
				c.getItems().addItem(RUNE_ESS, 1);
				pouche--;
			} else {
				c.sendMessage("You have run out of free inventory slots.");
				c.giantPouchE = pouche;
				return;
			}
		}
		c.giantPouchE = pouche;
	}
	
	public void handleStairs() {
		c.getDH().sendOption2("Climb Up", "Climb Down");
		c.dialogueAction = 850;
	}

	public void handleUp() {
		c.getDH().sendOption2("Climb Up", "");
		c.dialogueAction = 851;
	}

	public void handleDown() {
		c.getDH().sendOption2("Climb Down", "");
		c.dialogueAction = 852;
	}

	public int CraftInt, Dcolor, FletchInt;

	/**
	 * MulitCombat icon
	 * 
	 * @param i1
	 *            0 = off 1 = on
	 */
	public void multiWay(int i1) {
		// synchronized(c) {
		c.outStream.createFrame(61);
		c.outStream.writeByte(i1);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);

	}
	
		public void handleRSHD(int gloryId) {
		c.getDH().sendOption5("Nex kills, Task System, Fishing Comp, T Trek, Debug, Evil Tree", "Capes, Smelting, Cutscences, Headgear, Statue", "Frem Sagas, NPC info, Photo, LFM, PVP victim", "Macro Events", "Normal");
		c.usingGlory = true;
		c.usingROD = false;
	//	c.usingGamesNeck = false;
	}
	
	public void handleCommands(int gloryId) {
		c.getDH().sendOption5("Restore hp & pray.", "Boost stats.", "Max cash.", "QP cape please!", "QP hood please!");
		c.usingGlory = true;
		c.usingROD = false;
		//c.usingGamesNeck = false;
	}

	public void handleExtra(int gloryId) {
		c.getDH().sendOption2("Open stuff", "Star stuff");
		c.usingGlory = true;
		c.usingROD = false;
	//	c.usingGamesNeck = false;
	}

	public int backupItems[] = new int[Config.BANK_SIZE];
	public int backupItemsN[] = new int[Config.BANK_SIZE];

	public void otherBank(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i];
			backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i];
			c.bankItems[i] = o.bankItems[i];
		}
		openUpBank();

		for (int i = 0; i < o.bankItems.length; i++) {
			c.bankItemsN[i] = backupItemsN[i];
			c.bankItems[i] = backupItems[i];
		}
	}

	public boolean wearingCape(int cape) {
		int capes[] = { 9747, 9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759,
				9760, 9762, 9763, 9765, 9766, 9768, 9769, 9771, 9772, 9774,
				9775, 9777, 9778, 9780, 9781, 9783, 9784, 9786, 9787, 9789,
				9790, 9792, 9793, 9795, 9796, 9798, 9799, 9801, 9802, 9804,
				9805, 9807, 9808, 9810, 9811, 10662 };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	public int skillcapeGfx(int cape) {
		int capeGfx[][] = { { 9747, 823 }, { 9748, 823 }, { 9750, 828 },
				{ 9751, 828 }, { 9753, 824 }, { 9754, 824 }, { 9756, 832 },
				{ 9757, 832 }, { 9759, 829 }, { 9760, 829 }, { 9762, 813 },
				{ 9763, 813 }, { 9765, 817 }, { 9766, 817 }, { 9768, 833 },
				{ 9769, 833 }, { 9771, 830 }, { 9772, 830 }, { 9774, 835 },
				{ 9775, 835 }, { 9777, 826 }, { 9778, 826 }, { 9780, 818 },
				{ 9781, 818 }, { 9783, 812 }, { 9784, 812 }, { 9786, 827 },
				{ 9787, 827 }, { 9789, 820 }, { 9790, 820 }, { 9792, 814 },
				{ 9793, 814 }, { 9795, 815 }, { 9796, 815 }, { 9798, 819 },
				{ 9799, 819 }, { 9801, 821 }, { 9802, 821 }, { 9804, 831 },
				{ 9805, 831 }, { 9807, 822 }, { 9808, 822 }, { 9810, 825 },
				{ 9811, 825 }, { 10662, 816 } };
		for (int i = 0; i < capeGfx.length; i++) {
			if (capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}

	public int skillcapeEmote(int cape) {
		int capeEmote[][] = { { 9747, 4959 }, { 9748, 4959 }, { 9750, 4981 },
				{ 9751, 4981 }, { 9753, 4961 }, { 9754, 4961 }, { 9756, 4973 },
				{ 9757, 4973 }, { 9759, 4979 }, { 9760, 4979 }, { 9762, 4939 },
				{ 9763, 4939 }, { 9765, 4947 }, { 9766, 4947 }, { 9768, 4971 },
				{ 9769, 4971 }, { 9771, 4977 }, { 9772, 4977 }, { 9774, 4969 },
				{ 9775, 4969 }, { 9777, 4965 }, { 9778, 4965 }, { 9780, 4949 },
				{ 9781, 4949 }, { 9783, 4937 }, { 9784, 4937 }, { 9786, 4967 },
				{ 9787, 4967 }, { 9789, 4953 }, { 9790, 4953 }, { 9792, 4941 },
				{ 9793, 4941 }, { 9795, 4943 }, { 9796, 4943 }, { 9798, 4951 },
				{ 9799, 4951 }, { 9801, 4955 }, { 9802, 4955 }, { 9804, 4975 },
				{ 9805, 4975 }, { 9807, 4957 }, { 9808, 4957 }, { 9810, 4963 },
				{ 9811, 4963 }, { 10662, 4945 } };
		for (int i = 0; i < capeEmote.length; i++) {
			if (capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}

	public double round(double valueToRound, int numberOfDecimalPlaces) {
		double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
		double interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	public void resetPlayerSkillVariables(Client c) {
		 if(c.playerIsWoodcutting) {
			for(int i3 = 0; i3 < 9; i3++) {
				c.woodcuttingProp[i3] = -1;
			}
		}
		c.startAnimation(65535);
	}

	/* Treasure */
	public static int lowLevelReward[] = { 1077, 1089, 1107, 1125, 1131, 1129,
			1133, 1511, 1168, 1165, 1179, 1195, 1217, 1283, 1297, 1313, 1327,
			1341, 1361, 1367, 1426, 2633, 2635, 2637, 7388, 7386, 7392, 7390,
			7396, 7394, 2631, 7364, 7362, 7368, 7366, 2583, 2585, 2587, 2589,
			2591, 2593, 2595, 2597, 7332, 7338, 7350, 7356 };
	public static int mediumLevelReward[] = { 2599, 9183, 2601, 2603, 3200, 2605, 2607,
			2609, 3100, 2611, 2613, 7334, 7340, 7346, 7352, 7358, 7319, 7321, 7323,
			7325, 7327, 7372, 7370, 7380, 7378, 2645, 2647, 2649, 2577, 2579,
			1073, 1091, 1099, 1111, 1135, 1124, 1145, 1161, 1169, 1183, 1199,
			1211, 1245, 1271, 1287, 1301, 1317, 1332, 1357, 1371, 1430, 6916,
			6918, 6920, 6922, 6924, 10400, 10402, 10416, 11277, 10418, 10420, 10422,
			10436, 10438, 10446, 10448, 10450, 10452, 10454, 10456, 6889 };
	public static int highLevelReward[] = { 1079, 1093, 1113, 1127, 1147, 1163,
			1185, 1201, 1275, 1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503,
			861, 859, 2581, 2577, 2651, 1079, 1093, 1113, 1127, 1147, 1163,
			1185, 1201, 1275, 1303, 1319, 1333, 1359, 1373, 2491, 2497, 2503,
			861, 859, 2581, 2577, 2651, 2615, 2617, 2619, 2621, 2623, 2625,
			2627, 2629, 2639, 2641, 2643, 2651, 2653, 2655, 2657, 2659, 2661,
			2663, 2665, 2667, 2669, 2671, 2673, 2675, 7342, 7348, 7454, 7460,
			7374, 7376, 7382, 7384, 7398, 7399, 7400, 3481, 3483, 3485, 3486,
			3488, 1079, 1093, 1113, 1127, 1148, 1164, 1185, 1201, 1213, 1247,
			1275, 1289, 1303, 1319, 1333, 1347, 1359, 1374, 1432, 2615, 2617,
			2619, 2621, 2623, 10330, 10332, 10334, 10336, 10344, 10368, 10376,
			10384, 10370, 10378, 10386, 10372, 10330, 10332, 10334, 10336, 10338,
			10340, 10342, 10344, 10346, 10348, 10350, 10352, 10380, 10374, 10382, 10390,
			10470, 10472, 10474, 10440, 10442, 10444, 12422, 12424, 12426 };

	public static int lowLevelStacks[] = { 995, 380, 561, 886, };
	public static int mediumLevelStacks[] = { 995, 374, 561, 563, 890, };
	public static int highLevelStacks[] = { 995, 386, 561, 563, 560, 892 };

	public void sendString(final String s, final int id) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}

	}

	public static void addClueReward(Client c, int clueLevel) {
		int chanceReward = Misc.random(2);
		if (clueLevel == 0) {
			switch (chanceReward) {
			case 0:
				displayReward(c, lowLevelReward[Misc.random(16)], 1,
						lowLevelReward[Misc.random(16)], 1,
						lowLevelStacks[Misc.random(3)], 1 + Misc.random(150));
				break;
			case 1:
				displayReward(c, lowLevelReward[Misc.random(16)], 1,
						lowLevelStacks[Misc.random(3)], 1 + Misc.random(150),
						-1, 1);
				break;
			case 2:
				displayReward(c, lowLevelReward[Misc.random(16)], 1,
						lowLevelReward[Misc.random(16)], 1, -1, 1);
				break;
			}
		} else if (clueLevel == 1) {
			switch (chanceReward) {
			case 0:
				displayReward(c, mediumLevelReward[Misc.random(13)], 1,
						mediumLevelReward[Misc.random(13)], 1,
						mediumLevelStacks[Misc.random(4)], 1 + Misc.random(200));
				break;
			case 1:
				displayReward(c, mediumLevelReward[Misc.random(13)], 1,
						mediumLevelStacks[Misc.random(4)],
						1 + Misc.random(200), -1, 1);
				break;
			case 2:
				displayReward(c, mediumLevelReward[Misc.random(70)], 1,
						mediumLevelReward[Misc.random(70)], 1, -1, 1);
				break;
			}
		} else if (clueLevel == 2) {
			switch (chanceReward) {
			case 0:
				displayReward(c, highLevelReward[Misc.random(75)], 1,
						highLevelReward[Misc.random(60)], 1,
						highLevelStacks[Misc.random(5)], 1 + Misc.random(350));
				break;
			case 1:
				displayReward(c, highLevelReward[Misc.random(52)], 1,
						highLevelStacks[Misc.random(5)], 1 + Misc.random(350),
						-1, 1);
				break;
			case 2:
				displayReward(c, highLevelReward[Misc.random(75)], 1,
						highLevelReward[Misc.random(200)], 1, -1, 1);
				break;
			}
		}
	}

	public static void displayReward(Client c, int item, int amount, int item2,
			int amount2, int item3, int amount3) {
		int[] items = { item, item2, item3 };
		int[] amounts = { amount, amount2, amount3 };
		c.outStream.createFrameVarSizeWord(53);
		c.outStream.writeWord(6963);
		c.outStream.writeWord(items.length);
		for (int i = 0; i < items.length; i++) {
			if (c.playerItemsN[i] > 254) {
				c.outStream.writeByte(255);
				c.outStream.writeDWord_v2(amounts[i]);
			} else {
				c.outStream.writeByte(amounts[i]);
			}
			if (items[i] > 0) {
				c.outStream.writeWordBigEndianA(items[i] + 1);
			} else {
				c.outStream.writeWordBigEndianA(0);
			}
		}
		c.outStream.endFrameVarSizeWord();
		c.flushOutStream();
		c.getItems().addItem(item, amount);
		c.getItems().addItem(item2, amount2);
		c.getItems().addItem(item3, amount3);
		c.getPA().showInterface(6960);
	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		c.getItems().resetItems(3214);
	}

	public void sendSound(int soundId) {
		if (soundId > 0 & c != null && c.outStream != null) {
			c.outStream.createFrame(174);
			c.outStream.writeWord(soundId);
			c.outStream.writeByte(100);
			c.outStream.writeWord(5);
		}
	}

	public void sound(int soundId) {
		if (soundId > 0 && c.outStream != null) {
			c.outStream.createFrame(174);
			c.outStream.writeWord(soundId);
			c.outStream.writeByte(100);
			c.outStream.writeWord(5);
		}
	}

	public void sendColor(int id, int color) {
		c.outStream.createFrame(122);
		c.outStream.writeWordBigEndianA(id);
		c.outStream.writeWordBigEndianA(color);
	}

	public void sendSong(int id) {
		if (c.outStream != null && c != null && id != -1) {
			c.outStream.createFrame(74);
			c.outStream.writeWordBigEndian(id);
		}
	}

	public void sendQuickSong(int id, int songDelay) {
		if (c.outStream != null && c != null) {
			c.outStream.createFrame(121);
			c.outStream.writeWordBigEndian(id);
			c.outStream.writeWordBigEndian(songDelay);
			c.flushOutStream();
		}
	}

	public void setConfig(int id, int state) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(36);
				c.getOutStream().writeWordBigEndian(id);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	public double getAgilityRunRestore(Client c) {
		return 2260 - (c.playerLevel[16] * 10);
	}

	public Client getClient(String playerName) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.playerName.equalsIgnoreCase(playerName)) {
					return (Client) p;
				}
			}
		}
		return null;
	}

	public void normYell(String Message) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendClan(Misc.optimizeText(c.playerName), Message, "eazypk",
						c.playerRights);
			}
		}
	}

	public void admYell(String Message) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendClan(Misc.optimizeText(c.playerName), Message,
						"Developer", c.playerRights);
			}
		}
	}

	public void itemOnInterface(int interfaceChild, int zoom, int itemId) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(interfaceChild);
			c.getOutStream().writeWord(zoom);
			c.getOutStream().writeWord(itemId);
			c.flushOutStream();
		}
	}

	public void playerWalk(int x, int y) {
		PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
	}

	/**
	 * If the player is using melee and is standing diagonal from the opponent,
	 * then move towards opponent.
	 */

	public void movePlayerDiagonal(int i) {
		Client c2 = (Client) PlayerHandler.players[i];
		boolean hasMoved = false;
		int c2X = c2.getX();
		int c2Y = c2.getY();
		if (c.goodDistance(c2X, c2Y, c.getX(), c.getY(), 1)) {
			if (c.getX() != c2.getX() && c.getY() != c2.getY()) {
				if (c.getX() > c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() - 1, c.getY(),
							c.heightLevel, -1, 0)) {
						hasMoved = true;
						playerWalk(-1, 0);
					}
				} else if (c.getX() < c2.getX() && !hasMoved) {
					if (Region.getClipping(c.getX() + 1, c.getY(),
							c.heightLevel, 1, 0)) {
						hasMoved = true;
						playerWalk(1, 0);
					}
				}

				if (c.getY() > c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() - 1,
							c.heightLevel, 0, -1)) {
						hasMoved = true;
						playerWalk(0, -1);
					}
				} else if (c.getY() < c2.getY() && !hasMoved) {
					if (Region.getClipping(c.getX(), c.getY() + 1,
							c.heightLevel, 0, 1)) {
						hasMoved = true;
						playerWalk(0, 1);
					}
				}
			}
		}
		hasMoved = false;
	}

	public Clan getClan() {
		if (Server.clanManager.clanExists(c.playerName)) {
			return Server.clanManager.getClan(c.playerName);
		}
		return null;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		if (rights >= 3)
			rights--;
		c.outStream.createFrameVarSizeWord(217);
		c.outStream.writeString(name);
		c.outStream.writeString(Misc.formatPlayerName(message));
		c.outStream.writeString(clan);
		c.outStream.writeWord(rights);
		c.outStream.endFrameVarSize();
	}

	public void clearClanChat() {
		c.clanId = -1;
		c.getPA().sendString("Talking in: ", 18139);
		c.getPA().sendString("Owner: ", 18140);
		for (int j = 18144; j < 18244; j++) {
			c.getPA().sendString("", j);
		}
	}

	public void setClanData() {
		boolean exists = Server.clanManager.clanExists(c.playerName);
		if (!exists || c.clan == null) {
			sendString("Join chat", 18135);
			sendString("Talking in: Not in chat", 18139);
			sendString("Owner: None", 18140);
		}
		if (!exists) {
			sendString("Chat Disabled", 18306);
			String title = "";
			for (int id = 18307; id < 18317; id += 3) {
				if (id == 18307) {
					title = "Anyone";
				} else if (id == 18310) {
					title = "Anyone";
				} else if (id == 18313) {
					title = "General+";
				} else if (id == 18316) {
					title = "Only Me";
				}
				sendString(title, id + 2);
			}
			for (int index = 0; index < 100; index++) {
				sendString("", 18323 + index);
			}
			for (int index = 0; index < 100; index++) {
				sendString("", 18424 + index);
			}
			return;
		}
		Clan clan = Server.clanManager.getClan(c.playerName);
		sendString(clan.getTitle(), 18306);
		String title = "";
		for (int id = 18307; id < 18317; id += 3) {
			if (id == 18307) {
				title = clan.getRankTitle(clan.whoCanJoin)
						+ (clan.whoCanJoin > Clan.Rank.ANYONE
								&& clan.whoCanJoin < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 18310) {
				title = clan.getRankTitle(clan.whoCanTalk)
						+ (clan.whoCanTalk > Clan.Rank.ANYONE
								&& clan.whoCanTalk < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 18313) {
				title = clan.getRankTitle(clan.whoCanKick)
						+ (clan.whoCanKick > Clan.Rank.ANYONE
								&& clan.whoCanKick < Clan.Rank.OWNER ? "+" : "");
			} else if (id == 18316) {
				title = clan.getRankTitle(clan.whoCanBan)
						+ (clan.whoCanBan > Clan.Rank.ANYONE
								&& clan.whoCanBan < Clan.Rank.OWNER ? "+" : "");
			}
			sendString(title, id + 2);
		}
		if (clan.rankedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.rankedMembers.size()) {
					sendString("<clan=" + clan.ranks.get(index) + ">"
							+ clan.rankedMembers.get(index), 18323 + index);
				} else {
					sendString("", 18323 + index);
				}
			}
		}
		if (clan.bannedMembers != null) {
			for (int index = 0; index < 100; index++) {
				if (index < clan.bannedMembers.size()) {
					sendString(clan.bannedMembers.get(index), 18424 + index);
				} else {
					sendString("", 18424 + index);
				}
			}
		}
	}

	public void resetAutocast() {
		c.autocastId = 0;
		c.autocasting = false;
		c.getPA().sendFrame36(108, 0);
	}

	public int getItemSlot(int itemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == itemID) {
				return i;
			}
		}
		return -1;
	}

	public boolean isItemInBag(int itemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == itemID) {
				return true;
			}
		}
		return false;
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public void turnTo(int pointX, int pointY) {
		c.focusPointX = 2 * pointX + 1;
		c.focusPointY = 2 * pointY + 1;
		c.updateRequired = true;
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}
	
	
		public void addStarter() {
		// c.newPlayerAct = 1;
				c.isTraining = true;
				c.chosenstarter = true;
			//	c.playerTitle = "";
				//c.titleColor = 3;
				c.getPA().removeAllWindows();
				// c.isNewPlayer = true;
				if (!Connection
						.hasRecieved1stStarter(PlayerHandler.players[c.playerId].connectedFrom)) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
							Client c2 = (Client) Server.playerHandler.players[j];
							c2.sendMessage("[@cr7@@mag@New Player@bla@] @blu@"
									+ c.playerName
									+ " @bla@has logged in! Welcome!");
						}
					}
					c.getItems().addItem(995, 300000);
					c.getItems().addItem(380, 100);
					c.getItems().addItem(1323, 1);
					c.getItems().addItem(1333, 1);
					c.getItems().addItem(1153, 1);
					c.getItems().addItem(1067, 1);
					c.getItems().addItem(4367, 1);
					c.getItems().addItem(1059, 1);
					c.getItems().addItem(1115, 1);
					c.getItems().addItem(4121, 1);
					c.getItems().addItem(1725, 1);
					c.getItems().addItem(1540, 1);
					c.getItems().addItem(2550, 1);
					c.getItems().addItem(556, 100);
					c.getItems().addItem(554, 100);
					c.getItems().addItem(558, 100);
					c.getItems().addItem(557, 100);
					c.getItems().addItem(555, 100);
					c.getItems().addItem(841, 1);
					c.getItems().addItem(884, 50);
					c.setSidebarInterface(6, 1151);
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
					c.getItems().addItem(1067, 1);
					c.getItems().addItem(4367, 1);
					c.getItems().addItem(1059, 1);
					c.getItems().addItem(1115, 1);
					c.getItems().addItem(4121, 1);
					c.getItems().addItem(1725, 1);
					c.getItems().addItem(1540, 1);
					c.getItems().addItem(2550, 1);
					c.getItems().addItem(556, 100);
					c.getItems().addItem(554, 100);
					c.getItems().addItem(558, 100);
					c.getItems().addItem(557, 100);
					c.getItems().addItem(555, 100);
					c.getItems().addItem(841, 1);
					c.getItems().addItem(884, 50);
					c.setSidebarInterface(6, 1151);
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
			}

	public int absX, absY;
	public int heightLevel;

	public static void QuestReward(Client c, String questName, int PointsGain,
			String Line1, String Line2, String Line3, String Line4,
			String Line5, String Line6, int itemID) {
		c.getPA().sendFrame126("You have completed " + questName + "!", 12144);
		sendQuest(c, "" + (c.QuestPoints), 12147);
		// c.QuestPoints += PointsGain;
		sendQuest(c, Line1, 12150);
		sendQuest(c, Line2, 12151);
		sendQuest(c, Line3, 12152);
		sendQuest(c, Line4, 12153);
		sendQuest(c, Line5, 12154);
		sendQuest(c, Line6, 12155);
		c.getPlayerAssistant().sendFrame246(12145, 250, itemID);
		showInterface(c, 12140);
		Server.getStillGraphicsManager().stillGraphics(c, c.getX(), c.getY(),
				c.getHeightLevel(), 199, 0);
	}

	public static void showInterface(Client client, int i) {
		client.getOutStream().createFrame(97);
		client.getOutStream().writeWord(i);
		client.flushOutStream();
	}

	public static void sendQuest(Client client, String s, int i) {
		client.getOutStream().createFrameVarSizeWord(126);
		client.getOutStream().writeString(s);
		client.getOutStream().writeWordA(i);
		client.getOutStream().endFrameVarSizeWord();
		client.flushOutStream();
	}

	public void sendStillGraphics(int id, int heightS, int y, int x, int timeBCS) {
		c.getOutStream().createFrame(85);
		c.getOutStream().writeByteC(y - (c.mapRegionY * 8));
		c.getOutStream().writeByteC(x - (c.mapRegionX * 8));
		c.getOutStream().createFrame(4);
		c.getOutStream().writeByte(0);// Tiles away (X >> 4 + Y & 7)
										// //Tiles away from
		// absX and absY.
		c.getOutStream().writeWord(id); // Graphic ID.
		c.getOutStream().writeByte(heightS); // Height of the graphic when
												// cast.
		c.getOutStream().writeWord(timeBCS); // Time before the graphic
												// plays.
		c.flushOutStream();
	}

	public void createArrow(int type, int id) {
		if (c != null) {
			c.getOutStream().createFrame(254); // The packet ID
			c.getOutStream().writeByte(type); // 1=NPC, 10=Player
			c.getOutStream().writeWord(id); // NPC/Player ID
			c.getOutStream().write3Byte(0); // Junk
		}
	}

	public void createArrow(int x, int y, int height, int pos) {
		if (c != null) {
			c.getOutStream().createFrame(254); // The packet ID
			c.getOutStream().writeByte(pos); // Position on Square(2 = middle, 3
												// = west, 4 = east, 5 = south,
												// 6 = north)
			c.getOutStream().writeWord(x); // X-Coord of Object
			c.getOutStream().writeWord(y); // Y-Coord of Object
			c.getOutStream().writeByte(height); // Height off Ground
		}
	}

	public void sendQuest(String s, int i) {
		c.getOutStream().createFrameVarSizeWord(126);
		c.getOutStream().writeString(s);
		c.getOutStream().writeWordA(i);
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public void sendFrame126(String s, int id) {
		if (!c.checkPacket126Update(s, id)) {
			int bytesSaved = (s.length() + 4);
			return;
		}
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}

	}

	public void sendLink(String s) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(187);
			c.getOutStream().writeString(s);
		}

	}

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(134);
			c.getOutStream().writeByte(skillNum);
			c.getOutStream().writeDWord_v1(XP);
			c.getOutStream().writeByte(currentLevel);
			c.flushOutStream();
		}
		// }
	}

	public void sendFrame106(int sideIcon) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(106);
			c.getOutStream().writeByteC(sideIcon);
			c.flushOutStream();
			requestUpdates();
		}
	}

	public void sendFrame107() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(107);
			c.flushOutStream();
		}
	}

	public void sendFrame36(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(36);
			c.getOutStream().writeWordBigEndian(id);
			c.getOutStream().writeByte(state);
			c.flushOutStream();
		}

	}

	public void sendFrame185(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(185);
			c.getOutStream().writeWordBigEndianA(Frame);
		}

	}

	public void showInterface(int interfaceid) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(97);
			c.getOutStream().writeWord(interfaceid);
			c.flushOutStream();

		}
	}

	public void sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();

		}
	}

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.getOutStream().writeWord(SubFrame2);
			c.flushOutStream();

		}
	}

	public void sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(171);
			c.getOutStream().writeByte(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();

		}
	}

	public void sendFrame200(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(200);
			c.getOutStream().writeWord(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	public void sendFrame70(int i, int o, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(70);
			c.getOutStream().writeWord(i);
			c.getOutStream().writeWordBigEndian(o);
			c.getOutStream().writeWordBigEndian(id);
			c.flushOutStream();
		}

	}

	public void sendFrame75(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(75);
			c.getOutStream().writeWordBigEndianA(MainFrame);
			c.getOutStream().writeWordBigEndianA(SubFrame);
			c.flushOutStream();
		}

	}

	public void sendFrame164(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(164);
			c.getOutStream().writeWordBigEndian_dup(Frame);
			c.flushOutStream();
		}

	}

	public void setPrivateMessaging(int i) { // friends and ignore list status
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(221);
			c.getOutStream().writeByte(i);
			c.flushOutStream();
		}

	}

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(206);
			c.getOutStream().writeByte(publicChat);
			c.getOutStream().writeByte(privateChat);
			c.getOutStream().writeByte(tradeBlock);
			c.flushOutStream();
		}

	}

	public void sendFrame87(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(87);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.getOutStream().writeDWord_v1(state);
			c.flushOutStream();
		}

	}

	public void sendPM(long name, int rights, byte[] chatmessage,
			int messagesize) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSize(196);
			c.getOutStream().writeQWord(name);
			c.getOutStream().writeDWord(c.lastChatId++);
			c.getOutStream().writeByte(rights);
			c.getOutStream().writeBytes(chatmessage, messagesize, 0);
			c.getOutStream().endFrameVarSize();
			c.flushOutStream();
			String chatmessagegot = Misc.textUnpack(chatmessage, messagesize);
			String target = Misc.longToPlayerName(name);
		}

	}

	public void createPlayerHints(int type, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(type);
			c.getOutStream().writeWord(id);
			c.getOutStream().write3Byte(0);
			c.flushOutStream();
		}

	}

	public void createObjectHints(int x, int y, int height, int pos) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(pos);
			c.getOutStream().writeWord(x);
			c.getOutStream().writeWord(y);
			c.getOutStream().writeByte(height);
			c.flushOutStream();
		}

	}

	public void loadPM(long playerName, int world) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (world != 0) {
				world += 9;
			} else if (!Config.WORLD_LIST_FIX) {
				world += 1;
			}
			c.getOutStream().createFrame(50);
			c.getOutStream().writeQWord(playerName);
			c.getOutStream().writeByte(world);
			c.flushOutStream();
		}

	}

	public void removeAllWindows() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getPA().resetVariables();
			c.getOutStream().createFrame(219);
			c.flushOutStream();
		}

	}

	public void closeAllWindows() {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(219);
			c.flushOutStream();
			c.isBanking = false;
			c.getTradeAndDuel().declineTrade();
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34); // init item to smith screen
			c.outStream.writeWord(column); // Column Across Smith Screen
			c.outStream.writeByte(4); // Total Rows?
			c.outStream.writeDWord(slot); // Row Down The Smith Screen
			c.outStream.writeWord(id + 1); // item
			c.outStream.writeByte(amount); // how many there are?
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void walkableInterface(int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(208);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.flushOutStream();
		}

	}

	public int mapStatus = 0;

	public void sendFrame99(int state) { // used for disabling map
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (mapStatus != state) {
				mapStatus = state;
				c.getOutStream().createFrame(99);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}

		}
	}

	public void sendCrashFrame() { // used for crashing cheat clients
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(123);
				c.flushOutStream();
			}
		}
	}

	/**
	 * Reseting animations for everyone
	 **/

	public void frame1() {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client person = (Client) PlayerHandler.players[i];
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPA().requestUpdates();
						}
					}
				}

			}
		}
	}

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(16);
			c.getOutStream().writeByte(64);
			c.flushOutStream();

		}
	}

	public void createProjectile2(int x, int y, int offX, int offY, int angle,
			int speed, int gfxMoving, int startHeight, int endHeight,
			int lockon, int time, int slope) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(slope);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}

	}

	public void createProjectile3(int casterY, int casterX, int offsetY,
			int offsetX, int gfxMoving, int StartHeight, int endHeight,
			int speed, int AtkIndex) {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Client p = (Client) PlayerHandler.players[i];
				if (p.WithinDistance(c.absX, c.absY, p.absX, p.absY, 60)) {
					if (p.heightLevel == c.heightLevel) {
						if (PlayerHandler.players[i] != null
								&& !PlayerHandler.players[i].disconnected) {
							p.outStream.createFrame(85);
							p.outStream
									.writeByteC((casterY - (p.mapRegionY * 8)) - 2);
							p.outStream
									.writeByteC((casterX - (p.mapRegionX * 8)) - 3);
							p.outStream.createFrame(117);
							p.outStream.writeByte(50);
							p.outStream.writeByte(offsetY);
							p.outStream.writeByte(offsetX);
							p.outStream.writeWord(AtkIndex);
							p.outStream.writeWord(gfxMoving);
							p.outStream.writeByte(StartHeight);
							p.outStream.writeByte(endHeight);
							p.outStream.writeWord(51);
							p.outStream.writeWord(speed);
							p.outStream.writeByte(16);
							p.outStream.writeByte(64);
						}
					}
				}
			}
		}
	}

	public void createPlayersProjectile(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == c.heightLevel)
								person.getPA().createProjectile(x, y, offX,
										offY, angle, speed, gfxMoving,
										startHeight, endHeight, lockon, time);
						}
					}
				}

			}
		}
	}

	public void createPlayersProjectile2(int x, int y, int offX, int offY,
			int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile2(x, y, offX, offY,
									angle, speed, gfxMoving, startHeight,
									endHeight, lockon, time, slope);
						}
					}
				}
			}

		}
	}

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(4);
			c.getOutStream().writeByte(0);
			c.getOutStream().writeWord(id);
			c.getOutStream().writeByte(height);
			c.getOutStream().writeWord(time);
			c.flushOutStream();
		}

	}

	// creates gfx for everyone
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = PlayerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().stillGfx(id, x, y, height, time);
						}
					}
				}
			}

		}
	}

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face,
			int objectType) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}

	}

	public void checkObjectSpawn(int objectId, int objectX, int objectY,
			int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}

	}

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				c.getOutStream().createFrameVarSize(104);
				c.getOutStream().writeByteC(i);
				c.getOutStream().writeByteA(l);
				c.getOutStream().writeString(s);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
			}

		}
	}

	/**
	 * Open bank
	 **/
	public void openUpBank() {
		if (c.inNoBankNoSpawn()) {
			return;
		}
		if (c.requestPinDelete) {
			if (c.enterdBankpin) {
				c.requestPinDelete = false;
				c.sendMessage("[Notice] Your PIN pending deletion has been cancelled");
			} else if (c.lastLoginDate >= c.pinDeleteDateRequested
					&& c.hasBankpin) {
				c.hasBankpin = false;
				c.requestPinDelete = false;
				c.sendMessage("[Notice] Your PIN has been deleted. It is recommended "
						+ "to have one.");
			}
		}
		if (!c.enterdBankpin && c.hasBankpin) {
			c.getBankPin().openPin();
			return;
		}
		if (c.inTrade || c.tradeStatus == 1) {
			Client o = (Client) PlayerHandler.players[c.tradeWith];
			if (o != null) {
				o.getTradeAndDuel().declineTrade();
			}
		}
		if (c.duelStatus == 1) {
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o != null) {
				o.getTradeAndDuel().resetDuel();
			}
		}
		if (c.getOutStream() != null && c != null) {
			c.getItems().resetItems(5064);
			c.getItems().rearrangeBank();
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(23000);
			c.getOutStream().writeWord(5063);
			c.flushOutStream();
			c.isBanking = true;
		}
		// }
	}

	/**
	 * Private Messaging
	 **/
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = PlayerHandler.players[i1];
			if (p != null && p.isActive) {
				Client o = (Client) p;
				if (o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				for (int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = PlayerHandler.players[i2];
					if (p != null
							&& p.isActive
							&& Misc.playerNameToInt64(p.playerName) == c.friends[i]) {
						Client o = (Client) p;
						if (o != null) {
							if (c.playerRights >= 2
									|| p.privateChat == 0
									|| (p.privateChat == 1 && o
											.getPA()
											.isInPM(Misc
													.playerNameToInt64(c.playerName)))) {
								loadPM(c.friends[i], 1);
								pmLoaded = true;
							}
							break;
						}
					}
				}
				if (!pmLoaded) {
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = PlayerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					if (o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}

	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = PlayerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		if (o == null) {
			return;
		}
		long l = Misc.playerNameToInt64(PlayerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						if (o.getPA().isInPM(
								Misc.playerNameToInt64(c.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i] && c.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				if (l == c.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	public void sendFrame34a(int frame, int item, int slot, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId,
			int healType) {
		c.attackTimer = c.getCombat().getAttackDelay(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		if (c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if (c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the "
						+ c.getItems().getItemName(itemId).toLowerCase() + ".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}

	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {

		switch (spellId) {
		case 1162: // low alch
			if (System.currentTimeMillis() - c.alchDelay > 1000) {
				if (!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot)
						|| itemId == 995) {
					return;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						c.getShops().getItemShopValue(itemId) / 3);
				c.startAnimation(c.MAGIC_SPELLS[49][2]);
				c.gfx100(c.MAGIC_SPELLS[49][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - c.alchDelay > 2000) {
				if (!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (!c.getItems().playerHasItem(itemId, 1, slot)
						|| itemId == 995) {
					return;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995,
						(int) (c.getShops().getItemShopValue(itemId) * .75));
				c.startAnimation(c.MAGIC_SPELLS[50][2]);
				c.gfx100(c.MAGIC_SPELLS[50][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		case 1155:
			Obelisks(itemId);
			break;
		}
	}

	/**
	 * Dieing
	 **/
	public void applyDead() {
		c.isFullHelm = Item.isFullHelm(c.playerEquipment[c.playerHat]);
		c.isFullMask = Item.isFullMask(c.playerEquipment[c.playerHat]);
		c.isFullBody = Item.isFullBody(c.playerEquipment[c.playerChest]);
		c.getTradeAndDuel().stakedItems.clear();
		c.getPA().requestUpdates();
		c.respawnTimer = 15;
		c.isDead = false;
		c.freezeTimer = 1;
		if (c.inFightCaves()) {
			c.getPA().resetTzhaar();
		}

		if (c.duelStatus != 6) {
			// c.killerId = c.getCombat().getKillerId(c.playerId);
			c.killerId = findKiller();
			Client o = (Client) PlayerHandler.players[c.killerId];
			if (o != null) {
				if (c.killerId != c.playerId)
					if (c.inWild() && !c.inFunPk()) {
						if (!PlayerKilling.hostOnList(o, c.connectedFrom)) {
							PlayerKilling.addHostToList(o, c.connectedFrom);
							c.DC++;
							o.KC++;
							o.cStreak += 1;
							c.cStreak = 0;
							// o.getStreak().checkKillStreak();
							// o.getStreak().killedPlayer();
							o.pkPoints += 3;
							o.sendMessage("You have received @red@3 @bla@Pk Points after defeating @red@"
									+ Misc.optimizeText(c.playerName) + "!");
							c.getPA().loadQuests();
							o.getPA().loadQuests();
						} else {
							o.sendMessage("You have recently defeated @red@"
									+ Misc.optimizeText(c.playerName)
									+ ",@bla@ you didnt recieved pk points.");
						}
						
				if (o.playerRights == 4) {
				o.pkPoints += 4; 
				o.sendMessage("You received <col=255>one bonus PKP<col=0> for being a @gre@Premium@bla@." );
			}
				
				if (o.playerRights == 5) {
				o.pkPoints += 5; 
				o.sendMessage("You received <col=255>two bonus PKP<col=0> for being a @red@Sponsor@bla@." );
			}
				
				if (o.playerRights == 6)  {
				o.pkPoints += 6;
				o.sendMessage("You received <col=255>three bonus PKP<col=0> for being a @pur@VIP@bla@."); 
					
			}	
		}
				/*
				 * for (int j = 0; j < Server.playerHandler.players.length; j++)
				 * { if (Server.playerHandler.players[j] != null) { Client c2 =
				 * (Client)Server.playerHandler.players[j];
				 * c2.sendMessage("<col=16711680>[PVP] <col=0>"
				 * +Misc.optimizeText
				 * (o.playerName)+" has defeated "+Misc.optimizeText
				 * (c.playerName)+"!"); } }
				 */
				/*
				 * if (o.playerRights == 4 || o.playerRights == 5) { o.pkPoints
				 * += 4; o.sendMessage(
				 * "You received <col=255>one bonus PKP<col=0> for being a Donator."
				 * ); } if (o.playerRights == 6) { o.pkPoints += 5;
				 * o.sendMessage(
				 * "You received <col=255>two bonus PKP<col=0> for being a @cya@VIP."
				 * ); }
				 * 
				 * 
				 * if (o.KC == 9) { o.KC ++; o.sendMessage(
				 * "You now have 10 kills! You recieve an <col=255>achievement point."
				 * ); o.aPoints += 1; } else if (o.KC == 24) { o.KC++;
				 * o.sendMessage(
				 * "You now have 25 kills! You recieve 2 <col=255>achievement points."
				 * ); o.aPoints += 2; } else if (o.KC == 49) { o.KC++;
				 * o.sendMessage(
				 * "You now have 50 kills! You recieve 2 <col=255>achievement points."
				 * ); o.aPoints += 2; } else if (o.KC == 149) { o.KC++;
				 * o.sendMessage(
				 * "You now have 150 kills! You recieve 3 <col=255>achievement points."
				 * ); o.aPoints += 3; } else if (o.KC == 299) { o.KC++;
				 * o.sendMessage(
				 * "You now have 300 kills! You recieve 5 <col=255>achievement points."
				 * ); o.aPoints += 5; for (int j = 0; j <
				 * Server.playerHandler.players.length; j++) { if
				 * (Server.playerHandler.players[j] != null) { Client c2 =
				 * (Client)Server.playerHandler.players[j];
				 * c2.sendMessage("<col=16711680>[PVP] <col=0>"
				 * +Misc.optimizeText
				 * (o.playerName)+" has reached 300 Player kills!"); } }
				 * 
				 * } else if (o.KC == 499) { o.KC++; o.sendMessage(
				 * "You now have 500 kills! You recieve 7 <col=255>achievement points."
				 * ); o.aPoints += 7; for (int j = 0; j <
				 * Server.playerHandler.players.length; j++) { if
				 * (Server.playerHandler.players[j] != null) { Client c2 =
				 * (Client)Server.playerHandler.players[j];
				 * c2.sendMessage("<col=16711680>[PVP] <col=0>"
				 * +Misc.optimizeText
				 * (o.playerName)+" has reached 500 Player kills!"); } } }
				 */
				c.playerKilled = c.playerId;
				if (o.duelStatus == 5) {
					o.duelStatus++;
				}
			}
		}
		c.faceUpdate(0);
		EventManager.getSingleton().addEvent(new Event() {
			@Override
			public void execute(EventContainer b) {
				c.npcIndex = 0;
				c.playerIndex = 0;
				b.stop();
			}
		}, 2500);
		c.stopMovement();
		if (c.duelStatus <= 4) {
			c.getTradeAndDuel().stakedItems.clear();
			c.sendMessage("Oh dear you are dead!");
		} else if (c.duelStatus != 6) {
			Client o = (Client) PlayerHandler.players[c.killerId];
			c.getTradeAndDuel().stakedItems.clear();
			c.sendMessage("You have lost the duel!");
			PlayerSave.saveGame(o);
			PlayerSave.saveGame(c);
		}
		PlayerSave.saveGame(c);
		resetDamageDone();
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		c.tradeResetNeeded = true;
		removeAllWindows();
		closeAllWindows();
	}

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;
			}
		}
	}

	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;
	}

	public void handleStatus(int i, int i2, int i3) {
		if (i == 1)
			c.getItems().addItem(i2, i3);
		else if (i == 2) {
			c.playerXP[i2] = c.getPA().getXPForLevel(i3) + 5;
			c.playerLevel[i2] = c.getPA().getLevelForXP(c.playerXP[i2]);
		}
	}

	public void resetFollowers() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].followId == c.playerId) {
					Client c = (Client) PlayerHandler.players[j];
					c.getPA().resetFollow();
				}
			}
		}
	}

	public void giveLife() {
		c.isFullHelm = Item.isFullHelm(c.playerEquipment[c.playerHat]);
		c.isFullMask = Item.isFullMask(c.playerEquipment[c.playerHat]);
		c.isFullBody = Item.isFullBody(c.playerEquipment[c.playerChest]);
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 1;
		if (c.duelStatus <= 4 && !c.getPA().inPitsWait() && !c.inPits
				&& !c.inFightCaves() && !c.inPcGame() && !c.isInPc()
				&& !c.inFunPk()) {
			c.getItems().resetKeepItems();
			if (c.playerRights != 3) {
				if (!c.isSkulled) { // what items to keep
					c.getItems().keepItem(0, true);
					c.getItems().keepItem(1, true);
					c.getItems().keepItem(2, true);
				}
				if (c.prayerActive[10]
						&& System.currentTimeMillis() - c.lastProtItem > 700) {
					c.getItems().keepItem(3, true);
				}
				c.getItems().dropAllItems(); // drop all items
				c.getItems().deleteAllItems(); // delete all items

				if (!c.isSkulled) { // add the kept items once we finish
					// deleting and dropping them
					for (int i1 = 0; i1 < 3; i1++) {
						if (c.itemKeptId[i1] > 0) {
							c.getItems().addItem(c.itemKeptId[i1], 1);
						}
					}
				}
				if (c.prayerActive[10]) {
					if (c.itemKeptId[3] > 0) {
						c.getItems().addItem(c.itemKeptId[3], 1);
					}
				}
			}
			c.getItems().resetKeepItems();
		}
		c.getCombat().resetPrayers();
		for (int i = 0; i < 20; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		if (c.inPits) {
			Server.fightPits.removePlayerFromPits(c.playerId);
			c.pitsStatus = 1;
		} else if (c.isInPc()) {
			PestControl.handleDeath(c);
		} else if (PestControl.isInGame(c) || c.inPcGame()) {
			PestControl.handleDeath(c);
		} else if (c.duelStatus <= 4) { // if we are not in a duel repawn to
			// wildy
			movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
			c.isSkulled = false;
			c.skullTimer = 0;
			c.attackedPlayers.clear();
		} else if (c.inFightCaves()) {
			movePlayer(Config.FIGHT_CAVE_RESPAWN_X, Config.FIGHT_CAVE_RESPAWN_Y, 0);
		} else { // we are in a duel, respawn outside of arena
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o != null) {
				o.getPA().createPlayerHints(10, -1);
				if (o.duelStatus == 6) {
					o.getTradeAndDuel().duelVictory();
				}
			}
			c.getPA().movePlayer(
					Config.DUELING_RESPAWN_X
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			o.getPA().movePlayer(
					Config.DUELING_RESPAWN_X
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y
							+ (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			if (c.duelStatus != 6) { // if we have won but have died, don't
				// reset the duel status.
				c.getTradeAndDuel().resetDuel();
			}
		}
		// PlayerSaving.getSingleton().requestSave(c.playerId);
		PlayerSave.saveGame(c);
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		c.startAnimation(65535);
		frame1();
		resetTb();
		c.cStreak = 0;
		c.redSkull = false;
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.cStreak = 0;
		c.damageTaken = new int[Config.MAX_PLAYERS];
		c.getPA().requestUpdates();
		removeAllWindows();
		closeAllWindows();
		c.tradeResetNeeded = true;
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (c.newLocation) {
		case 1:
			sendFrame99(2);
			movePlayer(3578, 9706, -1);
			break;
		case 2:
			sendFrame99(2);
			movePlayer(3568, 9683, -1);
			break;
		case 3:
			sendFrame99(2);
			movePlayer(3557, 9703, -1);
			break;
		case 4:
			sendFrame99(2);
			movePlayer(3556, 9718, -1);
			break;
		case 5:
			sendFrame99(2);
			movePlayer(3534, 9704, -1);
			break;
		case 6:
			sendFrame99(2);
			movePlayer(3546, 9684, -1);
			break;
		}
		c.newLocation = 0;
	}

	/**
	 * Teleporting
	 **/
	public void spellTeleport(int x, int y, int height) {
		c.getPA().startTeleport(x, y, height,
				c.playerMagicBook == 1 ? "ancient" : "modern");
	}

	public void startMovement(int x, int y, int height) {

		if (c.inPits || inPitsWait()) {
			c.sendMessage("You can't teleport during fight pits!");
			return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (PestControl.isInGame(c)) {
			c.sendMessage("You can't teleport from a Pest Control Game!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;

		}

	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		InterfaceEvent.getInstance().startInterfaceEvent(c);
		c.canDepositAll = false;

		if (c.inPits || inPitsWait()) {
			c.sendMessage("You can't teleport during fight pits!");
			return;
		}
		if (PestControl.isInGame(c)) {
			c.sendMessage("You can't teleport from a Pest Control Game!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't teleport in a trade.");
			return;
		}

		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.isInJail()) {
			c.sendMessage("You can't teleport out of jail!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of jail!");
			return;
		}
		if ((c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL)) {
			c.sendMessage("@red@You can't teleport above level "
					+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (c.underAttackBy > 0 || c.underAttackBy2 > 0) {
			}
			if (teleportType.equalsIgnoreCase("modern")) {
				c.startAnimation(714);
				c.teleTimer = 11;
				c.teleGfx = 308;
				c.teleEndAnimation = 715;
			}

			if (teleportType.equalsIgnoreCase("ancient")) {
				c.startAnimation(1979);
				c.teleGfx = 0;
				c.teleTimer = 9;
				c.teleEndAnimation = 0;
				c.gfx0(392);
			}

		}
	}

	public void startTeleport2(int x, int y, int height) {
		InterfaceEvent.getInstance().startInterfaceEvent(c);
		c.canDepositAll = false;

		if (c.inPits || inPitsWait()) {
			c.sendMessage("You can't teleport during fight pits!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't teleport in a trade.");
			return;
		}
		if (PestControl.isInGame(c)) {
			c.sendMessage("You can't teleport from a Pest Control Game!");
			return;
		}

		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.stopMovement();
			removeAllWindows();
			c.usingSpecial = false;
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;
			c.canDepositAll = false;
		}
	}

	public void processTeleport() {
		InterfaceEvent.getInstance().startInterfaceEvent(c);
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if (c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}

	public void movePlayer(int x, int y, int h) {
		InterfaceEvent.getInstance().startInterfaceEvent(c);
		c.homeTimer = 0;
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
		c.canDepositAll = false;
	}

	/**
	 * Following
	 **/

	public String checkTimeOfDay() {
		Calendar cal = new GregorianCalendar();
		int TIME_OF_DAY = cal.get(Calendar.AM_PM);
		if (TIME_OF_DAY > 0)
			return "PM";
		else
			return "AM";
	}

	public void checkDateAndTime() {
		Calendar cal = new GregorianCalendar();

		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);

		String day = "";
		String month = "";
		String hour = "";
		String minute = "";
		String second = "";

		if (DAY < 10)
			day = "0" + DAY;
		else
			day = "" + DAY;
		if (MONTH < 10)
			month = "0" + MONTH;
		else
			month = "" + MONTH;
		if (HOUR < 10)
			hour = "0" + HOUR;
		else
			hour = "" + HOUR;
		if (MIN < 10)
			minute = "0" + MIN;
		else
			minute = "" + MIN;
		if (SECOND < 10)
			second = "0" + SECOND;
		else
			second = "" + SECOND;

		c.date = day + "/" + month + "/" + YEAR;
		c.currentTime = hour + ":" + minute + ":" + second;
	}

	public void chatReveal(String data) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				if (c2.playerRights == 3) {
					c2.sendMessage("=" + c.playerName + ": " + data + "");
				}
			}
		}
	}

	public void writeChatLog(String data) {
		if (c.warnings == 0) {
			return;
		}
		checkDateAndTime();
		String filePath = "./Data/ChatLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " "
					+ checkTimeOfDay() + "]: " + "[" + c.connectedFrom + "]: "
					+ "" + data + " ");
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void followPlayer() {
		if (PlayerHandler.players[c.followId] == null
				|| PlayerHandler.players[c.followId].isDead) {
			c.followId = 0;
			return;
		}
		if (c.getPA().inPitsWait()) {
			return;
		}
		if (c.isInHighRiskPK() && (c.underAttackBy > 0 || c.underAttackBy2 > 0)) {
			return;
		}
		if (c.isInJail()) {
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = PlayerHandler.players[c.followId].getX();
		int otherY = PlayerHandler.players[c.followId].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId > 0))
				&& bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId + 32768);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				playerWalk(0, -1);
				break;
			case 1:
				playerWalk(0, 1);
				break;
			case 2:
				playerWalk(1, 0);
				break;
			case 3:
				playerWalk(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(
						0,
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(
						0,
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX - 1)
								+ getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX - 1)
								+ getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY + 1));
			}
		}
		c.faceUpdate(c.followId + 32768);
	}

	public void followNpc() {
		if (NPCHandler.npcs[c.followId2] == null
				|| NPCHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = NPCHandler.npcs[c.followId2].getX();
		int otherY = NPCHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 10);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(),
				c.getY(), 10);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0))
				&& bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				playerWalk(0, -1);
				break;
			case 1:
				playerWalk(0, 1);
				break;
			case 2:
				playerWalk(1, 0);
				break;
			case 3:
				playerWalk(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(
						0,
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(
						0,
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX - 1)
								+ getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1)
								+ getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX - 1)
								+ getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(
						getMove(c.getX(), otherX + 1)
								+ getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1)
								+ getMove(c.getY(), otherY - 1));
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(0, getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY + 1));
			}
		}
		c.faceUpdate(c.followId2);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}

	public void walkTo(int i, int j) {
		c.homeTimer = 0;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		c.homeTimer = 0;
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = c.getX() + xMove;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + yMove;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		c.homeTimer = 0;
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		c.homeTimer = 0;
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753
				&& c.playerEquipment[c.playerChest] == 4757
				&& c.playerEquipment[c.playerLegs] == 4759
				&& c.playerEquipment[c.playerWeapon] == 4755;
	}

	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724
				&& c.playerEquipment[c.playerChest] == 4728
				&& c.playerEquipment[c.playerLegs] == 4730
				&& c.playerEquipment[c.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon])
						.toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void Obelisks(int id) {
		if (!c.getItems().playerHasItem(id)) {
			c.getItems().addItem(id, 1);
		}
	}

	public void sendStatement(String s) {
		sendFrame126(s, 357);
		sendFrame126("Click here to continue", 358);
		sendFrame164(356);
	}

	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0])
				+ getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4])
				+ getLevelForXP(c.playerXP[5]) + getLevelForXP(c.playerXP[6])
				+ getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10])
				+ getLevelForXP(c.playerXP[11]) + getLevelForXP(c.playerXP[12])
				+ getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16])
				+ getLevelForXP(c.playerXP[17]) + getLevelForXP(c.playerXP[18])
				+ getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20]));
		sendFrame126("Total Lvl: " + totalLevel, 3984);
		switch (skill) {
		case 0:
			sendFrame126("Congratulations, you just advanced an attack level!",
					6248);
			sendFrame126("Your attack level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6249);
			c.sendMessage("Congratulations, you just advanced an attack level.");
			sendFrame164(6247);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Attack@bla@.");
					}
				}
			}
			break;

		case 1:
			sendFrame126("Congratulations, you just advanced a defence level!",
					6254);
			sendFrame126("Your defence level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6255);
			c.sendMessage("Congratulations, you just advanced a defence level.");
			sendFrame164(6253);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Defence@bla@.");
					}
				}
			}
			break;

		case 2:
			sendFrame126(
					"Congratulations, you just advanced a strength level!",
					6207);
			sendFrame126("Your strength level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6208);
			c.sendMessage("Congratulations, you just advanced a strength level.");
			sendFrame164(6206);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Strength@bla@.");
					}
				}
			}
			break;

		case 3:
			sendFrame126(
					"Congratulations, you just advanced a hitpoints level!",
					6217);
			sendFrame126("Your hitpoints level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6218);
			c.sendMessage("Congratulations, you just advanced a hitpoints level.");
			sendFrame164(6216);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Hitpoints@bla@.");
					}
				}
			}
			break;

		case 4:
			c.sendMessage("Congratulations, you just advanced a ranging level.");
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Ranged@bla@.");
					}
				}
			}
			break;

		case 5:
			sendFrame126("Congratulations, you just advanced a prayer level!",
					6243);
			sendFrame126("Your prayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6244);
			c.sendMessage("Congratulations, you just advanced a prayer level.");
			sendFrame164(6242);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Prayer@bla@.");
					}
				}
			}
			break;

		case 6:
			sendFrame126("Congratulations, you just advanced a magic level!",
					6212);
			sendFrame126("Your magic level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6213);
			c.sendMessage("Congratulations, you just advanced a magic level.");
			sendFrame164(6211);
			if (getLevelForXP(c.playerXP[skill]) == 99) {

				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Magic@bla@.");
					}
				}
			}
			break;

		case 7:
			sendFrame126("Congratulations, you just advanced a cooking level!",
					6227);
			sendFrame126("Your cooking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6228);
			c.sendMessage("Congratulations, you just advanced a cooking level.");
			sendFrame164(6226);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Cooking@bla@.");
					}
				}
			}
			break;

		case 8:
			sendFrame126(
					"Congratulations, you just advanced a woodcutting level!",
					4273);
			sendFrame126("Your woodcutting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4274);
			c.sendMessage("Congratulations, you just advanced a woodcutting level.");
			sendFrame164(4272);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Woodcutting@bla@.");
					}
				}
			}
			break;

		case 9:
			sendFrame126(
					"Congratulations, you just advanced a fletching level!",
					6232);
			sendFrame126("Your fletching level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6233);
			c.sendMessage("Congratulations, you just advanced a fletching level.");
			sendFrame164(6231);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Fletching@bla@.");
					}
				}
			}
			break;

		case 10:
			sendFrame126("Congratulations, you just advanced a fishing level!",
					6259);
			sendFrame126("Your fishing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6260);
			c.sendMessage("Congratulations, you just advanced a fishing level.");
			sendFrame164(6258);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Fishing@bla@.");
					}
				}
			}
			break;

		case 11:
			sendFrame126(
					"Congratulations, you just advanced a fire making level!",
					4283);
			sendFrame126("Your firemaking level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4284);
			c.sendMessage("Congratulations, you just advanced a fire making level.");
			sendFrame164(4282);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Firemaking@bla@.");
					}
				}
			}
			break;

		case 12:
			sendFrame126(
					"Congratulations, you just advanced a crafting level!",
					6264);
			sendFrame126("Your crafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6265);
			c.sendMessage("Congratulations, you just advanced a crafting level.");
			sendFrame164(6263);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Crafting@bla@.");
					}
				}
			}
			break;

		case 13:
			sendFrame126(
					"Congratulations, you just advanced a smithing level!",
					6222);
			sendFrame126("Your smithing level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6223);
			c.sendMessage("Congratulations, you just advanced a smithing level.");
			sendFrame164(6221);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Smithing@bla@.");
					}
				}
			}
			break;

		case 14:
			sendFrame126("Congratulations, you just advanced a mining level!",
					4417);
			sendFrame126("Your mining level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4438);
			c.sendMessage("Congratulations, you just advanced a mining level.");
			sendFrame164(4416);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Mining@bla@.");
					}
				}
			}
			break;

		case 15:
			sendFrame126(
					"Congratulations, you just advanced a herblore level!",
					6238);
			sendFrame126("Your herblore level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 6239);
			c.sendMessage("Congratulations, you just advanced a herblore level.");
			sendFrame164(6237);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Herblore@bla@.");
					}
				}
			}
			break;

		case 16:
			sendFrame126("Congratulations, you just advanced a agility level!",
					4278);
			sendFrame126("Your agility level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4279);
			c.sendMessage("Congratulations, you just advanced an agility level.");
			sendFrame164(4277);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Agility@bla@.");
					}
				}
			}
			break;

		case 17:
			sendFrame126(
					"Congratulations, you just advanced a thieving level!",
					4263);
			sendFrame126("Your theiving level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4264);
			c.sendMessage("Congratulations, you just advanced a thieving level.");
			sendFrame164(4261);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Thieving@bla@.");
					}
				}
			}
			break;

		case 18:
			sendFrame126("Congratulations, you just advanced a slayer level!",
					12123);
			sendFrame126("Your slayer level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 12124);
			c.sendMessage("Congratulations, you just advanced a slayer level.");
			sendFrame164(12122);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@red@ @bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Slayer@bla@.");
					}
				}
			}
			break;

		case 20:
			sendFrame126(
					"Congratulations, you just advanced a runecrafting level!",
					4268);
			sendFrame126("Your runecrafting level is now "
					+ getLevelForXP(c.playerXP[skill]) + ".", 4269);
			c.sendMessage("Congratulations, you just advanced a runecrafting level.");
			sendFrame164(4267);
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.lvlPoints += 5;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
					if (PlayerHandler.players[j] != null) {
						Client c2 = (Client) PlayerHandler.players[j];
						c2.sendMessage("[@red@PLAYER ACHIEVEMENT@bla@]@bla@Player @blu@"
								+ c.playerName
								+ " @bla@just advanced to 99 Runecrafting@bla@!");
					}
				}
			}
			break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}

	public void refreshSkill(int i) {
		c.calcCombat();
		switch (i) {
		case 0:
			sendFrame126("" + c.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1)
					+ "", 4045);
			break;

		case 1:
			sendFrame126("" + c.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1)
					+ "", 4057);
			break;

		case 2:
			sendFrame126("" + c.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1)
					+ "", 4051);
			break;

		case 3:
			sendFrame126("" + c.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
			sendFrame126("" + c.playerXP[3] + "", 4080);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1)
					+ "", 4081);
			break;

		case 4:
			sendFrame126("" + c.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1)
					+ "", 4063);
			break;

		case 5:
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1)
					+ "", 4069);
			sendFrame126("" + c.playerLevel[5] + "/"
					+ getLevelForXP(c.playerXP[5]) + "", 687);// Prayer frame
			break;

		case 6:
			sendFrame126("" + c.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 4074);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1)
					+ "", 4075);
			break;

		case 7:
			sendFrame126("" + c.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 4134);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[7]) + 1)
					+ "", 4135);
			break;

		case 8:
			sendFrame126("" + c.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 4146);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1)
					+ "", 4147);
			break;

		case 9:
			sendFrame126("" + c.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 4110);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1)
					+ "", 4111);
			break;

		case 10:
			sendFrame126("" + c.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 4128);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1)
					+ "", 4129);
			break;

		case 11:
			sendFrame126("" + c.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 4140);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1)
					+ "", 4141);
			break;

		case 12:
			sendFrame126("" + c.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 4104);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1)
					+ "", 4105);
			break;

		case 13:
			sendFrame126("" + c.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 4122);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1)
					+ "", 4123);
			break;

		case 14:
			sendFrame126("" + c.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 4116);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1)
					+ "", 4117);
			break;

		case 15:
			sendFrame126("" + c.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 4092);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1)
					+ "", 4093);
			break;

		case 16:
			sendFrame126("" + c.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 4086);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1)
					+ "", 4087);
			break;

		case 17:
			sendFrame126("" + c.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 4098);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1)
					+ "", 4099);
			break;

		case 18:
			sendFrame126("" + c.playerLevel[18] + "", 12166);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 12167);
			sendFrame126("" + c.playerXP[18] + "", 12171);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1)
					+ "", 12172);
			break;

		case 19:
			sendFrame126("" + c.playerLevel[19] + "", 13926);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 13927);
			sendFrame126("" + c.playerXP[19] + "", 13921);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1)
					+ "", 13922);
			break;

		case 20:
			sendFrame126("" + c.playerLevel[20] + "", 4152);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 4153);
			sendFrame126("" + c.playerXP[20] + "", 4157);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1)
					+ "", 4158);
			break;
		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	public boolean addSkillXP(int amount, int skill) {
		if (c.expLock == true) {
			return false;
		}
		if (amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000) {
			if (c.playerXP[skill] > 200000000) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		if (skill == 11) {
			amount *= 20;
		}
		if (skill == 12) {
			amount *= 20;
		}
		/*if(c.lampStart = true) {
				c.playerXP[skill] += (int) ((double) amount * 2);
			}*/
			
		if (c.playerEquipment[c.playerRing] == 11014) // your ring ID here
		{
			amount *= Config.SERVER_EXP_BONUS * 2;
		} else {
			amount *= Config.SERVER_EXP_BONUS;
		}
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill])
					&& skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public void resetBarrows() {
		c.barrowsNpcs[0][1] = 0;
		c.barrowsNpcs[1][1] = 0;
		c.barrowsNpcs[2][1] = 0;
		c.barrowsNpcs[3][1] = 0;
		c.barrowsNpcs[4][1] = 0;
		c.barrowsNpcs[5][1] = 0;
		c.barrowsKillCount = 0;
		c.randomCoffin = Misc.random(3) + 1;
	}

	// public static int Barrows[] = {4708, 4710, 4712, 4714, 4716, 4718, 4720,
	// 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749,
	// 4751, 4753, 4755, 4757, 4759};
	public static int Runes[] = { 4740, 558, 560, 565 };
	public static int Pots[] = {};

	/*
	 * public int randomBarrows() { return
	 * Barrows[(int)(Math.random()*Barrows.length)]; }
	 */

	public int randomRunes() {
		return Runes[(int) (Math.random() * Runes.length)];
	}

	public int randomPots() {
		return Pots[(int) (Math.random() * Pots.length)];
	}

	public static int Clue1[] = {};

	public int randomClue1() {
		return Clue1[(int) (Math.random() * Clue1.length)];
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		// synchronized(c) {
		c.outStream.createFrame(254);
		c.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			c.outStream.writeWord(j);
			c.outStream.writeWord(k);
			c.outStream.writeByte(l);
		} else {
			c.outStream.writeWord(k);
			c.outStream.writeWord(l);
			c.outStream.writeByte(j);
		}

	}

	public void createPlayersObjectAnim(int X, int Y, int animationID,
			int tileObjectType, int orientation) {
		try {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(Y - (c.mapRegionY * 8));
			c.getOutStream().writeByteC(X - (c.mapRegionX * 8));
			int x = 0;
			int y = 0;
			c.getOutStream().createFrame(160);
			c.getOutStream().writeByteS(((x & 7) << 4) + (y & 7));// tiles away
																	// - could
																	// just send
																	// 0
			c.getOutStream().writeByteS(
					(tileObjectType << 2) + (orientation & 3));
			c.getOutStream().writeWordA(animationID);// animation id
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void objectAnim(int X, int Y, int animationID, int tileObjectType,
			int orientation) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client players = (Client) p;
				if (players.distanceToPoint(X, Y) <= 25) {
					players.getPA().createPlayersObjectAnim(X, Y, animationID,
							tileObjectType, orientation);
				}
			}
		}
	}

	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	private void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove(2638, 4688);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
	}

	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
		c.sendMessage("You rub the amulet...");
		c.usingGlory = true;
	}

	public void useCharge() {
		int[][] glory = { { 1712, 1710, 3 }, { 1710, 1708, 2 },
				{ 1708, 1706, 1 }, { 1706, 1704, 1 } };
		for (int i = 0; i < glory.length; i++) {
			if (c.itemUsing == glory[i][0]) {
				if (c.isOperate) {
					c.playerEquipment[c.playerAmulet] = glory[i][1];
				} else {
					c.getItems().deleteItem(glory[i][0], 1);
					c.getItems().addItem(glory[i][1], 1);
				}
				if (glory[i][2] > 1) {
					c.sendMessage("Your amulet has " + glory[i][2]
							+ " charges left.");
				} else {
					c.sendMessage("Your amulet has " + glory[i][2]
							+ " charge left.");
				}
			}
		}
		c.getItems().updateSlot(c.playerAmulet);
		c.isOperate = false;
		c.itemUsing = -1;
	}

	public void resetVariables() {
		c.getCrafting().resetCrafting();
		c.usingGlory = false;
	}

	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175
				&& c.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	public int antiFire() {
		int anti = 0;
		if (c.antiFirePot)
			anti = 2;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12]
				|| c.playerEquipment[c.playerShield] == 11283)
			anti = 2;
		return anti;
	}

	public int extendedAntiFire() {
		int anti = 0;
		if (c.extendedAntiFirePot)
			anti = 2;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12]
				|| c.playerEquipment[c.playerShield] == 11283)
			anti = 2;
		return anti;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 },
				{ 2402, 5 }, { 746, 5 }, { 4151, 150 }, { 565, 100000 },
				{ 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(
					itemsToCheck[j][0]))
				return true;
		}
		return false;
	}

	/*
	 * Vengeance
	 */
	public void castVeng() {
		if (c.playerLevel[6] < 94) {
			c.sendMessage("You need a magic level of 94 to cast this spell.");
			return;
		}
		if (c.playerLevel[1] < 40) {
			c.sendMessage("You need a defence level of 40 to cast this spell.");
			return;
		}
		if (!c.getItems().playerHasItem(9075, 4)
				|| !c.getItems().playerHasItem(557, 10)
				|| !c.getItems().playerHasItem(560, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		if (c.duelRule[4]) {
			c.sendMessage("You can't cast this spell because magic has been disabled.");
			return;
		}
		c.startAnimation(4410);
		c.gfx100(726);
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);
		c.getItems().deleteItem2(560, 2);
		addSkillXP(10000, 6);
		refreshSkill(6);
		c.vengOn = true;
		c.sendMessage(":vengeancecasted:");
		c.lastCast = System.currentTimeMillis();
	}

	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557, 10)
					&& c.getItems().playerHasItem(9075, 4)
					&& c.getItems().playerHasItem(560, 2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems()
						.deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	public void sendFrame34P2(int item, int slot, int frame, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	public int getWearingAmount() {
		int count = 0;
		for (int j = 0; j < c.playerEquipment.length; j++) {
			if (c.playerEquipment[j] > 0)
				count++;
		}
		return count;
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 11284:
		case 11283:
			if (c.playerIndex > 0) {
				MeleeRequirements.handleDfs(c);
			} else if (c.npcIndex > 0) {
				MeleeRequirements.handleDfsNPC(c);
			}
			break;
		case 3758:
			MeleeRequirements.handleFremmyShield(c);
			break;

		case 1712:
		case 1710:
		case 1708:
		case 1706:
			c.isOperate = true;
			c.itemUsing = itemId;
			Teles.AOG(c);
			break;

		case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.isOperate2 = true;
			c.itemUsing = itemId;
			Teles.ROD(c);
			break;

		case 3853:
		case 3855:
		case 3857:
		case 3859:
		case 3861:
		case 3863:
		case 3865:
		case 3867:
			c.isOperate = true;
			c.itemUsing = itemId;
			Teles.GN(c);
			break;
		}
	}

	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		// moveCheck(x,y);
		c.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}

	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX,
					PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY + 9400,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY,
							PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}

	public void resetTzhaar() {
		c.waveId = -1;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		//c.getPA().movePlayer(2438, 5168, 0);
	}

	public void enterCaves() {
		c.getPA().movePlayer(2413, 5117, c.playerId * 4);
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer e) {
				Server.fightCaves
						.spawnNextWave((Client) PlayerHandler.players[c.playerId]);
				e.stop();
			}

			@Override
			public void stop() {
				// throw new
				// UnsupportedOperationException("Not supported yet.");
			}
		}, 20);
	}

	public void stunEnemy(int i) {
		c.lastStun = System.currentTimeMillis();
		c.gfx100(254);
	}

	public void loadQuests() {
		//sendFrame126("@or1@   Player Statistics:", 29155);
	}

	/** Witches Potion **/
	public void witchFinish() {
		c.WitchesPotion = 3;
		c.questPoints++;
		c.getPA().addSkillXP(325000, 6);
		c.getPA().showInterface(297);
		c.getPA().showInterface(12140);
		c.getPA().sendFrame126("You have completed: Witches Potion", 12144);
		c.getPA().sendFrame126("325,000 Magic Experience &", 12150);
		c.getPA().sendFrame126("1 Quest point", 12151);
		c.getPA().sendFrame126("", 12152);
		c.getPA().sendFrame126("", 12153);
		c.getPA().sendFrame126("", 12154);
		c.getPA().sendFrame126("", 12155);
		c.getPA().sendFrame126("@gre@Witches Potion", 29179);
	}

	public void cookFinish() {
		c.cookAss = 3;
		c.questPoints++;
		c.getPA().addSkillXP(15000, 7);
		c.getPA().showInterface(297);
		c.getPA().showInterface(12140);
		c.getPA().sendFrame126("You have completed: Cook's Assistant", 12144);
		c.getPA().sendFrame126("15,000 Cooking Experience &", 12150);
		c.getPA().sendFrame126("1 Quest point", 12151);
		c.getPA().sendFrame126("", 12152);
		c.getPA().sendFrame126("", 12153);
		c.getPA().sendFrame126("", 12154);
		c.getPA().sendFrame126("", 12155);
		c.getPA().sendFrame126("@gre@Cook's Assistant", 29178);
	}

	public static int voterewards[] = { 1149, 1187, 1187, 2497, 2503, 4153,
			5698, 6924, 6922, 6920, 6918, 1079, 1093, 1127, 2615, 2617, 2623,
			2625, 3476, 3477, 1163, 2619, 2627, 1201, 2621, 2629, 7336, 7339,
			7341, 7343, 7348, 7354, 7360, 1712, 10363, 13081, 1149, 1187, 1187,
			2497, 2503, 4153, 5698, 6924, 6922, 6920, 6918, 1079, 1093, 1127,
			2615, 2617, 2623, 2625, 3476, 3477, 1163, 2619, 2627, 1201, 2621,
			2629, 7336, 7339, 7341, 7343, 7348, 7354, 7360, 1712, 10363, 1149,
			1187, 1187, 2497, 2503, 4153, 5698, 6924, 6922, 6920, 6918, 1079,
			1093, 1127, 2615, 2617, 2623, 2625, 3476, 3477, 1163, 2619, 2627,
			1201, 2621, 2629, 7336, 7339, 7341, 7343, 7348, 7354, 7360, 1712,
			10363, 1149, 1187, 1187, 2497, 2503, 4153, 5698, 6924, 6922, 6920,
			6918, 1079, 1093, 1127, 2615, 2617, 2623, 2625, 3476, 3477, 1163,
			2619, 2627, 1201, 2621, 2629, 7336, 7339, 7341, 7343, 7348, 7354,
			7360, 1712, 10363, 1149, 1187, 1187, 2497, 2503, 4153, 5698, 6924,
			6922, 6920, 6918, 1079, 1093, 1127, 2615, 2617, 2623, 2625, 3476,
			3477, 1163, 2619, 2627, 1201, 2621, 2629, 7336, 7339, 7341, 7343,
			7348, 7354, 7360, 1712, 10363, 13085, 1149, 1187, 1187, 2497, 2503,
			4153, 5698, 6924, 6922, 6920, 6918, 1079, 1093, 1127, 2615, 2617,
			2623, 2625, 3476, 3477, 1163, 2619, 2627, 1201, 2621, 2629, 7336,
			7339, 7341, 7343, 7348, 7354, 7360, 1712, 10363, };

	public int randomVotestuff() {
		return voterewards[(int) (Math.random() * voterewards.length)];
	}

	public long getTotalXp() {
		long totalxp = 0;
		for (int i = 0; i < 25; i++) {

			totalxp += c.playerXP[i];
		}
		return totalxp;
	}

	public void otherInv(Client c, Client o) {
		if (o == c || o == null || c == null)
			return;
		int[] backupItems = c.playerItems;
		int[] backupItemsN = c.playerItemsN;
		c.playerItems = o.playerItems;
		c.playerItemsN = o.playerItemsN;
		c.getItems().resetItems(3214);
		c.playerItems = backupItems;
		c.playerItemsN = backupItemsN;
	}

	public void drainPray(int i) {
		int prayerDrained = Misc.random(10) + 5;
		if (c.playerLevel[5] < prayerDrained)
			c.playerLevel[5] = 0;
		else
			c.playerLevel[5] -= prayerDrained;
		c.getPA().refreshSkill(5);
		c.sendMessage("Venenatis drains your prayer.");
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.poisonDamage = damage;
			c.lastPoison = System.currentTimeMillis();
			c.dealDamage(6);
			c.getPA().refreshSkill(3);
			c.handleHitMask(6);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.isDead || c.poisonDamage == 0) {
						if (c.poisonDamage == 0) {
							c.sendMessage("The poison has worn off.");
						}
						container.stop();
					}
					if (System.currentTimeMillis() - c.lastPoison > 18000
							&& c.poisonDamage > 0) {
						c.lastPoison = System.currentTimeMillis();
						c.dealDamage(6);
						c.getPA().refreshSkill(3);
						c.handleHitMask(6);
						c.poisonDamage--;
					}

				}

				@Override
				public void stop() {
					c.poisonDamage = 0;
				}
			}, 1);
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}
	}

	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j] - 1 == c.getItems().brokenBarrows[i][1]) {
					if (totalCost + 100000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 100000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0] + 1;
					c.sendMessage("You repair your "
							+ c.getItems().getItemName(
									c.getItems().brokenBarrows[i][0] + 1) + ".");
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
					totalCost);
	}

	public void handleLoginText() {
		loadQuests();
		c.getPA().sendFrame126("Monster Teleports", 13037);
		c.getPA().sendFrame126("Skill Teleports", 13047);
		c.getPA().sendFrame126("Boss Teleports", 13055);
		c.getPA().sendFrame126("Wilderness Teleports", 13063);
		c.getPA().sendFrame126("City Teleports", 13071);
		c.getPA().sendFrame126("Minigame Teleports", 13081); // carrallanger
		c.getPA().sendFrame126("Monster Teleports", 1300);
		c.getPA().sendFrame126("Skill Teleports", 1325);
		c.getPA().sendFrame126("Boss Teleports", 1350);
		c.getPA().sendFrame126("Wilderness Teleports", 1382);
		c.getPA().sendFrame126("City Teleports", 1415);
		c.getPA().sendFrame126("Minigame Teleports", 1454); // watchtower
	}

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}

}
