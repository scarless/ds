package server.game.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;

//import eazy.util.HighscoreConnector;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import server.Config;
import server.Connection;
import server.Server;
import server.content.skill.Prayer;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.event.Event;
import server.event.EventContainer;
import server.event.EventManager;
import server.game.items.Item;
import server.game.items.ItemAssistant;
import server.game.minigames.Barrows;
import server.game.minigames.PestControl;
import server.game.npcs.pet.Pet;
import server.game.players.content.BankPin;
import server.game.players.content.ChallengePoints;
import server.game.players.content.CombatAssistant;
import server.game.players.content.DialogueHandler;
import server.game.players.content.MageKills;
import server.game.players.content.MeleeKills;
import server.game.players.content.PlayerKilling;
import server.game.players.content.PotionMixing;
import server.game.players.content.RangeKills;
import server.game.players.content.Sounds;
import server.game.players.content.TradeAndDuel;
import server.game.players.content.consumables.Food;
import server.game.players.content.consumables.Potions;
import server.game.players.content.pvphighscores.HighScoresBoard;
import server.game.players.content.quests.CooksAssistant;
import server.game.players.content.quests.WitchesPotion;
import server.game.players.content.skills.Crafting;
import server.game.players.content.skills.Fishing;
import server.game.players.content.skills.Fletching;
import server.game.players.content.skills.Runecrafting;
import server.game.players.content.skills.SkillInterfaces;
import server.game.players.content.skills.Slayer;
import server.game.players.content.skills.Smithing;
import server.game.players.content.skills.SmithingInterface;
import server.game.players.content.skills.Thieving;
import server.game.players.content.skills.agility.AgilityAssistant;
import server.game.players.saving.PlayerSave;
import server.game.shops.ShopAssistant;
import server.music.PlayList;
import server.net.Packet;
import server.net.Packet.Type;
import server.util.Misc;
import server.util.Stream;
import server.util.log.TradeLog;
import server.world.Clan;

public class Client extends Player {

