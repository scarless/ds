package server.game.players.saving;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.util.Misc;

public class PlayerSave {

	/**
	 * Tells us whether or not the player exists for the specified name.
	 * 
	 * @param name
	 * @return
	 */
	public static boolean playerExists(String name) {
		File file = new File("./Data/characters/" + name + ".txt");
		return file.exists();
	}

	/**
	 * Tells use whether or not the specified name has the friend added.
	 * 
	 * @param name
	 * @param friend
	 * @return
	 */
	public static boolean isFriend(String name, String friend) {
		long nameLong = Misc.playerNameToInt64(friend);
		long[] friends = getFriends(name);
		if (friends != null && friends.length > 0) {
			for (int index = 0; index < friends.length; index++) {
				if (friends[index] == nameLong) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns a characters friends in the form of a long array.
	 * 
	 * @param name
	 * @return
	 */
	public static long[] getFriends(String name) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean end = false;
		int readMode = 0;
		BufferedReader file = null;
		boolean file1 = false;
		long[] readFriends = new long[200];
		long[] friends = null;
		int totalFriends = 0;
		try {
			file = new BufferedReader(new FileReader("./Data/characters/"
					+ name + ".txt"));
			file1 = true;
		} catch (FileNotFoundException fileex1) {
		}

		if (file1) {
			new File("./Data/characters/" + name + ".txt");
		} else {
			return null;
		}
		try {
			line = file.readLine();
		} catch (IOException ioexception) {
			return null;
		}
		while (end == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (readMode) {
				case 0:
					if (token.equals("character-friend")) {
						readFriends[Integer.parseInt(token3[0])] = Long
								.parseLong(token3[1]);
						totalFriends++;
					}
					break;
				}
			} else {
				if (line.equals("[FRIENDS]")) {
					readMode = 0;
				} else if (line.equals("[EOF]")) {
					try {
						file.close();
					} catch (IOException ioexception) {
					}
				}
			}
			try {
				line = file.readLine();
			} catch (IOException ioexception1) {
				end = true;
			}
		}
		try {
			if (totalFriends > 0) {
				friends = new long[totalFriends];
				for (int index = 0; index < totalFriends; index++) {
					friends[index] = readFriends[index];
				}
				return friends;
			}
			file.close();
		} catch (IOException ioexception) {
		}
		return null;
	}

	/**
	 * Loading
	 **/
	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;
		try {
			characterfile = new BufferedReader(new FileReader(
					"./Data/characters/" + playerName + ".txt"));
			File1 = true;
		} catch (FileNotFoundException fileex1) {
		}
		if (playerExists(Misc.removeSpaces(playerName))
				&& !playerExists(playerName)) {
			p.disconnected = true;
			return 3;
		}

		if (File1) {
			// new File ("./characters/"+playerName+".txt");
		} else {
			Misc.println(playerName + ": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(playerName + ": error loading file.");
			return 3;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
				case 1:
					if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2)
								|| Misc.basicEncrypt(playerPass).equals(token2)) {
							playerPass = token2;
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3087
								: Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3504
								: Integer.parseInt(token2));
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if (token.equals("character-title")) {
						p.playerTitle = token2;
					} else if (token.equals("firstIP")){
						p.firstIP = token2;
					} else if (token.equals("lastIP")){
						p.lastIP = token2;
					} else if (token.equals("joinTime")) {
						p.joinTime = Long.parseLong(token2);	
					} else if (token.equals("CurrentStreak")) {
						p.cStreak = Integer.parseInt(token2);
				    }  else if (token.equals("HighestStreak")) {
						p.hStreak = Integer.parseInt(token2);
					} else if (token.equals("character-title-color")) {
						p.titleColor = Integer.parseInt(token2);
					} else if (token.equals("killed-players")) {
						p.lastKilledPlayers.add(token2);
					} else if (token.equals("warnings")) {
						p.warnings = Integer.parseInt(token2);
					/*} else if (token.equals("prestigeLevel")) {
						p.prestigeLevel = Integer.parseInt(token2);
					} else if (token.equals("prestigePoints")) {
						p.prestigePoints = Integer.parseInt(token2);*/
					} else if (token.equals("connected-from")) {
						p.lastConnectedFrom.add(token2);
					} else if (token.equals("Jailed")) {
						p.Jail = Boolean.parseBoolean(token2);
					} else if (token.equals("isTraining")) {
						p.isTraining = Boolean.parseBoolean(token2);
					} else if (token.equals("mediumPouchDecay")) {
						p.mediumPouchDecay = Integer.parseInt(token2);
					} else if (token.equals("largePouchDecay")) {
						p.largePouchDecay = Integer.parseInt(token2);
					} else if (token.equals("giantPouchDecay")) {
						p.giantPouchDecay = Integer.parseInt(token2);
                    } else if (token.equals("chosenstarter")) {
                        p.chosenstarter = Boolean.parseBoolean(token2);
					} else if (token.equals("votingPoints")){
						p.votingPoints = Integer.parseInt(token2);
					} else if (token.equals("lastTargetName"))  {
	                    p.lastTargetName = token2;
				    } else if (token.equals("cantGetKills")) {
	                    p.cantGetKills = Integer.parseInt(token2);    
			        } else if (token.equals("cantGetKillsTimer")) {
	                    p.cantGetKillsTimer = Integer.parseInt(token2);
		            } else if (token.equals("killsThisMinute"))  {
	                   p.killsThisMinute = Integer.parseInt(token2);
	                } else if (token.equals("killsThisMinuteTimer")) {
	                   p.killsThisMinuteTimer = Integer.parseInt(token2);
	                    
				//-----------------------------------------------//
					}else if (token.equals("pinAttempts")) {
							p.pinAttempts = Integer.parseInt(token2);
			         } else if (token.equals("lockoutTime")){
							p.lockoutTime = Integer.parseInt(token2);
					} else if (token.equals("dayofweek")) {
						p.dayofweek = Integer.parseInt(token2);
					} else if (token.equals("dailygamble")) {
						p.dailygamble = Integer.parseInt(token2);
					} else if (token.equals("monster-points")) {
						p.monsterPoints = Integer.parseInt(token2);
					} else if (token.equals("lastKilled")) {
					    p.lastKilled = token2;
					} else if (token.equals("startPack")) {
						p.startPack = Boolean.parseBoolean(token2);
					} else if (token.equals("checkInv")) {
						p.checkInv = Boolean.parseBoolean(token2);
					} else if (token.equals("openDuel")) {
						p.openDuel = Boolean.parseBoolean(token2);
					} else if (token.equals("lastLoginDate")) {
						p.lastLoginDate = Integer.parseInt(token2);
					} else if (token.equals("setPin")) {
						p.setPin = Boolean.parseBoolean(token2);
					} else if (token.equals("hasBankpin")) {
						p.hasBankpin = Boolean.parseBoolean(token2);
					} else if (token.equals("redSkull")) {
						p.redSkull = Boolean.parseBoolean(token2);
					} else if (token.equals("pinRegisteredDeleteDay")) {
						p.pinDeleteDateRequested = Integer.parseInt(token2);
					} else if (token.equals("requestPinDelete")) {
						p.requestPinDelete = Boolean.parseBoolean(token2);
					} else if (token.equals("slayerTaskLevel")) {
						p.slayerTaskLevel = Integer.parseInt(token2);
					} else if (token.equals("slay-points")) {
						p.slaypoints = Integer.parseInt(token2);
					} else if (token.equals("tournament"))  {
                        p.tournament = Integer.parseInt(token2);
					} else if (token.equals("bankPin1")) {
						p.bankPin1 = Integer.parseInt(token2);
					} else if (token.equals("bankPin2")) {
						p.bankPin2 = Integer.parseInt(token2);
					} else if (token.equals("bankPin3")) {
						p.bankPin3 = Integer.parseInt(token2);
					} else if (token.equals("bankPin4")) {
						p.bankPin4 = Integer.parseInt(token2);
					} else if (token.equals("tutorial-progress")) {
						p.tutorial = Integer.parseInt(token2);
					} else if (token.equals("crystal-bow-shots")) {
						p.crystalBowArrowCount = Integer.parseInt(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("yellmute")) {
						p.yellmute = Integer.parseInt(token2);
					} else if (token.equals("character-yellTag")) {
						p.customYellTag = token2;
					} else if (token.equals("brother-info")) {
						p.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer
								.parseInt(token3[1]);
					}	else if (token.equals("alldp"))
						{
							p.alldp = Integer.parseInt(token2);
						}
					else if (token.equals("dp"))
						{
							p.dp = Integer.parseInt(token2);
						
					} else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);
					} else if (token.equals("selected-coffin")) {
						p.randomCoffin = Integer.parseInt(token2);
					} else if (token.equals("barrowsKillCount")) {
						p.barrowsKillCount = Integer.parseInt(token2);
					} else if (token.equals("play-time")) {
						p.pTime = Integer.parseInt(token2);
					} else if (token.equals("pkPoints")) {
						p.pkPoints = Integer.parseInt(token2);
					} else if (token.equals("donP")) {
						p.donPoints = Integer.parseInt(token2);
					} else if (token.equals("xpLock")) {
						
						p.expLock = Boolean.parseBoolean(token2);
					} 
					else if (token.equals("petID"))
                    {
                            p.petID = Integer.parseInt(token2);
                    }
                    else if (token.equals("petSummoned"))
                    {
                            p.setPetSummoned(Boolean.parseBoolean(token2));
                    } else if (token.equals("KC")) {
                        p.KC = Integer.parseInt(token2);
                    } else if (token.equals("DC")) {
                        p.DC = Integer.parseInt(token2);
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);
					} else if (token.equals("pc-points")) {
						p.pcPoints = Integer.parseInt(token2);
					 } else if (token.equals("aPoints")) {
						 p.aPoints = Integer.parseInt(token2);
					 } else if (token.equals("npc-kills")) {
						 p.npcKills = Integer.parseInt(token2); 
					 } else if (token.equals("boss-kills")) {
						 p.bossKills = Integer.parseInt(token2);
					 } else if (token.equals("bandosKills")) {
						 p.bandosKills = Integer.parseInt(token2);
					 } else if (token.equals("armadylKills")) {
						 p.armadylKills = Integer.parseInt(token2);
					 } else if (token.equals("saradominKills")) {
						 p.saradominKills = Integer.parseInt(token2);
					 } else if (token.equals("zamorakKills")) {
						 p.zamorakKills = Integer.parseInt(token2);
					 } else if (token.equals("dSupremeKills")) {
						 p.dSupremeKills = Integer.parseInt(token2);
					 } else if (token.equals("dPrimeKills")) {
						 p.dPrimeKills = Integer.parseInt(token2);
					 } else if (token.equals("dRexKills")) {
						 p.dRexKills = Integer.parseInt(token2);
					 } else if (token.equals("scorpiaKills")) {
						 p.scorpiaKills = Integer.parseInt(token2);
					 } else if (token.equals("vetionKills")) {
						 p.vetionKills = Integer.parseInt(token2);
					 } else if (token.equals("venenatisKills")) {
						 p.venenatisKills = Integer.parseInt(token2);
					 } else if (token.equals("callistoKills")) {
						 p.callistoKills = Integer.parseInt(token2);
					 } else if (token.equals("chaosKills")) {
						 p.chaosKills = Integer.parseInt(token2);
					 } else if (token.equals("seaTrollQueenKills")) {
						 p.seaTrollQueenKills = Integer.parseInt(token2);
					 } else if (token.equals("seaSnakeKills")) {
						 p.seaSnakeKills = Integer.parseInt(token2);
					 } else if (token.equals("penanceQueenKills")) {
						 p.penanceQueenKills = Integer.parseInt(token2);
					 } else if (token.equals("darkBeastKills")) {
						 p.darkBeastKills = Integer.parseInt(token2);
					 } else if (token.equals("abbyDemonKills")) {
						 p.abbyDemonKills = Integer.parseInt(token2);
					 } else if (token.equals("rockcrabKills")) {
						 p.rockcrabKills = Integer.parseInt(token2);
					 } else if (token.equals("hillgiantKills")) {
						 p.hillgiantKills = Integer.parseInt(token2);
					 } else if (token.equals("skeletonKills")) {
						 p.skeletonKills = Integer.parseInt(token2);
					 } else if (token.equals("ghostKills")) {
						 p.ghostKills = Integer.parseInt(token2);
					 } else if (token.equals("giantbatKills")) {
						 p.giantbatKills = Integer.parseInt(token2);
					 } else if (token.equals("chaosdwarfKills")) {
						 p.chaosdwarfKills = Integer.parseInt(token2);
					 } else if (token.equals("chaosdruidKills")) {
						 p.chaosdruidKills = Integer.parseInt(token2);
					 } else if (token.equals("lesserdemonKills")) {
						 p.lesserdemonKills = Integer.parseInt(token2);
					 } else if (token.equals("bansheeKills")) {
						 p.bansheeKills = Integer.parseInt(token2);
					 } else if (token.equals("infernalmageKills")) {
						 p.infernalmageKills = Integer.parseInt(token2);
					 } else if (token.equals("bloodveldKills")) {
						 p.bloodveldKills = Integer.parseInt(token2);
					 } else if (token.equals("gargoyleKills")) {
						 p.gargoyleKills = Integer.parseInt(token2);
					 } else if (token.equals("nechryaelKills")) {
						 p.nechryaelKills = Integer.parseInt(token2);
					 } else if (token.equals("blackdemonKills")) {
						 p.blackdemonKills = Integer.parseInt(token2);
					 } else if (token.equals("greendragonKills")) {
						 p.greendragonKills = Integer.parseInt(token2);
					 } else if (token.equals("bluedragonKills")) {
						 p.bluedragonKills = Integer.parseInt(token2);
					 } else if (token.equals("babybluedragonKills")) {
						 p.babybluedragonKills = Integer.parseInt(token2);
					 } else if (token.equals("bronzedragonkills")) {
						 p.bronzedragonKills = Integer.parseInt(token2);
					 } else if (token.equals("irondragonKills")) {
						 p.irondragonKills = Integer.parseInt(token2);
					 } else if (token.equals("steeldragonKills")) {
						 p.steeldragonKills = Integer.parseInt(token2);
					/**
					 * Valiant - Achievements
					 */
					} else if (line.startsWith("crabsKilled")) {
						p.crabsKilled = Integer.parseInt(token2);
					} else if (line.startsWith("fireslit")) {
						p.fireslit = Integer.parseInt(token2);
					} else if (token.equals("mute-end")) {
						p.muteEnd = Long.parseLong(token2);
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);
					} else if (token.equals("slayerPoints")) {
						p.slayerPoints = Integer.parseInt(token2);
					} else if (token.equals("taskAmount")) {
						p.taskAmount = Integer.parseInt(token2);
					}	else if (token.equals("meleePoints")) {
							p.meleePoints = Integer.parseInt(token2);
				    } else if (token.equals("magePoints")) {
							p.magePoints = Integer.parseInt(token2);
			        } else if (token.equals("rangePoints")) {
							p.rangePoints = Integer.parseInt(token2);
		            } else if (token.equals("challengePoints")) {
							p.challengePoints = Integer.parseInt(token2);
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);
					} else if (token.equals("barrowskillcount")) {
						p.barrowsKillCount = Integer.parseInt(token2);
					} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("removedTask0")) {
						p.removedTasks[0] = Integer.parseInt(token2);
					} else if (token.equals("removedTask1")) {
						p.removedTasks[1] = Integer.parseInt(token2);
					} else if (token.equals("removedTask2")) {
						p.removedTasks[2] = Integer.parseInt(token2);
					} else if (token.equals("removedTask3")) {
						p.removedTasks[3] = Integer.parseInt(token2);
					} else if (token.equals("wave")) {
						p.waveId = Integer.parseInt(token2);
					} else if (token.equals("void")) {
						for (int j = 0; j < token3.length; j++) {
							p.voidStatus[j] = Integer.parseInt(token3[j]);
						}
					} else if (token.equals("gwkc")) {
						p.killCount = Integer.parseInt(token2);
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[1]);
					}
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.bankItems[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[1]);
						p.bankItemsN[Integer.parseInt(token3[0])] = Integer
								.parseInt(token3[2]);
					}
					break;
				case 8:
					if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long
								.parseLong(token3[1]);
					}
					break;
				case 9:
					/*
					 * if (token.equals("character-ignore")) {
					 * ignores[Integer.parseInt(token3[0])] =
					 * Long.parseLong(token3[1]); }
					 */
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {
					ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {
					ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {
					ReadMode = 3;
				} else if (line.equals("[LOOK]")) {
					ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {
					ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {
					ReadMode = 6;
				} else if (line.equals("[BANK]")) {
					ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {
					ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {
					ReadMode = 9;
				} else if (line.equals("[EOF]")) {
					try {
						characterfile.close();
					} catch (IOException ioexception) {
					}
					return 1;
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
		return 13;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Client p) {
		if (!p.saveFile || p.newPlayer || !p.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (p.playerName == null || PlayerHandler.players[p.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int) (p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if (tbTime > 300000 || tbTime < 0) {
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter(
					"./Data/characters/" + p.playerName + ".txt"));

			/* ACCOUNT */
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);
			characterfile.write(p.playerPass, 0, p.playerPass.length());
			characterfile.newLine();
			characterfile.newLine();

			/* CHARACTER */
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer
					.toString(p.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0,
					Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0,
					Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer
					.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("firstIP = ", 0, 10);
			characterfile.write(p.firstIP, 0, p.firstIP.length());
			characterfile.newLine();
			characterfile.write("lastIP = ", 0, 9);
			characterfile.write(p.lastIP, 0, p.lastIP.length());
			characterfile.newLine();
			characterfile.write("joinTime = ", 0, 11);
			characterfile.write(Long.toString(p.joinTime), 0, Long.toString(p.joinTime).length());
			characterfile.newLine();
			characterfile.write("character-title = ", 0, 18);
			characterfile.write(p.playerTitle, 0, p.playerTitle.length());
			characterfile.newLine();
			characterfile.write("character-title-color = ", 0, 24);
			characterfile.write(Integer.toString(p.titleColor), 0, Integer
					.toString(p.titleColor).length());
			characterfile.newLine();
			for (int i = 0; i < p.lastConnectedFrom.size(); i++) {
				characterfile.write("connected-from = ", 0, 17);
				characterfile.write(p.lastConnectedFrom.get(i), 0,
						p.lastConnectedFrom.get(i).length());
				characterfile.newLine();
			}
			for (int j = 0; j < p.lastKilledPlayers.size(); j++) {
				characterfile.write("killed-players = ", 0, 17);
				characterfile.write(p.lastKilledPlayers.get(j), 0,
						p.lastKilledPlayers.get(j).length());
				characterfile.newLine();
			}
			for (int i = 0; i < p.removedTasks.length; i++) {
				characterfile.write("removedTask" + i + " = ", 0, 15);
				characterfile.write(Integer.toString(p.removedTasks[i]), 0,
						Integer.toString(p.removedTasks[i]).length());
				characterfile.newLine();
			}
			// start quests
			characterfile.write("pinAttempts = ", 0, 14);
			characterfile.write(Integer.toString(p.pinAttempts), 0, Integer
					.toString(p.pinAttempts).length());
			characterfile.newLine();
			characterfile.write("lockoutTime = ", 0, 14);
			characterfile.write(Integer.toString(p.lockoutTime), 0, Integer
					.toString(p.lockoutTime).length());
			characterfile.newLine();
			characterfile.write("warnings = ", 0, 11);
			characterfile.write(Integer.toString(p.warnings), 0, Integer
					.toString(p.warnings).length());
			characterfile.newLine();
			characterfile.write("isTraining = ", 0, 13);
			characterfile.write(Boolean.toString(p.isTraining), 0, Boolean.toString(p.isTraining).length());
			characterfile.newLine();
            characterfile.write("chosenstarter = ", 0, 16);
            characterfile.write(Boolean.toString(p.chosenstarter), 0, Boolean.toString(p.chosenstarter).length());
			characterfile.newLine();
			/*characterfile.write("prestigeLevel = ", 0, 16);
			characterfile.write(Integer.toString(p.prestigeLevel), 0, Integer
			.toString(p.prestigeLevel).length());
			characterfile.newLine();
			characterfile.write("prestigePoints = ", 0, 17);
			characterfile.write(Integer.toString(p.prestigePoints), 0, Integer
			.toString(p.prestigePoints).length());
			characterfile.newLine();*/
			characterfile.write("votingPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.votingPoints), 0, Integer.toString(p.votingPoints).length());
			characterfile.newLine();
			characterfile.write("mediumPouchDecay = ", 0, 19);
			characterfile.write(Integer.toString(p.mediumPouchDecay), 0, Integer.toString(p.mediumPouchDecay).length());
			characterfile.newLine();
			characterfile.write("largePouchDecay = ", 0, 18);
			characterfile.write(Integer.toString(p.largePouchDecay), 0, Integer.toString(p.largePouchDecay).length());
			characterfile.newLine();
			characterfile.write("giantPouchDecay = ", 0, 18);
			characterfile.write(Integer.toString(p.giantPouchDecay), 0, Integer.toString(p.giantPouchDecay).length());
			characterfile.newLine();
			characterfile.write("Jailed = ", 0, 9);
			characterfile.write(Boolean.toString(p.Jail), 0, Boolean.toString(p.Jail).length());
			characterfile.newLine();
			characterfile.write("cantGetKills = ", 0, 15);
			
	            characterfile.write(Integer.toString(p.cantGetKills), 0, Integer.toString(p.cantGetKills).length());
	            characterfile.newLine();
	            characterfile.write("cantGetKillsTimer = ", 0, 20);
	            characterfile.write(Integer.toString(p.cantGetKillsTimer), 0, Integer.toString(p.cantGetKillsTimer).length());
	            characterfile.newLine();
	            characterfile.write("killsThisMinute = ", 0, 18);
	            characterfile.write(Integer.toString(p.killsThisMinute), 0, Integer.toString(p.killsThisMinute).length());
	            characterfile.newLine();
	            characterfile.write("killsThisMinuteTimer = ", 0, 23);
	            characterfile.write(Integer.toString(p.killsThisMinuteTimer), 0, Integer.toString(p.killsThisMinuteTimer).length());
	            characterfile.newLine();
			characterfile.write("slay-points = ", 0, 14);
			characterfile.write(Integer.toString(p.slaypoints), 0, Integer
					.toString(p.slaypoints).length());
            characterfile.newLine();
            characterfile.write("tournament = ", 0, 13);
            characterfile.write(Integer.toString(p.tournament), 0, Integer.toString(p.tournament).length());
            characterfile.newLine();
			characterfile.write("CurrentStreak = ", 0, 16);
			characterfile.write(Integer.toString(p.cStreak), 0, Integer
					.toString(p.cStreak).length());
			characterfile.newLine();
			characterfile.write("HighestStreak = ", 0, 16);
			characterfile.write(Integer.toString(p.hStreak), 0, Integer
					.toString(p.hStreak).length());
			characterfile.newLine();
			characterfile.write("dayofweek = ", 0, 12);
			characterfile.write(Integer.toString(p.dayofweek), 0, Integer
					.toString(p.dayofweek).length());
			characterfile.newLine();
			characterfile.write("dailygamble = ", 0, 14);
			characterfile.write(Integer.toString(p.dailygamble), 0, Integer
					.toString(p.dailygamble).length());
			characterfile.newLine();
			characterfile.write("alldp = ", 0, 8);
			characterfile.write(Integer.toString(p.alldp), 0,
					Integer.toString(p.alldp).length());
			characterfile.newLine();
			characterfile.write("dp = ", 0, 5);
			characterfile.write(Integer.toString(p.dp), 0,
					Integer.toString(p.dp).length());
			characterfile.newLine();
			//-------------------------------------------------------//
			characterfile.write("monster-points = ", 0, 17);
			characterfile.write(Integer.toString(p.monsterPoints), 0, Integer
					.toString(p.monsterPoints).length());
			characterfile.newLine();
			characterfile.write("lastLoginDate = ", 0, 16);
			characterfile.write(Integer.toString(p.lastLoginDate), 0, Integer
					.toString(p.lastLoginDate).length());
			characterfile.newLine();
			characterfile.write("slayerTaskLevel = ", 0, 18);
			characterfile.write(Integer.toString(p.slayerTaskLevel), 0, Integer
					.toString(p.slayerTaskLevel).length());
			characterfile.newLine();
			characterfile.write("startPack = ", 0, 12);
			characterfile.write(Boolean.toString(p.startPack), 0, Boolean
					.toString(p.startPack).length());
			characterfile.write("checkInv = ", 0, 11);
			characterfile.write(Boolean.toString(p.checkInv), 0, Boolean
					.toString(p.checkInv).length());
			characterfile.newLine();
			characterfile.write("openDuel = ", 0, 11);
			characterfile.write(Boolean.toString(p.openDuel), 0, Boolean
					.toString(p.openDuel).length());
			characterfile.newLine();
			characterfile.write("petSummoned = ", 0, 14);
            characterfile.write(Boolean.toString(p.getPetSummoned()), 0, Boolean.toString(p.getPetSummoned()).length());
            characterfile.newLine();
            characterfile.write("petID = ", 0, 8);
            characterfile.write(Integer.toString(p.petID), 0, Integer.toString(p.petID).length());
            characterfile.newLine();
			characterfile.write("play-time = ", 0, 12);
			characterfile.write(Integer.toString(p.pTime), 0, Integer.toString(p.pTime).length());
			characterfile.newLine();
			characterfile.write("setPin = ", 0, 9);
			characterfile.write(Boolean.toString(p.setPin), 0, Boolean
					.toString(p.setPin).length());
			characterfile.newLine();
			characterfile.write("bankPin1 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin1), 0, Integer
					.toString(p.bankPin1).length());
			characterfile.newLine();
			characterfile.write("bankPin2 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin2), 0, Integer
					.toString(p.bankPin2).length());
			characterfile.newLine();
			characterfile.write("bankPin3 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin3), 0, Integer
					.toString(p.bankPin3).length());
			characterfile.newLine();
			characterfile.write("bankPin4 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin4), 0, Integer
					.toString(p.bankPin4).length());
			characterfile.newLine();
			characterfile.write("hasBankpin = ", 0, 13);
			characterfile.write(Boolean.toString(p.hasBankpin), 0, Boolean
					.toString(p.hasBankpin).length());
			characterfile.newLine();
			characterfile.write("pinRegisteredDeleteDay = ", 0, 25);
			characterfile.write(Integer.toString(p.pinDeleteDateRequested), 0,
					Integer.toString(p.pinDeleteDateRequested).length());
			characterfile.newLine();
			characterfile.write("requestPinDelete = ", 0, 19);
			characterfile.write(Boolean.toString(p.requestPinDelete), 0,
					Boolean.toString(p.requestPinDelete).length());
			characterfile.newLine();
			characterfile.write("slayerPoints = ", 0, 14);
			characterfile.write(Integer.toString(p.slayerPoints), 0, Integer
					.toString(p.slayerPoints).length());
			characterfile.newLine();
			characterfile.write("crystal-bow-shots = ", 0, 20);
			characterfile.write(Integer.toString(p.crystalBowArrowCount), 0,
					Integer.toString(p.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer
					.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer
					.toString(p.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("yellmute = ", 0, 11);
			characterfile.write(Integer.toString(p.yellmute), 0, Integer.toString(p.yellmute).length());
			characterfile.newLine();
			characterfile.write("character-yellTag = ", 0, 20);
			characterfile.write(p.customYellTag, 0, p.customYellTag.length());
			characterfile.newLine();
			for (int b = 0; b < p.barrowsNpcs.length; b++) {
				characterfile.write("brother-info = ", 0, 15);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(
						p.barrowsNpcs[b][1] <= 1 ? Integer.toString(0)
								: Integer.toString(p.barrowsNpcs[b][1]), 0,
						Integer.toString(p.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double
					.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(p.randomCoffin), 0, Integer
					.toString(p.randomCoffin).length());
			characterfile.newLine();
			/* PKing by eazy */
			characterfile.write("lastKilled = ", 0, 11);
			characterfile.write(p.lastKilled, 0, p.lastKilled.length());
            characterfile.newLine();
            characterfile.write("KC = ", 0, 5);
            characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
            characterfile.newLine();
            characterfile.write("DC = ", 0, 5);
            characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
            characterfile.newLine();
			characterfile.write("pkPoints = ", 0, 11);
			characterfile.write(Integer.toString(p.pkPoints), 0,
					Integer.toString(p.pkPoints).length());
			characterfile.newLine();
			characterfile.write("donP = ", 0, 6);
			characterfile.write(Integer.toString(p.donPoints), 0, Integer
					.toString(p.donPoints).length());
			characterfile.newLine();
			/* END PKing by eazy */
			characterfile.write("xpLock = ", 0, 9);
			characterfile.write(Boolean.toString(p.expLock), 0, Boolean
					.toString(p.expLock).length());
			characterfile.newLine();
			characterfile.write("barrows-killcount = ", 0, 20);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0,
					Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0,
					Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pcPoints), 0, Integer
					.toString(p.pcPoints).length());
			characterfile.newLine(); 
			characterfile.write("aPoints = ", 0, 10);
			characterfile.write(Integer.toString(p.aPoints), 0, Integer.toString(p.aPoints).length());
			characterfile.newLine();
			characterfile.write("npc-kills = ", 0, 12);
			characterfile.write(Integer.toString(p.npcKills), 0, Integer.toString(p.npcKills).length());
			characterfile.newLine();
			characterfile.write("boss-kills = ", 0, 13);
			characterfile.write(Integer.toString(p.bossKills), 0, Integer.toString(p.bossKills).length()); // <---- FUCKING GAY ERROR WAS IN THIS LINE!!!!!!!!!
			characterfile.newLine();
			characterfile.write("bandosKills = ", 0, 14);
			characterfile.write(Integer.toString(p.bandosKills), 0, Integer.toString(p.bandosKills).length());
			characterfile.newLine();
			characterfile.write("armadylKills = ", 0, 15);
			characterfile.write(Integer.toString(p.armadylKills), 0, Integer.toString(p.armadylKills).length());
			characterfile.newLine();
			characterfile.write("hillgiantKills = ", 0, 15);
			characterfile.write(Integer.toString(p.hillgiantKills), 0, Integer.toString(p.hillgiantKills).length());
			characterfile.newLine();
			characterfile.write("zamorakKills = ", 0, 15);
			characterfile.write(Integer.toString(p.zamorakKills), 0, Integer.toString(p.zamorakKills).length());
			characterfile.newLine();
			characterfile.write("saradominKills = ", 0, 17);
			characterfile.write(Integer.toString(p.saradominKills), 0, Integer.toString(p.saradominKills).length());
			characterfile.newLine();
			characterfile.write("dSupremeKills = ", 0, 16);
			characterfile.write(Integer.toString(p.dSupremeKills), 0, Integer.toString(p.dSupremeKills).length());
			characterfile.newLine();
			characterfile.write("dPrimeKills = ", 0, 14);
			characterfile.write(Integer.toString(p.dPrimeKills), 0, Integer.toString(p.dPrimeKills).length());
			characterfile.newLine();
			characterfile.write("dRexKills = ", 0, 12);
			characterfile.write(Integer.toString(p.dRexKills), 0, Integer.toString(p.dRexKills).length());
			characterfile.newLine();
			characterfile.write("scorpiaKills = ", 0, 15);
			characterfile.write(Integer.toString(p.scorpiaKills), 0, Integer.toString(p.scorpiaKills).length());
			characterfile.newLine();
			characterfile.write("vetionKills = ", 0, 14);
			characterfile.write(Integer.toString(p.vetionKills), 0, Integer.toString(p.vetionKills).length());
			characterfile.newLine();
			characterfile.write("venenatisKills = ", 0, 17);
			characterfile.write(Integer.toString(p.venenatisKills), 0, Integer.toString(p.venenatisKills).length());
			characterfile.newLine();
			characterfile.write("callistoKills = ", 0, 16);
			characterfile.write(Integer.toString(p.callistoKills), 0, Integer.toString(p.callistoKills).length());
			characterfile.newLine();
			characterfile.write("chaosKills = ", 0, 13);
			characterfile.write(Integer.toString(p.chaosKills), 0, Integer.toString(p.chaosKills).length());
			characterfile.newLine();
			characterfile.write("seaTrollQueenKills = ", 0, 21);
			characterfile.write(Integer.toString(p.seaTrollQueenKills), 0, Integer.toString(p.seaTrollQueenKills).length());
			characterfile.newLine();
			characterfile.write("seaSnakeKills = ", 0, 16);
			characterfile.write(Integer.toString(p.seaSnakeKills), 0, Integer.toString(p.seaSnakeKills).length());
			characterfile.newLine();
			characterfile.write("penanceQueenKills = ", 0, 20);
			characterfile.write(Integer.toString(p.penanceQueenKills), 0, Integer.toString(p.penanceQueenKills).length());
			characterfile.newLine();
			characterfile.write("darkBeastKills = ", 0, 17);
			characterfile.write(Integer.toString(p.darkBeastKills), 0, Integer.toString(p.darkBeastKills).length());
			characterfile.newLine();
			characterfile.write("abbyDemonKills = ", 0, 17);
			characterfile.write(Integer.toString(p.abbyDemonKills), 0, Integer.toString(p.abbyDemonKills).length());
			characterfile.newLine();
			characterfile.write("rockcrabKills = ", 0, 16);
			characterfile.write(Integer.toString(p.rockcrabKills), 0, Integer.toString(p.rockcrabKills).length());
			characterfile.newLine();
			characterfile.write("ban-start = ", 0, 12);
			characterfile.write(Long.toString(p.banStart), 0,
					Long.toString(p.banStart).length());
			characterfile.newLine();

			characterfile.write("ban-end = ", 0, 10);
			characterfile.write(Long.toString(p.banEnd), 0,
					Long.toString(p.banEnd).length());
			characterfile.newLine();
			characterfile.write("mute-end = ", 0, 11);
			characterfile.write(Long.toString(p.muteEnd), 0,
					Long.toString(p.muteEnd).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(p.slayerTask), 0, Integer
					.toString(p.slayerTask).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(p.taskAmount), 0, Integer
					.toString(p.taskAmount).length());
			characterfile.newLine();
			characterfile.write("meleePoints = ", 0, 14); //funny af its already 14 thats right, i just start at the right and mash the left arrow.
			characterfile.write(Integer.toString(p.meleePoints), 0, Integer
					.toString(p.meleePoints).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13); //funny af its already 14 thats right, i just start at the right and mash the left arrow.
			characterfile.write(Integer.toString(p.magePoints), 0, Integer
					.toString(p.magePoints).length());
			characterfile.newLine();
			characterfile.write("rangePoints = ", 0, 14); //funny af its already 14 thats right, i just start at the right and mash the left arrow.
			characterfile.write(Integer.toString(p.rangePoints), 0, Integer
					.toString(p.rangePoints).length());
			characterfile.newLine();
			characterfile.write("challengePoints = ", 0, 18); //funny af its already 14 thats right, i just start at the right and mash the left arrow.
			characterfile.write(Integer.toString(p.challengePoints), 0, Integer
					.toString(p.challengePoints).length());
			characterfile.newLine();
			characterfile.write("redSkull = ", 0, 11);
			characterfile.write(Boolean.toString(p.redSkull), 0, Boolean.toString(p.redSkull).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer
					.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("barrowskillcount = ", 0, 19);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0,
					Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean
					.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("wave = ", 0, 7);
			characterfile.write(Integer.toString(p.waveId), 0, Integer
					.toString(p.waveId).length());
			characterfile.newLine();
			characterfile.write("gwkc = ", 0, 7);
			characterfile.write(Integer.toString(p.killCount), 0, Integer
					.toString(p.killCount).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer
					.toString(p.fightMode).length());
			characterfile.newLine();
			characterfile.write("void = ", 0, 7);
			String toWrite = p.voidStatus[0] + "\t" + p.voidStatus[1] + "\t"
					+ p.voidStatus[2] + "\t" + p.voidStatus[3] + "\t"
					+ p.voidStatus[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.write("firesLit = ", 0, 11);
			characterfile.write(Integer.toString(p.fireslit), 0, Integer
					.toString(p.fireslit).length());
			characterfile.newLine();
			characterfile.write("crabsKilled = ", 0, 14);
			characterfile.write(Integer.toString(p.crabsKilled), 0, Integer
					.toString(p.crabsKilled).length());
			characterfile.newLine();
			characterfile.newLine();

			/* EQUIPMENT */
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0,
						Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0,
						Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* LOOK */
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0,
						Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* SKILLS */
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0,
						Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer
						.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* ITEMS */
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0,
							Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0,
							Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* BANK */
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.bankItems.length; i++) {
				if (p.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItems[i]), 0,
							Integer.toString(p.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItemsN[i]), 0,
							Integer.toString(p.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* FRIENDS */
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* IGNORES */
			/*
			 * characterfile.write("[IGNORES]", 0, 9); characterfile.newLine();
			 * for (int i = 0; i < ignores.length; i++) { if (ignores[i] > 0) {
			 * characterfile.write("character-ignore = ", 0, 19);
			 * characterfile.write(Integer.toString(i), 0,
			 * Integer.toString(i).length()); characterfile.write("	", 0, 1);
			 * characterfile.write(Long.toString(ignores[i]), 0,
			 * Long.toString(ignores[i]).length()); characterfile.newLine(); } }
			 * characterfile.newLine();
			 */
			/* EOF */
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch (IOException ioexception) {
			Misc.println(p.playerName + ": error writing file.");
			return false;
		}
		return true;
	}

}