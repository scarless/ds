package server.game.players.content.skills.agility.impl;

import server.Config;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.players.Client;


/**
 * Handles the Gnome agility course
 * 
 * @author L__A
 * @author MGT Madness
 */

public class GnomeCourse {

	public static void startGnomeCourse(final Client c, final int objectType) {

		switch (objectType) {

		// Log balance
		case 2295:
			if (c.absX != 2474) {
				return;
			}
			if (System.currentTimeMillis() - c.agility1 < 3000) {
				return;
			}
			c.agility1 = System.currentTimeMillis();
			c.getAgility1().doingAgility = true;
			c.getAgility1().agilityWalk(c, 762, 0, -7);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.absY == 3429) {
						c.getAgility1().doingAgility = false;
						container.stop();
					}
				}

				@Override
				public void stop() {
					c.getAgility1().resetAgilityWalk(c);
					c.turnPlayerTo(c.absX, c.absY - 1);
					c.getPA().addSkillXP((int) 17.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.logBalance1 = true;
					c.getAgility1().doingAgility = false;
				}
			}, 1);
			break;

		// Obstacle net
		case 2285:
			if (c.absY != 3426) {
				return;
			}
			if (System.currentTimeMillis() - c.agility2 < 3000) {
				return;
			}
			c.agility2 = System.currentTimeMillis();

			c.startAnimation(828);

			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					container.stop();
				}

				@Override
				public void stop() {
					c.startAnimation(65535);
					c.getPA().movePlayer(c.absX, 3424, 1);
					c.getPA().addSkillXP((int) 17.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.obstacleNetUp = true;
				}
			}, 1);

			break;

		// Tree branch
		case 2313:
			if (c.absY != 3423 && c.absY != 3422) {
				return;
			}
			if (System.currentTimeMillis() - c.agility3 < 3000) {
				return;
			}
			c.agility3 = System.currentTimeMillis();
			c.startAnimation(828);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					container.stop();
				}

				@Override
				public void stop() {
					c.startAnimation(65535);
					c.getPA().movePlayer(2473, 3420, 2);
					c.getPA().addSkillXP((int) 25 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.treeBranchUp = true;
				}
			}, 1);

			break;

		// Balancing rope
		case 2312:
			if (c.absY != 3420) {
				return;
			}
			if (System.currentTimeMillis() - c.agility4 < 3000) {
				return;
			}
			c.agility4 = System.currentTimeMillis();
			c.getAgility1().doingAgility = true;
			while (c.absX != 2477 && c.absY != 3420) {
				c.getPA().playerWalk(2477 - c.absX, 3420 - c.absY);
			}
			c.getAgility1().agilityWalk(c, 762, 6, 0);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.absX == 2483 && c.absY == 3420) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					c.getAgility1().resetAgilityWalk(c);
					c.turnPlayerTo(c.absX + 1, c.absY);
					c.getPA().addSkillXP((int) 17.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.balanceRope = true;
					c.getAgility1().doingAgility = false;
				}
			}, 1);
			break;

		// Tree branch
		case 2314:
		case 2315:
			if (System.currentTimeMillis() - c.agility5 < 3000) {
				return;
			}
			c.agility5 = System.currentTimeMillis();
			c.startAnimation(828);

			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					container.stop();
				}

				@Override
				public void stop() {
					c.startAnimation(65535);
					c.getPA().movePlayer(2486, 3419, 0);
					c.getPA().addSkillXP((int) 25 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.treeBranchDown = true;
				}
			}, 1);

			break;

		// Obstacle net
		case 2286:
			if (c.absY != 3425) {
				return;
			}
			if (System.currentTimeMillis() - c.agility6 < 3000) {
				return;
			}
			c.agility6 = System.currentTimeMillis();
			c.getAgility1().doingAgility = true;
			c.startAnimation(828);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					c.getPA().movePlayer(c.absX, 3427, 0);
					container.stop();
				}

				@Override
				public void stop() {
					c.turnPlayerTo(c.absX, 3426);
					c.getPA().addSkillXP((int) 17.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					c.obstacleNetOver = true;
					c.getAgility1().doingAgility = false;
				}
			}, 1);
			break;

		// Obstacle pipe
		case 154:
		case 4058:
			if (c.absY != 3430) {
				return;
			}
			if (System.currentTimeMillis() - c.agility7 < 3000) {
				return;
			}
			c.agility7 = System.currentTimeMillis();
			c.getAgility1().doingAgility = true;
			while (c.absX != 2484 && c.absY != c.objectY - 1) {
				c.getPA().playerWalk(2484 - c.absX, (c.objectY - 1) - c.absY);
			}
			c.getAgility1().agilityWalk(c, 844, 0, 7);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.absY == 3437) {
						container.stop();
					}
				}

				@Override
				public void stop() {
					c.getAgility1().resetAgilityWalk(c);
					c.turnPlayerTo(c.absX, c.absY + 1);
					if (c.logBalance1 && c.obstacleNetUp && c.treeBranchUp && c.balanceRope && c.treeBranchDown
							&& c.obstacleNetOver) {
						c.getPA().addSkillXP((int) 46.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					} else {
						c.getPA().addSkillXP((int) 17.5 * Config.AGILITY_EXPERIENCE, c.playerAgility);
					}
					c.logBalance1 = false;
					c.obstacleNetUp = false;
					c.treeBranchUp = false;
					c.balanceRope = false;
					c.treeBranchDown = false;
					c.obstacleNetOver = false;
					c.getAgility1().doingAgility = false;
				}
			}, 1);
			break;
		}
	}
}