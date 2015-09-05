package server.game.players.content.skills.woodcutting;

import java.util.HashMap;




import server.Config;
import server.Server;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.objects.Objects;
import server.game.players.Client;
import server.game.players.Player;
import server.game.players.PlayerAssistant;
import server.game.players.PlayerHandler;
import server.game.players.content.skills.SkillHandler;
import server.game.players.content.skills.SkillingTools;
import server.util.Misc;
import server.world.ObjectHandler;

public class Woodcutting extends SkillHandler {
	
    public static final int[] COMMON_SEEDS = {5312, 5283, 5284, 5285, 5286, 5313};
    public static final int[] UNCOMMON_SEEDS = {5314, 5288, 5287, 5315, 5289};
    public static final int[] RARE_SEEDS = {5316, 5290};
    public static final int[] VERY_RARE_SEEDS = {5317};

    public static final int[] COMMON_RING = {1635, 1637};
    public static final int[] UNCOMMON_RING = {1639};
    public static final int[] RARE_RING = {1641};
    public static final int[] VERY_RARE_RING = {1643};
    
	public enum Trees {
		NORMAL(new int[]{1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1289,
				1290, 1291, 1315, 1316, 1318, 1319, 1330, 1331, 1332, 1333, 1365, 1383, 1384,
				2409, 3033, 3034, 3035, 3036, 3881, 3882, 3883, 5902, 5903, 5904}, 1, 25, 1511, 
				1342, 75, 100),
		OAK(new int[]{1281, 2037}, 15, 37.5, 1521, 1356, 14, 25),
		WILLOW(new int[]{1308, 5551, 5552, 5553}, 30, 67.5, 1519, 7399, 14, 5),
		MAPLE(new int[]{1307, 4677}, 45, 100, 1517, 1343, 59, 15),
		YEW(new int[]{1309}, 60, 175, 1515, 7402, 100, 5),
		MAGIC(new int[]{1306}, 75, 250, 1513, 7401, 125, 3),
		ACHEY(new int[]{2023}, 1, 25, 2862, 3371, 75, 100),
		MAHOGANY(new int[]{9034}, 50, 125, 6332, 9035, 80, 10),
		ARCTIC(new int[]{21273}, 55, 175, 10810, 21274, 80, 6),
		TEAK(new int[]{9036}, 35, 85, 6333, 9037, 14, 20),
		HOLLOW(new int[]{2289, 4060}, 45, 83, 3239, 2310, 59, 15),
		DRAWMEN(new int[]{1292}, 36, 0, 771, 1513, 59, 100);
		//VINES(new int[]{5107}, 1, 0, -1, -1, 30, 100);
		
		private int[] objectId;
		private int reqLvl, logId, stumpId, respawnTime, decayChance;
		private double xp;
		
		private Trees(int[] objectId, int reqLvl, double xp, int logId, int stumpId, 
				int respawnTime, int decayChance) {
			this.objectId = objectId;
			this.reqLvl = reqLvl;
			this.xp = xp;
			this.logId = logId;
			this.stumpId = stumpId;
			this.respawnTime = respawnTime;
			this.decayChance = decayChance;
		}
		
		private static HashMap<Integer, Trees> trees = new HashMap<Integer, Trees>();
		
		static {
			for (Trees t : Trees.values()) {
				for (int i : t.objectId) {
					trees.put(i, t);
				}
			}
		}
		
		public static Trees getTree(int id) {
			return trees.get(id);
		}
		
		public int[] getObjectId() {
			return objectId;
		}
		
		public int getReqLvl() {
			return reqLvl;
		}
		
		public double getXp() {
			return xp;
		}
		
		public int getLogId() {
			return logId;
		}
		
		public int getStumpId() {
			return stumpId;
		}
		
		public int getRespawnTime() {
			return respawnTime;
		}
		
		public int getDecayChance() {
			return decayChance;
		}
	}

