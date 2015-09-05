package server.game.players.content.quests;

import server.game.players.Client;

/*
 * @author Liam Aka Insidia X (R-S Name).
 */

public class WitchesPotion {

	static Client c;
	
	public WitchesPotion(Client c) {
	this.c = c;
	}
	
	public static void showInformation() {
		for (int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
		c.getPA().sendFrame126("@dre@Witches Potion", 8144);
		c.getPA().sendFrame126("", 8145);
		if(c.WitchesPotion == 0) {
			c.getPA().sendFrame126("Witches Potion", 8144);
			c.getPA().sendFrame126("Talk to Hetty in her house in Rimmington, south of Falador,", 8147);
			c.getPA().sendFrame126("west of Port Sarim.", 8148);
			c.getPA().sendFrame126("", 8149);
			c.getPA().sendFrame126("There are no requirments.", 8150);
		} else if(c.WitchesPotion == 1) {
			c.getPA().sendFrame126("Witches Potion", 8144);
			c.getPA().sendFrame126("<str>I've talked to Hetty.", 8147);
			c.getPA().sendFrame126("She wants me to gather the following ingredients:", 8148);
			
			if(c.getItems().playerHasItem(300,1)) {
				c.getPA().sendFrame126("<str>A Rat's tail", 8149);
			} else {
				c.getPA().sendFrame126("@red@A Rat's tail", 8149);
			}
			if(c.getItems().playerHasItem(2146,1)) {
			c.getPA().sendFrame126("<str>Some Burnt Meat", 8150);
			} else {
			c.getPA().sendFrame126("@red@Some Burnt Meat", 8150);
			}
			if(c.getItems().playerHasItem(221,1)) {
			c.getPA().sendFrame126("<str>An Eye of newt", 8151);
			} else {
				c.getPA().sendFrame126("@red@An Eye of newt", 8151);	
			}
			if(c.getItems().playerHasItem(1957,1)) {
			c.getPA().sendFrame126("<str>And An Onion", 8152);
			} else {
				c.getPA().sendFrame126("@red@And An Onion", 8152);
			}
			} else if(c.WitchesPotion == 2) {
				c.getPA().sendFrame126("Witches Potion", 8144);
				c.getPA().sendFrame126("<str>I've talked to Hetty.", 8147);
				c.getPA().sendFrame126("She wants me to gather the following ingredients:", 8148);
				if(c.getItems().playerHasItem(300,1)) {
				c.getPA().sendFrame126("<str>A Rat's tail", 8149);
				} else {
				c.getPA().sendFrame126("@red@A Rat's tail", 8149);
				}
				if(c.getItems().playerHasItem(2146,1)) {
				c.getPA().sendFrame126("<str>Some Burnt Meat", 8150);
				} else {
					c.getPA().sendFrame126("@red@Some Burnt Meat", 8150);	
				}
				if(c.getItems().playerHasItem(221,1)) {
				c.getPA().sendFrame126("<str>An Eye of newt", 8151);
				} else {
					c.getPA().sendFrame126("@red@An Eye of newt", 8151);
				}
				if (c.getItems().playerHasItem(1957,1)) {
					c.getPA().sendFrame126("<str>And An Onion", 8152);
				} else {
					c.getPA().sendFrame126("@red@And An Onion", 8152);
				}
				} else if(c.WitchesPotion == 3) {
					c.getPA().sendFrame126("Witches", 8144);
					c.getPA().sendFrame126("<str>I talked to Hetty.", 8147);
					c.getPA().sendFrame126("<str>I gave her the ingredients.", 8148);
					c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8150);
					c.getPA().sendFrame126("As a reward, I gained 325,000 Magic Experience &", 8151);
			        c.getPA().sendFrame126("1 Quest point.", 8152);

				}
				c.getPA().showInterface(8134);
			}
	}