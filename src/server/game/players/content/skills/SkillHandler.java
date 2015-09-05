package server.game.players.content.skills;

import java.util.Random;




import server.game.players.Client;
import server.game.players.Player;
import server.game.players.PlayerAssistant;
import server.game.players.content.skills.woodcutting.Woodcutting;



public class SkillHandler {
	
	private static final int SKILLING_XP = 40;
	public static final int AGILITY_XP = SKILLING_XP;
	public static final int PRAYER_XP = SKILLING_XP;
	public static final int MINING_XP = SKILLING_XP;
	public static final int RUNECRAFTING_XP = SKILLING_XP;
	public static final int THIEVING_XP = SKILLING_XP;
	public static final int FLETCHING_XP = SKILLING_XP;
	public static boolean[] isSkilling = new boolean[25];
	public static final int HERBLORE_XP = 40;
	public static final int COOKING_XP = 1;
	public static final int FISHING_XP = 20;
	public static final int WOODCUTTING_XP = 1000000;

	public static final boolean view190 = true;

	public static String getLine(Client c) {
		return ("\\n\\n\\n\\n\\n");
	}

	public static boolean noInventorySpace(Client c, String skill) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue "+skill+"!");
			c.getPA().sendStatement("You haven't got enough inventory space to continue "+skill+"!");
			return false;
		}
		return true;
	}

	public static boolean hasRequiredLevel(Client c, int id, int lvlReq, String skill, String event) {
		if(c.playerLevel[id] < lvlReq) {
			c.sendMessage("You haven't got high enough "+skill+" level to "+event+"");
			c.sendMessage("You at least need the "+skill+" level of "+ lvlReq +".");
			c.getPA().sendStatement("You haven't got high enough "+skill+" level to "+event+"!");
			return false;
		}
		return true;
	}
	public static boolean noInventorySpace(Player c, String skill) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue "+skill+"!");
			((PlayerAssistant) c.getPA()).sendStatement("You haven't got enough inventory space to continue "+skill+"!");
			return false;
		}
		return true;
	}
	public static void deleteTime(Client c) {
		c.doAmount--;
	}
}