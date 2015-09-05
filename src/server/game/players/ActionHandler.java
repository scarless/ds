package server.game.players;

import server.Server;
import server.event.CycleEventHandler;
import server.game.minigames.PestControl;
import server.game.minigames.Sailing;
import server.game.minigames.WarriorsGuild;
import server.game.npcs.pet.Pet;
import server.game.objects.Object;
import server.game.players.content.Flax;
import server.game.players.content.FlourMill;
import server.game.players.content.Special;
import server.game.players.content.pvphighscores.HighScoresBoard;
import server.game.players.content.skills.Fishing;
import server.game.players.content.skills.Mining;
import server.game.players.content.skills.Runecrafting;
import server.game.players.content.skills.Thieving;
import server.game.players.content.skills.Crafting;
import server.game.players.content.skills.agility.impl.*;
import server.game.players.content.skills.farming.herbs.HerbPicking;
import server.game.players.content.skills.farming.trees.TreePicking;
import server.game.players.content.skills.woodcutting.*;
import server.util.Misc;
import server.util.ScriptManager;

public class ActionHandler {

	private Client c;

	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		// Woodcutting.startChopping(c, objectType, obX, obY);
		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
	
	
		if (c.getAgility1().isBarbarianCourseObject(c, objectType)) {
			BarbarianCourse.startBarbarianCourse(c, objectType);
			return;
		}
		if (c.getAgility1().checkGnomeCourseObjects(c, objectType)) {
			GnomeCourse.startGnomeCourse(c, objectType);
			return;
		}
		if (Woodcutting.chopTree(c, objectType, obX, obY)) {
			return;
		}

