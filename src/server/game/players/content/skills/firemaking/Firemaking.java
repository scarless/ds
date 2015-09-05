package server.game.players.content.skills.firemaking;

import server.Server;
import server.clip.region.Region;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.objects.Object;
import server.game.players.Client;
import server.game.players.Player;
import server.game.players.content.skills.SkillHandler;
import server.game.players.content.skills.firemaking.LogData.logData;
import server.util.Misc;

public class Firemaking extends SkillHandler {

	public static void attemptFire(final Client c, final int itemUsed, final int usedWith, final int x, final int y, final boolean groundObject) {
		if (!c.getItems().playerHasItem(590)) {
			c.sendMessage("You need a tinderbox to light a fire.");
			return;
		}
		if (isSkilling[11] == true) {
			return;
		}
		for (final logData l : logData.values()) {
			final int logId = usedWith == 590 ? itemUsed : usedWith;
			if (logId == l.getLogId()) {
				if (c.playerLevel[11] < l.getLevel()) {
					c.sendMessage("You need a firemaking level of "+ l.getLevel() +" to light "+ c.getItems().getItemName(logId));
					return;
				}
				if (c.inBank()) {
					c.sendMessage("You cannot light a fire in a bank.");
					return;
				}
				if (c.isInHighRiskPK()){
					c.sendMessage("Dude, seriously?");
					return;
				}	
				if (Server.objectManager.objectExists(c.absX, c.absY)) {
					c.sendMessage("You cannot light a fire here.");
					return;
				}
				isSkilling[11] = true;
				long lastSkillingAction = 0;
				boolean notInstant = (System.currentTimeMillis() - lastSkillingAction) > 2500;
				int cycle = 2;
				if (notInstant) {
					c.sendMessage("You attempt to light a fire.");
					if (groundObject == false) {
						c.getItems().deleteItem(logId, c.getItems().getItemSlot(logId), 1);
						Server.itemHandler.createGroundItem(c, logId, c.absX, c.absY, 1, c.playerId);
					}
					cycle = 3 + Misc.random(6);
				} else {
					if (groundObject == false) {
						c.getItems().deleteItem(logId, c.getItems().getItemSlot(logId), 1);
					}
				}
				final boolean walk;
				if (Region.getClipping((x - 1), y, c.heightLevel, -1, 0)) {
					walk = true;
				} else {
					walk = false;
				}
				c.startAnimation(733);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (isSkilling[11] == true) {
							c.getPA().walkTo(walk == true ? -1 : 1, 0);
							c.postProcessing();
							Server.itemHandler.removeGroundItem(c, logId, x, c.absY, false);
							new Object(2732, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							c.sendMessage("The fire catches and the log beings to burn.");
							CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
								@Override
								public void execute(CycleEventContainer container) {
									c.turnPlayerTo(walk == true ? (x + 1) : (x - 1), y);
									container.stop();
								}

								@Override
								public void stop() {

								}				
							}, 2);						
							container.stop();
						} else {
							return;
						}
					}
					@Override
					public void stop() {
						c.startAnimation(65535);
						c.getPA().addSkillXP((int) (l.getXp()), 11);
						long lastSkillingAction = System.currentTimeMillis();
						isSkilling[11] = false;
					}
				}, cycle);				
			}
		}
	}
}