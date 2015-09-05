package server.game.players.content.skills.farming.herbs;

import server.game.players.Client;



public class HerbFarming {
	
	public static void farmHerbs(Client client, int seed) {
		for (final Herbs.Seeds h : Herbs.Seeds.values()) {
			if (h.getSeed() == seed) {
				if (client.playerLevel[19] < h.getLevelReq()) {
					client.sendMessage("You need a Farming level of "+h.getLevelReq()+" to plant this seed.");
					return;
				}
				client.startAnimation(2291);
				@SuppressWarnings("unused")
				Object c;
				client.sendMessage("You plant the seed into the ground, and it grows into a herb.");
				client.getItems().deleteItem2(h.getSeed(), 1);
				client.getPA().addSkillXP(h.getSeedExp(), 19);
				client.currentHerb = h.getHerb();
				client.herbAmount = HerbPicking.getHerbAmount(client, h.getHerb());
				client.getPA().object(Herbs.HERB_PICKING, 2813, 3463, -1, 10);
			}
		}
	}
	
	public static boolean isHerbSeed(Client client, int seed) {
		for (final Herbs.Seeds h : Herbs.Seeds.values()) {
			if (seed == h.getSeed()) {
				return true;
			}
		}
		return false;
	}

}
