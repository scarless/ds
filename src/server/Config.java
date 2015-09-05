package server;

import server.util.Misc;

/**
 * The Configuration File Of The Server
 * 
 * @author Sanity Revision by Shawn Notes by Shawn
 */
public class Config {

	/**
	 * Enable or disable server debugging.
	 */
	public static final boolean SOUND = true;
	public static final boolean SERVER_DEBUG = true;
	public static boolean sendServerPackets = false;
	public static boolean stakeDisabled = true;
	/**
	 * Your server name.
	 */
	public static final String SERVER_NAME = "Dragon-Scape";

	/**
	 * The welcome message displayed once logged in the server.
	 */
	public static final String WELCOME_MESSAGE = "Welcome to Dragon-Scape!!";

	/**
	 * A URL to your server forums. Not necessary needed.
	 */
	public static final String FORUMS = "www.dragon-scape.net/orum";

	public static boolean doubleEXPWeekend = false;
	/**
	 * The client version you are using. Not necessary needed.
	 */
	public static final int CLIENT_VERSION = 317;

	/**
	 * The delay it takes to type and or send a message.
	 */
	public static int MESSAGE_DELAY = 6000;

	/**
	 * The highest amount ID. Change is not needed here unless loading items
	 * higher than the 667 revision.
	 */
	public static final int ITEM_LIMIT = 25000;

	/**
	 * An integer needed for the above code.
	 */
	public static final int MAXITEM_AMOUNT = Integer.MAX_VALUE;

	/**
	 * The size of a players bank.
	 */
	public static final int BANK_SIZE = 352;

	/**
	 * The max amount of players until your server is full.
	 */
	public static final int MAX_PLAYERS = 300;

	/**
	 * The delay of logging in from connections. Do not change this.
	 */
	public static final int CONNECTION_DELAY = 100;

	/**
	 * How many IP addresses can connect from the same network until the server
	 * rejects another.
	 */
	public static final int IPS_ALLOWED = 2;

	/**
	 * Change to true if you want to stop the world --8. Can cause screen
	 * freezes on SilabSoft Clients. Change is not needed.
	 */
	public static final boolean WORLD_LIST_FIX = false;

	/**
	 * Items that can not be sold in any stores.
	 */
	public static final int[] ITEMS_KEPT_ON_DEATH = { 6570, 19111, 11663,
			11664, 11665, 8839, 8840, 8842, 7462, 7461, 10548, 10551, 20763,
			20764, 20771, 20772, 10548, 10551, 11014 };
	public static final int[] ITEM_SELLABLE = { 1050, 15000, 15001, 15002,
			15004, 15005, 15006, 15007, 1051, 1044, 1045, 1046, 1047, 1048,
			1049, 1052, 11704, 11705, 11696, 11697, 11724, 11725, 11694, 11726,
			11727, 11728, 11729, 3842, 3844, 3840, 8844, 8845, 8846, 8847,
			8848, 8849, 8850, 10551, 10548, 6570, 7462, 7461, 7460, 7459, 7458,
			7457, 7456, 7455, 7454, 9748, 9754, 9751, 9769, 9757, 9760, 9763,
			9802, 9808, 9784, 9799, 9805, 9781, 9796, 9793, 9775, 9772, 9778,
			9787, 9811, 9766, 9749, 9755, 9752, 9770, 9758, 9761, 9764, 9803,
			9809, 9785, 9800, 9806, 9782, 9797, 9794, 9776, 9773, 9779, 9788,
			9812, 9767, 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807,
			9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786, 9810,
			9765, 8839, 8840, 8842, 11663, 11664, 11665, 10499, 995, 11014 };

	/**
	 * Items that can not be traded or staked.
	 */
	public static final int[] ITEM_TRADEABLE = { 3842, 2692, 2677, 2678, 2679,
			2680, 2801, 2722, 15000, 15004, 15005, 15006, 15007, 15001, 15002,
			3844, 3840, 8844, 8845, 8846, 8847, 8848, 8849, 8850, 10551, 6570,
			7462, 7461, 7460, 7459, 7458, 7457, 7456, 7455, 7454, 9748, 9754,
			9751, 9769, 9757, 9760, 9763, 9802, 9808, 9784, 9799, 9805, 9781,
			9796, 9793, 9775, 9772, 9778, 9787, 9811, 9766, 9749, 9755, 9752,
			9770, 9758, 9761, 9764, 9803, 9809, 9785, 9800, 9806, 9782, 9797,
			9794, 9776, 9773, 9779, 9788, 9812, 9767, 9747, 9753, 9750, 9768,
			9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792,
			9774, 9771, 9777, 9786, 9810, 9765, 8839, 8840, 8842, 11663, 11664,
			11665, 10499, 11014 };

	/**
	 * Items that can not be dropped.
	 */
	public static final int[] UNDROPPABLE_ITEMS = { 15000, 15001, 15002, 15004,
			15005, 15006, 15007, 6570, 7462, 7461, 10551, 10548, 8839, 8840,
			8842, 11674, 11675, 11676, 11014 };

	/**
	 * Items that are listed as fun weapons for dueling.
	 */
	public static final int[] FUN_WEAPONS = { 2460, 2461, 2462, 2463, 2464,
			2465, 2466, 2467, 2468, 2469, 2470, 2471, 2471, 2473, 2474, 2475,
			2476, 2477 };

	/**
	 * If administrators can trade or not.
	 */
	public static final boolean ADMIN_CAN_TRADE = true;

	/**
	 * If administrators can sell items or not.
	 */
	public static final boolean ADMIN_CAN_SELL_ITEMS = true;

	/**
	 * If administrators can drop items or not.
	 */
	public static final boolean ADMIN_DROP_ITEMS = true;

	/**
	 * The starting location of your server.
	 */
	public static final int START_LOCATION_X = 3087;
	public static final int START_LOCATION_Y = 3504;

	/**
	 * The re-spawn point of when someone dies.
	 */
	public static final int RESPAWN_X = 3087;
	public static final int RESPAWN_Y = 3491;

	/**
	 * The re-spawn point of when a duel ends.
	 */
	public static final int DUELING_RESPAWN_X = 3362;
	public static final int DUELING_RESPAWN_Y = 3263;

	/**
	 * Fight Caves Respawn
	 */
	public static final int FIGHT_CAVE_RESPAWN_X = 2438;
	public static final int FIGHT_CAVE_RESPAWN_Y = 5168;

	/**
	 * The point in where you spawn in a duel. Do not change this.
	 */
	public static final int RANDOM_DUELING_RESPAWN = 0;

	/**
	 * The level in which you can not teleport in the wild, and higher.
	 */
	public static final int NO_TELEPORT_WILD_LEVEL = 20;

	/**
	 * The timer in which you are skulled goes away. Seconds x2 Ex. 60x2=120
	 * Skull timer would be 1 minute.
	 */
	public static final int SKULL_TIMER = 1200;

	/**
	 * How long the teleport block effect takes.
	 */
	public static final int TELEBLOCK_DELAY = 20000;

	/**
	 * Single and multi player killing zones.
	 */
	public static final boolean SINGLE_AND_MULTI_ZONES = true;

	/**
	 * Wilderness levels and combat level differences. Used when attacking
	 * players.
	 */
	public static final boolean COMBAT_LEVEL_DIFFERENCE = true;

	/**
	 * Combat level requirements needed to wield items.
	 */
	public static final boolean itemRequirements = true;

	/**
	 * Combat experience rates.
	 */
	public static final int EXP_GAINER = 30;
	public static final int MELEE_EXP_RATE = 1000; // damage * exp rate
	public static final int RANGE_EXP_RATE = 1000;
	public static final int MAGIC_EXP_RATE = 1000;

	/**
	 * Special server experience bonus rates. (Double experience weekend etc)
	 */
	public static double SERVER_EXP_BONUS = 1;

	/**
	 * How fast the special attack bar refills.
	 */
	public static final int INCREASE_SPECIAL_AMOUNT = 17500;

	/**
	 * If you need more than one prayer point to use prayer.
	 */
	public static final boolean PRAYER_POINTS_REQUIRED = true;

	/**
	 * If you need a certain prayer level to use a certain prayer.
	 */
	public static final boolean PRAYER_LEVEL_REQUIRED = true;

	/**
	 * If you need a certain magic level to use a certain spell.
	 */
	public static final boolean MAGIC_LEVEL_REQUIRED = true;

	/**
	 * How long the god charge spell lasts.
	 */
	public static final int GOD_SPELL_CHARGE = 300000;

	/**
	 * If you need runes to use magic spells.
	 */
	public static final boolean RUNES_REQUIRED = true;

	/**
	 * If you need correct arrows to use with bows.
	 */
	public static final boolean CORRECT_ARROWS = true;

	/**
	 * If the crystal bow degrades.
	 */
	public static final boolean CRYSTAL_BOW_DEGRADES = true;

	/**
	 * How often the server saves data.
	 */
	public static final int SAVE_TIMER = 120; // Saves every two minutes.

	/**
	 * How far NPCs can walk.
	 */
	public static final int NPC_RANDOM_WALK_DISTANCE = 5; // 5x5 square, NPCs
															// would be able to
															// walk 25 squares
															// around.

	/**
	 * How far NPCs can follow you when attacked.
	 */
	public static final int NPC_FOLLOW_DISTANCE = 10; // 10 squares

	/**
	 * NPCs that act as if they are dead. (For salve amulet, etc)
	 */
	public static final int[] UNDEAD_NPCS = { 90, 91, 92, 93, 94, 103, 104, 73,
			74, 75, 76, 77 };

	public static final int[][] commonDrops = { { 5291, 1 + Misc.random(5) },// common
			{ 5300, 1 + Misc.random(4) },// common
			{ 5301, 1 + Misc.random(4) },// common
			{ 5302, 1 + Misc.random(3) },// common
			{ 5303, 1 + Misc.random(3) },// common
			{ 995, 1 + Misc.random(200000) } // common
	};

	public static final int[][] uncommonDrops = { { 5316, 1 + Misc.random(5) },// uncommon
			{ 8779, 60 },// uncommon
			{ 989, 1 },// uncommon
			{ 2677, 1 },// uncommon
			{ 2678, 1 },// uncommon
			{ 2679, 1 },// uncommon
			{ 2680, 1 },// uncommon
			{ 384, 75 + Misc.random(5) },// uncommon
			{ 7945, 100 + Misc.random(5) },// uncommon
	};

	public static final int rareDrops[] = { 12006,// rare
			12002, 4081, 1800,// rare
			11907 // rare
	};