	// words blocked from yell and
	// words blocked from yell and
	public String[] badwords = { "@cr@", "@cr1@", "@cr2@", "@cr3@", "@cr4@",
			"@cr5@", "@cr6@", "@cr7@", "@cr@8", "@cr9@", "develop", "Develop",
			"Developer", "developer", "all join", "Server Developer",
			"server developer", "CEO", "helper", "Helper", "Support",
			"support", "Veteran", "veteran", "Website developer",
			"Staff Member", "Staff", "staff", "staff member", "foreverpkers",
			"fpk", "fpks", "Mod", "Moderator", "Admin", "Administrator",
			"Owner", "owner", "admin", "mod", "moderator", "administrator",
			"pker", "assassin", "legendary pker", "boss killer", "pvm master",
			"Pker", "Boss Killer", "Assassin", "PVM Master", "Legendary Pker" };
	/*private void loginScreen() {
		getPA().sendFrame126(" @cya@Welcome to Dragon-Scape", 15257);
		getPA().sendFrame126(" Last connected from: "+ connectedFrom, 15258);
		getPA().sendFrame126("Become a mighty adventurer today.", 15259);
		getPA().sendFrame126("Register on the forums to exclusive content", 15260);
		getPA().sendFrame126("Behind the scenes and more!", 15261);
		getPA().sendFrame126("Become wealthy from thieving and slayer", 15262);
		getPA().sendFrame126("CLICK HERE TO PLAY", 15263);
		getPA().sendFrame126("Don't forget to secure your bank, set a bank pin!", 15270); 
		getPA().showInterface(15244);
	}*/
	public void rspsdata(Client c, String username) {
		try {
			username = username.replaceAll(" ", "_");
			String secret = "8fb21ee7a2207526da55a679f0332de2"; // YOUR SECRET
																// KEY!
			String email = "example@hotmail.com"; // This is the one you use
														// to login into
														// RSPS-PAY
			URL url = new URL(
					"http://rsps-pay.com/"
							+ username + "&secret=" + secret + "&email="
							+ email);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String results = reader.readLine();
			if (results.toLowerCase().contains("!error:")) {

			} else {
				String[] ary = results.split(",");
				for (int i = 0; i < ary.length; i++) {
					switch (ary[i]) {
					case "0": // Donator not found.
						sendMessage("Sorry, your donation was not found.");
						break;
					case "4807":// Regular Donator
						sendMessage("Thank you for purcashing regular donator!");
						c.playerRights = 4;
						c.dp += 100;
						break;
					case "4808": // Super Donator
						sendMessage("Thank you for purcashing super donator!");
						c.playerRights = 5;
						c.dp += 500;
						break;
					case "4809": // Extreme Donator
						sendMessage("Thank you for purcashing extreme donator!");
						c.playerRights = 6;
						c.dp += 2500;
						break;
					case "4812": // Legendary
						c.playerRights = 7;
						c.dp += 5000;
						sendMessage("Thank you for purcashing legendary donator!");
						break;
					case "4822": // 50 tokens
						sendMessage("You receive 50 tokens!");
						c.dp += 50;
						break;
					case "4823": //
						sendMessage("You receive 100 tokens!");
						c.dp += 100;
						break;
					case "4824": // 200 tokens
						sendMessage("You receive 200 tokens!");
						c.dp += 200;// PlayerHandler.players[i]
						break;
					case "4825": // 50 tokens
						sendMessage("You receive 300 tokens!");
						c.dp += 300;
						break;
					case "4826": // 50 tokens
						sendMessage("You receive 400 tokens!");
						c.dp += 400;
						break;
					case "4827": // 50 tokens
						sendMessage("You receive 500 tokens and 25 bonus tokens!");
						c.dp += 525;
						break;
					case "4828": // 50 tokens
						sendMessage("You receive 750 tokens! and 25 bonus tokens!");
						c.dp += 775;
						break;
					case "4829": // 50 tokens
						sendMessage("You receive 1,000 tokens! and 50 bonus tokens!");
						c.dp += 1050;
						break;
					case "4830": // 50 tokens
						sendMessage("You receive 1,500 tokens! and 75 bonus tokens!");
						c.dp += 1575;
						break;
					case "4831": // 50 tokens
						sendMessage("You receive 2,000 tokens! and 100 bonus tokens!");
						c.dp += 2100;
						break;
					case "5151": // x3 Crysal keys
						sendMessage("Your crystal key(s) have been placed in your bank.");
						c.getItems().addItemToBank(990, 3);
						break;
					case "5152": // x3 Crysal keys
						sendMessage("Your mystery box(s) have been placed in your bank.");
						c.getItems().addItemToBank(6199, 3);
						break;
					case "5153": // x3 Crysal keys
						sendMessage("Your dragon token has been placed in your bank.");
						c.getItems().addItemToBank(7478, 1);
						break;

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean bonusDrops() {
	if(lampStart2 >= System.currentTimeMillis() - 3600000)
		return true;
		
	return false;
	}
	
	public boolean bonusXP() {
	if(lampStart >= System.currentTimeMillis() - 3600000)
		return true;
		
	return false;
	}
	
	public Client getClient(int index) {
		return ((Client) PlayerHandler.players[index]);
	}
	public boolean validClient(int index) {
		Client p = (Client) PlayerHandler.players[index];
		if ((p != null) && !p.disconnected) {
			return true;
		}
		return false;
	}
	
	public void autoMessage() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				int Message = 0;
				Message = Misc.random(7);
				if (Message == 0) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Remember to read the rules! ::rules");
				} else if (Message == 1) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ If you need any help be sure to ask a staff member!");
				} else if (Message == 2) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Don't forget to download latest client version at forums");
				} else if (Message == 3) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Visit the website: www.pr-forum.net");
				} else if (Message == 4) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Please post your suggestions/bugs on the forums, not ingame!");
				} else if (Message == 5) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Remember to type ::commands");
				} else if (Message == 6) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Double XP is activated every weekend!");
				} else if (Message == 7) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ If you need assistance type ::help");
				} else if (Message == 8) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Did you know you can donate with RSGP aswell");
				} else if (Message == 9) {
					sendMessage("@bla@[@blu@@cr6@Server Notice@bla@]@blu@ Did you know all slayer monsters drop extremely rare keys?");
				}
			}
		}, 600000); // Milliseconds
	};

	public static int PK[] = { 4151, 6585, 7462, 6731, 6733, 6735, 6737, 10551,
			10548, 10549, 8850, 11732, 11730 };

	public static int randomPK() {
		return PK[(int) (Math.random() * PK.length)];
	}

	public static int GS[] = { 13902, 13899 };

	public static int randomGodswords() {
		return GS[(int) (Math.random() * GS.length)];
	}

	public static int Armor[] = { 13887, 13893, 13870, 13873, 13876, 13884,
			13890, 13858, 13861, 13864 };

	public static int randomArmor() {
		return Armor[(int) (Math.random() * Armor.length)];
	}

	public int waveType;
	public int[] waveInfo = new int[3];

	private AgilityAssistant agilityassistant = new AgilityAssistant();

	public AgilityAssistant getAgility1() {
		return agilityassistant;
	}

	public CooksAssistant getCA() {
		return cookAssistant;
	}

	public WitchesPotion getWP() {
		return witchesPotion;
	}

	public String currentClanName = "no Clan";
	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private Channel session;
	public String firstIP = "0";
	public String lastIP = "0";
	public boolean attackSkill = false;
	public boolean strengthSkill = false;
	public boolean defenceSkill = false;
	public boolean mageSkill = false;
	public boolean rangeSkill = false;
	public boolean prayerSkill = false;
	public boolean healthSkill = false;
	public int exchange;
	private BankPin bankPin = new BankPin(this);
	private WitchesPotion witchesPotion = new WitchesPotion(this);
	private CooksAssistant cookAssistant = new CooksAssistant(this);
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private Barrows barrows = new Barrows(this);
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);

	private TradeLog tradeLog = new TradeLog(this);
	Date dNow = new Date();
	SimpleDateFormat aj = new SimpleDateFormat("mm");
	SimpleDateFormat cj = new SimpleDateFormat("ss");
	SimpleDateFormat ej = new SimpleDateFormat("DD");
	SimpleDateFormat fj = new SimpleDateFormat("HH");
	public int todaysdate = Integer.parseInt(ej.format(dNow));
	public int todaysmin = Integer.parseInt(aj.format(dNow));
	public int todayssec = Integer.parseInt(cj.format(dNow));
	public int todayshour = Integer.parseInt(fj.format(dNow));

	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting();
	private Crafting crafting = new Crafting(this);
	private Fletching fletching = new Fletching(this);
	private Prayer prayer = new Prayer(this);
	private Thieving thieving = new Thieving(this);
	private Smithing smith = new Smithing(this);
	private SmithingInterface smithInt = new SmithingInterface(this);

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int barrowsRewardDelay = 0;
	public int mysteryBox = 0;
	public int returnCode = 2;
	private Future<?> currentTask;
	public int currentRegion = 0;
	public long lastRoll;
	public int diceItem;
	public int page;
	public String lastYell = "";
	public String customYellTag = "Tag";
	public boolean slayerHelmetEffect;
	public boolean inEazyCC, checkInv;
	public String lastKilled = "";
	public String customLogin;

	/* Musics */

	private final PlayList playList = new PlayList(this);

	public PlayList getPlayList() {
		return playList;
	}

	private final Map<String, Object> temporary = new HashMap<String, Object>();

	public Object getTemporary(String name) {
		return temporary.get(name);
	}

	public void addTemporary(String name, Object value) {
		if (name.equals("BUSY"))
			System.out.println("added: " + name);
		temporary.put(name, value);
	}

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 * @param volume
	 *            The volume amount of the sound (1-100)
	 * @param delay
	 *            The delay (0 = immediately 30 = 1/2cycle 60=full cycle) before
	 *            the sound plays.
	 */
	public void sendSound(int id, int volume, int delay) {
		if (id > 0 & this != null && this.outStream != null) {
			outStream.createFrame(174);
			outStream.writeWord(id);
			outStream.writeByte(100);
			outStream.writeWord(5);
		}
	}

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 * @param volume
	 *            The volume amount of the sound (1-100)
	 */
	public void sendSound(int id, int volume) {
		sendSound(id, volume, 0);
	}

	public void antiFirePotion() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				antiFirePot = false;
				sendMessage("Your resistance to dragon fire has worn off.");
				c.stop();
			}
		}, 120000);

	}

	/**
	 * Outputs a send packet which is built from the data params provided
	 * towards a connected user client channel.
	 * 
	 * @param id
	 *            The identification number of the sound.
	 */
	public void sendSound(int id) {
		sendSound(id, 100);// pretty sure it's 100 just double check
		// otherwise it will be 1
	}

	/**
	 * Play sounds
	 * 
	 * @param SOUNDID
	 *            : ID
	 * @param delay
	 *            : SOUND DELAY
	 */
	public int soundVolume = 10;

	public void playSound(int SOUNDID, int delay) {
		if (Config.SOUND) {
			if (soundVolume <= -1) {
				return;
			}
			/**
			 * Deal with regions We dont need to play this again because you are
			 * in the current region
			 */
			if (this != null) {
				if (this.soundVolume >= 0) {
					if (this.goodDistance(this.absX, this.absY, this.absX,
							this.absY, 2)) {
						System.out.println("Playing sound " + this.playerName
								+ ", Id: " + SOUNDID + ", Vol: "
								+ this.soundVolume);
						this.getOutStream().createFrame(174);
						this.getOutStream().writeWord(SOUNDID);
						this.getOutStream().writeByte(this.soundVolume);
						this.getOutStream().writeWord(/* delay */0);
					}
				}
			}

		}
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected
				|| outStream.currentOffset == 0)
			return;

		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED,
				ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;

	}

	private Sounds sound = new Sounds(this);

	public Sounds getSound() {
		return sound;
	}

	public boolean musicOn = false;

	public void playMusic(int song) {
		if (musicOn) {
			outStream.createFrame(74);
			outStream.writeWordBigEndian(song);
		}
	}

	public Client(Channel s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void SaveGame() {
		PlayerSave.saveGame(this);
	}

	private Map<Integer, TinterfaceText> interfaceText = new HashMap<Integer, TinterfaceText>();

	public class TinterfaceText {
		public int id;
		public String currentState;

		public TinterfaceText(String s, int id) {
			this.currentState = s;
			this.id = id;
		}

	}

	public boolean checkPacket126Update(String text, int id) {
		if (interfaceText.containsKey(id)) {
			TinterfaceText t = interfaceText.get(id);
			if (text.equals(t.currentState)) {
				return false;
			}
		}
		interfaceText.put(id, new TinterfaceText(text, id));
		return true;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		message = message.substring(0, 1).toUpperCase() + message.substring(1);
		clan = clan.substring(0, 1).toUpperCase() + clan.substring(1);
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 4, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, /* 0 */-1, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, /* 0 */4, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	/*
	 * public void destruct() {
	 * 
	 * HighScoresBoard.getLeaderBoard().saveLeaders(); if (isInPc()) {
	 * PestControl.removePlayerGame(this); getPA().movePlayer(2657, 2639, 0); }
	 * if (inFightCaves()) { getPA().movePlayer(2438, 5168, 0); } if (session ==
	 * null) return; if (underAttackBy > 0 || underAttackBy2 > 0) return; if
	 * (duelStatus == 6) { getTradeAndDuel().claimStakedItems(); } if
	 * (duelStatus >= 1 && duelStatus <= 5) {
	 * getTradeAndDuel().bothDeclineDuel(); saveCharacter = true; return; } if
	 * (disconnected == true) { getTradeAndDuel().declineTrade();
	 * getTradeAndDuel().declineDuel(); saveCharacter = true; } if (clan !=
	 * null) { clan.removeMember(this); } Misc.println("[Logged out]: " +
	 * playerName + ""); CycleEventHandler.getSingleton().stopEvents(this);
	 * disconnected = true; session.close(); session = null; inStream = null;
	 * outStream = null; isActive = false; buffer = null; super.destruct(); }
	 */
	public void destruct() {
		HighScoresBoard.getLeaderBoard().saveLeaders();
		if (session == null)
			return;
		PlayerSave.saveGame(this);
		if (isInPc()) {
			PestControl.removePlayerGame(this);
			getPA().movePlayer(2440, 3089, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(2438, 5168, 0);
		}
		if (session == null)
			return;
		if (underAttackBy > 0 || underAttackBy2 > 0)
			return;
		if (inPits)
			Server.fightPits.removePlayerFromPits(playerId);
		if (clan != null) {
			clan.removeMember(this);
		}
		RefreshAllSkills();
		Misc.println("[Logged out]: " + playerName + "");
		CycleEventHandler.getSingleton().stopEvents(this);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
		// }
	}

	public void RefreshAllSkills() {
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
	}

	public void calcCombat() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));

		combatLevel = 0;
		if (ran > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
		} else if (mag > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
					+ ((getLevelForXP(playerXP[3])) * 0.25)
					+ ((getLevelForXP(playerXP[5])) * 0.125)
					+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
		}
	}

	public void sendMessage(String s) {
		// synchronized (this) {
		// System.out.println("Here8.");
		if (getOutStream() != null) {
			// System.out.println("Here9.");
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}

	}

	public void setSidebarInterface(int menuId, int form) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}

	}

	public void joinEazyCC() {
		if (clan == null) {
			Clan localClan = Server.clanManager.getClan("Eazy");
			if (localClan != null)
				localClan.addMember(this);
			else if ("help".equalsIgnoreCase(this.playerName))
				Server.clanManager.create(this);
			else {
				sendMessage(Misc.formatPlayerName("Eazy")
						+ " has disabled this clan for now.");
			}
			getPA().refreshSkill(21);
			getPA().refreshSkill(22);
			getPA().refreshSkill(23);
		}
	}

	public void runEnergyPotion() {
		int frame = isRunning2 == true ? 1 : 0;
		getPA().sendFrame36(173, frame);
		runEnergy += 25;
	}

	public void extendedAntiFirePotion() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				extendedAntiFirePot = false;
				sendMessage("Your resistance to dragon fire has worn off.");
				c.stop();
			}
		}, 220000);
	}

	public void loginUI() {
		//loginScreen();
		int randomMessage = Misc.random(2);
		getPA().sendFrame126("Welcome to Dragon-Scape @red@Developer Mode", 15257);
		getPA().sendFrame126(
				"You last logged in with the IP: @blu@" + lastIP + "", 15258);

	}

	@Override
	public void initialize() {
		//loginScreen();
		joinTime = System.currentTimeMillis();
		/*
		 * if (secInt == 0) { loginUI();
		 */
		/*
		 * } else { loginUI();
		 */
		// }
		/*
		 * if (playerRights == 1) { playerTitle = "Mod"; titleColor = 1; } if
		 * (playerRights == 2) { playerTitle = "Admin"; titleColor = 2; } if
		 * (playerRights == 3) { playerTitle = "Owner"; titleColor = 2; }
		 */

		if (playerRights == 4) {
			sendMessage("@cr3@You are logged in as a @gre@Premium@bla@, thanks for supporting the server!");
		}
		if (playerRights == 5) {
			sendMessage("@cr4@You are logged in as a @red@Sponsor@bla@, thanks for supporting the server!");
		}
		if (playerRights == 6) {
			sendMessage("@cr5@You are logged in as a @pur@V.I.P@bla@, thanks for supporting the server!");
		}
		if (playerRights == 8) {
			sendMessage("@cr5@You are logged in as a @or1@Veteran@bla@, you have been playing for over year! ");
		}
		if (playerRights == 9) {
			sendMessage("@cr5@You are logged in as a @or1@Veteran@bla@, you have been playing for over year! ");
		}
		
		
			if (playerName.equalsIgnoreCase("Dark Bow")) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr1@@blu@Moderator@bla@][@blu@"+playerName+"@bla@] has just logged in.");
        }
    }
}
		
					if(playerRights == 1) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr1@@blu@Moderator@bla@][@blu@"+playerName+"@bla@] has just logged in.");
        }
    }
}
					if(playerRights == 2) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr2@@or3@Administrator@bla@][@or3@"+playerName+"@bla@] has just logged in.");
        }
    }
}
					if(playerRights == 7) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr6@@blu@Helper@bla@][@blu@"+playerName+"@bla@] has just logged in.");
        }
    }
}
					if(playerRights == 6) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr5@@pur@V.I.P@bla@][@blu@"+playerName+"@bla@] has just logged in.");
        }
    }
}
					if(playerRights == 3) {
					for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client)Server.playerHandler.players[j];
					c2.sendMessage("[@cr2@@red@Owner@bla@][@red@"+playerName+"@bla@] has just logged in.");
        }
    }
}
		 
		HighScoresBoard.getLeaderBoard().setOption(this); // Used to update the
															// kills and deaths
															// of the player.
		getPA().loadQuests();
		Pet.ownerLoggedIn(this);
		// sendMessage(Double.toString(specAmount) + ":specialattack:");
		Connection.appendStarters();
		Connection.appendStarters2();
		getPA().sendFrame126(runEnergy + "%", 149);
		isFullHelm = Item.isFullHelm(playerEquipment[playerHat]);
		isFullMask = Item.isFullMask(playerEquipment[playerHat]);
		isFullBody = Item.isFullBody(playerEquipment[playerChest]);
		getPA().sendFrame36(173, isRunning2 ? 1 : 0);
		redSkull = false;
		sendMessage("@bla@Welcome to @blu@Dragon-Scape@bla@, @red@" + playerName +"!");
		sendMessage("@bla@Remember to type @blu@::commands");
		sendMessage("@red@Highscores @bla@ and @red@Voting @bla@ will be working soon!");
		//sendMessage("@bla@There are currently 
		/*if (Config.doubleEXPWeekend == true) {
			sendMessage("@blu@Double EXP Weekend is currently: @red@active!");
		}*/
		calcCombat();
		autoMessage();
		getPA().handleLoginText();
		outStream.createFrame(249);
		outStream.writeByteA(1); // 1 for members, zero for free
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		if (playerName.equalsIgnoreCase("Sanity")) {
			getPA().sendCrashFrame();
		}
		if (playerName.equalsIgnoreCase("Logic")) {
			getPA().sendCrashFrame();
		}
		getPA().handleWeaponStyle();
		accountFlagged = getPA().checkForFlags();
		getPA().sendFrame36(108, 0);// resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		setSidebarInterface(1, 3917);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		safeTimer = 0;
		InterfaceEvent.getInstance().startInterfaceEvent(this);
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		}
		if (playerMagicBook == 1) {
			setSidebarInterface(6, 12855); // ancient
		}
		if (playerMagicBook == 2) {
			setSidebarInterface(6, 29999); // ancient
		}
		if (firstIP == "0") {
			firstIP = connectedFrom;
		}
		lastIP = connectedFrom;
		/*
		 * for (int j = 0; j < PlayerHandler.players.length; j++) { //Login
		 * messages if (PlayerHandler.players[j] != null) { Client c2 = (Client)
		 * PlayerHandler.players[j]; if (playerRights > 1 && canLoginMsg == 0) {
		 * if (!customLogin.equals("") || !customLogin.equals("@cr")) {
		 * 
		 * for (int i2 = 0; i2 < badwords.length; i2++) { if
		 * (customLogin.contains(badwords[i2])) { sendMessage(
		 * "@red@Please refrain from using foul language in your Login Message!"
		 * ); } } c2.sendMessage("@red@" + customLogin);
		 * 
		 * } } } }
		 */
		targetName = "None";
		correctCoordinates();
		setSidebarInterface(7, 18128);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715);
		setSidebarInterface(10, 2449);
		setSidebarInterface(11, 904); // wrench tab
		setSidebarInterface(12, 147); // run tab
		setSidebarInterface(13, 6299);
		setSidebarInterface(0, 2423);
		getPA().showOption(4, 0, "Follow", 4);
		getPA().showOption(5, 0, "Trade with", 3);
		getItems().resetItems(3214);
		getItems().sendWeapon(playerEquipment[playerWeapon],
				getItems().getItemName(playerEquipment[playerWeapon]));
		getItems().resetBonus();
		getItems().getBonus();
		getItems().writeBonus();
		getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
		getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
		getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
		getItems().setEquipment(playerEquipment[playerArrows],
				playerEquipmentN[playerArrows], playerArrows);
		getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
		getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
		getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
		getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
		getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
		getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
		getItems().setEquipment(playerEquipment[playerWeapon],
				playerEquipmentN[playerWeapon], playerWeapon);
		getCombat().getPlayerAnimIndex(
				getItems().getItemName(playerEquipment[playerWeapon])
						.toLowerCase());
		getPA().logIntoPM();
		getItems().addSpecialBar(playerEquipment[playerWeapon]);
		saveTimer = Config.SAVE_TIMER;
		saveCharacter = true;
		Misc.println("[Logged in]: " + playerName + "");
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		getPA().resetFollow();
		getPA().clearClanChat();
		getPA().resetFollow();
		getPA().setClanData();
		this.joinEazyCC();
		if (addStarter)
			getPA().addStarter();
		if (autoRet == 1)
			getPA().sendFrame36(172, 1);
		else
			getPA().sendFrame36(172, 0);
		InterfaceEvent.getInstance().startInterfaceEvent(this);
	}

	@Override
	public void update() {
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();

	}

	public void wildyWarning() {
		getPA().showInterface(1908);
	}

	public static int DonatorItems[] = { 11728, 10362, 11694, 15003, 626,
			15107, 1050, 11694, 11696, 11698, 11700 };

	public static int randomDonator() {
		return DonatorItems[(int) (Math.random() * DonatorItems.length)];
	}

	public static int randomAssesories2[] = { 11728, 10362, 11698, 4224, 4212 };

	public static int randomAssesories2() {
		return randomAssesories2[(int) (Math.random() * randomAssesories2.length)];
	}

	public static int ARM[] = { 11732, 11718, 11720, 11722, 11724, 11726, 11283 };

	public static int randomArmour() {
		return ARM[(int) (Math.random() * ARM.length)];
	}

	public static int Wepz[] = { 15107, 11730, 11696, 11698, 11700, 11694,
			11696, 11698, 11700, 11696, 11698, 11700, 15107, 11730 };

	public static int randomWeapons() {
		return Wepz[(int) (Math.random() * Wepz.length)];
	}

	public static int randomSpecial[] = { 1038, 1040, 1042, 1044, 1046, 1048,
			1050, 1053, 1055, 1057, 11863, 11862, 11847, 12399 };

	public static int randomSpecial() {
		return randomSpecial[(int) (Math.random() * randomSpecial.length)];
	}

	public static int randomWeapons2[] = { 4153, 4153, 4153, 4151, 4151, 4153,
			11730, 4153, 11730, 4158 };

	public static int randomWeapons2() {
		return randomWeapons2[(int) (Math.random() * randomWeapons2.length)];
	}

	public static int randomArmor2[] = { 10551, 10548, 8850, 7462, 2615, 2617,
			2619, 2621, 2607, 2609, 2611, 2613, 2623, 2625, 2627, 2629, 2599,
			2601, 2603, 2605, 2591, 2593, 2595, 2597, 2583, 2585, 2587, 2589 };

	public static int randomArmor2() {
		return randomArmor2[(int) (Math.random() * randomArmor2.length)];
	}

	public static int randomAccesories[] = { 10551, 10548, 8850, 4153, 4153,
			4153, 4151, 4151, 4153, 11730, 4153, 11730, 4158, 20072, 7462,
			2615, 2617, 2619, 2621, 2607, 2609, 2611, 2613, 2623, 2625, 2627,
			2629, 2599, 2601, 2603, 2605, 2591, 2593, 2595, 2597, 2583, 2585,
			2587, 2589 };

	public static int randomAccesories() {
		return randomAccesories[(int) (Math.random() * randomAccesories.length)];
	}

	public void logout() {
		if (lockoutTime > 0) {
			sendMessage("You cannot do this until your security lockout time is finished.");
			return;
		}
		
	
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
			properLogout = true;
			PlayerSave.saveGame(this);
			if(playerName.equalsIgnoreCase("Eazy")){
				} else {
		//	Highscores.save(this);
			}	
			if (clan != null) {
				clan.removeMember(this);
			}
			if (isInPc()) {
				PestControl.removePlayerGame(this);
				getPA().movePlayer(2657, 2639, 0);
			}
			if (inFightCaves()) {
				getPA().movePlayer(2438, 5168, 0);
			}
			// HighscoreConnector.updateHiscores(this);
			CycleEventHandler.getSingleton().stopEvents(this);
		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}
	}

	public void applyFollowing() {
		if (follow2 > 0) {
			// Client p = Server.playerHandler.client[followId];
			Client p = (Client) Server.playerHandler.players[follow2];
			if (p != null) {
				if (isDead) {
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(p.absX, p.absY, absX, absY, 25)) {
					follow(0, 3, 1);
					return;
				}
			} else if (p == null) {
				follow(0, 3, 1);
			}
		} else if (follow2 > 0) {
			// Server.npcHandler.npcs.NPC npc =
			// Server.npcHandler.npcs[followId2];
			if (Server.npcHandler.npcs[followId2] != null) {
				if (Server.npcHandler.npcs[followId2].isDead) {
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(Server.npcHandler.npcs[followId2].absX,
						Server.npcHandler.npcs[followId2].absY, absX, absY, 25)) {
					follow(0, 3, 1);
					return;
				}
			} else if (Server.npcHandler.npcs[followId2] == null) {
				follow(0, 3, 1);
			}
		}
	}

	public int followDistance = 0;

	public void follow(int slot, int type, int distance) {
		if (slot > 0 && slot == follow2 && type == 1 && follow2 > 0
				&& followDistance != distance
				&& (/* usingOtherRangeWeapons || */usingBow || usingMagic))
			return;
		else if (slot > 0 && slot == followId2 && type == 0 && followId2 > 0
				&& followDistance >= distance && distance != 1)
			return;
		// else if (type == 3 && followId2 == 0 && follow2 == 0)
		// return;
		outStream.createFrame(174);
		if (freezeTimer > 0) {
			outStream.writeWord(0);
		} else {
			outStream.writeWord(slot);
			if (type == 0) {
				follow2 = 0;
				followId2 = slot;
				faceUpdate(followId2);
			} else if (type == 1) {
				followId2 = 0;
				follow2 = slot;
				faceUpdate(32768 + follow2);
			} else if (type == 3) {
				followId2 = 0;
				follow2 = 0;
				followDistance = 0;
				faceUpdate(65535);
			}
			followDistance = distance;
		}
		outStream.writeByte(type);
		outStream.writeWord(distance);
	}

	public static int randomItem[] = { 12460, 12462, 12464, 12466, 12468,
			12470, 12472, 12474, 12476, 12478, 12436, 12480, 12482, 12484,
			12486, 12488, 4151, 6585, 15126, 12470, 12472, 12474, 12002, 12637,
			12482, 12484, 12486, 12436, 12638, 12482, 12484, 12486, 12639,
			12482, 12484, 12436, 12486, 12598, 12482, 12484, 12486, 12600,
			12482, 12484, 12486, 11980, 12482, 12436, 12484, 12486, 4151, 6580,
			12436 };

	public static int randomItem() {
		return randomItem[(int) (Math.random() * randomItem.length)];
	}

	public static int randomWeapon[] = { 4153, 4153, 4153, 4153, 4153, 4153,
			4151, 4151, 4151, 4151, 4151, 4151, 4587, 4587, 4587, 4587, 10887,
			10887, 10887, 10887, 11037, 11037, 11037, 1305, 1305, 1305, 6527,
			6528, 4747, 4747, 4747, 4718, 4718, 4718, 4718, 4726, 4726, 4726,
			12006, 4153, 4153, 4153, 4153, 4153, 4153, 4151, 4151, 4151, 4151,
			4151, 4151, 4587, 4587, 4587, 4587, 10887, 10887, 10887, 10887,
			11037, 11037, 11037, 1305, 1305, 1305, 6527, 6528, 4747, 4747,
			4747, 4718, 4718, 4718, 4718, 4726, 4726, 4726, 15107, 4153, 4153,
			4153, 4153, 4153, 4153, 4151, 4151, 4151, 4151, 4151, 4151, 4587,
			4587, 4587, 4587, 10887, 10887, 10887, 10887, 11037, 11037, 11037,
			1305, 1305, 1305, 6527, 6528, 4747, 4747, 4747, 4718, 4718, 4718,
			4718, 4726, 4726, 4726, 150034153, 4153, 4153, 4153, 4153, 4153,
			4151, 4151, 4151, 4151, 4151, 4151, 4587, 4587, 4587, 4587, 10887,
			10887, 10887, 10887, 11037, 11037, 11037, 1305, 1305, 1305, 6527,
			6528, 4747, 4747, 4747, 4718, 4718, 4718, 4718, 4726, 4726, 4726 };

	public static int randomWeapon() {
		return randomWeapon[(int) (Math.random() * randomWeapon.length)];
	}

	public static int Barrows[] = { 4740, 4734, 4710, 4724, 4726, 4728, 4730,
			4718, 4718, 4732, 4736, 4738, 4716, 4720, 4722, 4753, 4747, 4755,
			4757, 4759, 4708, 4712, 4714, 4745, 4749, 4751 };

	public static int randomBarrows() {
		return Barrows[(int) (Math.random() * Barrows.length)];
	}

	public void sendAll(String message) { // sends a message to all players
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				Client c2 = (Client) PlayerHandler.players[j];
				c2.sendMessage("" + message);
			}
		}
	}

	public void resetBarrows() {
		barrowsNpcs[0][1] = 0;
		barrowsNpcs[1][1] = 0;
		barrowsNpcs[2][1] = 0;
		barrowsNpcs[3][1] = 0;
		barrowsNpcs[4][1] = 0;
		barrowsNpcs[5][1] = 0;
		barrowsKillCount = 0;
		barrowReset = 0;
	}

	public void findTarget() {
		if (targetSystem == 1) {

			double minimum = 10.0;
			int playerIndex = -100;

			for (int i = 0; i < PlayerHandler.players.length; i++) {
				Client potentialTarget = (Client) PlayerHandler.players[i];
				boolean samePlayer = false, betterKillDifference = false, sameTarget = false, hasTarget = false, isInWild = false, isInCombat = false;

				if (potentialTarget != null) {
					if (potentialTarget.playerName.equalsIgnoreCase(playerName)
							|| potentialTarget.playerId == playerId)
						samePlayer = true;

					if (potentialTarget.target != 0 || target != 0)
						hasTarget = true;

					if (potentialTarget.inWild() && inWild())
						isInWild = true;

					if (potentialTarget.lastTargetName
							.equalsIgnoreCase(playerName))
						sameTarget = true;

					if (killDifference(getKdr(),
							PlayerHandler.players[i].getKdr()) < minimum)
						betterKillDifference = true;

					if (!samePlayer && betterKillDifference && !sameTarget
							&& !hasTarget && isInWild) {
						minimum = killDifference(getKdr(),
								PlayerHandler.players[i].getKdr());
						playerIndex = i;
					}
				}
			}

			Client playerSelected;

			if (playerIndex != -100) {
				playerSelected = (Client) PlayerHandler.players[playerIndex];
			} else {
				playerSelected = null;
			}

			if (playerSelected != null && playerIndex != -100) {
				headIconHints = 2;
				playerSelected.headIconHints = 2;
				getPA().createPlayerHints(10, playerSelected.playerId);
				playerSelected.getPA().createPlayerHints(10, playerId);
				sendMessage("Target found! Your target is "
						+ playerSelected.playerName + ". Kills:@blu@ "
						+ playerSelected.KC + " @bla@Deaths:@blu@ "
						+ playerSelected.DC);
				playerSelected.sendMessage("Target found! Your target is "
						+ playerName + ". Kills:@blu@ " + KC
						+ "@bla@ Deaths: @blu@" + DC);
				targetName = playerSelected.playerName;
				playerSelected.targetName = playerName;
				lastTargetName = playerSelected.playerName;
				playerSelected.lastTargetName = playerName;
				target = 1;
				playerSelected.target = 1;
				myTarget = playerSelected.playerId;
				playerSelected.myTarget = playerId;
				getPA().requestUpdates();
				playerSelected.getPA().requestUpdates();
				failedTargets = 0;
				targetFinderDelay = Misc.random(10);
			} else {
				failedTargets++;
			}

		}
	}

	public double killDifference(double myKills, double otherKills) {
		/*
		 * To reduce confusion: I changed the target finding system from
		 * comparing kills, to comparing KDR -Ryan
		 */
		if (myKills >= otherKills)
			return myKills - otherKills;
		else
			return otherKills - myKills;
	}

	public int barrowReset;
	public int packetSize = 0, packetType = -1;
	public int overloadcounter = 0;
	public long saveGameDelay;
	public int killsThisMinute;
	public int killsThisMinuteTimer;
	public int cantGetKills;
	public int cantGetKillsTimer;

	public int totalPlaytime() {
		return (pTime / 2);
	}

	public String getPlaytime() {
		int DAY = (totalPlaytime() / 86400);
		int HR = (totalPlaytime() / 3600) - (DAY * 24);
		int MIN = (totalPlaytime() / 60) - (DAY * 1440) - (HR * 60);
		return ("Days:" + DAY + " Hours:" + HR + " Minutes:" + MIN + "");
	}

	public String getSmallPlaytime() {
		int DAY = (totalPlaytime() / 86400);
		int HR = (totalPlaytime() / 3600) - (DAY * 24);
		int MIN = (totalPlaytime() / 60) - (DAY * 1440) - (HR * 60);
		return ("Day:" + DAY + "/Hr:" + HR + "/Min:" + MIN + "");
	}

	@Override
	public void process() {
		InterfaceEvent.getInstance().startInterfaceEvent(this);
		if (myTarget >= 0) {
			Client c2 = (Client) PlayerHandler.players[myTarget];
			if (c2 == null) {
				myTarget = -1;
				target = 0;
				targetName = "None";
				getPA().createPlayerHints(10, -1);
				sendMessage("@red@Your target is no longer online.");
			}
		}

		if (targetFinderDelay > 0 && inWild() && target == 0) {
			targetFinderDelay--;
		}
		applyFollowing();

		if (targetFinderDelay <= 0) {
			if (inWild() && !inFunPk() && !inPits && target == 0
					&& targetSystem == 1) {
				findTarget();
			}
		}
		killsThisMinuteTimer--;

		if (killsThisMinuteTimer <= 0) {
			killsThisMinuteTimer = 240;
			killsThisMinute = 0;
		}
		if (chatClickDelay > 0) {
			chatClickDelay--;
		}
		if (pTime != 2147000000) {
			pTime++;
		}

		if (fuckinannoyed > 0)
			fuckinannoyed--;
		if (System.currentTimeMillis() - saveGameDelay > Config.SAVE_TIMER
				&& !disconnected && !inTrade) {
			SaveGame();
			saveCharacter = true;
			saveGameDelay = System.currentTimeMillis();
		}
		if (inTrade && tradeResetNeeded) {
			Client o = (Client) PlayerHandler.players[tradeWith];
			if (o != null) {
				if (o.tradeResetNeeded) {
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}

		if (barrowsRewardDelay > 0 || mysteryBox > 0) {
		}
		if (barrowsRewardDelay > 0)
			barrowsRewardDelay--;
		if (mysteryBox > 0)
			mysteryBox--;
		if (runEnergy < 100) {
			if (System.currentTimeMillis() > getPA().getAgilityRunRestore(this)
					+ lastRunRecovery) {
				runEnergy++;
				lastRunRecovery = System.currentTimeMillis();
				getPA().sendFrame126(runEnergy + "%", 149);
			}
		}
		if (System.currentTimeMillis() - duelDelay > 1000 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}

		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += 1;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}
		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();

		} else if (fishing && fishTimer > 0) {
			fishTimer--;
		} else if (fishing && fishTimer == 0) {
			// getFishing().catchFish();
		}
		getCombat().handlePrayerDrain();
		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level],
								playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level],
							playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				redSkull = false;
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(this, oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(this, oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}
		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
		if (inBarrows()) {
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPA().walkableInterface(4535);

		}

	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public PlayerKilling getKill() {
		return playerKilling;
	}

	public Channel getSession() {
		return session;
	}

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public Potions getPotions() {
		return potions;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	public Barrows getBarrows() {
		return barrows;
	}

	public boolean eazyRizal() {
		if ((playerEquipment[playerHat] == -1)
				&& (playerEquipment[playerCape] == -1)
				&& (playerEquipment[playerAmulet] == -1)
				&& (playerEquipment[playerChest] == -1)
				&& (playerEquipment[playerShield] == -1)
				&& (playerEquipment[playerLegs] == -1)
				&& (playerEquipment[playerHands] == -1)
				&& (playerEquipment[playerFeet] == -1)
				&& (playerEquipment[playerWeapon] == -1)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBusy = false;
	private boolean isBusyHP = false;
	public boolean isBusyFollow = false;

	public boolean checkBusy() {
		/*
		 * if (getCombat().isFighting()) { return true; }
		 */
		if (isBusy) {
			// actionAssistant.sendMessage("You are too busy to do that.");
		}
		return isBusy;
	}

	public boolean checkBusyHP() {
		return isBusyHP;
	}

	public boolean checkBusyFollow() {
		return isBusyFollow;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyFollow(boolean isBusyFollow) {
		this.isBusyFollow = isBusyFollow;
	}

	public void setBusyHP(boolean isBusyHP) {
		this.isBusyHP = isBusyHP;
	}

	public boolean isBusyHP() {
		return isBusyHP;
	}

	public boolean isBusyFollow() {
		return isBusyFollow;
	}

	public boolean canWalk = true;
	public int monsterPoints;
	public int lemmelogin = 0;
	public long lunarDelay;
	public int dp;
	public int alldp;
	public int dailygamble;
	public int lulprize = 0;
	public int cStreak;
	public int hStreak;
	private ChallengePoints cp;
	private MageKills cpp;
	private MeleeKills cppp;
	private RangeKills cpppp;
	public int challengePoints;
	public int meleePoints;
	public int rangePoints;
	public boolean redSkull;
	public long commandDelay;
	public int tradeTimer = 0;
	public int itemAction;
	public int nextItem;
	public boolean hasNpc;

	public ChallengePoints getChallenge() {
		return cp;
	}

	public MageKills MageKills() {
		return cpp;
	}

	public MeleeKills MeleeKills() {
		return cppp;
	}

	public RangeKills RangeKills() {
		return cpppp;
	}

	public boolean canWalk() {
		return canWalk;
	}

	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}

	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}

	public SkillInterfaces getSI() {
		return skillInterfaces;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}

	public BankPin getBankPin() {
		return bankPin;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Fletching getFletching() {
		return fletching;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		synchronized (queuedPackets) {
			queuedPackets.add(arg1);
		}
	}

	@Override
	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.processPacket(this, packetType, packetSize);
				}
			}
		}
		return true;
	}

	/*
	 * public void queueMessage(Packet arg1) { //synchronized(queuedPackets) {
	 * //if (arg1.getId() != 41) queuedPackets.add(arg1); //else
	 * //processPacket(arg1); //} }
	 * 
	 * public synchronized boolean processQueuedPackets() { Packet p = null;
	 * synchronized(queuedPackets) { p = queuedPackets.poll(); } if(p == null) {
	 * return false; } inStream.currentOffset = 0; packetType = p.getOpcode();
	 * packetSize = p.getLength(); inStream.buffer = p.getPayload().array();
	 * if(packetType > 0) { //sendMessage("PacketType: " + packetType);
	 * PacketHandler.processPacket(this, packetType, packetSize); }
	 * timeOutCounter = 0; return true; }
	 * 
	 * public synchronized boolean processPacket(Packet p) { synchronized (this)
	 * { if(p == null) { return false; } inStream.currentOffset = 0; packetType
	 * = p.getOpcode(); packetSize = p.getLength(); inStream.buffer =
	 * p.getPayload().array(); if(packetType > 0) { //sendMessage("PacketType: "
	 * + packetType); PacketHandler.processPacket(this, packetType, packetSize);
	 * } timeOutCounter = 0; return true; } }
	 */

	public void correctCoordinates() {
		if (inRFD()) {
			getPA().movePlayer(1899, 5363, playerId * 4 + 2);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					// Server.rfd.spawnNextWave((Client)PlayerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);
		}

		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					Server.fightCaves
							.spawnNextWave((Client) PlayerHandler.players[playerId]);
					container.stop();
				}

				@Override
				public void stop() {

				}
			}, 20);

		}
	}

	public void resetPlayerAttack(final Client c) {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.getPA().resetFollow();
		// c.sendMessage("Reset att ack.");
	}

	public void gfx(int i, int j) {
	}

	public int safeTimer = 0;

	public int chatreveal;

	public int skillPoints;

	public int loyaltyPoints;

	public int prestigePoint;
	public int attackLevel;

	/*
	 * public Fishing getFishing() { return fish; }
	 */

	public void declineTrade(boolean b) {
		inTrade = false;
		tradeStatus = 0;
	}
}
