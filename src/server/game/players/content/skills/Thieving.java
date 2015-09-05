package server.game.players.content.skills;

import server.Config;
import server.Server;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.content.Events;
import server.util.Misc;

/**
 * Class Thieving Handles Thieving
 * 
 * @author PapaDoc 00:28 01/09/2010
 */


public class Thieving {
	
	private Client c;
		
	public Thieving(Client c) {
		this.c = c;
	}
	
	public void stealFromNPC(int id) {
		if (System.currentTimeMillis() - c.lastThieve < 2000)
			return;
		for (int j = 0; j < npcThieving.length; j++) {
			if (npcThieving[j][0] == id) {
				if (c.playerLevel[c.playerThieving] >= npcThieving[j][1]) {
					if (Misc.random(c.playerLevel[c.playerThieving] + 2 - npcThieving[j][1]) != 1) {
						c.getPA().addSkillXP(npcThieving[j][2] * Config.THIEVING_EXPERIENCE, c.playerThieving);
						c.getItems().addItem(995, npcThieving[j][3]);
						c.startAnimation(881);
						c.lastThieve = System.currentTimeMillis();
						c.sendMessage("You steal some money...");
						break;
					} else {
						c.setHitDiff(npcThieving[j][4]);
						c.setHitUpdateRequired(true);
						c.playerLevel[3] -= npcThieving[j][4];
						c.getPA().refreshSkill(3);
						c.lastThieve = System.currentTimeMillis() + 2000;
						c.sendMessage("You fail to pickpocket the NPC.");
						break;
					}
				} else {
					c.sendMessage("You need a thieving level of " + npcThieving[j][1] + " to thieve from this NPC.");
				}
			}		
		}
	}
	
	public void stealFromStall(int id, int amount, int xp, int level) {
		if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
		if(c.getItems().freeSlots() <= 0) {
			c.sendMessage("You need to clear your inventory to continue theiving.");
			return;
		}
		if (c.playerLevel[c.playerThieving] >= level) {
			if (c.getItems().addItem(995, c.playerLevel[c.playerThieving] * 50)) {
				c.getItems().addItem(id, 1);
				c.startAnimation(832);
				c.getPA().addSkillXP(xp * Config.THIEVING_EXPERIENCE, c.playerThieving);
				c.lastThieve = System.currentTimeMillis();
			}		
		} else {
			c.sendMessage("You must have a thieving level of " + level + " to steal from this stall.");
		}
	}
	//npc, level, exp, coin amount
	public int[][] npcThieving = {{1,1,8,200,1},{18,25,26,500,1},{9,40,47,1000,2},{26,55,85,1500,3},{20,70,152,2000,4},{21,80,273,3000,5}};

}