package server.game.players.content;

import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.players.Client;

/**
 * @author Patrick van Elderen
 * 
 */
public class FlourMill {

	public static int EMPTY_POT = 1931, POT_OF_FLOUR = 1933, GRAIN = 1947,
			EMY_FLOUR_BIN = 1781, FULL_FLOUR_BIN = 1782;
	/**
	 * Limits the amount of flour. (RS-Limit = 30)
	 */
	public static int LIMIT = 30;

	/**
	 * Item on object.(Use grain on hopper)
	 * 
	 * @param c
	 */
	public static void grainOnHopper(Client c, int objectID, int itemId) {
		if (itemId == GRAIN) {
			// Grain amount - flour amount. Prevents putting more than 30
			if (c.grain == LIMIT - c.flourAmount || c.flourAmount == LIMIT) {
				c.sendMessage("You can't put anymore grain into the hopper.");
				return;
			}
			c.startAnimation(832);
			c.getItems().deleteItem(GRAIN, 1);
			c.grain++;// + 1
			c.sendMessage("You put the grain in the hopper.");
		} else {
			c.sendMessage("Nothing interesting happens.");
		}
	}

	/**
	 * When player operates the lever.
	 * 
	 * @param c
	 */
	public static void hopperControl(final Client c) {
		if (c.grain > 0) {
			if (c.flourAmount == LIMIT) {
				c.sendMessage("There is currently too much grain in the hopper.");
				return;
			}
			c.sendMessage("You operate the hopper. The grain slide down the chute");
			c.startAnimation(832);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.heightLevel == 2) { // if height level is 2. NO OBJECT
						return;
					}
					if (c.heightLevel == 0) {
						c.getPA().object(FULL_FLOUR_BIN, 3166, 3306, 0, 10);
						container.stop();
					}
				}
				@Override
				public void stop() {
				}
			}, 1);
			c.flourAmount += c.grain;
			if (c.flourAmount > LIMIT)// if somehow flouramout exceeds limit
				c.flourAmount = LIMIT;// Flour amount returns to limit.
			c.grain = 0;
		} else {
			c.startAnimation(832);
			c.sendMessage("You operate the hopper. Nothing interesting happens.");
		}
	}

	/**
	 * Emptys the flour bin...
	 * 
	 * @param c
	 */
	public static void emptyFlourBin(Client c) {
		if (c.getItems().playerHasItem(EMPTY_POT, 1) && c.flourAmount > 0) {
			c.getItems().deleteItem(EMPTY_POT, 1);
			c.getItems().addItem(POT_OF_FLOUR, 1);
			c.sendMessage("You fill a pot with flour from the bin.");
			c.flourAmount--;// Removes amount of flour.
			if (c.flourAmount < 0)// *JUST A CHECK FOR BUGS.
				c.flourAmount = 0;
			if (c.flourAmount == 0) {
				c.getPA().object(EMY_FLOUR_BIN, 3166, 3306, 0, 10);
				c.sendMessage("The flour bin is now empty.");
			}
		} else {
			c.sendMessage("You don't have an empty pot to fill flour with.");
		}
	}
}