	public static boolean chopTree(final Player c, final int tree, final int obX, final int obY) {
		c.turnPlayerTo(obX, obY);
		final Trees Tree = Trees.getTree(tree);
		
		
		if(Tree == null)
			return false;
		if (System.currentTimeMillis() - c.lastThieve < 2500)
			return false;
		
		if (!noInventorySpace(c, "woodcutting")){
			resetWoodcutting(c);
			return false;
		}
		if (hasAxe(c) && !canUseAxe(c)){
			c.sendMessage("Your Woodcutting level is too low to use this axe.");
			return false;
		}
		if (!hasAxe(c)) {
			c.sendMessage("You need a Woodcutting axe to chop this.");
			return false;
		}
		c.lastThieve = System.currentTimeMillis();
		c.sendMessage("You swing your axe at the tree.");


		if (c.playerSkilling[c.playerWoodcutting]) {
			return false;
		}

		c.playerSkilling[c.playerWoodcutting] = true;
		c.stopPlayerSkill = true;

		c.playerSkillProp[8][1] = Tree.getRespawnTime();	//RESPAWN TIME
		c.playerSkillProp[8][2] = Tree.getReqLvl();	//LEVELREQ
		c.playerSkillProp[8][3] = (int) Tree.getXp();		//XP
		c.playerSkillProp[8][4] = getAnimId(c);			//ANIM
		c.playerSkillProp[8][5] = Tree.getRespawnTime();	//RESPAWN TIME
		c.playerSkillProp[8][6] = Tree.getLogId();		//LOG

		c.woodcuttingTree = obX + obY;

		if (!SkillHandler.hasRequiredLevel((Client) c, 8, c.playerSkillProp[8][2], "woodcutting", "cut this tree")) {
			resetWoodcutting(c);
			return false;
		}

		c.startAnimation(c.playerSkillProp[8][4]);

				if(Tree != null) {
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if (!noInventorySpace(c, "woodcutting") || !c.stopPlayerSkill || !c.playerSkilling[c.playerWoodcutting]
									|| !c.goodDistance(obX, obY, c.absX, c.absY, 3)){
								container.stop();
							}
							if(c.playerSkillProp[8][6] > 0) {
								int burnChance = Misc.random(5);
								if (burnChance == 2) {
								if (c.playerEquipment[c.playerWeapon] == 120244)
										((Client) c).gfx(1776,150);
								        //((Client) c).getPA().addSkillXP(Config.FIREMAKING_EXPERIENCE, c.playerFiremaking);
										//c.sendMessage("Your inferno adze burns the logs.");
										//return;
										//run in debug mode it will auto update inmodel
						} else {
									c.getItems().addItem(c.playerSkillProp[8][6], 1);
						}
					}			
							if(c.playerSkillProp[8][3] > 0) {
							if (c.playerEquipment[c.playerHands] == 13850) {
								((Client) c).getPA().addSkillXP(c.playerSkillProp[8][3] * Config.WOODCUTTING_EXPERIENCE * 2, 8);
							} else {
								((Client) c).getPA().addSkillXP(c.playerSkillProp[8][3] * Config.WOODCUTTING_EXPERIENCE, 8);
								}
							}
								
							if(c.playerSkillProp[8][6] > 0) {

								c.sendMessage("You get some " + c.getItems().getItemName(c.playerSkillProp[8][6]) + ".");
							}
							/*if(Misc.random(100) == 0) {
								recieveBirdsNest(c);
							}*/
							if (!hasAxe(c)) {
								c.sendMessage("You need a Woodcutting axe which you need a Woodcutting level to use.");
								container.stop();
							}
							if((tree != 1292 && Misc.random(100) <= Tree.getDecayChance()) && c.playerSkilling[c.playerWoodcutting]) {
								createStump(c, tree, obX, obY);
								container.stop();
							}
						}
						@Override
						public void stop() {
							resetWoodcutting(c);
						}
					}, (Misc.random(5) + getAxeTime(c) + (10 - (int)Math.floor(c.playerLevel[8] / 10))));
					CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
						@Override
						public void execute(CycleEventContainer container) {
							if(!c.stopPlayerSkill) {
								resetWoodcutting(c);
								container.stop();
							}
							if(c.playerSkillProp[8][4] > 0 && c.playerSkilling[c.playerWoodcutting]) {
								c.startAnimation(c.playerSkillProp[8][4]);
							}
						}
						@Override
						public void stop() {

						}
					}, 4);
				}
				return true;
	}

	private static void treeLocated(Player c, int obX, int obY) {
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(PlayerHandler.players[i] != null) {
				Player person = PlayerHandler.players[i];
				if(person != null) {
					if(person.distanceToPoint(c.absX, c.absY) <= 10){
						if(c.woodcuttingTree == person.woodcuttingTree) {
							person.woodcuttingTree = -1;
							resetWoodcutting(person);
						}
					}
				}
			}
		}
	}
	
	public static void createStump(final Player c, final int tree, final int obX, final int obY) {
		final Trees Tree = Trees.getTree(tree);
		Objects t = null;
		for (Objects o : ObjectHandler.globalObjects) {
			if (Objects.objectId == tree && Objects.objectX == obX && Objects.objectY == obY) {
				t = o;
			}
		}
		Server.objectHandler.placeObject(new Objects(Tree.getStumpId(), obX, obY, c.heightLevel, t != null ? t.getObjectFace() : 0, 
				t != null ? t.getObjectType() : 10, obY));
		treeLocated(c, obX, obY);
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				container.stop();
			}
			@Override
			public void stop() {
				Server.objectHandler.placeObject(new Objects(tree, obX, obY, c.heightLevel, 0, 10, obY));
				//resetWoodcutting(c);
			}
		}, Tree.getRespawnTime());
	}
	
	/*public static void recieveBirdsNest(Player c) {
		Server.itemHandler.createGroundItem(c, 5070 + Misc.random(4), c.getX(), c.getY(), c.heightLevel, 1, c.getId());
		c.sendMessage("A bird's nest falls out of the tree!");
	}*/

	public static void resetWoodcutting(Player c) {
		c.startAnimation(65535);
		((PlayerAssistant) c.getPA()).removeAllWindows();
		c.playerSkilling[c.playerWoodcutting] = false;
		for(int i = 0; i < 9; i++) {
			c.playerSkillProp[8][i] = -1;
		}
	}

	public static boolean hasAxe(Player c) {
		boolean has = false;
		for(int i = 0; i < axes.length; i++) {
			if(c.getItems().playerHasItem(axes[i][0]) || c.playerEquipment[3] == axes[i][0]) {
				has = true;
			}
		}
		return has;
	}

	private static int getAxeTime(Player c) {
		int axe = -1;
		for(int i = 0; i < axes.length; i++) {
			if(c.playerLevel[8] >= axes[i][1]) {
				if(c.getItems().playerHasItem(axes[i][0]) || c.playerEquipment[3] == axes[i][0]) {
					axe = axes[i][3];
				}
			}
		}
		return axe;
	}

	private static int getAnimId(Player c) {
		int anim = -1;
		for(int i = 0; i < axes.length; i++) {
			if(c.playerLevel[8] >= axes[i][1]) {
				if(c.getItems().playerHasItem(axes[i][0]) || c.playerEquipment[3] == axes[i][0]) {
					anim = axes[i][2];
				}
			}
		}
		return anim;
	}

	private static boolean canUseAxe(Player c) {
		if((performCheck(c, 1349, 1) || performCheck(c, 1351, 1) 
			|| performCheck(c, 1353, 6) || performCheck(c, 1361, 6) 
			|| performCheck(c, 1355, 21) || performCheck(c, 1357, 31)
			|| performCheck(c, 1359, 41) || performCheck(c, 6739, 61)) 
			|| performCheck (c, 12024, 92)) {
			//&& !(c.playerEquipment[c.playerWeapon] == SkillingTools.infernoAdze)) {
			return true;
		}
		/*if (c.playerEquipment[c.playerWeapon] == SkillingTools.infernoAdze) {
			if (c.getSkillTools().getAdzeRequirements(c))
				return true;
		}*/
		return false;
	}

	private static boolean performCheck(Player c, int i, int l) {
		return (c.getItems().playerHasItem(i) || c.playerEquipment[3] == i) && c.playerLevel[8] >= l;
	}

	private static int[][] axes = {
		{1351, 1, 879, 10},
		{1349, 1, 877, 10},
		{1353, 6, 875, 10},
		{1361, 6, 873, 8},
		{1355, 21, 871, 7},
		{1357, 31, 869, 6},
		{1359, 41, 867, 4},
		{6739, 61, 2846, 3},
		{SkillingTools.infernoAdze, 61, 12024, 2},
	};	
	
	  public static boolean handleNest(Player c, int itemId){
	        int[] commonItems, uncommonItems, rareItems, veryRareItems;
	        switch(itemId){
	            case 5070:
	                c.getItems().deleteItem(itemId, 1);
	                c.getItems().addItem(5075, 1);
	                c.getItems().addItem(5076, 1);
	                return true;
	            case 5071:
	                c.getItems().deleteItem(itemId, 1);
	                c.getItems().addItem(5075, 1);
	                c.getItems().addItem(5078, 1);
	                return true;
	            case 5072:
	                c.getItems().deleteItem(itemId, 1);
	                c.getItems().addItem(5075, 1);
	                c.getItems().addItem(5077, 1);
	                return true;
	            case 5073:
	                commonItems = COMMON_SEEDS;
	                uncommonItems = UNCOMMON_SEEDS;
	                rareItems = RARE_SEEDS;
	                veryRareItems = VERY_RARE_SEEDS;
	                break;
	            case 5074:
	                commonItems = COMMON_RING;
	                uncommonItems = UNCOMMON_RING;
	                rareItems = RARE_RING;
	                veryRareItems = VERY_RARE_RING;
	                break;
	            default :
	                return false;
	        }
	        int randomNumber = Misc.random(100), finalItem;
	        if(randomNumber <= 60) finalItem = commonItems[Misc.random(commonItems.length - 1)];
	        else if(randomNumber <= 80) finalItem = uncommonItems[Misc.random(uncommonItems.length - 1)];
	        else if(randomNumber <= 95) finalItem = rareItems[Misc.random(rareItems.length - 1)];
	        else finalItem = veryRareItems[Misc.random(veryRareItems.length - 1)];

	        c.sendMessage("You search the nest...and find something in it!");
	        c.getItems().deleteItem(itemId, 1);
	        c.getItems().addItem(5075, 1);
	        c.getItems().addItem(finalItem, 1);
	        return true;
	    }

}