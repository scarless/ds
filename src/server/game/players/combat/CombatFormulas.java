package server.game.players.combat;

import server.game.players.Client;

/**
 * 
 * 
 * @author Rhubarb
 *
 */
public class CombatFormulas {
	
	/**
	 * Is the player in the diagonal block: formula.
	 * @param attacked
	 * @param attacker
	 * @return
	 */
	public final static boolean isInDiagonalBlock(Client attacked, Client attacker) {
		return attacked.absX - 1 == attacker.absX
				&& attacked.absY + 1 == attacker.absY
				|| attacker.absX - 1 == attacked.absX
				&& attacker.absY + 1 == attacked.absY
				|| attacked.absX + 1 == attacker.absX
				&& attacked.absY - 1 == attacker.absY
				|| attacker.absX + 1 == attacked.absX
				&& attacker.absY - 1 == attacked.absY
				|| attacked.absX + 1 == attacker.absX
				&& attacked.absY + 1 == attacker.absY
				|| attacker.absX + 1 == attacked.absX
				&& attacker.absY + 1 == attacked.absY;
	}
	
	/**
	 * Stops the diagonal attack, if they're in that region.
	 * @param attacked
	 * @param attacker
	 */
	public static void stopDiagonalAttack(Client attacked, Client attacker) {
		if (attacker.freezeTimer > 0) {
			attacker.sendMessage("A magical force stops you from moving.");
			attacker.getCombat().resetPlayerAttack();
		} else {
			int xMove = attacked.getX() - attacker.getX();
			int yMove = 0;
			if (xMove == 0)
				yMove = attacked.getY() - attacker.getY();
			int k = attacker.getX() + xMove;
			k -= attacker.mapRegionX * 8;
			attacker.getNewWalkCmdX()[0] = attacker.getNewWalkCmdY()[0] = 0;
			int l = attacker.getY() + yMove;
			l -= attacker.mapRegionY * 8;
			for (int n = 0; n < attacker.newWalkCmdSteps; n++) {
				attacker.getNewWalkCmdX()[n] += k;
				attacker.getNewWalkCmdY()[n] += l;
			}
		}
	}
}