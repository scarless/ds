package server.game.players.content;

import server.game.players.Client;

public class MageKills {


/**
 * Mage kills.
 * @author Patrick van Elderen, 21-04-2014.
 */
	
	private static final int[] MAGE_WEAPONS = {4170, 4675, 6914, 1381, 1383, 1385, 1387, 1389, 1401, 1403, 1405, 1407, 1409, 11994, 15110, };
	
	public static void killedWithMage(Client c) {
	
		for(int i : MAGE_WEAPONS) {
			if(c.playerEquipment[c.playerWeapon] == i) {
				c.magePoints++;
				c.sendMessage("Mage kill points increased. You have @gr1@" + c.magePoints + "@red@ Mage Points");
			}
		}
	}
}