package server.game.players.content;

import server.game.players.Client;

public class RangeKills {


/**
 * Range kills.
 * @author Patrick van Elderen, 21-04-2014.
 */
	
	private static final int[] Range_WEAPONS = {9185, 841, 15003, 15041};
	
	public static void killedWithRange(Client c) {
	
		for(int i : Range_WEAPONS) {
			if(c.playerEquipment[c.playerWeapon] == i) {
				c.rangePoints++;
				c.sendMessage("Mage kill points increased. You have @gr1@" + c.magePoints + "@red@ Mage Points");
			}
		}
	}
}