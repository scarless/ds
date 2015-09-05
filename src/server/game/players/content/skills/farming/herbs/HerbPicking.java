package server.game.players.content.skills.farming.herbs;

import server.game.players.Client;
import server.game.players.content.skills.farming.Farming;
import server.util.Misc;

public class HerbPicking {
	
	public static void herbPicking(Client client, int herb) {
		for (final Herbs.Seeds h : Herbs.Seeds.values()) {
			if (herb == h.getHerb()) {
				if (client.herbAmount <= 0)
					return;
				if (client.getItems().freeSlots() < 1) {
					client.sendMessage("You don't have any free space to do this!");
					return;
				}
				if (client.herbAmount == 1) {
					client.currentHerb = -1;
					client.herbAmount = -1;
					client.getPA().object(Farming.WEED_PATCH, client.objectX, client.objectY, -1, 10);
					client.sendMessage("You finish picking all the herbs off of the patch.");
				}
				client.startAnimation(2286);
				client.herbAmount--;
				client.getItems().addItem(h.getHerb(), 1);
				client.getPA().addSkillXP(h.getSeedExp(), 19);
			}
		}
	}
	
	public static int getHerbAmount(Client client, int herb) {
		return (herb < 5300) ? 4 + Misc.random(4) : 2 + Misc.random(6);
	}

}
