package server.game.players.content;


import server.game.players.Client;

/**
 * challenge kills.
 * @author Patrick van Elderen, 21-04-2014.
 */

public class ChallengePoints {
	
	
	private static final int[] CHALLENGE_WEAPONS = {1305, 1377, 4587, 1434, 7158, 6528, 6527, 6523, 861};
	public static void killedWithChallengeWeapon(Client c) {
			for(int i : CHALLENGE_WEAPONS) {
			if(c.playerEquipment[c.playerWeapon] == i) {
				c.challengePoints++;
				c.sendMessage("@blu@You killed your openent with a challenge weapon you now have @gr1@" + c.challengePoints + "@red@ Challenge Points");
			}
		}
	}
}