package server.game.players;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import server.Config;
import server.game.items.Item;
import server.game.items.ItemAssistant;
import server.game.npcs.NPC;
import server.game.npcs.NPCHandler;
//import server.game.players.content.skills.SkillingTools;
import server.util.ISAACCipher;
import server.util.Misc;
import server.util.Stream;
import server.world.Clan;

public abstract class Player {

	/* gwd killcounts */
	public int ArmaKc;
	public int SaraKc;
	public int ZammyKc;
	public int BandKc;
	// End
	
	public int smallPouchP, smallPouchE;
	public int mediumPouchP, mediumPouchE;
	public int largePouchP, largePouchE;
	public int giantPouchP, giantPouchE;
	public int mediumPouchDecay, largePouchDecay, giantPouchDecay;
	/**
	 * True, if the player has summoned a pet.
	 */
	public String targetName = "None";
	public String lastTargetName = "";
	public int failedTargets = 0;
	public int target;
	public int pTime;
	// public int prestigeLevel = 0;
	public int prestige;
	public int prestigePoints;
	public int page;
	public int lvlPoints;
	public boolean instantWalk = false;
	public int aPoints;
	public int prestigeLevel;
	public int bossKills;
	public int myTarget = -1;
	public int targetDifference = 5;
	public int targetPoints, tournament = 0, FreePointsTimer = 0,
			FreePoints = 0, totalTargetPoints, targetSystem = 1;
	public boolean slayerHelmAffect;
	public boolean petSummoned;
	public long lampStart;
	public long lampStart2;
	public int chatClickDelay = 0;
	public int worshippedGod = 0, godReputation = 0;

	// public int ssSpec = false;
	public int targetFinderDelay = Misc.random(10);
	public int tradeinWith;

	/* Cannon Variables */
	public boolean settingUpCannon, hasCannon, cannonIsShooting, setUpBase,
			setUpStand, setUpBarrels, setUpFurnace;
	public int cannonBalls, cannonBaseX, cannonBaseY, cannonBaseH, rotation,
			cannonID;
	public int oldCannon;

	public boolean worshippingGod() {
		if (worshippedGod > 0)
			return true;
		else
			return false;
	}

	public String worshippedGodString() {
		if (worshippedGod == 1)
			return "Saradomin";
		else if (worshippedGod == 2)
			return "Zamorak";
		else
			return "Nobody";
	}

	public void addGodReputation(int amount) {
		godReputation += amount;
	}

	public double getKdr() {
		if ((KC == 0 && DC == 0) || (KC != 0 && DC == 0))
			return 0;
		else
			return ((double) KC) / ((double) DC);
	}

	/**
	 * The state of petSummoned.
	 * 
	 * @return The state of petSummoned.
	 */
	public int tzKekSpawn = 0, tzKekTimer = 0, caveWave, tzhaarNpcs;
	public int canLoginMsg = 0;
	public boolean canDepositAll = false;
	public long banStart;
	public long banEnd;
	public long lastStun;
	public boolean zaryteSpecial;
	public int foodEaten, fuckinannoyed;
	public int Inferno_Tick;
	public int yellmute = 0;
	public String currentTime, date;
	public int warnings = 0;
	public int burnEXP;
	public boolean finishedWildPipe = false;
	public boolean finishedWildRope = false;
	public boolean finishedWildStone = false;
	public boolean finishedWildLog = false;
	public boolean finishedWildRocks = false;
	public boolean doingAgility = false;
	public String burnName;
	public int WitchesPotion = 0, grain = 0, flourAmount = 0,
			totalPlaytime = 0, homeTimer = 0;
	public long lastPUpdate;
	public long lastVecna = 0;
	public int rigourPray;

	public boolean getPetSummoned() {
		return petSummoned;
	}

	public boolean isInFightCaves() {
		return absX >= 2365 && absX <= 2429 && absY >= 5055 && absY <= 5122;
	}

	public int calculateMaxLifePoints() {
		int lifePoints = getLevelForXP(playerXP[3]);// The normal hp
		int torvaLegs = 20143;
		int torvaBody = 20139;
		int torvaHelm = 20135;
		int pernixLegs = 20155;
		int pernixBody = 20151;
		int pernixHelm = 20147;
		int virtusLegs = 20167;
		int virtusBody = 20163;
		int virtusHelm = 20159;
		if (playerEquipment[playerLegs] == torvaLegs
				|| playerEquipment[playerLegs] == pernixLegs
				|| playerEquipment[playerLegs] == virtusLegs)
			lifePoints += 13;
		if (playerEquipment[playerChest] == torvaBody
				|| playerEquipment[playerChest] == pernixBody
				|| playerEquipment[playerChest] == virtusBody)
			lifePoints += 20;
		if (playerEquipment[playerHat] == torvaHelm
				|| playerEquipment[playerHat] == pernixHelm
				|| playerEquipment[playerHat] == virtusHelm)
			lifePoints += 7;
		return lifePoints;
	}

	public boolean hasGodArmour() {
		for (int i = 20135; i < 20168; i++) {
			if (playerEquipment[playerHat] == i
					|| playerEquipment[playerChest] == i
					|| playerEquipment[playerLegs] == i)
				return true;
		}
		return false;
	}

	public boolean torva() {
		if (playerEquipment[playerHat] == 20135
				&& playerEquipment[playerLegs] == 20143
				&& playerEquipment[playerChest] == 20139
				|| playerEquipment[playerHat] == 20147
				&& playerEquipment[playerLegs] == 20155
				&& playerEquipment[playerChest] == 20151
				|| playerEquipment[playerHat] == 20159
				&& playerEquipment[playerLegs] == 20167
				&& playerEquipment[playerChest] == 20163) {
			return true;
		}
		return false;
	}

	public void sendMessage(String string) {
		// TODO Auto-generated method stub

	}

	public ItemAssistant getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public SkillingTools getSkillTools() { // TODO Auto-generated method stub
	 * return null; }
	 */

	/**
	 * Change the state of petSummoned.
	 * 
	 * @param state
	 *            The state of petSummoned.
	 */
	public void setPetSummoned(boolean state) {
		petSummoned = state;
	}

	/**
	 * The NPC type of the pet that the player currently has summoned.
	 */
	public int petID;
	public int removedTasks[] = { -1, -1, -1, -1 };
	public long buySlayerTimer;
	public boolean needsNewTask = false;
	public int slayerPoints = 0;
	public int dayofweek;
	public int[] woodcuttingProp = new int[10];
	public boolean playerIsWoodcutting;
	public int hideId;
	public boolean isUsingInterfaceEvent;
	public int extraTime;
	public long agilityDelay;
	public int nextItem = 0, itemShown = -1, itemAction = 0;
	public long agility1, agility2, agility3, agility4, agility5, agility6,
			agility7;
	public boolean ropeSwing, logBalance, obstacleNet, balancingLedge, Ladder;
	public boolean logBalance1, obstacleNetUp, treeBranchUp, balanceRope,
			treeBranchDown, obstacleNetOver;
	public int dupefix1 = 0;

	public ArrayList<String> killedPlayers = new ArrayList<String>();
	public ArrayList<Integer> attackedPlayers = new ArrayList<Integer>();
	public ArrayList<String> lastKilledPlayers = new ArrayList<String>();
	public ArrayList<String> lastConnectedFrom = new ArrayList<String>();

	/**
	 * Clan Chat Variables
	 */
	public Clan clan;
	public String clanName, properName;
	public String lastClanChat = "";

	/**
	 * Title Variables
	 */
	public String playerTitle = "";
	public int titleColor = 0;

	/**
	 * Event Variables
	 */
	public boolean hasEvent;

	public boolean playerIsDuping() {
		if (isShopping || inTrade || isBanking || duelStatus == 1)
			return true;
		return false;
	}

	public boolean isShopping = false;
	public boolean inTrade = false;
	/**
	 * Achievement Variables
	 */
	public int achievementsCompleted;
	public int achievementPoints;
	public int fireslit;
	public int crabsKilled;
	public int treesCut;
	public int droppingDefender = 8844;
	public boolean inDefenderRoom = false;
	public boolean expLock = false;
	public boolean buyingX;
	public boolean leverClicked = false, isTraining = false,
			chosenstarter = false;
	public double crossbowDamage;

	public int pkp, KC, DC, kdr;

	public boolean[] clanWarRule = new boolean[10];
	public int follow2 = 0;
	public int antiqueSelect = 0;
	public boolean usingLamp = false;
	public boolean normalLamp = false;
	public boolean antiqueLamp = false;
	public boolean usingAltar = false;
	public int altarItemId = 0, safeTimer = 0;
	public int[][] playerSkillProp = new int[20][15];
	public boolean[] playerSkilling = new boolean[20];
	public boolean setPin = false;
	public String bankPin = "";
	public boolean teleporting;

	public long muteEnd;
	public long lastReport = 0;
	public int level1 = 0, level2 = 0, level3 = 0;
	public String lastReported = "";
	public long lastButton;
	public int leatherType = -1;
	public boolean isWc;
	public boolean homeTele;
	public int homeTeleDelay = 0;

	public boolean wcing;
	public int treeX, treeY;
	public long miscTimer;
	public boolean canWalk;
	public long lastFlower;
	public long waitTime;
	public boolean usingROD = false;
	public boolean usingGlory = false;
	public boolean usingGN = false;
	public boolean isOperate2;
	public int DELAY = 1250;
	public long saveButton = 0;
	public int attempts = 3;
	public boolean isOperate;
	public int itemUsing;

	public boolean isFullBody = false;
	public boolean isFullHelm = false;
	public boolean isFullMask = false;

	public long lastCast;
	public boolean hasBankPin, enterdBankpin, firstPinEnter, requestPinDelete,
			secondPinEnter, thirdPinEnter, fourthPinEnter, hasBankpin;
	public int lastLoginDate, playerBankPin, recoveryDelay = 3,
			attemptsRemaining = 3, lastPinSettings = -1, setPinDate = -1,
			changePinDate = -1, deletePinDate = -1, firstPin, secondPin,
			thirdPin, fourthPin, bankPin1, bankPin2, bankPin3, bankPin4,
			pinDeleteDateRequested;

	public boolean isBanking = false, isCooking = false, initialized = false,
			disconnected = false, ruleAgreeButton = false,
			RebuildNPCList = false, isActive = false, isKicked = false,
			isSkulled = false, friendUpdate = false, newPlayer = false,
			hasMultiSign = false, saveCharacter = false, mouseButton = false,
			splitChat = false, chatEffects = true, acceptAid = false,
			nextDialogue = false, autocasting = false, usedSpecial = false,
			mageFollow = false, dbowSpec = false, craftingLeather = false,
			properLogout = false, secDbow = false, maxNextHit = false,
			ssSpec = false, vengOn = false, addStarter = false,
			startPack = false, accountFlagged = false, msbSpec = false,

