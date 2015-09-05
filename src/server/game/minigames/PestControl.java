package server.game.minigames;

import java.util.HashMap;

import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.util.Misc;

/**
 * @author Harlan
 * Credits to Tyler (Zivik), Sanity
 */

public class PestControl {

	/**
    /**
	 * how long before were put into the game from lobby
	 */
	public static final int WAIT_TIMER = 10;
	/**
	 * How many players we need to start a game
	 */
	public final static int PLAYERS_REQUIRED = 1;
	/**
	 * Hashmap for the players in lobby
	 */
	public static HashMap<Client, Integer> waitingBoat = new HashMap<Client, Integer>();
	private static HashMap<Client, Integer> gamePlayers = new HashMap<Client, Integer>();

	public static int gameTimer = -1;
	public static int waitTimer = 10;
	public static boolean gameStarted = false;
	private int properTimer = 0;
	public static int KNIGHTS_HEALTH = -1;

	/**
	 * Array used for storing the portals health
	 */
	public static int[] portalHealth = {
		200,
		200,
		200,
		200
	};
	public static int[] portals = {
		6142,
		6143,
		6144,
		6145
	};

	public static void handleDeath(Client client) {
		client.getPA().movePlayer(2657, 2612, 0);
	}
	
	public static void purchaseExperience(Client client, int skill) {
		int[] skills = {0, 1, 2, 3, 4, 5, 6};
		String[] names = {"Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer", "Magic"};
		int value = 100000;
		int cost = 100;
		if (client.pcPoints < cost) {
			client.sendMessage("You need a minimum of 100 pest control points to purchase experience");
			client.dialogueAction = -1;
			client.getPA().closeAllWindows();
			return;
		}
		client.pcPoints -= cost;
		for (int i = 0; i < skills.length; i++) {
			if (skills[i] == skill) {
				client.getPA().requestUpdates();
				client.getPA().addSkillXP(value, skill);
				client.getPA().refreshSkill(skill);
				client.sendMessage("You purchase 100,000 experience in "+names[i]+" for 100 pest control points.");
				client.getPA().closeAllWindows();
				client.dialogueAction = -1;
			}
		}
	}
	
	public int getPointValue(Client player) {
		int[] damage = {800, 700, 500, 350, 250, 100, 50, 30};
		for (int i = 0; i < damage.length; i++) {
			if (player.pcDamage >= damage[i]) {
				return 8-i;
			}
		}
		return 0;
	}
	/**
	 * array used for storing the npcs used in the minigame
	 * @order npcId, xSpawn, ySpawn, health
	 */
	public int shifter = 3732 + Misc.random(9);
	public int brawler = 3772 + Misc.random(4);
	public int defiler = 3762 + Misc.random(9);
	public int ravager = 3742 + Misc.random(4);
	public int torcher = 3752 + Misc.random(7);

	private int[][] pcNPCData = {
			{6142,2628,2591}, //portal
			{6143,2680,2588}, //portal
			{6144,2669,2570}, //portal
			{6145,2645,2569}, //portal
			{3782,2656,2592}, //knight
	};
	private int[][] voidMonsterData = {
			{shifter, 2660 + Misc.random(4), 2592 + Misc.random(4)},
			{brawler,2663 + Misc.random(4),2575 + Misc.random(4)},
			{defiler,2656 + Misc.random(4),2572 + Misc.random(4)},
			{ravager,2664 + Misc.random(4),2574 + Misc.random(4)},
			{torcher,2656 + Misc.random(4),2595 + Misc.random(4)},
			{ravager,2634 + Misc.random(4),2596 + Misc.random(4)},
			{brawler,2638 + Misc.random(4),2588 + Misc.random(4)},
			{shifter,2637 + Misc.random(4),2598 + Misc.random(4)},
			{ravager,2677 + Misc.random(4),2579 + Misc.random(4)},
			{defiler,2673 + Misc.random(4),2584 + Misc.random(4)},
			{defiler,2673 + Misc.random(4),2584 + Misc.random(4)},
			{defiler,2675 + Misc.random(4),2591 + Misc.random(4)}
	};

