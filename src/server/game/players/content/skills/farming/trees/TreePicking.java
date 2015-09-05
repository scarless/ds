package server.game.players.content.skills.farming.trees;

import server.game.players.Client;
import server.game.players.content.skills.farming.Farming;
import server.util.Misc;

public class TreePicking {
	
	public static void treePicking(Client client, int seed) {
		for (final Trees.Seeds t : Trees.Seeds.values()) {
			if (seed == t.getLog()) {
				if (client.treeAmount <= 0)
					return;
				if (client.getItems().freeSlots() < 1) {
					client.sendMessage("You don't have any free space to do this!");
					return;
				}
				if (client.treeAmount == 1) {
					client.currentTree = -1;
					client.treeAmount = -1;
					client.getPA().object(Farming.WEED_PATCH, client.objectX, client.objectY, -1, 10);
					client.sendMessage("You finish picking all the resources off the tree.");
				}
				client.startAnimation(2286);
				client.treeAmount--;
				client.getItems().addItem(t.getLog(), 1);
				client.getPA().addSkillXP(t.getExp(), 19);
			}
		}
	}

	public static int getTreeAmount(Client client, int seed) {
		return (seed < 5286) ? 4 + Misc.random(4) : 2 + Misc.random(6);
	}
	
	public static void removeDeadTree(Client client) {
		client.getPA().object(Farming.WEED_PATCH, client.objectX, client.objectY, -1, 10);
		client.sendMessage("You remove the dead tree from the patch.");
	}

}
