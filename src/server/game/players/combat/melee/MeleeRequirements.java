package server.game.players.combat.melee;

import java.awt.Event;

import server.*;
import server.event.EventContainer;
import server.event.EventManager;
import server.game.npcs.NPCHandler;
import server.game.players.*;
import server.game.players.combat.*;
import server.game.players.combat.magic.*;
import server.util.Misc;

public class MeleeRequirements {

	public static int getKillerId(Client c, int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {	
			if (PlayerHandler.players[i] != null) {
				if(PlayerHandler.players[i].killedBy == playerId) {
					if (PlayerHandler.players[i].withinDistance(PlayerHandler.players[playerId])) {
						if(PlayerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = PlayerHandler.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}	
					PlayerHandler.players[i].totalPlayerDamageDealt = 0;
					PlayerHandler.players[i].killedBy = 0;
				}	
			}
		}				
		return killerId;
	}
	
	public static void handleDfs(Client c) {
		if (c.playerEquipment[c.playerShield] == 11284 || c.playerEquipment[c.playerShield] == 11283
				|| c.playerEquipment[c.playerShield] == 11285)
		{
			if (System.currentTimeMillis() - c.dfsDelay > 30000)
			{
				if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null)
				{
					Client o = (Client) PlayerHandler.players[c.playerIndex];
					int damage = Misc.random(20);
					if (damage < 2)
						damage = 2;
					c.startAnimation(2836);
					c.sendMessage(":dfscasted:");
					if (o.getPA().antiFire() == 1)
					{
						damage = (int) ((double) damage * .75);
						o.sendMessage("@red@You are slightly protected from the dragonfire shield.");
					}
					if (o.getPA().antiFire() == 2)
					{
						damage = (int) ((double) damage * .5);
						o.sendMessage("@red@The dragonfire shield is not very effective against you.");
					}
					PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
					PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
					PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
					PlayerHandler.players[c.playerIndex].updateRequired = true;
					((Client) PlayerHandler.players[c.playerIndex]).getPA().requestUpdates();
					c.dfsDelay = System.currentTimeMillis();
				} else
				{
					c.sendMessage("@red@I should be in combat before using this.");
				}
			} else
			{
				c.sendMessage("@red@My shield hasn't finished recharging yet.");
			}
		}
	}

	public static void handleDfsNPC(Client c) {
		try
		{
			if (c.npcIndex > 0)
			{
				if (NPCHandler.npcs[c.npcIndex] != null)
				{
					c.projectileStage = 2;
					final int pX = c.getX();
					final int pY = c.getY();
					final int nX = NPCHandler.npcs[c.npcIndex].getX();
					final int nY = NPCHandler.npcs[c.npcIndex].getY();
					final int offX = (pY - nY) * -1;
					final int offY = (pX - nX) * -1;
					if (System.currentTimeMillis() - c.dfsDelay > 30000) {
						if (c.npcIndex > 0 && NPCHandler.npcs[c.npcIndex] != null) {
							final int damage = Misc.random(15) + 10;
							c.startAnimation(2836);
							c.gfx0(600);
							c.sendMessage(":dfscasted:");
							NPCHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
							NPCHandler.npcs[c.npcIndex].updateRequired = true;
							NPCHandler.npcs[c.npcIndex].hitDiff2 = damage;
							NPCHandler.npcs[c.npcIndex].HP -= damage;
							//NPCHandler.npcs[c.npcIndex].gfx100(1167);
							if (NPCHandler.npcs[c.npcIndex].isDead == true)
							{
								c.sendMessage("This NPC is already dead!");
								return;
							}
							c.dfsDelay = System.currentTimeMillis();
						} else
						{
							c.sendMessage("I should be in combat before using this.");
						}
					} else
					{
						c.sendMessage("My shield hasn't finished recharging yet.");
					}
				}
			}
		} catch (Exception e) {
		}
		}
		