	public void process() {//god knows why he is using process
		try {
			if (properTimer > 0) {
				properTimer --;
				return;
			} else {
				properTimer = 1;
			}
			setBoatInterface();
			if (waitTimer > 0)
				waitTimer--;
			else if (waitTimer == 0)
				startGame();
			if (KNIGHTS_HEALTH == 0)
				endGame(false);
			if (gameStarted && playersInGame() < 1)  {
				endGame(false);
			}
			if (gameTimer > 0 && gameStarted) {
				gameTimer--;
				if (KNIGHTS_HEALTH > 4)
					KNIGHTS_HEALTH -= Misc.random(3)+1;
				else
					endGame(false);
				setGameInterface();
				if (allPortalsDead() || allPortalsDead3())
					endGame(true);
			} else if (gameTimer <= 0 && gameStarted)
				endGame(false);
		} catch (RuntimeException e) {
			System.out.println("[PC] Failed to set process!");
			e.printStackTrace();
		}
	}//i knew what i wass doing :P 
	/**
	 * Method we use for removing a player from the pc game
	 * @param player The Player.
	 */
	 public static void removePlayerGame(Client player) {
		 if (gamePlayers.containsKey(player)) {
			 player.getPA().movePlayer(2657, 2639, 0);
			 gamePlayers.remove(player);
		 }
	 }

	/**
	 * Setting the interfaces for the waiting lobby
	 */
	 private void setBoatInterface() {
		 try {
			 for (Client c : waitingBoat.keySet()) {
				 if (c != null) {
					 try {
						// System.out.println("IN PC BOAT - send walkable interface");
						 if (gameStarted) {
							 c.getPA().sendFrame126("Next Departure: " + (waitTimer + gameTimer)/60 + " minutes", 21120);
						 } else {
							 c.getPA().sendFrame126("Next Departure: " + waitTimer + "", 21120);
						 }
						 c.getPA().sendFrame126("Players Ready: " + playersInBoat() + "", 21121);
						 c.getPA().sendFrame126("(Need " + PLAYERS_REQUIRED + " to 25 players)", 21122);
						 c.getPA().sendFrame126("Points: " + c.pcPoints + "", 21123);
						 c.getPA().walkableInterface(21119);
						 switch (waitTimer) {
						 case 10:
							 c.sendMessage("Next game will start in: 10 seconds.");
							 break;
						 }
					 } catch (RuntimeException e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
					 }
				 }
			 }
		 } catch (RuntimeException e) {
			 System.out.println("Failed to set interfaces");
			 e.printStackTrace();
		 }
	 }


	 /**
	  * Setting the interface for in game players
	  * ok ill load this another way
	  */
	 public void setGameInterface() {
		 for (Client player : gamePlayers.keySet()) {
			 if (player != null) {
				 for (int i = 0; i < portalHealth.length; i++) {
					 if (portalHealth[i] > 0) {
						 player.getPA().sendFrame126("" + portalHealth[i] + "", 21111 + i);
					 } else
						 player.getPA().sendFrame126("Dead", 21111 + i);

				 }//ffs ! man 
				 player.getPA().sendFrame126("" + KNIGHTS_HEALTH, 21115);
				 player.getPA().sendFrame126("" + player.pcDamage, 21116);
				 if (gameTimer > 60) {
					 player.getPA().sendFrame126("Time remaining: " + (gameTimer/60) + " minutes", 21117);
				 } else {
					 player.getPA().sendFrame126("Time remaining: " + gameTimer + " seconds", 21117);
				 }
				 player.getPA().walkableInterface(21100);
			 }
		 }
	 }

