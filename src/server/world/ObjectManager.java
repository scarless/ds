package server.world;

import java.util.ArrayList;

import server.Server;
import server.game.objects.Object;
import server.game.players.Client;
import server.game.players.Player;
import server.game.players.PlayerHandler;
import server.game.players.content.FlourMill;
import server.util.Misc;

/**
 * @author Sanity
 */

public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();

	public void process() {
		for (final Object o : objects) {
			if (o.tick > 0) {
				o.tick--;
			} else {
				updateObject(o);
				toRemove.add(o);
			}
		}
		for (final Object o : toRemove) {
			/*
			 * if (o.objectId == 2732) { for (final Player player :
			 * PlayerHandler.players) { if (player != null) { final Client c =
			 * (Client)player; Server.itemHandler.createGroundItem(c, 592,
			 * o.objectX, o.objectY, 1, c.playerId); } } }
			 */
			if (isObelisk(o.newId)) {
				final int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);
		}
		toRemove.clear();
	}

	public boolean objectExists(final int x, final int y) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y) {
				return true;
			}
		}
		return false;
	}

	public void removeObject(int x, int y) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client) Server.playerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);
				c.getPA().object(158, 3097, 3493, 0, 10);
			}
		}
	}

	public void updateObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);
			}
		}
	}

	public void placeObject(Object o) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
							o.type);
			}
		}
	}

	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}
		return null;
	}

	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o, c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face,
						o.type);
		}
		loadCustomSpawns(c);
		Deletewalls(c);
	}

	public void Deletewalls(Client c) {
		/*
		 * c.getPA().checkObjectSpawn(-1, 3207, 3214, 1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3207, 3222, 1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3215, 3225, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3215, 3211, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3208, 3211, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3215, 3211, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3094, 3510, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3095, 3510, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3092, 3511, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3094, 3507, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3095, 3507, -1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3093, 3512, 1, 10);
		 * c.getPA().checkObjectSpawn(-1, 3093, 3511, -1, 4);
		 * c.getPA().checkObjectSpawn(-1, 3093, 3511, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3094, 3511, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3094, 3514, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3094, 3513, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3093, 3514, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3092, 3511, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3091, 3511, -1, 0);
		 * c.getPA().checkObjectSpawn(-1, 3092, 3506, -1, 0);
		 */
		
		// shops
		c.getPA().checkObjectSpawn(4483, 3371, 2973, 5, 10); //shops bank chest
		c.getPA().checkObjectSpawn(4483, 3371, 2972, 5, 10); //shops bank chest
		c.getPA().checkObjectSpawn(-1, 3207, 3214, 1, 0);//lummy wall
		c.getPA().checkObjectSpawn(-1, 3207, 3222, 1, 0);//lummy wall
		
		//home edge 
		c.getPA().checkObjectSpawn(-1, 3101, 3509, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3101, 3510, 0, 0);
		c.getPA().checkObjectSpawn(71, 3101, 3505, -1, 10);// funPk
		
		
		c.getPA().checkObjectSpawn(-1, 2327, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2328, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2329, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2330, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2331, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2332, 3685, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2149, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2664, 4706, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 4706, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2664, 4705, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 4705, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2148, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2146, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2145, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2144, 5101, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5096, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5095, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5094, 0, 2); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5093, 0, 2); // shit
		c.getPA().checkObjectSpawn(14924, 2332, 3689, 1, 2); // Home door??
		c.getPA().checkObjectSpawn(13291, 2642, 4689, 3, 10);//magic chest
		c.getPA().checkObjectSpawn(375, 3091, 3512, 3, 10);// crystal key chest
		c.getPA().checkObjectSpawn(375, 2643, 4688, 3, 10);// crystal key chest
		//wilderness
				c.getPA().checkObjectSpawn(-1, 3225, 3904, -1, 0);
				c.getPA().checkObjectSpawn(-1, 3224, 3904, -1, 0);
	}

	@SuppressWarnings("unused")
	private int[][] customObjects = { {} };

	public static void loadCustomSpawns(Client c) {

		if (c.flourAmount > 0 && c.heightLevel == 0)
			c.getPA().checkObjectSpawn(FlourMill.FULL_FLOUR_BIN, 3166, 3306, 0,
					10);
		c.getPA().checkObjectSpawn(-1, 3112, 3514, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3111, 3514, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3112, 3515, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3111, 3515, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2332, 3687, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2332, 3686, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2332, 3691, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2332, 3692, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2332, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2331, 3692, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2331, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2330, 3692, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2330, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2329, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2328, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2327, 3693, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2327, 3691, 0, 10);
		;
		c.getPA().checkObjectSpawn(-1, 2327, 3689, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2327, 3688, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2327, 3687, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2329, 3686, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2327, 3687, 0, 10);
		c.getPA().checkObjectSpawn(354, 3333, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3334, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3335, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3336, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3337, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3338, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3339, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3340, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3341, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3342, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3343, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3344, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2814, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2813, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2812, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2811, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2810, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2809, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3344, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3343, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3342, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3341, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(9398, 2663, 4706, 0, 10);// donatorzone fishing bank
		c.getPA().checkObjectSpawn(1306, 2651, 4716, 0, 10); // magic tree donator zone
		c.getPA().checkObjectSpawn(1306, 2651, 4712, 0, 10); // magic tree donator zone
		c.getPA().checkObjectSpawn(1306, 2653, 4708, 0, 10); // magic tree donator zone
		c.getPA().checkObjectSpawn(1306, 2657, 4707, 0, 10); // magic tree donator zone
		c.getPA().checkObjectSpawn(354, 3340, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3339, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3338, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3337, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3336, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3335, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3334, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3333, 2806, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3333, 2807, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3333, 2808, -1, 10);// funPk
		// c.getPA().checkObjectSpawn(354, 3334, 2809, -1, 10);//funPk
		c.getPA().checkObjectSpawn(354, 3332, 2808, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3330, 2809, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3330, 2810, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3330, 2811, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3332, 2812, -1, 10);// funPk
		c.getPA().checkObjectSpawn(409, 2637, 4693, 0, 10);// dzone white altar
		c.getPA().checkObjectSpawn(26972, 2588, 9423, 0, 10);// dzone bank
		// c.getPA().checkObjectSpawn(354, 3333, 2811, -1, 10);//funPk
		c.getPA().checkObjectSpawn(354, 3333, 2812, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3333, 2813, -1, 10);// funPk
		c.getPA().checkObjectSpawn(2213, 3331, 2809, -1, 10);// funPk
		c.getPA().checkObjectSpawn(2213, 3331, 2811, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3331, 2812, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3331, 2808, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2808, -1, 10);// funPk
		c.getPA().checkObjectSpawn(354, 3345, 2807, -1, 10);// funPk
		c.getPA().checkObjectSpawn(1281, 2843, 3436, -1, 10);// funPk
		c.getPA().checkObjectSpawn(172, 2331, 3672, 0, 10);// Comp
		c.getPA().checkObjectSpawn(375, 2333, 3672, 0, 10); // crystal

		// Cool
		c.getPA().checkObjectSpawn(10284, 3564, 3287, 0, 10); // edge B Chest
		c.getPA().checkObjectSpawn(4874, 3094, 3500, 1, 10); // edge banana
		c.getPA().checkObjectSpawn(4878, 3098, 3500, 3, 10); // edge Scimitar
		c.getPA().checkObjectSpawn(4875, 3095, 3500, 3, 10); // edge Crafting
		c.getPA().checkObjectSpawn(4876, 3096, 3500, 3, 10); // edge general
		c.getPA().checkObjectSpawn(4877, 3097, 3500, 3, 10); // edge Runes
		c.getPA().checkObjectSpawn(2213, 2327, 3693, 2, 10); // Home Bank
		c.getPA().checkObjectSpawn(2213, 2329, 3693, 2, 10); // Home Bank
		c.getPA().checkObjectSpawn(2213, 2330, 3693, 2, 10); // Home Bank
		c.getPA().checkObjectSpawn(2213, 2331, 3693, 2, 10); // Home Bank
		c.getPA().checkObjectSpawn(2213, 2332, 3693, 2, 10); // Home Bank
		c.getPA().checkObjectSpawn(409, 3084, 3490, 1, 10); // Home Altar
		c.getPA().checkObjectSpawn(3192, 3091, 3516, 2, 10); // edge Home Score
		c.getPA().checkObjectSpawn(14924, 2332, 3689, 1, 10); // Home door??
		c.getPA().checkObjectSpawn(410, 3084, 3499, 0, 10); // DI Lunars Altar.
		c.getPA().checkObjectSpawn(6552, 3084, 3493, 1, 10); // Home Altar
		c.getPA().checkObjectSpawn(2213, 3282, 2765, 2, 10); // Shop Bank
		c.getPA().checkObjectSpawn(2213, 3281, 2765, 2, 10); // Shop Bank
		c.getPA().checkObjectSpawn(2213, 3280, 2765, 2, 10); // Shop Bank
		c.getPA().checkObjectSpawn(409, 3280, 2774, 2, 10); // Shop Pray
		c.getPA().checkObjectSpawn(2213, 2638, 4693, 2, 10); // DI Bank.
		c.getPA().checkObjectSpawn(2213, 2333, 9805, 1, 10); // DI Bank.
		c.getPA().checkObjectSpawn(2213, 2332, 9807, 2, 10); // DI Bank.
		c.getPA().checkObjectSpawn(2213, 2331, 9807, 2, 10); // DI Bank.
		c.getPA().checkObjectSpawn(2213, 2330, 9807, 2, 10); // DI Bank.
		c.getPA().checkObjectSpawn(6552, 2331, 9812, 0, 10); // DI Ancient Al
		c.getPA().checkObjectSpawn(409, 2335, 9812, 0, 10); // DI Normal Al
		c.getPA().checkObjectSpawn(410, 2338, 9812, 0, 10); // DI Lunars Altar.
		c.getPA().checkObjectSpawn(411, 2632, 4693, 0, 10); // DI Spec Altar.
		c.getPA().checkObjectSpawn(2213, 3655, 3521, 3, 10); // Shops Bank
		c.getPA().checkObjectSpawn(2213, 3655, 3520, 3, 10); // Shops Bank
		c.getPA().checkObjectSpawn(2213, 3655, 3519, 3, 10); // Shops Bank
		c.getPA().checkObjectSpawn(1306, 2343, 9808, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(1306, 3356, 9640, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(1306, 3356, 9642, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(411, 3366, 9632, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(409, 3364, 9632, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(6562, 3362, 9632, 0, 10); // super zone tree
		c.getPA().checkObjectSpawn(2106, 2342, 9803, 0, 10); // super zone rock
		c.getPA().checkObjectSpawn(2106, 2342, 9802, 0, 10); // super zone rock
		c.getPA().checkObjectSpawn(2106, 2342, 9801, 0, 10); // super zone rock
		c.getPA().checkObjectSpawn(2106, 2342, 9800, 0, 10); // super zone rock
		c.getPA().checkObjectSpawn(411, 2329, 9812, 0, 10); // spec altar

		c.getPA().checkObjectSpawn(-1, 2149, 5101, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2148, 5101, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2660, 4705, 0, 10); // shit
		
		//wheat
		
		c.getPA().checkObjectSpawn(-1, 2664, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2664, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2666, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2666, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2667, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2667, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2668, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2668, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2669, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2669, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2670, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2670, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2671, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2671, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2672, 4701, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2672, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2673, 4702, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2673, 4701, 0, 10); // shit
		
		c.getPA().checkObjectSpawn(-1, 2664, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2664, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2665, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2666, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2666, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2667, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2667, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2668, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2668, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2669, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2669, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2670, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2670, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2671, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2671, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2672, 2699, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2672, 2698, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2673, 2699, 0, 10); // shit
	

		
		c.getPA().checkObjectSpawn(-1, 2658, 4711, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2657, 4717, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2147, 5101, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2146, 5101, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2145, 5101, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2152, 5098, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2152, 5099, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2153, 5098, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2153, 5099, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2154, 5096, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2158, 5097, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3091, 3495, 0, 10); // shit
		c.getPA().checkObjectSpawn(2213, 2792, 3277, 0, 10); // shit
		c.getPA().checkObjectSpawn(2213, 2793, 3277, 0, 10); // shit
		c.getPA().checkObjectSpawn(2213, 2794, 3277, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 2792, 3274, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3083, 3500, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3083, 3501, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3083, 3501, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3084, 3501, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3084, 3500, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3096, 3503, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3099, 3512, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3100, 3512, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3100, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3100, 3508, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3100, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3099, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3091, 3508, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3091, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3093, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3093, 3508, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3089, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3088, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3089, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3089, 3511, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3089, 3511, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3088, 3509, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3089, 3509, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3094, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3095, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3096, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3097, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3098, 3507, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3099, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3098, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3097, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3096, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3085, 3506, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3090, 3516, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3082, 3516, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3096, 3501, 0, 10); // shit

		c.getPA().checkObjectSpawn(-1, 3091, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3091, 3511, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3092, 3511, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3094, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3095, 3510, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3096, 3511, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3093, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3092, 3513, 0, 10); // shit
		c.getPA().checkObjectSpawn(-1, 3091, 3513, 0, 10); // shit

		c.getPA().checkObjectSpawn(1276, 2152, 5095, 0, 10);
		c.getPA().checkObjectSpawn(1281, 2149, 5093, 0, 10);
		c.getPA().checkObjectSpawn(1308, 2145, 5095, 0, 10);
		c.getPA().checkObjectSpawn(1308, 2145, 5095, 0, 10);
		c.getPA().checkObjectSpawn(1309, 2142, 5097, 0, 10);
		c.getPA().checkObjectSpawn(1306, 2146, 5099, 0, 10);
		c.getPA().checkObjectSpawn(2213, 3233, 9319, 0, 10);

		c.getPA().checkObjectSpawn(2732, 2606, 3401, 3, 10); // trees
		c.getPA().checkObjectSpawn(-1, 2950, 3450, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3103, 3499, 0, 0);
		c.getPA().checkObjectSpawn(-1, 3046, 9756, 0, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(411, 2590, 9430, 2, 10);// dzone
		c.getPA().checkObjectSpawn(410, 2585, 9430, 2, 10);// dzone
		c.getPA().checkObjectSpawn(6552, 2587, 9430, 2, 10);// dzone
		c.getPA().checkObjectSpawn(2094, 3050, 9750, 2, 10);
		c.getPA().checkObjectSpawn(2213, 3047, 9779, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		c.getPA().checkObjectSpawn(4156, 3083, 3440, 0, 10);
		c.getPA().checkObjectSpawn(2783, 2841, 3436, 0, 10);
		c.getPA().checkObjectSpawn(2728, 2861, 3429, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2844, 3440, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 3437, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2840, 3439, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2841, 3443, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2851, 3438, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2510, 9457, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2512, 9462, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2503, 9465, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2518, 9463, -1, 10);
		c.getPA().checkObjectSpawn(2835, 2528, 9456, -1, 10);
		c.getPA().checkObjectSpawn(2835, 2527, 9455, -1, 10);
		c.getPA().checkObjectSpawn(2835, 2529, 9457, -1, 10);
		c.getPA().checkObjectSpawn(409, 2947, 3820, -1, 10);
		c.getPA().checkObjectSpawn(2213, 2149, 5106, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2150, 5106, 0, 10);
		c.getPA().checkObjectSpawn(2213, 2148, 5106, 0, 10);
		c.getPA().checkObjectSpawn(2466, 2591, 9422, -1, 10);
		
		
		//mining area
		c.getPA().checkObjectSpawn(2106, 3036, 9761, 0, 10); //rune rocks
		c.getPA().checkObjectSpawn(2106, 3037, 9760, 0, 10); //rune rocks
		c.getPA().checkObjectSpawn(2213, 2836, 3263, 0, 10); //bank
		c.getPA().checkObjectSpawn(2213, 2835, 3263, 0, 10); //bank
		c.getPA().checkObjectSpawn(2213, 2834, 3263, 0, 10); //bank
		c.getPA().checkObjectSpawn(2213, 2833, 3263, 0, 10); //bank
		c.getPA().checkObjectSpawn(3044, 2834, 3273, 1, 10); //furnace
		c.getPA().checkObjectSpawn(2090, 2832, 3267, 0, 10); //copper
		c.getPA().checkObjectSpawn(2094, 2832, 3268, 0, 10); //tin
		c.getPA().checkObjectSpawn(2092, 2832, 3269, 0, 10); //iron
		c.getPA().checkObjectSpawn(2096, 2832, 3270, 0, 10); //coal
		c.getPA().checkObjectSpawn(2102, 2836, 3267, 0, 10); //mith
		c.getPA().checkObjectSpawn(2105, 2836, 3268, 0, 10); //addy
		c.getPA().checkObjectSpawn(2106, 2836, 3269, 0, 10); //rune
		c.getPA().checkObjectSpawn(2098, 2836, 3270, 0, 10); //gold
		c.getPA().checkObjectSpawn(2783, 2832, 3272, 0, 10); //anvil
		c.getPA().checkObjectSpawn(2643, 2831, 3274, 1, 10); //furnace 2
		c.getPA().checkObjectSpawn(2213, 2912, 5474, 0, 10); //wc areA BANK
		c.getPA().checkObjectSpawn(2213, 2911, 5474, 0, 10); //wc areA BANK
		c.getPA().checkObjectSpawn(1276, 2907, 5476, 0, 10); //tree
		c.getPA().checkObjectSpawn(1276, 2907, 5474, 0, 10); //tree
		c.getPA().checkObjectSpawn(1281, 2907, 5472, 0, 10); //tree oak
		c.getPA().checkObjectSpawn(1281, 2907, 5470, 0, 10); //tree oak
		c.getPA().checkObjectSpawn(1308, 2907, 5467, 0, 10); //willow
		c.getPA().checkObjectSpawn(1308, 2910, 5467, 0, 10); //willow
		c.getPA().checkObjectSpawn(1307, 2913, 5467, 0, 10); //maple
		c.getPA().checkObjectSpawn(1307, 2916, 5467, 0, 10); //maple
		c.getPA().checkObjectSpawn(1309, 2916, 5470, 0, 10); //yew
		c.getPA().checkObjectSpawn(1309, 2916, 5472, 0, 10); //yew
		c.getPA().checkObjectSpawn(1306, 2916, 5475, 0, 10); //magic
		c.getPA().checkObjectSpawn(4111, 3091, 3506, 2, 10); //armour chest
		c.getPA().checkObjectSpawn(2403, 3092, 3506, 2, 10); //weapon chest
		c.getPA().checkObjectSpawn(172, 3093, 3506, 2, 10); //compcape
		
		
		
		if (c.heightLevel == 0)
			c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		else
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}

	public final int IN_USE_ID = 14825;

	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = { { 3154, 3618 }, { 3225, 3665 },
			{ 3033, 3730 }, { 3104, 3792 }, { 2978, 3864 }, { 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };

	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1], 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0],
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4,
						obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId, 16));
			}
		}
	}

	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}

	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(),
						obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2,
						1)) {
					c.getPA().startTeleport2(
							obeliskCoords[random][0] + xOffset,
							obeliskCoords[random][1] + yOffset, 0);
				}
			}
		}
	}

	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60
				&& c.heightLevel == o.height;
	}

	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}
	}

}