	/**
	 * What NPCs drop when dead.
	 */
	public static final int[][] NPC_DROPS = {
		// Barrow Boiiiiss
		
		
		
			/*// Verac
			{ 2030, 526, 1, 0 },
			{ 2030, 4753, 1, 80 },
			{ 2030, 4755, 1, 80 },
			{ 2030, 4757, 1, 80 },
			{ 2030, 4759, 1, 80 },
			// Torag
			{ 2029, 526, 1, 0 },
			{ 2029, 4745, 1, 80 },
			{ 2029, 4747, 1, 80 },
			{ 2029, 4749, 1, 80 },
			{ 2029, 4751, 1, 80 },
			// Karil
			{ 2028, 526, 1, 0 },
			{ 2028, 4740, 100, 10 },
			{ 2028, 4732, 1, 80 },
			{ 2028, 4734, 1, 80 },
			{ 2028, 4736, 1, 80 },
			{ 2028, 4738, 1, 80 },
			// Guthan
			{ 2027, 526, 1, 0 },
			{ 2027, 4724, 1, 80 },
			{ 2027, 4726, 1, 80 },
			{ 2027, 4728, 1, 80 },
			{ 2027, 4730, 1, 80 },
			// Dharok
			{ 2026, 526, 1, 0 },
			{ 2026, 4718, 1, 80 },
			{ 2026, 4720, 1, 80 },
			{ 2026, 4722, 1, 80 },
			{ 2026, 4716, 1, 80 },
			// Ahrims
			{ 2025, 526, 1, 0 },
			{ 2025, 4708, 1, 35 },
			{ 2025, 4710, 1, 35 },
			{ 2025, 4712, 1, 35 },
			{ 2025, 4714, 1, 35 },*/

			// Alkharid Warriors
			{ 18, 526, 1, 0 }, // bones
			{ 18, 995, 500 + Misc.random(500), 10 }, // coins
			{ 18, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 18, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 18, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 18, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			// { 18, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 18, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 18, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 18, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			// { 18, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			// { 18, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			// { 18, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			// { 18, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			// { 18, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			// { 18, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 18, 1437, 15 + Misc.random(10), 25 }, // rune ess
			{ 18, 1624, 1 + Misc.random(4), 13 }, // uncut sapphire
			{ 18, 1622, 1 + Misc.random(4), 16 }, // uncut emerald
			{ 18, 1620, 1 + Misc.random(4), 18 }, // uncut ruby
			{ 18, 1618, 1 + Misc.random(4), 30 }, // uncut diamond
			// { 18, 1632, 1 + Misc.random(4), 30 }, // uncut dstone

			// dagannoths
			{ 2455, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 2455, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 2455, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 2455, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 2455, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 2455, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 2455, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 2455, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 2455, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 2455, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 2455, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 2455, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 2455, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 2455, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 2455, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 2455, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 2455, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 2455, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 2455, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 2455, 1632, 1 + Misc.random(4), 30 }, // uncut dstone

			// Slayer Tower Monsters Floor 2
			// dust devil
			{ 1624, 592, 1, 0 }, // ashes
			{ 1624, 995, 1000 + Misc.random(2500), 7 }, // coins
			{ 1624, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 1624, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 1624, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 1624, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 1624, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 1624, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 1624, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 1624, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 1624, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 1624, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 1624, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 1624, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 1624, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 1624, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 1624, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 1624, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 1624, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 1624, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 1624, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 1624, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 1624, 985, 1, 150 }, // tooth key
			{ 1624, 987, 1, 150 }, // loop key
			// { 1624, 2713, 1, 85 }, //clue
			{ 1624, 3140, 1, 75 }, // dchain
			{ 1624, 1149, 1, 60 }, // dmed
			{ 1624, 1333, 1, 9 }, // rune scimitar
			{ 1624, 1247, 1, 10 }, // rune spear

			// Bloodveld
			{ 1618, 526, 1, 0 },
			{ 1618, 532, 1 + Misc.random(3), 8 }, // big bones
			{ 1618, 995, 1000 + Misc.random(5000), 7 }, // coins
			{ 1618, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 1618, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 1618, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 1618, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 1618, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 1618, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 1618, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 1618, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 1618, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 1618, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 1618, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 1618, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 1618, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 1618, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 1618, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 1618, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 1618, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 1618, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 1618, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 1618, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 1618, 985, 1, 150 }, // tooth key
			{ 1618, 987, 1, 150 }, // loop key
			// { 1618, 2713, 1, 85 }, //clue
			{ 1618, 1333, 1, 12 }, // rune scim
			{ 1618, 1247, 1, 11 }, // rune spear
			{ 1618, 830, 5 + Misc.random(15), 12 }, // rune javelin
			{ 1618, 1319, 1, 26 }, // rune 2h
			{ 1618, 4587, 1, 98 }, // dscimmy
			{ 1618, 1079, 1, 19 }, // rune platelegs
			{ 1618, 1147, 1, 20 }, // rune med helm
			{ 1618, 1149, 1, 70 }, // dmed helm

			// Slayer Tower Floor 3
			// Gargoyle
			// { 1610, 2713, 1, 85 }, //clue
			{ 1610, 526, 1, 0 }, // bones
			{ 1610, 995, 2500 + Misc.random(7500), 11 }, // coins
			// { 1610, 5291, 1 + Misc.random(2), 4 }, // guam seed
			// { 1610, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			// { 1610, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 1610, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 1610, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 1610, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 1610, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 1610, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 1610, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 1610, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 1610, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 1610, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 1610, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 1610, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 1610, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 1610, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 1610, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 1610, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 1610, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 1610, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 1610, 985, 1, 150 }, // tooth key
			{ 1610, 987, 1, 150 }, // loop key
			{ 1610, 1333, 1, 9 }, // rune scim
			{ 1610, 4153, 1, 120 }, // gmaul
			{ 1610, 4089, 1, 25 }, // mystic hat blue
			{ 1610, 4091, 1, 30 }, // mystic top blue
			{ 1610, 1333, 1, 12 }, // rune scim
			{ 1610, 1247, 1, 11 }, // rune spear
			{ 1610, 830, 5 + Misc.random(15), 18 }, // rune javelin
			{ 1610, 1319, 1, 26 }, // rune 2h
			{ 1610, 1079, 1, 19 }, // rune platelegs
			{ 1610, 1147, 1, 20 }, // rune med helm
			{ 1610, 1149, 1, 70 }, // dmed helm

			// Nechryeal
			{ 1613, 592, 1, 0 }, // ashes
			{ 1613, 995, 1000 + Misc.random(5000), 5 }, // coins
			// { 1613, 2713, 1, 85 }, //clue scroll
			// { 1613, 5291, 1 + Misc.random(2), 4 }, // guam seed
			// { 1613, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			// { 1613, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 1613, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 1613, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 1613, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 1613, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 1613, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 1613, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 1613, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 1613, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 1613, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 1613, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 1613, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 1613, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 1613, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 1613, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 1613, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 1613, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 1613, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 1613, 985, 1, 75 }, // tooth key
			{ 1613, 987, 1, 75 }, // loop key
			{ 1613, 11732, 1, 85 }, // dboots
			{ 1613, 4131, 1, 45 }, // rune boots
			{ 1613, 1315, 1, 10 }, // mithril 2h
			{ 1613, 1333, 1, 12 }, // rune scim
			{ 1613, 1247, 1, 11 }, // rune spear
			{ 1613, 830, 5 + Misc.random(15), 18 }, // rune javelin
			{ 1613, 1319, 1, 26 }, // rune 2h
			{ 1613, 1079, 1, 19 }, // rune platelegs
			{ 1613, 1147, 1, 20 }, // rune med helm
			// Giant Sea Snake
			{ 3943, 592, 1, 0 }, // ashes
			{ 3943, 6585, 1, 200 }, // fury
			{ 3943, 6737, 1, 200 }, // berserker ring
			{ 3943, 11920, 1, 400 }, // dpick
			{ 3943, 995, 10000 + Misc.random(5), 100 }, // coins
			{ 3943, 4081, 1, 650 }, // Salve Amulet
			{ 3943, 7478, 1, 1500 }, // dragon token
			{ 3943, 1437, 50, 25 }, // rune essence
			{ 3943, 1704, 1, 50 }, // glory
			{ 3943, 1079, 1, 50 }, // Rune platelegs
			{ 3943, 1127, 1, 50 }, // Rune platebody
			{ 3943, 990, 1, 85 }, // Rune platebody
			{ 3943, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 3943, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 3943, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 3943, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 3943, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 3943, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 3943, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 3943, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 3943, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 3943, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 3943, 1437, 15 + Misc.random(10), 20 }, // rune ess
			{ 3943, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 3943, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 3943, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 3943, 1618, 1 + Misc.random(4), 25 }, // uncut diamond
			{ 3943, 1632, 1 + Misc.random(4), 50 }, // uncut dstone
			{ 3943, 1800, 1, 850 }, // enchanted bottle

			// Barrelchest
			{ 5666, 532, 1, 0 }, // ashes
			{ 5666, 6585, 1, 250 }, // fury
			{ 5666, 10887, 1, 350 }, // anchor
			{ 5666, 990, 1, 85 }, // anchor
			{ 5666, 995, 10000 + Misc.random(5), 100 }, // coins
			{ 5666, 7478, 1, 1000 }, // dragon token
			{ 5666, 1437, 50, 25 }, // rune essence
			{ 5666, 1704, 1, 50 }, // glory
			{ 5666, 1079, 1, 50 }, // Rune platelegs
			{ 5666, 1127, 1, 50 }, // Rune platebody
			{ 5666, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 5666, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 5666, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 5666, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 5666, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 5666, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 5666, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 5666, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 5666, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 5666, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 5666, 1437, 15 + Misc.random(10), 20 }, // rune ess
			{ 5666, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 5666, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 5666, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 5666, 1618, 1 + Misc.random(4), 25 }, // uncut diamond
			{ 5666, 1632, 1 + Misc.random(4), 50 }, // uncut dstone

			// Abyssal Demon
			{ 1615, 592, 1, 0 }, // ashes
			{ 1615, 995, 5000 + Misc.random(3000), 11 }, // coins
			{ 1615, 2722, 1, 100 }, //clue scroll
			// { 1615, 5291, 1 + Misc.random(2), 4 }, // guam seed
			// { 1615, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			// { 1615, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 1615, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 1615, 5295, 1 + Misc.random(2), 39 }, // ranarr seed
			{ 1615, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 1615, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 1615, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 1615, 5299, 1 + Misc.random(2), 19 }, // kwuarm seed
			{ 1615, 5300, 1 + Misc.random(2), 29 }, // snapdragon seed
			{ 1615, 5301, 1 + Misc.random(2), 20 }, // cadantine seed
			{ 1615, 5302, 1 + Misc.random(2), 28 }, // lantadyme seed
			{ 1615, 5303, 1 + Misc.random(2), 40 }, // dwarfweed seed
			{ 1615, 5304, 1 + Misc.random(2), 67 }, // torstol seed
			{ 1615, 1437, 15 + Misc.random(30), 10 }, // rune ess
			{ 1615, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 1615, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 1615, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 1615, 1618, 1 + Misc.random(4), 15 }, // uncut diamond
			{ 1615, 1632, 1 + Misc.random(4), 25 }, // uncut dstone
			{ 1615, 985, 1, 120 }, // tooth key
			{ 1615, 987, 1, 150 }, // loop key
			{ 1615, 1333, 1, 25 }, // rune scim
			{ 1615, 1247, 1, 15 }, // rune spear
			{ 1615, 830, 15 + Misc.random(10), 9 }, // rune jav
			{ 1615, 1319, 1, 25 }, // rune 2h
			{ 1615, 4587, 1, 50 }, // dscim
			{ 1615, 1079, 1, 45 }, // rune platelegs
			{ 1615, 1147, 1, 35 }, // rune med
			{ 1615, 1149, 1, 50 }, // dmed
			{ 1615, 4151, 1, 275 }, // whip
			{ 1615, 1249, 1, 50 }, // dragon spear

			// Suqah
			{ 4532, 532, 1, 0 }, // big bones
			{ 4532, 9080, 1, 0 }, // suqah hide
			{ 4532, 9079, 1, 25 }, // suqah tooth
			{ 4532, 199, 1, 10 }, // grimy guam
			{ 4532, 201, 1, 15 }, // grimy marrentill
			{ 4532, 2677, 1, 75 }, // clue scroll
			{ 4532, 1623, 1, 15 }, // uncut sapphire
			{ 4532, 995, 15000 + Misc.random(6000), 11 }, // coins

			// Sea Troll Queen
			{ 3847, 592, 1, 0 }, // ashes
			{ 3847, 6585, 1, 200 }, // fury
			{ 3847, 3751, 1, 45 }, // berserker helm
			{ 3847, 3749, 1, 45 }, // archer helm
			{ 3847, 3753, 1, 45 }, // warrior helm
			{ 3847, 3755, 1, 45 }, // farseer helm
			{ 3847, 990, 3, 75 }, // crystal key x3
			{ 3847, 995, 10000 + Misc.random(10000), 50 }, // coins
			{ 3847, 7158, 1, 85 }, // dragon 2h
			{ 3847, 4081, 1, 450 }, // salve amulet

			// Dark Beast
			{ 2783, 526, 1, 0 }, // bones
			{ 2783, 995, 3000 + Misc.random(5000), 7 }, // coins
			{ 2783, 2722, 1, 100 }, //clue
			// { 2783, 5291, 1 + Misc.random(2), 4 }, // guam seed
			// { 2783, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			// { 2783, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 2783, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 2783, 5295, 1 + Misc.random(2), 39 }, // ranarr seed
			{ 2783, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 2783, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 2783, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 2783, 5299, 1 + Misc.random(2), 19 }, // kwuarm seed
			{ 2783, 5300, 1 + Misc.random(2), 29 }, // snapdragon seed
			{ 2783, 5301, 1 + Misc.random(2), 20 }, // cadantine seed
			{ 2783, 5302, 1 + Misc.random(2), 28 }, // lantadyme seed
			{ 2783, 5303, 1 + Misc.random(2), 40 }, // dwarfweed seed
			{ 2783, 5304, 1 + Misc.random(2), 67 }, // torstol seed
			{ 2783, 1437, 15 + Misc.random(30), 10 }, // rune ess
			{ 2783, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 2783, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 2783, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 2783, 1618, 1 + Misc.random(4), 15 }, // uncut diamond
			{ 2783, 1632, 1 + Misc.random(3), 21 }, // uncut dstone
			{ 2783, 985, 1, 120 }, // tooth key
			{ 2783, 987, 1, 150 }, // loop key
			{ 2783, 1333, 1, 25 }, // rune scim
			{ 2783, 1247, 1, 15 }, // rune spear
			{ 2783, 830, 15 + Misc.random(20), 9 }, // rune jav
			{ 2783, 1319, 1, 35 }, // rune 2h
			{ 2783, 4587, 1, 50 }, // dscim
			{ 2783, 1079, 1, 25 }, // rune legs
			{ 2783, 1147, 1, 15 }, // rune med
			{ 2783, 1149, 1, 50 }, // dmed
			{ 2783, 11235, 1, 300 }, // dark bow
			{ 2783, 11212, 1 + Misc.random(5), 17 }, // dragon arrows

			// Bosses

			// Bandos boss - General graardor
			{ 6260, 14001, 1, 2000 },
			{ 6260, 4834, 1, 0 },
			{ 6260, 561, 320 + Misc.random(120), 20 },
			{ 6260, 560, 210 + Misc.random(70), 15 },
			{ 6260, 995, 15000 + Misc.random(5000), 15 },
			{ 6260, 830, 100, 5 },
			{ 6260, 1201, 1, 10 },
			{ 6260, 1247, 1, 30 },
			{ 6260, 1303, 1, 3 },
			{ 6260, 1319, 1, 12 },
			{ 6260, 886, 500, 30 },
			{ 6260, 11704, 1, 400 },
			{ 6260, 11728, 1, 300 },
			{ 6260, 11724, 1, 400 },
			{ 6260, 11726, 1, 400 },
			{ 6260, 11710, 1, 300 },
			{ 6260, 11712, 1, 300 },
			{ 6260, 11714, 1, 300 },
			{ 6260, 1127, 1, 35 },
			{ 6260, 1514, 40, 28 },
			{ 6260, 5304, 1 + Misc.random(4), 20 },

			// Sergeant Grimspike
			{ 6265, 526, 1, 0 },
			{ 6265, 385, 2, 10 },
			{ 6265, 995, 13000 + Misc.random(1500), 5 },
			{ 6265, 11710, 1, 300 },
			{ 6265, 11712, 1, 300 },
			{ 6265, 11714, 1, 300 },
			{ 6265, 886, 320, 5 },
			{ 6265, 5304, 1 + Misc.random(4), 36 },
			{ 6265, 11724, 1, 1070 },
			{ 6265, 11726, 1, 1070 },
			{ 6265, 11728, 1, 1070 },

			// Sergeant Strongstack
			{ 6261, 526, 1, 0 },
			{ 6261, 385, 2, 10 },
			{ 6261, 995, 13000 + Misc.random(1500), 5 },
			{ 6261, 11710, 1, 300 },
			{ 6261, 11712, 1, 300 },
			{ 6261, 11714, 1, 300 },
			{ 6261, 886, 320, 5 },
			{ 6261, 5304, 1 + Misc.random(4), 36 },
			{ 6261, 11724, 1, 1070 },
			{ 6261, 11726, 1, 1070 },
			{ 6261, 11728, 1, 1070 },

			// Sergeant Steelwill
			{ 6263, 526, 1, 0 },
			{ 6263, 385, 2, 10 },
			{ 6263, 995, 13000 + Misc.random(1500), 5 },
			{ 6263, 11710, 1, 1070 },
			{ 6263, 11712, 1, 1070 },
			{ 6263, 11714, 1, 1070 },
			{ 6263, 5304, 1 + Misc.random(4), 36 },
			{ 6263, 886, 320, 5 },
			{ 6263, 11724, 1, 1070 },
			{ 6263, 11726, 1, 1070 },
			{ 6263, 11728, 1, 1070 },

			// Sara boss - Commander Zilyana
			{ 6247, 14003, 1, 2000 }, // pet
			{ 6247, 526, 1, 0 },
			{ 6247, 11706, 1, 350 }, // hilt
			{ 6247, 11730, 1, 400 }, // ss
			{ 6247, 15003, 1, 550 }, // acb
			{ 6247, 561, 210, 20 },
			{ 6247, 1149, 1, 40 },
			{ 6247, 560, 50 + Misc.random(40), 30 },
			{ 6247, 995, 15000 + Misc.random(5000), 20 },
			{ 6247, 1079, 1, 35 },
			{ 6247, 5304, 1 + Misc.random(4), 36 },

			// Bree
			{ 6252, 526, 1, 0 },
			{ 6252, 385, 2, 10 },
			{ 6252, 995, 13000 + Misc.random(1500), 5 },
			{ 6252, 11730, 1, 1070 },
			{ 6252, 11710, 1, 300 },
			{ 6252, 11712, 1, 300 },
			{ 6252, 11714, 1, 300 },
			{ 6252, 886, 320, 5 },
			{ 6262, 5304, 1 + Misc.random(4), 36 },

			// Starlight
			{ 6248, 526, 1, 0 },
			{ 6248, 385, 2, 10 },
			{ 6248, 995, 13000 + Misc.random(1500), 5 },
			{ 6248, 11730, 1, 1070 },
			{ 6248, 11710, 1, 300 },
			{ 6248, 11712, 1, 300 },
			{ 6248, 11714, 1, 300 },
			{ 6248, 886, 320, 5 },
			{ 6248, 5304, 1 + Misc.random(4), 36 },

			// Growler
			{ 6222, 526, 1, 0 },
			{ 6222, 385, 2, 10 },
			{ 6222, 995, 13000 + Misc.random(1500), 5 },
			{ 6222, 11730, 1, 1070 },
			{ 6222, 11710, 1, 300 },
			{ 6222, 11712, 1, 300 },
			{ 6222, 11714, 1, 300 },
			{ 6222, 886, 320, 5 },
			{ 6222, 5304, 1 + Misc.random(4), 36 },

			// Aramdyl Boss - Kree'arra
			{ 6222, 14000, 1, 2000 },
			{ 6222, 532, 1, 0 },
			{ 6222, 314, 1 + Misc.random(15), 5 },
			{ 6222, 9185, 1, 10 },
			{ 6222, 9244, 15 + Misc.random(5), 15 },
			{ 6222, 561, 167, 30 },
			{ 6222, 560, 41 + Misc.random(10), 30 },
			{ 6222, 11702, 1, 500 }, // hilt
			{ 6222, 11718, 1, 400 }, // helm
			{ 6222, 11720, 1, 400 }, // chest
			{ 6222, 11722, 1, 400 }, // legs
			{ 6222, 11710, 1, 300 },
			{ 6222, 11712, 1, 300 },
			{ 6222, 11714, 1, 300 },
			{ 6222, 1201, 1, 25 },
			{ 6222, 1149, 1, 40 },
			{ 6222, 5304, 1 + Misc.random(4), 24 },

			// Flight Kilisa
			{ 6227, 11722, 1, 1000 },
			{ 6227, 11720, 1, 1000 },
			{ 6227, 11718, 1, 1000 },
			{ 6227, 11710, 1, 300 },
			{ 6227, 11712, 1, 300 },
			{ 6227, 11714, 1, 300 },
			{ 6227, 995, 9500, 5 },
			{ 6227, 526, 1, 0 },
			{ 6227, 5304, 1 + Misc.random(4), 36 },

			// Flockleader Geerin
			{ 6225, 11722, 1, 1000 },
			{ 6225, 11720, 1, 1000 },
			{ 6225, 11718, 1, 1000 },
			{ 6227, 11710, 1, 300 },
			{ 6227, 11712, 1, 300 },
			{ 6227, 11714, 1, 300 },
			{ 6225, 995, 9500, 5 },
			{ 6225, 526, 1, 0 },
			{ 6225, 5304, 1 + Misc.random(4), 36 },

			// Wingman Skree
			{ 6223, 11722, 1, 1000 },
			{ 6223, 11720, 1, 1000 },
			{ 6223, 11718, 1, 1000 },
			{ 6227, 11710, 1, 300 },
			{ 6227, 11712, 1, 300 },
			{ 6227, 11714, 1, 300 },
			{ 6223, 995, 9500, 5 },
			{ 6223, 526, 1, 0 },
			{ 6223, 5304, 1 + Misc.random(4), 36 },

			// Zamorak boss - K'ril tsu
			{ 6203, 592, 1, 1 },
			{ 6203, 5626, 295, 5 },
			{ 6203, 1333, 1, 15 },
			{ 6203, 5680, 1, 15 },
			{ 6203, 11736, 1, 128 },
			{ 6203, 11716, 1, 300 }, // 128
			{ 6203, 1289, 1, 150 },
			{ 6203, 15110, 1, 512 },
			{ 6203, 1079, 1, 30 },
			{ 6203, 1123, 1, 25 },
			{ 6203, 146, 3, 10 },
			{ 6203, 3027, 3, 10 },
			{ 6203, 158, 3, 10 },
			{ 6203, 190, 3, 10 },
			{ 6203, 560, 120, 15 },
			{ 6203, 995, 9350, 5 },
			{ 6203, 565, 85, 10 },
			{ 6203, 2486, 7, 25 },
			{ 6203, 5302, 3, 25 },
			{ 6203, 11710, 1, 256 },
			{ 6203, 11712, 1, 256 },
			{ 6203, 11714, 1, 256 },
			{ 6203, 11708, 1, 512 },
			{ 6203, 14002, 1, 2500 },
			{ 6203, 2366, 1, 75 },
			{ 6203, 1249, 1, 60 },
			{ 6203, 1615, 1, 50 },
			{ 6203, 443, 100, 30 },
			{ 6203, 561, 65, 10 },
			{ 6023, 987, 1, 80 }, // loop key
			{ 6203, 985, 1, 65 }, // tooth key
			{ 6203, 391, 5, 10 },
			// Zam minion 1
			{ 6208, 11710, 1, 256 },
			{ 6208, 11712, 1, 256 },
			{ 6208, 11714, 1, 256 },
			{ 6208, 7060, 3, 5 },
			{ 6208, 592, 1, 1 },
			// Zam minion 2
			{ 6204, 11710, 1, 256 },
			{ 6204, 11712, 1, 256 },
			{ 6204, 11714, 1, 256 },
			{ 6204, 7060, 3, 5 },
			{ 6204, 592, 1, 1 },
			// Zam minion 3
			{ 6206, 11710, 1, 256 },
			{ 6206, 11712, 1, 256 },
			{ 6206, 11714, 1, 256 },
			{ 6206, 7060, 3, 5 },
			{ 6206, 592, 1, 1 },

			// Dag Kings
			// Supreme
			{ 2881, 6155, 1, 0 }, // hide
			{ 2881, 6729, 1, 0 }, // bones
			{ 2881, 884, 1, 10 }, // iron arrow
			{ 2881, 3748, 1, 30 }, // frem helm
			{ 2881, 3757, 1, 30 }, // frem sword
			{ 2881, 3758, 1, 30 }, // frem shield
			{ 2881, 1149, 1, 50 }, // d med
			{ 2881, 6739, 1, 100 }, // d axe
			{ 2881, 987, 1, 80 }, // loop key
			{ 2881, 985, 1, 65 }, // tooth key
			{ 2881, 3749, 1, 50 }, // archer helm
			{ 2881, 11771, 1, 55 }, // archer ring i
			{ 2881, 6133, 1, 75 }, // spined body
			{ 2881, 6135, 1, 75 }, // spined legs
			{ 2881, 6562, 1, 60 }, // mud battlestaff

			// Prime
			{ 2882, 6155, 1, 0 }, // hides
			{ 2882, 6729, 1, 0 }, // bones
			{ 2882, 6562, 1, 60 }, // mud battlestaff
			{ 2882, 1149, 1, 50 }, // d med
			{ 2882, 6739, 1, 100 }, // d axe
			{ 2882, 555, Misc.random(65), 7 }, // water rune
			{ 2882, 11770, 1, 80 }, // seers ring i
			{ 2882, 3755, 1, 45 }, // farseer helm
			{ 2882, 560, Misc.random(29), 25 }, // death rune
			{ 2882, 6139, 1, 75 }, // skeletal top
			{ 2882, 6141, 1, 75 }, // skeletal legs
			{ 2882, 1201, 1, 75 }, // rune kite
			{ 2882, 987, 1, 80 }, // loop key
			{ 2882, 985, 1, 80 }, // tooth key
			{ 2882, 565, Misc.random(11), 68 }, // blood rune
			{ 2882, 3748, 1, 30 }, // frem helm
			{ 2882, 3757, 1, 30 }, // frem sword
			{ 2882, 3758, 1, 30 }, // frem shield

			// Rex
			{ 2883, 6155, 1, 0 }, // hides
			{ 2883, 6729, 1, 0 }, // bones
			{ 2883, 157, 1, 25 }, // sup str (3)
			{ 2883, 141, 1, 32 }, // pray pot (2)
			{ 2883, 149, 1, 19 }, // sup atk (1)
			{ 2883, 985, 1, 30 }, // tooth key
			{ 2883, 987, 1, 80 }, // loop key
			{ 2883, 1329, 1, 50 }, // mith scim
			{ 2883, 1315, 1, 30 }, // mith 2h
			{ 2883, 1319, 1, 40 }, // rune 2h
			{ 2883, 6739, 1, 100 }, // d axe
			{ 2883, 3751, 1, 50 }, // berz helm
			{ 2883, 11773, 1, 100 }, // zerk ring i
			{ 2883, 11772, 1, 85 }, // warrior ring i
			{ 2883, 3753, 1, 50 }, // warrior helm
			{ 2883, 3748, 1, 30 }, // frem helm
			{ 2883, 3757, 1, 30 }, // frem sword
			{ 2883, 3758, 1, 30 }, // frem shield

			// Kalphite Queen phase 1 "1158"
			{ 1158, 532, 1, 0 },
			{ 1158, 5291, 1 + Misc.random(8), 3 },
			{ 1158, 5300, 1 + Misc.random(4), 12 },
			{ 1158, 5301, 1 + Misc.random(4), 15 },
			{ 1158, 5302, 1 + Misc.random(3), 19 },
			{ 1158, 5303, 1 + Misc.random(3), 24 },
			{ 1158, 995, 1 + Misc.random(200000), 50 },
			// { 1158, 2722, 1, 350 },// Clue scroll hard

			// Kalphite Queen phase 2 "1160"
			// { 1160, 2722, 1, 350 },// Clue scroll hard
			{ 1160, 532, 1, 0 },
			{ 1160, 5291, 1 + Misc.random(8), 3 },
			{ 1160, 5300, 1 + Misc.random(4), 12 },
			{ 1160, 5301, 1 + Misc.random(4), 15 },
			{ 1160, 5302, 1 + Misc.random(3), 19 },
			{ 1160, 5303, 1 + Misc.random(3), 24 },
			{ 1160, 995, 1 + Misc.random(200000), 50 },

			// Wildy Bosses
			// chaos elemental
			{ 3200, 592, 1, 0 },
			{ 3200, 187, 1, 25 },
			{ 3200, 5291, 1 + Misc.random(8), 3 },
			{ 3200, 5292, 1 + Misc.random(7), 4 },
			{ 3200, 5293, 1 + Misc.random(6), 5 },
			{ 3200, 5294, 1 + Misc.random(5), 7 },
			{ 3200, 5295, 1 + Misc.random(4), 12 },
			{ 3200, 5296, 1 + Misc.random(4), 16 },
			{ 3200, 5297, 1 + Misc.random(4), 21 },
			{ 3200, 5298, 1 + Misc.random(4), 22 },
			{ 3200, 5299, 1 + Misc.random(4), 23 },
			{ 3200, 5300, 1 + Misc.random(4), 24 },
			{ 3200, 5301, 1 + Misc.random(3), 28 },
			{ 3200, 5302, 1 + Misc.random(3), 29 },
			{ 3200, 5303, 1 + Misc.random(3), 30 },
			{ 3200, 6585, 1, 250 }, // fury
			{ 3200, 11732, 1, 200 }, // d boots
			{ 3200, 13879, 1 + Misc.random(10), 30 }, // morri javs
			{ 3200, 13883, 1 + Misc.random(10), 30 }, // morri throwaxe
			{ 3200, 13899, 1, 850 }, // vls
			{ 3200, 13902, 1, 750 }, // swh
			{ 3200, 7158, 1, 450 }, // d2h
			{ 3200, 14007, 1, 2000 }, // pet

			// scorpia
			{ 4172, 592, 1, 0 },
			{ 4172, 187, 1, 25 },
			{ 4172, 5291, 1 + Misc.random(8), 3 },
			{ 4172, 5292, 1 + Misc.random(7), 4 },
			{ 4172, 5293, 1 + Misc.random(6), 5 },
			{ 4172, 5294, 1 + Misc.random(5), 7 },
			{ 4172, 5295, 1 + Misc.random(4), 12 },
			{ 4172, 5296, 1 + Misc.random(4), 16 },
			{ 4172, 5297, 1 + Misc.random(4), 21 },
			{ 4172, 5298, 1 + Misc.random(4), 22 },
			{ 4172, 5299, 1 + Misc.random(4), 23 },
			{ 4172, 5300, 1 + Misc.random(4), 24 },
			{ 4172, 5301, 1 + Misc.random(3), 28 },
			{ 4172, 5302, 1 + Misc.random(3), 29 },
			{ 4172, 5303, 1 + Misc.random(3), 30 },
			{ 4172, 11931, 1, 250 }, // malediction shard 1
			{ 4172, 11928, 1, 250 }, // odium shard 1
			{ 4172, 6585, 1, 200 }, // fury
			{ 4172, 11920, 1, 300 }, // dpick

			// Vet'ion
			{ 4175, 187, 1, 25 },
			{ 4175, 4675, 1, 100 },// Ancient staff
			{ 4175, 1275, 1, 100 },// Rune pickaxe
			{ 4175, 1913, 1, 100 },// Rune 2h sword
			{ 4175, 1305, 1, 100 },// Dragon longsword
			{ 4175, 7158, 1, 700 },// Dragon 2h sword
			{ 4175, 565, 1, 200 },// Blood rune
			{ 4175, 560, 1, 300 },// Death rune
			{ 4175, 562, 1, 400 },// Chaos rune
			{ 4175, 258, 100, 80 },// Ranarr
			{ 4175, 995, 15000 + Misc.random(5000), 50 },
			{ 4175, 537, 100, 120 },// Dragon bones
			{ 4175, 1632, 100, 80 },// Uncut dragonstone
			{ 4175, 1514, 100, 80 },// Magic logs
			// { 4175, 2722, 1, 200 },// Clue scroll hard
			{ 4175, 13870, 1, 800 },// Morri
			{ 4175, 13873, 1, 800 },// Morri
			{ 4175, 13876, 1, 800 },// Morri
			{ 4175, 13858, 1, 650 },// Zuri
			{ 4175, 13861, 1, 650 },// Zuri
			{ 4175, 13864, 1, 650 },// Zuri
			{ 4175, 11932, 1, 250 }, // malediction shard 2
			{ 4175, 11929, 1, 250 }, // odium shard 2

			// venetatis
			{ 4173, 187, 1, 25 },
			{ 4173, 5291, 1 + Misc.random(8), 3 },
			{ 4173, 5292, 1 + Misc.random(7), 4 },
			{ 4173, 5293, 1 + Misc.random(6), 5 },
			{ 4173, 5294, 1 + Misc.random(5), 7 },
			{ 4173, 5295, 1 + Misc.random(4), 12 },
			{ 4173, 5296, 1 + Misc.random(4), 16 },
			{ 4173, 5297, 1 + Misc.random(4), 21 },
			{ 4173, 5298, 1 + Misc.random(4), 22 },
			{ 4173, 5299, 1 + Misc.random(4), 23 },
			{ 4173, 5300, 1 + Misc.random(4), 24 },
			{ 4173, 5301, 1 + Misc.random(3), 28 },
			{ 4173, 5302, 1 + Misc.random(3), 29 },
			{ 4173, 5303, 1 + Misc.random(3), 30 },
			{ 4173, 11920, 1, 250 }, // dpick
			{ 4173, 13887, 1, 950 }, // vesta chain
			{ 4173, 13893, 1, 920 }, // vesta skirt
			{ 4173, 13899, 1, 930 }, // vesta long
			{ 4173, 13884, 1, 900 }, // stat plate
			{ 4173, 13890, 1, 935 }, // stat legs
			{ 4173, 13896, 1, 910 }, // stat full helm
			{ 4173, 592, 1, 0 },
			{ 4173, 11933, 1, 250 }, // malediction shard 3
			{ 4173, 11930, 1, 250 }, // odium shard 3

			// Callisto
			 { 4001, 532, 1, 0 }, // big bones
			{ 4001, 12605, 1, 400 }, // tyrannical ring
			{ 4001, 12603, 1, 400 }, // treasonous ring
			{ 4001, 12601, 1, 400 }, // ring of the gods
			{ 4001, 2722, 1, 100 }, // clue scroll
			{ 4001, 990, 2, 150 }, // crystal key
			{ 4001, 6199, 1, 185 }, // mystery box

			// King Black Dragon
			{ 50, 187, 1, 25 },
			{ 50, 14004, 1, 2000 }, // pet
			{ 50, 12524, 1, 1000 }, // black dragon mask
			{ 50, 4087, 1, 725 }, // dlegs
			{ 50, 1187, 1, 175 }, // dsq
			{ 50, 536, 1, 0 }, // dbones
			{ 50, 7987, 1, 1750 }, // kbd heads
			{ 50, 1747, 1, 0 }, // black dhide
			{ 50, 1319, 1, 150 }, // rune 2h
			{ 50, 1369, 1, 75 }, // mith battleaxe
			{ 50, 1247, 1, 110 }, // rune spear
			{ 50, 1249, 1, 120 }, // d spear
			{ 50, 565, 15, 120 }, // blood rune
			{ 50, 560, 7 + Misc.random(38), 45 }, // death rune
			{ 50, 884, 690, 110 }, // iron arrow
			{ 50, 556, 105, 15 }, // air rune
			{ 50, 554, 105, 110 }, // fire rune
			{ 50, 557, 105, 15 }, // earth rune
			{ 50, 892, 45, 140 }, // rune arrow
			{ 50, 1149, 1, 170 }, // dmed
			{ 50, 2368, 1, 175 }, // left half
			{ 50, 1725, 1, 110 }, // str ammy
			{ 50, 995, 25000, 50 }, // coins
			{ 50, 454, 100, 122 }, // coal
			{ 50, 1516, 100, 150 }, // yew log
			{ 50, 11286, 1, 750 }, // visage

			// Dragons

			// Green Dragon
			{ 941, 187, 1, 50 },
			{ 941, 536, 1, 0 },
			{ 941, 1753, 1, 0 },
			{ 941, 2377, 1, 75 },
			{ 941, 1333, 1, 43 },
			{ 941, 1247, 1, 43 },
			{ 941, 830, 40, 69 },
			{ 941, 1319, 1, 43 },
			{ 941, 1079, 1, 53 },
			{ 941, 1147, 1, 53 },
			{ 941, 1149, 1, 150 },
			{ 941, 384, 10, 35 },
			{ 941, 5296, 5, 20 },
			{ 941, 216, 3, 20 },
			{ 941, 378, 10, 20 },
			{ 941, 214, 2, 30 },
			{ 941, 212, 2, 30 },
			{ 941, 5304, 2, 35 },
			{ 941, 5300, 2, 35 },
			{ 941, 218, 3, 45 },
			{ 941, 5297, 2, 35 },
			{ 941, 5295, 2, 35 },
			{ 941, 200, 2, 35 },
			{ 941, 200, 2, 35 },
			{ 941, 220, 2, 35 },
			{ 941, 202, 2, 35 },
			{ 941, 204, 2, 35 },
			{ 941, 206, 2, 35 },
			{ 941, 208, 2, 45 },
			{ 941, 210, 2, 35 },
			{ 941, 12518, 1, 1000 },

			// Baby Blue Dragon
			{ 52, 187, 1, 75 },
			{ 52, 534, 1, 0 }, // baby dragon bones
			 { 52, 2722, 1, 185 },
			{ 52, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 52, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 52, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 52, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 52, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 52, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 52, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 52, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 52, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 52, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 52, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 52, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 52, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 52, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 52, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 52, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 52, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 52, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 52, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 52, 1632, 1 + Misc.random(4), 30 }, // uncut dstone

			// Blue Dragon
			{ 55, 187, 1, 75 },
			{ 55, 536, 1, 0 },
			{ 55, 1751, 1, 0 },
			{ 55, 2722, 1, 175 },
			{ 55, 1333, 1, 43 },
			{ 55, 1247, 1, 43 },
			{ 55, 830, 40, 69 },
			{ 55, 1319, 1, 43 },
			{ 55, 1079, 1, 53 },
			{ 55, 1147, 1, 53 },
			{ 55, 1149, 1, 150 },
			{ 55, 384, 10, 35 },
			{ 55, 5296, 5, 20 },
			{ 55, 216, 3, 20 },
			{ 55, 378, 10, 20 },
			{ 55, 214, 2, 30 },
			{ 55, 212, 2, 30 },
			{ 55, 5304, 2, 35 },
			{ 55, 5300, 2, 35 },
			{ 55, 218, 3, 45 },
			{ 55, 5297, 2, 35 },
			{ 55, 5295, 2, 35 },
			{ 55, 200, 2, 35 },
			{ 55, 200, 2, 35 },
			{ 55, 220, 2, 35 },
			{ 55, 202, 2, 35 },
			{ 55, 204, 2, 35 },
			{ 55, 206, 2, 35 },
			{ 55, 208, 2, 45 },
			{ 55, 210, 2, 35 },
			{ 55, 12520, 1, 1000 },
			
			//Mithril Dragon
			{5363,11335,1,150},{5363,4087,1,150},{5363,3140,1,150},{5363,2722,1,130},{5363,5698,1,50},{5363,536,1,0}, {5363,384,10,35},{5363,5296,5,20},{5363,216,3,20},{5363,378,10,20},
			{5363,214,2,30},{5363,212,2,30},{5363,5304,2,35},{5363,5300,2,35},{5363,218,3,45},
			{5363,5297,2,35},{5363,5295,2,35},{5363,200,2,35},{5363,11286,1,380},{5363,200,2,35},{5363,220,2,35},
			{5363,202,2,35},{5363,204,2,35},{5363,206,2,35},{5363,208,2,45},{5363,210,2,35},{5363,12369,1,1000},

			// Red Dragon
			{ 53, 187, 1, 90 },
			{ 53, 536, 1, 0 },
			{ 53, 1749, 1, 0 },
			// { 53, 2713, 1, 75 },
			{ 53, 1333, 1, 43 },
			{ 53, 1247, 1, 43 },
			{ 53, 830, 40, 69 },
			{ 53, 1319, 1, 43 },
			{ 53, 1079, 1, 53 },
			{ 53, 1147, 1, 53 },
			{ 53, 1149, 1, 150 },
			{ 53, 384, 10, 35 },
			{ 53, 5296, 5, 20 },
			{ 53, 216, 3, 20 },
			{ 53, 378, 10, 20 },
			{ 53, 214, 2, 30 },
			{ 53, 212, 2, 30 },
			{ 53, 5304, 2, 35 },
			{ 53, 5300, 2, 35 },
			{ 53, 218, 3, 45 },
			{ 53, 5297, 2, 35 },
			{ 53, 5295, 2, 35 },
			{ 53, 200, 2, 35 },
			{ 53, 200, 2, 35 },
			{ 53, 220, 2, 35 },
			{ 53, 202, 2, 35 },
			{ 53, 204, 2, 35 },
			{ 53, 206, 2, 35 },
			{ 53, 208, 2, 45 },
			{ 53, 210, 2, 35 },
			{ 53, 12522, 1, 1000 },

			// Black dragon
			{ 54, 187, 1, 75 },
			{ 54, 536, 1, 0 },
			{ 54, 1747, 1, 0 },
			// { 54, 5291, 1 + Misc.random(2), 4 }, // guam seed
			// { 54, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			// { 54, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			// { 54, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 54, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 54, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 54, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 54, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 54, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 54, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 54, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 54, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 54, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 54, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 54, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 54, 985, 1, 120 }, // tooth key
			{ 54, 987, 1, 150 }, // loop key
			{ 54, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 54, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 54, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 54, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 54, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 54, 560, 15 + Misc.random(25), 9 }, // death rune
			{ 54, 868, 20 + Misc.random(30), 12 }, // rune knife
			{ 54, 4131, 1, 45 }, // rune boots
			{ 54, 1149, 1, 50 }, // d med
			{ 54, 2368, 1, 80 }, // shield right half
			{ 54, 2366, 1, 45 }, // shield left half
			{ 54, 1319, 1, 26 }, // rune 2h
			{ 54, 1247, 1, 15 }, // rune spear
			{ 54, 1249, 1, 21 }, // dragon spear
			{ 54, 11286, 1, 750 }, // Draconic visage
			{ 54, 12524, 1, 2000 }, // black dragon mask

			// Bronze Dragon
			{ 1590, 187, 1, 67 },
			{ 1590, 4087, 1, 38 },
			{ 1590, 1187, 1, 25 },
			{ 1590, 536, 1, 0 },
			{ 1590, 2349, 5, 0 },
			{ 1590, 1319, 1, 47 },
			{ 1590, 1369, 1, 28 },
			{ 1590, 1247, 1, 18 },
			{ 1590, 1249, 1, 37 },
			{ 1590, 565, 15, 48 },
			{ 1590, 560, 7 + Misc.random(70), 65 },
			{ 1590, 884, 690, 37 },
			{ 1590, 556, 105, 9 },
			{ 1590, 554, 105, 17 },
			{ 1590, 557, 105, 9 },
			{ 1590, 892, 15 + Misc.random(30), 73 },
			{ 1590, 1149, 1, 63 },
			{ 1590, 2368, 1, 63 },
			{ 1590, 1725, 1, 18 },
			{ 1590, 995, 10000 + Misc.random(20500), 30 },
			{ 1590, 454, 25 + Misc.random(25), 22 },
			{ 1590, 1516, 25 + Misc.random(25), 50 },
			{ 1590, 11286, 1, 750 },
			{ 1590, 12363, 1, 1000 },

			// Iron Dragon
			{ 1591, 187, 1, 65 },
			{ 1591, 2352, 5, 0 },
			{ 1591, 536, 1, 0 },
			{ 1591, 4087, 1, 100 }, // Dragon platelegs
			{ 1591, 1319, 1, 34 }, // Rune 2h
			{ 1591, 1247, 1, 11 }, // Rune spear
			{ 1591, 1249, 1, 50 }, // Dragon spear
			{ 1591, 565, 15, 29 }, // Blood rune
			{ 1591, 560, 17 + Misc.random(65), 54 }, // Death rune
			{ 1591, 884, 690, 24 }, // Iron arrow
			{ 1591, 556, 105, 7 }, // Air rune
			{ 1591, 554, 105, 12 }, // Fire rune
			{ 1591, 557, 105, 7 }, // Earth rune
			{ 1591, 892, 20 + Misc.random(25), 51 }, // Rune arrow
			{ 1591, 1149, 1, 65 }, // dragon med helm
			{ 1591, 2368, 1, 80 }, // shield right half
			{ 1591, 2366, 1, 45 }, // shield left half
			{ 1591, 1725, 1, 12 }, // amulet of str
			{ 1591, 995, 16000 + Misc.random(16500), 19 }, // Coins
			{ 1591, 454, 35 + Misc.random(25), 15 }, // Coal
			{ 1591, 1516, 35 + Misc.random(25), 29 }, // Yew logs
			{ 1591, 985, 1, 120 }, // tooth key
			{ 1591, 987, 1, 150 }, // loop key
			{ 1591, 11286, 1, 500 }, // Draconic visage
			{ 1591, 12365, 1, 1000 }, // Iron dragon mask

			// Steel dragon
			{ 1592, 187, 1, 65 },
			{ 1592, 536, 1, 0 },
			{ 1592, 2354, 5, 0 },
			{ 1592, 4087, 1, 70 }, // d legs
			{ 1592, 1319, 1, 26 }, // rune 2h
			{ 1592, 1247, 1, 8 }, // rune spear
			{ 1592, 1249, 1, 21 }, // dragon spear
			{ 1592, 565, 15 + Misc.random(25), 22 }, // blood rune
			{ 1592, 560, 17 + Misc.random(65), 38 }, // death rune
			{ 1592, 884, 690, 17 }, // iron arrow
			{ 1592, 556, 105, 6 }, // air rune
			{ 1592, 554, 105, 10 }, // fire rune
			{ 1592, 557, 105, 6 }, // earth rune
			{ 1592, 892, 20 + Misc.random(25), 38 }, // rune arrow
			{ 1592, 1149, 1, 26 }, // d med
			{ 1592, 2368, 1, 80 }, // shield right half
			{ 1591, 2366, 1, 45 }, // shield left half
			{ 1592, 1725, 1, 10 }, // amulet of str
			{ 1592, 985, 1, 120 }, // tooth key
			{ 1592, 987, 1, 150 }, // loop key
			{ 1592, 11286, 1, 500 }, // visage
			{ 1592, 12367, 1, 1000 }, // steel dragon mask

			// Cyclops
			{ 4291, 1187, 1, 17 },
			{ 4291, 532, 1, 0 },
			{ 4291, 1319, 1, 34 },
			{ 4291, 1369, 1, 22 },
			{ 4291, 1247, 1, 11 },
			{ 4291, 1249, 1, 27 },
			{ 4291, 565, 15, 29 },
			{ 4291, 560, 17 + Misc.random(65), 54 },
			{ 4291, 884, 690, 24 },
			{ 4291, 556, 105, 7 },
			{ 4291, 554, 105, 12 },
			{ 4291, 557, 105, 7 },
			{ 4291, 892, 20 + Misc.random(25), 51 },
			{ 4291, 1149, 1, 150 },
			{ 4291, 2368, 1, 36 },
			{ 4291, 1725, 1, 12 },
			{ 4291, 995, 1600 + Misc.random(1650), 19 },
			{ 4291, 454, 35 + Misc.random(25), 15 },
			{ 4291, 1516, 35 + Misc.random(25), 29 },

			// Men
			{ 1, 526, 1, 0 },
			{ 1, 995, 1, 5 },
			{ 1, 995, 1, 5 },
			{ 3, 995, 150, 3 },

			// Wolf
			{ 96, 958, 1, 3 },
			{ 96, 2859, 1, 0 },

			// Desert Snake
			{ 1874, 6287, 1, 0 },
			{ 1874, 6322, 1, 14 },
			{ 1874, 6324, 1, 14 },
			{ 1874, 6326, 1, 7 },
			{ 1874, 6328, 1, 7 },
			{ 1874, 6330, 1, 7 },
			{ 1874, 995, 1400 + (Misc.random(99)), 4 },
			{ 1874, 6384, 1, 5 },
			{ 1874, 6386, 1, 5 },

			// Renegade Knight
			{ 237, 526, 1, 0 },
			{ 237, 995, 1000, 8 },
			{ 237, 1283, 1, 5 },
			{ 237, 1301, 1, 10 },

			// Dagannoth - 92
			{ 1341, 526, 1, 0 },
			{ 1341, 6128, 1, 25 },
			{ 1341, 6129, 1, 25 },
			{ 1341, 6130, 1, 25 },
			{ 1341, 6145, 1, 14 },
			{ 1341, 6151, 1, 14 },
			{ 1341, 6155, 1, 4 },
			{ 1341, 385, 2, 6 },
			{ 1341, 995, 2500 + (Misc.random(99)), 6 },

			// Slayer Monsters

			// Banshee
			// { 1612, 2713, 1, 85 },
			{ 1612, 995, 1000, 9 },
			{ 1612, 1333, 1, 10 },
			{ 1612, 1247, 1, 8 },
			{ 1612, 830, 20, 9 },
			{ 1612, 592, 1, 0 },

			// Crawing Hand
			// { 1648, 2713, 1, 85 },
			{ 1648, 526, 1, 0 },
			{ 1649, 526, 1, 0 },
			{ 1650, 526, 1, 0 },
			{ 1651, 526, 1, 0 },
			{ 1652, 526, 1, 0 },

			// Infernal Mage
			// { 1643, 2713, 1, 85 },
			{ 1643, 526, 1, 0 },
			{ 1643, 1437, 25 + Misc.random(10), 7 }, // rune ess
			{ 1643, 4675, 1, 28 }, // ancient staff
			{ 1643, 554, 50, 5 }, // fire rune
			{ 1643, 557, 50, 5 }, // earth rune
			{ 1643, 555, 50, 4 }, // water rune
			{ 1643, 560, 20, 6 }, // death rune
			{ 1643, 565, 15, 8 }, // blood rune
			{ 1643, 4089, 1, 9 }, // blue mystic hat
			{ 1643, 4091, 1, 20 }, // blue mystic top
			{ 1643, 4093, 1, 36 }, // blue mystic bottom
			{ 1643, 4101, 1, 41 }, // dark mystic top
			{ 1643, 4103, 1, 45 }, // dark mystic bottom
			{ 1643, 4111, 1, 27 }, // light mystic top
			{ 1643, 4113, 1, 26 }, // light mystic bottom

			// Spiritual Warrior
			{ 6229, 11732, 1, 80 },
			{ 6229, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Ranger
			{ 6230, 11732, 1, 50 },
			{ 6230, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Mage
			{ 6231, 11732, 1, 50 },
			{ 6231, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Warrior
			{ 6219, 11732, 1, 50 },
			{ 6219, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Ranger
			{ 6220, 11732, 1, 50 },
			{ 6220, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Mage
			{ 6221, 11732, 1, 50 },
			{ 6221, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Warrior
			{ 6255, 11732, 1, 50 },
			{ 6255, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Ranger
			{ 6256, 11732, 1, 50 },
			{ 6256, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Mage
			{ 6257, 11732, 1, 50 },
			{ 6257, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Ranger
			{ 6276, 11732, 1, 50 },
			{ 6276, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Warrior
			{ 6277, 11732, 1, 50 },
			{ 6277, 5304, 1 + Misc.random(2), 88 },

			// Spiritual Mage
			{ 6278, 11732, 1, 50 },
			{ 6278, 5304, 1 + Misc.random(2), 88 },

			// Demons
			// Lesser Demon
			// { 82, 2713, 1, 85 },
			{ 82, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 82, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 82, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 82, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 82, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 82, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 82, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 82, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 82, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 82, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 82, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 82, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 82, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 82, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 82, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 82, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 82, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 82, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 82, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 82, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			{ 82, 592, 1, 0 },
			{ 82, 1333, 1, 9 }, // rune scimmy
			{ 82, 1247, 1, 7 }, //

			// Skeleton
			// { 92, 2713, 1, 85 },
			{ 92, 526, 1, 0 },
			{ 92, 1247, 1, 8 },
			{ 92, 995, 5000, 7 },

			// Magic Axe
			// { 127, 2713, 1, 85 },
			{ 127, 1373, 1, 9 },
			{ 127, 1363, 1, 0 },

			// hellhound
			{ 49, 526, 1, 0 }, // bones
			{ 49, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 49, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 49, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 49, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 49, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 49, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 49, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 49, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 49, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 49, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 49, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 49, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 49, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 49, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 49, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 49, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 49, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 49, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 49, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 49, 1632, 1 + Misc.random(4), 30 }, // uncut dstone

			// Black Demon
			{ 84, 5291, 1 + Misc.random(2), 4 }, // guam seed
			{ 84, 5292, 1 + Misc.random(2), 5 }, // marrentil seed
			{ 84, 5293, 1 + Misc.random(2), 6 }, // tarromin seed
			{ 84, 5294, 1 + Misc.random(2), 7 }, // harralander seed
			{ 84, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 84, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 84, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 84, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 84, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 84, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 84, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 84, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 84, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 84, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 84, 1437, 15 + Misc.random(10), 10 }, // rune ess
			{ 84, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 84, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 84, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 84, 1618, 1 + Misc.random(4), 17 }, // uncut diamond
			{ 84, 1632, 1 + Misc.random(4), 30 }, // uncut dstone
			// { 84, 2713, 1, 85 },
			{ 84, 592, 1, 0 },
			{ 84, 1333, 1, 8 }, // rune scimmy
			{ 84, 1247, 1, 9 }, // rune spear
			{ 84, 5698, 1, 15 }, // dds
			{ 84, 4587, 1, 20 }, // dscim

			// Goblin - lv 2 - 1770-1776
			{ 1770, 526, 1, 0 }, // Bones - Always
			{ 1770, 288, 1, 15 },
			{ 1770, 995, 1 + Misc.random(1000), 15 },
			{ 1770, 1438, 1, 20 },
			{ 1770, 1009, 1, 30 },
			{ 1770, 1917, 1, 15 },
			{ 1770, 1919, 1, 35 },
			{ 1771, 526, 1, 0 }, // Bones - Always
			{ 1771, 288, 1, 15 },
			{ 1771, 995, 1 + Misc.random(1000), 15 },
			{ 1771, 1438, 1, 20 },
			{ 1771, 1009, 1, 30 },
			{ 1771, 1917, 1, 15 },
			{ 1771, 1919, 1, 35 },
			{ 1772, 526, 1, 0 }, // Bones - Always
			{ 1772, 288, 1, 15 },
			{ 1772, 995, 1 + Misc.random(1000), 15 },
			{ 1772, 1438, 1, 20 },
			{ 1772, 1009, 1, 30 },
			{ 1772, 1917, 1, 15 },
			{ 1772, 1919, 1, 35 },
			{ 1773, 526, 1, 0 }, // Bones - Always
			{ 1773, 288, 1, 15 },
			{ 1773, 995, 1 + Misc.random(1000), 15 },
			{ 1773, 1438, 1, 20 },
			{ 1773, 1009, 1, 30 },
			{ 1773, 1917, 1, 15 },
			{ 1773, 1919, 1, 35 },
			{ 1774, 526, 1, 0 }, // Bones - Always
			{ 1774, 288, 1, 15 },
			{ 1774, 995, 1 + Misc.random(1000), 15 },
			{ 1774, 1438, 1, 20 },
			{ 1774, 1009, 1, 30 },
			{ 1774, 1917, 1, 15 },
			{ 1774, 1919, 1, 35 },
			{ 1775, 526, 1, 0 }, // Bones - Always
			{ 1775, 288, 1, 15 },
			{ 1775, 995, 1 + Misc.random(1000), 15 },
			{ 1775, 1438, 1, 20 },
			{ 1775, 1009, 1, 30 },
			{ 1775, 1917, 1, 15 },
			{ 1775, 1919, 1, 35 },
			{ 1776, 526, 1, 0 }, // Bones - Always
			{ 1776, 288, 1, 15 },
			{ 1776, 995, 1 + Misc.random(1000), 15 },
			{ 1776, 1438, 1, 20 },
			{ 1776, 1009, 1, 30 },
			{ 1776, 1917, 1, 15 },
			{ 1776, 1919, 1, 35 },

			// Goblin - lv 5
			{ 101, 526, 1, 0 }, // Bones - Always
			{ 101, 288, 1, 15 },
			{ 101, 995, 1 + Misc.random(1000), 15 },
			{ 101, 1438, 1, 20 },
			{ 101, 1009, 1, 30 },
			{ 101, 1917, 1, 15 },
			{ 101, 1919, 1, 35 },

			// Rock Crabs (1265)
			// Bones
			{ 1265, 526, 1, 0 }, // Bones - Always
			// Herbs
			{ 1265, 207, 1, 45 }, // Grimy Ranarr - rare
			{ 1265, 211, 1, 45 }, // Grimy avantoe - rare
			{ 1265, 5293, 1, 45 }, // tarromin seed - rare
			{ 1265, 5295, 1, 45 }, // ranarr seed - rare
			{ 1265, 5297, 1, 80 }, // irit seed - very rare
			// Skilling equipment
			{ 1265, 1265, 1, 50 }, // Bronze pickaxe - common
			{ 1265, 1267, 1, 50 }, // iron pickaxe - common
			{ 1265, 1351, 1, 50 }, // bronze hatchet - uncommon
			{ 1265, 826, 1, 50 }, // iron javelin - uncommon
			// Runes
			{ 1265, 554, 1, 30 }, // fire rune - common
			{ 1265, 561, 1, 50 }, // nature rune - uncommon
			{ 1265, 562, 1, 75 }, // chaos rune - rare
			{ 1265, 564, 1, 75 }, // cosmic rune - rare
			// Misc
			{ 1265, 995, 4 + Misc.random(494), 10 }, // coins - common
			{ 1265, 407, 1, 40 }, // oyster - common
			{ 1265, 402, 1 + Misc.random(4), 60 }, // seaweed - common
			{ 1265, 411, 1, 30 }, // oyster pearl - common
			{ 1265, 409, 1, 50 }, // empty oyster - common
			{ 1265, 1969, 1, 50 }, // Spinach roll - uncommon
			{ 1265, 946, 1, 50 }, // Knife - uncommon
			{ 1265, 440, 1, 50 }, // Iron ore - uncommon
			{ 1265, 454, 2, 50 }, // Coal - uncommon
			{ 1265, 437, 3, 70 }, // Copper ore - uncommon
			{ 1265, 439, 3, 80 }, // Tin ore - uncommon
			{ 1265, 313, 1 + Misc.random(9), 30 }, // Fishing bait - uncommon
			{ 1265, 45, 5, 50 }, // Opal bolt tips - uncommon
			{ 1265, 9187, 1, 40 }, // Jade bolt tips - rare
			{ 1265, 225, 1, 40 }, // Limpwurt root - rare
			{ 1265, 405, 1, 40 }, // Casket - Rare
			// End of Rock Crabs

			// Hill Giants (117)
			// Bones
			{ 117, 532, 1, 0 }, // Big bones - Always
			// Armour/Weapons
			{ 117, 1309, 1, 50 }, // Iron 2h - uncommon
			{ 117, 890, 1 + Misc.random(4), 80 }, // Adamant arrow - Very Rare
			{ 117, 1191, 1, 50 }, // Iron Kite-sheild - common
			{ 117, 1153, 1, 50 }, // Iron full helm - common
			{ 117, 1207, 1, 50 }, // Steel dagger - common
			{ 117, 884, 3, 50 }, // Iron arrow - common
			{ 117, 888, 5, 50 }, // Mithril arrow - Rare
			{ 117, 1295, 1, 50 }, // Steel longsword - uncommon
			{ 117, 1203, 1, 50 }, // Iron dagger - common
			{ 117, 886, 10 + Misc.random(5), 80 }, // Steel arrow - uncommon
			{ 117, 1119, 1, 50 }, // Steel platebody - uncommon
			// Runes
			{ 117, 555, 7, 75 }, // Water rune - common
			{ 117, 554, 15, 75 }, // Fire rune - common
			{ 117, 564, 2, 75 }, // Cosmic rune - common
			{ 117, 563, 3, 75 }, // Law rune - uncommon
			{ 117, 561, 6, 75 }, // Nature rune - uncommon
			{ 117, 558, 3, 75 }, // Mind rune - common
			{ 117, 560, 2, 100 }, // Death rune - rare
			{ 117, 562, 2, 100 }, // Chaos rune - rare
			// Herbs
			{ 117, 199, 1, 50 }, // Grimy guam - common
			{ 117, 201, 1, 50 }, // Grimy marrentill - common
			{ 117, 203, 1, 50 }, // Grimy tarromin - common
			{ 117, 207, 1, 75 }, // Grimy ranarr - rare
			{ 117, 205, 1, 75 }, // Grimy harralander - rare
			{ 117, 209, 1, 75 }, // Grimy irit - rare
			{ 117, 211, 1, 75 }, // Grimy avantoe - rare
			{ 117, 213, 1, 75 }, // Grimy kwuarm - rare
			{ 117, 217, 1, 75 }, // Grimy dwarf weed - rare
			{ 117, 215, 1, 100 }, // Grimy cadantine - very rare
			{ 117, 2485, 1, 100 }, // Grimy lantadyme - very rare
			// Seeds
			{ 117, 5322, 1 + Misc.random(2), 50 }, // tomato seed - common
			{ 117, 5306, 1 + Misc.random(2), 60 }, // jute seed - uncommon
			{ 117, 5318, 1 + Misc.random(3), 50 }, // potato seed - common
			{ 117, 5319, 4 + Misc.random(4), 50 }, // onion seed - common
			{ 117, 5323, 2, 50 }, // strawberry seed - common
			{ 117, 5324, 4, 50 }, // cabbage seed - common
			{ 117, 5307, 1 + Misc.random(2), 75 }, // hammerstone seed -
													// uncommon
			{ 117, 5100, 1, 75 }, // limpwurt seed - rare
			{ 117, 5320, 3, 75 }, // sweetcorn seed - rare
			{ 117, 5302, 1, 60 }, // lantadyme seed - uncommon
			{ 117, 5321, 2, 100 }, // watermelon seed - Very rare
			// Misc
			{ 117, 995, 300 + Misc.random(50), 40 }, // coins - common
			{ 117, 225, 1, 60 }, // limpwurt root - common
			{ 117, 1917, 1, 60 }, // beer - common
			{ 117, 1446, 1, 60 }, // Body talisman - uncommon
			{ 117, 2353, 1, 75 }, // steel bar - rare
			// End Hill Giant

			// Chaos Druid(181)
			// Herbs
			{ 181, 199, 1, 50 }, // Grimy guam - common
			{ 181, 201, 1, 50 }, // Grimy marrentill - common
			{ 181, 203, 1, 50 }, // Grimy tarromin - common
			{ 181, 207, 1, 80 }, // Grimy ranarr - uncommon
			{ 181, 205, 1, 80 }, // Grimy harralander - uncommon
			{ 181, 209, 1, 80 }, // Grimy irit - uncommon
			{ 181, 211, 1, 80 }, // Grimy avantoe - uncommon
			{ 181, 213, 1, 80 }, // Grimy kwuarm - uncommon
			{ 181, 217, 1, 80 }, // Grimy dwarf weed - uncommon
			{ 181, 215, 1, 80 }, // Grimy cadantine - uncommon
			{ 181, 2485, 1, 80 }, // Grimy lantadyme - uncommon
			// Runes
			{ 181, 563, 2, 70 }, // Law rune - common
			{ 181, 559, 9, 70 }, // Body rune - common
			{ 181, 556, 15, 70 }, // Air rune - common
			{ 181, 557, 9, 70 }, // Earth rune - common
			{ 181, 558, 12, 70 }, // Mind rune - common
			{ 181, 561, 3, 100 }, // Nature rune - uncommon
			// Misc
			{ 181, 995, 365, 50 }, // coins - common
			{ 181, 9142, 6, 70 }, // Mirthril bolts - common
			{ 181, 227, 1, 70 }, // Vial of water - common
			{ 181, 540, 1, 70 }, // Druid's robe(top) - common
			{ 181, 538, 1, 70 }, // Druid's robe bottom - common
			{ 181, 1291, 1, 80 }, // Bronze Longsword - uncommon
			{ 181, 231, 1, 90 }, // snape grass - rare
			{ 181, 1594, 1, 90 }, // unholy mould - rare
			{ 181, 1454, 1, 100 }, // cosmic talisman - very rare
			{ 181, 1153, 1, 100 }, // iron helm - very rare
			{ 181, 1627, 1, 100 }, // uncut jade - very rare
			// End Chaos Druid

			// Moss Giants(112)
			// Bones
			{ 112, 532, 1, 0 }, // Big bones - always
			// Weapons
			{ 112, 1389, 1, 60 }, // Magic Staff - common
			{ 112, 1243, 1, 60 }, // mithril spear - common
			{ 112, 1285, 1, 60 }, // mithril sword - common
			{ 112, 886, 30, 60 }, // steel arrow - common
			{ 112, 884, 15, 60 }, // iron arrow - common
			{ 112, 1287, 1, 90 }, // adamant sword - rare
			// Armour
			{ 112, 1165, 1, 60 }, // Black full helm - common
			{ 112, 1179, 1, 60 }, // Black sq shield - common
			{ 112, 1157, 1, 60 }, // Steel full helm - common
			{ 112, 1193, 1, 75 }, // Steel kiteshield - uncommon
			// Runes
			{ 112, 556, 18, 60 }, // Air Rune - common
			{ 112, 564, 3, 60 }, // cosmic rune - common
			{ 112, 557, 27, 60 }, // Earth rune - common
			{ 112, 561, 6, 60 }, // Nature rune - common
			{ 112, 563, 3 + Misc.random(3), 75 }, // Law rune - uncommon
			{ 112, 565, 1, 75 }, // Blood rune - uncommon
			{ 112, 562, 7, 75 }, // Chaos rune - uncommon
			{ 112, 560, 1 + Misc.random(2), 90 }, // Death Rune - Rare
			// Seeds - long. lol
			{ 112, 5323, 1, 60 }, // Strawberry seed - common
			{ 112, 5311, 1, 60 }, // Wildblood seed - common
			{ 112, 5293, 1, 60 }, // Tarromin seed - common
			{ 112, 5294, 1, 60 }, // Harralander seed - common
			{ 112, 5104, 1, 60 }, // Jangerberry seed - common
			{ 112, 5292, 1, 60 }, // Marrentill seed - common
			{ 112, 5281, 1, 75 }, // Belladonna seed - uncommon
			{ 112, 12176, 1, 75 }, // Spirit weed seed - uncommon
			{ 112, 5100, 1, 75 }, // Limpwurt seed - uncommon
			{ 112, 5296, 1, 75 }, // Toadflax seed - uncommon
			{ 112, 5105, 1, 75 }, // Whiteberry seed - uncommon
			{ 112, 5282, 1, 75 }, // Bittercap mushroom spore - uncommon
			{ 112, 4206, 1, 75 }, // Consecration seed - uncommon
			{ 112, 5298, 1, 90 }, // Avantoe seed - Rare
			{ 112, 5280, 1, 90 }, // Cactus seed - Rare
			{ 112, 5297, 1, 90 }, // Irit seed - Rare
			{ 112, 5299, 1, 90 }, // Kwuarm seed - Rare
			{ 112, 5106, 1, 90 }, // Poison ivy seed - Rare
			{ 112, 5295, 1, 90 }, // Ranarr seed - Rare
			{ 112, 5301, 1, 90 }, // Cadantine seed - Rare
			{ 112, 5302, 1, 90 }, // Lantadyme seed - Rare
			{ 112, 5321, 1, 90 }, // Watermelon seed - Rare
			{ 112, 5303, 1, 110 }, // Dwarf weed seed - Very Rare
			// Herbs
			{ 112, 199, 1, 60 }, // Grimy guam - common
			{ 112, 201, 1, 60 }, // Grimy marrentill - common
			{ 112, 203, 1, 60 }, // Grimy tarromin - common
			{ 112, 205, 1, 60 }, // Grimy harralander - common
			{ 112, 211, 1, 75 }, // Grimy avantoe - uncommon
			{ 112, 209, 1, 75 }, // Grimy irit - uncommon
			{ 112, 213, 1, 75 }, // Grimy kwuarm - uncommon
			{ 112, 207, 1, 75 }, // Grimy ranarr - uncommon
			{ 112, 2485, 1, 75 }, // Grimy lantadyme - uncommon
			{ 112, 217, 1, 75 }, // Grimy dwarf weed - uncommon
			{ 112, 215, 1, 75 }, // Grimy cadantine - uncommon
			{ 112, 220, 1, 110 }, // Grimy torstol - very rare
			// Misc
			{ 112, 995, 250 + Misc.random(250), 60 }, // coins - common
			{ 112, 2353, 1, 60 }, // steel bar - common
			{ 112, 453, 1, 75 }, // Coal - uncommon
			{ 112, 1969, 1, 75 }, // Spinach roll - Uncommon
			{ 112, 1442, 1, 90 }, // Fire talisman - Rare
			// End Moss Giants

			// Skeletal Wyvern
			{ 3070, 6812, 1, 0 },
			{ 3070, 1618, 5, 5 },
			{ 3070, 386, 8, 10 },
			{ 3070, 995, 1500, 10 },
			{ 3070, 11286, 1, 750 },
			{ 3070, 560, 30, 8 },
			{ 3070, 555, 50, 4 },
			{ 3070, 12524, 1, 1000 },
			{ 3070, 990, 1, 85 },

			// dark wizard
			{ 3243, 526, 1, 0 },
			{ 3243, 995, 1300, 5 },
			{ 3243, 8007, 1, 10 },
			{ 3243, 8008, 1, 10 },
			{ 3243, 8009, 1, 10 },
			{ 3243, 8010, 1, 10 },
			{ 3243, 8011, 1, 20 },
			{ 3243, 8012, 1, 20 },
			{ 3243, 8013, 1, 20 },
			{ 3243, 5295, 1 + Misc.random(2), 49 }, // ranarr seed
			{ 3243, 5296, 1 + Misc.random(2), 14 }, // toadflax seed
			{ 3243, 5297, 1 + Misc.random(2), 16 }, // irit seed
			{ 3243, 5298, 1 + Misc.random(2), 27 }, // avantoe seed
			{ 3243, 5299, 1 + Misc.random(2), 29 }, // kwuarm seed
			{ 3243, 5300, 1 + Misc.random(2), 50 }, // snapdragon seed
			{ 3243, 5301, 1 + Misc.random(2), 30 }, // cadantine seed
			{ 3243, 5302, 1 + Misc.random(2), 40 }, // lantadyme seed
			{ 3243, 5303, 1 + Misc.random(2), 50 }, // dwarfweed seed
			{ 3243, 5304, 1 + Misc.random(2), 80 }, // torstol seed
			{ 3243, 1437, 15 + Misc.random(10), 20 }, // rune ess
			{ 3243, 1624, 1 + Misc.random(4), 11 }, // uncut sapphire
			{ 3243, 1622, 1 + Misc.random(4), 12 }, // uncut emerald
			{ 3243, 1620, 1 + Misc.random(4), 13 }, // uncut ruby
			{ 3243, 1618, 1 + Misc.random(4), 25 }, // uncut diamond
			{ 3243, 1632, 1 + Misc.random(4), 50 }, // uncut dstone

			// Kraken
			{ 4154, 15012, 1, 450 },
			{ 4154, 1618, 5, 5 },
			{ 4154, 386, 8, 10 },
			{ 4154, 995, 1500, 10 },
			{ 4154, 11286, 1, 750 },
			{ 4154, 560, 30, 8 },
			{ 4154, 555, 50, 4 },
			{ 4154, 12524, 1, 1000 },
			{ 4154, 990, 1, 85 },

			// Fire Giant
			{ 110, 995, 5000, 12 },
			{ 110, 1333, 1, 13 },
			{ 110, 1247, 1, 13 },
			{ 110, 830, 40, 14 },
			{ 110, 1319, 1, 13 },
			{ 110, 4587, 1, 11 },
			{ 110, 1079, 1, 13 },
			{ 110, 1147, 1, 10 },
			{ 110, 1149, 1, 50 },
			{ 110, 532, 1, 0 },

			// Elf Warrior
			{ 1183, 526, 1, 0 },
			{ 1183, 4212, 1, 18 },

			// Ogre shaman
			{ 5176, 532, 1, 0 },
			{ 5176, 5291, 1 + Misc.random(8), 3 },
			{ 5176, 5300, 1 + Misc.random(4), 12 },
			{ 5176, 5301, 1 + Misc.random(4), 15 },
			{ 5176, 5302, 1 + Misc.random(3), 19 },
			{ 5176, 5303, 1 + Misc.random(3), 24 },
			{ 5176, 995, 1 + Misc.random(200000), 50 },
			{ 5176, 13085, 1, 1050 },
			{ 5176, 13081, 1, 1050 },

			// Tzhaar
			{ 2591, 6522, 20, 10 }, { 2591, 6523, 1, 10 },
			{ 2591, 6524, 1, 10 }, { 2591, 6525, 1, 10 },
			{ 2591, 6526, 1, 10 }, { 2591, 6527, 1, 10 },
			{ 2591, 6528, 1, 10 }, { 2592, 6522, 20, 10 },
			{ 2592, 6523, 1, 10 }, { 2592, 6524, 1, 10 },
			{ 2592, 6525, 1, 10 }, { 2592, 6526, 1, 10 },
			{ 2592, 6527, 1, 10 }, { 2592, 6528, 1, 10 },
			{ 2593, 6522, 20, 10 }, { 2593, 6523, 1, 10 },
			{ 2593, 6524, 1, 10 }, { 2593, 6525, 1, 10 },
			{ 2593, 6526, 1, 10 }, { 2593, 6527, 1, 10 },
			{ 2593, 6528, 1, 10 }, { 2594, 6522, 20, 10 },
			{ 2594, 6523, 1, 10 }, { 2594, 6524, 1, 10 },
			{ 2594, 6525, 1, 10 }, { 2594, 6526, 1, 10 },
			{ 2594, 6527, 1, 10 }, { 2594, 6528, 1, 10 },
			{ 2595, 6522, 20, 10 }, { 2595, 6523, 1, 10 },
			{ 2595, 6524, 1, 10 }, { 2595, 6525, 1, 10 },
			{ 2595, 6526, 1, 10 }, { 2595, 6527, 1, 10 },
			{ 2595, 6528, 1, 10 }, { 2596, 6522, 20, 10 },
			{ 2596, 6523, 1, 10 }, { 2596, 6524, 1, 10 },
			{ 2596, 6525, 1, 10 }, { 2596, 6526, 1, 10 },
			{ 2596, 6527, 1, 10 }, { 2596, 6528, 1, 10 },
			{ 2597, 6522, 20, 10 }, { 2597, 6523, 1, 10 },
			{ 2597, 6524, 1, 10 }, { 2597, 6525, 1, 10 },
			{ 2597, 6526, 1, 10 }, { 2597, 6527, 1, 10 },
			{ 2597, 6528, 1, 10 }, { 2598, 6522, 20, 10 },
			{ 2598, 6523, 1, 10 }, { 2598, 6524, 1, 10 },
			{ 2598, 6525, 1, 10 }, { 2598, 6526, 1, 10 },
			{ 2598, 6527, 1, 10 }, { 2598, 6528, 1, 10 },
			{ 2599, 6522, 20, 10 }, { 2599, 6523, 1, 10 },
			{ 2599, 6524, 1, 10 }, { 2599, 6525, 1, 10 },
			{ 2599, 6526, 1, 10 }, { 2599, 6527, 1, 10 },
			{ 2599, 6528, 1, 10 }, { 2600, 6522, 20, 10 },
			{ 2600, 6523, 1, 10 }, { 2600, 6524, 1, 10 },
			{ 2600, 6525, 1, 10 }, { 2600, 6526, 1, 10 },
			{ 2600, 6527, 1, 10 }, { 2600, 6528, 1, 10 },
			{ 2601, 6522, 20, 10 }, { 2601, 6523, 1, 10 },
			{ 2601, 6524, 1, 10 }, { 2601, 6525, 1, 10 },
			{ 2601, 6526, 1, 10 }, { 2601, 6527, 1, 10 },
			{ 2601, 6528, 1, 10 }, { 2602, 6522, 20, 10 },
			{ 2602, 6523, 1, 10 }, { 2602, 6524, 1, 10 },
			{ 2602, 6525, 1, 10 }, { 2602, 6526, 1, 10 },
			{ 2602, 6527, 1, 10 }, { 2602, 6528, 1, 10 },
			{ 2603, 6522, 20, 10 }, { 2603, 6523, 1, 10 },
			{ 2603, 6524, 1, 10 }, { 2603, 6525, 1, 10 },
			{ 2603, 6526, 1, 10 }, { 2603, 6527, 1, 10 },
			{ 2603, 6528, 1, 10 }, { 2604, 6522, 20, 10 },
			{ 2604, 6523, 1, 10 }, { 2604, 6524, 1, 10 },
			{ 2604, 6525, 1, 10 }, { 2604, 6526, 1, 10 },
			{ 2604, 6527, 1, 10 }, { 2604, 6528, 1, 10 },
			{ 2605, 6522, 20, 10 }, { 2605, 6523, 1, 10 },
			{ 2605, 6524, 1, 10 }, { 2605, 6525, 1, 10 },
			{ 2605, 6526, 1, 10 }, { 2605, 6527, 1, 10 },
			{ 2605, 6528, 1, 10 }, { 2605, 995, 1, 10 },
			{ 2606, 6522, 20, 10 }, { 2606, 6523, 1, 10 },
			{ 2606, 6524, 1, 10 }, { 2606, 6525, 1, 10 },
			{ 2606, 6526, 1, 10 }, { 2606, 6527, 1, 10 },
			{ 2606, 6528, 1, 10 }, { 2607, 6522, 20, 10 },
			{ 2607, 6523, 1, 10 }, { 2607, 6524, 1, 10 },
			{ 2607, 6525, 1, 10 }, { 2607, 6526, 1, 10 },
			{ 2607, 6527, 1, 10 }, { 2607, 6528, 1, 10 },
			{ 2608, 6522, 20, 10 }, { 2608, 6523, 1, 10 },
			{ 2608, 6524, 1, 10 }, { 2608, 6525, 1, 10 },
			{ 2608, 6526, 1, 10 }, { 2608, 6527, 1, 10 },
			{ 2608, 6528, 1, 10 }, { 2609, 6522, 20, 10 },
			{ 2609, 6523, 1, 10 }, { 2609, 6524, 1, 10 },
			{ 2609, 6525, 1, 10 }, { 2609, 6526, 1, 10 },
			{ 2609, 6527, 1, 10 }, { 2609, 6528, 1, 10 }, { 2609, 995, 1, 10 },
			{ 2610, 6522, 20, 10 }, { 2610, 6523, 1, 10 },
			{ 2610, 6524, 1, 10 }, { 2610, 6525, 1, 10 },
			{ 2610, 6526, 1, 10 }, { 2610, 6527, 1, 10 },
			{ 2610, 6528, 1, 10 }, { 2610, 995, 1, 10 },
			{ 2611, 6522, 20, 10 }, { 2611, 6523, 1, 10 },
			{ 2611, 6524, 1, 10 }, { 2611, 6525, 1, 10 },
			{ 2611, 6526, 1, 10 }, { 2611, 6527, 1, 10 },
			{ 2611, 6528, 1, 10 }, { 2611, 995, 1, 10 },
			{ 2612, 6522, 20, 10 }, { 2612, 6523, 1, 10 },
			{ 2612, 6524, 1, 10 }, { 2612, 6525, 1, 10 },
			{ 2612, 6526, 1, 10 }, { 2612, 6527, 1, 10 },
			{ 2612, 6528, 1, 10 }, { 2612, 995, 1, 10 },
			{ 2613, 6522, 20, 10 }, { 2613, 6523, 1, 10 },
			{ 2613, 6524, 1, 10 }, { 2613, 6525, 1, 10 },
			{ 2613, 6526, 1, 10 }, { 2613, 6527, 1, 10 },
			{ 2613, 6528, 1, 10 }, { 2613, 995, 1, 10 },
			{ 2614, 6522, 20, 10 }, { 2614, 6523, 1, 10 },
			{ 2614, 6524, 1, 10 }, { 2614, 6525, 1, 10 },
			{ 2614, 6526, 1, 10 }, { 2614, 6527, 1, 10 },
			{ 2614, 6528, 1, 10 }, { 2614, 995, 1, 10 },
			{ 2615, 6522, 20, 10 }, { 2615, 6523, 1, 10 },
			{ 2615, 6524, 1, 10 }, { 2615, 6525, 1, 10 },
			{ 2615, 6526, 1, 10 }, { 2615, 6527, 1, 10 },
			{ 2615, 6528, 1, 10 }, { 2615, 995, 1, 10 },
			{ 2616, 6522, 20, 10 }, { 2616, 6523, 1, 10 },
			{ 2616, 6524, 1, 10 }, { 2616, 6525, 1, 10 },
			{ 2616, 6526, 1, 10 }, { 2616, 6527, 1, 10 },
			{ 2616, 6528, 1, 10 }, { 2616, 995, 1, 10 },


	};

	/**
	 * Glory locations.
	 */
	public static final int EDGEVILLE_X = 3087;
	public static final int EDGEVILLE_Y = 3500;
	public static final String EDGEVILLE = "";
	public static final int AL_KHARID_X = 3293;
	public static final int AL_KHARID_Y = 3174;
	public static final String AL_KHARID = "";
	public static final int KARAMJA_X = 3087;
	public static final int KARAMJA_Y = 3500;
	public static final String KARAMJA = "";
	public static final int MAGEBANK_X = 2538;
	public static final int MAGEBANK_Y = 4716;
	public static final String MAGEBANK = "";

	/**
	 * Teleport spells.
	 **/
	/*
	 * Modern spells
	 */
	public static final int VARROCK_X = 3210;
	public static final int VARROCK_Y = 3424;
	public static final String VARROCK = "";

	public static final int LUMBY_X = 3222;
	public static final int LUMBY_Y = 3218;
	public static final String LUMBY = "";

	public static final int FALADOR_X = 2964;
	public static final int FALADOR_Y = 3378;
	public static final String FALADOR = "";

	public static final int CAMELOT_X = 2757;
	public static final int CAMELOT_Y = 3477;
	public static final String CAMELOT = "";

	public static final int ARDOUGNE_X = 2662;
	public static final int ARDOUGNE_Y = 3305;
	public static final String ARDOUGNE = "";

	public static final int WATCHTOWER_X = 3087;
	public static final int WATCHTOWER_Y = 3500;
	public static final String WATCHTOWER = "";

	public static final int TROLLHEIM_X = 3243;
	public static final int TROLLHEIM_Y = 3513;
	public static final String TROLLHEIM = "";

	/*
	 * Ancient spells
	 */
	public static final int PADDEWWA_X = 3098;
	public static final int PADDEWWA_Y = 9884;

	public static final int SENNTISTEN_X = 3322;
	public static final int SENNTISTEN_Y = 3336;

	public static final int KHARYRLL_X = 3492;
	public static final int KHARYRLL_Y = 3471;

	public static final int LASSAR_X = 3006;
	public static final int LASSAR_Y = 3471;

	public static final int DAREEYAK_X = 3161;
	public static final int DAREEYAK_Y = 3671;

	public static final int CARRALLANGAR_X = 3156;
	public static final int CARRALLANGAR_Y = 3666;

	public static final int ANNAKARL_X = 3288;
	public static final int ANNAKARL_Y = 3886;

	public static final int GHORROCK_X = 2977;
	public static final int GHORROCK_Y = 3873;
	//

	/**
	 * Timeout time.
	 */
	public static final int TIMEOUT = 20;

	/**
	 * Cycle time.
	 */
	public static final int CYCLE_TIME = 600;

	/**
	 * Buffer size.
	 */
	public static final int BUFFER_SIZE = 512;

	/**
	 * Slayer Variables.
	 */
	public static final int[][] SLAYER_TASKS = { { 1, 87, 90, 4, 5 }, // low
																		// tasks
			{ 6, 7, 8, 9, 10 }, // med tasks
			{ 11, 12, 13, 14, 15 }, // high tasks
			{ 1, 1, 15, 20, 25 }, // low reqs
			{ 30, 35, 40, 45, 50 }, // med reqs
			{ 60, 75, 80, 85, 90 } }; // high reqs

	/**
	 * Skill experience multipliers.
	 */
	public static final int WOODCUTTING_EXPERIENCE = 30;
	public static final int MINING_EXPERIENCE = 30;
	public static final int SMITHING_EXPERIENCE = 55;
	public static final int FARMING_EXPERIENCE = 38;
	public static final int FIREMAKING_EXPERIENCE = 25;
	public static final int HERBLORE_EXPERIENCE = 30;
	public static final int FISHING_EXPERIENCE = 15;
	public static final int AGILITY_EXPERIENCE = 80;
	public static final int PRAYER_EXPERIENCE = 50;
	public static final int RUNECRAFTING_EXPERIENCE = 100;
	public static final int CRAFTING_EXPERIENCE = 20;
	public static final int THIEVING_EXPERIENCE = 100;
	public static final int SLAYER_EXPERIENCE = 50;
	public static final int COOKING_EXPERIENCE = 60;
	public static final int FLETCHING_EXPERIENCE = 65;
	public static final int INCREASE_SPECIAL_AMOUNT_ZAM = 14500;

	public static final boolean goodDistance(int objectX, int objectY,
			int playerX, int playerY, int distance) {
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if (objectX + i == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				} else if (objectX - i == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& (objectY + j == playerY || objectY - j == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}
}