		switch (objectType) {

		case 13291:// magic chest
			if (c.dp >= 5 && c.getItems().freeSlots() >= 3) {
				c.getDH().sendDialogues(9041, 0);
			} else {
				c.getDH().sendDialogues(9038, 0);
			}
			break;
		case 2466:
			c.getPA().movePlayer(2661, 2648, 0);
			break;
		case 5097:
		c.getPA().movePlayer(2636, 9510, 2);
		break;
		
		case 2465:
			if (c.safeTimer > 0) {
				c.sendMessage("You are still in the wilderness.");
				return;
			} else {
				c.getPA().startTeleport(2150, 5094, 0, "modern");
			}
		case 2467:
			if (c.safeTimer > 0) {
				c.sendMessage("You are still in the wilderness.");
				return;
			} else {
				c.getPA().startTeleport(2846, 3542, 0, "modern");
			}
			break;
		case 3192:
			HighScoresBoard.getLeaderBoard().displayLeaderBoard(c);
			break;
			case 2643:
			c.getCrafting().mouldInterface();
			break;

		case 2469:
			if (c.safeTimer > 0) {
				c.sendMessage("You are still in the wilderness.");
				return;
			} else {
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			}
			break;
		case 1536: // open door
			if (obX == 3204 && obY == 3478) {
				c.getPA().object(3204, 3478, 6951, 0, 0);
				c.getPA().object(3204, 3478, 1537, 1, 0);
			}
			if (obX == 3204 && obY == 3485) {
				c.getPA().object(3204, 3485, 6951, 0, 0);
				c.getPA().object(3204, 3485, 1537, 1, 0);
			}
			if (obX == 3206 && obY == 3472) {
				c.getPA().object(3206, 3472, 6951, 0, 0);
				c.getPA().object(3206, 3472, 1537, 1, 0);
			}
			if (obX == 3217 && obY == 3488) { // East
				c.getPA().object(3217, 3488, 6951, 0, 0);
				c.getPA().object(3217, 3488, 1537, 3, 0);
			}
			if (obX == 3215 && obY == 3477) { // north
				c.getPA().object(3215, 3477, 6951, 0, 0);
				c.getPA().object(3215, 3477, 1537, 0, 0);
			}
			if (obX == 3214 && obY == 3486) { // north
				c.getPA().object(3214, 3486, 6951, 0, 0);
				c.getPA().object(3214, 3486, 1537, 0, 0);
			}
			if (obX == 3223 && obY == 3479) {
				c.getPA().object(3223, 3479, 6951, 0, 0);
				c.getPA().object(3223, 3479, 1537, 0, 0);
			}
			if (obX == 3219 && obY == 3472) {
				c.getPA().object(3219, 3472, 6951, 0, 0);
				c.getPA().object(3219, 3472, 1537, 1, 0);
			}
			if (obX == 3219 && obY == 3473) {
				c.getPA().object(3219, 3473, 6951, 0, 0);
				c.getPA().object(3219, 3473, 1537, 1, 0);
			}
			if (obX == 3207 && obY == 3222) {
				c.getPA().object(3207, 3222, 6951, 0, 0);
				c.getPA().object(3207, 3222, 1537, 1, 0);
			}
			if (obX == 3207 && obY == 3214) {
				c.getPA().object(3207, 3214, 6951, 0, 0);
				c.getPA().object(3207, 3214, 1537, 1, 0);
			}
			if (obX == 3215 && obY == 3211) {
				c.getPA().object(3215, 3211, 6951, 0, 0);
				c.getPA().object(3215, 3211, 1537, 0, 0);
			}
			if (obX == 3215 && obY == 3225) {
				c.getPA().object(3215, 3225, 6951, 0, 0);
				c.getPA().object(3215, 3225, 1537, 0, 0);
			}
			break;
		case 1782:// full flour bin
			FlourMill.emptyFlourBin(c);
			break;
		case 2718: // Hopper
			FlourMill.hopperControl(c);
			break;
		case 2477:
			if (c.dp >= 20) {
				c.getPA().movePlayer(2659, 4579, 1);
				// c.monkeyCount = 0;
			} else {
				c.sendMessage("You need 20 monkey count before teleporting to Jungle Demons.");
			}
			break;
		case 1500:
			if (c.playerLevel[0] >= 98 && c.playerLevel[1] >= 98
					&& c.playerLevel[2] >= 98 && c.playerLevel[3] >= 98
					&& c.playerLevel[4] >= 98 && c.playerLevel[5] >= 98
					&& c.playerLevel[6] >= 98 && c.playerLevel[7] >= 98
					&& c.playerLevel[8] >= 98 && c.playerLevel[9] >= 98
					&& c.playerLevel[10] >= 98 && c.playerLevel[11] >= 98
					&& c.playerLevel[12] >= 98 && c.playerLevel[13] >= 98
					&& c.playerLevel[14] >= 98 && c.playerLevel[15] >= 98
					&& c.playerLevel[16] >= 98 && c.playerLevel[17] >= 98
					&& c.playerLevel[18] >= 98 && c.playerLevel[19] >= 98
					&& c.playerLevel[20] >= 98) {
				c.getDH().sendDialogues(3515, 1);
			} else {
				c.sendMessage("Come back when you have achieved maxed stats!");
			}
			break;
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			break;
		case 8689:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				if (c.getItems().playerHasItem(1925, 1)) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItems().addItem(1927, 1);
					c.getItems().deleteItem(1925, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.sendMessage("You need a bucket to milk a cow!");
				}
			}
			break;
		case 5581:
			if (c.getItems().playerHasItem(1351)) {
				c.sendMessage("You already have a bronze axe.");
				return;
			}
			c.startAnimation(827);
			c.getItems().addItem(1351, 1);
			c.sendMessage("You found a bronze axe.");
			break;
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
			break;

		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;

		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
			break;
		case 28779:
			if (c.objectX == 3254 && c.objectY == 5451) {
				c.getPA().movePlayer(3250, 5448, 0);
			}
			if (c.objectX == 3250 && c.objectY == 5448) {
				c.getPA().movePlayer(3254, 5451, 0);
			}
			if (c.objectX == 3241 && c.objectY == 5445) {
				c.getPA().movePlayer(3233, 5445, 0);
			}
			if (c.objectX == 3233 && c.objectY == 5445) {
				c.getPA().movePlayer(3241, 5445, 0);
			}
			if (c.objectX == 3259 && c.objectY == 5446) {
				c.getPA().movePlayer(3265, 5491, 0);
			}
			if (c.objectX == 3265 && c.objectY == 5491) {
				c.getPA().movePlayer(3259, 5446, 0);
			}
			if (c.objectX == 3260 && c.objectY == 5491) {
				c.getPA().movePlayer(3266, 5446, 0);
			}
			if (c.objectX == 3266 && c.objectY == 5446) {
				c.getPA().movePlayer(3260, 5491, 0);
			}
			if (c.objectX == 3241 && c.objectY == 5469) {
				c.getPA().movePlayer(3233, 5470, 0);
			}
			if (c.objectX == 3233 && c.objectY == 5470) {
				c.getPA().movePlayer(3241, 5469, 0);
			}
			if (c.objectX == 3235 && c.objectY == 5457) {
				c.getPA().movePlayer(3229, 5454, 0);
			}
			if (c.objectX == 3229 && c.objectY == 5454) {
				c.getPA().movePlayer(3235, 5457, 0);
			}
			if (c.objectX == 3280 && c.objectY == 5460) {
				c.getPA().movePlayer(3273, 5460, 0);
			}
			if (c.objectX == 3273 && c.objectY == 5460) {
				c.getPA().movePlayer(3280, 5460, 0);
			}
			if (c.objectX == 3283 && c.objectY == 5448) {
				c.getPA().movePlayer(3287, 5448, 0);
			}
			if (c.objectX == 3287 && c.objectY == 5448) {
				c.getPA().movePlayer(3283, 5448, 0);
			}
			if (c.objectX == 3244 && c.objectY == 5495) {
				c.getPA().movePlayer(3239, 5498, 0);
			}
			if (c.objectX == 3239 && c.objectY == 5498) {
				c.getPA().movePlayer(3244, 5495, 0);
			}
			if (c.objectX == 3232 && c.objectY == 5501) {
				c.getPA().movePlayer(3238, 5507, 0);
			}
			if (c.objectX == 3238 && c.objectY == 5507) {
				c.getPA().movePlayer(3232, 5501, 0);
			}
			if (c.objectX == 3218 && c.objectY == 5497) {
				c.getPA().movePlayer(3222, 5488, 0);
			}
			if (c.objectX == 3222 && c.objectY == 5488) {
				c.getPA().movePlayer(3218, 5497, 0);
			}
			if (c.objectX == 3218 && c.objectY == 5478) {
				c.getPA().movePlayer(3215, 5475, 0);
			}
			if (c.objectX == 3215 && c.objectY == 5475) {
				c.getPA().movePlayer(3218, 5478, 0);
			}
			if (c.objectX == 3224 && c.objectY == 5479) {
				c.getPA().movePlayer(3222, 5474, 0);
			}
			if (c.objectX == 3222 && c.objectY == 5474) {
				c.getPA().movePlayer(3224, 5479, 0);
			}
			if (c.objectX == 3208 && c.objectY == 5471) {
				c.getPA().movePlayer(3210, 5477, 0);
			}
			if (c.objectX == 3210 && c.objectY == 5477) {
				c.getPA().movePlayer(3208, 5471, 0);
			}
			if (c.objectX == 3214 && c.objectY == 5456) {
				c.getPA().movePlayer(3212, 5452, 0);
			}
			if (c.objectX == 3212 && c.objectY == 5452) {
				c.getPA().movePlayer(3214, 5456, 0);
			}
			if (c.objectX == 3204 && c.objectY == 5445) {
				c.getPA().movePlayer(3197, 5448, 0);
			}
			if (c.objectX == 3197 && c.objectY == 5448) {
				c.getPA().movePlayer(3204, 5445, 0);
			}
			if (c.objectX == 3189 && c.objectY == 5444) {
				c.getPA().movePlayer(3187, 5460, 0);
			}
			if (c.objectX == 3187 && c.objectY == 5460) {
				c.getPA().movePlayer(3189, 5444, 0);
			}
			if (c.objectX == 3192 && c.objectY == 5472) {
				c.getPA().movePlayer(3186, 5472, 0);
			}
			if (c.objectX == 3186 && c.objectY == 5472) {
				c.getPA().movePlayer(3192, 5472, 0);
			}
			if (c.objectX == 3185 && c.objectY == 5478) {
				c.getPA().movePlayer(3191, 5482, 0);
			}
			if (c.objectX == 3191 && c.objectY == 5482) {
				c.getPA().movePlayer(3185, 5478, 0);
			}
			if (c.objectX == 3171 && c.objectY == 5473) {
				c.getPA().movePlayer(3167, 5471, 0);
			}
			if (c.objectX == 3167 && c.objectY == 5471) {
				c.getPA().movePlayer(3171, 5473, 0);
			}
			if (c.objectX == 3171 && c.objectY == 5478) {
				c.getPA().movePlayer(3167, 5478, 0);
			}
			if (c.objectX == 3167 && c.objectY == 5478) {
				c.getPA().movePlayer(3171, 5478, 0);
			}
			if (c.objectX == 3168 && c.objectY == 5456) {
				c.getPA().movePlayer(3178, 5460, 0);
			}
			if (c.objectX == 3178 && c.objectY == 5460) {
				c.getPA().movePlayer(3168, 5456, 0);
			}
			if (c.objectX == 3191 && c.objectY == 5495) {
				c.getPA().movePlayer(3194, 5490, 0);
			}
			if (c.objectX == 3194 && c.objectY == 5490) {
				c.getPA().movePlayer(3191, 5495, 0);
			}
			if (c.objectX == 3141 && c.objectY == 5480) {
				c.getPA().movePlayer(3142, 5489, 0);
			}
			if (c.objectX == 3142 && c.objectY == 5489) {
				c.getPA().movePlayer(3141, 5480, 0);
			}
			if (c.objectX == 3142 && c.objectY == 5462) {
				c.getPA().movePlayer(3154, 5462, 0);
			}
			if (c.objectX == 3154 && c.objectY == 5462) {
				c.getPA().movePlayer(3142, 5462, 0);
			}
			if (c.objectX == 3143 && c.objectY == 5443) {
				c.getPA().movePlayer(3155, 5449, 0);
			}
			if (c.objectX == 3155 && c.objectY == 5449) {
				c.getPA().movePlayer(3143, 5443, 0);
			}
			if (c.objectX == 3307 && c.objectY == 5496) {
				c.getPA().movePlayer(3317, 5496, 0);
			}
			if (c.objectX == 3317 && c.objectY == 5496) {
				c.getPA().movePlayer(3307, 5496, 0);
			}
			if (c.objectX == 3318 && c.objectY == 5481) {
				c.getPA().movePlayer(3322, 5480, 0);
			}
			if (c.objectX == 3322 && c.objectY == 5480) {
				c.getPA().movePlayer(3318, 5481, 0);
			}
			if (c.objectX == 3299 && c.objectY == 5484) {
				c.getPA().movePlayer(3303, 5477, 0);
			}
			if (c.objectX == 3303 && c.objectY == 5477) {
				c.getPA().movePlayer(3299, 5484, 0);
			}
			if (c.objectX == 3286 && c.objectY == 5470) {
				c.getPA().movePlayer(3285, 5474, 0);
			}
			if (c.objectX == 3285 && c.objectY == 5474) {
				c.getPA().movePlayer(3286, 5470, 0);
			}
			if (c.objectX == 3290 && c.objectY == 5463) {
				c.getPA().movePlayer(3302, 5469, 0);
			}
			if (c.objectX == 3302 && c.objectY == 5469) {
				c.getPA().movePlayer(3290, 5463, 0);
			}
			if (c.objectX == 3296 && c.objectY == 5455) {
				c.getPA().movePlayer(3299, 5450, 0);
			}
			if (c.objectX == 3299 && c.objectY == 5450) {
				c.getPA().movePlayer(3296, 5455, 0);
			}
			if (c.objectX == 3280 && c.objectY == 5501) {
				c.getPA().movePlayer(3285, 5508, 0);
			}
			if (c.objectX == 3285 && c.objectY == 5508) {
				c.getPA().movePlayer(3280, 5501, 0);
			}
			if (c.objectX == 3300 && c.objectY == 5514) {
				c.getPA().movePlayer(3297, 5510, 0);
			}
			if (c.objectX == 3297 && c.objectY == 5510) {
				c.getPA().movePlayer(3300, 5514, 0);
			}
			if (c.objectX == 3289 && c.objectY == 5533) {
				c.getPA().movePlayer(3288, 5536, 0);
			}
			if (c.objectX == 3288 && c.objectY == 5536) {
				c.getPA().movePlayer(3289, 5533, 0);
			}
			if (c.objectX == 3285 && c.objectY == 5527) {
				c.getPA().movePlayer(3282, 5531, 0);
			}
			if (c.objectX == 3282 && c.objectY == 5531) {
				c.getPA().movePlayer(3285, 5527, 0);
			}
			if (c.objectX == 3325 && c.objectY == 5518) {
				c.getPA().movePlayer(3323, 5531, 0);
			}
			if (c.objectX == 3323 && c.objectY == 5531) {
				c.getPA().movePlayer(3325, 5518, 0);
			}
			if (c.objectX == 3299 && c.objectY == 5533) {
				c.getPA().movePlayer(3297, 5536, 0);
			}
			if (c.objectX == 3297 && c.objectY == 5536) {
				c.getPA().movePlayer(3299, 5533, 0);
			}
			if (c.objectX == 3321 && c.objectY == 5554) {
				c.getPA().movePlayer(3315, 5552, 0);
			}
			if (c.objectX == 3315 && c.objectY == 5552) {
				c.getPA().movePlayer(3321, 5554, 0);
			}
			if (c.objectX == 3291 && c.objectY == 5555) {
				c.getPA().movePlayer(3285, 5556, 0);
			}
			if (c.objectX == 3285 && c.objectY == 5556) {
				c.getPA().movePlayer(3291, 5555, 0);
			}
			if (c.objectX == 3266 && c.objectY == 5552) {
				c.getPA().movePlayer(3262, 5552, 0);
			}
			if (c.objectX == 3262 && c.objectY == 5552) {
				c.getPA().movePlayer(3266, 5552, 0);
			}
			if (c.objectX == 3256 && c.objectY == 5561) {
				c.getPA().movePlayer(3253, 5561, 0);
			}
			if (c.objectX == 3253 && c.objectY == 5561) {
				c.getPA().movePlayer(3256, 5561, 0);
			}
			if (c.objectX == 3249 && c.objectY == 5546) {
				c.getPA().movePlayer(3252, 5543, 0);
			}
			if (c.objectX == 3252 && c.objectY == 5543) {
				c.getPA().movePlayer(3249, 5546, 0);
			}
			if (c.objectX == 3261 && c.objectY == 5536) {
				c.getPA().movePlayer(3268, 5534, 0);
			}
			if (c.objectX == 3268 && c.objectY == 5534) {
				c.getPA().movePlayer(3261, 5536, 0);
			}
			if (c.objectX == 3243 && c.objectY == 5526) {
				c.getPA().movePlayer(3241, 5529, 0);
			}
			if (c.objectX == 3241 && c.objectY == 5529) {
				c.getPA().movePlayer(3243, 5526, 0);
			}
			if (c.objectX == 3230 && c.objectY == 5547) {
				c.getPA().movePlayer(3226, 5553, 0);
			}
			if (c.objectX == 3226 && c.objectY == 5553) {
				c.getPA().movePlayer(3230, 5547, 0);
			}
			if (c.objectX == 3206 && c.objectY == 5553) {
				c.getPA().movePlayer(3204, 5546, 0);
			}
			if (c.objectX == 3204 && c.objectY == 5546) {
				c.getPA().movePlayer(3206, 5553, 0);
			}
			if (c.objectX == 3211 && c.objectY == 5533) {
				c.getPA().movePlayer(3214, 5533, 0);
			}
			if (c.objectX == 3214 && c.objectY == 5533) {
				c.getPA().movePlayer(3211, 5533, 0);
			}
			if (c.objectX == 3208 && c.objectY == 5527) {
				c.getPA().movePlayer(3211, 5523, 0);
			}
			if (c.objectX == 3211 && c.objectY == 5523) {
				c.getPA().movePlayer(3208, 5527, 0);
			}
			if (c.objectX == 3201 && c.objectY == 5531) {
				c.getPA().movePlayer(3197, 5529, 0);
			}
			if (c.objectX == 3197 && c.objectY == 5529) {
				c.getPA().movePlayer(3201, 5531, 0);
			}
			if (c.objectX == 3202 && c.objectY == 5515) {
				c.getPA().movePlayer(3196, 5512, 0);
			}
			if (c.objectX == 3196 && c.objectY == 5512) {
				c.getPA().movePlayer(3202, 5515, 0);
			}
			if (c.objectX == 3190 && c.objectY == 5515) {
				c.getPA().movePlayer(3190, 5519, 0);
			}
			if (c.objectX == 3190 && c.objectY == 5519) {
				c.getPA().movePlayer(3190, 5515, 0);
			}
			if (c.objectX == 3185 && c.objectY == 5518) {
				c.getPA().movePlayer(3181, 5517, 0);
			}
			if (c.objectX == 3181 && c.objectY == 5517) {
				c.getPA().movePlayer(3185, 5518, 0);
			}
			if (c.objectX == 3187 && c.objectY == 5531) {
				c.getPA().movePlayer(3182, 5530, 0);
			}
			if (c.objectX == 3182 && c.objectY == 5530) {
				c.getPA().movePlayer(3187, 5531, 0);
			}
			if (c.objectX == 3169 && c.objectY == 5510) {
				c.getPA().movePlayer(3159, 5501, 0);
			}
			if (c.objectX == 3159 && c.objectY == 5501) {
				c.getPA().movePlayer(3169, 5510, 0);
			}
			if (c.objectX == 3165 && c.objectY == 5515) {
				c.getPA().movePlayer(3173, 5530, 0);
			}
			if (c.objectX == 3173 && c.objectY == 5530) {
				c.getPA().movePlayer(3165, 5515, 0);
			}
			if (c.objectX == 3156 && c.objectY == 5523) {
				c.getPA().movePlayer(3152, 5520, 0);
			}
			if (c.objectX == 3152 && c.objectY == 5520) {
				c.getPA().movePlayer(3156, 5523, 0);
			}
			if (c.objectX == 3148 && c.objectY == 5533) {
				c.getPA().movePlayer(3153, 5537, 0);
			}
			if (c.objectX == 3153 && c.objectY == 5537) {
				c.getPA().movePlayer(3148, 5533, 0);
			}
			if (c.objectX == 3143 && c.objectY == 5535) {
				c.getPA().movePlayer(3147, 5541, 0);
			}
			if (c.objectX == 3147 && c.objectY == 5541) {
				c.getPA().movePlayer(3143, 5535, 0);
			}
			if (c.objectX == 3168 && c.objectY == 5541) {
				c.getPA().movePlayer(3171, 5542, 0);
			}
			if (c.objectX == 3171 && c.objectY == 5542) {
				c.getPA().movePlayer(3168, 5541, 0);
			}
			if (c.objectX == 3190 && c.objectY == 5549) {
				c.getPA().movePlayer(3190, 5554, 0);
			}
			if (c.objectX == 3190 && c.objectY == 5554) {
				c.getPA().movePlayer(3190, 5549, 0);
			}
			if (c.objectX == 3180 && c.objectY == 5557) {
				c.getPA().movePlayer(3174, 5558, 0);
			}
			if (c.objectX == 3174 && c.objectY == 5558) {
				c.getPA().movePlayer(3180, 5557, 0);
			}
			if (c.objectX == 3162 && c.objectY == 5557) {
				c.getPA().movePlayer(3158, 5561, 0);
			}
			if (c.objectX == 3158 && c.objectY == 5561) {
				c.getPA().movePlayer(3162, 5557, 0);
			}
			if (c.objectX == 3166 && c.objectY == 5553) {
				c.getPA().movePlayer(3162, 5545, 0);
			}
			if (c.objectX == 3162 && c.objectY == 5545) {
				c.getPA().movePlayer(3166, 5553, 0);
			}
			if (c.objectX == 3142 && c.objectY == 5545) {
				c.getPA().movePlayer(3115, 5528, 0);
			}
			break;

