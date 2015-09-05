package server.util.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import server.game.players.Client;
import server.game.players.PlayerHandler;

public class TradeLog {

	private Client c;

	public TradeLog(Client Client) {
		this.c = Client;
	}

	/**
	 * Will write what kind of item a player has received. MONTH = 0 = January
	 * DAY OF MONTH = 30 || 31
	 */
	public void tradeReceived(String itemName, int itemAmount) {
		Client o = (Client) PlayerHandler.players[c.tradeWith];
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(new FileWriter(
					"./Data/logs/TradeLog/received/" + c.playerName + ".txt",
					true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "
						+ C.get(Calendar.MONTH) + "\tDay : "
						+ C.get(Calendar.DAY_OF_MONTH));
				bItem.newLine();
				bItem.write("Received " + itemAmount + " " + itemName
						+ " From " + o.playerName);
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will write what kind of item a player has traded with another player.
	 * MONTH = 0 = January DAY OF MONTH = 30 || 31
	 */
	public void tradeGive(String itemName, int itemAmount) {
		Client o = (Client) PlayerHandler.players[c.tradeWith];
		Calendar C = Calendar.getInstance();
		try {
			BufferedWriter bItem = new BufferedWriter(
					new FileWriter("./Data/logs/TradeLog/given/" + c.playerName
							+ ".txt", true));
			try {
				bItem.newLine();
				bItem.write("Year : " + C.get(Calendar.YEAR) + "\tMonth : "
						+ C.get(Calendar.MONTH) + "\tDay : "
						+ C.get(Calendar.DAY_OF_MONTH));
				bItem.newLine();
				bItem.write("Given " + itemAmount + " " + itemName + " To "
						+ o.playerName);
				bItem.newLine();
				bItem.write("--------------------------------------------------");
			} finally {
				bItem.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}