			dtOption = false, dtOption2 = false, doricOption = false,
			doricOption2 = false, caOption2 = false, caOption2a = false,
			caOption4a = false, caOption4b = false, caOption4c = false,
			caPlayerTalk1 = false, horrorOption = false, rfdOption = false,
			inDt = false, inHfd = false, disableAttEvt = false, Jail = false,
			AttackEventRunning = false, npcindex, spawned = false;

	public int

	saveDelay, height = 0, playerKilled, pkPoints, totalPlayerDamageDealt,
			killedBy, lastChatId = 1, privateChat, insane = 0, legend = 0,
			friendSlot = 0, dialogueId, randomCoffin, newLocation, specEffect,
			specBarId, attackLevelReq, defenceLevelReq, strengthLevelReq,
			rangeLevelReq, magicLevelReq, followId, skullTimer, votingPoints,
			nextChat = 0, talkingNpc = -1, dialogueAction = 0, autocastId,
			followDistance, followId2, barrageCount = 0, delayedDamage = 0,
			delayedDamage2 = 0, pcPoints = 0, bossnpcKills = 0, npcKills = 0, bandosKills = 0, donPoints = 0,
			magePoints = 0, lastArrowUsed = -1, clanId = -1, autoRet = 0,
			pcDamage = 0, xInterfaceId = 0, xRemoveId = 0, xRemoveSlot = 0,
			tzhaarToKill = 0, tzhaarKilled = 0, waveId, frozenBy = 0,
			poisonDamage = 0, teleAction = 0, newPlayerAct = 0,
			bonusAttack = 0, lastNpcAttacked = 0, killCount = 0,
			actionTimer, rfdRound = 0, roundNpc = 0, cookAss = 0,
			desertTreasure = 0, horrorFromDeep = 0, QuestPoints = 0,
			doricQuest = 0, ghostKills = 0, giantbatKills = 0, armadylKills = 0, zamorakKills = 0, saradominKills = 0, dSupremeKills = 0,
			dPrimeKills = 0, bluedragonKills = 0, bronzedragonKills = 0, irondragonKills = 0, blackdemonKills = 0, greendragonKills = 0, babybluedragonKills = 0, bloodveldKills = 0, gargoyleKills = 0, nechryaelKills = 0, bansheeKills = 0, infernalmageKills = 0, lesserdemonKills = 0, chaosdwarfKills = 0, chaosdruidKills = 0, hillgiantKills = 0, skeletonKills = 0, ghostkills = 0, rockcrabKills = 0, dRexKills = 0, scorpiaKills = 0, vetionKills = 0, venenatisKills = 0, 
			callistoKills = 0, crawlinghandKills = 0, steeldragonKills = 0, chaosKills = 0, seaTrollQueenKills = 0, dustdevilKills = 0, seaSnakeKills = 0, penanceQueenKills = 0,
			darkBeastKills = 0, abbyDemonKills = 0;

	public int[] voidStatus = new int[5];
	public int[] itemKeptId = new int[4];
	public int[] pouches = new int[4];
	public final int[] POUCH_SIZE = { 3, 6, 9, 12 };
	public boolean[] invSlot = new boolean[28], equipSlot = new boolean[14];
	public long friends[] = new long[200];
	public double specAmount = 0;
	public double specAccuracy = 1;
	public double specDamage = 1;
	public double prayerPoint = 1.0;
	public boolean storing = false;
	public int teleGrabItem, teleGrabX, teleGrabY, duelCount, underAttackBy,
			underAttackBy2, wildLevel, teleTimer, respawnTimer, saveTimer = 0,
			teleBlockLength, poisonDelay;
	public long lastPlayerMove, lastPoison, lastPoisonSip, poisonImmune,
			lastSpear, lastProtItem, dfsDelay, lastVeng, lastYell,
			teleGrabDelay, protMageDelay, protMeleeDelay, protRangeDelay,
			lastAction, lastThieve, lastLockPick, alchDelay, specCom,
			specDelay = System.currentTimeMillis(), duelDelay, teleBlockDelay,
			godSpellDelay, singleCombatDelay, singleCombatDelay2, reduceStat,
			restoreStatsDelay, logoutDelay, buryDelay, cleanDelay, diceDelay,
			foodDelay, potDelay;
	public boolean canChangeAppearance = false;
	public boolean mageAllowed;
	public byte poisonMask = 0;
	public int focusPointX = -1, focusPointY = -1;
	public int questPoints = 0;
	public int cooksA;
	public int logsCut;
	public int amountFished;
	public int woodChopped;
	public int oresMined;
	public int totalLevel;
	public int jadCount;
	public int fishCooked;
	public int lastDtKill = 0;
	public int dtHp = 0, dtMax = 0, dtAtk = 0, dtDef = 0;
	public int desertT;
	public long lastChat, lastRandom, lastCaught = 0, lastAttacked,
			homeTeleTime, lastDagChange = -1, reportDelay, lastPlant,
			objectTimer, npcTimer, lastEss, lastClanMessage;
	public int DirectionCount = 0;
	public boolean appearanceUpdateRequired = true;
	public int hitDiff2;
	public int hitDiff = 0;
	public boolean hitUpdateRequired2;
	public boolean hitUpdateRequired = false;
	public boolean isDead = false;
	public boolean randomEvent = false;
	public boolean FirstClickRunning = false;
	public boolean WildernessWarning = false;

	/*
	 * End*
	 */

	public void faceNPC(int index) {
		faceNPC = index;
		faceNPCupdate = true;
		updateRequired = true;
	}

	protected boolean faceNPCupdate = false;
	public int faceNPC = -1;

	public void appendFaceNPCUpdate(Stream str) {
		str.writeWordBigEndian(faceNPC);
	}

	public int[] keepItems = new int[4];
	public int[] keepItemsN = new int[4];
	public int WillKeepAmt1, WillKeepAmt2, WillKeepAmt3, WillKeepAmt4,
			WillKeepItem1, WillKeepItem2, WillKeepItem3, WillKeepItem4,
			WillKeepItem1Slot, WillKeepItem2Slot, WillKeepItem3Slot,
			WillKeepItem4Slot, EquipStatus;

	public void ResetKeepItems() {
		WillKeepAmt1 = -1;
		WillKeepItem1 = -1;
		WillKeepAmt2 = -1;
		WillKeepItem2 = -1;
		WillKeepAmt3 = -1;
		WillKeepItem3 = -1;
		WillKeepAmt4 = -1;
		WillKeepItem4 = -1;
	}

	public void StartBestItemScan(Client c) {
		if (c.isSkulled && !c.prayerActive[10]) {
			ItemKeptInfo(c, 0);
			return;
		}
		FindItemKeptInfo(c);
		ResetKeepItems();
		BestItem1(c);
	}

	public void FindItemKeptInfo(Client c) {
		if (isSkulled && c.prayerActive[10])
			ItemKeptInfo(c, 1);
		else if (!isSkulled && !c.prayerActive[10])
			ItemKeptInfo(c, 3);
		else if (!isSkulled && c.prayerActive[10])
			ItemKeptInfo(c, 4);
	}

	public void ItemKeptInfo(Client c, int Lose) {
		for (int i = 17109; i < 17131; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.getPA().sendFrame126("Items you will keep on death:", 17104);
		c.getPA().sendFrame126("Items you will lose on death:", 17105);
		c.getPA().sendFrame126("Player Information", 17106);
		c.getPA().sendFrame126("Max items kept on death:", 17107);
		c.getPA().sendFrame126("~ " + Lose + " ~", 17108);
		c.getPA().sendFrame126("The normal amount of", 17111);
		c.getPA().sendFrame126("items kept is three.", 17112);
		switch (Lose) {
		case 0:
		default:
			c.getPA().sendFrame126("Items you will keep on death:", 17104);
			c.getPA().sendFrame126("Items you will lose on death:", 17105);
			c.getPA().sendFrame126("You're marked with a", 17111);
			c.getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			c.getPA().sendFrame126("items you keep from", 17113);
			c.getPA().sendFrame126("three to zero!", 17114);
			break;
		case 1:
			c.getPA().sendFrame126("Items you will keep on death:", 17104);
			c.getPA().sendFrame126("Items you will lose on death:", 17105);
			c.getPA().sendFrame126("You're marked with a", 17111);
			c.getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			c.getPA().sendFrame126("items you keep from", 17113);
			c.getPA().sendFrame126("three to zero!", 17114);
			c.getPA().sendFrame126("However, you also have", 17115);
			c.getPA().sendFrame126("the @red@Protect @lre@Items prayer", 17116);
			c.getPA().sendFrame126("active, which saves you", 17117);
			c.getPA().sendFrame126("one extra item!", 17118);
			break;
		case 3:
			c.getPA().sendFrame126(
					"Items you will keep on death(if not skulled):", 17104);
			c.getPA().sendFrame126(
					"Items you will lose on death(if not skulled):", 17105);
			c.getPA().sendFrame126("You have no factors", 17111);
			c.getPA().sendFrame126("affecting the items you", 17112);
			c.getPA().sendFrame126("keep.", 17113);
			break;
		case 4:
			c.getPA().sendFrame126(
					"Items you will keep on death(if not skulled):", 17104);
			c.getPA().sendFrame126(
					"Items you will lose on death(if not skulled):", 17105);
			c.getPA().sendFrame126("You have the @red@Protect", 17111);
			c.getPA().sendFrame126("@red@Item @lre@prayer active,", 17112);
			c.getPA().sendFrame126("which saves you one", 17113);
			c.getPA().sendFrame126("extra item!", 17114);
			break;
		}
	}

	public void BestItem1(Client c) {
		int BestValue = 0;
		int NextValue = 0;
		int ItemsContained = 0;
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerItems[ITEM] - 1;
					WillKeepItem1Slot = ITEM;
					if (playerItemsN[ITEM] > 2 && !c.prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerItemsN[ITEM] > 3 && c.prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerEquipment[EQUIP];
					WillKeepItem1Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 && !c.prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerEquipmentN[EQUIP] > 3
							&& c.prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled
				&& ItemsContained > 1
				&& (WillKeepAmt1 < 3 || (c.prayerActive[10] && WillKeepAmt1 < 4))) {
			BestItem2(c, ItemsContained);
		}
	}

	public void BestItem2(Client c, int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerItems[ITEM] - 1;
					WillKeepItem2Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - WillKeepAmt1
							&& !c.prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerItemsN[ITEM] > 3 - WillKeepAmt1
							&& c.prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerEquipment[EQUIP];
					WillKeepItem2Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - WillKeepAmt1
							&& !c.prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1
							&& c.prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled
				&& ItemsContained > 2
				&& (WillKeepAmt1 + WillKeepAmt2 < 3 || (c.prayerActive[10] && WillKeepAmt1
						+ WillKeepAmt2 < 4))) {
			BestItem3(c, ItemsContained);
		}
	}

