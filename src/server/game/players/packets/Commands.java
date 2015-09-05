//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package server.game.players.packets;

import java.util.LinkedList;

import server.Connection;
import server.Server;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;

public class Commands implements PacketType {
    public Commands() {
    }
    
    /*
     * Beta version: Purpose of this method is to eradicate errors from 
     * incorrectly typing usernames. This method is a feature that you 
     * may use to match the start of a username. 
     * Examples:	jo$1 will pick the second user online whose name
     * 				starts with jo.
     * 				jo   will search for a user named "jo" and if none are
     * 				found it returns a list of users whose names start
     * 				with jo. To use one of them with a wild card type jo$0
     * 				to select the first one.
     * If a user logs on with a matching prefix to their name, there may be
     * an error in the index for some users. 
     */
    public Client getPlayer(Client c,String query) {
    	if (query.contains("$")) {
    		String sub = query.substring(0,query.indexOf("$"));
    		int index = Integer.parseInt(query.substring(query.indexOf("$")+1));
    		
    		LinkedList<String> matches = new LinkedList<>();
        	for (int i = 0; i < PlayerHandler.players.length;i++) {
        		if (PlayerHandler.players[i] != null) {
        			if (sub.equalsIgnoreCase(PlayerHandler.players[i].playerName)) {
        				matches.add(PlayerHandler.players[i].playerName);
        			} else if (PlayerHandler.players[i] != null 
        					&& PlayerHandler.players[i].playerName != null 
        					&& PlayerHandler.players[i].playerName.startsWith(query)) {
        				matches.add(PlayerHandler.players[i].playerName);
        			}
        		}
        	}
        	if (matches.size() <= index) {
        		String output = String.format("Index %i does not exist for query \"%s\"", index,query);
        		c.sendMessage(matches.toString());
        		c.sendMessage(output);
        		return null;
        	} else {
        		return (Client)PlayerHandler.getPlayer(matches.get(index));
        	}
        	
    	}
    	LinkedList<String> matches = new LinkedList<>();
    	for (int i = 0; i < PlayerHandler.players.length;i++) {
    		if (PlayerHandler.players[i] != null) {
    			if (query.equalsIgnoreCase(PlayerHandler.players[i].playerName)) {
    				return (Client)PlayerHandler.players[i];
    			} else if (PlayerHandler.players[i] != null 
    					&& PlayerHandler.players[i].playerName != null 
    					&& PlayerHandler.players[i].playerName.startsWith(query)) {
    				matches.add(PlayerHandler.players[i].playerName);
    			}
    		}
    	}
    	String output = String.format("%s", query,matches);
    	//it is possible to overflow the buffer, at apx 150 users there becomes an
    	//increased risk of overflowing the out buffer.
    	c.sendMessage(output);
    	//unknown width, mat need to have some words moved around.
    	c.sendMessage("Hint: adm$0 will get the first online user whose name");
    	c.sendMessage("starts with adm");
    	return null;
    }
    public void processPacket(Client c, int packetType, int packetSize) {
        String playerCommand = c.getInStream().readString();
        playerCommand = Misc.getFilteredInput(playerCommand);
        Misc.println(c.playerName + " playerCommand: " + playerCommand);
        String e;
        if(playerCommand.toLowerCase().startsWith("/")) {
            if(Connection.isMuted(c)) {
                c.sendMessage("You are muted for breaking a rule.");
                return;
            }

            if(c.clan != null) {
                e = playerCommand.substring(1);
                c.clan.sendChat(c, e);
            } else {
                c.sendMessage("You can only do this in a clan chat..");
            }
        }

        String i;
        int c2;
        if(playerCommand.toLowerCase().startsWith("yell") && c.playerRights >= 1) {
            e = "";
            i = playerCommand.substring(4);
            if(c.playerRights == 1) {
                e = "[@blu@Moderator@bla@] @cr1@" + c.playerName + ":@pur@";
            }

            if(c.playerRights == 2) {
                e = "[@or3@Administrator@bla@] @cr2@" + Misc.ucFirst(c.playerName) + ":@or3@";
            }

            if(c.playerRights == 3) {
                e = "[@red@Developer@bla@] @cr2@" + Misc.ucFirst(c.playerName) + ":@red@";
            }

            if(c.playerRights == 4) {
                e = "[@cr3@@gre@Premium@bla@] " + c.playerName + ":@dre@";
            }

            if(c.playerRights == 5) {
                e = "[@cr4@@red@Sponsor@bla@] " + c.playerName + ":@dre@";
            }

            if(c.playerRights == 6) {
                e = "[@cr5@@pur@V.I.P@bla@] " + c.playerName + ":@dre@";
            }

            if(c.playerRights == 7) {
                e = "[@cr6@@blu@Helper@bla@] " + c.playerName + ":@blu@";
            }

            if(c.playerRights == 8) {
                c.sendMessage("You must be a Donator or Staff member to use this command!");
                return;
            }

            if(Connection.isMuted(c)) {
                c.sendMessage("You are muted for breaking a rule.");
                return;
            }

            if(c.playerName.equalsIgnoreCase("Dark Bow")) {
                e = "[@or2@Web Developer@bla@] @cr2@" + c.playerName + ":@or2@";
            }

            if(c.playerName.equalsIgnoreCase("Ateeq")) {
                e = "[@red@Server Owner@bla@] @cr2@" + c.playerName + ":@red@";
            }

            for(c2 = 0; c2 < PlayerHandler.players.length; ++c2) {
                if(PlayerHandler.players[c2] != null) {
                    Client i1 = (Client)PlayerHandler.players[c2];
                    i1.sendMessage(e + i);
                }
            }
        }

        if(playerCommand.startsWith("bank") && c.playerName.equalsIgnoreCase("Kendal")) {
            if(c.inWild()) {
                c.sendMessage("You cant open bank in wilderness.");
                return;
            }

            if(c.duelStatus >= 1) {
                c.sendMessage("You cant open bank during a duel.");
                return;
            }

            if(c.inTrade) {
                c.sendMessage("You cant open bank during a trade.");
                return;
            }

            c.getPA().openUpBank();
        }

        byte var78;
        int var82;
        byte var83;
        if(playerCommand.startsWith("resetdef")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 1;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var27) {
                ;
            }
        }

