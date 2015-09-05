package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;

public class ItemOnPlayer implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int playerId = c.inStream.readUnsignedWord();
		int itemId = c.playerItems[c.inStream.readSignedWordBigEndian()] - 1;

		switch (itemId) {
		case 962:
			handleCrackers(c, itemId, playerId);
			break;
		default:
			c.sendMessage("Nothing interesting happens.");
			break;
		}

	}

	private void handleCrackers(Client c, int itemId, int playerId) {
		Client usedOn = (Client) PlayerHandler.players[playerId];
		if (!c.getItems().playerHasItem(itemId))
			return;

		if (usedOn.getItems().freeSlots() < 1) {
			c.sendMessage("The other player doesn't have enough inventory space!");
			return;
		}

		// int crackerReward = Misc.random(10); // Coded by Jupiter
		// if (crackerReward == 0) {
		c.getItems().deleteItem(962, 1);
		c.getItems().addItem(4012, 1);
		// c.getItems().addItem(getRandomPhat(), 1);
		// c.getItems().addItem(getRandomStuff(), 1);
		c.sendMessage("You got a @blu@Blue Partyhat@bla@.");
		// c.startAnimation(9497);
		// c.forcedChat("Yay! I got that cracker!");
		// usedOn.sendMessage("You didnt get the prize.");
		// usedOn.startAnimation(9497);
		// }
		/*
		 * else if (crackerReward == 1) { c.getItems().addItem(getRandomPhat(),
		 * 1); c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 2) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 3) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 4) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 5) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 6) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 7) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 8) {
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * c.sendMessage("You got the prize!"); //c.startAnimation(9497);
		 * //c.forcedChat("Yay! I got that cracker!");
		 * //usedOn.sendMessage("You didnt get the prize.");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 9) {
		 * //usedOn.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * //c.sendMessage("You didnt get the prize.");
		 * //c.startAnimation(9497); //c.forcedChat("Yay! I got that cracker!");
		 * usedOn.sendMessage("You got the prize!");
		 * //usedOn.startAnimation(9497); } else if (crackerReward == 10) {
		 * //usedOn.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomPhat(), 1);
		 * c.getItems().addItem(getRandomStuff(), 1);
		 * //c.sendMessage("You didnt get the prize.");
		 * //c.startAnimation(9497); //c.forcedChat("Yay! I got that cracker!");
		 * usedOn.sendMessage("You got the prize!");
		 * //usedOn.startAnimation(9497); }
		 */
	}

	private static int getRandomPhat() {
		int[] phats = { 1046, 1046, 1046, 1046, 1046, 1046, 1046, 1046, 1046,
				1046, 1046, 1046, 1040, 1040, 1040, 1040, 1040, 1040, 1040,
				1040, 1040, 1044, 1044, 1044, 1044, 1044, 1044, 1038, 1038,
				1038 };
		return phats[(int) Math.floor(Math.random() * phats.length)];
	}

	private static int getRandomStuff() {
		int[] phats = { 1965, 592 };
		return phats[(int) Math.floor(Math.random() * phats.length)];
	}

	private static int getRandomRares() {
		int[] phats = { 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959, 1959,
				1989, 1989, 1989, 1989, 1989, 1989, 1989, 1989, 1989, 1419,
				1419, 1419, 1037, 1037, 1037, 1037, 1037, 1037, 1037, 1037,
				1037, 1961, 1961, 1961, 1961, 1961, 1961, 1961, 1961, 1961,
				1053, 1053, 1053, 1053, 1053, 1053, 1053, 1053, 1053, 1055,
				1055, 1055, 1055, 1055, 1055, 1057, 1057, 1057 };
		return phats[(int) Math.floor(Math.random() * phats.length)];
	}
}