	 public static void setGameInterface2() {
		 for (Client player : gamePlayers.keySet()) {
			 if (player != null) {
				 for (int i = 0; i < portalHealth.length; i++) {
					 player.getPA().sendFrame126(" ", 21111 + i);
				 }
				 player.getPA().sendFrame126(" ", 21115);
				 player.getPA().sendFrame126(" ", 21116);
				 player.getPA().sendFrame126(" ", 21117);
			 }
			 player.getPA().sendFrame126("@or2@Pest Control Points: " + player.pcPoints + "",
					 16028);
		 }
	 }
	 /***
	  * Moving players to arena if there's enough players
	  */
	 private void startGame() {
		 if (playersInBoat() < PLAYERS_REQUIRED) {
			 waitTimer = WAIT_TIMER;
			 return;
		 }
		 for (int i = 0; i < portalHealth.length; i++)
			 portalHealth[i] = 200;
		 gameTimer = 400;
		 KNIGHTS_HEALTH = 500;
		 waitTimer = -1;
		 spawnNPC();
		 gameStarted = true;
		 for (Client player : waitingBoat.keySet()) {
			 int team = waitingBoat.get(player);
			 if (player == null) {
				 continue;
			 }
			 if (!player.inPcBoat() && waitingBoat.containsKey(player)) {
				 waitingBoat.remove(player);
			 }
			 player.getPA().movePlayer(2656 + Misc.random3(3), 2614 - Misc.random3(4), 0);
			 player.sendMessage("@red@The pest control game has started!");
			 gamePlayers.put(player, team);
		 }

		 waitingBoat.clear();
	 }

	 /**
	  * Checks how many players are in the waiting lobby
	  * @return players waiting
	  */
	 public int playersInBoat() {
		 int players = 0;
		 for (Client player : waitingBoat.keySet()) {
			 if (player != null) {
				 players++;
			 }
			 if (player == null) {
				 players--;
			 }
		 }
		 return players;
	 }
	 /**
	  * Checks how many players are in the game
	  * @return players in the game
	  */
	 private int playersInGame() {
		 int players = 0;
		 for (Client player : gamePlayers.keySet()) {
			 if (player != null) {
				 players++;
			 } 
			 if (player == null) {
				 players--;
			 }
		 }
		 return players;
	 }
	 /**
	  * Ends the game
	  * @param won Did you win?
	  */
	 private void endGame(boolean won) {
		 for (Client player : gamePlayers.keySet()) {
			 if (player == null) {
				 continue;
			 }
			 player.getPA().movePlayer(2657, 2639, 0);
			 if (won && player.pcDamage > 10) {
				 int POINT_REWARD = getPointValue(player);
				 player.sendMessage("You have won the pest control game and have been awarded " + POINT_REWARD + " Pest Control points.");
				 player.pcPoints += POINT_REWARD;
				 player.getItems().addItem(995, player.combatLevel * 10);
			 } else if (won) {
				 player.sendMessage("The void knights notice your lack of zeal. You gain no points for this round.");
			 } else {
				 player.sendMessage("You failed to kill all the portals in 3 minutes and have not been awarded any points.");
			 }
		 }
		 setGameInterface2();
		 cleanUpPlayer();
		 cleanUp();
	 }

