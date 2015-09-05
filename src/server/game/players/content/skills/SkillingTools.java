package server.game.players.content.skills;

import server.Config;
import server.game.players.Client;
import server.game.players.Player;
import server.game.players.content.skills.firemaking.LogData.logData;
import server.util.Misc;

public class SkillingTools extends SkillHandler {

	public final static int infernoAdze = 12024;

	public final boolean getAdzeRequirements(Player p) {
		if (!(p.playerLevel[p.playerWoodcutting] >= 61 && p.playerLevel[p.playerFiremaking] >= 92 && p.playerLevel[p.playerMining] >= 41)) {
			p.sendMessage("You need at least 92 Firemaking, 61 woodcutting and 41 mining to use this axe.");
			return false;
		}	
		return true;
	}

	public void cutAndFire(Client p, int logID)  {
		int burnChance = Misc.random(2);
		if(burnChance == 2) {
			p.gfx(1776,150);

			for (logData l : logData.values()) {
				if (logID == l.getLogId()) {
					p.getPA().addSkillXP(Config.FIREMAKING_EXPERIENCE, p.playerFiremaking);
					p.sendMessage("Your inferno adze burns the logs.");
					break;
				}
			}
		} else 
			p.getItems().addItem(logID, 1);
	}
}