        if(playerCommand.startsWith("resetatt")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 0;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var26) {
                ;
            }
        }

        if(playerCommand.startsWith("resetstr")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 2;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var25) {
                ;
            }
        }

        if(playerCommand.startsWith("resetpray")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 5;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var24) {
                ;
            }
        }

        if(playerCommand.startsWith("resetrange")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 4;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var23) {
                ;
            }
        }

        if(playerCommand.startsWith("resetmage")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 6;
                var78 = 1;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var22) {
                ;
            }
        }

        if(playerCommand.startsWith("resethp")) {
            if(c.inWild()) {
                return;
            }

            for(var82 = 0; var82 < c.playerEquipment.length; ++var82) {
                if(c.playerEquipment[var82] > 0) {
                    c.sendMessage("Please take all your armour and weapons off before using this command.");
                    return;
                }
            }

            try {
                var83 = 3;
                var78 = 10;
                c.playerXP[var83] = c.getPA().getXPForLevel(var78) + 5;
                c.playerLevel[var83] = c.getPA().getLevelForXP(c.playerXP[var83]);
                c.getPA().refreshSkill(var83);
            } catch (Exception var21) {
                ;
            }
        }

        int var81;
        String[] var87;
        if(playerCommand.startsWith("changetitle")) {
            if(c.playerRights == 0) {
                c.sendMessage("You must be a Donator or Staff member to use this command!");
                return;
            }

            if(!c.playerTitle.equals("")) {
                for(var82 = 0; var82 < c.badwords.length; ++var82) {
                    if(playerCommand.toLowerCase().contains(c.badwords[var82])) {
                        c.sendMessage("You cannot use this title.");
                        return;
                    }
                }
            }

            try {
                var87 = playerCommand.split("-");

                for(var81 = 0; var81 < c.badwords.length; ++var81) {
                    if(var87[1].toLowerCase().contains(c.badwords[var81])) {
                        c.sendMessage("You cannot use this title.");
                        return;
                    }
                }

                if(c.getItems().playerHasItem(995, 10000000)) {
                    c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 10000000);
                    c.playerTitle = var87[1];
                    System.out.println(var87[1]);
                    i = var87[2].toLowerCase();
                    if(i.equals("orange")) {
                        c.titleColor = 0;
                    }

                    if(i.equals("purple")) {
                        c.titleColor = 1;
                    }

                    if(i.equals("red")) {
                        c.titleColor = 2;
                    }

                    if(i.equals("green")) {
                        c.titleColor = 3;
                    }

                    c.sendMessage("You succesfully changed your title.");
                    c.updateRequired = true;
                    c.setAppearanceUpdateRequired(true);
                } else {
                    c.sendMessage("You need 10M coins to change your title.");
                }
            } catch (Exception var77) {
                c.sendMessage("Use as (::title-test-green)");
                c.sendMessage("Colors are orange, purple, red and green.");
            }
        }

        if(playerCommand.equalsIgnoreCase("removetitle")) {
            c.playerTitle = "";
            c.updateRequired = true;
            c.setAppearanceUpdateRequired(true);
            c.sendMessage("Your custom title is now removed.");
        } else {
            Client var79;
            String var80;
            if(playerCommand.equalsIgnoreCase("players")) {
                c.getPA().sendFrame126("Dragon-Scape - @red@Online Players", 8144);
                c.getPA().sendFrame126("@blu@Online players(" + PlayerHandler.getPlayerCount() + "):", 8145);
                short var88 = 8147;

                for(var81 = 1; var81 < 300; ++var81) {
                    var79 = c.getClient(var81);
                    if(c.validClient(var81) && var79.playerName != null) {
                        var80 = "";
                        if(var79.playerRights == 1) {
                            var80 = "@cr1@@blu@Mod, ";
                        } else if(var79.playerRights == 2) {
                            var80 = "@yel@Admin, ";
                        } else if(var79.playerRights == 3) {
                            var80 = "@cr2@@red@Owner, ";
                        } else if(var79.playerRights == 4) {
                            var80 = "@cr3@@gre@Premium, ";
                        } else if(var79.playerRights == 5) {
                            var80 = "@cr4@@red@Sponsor, ";
                        } else if(var79.playerRights == 6) {
                            var80 = "@cr5@@pur@V.I.P, ";
                        } else if(var79.playerRights == 7) {
                            var80 = "@cr6@@blu@Helper, ";
                        }

                        var80 = var80 + "level-" + var79.combatLevel;
                        String c21 = "";
                        if(c.playerRights > 0) {
                            c21 = "(" + var79.playerId + ") ";
                        }

                        c.getPA().sendFrame126("@dre@" + c21 + var79.playerName + " (" + var80 + ") @blu@ Kills: " + var79.KC + " ", var88);
                    }
                }

                c.getPA().showInterface(8134);
                c.flushOutStream();
            }

            if(playerCommand.toLowerCase().startsWith("task")) {
                StringBuilder var10001 = (new StringBuilder("You have to kill ")).append(c.taskAmount).append(" more ");
                NPCHandler var10002 = Server.npcHandler;
                c.sendMessage(var10001.append(NPCHandler.getNpcListName(c.slayerTask)).append("s.").toString());
            }

            if(playerCommand.equals("maxhit")) {
                c.sendMessage("Your current melee maxhit is: <col=ff0000>" + c.getCombat().calculateMeleeMaxHit());
            }

            if(playerCommand.equalsIgnoreCase("xplock")) {
                if(!c.expLock) {
                    c.expLock = true;
                    c.sendMessage("@blu@Your experience is now locked. You will not gain experience.");
                } else {
                    c.expLock = false;
                    c.sendMessage("@blu@Your experience is now unlocked. You will gain experience.");
                }
            }

            if(playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
                c.playerPass = Misc.getFilteredInput(playerCommand.substring(15));
                c.sendMessage("Your new password is: " + c.playerPass);
            }

            if(playerCommand.toLowerCase().startsWith("resettask") || playerCommand.equalsIgnoreCase("rt")) {
                c.taskAmount = -1;
                c.slayerTask = 0;
                c.sendMessage("Your slayer task has been reset sucessfully.");
                c.getPA().sendFrame126("@whi@Task: @gre@Empty", 7383);
            }

            if(playerCommand.equalsIgnoreCase("skull")) {
                c.isSkulled = true;
                c.skullTimer = 1200;
                c.headIconPk = 0;
                c.getPA().requestUpdates();
            }

            if(playerCommand.equalsIgnoreCase("empty") && !c.inWild()) {
                c.getDH().sendOption2("Yes, I want to empty my inventory items.", "Never mind.");
                c.dialogueAction = 162;
            }

            if(playerCommand.equalsIgnoreCase("commands")) {
                c.getPA().showInterface(8134);
                c.flushOutStream();
                c.getPA().sendFrame126("Commands List:", 8144);
                c.getPA().sendFrame126("", 8145);
                c.getPA().sendFrame126("@red@::players @blu@(playercount).", 8147);
                c.getPA().sendFrame126("@red@::edge @blu@(Teleports you to Edgeville).", 8148);
                c.getPA().sendFrame126("@red@::train@blu@ (Rock Crabs). ", 8149);
                c.getPA().sendFrame126("@red@::maxhit@blu@ (Your melee max hit).", 8150);
                c.getPA().sendFrame126("@red@::reset(stat name)@blu@ (pray,att,str,def.mage,range,hp)", 8151);
                c.getPA().sendFrame126("@red@::home @blu@(Teleports you to Home).", 8152);
                c.getPA().sendFrame126("@red@::skull @blu@(Skulls you).", 8153);
                c.getPA().sendFrame126("@red@::agility @blu@(Teleports you to Gnome Course).", 8154);
                c.getPA().sendFrame126("@red@::xplock@blu@ (Lock/Unlock your experience).", 8155);
                c.getPA().sendFrame126("@red@::forum@blu@ (Brings you to forums).", 8156);
                c.getPA().sendFrame126("@red@::vote@blu@ (Brings you to votepage).", 8157);
                c.getPA().sendFrame126("@red@::duel@blu@ (Teleports you to Duel arena).", 8158);
                c.getPA().sendFrame126("@red@::barrows@blu@ (Teleports you to barrows).", 8159);
                c.getPA().sendFrame126("@red@::tz@blu@ (Teleports you to Tzhaar Cave).", 8160);
                c.getPA().sendFrame126("@red@::shops@blu@ (Teleports you to the Shops area.).", 8161);
                c.getPA().sendFrame126("@red@::resetxp@blu@ (Resets your XP counter.).", 8162);
                c.getPA().sendFrame126("@red@::resettask @blu@ (Resets your slayer task.)", 8163);
                c.getPA().sendFrame126("@red@::doncommands @blu@ (Shows you all donor commands)", 8164);
                c.sendMessage("Player commands.");
            }

            if(playerCommand.equalsIgnoreCase("home")) {
                c.getPA().startTeleport(3087, 3504, 0, "modern");
            }

            if(playerCommand.equalsIgnoreCase("edge")) {
                c.getPA().startTeleport(3087, 3505, 0, "modern");
            }

            if(playerCommand.equalsIgnoreCase("train")) {
                c.getPA().startTeleport(2679, 3718, 0, "modern");
            }

            if(playerCommand.equalsIgnoreCase("agility")) {
                c.getPA().startTeleport(2480, 3435, 0, "modern");
            }

            if(playerCommand.equalsIgnoreCase("duel")) {
                c.getPA().startTeleport(3365, 3265, 0, "modern");
                c.sendMessage("@red@RECOMMENDING TO USE MM! YOU WILL BE NOT REFUNDED IF YOU DO NOT HAVE PROOF.");
            }

            if(playerCommand.equalsIgnoreCase("barrows")) {
                c.getPA().startTeleport(3565, 3305, 0, "modern");
            }

            if(c.playerRights >= 1 && c.playerRights <= 7 && playerCommand.equals("prezone")) {
                c.getPA().startTeleport(2587, 9426, 4, "modern");
            }

            if(playerCommand.equalsIgnoreCase("vipzone") && c.playerRights >= 6) {
                c.getPA().startTeleport(2337, 9799, 0, "modern");
            }

            Client var84;
            int var85;
            if(c.playerRights >= 1 && c.playerRights <= 3) {
                if(playerCommand.startsWith("checkbank") && c.playerRights >= 1 && c.playerRights <= 3) {
                    try {
                        var87 = playerCommand.split(" ", 2);

                        for(var81 = 0; var81 < 300; ++var81) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(var79 != null && var79.playerName.equalsIgnoreCase(var87[1])) {
                                c.getPA().otherBank(c, var79);
                                break;
                            }
                        }
                    } catch (Exception var76) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.equalsIgnoreCase("modcommands") && c.playerRights == 1) {
                    c.getPA().showInterface(8134);
                    c.getPA().sendFrame126("@mag@         ~ Moderator Commands ~", 8144);
                    c.getPA().sendFrame126(" ", 8145);
                    c.getPA().sendFrame126("@red@::yell", 8145);
                    c.getPA().sendFrame126("@red@::timedmute", 8147);
                    c.getPA().sendFrame126("@red@::unmute", 8148);
                    c.getPA().sendFrame126("@red@::ipmute", 8149);
                    c.getPA().sendFrame126("@red@::unipmute", 8150);
                    c.getPA().sendFrame126("@red@::jail", 8151);
                    c.getPA().sendFrame126("@red@::kick", 8152);
                    c.getPA().sendFrame126("@red@::afk", 8153);
                    c.getPA().sendFrame126("@red@::staffzone", 8154);
                    c.getPA().sendFrame126("@red@::xteleto (name)", 8155);
                    c.getPA().sendFrame126("@red@::xteletome (name)", 8156);
                    c.getPA().sendFrame126("@red@::checkinv (name)", 8157);
                }

                if(playerCommand.startsWith("checkinv") && c.playerRights == 1) {
                    try {
                        var87 = playerCommand.split(" ", 2);

                        for(var81 = 0; var81 < 300; ++var81) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(var87[1])) {
                                c.getPA().otherInv(c, var79);
                                c.getDH().sendDialogues(206, 0);
                                break;
                            }
                        }
                    } catch (Exception var75) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("kick")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                PlayerHandler.players[var81].disconnected = true;
                                var79.sendMessage("You got kicked by @blu@ " + c.playerName + ".");
                            }
                        }
                    } catch (Exception var74) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("mute")) {
                    try {
                        e = playerCommand.substring(5);
                        Connection.addNameToMuteList(e);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.sendMessage("You have been muted by: " + c.playerName);
                                break;
                            }
                        }
                    } catch (Exception var73) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unmute")) {
                    try {
                        e = playerCommand.substring(7);
                        Connection.unMuteUser(e);
                    } catch (Exception var20) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("jail")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3460;
                                var79.teleportToY = 9667;
                                var79.Jail = true;
                                var79.sendMessage("You have been jailed by " + c.playerName);
                                c.sendMessage("Successfully Jailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var72) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unjail")) {
                    try {
                        e = playerCommand.substring(7);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                if(var79.inWild()) {
                                    c.sendMessage("This player is in the wilderness, not in jail.");
                                    return;
                                }

                                if(var79.duelStatus == 5 || var79.inDuelArena()) {
                                    c.sendMessage("This player is during a duel, and not in jail.");
                                    return;
                                }

                                var79.teleportToX = 3093;
                                var79.teleportToY = 3493;
                                var79.Jail = false;
                                var79.sendMessage("You have been unjailed by " + c.playerName + ". You can now teleport.");
                                c.sendMessage("Successfully unjailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var71) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("timedmute") && c.playerRights >= 1 && c.playerRights <= 3) {
                    try {
                        var87 = playerCommand.split("-");
                        if(var87.length < 2) {
                            c.sendMessage("Currect usage: ::timedmute-playername-seconds");
                            return;
                        }

                        i = var87[1];
                        c2 = Integer.parseInt(var87[2]) * 1000;

                        for(var85 = 0; var85 < 300; ++var85) {
                            if(PlayerHandler.players[var85] != null && PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                var84 = (Client)PlayerHandler.players[var85];
                                var84.sendMessage("You have been muted by: " + c.playerName + " for " + c2 / 1000 + " seconds");
                                var84.muteEnd = System.currentTimeMillis() + (long)c2;
                                break;
                            }
                        }
                    } catch (Exception var70) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("teletome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = c.absX;
                                var79.teleportToY = c.absY;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to you.");
                                var79.sendMessage("You have been teleported to " + c.playerName);
                            }
                        }
                    } catch (Exception var69) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("xteleto")) {
                    e = playerCommand.substring(8);

                    for(var81 = 0; var81 < 300; ++var81) {
                        if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(c.duelStatus == 5) {
                                c.sendMessage("You cannot teleport to a player during a duel.");
                                return;
                            }

                            c.getPA().movePlayer(PlayerHandler.players[var81].getX(), PlayerHandler.players[var81].getY(), c.heightLevel);
                        }
                    }
                }

                if(playerCommand.startsWith("movehome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3096;
                                var79.teleportToY = 3468;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to home");
                                var79.sendMessage("You have been teleported to home");
                            }
                        }
                    } catch (Exception var68) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if((c.playerName.equalsIgnoreCase("xaxa") || c.playerName.equalsIgnoreCase("zaza") || c.playerName.equalsIgnoreCase("papa") || c.playerName.equalsIgnoreCase("baba") || c.playerName.equalsIgnoreCase("mama") || c.playerName.equalsIgnoreCase("haha") || c.playerName.equalsIgnoreCase("sasa")) && playerCommand.equals("tggzone")) {
                    c.getPA().startTeleport(2441, 3090, 0, "modern");
                }
            }

            Client var89;
            if(c.playerRights >= 2 && c.playerRights <= 3) {
                if(playerCommand.startsWith("ban")) {
                    try {
                        e = playerCommand.substring(4);
                        Connection.addNameToBanList(e);
                        Connection.addNameToFile(e);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].disconnected = true;
                            }
                        }
                    } catch (Exception var67) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unban")) {
                    try {
                        e = playerCommand.substring(6);
                        Connection.removeNameFromBanList(e);
                        c.sendMessage(e + " has been unbanned.");
                    } catch (Exception var19) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("ipban")) {
                    try {
                        e = playerCommand.substring(6);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                if(c.playerName == PlayerHandler.players[var81].playerName) {
                                    c.sendMessage("You cannot IP Ban yourself.");
                                } else if(!Connection.isIpBanned(PlayerHandler.players[var81].connectedFrom)) {
                                    Connection.addIpToBanList(PlayerHandler.players[var81].connectedFrom);
                                    Connection.addIpToFile(PlayerHandler.players[var81].connectedFrom);
                                    c.sendMessage("You have IP banned the user: " + PlayerHandler.players[var81].playerName + " with the host: " + PlayerHandler.players[var81].connectedFrom);
                                    PlayerHandler.players[var81].disconnected = true;
                                } else {
                                    c.sendMessage("This user is already IP Banned.");
                                }
                            }
                        }
                    } catch (Exception var66) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unipban")) {
                    e = playerCommand.substring(8);
                    Connection.removeIpFromBanList(e);
                }

                if(playerCommand.equals("alltome")) {
                    for(var82 = 0; var82 < PlayerHandler.players.length; ++var82) {
                        if(PlayerHandler.players[var82] != null) {
                            var89 = (Client)PlayerHandler.players[var82];
                            var89.teleportToX = c.absX;
                            var89.teleportToY = c.absY;
                            var89.heightLevel = c.heightLevel;
                            var89.sendMessage("Mass teleport to: " + c.playerName);
                        }
                    }
                }

                if(playerCommand.startsWith("pickup") && c.playerRights == 2) {
                    try {
                        var87 = playerCommand.split(" ");
                        if(var87.length == 3) {
                            var81 = Integer.parseInt(var87[1]);
                            c2 = Integer.parseInt(var87[2]);
                            if(var81 <= 20200 && var81 >= 0) {
                                c.getItems().addItem(var81, c2);
                            } else {
                                c.sendMessage("No such item.");
                            }
                        } else {
                            c.sendMessage("Use as ::pickup 995 200");
                        }
                    } catch (Exception var65) {
                        ;
                    }
                }

                if(playerCommand.startsWith("kick")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                if(var79.inWild()) {
                                    c.sendMessage("You cannot kick a player when he is in wilderness.");
                                    return;
                                }

                                if(var79.duelStatus == 5) {
                                    c.sendMessage("You cant kick a player while he is during a duel");
                                    return;
                                }

                                PlayerHandler.players[var81].disconnected = true;
                                var79.sendMessage("You got kicked by @blu@ " + c.playerName + ".");
                            }
                        }
                    } catch (Exception var64) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("checkinv") && c.playerRights == 1) {
                    try {
                        var87 = playerCommand.split(" ", 2);

                        for(var81 = 0; var81 < 300; ++var81) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(var87[1])) {
                                c.getPA().otherInv(c, var79);
                                c.getDH().sendDialogues(206, 0);
                                break;
                            }
                        }
                    } catch (Exception var63) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("mute")) {
                    try {
                        e = playerCommand.substring(5);
                        Connection.addNameToMuteList(e);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.sendMessage("You have been muted by: " + c.playerName);
                                break;
                            }
                        }
                    } catch (Exception var62) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unmute")) {
                    try {
                        e = playerCommand.substring(7);
                        Connection.unMuteUser(e);
                    } catch (Exception var18) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("jail")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3460;
                                var79.teleportToY = 9667;
                                var79.Jail = true;
                                var79.sendMessage("You have been jailed by " + c.playerName);
                                c.sendMessage("Successfully Jailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var61) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unjail")) {
                    try {
                        e = playerCommand.substring(7);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                if(var79.inWild()) {
                                    c.sendMessage("This player is in the wilderness, not in jail.");
                                    return;
                                }

                                if(var79.duelStatus == 5 || var79.inDuelArena()) {
                                    c.sendMessage("This player is during a duel, and not in jail.");
                                    return;
                                }

                                var79.teleportToX = 3093;
                                var79.teleportToY = 3493;
                                var79.Jail = false;
                                var79.sendMessage("You have been unjailed by " + c.playerName + ". You can now teleport.");
                                c.sendMessage("Successfully unjailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var60) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("timedmute") && c.playerRights >= 1 && c.playerRights <= 3) {
                    try {
                        var87 = playerCommand.split("-");
                        if(var87.length < 2) {
                            c.sendMessage("Currect usage: ::timedmute-playername-seconds");
                            return;
                        }

                        i = var87[1];
                        c2 = Integer.parseInt(var87[2]) * 1000;

                        for(var85 = 0; var85 < 300; ++var85) {
                            if(PlayerHandler.players[var85] != null && PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                var84 = (Client)PlayerHandler.players[var85];
                                var84.sendMessage("You have been muted by: " + c.playerName + " for " + c2 / 1000 + " seconds");
                                var84.muteEnd = System.currentTimeMillis() + (long)c2;
                                break;
                            }
                        }
                    } catch (Exception var59) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("teletome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = c.absX;
                                var79.teleportToY = c.absY;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to you.");
                                var79.sendMessage("You have been teleported to " + c.playerName);
                            }
                        }
                    } catch (Exception var58) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("xteleto")) {
                    e = playerCommand.substring(8);

                    for(var81 = 0; var81 < 300; ++var81) {
                        if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(c.duelStatus == 5) {
                                c.sendMessage("You cannot teleport to a player during a duel.");
                                return;
                            }

                            c.getPA().movePlayer(PlayerHandler.players[var81].getX(), PlayerHandler.players[var81].getY(), c.heightLevel);
                        }
                    }
                }

                if(playerCommand.startsWith("movehome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3096;
                                var79.teleportToY = 3468;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to home");
                                var79.sendMessage("You have been teleported to home");
                            }
                        }
                    } catch (Exception var57) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }
            }

            if(c.playerRights == 3) {
                if(playerCommand.toLowerCase().startsWith("title")) {
                    try {
                        var87 = playerCommand.split("-");
                        c.playerTitle = var87[1];
                        i = var87[2].toLowerCase();
                        if(i.equals("orange")) {
                            c.titleColor = 0;
                        }

                        if(i.equals("purple")) {
                            c.titleColor = 1;
                        }

                        if(i.equals("red")) {
                            c.titleColor = 2;
                        }

                        if(i.equals("green")) {
                            c.titleColor = 3;
                        }

                        c.sendMessage("You succesfully changed your title.");
                        c.updateRequired = true;
                        c.setAppearanceUpdateRequired(true);
                    } catch (Exception var17) {
                        c.sendMessage("Use as ::title-[title]-[color]");
                    }
                }

                if(playerCommand.startsWith("getip") && playerCommand.length() > 6 && c.playerRights == 3) {
                    e = playerCommand.substring(6);

                    for(var81 = 0; var81 < 300; ++var81) {
                        if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                            c.sendMessage(PlayerHandler.players[var81].playerName + " ip is " + PlayerHandler.players[var81].connectedFrom);
                            return;
                        }
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givetitle")) {
                    for(var82 = 0; var82 < PlayerHandler.players.length; ++var82) {
                        if(PlayerHandler.players[var82] != null) {
                            var89 = (Client)PlayerHandler.players[var82];

                            try {
                                String[] var86 = playerCommand.split("-");
                                var89.playerTitle = var86[1];
                                var80 = var86[2].toLowerCase();
                                if(var80.equals("orange")) {
                                    var89.titleColor = 0;
                                }

                                if(var80.equals("purple")) {
                                    var89.titleColor = 1;
                                }

                                if(var80.equals("red")) {
                                    var89.titleColor = 2;
                                }

                                if(var80.equals("green")) {
                                    var89.titleColor = 3;
                                }

                                c.sendMessage("You succesfully changed your title.");
                                var89.updateRequired = true;
                                var89.setAppearanceUpdateRequired(true);
                            } catch (Exception var16) {
                                c.sendMessage("Player is either offline, or does not exist.");
                            }
                        }
                    }
                }

                if(playerCommand.equalsIgnoreCase("spells")) {
                    if(c.playerMagicBook == 2) {
                        c.sendMessage("You switch to modern magic.");
                        c.setSidebarInterface(6, 1151);
                        c.playerMagicBook = 0;
                    } else if(c.playerMagicBook == 0) {
                        c.sendMessage("You switch to ancient magic.");
                        c.setSidebarInterface(6, 12855);
                        c.playerMagicBook = 1;
                    } else if(c.playerMagicBook == 1) {
                        c.sendMessage("You switch to lunar magic.");
                        c.setSidebarInterface(6, 29999);
                        c.playerMagicBook = 2;
                    }
                }

                if(playerCommand.toLowerCase().startsWith("movehome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3096;
                                var79.teleportToY = 3468;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to home");
                                var79.sendMessage("You have been teleported to home");
                            }
                        }
                    } catch (Exception var56) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("lvl")) {
                    try {
                        var87 = playerCommand.split(" ");
                        var81 = Integer.parseInt(var87[1]);
                        c2 = Integer.parseInt(var87[2]);
                        if(c2 > 99) {
                            c2 = 99;
                        } else if(c2 < 0) {
                            c2 = 1;
                        }

                        c.playerXP[var81] = c.getPA().getXPForLevel(c2) + 5;
                        c.playerLevel[var81] = c.getPA().getLevelForXP(c.playerXP[var81]);
                        c.getPA().refreshSkill(var81);
                    } catch (Exception var15) {
                        ;
                    }
                }

                if(playerCommand.equalsIgnoreCase("alltome")) {
                    for(var82 = 0; var82 < PlayerHandler.players.length; ++var82) {
                        if(PlayerHandler.players[var82] != null) {
                            var89 = (Client)PlayerHandler.players[var82];
                            var89.teleportToX = c.absX;
                            var89.teleportToY = c.absY;
                            var89.heightLevel = c.heightLevel;
                            var89.sendMessage("Mass teleport to: " + c.playerName);
                        }
                    }
                }

                if(playerCommand.toLowerCase().startsWith("tele")) {
                    var87 = playerCommand.split(" ");
                    if(var87.length > 3) {
                        c.getPA().movePlayer(Integer.parseInt(var87[1]), Integer.parseInt(var87[2]), Integer.parseInt(var87[3]));
                    } else if(var87.length == 3) {
                        c.getPA().movePlayer(Integer.parseInt(var87[1]), Integer.parseInt(var87[2]), c.heightLevel);
                    }
                }

                if(playerCommand.toLowerCase().startsWith("getid")) {
                    var87 = playerCommand.split(" ");
                    i = "";
                    c2 = 0;

                    for(var85 = 1; var85 < var87.length; ++var85) {
                        i = i + var87[var85] + " ";
                    }

                    i = i.substring(0, i.length() - 1);
                    c.sendMessage("Searching: " + i);

                    for(var85 = 0; var85 < Server.itemHandler.ItemList.length; ++var85) {
                        if(Server.itemHandler.ItemList[var85] != null && Server.itemHandler.ItemList[var85].itemName.replace("_", " ").toLowerCase().contains(i.toLowerCase())) {
                            c.sendMessage("@red@" + Server.itemHandler.ItemList[var85].itemName.replace("_", " ") + " - " + Server.itemHandler.ItemList[var85].itemId);
                            ++c2;
                        }
                    }

                    c.sendMessage(c2 + " results found...");
                }

                if(playerCommand.toLowerCase().startsWith("mute")) {
                    try {
                        e = playerCommand.substring(5);
                        Connection.addNameToMuteList(e);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.sendMessage("You have been muted by: " + c.playerName);
                                break;
                            }
                        }
                    } catch (Exception var55) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("unmute")) {
                    try {
                        e = playerCommand.substring(7);
                        Connection.unMuteUser(e);
                    } catch (Exception var14) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("bank")) {
                    c.getPA().openUpBank();
                }

                if(playerCommand.toLowerCase().startsWith("item")) {
                    try {
                        var87 = playerCommand.split(" ");
                        if(var87.length == 3) {
                            var81 = Integer.parseInt(var87[1]);
                            c2 = Integer.parseInt(var87[2]);
                            if(var81 <= 20200 && var81 >= 0) {
                                c.getItems().addItem(var81, c2);
                            } else {
                                c.sendMessage("No such item.");
                            }
                        } else {
                            c.sendMessage("Use as ::item 995 200");
                        }
                    } catch (Exception var54) {
                        ;
                    }
                }

                if(playerCommand.equalsIgnoreCase("infhp")) {
                    c.getPA().requestUpdates();
                    c.playerLevel[3] = 99999;
                    c.getPA().refreshSkill(3);
                    c.gfx0(754);
                    c.sendMessage("Infiniate Health for the win.");
                }

                if(playerCommand.toLowerCase().startsWith("givedp")) {
                    try {
                        var87 = playerCommand.split(" ");
                        i = var87[1];
                        c2 = Integer.parseInt(var87[2]);

                        for(var85 = 0; var85 < PlayerHandler.players.length; ++var85) {
                            if(PlayerHandler.players[var85] != null) {
                                if(PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                    var84 = (Client)PlayerHandler.players[var85];
                                    var84.dp += c2;
                                    c.sendMessage("You have given " + i + ", " + c2 + " points.");
                                    var84.sendMessage("You have been given " + c2 + " points by " + c.playerName + ".");
                                } else {
                                    c.sendMessage("Use as ::givepoints name amount");
                                }
                            }
                        }
                    } catch (Exception var53) {
                        ;
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givevpoints")) {
                    try {
                        var87 = playerCommand.split(" ");
                        i = var87[1];
                        c2 = Integer.parseInt(var87[2]);

                        for(var85 = 0; var85 < PlayerHandler.players.length; ++var85) {
                            if(PlayerHandler.players[var85] != null) {
                                if(PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                    var84 = (Client)PlayerHandler.players[var85];
                                    var84.votingPoints += c2;
                                    c.sendMessage("You have given " + i + ", " + c2 + " points.");
                                    var84.sendMessage("You have been given " + c2 + " points by " + c.playerName + ".");
                                } else {
                                    c.sendMessage("Use as ::givevpoints name amount");
                                }
                            }
                        }
                    } catch (Exception var52) {
                        ;
                    }
                }

                if(playerCommand.toLowerCase().startsWith("hail")) {
                    for(var82 = 0; var82 < PlayerHandler.players.length; ++var82) {
                        if(PlayerHandler.players[var82] != null) {
                            var89 = (Client)PlayerHandler.players[var82];
                            c2 = Misc.random(10);
                            if(c2 == 0) {
                                var89.forcedChat("Lol");
                            } else if(c2 == 1) {
                                var89.forcedChat("Ateeq is awesome, yeah");
                            } else if(c2 == 2) {
                                var89.forcedChat("Dragon-Scape has the best development team ever!");
                            } else if(c2 == 3) {
                                var89.forcedChat("Dragon-Scape is the best!");
                            } else if(c2 == 4) {
                                var89.forcedChat("Why am I so amazing?...");
                            } else if(c2 == 5) {
                                var89.forcedChat("Fuck off");
                            } else if(c2 == 6) {
                                var89.forcedChat("I suck cock");
                            } else if(c2 == 7) {
                                var89.forcedChat("wtf wtf wtf!!! is this dragon-scape wtf??");
                            } else if(c2 == 8) {
                                var89.forcedChat("RITAMÄKI FTW!");
                            } else if(c2 == 4) {
                                var89.forcedChat("Shut the fuck up mafataka");
                            }
                        }
                    }
                }

                if(playerCommand.toLowerCase().startsWith("kill")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                c.sendMessage("You have killed the user: " + PlayerHandler.players[var81].playerName);
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.isDead = true;
                                break;
                            }
                        }
                    } catch (Exception var51) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("massnpc")) {
                    try {
                        var82 = 0;
                        var81 = Integer.parseInt(playerCommand.substring(8));
                        if(var81 <= 0) {
                            c.sendMessage("No such NPC.");
                        } else {
                            c2 = 0;

                            while(true) {
                                if(c2 >= 5) {
                                    c.sendMessage("5x5 npc\'s spawned");
                                    break;
                                }

                                for(var85 = 0; var85 < 5; ++var85) {
                                    if(var82 > 196) {
                                        return;
                                    }

                                    Server.npcHandler.spawnNpc(c, var81, c.absX + c2, c.absY + var85, c.heightLevel, 0, 120, 7, 70, 70, false, false);
                                    ++var82;
                                }

                                ++c2;
                            }
                        }
                    } catch (Exception var50) {
                        c.sendMessage("Nope.");
                    }
                }

                int i2;
                if(playerCommand.toLowerCase().startsWith("takeitem")) {
                    try {
                        var87 = playerCommand.split(" ");
                        var81 = Integer.parseInt(var87[1]);
                        c2 = Integer.parseInt(var87[2]);
                        var80 = var87[3];
                        var84 = null;

                        for(i2 = 0; i2 < 300; ++i2) {
                            if(PlayerHandler.players[i2] != null && PlayerHandler.players[i2].playerName.equalsIgnoreCase(var80)) {
                                var84 = (Client)PlayerHandler.players[i2];
                                break;
                            }
                        }

                        if(var84 == null) {
                            c.sendMessage("Player doesn\'t exist.");
                            return;
                        }

                        c.sendMessage("You have just removed " + c2 + " of item number: " + var81 + ".");
                        var84.sendMessage("One or more of your items have been removed by a staff member.");
                        var84.getItems().deleteItem(var81, c2);
                    } catch (Exception var49) {
                        c.sendMessage("Use as ::takeitem ID AMOUNT PLAYERNAME.");
                    }
                }

                if(playerCommand.equalsIgnoreCase("vengall")) {
                    for(var82 = 0; var82 < PlayerHandler.players.length; ++var82) {
                        if(PlayerHandler.players[var82] != null) {
                            var89 = (Client)PlayerHandler.players[var82];
                            var89.vengOn = true;
                            var89.startAnimation(4410);
                        }
                    }
                }

                if(playerCommand.toLowerCase().startsWith("smite")) {
                    try {
                        e = playerCommand.substring(6);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                c.startAnimation(811);
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.isDead = true;
                                c.gfx0(76);
                                var79.handleHitMask(100);
                                var79.gfx0(370);
                                var79.forcedChat("FUCK NOO!");
                                var79.getPA().refreshSkill(3);
                                var79.dealDamage(999999);
                                break;
                            }
                        }
                    } catch (Exception var48) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("wipeinv")) {
                    try {
                        var87 = playerCommand.split(" ");
                        i = var87[1];
                        var79 = null;

                        for(var85 = 0; var85 < 300; ++var85) {
                            if(PlayerHandler.players[var85] != null && PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                var79 = (Client)PlayerHandler.players[var85];
                                break;
                            }
                        }

                        if(var79 == null) {
                            c.sendMessage("Player doesn\'t exist.");
                            return;
                        }

                        var79.getItems().removeAllItems();
                        var79.sendMessage("Your inventory has been cleared by a staff member.");
                        c.sendMessage("You cleared " + var79.playerName + "\'s inventory.");
                    } catch (Exception var47) {
                        c.sendMessage("Use as ::wipeinv PLAYERNAME.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("giveitem")) {
                    try {
                        var87 = playerCommand.split(" ", 2);
                        var81 = Integer.parseInt(var87[1]);
                        c2 = Integer.parseInt(var87[2]);
                        var80 = var87[3];
                        var84 = null;

                        for(i2 = 0; i2 < 300; ++i2) {
                            if(PlayerHandler.players[i2] != null && PlayerHandler.players[i2].playerName.equalsIgnoreCase(var80)) {
                                var84 = (Client)PlayerHandler.players[i2];
                                break;
                            }
                        }

                        if(var84 == null) {
                            c.sendMessage("Player doesn\'t exist.");
                            return;
                        }

                        c.sendMessage("You have just given " + c2 + " of item number: " + var81 + ".");
                        var84.sendMessage("You have just been given item(s).");
                        var84.getItems().addItem(var81, c2);
                    } catch (Exception var46) {
                        c.sendMessage("Use as ::giveitem ID AMOUNT PLAYERNAME.");
                    }
                }

                if(playerCommand.equalsIgnoreCase("mypos")) {
                    c.sendMessage("X: " + c.absX);
                    c.sendMessage("Y: " + c.absY);
                    c.sendMessage("H: " + c.heightLevel);
                }

                if(playerCommand.toLowerCase().startsWith("interface")) {
                    try {
                        var87 = playerCommand.split(" ");
                        var81 = Integer.parseInt(var87[1]);
                        c.getPA().showInterface(var81);
                    } catch (Exception var13) {
                        c.sendMessage("::interface ####");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("gfx")) {
                    var87 = playerCommand.split(" ");
                    c.gfx0(Integer.parseInt(var87[1]));
                }

                if(playerCommand.equalsIgnoreCase("spec")) {
                    c.specAmount = 10.0D;
                }

                if(playerCommand.equalsIgnoreCase("infspec")) {
                    c.specAmount = 10000.0D;
                }

                if(playerCommand.toLowerCase().startsWith("object")) {
                    var87 = playerCommand.split(" ");
                    c.getPA().object(Integer.parseInt(var87[1]), c.absX, c.absY, 0, 10);
                }

                if(playerCommand.toLowerCase().startsWith("npc")) {
                    try {
                        var82 = Integer.parseInt(playerCommand.substring(4));
                        if(var82 > 0) {
                            Server.npcHandler.spawnNpc(c, var82, c.absX, c.absY, 0, 0, 1200, 7, 70, 70, false, false);
                            c.sendMessage("You spawn a Npc.");
                        } else {
                            c.sendMessage("No such NPC.");
                        }
                    } catch (Exception var12) {
                        ;
                    }
                }

                if(playerCommand.toLowerCase().startsWith("ban")) {
                    try {
                        e = playerCommand.substring(4);
                        Connection.addNameToBanList(e);
                        Connection.addNameToFile(e);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].disconnected = true;
                            }
                        }
                    } catch (Exception var45) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("unban")) {
                    try {
                        e = playerCommand.substring(6);
                        Connection.removeNameFromBanList(e);
                        c.sendMessage(e + " has been unbanned.");
                    } catch (Exception var11) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("ipmute")) {
                    try {
                        e = playerCommand.substring(7);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                Connection.addIpToMuteList(PlayerHandler.players[var81].connectedFrom);
                                c.sendMessage("You have IP Muted the user: " + PlayerHandler.players[var81].playerName);
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.sendMessage("You have been muted by: " + c.playerName);
                                break;
                            }
                        }
                    } catch (Exception var44) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("unipmute")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                Connection.unIPMuteUser(PlayerHandler.players[var81].connectedFrom);
                                c.sendMessage("You have Un Ip-Muted the user: " + PlayerHandler.players[var81].playerName);
                                break;
                            }
                        }
                    } catch (Exception var43) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("unipban")) {
                    e = playerCommand.substring(8);
                    Connection.removeIpFromBanList(e);
                }

                if(playerCommand.toLowerCase().startsWith("kick")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].disconnected = true;
                            }
                        }
                    } catch (Exception var42) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("demote")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 0;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve demoted the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var41) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givemod")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                c.playerTitle = "Moderator";
                                PlayerHandler.players[var81].playerRights = 1;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to moderator the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var40) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("giveadmin")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 2;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to admin the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var39) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("afk")) {
                    c.startAnimation(4117);
                    c.forcedText = "AFK!";
                    c.forcedChatUpdateRequired = true;
                    c.updateRequired = true;
                }

                if(playerCommand.toLowerCase().startsWith("givedp")) {
                    try {
                        var87 = playerCommand.split(" ", 2);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(var87[1])) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.donPoints = Integer.parseInt(var87[2]);
                                c.sendMessage("You gave " + Integer.parseInt(var87[2]) + " points to " + var87[1] + ", he has now " + var79.donPoints + " points.");
                                var79.sendMessage("You recieve " + Integer.parseInt(var87[2]) + ", you now have " + var79.donPoints + ".");
                            }
                        }
                    } catch (Exception var38) {
                        c.sendMessage("Player must be offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givedon")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 4;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to donator the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var37) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givesup")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 5;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to supporter the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var36) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givespon")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 5;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to sponsor the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var35) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("givevip")) {
                    try {
                        e = playerCommand.substring(8);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                PlayerHandler.players[var81].playerRights = 6;
                                PlayerHandler.players[var81].disconnected = true;
                                c.sendMessage("You\'ve promoted to vip the user:  " + PlayerHandler.players[var81].playerName + " IP: " + PlayerHandler.players[var81].connectedFrom);
                            }
                        }
                    } catch (Exception var34) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("update")) {
                    var87 = playerCommand.split(" ");
                    var81 = Integer.parseInt(var87[1]);
                    PlayerHandler.updateSeconds = var81;
                    PlayerHandler.updateAnnounced = false;
                    PlayerHandler.updateRunning = true;
                    PlayerHandler.updateStartTime = System.currentTimeMillis();
                }

                if(playerCommand.toLowerCase().startsWith("emote")) {
                    var87 = playerCommand.split(" ");
                    c.startAnimation(Integer.parseInt(var87[1]));
                    c.getPA().requestUpdates();
                }

                if(playerCommand.toLowerCase().startsWith("god")) {
                    if(c.playerStandIndex != 1501) {
                        c.startAnimation(1500);
                        c.playerStandIndex = 1501;
                        c.playerTurnIndex = 1851;
                        c.playerWalkIndex = 1851;
                        c.playerTurn180Index = 1851;
                        c.playerTurn90CWIndex = 1501;
                        c.playerTurn90CCWIndex = 1501;
                        c.playerRunIndex = 1851;
                        c.updateRequired = true;
                        c.appearanceUpdateRequired = true;
                        c.sendMessage("You turn God mode on.");
                    } else {
                        c.playerStandIndex = 808;
                        c.playerTurnIndex = 823;
                        c.playerWalkIndex = 819;
                        c.playerTurn180Index = 820;
                        c.playerTurn90CWIndex = 821;
                        c.playerTurn90CCWIndex = 822;
                        c.playerRunIndex = 824;
                        c.updateRequired = true;
                        c.appearanceUpdateRequired = true;
                        c.sendMessage("Godmode has been de-activated.");
                    }
                }

                if(playerCommand.toLowerCase().startsWith("float")) {
                    if(c.playerStandIndex != 3419) {
                        c.startAnimation(3419);
                        c.playerStandIndex = 3419;
                        c.playerTurnIndex = 3419;
                        c.playerWalkIndex = 3419;
                        c.playerTurn180Index = 3419;
                        c.playerTurn90CWIndex = 3419;
                        c.playerTurn90CCWIndex = 3419;
                        c.playerRunIndex = 3419;
                        c.updateRequired = true;
                        c.appearanceUpdateRequired = true;
                        c.sendMessage("You turn floating mode on.");
                    } else {
                        c.playerStandIndex = 808;
                        c.playerTurnIndex = 823;
                        c.playerWalkIndex = 819;
                        c.playerTurn180Index = 820;
                        c.playerTurn90CWIndex = 821;
                        c.playerTurn90CCWIndex = 822;
                        c.playerRunIndex = 824;
                        c.updateRequired = true;
                        c.appearanceUpdateRequired = true;
                        c.sendMessage("Godmode has been de-activated.");
                    }
                }

                if(playerCommand.equalsIgnoreCase("dhpray")) {
                    c.getPA().requestUpdates();
                    c.playerLevel[5] = 9900000;
                    c.playerLevel[3] = 1;
                    c.getPA().refreshSkill(3);
                    c.getPA().refreshSkill(5);
                }

                if(playerCommand.equalsIgnoreCase("vipzone")) {
                    c.getPA().startTeleport(2337, 9799, 0, "modern");
                }

                if(c.playerRights >= 1 && c.playerRights <= 7 && playerCommand.equals("prezone")) {
                    c.getPA().startTeleport(2587, 9426, 4, "modern");
                }

                if(playerCommand.equalsIgnoreCase("staffzone")) {
                    c.getPA().startTeleport(3233, 9316, 0, "modern");
                }

                if(playerCommand.toLowerCase().startsWith("ct")) {
                    c.instantWalk = !c.instantWalk;
                    c.sendMessage("@red@Instant walk = " + c.instantWalk);
                }

                if(playerCommand.toLowerCase().startsWith("master")) {
                    if(c.inWild()) {
                        return;
                    }

                    for(var82 = 0; var82 < c.playerLevel.length; ++var82) {
                        c.playerXP[var82] = c.getPA().getXPForLevel(99) + 5;
                        c.playerLevel[var82] = c.getPA().getLevelForXP(c.playerXP[var82]);
                        c.getPA().refreshSkill(var82);
                    }
                }
            }

            if(c.playerRights == 7) {
                if(playerCommand.equalsIgnoreCase("staffzone")) {
                    c.getPA().startTeleport(3233, 9316, 0, "modern");
                }

                if(playerCommand.startsWith("jail")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3460;
                                var79.teleportToY = 9667;
                                var79.Jail = true;
                                var79.sendMessage("You have been jailed by " + c.playerName);
                                c.sendMessage("Successfully Jailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var33) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("unjail")) {
                    try {
                        e = playerCommand.substring(7);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                if(var79.inWild()) {
                                    c.sendMessage("This player is in the wilderness, not in jail.");
                                    return;
                                }

                                if(var79.duelStatus == 5 || var79.inDuelArena()) {
                                    c.sendMessage("This player is during a duel, and not in jail.");
                                    return;
                                }

                                var79.teleportToX = 3093;
                                var79.teleportToY = 3493;
                                var79.Jail = false;
                                var79.sendMessage("You have been unjailed by " + c.playerName + ". You can now teleport.");
                                c.sendMessage("Successfully unjailed " + var79.playerName + ".");
                            }
                        }
                    } catch (Exception var32) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("timedmute") && c.playerRights >= 1 && c.playerRights <= 3) {
                    try {
                        var87 = playerCommand.split("-");
                        if(var87.length < 2) {
                            c.sendMessage("Currect usage: ::timedmute-playername-seconds");
                            return;
                        }

                        i = var87[1];
                        c2 = Integer.parseInt(var87[2]) * 1000;

                        for(var85 = 0; var85 < 300; ++var85) {
                            if(PlayerHandler.players[var85] != null && PlayerHandler.players[var85].playerName.equalsIgnoreCase(i)) {
                                var84 = (Client)PlayerHandler.players[var85];
                                var84.sendMessage("You have been muted by: " + c.playerName + " for " + c2 / 1000 + " seconds");
                                var84.muteEnd = System.currentTimeMillis() + (long)c2;
                                break;
                            }
                        }
                    } catch (Exception var31) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("teletome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = c.absX;
                                var79.teleportToY = c.absY;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to you.");
                                var79.sendMessage("You have been teleported to " + c.playerName);
                            }
                        }
                    } catch (Exception var30) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("xteleto")) {
                    e = playerCommand.substring(8);

                    for(var81 = 0; var81 < 300; ++var81) {
                        if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                            var79 = (Client)PlayerHandler.players[var81];
                            if(c.duelStatus == 5) {
                                c.sendMessage("You cannot teleport to a player during a duel.");
                                return;
                            }

                            c.getPA().movePlayer(PlayerHandler.players[var81].getX(), PlayerHandler.players[var81].getY(), c.heightLevel);
                        }
                    }
                }

                if(playerCommand.startsWith("kick")) {
                    try {
                        e = playerCommand.substring(5);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                PlayerHandler.players[var81].disconnected = true;
                                var79.sendMessage("You got kicked by @blu@ " + c.playerName + ".");
                            }
                        }
                    } catch (Exception var29) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }

                if(playerCommand.startsWith("movehome")) {
                    try {
                        e = playerCommand.substring(9);

                        for(var81 = 0; var81 < 300; ++var81) {
                            if(PlayerHandler.players[var81] != null && PlayerHandler.players[var81].playerName.equalsIgnoreCase(e)) {
                                var79 = (Client)PlayerHandler.players[var81];
                                var79.teleportToX = 3096;
                                var79.teleportToY = 3468;
                                var79.heightLevel = c.heightLevel;
                                c.sendMessage("You have teleported " + var79.playerName + " to home");
                                var79.sendMessage("You have been teleported to home");
                            }
                        }
                    } catch (Exception var28) {
                        c.sendMessage("Player Must Be Offline.");
                    }
                }
            }

        }
    }
}
