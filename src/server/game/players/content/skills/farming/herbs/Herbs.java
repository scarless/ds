package server.game.players.content.skills.farming.herbs;


public class Herbs {
	
	public final static int HERB_PICKING = 8143;
	
	public static enum Seeds {
		GUAM_SEED(5291, 199, 100, 1),
		MARRENTILL_SEED(5292, 201, 150, 14),
		TARROMIN_SEED(5293, 203, 250, 19),
		HARRALANADER_SEED(5294, 205, 400, 26),
		RANARR_SEED(5295, 207, 400, 32),
		TOADFLAX_SEED(5296, 3049, 500, 38),
		IRIT_SEED(5297, 209, 750, 44),
		AVANTOE_SEED(5298, 211, 1000, 50),
		KWUARM_SEED(5299, 213, 1500, 56),
		SNAPDRAGON_SEED(5300, 3051, 1750, 62),
		CADANTINE_SEED(5301, 215, 2200, 67),
		LANTADYME_SEED(5302, 2485, 2750, 73),
		DWARF_SEED(5303, 217, 3500, 79),
		TORTSOL_SEED(5304, 219, 5000, 85);

		private int itemId, herb, seedExp, levelReq;
		private Seeds(final int itemId, final int herb, final int seedExp, final int levelReq) {
			this.itemId = itemId;
			this.herb = herb;
			this.seedExp = seedExp;
			this.levelReq = levelReq;
		}
		public int getSeed() {
			return itemId;
		}
		public int getHerb() {
			return herb;
		}
		public int getSeedExp() {
			return seedExp;
		}
		public int getLevelReq() {
			return levelReq;
		}
	}

}
