package server.game.players.content.random;

import server.game.players.Client;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.util.Misc;



public class MysteryBox {

	public static boolean Canusebox = true;
	
	public static int Common [] = 
	{1323, 1313, 1315, 1321, 1109, 822, 1163, 1199,}; // Add more item Id's
	
	public static int Uncommon [] = 
	{10828, 4587, 4586, 9185, 1149, 1305, 1434, 1249, 1187, 5698,}; // Add more item Id's
	
	public static int Rare [] = 
	{4151, 6731, 6733, 6735, 11732, 6737, 1377, 2572, 6568, 6739, 11730, 11702, 11704, 11706, 11708}; // Add more item Id's
	

	public static int GenerateMyrsteryPrize(final Client c) {
		 EventManager.getSingleton().addEvent(new Event() {
			int BoxTimer = 2;
			int Coins = 50000 + Misc.random(25000);
			public void execute(EventContainer Box) {
				Canusebox = false;
				if (BoxTimer == 2) {
					c.sendMessage("Calculating prize...");
				}
				if (BoxTimer == 0) {
					c.getItems().addItem(995, Coins);
					int Random = Misc.random(100);
					if (Random <= 64) {
						c.getItems().addItem(Common[(int) (Math.random() * Common.length)], 1);
						c.sendMessage("You have recieved a @gre@common @bla@item and @blu@"+ Coins +" @bla@coins.");
					} else if (Random >= 65 && Random <= 89) {
						c.getItems().addItem(Uncommon[(int) (Math.random() * Uncommon.length)], 1);
						c.sendMessage("You have recieved an @yel@uncommon @bla@item and @blu@"+ Coins +" @bla@coins.");
					} else if (Random >= 90 && Random <= 100) {
						c.getItems().addItem(Rare[(int) (Math.random() * Rare.length)], 1);
						c.sendMessage("You have recieved a @red@rare @bla@item and @blu@"+ Coins +" @bla@coins.");
					}
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
		return Common[(int) (Math.random() * Common.length)];
	}
	
	public static void Open(int itemID, Client c) {
		if (itemID == 6199) {
			if (c.getItems().freeSlots() > 1) {
				if (Canusebox == true) {
					c.getItems().deleteItem(6199, 1);
					GenerateMyrsteryPrize(c);
				} else {
					c.sendMessage("Please wait while your current process is finished.");
				}
			} else {
				c.sendMessage("You need atleast 2 slots to open the Mystery box.");
			}
		}
	}
	
}