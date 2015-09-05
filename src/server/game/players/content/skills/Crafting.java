package server.game.players.content.skills;

import server.game.players.Client;
import server.util.Misc;
import server.Config;

public class Crafting {

	Client c;

	public Crafting(Client c) {
		this.c = c;
	}

	public enum LeatherCrafting {

		LEATHERVAMBS(1741, 1063, 11, 22, 1),
		LEATHERCHAPS(1741, 1095, 14, 25, 1),
		LEATHERBODY(1741, 1129, 18, 27, 1),

		GREENVAMBS(1745, 1065, 57, 62, 1),
		GREENCHAPS(1745, 1099, 60, 124, 2),
		GREENBODY(1745, 1135, 63, 186, 3),

		BLUEVAMBS(2505, 2487, 66, 70, 1),
		BLUECHAPS(2505, 2493, 68, 140, 2),
		BLUEBODY(2505, 2499, 71, 210, 3),

		REDVAMBS(2507, 2489, 73, 78, 1),
		REDCHAPS(2507, 2495, 75, 156, 2),
		REDBODY(2507, 2501, 77, 234, 3),

		BLACKVAMBS(2509, 2491, 79, 86, 1),
		BLACKCHAPS(2509, 2497, 82, 172, 2),
		BLACKBODY(2509, 2503, 84, 258, 3);

		private int leatherId, outcome, reqLevel, XP, reqAmt;
		private LeatherCrafting(int leatherId, int outcome, int reqLevel, int XP, int reqAmt) {
			this.leatherId = leatherId;
			this.outcome = outcome;
			this.reqLevel = reqLevel;
			this.XP = XP;
			this.reqAmt = reqAmt;	
		}

		public int getLeather() {
			return leatherId;
		}

		public int getOutcome() {
			return outcome;
		}

		public int getReqLevel() {
			return reqLevel;
		}

		public int getXP() {
			return XP;
		}

		public int getReqAmt() {
			return reqAmt;
		}
	}
	
	private final int[][] RINGS =	{ // Ring, Gem, Level, XP
		{1635, -1, 5, 15},
		{1637, 1607, 20, 40},
		{1639, 1605, 27, 55},
		{1641, 1603, 34, 70},
		{1643, 1601, 43, 85},
		{1645, 1615, 55, 100},
		{6575, 6573, 67, 115}
	};
	private int[][] NECKLACES = {
		{1654, -1, 6, 20},
		{1656, 1607, 22, 55},
		{1658, 1605, 29, 60},
		{1660, 1603, 40, 75},
		{1662, 1601, 56, 90},
		{1664, 1615, 72, 105},
		{6577, 6573, 82, 120}
	};
	private int[][] AMULETS = {
		{1673, -1, 8, 30},
		{1675, 1607, 24, 65},
		{1677, 1605, 31, 70},
		{1679, 1603, 50, 85},
		{1681, 1601, 70, 100},
		{1683, 1615, 80, 150},
		{6579, 6573, 90, 165} 
	};
	
	private final int[][] MOULD_INTERFACE_IDS = {
		/* Rings */
			{1635, 1637, 1639, 1641, 1643, 1645, 6575},
		/* Neclece */
			{1654, 1656, 1658, 1660, 1662, 1664, 6577},
		/* amulet */
			{1673, 1675, 1677, 1679, 1681, 1683, 6579} 
		
	};
	
