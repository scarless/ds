package server.game.players.content.skills.farming.trees;

public class Trees {
	
	public static enum Seeds {
		APPLE_TREE(5283, 7948, 7960, 1955, 27, 6200),
		BANANA_TREE(5284, 8006, 8018, 1963, 33, 7500),
		ORANGE_TREE(5285, 8064, 8076, 2108, 39, 9300),
		CURRY_TREE(5286, 8033, 8045, 5970, 42, 11600),
		PALM_TREE(5289, 8091, 8103, 2339, 68, 28300),
		WILLOW_TREE(5313, 8488, 8501, 1519, 30, 10500),
		MAPLE_TREE(5314, 8444, 8461, 1517, 45, 15875),
		YEW_TREE(5315, 8513, 8534, 1515, 60, 21250),
		MAGIC_SEED(5316, 8409, 8434, 1513, 75, 31250);

		private int seed, healthy, dead, log, levelReq, experience;
		private Seeds(final int seed, final int healthy, final int dead, final int log, final int levelReq, final int experience) {
			this.seed = seed;
			this.healthy = healthy;
			this.dead = dead;
			this.log = log;
			this.levelReq = levelReq;
			this.experience = experience;
		}
		public int getSeed() {
			return seed;
		}
		public int getHealthy() {
			return healthy;
		}
		public int getDead() {
			return dead;
		}
		public int getLog() {
			return log;
		}
		public int getLevelReq() {
			return levelReq;
		}
		public int getExp() {
			return experience;
		}
	}

}
