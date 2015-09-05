package server.game.players.content.skills.farming.trees;

import server.game.players.Client;
import server.util.Misc;

public class TreeFarming {

	public static void farmTree(Client client, int seed) {
		for (final Trees.Seeds t : Trees.Seeds.values()) {
			if (t.getSeed() == seed) {
				if (client.playerLevel[19] < t.getLevelReq()) {
					client.sendMessage("You need a Farming level of "+t.getLevelReq()+" to plant this seed.");
					return;
				}
				client.startAnimation(2291);
				client.getItems().deleteItem2(t.getSeed(), 1);
				client.getPA().addSkillXP(t.getExp(), 19);
				client.currentTree = t.getLog();
				client.treeAmount = TreePicking.getTreeAmount(client, t.getSeed());
				client.getPA().object(treeType(client), 2813, 3463, -1, 10);
			}
		}
	}
	
	private static int treeType(Client client) {
		for (final Trees.Seeds t : Trees.Seeds.values()) {
			if (t.getLog() == client.currentTree) {
				if (Misc.random(10) == 5) {
					client.sendMessage("You plant the seed into the ground. Unfortunaly the tree was diseased and died.");
					return t.getDead();
				} else {
					client.sendMessage("You plant the seed into the ground, and it grows into a tree.");
					return t.getHealthy();
				}
			}
		}
		return -1;
	}

	
	public static boolean isTreeSeed(Client client, int seed) {
		for (final Trees.Seeds t : Trees.Seeds.values()) {
			if (seed == t.getSeed()) {
				return true;
			}
		}
		return false;
	}

}