	public void mouldInterface() {
		c.getPA().showInterface(4161);
	/* Rings */
		if(c.getItems().playerHasItem(1592, 1)) {
			for(int i = 0; i < MOULD_INTERFACE_IDS[0].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[0][i], i, 4233, 1);
			}
			c.getPA().sendFrame34(1643, 4, 4233, 1);
			c.getPA().sendFrame126("", 4230);
			c.getPA().sendFrame246(4229, 0, -1);
		} else {
			c.getPA().sendFrame246(4229, 120, 1592);
			for(int i = 0; i < MOULD_INTERFACE_IDS[0].length; i++) {
				c.getPA().sendFrame34(-1, i, 4233, 1);
			}
			c.getPA().sendFrame126("You need a ring mould to craft rings.", 4230);
		}
	/* Necklace */
		if(c.getItems().playerHasItem(1597, 1)) {
			for(int i = 0; i < MOULD_INTERFACE_IDS[1].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[1][i], i, 4239, 1);
			}
			c.getPA().sendFrame34(1662, 4, 4239, 1);
			c.getPA().sendFrame246(4235, 0, -1);
			c.getPA().sendFrame126("", 4236);
		} else {
			c.getPA().sendFrame246(4235, 120, 1597);
			c.getPA().sendFrame126("You need a necklace mould to craft necklaces", 4236);
			for(int i = 0; i < MOULD_INTERFACE_IDS[1].length; i++) {
				c.getPA().sendFrame34(-1, i, 4239, 1);
			}
		}
	/* Amulets */
		if(c.getItems().playerHasItem(1595, 1)) {
			for(int i = 0; i < MOULD_INTERFACE_IDS[2].length; i++) {
				c.getPA().sendFrame34(MOULD_INTERFACE_IDS[2][i], i, 4245, 1);
			}
			c.getPA().sendFrame34(1681, 4, 4245, 1);
			c.getPA().sendFrame246(4241, 0, -1);
			c.getPA().sendFrame126("", 4242);
		} else {
			c.getPA().sendFrame246(4241, 120, 1595);
			c.getPA().sendFrame126("You need a amulet mould to craft necklaces", 4242);
			for(int i = 0; i < MOULD_INTERFACE_IDS[2].length; i++) {
				c.getPA().sendFrame34(-1, i, 4245, 1);
			}
		}
	}
	
	public void mouldItem(int item, int amount) {
		int done = 0;
		
		final int GOLD_BAR = 2357;
		
		boolean isRing = false;
		boolean isNeck = false;
		boolean isAmulet = false;
		int gem = 0;
		int itemAdd = -1;
		int xp = 0;
		int lvl = 1;
		for(int i = 0; i < 7; i++) {
			if(item == RINGS[i][0]) {
				isRing = true;
				itemAdd = RINGS[i][0];
				gem = RINGS[i][1];
				lvl = RINGS[i][2];
				xp = RINGS[i][3];
				break;
			}
			if(item == NECKLACES[i][0]) {
				isNeck = true;
				itemAdd = NECKLACES[i][0];
				gem = NECKLACES[i][1];
				lvl = NECKLACES[i][2];
				xp = NECKLACES[i][3];
				break;
			}
			if(item == AMULETS[i][0]) {
				isAmulet = true;
				itemAdd = AMULETS[i][0];
				gem = AMULETS[i][1];
				lvl = AMULETS[i][2];
				xp = AMULETS[i][3];
				break;
			}
		}
		if(!isRing && !isNeck && !isAmulet) {
			return;
		}
		if(c.playerLevel[c.playerCrafting] >= lvl) {
			if(c.getItems().getItemName(itemAdd).toLowerCase().contains("gold") && !c.getItems().playerHasItem(GOLD_BAR, 1) || !c.getItems().playerHasItem(GOLD_BAR, 1)) {
				c.sendMessage("You need a Gold bar to make this.");
				return;
			} else if(!c.getItems().playerHasItem(gem, 1) && c.getItems().playerHasItem(GOLD_BAR, 1)) {
				c.sendMessage(getRequiredMessage(c.getItems().getItemName(gem)));
				return;
			}
			c.getPA().removeAllWindows();
			while((done < amount) && (c.getItems().getItemName(gem).toLowerCase().contains("unarmed") && c.getItems().playerHasItem(GOLD_BAR, 1) ||
					c.getItems().playerHasItem(gem, 1) && c.getItems().playerHasItem(GOLD_BAR, 1))) {
				c.getItems().deleteItem(gem, 1);
				c.getItems().deleteItem(GOLD_BAR, 1);
				c.getItems().addItem(itemAdd, 1);
c.getPA().addSkillXP(xp * Config.CRAFTING_EXPERIENCE, c.playerCrafting);
				c.getPA().refreshSkill(c.playerCrafting);
				done++;
			}
			if(done == 1) {
				c.sendMessage("You craft the gold and gem together to form a " + c.getItems().getItemName(itemAdd) + ".");
			} else if(done > 1) {
				c.sendMessage("You craft the gold and gem together to form " + done + " " + c.getItems().getItemName(itemAdd) + "'s.");
			}
		} else {
			c.sendMessage("You need a Crafting level of "+lvl+" to craft this.");
			return;
		}
	}
	
	public String getRequiredMessage(String item) {
		if(item.startsWith("A") || item.startsWith("E") || item.startsWith("I") || item.startsWith("O") || item.startsWith("U")) {
			return "You need a Gold bar and an "+item+" to make this.";
		} else {
			return "You need a Gold bar and a "+item+" to make this.";
		}
	}

	int[][] leathers = {
			{1741, 1095, 1063, 1129},
			{1745, 1099, 1065, 1135},
			{2505, 2493, 2487, 2499},
			{2507, 2495, 2489, 2501},
			{2509, 2497, 2491, 2503}};

	public void openLeather(int hide) {
		for (int i = 0; i < leathers.length; i++) {
			if (leathers[i][0] == hide) {
				c.getPA().sendFrame164(8880); //leather
				c.getPA().sendFrame126("What would you like to make?", 8879);
				c.getPA().sendFrame246(8884, 250, leathers[i][1]); // middle
				c.getPA().sendFrame246(8883, 250, leathers[i][2]); // left picture
				c.getPA().sendFrame246(8885, 250, leathers[i][3]); // right pic
				c.getPA().sendFrame126("Vambs", 8889);
				c.getPA().sendFrame126("Chaps", 8893);
				c.getPA().sendFrame126("Body", 8897);
			}
		}
		c.craftingLeather = true;
		c.hideId = hide;
	}

	public void handleLeather(int item1, int item2) {
		openLeather((item1 == 1733) ? item2 : item1);
	}

	public void handleCraftingClick(int clickId) {
		switch (clickId) {
		case 34185: //Vambs
			switch (c.hideId) {
			case 1741:
				craftLeather(1063); //Leather vambs
				break;
			case 1745:
				craftLeather(1065); //Green d'hide vambs
				break;
			case 2505:
				craftLeather(2487); //Blue d'hide vambs
				break;
			case 2507:
				craftLeather(2489); //Red d'hide vambs
				break;
			case 2509:
				craftLeather(2491); //Black d'hide vambs
				break;
			}
			break;
		case 34189:
			switch (c.hideId) {
			case 1741:
				craftLeather(1095); //Leather chaps
				break;
			case 1745:
				craftLeather(1099); //Green d'hide chaps
				break;
			case 2505:
				craftLeather(2493); //Blue d'hide chaps
				break;
			case 2507:
				craftLeather(2495); //Red d'hide chaps
				break;
			case 2509:
				craftLeather(2497); //Black d'hide chaps
				break;
			}
			break;
		case 34193:
			switch (c.hideId) {
			case 1741:
				craftLeather(1129); //Leather body
				break;
			case 1745:
				craftLeather(1135); //Green d'hide body
				break;
			case 2505:
				craftLeather(2499); //Blue d'hide body
				break;
			case 2507:
				craftLeather(2501); //Red d'hide body
				break;
			case 2509:
				craftLeather(2503); //Black d'hide body
				break;
			}
			break;
		}
	}

	private LeatherCrafting forLeather(int id) {
		for (LeatherCrafting lc : LeatherCrafting.values()) {
			if (lc.getOutcome() == id) {
				return lc;
			}
		}
		return null;
	}

	public void craftLeather(int id) {
		LeatherCrafting lea = forLeather(id);
		if (lea != null) {
			if (c.playerLevel[c.playerCrafting] >= lea.getReqLevel()) {
				if (c.getItems().playerHasItem(lea.getLeather(), lea.getReqAmt())) {
					c.startAnimation(1249);
					c.getItems().deleteItem(lea.getLeather(), lea.getReqAmt());
					c.getItems().addItem(lea.getOutcome(), 1);
					c.getPA().addSkillXP(lea.getXP(), c.playerCrafting);
					resetCrafting();
				} else {
					c.sendMessage("You do not have enough items to craft this item.");
				}
			} else {
				c.sendMessage("You need a crafting level of "+lea.getReqLevel()+" to craft this item.");
			}
			c.getPA().removeAllWindows();
		}
	}

	public void resetCrafting() {
		c.craftingLeather = false;
		c.hideId = -1;
	}

	public enum GemCrafting {

		OPAL(1625, 1609, 891, 1, 15, true),
		JADE(1627, 1611, 891, 13, 20, true),
		REDTOPAZ(1629, 1613, 892, 16, 25, true),
		SAPPHIRE(1623, 1607, 888, 1, 50, false),
		EMERALD(1621, 1605, 889, 27, 68, false),
		RUBY(1619, 1603, 887, 34, 85, false),
		DIAMOND(1617, 1601, 890, 43, 108, false),
		DRAGONSTONE(1631, 1615, 890, 55, 138, false),
		ONYX(6571, 6573, 2717, 67, 168, false);

		private int uncutID, cutID, animation, levelReq, XP;
		private boolean isSemiPrecious;

		private GemCrafting(int uncutID, int cutID, int animation, int levelReq, int XP, boolean semiPrecious) {
			this.uncutID = uncutID;
			this.cutID = cutID;
			this.animation = animation;
			this.levelReq = levelReq;
			this.XP = XP;
			this.isSemiPrecious = semiPrecious;
		}

		public int getUncut() {
			return uncutID;
		}

		public int getCut() {
			return cutID;
		}

		public int getAnim() {
			return animation;
		}

		public int getReq() {
			return levelReq;
		}

		public int getXP() {
			return XP;
		}

		public boolean isSemiPrecious() {
			return isSemiPrecious;
		}
	}

	public void handleChisel(int id1, int id2) {
		cutGem((id1 == 1755) ? id2 : id1);
	}

	private GemCrafting forGem(int id) {
		for (GemCrafting g : GemCrafting.values()) {
			if (g.getUncut() == id) {
				return g;
			}
		}
		return null;
	}

	public void cutGem(int id) {
		GemCrafting gem = forGem(id);
		if (gem != null) {
			if (c.getItems().playerHasItem(gem.getUncut(), 1)) {
				if (c.playerLevel[c.playerCrafting] >= gem.getReq()) {
					c.getItems().deleteItem(gem.getUncut(), 1);
					if (gem.isSemiPrecious()) {
						if (Misc.random(100) == 37) {
							c.sendMessage("You accidently crush the gem!");
							c.getItems().addItem(1633, 1);
						}
					} else {
						c.getItems().addItem(gem.getCut(), 1);
						c.getPA().addSkillXP(gem.getXP(), c.playerCrafting);
					}
					c.startAnimation(gem.getAnim());
				} else {
					c.sendMessage("You need a crafting level of "+gem.getReq()+" to cut this gem.");
				}
			}
		}
	}
}