	public static void handleFremmyShield(Client c) {
		if (c.playerEquipment[c.playerShield] == 3758)
		{
			if (System.currentTimeMillis() - c.dfsDelay > 25000)
			{

				if (c.playerIndex > 0 && PlayerHandler.players[c.playerIndex] != null)
				{
					Client o = (Client) PlayerHandler.players[c.playerIndex];
					int damage = Misc.random(25);
					if (damage < 3)
						damage = 3;
					c.startAnimation(439);
					PlayerHandler.players[c.playerIndex].playerLevel[3] -= damage;
					PlayerHandler.players[c.playerIndex].hitDiff2 = damage;
					PlayerHandler.players[c.playerIndex].hitUpdateRequired2 = true;
					PlayerHandler.players[c.playerIndex].updateRequired = true;
					((Client) PlayerHandler.players[c.playerIndex]).getPA().requestUpdates();
					c.dfsDelay = System.currentTimeMillis();
				} else
				{
					c.sendMessage("@red@I should be in combat before using this.");
				}
			} else
			{
				c.sendMessage("@red@My shield hasn't finished recharging yet.");
			}
		}
	}
	
	public static int getCombatDifference(int combat1, int combat2) {
		if(combat1 > combat2) {
			return (combat1 - combat2);
		}
		if(combat2 > combat1) {
			return (combat2 - combat1);
		}	
		return 0;
	}

	public static boolean checkReqs(Client c) {
		if(PlayerHandler.players[c.playerIndex] == null) {
			return false;
		}
		if (c.playerIndex == c.playerId)
			return false;
		if (c.inPits && PlayerHandler.players[c.playerIndex].inPits)
			return true;
		if(PlayerHandler.players[c.playerIndex].inDuelArena() && c.duelStatus != 5 && !c.usingMagic) {
			if(c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return false;
			}
			c.getTradeAndDuel().requestDuel(c.playerIndex);
			return false;
		}
		if(c.duelStatus == 5 && PlayerHandler.players[c.playerIndex].duelStatus == 5) {
			if(PlayerHandler.players[c.playerIndex].duelingWith == c.getId()) {
				return true;
			} else {
				c.sendMessage("This isn't your opponent!");
				return false;
			}
		}
		if(!PlayerHandler.players[c.playerIndex].inWild() && PlayerHandler.players[c.playerIndex].safeTimer != 0 && !PlayerHandler.players[c.playerIndex].isInHighRiskPK() && !PlayerHandler.players[c.playerIndex].inFunPk()) {
			c.sendMessage("That player is not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(!c.inWild() && c.safeTimer <= 0 && !c.isInHighRiskPK() && !c.inFunPk()) {
			c.sendMessage("You are not in the wilderness.");
			c.stopMovement();
			c.getCombat().resetPlayerAttack();
			return false;
		}
		if(Config.COMBAT_LEVEL_DIFFERENCE) {
			int combatDif1 = c.getCombat().getCombatDifference(c.combatLevel, PlayerHandler.players[c.playerIndex].combatLevel);
			if(combatDif1 > c.wildLevel || combatDif1 > PlayerHandler.players[c.playerIndex].wildLevel) {
				c.sendMessage("Your combat level difference is too great to attack that player here.");
				c.stopMovement();
				c.getCombat().resetPlayerAttack();
				return false;
			}
		}
		
		if(Config.SINGLE_AND_MULTI_ZONES) {
			if(!PlayerHandler.players[c.playerIndex].inMulti()) {	// single combat zones
				if(PlayerHandler.players[c.playerIndex].underAttackBy != c.playerId  && PlayerHandler.players[c.playerIndex].underAttackBy != 0) {
					c.sendMessage("That player is already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
				if(PlayerHandler.players[c.playerIndex].playerId != c.underAttackBy && c.underAttackBy != 0 || c.underAttackBy2 > 0) {
					c.sendMessage("You are already in combat.");
					c.stopMovement();
					c.getCombat().resetPlayerAttack();
					return false;
				}
			}
		}
		return true;
	}	

	public static int getRequiredDistance(Client c) {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving)
			return 2;
		else if(c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}
}