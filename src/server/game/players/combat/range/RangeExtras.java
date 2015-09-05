package server.game.players.combat.range;

import server.Server;
import server.game.npcs.NPC;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.util.Misc;

public class RangeExtras {

	public static void appendMutliChinchompa(Client c, int npcId) {
		if (NPCHandler.npcs[npcId] != null) {
			NPC n = (NPC)NPCHandler.npcs[npcId];
			if (n.isDead) {
				return;
			}
			c.multiAttacking = true;
			int damage = Misc.random(c.getCombat().rangeMaxHit());
			if (NPCHandler.npcs[npcId].HP - damage < 0) { 
				damage = NPCHandler.npcs[npcId].HP;
			}
			NPCHandler.npcs[npcId].underAttackBy = c.playerId;
			NPCHandler.npcs[npcId].underAttack = true;
			NPCHandler.npcs[npcId].handleHitMask(damage);
			NPCHandler.npcs[npcId].dealDamage(damage);
		}
	}

	private static void createCombatGFX(Client c, int i, int gfx, boolean height100) {
		Client p = (Client)PlayerHandler.players[i];
		NPC n = (NPC)NPCHandler.npcs[i];
		if(c.playerIndex > 0) {
			if(height100) {
				p.gfx100(gfx);
			} else {
				p.gfx0(gfx);
			}
		} else if(c.npcIndex > 0) {
			if(height100) {
				n.gfx100(gfx);
			} else {
				n.gfx0(gfx);
			}
		}
	}

	public static void crossbowSpecial(Client c, int i) {
		if(NPCHandler.npcs.length < i)
			return;
		if(PlayerHandler.players.length < i)
			return;
		if(Server.npcHandler.npcs[i] == null)
			return;
		/*if(Server.playerHandler.players[i] == null && c.playerIndex > 0)
			return;*/
		Client p = (Client)PlayerHandler.players[i];
		NPC n = (NPC)NPCHandler.npcs[i];
		c.crossbowDamage = 1.4;

		switch (c.lastArrowUsed) {
		/**
		 * Lucky lightning range special. 
		 */
			case 9236:
				createCombatGFX(c, i, 749, false);
				c.crossbowDamage = 1.25;
				break;
				
				/**
				 * Earth's fury range special.
				 */
			case 9237: 
				createCombatGFX(c, i, 755, false);
				break;
				
				/**
				 * Sea curse range special.
				 */
			case 9238:
				createCombatGFX(c, i, 750, false);
				c.crossbowDamage = 1.10;
				break;
				
				/**
				 * Down to earth range special.
				 */
			case 9239:
				createCombatGFX(c, i, 757, false);
 				if(c.playerIndex > 0) {
					p.playerLevel[6] -= 2;
					p.getPA().refreshSkill(6);
					p.sendMessage("Your magic has been lowered!");
				}
				break;
				
				/**
				 * Clear mind range special.
				 */
			case 9240:
				createCombatGFX(c, i, 751, false);
				if(c.playerIndex > 0) {
					p.playerLevel[5] -= 2;
					p.getPA().refreshSkill(5);
					p.sendMessage("Your prayer has been lowered!");
					c.playerLevel[5] += 2;
					if(c.playerLevel[5] >= c.getPA().getLevelForXP(c.playerXP[5])) {
						c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
					}
					c.getPA().refreshSkill(5);
				}
				break;
				
				/**
				 * Magical posion range special.
				 */
			case 9241: 
				createCombatGFX(c, i, 752, false);
				if(c.playerIndex > 0) {
					p.getPA().appendPoison(6);
				}
				break;
				
				/**
				 * Blood forfit range special.
				 */
			case 9242:
				createCombatGFX(c, i, 754, false);

				if(c.playerLevel[3] - c.playerLevel[3]/20 < 1) {
					break;
				}
				c.handleHitMask(c.playerLevel[3]/20);
				c.dealDamage(c.playerLevel[3]/20);
				if(c.npcIndex > 0) {
					n.handleHitMask(n.HP/10);
					n.dealDamage(n.HP/10);
				} else if(c.playerIndex > 0) {
					p.handleHitMask(c.playerLevel[3]/10);
					p.dealDamage(c.playerLevel[3]/10);
				}
				break;
				
				/**
				 * Armour piercing range special.
				 */
			case 9243:
				createCombatGFX(c, i, 758, true);
				c.crossbowDamage = 1.15;
				c.ignoreDefence = true;
				break;
				
				/**
				 * Dragon's breah range special.
				 */
			case 9244:
				createCombatGFX(c, i, 756, false);
				if(c.playerEquipment[c.playerShield] != 1540 || c.playerEquipment[c.playerShield] != 11283
					|| c.playerEquipment[c.playerShield] != 11284) {
						c.crossbowDamage = 1.45;
				}
				break;
				
				/**
				 * Life leech range special.
				 */
			case 9245:
				createCombatGFX(c, i, 753, false);
				c.crossbowDamage = 1.15;
				c.playerLevel[3] += c.boltDamage/25;
				if(c.playerLevel[3] >= 112) {
					c.playerLevel[3] = 112;
				}
				c.getPA().refreshSkill(3);
				break;
		}
	}
}