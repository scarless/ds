package server.game.players.content.random;

import server.game.players.Client;
import server.event.EventManager;
import server.event.Event;
import server.event.EventContainer;
import server.util.Misc;



public class WeaponChest {

	private static final int[] CHEST_REWARDS = { 1079, 1093, 1127, 1333, 1149, 2363, 4037, 4039, 7462, 2643, 6760, 6762, 6764, 7804, 4587, 1942, 4087, 6585, 6737, 2583, 2585, 2591, 2593, 2587, 2595, 2615, 2617, 2623, 2625, 2619, 2627, 3476, 3477, 2639, 2641, 1319, 4131, 4585, 6617, 6625, 6623, 10370, 10372, 10386, 10388, 10378, 10380, 10390};
	public static final int KEY = 7297;
	private static final int OPEN_ANIMATION = 881;

	public static boolean canOpen(Client c) {
		if (c.getItems().playerHasItem(KEY)) {
			return true;
		} else {
			c.sendMessage("The chest is locked. You need Weapon Key to unlock this chest.");
			return false;
		}
	}

	public static void searchChest(final Client c, final int id, final int x,
			final int y) {
		if (canOpen(c)) {
			c.sendMessage("You unlock the chest with your key.");
			c.getItems().deleteItem(KEY, 1);
			c.startAnimation(OPEN_ANIMATION);
			c.getPA().checkObjectSpawn(id + 1, x, y, 2, 10);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer e) {
					c.getItems().addItem(
							CHEST_REWARDS[Misc.random(getLength() - 1)], 1);
					c.sendMessage("You find a rare weapon in the chest!");
					c.getPA().checkObjectSpawn(id, x, y, 2, 10);

					e.stop();
				}
			}, 1800);
		}
	}

	public static int getLength() {
		return CHEST_REWARDS.length;
	}
}