		// Compa
	case 172:
			if (c.playerLevel[0] >= 99 && c.playerLevel[1] >= 99
					&& c.playerLevel[2] >= 99 && c.playerLevel[3] >= 99
					&& c.playerLevel[4] >= 99 && c.playerLevel[5] >= 99
					&& c.playerLevel[6] >= 99 && c.playerLevel[7] >= 99
					&& c.playerLevel[8] >= 99 && c.playerLevel[9] >= 99
					&& c.playerLevel[10] >= 99 && c.playerLevel[11] >= 99
					&& c.playerLevel[12] >= 99 && c.playerLevel[13] >= 99
					&& c.playerLevel[14] >= 99 && c.playerLevel[15] >= 99
					&& c.playerLevel[16] >= 99 && c.playerLevel[17] >= 99
					&& c.playerLevel[18] >= 99 && c.playerLevel[19] >= 99
					&& c.playerLevel[20] >= 99 && c.playerLevel[21] >= 99
					&& c.playerLevel[22] >= 99 && c.playerLevel[23] >= 99
					&& c.pTime >= 1209600) {
				c.getDH().sendDialogues(3515, 1);
			} else {
				if (c.playerLevel[0] < 99 && c.playerLevel[1] < 99
						&& c.playerLevel[2] < 99 && c.playerLevel[3] < 99
						&& c.playerLevel[4] < 99 && c.playerLevel[5] < 99
						&& c.playerLevel[6] < 99 && c.playerLevel[7] < 99
						&& c.playerLevel[8] < 99 && c.playerLevel[9] < 99
						&& c.playerLevel[10] < 99 && c.playerLevel[11] < 99
						&& c.playerLevel[12] < 99 && c.playerLevel[13] < 99
						&& c.playerLevel[14] < 99 && c.playerLevel[15] < 99
						&& c.playerLevel[16] < 99 && c.playerLevel[17] < 99
						&& c.playerLevel[18] < 99 && c.playerLevel[19] < 99
						&& c.playerLevel[20] < 99 && c.playerLevel[21] < 99
						&& c.playerLevel[22] < 99 && c.playerLevel[23] < 99
						&& c.pTime < 1209600) {

					c.sendMessage("Come back when you have achieved maxed stats, and have been playing for total of 7 days!");
				} else {
					if (c.playerLevel[0] < 99 && c.playerLevel[1] < 99
							&& c.playerLevel[2] < 99 && c.playerLevel[3] < 99
							&& c.playerLevel[4] < 99 && c.playerLevel[5] < 99
							&& c.playerLevel[6] < 99 && c.playerLevel[7] < 99
							&& c.playerLevel[8] < 99 && c.playerLevel[9] < 99
							&& c.playerLevel[10] < 99 && c.playerLevel[11] < 99
							&& c.playerLevel[12] < 99 && c.playerLevel[13] < 99
							&& c.playerLevel[14] < 99 && c.playerLevel[15] < 99
							&& c.playerLevel[16] < 99 && c.playerLevel[17] < 99
							&& c.playerLevel[18] < 99 && c.playerLevel[19] < 99
							&& c.playerLevel[20] >= 99 && c.pTime >= 1209600) {

						c.sendMessage("Come back when you have achieved maxed stats!");
					} else {
						if (c.playerLevel[0] >= 99 && c.playerLevel[1] >= 99
								&& c.playerLevel[2] >= 99
								&& c.playerLevel[3] >= 99
								&& c.playerLevel[4] >= 99
								&& c.playerLevel[5] >= 99
								&& c.playerLevel[6] >= 99
								&& c.playerLevel[7] >= 99
								&& c.playerLevel[8] >= 99
								&& c.playerLevel[9] >= 99
								&& c.playerLevel[10] >= 99
								&& c.playerLevel[11] >= 99
								&& c.playerLevel[12] >= 99
								&& c.playerLevel[13] >= 99
								&& c.playerLevel[14] >= 99
								&& c.playerLevel[15] >= 99
								&& c.playerLevel[16] >= 99
								&& c.playerLevel[17] >= 99
								&& c.playerLevel[18] >= 99
								&& c.playerLevel[19] >= 99
								&& c.playerLevel[20] >= 99 && c.pTime < 1209600) {

							c.sendMessage("Come back when you have been playing for total of 7 days!");
						}
					}
				}
			}
			break;
		// Borks portal
		case 29537:
			if (c.objectX == 3115 && c.objectY == 5528) {
				c.getPA().movePlayer(3142, 5545, 0);
			}
			break;
		// Chaos tunnel entrances
		case 28891:
			if (c.underAttackBy < 0 || c.underAttackBy2 < 0)
				c.sendMessage("You cannot enter the rift whilst your under attack.");
			else
				c.getPA().movePlayer(3183, 5470, 0);
			break;
		case 28892:
			if (c.objectX == 3165 && c.objectY == 3561) {
				if (c.underAttackBy < 0 || c.underAttackBy2 < 0)
					c.sendMessage("You cannot enter the rift whilst your under attack.");
				else
					c.getPA().movePlayer(3292, 5479, 0);
			}
			if (c.objectX == 3165 && c.objectY == 3618) {
				if (c.underAttackBy < 0 || c.underAttackBy2 < 0)
					c.sendMessage("You cannot enter the rift whilst your under attack.");
				else
					c.getPA().movePlayer(3291, 5538, 0);
			}
			break;
		case 28893:
			if (c.objectX == 3119 && c.objectY == 3571) {
				if (c.underAttackBy < 0 || c.underAttackBy2 < 0)
					c.sendMessage("You cannot enter the rift whilst your under attack.");
				else
					c.getPA().movePlayer(3248, 5490, 0);
			}
			if (c.objectX == 3107 && c.objectY == 3639) {
				if (c.underAttackBy < 0 || c.underAttackBy2 < 0)
					c.sendMessage("You cannot enter the rift whilst your under attack.");
				else
					c.getPA().movePlayer(3234, 5559, 0);
			}
			break;
		// Chaos tunnel exits
		case 28782:
			if (c.objectX == 3183 && c.objectY == 5470) {
				c.getPA().movePlayer(3059, 3549, 0);
			}
			if (c.objectX == 3248 && c.objectY == 5490) {
				c.getPA().movePlayer(3120, 3571, 0);
			}
			if (c.objectX == 3292 && c.objectY == 5479) {
				c.getPA().movePlayer(3166, 3561, 0);
			}
			if (c.objectX == 3291 && c.objectY == 5538) {
				c.getPA().movePlayer(3166, 3618, 0);
			}
			if (c.objectX == 3234 && c.objectY == 5559) {
				c.getPA().movePlayer(3107, 3640, 0);
			}
			break;
		case 1766:
			c.getPA().movePlayer(3016, 3849, 0);
			break;
		case 10230:
			c.getDH().sendStatement(
					"@red@This area is dangerous@bla@,@red@ You sure you want to enter@bla@, "
							+ c.playerName + "?");
			c.getPA().movePlayer(2911, 4444, 0);
			break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
			break;
		case 1765:
			c.getPA().movePlayer(2271, 4680, 0);
			break;
		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);
			break;
		case 9319:
			c.getPA().startTeleport2(3422, 3549, 1);
			break;
		case 1817:
			c.getPA().startTeleport(3067, 10253, 0, "modern");
			break;

		case 37929:
			c.getPA().movePlayer(2921, 4384, 0);
			break;
		/** Farming */

		case 7960:
		case 8018:
		case 8076:
		case 8045:
		case 8103:
		case 8501:
		case 8461:
		case 8534:
		case 8434:
			TreePicking.removeDeadTree(c);
			break;
		case 6702:
		case 6703:
		case 6704:
		case 6705:
		case 6706:
		case 6707:
			c.getBarrows().useStairs();
			break;
		case 8091:
		case 7948:
		case 8006:
		case 8064:
		case 8033:
		case 8488:
		case 8444:
		case 8513:
		case 8409:
			TreePicking.treePicking(c, c.currentTree);
			break;
		case 2287:
			if (c.playerLevel[16] >= 30) {
				c.getPA().movePlayer(2552, 3558, 0);
			} else {
				c.sendMessage("You need level 30 agility to access this agility course");
			}
			break;
		case 1294:
			c.getDH().sendDialogues(454, 0);
			break;
		case 8143:
			HerbPicking.herbPicking(c, c.currentHerb);
			break;
		case 29728:
			c.getPA().movePlayer(3159, 4279, 3);
			break;
		case 8389:
		case 8151:
			if (c.getItems().playerHasItem(5341)) {
				c.startAnimation(2273);
				c.sendMessage("You rake away the weeds, and it turns into a nice growing area.");
				c.getPA().object(8392, 2813, 3463, -1, 10);
			} else {
				c.sendMessage("You need a rake to eliminate the weeds in this area.");
			}
			break;
		case 8392:
			c.sendMessage("I should probably plant some sort of seed in here.");
			break;
		case 10284:
			c.getBarrows().useChest();
			break;

		case 6823:
			if (c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY() - 1, 3,
						0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6772:
			if (c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX() + 1, c.getY(), 3,
						0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6822:
			if (c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY() - 1, 3,
						0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6773:
			if (c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY() - 1, 3,
						0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6771:
			c.getDH().sendDialogues(29, 2026);
			break;

		case 6821:
			if (c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX() - 1, c.getY(), 3,
						0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
		case 1739:
		case 12537:
			c.getPA().handleStairs();
			c.dialogueAction = 850;
			break;

		case 1738:
		case 12536:
			c.getPA().handleUp();
			c.dialogueAction = 851;
			break;

		case 1740:
		case 12538:
			c.getPA().handleDown();
			c.dialogueAction = 852;
			break;
		/*
		 * case 1738: if (c.absX == 3206) { c.getPA().movePlayer(3206, 3209, 1);
		 * } else { c.getPA().movePlayer(2841, 3538, c.heightLevel);
		 * c.heightLevel = 2; } break;
		 */
		case 15638:
			c.getPA().movePlayer(2841, 3538, c.heightLevel);
			c.heightLevel = 0;
			break;
		case 15644:
		case 15641:
			if (c.heightLevel == 2) {
				if (c.absX == 2846) {
					WarriorsGuild.enterRoom(c);
				} else {
					c.inDefenderRoom = false;
					CycleEventHandler.getSingleton().stopEvents(c);
					c.getPA().movePlayer(2846, 3540, c.heightLevel);
				}
			} else {
				if (c.getY() == 3545) {
					c.getPA().movePlayer(c.getX(), c.getY() + 1, c.heightLevel);
				} else {
					c.getPA().movePlayer(c.getX(), c.getY() - 1, c.heightLevel);
				}
			}
			break;
		case 1733:
			c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
			break;

		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
			break;
		case 14315:
			if (!PestControl.waitingBoat.containsKey(c)) {
				PestControl.addToWaitRoom(c);
			} else {
				c.getPA().movePlayer(2661, 2639, 0);
			}
			break;
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else
				c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
			break;
		case 14314:
			if (c.inPcBoat()) {
				if (PestControl.waitingBoat.containsKey(c)) {
					PestControl.leaveWaitingBoat(c);
				} else {
					c.getPA().movePlayer(2657, 2639, 0);
				}
			}
			break;
		case 245:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY + 2, 2);
			break;
		case 246:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY - 2, 1);
			break;
		case 272:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 1);
			break;
		case 273:
			this.c.getPA().movePlayer(this.c.absX, this.c.absY, 0);
			break;
		/* Godwars Door */
		case 26426: // armadyl
			if (c.absX == 2839 && c.absY == 5295) {
				c.getPA().movePlayer(2839, 5296, 2);
				// c.sendMessage("@blu@eazy wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2839, 5295, 2);
			}
			break;
		case 26425: // bandos
			if (c.absX == 2863 && c.absY == 5354) {
				c.getPA().movePlayer(2864, 5354, 2);
				// c.sendMessage("@blu@eazy wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2863, 5354, 2);
			}
			break;
		case 26428: // bandos
			if (c.absX == 2925 && c.absY == 5332) {
				c.getPA().movePlayer(2925, 5331, 2);
				// c.sendMessage("@blu@eazy wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2925, 5332, 2);
			}
			break;
		case 26427: // bandos
			if (c.absX == 2908 && c.absY == 5265) {
				c.getPA().movePlayer(2907, 5265, 0);
				// c.sendMessage("@blu@eazy wishes the best of luck to you");
			} else {
				c.getPA().movePlayer(2908, 5265, 0);
			}
			break;

		case 5960:
			c.getPA().startTeleport(3090, 3956, 0, "modern");
			break;
		case 3223:
		//case 21764:
		case 411:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				// c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			}
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.startAnimation(645);
			c.specAmount = 10.0;
			c.sendMessage(Double.toString(c.specAmount) + ":specialattack:");
			c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
			c.sendMessage("@red@You've recharged your special attack, prayer points and hitpoints.");
			break;
		case 5959:
			c.getPA().movePlayer(2539, 4712, 0);
			break;
		case 1814:
			this.c.getPA().movePlayer(3153, 3923, 0);
			break;
		case 1815:
			this.c.getPA().movePlayer(3087, 3500, 0);
			break;
		/* Start Brimhavem Dungeon */
		case 2879:
			c.getPA().movePlayer(2542, 4718, 0);
			break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
			break;
		case 3782:
			c.getPA().movePlayer(2897, 3618, 0);
			break;
		case 3783:
			c.getPA().movePlayer(2897, 3619, 0);
			break;
		case 3785:	
			c.getPA().movePlayer(2916, 3629, 0);
			break;
		case 3786:
			c.getPA().movePlayer(2917, 3629, 0);
			break;
		case 5083:
			c.getPA().movePlayer(2713, 9564, 0);
			c.sendMessage("You enter the dungeon.");
			break;
		case 2492:
			c.sendMessage("Use the arena exit.");
			break;
		case 6238:
		case 6240:
			c.sendMessage("The doors are locked.");
			break;

		case 5103:
			if (c.absX == 2691 && c.absY == 9564) {
				c.getPA().movePlayer(2689, 9564, 0);
			} else if (c.absX == 2689 && c.absY == 9564) {
				c.getPA().movePlayer(2691, 9564, 0);
			}
			break;

		case 5106:
			if (c.absX == 2674 && c.absY == 9479) {
				c.getPA().movePlayer(2676, 9479, 0);
			} else if (c.absX == 2676 && c.absY == 9479) {
				c.getPA().movePlayer(2674, 9479, 0);
			}
			break;

		case 5105:
			if (c.absX == 2672 && c.absY == 9499) {
				c.getPA().movePlayer(2674, 9499, 0);
			} else if (c.absX == 2674 && c.absY == 9499) {
				c.getPA().movePlayer(2672, 9499, 0);
			}
			break;

		case 5107:
			if (c.absX == 2693 && c.absY == 9482) {
				c.getPA().movePlayer(2695, 9482, 0);
			} else if (c.absX == 2695 && c.absY == 9482) {
				c.getPA().movePlayer(2693, 9482, 0);
			}
			break;

		case 5104:
			if (c.absX == 2683 && c.absY == 9568) {
				c.getPA().movePlayer(2683, 9570, 0);
			} else if (c.absX == 2683 && c.absY == 9570) {
				c.getPA().movePlayer(2683, 9568, 0);
			}
			break;

		case 5100:
			if (c.absY <= 9567) {
				c.getPA().movePlayer(2655, 9573, 0);
			} else if (c.absY >= 9572) {
				c.getPA().movePlayer(2655, 9566, 0);
			}
			break;

		case 5099:
			if (c.absY <= 9493) {
				c.getPA().movePlayer(2698, 9500, 0);
			} else if (c.absY >= 9499) {
				c.getPA().movePlayer(2698, 9492, 0);
			}
			break;

		case 5088:
			if (c.absX <= 2682) {
				c.getPA().movePlayer(2687, 9506, 0);
			} else if (c.absX >= 2687) {
				c.getPA().movePlayer(2682, 9506, 0);
			}
			break;

		case 5110:
			c.getPA().movePlayer(2647, 9557, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5111:
			c.getPA().movePlayer(2649, 9562, 0);
			c.sendMessage("You cross the lava");
			break;

		case 5084:
			c.getPA().movePlayer(2744, 3151, 0);
			c.sendMessage("You exit the dungeon.");
			break;
		/* End Brimhavem Dungeon */
		case 6481:
			c.getPA().movePlayer(3233, 9315, 0);
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;

		case 1551:
			if (c.absX == 3252 && c.absY == 3266) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3266) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;
		case 1553:
			if (c.absX == 3252 && c.absY == 3267) {
				c.getPA().movePlayer(3253, 3266, 0);
			}
			if (c.absX == 3253 && c.absY == 3267) {
				c.getPA().movePlayer(3252, 3266, 0);
			}
			break;

		case 2491:
			Mining.mineEss(c, objectType);
			break;
		case 3044:
			c.getSmithing().sendSmelting();
			break;
		// abyss rifts
		case 7129: // fire riff
			Runecrafting.craftRunesOnAltar(c, 14, 7, 554, 50, 60, 70);
			break;
		case 7130: // earth riff
			Runecrafting.craftRunesOnAltar(c, 9, 7, 557, 45, 55, 65);
			break;
		case 7131: // body riff
			Runecrafting.craftRunesOnAltar(c, 20, 8, 559, 55, 65, 75);
			break;
		case 7132: // cosmic riff
			Runecrafting.craftRunesOnAltar(c, 35, 10, 564, 72, 84, 96);
			break;
		case 7133: // nat riff
			Runecrafting.craftRunesOnAltar(c, 44, 9, 561, 60, 74, 91);
			break;
		case 7134: // chaos riff
			Runecrafting.craftRunesOnAltar(c, 35, 9, 562, 60, 70, 80);
			break;
		case 7135: // law riff
			Runecrafting.craftRunesOnAltar(c, 54, 10, 563, 65, 79, 93);
			break;
		case 7136: // death riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 560, 72, 84, 96);
			break;
		case 7137: // water riff
			Runecrafting.craftRunesOnAltar(c, 5, 6, 555, 30, 45, 60);
			break;
		case 7138: // soul riff
			Runecrafting.craftRunesOnAltar(c, 65, 10, 566, 72, 84, 96);
			break;
		case 7139: // air riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 556, 30, 45, 60);
			break;
		case 7140: // mind riff
			Runecrafting.craftRunesOnAltar(c, 1, 5, 558, 30, 45, 60);
			break;
		case 7141: // blood rift
			Runecrafting.craftRunesOnAltar(c, 77, 11, 565, 89, 94, 99);
			break;

		/* AL KHARID */
		case 2883:
		case 2882:
			c.getDH().sendDialogues(1023, 925);
			break;
		case 2412:
			Sailing.startTravel(c, 1);
			break;
		case 2414:
			Sailing.startTravel(c, 2);
			break;
		case 2083:
			Sailing.startTravel(c, 5);
			break;
		case 2081:
			Sailing.startTravel(c, 6);
			break;
		case 14304:
			Sailing.startTravel(c, 14);
			break;
		case 14306:
			Sailing.startTravel(c, 15);
			break;

		
		case 3045:
		case 14367:
		case 11758:
		case 3193:
		case 10517:
		case 11402:
		case 26972:
			c.getPA().openUpBank();
			break;

		case 4483:
		case 2213:
			c.getPA().openUpBank();
			break;

		/**
		 * Entering the Fight Caves.
		 */
		
		case 9356:
			c.getPA().enterCaves();
		break;
		 

		/**
		 * Clicking on the Ancient Altar.
		 */
		case 10378:
			if (!c.getItems().playerHasItem(2677, 1)) {
			} else if (c.getItems().playerHasItem(2677, 1)
					&& c.getItems().freeSlots() >= 2) {
				c.getItems().addItem(c.getPA().randomRunes(),
						Misc.random(150) + 100);
				c.getItems().deleteItem(2677, 1);
				if (Misc.random(3) == 1)
					c.getItems().addItem(c.getPA().randomClue1(), 1);
			} else if (c.getItems().playerHasItem(2677, 1)
					&& c.getItems().freeSlots() >= 2) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;

		case 6552:
			if (c.playerMagicBook == 0 || c.playerMagicBook == 2) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.autocasting = false;
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		case 410:
			if (c.playerMagicBook == 0 && c.playerLevel[1] >= 1) {
				c.playerMagicBook = 2;
				c.startAnimation(645);
				c.setSidebarInterface(6, 29999);
				c.sendMessage("Lunar spells activated!");
				c.getPA().resetAutocast();
			} else {
				c.startAnimation(645);
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a strange drain upon your memory...");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			break;

		/**
		 * Recharing prayer points.
		 */
		case 409:
		case 4859:
		case 24343:
		case 26289:
		case 26288:
		case 26287:
		case 26286:
		case 19145:
			if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;

		/**
		 * Aquring god capes.
		 */
		case 2873: // Saradomin Statue
			c.getDH().sendDialogues(10000, -1);
			break;

		case 2875: // Guthix Statue
			break;

		case 2874: // Zammy statue
			c.getDH().sendDialogues(10004, -1);
			break;

		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
			break;

		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if (obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6730:
			if (obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if (obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;

		case 6727:
			if (obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6746:
			if (obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;

		case 6748:
			if (obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6729:
			if (obX == 3545 && obY == 9677) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if (obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;

		case 6726:
			if (obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6745:
			if (obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if (obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;

		case 6743:
			if (obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 6724:
			if (obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if (obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;

		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().playerWalk(0, -1);
				else
					c.getPA().playerWalk(0, 1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
			if (c.objectY == 3211) {
				if (c.absY >= c.objectY)
					c.getPA().playerWalk(0, +1);
				else if (c.objectY == 3211)
					if (c.absY >= c.objectY)
						c.getPA().movePlayer(3208, 3211, 0);
				break;
			}
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY,
					0);
			break;
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0, 1);
			else
				c.getPA().walkTo(0, -1);
			break;

		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			break;
		case 1558:
			if (c.absX == 3041 && c.absY == 10308) {
				c.getPA().movePlayer(3040, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10308) {
				c.getPA().movePlayer(3041, 10308, 0);
			} else if (c.absX == 3040 && c.absY == 10307) {
				c.getPA().movePlayer(3041, 10307, 0);
			} else if (c.absX == 3041 && c.absY == 10307) {
				c.getPA().movePlayer(3040, 10307, 0);
			} else if (c.absX == 3044 && c.absY == 10341) {
				c.getPA().movePlayer(3045, 10341, 0);
			} else if (c.absX == 3045 && c.absY == 10341) {
				c.getPA().movePlayer(3044, 10341, 0);
			} else if (c.absX == 3044 && c.absY == 10342) {
				c.getPA().movePlayer(3045, 10342, 0);
			} else if (c.absX == 3045 && c.absY == 10342) {
				c.getPA().movePlayer(3045, 10342, 0);
			}
			break;
		case 1557:
			if (c.absX == 3023 && c.absY == 10312) {
				c.getPA().movePlayer(3022, 10312, 0);
			} else if (c.absX == 3022 && c.absY == 10312) {
				c.getPA().movePlayer(3023, 10312, 0);
			} else if (c.absX == 3023 && c.absY == 10311) {
				c.getPA().movePlayer(3022, 10311, 0);
			} else if (c.absX == 3022 && c.absY == 10311) {
				c.getPA().movePlayer(3023, 10311, 0);
			}
			break;
		case 2558:
			c.sendMessage("This door is locked.");
			break;

		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
			break;

		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;

		case 10529:
			if (c.absX == 3427 && c.absY == 3556) {
				c.getPA().movePlayer(3427, 3555, 1);
			}
			if (c.absX == 3427 && c.absY == 3555) {
				c.getPA().movePlayer(3427, 3556, 1);
			}
			break;
		case 10527:
			if (c.absX == 3426 && c.absY == 3556) {
				c.getPA().movePlayer(3426, 3555, 1);
			}
			if (c.absX == 3426 && c.absY == 3555) {
				c.getPA().movePlayer(3426, 3556, 1);
			}
			break;

		case 733:
			c.startAnimation(451);

			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10,
						733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10,
						733, 50);
			}
			break;

		default:
			ScriptManager.callFunc("objectClick1_" + objectType, c, objectType,
					obX, obY);
			break;

		/**
		 * Forfeiting a duel.
		 */
		case 3203:
			c.sendMessage("Forfeiting has been disabled.");
			break;

		}
	}

	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.turnPlayerTo(c.objectX, c.objectY);
		// c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		case 4874:
			c.getThieving().stealFromStall(1625, 1, 8, 1);
			break;
		case 4875:
			c.getThieving().stealFromStall(1623, 1, 30, 25);
			break;
		case 4876:
			c.getThieving().stealFromStall(1621, 1, 80, 50);
			break;
		case 4877:
			c.getThieving().stealFromStall(1619, 1, 125, 75);
			break;
		case 4878:
			c.getThieving().stealFromStall(1617, 1, 150, 90);
			break;
		case 6572:
			break;
		case 1739:
			c.getPA().movePlayer(c.absX, c.absY, 2);
			break;
		case 8689:

			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				if (c.getItems().playerHasItem(1925, 1)) {
					c.turnPlayerTo(c.objectX, c.objectY);
					c.startAnimation(2292);
					c.getItems().addItem(1927, 1);
					c.getItems().deleteItem(1925, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.sendMessage("You need a bucket to milk a cow!");
				}
			}
			break;
		/** Farming */
		case 7960:
		case 8018:
		case 8076:
		case 8045:
		case 8103:
		case 8501:
		case 8461:
		case 8534:
		case 8434:
			TreePicking.removeDeadTree(c);
			break;
		case 8151:
		case 8389:
			if (c.getItems().playerHasItem(5341)) {
				c.startAnimation(2273);
				c.sendMessage("You rake away the weeds, and it turns into a nice growing area.");
				c.getPA().object(8392, 2813, 3463, -1, 10);
			} else {
				c.sendMessage("You need a rake to eliminate the weeds in this area.");
			}
			break;
		case 8392:
			c.sendMessage("I should probably plant some sort of seed in here.");
			break;
		case 7848:
			c.sendMessage("This patch doesn't seem to work.");
			break;
		case 2558:
		case 2557:
			if (System.currentTimeMillis() - c.lastLockPick < 1000
					|| c.freezeTimer > 0) {
				return;
			}
			c.lastLockPick = System.currentTimeMillis();
			if (c.getItems().playerHasItem(1523, 1)) {

				if (Misc.random(10) <= 3) {
					c.sendMessage("You fail to pick the lock.");
					break;
				}
				if (c.objectX == 3044 && c.objectY == 3956) {
					if (c.absX == 3045) {
						c.getPA().playerWalk(-1, 0);
					} else if (c.absX == 3044) {
						c.getPA().playerWalk(1, 0);
					}

				} else if (c.objectX == 3038 && c.objectY == 3956) {
					if (c.absX == 3037) {
						c.getPA().playerWalk(1, 0);
					} else if (c.absX == 3038) {
						c.getPA().playerWalk(-1, 0);
					}
				} else if (c.objectX == 3041 && c.objectY == 3959) {
					if (c.absY == 3960) {
						c.getPA().playerWalk(0, -1);
					} else if (c.absY == 3959) {
						c.getPA().playerWalk(0, 1);
					}
				} else if (c.objectX == 3191 && c.objectY == 3963) {
					if (c.absY == 3963) {
						c.getPA().playerWalk(0, -1);
					} else if (c.absY == 3962) {
						c.getPA().playerWalk(0, 1);
					}
				} else if (c.objectX == 3190 && c.objectY == 3957) {
					if (c.absY == 3957) {
						c.getPA().playerWalk(0, 1);
					} else if (c.absY == 3958) {
						c.getPA().playerWalk(0, -1);
					}
				}
			} else {
				c.sendMessage("I need a lockpick to pick this lock.");
			}
			break;
		case 2882:
		case 2883:
			if (c.getItems().playerHasItem(995, 10)) {
				c.getItems().deleteItem(995, 10);
				c.sendMessage("You pass the gate.");
				Special.movePlayer(c);
				Special.openKharid(c, c.objectId);
				c.turnPlayerTo(c.objectX, c.objectY);
			} else {
				c.sendMessage("You need 10 coins to enter Al-Kharid");
			}
			break;
		case 17010:
			if (c.playerMagicBook == 0) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 1) {
				c.sendMessage("You switch spellbook to lunar magic.");
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			if (c.playerMagicBook == 2) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				break;
			}
			break;
		/*
		 * One stall that will give different amount of money depending on your
		 * thieving level, also different amount of xp.
		 */

		case 2781:
		case 26814:
		case 11666:
		case 3044:
			c.getSmithing().sendSmelting();
			break;

		case 2646:
			Flax.pickFlax(c, obX, obY);
			break;

		/**
		 * Opening the bank.
		 */
		case 2213:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
		case 4483:
			c.getPA().openUpBank();
			break;

		}
	}

	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		// c.sendMessage("Object type: " + objectType);
		switch (objectType) {
		case 2882:
		case 2883:
			if (c.getItems().playerHasItem(995, 10)) {
				c.getItems().deleteItem(995, 10);
				c.sendMessage("You pass the gate.");
				Special.movePlayer(c);
				Special.openKharid(c, c.objectId);
				c.turnPlayerTo(c.objectX, c.objectY);
			} else {
				c.sendMessage("You need 10 coins to enter Al-Kharid");
			}
			break;
		default:
			ScriptManager.callFunc("objectClick3_" + objectType, c, objectType,
					obX, obY);
			break;

		}
	}

	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		Pet.pickUpPetRequirements(c, npcType);
		c.npcClickIndex = 0;

		if (Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 1, npcType);
			return;
		}

		switch (npcType) {
		case 1152:
			c.getDH().sendDialogues(16, npcType);
			break;
		case 825:
		c.getDH().sendDialogues(1812, npcType);
		break;
		case 4511:
			c.getDH().sendDialogues(813, npcType);
			break;
		case 2283:
			c.getDH().sendDialogues(815, npcType);
			break;
		case 1596:
			c.getDH().sendDialogues(228, npcType);
			break;
		case 542: // VOTE
			c.getShops().openShop(14);
			// c.sendMessage("@yel@Use ::vote to vote and ::checkvote to claim reward!");
			break;
		case 2622: //tzhaar ore
			c.getShops().openShop(21);
			break;
		case 534: // DONOR
			c.getShops().openShop(9);
			break;
		case 2566: // SKILLCAPE
			c.getShops().openSkillCape();
			break;
		case 1282:
			c.getShops().openShop(8);
			break;
		case 652:
			c.getDH().sendDialogues(2244, c.npcType);
			break;
		case 605:
			c.getDH().sendDialogues(5555, c.npcType);
			break;
		case 791:
			c.getDH().sendDialogues(129, 791);
			break;
		case 599:
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			break;

		case 3789:
			c.sendMessage("Your current Pest Control points are @blu@"
					+ c.pcPoints);
			break;
		case 1702:
			c.getDH().sendDialogues(15001, npcType);
			break;
		case 872:
			c.getDH().sendDialogues(817, npcType);
			break;

		case 596:
			c.getShops().openShop(6);
			break;
		case 847:
			c.getShops().openShop(22);
			break;
		case 580:
			c.getShops().openShop(19);
			break;
		case 659:
			c.getShops().openShop(11);
			break;

		case 2305:
			c.getShops().openShop(16);
			break;
		case 576:
			c.getShops().openShop(70);
			break;
		case 586:
			c.getShops().openShop(17);
			if (c.playerRights == 0) {
				c.sendMessage("You cannot access this shop.");
			}
			break;

		case 520:
			c.getShops().openShop(18);
			if (c.playerRights == 0) {
				c.sendMessage("You cannot access this shop.");
			}
			break;

		case 590:
			c.getShops().openShop(20);
			break;
		case 587:
			c.getShops().openShop(21);
			break;

		case 944:
			c.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons",
					"Chaos Dwarf", "-- Next Page --");
			c.teleAction = 7;
			break;
		/*
		 * case 659: c.getShops().openShop(11); break;
		 */

		case 528:
			c.getShops().openShop(1);
			break;

		case 1597:
			if (c.slayerTask <= 0) {
				c.getDH().sendDialogues(11, npcType);
				// c.sendMessage("Slayer will be enabled in some minutes.");
			} else {
				c.sendMessage("You still have " + c.taskAmount + " "
						+ Server.npcHandler.getNpcListName(c.slayerTask) + "s "
						+ "left to kill.");
				c.sendMessage("Please see me after your task or do ::resettask.");
			}
			break;

		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker

		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // Banker
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getDH().sendDialogues(1013, c.npcType);
			break;
		case 1986:
			c.getDH().sendDialogues(2244, c.npcType);
			break;

		case 644:
			c.getShops().openShop(9);
			c.sendMessage("You currently have @red@" + c.donPoints
					+ " @bla@donator points.");
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 1785:
			c.getShops().openShop(8);
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.getDH().sendOption2("Teleport me to Runecrafting Abyss.",
					"I want to stay here, thanks.");
			c.dialogueAction = 2258;
			break;

		case 546:
			// c.getShops().openShop(5);
			c.getDH().sendOption4("Buy 1,000 barrage spells (Pay 17m)",
					"Buy 1,000 vengeance spells (Pay 15m)",
					"Buy 1,000 of all runes (Pay 10m)", "Open mage shop");
			c.dialogueAction = 1658;
			break;

		case 549:
			c.getShops().openShop(3);
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 198:
			c.getShops().openSkillCape();
			break;

		/**
		 * Make over mage.
		 */

		}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		
		if (Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 2, npcType);
			return;
		}

		switch (npcType) {
		case 3789:
			c.getShops().openShop(15);
			break;
		case 959:
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3]))
				c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.sendMessage("Your hitpoints has been Restored.");
			break;
		case 546:
			c.getShops().openShop(5);
			break;
		case 1597:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 953: // Banker
		case 2574: // Banker
		case 166: // Gnome Banker
		case 1702: // Ghost Banker
		case 494: // Banker
		case 495: // Banker
		case 496: // Banker
		case 497: // Banker
		case 498: // Banker
		case 499: // Banker
		case 567: // Banker
		case 1036: // Banker
		case 1360: // Banker
		case 2163: // Banker
		case 2164: // Banker
		case 2354: // er
		case 2355: // Banker
		case 2568: // Banker
		case 2569: // Banker
		case 2570: // Banker
			c.getPA().openUpBank();
			break;

		case 1785:
			c.getShops().openShop(8);
			break;

		case 550:
			c.getShops().openShop(4);
			break;

		case 3796:
			c.getShops().openShop(6);
			break;

		case 1860:
			c.getShops().openShop(6);
			break;

		case 519:
			c.getShops().openShop(7);
			break;

		case 548:
			c.getDH().sendDialogues(69, c.npcType);
			break;

		case 2258:
			c.sendMessage("This NPC do not have a shop so you cant trade him.");
			// c.getPA().startTeleport(3039, 4834, 0, "modern"); //first click
			// teleports second click open shops
			break;

		case 541:
			c.getShops().openShop(2);
			break;

		case 528:
			c.getShops().openShop(1);
			break;

		case 532:
			c.getShops().openShop(1);
			break;
		}
	}

	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch (npcType) {
		case 1599:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 1597:
			c.getShops().openShop(10);
			c.sendMessage("You currently have @red@" + c.slayerPoints
					+ " @bla@slayer points.");
			break;
		case 548:
			if (!c.eazyRizal()) {
				c.sendMessage("You must remove your equipment before changing your appearence.");
				c.canChangeAppearance = false;
			} else {
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			}
			break;

		case 836:
			c.getShops().openShop(103);
			break;

		}
	}

}