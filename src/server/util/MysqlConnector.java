/*
package server.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eazy.model.players.Client;




public class MysqlConnector {

	private static Connection connection;
private static long lasConnection = System.currentTimeMillis();
	static {
		createConnection();
	}

	public static void init() {
		createConnection();
	}

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection("jdbc:mysql://eazypk.net/eazypkn_donation", "eazypkn_dmanage ","Admin1902154Ad");
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
	
		public static void claimPayment(final Client p, final String name) {
		try {
			if (System.currentTimeMillis() - lasConnection > 10000) {
				destroyConnection();
				createConnection();
				lasConnection = System.currentTimeMillis();
			}
			Statement s = connection.createStatement();
			String name2 = name.replaceAll(" ", "_");
			String query = "SELECT * FROM itemstore WHERE username = '"+name2+"'";
			ResultSet rs = s.executeQuery(query);
			boolean claimed = false;
			while(rs.next()) {
				int prod = Integer.parseInt(rs.getString("productid"));
				int price = Integer.parseInt(rs.getString("price"));
				if (prod == 1 && price == 5) {
					p.getItems().addItem(4067, 5);
					p.dp += 5;
					p.alldp += 5;
					claimed = true;
				} else if (prod == 2 && price  == 10) {
					p.getItems().addItem(4067, 10);
					p.dp += 10;
					p.alldp += 10;
					claimed = true;
				} else if (prod == 3 && price  == 15) {
					p.dp += 15;
					p.alldp += 15;
					p.getItems().addItem(4067, 15);
					claimed = true;
				} else if (prod == 4 && price  == 20) {
					p.dp += 20;
					p.alldp += 20;
					p.getItems().addItem(4067, 20);
					claimed = true;
				} else if (prod == 5 && price  == 25) {
					p.dp += 30;
					p.alldp += 30;
					p.getItems().addItem(4067, 30);
					claimed = true;
				} else if (prod == 6 && price  == 40) {
					p.dp += 50;
					p.alldp += 50;
					p.getItems().addItem(4067, 50);
					claimed = true;
				} else if (prod == 7 && price  == 55) {
					p.dp += 75;
					p.alldp += 75;
					p.getItems().addItem(4067, 75);
					claimed = true;
				} else if (prod == 8 && price  == 70) {
					p.dp += 100;
					p.alldp += 100;
					p.getItems().addItem(4067, 100);
					claimed = true;
				} else if (prod == 9 && price  == 90) {
					p.dp += 150;
					p.alldp += 150;
					p.getItems().addItem(4067, 150);
					claimed = true;
				} else if (prod == 10 && price  == 120) {
					p.dp += 200;
					p.alldp += 200;
					p.getItems().addItem(4067, 200);
					claimed = true;
				}
				}
				if (claimed) {
					s.execute("DELETE FROM `itemstore` WHERE `username` = '"+name2+"';");
					
					p.sendMessage("Item claimed");
				}
				else
					p.sendMessage("Your name was not in our donation records");
			
				
			} catch (Exception e) {
				e.printStackTrace();
				p.sendMessage("Unable to claim your item at this time");
			}
		}
		
	}
	*/