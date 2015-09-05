/* package server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import eazy.game.players.Client;





public class HighscoreConnector {

	private static Connection connection;
	private static long lastConnection = System.currentTimeMillis();
	static {
		createConnection();
	}

	public static void init() {
		createConnection();
	}

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(
					"jdbc:mysql://eazypk.net/eazypkn_highscores",
					"eazypkn_hsmanag", "v73z5oTyxRML");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void destroyConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateHiscores(Client player) {
		try {
			if (System.currentTimeMillis() - lastConnection > 10000) {
				destroyConnection();
				createConnection();
				lastConnection = System.currentTimeMillis();
			}
			String username = player.playerName;
			int rights = player.playerRights;

			final Statement s = connection.createStatement();

			s.execute("DELETE FROM hs_users WHERE username = '" + username
					+ "';");
			String statement = "INSERT INTO `eazypkn_highscores`.`hs_users` (`username`, `rights`, `overall_xp`, `attack_xp`, `defence_xp`, `strength_xp`, `constitution_xp`, `ranged_xp`, `prayer_xp`, `magic_xp`, `cooking_xp`, `woodcutting_xp`, `fletching_xp`, `fishing_xp`, `firemaking_xp`, `crafting_xp`, `smithing_xp`, `mining_xp`, `herblore_xp`, `agility_xp`, `thieving_xp`, `slayer_xp`, `farming_xp`, `runecrafting_xp`, `hunter_xp`, `construction_xp`, `summoning_xp`, `dungeoneering_xp`) VALUES ('"
					+ username
					+ "', '"
					+ rights
					+ "', '"
					+ player.getPA().getTotalXp() + "',";
			for (int i = 0; i < 25; i++) {
				statement += "'" + (int) player.playerXP[i] + "', ";
			}

			statement = statement.substring(0, statement.length() - 2) + ");";

			s.execute(statement);
			System.out.println("Hiscores saving");
			 destroyConnection();
		} catch (final Exception e) {
			if (System.currentTimeMillis() - lastConnection > 10000) {
				destroyConnection();
				createConnection();
				lastConnection = System.currentTimeMillis();
				
			}
		}
	}

}
*/