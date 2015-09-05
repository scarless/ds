package server.game.players.content.random;

import server.game.players.Client;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.util.Misc;



public class WeaponCase {

	public static boolean Canusebox = true;
	
	public static int RandomWep [] = 
	{15107, 15003, 11694, 4151, 4153, 13889 }; // Add more item Id's

	public static int GenerateMyrsteryPrize(final Client c) {
		 EventManager.getSingleton().addEvent(new Event() {
			int BoxTimer = 3;
			public void execute(EventContainer Box) {
				Canusebox = false;
				if (BoxTimer == 3) {
					c.sendMessage("Opening your Weapon case...");
				}
				if (BoxTimer == 0) {
					int Random = Misc.random(100);
					//if (c.getItems().playerHasItem(13082, 1))
					if (c.getItems().playerHasItem(7297, 1))
					if (Random <= 64) {
						//c.getItems().deleteItem(13082, c.getItems().getItemSlot(13082), 1);
						c.getItems().deleteItem(7297, c.getItems().getItemSlot(7297), 1);
						c.getItems().addItem(RandomWep[(int) (Math.random() * RandomWep.length)], 1);
						c.sendMessage("@red@You opened your Weapon Case. You go");

				}
			}
			                    else
                    {
                        c.sendMessage("You need Weapon Case Key to open this box.");
                    }
				if (c == null || BoxTimer <= 0) {
				   	Box.stop();
					Canusebox = true;
                    return; 
				}
				if (BoxTimer >= 0) {
					BoxTimer--;
				}
			}
		}, 1000);
		return RandomWep[(int) (Math.random() * RandomWep.length)];
	}
	
	public static void Open(int itemID, Client c) {
		if (itemID == 13082) {
			if (c.getItems().freeSlots() > 1) {
				if (Canusebox == true) {
					c.getItems().deleteItem(13082, 1);
					GenerateMyrsteryPrize(c);
				} else {
					c.sendMessage("Please wait while your current process is finished.");
				}
			} else {
				c.sendMessage("You need atleast 2 slots to open the Weapon Case.");
			}
		}
	}
	
}