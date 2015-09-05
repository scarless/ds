package server.game.players.content;

import server.game.players.Client;
import server.util.Misc;

/**
 * Melee kills.
 * @author Patrick van Elderen, 21-04-2014.
 */

public class MeleeKills {
	
	
	private static final int[] MELEE_WEAPONS = {1305, 1249, 5698, 1215, 1377, 3204, 4587, 1434, 14484, 11694, 11696, 11698, 11700, 4151, 7158, 6527, 6528, 6523, 4153, 1323, 1333, 1319, 11730};
	public static void killedWithMelee(Client c) {	
		for(int i : MELEE_WEAPONS) {
			if(c.playerEquipment[c.playerWeapon] == i) {
				c.meleePoints++;
				c.sendMessage("Melee kill points increased. You have @gr1@" + c.meleePoints + "@red@ Melee Points");
			}
		}
	}
}