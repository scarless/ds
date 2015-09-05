package server.world;

import java.util.Collections;
import java.util.LinkedList;

import server.Config;
import server.Server;
import server.game.players.Client;
import server.game.players.PlayerHandler;
import server.game.players.saving.PlayerSave;
import server.util.Misc;

/**
 * This class stores all information about the clan. This includes active
 * members, banned members, ranked members and their ranks, clan title, and clan
 * founder. All clan joining, leaving, and moderation/setup is also handled in
 * this class.
 * 
 * @author Galkon
 * 
 */
public class Clan {
	
	
	
	String[] stuffz = { ".net", "join", "all join", "c0m", ".com",
			"e m p y r e a n", "empy", "empyrean", "wasa", ".info", ".org",
			"shit serv", "bad serv", "shit eco", ".tk", ". tk", ". com",
			". org", ". net", ". info", "www.", "selling rs", "sell rs",
			"buying rs", "this server suck", "this server is gay",
			"ryans gay", "james is gay", "sell runescape",
			"selling runescape", "buy runescape", "buying runescape",
			"sell don", "sellin don", "selling don", "buying don",
			"buy don", "sell acc", "sellin acc", "buyin acc", "buy acc",
			"pkrss", "p k r s s", "com", "www", "pkrs s", "test" };

	/**
	 * Adds a member to the clan.
	 * 
	 * @param player
	 */
	public void addMember(Client player) {
		if (isBanned(player.playerName)) {
			player.sendMessage("<col=FF0000>You are currently banned from this clan chat.</col>");
			return;
		}
		if (whoCanJoin > Rank.ANYONE && !isFounder(player.playerName)) {
			if (getRank(player.playerName) < whoCanJoin) {
				player.sendMessage("Only " + getRankTitle(whoCanJoin)
						+ "s+ may join this chat.");
				return;
			}
		}
		player.clan = this;
		player.lastClanChat = getFounder();
		activeMembers.add(player.playerName);
		player.getPA().sendString("Leave chat", 18135);
		player.getPA().sendString(
				"Talking in: <col=FFFF64>" + getTitle() + "</col>", 18139);
		player.getPA().sendString(
				"Owner: <col=FFFFFF>" + Misc.formatPlayerName(getFounder())
						+ "</col>", 18140);
		player.sendMessage("Now talking in clan chat <col=FFFF64><shad=0>"
				+ getTitle() + "</shad></col>.");
		player.sendMessage("To talk, start each line of chat with the / symbol.");
		updateMembers();
	}

	/**
	 * Removes the player from the clan.
	 * 
	 * @param player
	 */
	public void removeMember(Client player) {
		for (int index = 0; index < activeMembers.size(); index++) {
			if (activeMembers.get(index).equalsIgnoreCase(player.playerName)) {
				player.clan = null;
				resetInterface(player);
				activeMembers.remove(index);
			}
		}
		player.getPA().refreshSkill(21);
		player.getPA().refreshSkill(22);
		player.getPA().refreshSkill(23);
		updateMembers();
	}

	/**
	 * Removes the player from the clan.
	 * 
	 * @param player
	 */
	public void removeMember(String name) {
		for (int index = 0; index < activeMembers.size(); index++) {
			if (activeMembers.get(index).equalsIgnoreCase(name)) {
				Client player = (Client) PlayerHandler.getPlayer(name);
				if (player != null) {
					player.clan = null;
					resetInterface(player);
					activeMembers.remove(index);
				}
			}
		}
		updateMembers();
	}

	/**
	 * Updates the members on the interface for the player.
	 * 
	 * @param player
	 */
	public void updateInterface(Client player) {
		player.getPA().sendString(
				"Talking in: <col=FFFF64>" + getTitle() + "</col>", 18139);
		player.getPA().sendString(
				"Owner: <col=FFFFFF>" + Misc.formatPlayerName(getFounder())
						+ "</col>", 18140);
		Collections.sort(activeMembers);
		for (int index = 0; index < 100; index++) {
			if (index < activeMembers.size()) {
				player.getPA().sendString(
						"<clan="
								+ getRank(activeMembers.get(index))
								+ ">"
								+ Misc.formatPlayerName(activeMembers
										.get(index)), 18144 + index);
			} else {
				player.getPA().sendString("", 18144 + index);
			}
		}
	}

	/**
	 * Updates the interface for all members.
	 */
	public void updateMembers() {
		for (int index = 0; index < Config.MAX_PLAYERS; index++) {
			Client player = (Client) PlayerHandler.players[index];
			if (player != null && activeMembers != null) {
				if (activeMembers.contains(player.playerName)) {
					updateInterface(player);
				}
			}
		}
	}