	public void BestItem3(Client c, int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerItems[ITEM] - 1;
					WillKeepItem3Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - (WillKeepAmt1 + WillKeepAmt2)
							&& !c.prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerItemsN[ITEM] > 3 - (WillKeepAmt1 + WillKeepAmt2)
							&& c.prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerEquipment[EQUIP];
					WillKeepItem3Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - (WillKeepAmt1 + WillKeepAmt2)
							&& !c.prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1
							&& c.prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 3 && c.prayerActive[10]
				&& ((WillKeepAmt1 + WillKeepAmt2 + WillKeepAmt3) < 4)) {
			BestItem4(c);
		}
	}

	public void BestItem4(Client c) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)
						&& !(ITEM == WillKeepItem3Slot && playerItems[ITEM] - 1 == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerItems[ITEM] - 1;
					WillKeepItem4Slot = ITEM;
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(c.getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)
						&& !(EQUIP + 28 == WillKeepItem3Slot && playerEquipment[EQUIP] == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerEquipment[EQUIP];
					WillKeepItem4Slot = EQUIP + 28;
				}
			}
		}
	}

	public final int[] BOWS = { 9185, 15003, 15041, 839, 845, 847, 851, 855,
			859, 841, 843, 849, 853, 857, 861, 4212, 4214, 14015, 4215, 11235,
			4216, 4217, 4218, 4219, 4220, 4221, 4222, 4223, 6724, 4734, 4934,
			4935, 4936, 4937 };
	public final int[] ARROWS = { 882, 884, 886, 888, 890, 892, 4740, 11212,
			9140, 9141, 4142, 9143, 9144, 9240, 9241, 9242, 9243, 9244, 9245 };
	public final int[] NO_ARROW_DROP = { 4212, 4214, 4215, 4216, 4217, 4218,
			4219, 4220, 4221, 4222, 4223, 4734, 4934, 4935, 4936, 4937 };
	public final int[] OTHER_RANGE_WEAPONS = { 863, 864, 865, 866, 867, 868,
			869, 806, 807, 808, 809, 810, 811, 825, 826, 827, 828, 829, 830,
			800, 801, 802, 803, 804, 805, 6522, 11230 };

	public final int[][] MAGIC_SPELLS = {
			// example {magicId, level req, animation, startGFX, projectile Id,
			// endGFX, maxhit, exp gained, rune 1, rune 1 amount, rune 2, rune 2
			// amount, rune 3, rune 3 amount, rune 4, rune 4 amount}

			// Modern Spells
			{ 1152, 1, 711, 90, 91, 92, 2, 5, 556, 1, 558, 1, 0, 0, 0, 0 }, // wind
																			// strike
			{ 1154, 5, 711, 93, 94, 95, 4, 7, 555, 1, 556, 1, 558, 1, 0, 0 }, // water
																				// strike
			{ 1156, 9, 711, 96, 97, 98, 6, 9, 557, 2, 556, 1, 558, 1, 0, 0 },// earth
																				// strike
			{ 1158, 13, 711, 99, 100, 101, 8, 11, 554, 3, 556, 2, 558, 1, 0, 0 }, // fire
																					// strike
			{ 1160, 17, 711, 117, 118, 119, 9, 13, 556, 2, 562, 1, 0, 0, 0, 0 }, // wind
																					// bolt
			{ 1163, 23, 711, 120, 121, 122, 10, 16, 556, 2, 555, 2, 562, 1, 0,
					0 }, // water bolt
			{ 1166, 29, 711, 123, 124, 125, 11, 20, 556, 2, 557, 3, 562, 1, 0,
					0 }, // earth bolt
			{ 1169, 35, 711, 126, 127, 128, 12, 22, 556, 3, 554, 4, 562, 1, 0,
					0 }, // fire bolt
			{ 1172, 41, 711, 132, 133, 134, 13, 25, 556, 3, 560, 1, 0, 0, 0, 0 }, // wind
																					// blast
			{ 1175, 47, 711, 135, 136, 137, 14, 28, 556, 3, 555, 3, 560, 1, 0,
					0 }, // water blast
			{ 1177, 53, 711, 138, 139, 140, 15, 31, 556, 3, 557, 4, 560, 1, 0,
					0 }, // earth blast
			{ 1181, 59, 711, 129, 130, 131, 16, 35, 556, 4, 554, 5, 560, 1, 0,
					0 }, // fire blast
			{ 1183, 62, 711, 158, 159, 160, 17, 36, 556, 5, 565, 1, 0, 0, 0, 0 }, // wind
																					// wave
			{ 1185, 65, 711, 161, 162, 163, 18, 37, 556, 5, 555, 7, 565, 1, 0,
					0 }, // water wave
			{ 1188, 70, 711, 164, 165, 166, 19, 40, 556, 5, 557, 7, 565, 1, 0,
					0 }, // earth wave
			{ 1189, 75, 711, 155, 156, 157, 20, 42, 556, 5, 554, 7, 565, 1, 0,
					0 }, // fire wave
			{ 1153, 3, 716, 102, 103, 104, 0, 13, 555, 3, 557, 2, 559, 1, 0, 0 }, // confuse
			{ 1157, 11, 716, 105, 106, 107, 0, 20, 555, 3, 557, 2, 559, 1, 0, 0 }, // weaken
			{ 1161, 19, 716, 108, 109, 110, 0, 29, 555, 2, 557, 3, 559, 1, 0, 0 }, // curse
			{ 1542, 66, 729, 167, 168, 169, 0, 76, 557, 5, 555, 5, 566, 1, 0, 0 }, // vulnerability
			{ 1543, 73, 729, 170, 171, 172, 0, 83, 557, 8, 555, 8, 566, 1, 0, 0 }, // enfeeble
			{ 1562, 80, 729, 173, 174, 107, 0, 90, 557, 12, 555, 12, 556, 1, 0,
					0 }, // stun
			{ 1572, 20, 711, 177, 178, 181, 0, 30, 557, 3, 555, 3, 561, 2, 0, 0 }, // bind
			{ 1582, 50, 711, 177, 178, 180, 2, 60, 557, 4, 555, 4, 561, 3, 0, 0 }, // snare
			{ 1592, 79, 711, 177, 178, 179, 4, 90, 557, 5, 555, 5, 561, 4, 0, 0 }, // entangle
			{ 1171, 39, 724, 145, 146, 147, 15, 25, 556, 2, 557, 2, 562, 1, 0,
					0 }, // crumble undead
			{ 1539, 50, 708, 87, 88, 89, 25, 42, 554, 5, 560, 1, 0, 0, 0, 0 }, // iban
																				// blast
			{ 12037, 50, 1576, 327, 328, 329, 19, 30, 560, 1, 558, 4, 0, 0, 0,
					0 }, // magic dart
			{ 1190, 60, 811, 0, 0, 76, 20, 60, 554, 2, 565, 2, 556, 4, 0, 0 }, // sara
																				// strike
			{ 1191, 60, 811, 0, 0, 77, 20, 60, 554, 1, 565, 2, 556, 4, 0, 0 }, // cause
																				// of
																				// guthix
			{ 1192, 60, 811, 0, 0, 78, 20, 60, 554, 4, 565, 2, 556, 1, 0, 0 }, // flames
																				// of
																				// zammy
			{ 12445, 85, 1819, 0, 344, 345, 0, 65, 563, 1, 562, 1, 560, 1, 0, 0 }, // teleblock

			// Ancient Spells
			{ 12939, 50, 1978, 0, 384, 385, 13, 30, 560, 2, 562, 2, 554, 1,
					556, 1 }, // smoke rush
			{ 12987, 52, 1978, 0, 378, 379, 14, 31, 560, 2, 562, 2, 566, 1,
					556, 1 }, // shadow rush
			{ 12901, 56, 1978, 0, 0, 373, 15, 33, 560, 2, 562, 2, 565, 1, 0, 0 }, // blood
																					// rush
			{ 12861, 58, 1978, 0, 360, 361, 16, 34, 560, 2, 562, 2, 555, 2, 0,
					0 }, // ice rush
			{ 12963, 62, 1979, 0, 0, 389, 19, 36, 560, 2, 562, 4, 556, 2, 554,
					2 }, // smoke burst
			{ 13011, 64, 1979, 0, 0, 382, 20, 37, 560, 2, 562, 4, 556, 2, 566,
					2 }, // shadow burst
			{ 12919, 68, 1979, 0, 0, 376, 21, 39, 560, 2, 562, 4, 565, 2, 0, 0 }, // blood
																					// burst
			{ 12881, 70, 1979, 0, 0, 363, 22, 40, 560, 2, 562, 4, 555, 4, 0, 0 }, // ice
																					// burst
			{ 12951, 74, 1978, 0, 386, 387, 23, 42, 560, 2, 554, 2, 565, 2,
					556, 2 }, // smoke blitz
			{ 12999, 76, 1978, 0, 380, 381, 24, 43, 560, 2, 565, 2, 556, 2,
					566, 2 }, // shadow blitz
			{ 12911, 80, 1978, 0, 374, 375, 25, 45, 560, 2, 565, 4, 0, 0, 0, 0 }, // blood
																					// blitz
			{ 12871, 82, 1978, 366, 0, 367, 26, 46, 560, 2, 565, 2, 555, 3, 0,
					0 }, // ice blitz
			{ 12975, 86, 1979, 0, 0, 391, 27, 48, 560, 4, 565, 2, 556, 4, 554,
					4 }, // smoke barrage
			{ 13023, 88, 1979, 0, 0, 383, 28, 49, 560, 4, 565, 2, 556, 4, 566,
					3 }, // shadow barrage
			{ 12929, 92, 1979, 0, 0, 377, 29, 51, 560, 4, 565, 4, 566, 1, 0, 0 }, // blood
																					// barrage
			{ 12891, 94, 1979, 0, 0, 369, 30, 52, 560, 4, 565, 2, 555, 6, 0, 0 }, // ice
																					// barrage

			{ -1, 80, 811, 301, 0, 0, 0, 0, 554, 3, 565, 3, 556, 3, 0, 0 }, // charge
			{ -1, 21, 712, 112, 0, 0, 0, 10, 554, 3, 561, 1, 0, 0, 0, 0 }, // low
																			// alch
			{ -1, 55, 713, 113, 0, 0, 0, 20, 554, 5, 561, 1, 0, 0, 0, 0 }, // high
																			// alch
			{ -1, 33, 728, 142, 143, 144, 0, 35, 556, 1, 563, 1, 0, 0, 0, 0 } // telegrab

	};

	public boolean isAutoButton(int button) {
		for (int j = 0; j < autocastIds.length; j += 2) {
			if (autocastIds[j] == button)
				return true;
		}
		return false;
	}

	/* Start of combat variables */

	public boolean multiAttacking, rangeEndGFXHeight, playerFletch,
			playerIsFletching, playerIsMining, playerIsFiremaking,
			playerIsFishing, playerIsCooking;
	public boolean below459 = true, defaultWealthTransfer, updateInventory,
			oldSpec, stopPlayerSkill, playerStun, stopPlayerPacket, usingClaws;
	public boolean playerBFishing, finishedBarbarianTraining, ignoreDefence,
			secondFormAutocast, usingArrows, usingOtherRangeWeapons,
			usingCross, magicDef, spellSwap, recoverysSet;
	public int rangeEndGFX, boltDamage, teleotherType, playerTradeWealth,
			woodcuttingTree, stageT, dfsCount, recoilHits, playerDialogue,
			clawDelay, previousDamage;
	public boolean protectItem = false;
	public int doAmount, itemToDelete, itemToAdd;
	public int lastClickedTab;
	public int destination;

	/* End of combat variables */

	public int[] autocastIds = { 51133, 32, 51185, 33, 51091, 34, 24018, 35,
			51159, 36, 51211, 37, 51111, 38, 51069, 39, 51146, 40, 51198, 41,
			51102, 42, 51058, 43, 51172, 44, 51224, 45, 51122, 46, 51080, 47,
			7038, 0, 7039, 1, 7040, 2, 7041, 3, 7042, 4, 7043, 5, 7044, 6,
			7045, 7, 7046, 8, 7047, 9, 7048, 10, 7049, 11, 7050, 12, 7051, 13,
			7052, 14, 7053, 15, 47019, 27, 47020, 25, 47021, 12, 47022, 13,
			47023, 14, 47024, 15 };

	// public String spellName = "Select Spell";
	public void assignAutocast(int button) {
		for (int j = 0; j < autocastIds.length; j++) {
			if (autocastIds[j] == button) {
				Client c = (Client) PlayerHandler.players[this.playerId];
				autocasting = true;
				autocastId = autocastIds[j + 1];
				c.getPA().sendFrame36(108, 1);
				c.setSidebarInterface(0, 328);
				// spellName = getSpellName(autocastId);
				// spellName = spellName;
				// c.getPA().sendFrame126(spellName, 354);
				c = null;
				break;
			}
		}
	}

	public int getLocalX() {
		return getX() - 8 * getMapRegionX();
	}

	public int getLocalY() {
		return getY() - 8 * getMapRegionY();
	}

	public String getSpellName(int id) {
		switch (id) {
		case 0:
			return "Air Strike";
		case 1:
			return "Water Strike";
		case 2:
			return "Earth Strike";
		case 3:
			return "Fire Strike";
		case 4:
			return "Air Bolt";
		case 5:
			return "Water Bolt";
		case 6:
			return "Earth Bolt";
		case 7:
			return "Fire Bolt";
		case 8:
			return "Air Blast";
		case 9:
			return "Water Blast";
		case 10:
			return "Earth Blast";
		case 11:
			return "Fire Blast";
		case 12:
			return "Air Wave";
		case 13:
			return "Water Wave";
		case 14:
			return "Earth Wave";
		case 15:
			return "Fire Wave";
		case 32:
			return "Shadow Rush";
		case 33:
			return "Smoke Rush";
		case 34:
			return "Blood Rush";
		case 35:
			return "Ice Rush";
		case 36:
			return "Shadow Burst";
		case 37:
			return "Smoke Burst";
		case 38:
			return "Blood Burst";
		case 39:
			return "Ice Burst";
		case 40:
			return "Shadow Blitz";
		case 41:
			return "Smoke Blitz";
		case 42:
			return "Blood Blitz";
		case 43:
			return "Ice Blitz";
		case 44:
			return "Shadow Barrage";
		case 45:
			return "Smoke Barrage";
		case 46:
			return "Blood Barrage";
		case 47:
			return "Ice Barrage";
		default:
			return "Select Spell";
		}
	}

	public boolean fullVoidRange() {
		return playerEquipment[playerHat] == 11664
				&& playerEquipment[playerLegs] == 8840
				&& playerEquipment[playerChest] == 8839
				&& playerEquipment[playerHands] == 8842;
	}

	public boolean fullVerac() {
		return playerEquipment[playerHat] == 4753
				&& playerEquipment[playerLegs] == 4759
				&& playerEquipment[playerChest] == 4757
				&& playerEquipment[playerWeapon] == 4755;
	}

	public boolean fullFremmy() {
		return playerEquipment[playerHat] == 3748
				&& playerEquipment[playerWeapon] == 3757
				&& playerEquipment[playerShield] == 3758;
	}

	public boolean fullVoidMage() {
		return playerEquipment[playerHat] == 11663
				&& playerEquipment[playerLegs] == 8840
				&& playerEquipment[playerChest] == 8839
				&& playerEquipment[playerHands] == 8842;
	}

	public boolean fullVoidMelee() {
		return playerEquipment[playerHat] == 11665
				&& playerEquipment[playerLegs] == 8840
				&& playerEquipment[playerChest] == 8839
				&& playerEquipment[playerHands] == 8842;
	}

	public int[][] barrowsNpcs = { { 2030, 0 }, // verac
			{ 2029, 0 }, // toarg
			{ 2028, 0 }, // karil
			{ 2027, 0 }, // guthan
			{ 2026, 0 }, // dharok
			{ 2025, 0 } // ahrim
	};
	public int barrowsKillCount;

	public int reduceSpellId;
	public final int[] REDUCE_SPELL_TIME = { 250000, 250000, 250000, 500000,
			500000, 500000 }; // how long does the other player stay immune to
								// the spell
	public long[] reduceSpellDelay = new long[6];
	public final int[] REDUCE_SPELLS = { 1153, 1157, 1161, 1542, 1543, 1562 };
	public boolean[] canUseReducingSpell = { true, true, true, true, true, true };

	public int slayerTask, taskAmount;

	public int prayerId = -1;
	public int headIcon = -1;
	public int bountyIcon = 0;
	public long stopPrayerDelay, prayerDelay;
	public boolean usingPrayer;
	public final int[] PRAYER_DRAIN_RATE = { 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500 };
	public final int[] PRAYER_LEVEL_REQUIRED = { 1, 4, 7, 8, 9, 10, 13, 16, 19,
			22, 25, 26, 27, 28, 31, 34, 37, 40, 43, 44, 45, 46, 49, 52, 60, 70 };
	public final int[] PRAYER = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	public final String[] PRAYER_NAME = { "Thick Skin", "Burst of Strength",
			"Clarity of Thought", "Sharp Eye", "Mystic Will", "Rock Skin",
			"Superhuman Strength", "Improved Reflexes", "Rapid Restore",
			"Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore",
			"Steel Skin", "Ultimate Strength", "Incredible Reflexes",
			"Protect from Magic", "Protect from Missiles",
			"Protect from Melee", "Eagle Eye", "Mystic Might", "Retribution",
			"Redemption", "Smite", "Chivalry", "Piety" };
	public final int[] PRAYER_GLOW = { 83, 84, 85, 601, 602, 86, 87, 88, 89,
			90, 91, 603, 604, 92, 93, 94, 95, 96, 97, 605, 606, 98, 99, 100,
			607, 608 };
	public final int[] PRAYER_HEAD_ICONS = { -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, 2, 1, 0, -1, -1, 3, 5, 4, -1, -1 };
	// {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3,2,1,4,6,5};

	public boolean[] prayerActive = { false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false };

	public int duelTimer, duelTeleX, duelTeleY, duelSlot, duelSpaceReq,
			duelOption, duelingWith, duelStatus;
	public int headIconPk = -1, headIconHints;
	public boolean duelRequested;
	public boolean[] duelRule = new boolean[22];
	public final int[] DUEL_RULE_ID = { 1, 2, 16, 32, 64, 128, 256, 512, 1024,
			4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 2097152,
			8388608, 16777216, 67108864, 134217728 };

	public boolean doubleHit, usingSpecial, npcDroppingItems, usingRangeWeapon,
			usingBow, usingMagic, castingMagic;
	public int specMaxHitIncrease, freezeDelay, freezeTimer = -6, killerId,
			playerIndex, oldPlayerIndex, lastWeaponUsed, projectileStage,
			crystalBowArrowCount, playerMagicBook, teleGfx, teleEndAnimation,
			teleHeight, teleX, teleY, rangeItemUsed, killingNpcIndex,
			totalDamageDealt, oldNpcIndex, fightMode, attackTimer, npcIndex,
			npcClickIndex, npcType, castingSpellId, oldSpellId, spellId,
			hitDelay;
	public boolean magicFailed, oldMagicFailed;
	public int bowSpecShot, clickNpcType, clickObjectType, objectId, objectX,
			objectY, objectXOffset, objectYOffset, objectDistance;
	public int pItemX, pItemY, pItemId;
	public boolean isMoving, walkingToItem;
	public boolean updateShop;
	public int myShopId;
	public boolean openDuel = false;
	public int tradeStatus, tradeWith;
	public boolean forcedChatUpdateRequired, inDuel, tradeAccepted, goodTrade,
			tradeRequested, tradeResetNeeded, tradeConfirmed, tradeConfirmed2,
			canOffer, acceptTrade, acceptedTrade;
	public int attackAnim, animationRequest = -1, animationWaitCycles;
	public int[] playerBonus = new int[12];
	public boolean isRunning2 = true;
	public boolean takeAsNote;
	public int combatLevel;
	public boolean saveFile = false;
	public int playerAppearance[] = new int[13];
	public int apset;
	public int actionID;
	public int wearItemTimer, wearId, wearSlot, interfaceId;
	public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

	public int tutorial = 15;
	// public int[] woodcut = new int [3];
	public int[] woodcut = new int[7];
	public int wcTimer = 0;
	public int[] mining = new int[3];
	public int miningTimer = 0;
	public boolean fishing = false;
	public int fishTimer = 0;
	public int smeltType; // 1 = bronze, 2 = iron, 3 = steel, 4 = gold, 5 =
							// mith, 6 = addy, 7 = rune
	public int smeltAmount;
	public int smeltTimer = 0;
	public boolean smeltInterface;
	public boolean patchCleared;
	public int[] farm = new int[2];

	public boolean antiFirePot = false;
	public boolean extendedAntiFirePot = false;

	/**
	 * Castle Wars
	 */
	public int castleWarsTeam;
	public boolean inCwGame;
	public boolean inCwWait;

	/**
	 * Fight Pits
	 */
	public boolean inPits = false;
	public int pitsStatus = 0;

	/**
	 * SouthWest, NorthEast, SouthWest, NorthEast
	 */

	public boolean isInTut() {
		if (absX >= 2625 && absX <= 2687 && absY >= 4670 && absY <= 4735) {
			return true;
		}
		return false;
	}

	public boolean inBarrows() {
		if (absX > 3520 && absX < 3598 && absY > 9653 && absY < 9750) {
			return true;
		}
		return false;
	}

	public boolean inArea(int x, int y, int x1, int y1) {
		if (absX > x && absX < x1 && absY < y && absY > y1) {
			return true;
		}
		return false;
	}

	public boolean Area(final int x1, final int x2, final int y1, final int y2) {
		return (absX >= x1 && absX <= x2 && absY >= y1 && absY <= y2);
	}

	public boolean inBank() {
		return Area(3090, 3099, 3487, 3500) || Area(3089, 3090, 3492, 3498)
				|| Area(3248, 3258, 3413, 3428) || Area(3179, 3191, 3432, 3448)
				|| Area(2944, 2948, 3365, 3374) || Area(2942, 2948, 3367, 3374)
				|| Area(2944, 2950, 3365, 3370) || Area(3008, 3019, 3352, 3359)
				|| Area(3017, 3022, 3352, 3357) || Area(3203, 3213, 3200, 3237)
				|| Area(3212, 3215, 3200, 3235) || Area(3215, 3220, 3202, 3235)
				|| Area(3220, 3227, 3202, 3229) || Area(3227, 3230, 3208, 3226)
				|| Area(3226, 3228, 3230, 3211) || Area(3227, 3229, 3208, 3226);
	}

	 	public boolean inJail() {
		if (absX > 3050 && absX < 3062 && absY > 4967 && absY < 4980) {
			return true;
		}
		return false;
	}
	
		public boolean isInJail() {		
		if(absX >= 3093 && absX <= 3112 && absY >= 9507 && absY <= 9526) {
			return true;
		}
		return false;
	}

	public boolean inRFD() // If player in rfd cooredinates
	{
		return absX >= 1885 && absX <= 1913 && absY >= 5340 && absY <= 5369;
	}

	public boolean varrockPK2() {
		if (absY >= 3433 && absY <= 3447 && absX >= 3180 && absX <= 3186) {
			return false;
		}
		if (absY >= 3400 && absY <= 3468 && absX >= 3162 && absX <= 3276) {
			return true;
		}
		return false;
	}

	public boolean isInWG() {
		if (absX > 2837 && absX < 2877 && absY > 3542 && absY < 3557
				|| absX > 2846 && absX < 2878 && absY > 3530 && absY < 3542) {
			return true;
		}
		return false;
	}

	public boolean gwdCoords() {
		if (heightLevel == 2 || heightLevel == 0) {
			if (absX >= 2800 && absX <= 2950 && absY >= 5200 && absY <= 5400) {
				return true;
			}
		}
		return false;
	}

	public boolean inWild() {
		if (absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966
				|| absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {
			return true;
		}
		return false;
	}

	public boolean safeAreas(int x, int y, int x1, int y1) {
		return (absX >= x && absX <= x1 && absY >= y && absY <= y1);
	}

	public boolean inNoBankNoSpawn() {
		if ((absX >= 3088 && absX <= 3099 && absY >= 3514 && absY <= 3515)
				|| (absX >= 3089 && absX <= 3090 && absY >= 3505 && absY <= 3514)
				|| (absX >= 2899 && absX <= 2928 && absY >= 4435 && absY <= 4463)
				|| (absX >= 2910 && absX <= 2940 && absY >= 5310 && absY <= 5350)
				|| (absX >= 2875 && absX <= 2900 && absY >= 5368 && absY <= 5400)
				|| (absX >= 2906 && absX <= 2946 && absY >= 5257 && absY <= 5297)
				|| (absX >= 2896 && absX <= 2937 && absY >= 3617 && absY <= 3657)
				|| (absX >= 1885 && absX <= 1985 && absY >= 4407 && absY <= 4507)
				|| (absX >= 2885 && absX <= 2956 && absY >= 3600 && absY <= 3655)
				|| (absX >= 2539 && absX <= 2939 && absY >= 4886 && absY <= 5286)
				|| (absX > 1985 && absX < 2885 && absY > 9337 && absY < 10345)) {
			return true;
		}
		return false;
	}

	public boolean isInHighRiskPK() {
		if ((absX >= 2560 && absX <= 2638 && absY >= 3130 && absY <= 3200 && heightLevel == 4)
				|| (absX >= 3440 && absX <= 3550 && absY >= 9470 && absY <= 9530)) {
			return true;
		}
		return false;
	}

	public boolean inNoProtectItem() {
		if (absX >= 3088 && absX <= 3090 && absY >= 3486 && absY <= 3488) {
			return true;
		}
		return false;
	}

	public boolean inFunPk() {
		if (absX >= 2890 && absX <= 2926 && absY >= 3590 && absY <= 3630) {
			return true;
		}
		return false;
	}

	public boolean inPcBoat() {
		return absX >= 2660 && absX <= 2663 && absY >= 2638 && absY <= 2643;
	}

	public boolean inPcGame() {
		return absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619;
	}

	public boolean isInPc() {
		if (absX > 2624 && absX < 2688 && absY > 2560 && absY < 2622) {
			return true;
		}
		return false;
	}

	public boolean isInBarrows() {
		if (absX > 3543 && absX < 3584 && absY > 3265 && absY < 3311) {
			return true;
		}
		return false;
	}

	public boolean isInBarrows2() {
		if (absX > 3529 && absX < 3581 && absY > 9673 && absY < 9722) {
			return true;
		}
		return false;
	}

	public boolean arenas() {
		if (absX > 3331 && absX < 3391 && absY > 3242 && absY < 3260) {
			return true;
		}
		return false;
	}

	public boolean inDuelArena() {
		if ((absX > 3322 && absX < 3394 && absY > 3195 && absY < 3291)
				|| (absX > 3311 && absX < 3323 && absY > 3223 && absY < 3248)) {
			return true;
		}
		return false;
	}

	public boolean inMulti() {
		if ((absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607)
				||
				/**
				 * Gwd
				 */
				(absX >= 2800 && absX <= 2900 && absY >= 5300 && absY <= 5400)
				|| (absX >= 2800 && absX <= 3000 && absY >= 5200 && absY <= 5400)
				|| (absX >= 2900 && absX <= 3000 && absY >= 5300 && absY <= 5400)
				/*
			 * Sea troll queen
			 */
			||	(absX >= 2143 && absX <= 2157 && absY >= 5138 && absY <= 5148)
				|| (absX >= 2800 && absX <= 3000 && absY >= 5300 && absY <= 5400)
				||
				/**
				 * End GWD
				 */
				/**
				 * KQ
				 */
				(absX >= 3100 && absX <= 3700 && absY >= 9200 && absY <= 9700)
				||
				/**
				 * End KQ
				 */
				(absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839)
				|| (absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967)
				|| (absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967)
				|| (absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831)
				|| (absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903)
				|| (absX >= 2824 && absX <= 2944 && absY >= 5258 && absY <= 5369)
				|| (absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711)
				|| (absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647)
				|| (absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619)
				|| (absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117)
				|| (absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630)
				|| (absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464)
				|| (absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
			return true;
		}
		return false;
	}

	public boolean inFightCaves() {
		return absX >= 2360 && absX <= 2445 && absY >= 5045 && absY <= 5125;
	}

	public boolean inPirateHouse() {
		return absX >= 3038 && absX <= 3044 && absY >= 3949 && absY <= 3959;
	}

	public String connectedFrom = "";
	// public static String addr = ((InetSocketAddress)
	// c.getSession().getRemoteAddress()).getAddress().getHostAddress();
	public String globalMessage = "";

	public abstract void initialize();

	public abstract void update();

	public int playerId = -1;
	public String playerName = null;
	public String playerName2 = null;
	public String playerPass = null;
	public int playerRights;
	public PlayerHandler handler = null;
	public int playerItems[] = new int[28];
	public int playerItemsN[] = new int[28];
	public int bankItems[] = new int[Config.BANK_SIZE];
	public int bankItemsN[] = new int[Config.BANK_SIZE];
	public boolean bankNotes = false;

	public int playerStandIndex = 0x328;
	public int playerTurnIndex = 0x337;
	public int playerWalkIndex = 0x333;
	public int playerTurn180Index = 0x334;
	public int playerTurn90CWIndex = 0x335;
	public int playerTurn90CCWIndex = 0x336;
	public int playerRunIndex = 0x338;

	public int playerHat = 0;
	public int playerCape = 1;
	public int playerAmulet = 2;
	public int playerWeapon = 3;
	public int playerChest = 4;
	public int playerShield = 5;
	public int playerLegs = 7;
	public int playerHands = 9;
	public int playerFeet = 10;
	public static int playerRing = 12;
	public int playerArrows = 13;

	public int playerAttack = 0;
	public int playerDefence = 1;
	public int playerStrength = 2;
	public int playerHitpoints = 3;
	public int playerRanged = 4;
	public int playerPrayer = 5;
	public int playerMagic = 6;
	public int playerCooking = 7;
	public int playerWoodcutting = 8;
	public int playerFletching = 9;
	public int playerFishing = 10;
	public int playerFiremaking = 11;
	public int playerCrafting = 12;
	public int playerSmithing = 13;
	public int playerMining = 14;
	public int playerHerblore = 15;
	public int playerAgility = 16;
	public int playerThieving = 17;
	public static int playerSlayer = 18;
	public int playerFarming = 19;
	public int playerRunecrafting = 20;

	public int[] playerEquipment = new int[14];
	public int[] playerEquipmentN = new int[14];
	public int[] playerLevel = new int[25];
	public int[] playerXP = new int[25];
	public int slayerTaskLevel, slaypoints;

	public void updateshop(int i) {
		Client p = (Client) PlayerHandler.players[playerId];
		p.getShops().resetShop(i);
	}

	public void println_debug(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public void println(String str) {
		System.out.println("[player-" + playerId + "]: " + str);
	}

	public Player(int _playerId) {
		playerId = _playerId;
		playerRights = 0;

		for (int i = 0; i < playerItems.length; i++) {
			playerItems[i] = 0;
		}
		for (int i = 0; i < playerItemsN.length; i++) {
			playerItemsN[i] = 0;
		}

		for (int i = 0; i < playerLevel.length; i++) {
			if (i == 3) {
				playerLevel[i] = 10;
			} else {
				playerLevel[i] = 1;
			}
		}

		for (int i = 0; i < playerXP.length; i++) {
			if (i == 3) {
				playerXP[i] = 1300;
			} else {
				playerXP[i] = 0;
			}
		}
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			bankItems[i] = 0;
		}

		for (int i = 0; i < Config.BANK_SIZE; i++) {
			bankItemsN[i] = 0;
		}

		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 1; // head
		playerAppearance[2] = 25;// Torso
		playerAppearance[3] = 26; // arms
		playerAppearance[4] = 34; // hands
		playerAppearance[5] = 38; // legs
		playerAppearance[6] = 42; // feet
		playerAppearance[7] = 14; // beard
		playerAppearance[8] = 3; // hair colour
		playerAppearance[9] = 0; // torso colour
		playerAppearance[10] = 0; // legs colour
		playerAppearance[11] = 3; // feet colour
		playerAppearance[12] = 2; // skin colour

		apset = 0;
		actionID = 0;

		playerEquipment[playerHat] = -1;
		playerEquipment[playerCape] = -1;
		playerEquipment[playerAmulet] = -1;
		playerEquipment[playerChest] = -1;
		playerEquipment[playerShield] = -1;
		playerEquipment[playerLegs] = -1;
		playerEquipment[playerHands] = -1;
		playerEquipment[playerFeet] = -1;
		playerEquipment[playerRing] = -1;
		playerEquipment[playerArrows] = -1;
		playerEquipment[playerWeapon] = -1;

		heightLevel = 0;

		teleportToX = Config.START_LOCATION_X;
		teleportToY = Config.START_LOCATION_Y;

		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public int[][] barrowCrypt = { { 4921, 0 }, { 2035, 0 } };

	void destruct() {
		playerListSize = 0;
		for (int i = 0; i < maxPlayerListSize; i++)
			playerList[i] = null;
		absX = absY = -1;
		mapRegionX = mapRegionY = -1;
		currentX = currentY = 0;
		resetWalkingQueue();
	}

	public static final int maxPlayerListSize = Config.MAX_PLAYERS;
	public Player playerList[] = new Player[maxPlayerListSize];
	public int playerListSize = 0;

	public byte playerInListBitmap[] = new byte[(Config.MAX_PLAYERS + 7) >> 3];

	public static final int maxNPCListSize = NPCHandler.maxNPCs;
	public NPC npcList[] = new NPC[maxNPCListSize];
	public int npcListSize = 0;

	public byte npcInListBitmap[] = new byte[(NPCHandler.maxNPCs + 7) >> 3];

	public boolean WithinDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((objectX + i) == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if ((objectX - i) == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean withinDistance(Player otherPlr) {
		if (heightLevel != otherPlr.heightLevel)
			return false;
		int deltaX = otherPlr.absX - absX, deltaY = otherPlr.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(NPC npc) {
		if (heightLevel != npc.heightLevel)
			return false;
		if (npc.needRespawn == true)
			return false;
		int deltaX = npc.absX - absX, deltaY = npc.absY - absY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public boolean withinDistance(int absX, int getY, int getHeightLevel) {
		if (this.getHeightLevel() != getHeightLevel)
			return false;
		int deltaX = this.getX() - absX, deltaY = this.getY() - getY;
		return deltaX <= 15 && deltaX >= -16 && deltaY <= 15 && deltaY >= -16;
	}

	public int getHeightLevel;

	public int getHeightLevel() {
		return getHeightLevel;
	}

	public int distanceToPoint(int pointX, int pointY) {
		return (int) Math.sqrt(Math.pow(absX - pointX, 2)
				+ Math.pow(absY - pointY, 2));
	}

	public int mapRegionX, mapRegionY;
	public int absX, absY;
	public int currentX, currentY;

	public int heightLevel;
	public int playerSE = 0x328;
	public int playerSEW = 0x333;
	public int playerSER = 0x334;

	public boolean updateRequired = true;

	public final int walkingQueueSize = 50;
	public int walkingQueueX[] = new int[walkingQueueSize],
			walkingQueueY[] = new int[walkingQueueSize];
	public int wQueueReadPtr = 0;
	public int wQueueWritePtr = 0;
	public boolean isRunning = true;
	public int teleportToX = -1, teleportToY = -1;

	public void resetWalkingQueue() {
		wQueueReadPtr = wQueueWritePtr = 0;

		for (int i = 0; i < walkingQueueSize; i++) {
			walkingQueueX[i] = currentX;
			walkingQueueY[i] = currentY;
		}
	}

	public void addToWalkingQueue(int x, int y) {
		// if (VirtualWorld.I(heightLevel, absX, absY, x, y, 0)) {
		int next = (wQueueWritePtr + 1) % walkingQueueSize;
		if (next == wQueueWritePtr)
			return;
		walkingQueueX[wQueueWritePtr] = x;
		walkingQueueY[wQueueWritePtr] = y;
		wQueueWritePtr = next;
		// }
	}

	public boolean goodDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((objectX + i) == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if ((objectX - i) == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& ((objectY + j) == playerY
								|| (objectY - j) == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNextWalkingDirection() {
		if (wQueueReadPtr == wQueueWritePtr)
			return -1;
		int dir;
		do {
			dir = Misc.direction(currentX, currentY,
					walkingQueueX[wQueueReadPtr], walkingQueueY[wQueueReadPtr]);
			if (dir == -1) {
				wQueueReadPtr = (wQueueReadPtr + 1) % walkingQueueSize;
			} else if ((dir & 1) != 0) {
				println_debug("Invalid waypoint in walking queue!");
				resetWalkingQueue();
				return -1;
			}
		} while ((dir == -1) && (wQueueReadPtr != wQueueWritePtr));
		if (dir == -1)
			return -1;
		dir >>= 1;
		currentX += Misc.directionDeltaX[dir];
		currentY += Misc.directionDeltaY[dir];
		absX += Misc.directionDeltaX[dir];
		absY += Misc.directionDeltaY[dir];
		if (isRunning()) {
			Client c = (Client) this;
			if (runEnergy > 0 && c.inWild()) {
				runEnergy -= 1;
				c.getPA().sendFrame126(runEnergy + "%", 149);
			} else if (runEnergy > 0) {
				//runEnergy -= 1;
				c.getPA().sendFrame126(runEnergy + "%", 149);
			} else {
				isRunning2 = false;
				c.getPA().setConfig(173, 0);
			}
		}
		return dir;
	}

	public int runEnergy = 100;
	public long lastRunRecovery;

	public boolean isRunning() {
		return isNewWalkCmdIsRunning() || (isRunning2 && isMoving);
	}

	public boolean didTeleport = false;
	public boolean mapRegionDidChange = false;
	public int dir1 = -1, dir2 = -1;
	public boolean createItems = false;
	public int poimiX = 0, poimiY = 0;

	public synchronized void getNextPlayerMovement() {
		mapRegionDidChange = false;
		didTeleport = false;
		dir1 = dir2 = -1;

		if (teleportToX != -1 && teleportToY != -1) {
			mapRegionDidChange = true;
			if (mapRegionX != -1 && mapRegionY != -1) {
				int relX = teleportToX - mapRegionX * 8, relY = teleportToY
						- mapRegionY * 8;
				if (relX >= 2 * 8 && relX < 11 * 8 && relY >= 2 * 8
						&& relY < 11 * 8)
					mapRegionDidChange = false;
			}
			if (mapRegionDidChange) {
				mapRegionX = (teleportToX >> 3) - 6;
				mapRegionY = (teleportToY >> 3) - 6;
			}
			currentX = teleportToX - 8 * mapRegionX;
			currentY = teleportToY - 8 * mapRegionY;
			absX = teleportToX;
			absY = teleportToY;
			resetWalkingQueue();

			teleportToX = teleportToY = -1;
			didTeleport = true;
		} else {
			dir1 = getNextWalkingDirection();
			if (dir1 == -1)
				return;
			if (isRunning) {
				dir2 = getNextWalkingDirection();
			}
			Client c = (Client) this;
			// c.sendMessage("Cycle Ended");
			int deltaX = 0, deltaY = 0;
			if (currentX < 2 * 8) {
				deltaX = 4 * 8;
				mapRegionX -= 4;
				mapRegionDidChange = true;
			} else if (currentX >= 11 * 8) {
				deltaX = -4 * 8;
				mapRegionX += 4;
				mapRegionDidChange = true;
			}
			if (currentY < 2 * 8) {
				deltaY = 4 * 8;
				mapRegionY -= 4;
				mapRegionDidChange = true;
			} else if (currentY >= 11 * 8) {
				deltaY = -4 * 8;
				mapRegionY += 4;
				mapRegionDidChange = true;
			}

			if (mapRegionDidChange/*
								 * && VirtualWorld.I(heightLevel, currentX,
								 * currentY, currentX + deltaX, currentY +
								 * deltaY, 0)
								 */) {
				currentX += deltaX;
				currentY += deltaY;
				for (int i = 0; i < walkingQueueSize; i++) {
					walkingQueueX[i] += deltaX;
					walkingQueueY[i] += deltaY;
				}
			}
			// CoordAssistant.processCoords(this);

		}
	}

	public void updateThisPlayerMovement(Stream str) {
		// synchronized(this) {
		if (mapRegionDidChange) {
			str.createFrame(73);
			str.writeWordA(mapRegionX + 6);
			str.writeWord(mapRegionY + 6);
		}

		if (didTeleport) {
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);
			str.writeBits(2, 3);
			str.writeBits(2, heightLevel);
			str.writeBits(1, 1);
			str.writeBits(1, (updateRequired) ? 1 : 0);
			str.writeBits(7, currentY);
			str.writeBits(7, currentX);
			return;
		}

		if (dir1 == -1) {
			// don't have to update the character position, because we're just
			// standing
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			isMoving = false;
			if (updateRequired) {
				// tell client there's an update block appended at the end
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
			if (DirectionCount < 50) {
				DirectionCount++;
			}
		} else {
			DirectionCount = 0;
			str.createFrameVarSizeWord(81);
			str.initBitAccess();
			str.writeBits(1, 1);

			if (dir2 == -1) {
				isMoving = true;
				str.writeBits(2, 1);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				if (updateRequired)
					str.writeBits(1, 1);
				else
					str.writeBits(1, 0);
			} else {
				isMoving = true;
				str.writeBits(2, 2);
				str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
				str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
				if (updateRequired)
					str.writeBits(1, 1);
				else
					str.writeBits(1, 0);
			}
		}

	}

	public void updatePlayerMovement(Stream str) {
		// synchronized(this) {
		if (dir1 == -1) {
			if (updateRequired || isChatTextUpdateRequired()) {

				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else
				str.writeBits(1, 0);
		} else if (dir2 == -1) {

			str.writeBits(1, 1);
			str.writeBits(2, 1);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1
					: 0);
		} else {

			str.writeBits(1, 1);
			str.writeBits(2, 2);
			str.writeBits(3, Misc.xlateDirectionToClient[dir1]);
			str.writeBits(3, Misc.xlateDirectionToClient[dir2]);
			str.writeBits(1, (updateRequired || isChatTextUpdateRequired()) ? 1
					: 0);
		}

	}

	public byte cachedPropertiesBitmap[] = new byte[(Config.MAX_PLAYERS + 7) >> 3];

	public void addNewNPC(NPC npc, Stream str, Stream updateBlock) {
		// synchronized(this) {
		int id = npc.npcId;
		npcInListBitmap[id >> 3] |= 1 << (id & 7);
		npcList[npcListSize++] = npc;

		str.writeBits(14, id);

		int z = npc.absY - absY;
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = npc.absX - absX;
		if (z < 0)
			z += 32;
		str.writeBits(5, z);

		str.writeBits(1, 0);
		str.writeBits(14, npc.npcType);

		boolean savedUpdateRequired = npc.updateRequired;
		npc.updateRequired = true;
		npc.appendNPCUpdateBlock(updateBlock);
		npc.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
	}
	
	public long joinTime = -1;
	public boolean newPlayer()
	{
		if (joinTime == -1)
			return false;
		else
			return (System.currentTimeMillis() - joinTime <= 1800000);
	}
	public String getTimeLeftForNP()
	{
		long millis = System.currentTimeMillis() - joinTime;
		int minutes = (int) ((millis/ 1000) / 30);
		return "About "+(30-minutes)+" minutes left in new player protection.";
	}

	public void addNewPlayer(Player plr, Stream str, Stream updateBlock) {
		// synchronized(this) {
		if (playerListSize >= 255) {
			return;
		}
		int id = plr.playerId;
		playerInListBitmap[id >> 3] |= 1 << (id & 7);
		playerList[playerListSize++] = plr;
		str.writeBits(11, id);
		str.writeBits(1, 1);
		boolean savedFlag = plr.isAppearanceUpdateRequired();
		boolean savedUpdateRequired = plr.updateRequired;
		plr.setAppearanceUpdateRequired(true);
		plr.updateRequired = true;
		plr.appendPlayerUpdateBlock(updateBlock);
		plr.setAppearanceUpdateRequired(savedFlag);
		plr.updateRequired = savedUpdateRequired;
		str.writeBits(1, 1);
		int z = plr.absY - absY;
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
		z = plr.absX - absX;
		if (z < 0)
			z += 32;
		str.writeBits(5, z);
	}

	protected static Stream playerProps;
	static {
		playerProps = new Stream(new byte[100]);
	}

	protected void appendPlayerAppearance(Stream str) {
		playerProps.currentOffset = 0;

		playerProps.writeByte(playerAppearance[0]);

		playerProps.writeByte(playerTitle.length() > 0 ? 1 : 0);
		playerProps.writeString(playerTitle);
		playerProps.writeByte(titleColor);
		playerProps.writeByte(headIcon);
		playerProps.writeByte(headIconPk);

		if (playerEquipment[playerHat] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerHat]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerCape] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerCape]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerAmulet] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerAmulet]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerWeapon] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerWeapon]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerChest] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerChest]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[2]);
		}

		if (playerEquipment[playerShield] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerShield]);
		} else {
			playerProps.writeByte(0);
		}

		if (!Item.isFullBody(playerEquipment[playerChest])) {
			playerProps.writeWord(0x100 + playerAppearance[3]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerLegs] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerLegs]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[5]);
		}

		if (!Item.isFullHelm(playerEquipment[playerHat])
				&& !Item.isFullMask(playerEquipment[playerHat])) {
			playerProps.writeWord(0x100 + playerAppearance[1]);
		} else {
			playerProps.writeByte(0);
		}

		if (playerEquipment[playerHands] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerHands]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[4]);
		}

		if (playerEquipment[playerFeet] > 1) {
			playerProps.writeWord(0x200 + playerEquipment[playerFeet]);
		} else {
			playerProps.writeWord(0x100 + playerAppearance[6]);
		}

		if (playerAppearance[0] != 1
				&& !Item.isFullMask(playerEquipment[playerHat])) {
			playerProps.writeWord(0x100 + playerAppearance[7]);
		} else {
			playerProps.writeByte(0);
		}

		playerProps.writeByte(playerAppearance[8]);
		playerProps.writeByte(playerAppearance[9]);
		playerProps.writeByte(playerAppearance[10]);
		playerProps.writeByte(playerAppearance[11]);
		playerProps.writeByte(playerAppearance[12]);
		playerProps.writeWord(playerStandIndex); // standAnimIndex
		playerProps.writeWord(playerTurnIndex); // standTurnAnimIndex
		playerProps.writeWord(playerWalkIndex); // walkAnimIndex
		playerProps.writeWord(playerTurn180Index); // turn180AnimIndex
		playerProps.writeWord(playerTurn90CWIndex); // turn90CWAnimIndex
		playerProps.writeWord(playerTurn90CCWIndex); // turn90CCWAnimIndex
		playerProps.writeWord(playerRunIndex); // runAnimIndex

		playerProps.writeQWord(Misc.playerNameToInt64(playerName));

		/*
		 * int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5); int ran = (int)
		 * ((getLevelForXP(playerXP[4])) * 1.5); int attstr = (int) ((double)
		 * (getLevelForXP(playerXP[0])) + (double)
		 * (getLevelForXP(playerXP[2])));
		 * 
		 * combatLevel = 0; if (ran > attstr) { combatLevel = (int)
		 * (((getLevelForXP(playerXP[1])) * 0.25) +
		 * ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5]))
		 * * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875)); } else if (mag >
		 * attstr) { combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
		 * + ((getLevelForXP(playerXP[3])) * 0.25) +
		 * ((getLevelForXP(playerXP[5])) * 0.125) +
		 * ((getLevelForXP(playerXP[6])) * 0.4875)); } else { combatLevel =
		 * (int) (((getLevelForXP(playerXP[1])) * 0.25) +
		 * ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5]))
		 * * 0.125) + ((getLevelForXP(playerXP[0])) * 0.325) +
		 * ((getLevelForXP(playerXP[2])) * 0.325)); }
		 */
		playerProps.writeByte(combatLevel); // combat level
		playerProps.writeWord(0);
		str.writeByteC(playerProps.currentOffset);
		str.writeBytes(playerProps.buffer, playerProps.currentOffset, 0);
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}

	private boolean chatTextUpdateRequired = false;
	private byte chatText[] = new byte[4096];
	private byte chatTextSize = 0;
	private int chatTextColor = 0;
	private int chatTextEffects = 0;

	protected void appendPlayerChatText(Stream str) {
		// synchronized(this) {
		str.writeWordBigEndian(((getChatTextColor() & 0xFF) << 8)
				+ (getChatTextEffects() & 0xFF));
		str.writeByte(playerRights);
		str.writeByteC(getChatTextSize());
		str.writeBytes_reverse(getChatText(), getChatTextSize(), 0);

	}

	public void forcedChat(String text) {
		forcedText = text;
		forcedChatUpdateRequired = true;
		updateRequired = true;
		setAppearanceUpdateRequired(true);
	}

	public String forcedText = "null";

	public void appendForcedChat(Stream str) {
		// synchronized(this) {
		str.writeString(forcedText);
	}

	/**
	 * Graphics
	 **/

	public int mask100var1 = 0;
	public int mask100var2 = 0;
	protected boolean mask100update = false;

	public void appendMask100Update(Stream str) {
		// synchronized(this) {
		str.writeWordBigEndian(mask100var1);
		str.writeDWord(mask100var2);

	}

	public void gfx100(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 6553600;
		mask100update = true;
		updateRequired = true;
	}

	public void gfx0(int gfx) {
		mask100var1 = gfx;
		mask100var2 = 65536;
		mask100update = true;
		updateRequired = true;
	}

	public boolean wearing2h() {
		Client c = (Client) this;
		String s = c.getItems().getItemName(c.playerEquipment[c.playerWeapon]);
		if (s.contains("2h"))
			return true;
		else if (s.contains("godsword"))
			return true;
		else if (s.contains("magic shortbow (upg)"))
			return true;
		return false;
	}

	/**
	 * Animations
	 **/
	public void startAnimation(int animId) {
		if (wearing2h() && animId == 829)
			return;
		animationRequest = animId;
		animationWaitCycles = 0;
		updateRequired = true;
	}

	public void startAnimation(int animId, int time) {
		animationRequest = animId;
		animationWaitCycles = time;
		updateRequired = true;
	}

	public void appendAnimationRequest(Stream str) {
		// synchronized(this) {
		str.writeWordBigEndian((animationRequest == -1) ? 65535
				: animationRequest);
		str.writeByteC(animationWaitCycles);

	}

	/**
	 * Face Update
	 **/

	protected boolean faceUpdateRequired = false;
	public int face = -1;
	public int FocusPointX = -1, FocusPointY = -1;

	public void faceUpdate(int index) {
		face = index;
		faceUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceUpdate(Stream str) {
		// synchronized(this) {
		str.writeWordBigEndian(face);

	}

	public void turnPlayerTo(int pointX, int pointY) {
		FocusPointX = 2 * pointX + 1;
		FocusPointY = 2 * pointY + 1;
		updateRequired = true;
	}

	private void appendSetFocusDestination(Stream str) {
		// synchronized(this) {
		str.writeWordBigEndianA(FocusPointX);
		str.writeWordBigEndian(FocusPointY);

	}

	/**
	 * Hit Update
	 **/

	protected void appendHitUpdate(Stream str) {
		// synchronized(this) {
		str.writeByte(getHitDiff()); // What the perseon got 'hit' for
		if (poisonMask == 1) {
			str.writeByteA(2);
		} else if (getHitDiff() > 0) {
			str.writeByteA(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteA(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[3] <= 0) {
			playerLevel[3] = 0;
			isDead = true;
		}
		str.writeByteC(playerLevel[3]); // Their current hp, for HP bar
		str.writeByte(getLevelForXP(playerXP[3])); // Their max hp, for HP bar

	}

	protected void appendHitUpdate2(Stream str) {
		// synchronized(this) {
		str.writeByte(hitDiff2); // What the perseon got 'hit' for
		if (poisonMask == 2) {
			str.writeByteS(2);
			poisonMask = -1;
		} else if (hitDiff2 > 0) {
			str.writeByteS(1); // 0: red hitting - 1: blue hitting
		} else {
			str.writeByteS(0); // 0: red hitting - 1: blue hitting
		}
		if (playerLevel[3] <= 0) {
			playerLevel[3] = 0;
			isDead = true;
		}
		str.writeByte(playerLevel[3]); // Their current hp, for HP bar
		str.writeByteC(getLevelForXP(playerXP[3])); // Their max hp, for HP bar

	}

	public void appendPlayerUpdateBlock(Stream str) {
		// synchronized(this) {
		if (!updateRequired && !isChatTextUpdateRequired())
			return; // nothing required
		int updateMask = 0;
		if (mask100update) {
			updateMask |= 0x100;
		}
		if (animationRequest != -1) {
			updateMask |= 8;
		}
		if (forcedChatUpdateRequired) {
			updateMask |= 4;
		}
		if (isChatTextUpdateRequired()) {
			updateMask |= 0x80;
		}
		if (isAppearanceUpdateRequired()) {
			updateMask |= 0x10;
		}
		if (faceUpdateRequired) {
			updateMask |= 1;
		}
		if (FocusPointX != -1) {
			updateMask |= 2;
		}
		if (isHitUpdateRequired()) {
			updateMask |= 0x20;
		}

		if (hitUpdateRequired2) {
			updateMask |= 0x200;
		}

		if (updateMask >= 0x100) {
			updateMask |= 0x40;
			str.writeByte(updateMask & 0xFF);
			str.writeByte(updateMask >> 8);
		} else {
			str.writeByte(updateMask);
		}

		// now writing the various update blocks itself - note that their order
		// crucial
		if (mask100update) {
			appendMask100Update(str);
		}
		if (animationRequest != -1) {
			appendAnimationRequest(str);
		}
		if (forcedChatUpdateRequired) {
			appendForcedChat(str);
		}
		if (isChatTextUpdateRequired()) {
			appendPlayerChatText(str);
		}
		if (faceUpdateRequired) {
			appendFaceUpdate(str);
		}
		if (isAppearanceUpdateRequired()) {
			appendPlayerAppearance(str);
		}
		if (FocusPointX != -1) {
			appendSetFocusDestination(str);
		}
		if (isHitUpdateRequired()) {
			appendHitUpdate(str);
		}
		if (hitUpdateRequired2) {
			appendHitUpdate2(str);
		}

	}

	public void clearUpdateFlags() {
		updateRequired = false;
		setChatTextUpdateRequired(false);
		setAppearanceUpdateRequired(false);
		setHitUpdateRequired(false);
		hitUpdateRequired2 = false;
		forcedChatUpdateRequired = false;
		mask100update = false;
		animationRequest = -1;
		FocusPointX = -1;
		FocusPointY = -1;
		poisonMask = -1;
		faceUpdateRequired = false;
		face = 65535;
	}

	public void stopMovement() {
		if (teleportToX <= 0 && teleportToY <= 0) {
			teleportToX = absX;
			teleportToY = absY;
		}
		newWalkCmdSteps = 0;
		getNewWalkCmdX()[0] = getNewWalkCmdY()[0] = travelBackX[0] = travelBackY[0] = 0;
		getNextPlayerMovement();
	}

	private int newWalkCmdX[] = new int[walkingQueueSize];
	private int newWalkCmdY[] = new int[walkingQueueSize];
	public int newWalkCmdSteps = 0;
	private boolean newWalkCmdIsRunning = false;
	protected int travelBackX[] = new int[walkingQueueSize];
	protected int travelBackY[] = new int[walkingQueueSize];
	protected int numTravelBackSteps = 0;

	public void preProcessing() {
		newWalkCmdSteps = 0;
	}

	public abstract void process();

	public abstract boolean processQueuedPackets();

	public synchronized void postProcessing() {
		if (newWalkCmdSteps > 0) {
			int firstX = getNewWalkCmdX()[0], firstY = getNewWalkCmdY()[0];

			int lastDir = 0;
			boolean found = false;
			numTravelBackSteps = 0;
			int ptr = wQueueReadPtr;
			int dir = Misc.direction(currentX, currentY, firstX, firstY);
			if (dir != -1 && (dir & 1) != 0) {
				do {
					lastDir = dir;
					if (--ptr < 0)
						ptr = walkingQueueSize - 1;

					travelBackX[numTravelBackSteps] = walkingQueueX[ptr];
					travelBackY[numTravelBackSteps++] = walkingQueueY[ptr];
					dir = Misc.direction(walkingQueueX[ptr],
							walkingQueueY[ptr], firstX, firstY);
					if (lastDir != dir) {
						found = true;
						break;
					}

				} while (ptr != wQueueWritePtr);
			} else
				found = true;

			if (!found)
				println_debug("Fatal: couldn't find connection vertex! Dropping packet.");
			else {
				wQueueWritePtr = wQueueReadPtr;

				addToWalkingQueue(currentX, currentY);

				if (dir != -1 && (dir & 1) != 0) {

					for (int i = 0; i < numTravelBackSteps - 1; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
					int wayPointX2 = travelBackX[numTravelBackSteps - 1], wayPointY2 = travelBackY[numTravelBackSteps - 1];
					int wayPointX1, wayPointY1;
					if (numTravelBackSteps == 1) {
						wayPointX1 = currentX;
						wayPointY1 = currentY;
					} else {
						wayPointX1 = travelBackX[numTravelBackSteps - 2];
						wayPointY1 = travelBackY[numTravelBackSteps - 2];
					}

					dir = Misc.direction(wayPointX1, wayPointY1, wayPointX2,
							wayPointY2);
					if (dir == -1 || (dir & 1) != 0) {
						println_debug("Fatal: The walking queue is corrupt! wp1=("
								+ wayPointX1
								+ ", "
								+ wayPointY1
								+ "), "
								+ "wp2=("
								+ wayPointX2
								+ ", "
								+ wayPointY2
								+ ")");
					} else {
						dir >>= 1;
						found = false;
						int x = wayPointX1, y = wayPointY1;
						while (x != wayPointX2 || y != wayPointY2) {
							x += Misc.directionDeltaX[dir];
							y += Misc.directionDeltaY[dir];
							if ((Misc.direction(x, y, firstX, firstY) & 1) == 0) {
								found = true;
								break;
							}
						}
						if (!found) {
							println_debug("Fatal: Internal error: unable to determine connection vertex!"
									+ "  wp1=("
									+ wayPointX1
									+ ", "
									+ wayPointY1
									+ "), wp2=("
									+ wayPointX2
									+ ", "
									+ wayPointY2
									+ "), "
									+ "first=("
									+ firstX + ", " + firstY + ")");
						} else
							addToWalkingQueue(wayPointX1, wayPointY1);
					}
				} else {
					for (int i = 0; i < numTravelBackSteps; i++) {
						addToWalkingQueue(travelBackX[i], travelBackY[i]);
					}
				}

				for (int i = 0; i < newWalkCmdSteps; i++) {
					addToWalkingQueue(getNewWalkCmdX()[i], getNewWalkCmdY()[i]);
				}

			}

			isRunning = isNewWalkCmdIsRunning() || isRunning2;
		}
	}

	public int getMapRegionX() {
		return mapRegionX;
	}

	public int getMapRegionY() {
		return mapRegionY;
	}

	public int getX() {
		return absX;
	}

	public int getY() {
		return absY;
	}

	public int getId() {
		return playerId;
	}

	public void setHitDiff(int hitDiff) {
		this.hitDiff = hitDiff;
	}

	public void setHitDiff2(int hitDiff2) {
		this.hitDiff2 = hitDiff2;
	}

	public int getHitDiff() {
		return hitDiff;
	}

	public void setHitUpdateRequired(boolean hitUpdateRequired) {
		this.hitUpdateRequired = hitUpdateRequired;
	}

	public void setHitUpdateRequired2(boolean hitUpdateRequired2) {
		this.hitUpdateRequired2 = hitUpdateRequired2;
	}

	public boolean isHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired() {
		return hitUpdateRequired;
	}

	public boolean getHitUpdateRequired2() {
		return hitUpdateRequired2;
	}

	public void setAppearanceUpdateRequired(boolean appearanceUpdateRequired) {
		this.appearanceUpdateRequired = appearanceUpdateRequired;
	}

	public boolean isAppearanceUpdateRequired() {
		return appearanceUpdateRequired;
	}

	public void setChatTextEffects(int chatTextEffects) {
		this.chatTextEffects = chatTextEffects;
	}

	public int getChatTextEffects() {
		return chatTextEffects;
	}

	public void setChatTextSize(byte chatTextSize) {
		this.chatTextSize = chatTextSize;
	}

	public byte getChatTextSize() {
		return chatTextSize;
	}

	public void setChatTextUpdateRequired(boolean chatTextUpdateRequired) {
		this.chatTextUpdateRequired = chatTextUpdateRequired;
	}

	public boolean isChatTextUpdateRequired() {
		return chatTextUpdateRequired;
	}

	public void setChatText(byte chatText[]) {
		this.chatText = chatText;
	}

	public byte[] getChatText() {
		return chatText;
	}

	public void setChatTextColor(int chatTextColor) {
		this.chatTextColor = chatTextColor;
	}

	public int getChatTextColor() {
		return chatTextColor;
	}

	public void setNewWalkCmdX(int newWalkCmdX[]) {
		this.newWalkCmdX = newWalkCmdX;
	}

	public int[] getNewWalkCmdX() {
		return newWalkCmdX;
	}

	public void setNewWalkCmdY(int newWalkCmdY[]) {
		this.newWalkCmdY = newWalkCmdY;
	}

	public int[] getNewWalkCmdY() {
		return newWalkCmdY;
	}

	public void setNewWalkCmdIsRunning(boolean newWalkCmdIsRunning) {
		this.newWalkCmdIsRunning = newWalkCmdIsRunning;
	}

	public boolean isNewWalkCmdIsRunning() {
		return newWalkCmdIsRunning;
	}

	private ISAACCipher inStreamDecryption = null, outStreamDecryption = null;

	public void setInStreamDecryption(ISAACCipher inStreamDecryption) {
		this.inStreamDecryption = inStreamDecryption;
	}

	public void setOutStreamDecryption(ISAACCipher outStreamDecryption) {
		this.outStreamDecryption = outStreamDecryption;
	}

	public boolean samePlayer() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName)) {
					disconnected = true;
					return true;
				}
			}
		}
		return false;
	}

	public void putInCombat(int attacker) {
		underAttackBy = attacker;
		logoutDelay = System.currentTimeMillis();
		singleCombatDelay = System.currentTimeMillis();
	}

	public void dealDamage(int damage) {
		if (teleTimer <= 0)
			playerLevel[3] -= damage;
		else {
			if (hitUpdateRequired)
				hitUpdateRequired = false;
			if (hitUpdateRequired2)
				hitUpdateRequired2 = false;
		}

	}

	public int slotNumber = 0;
	public int lockoutTime = 0;
	public int pinAttempts = 0;
	public String[] tempPin = new String[5];
	public String myPin = "";
	public boolean hasPin;
	public int currentTree;
	public int treeAmount;
	public int currentHerb;
	public int herbAmount;
	public int[] damageTaken = new int[Config.MAX_PLAYERS];

	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;
		}
		updateRequired = true;
	}

	public Object getPA() {
		// TODO Auto-generated method stub
		return null;
	}

}