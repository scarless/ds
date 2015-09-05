package server.game.npcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import server.Config;
import server.Server;
import server.clip.region.Region;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.minigames.WarriorsGuild;
import server.game.npcs.pet.Pet;
import server.game.players.Client;
import server.game.players.Player;
import server.game.players.PlayerHandler;
import server.game.players.content.Events;
import server.util.Misc;

public class NPCHandler {
	public static int maxNPCs = 10000;
	public static int maxListedNPCs = 10000;
	public static int maxNPCDrops = 10000;
	public static int random;
	public static NPC npcs[] = new NPC[maxNPCs];
	public static NPCList NpcList[] = new NPCList[maxListedNPCs];

	public NPCHandler() {
		for (int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
		}
		for (int i = 0; i < maxListedNPCs; i++) {
			NpcList[i] = null;
		}
		try {
			loadNPCList("./Data/CFG/npc.cfg");
			loadAutoSpawn("./Data/CFG/spawn-config.cfg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.heightLevel != npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX, c.absY,
						npcs[i].absX, npcs[i].absY, 15)) {
					int nX = NPCHandler.npcs[i].getX() + offset(i);
					int nY = NPCHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY) * -1;
					int offY = (nX - pX) * -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50,
							getProjectileSpeed(i), npcs[i].projectileId, 43,
							31, -c.getId() - 1, 65);
				}
			}
		}
	}

	public boolean switchesAttackers(int i) {
		switch (npcs[i].npcType) {
		case 3649: // halloween event npc, change this!
		case 2551:
		case 2552:
		case 2553:
			// case 4154:
		case 4173:
		case 4172:
		case 4001:
		case 4175:
		case 3070:
		case 3847:
		case 5247:
		case 3943:
		case 2559:
		case 2560:
		case 2561:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 5666:
			// case 6260:
		case 6261:
		case 6263:
		case 6265:
		case 6208:
		case 6203:
			return true;
		}
		return false;
	}

	public void multiAttackDamage(int i) {
		int max = getMaxHit(i);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if (c.isDead || c.heightLevel != npcs[i].heightLevel)
					continue;
				if (PlayerHandler.players[j].goodDistance(c.absX, c.absY,
						npcs[i].absX, npcs[i].absY, 15)) {
					if (npcs[i].attackType == 2) {
						if (!c.prayerActive[16]) {
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().mageDef())) {
								int dam = Misc.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					} else if (npcs[i].attackType == 1) {
						if (!c.prayerActive[17]) {
							int dam = Misc.random(max);
							if (Misc.random(500) + 200 > Misc.random(c
									.getCombat().calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam);
							} else {
								c.dealDamage(0);
								c.handleHitMask(0);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0);
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx0(npcs[i].endGfx);
					}
				}
				c.getPA().refreshSkill(3);
			}
		}
	}

	public int getClosePlayer(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (j == npcs[i].spawnedBy)
					return j;
				if (goodDistance(PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY, npcs[i].absX,
						npcs[i].absY, 2 + distanceRequired(i)
								+ followDistance(i))
						|| isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel)
							return j;
				}
			}
		}
		return 0;
	}

	public boolean isFightCaveNpc(int i) {
		switch (npcs[i].npcType) {
		case 2627:
		case 2630:
		case 2631:
		case 2741:
		case 2743:
		case 2745:
			return true;
		}
		return false;
	}

	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (goodDistance(PlayerHandler.players[j].absX,
						PlayerHandler.players[j].absY, npcs[i].absX,
						npcs[i].absY, 2 + distanceRequired(i)
								+ followDistance(i))
						|| isRFDNpc(i) || isFightCaveNpc(i)) {
					if ((PlayerHandler.players[j].underAttackBy <= 0 && PlayerHandler.players[j].underAttackBy2 <= 0)
							|| PlayerHandler.players[j].inMulti())
						if (PlayerHandler.players[j].heightLevel == npcs[i].heightLevel)
							players.add(j);
				}
			}
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() - 1));
		else
			return 0;
	}

	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2883:
		case 2882:
		case 2881:
			return 3;
		}
		return 0;
	}

	public boolean isAggressive(int i) {

		if (npcs[i].npcType > 760 && npcs[i].npcType < 767
				|| npcs[i].npcType == 2668 || npcs[i].npcType > 3504
				&& npcs[i].npcType < 3508) {
			return false;
		}
		switch (npcs[i].npcType) {
		case 4175: // Vetion
					// case 4158:// CaveKraken
		case 4172: // Scorpia
		case 4173: // Venenatis
		case 4001: // Callisto
		case 1850:
		//case 4532: // Suqah
		case 2550:
		case 6223:
		case 3847:
		case 6227:
		case 6225:
		case 6204:
		case 6206:
		case 4176:
		case 4154:
		case 6208:
		case 1593:
		case 2551:
		case 2552:
		case 6252:
		case 6250:
		case 6248:
		case 6263:
		case 6265:
		case 5666:
		case 2553:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2565:
		case 2892:
		case 2894:
		case 2881:
		case 2882:
		case 2883:
		case 1459:
		case 50:
		case 742:
		case 1926:
		case 1913:
		case 1974:
		case 1914:
		case 6203:
		case 6260:
		case 6222:
		case 6247:
		case 6259:
		case 6261:
		case 5363:
		case 1465:
		case 1471:
			return true;
		}

		if (npcs[i].inWild() && npcs[i].MaxHP > 0)
			return true;
		if (isFightCaveNpc(i))
			return true;
		return false;
	}

	public void appendBossKC(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			int[] bossKC = { 50, 3200, 2881, 2882, 2883, 1158, 1159, 1160,
					4001, 6247, 6222, 6203, 6260, 5666, 5247, 3847, 1472, 3943,
					2745, 4172, 4173, 4001, 4175 };
			for (int j : bossKC) {
				if (npcs[i].npcType == j) {
					c.bossKills += 1;
					if (c.bossKills == 50) {
						c.sendMessage("You now have 50 boss kills and recieve <col=255>one achievement points<col=0!");
						c.aPoints += 1;
					}
					if (c.bossKills == 175) {
						c.sendMessage("You now have 175 boss kills and recieve <col=255>2 achievement points<col=0!");
						c.aPoints += 2;
					}
					if (c.bossKills == 250) {
						c.sendMessage("You now have 250 boss kills and recieve <col=255>3 achievement points<col=0!");
						c.aPoints += 3;
					}
					if (c.bossKills == 500) {
						c.sendMessage("You now have 500 boss kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 4;
					}
					if (c.bossKills == 1000) {
						c.sendMessage("You now have 1000 boss kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 8;
					}
					if (c.bossKills == 1350) {
						c.sendMessage("You now have 1350 boss kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 10;
					}
					if (c.bossKills == 2000) {
						c.sendMessage("You now have 2000 boss kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 15;
					}
					break;
				}
			}
		}
	}

	public void appendBandosKills(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			int[] bandosKills = { 6260 };
			for (int j : bandosKills) {
				if (npcs[i].npcType == j) {
					c.bandosKills += 1;
					if (c.bandosKills == 50) {
						c.sendMessage("You now have 50 Bandos kills and recieve <col=255>one achievement points<col=0!");
						c.aPoints += 1;
					}
					if (c.bandosKills == 175) {
						c.sendMessage("You now have 175 Bandos kills and recieve <col=255>2 achievement points<col=0!");
						c.aPoints += 2;
					}
					if (c.bandosKills == 250) {
						c.sendMessage("You now have 250 Bandos kills and recieve <col=255>3 achievement points<col=0!");
						c.aPoints += 3;
					}
					if (c.bandosKills == 500) {
						c.sendMessage("You now have 500 Bandos kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 4;
					}
					if (c.bandosKills == 1000) {
						c.sendMessage("You now have 1000 Bandos kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 8;
					}
					if (c.bandosKills == 1350) {
						c.sendMessage("You now have 1350 Bandos kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 10;
					}
					if (c.bandosKills == 2000) {
						c.sendMessage("You now have 2000 Bandos kills and recieve <col=255>4 achievement points<col=0!");
						c.aPoints += 15;
					}
					break;
				}
			}
		}
	}

	public boolean isRFDNpc(int i) {
		switch (npcs[i].npcType) { // after a switch, you add codes that use
									// case
		case 3493:
		case 3494:
		case 3495:
		case 3496:
		case 3491:
			return true;
		}
		return false;
	}

	public void handleJadDeath(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].spawnedBy];
		c.getItems().addItem(6570, 1);
		c.sendMessage("Congratulations on completing the fight caves minigame!");

		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];

				/*c2.sendMessage("[@red@Fight Caves@bla@] Player " + c.playerName
						+ " has just killed Jad and was rewarded!");*/
				c.getPA().resetTzhaar();
				c.waveId = 0;
				c.getPA().movePlayer(Config.FIGHT_CAVE_RESPAWN_X, Config.FIGHT_CAVE_RESPAWN_Y, 0);
			}
		}
	}

	private void killedTzhaar(int i) {
		final Client c2 = (Client) PlayerHandler.players[npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			c2.waveId++;
			if (c2 != null) {
				Server.fightCaves.spawnNextWave(c2);
			}

		}
	}

	public boolean ArmadylKC(int i) {
		switch (npcs[i].npcType) {
		case 6229:// Npcs That Give ArmaKC
		case 6230:
		case 6231:
		case 6232:
		case 6233:
		case 6234:
		case 6235:
		case 6236:
		case 6237:
		case 6238:
		case 6239:
		case 6240:
		case 6241:
		case 6242:
		case 6243:
		case 6244:
		case 6245:
		case 6246:
		case 274:
		case 275:
		case 6222:
		case 6223:
		case 6225:
		case 6227:
			return true;

		}

		return false;
	}

	public boolean BandosKC(int i) {
		switch (npcs[i].npcType) {
		case 6260:// Npcs That Give BandosKC
		case 6261:
		case 6263:
		case 6265:
		case 6271:
		case 6272:
		case 6273:
		case 6274:
		case 6275:
		case 6276:
		case 6277:
		case 6279:
		case 6278:
		case 6280:
		case 6268:
		case 122:
		case 6282:
		case 6281:
		case 6283:
			return true;

		}

		return false;
	}

	public boolean ZammyKC(int i) {
		switch (npcs[i].npcType) {
		case 6210:// Npcs That Give BandosKC
		case 6211:
		case 6212:
		case 6214:
		case 6218:
		case 49:
		case 82:
		case 83:
		case 84:
		case 95:
		case 92:
		case 75:
		case 78:
		case 912:
			return true;

		}

		return false;
	}

	public boolean SaraKC(int i) {
		switch (npcs[i].npcType) {
		case 6254:// Npcs That Give SaraKC
		case 6255:
		case 6256:
		case 6257:
		case 6258:
		case 6259:
		case 96:
		case 97:
		case 111:
		case 125:
		case 913:
			return true;

		}

		return false;
	}

	public boolean isRFDNpc2(int i) {
		switch (npcs[i].npcType) { // after a switch, you add codes that use
									// case
		case 3495:
			return true; // for completing minigames
		}
		return false; // if not completed minigame
	}

	/**
	 * Summon npc, barrows, etc
	 **/
	/**
	 * Summon npc, barrows, etc
	 **/
	public void spawnNpc(final Client c, int npcType, int x, int y,
			int heightLevel, int WalkingType, int HP, int maxHit, int attack,
			int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		final NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if (headIcon)
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (attackPlayer) {
			newNPC.underAttack = true;
			if (c != null) {
				newNPC.killerId = c.playerId;
			}
		}
		int[] animatedArmor = { 4278, 4279, 4280, 4281, 4282, 4283, 4284 };
		for (int anAnimatedArmor : animatedArmor) {
			if (newNPC.npcType == anAnimatedArmor) {
				newNPC.forceAnim(4410);
				newNPC.forceChat("I'M ALIVE!");
			}
		}
		for (int[] Guard : Events.NPCs) {
			if (newNPC.npcType == Guard[2]) {
				newNPC.forceChat("Halt, Thief!");
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						container.stop();
					}

					@Override
					public void stop() {
						newNPC.isDead = true;
						newNPC.updateRequired = true;
						c.hasEvent = false;
					}
				}, 200);
			}
		}
		npcs[slot] = newNPC;
	}

	public static void spawnNpc3(final int npcType, final int x, final int y,
			final int heightLevel, final int WalkingType, final int HP,
			final int maxHit, final int attack, final int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		final NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		NPCHandler.npcs[slot] = newNPC;
	}

	public static void spawnNpc2(int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	/**
	 * Emotes
	 **/

	public static int getAttackEmote(int i) {

		if (npcs[i].npcType >= 3732 && npcs[i].npcType <= 3741) {
			return 3901;
		}
		if (npcs[i].npcType >= 3742 && npcs[i].npcType <= 3746) {
			return 3915;
		}
		if (npcs[i].npcType >= 3747 && npcs[i].npcType <= 3751) {
			return 3908;
		}
		if (npcs[i].npcType >= 3752 && npcs[i].npcType <= 3761) {
			return 3880;
		}
		if (npcs[i].npcType >= 3762 && npcs[i].npcType <= 3771) {
			return 3920;
		}
		if (npcs[i].npcType >= 3772 && npcs[i].npcType <= 3776) {
			return 3896;
		}

		switch (NPCHandler.npcs[i].npcType) {

		case 1977: // Fareed
		case 1: // Man
			return 422;
		case 1267: // Rock Crab
		case 1265: // Rock Crab
			return 1312;
		case 4155: // Smoke Devils
			return 1552;
		case 4176: // lava dragon
			return 80;
		case 4175: // Vet'ion attack
			return 5485;
		case 4001: // Callisto
			return 4925;
		case 4173: // Venenatis
			return 5327;
		case 4172: // Scorpia attack
			return 6254;
		case 4158: // CaveKraken
			return 3991;
		case 1851:
		case 1852:
		case 1850:
		case 1856:
			return 1840; // avatar of strength
			

		case 3070://wyv
if (npcs[i].attackType == 0)
return 2989;
if (npcs[i].attackType == 3)
return 2989;
		case 3847:
		return 3991;
		case 75: // Zombie
		case 6763: // Dried Zombie
			return 5569;
		case 2837: // Zombie Train
			return 5569;
		case 3493:
			return 3501;
		case 3494:
			return 1750;
		case 3496:
			return 3508;
		case 3497:
			return 1341;
		case 1471:
			return 5519;
		case 5176:// Ogre Shaman
		case 5181:// Ogre Shaman
		case 5184:// Ogre Shaman
		case 5187:// Ogre Shaman
		case 5190:// Ogre Shaman
		case 5193:// Ogre Shaman
			return 359;
		case 1465:
			return 5521;
		case 1459:
			return 1402;
		case 2550:
			if (npcs[i].attackType == 0)
				return 7060;
			else
				return 7063;
		case 1593:
		case 49:
			return 6579;
		case 3340:
			return 3312;
		case 5666:
			return 5894;
		case 2892:
		case 2894:
			return 2868;
		case 1158: // kalphite queen
			if (npcs[i].attackType == 2)
				return 6240;
			if (npcs[i].attackType == 0)
				return 6241;
			else
				return 6240;
		case 1160: // kalphite queen form 2
			if (npcs[i].attackType == 2)
				return 6237;
			if (npcs[i].attackType == 0)
				return 6235;
			else
				return 6237;
		case 6248:
			return 6376;
		case 6250:
			return 7018;
		case 6252:
			return 7009;
		case 6225:
		case 6227:
		case 6223:
			return 6953;
		case 2627:
			return 2621;
		case 1600:
			return 227;
		case 2630:
			return 2625;
		case 6263:
		case 6261:
		case 6265:
			return 6154;
		case 2631:
			return 2633;
		case 2741:
			return 2637;
		case 2746:
			return 2637;
		case 2607:
			return 2611;
		case 2743: // 360
			return 2647;
			// bandos gwd
		case 2551:
		case 2552:
		case 2553:
			return 6154;
			// end of gwd
			// arma gwd
		case 2558:
			return 3505;
		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
			return 6223;
		case 2560:
			return 6953;
		case 2559:
			return 6952;
		case 2561:
			return 6954;
			// end of arma gwd
			// sara gwd
			// Godwars
		case 6203:
			if (npcs[i].attackType == 0)
				return 6945;
			else
				return 6947;
		case 6260:
			if (npcs[i].attackType == 0)
				return 7060;
			else
				return 7063;
		case 4154:
			return 3991;
		case 6222:
			return 6977;
		case 6247:
			return 6964;
		case 6273:
			return 4320;
		case 6267:
			return 359;
		case 6268:
			return 2930;
		case 6269:
			return 4652;
		case 6270:
		case 4291:// Cyclops
		case 4292:// Ice cyclops
			return 4652;
		case 6271:
			return 4320;
		case 6272:
			return 4320;
		case 6274:
			return 4320;
			// End Godwars
		case 2562:
			return 6964;
		case 2563:
			return 6376;
		case 2564:
			return 7018;
		case 2565:
			return 7009;
			// end of sara gwd
		case 13: // wizards
			return 711;

		case 103:
		case 655:
			return 5540;

		case 1624:
			return 1557;

		case 1648:
			return 1590;

		case 2783: // dark beast
			return 2732;

		case 1615: // abby demon
			return 1537;

		case 1613: // nech
			return 1528;

		case 1610:
		case 1611: // garg
			return 1519;

		case 1616: // basilisk
			return 1546;

		case 92:
		case 90: // skele
			return 5485;

		case 50: // drags
		case 5363:
		case 742:
		case 53:
		case 54:
		case 55:
		case 941:
		case 4682:
		case 5362:
		case 1590:
		case 1591:
		case 1592:
		case 3590:
			return 80;

		case 124: // earth warrior
			return 390;

		case 803: // monk
			return 422;

		case 52: // baby drag
			return 25;

		case 58: // Shadow Spider
		case 59: // Giant Spider
		case 60: // Giant Spider
		case 61: // Spider
		case 62: // Jungle Spider
		case 63: // Deadly Red Spider
		case 64: // Ice Spider
		case 134:
			return 143;

		case 105: // Bear
		case 106: // Bear
			return 41;

		case 412:
		case 78:
			return 4915;

		case 47:
		case 2033: // rat
			return 138;

		case 2031: // bloodworm
			return 2070;

		case 101: // goblin
			return 6184;

		case 5529:
		case 81: // cow
			return 5849;

		case 21: // hero
			return 451;

		case 41: // chicken
		case 2463:
		case 2464:
		case 2465:
		case 2466:
		case 2467:
		case 2468:
			return 5387;

		case 9: // guard
		case 32: // guard
		case 20: // paladin
			return 451;

		case 1338: // dagannoth
		case 1340:
		case 1342:
			return 1341;

		case 19: // white knight
			return 406;

		case 110:
		case 111: // ice giant
		case 117:
		case 112:
			return 4652;

		case 2452:
			return 1312;

		case 2889:
			return 2859;

		case 118:
		case 119:
			return 99;

		case 6204:
		case 6206:
		case 6208:
		case 82: // Lesser Demon
		case 83: // Greater Demon
		case 84: // Black Demon
		case 1472: // jungle demon
			return 64;
		case 125: // ice warrior
		case 178:
			return 451;

		case 123:
		case 122:
			return 164;

		case 2028: // karil
			return 2075;

		case 2025: // ahrim
			return 729;

		case 2026: // dharok
			return 2067;

		case 2027: // guthan
			return 2080;

		case 2029: // torag
			return 0x814;

		case 2030: // verac
			return 2062;

		case 2881: // supreme
			return 2855;

		case 2882: // prime
			return 2854;

		case 2883: // rex
			return 2851;

		case 3200:
			return 3146;

		case 2745:
			if (npcs[i].attackType == 2)
				return 2656;
			else if (npcs[i].attackType == 1)
				return 2652;
			else if (npcs[i].attackType == 0)
				return 2655;

		default:
			return 0x326;
		}
	}

	public int getDeadEmote(int i) {

		if (npcs[i].npcType >= 3732 && npcs[i].npcType <= 3741) {
			return 3903;
		}
		if (npcs[i].npcType >= 3742 && npcs[i].npcType <= 3746) {
			return 3917;
		}
		if (npcs[i].npcType >= 3747 && npcs[i].npcType <= 3751) {
			return 3909;
		}
		if (npcs[i].npcType >= 3752 && npcs[i].npcType <= 3761) {
			return 3881;
		}
		if (npcs[i].npcType >= 3762 && npcs[i].npcType <= 3771) {
			return 3922;
		}
		if (npcs[i].npcType >= 3772 && npcs[i].npcType <= 3776) {
			return 3894;
		}

		switch (npcs[i].npcType) {
		case 272:
			// handleLucien(i);
			return 2304;
		case 4001: // Callisto
			return 4929;
		case 4176: // lava dragon
			return 92;
		case 4175: // Vet'ion Dead
			return 5491;
		case 4173: // Venenatis Deademote
			return 5329;
		case 4172: // Scorpia Dead
			return 6256;
		case 3070://wyv
			return 2987;//wyv death
		case 3847: // CaveKraken
			return 3991;
		case 5176:// Ogre Shaman
		case 5181:// Ogre Shaman
		case 5184:// Ogre Shaman
		case 5187:// Ogre Shaman
		case 5190:// Ogre Shaman
		case 5193:// Ogre Shaman
			return 361;
		case 1850:
			return 1841; // Avatar of Strength
		case 75: // Zombie
		case 6763: // Dried Zombie
			return 5569;
		case 110: // Fire Giant
		case 112: // Moss Giant
			return 4652;
		case 3493:
			// handleRFD(i);
			return 3503;
		case 3494:
			// handleRFD(i);
			return 1752;
		case 3495:
			// handleRFD(i);
			return 1752;
		case 3496:
			// handleRFD(i);
			return 3509;
		case 3497:
			// handleRFD(i);
			return 1342;
		case 1471:
			return 5521;
		case 1465:
			return 1384;
		case 3340:
			return 3310;
		case 1593:
		case 49:
			return 6576;
		case 92:
			return 5491;
		case 6248:
			return 6377;
		case 6250:
			return 7016;
		case 6252:
			return 7011;
		case 6225:
		case 6223:
		case 6227:
			return 6956;
		case 1600:
			return 228;
		case 1265:
			return 1314;
		case 5666:
			return 5898;
		case 6263:
		case 6261:
		case 6265:
			return 6156;
		case 1158:
			return 6242;
		case 1160:
			return 6233;
		case 1459:
			return 1404;
		case 117:
			return 4651;
			// sara gwd
		case 2562:
			return 6965;
		case 2563:
			return 6377;
		case 2564:
			return 7016;
		case 2565:
			return 7011;
			// bandos gwd
		case 2551:
		case 2552:
		case 2553:
			return 6156;
		case 2550:
			return 7062;
		case 2892:
		case 2894:
			return 2865;
			// Godwars
		case 6247:
			return 6965;
		case 6203:
			return 6944;
		case 4154:
			return 3993;
		case 6260:
			return 7062;
		case 6222:
			return 6975;
		case 6267:
			// appendKillCount(i);
			return 357;
		case 6268:
			// appendKillCount(i);
			return 2938;
		case 6269:
			// appendKillCount(i);
			return 4653;
		case 6270:
		case 4291:// Cyclops
		case 4292:// Ice cyclops
					// appendKillCount(i);
			return 4653;
		case 6271:
		case 6272:
		case 6273:
		case 6274:
			// appendKillCount(i);
			return 4321;
			// End GodWars
		case 1612: // banshee
			return 1524;
		case 2558:
			return 3503;
		case 2559:
		case 2560:
		case 2561:
			return 6956;
		case 2607:
			return 2607;
		case 2627:
			return 2620;
		case 2630:
			return 2627;
		case 2631:
			return 2630;
		case 2738:
			return 2627;
		case 2741:
			return 2638;
		case 2746:
			return 2638;
		case 2743:
			return 2646;
		case 2745:
			// handleJadDeath(i);
			return 2654;
		case 6142:
		case 6143:
		case 6144:
		case 6145:
		case 6150:
		case 6151:
		case 6152:
		case 6153:
			return -1;

		case 3200:
		case 2837:
			return 3147;

		case 2035: // spider
			return 146;

		case 47:
		case 2033: // rat
			return 141;

		case 2031: // bloodvel
			return 2073;

		case 101: // goblin
			return 6182;

		case 5529:
		case 81: // cow
			return 5851;

		case 41: // chicken
			return 5389;

		case 1338: // dagannoth
		case 1340:
		case 1342:
			return 1342;

		case 2881:
		case 2882:
		case 2883:
			return 2856;

		case 111: // ice giant
			return 131;

		case 125: // ice warrior
			return 843;

		case 751:
		case 73: // Zombies!!
			return 302;

		case 1626:
		case 1627:
		case 1628:
		case 1629:
		case 1630:
		case 1631:
		case 1632: // turoth!
			return 1597;

		case 1616: // basilisk
			return 1548;

		case 1653: // hand
			return 1590;

		case 6204:
		case 6206:
		case 6208:
		case 82: // demons
		case 83:
		case 84:
			return 67;

		case 1605: // abby spec
			return 1508;

		case 51: // baby drags
		case 52:
		case 1589:
		case 3376:
			return 28;

		case 1610:
		case 1611:
			return 1518;

		case 1618:
		case 1619:
			return 1553;

		case 1620:
		case 1621:
			return 1563;

		case 2783:
			return 2733;

		case 1615:
			return 1538;

		case 1624:
			return 1558;

		case 1613:
			return 1530;

		case 1633:
		case 1634:
		case 1635:
		case 1636:
			return 1580;

		case 1648:
		case 1649:
		case 1650:
		case 1651:
		case 1652:
		case 1654:
		case 1655:
		case 1656:
		case 1657:
			return 1590;

		case 100:
		case 102:
			return 313;

		case 105:
		case 106:
			return 44;

		case 412:
		case 78:
			return 4917;

		case 122:
		case 123:
			return 167;

		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
		case 64:
		case 134:
			return 146;

		case 1153:
		case 1154:
		case 1155:
		case 1156:
		case 1157:
			return 6230;

		case 103:
		case 104:
			return 5542;

		case 118:
		case 119:
			return 102;
		case 4682:
			// handleLucien(i);
			return 92;
		case 50: // drags
		case 5363:
		case 742:
		case 53:
		case 54:
		case 55:
		case 941:
		case 5362:
		case 1590:
		case 1591:
		case 1592:
		case 3590:
			return 92;
		case 253:
		case 258:
			// appendAssaultCount(i);
			return 2304;

		default:
			return 2304;
		}
	}

	/**
	 * Attack delays
	 */
	public int getNpcDelay(int i) {
		switch (npcs[i].npcType) {
		case 2025:
		case 2028:
			return 7;

		case 2745:
			return 8;

		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2550:
			return 6;
			// saradomin gw boss
		case 2562:
			return 2;

		default:
			return 5;
		}
	}

	/**
	 * Hit delays
	 */
	public int getHitDelay(int i) {
		switch (npcs[i].npcType) {
		case 3649: // halloween event npc, change this!
		case 2881:
		case 2882:
		case 3200:
		case 2837:
		case 2892:
		case 2894:
			return 3;

		case 2743:
		case 2631:
		case 2558:
		case 2559:
		case 2560:
			return 3;
		case 1158:
		case 1160:
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2)
				return 3;
			else
				return 2;
		case 2745:
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2)
				return 5;
			else
				return 2;

		case 2025:
			return 4;
		case 2028:
			return 3;

		default:
			return 2;
		}
	}

	/**
	 * Npc respawn time
	 */
	public int getRespawnTime(int i) {

		switch (npcs[i].npcType) {
		case 6064:
		case 6053:
			return 600;
		case 1028:
			return 40;
		case 182:
		case 183:
		case 127:
			return 60;
		case 3847:
		return 100;
		case 3070:
		return 75;
		case 2881:
		case 2882:
		case 2883:
		case 2558:
		case 2559:
		case 2560:
		case 2561:
		case 2562:
		case 2563:
		case 2564:
		case 2550:
		case 2551:
		case 2552:
		case 2553:
		case 1926:
		case 6260:
		case 6261:
		case 6263:
		case 6222:
		case 6223:
		case 6225:
		case 6227:
		case 6203:
		case 6208:
		case 6204:
		case 6206:
		case 6247:
		case 6252:
		case 6250:
		case 6248:
		case 6265:
			return 100;
		case 5219:
		case 6142:
		case 6143:
		case 6144:
		case 6145:
		case 6150:
		case 6151:
		case 6152:
		case 6153:
			return 500;
		case 5529:
			return 3;
		default:
			return 25;
		}
	}

	public void newNPC(int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return; // no free slot found

		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] == null) {
				slot = i;
				break;
			}
		}

		if (slot == -1)
			return; // no free slot found

		NPCList newNPCList = new NPCList(npcType);
		newNPCList.npcName = npcName;
		newNPCList.npcCombat = combat;
		newNPCList.npcHealth = HP;
		NpcList[slot] = newNPCList;
	}

	private int getDistanceBeforeDeath(int index) {
		if (npcs[index] != null) {
			// if(NPCHandler.isFightCaveNpc(index))
			return 60;
		}
		return 20;
	}

	public static boolean isSpawnedBy(Client player, NPC npc) {
		if (player != null && npc != null)
			if (npc.spawnedBy == player.playerId
					|| npc.killerId == player.playerId)
				return true;
		return false;
	}

	public void process() {
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] == null)
				continue;
			npcs[i].clearUpdateFlags();

		}

		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				NPC NPC = npcs[i];
				Client petOwner = (Client) PlayerHandler.players[NPC.summonedBy];
				if (petOwner == null && NPC.summoned) {
					Pet.deletePet(NPC);
				}
				if (petOwner != null && petOwner.isDead) {
					Pet.deletePet(NPC);
				}
				if (petOwner != null && petOwner.getPetSummoned()
						&& NPC.summoned) {
					if (petOwner.goodDistance(NPC.getX(), NPC.getY(),
							petOwner.absX, petOwner.absY, 15)) {
						Server.npcHandler.followPlayer(i, petOwner.playerId);
					} else {
						Pet.deletePet(NPC);
						Pet.summonPet(petOwner, petOwner.petID, petOwner.absX,
								petOwner.absY - 1, petOwner.heightLevel);
					}
				}
				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
				}
				if (npcs[i].npcType == 423 && npcs[i].HP <= 40
						&& Misc.random(9) == 1) {
					npcs[i].gfx0(436);
					npcs[i].HP += 29;
					npcs[i].updateRequired = true;
					npcs[i].animUpdateRequired = true;
					npcs[i].updateRequired = true;
					npcs[i].forceChat("Thanks for lettin me eat ya brains son! (Zombie healed)");
					npcs[i].updateRequired = true;
					npcs[i].animUpdateRequired = true;
				}
				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}

				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}

				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					applyDamage(i);
				}

				if (npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}
				/*
				 * if (npcs[i].npcType == 471) { // vote points if
				 * (Misc.random2(10) <= 3) { npcs[i].updateRequired = true;
				 * npcs[
				 * i].forceChat("Vote daily for nice rewards view them here"); }
				 * }
				 */
				if (npcs[i].spawnedBy > 0) { // delete summons npc
					if (PlayerHandler.players[npcs[i].spawnedBy] == null
							|| PlayerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel
							|| PlayerHandler.players[npcs[i].spawnedBy].respawnTimer > 0
							|| !PlayerHandler.players[npcs[i].spawnedBy]
									.goodDistance(
											npcs[i].getX(),
											npcs[i].getY(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getX(),
											PlayerHandler.players[npcs[i].spawnedBy]
													.getY(),
											getDistanceBeforeDeath(i))) {

						if (PlayerHandler.players[npcs[i].spawnedBy] != null) {
							for (int o = 0; o < PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++) {
								if (npcs[i].npcType == PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
									if (PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1)
										PlayerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
								}
							}
						}
						npcs[i] = null;
					}
				}
				if (npcs[i] == null)
					continue;

				/**
				 * Attacking player
				 **/
				if (isAggressive(i) && npcs[i].npcType != 7891
						&& !npcs[i].underAttack && !npcs[i].isDead
						&& !switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				} else if (isAggressive(i) && !npcs[i].underAttack
						&& !npcs[i].isDead && switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				}

				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000)
					npcs[i].underAttackBy = 0;

				if ((npcs[i].killerId > 0 || npcs[i].underAttack)
						&& npcs[i].npcType != 7891 && !npcs[i].walkingHome
						&& retaliates(npcs[i].npcType)) {
					if (!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if (PlayerHandler.players[p] != null) {
							Client c = (Client) PlayerHandler.players[p];
							followPlayer(i, c.playerId);
							if (npcs[i] == null)
								continue;
							if (npcs[i].attackTimer == 0) {
								attackPlayer(c, i);
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}

				/**
				 * Random walking and walking home
				 **/
				if (npcs[i] == null)
					continue;
				if ((!npcs[i].underAttack || npcs[i].walkingHome)
						&& npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;
					if (npcs[i].spawnedBy == 0) {
						if ((npcs[i].absX > npcs[i].makeX
								+ Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absX < npcs[i].makeX
										- Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absY > npcs[i].makeY
										+ Config.NPC_RANDOM_WALK_DISTANCE)
								|| (npcs[i].absY < npcs[i].makeY
										- Config.NPC_RANDOM_WALK_DISTANCE)) {
							npcs[i].walkingHome = true;
						}
					}
					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX
							&& npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if (npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						handleClipping(i);
						npcs[i].getNextNPCMovement(i);
						handleClipping(i);
						npcs[i].updateRequired = true;
					}
					if (npcs[i].walkingType >= 0) {
						switch (npcs[i].walkingType) {

						case 5:
							npcs[i].turnNpc(npcs[i].absX - 1, npcs[i].absY);
							break;

						case 4:
							npcs[i].turnNpc(npcs[i].absX + 1, npcs[i].absY);
							break;

						case 3:
							npcs[i].turnNpc(npcs[i].absX, npcs[i].absY - 1);
							break;
						case 2:
							npcs[i].turnNpc(npcs[i].absX, npcs[i].absY + 1);
							break;

						case 1:
							if (Misc.random(3) == 1 && !npcs[i].walkingHome) {
								int MoveX = 0;
								int MoveY = 0;
								int Rnd = Misc.random(9);
								if (Rnd == 1) {
									MoveX = 1;
									MoveY = 1;
								} else if (Rnd == 2) {
									MoveX = -1;
								} else if (Rnd == 3) {
									MoveY = -1;
								} else if (Rnd == 4) {
									MoveX = 1;
								} else if (Rnd == 5) {
									MoveY = 1;
								} else if (Rnd == 6) {
									MoveX = -1;
									MoveY = -1;
								} else if (Rnd == 7) {
									MoveX = -1;
									MoveY = 1;
								} else if (Rnd == 8) {
									MoveX = 1;
									MoveY = -1;
								}
								if (MoveX == 1) {
									if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
										npcs[i].moveX = MoveX;
									} else {
										npcs[i].moveX = 0;
									}
								}
								if (MoveX == -1) {
									if (npcs[i].absX - MoveX > npcs[i].makeX - 1) {
										npcs[i].moveX = MoveX;
									} else {
										npcs[i].moveX = 0;
									}
								}
								if (MoveY == 1) {
									if (npcs[i].absY + MoveY < npcs[i].makeY + 1) {
										npcs[i].moveY = MoveY;
									} else {
										npcs[i].moveY = 0;
									}
								}
								if (MoveY == -1) {
									if (npcs[i].absY - MoveY > npcs[i].makeY - 1) {
										npcs[i].moveY = MoveY;
									} else {
										npcs[i].moveY = 0;
									}
								}
								handleClipping(i);
								npcs[i].getNextNPCMovement(i);
								handleClipping(i);
								npcs[i].updateRequired = true;
							}
							break;
						}
					}
				}
				if (npcs[i].isDead == true) {
					Client c = (Client) PlayerHandler.players[npcs[i].oldIndex];
					Client temp = (Client) PlayerHandler.players[npcs[i].killerId];
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false
							&& npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = getNpcKillerId(i);
						npcs[i].animNumber = getDeadEmote(i); // dead emote
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						killedBarrow(i);
						if (isFightCaveNpc(i))
							killedTzhaar(i);
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);

					} else if (npcs[i].actionTimer == 0
							&& npcs[i].applyDead == true
							&& npcs[i].needRespawn == false) {
						Client p = (Client) PlayerHandler.players[NPCHandler.npcs[i].killedBy];
						if (npcs[i].npcType == 2745) {
							handleJadDeath(i);
						}
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = getRespawnTime(i); // respawn time
						dropItems(i); // npc drops items!
						appendSlayerExperience(i);
						appendBossKC(i);
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType >= 2440 && npcs[i].npcType <= 2446) {
							Server.objectManager.removeObject(npcs[i].absX,
									npcs[i].absY);
						}
						if (npcs[i].npcType == 2044) {
							temp.teleportToX = 3194; // x coord
							temp.teleportToY = 9553; // y coord
							c.getDH().sendDialogues(1037, 857);
							// c.reputation = c.reputation + 10;
						}
						if (npcs[i].npcType == 1158) {
							Server.npcHandler.spawnNpc(c, 1160, npcs[i].absX,
									npcs[i].absY - 1, 0, 0, 255, 31, 220, 330,
									true, true);
						}
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].needRespawn == true) {
						if (npcs[i].spawnedBy > 0) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;
							int old9 = npcs[i].defence;

							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7,
									old8, old9);
						}
					}
				}
			}
		}
	}

	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
		case 6260:
		case 2508:
			if (npcs[i].firstAttacker > 0)
				return false;
			break;
		}
		return true;
	}

	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
		case 2558:
			return true;
		case 4173:
			if (npcs[i].attackType == 2)
				return true;
		case 2562:
			if (npcs[i].attackType == 2)
				return true;
		case 2550:
			if (npcs[i].attackType == 1)
				return true;
		case 3847: //kraken
				if (npcs[i].attackType == 1)
				return true;
		case 4001: //callisto
				if (npcs[i].attackType == 1)
				return true;
		case 1913:
			if (npcs[i].attackType == 3)
				return true;
		case 1974:
			if (npcs[i].attackType == 2)
				return true;
		case 1977:
			if (npcs[i].attackType == 2)
				return true;
		case 1914:
			if (npcs[i].attackType == 3)
				return true;
		case 6260:
			if (npcs[i].attackType == 1)
				return true;
		default:
			return false;
		}

	}

	/**
	 * Npc killer id?
	 **/

	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int p = 1; p < Config.MAX_PLAYERS; p++) {
			if (PlayerHandler.players[p] != null) {
				if (PlayerHandler.players[p].lastNpcAttacked == npcId) {
					if (PlayerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = PlayerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					PlayerHandler.players[p].totalDamageDealt = 0;
				}
			}
		}
		return killerId;
	}

	/**
	 * 
	 */
	private void killedBarrow(int i) {
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			for (int o = 0; o < c.barrowsNpcs.length; o++) {
				if (npcs[i].npcType == c.barrowsNpcs[o][0]) {
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;
				}
			}
		}
	}

	/**
	 * Dropping Items!
	 **/
	public void dropItems(int i) {

		int npc = 0;
		double ringBonus = 1, wildBonus = 1, potentialBonus = 1, lampBonus = 1, nerdBonus = 1;

		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];

		if (c != null) {
		
		if(c.bonusDrops())
			lampBonus = 0.50;

			c.npcKills += 1;
			if (npcs[i].npcType == 6260) {
				c.bandosKills += 1;
			}
			else if (npcs[i].npcType == 6222) {
				c.armadylKills += 1;
			}
			else if (npcs[i].npcType == 6247) {
				c.saradominKills += 1;
			}
			else if (npcs[i].npcType == 6203) {
				c.zamorakKills += 1;
			}
			else if (npcs[i].npcType == 3200) {
				c.chaosKills += 1;
			}
			else if (npcs[i].npcType == 2881) {
				c.dSupremeKills += 1;
			}
			else if (npcs[i].npcType == 2882) {
				c.dPrimeKills += 1;
			}
			else if (npcs[i].npcType == 2883) {
				c.dRexKills += 1;
			}
			else if (npcs[i].npcType == 4172) {
				c.scorpiaKills += 1;
			}
			else if (npcs[i].npcType == 4173) {
				c.venenatisKills += 1;
			}
			else if (npcs[i].npcType == 4001) {
				c.callistoKills += 1;
			}
			else if (npcs[i].npcType == 3847) {
				c.seaTrollQueenKills += 1;
			}
			else if (npcs[i].npcType == 3943) {
				c.seaSnakeKills += 1;
			}
			else if (npcs[i].npcType == 5247) {
				c.penanceQueenKills += 1;
			}
			else if (npcs[i].npcType == 4175) {
				c.vetionKills += 1;
			}
			else if (npcs[i].npcType == 1265) {
				c.rockcrabKills += 1;
			}
			else if (npcs[i].npcType == 1590) {
				c.bronzedragonKills += 1;
			}
			else if (npcs[i].npcType == 1591) {
				c.irondragonKills += 1;
			}
			else if (npcs[i].npcType == 1592) {
				c.steeldragonKills += 1;
			}
			else if (npcs[i].npcType == 1610) {
				c.gargoyleKills += 1;
			}
			else if (npcs[i].npcType == 55) {
				c.bluedragonKills += 1;
			}
			else if (npcs[i].npcType == 117) {
				c.hillgiantKills += 1;
			}
			else if (npcs[i].npcType == 52) {
				c.babybluedragonKills += 1;
			}
			else if (npcs[i].npcType == 941) {
				c.greendragonKills += 1;
			}
			else if (npcs[i].npcType == 84) {
				c.blackdemonKills += 1;
			}
			else if (npcs[i].npcType == 5332) {
				c.skeletonKills += 1;
			}
			else if (npcs[i].npcType == 1613) {
				c.nechryaelKills += 1;
			}
			else if (npcs[i].npcType == 1643) {
				c.infernalmageKills += 1;
			}
			else if (npcs[i].npcType == 1624) {
				c.dustdevilKills += 1;
			}
			else if (npcs[i].npcType == 1618) {
				c.bloodveldKills += 1;
			}
			else if (npcs[i].npcType == 1612) {
				c.bansheeKills += 1;
			}
			else if (npcs[i].npcType == 2912) {
				c.giantbatKills += 1;
			}
			else if (npcs[i].npcType == 82) {
				c.lesserdemonKills += 1;
			}
			else if (npcs[i].npcType == 1648) {
				c.crawlinghandKills += 1;
			}
			else if (npcs[i].npcType == 181) {
				c.chaosdruidKills += 1;
			}
			else if (npcs[i].npcType == 119) {
				c.chaosdwarfKills += 1;
			}
			else if (npcs[i].npcType == 5342) {
				c.ghostKills += 1;
			}
			else if (npcs[i].npcType == 2783) {
				c.darkBeastKills += 1;
			}
			else if (npcs[i].npcType == 1615) {
				c.abbyDemonKills += 1;
			}
			if (npcs[i].npcType == 4154 || npcs[i].npcType == 4158) {

				if (Misc.random(50) == 0) {
					Server.itemHandler.createGroundItem(c,
							(int) (Math.random() * Config.rareDrops.length),
							c.absX, c.absY, 1, c.playerId);
				} else if (Misc.random(1) == 0) {

					int value = (int) (Math.random() * Config.commonDrops.length);

					Server.itemHandler.createGroundItem(c,
							Config.commonDrops[value][0], c.absX, c.absY,
							Config.commonDrops[value][1], c.playerId);

				} else if (Misc.random(15) == 0) {
					int value = (int) (Math.random() * Config.uncommonDrops.length);

					Server.itemHandler.createGroundItem(c,
							Config.uncommonDrops[value][0], c.absX, c.absY,
							Config.commonDrops[value][1], c.playerId);
				}

				c.sendMessage("You have killed the kraken.");
				return;
			}

			int[] animatedArmor = { 4278, 4279, 4280, 4281, 4282, 4283, 4284 };

			int[][] armor = new int[][]

			{ { 1155, 1117, 1075 }, { 1153, 1115, 1067 }, { 1157, 1119, 1069 },
					{ 1165, 1125, 1077 }, { 1159, 1121, 1071 },
					{ 1161, 1123, 1073 }, { 1163, 1127, 1079 } };

			int[] tokens = { 5, 10, 15, 20, 25, 30, 35 };

			for (int j = 0; j < animatedArmor.length; j++) {

				if (npcs[i].npcType == animatedArmor[j]) {

					c.spawned = false;

					Server.itemHandler.createGroundItem(c, armor[j][0],
							npcs[i].absX, npcs[i].absY, 1, c.playerId);
					Server.itemHandler.createGroundItem(c, armor[j][1],
							npcs[i].absX, npcs[i].absY, 1, c.playerId);
					Server.itemHandler.createGroundItem(c, armor[j][2],
							npcs[i].absX, npcs[i].absY, 1, c.playerId);
					Server.itemHandler.createGroundItem(c, 8851, npcs[i].absX,
							npcs[i].absY, tokens[j], c.playerId);
				}
			}

			if (npcs[i].npcType == 4291) {
				if (!c.inDefenderRoom) {
					c.sendMessage("Re pass the door to earn defenders.");
					return;
				}
				if (ZammyKC(i)) {
					c.ZammyKc += 1;
					c.getPA().sendFrame126("" + c.ZammyKc + "", 16219);
				}
				if (ArmadylKC(i)) {
					c.ArmaKc += 1;
					c.getPA().sendFrame126("" + c.ArmaKc + "", 16216);
				}
				if (BandosKC(i)) {
					c.BandKc += 1;
					c.getPA().sendFrame126("" + c.BandKc + "", 16217);
				}
				if (SaraKC(i)) {
					c.SaraKc += 1;
					c.getPA().sendFrame126("" + c.SaraKc + "", 16218);
				}
				if (Misc.random(9) == 5) {
					int droppedDefender = WarriorsGuild.getCurrentDefender(c);
					Server.itemHandler.createGroundItem(c, droppedDefender,
							npcs[i].absX, npcs[i].absY, 1, c.playerId);
					c.sendMessage("@red@You received a "
							+ c.getItems().getItemName(droppedDefender)
									.toLowerCase() + " drop!");
				}
			}

			for (npc = 0; npc < Config.NPC_DROPS.length; npc++) {

				if (npcs[i].npcType == Config.NPC_DROPS[npc][0]) {
					int rareloot = Misc.random(Config.NPC_DROPS[npc][3]);

					/*
					 * if (c.playerEquipment[Player.playerRing] == 773 &&
					 * rareloot > 100 && rareloot != 0) rareloot =
					 * Misc.random(rareloot + 5);
					 */
					if (rareloot == 0) {

						Server.itemHandler.createGroundItem(c,
								Config.NPC_DROPS[npc][1], npcs[i].absX,
								npcs[i].absY, Config.NPC_DROPS[npc][2],
								c.playerId);
						if (Config.NPC_DROPS[npc][1] == 11704
								|| Config.NPC_DROPS[npc][1] == 11702
								|| Config.NPC_DROPS[npc][1] == 15037
								|| Config.NPC_DROPS[npc][1] == 11706
								|| Config.NPC_DROPS[npc][1] == 15038
								|| Config.NPC_DROPS[npc][1] == 13740
								|| Config.NPC_DROPS[npc][1] == 13742
								|| Config.NPC_DROPS[npc][1] == 13744
								|| Config.NPC_DROPS[npc][1] == 13738
								|| Config.NPC_DROPS[npc][1] == 15040
								|| Config.NPC_DROPS[npc][1] == 15041
								|| Config.NPC_DROPS[npc][1] == 11708
								|| Config.NPC_DROPS[npc][1] == 11724
								|| Config.NPC_DROPS[npc][1] == 11726
								|| Config.NPC_DROPS[npc][1] == 11718
								|| Config.NPC_DROPS[npc][1] == 11720
								|| Config.NPC_DROPS[npc][1] == 11722
								|| Config.NPC_DROPS[npc][1] == 15103
								|| Config.NPC_DROPS[npc][1] == 11698
								|| Config.NPC_DROPS[npc][1] == 11694
								|| Config.NPC_DROPS[npc][1] == 11994
								|| Config.NPC_DROPS[npc][1] == 11995
								|| Config.NPC_DROPS[npc][1] == 11730
								|| Config.NPC_DROPS[npc][1] == 11732
								|| Config.NPC_DROPS[npc][1] == 4753
								|| Config.NPC_DROPS[npc][1] == 4755
								|| Config.NPC_DROPS[npc][1] == 4757
								|| Config.NPC_DROPS[npc][1] == 4759
								|| Config.NPC_DROPS[npc][1] == 4745
								|| Config.NPC_DROPS[npc][1] == 4747
								|| Config.NPC_DROPS[npc][1] == 4749
								|| Config.NPC_DROPS[npc][1] == 4751
								|| Config.NPC_DROPS[npc][1] == 4740
								|| Config.NPC_DROPS[npc][1] == 4732
								|| Config.NPC_DROPS[npc][1] == 4734
								|| Config.NPC_DROPS[npc][1] == 4736
								|| Config.NPC_DROPS[npc][1] == 4738
								|| Config.NPC_DROPS[npc][1] == 4724
								|| Config.NPC_DROPS[npc][1] == 4726
								|| Config.NPC_DROPS[npc][1] == 4728
								|| Config.NPC_DROPS[npc][1] == 4716
								|| Config.NPC_DROPS[npc][1] == 4722
								|| Config.NPC_DROPS[npc][1] == 4718
								|| Config.NPC_DROPS[npc][1] == 4720
								|| Config.NPC_DROPS[npc][1] == 4708
								|| Config.NPC_DROPS[npc][1] == 4710
								|| Config.NPC_DROPS[npc][1] == 4712
								|| Config.NPC_DROPS[npc][1] == 4714
								|| Config.NPC_DROPS[npc][1] == 4151
								|| Config.NPC_DROPS[npc][1] == 4153
								|| Config.NPC_DROPS[npc][1] == 3140
								|| Config.NPC_DROPS[npc][1] == 6731
								|| Config.NPC_DROPS[npc][1] == 6733
								|| Config.NPC_DROPS[npc][1] == 6735
								|| Config.NPC_DROPS[npc][1] == 6737
								|| Config.NPC_DROPS[npc][1] == 6731
								|| Config.NPC_DROPS[npc][1] == 6585
								|| Config.NPC_DROPS[npc][1] == 15019
								|| Config.NPC_DROPS[npc][1] == 15023
								|| Config.NPC_DROPS[npc][1] == 11235
								|| Config.NPC_DROPS[npc][1] == 11286
								|| Config.NPC_DROPS[npc][1] == 11994
								|| Config.NPC_DROPS[npc][1] == 12002
								|| Config.NPC_DROPS[npc][1] == 12003
								|| Config.NPC_DROPS[npc][1] == 12004
								|| Config.NPC_DROPS[npc][1] == 12005
								|| Config.NPC_DROPS[npc][1] == 12010
								|| Config.NPC_DROPS[npc][1] == 11283
								|| Config.NPC_DROPS[npc][1] == 11986
								|| Config.NPC_DROPS[npc][1] == 11987
								|| Config.NPC_DROPS[npc][1] == 11724
								|| Config.NPC_DROPS[npc][1] == 11726
								|| Config.NPC_DROPS[npc][1] == 11718
								|| Config.NPC_DROPS[npc][1] == 11720
								|| Config.NPC_DROPS[npc][1] == 11722
								|| Config.NPC_DROPS[npc][1] == 11722
								|| Config.NPC_DROPS[npc][1] == 11716
								|| Config.NPC_DROPS[npc][1] == 11728
								|| Config.NPC_DROPS[npc][1] == 12011
								|| Config.NPC_DROPS[npc][1] == 14000
								|| Config.NPC_DROPS[npc][1] == 14001
								|| Config.NPC_DROPS[npc][1] == 14017
								|| Config.NPC_DROPS[npc][1] == 14018
								|| Config.NPC_DROPS[npc][1] == 14019
								|| Config.NPC_DROPS[npc][1] == 14002
								|| Config.NPC_DROPS[npc][1] == 14003
								|| Config.NPC_DROPS[npc][1] == 14004
								|| Config.NPC_DROPS[npc][1] == 14005
								|| Config.NPC_DROPS[npc][1] == 15030
								|| Config.NPC_DROPS[npc][1] == 12363
								|| Config.NPC_DROPS[npc][1] == 12365
								|| Config.NPC_DROPS[npc][1] == 12367
								|| Config.NPC_DROPS[npc][1] == 12369
								|| Config.NPC_DROPS[npc][1] == 12522
								|| Config.NPC_DROPS[npc][1] == 12520
								|| Config.NPC_DROPS[npc][1] == 12518
								|| Config.NPC_DROPS[npc][1] == 12524
								|| Config.NPC_DROPS[npc][1] == 13899
								|| Config.NPC_DROPS[npc][1] == 13887
								|| Config.NPC_DROPS[npc][1] == 13887
								|| Config.NPC_DROPS[npc][1] == 13884
								|| Config.NPC_DROPS[npc][1] == 13890
								|| Config.NPC_DROPS[npc][1] == 13902
								|| Config.NPC_DROPS[npc][1] == 13896
								|| Config.NPC_DROPS[npc][1] == 13858
								|| Config.NPC_DROPS[npc][1] == 13861
								|| Config.NPC_DROPS[npc][1] == 13864
								|| Config.NPC_DROPS[npc][1] == 13870
								|| Config.NPC_DROPS[npc][1] == 13876
								|| Config.NPC_DROPS[npc][1] == 13873
								|| Config.NPC_DROPS[npc][1] == 15003
								|| Config.NPC_DROPS[npc][1] == 15039
								|| Config.NPC_DROPS[npc][1] == 6739
								|| Config.NPC_DROPS[npc][1] == 11770
								|| Config.NPC_DROPS[npc][1] == 11771
								|| Config.NPC_DROPS[npc][1] == 11772
								|| Config.NPC_DROPS[npc][1] == 4081
								|| Config.NPC_DROPS[npc][1] == 10588
								|| Config.NPC_DROPS[npc][1] == 13081
								|| Config.NPC_DROPS[npc][1] == 13082
								|| Config.NPC_DROPS[npc][1] == 5585
								|| Config.NPC_DROPS[npc][1] == 7297
								|| Config.NPC_DROPS[npc][1] == 11773) {
							for (int j = 0; j < PlayerHandler.players.length; j++) {
								if (PlayerHandler.players[j] != null) {
									Client c2 = (Client) PlayerHandler.players[j];
									c2.sendMessage("@bla@[@red@Rare Drop@bla@] @red@Player @blu@"
											+ c.playerName
											+ "@red@ has just received @blu@ 1 x @red@ "
											+ c.getItems().getItemName(
													Config.NPC_DROPS[npc][1])
											+ "!");
								}
							}
						}
					}
				}
			}
		}
	}

	// id of bones dropped by npcs
	public int boneDrop(int type) {
		switch (type) {
		case 1:// normal bones
		case 9:
		case 100:
		case 12:
		case 17:
		case 803:
		case 18:
		case 81:
		case 101:
		case 41:
		case 19:
		case 459:
		case 75:
		case 86:
		case 78:
		case 912:
		case 913:
		case 914:
		case 1648:
		case 1643:
		case 1618:
		case 1624:
		case 181:
		case 119:
		case 6218:
		case 26:
		case 1341:
			return 526;
		case 117:
			return 532;// big bones
		case 50:// drags
		case 53:
		case 54:
		case 55:
		case 941:
		case 5363:
		case 1590:
		case 1591:
		case 1592:
			return 536;
		case 84:
		case 1615:
		case 1613:
		case 82:
		case 3200:
		case 991:
			return 592;
		case 2881:
		case 2882:
		case 2883:
			return 6729;
		default:
			return -1;
		}
	}

	public int getStackedDropAmount(int itemId, int npcId) {
		switch (itemId) {
		case 995:
			switch (npcId) {
			case 1:
				return 50 + Misc.random(50);
			case 9:
				return 133 + Misc.random(100);
			case 1624:
				return 1000 + Misc.random(300);
			case 1618:
				return 1000 + Misc.random(300);
			case 1643:
				return 1000 + Misc.random(300);
			case 1610:
				return 1000 + Misc.random(1000);
			case 1613:
				return 1500 + Misc.random(1250);
			case 1615:
				return 3000;
			case 18:
				return 500;
			case 101:
				return 60;
			case 913:
			case 912:
			case 914:
				return 750 + Misc.random(500);
			case 1612:
				return 250 + Misc.random(500);
			case 1648:
				return 250 + Misc.random(250);
			case 459:
				return 200;
			case 82:
				return 1000 + Misc.random(455);
			case 52:
				return 400 + Misc.random(200);
			case 6218:
				return 1500 + Misc.random(2000);
			case 1341:
				return 1500 + Misc.random(500);
			case 26:
				return 500 + Misc.random(100);
			case 20:
				return 750 + Misc.random(100);
			case 21:
				return 890 + Misc.random(125);
			case 117:
				return 500 + Misc.random(250);
			case 2607:
				return 500 + Misc.random(350);
			}
			break;
		case 11212:
			return 10 + Misc.random(4);
		case 565:
		case 561:
			return 10;
		case 560:
		case 563:
		case 562:
			return 15;
		case 555:
		case 554:
		case 556:
		case 557:
			return 20;
		case 892:
			return 40;
		case 886:
			return 100;
		case 6522:
			return 6 + Misc.random(5);

		}

		return 1;
	}

	/**
	 * Slayer xp and tasks
	 **/
	public void appendSlayerExperience(int i) {
		int npc = 0;
		Client c = (Client) PlayerHandler.players[npcs[i].killedBy];
		if (c != null) {
			if (c.slayerTask == npcs[i].npcType) {
				c.taskAmount--;
				c.getPA().addSkillXP(npcs[i].MaxHP * Config.SLAYER_EXPERIENCE,
						18);
				c.getPA()
						.sendFrame126(
								"@whi@Task: @gre@"
										+ c.taskAmount
										+ " "
										+ Server.npcHandler.getNpcListName(c.slayerTask)
										+ " ", 29168);

				if (c.taskAmount <= 0) {
					if (c.combatLevel < 50) {
						c.getPA().addSkillXP(
								(npcs[i].MaxHP * 8) * Config.SLAYER_EXPERIENCE,
								18);
						c.slayerTask = -1;
						c.slayerPoints += 3;
						c.getPA().sendFrame126(
								"@whi@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 29167);
						c.sendMessage("You completed @gre@EASY@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("You have received @red@3@bla@ slayer points for this.");
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 5;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("You've recieved a bonus of xp and 5 slayer points because wearing slayer helmet.");
						}
					} else if (c.combatLevel >= 50 && c.combatLevel <= 90) {
						c.getPA()
								.addSkillXP(
										(npcs[i].MaxHP * 10)
												* Config.SLAYER_EXPERIENCE, 18);
						c.slayerTask = -1;
						c.slayerPoints += 5;
						c.getPA().sendFrame126(
								"@whi@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 7339);
						c.sendMessage("You completed @blu@MEDIUM@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("You have received @blu@5 @bla@slayer points for this.");
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 5;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("You've recieved a bonus of xp and 5 slayer points because wearing slayer helmet.");
						}
					} else if (c.combatLevel > 90 && c.combatLevel <= 120) {
						c.getPA()
								.addSkillXP(
										(npcs[i].MaxHP * 12)
												* Config.SLAYER_EXPERIENCE, 18);
						c.slayerTask = -1;
						c.slayerPoints += 8;
						c.getPA().sendFrame126(
								"@whi@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 7339);
						c.sendMessage("You completed @red@HARD@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("You have received @red@8@bla@slayer points for this.");
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 5;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("You've recieved a bonus of xp and 5 slayer points because wearing slayer helmet.");

						}

					} else if (c.combatLevel > 120 && c.combatLevel <= 126) {
						c.getPA()
								.addSkillXP(
										(npcs[i].MaxHP * 14)
												* Config.SLAYER_EXPERIENCE, 18);
						c.slayerTask = -1;
						c.slayerPoints += 10;
						c.getPA().sendFrame126(
								"@whi@Slayer Points: @gre@" + c.slayerPoints
										+ " ", 7339);
						c.sendMessage("You completed @pur@ELITE@bla@ level slayer task. Please see a slayer master to get a new one.");
						c.sendMessage("You have received @red@10 @bla@slayer points for this.");
						if (c.playerEquipment[c.playerHat] == 13263) {
							c.slayerPoints += 5;
							c.getPA().addSkillXP(
									(npcs[i].MaxHP * 5)
											* Config.SLAYER_EXPERIENCE, 18);
							c.sendMessage("You've recieved a bonus of xp and 5 slayer points because wearing slayer helmet.");
						}
					}
				}
			}
		}
	}

	/**
	 * Resets players in combat
	 */

	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null)
				if (PlayerHandler.players[j].underAttackBy2 == i)
					PlayerHandler.players[j].underAttackBy2 = 0;
		}
	}

	/**
	 * Npc Follow Player
	 **/

	public int GetMove(int Place1, int Place2) {
		if ((Place1 - Place2) == 0) {
			return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
		return 0;
	}

	public boolean followPlayer(int i) {
		switch (npcs[i].npcType) {
		case 2892:
		case 2894:
			return false;
		}
		return true;
	}

	public void handleClipping(int i) {
		NPC npc = npcs[i];
		if (npc.moveX == 1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = -1;
			}
		} else if (npc.moveX == 1 && npc.moveY == -1) {
			if ((Region
					.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
					npc.moveY = -1;
				else
					npc.moveX = 1;
			}
		} else if (npc.moveX == -1 && npc.moveY == 1) {
			if ((Region
					.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0) {
				npc.moveX = 0;
				npc.moveY = 0;
				if ((Region
						.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
					npc.moveY = 1;
				else
					npc.moveX = -1;
			}
		} // Checking Diagonal movement.

		if (npc.moveY == -1) {
			if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
				npc.moveY = 0;
		} else if (npc.moveY == 1) {
			if ((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
				npc.moveY = 0;
		} // Checking Y movement.
		if (npc.moveX == 1) {
			if ((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0)
				npc.moveX = 0;
		} else if (npc.moveX == -1) {
			if ((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
				npc.moveX = 0;
		} // Checking X movement.
	}

	public void followPlayer(int i, int playerId) {
		if (PlayerHandler.players[playerId] == null) {
			return;
		}
		if (PlayerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}

		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}
		for (int po = 0; po < portals.length; po++) { // ok and again
														// btw just incase you
														// dont know its better
														// the loop
														// instead of 4 lines of
														// code, they
														// function the same way
														// I understand
														// i think so anyway
														// haha :P
			if (npcs[i].npcType == portals[po]) {
				return;
			}
		}

		int playerX = PlayerHandler.players[playerId].absX;
		int playerY = PlayerHandler.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY,
				distanceRequired(i)))
			return;
		if ((npcs[i].spawnedBy > 0)
				|| ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absX > npcs[i].makeX
								- Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absY < npcs[i].makeY
								+ Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY
						- Config.NPC_FOLLOW_DISTANCE))) {
			if (npcs[i].heightLevel == PlayerHandler.players[playerId].heightLevel) {
				if (PlayerHandler.players[playerId] != null && npcs[i] != null) {
					if (playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX > npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX == npcs[i].absX
							|| playerY == npcs[i].absY) {
						int o = Misc.random(3);
						switch (o) {
						case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY + 1);
							break;

						case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY - 1);
							break;

						case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX + 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;

						case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX - 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}
					}
					npcs[i].facePlayer(playerId);
					handleClipping(i);
					npcs[i].getNextNPCMovement(i);
					handleClipping(i);
					npcs[i].facePlayer(playerId);
					npcs[i].updateRequired = true;
				}
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

	public boolean hitThrough = false;

	/**
	 * load spell
	 **/
	public void loadSpell2(int i) {
		npcs[i].attackType = 3;
		int random = Misc.random(3);
		if (random == 0) {
			npcs[i].projectileId = 393; // red
			npcs[i].endGfx = 430;
		} else if (random == 1) {
			npcs[i].projectileId = 394; // green
			npcs[i].endGfx = 429;
		} else if (random == 2) {
			npcs[i].projectileId = 395; // white
			npcs[i].endGfx = 431;
		} else if (random == 3) {
			npcs[i].projectileId = 396; // blue
			npcs[i].endGfx = 428;
		}
	}

	public void loadSpell(Client c, int i) {
		switch (npcs[i].npcType) {
		case 2892:
			npcs[i].projectileId = 94;
			npcs[i].attackType = 2;
			npcs[i].endGfx = 95;
			break;
		case 2894:
			npcs[i].projectileId = 298;
			npcs[i].attackType = 1;
			break;
		// kalphite queen form 1
		case 1158:
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					// Client c = (Client)PlayerHandler.players[j];
					int kq1;
					if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX,
							c.absY, 2))
						kq1 = Misc.random(2);
					else
						kq1 = Misc.random(1);
					if (kq1 == 0) {
						npcs[i].projectileId = 280; // mage
						npcs[i].endGfx = 281;
						npcs[i].attackType = 2;
					} else if (kq1 == 1) {
						npcs[i].attackType = 1; // range
						npcs[i].endGfx = 281;
						npcs[i].projectileId = 473;
					} else if (kq1 == 2) {
						npcs[i].attackType = 0; // melee
						npcs[i].projectileId = -1;
					}
				}
			}
			break;
		// kalphite queen form 2
		case 1160:
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					// Client c = (Client)PlayerHandler.players[j];
					int kq1;
					if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX,
							c.absY, 2))
						kq1 = Misc.random(2);
					else
						kq1 = Misc.random(1);
					if (kq1 == 0) {
						npcs[i].projectileId = 280; // mage
						npcs[i].endGfx = 281;
						npcs[i].attackType = 2;
					} else if (kq1 == 1) {
						npcs[i].attackType = 1; // range
						npcs[i].endGfx = 281;
						npcs[i].projectileId = 473;
					} else if (kq1 == 2) {
						npcs[i].attackType = 0; // melee
						npcs[i].projectileId = -1;
					}
				}
			}
			break;
		/*
		 * Better Dragons
		 */
		case 5363: // Mithril-Dragon
		case 54: // Black-Dragon
		case 55: // Blue-Dragon
		case 941: // Green-Dragon
		case 1592:
		case 1591:
		case 4682:
		case 5362:
		case 4176: // Lava Dragon
			int random1 = Misc.random(4);
			switch (random1) {
			case 1:
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
				break;
			case 0:
				hitThrough = true;
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
				break;
			case 2:
			case 3:
			case 4:
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
				break;
			}
			break;

		case 50:
		case 742:
			int random = Misc.random(4);
			switch (random) {
			case 0:
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
				break;
			case 1:
				npcs[i].projectileId = 394; // green
				npcs[i].endGfx = 429;
				npcs[i].attackType = 3;
				break;
			case 2:
				npcs[i].projectileId = 395; // white
				npcs[i].endGfx = 431;
				npcs[i].attackType = 3;
				break;
			case 3:
				npcs[i].projectileId = 396; // blue
				npcs[i].endGfx = 428;
				npcs[i].attackType = 3;
				break;
			case 4:
				npcs[i].projectileId = -1; // melee
				npcs[i].endGfx = -1;
				npcs[i].attackType = 0;
				break;
			}
			break;
		case 3070: // Wyvern
			if (Misc.random(10) > 7) {
				npcs[i].projectileId = 393; // red
				npcs[i].endGfx = 430;
				npcs[i].attackType = 3;
				startAnimation(2989, i);
			} else {
				startAnimation(2980, i);
				npcs[i].attackType = 0;
			}
			break;
		// arma npcs
		case 6223:
		case 2561:
			npcs[i].attackType = 2;
			npcs[i].endGfx = 1196;
			break;
		case 6227:
			npcs[i].attackType = 0;
			break;
		case 6225:
		case 2560:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1190;
			break;
						case 5247:
		int randu = Misc.random(2);
			if (randu == 0) {
				npcs[i].attackType = 0;
			} else if (randu == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 871;
				npcs[i].projectileId = 870;
			}
			break;
						
			case 3847:
		int rand = Misc.random(2);
		if (rand == 0) {
				npcs[i].projectileId = 162; // green
				npcs[i].endGfx = 163;
				npcs[i].attackType = 2;
				hitThrough = true;
		} else if (rand == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 166;
				npcs[i].projectileId = 165;
			
		} else if (rand == 2) {
				npcs[i].attackType = 0;
			}
			break;
		case 4173: // Venentatis
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					int prayHit = Misc.random(2);
					int styles = Misc.random(2);
					if (styles == 0) {
						npcs[i].attackType = 2;
						hitThrough = true;
						npcs[i].gfx100(164);
						npcs[i].projectileId = 165;
						npcs[i].endGfx = 166;
					}
					if (styles == 1) {
						npcs[i].attackType = 2;
						//hitThrough = true;
						if (prayHit == 1)
							npcs[i].attackType = 2;
						c.getPA().drainPray(i);
						npcs[i].gfx100(170);
						npcs[i].projectileId = 171;
						npcs[i].endGfx = 172;
					}
					if (styles == 2) {
						npcs[i].attackType = 0;
						hitThrough = true;
						npcs[i].gfx100(-1);
						npcs[i].projectileId = -1;
						npcs[i].endGfx = -1;
						c.gfx100(80);
						c.getPA().appendPoison(6);
						c.sendMessage("Venenatis hurls her web at you, sticking you to the ground.");
						c.freezeTimer = 5;
					}
				}
			}
			break;
		/*
		 * case 4173: // Venenatis int prayHit = Misc.random(2); int styles =
		 * Misc.random(2); if (styles == 0) { npcs[i].attackType = 2;
		 * npcs[i].gfx100(164); npcs[i].projectileId = 165; npcs[i].endGfx =
		 * 166; } else if (styles == 1) { if (prayHit == 1)
		 * c.getPA().drainPray(i); npcs[i].attackType = 2; npcs[i].gfx100(164);
		 * npcs[i].projectileId = 165; npcs[i].endGfx = 166; } else {
		 * npcs[i].attackType = 0; npcs[i].projectileId = -1;
		 * c.getPA().appendPoison(24); c.getPA().stunEnemy(i); } break;
		 */

		case 4172: // scorpia
			int prayHit2 = Misc.random(2);
			npcs[i].attackType = 0;
			if (prayHit2 == 0) {
				hitThrough = true;
				c.getPA().appendPoison(40);
			} else if (prayHit2 == 1) {
				hitThrough = false;
			} else {
				hitThrough = false;
			}
			break;
		case 4001: // Callisto
			int meleehit = Misc.random(2);
			if (meleehit == 0) {
				npcs[i].attackType = 0;
			} else if (meleehit == 1) {
				npcs[i].attackType = 0;
				c.getPA().stunEnemy(i);
			} else {
				npcs[i].attackType = 2;
			}
			break;
		case 2028:
			int prayHittin = Misc.random(6);
			if (prayHittin == 0) {
				hitThrough = true;
			} else if (prayHittin == 1) {
				hitThrough = false;
			} else {
				hitThrough = false;
			}
			npcs[i].attackType = 1;
			npcs[i].projectileId = 27;

			break;
		case 2026:
		case 2027:

		case 2029:
		case 2030:
			int prayHitn = Misc.random(6);
			if (prayHitn == 0) {
				hitThrough = true;
			} else if (prayHitn == 1) {
				hitThrough = false;
			} else {
				hitThrough = false;
			}
			break;

		case 4175:
			int prayHit3 = Misc.random(2);
			if (prayHit3 == 0) {
				hitThrough = true;
			} else if (prayHit3 == 1) {
				hitThrough = false;
			} else {
				hitThrough = false;
			}
			break;
		case 4158:
		case 4154:
			int style = Misc.random(2);
			npcs[i].attackType = 2;
			if (style == 0) {
				npcs[i].projectileId = 121;
				hitThrough = true;
			} else if (style == 1) {
				npcs[i].projectileId = 124;
				hitThrough = false;
			} else {
				npcs[i].projectileId = 127;
				hitThrough = false;
			}
			break;
		case 6203:
			random = Misc.random(2);

			if (random == 1) {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1211;
				npcs[i].endGfx = 1211;
			} else {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			}
			break;
		case 6222:
			random = Misc.random(4);

			if (random == 1) {
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1198;

			} else {
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1197;
			}
			break;
		case 6208:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1213;
			break;
		// sara npcs
		case 6247:

		case 2562: // sara
			random = Misc.random(1);
			if (random == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 1194;
				npcs[i].projectileId = -1;
			} else if (random == 1)
				npcs[i].attackType = 0;
			break;
		case 6248:
		case 2563: // star
			npcs[i].attackType = 0;
			break;
		case 6250:
		case 2564: // growler
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1203;
			break;
		case 6204:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1209;
			break;
		case 6252:
		case 2565: // bree
			npcs[i].attackType = 1;
			npcs[i].projectileId = 9;
			break;
		case 6263:
			npcs[i].attackType = 2;
			npcs[i].projectileId = 1203;
			npcs[i].endGfx = 1204;
			// npcs[i].startGfx = 1202;
			break;
		case 6265:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 1206;
			break;
		// pvp bosses
		case 1913:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				npcs[i].attackType = 0;
			else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 1211;
				npcs[i].projectileId = 288;
			}
		case 1914:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				npcs[i].attackType = 0;
			else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 1211;
				npcs[i].projectileId = 288;
			}
		case 1974:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				npcs[i].attackType = 0;
			else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 1211;
				npcs[i].projectileId = 288;
			}
		case 1977:
			random = Misc.random(2);
			if (random == 0 || random == 1)
				npcs[i].attackType = 0;
			else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = 1211;
				npcs[i].projectileId = 1200;
			}
			break;
		// bandos npcs
		case 2550:
		case 6260:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else {
				npcs[i].attackType = 1;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 1200;
			}
			break;
		case 6261:
			npcs[i].attackType = 0;
			break;

		case 2025:
			npcs[i].attackType = 2;
			int r = Misc.random(3);
			if (r == 0) {
				npcs[i].gfx100(158);
				npcs[i].projectileId = 159;
				npcs[i].endGfx = 160;
			}
			if (r == 1) {
				npcs[i].gfx100(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
				hitThrough = true;
			}
			if (r == 2) {
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			if (r == 3) {
				npcs[i].gfx100(155);
				npcs[i].projectileId = 156;
			}
			break;
		case 2881: // supreme
			npcs[i].attackType = 1;
			npcs[i].projectileId = 298;
			break;

		case 2882: // prime
			npcs[i].attackType = 2;
			npcs[i].projectileId = 162;
			npcs[i].endGfx = 477;
			break;
			
		case 2837:
			int r3 = Misc.random(5);
			if (r3 == 0) {
				npcs[i].attackType = 1;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
				// c.getPA().chaosElementalEffect(c, 1);
			} else if (r3 == 1) {
				npcs[i].attackType = 2;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
				// c.getPA().chaosElementalEffect(c, 0);
			} else {
				npcs[i].attackType = 0;
				npcs[i].gfx100(556);
				npcs[i].projectileId = 557;
				npcs[i].endGfx = 558;
			}
			break;
			
		case 2745:
            // TzTok-Jad
            if (goodDistance(npcs[i].absX, npcs[i].absY, PlayerHandler.players[npcs[i].spawnedBy].absX, PlayerHandler.players[npcs[i].spawnedBy].absY, 1)) 
            	random = Misc.random(2);
            else 
            	random = Misc.random(1);
            if (random == 0)
            {
                    npcs[i].attackType = 2;
                    npcs[i].endGfx = 157;
                    npcs[i].projectileId = 448;
            }
            else if (random == 1)
            {
                    npcs[i].attackType = 1;
                    npcs[i].endGfx = 451;
                    npcs[i].projectileId = -1;
            }
            else if (random == 2)
            {
                    npcs[i].attackType = 0;
                    npcs[i].projectileId = -1;
            }
            break;

		case 3200:
			int r2 = Misc.random(5);
			if (r2 == 0) {
				npcs[i].attackType = 1;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
				// c.getPA().chaosElementalEffect(c, 1);
			} else if (r2 == 1) {
				npcs[i].attackType = 2;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
				// c.getPA().chaosElementalEffect(c, 0);
			} else {
				npcs[i].attackType = 0;
				npcs[i].gfx100(556);
				npcs[i].projectileId = 557;
				npcs[i].endGfx = 558;
			}
			break;

		case 2631:
			npcs[i].attackType = 1;
			npcs[i].projectileId = 443;
			break;
		}
	}

	/**
	 * Distanced required to attack
	 **/
	public int distanceRequired(int i) {
		switch (npcs[i].npcType) {
		case 4154:
		case 4158:
		case 4173:
		case 4172:
		case 4001:
		case 4175:
			return 8;
		case 2025:
		case 2028:
			return 6;
		case 1158:
		case 1160:
			return 8;
		case 50:
		case 742:
		case 1592:
		case 2562:
		case 4176:
			return 2;
		case 2881: // dag kings
		case 2882:
		case 3200: // chaos ele
		case 2837:
		case 2743:
		case 2631:
		case 2745:
		case 3649: // halloween event npc, change this!
			return 8;
		case 2883: // rex
			return 1;
		case 6223:
		case 6227:
		case 6225:
			return 20;
		case 6259:
			return 15;
		case 2552:
		case 2553:
		case 2556:
		case 2557:
		case 2558:
		case 2559:
		case 2560:
		case 2564:
		case 2565:
		case 6265:
		case 6263:
		case 6252:
		case 6250:
		case 6248:
		case 6247:
		case 6208:
		case 6204:
			return 15;
			// things around dags
		case 2892:
		case 2894:
			return 10;
		default:
			return 1;
		}
	}

	public int followDistance(int i) {
		switch (npcs[i].npcType) {
		case 3732:
		case 3772:
		case 3762:
		case 3742:
		case 3752:
			return 15;
		case 6260:
		case 2551:
		case 2562:
		case 2563:
			return 8;
		case 2883:
			return 4;
		case 2881:
		case 2882:
			return 1;
		case 4173:
		case 4172:
			return 20;
		case 4176:
			return 10;
		}
		return 0;

	}

	public int getProjectileSpeed(int i) {
		switch (npcs[i].npcType) {
		case 1158:
		case 1160:
			return 90;
		case 2881:
		case 2882:
		case 3200:
			return 85;

		case 2745:
			return 130;

		case 50:
			return 90;

		case 2025:
			return 85;

		case 4173:
		case 4172:
			return 85;

		case 2028:
			return 80;

		default:
			return 85;
		}
	}

	/**
	 * NPC Attacking Player
	 **/

	private int[] portals = { 6142, 6143, 6144, 6145 };

	/**
	 * NPC Attacking Player
	 **/

	public void attackPlayer(Client c, int i) {
		if (npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0
					&& npcs[i].underAttackBy != c.playerId) {
				npcs[i].killerId = 0;
				return;
			}
			for (int po = 0; po < portals.length; po++) {
				if (npcs[i].npcType == portals[po]) {
					return;
				}
			}
			if (!npcs[i].inMulti()
					&& (c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != c.heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
			if (c.hasPin) {
				return;
			}
			if (npcs[i].npcType == 6142 || npcs[i].npcType == 6143
					|| npcs[i].npcType == 6144 || npcs[i].npcType == 6145
					|| npcs[i].npcType == 2440 || npcs[i].npcType == 2443
					|| npcs[i].npcType == 2446) {
				return;
			}
			boolean special = false;// specialCase(c,i);
			if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
					c.getY(), distanceRequired(i)) || special) {
				if (c.respawnTimer <= 0) {
					npcs[i].facePlayer(c.playerId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					if (special)
						loadSpell(c, i);
					// loadSpell2(i);
					else
						loadSpell(c, i);
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = c.playerId;
						// return;
					}
					if (npcs[i].projectileId > 0) {
						int nX = NPCHandler.npcs[i].getX() + offset(i);
						int nY = NPCHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY) * -1;
						int offY = (nX - pX) * -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY,
								50, getProjectileSpeed(i),
								npcs[i].projectileId, 43, 31, -c.getId() - 1,
								65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].oldIndex = c.playerId;
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
				}
			}
		}
	}

	public int offset(int i) {
		switch (npcs[i].npcType) {
		case 1158:
		case 1160:
			return 2;
		case 2881:
		case 2882:
			return 1;
		case 2745:
		case 8349:
		case 2743:
		case 8133:
		case 50:
			return 1;
		}
		return 0;
	}

	public boolean specialCase(Client c, int i) { // responsible for npcs that
													// much
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8)
				&& !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(),
						c.getY(), distanceRequired(i)))
			return true;
		return false;
	}

	public boolean retaliates(int npcType) {
		return npcType < 3777 || npcType > 3780
				&& !(npcType >= 6142 && npcType <= 6145)
				|| !(npcType >= 2440 && npcType <= 2446);
	}

	public void applyDamage(int i) {
		if (npcs[i] != null) {
			if (PlayerHandler.players[npcs[i].oldIndex] == null) {
				return;
			}
			if (npcs[i].isDead)
				return;
			Client c = (Client) PlayerHandler.players[npcs[i].oldIndex];
			if (multiAttacks(i)) {
				multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0)
				if (c.autoRet == 1)
					c.npcIndex = i;
			if (c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0
					&& c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}
			if (c.respawnTimer <= 0) {
				int damage = 0;
				if (npcs[i].attackType == 0) {
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateMeleeDefence()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (npcs[i].npcType != 4154 && npcs[i].npcType != 4158
							&& npcs[i].npcType != 4172
							&& npcs[i].npcType != 4001
							&& npcs[i].npcType != 4175
							&& npcs[i].npcType != 4176)
						hitThrough = false;
					if (c.prayerActive[18] && !hitThrough) { // protect from
																// melee
						damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
				}

				switch (npcs[i].attackType) {
				case 1:
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
					}
					if (npcs[i].npcType != 4154 && npcs[i].npcType != 4158
							&& npcs[i].npcType != 4172
							&& npcs[i].npcType != 4001
							&& npcs[i].npcType != 4175
							&& npcs[i].npcType != 4176)
						hitThrough = false;
					if (c.prayerActive[17] && !hitThrough) { // protect from
																// range
						damage = 0;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}

					break;
				}

				switch (npcs[i].attackType) {
				case 2: // magic
					damage = Misc.random(npcs[i].maxHit);
					boolean magicFailed = false;
					if (10 + Misc.random(c.getCombat().mageDef()) > Misc
							.random(NPCHandler.npcs[i].attack)) {
						damage = 0;
						magicFailed = true;
					}
					if (npcs[i].npcType != 4154 && npcs[i].npcType != 4158
							&& npcs[i].npcType != 4172
							&& npcs[i].npcType != 4001
							&& npcs[i].npcType != 4175
							&& npcs[i].npcType != 4176)
						hitThrough = false;
					if (c.prayerActive[16] && !hitThrough) { // protect from
																// magic
						damage = 0;
						magicFailed = true;
					}
					if (c.playerLevel[3] - damage < 0) {
						damage = c.playerLevel[3];
					}
					if (npcs[i].endGfx > 0
							&& (!magicFailed || isFightCaveNpc(i))) {
						c.gfx100(npcs[i].endGfx);
					} else {
						c.gfx100(85);
					}
					break;
				}

				switch (npcs[i].attackType) {
				case 3: // fire breath
					int anti = c.getPA().antiFire();
					switch (anti) {
					case 0:
						damage = Misc.random(30) + 10;
						c.sendMessage("You are badly burnt by the dragon fire!");
						break;
					case 1:
						if (c.getItems().playerHasEquipped(5, 11284)
								|| c.getItems().playerHasEquipped(5, 11283)) {
							c.startAnimation(6695);
							damage = npcs[i].npcType == 50
									|| npcs[i].npcType == 1592 ? Misc
									.random(10) : Misc.random(5);
							c.sendMessage("Your Dragonfire Shield absorbs most the damage");
						} else if (c.getItems().playerHasEquipped(5, 1540)) {
							damage = Misc.random(13);
							c.sendMessage("Your shield protects you from the fire.");
						}
						break;
					case 2:
						damage = Misc.random(5);
						break;
					}
				}
				handleSpecialEffects(c, i, damage);
				c.logoutDelay = System.currentTimeMillis(); // logout delay
				// c.setHitDiff(damage);
				c.handleHitMask(damage);
				c.playerLevel[3] -= damage;
				// /FightCaves.tzKihEffect(c, i, damage);
				c.getPA().refreshSkill(3);
				c.updateRequired = true;
				// c.setHitUpdateRequired(true);
			}
		}
	}

	public void handleSpecialEffects(Client c, int i, int damage) {
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPA().refreshSkill(5);
						c.getPA().appendPoison(12);
					}
				}
			}
		}

	}

	public static void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}

	/*public boolean goodDistance(int objectX, int objectY, int playerX,
			int playerY, int distance) {
		return ((objectX - playerX <= distance && objectX - playerX >= -distance) && (objectY
				- playerY <= distance && objectY - playerY >= -distance));
	}*/
	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX-playerX <= distance && objectX-playerX >= -distance) && (objectY-playerY <= distance && objectY-playerY >= -distance));
	}

	public int getMaxHit(int i) {
		switch (npcs[i].npcType) {
		case 6222:
			if (npcs[i].attackType == 1)
				return 50;
			else
				return 23;
		case 6203:
			if (npcs[i].attackType == 2)
				return 25;
			else
				return 48;
		case 4173:
		case 4172:
		case 4001:
		case 4175:
			return 45;
		case 6260:
			if (npcs[i].attackType == 0)
				return 30;
			else
				return 30;
		case 4154:
			return 30;
		case 2558:
			if (npcs[i].attackType == 2)
				return 28;
			else
				return 68;
		case 3070:
			if (npcs[i].attackType == 3)
				return 23;
		case 2562:
			return 31;
		case 2550:
			return 36;
		case 1913:
			return 70;
		case 1914:
			return 70;
		case 1977:
			return 70;
		case 1974:
			return 70;
		}
		return 1;
	}

	@SuppressWarnings("resource")
	public boolean loadAutoSpawn(String FileName) throws IOException {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			characterfile.close();
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]),
							Integer.parseInt(token3[1]),
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]),
							Integer.parseInt(token3[4]),
							getNpcListHP(Integer.parseInt(token3[0])),
							Integer.parseInt(token3[5]),
							Integer.parseInt(token3[6]),
							Integer.parseInt(token3[7]));

				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public int getNpcListHP(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcHealth;
				}
			}
		}
		return 0;
	}

	public static String getNpcListName(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcName;
				}
			}
		}
		return "nothing";
	}

	@SuppressWarnings("resource")
	public boolean loadNPCList(String FileName) throws IOException {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./" + FileName));
		} catch (FileNotFoundException fileex) {
			Misc.println(FileName + ": file not found.");
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(FileName + ": error loading file.");
			characterfile.close();
			return false;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1],
							Integer.parseInt(token3[2]),
							Integer.parseInt(token3[3]));
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ioexception) {
		}
		return false;
	}

	public NPC[] getNPCs() {
		return npcs;
	}

}