	 /**
	  * Resets the game variables and map
	  */
	 private void cleanUp() {
		 gameTimer = -1;
		 KNIGHTS_HEALTH = -1;
		// getPA().movePlayer(2657, 2639, 0);
		 waitTimer = WAIT_TIMER;
		 gameStarted = false;
		 gamePlayers.clear();
		 /*
		  * Removes the npcs from the game if any left over for whatever reason
		  */
		 for (int[] aPcNPCData : pcNPCData) {
			 for (int j = 0; j < NPCHandler.npcs.length; j++) {
				 if (NPCHandler.npcs[j] != null) {
					 if (NPCHandler.npcs[j].npcType == aPcNPCData[0])
						 NPCHandler.npcs[j] = null;
				 }
			 }
		 }
		 for (int[] aPcNPCData : voidMonsterData) {
			 for (int j = 0; j < NPCHandler.npcs.length; j++) {
				 if (NPCHandler.npcs[j] != null) {
					 if (NPCHandler.npcs[j].npcType == aPcNPCData[0])
						 NPCHandler.npcs[j] = null;
				 }
			 }
		 }
	 }
	 /**
	  * Cleans the player of any damage, loss they may of received
	  */
	 private void cleanUpPlayer(){
		 for (Client player : gamePlayers.keySet()) {
			 player.poisonDamage = 0;
			 player.getPA().movePlayer(2657, 2639, 0);
			 player.getCombat().resetPrayers();
			 for (int i = 0; i < 24; i++) {
				 player.playerLevel[i] = player.getPA().getLevelForXP(player.playerXP[i]);
				 player.getPA().refreshSkill(i);
			 }
			 player.specAmount = 10;
			 player.sendMessage(Double.toString(player.specAmount) + ":specialattack:");
			 player.getPA().refreshSkill(5);
			 player.pcDamage = 0;
			 player.getItems().addSpecialBar(player.playerEquipment[player.playerWeapon]);
		 }
	 }
	 /**
	  * Checks if the portals are dead
	  * @return players dead
	  */
	 private static boolean allPortalsDead() {
		 int count = 0;
		 for (int aPortalHealth : portalHealth) {
			 if (aPortalHealth <= 0)
				 count++;
			 //System.out.println("Portal Health++" + count);
		 }
		 return count >= 4;
	 }
	 public boolean allPortalsDead3() {
		 int count = 0;
		 for (int j = 0; j < NPCHandler.npcs.length; j++) {
			 if (NPCHandler.npcs[j] != null) {
				 if (NPCHandler.npcs[j].npcType > 6141 && NPCHandler.npcs[j].npcType < 6146)
					 if (NPCHandler.npcs[j].needRespawn)
						 count++;
			 }
		 }
		 return count >= 4;
	 }

	 /**
	  * Moves a player out of the waiting boat
	  * @param c Client c
	  */
	 public static void leaveWaitingBoat(Client c) {
		 if (waitingBoat.containsKey(c)) {
			 waitingBoat.remove(c);
			 c.getPA().removeAllWindows();
			 c.getPA().sendFrame126(" ", 21120);
			 c.getPA().sendFrame126(" ", 21121);
			 c.getPA().sendFrame126(" ", 21122);
			 c.getPA().sendFrame126(" ", 21123);
			 c.getPA().movePlayer(2657,2639,0);
		 }
	 }

	 /**
	  * Moves a player into the hash and into the lobby
	  * @param player The player
	  */
	 public static void addToWaitRoom(Client player) {
		 if (player != null) {
			 waitingBoat.put(player, 1);
			 player.sendMessage("You have joined the Pest Control boat.");
			 player.getPA().movePlayer(2661,2639,0);
		 }
	 }

	 /**
	  * Checks if a player is in the game
	  * @param player The player
	  * @return return
	  */
	 public static boolean isInGame(Client player) {
		 return gamePlayers.containsKey(player);
	 }
	 /**
	  * Checks if a player is in the pc boat (lobby)
	  * @param player The player
	  * @return return
	  */
	 public static boolean isInPcBoat(Client player) {
		 return waitingBoat.containsKey(player);
	 }

	 public static boolean npcIsPCMonster(int npcType) {
		 return npcType >= 3727 && npcType <= 3776;
	 }

	 private void spawnNPC() {
		 for (int[] aPcNPCData : pcNPCData) {
			 NPCHandler.spawnNpc3(aPcNPCData[0], aPcNPCData[1], aPcNPCData[2], 0, 0, 200, 1, 10, playersInGame() * 200);
		 }
		 for (int[]  voidMonsters: voidMonsterData) {
			 NPCHandler.spawnNpc3(voidMonsters[0], voidMonsters[1], voidMonsters[2], 0, 1, 500, 1, 200, 25);
		 }
	 }
}
