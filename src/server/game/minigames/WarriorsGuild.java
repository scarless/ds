package server.game.minigames;

import server.Server;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.players.Client;

public class WarriorsGuild {

	private enum Armor {
		BRONZE(4278, new int[] {1155, 1117, 1075}, 20, 5, 30, 100),
		IRON(4279,  new int[] {1153, 1115, 1067}, 30, 7, 40, 100),
		STEEL(4280,  new int[] {1157, 1119, 1069}, 40, 10, 60, 100),
		BLACK(4281,  new int[] {1165, 1125, 1077}, 50, 15, 70, 100),
		MITHRIL(4282,  new int[] {1159, 1121, 1071}, 60, 15, 80, 100),
		ADAMANT(4283,  new int[] {1161, 1123, 1073}, 80, 17, 90, 100),
		RUNITE(4284,  new int[] {1163, 1127, 1079}, 100, 20, 100, 100);

		private int npcId, hitpoints, maxHit, attack, defence;
		private int[] armorReq;

		private Armor(final int npcId, final int[] armorReq, final int hitpoints, final int maxHit, final int attack, final int defence) {
			this.npcId = npcId;
			this.armorReq = armorReq;
			this.hitpoints = hitpoints;
			this.maxHit = maxHit;
			this.attack = attack;
			this.defence = defence;
		}

		public int getNpc() {
			return npcId;
		}
		public int[] getArmorReq() {
			return armorReq;
		}
		public int getHP() {
			return hitpoints;
		}
		public int getMaxHit() {
			return maxHit;
		}
		public int getAttack() {
			return attack;
		}
		public int getDefence() {
			return defence;
		}
	}
	
	public static int getDroppedArmour(Client client, int npc) {
		for (final Armor data : Armor.values()) {
			if (npc == data.getNpc()) {
				Server.itemHandler.createGroundItem(client, data.getArmorReq()[0], Server.npcHandler.npcs[npc].absX, Server.npcHandler.npcs[npc].absY, 1, client.playerId);
				Server.itemHandler.createGroundItem(client, data.getArmorReq()[1], Server.npcHandler.npcs[npc].absX, Server.npcHandler.npcs[npc].absY, 1, client.playerId);
				Server.itemHandler.createGroundItem(client, data.getArmorReq()[2], Server.npcHandler.npcs[npc].absX, Server.npcHandler.npcs[npc].absY, 1, client.playerId);
			}
		}
		return -1;
	}

	private static boolean checkArmour(Client client, int usedItem) {
		for (final Armor data : Armor.values()) {
			if (data.getArmorReq()[0] == usedItem && data.getArmorReq()[1] == usedItem && data.getArmorReq()[2] == usedItem) {
				return true;
			}
		}
		client.sendMessage("You don't have the required armor pieces to do this!");
		return false;
	}

	private static int getArmour(Client client, int usedItem) {
		for (final Armor data : Armor.values()) {
			if (data.getArmorReq()[0] == usedItem || data.getArmorReq()[1] == usedItem || data.getArmorReq()[2] == usedItem) {
				return usedItem;
			}
		}
		return usedItem;
	}

	public static void startArmourAnimation(Client client, int usedItem) {
		if (client.getY() != 3537 || client.getX() != 2851 && client.getX() != 2857) {
			client.sendMessage("Please stand closer to the animator before attempting to do this.");
			return;
		}
		if (checkArmour(client, usedItem))
			return;
		if (client.spawned) {
			client.sendMessage("You already have an armor animated.");
			return;
		}
		for (final Armor data : Armor.values()) {
			if (data.getArmorReq()[0] == getArmour(client, usedItem) || 
					data.getArmorReq()[1] == getArmour(client, usedItem) ||
					data.getArmorReq()[2] == getArmour(client, usedItem)) {
				if (client.getItems().playerHasItem(data.getArmorReq()[0]) && client.getItems().playerHasItem(data.getArmorReq()[1]) && client.getItems().playerHasItem(data.getArmorReq()[2])) {
					client.getPA().playerWalk(0, 3);
					client.sendMessage("You animate the armor!");
					for(int i = 0; i < 3; i++) {
						client.getItems().deleteItem(data.getArmorReq()[i], 1);
					}
					client.spawned = true;
					Server.npcHandler.spawnNpc(client, data.getNpc(), client.getX(), client.getY()+4, 0, 0, data.getHP(), data.getMaxHit(), data.getAttack(), data.getDefence(), true, true);
				}
			}
		}
	}

	static int[] defenders = {20072, 8850, 8849, 8848, 8847, 8846, 8845, 8844};
	public static int getCurrentDefender(Client client) {
		for (int i = 0; i < defenders.length; i++) {
			if (client.getItems().playerHasItem(defenders[i]) ||
					client.playerEquipment[client.playerShield] == defenders[i]) {
				if (defenders[i] == 20072)
					return client.droppingDefender = 20072;
				return client.droppingDefender = defenders[i-1];
			}
		}
		return client.droppingDefender = 8844;
	}

	public static void enterRoom(Client client) {
		if (!client.getItems().playerHasItem(8851, 100)) {
			client.sendMessage("You need a minimum of 100 tokens to enter here.");
			return;
		}
		CycleEventHandler.getSingleton().stopEvents(client);
		defenderTimer(client);
		client.getPA().movePlayer(2847, 3540, client.heightLevel);
		client.getItems().deleteItem2(8851, 10);
		client.sendMessage("10 Tokens are removed as you enter through the door.");
		getCurrentDefender(client);
		client.inDefenderRoom = true;
		client.sendMessage("The cyclops's are dropping @red@"+client.getItems().getItemName(client.droppingDefender).toLowerCase()+"@bla@'s.");
	}

	private static void defenderTimer(final Client client) {
		CycleEventHandler.getSingleton().addEvent(client, new CycleEvent() {	
			public void execute(CycleEventContainer container) {
				if (!client.isInWG()) {
					container.stop();
					return;
				}
				if (!client.getItems().playerHasItem(8851, 10)) {
					client.getPA().movePlayer(2843, 3540, client.heightLevel);
					client.sendMessage("You've ran out of tokens! You need a minimum of 10 tokens to stay in.");
					container.stop();
					return;
				}
				if (client.inDefenderRoom) {
					client.getItems().deleteItem2(8851, 10);
					client.sendMessage("10 Tokens are removed as another minute passes.");
				}
			}
			@Override
			public void stop() {
				client.inDefenderRoom = false;
			}
		}, 80);			
	}

}
