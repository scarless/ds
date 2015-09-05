package server.game.players.content;

import server.game.players.Client;
import server.game.players.Player;

/**
 * Class Books
 * @author: RXPK
 */

public class Books {

	/* Opens the book and sends the interface */
	public static void openBook(Client c) {
		c.page = 1;
		updatePage(c);
		c.getPA().showInterface(837);
	}
	
	/* Advances the book to the next page */
	public static void advancePage(Client c) {
		if (c.page == 5) {
			c.sendMessage("This is the last page of the book.");
		} else {
			c.page++;
			updatePage(c);
		}
	}
	
	/* Goes back a page */
	public static void decreasePage(Client c) {
		if (c.page == 1) {
			c.sendMessage("This is the first page of the book.");
		} else {
			c.page--;
			updatePage(c);
		}
	}
	
	/* Updates the strings on the interface to the correct page text */
	private static void updatePage(Client c) {
		/* Text for page 1 */
		if (c.page == 1) {
			c.getPA().sendFrame126("Project-OSPS Drops", 903);	/* Title of the book */
			c.getPA().sendFrame126("Project-OS123", 14165);	/* Bottom left corner (use "Previous" for all pages except 1st) */
			c.getPA().sendFrame126("Project-OS111", 14166);	/* Bottom right corner (use "Next" for all pages except last) */
			
			c.getPA().sendFrame126("1", 843);		/* Left page: line 1 */
			c.getPA().sendFrame126("2", 844);		/* Left page: line 2 */
			c.getPA().sendFrame126("3", 845);		/* Left page: line 3 */
			c.getPA().sendFrame126("4", 846);		/* Left page: line 4 */
			c.getPA().sendFrame126("5", 847);		/* Left page: line 5 */
			c.getPA().sendFrame126("6", 848);		/* Left page: line 6 */
			c.getPA().sendFrame126("7", 849);		/* Left page: line 7 */
			c.getPA().sendFrame126("8", 850);		/* Left page: line 8 */
			c.getPA().sendFrame126("9", 851);		/* Left page: line 9 */
			c.getPA().sendFrame126("10", 852);		/* Left page: line 10 */
			c.getPA().sendFrame126("11", 853);		/* Left page: line 11 */
			
			c.getPA().sendFrame126("11", 854);		/* Right page: line 1 */
			c.getPA().sendFrame126("22", 855);		/* Right page: line 2 */
			c.getPA().sendFrame126("33", 856);		/* Right page: line 3 */
			c.getPA().sendFrame126("44", 857);		/* Right page: line 4 */
			c.getPA().sendFrame126("55", 858);		/* Right page: line 5 */
			c.getPA().sendFrame126("66", 859);		/* Right page: line 6 */
			c.getPA().sendFrame126("77", 860);		/* Right page: line 7 */
			c.getPA().sendFrame126("88", 861); 		/* Right page: line 8 */
			c.getPA().sendFrame126("99", 862);		/* Right page: line 9 */
			c.getPA().sendFrame126("100", 863);		/* Right page: line 10 */
			c.getPA().sendFrame126("1111", 864); 		/* Right page: line 11 */
		}
		
		
		/* Text for page 2 */
		if (c.page == 2) {
			c.getPA().sendFrame126("", 903);	/* Title of the book */
			c.getPA().sendFrame126("", 14165);	/* Bottom left corner (use "Previous" for all pages except 1st) */
			c.getPA().sendFrame126("", 14166);	/* Bottom right corner (use "Next" for all pages except last) */
			
			c.getPA().sendFrame126("", 843);		/* Left page: line 1 */
			c.getPA().sendFrame126("", 844);		/* Left page: line 2 */
			c.getPA().sendFrame126("", 845);		/* Left page: line 3 */
			c.getPA().sendFrame126("", 846);		/* Left page: line 4 */
			c.getPA().sendFrame126("", 847);		/* Left page: line 5 */
			c.getPA().sendFrame126("", 848);		/* Left page: line 6 */
			c.getPA().sendFrame126("", 849);		/* Left page: line 7 */
			c.getPA().sendFrame126("", 850);		/* Left page: line 8 */
			c.getPA().sendFrame126("", 851);		/* Left page: line 9 */
			c.getPA().sendFrame126("", 852);		/* Left page: line 10 */
			c.getPA().sendFrame126("", 853);		/* Left page: line 11 */
			
			c.getPA().sendFrame126("", 854);		/* Right page: line 1 */
			c.getPA().sendFrame126("", 855);		/* Right page: line 2 */
			c.getPA().sendFrame126("", 856);		/* Right page: line 3 */
			c.getPA().sendFrame126("", 857);		/* Right page: line 4 */
			c.getPA().sendFrame126("", 858);		/* Right page: line 5 */
			c.getPA().sendFrame126("", 859);		/* Right page: line 6 */
			c.getPA().sendFrame126("", 860);		/* Right page: line 7 */
			c.getPA().sendFrame126("", 861); 		/* Right page: line 8 */
			c.getPA().sendFrame126("", 862);		/* Right page: line 9 */
			c.getPA().sendFrame126("", 863);		/* Right page: line 10 */
			c.getPA().sendFrame126("", 864); 		/* Right page: line 11 */
		}
		
		
		/* Text for page 3 */
		if (c.page == 3) {
			c.getPA().sendFrame126("", 903);	/* Title of the book */
			c.getPA().sendFrame126("", 14165);	/* Bottom left corner (use "Previous" for all pages except 1st) */
			c.getPA().sendFrame126("", 14166);	/* Bottom right corner (use "Next" for all pages except last) */
			
			c.getPA().sendFrame126("", 843);		/* Left page: line 1 */
			c.getPA().sendFrame126("", 844);		/* Left page: line 2 */
			c.getPA().sendFrame126("", 845);		/* Left page: line 3 */
			c.getPA().sendFrame126("", 846);		/* Left page: line 4 */
			c.getPA().sendFrame126("", 847);		/* Left page: line 5 */
			c.getPA().sendFrame126("", 848);		/* Left page: line 6 */
			c.getPA().sendFrame126("", 849);		/* Left page: line 7 */
			c.getPA().sendFrame126("", 850);		/* Left page: line 8 */
			c.getPA().sendFrame126("", 851);		/* Left page: line 9 */
			c.getPA().sendFrame126("", 852);		/* Left page: line 10 */
			c.getPA().sendFrame126("", 853);		/* Left page: line 11 */
			
			c.getPA().sendFrame126("", 854);		/* Right page: line 1 */
			c.getPA().sendFrame126("", 855);		/* Right page: line 2 */
			c.getPA().sendFrame126("", 856);		/* Right page: line 3 */
			c.getPA().sendFrame126("", 857);		/* Right page: line 4 */
			c.getPA().sendFrame126("", 858);		/* Right page: line 5 */
			c.getPA().sendFrame126("", 859);		/* Right page: line 6 */
			c.getPA().sendFrame126("", 860);		/* Right page: line 7 */
			c.getPA().sendFrame126("", 861); 		/* Right page: line 8 */
			c.getPA().sendFrame126("", 862);		/* Right page: line 9 */
			c.getPA().sendFrame126("", 863);		/* Right page: line 10 */
			c.getPA().sendFrame126("", 864); 		/* Right page: line 11 */
		}
		
		
		/* Text for page 4 */
		if (c.page == 4) {
			c.getPA().sendFrame126("", 903);	/* Title of the book */
			c.getPA().sendFrame126("", 14165);	/* Bottom left corner (use "Previous" for all pages except 1st) */
			c.getPA().sendFrame126("", 14166);	/* Bottom right corner (use "Next" for all pages except last) */
			
			c.getPA().sendFrame126("", 843);		/* Left page: line 1 */
			c.getPA().sendFrame126("", 844);		/* Left page: line 2 */
			c.getPA().sendFrame126("", 845);		/* Left page: line 3 */
			c.getPA().sendFrame126("", 846);		/* Left page: line 4 */
			c.getPA().sendFrame126("", 847);		/* Left page: line 5 */
			c.getPA().sendFrame126("", 848);		/* Left page: line 6 */
			c.getPA().sendFrame126("", 849);		/* Left page: line 7 */
			c.getPA().sendFrame126("", 850);		/* Left page: line 8 */
			c.getPA().sendFrame126("", 851);		/* Left page: line 9 */
			c.getPA().sendFrame126("", 852);		/* Left page: line 10 */
			c.getPA().sendFrame126("", 853);		/* Left page: line 11 */
			
			c.getPA().sendFrame126("", 854);		/* Right page: line 1 */
			c.getPA().sendFrame126("", 855);		/* Right page: line 2 */
			c.getPA().sendFrame126("", 856);		/* Right page: line 3 */
			c.getPA().sendFrame126("", 857);		/* Right page: line 4 */
			c.getPA().sendFrame126("", 858);		/* Right page: line 5 */
			c.getPA().sendFrame126("", 859);		/* Right page: line 6 */
			c.getPA().sendFrame126("", 860);		/* Right page: line 7 */
			c.getPA().sendFrame126("", 861); 		/* Right page: line 8 */
			c.getPA().sendFrame126("", 862);		/* Right page: line 9 */
			c.getPA().sendFrame126("", 863);		/* Right page: line 10 */
			c.getPA().sendFrame126("", 864); 		/* Right page: line 11 */
		}
		
		
		/* Text for page 5 */
		if (c.page == 5) {
			c.getPA().sendFrame126("", 903);	/* Title of the book */
			c.getPA().sendFrame126("", 14165);	/* Bottom left corner (use "Previous" for all pages except 1st) */
			c.getPA().sendFrame126("", 14166);	/* Bottom right corner (use "Next" for all pages except last) */
			
			c.getPA().sendFrame126("", 843);		/* Left page: line 1 */
			c.getPA().sendFrame126("", 844);		/* Left page: line 2 */
			c.getPA().sendFrame126("", 845);		/* Left page: line 3 */
			c.getPA().sendFrame126("", 846);		/* Left page: line 4 */
			c.getPA().sendFrame126("", 847);		/* Left page: line 5 */
			c.getPA().sendFrame126("", 848);		/* Left page: line 6 */
			c.getPA().sendFrame126("", 849);		/* Left page: line 7 */
			c.getPA().sendFrame126("", 850);		/* Left page: line 8 */
			c.getPA().sendFrame126("", 851);		/* Left page: line 9 */
			c.getPA().sendFrame126("", 852);		/* Left page: line 10 */
			c.getPA().sendFrame126("", 853);		/* Left page: line 11 */
			
			c.getPA().sendFrame126("", 854);		/* Right page: line 1 */
			c.getPA().sendFrame126("", 855);		/* Right page: line 2 */
			c.getPA().sendFrame126("", 856);		/* Right page: line 3 */
			c.getPA().sendFrame126("", 857);		/* Right page: line 4 */
			c.getPA().sendFrame126("", 858);		/* Right page: line 5 */
			c.getPA().sendFrame126("", 859);		/* Right page: line 6 */
			c.getPA().sendFrame126("", 860);		/* Right page: line 7 */
			c.getPA().sendFrame126("", 861); 		/* Right page: line 8 */
			c.getPA().sendFrame126("", 862);		/* Right page: line 9 */
			c.getPA().sendFrame126("", 863);		/* Right page: line 10 */
			c.getPA().sendFrame126("", 864); 		/* Right page: line 11 */
		}
	}
}