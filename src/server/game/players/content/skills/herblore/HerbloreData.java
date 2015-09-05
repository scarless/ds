package server.game.players.content.skills.herblore;

public class HerbloreData {

	public static final int TAR = 1939;

	public static final int WATER_VIAL = 227;

	public static final int PESTLE = 233;

	public static final int ANIM = 363, ANIM2 = 364;

	public static final int[][] CLEAN_DATA = { { 199, 249, 1, 3 }, { 201, 251, 5, 4 },
			{ 203, 253, 11, 5 }, { 205, 255, 20, 6 }, { 207, 257, 25, 8 }, { 209, 259, 40, 9 },
			{ 211, 261, 48, 10 }, { 213, 263, 54, 11 }, { 215, 265, 65, 12 }, { 217, 267, 70, 13 },
			{ 219, 269, 75, 15 }, { 3049, 2998, 30, 7 }, { 12174, 12172, 35, 8 },
			{ 14836, 14854, 30, 9 }, { 3051, 3000, 59, 12 }, { 2485, 2481, 67, 13 },
			{ 1525, 152, 1, 3 }, { 1527, 1528, 1, 3 }, { 1529, 1530, 1, 3 }, { 1531, 1532, 1, 3 },
			{ 1533, 1534, 1, 3 } };

	public enum PotionData {
		ATTACK_POTION(91, 221, 121, 1, 25, false), ANTIPOISON(93, 235, 175, 5, 38, false), STRENGTH_POTION(
				95, 225, 115, 12, 50, false), RESTORE_POTION(97, 223, 127, 22, 63, false), DEFENCE_POTION(
				99, 239, 133, 30, 75, false), COMBAT_POTION(97, 9736, 9741, 36, 84, false), PRAYER_POTION(
				99, 231, 139, 38, 88, false), SUPER_ATTACK(101, 221, 145, 45, 100, false), SUPER_ANTIPOISON(
				101, 235, 181, 48, 106, false), FISHING_POTION(103, 231, 151, 50, 112, false), SUPER_STRENGTH(
				105, 225, 157, 55, 125, false), WEAPON_POISON3(105, 241, 5940, 60, 137, false), SUPER_RESTORE(
				3004, 223, 3026, 63, 142, false), SUPER_DEFENCE(107, 239, 163, 66, 150, false), ANTIFIRE_POTION(
				2483, 241, 2454, 69, 158, false), RANGING_POTION(109, 245, 169, 72, 163, false), MAGIC_POTION(
				2483, 3138, 3042, 76, 173, false), ZAMORAK_BREW(111, 247, 189, 78, 175, false), SARADOMIN_BREW(
				3002, 6693, 6687, 81, 180, false), 
				/*EXTREME_STRENGTH(157, 267, 15313, 89, 230, false), EXTREME_ATTACK(
				145, 261, 15309, 88, 220, false), EXTREME_DEFENCE(163, 2481, 15317, 90, 240, false), EXTREME_MAGIC(
				3042, 9594, 15321, 91, 250, false), EXTREME_RANGING(169, 267, 15325, 92, 260, false), SUPER_PRAYER(
				139, 4255, 15329, 94, 270, false), RECOVER_SPECIAL(3018, 5972, 15301, 84, 200,
				false), HUNTER_POTION(103, 10111, 10000, 53, 120, false), SUMMONING_POTION(12181,
				12654, 12142, 40, 92, false),*/ GUAM_POTION(WATER_VIAL, 249, 91, 1, 0, false), MARRENTILl_POTION(
				WATER_VIAL, 251, 93, 5, 0, false), TARROMIN_POTION(WATER_VIAL, 253, 95, 12, 0,
				false), HARRALANDER_POTION(WATER_VIAL, 255, 97, 22, 0, false), RANARR_POTION(
				WATER_VIAL, 257, 99, 30, 0, false), IRIT_POTION(WATER_VIAL, 259, 101, 34, 0, false), AVANTOE_POTION(
				WATER_VIAL, 261, 103, 45, 0, false), KWUARM_POTION(WATER_VIAL, 263, 105, 55, 0,
				false), CADANTINE_POTION(WATER_VIAL, 265, 107, 66, 0, false), DWARF_WEED_POTION(
				WATER_VIAL, 267, 109, 72, 0, false), TORSTOL_POTION(WATER_VIAL, 269, 111, 75, 0,
				false), LANTADYME_POTION(WATER_VIAL, 2481, 2483, 67, 0, false), SNAPDRAGON_POTION(
				WATER_VIAL, 3000, 3004, 59, 0, false), TOADFLAX_POTION(WATER_VIAL, 2998, 3002, 30,
				0, false), SPIRIT_WEED_POTION(WATER_VIAL, 12172, 12181, 40, 0, false), GUAM_TAR(
				249, TAR, 10142, 19, 30, true), MARRENTILL_TAR(251, TAR, 10143, 31, 43, true), TARROMIN_TAR(
				253, TAR, 10144, 39, 55, true), HARRALANDER_TAR(255, TAR, 10145, 44, 73, true);

		public int primary, secondary, finished;

		public int levelReq, expEarned;

		public boolean tar;

		private PotionData(final int primary, final int secondary, final int finished,
				final int levelReq, final int expEarned, final boolean tar) {
			this.primary = primary;
			this.secondary = secondary;
			this.finished = finished;
			this.expEarned = expEarned;
			this.tar = tar;
		}

		public int getPrimary() {
			return primary;
		}

		public int getSecondary() {
			return secondary;
		}

		public int getFinal() {
			return finished;
		}

		public int getReq() {
			return levelReq;
		}

		public int getExp() {
			return expEarned;
		}

		public boolean isTar() {
			return tar;
		}
	}
	
	public enum GrindableData {
		UNICORN_HORN(237, 235), CHOCOLATE_BAR(1973, 1975), BIRDS_NEST(5075,
				6693), KEBBIT_TEETH(10109, 10111), BLUE_DRAGON_SCALE(243, 241), DIAMOND_ROOT(
				14703, 14704), DESERT_GOAT_HORN(9735, 9736), RUNE_SHARDS(6466,
				6467), ASHES(592, 8865), SEAWEED(401, 6683);

		public int input;
		public int output;

		private GrindableData(final int input, final int output) {
			this.input = input;
			this.output = output;
		}

		public int getInput() {
			return input;
		}

		public int getOutput() {
			return output;
		}
	}
	
}