	/**
	 * Resets the clan interface.
	 * 
	 * @param player
	 */
	public void resetInterface(Client player) {
		player.getPA().sendString("Join chat", 18135);
		player.getPA().sendString("Talking in: Not in chat", 18139);
		player.getPA().sendString("Owner: None", 18140);
		for (int index = 0; index < 100; index++) {
			player.getPA().sendString("", 18144 + index);
		}
	}

	/**
	 * Sends a message to the clan.
	 * 
	 * @param player
	 * @param message
	 */
	public void sendChat(Client paramClient, String paramString) {
		if (getRank(paramClient.playerName) < this.whoCanTalk) {
			paramClient.sendMessage("Only " + getRankTitle(this.whoCanTalk)
					+ "s+ may talk in this chat.");
			return;
		}
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c = (Client) PlayerHandler.players[j];
				if ((c != null) && (this.activeMembers.contains(c.playerName)))
					c.sendMessage("@bla@[@blu@" + getTitle()
							+ "@bla@] <clan=" + getRank(paramClient.playerName)
							+ ">@bla@"
							+ Misc.optimizeText(paramClient.playerName)
							+ ": @red@" + paramString + "");
			}
		}
	}

	/**
	 * Sends a message to the clan.
	 * 
	 * @param player
	 * @param message
	 */
	public void sendMessage(String message) {
		for (int index = 0; index < Config.MAX_PLAYERS; index++) {
			Client p = (Client) PlayerHandler.players[index];
			if (p != null) {
				if (activeMembers.contains(p.playerName)) {
					p.sendMessage(message);
				}
			}
		}
	}

	/**
	 * Sets the rank for the specified name.
	 * 
	 * @param name
	 * @param rank
	 */
	public void setRank(String name, int rank) {
		if (rankedMembers.contains(name)) {
			ranks.set(rankedMembers.indexOf(name), rank);
		} else {
			rankedMembers.add(name);
			ranks.add(rank);
		}
		save();
	}

	/**
	 * Demotes the specified name.
	 * 
	 * @param name
	 */
	public void demote(String name) {
		if (!rankedMembers.contains(name)) {
			return;
		}
		int index = rankedMembers.indexOf(name);
		rankedMembers.remove(index);
		ranks.remove(index);
		save();
	}

	/**
	 * Gets the rank of the specified name.
	 * 
	 * @param name
	 * @return
	 */
	public int getRank(String name) {
		name = Misc.formatPlayerName(name);
		if (rankedMembers.contains(name)) {
			return ranks.get(rankedMembers.indexOf(name));
		}
		if (isFounder(name)) {
			return Rank.OWNER;
		}
		if (PlayerSave.isFriend(getFounder(), name)) {
			return Rank.FRIEND;
		}
		return -1;
	}

	/**
	 * Can they kick?
	 * 
	 * @param name
	 * @return
	 */
	public boolean canKick(String name) {
		if (isFounder(name)) {
			return true;
		}
		if (getRank(name) >= whoCanKick) {
			return true;
		}
		return false;
	}

	/**
	 * Can they ban?
	 * 
	 * @param name
	 * @return
	 */
	public boolean canBan(String name) {
		if (isFounder(name)) {
			return true;
		}
		if (getRank(name) >= whoCanBan) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is the founder.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isFounder(String name) {
		if (getFounder().equalsIgnoreCase(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is a ranked user.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isRanked(String name) {
		name = Misc.formatPlayerName(name);
		if (rankedMembers.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the specified name is banned.
	 * 
	 * @param name
	 * @return
	 */
	public boolean isBanned(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Kicks the name from the clan chat.
	 * 
	 * @param name
	 */
	public void kickMember(String name) {
		if (!activeMembers.contains(name)) {
			return;
		}
		if (name.equalsIgnoreCase(getFounder())) {
			return;
		}
		removeMember(name);
		Client player = (Client) PlayerHandler.getPlayer(name);
		if (player != null) {
			player.sendMessage("You have been kicked from the clan chat.");
		}
		sendMessage("@blu@[Attempting to kick/ban @dre@'"
				+ Misc.formatPlayerName(name) + "'"
				+ " @blu@from this friends chat]");
	}

	/**
	 * Bans the name from entering the clan chat.
	 * 
	 * @param name
	 */
	public void banMember(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			return;
		}
		if (name.equalsIgnoreCase(getFounder())) {
			return;
		}
		if (isRanked(name)) {
			return;
		}
		removeMember(name);
		bannedMembers.add(name);
		save();
		Client player = (Client) PlayerHandler.getPlayer(name);
		if (player != null) {
			player.sendMessage("You have been kicked from the clan chat.");
		}
		sendMessage("@blu@[Attempting to kick/ban @dre@'"
				+ Misc.formatPlayerName(name) + "'"
				+ " @blu@from this friends chat]");
	}

	/**
	 * Unbans the name from the clan chat.
	 * 
	 * @param name
	 */
	public void unbanMember(String name) {
		name = Misc.formatPlayerName(name);
		if (bannedMembers.contains(name)) {
			bannedMembers.remove(name);
			save();
		}
	}

	/**
	 * Saves the clan.
	 */
	public void save() {
		Server.clanManager.save(this);
		updateMembers();
	}

	/**
	 * Deletes the clan.
	 */
	public void delete() {
		for (String name : activeMembers) {
			removeMember(name);
			Client player = (Client) PlayerHandler.getPlayer(name);
			player.sendMessage("The clan you were in has been deleted.");
		}
		Server.clanManager.delete(this);
	}

	/**
	 * Creates a new clan for the specified player.
	 * 
	 * @param player
	 */
	public Clan(Client player) {
		setTitle(player.playerName + "'s Clan");
		setFounder(player.playerName.toLowerCase());
	}

	/**
	 * Creates a new clan for the specified title and founder.
	 * 
	 * @param title
	 * @param founder
	 */
	public Clan(String title, String founder) {
		setTitle(title);
		setFounder(founder);
	}

	/**
	 * Gets the founder of the clan.
	 * 
	 * @return
	 */
	public String getFounder() {
		return founder;
	}

	/**
	 * Sets the founder.
	 * 
	 * @param founder
	 */
	public void setFounder(String founder) {
		this.founder = founder;
	}

	/**
	 * Gets the title of the clan.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 * @return
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The title of the clan.
	 */
	public String title;

	/**
	 * The founder of the clan.
	 */
	public String founder;

	/**
	 * The active clan members.
	 */
	public LinkedList<String> activeMembers = new LinkedList<String>();

	/**
	 * The banned members.
	 */
	public LinkedList<String> bannedMembers = new LinkedList<String>();

	/**
	 * The ranked clan members.
	 */
	public LinkedList<String> rankedMembers = new LinkedList<String>();

	/**
	 * The clan member ranks.
	 */
	public LinkedList<Integer> ranks = new LinkedList<Integer>();

	/**
	 * The clan ranks.
	 * 
	 * @author Galkon
	 * 
	 */
	public static class Rank {
		public final static int ANYONE = -1;
		public final static int FRIEND = 0;
		public final static int RECRUIT = 1;
		public final static int CORPORAL = 2;
		public final static int SERGEANT = 3;
		public final static int LIEUTENANT = 4;
		public final static int CAPTAIN = 5;
		public final static int GENERAL = 6;
		public final static int OWNER = 7;
	}

	/**
	 * Gets the rank title as a string.
	 * 
	 * @param rank
	 * @return
	 */
	public String getRankTitle(int rank) {
		switch (rank) {
		case -1:
			return "Anyone";
		case 0:
			return "Friend";
		case 1:
			return "Recruit";
		case 2:
			return "Corporal";
		case 3:
			return "Sergeant";
		case 4:
			return "Lieutenant";
		case 5:
			return "Captain";
		case 6:
			return "General";
		case 7:
			return "Only Me";
		}
		return "";
	}

	/**
	 * Sets the minimum rank that can join.
	 * 
	 * @param rank
	 */
	public void setRankCanJoin(int rank) {
		whoCanJoin = rank;
	}

	/**
	 * Sets the minimum rank that can talk.
	 * 
	 * @param rank
	 */
	public void setRankCanTalk(int rank) {
		whoCanTalk = rank;
	}

	/**
	 * Sets the minimum rank that can kick.
	 * 
	 * @param rank
	 */
	public void setRankCanKick(int rank) {
		whoCanKick = rank;
	}

	/**
	 * Sets the minimum rank that can ban.
	 * 
	 * @param rank
	 */
	public void setRankCanBan(int rank) {
		whoCanBan = rank;
	}

	/**
	 * The ranks privileges require (joining, talking, kicking, banning).
	 */
	public int whoCanJoin = Rank.ANYONE;
	public int whoCanTalk = Rank.ANYONE;
	public int whoCanKick = Rank.GENERAL;
	public int whoCanBan = Rank.OWNER;

}