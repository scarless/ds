package server.game.players;

import server.Config;
import server.Server;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.npcs.NPCHandler;
import server.game.players.PlayerHandler;

/**
 * Walkable interfaces that are shown by entering a specific area.
 * 
 * @author MGT Madness, created on the 30-11-2013.
 */
public class InterfaceEvent {

	/**
	 * The class instance.
	 */
	private static final InterfaceEvent instance = new InterfaceEvent();

	/**
	 * Returns a visible encapsulation of the class instance.
	 * 
	 * @return The returned encapsulated instance.
	 */
	public final static InterfaceEvent getInstance() {
		return instance;
	}

	// does the wildy interface do this? nop i think i forgot to import pest
	// here

	/**
	 * Start the cycle event of showing interfaces that last for 10 seconds.
	 */
	public void startInterfaceEvent(final Client c) {
		c.extraTime = 50;
		if (c.isUsingInterfaceEvent) {
			return;
		}
		c.isUsingInterfaceEvent = true;
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				if (c.extraTime > 0) {
					// System.out.println("Looping for: " + c.playerName);
					c.extraTime--;
					if (c.playerLevel[0] > 118 || c.playerLevel[1] > 118
							|| c.playerLevel[2] > 118 || c.playerLevel[4] > 112
							|| c.playerLevel[6] > 104) {
						if (c.inWild()) {
							c.playerLevel[0] = 118;
							c.playerLevel[1] = 118;
							c.playerLevel[2] = 118;
							c.playerLevel[4] = 112;
							c.playerLevel[6] = 104;
							c.getPA().refreshSkill(0);
							c.getPA().refreshSkill(1);
							c.getPA().refreshSkill(2);
							c.getPA().refreshSkill(4);
							c.getPA().refreshSkill(6);
							// c.sendMessage("Your boosted combat levels have been drained.");
						}
					}
					c.getPA().sendFrame126("@or2@         Information", 29155);
					/*c.getPA().sendFrame126("@gre@  - @gre@General Information",
							29161);*/
					if (c.playerRights == 0) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @gre@Player", 29161);
					}
					if (c.playerRights == 1) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @blu@@cr0@Moderator", 29161);
					}
					if (c.playerRights == 2) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @yel@@cr1@Admin",
								29161);
					}

					if (c.playerRights == 3) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @red@@cr2@Owner", 29161);
					}
					if (c.playerRights == 4) {
						c.getPA()
								.sendFrame126(
										"@whi@-@or2@  Player Rank: @gre@@cr3@Premium",
										29161);
					}
					if (c.playerRights == 5) {
						c.getPA()
								.sendFrame126(
										"@whi@-@or2@  Player Rank: @red@@cr4@Sponsor",
										29161);
					}
					if (c.playerRights == 6) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @pur@@cr5@V.I.P", 29161);
					}
					if (c.playerRights == 7) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @blu@@cr6@Helper", 29161);
					}
					if (c.playerRights == 8) {
						c.getPA().sendFrame126(
								"@whi@-@or2@  Player Rank: @or1@@cr7@Veteran", 29161);
					}
					c.getPA().sendFrame126(
							"@whi@-@or2@  Players Online: @gre@"
									+ PlayerHandler.getPlayerCount() + "",
							29162);
					c.getPA().sendFrame126(
							"@whi@-@or2@  Time Online: @gre@" + c.pTime / 2 / 60
									+ " mins.", 29163);
					c.getPA().sendFrame126(
							"@whi@-@or2@  Exp Lock Status: @gre@" + c.expLock + ".",
							29164);
					c.getPA().sendFrame126("@whi@-@or2@  Donator Tokens: @gre@" + c.dp,
							29165);
					c.getPA().sendFrame126("@whi@-@or2@  Skilling Points: @gre@" + c.dp,
							663);
					c.getPA().sendFrame126(
							"@whi@-@or2@  Vote Points: @gre@" + c.votingPoints,
							29166);
					c.getPA().sendFrame126(
							"@whi@-@or2@  Pk Points: @gre@" + c.pkPoints, 29167);
					c.getPA()
							.sendFrame126(
									"@whi@-@or2@  Slayer Points: @gre@"
											+ c.slayerPoints + " ", 29168);
	
					c.getPA().sendFrame126("@whi@-@or2@  Achievement Log: @gre@[@gre@@whi@Click Here@gre@]", 29169);
					c.getPA().sendFrame126(
							"@whi@-@or2@  Boss Kill Log: @gre@[@gre@@whi@Click Here@gre@]",
							29170);
					c.getPA().sendFrame126("@whi@-@or2@  NPC Kill Log: @gre@[@gre@@whi@Click here@gre@]", 29171);
					c.getPA().sendFrame126("@whi@-@or2@  Player Kills: @gre@" + c.KC, 29172);
					//c.getPA().sendFrame126("@or2@ Command List: @gre@[@gre@Click Here@gre@]", 29174);
					//c.getPA().sendFrame126("", 29175);
					//c.getPA().sendFrame126(" ", 29176);

					if (c.inWild()) {
						int modY = c.absY > 6400 ? c.absY - 6400 : c.absY;
						c.wildLevel = (((modY - 3520) / 8) + 1);
						c.getPA().walkableInterface(197);
						if (Config.SINGLE_AND_MULTI_ZONES) {
							if (c.inMulti()) {
								int value = c.combatLevel + c.wildLevel;
								if (value > 126) {
									value = 126;
								}
								c.getPA().sendFrame126(
										"@yel@" + (c.combatLevel - c.wildLevel)
												+ "-" + value, 199);
							} else {
								int value = c.combatLevel + c.wildLevel;
								if (value > 126) {
									value = 126;
								}
								c.getPA().sendFrame126(
										"@yel@" + (c.combatLevel - c.wildLevel)
												+ "-" + value, 199);
							}
						} else {
							c.getPA().multiWay(-1);
							int value = c.combatLevel + c.wildLevel;
							if (value > 126) {
								value = 126;
							}
							c.getPA().sendFrame126(
									"@yel@" + (c.combatLevel - c.wildLevel)
											+ "-" + value, 199);
						}
						c.getPA().showOption(3, 0, "Attack", 1);
					} else if (!c.inWild() && c.safeTimer > 0) {
						c.getPA().walkableInterface(197);
						c.wildLevel = (5);
						c.getPA().showOption(3, 0, "Attack", 1);
						int kkk = (int) c.safeTimer / 2;
						c.getPA().sendFrame126("@red@" + kkk, 199);
					} else if (c.inFunPk()) {
						c.wildLevel = (123);
						c.getPA().showOption(3, 0, "Attack", 1);
						c.getPA().sendFrame126("@red@Multi", 199);
						c.getPA().walkableInterface(197);
					} else if (c.isInHighRiskPK()) {
						int higher = c.combatLevel + c.wildLevel;
						int lower = c.combatLevel - c.wildLevel;
						if (higher > 126) {
							higher = 126;
						}
						if (lower < 3) {
							lower = 3;
						}
						c.wildLevel = (20);
						c.getPA().showOption(3, 0, "Attack", 1);
						c.getPA().sendFrame126(
								"@or2@" + lower + " - " + higher, 199);
						c.getPA().walkableInterface(197);
						/*
						 * } else if (c.varrockPK()) { c.wildLevel = 10;
						 * c.getPA().showOption(3, 0, "Attack", 1); int value =
						 * c.combatLevel + c.wildLevel; if (value > 126) { value
						 * = 126; } c.getPA().sendFrame126("@yel@" +
						 * (c.combatLevel - c.wildLevel) +"-" + value, 199);
						 * c.getPA().walkableInterface(197);
						 */
						/*
						 * } else if (c.lummyPK()) { c.wildLevel = 6;
						 * c.getPA().showOption(3, 0, "Attack", 1); int value =
						 * c.combatLevel + c.wildLevel; if (value > 126) { value
						 * = 126; } c.getPA().sendFrame126("@yel@" +
						 * (c.combatLevel - c.wildLevel) +"-" + value, 199);
						 * c.getPA().walkableInterface(197);
						 */
					} else if (c.inPcBoat()) {
						c.getPA().walkableInterface(21119);
					} else if (c.inPcGame()) {
						c.getPA().walkableInterface(21100);
						/*
						 * } else if (c.inDuelArena()) {
						 * c.getPA().walkableInterface(201); if (c.duelStatus ==
						 * 5) { c.getPA().showOption(3, 0, "Attack", 1); } else
						 * { c.getPA().showOption(3, 0, "Challenge", 1); }
						 */
					} else if (c.inCwGame || c.inPits) {
						c.getPA().showOption(3, 0, "Attack", 1);
					} else if (c.getPA().inPitsWait()) {
						c.getPA().showOption(3, 0, "Null", 1);
					} else if (!c.inCwWait) {
						c.getPA().sendFrame99(0);
						c.getPA().walkableInterface(-1);
						c.getPA().showOption(3, 0, "Null", 1);
					}

					if (!c.hasMultiSign && c.inMulti()) {
						c.hasMultiSign = true;
						c.getPA().multiWay(1);
					}

					if (c.hasMultiSign && !c.inMulti()) {
						c.hasMultiSign = false;
						c.getPA().multiWay(-1);
					}
				}
				if (c.extraTime <= 0) {
					container.stop();
				}
			}

			@Override
			public void stop() {
				c.isUsingInterfaceEvent = false;
			}
		}, 1);
	}

}
