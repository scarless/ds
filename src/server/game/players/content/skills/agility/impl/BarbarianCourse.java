package server.game.players.content.skills.agility.impl;

import server.Config;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.players.Client;


/**
 * Handles the Barbarian agility course
 * @author MGT Madness
 * @see modified: 26-10-2013
 */
public class BarbarianCourse 
{
	
	/**
	 * Handles Barbarian agility course objects.
	 * @param c The player
	 * @param objectType The object used by the player
	 */
	public static void startBarbarianCourse(final Client c, final int objectType)
    {
		
        switch (objectType)
        {
        
        // Obstacle pipe
        case 2287:
        	if (c.getPA().getLevelForXP(c.playerXP[16]) >= 70)
        	{
        		
        	if (c.absY == 3561 && c.absX == 2552)
            {
            if (System.currentTimeMillis() - c.agility7 < 3000)
            {
                return;
            }
            c.agility7 = System.currentTimeMillis();
            c.getAgility1().doingAgility = true;
            c.getAgility1().agilityWalk(c, 844, 0, -3);
            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absY == 3558)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.turnPlayerTo(c.absX, c.absY - 1);
                    c.getAgility1().resetAgilityWalk(c);
                    c.getAgility1().doingAgility = false;
                }
            }, 1);
            }
        	
        	}
        	else
        	{
        		c.sendMessage("You need 70 agility to use this course.");
        	}
        	break;
        
        // Rope swing
        case 2282:
        	
        	if (c.absX == 2551 && c.absY == 3554)
        	{
        	if (System.currentTimeMillis() - c.agility1 < 3000)
        	{
        		return;
        	}
        	c.agility1 = System.currentTimeMillis();
        	c.ropeSwing = true;
        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityRun(c, 751, 0, - 5);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absY == 3549)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.turnPlayerTo(c.absX, c.absY - 1);
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                    c.getPA().addSkillXP((int) 22 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                }
            }, 1);
        	}
        	
        	break;
        	
        	// Log balance
        case 2294:
        	
        	if (c.absX == 2551 && c.absY == 3546)
        	{
        	if (System.currentTimeMillis() - c.agility2 < 3000)
        	{
        		return;
        	}
        	c.agility2 = System.currentTimeMillis();
        	c.logBalance = true;
        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityWalk(c, 762, -10, 0);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absX == 2541)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.turnPlayerTo(c.absX - 1, c.absY);
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                    c.getPA().addSkillXP((int) 13.7 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                }
            }, 1);
        	}
        	
        	break;
        	
        	// Obstacle net
        case 2284:
        	
        	if (c.absX != 2539)
            {
                return;
            }
            if (System.currentTimeMillis() - c.agility1 < 3000)
            {
                return;
            }
            c.agility1 = System.currentTimeMillis();
            
            c.turnPlayerTo(c.objectX, c.objectY);
            c.startAnimation(828);
            c.obstacleNet = true;

            CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    container.stop();
                }@
                Override
                public void stop()
                {
                    c.startAnimation(65535);
                    c.getPA().movePlayer(c.absX - 2, c.absY, 1);
                    c.getPA().addSkillXP((int) 8.2 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                }
            }, 1);
        	
        	break;
        	
        	// Balancing ledge
        case 2302:
        	
        	if (c.absX == 2536 && c.absY == 3547)
        	{
        	if (System.currentTimeMillis() - c.agility2 < 3000)
        	{
        		return;
        	}
        	c.agility2 = System.currentTimeMillis();
        	
        	c.balancingLedge = true;
        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityWalk(c, 756, -4, 0);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absX == 2532)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.turnPlayerTo(c.absX, c.absY - 1);
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                    c.getPA().addSkillXP((int) 22 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                }
            }, 1);
        	}
        	
        	break;
        	
        	// Ladder
        case 3205:
        	
        	if (c.absX == 2532 && c.absY == 3546)
        	{
        	if (System.currentTimeMillis() - c.agility1 < 3000)
        	{
        		return;
        	}
        	c.agility1 = System.currentTimeMillis();
        	
        	c.Ladder = true;
        	c.startAnimation(827);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                        container.stop();
                }@
                Override
                public void stop()
                {
                	c.getPA().movePlayer(c.absX,c.absY, 0);
                    c.getAgility1().resetAgilityWalk(c);
                }
            }, 2);
        	}
        	
        	break;
        	
        	// Crumbling wall
        case 1948:

        	if (c.absX == 2535 && c.absY == 3553)
        	{
        	if (System.currentTimeMillis() - c.agility2 < 3000)
        	{
        		return;
        	}
        	c.agility2 = System.currentTimeMillis();

        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityWalk(c, 839, 2, 0);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absX == 2537)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                    c.getPA().addSkillXP((int) 13.7 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                }
            }, 1);
        	}
        	else if (c.absX == 2538 && c.absY == 3553)
        	{
        	if (System.currentTimeMillis() - c.agility1 < 3000)
        	{
        		return;
        	}
        	c.agility1 = System.currentTimeMillis();
        	
        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityWalk(c, 839, 2, 0);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absX == 2540)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                }
            }, 1);
        	}
        	
        	else if (c.absX == 2541 && c.absY == 3553)
        	{
        	if (System.currentTimeMillis() - c.agility2 < 3000)
        	{
        		return;
        	}
        	c.agility2 = System.currentTimeMillis();
        	
        	c.getAgility1().doingAgility = true;
        	c.getAgility1().agilityWalk(c, 839, 2, 0);
        	
        	CycleEventHandler.getSingleton().addEvent(c, new CycleEvent()
            {@
                Override
                public void execute(CycleEventContainer container)
                {
                    if (c.absX == 2543)
                    {
                        container.stop();
                    }
                }@
                Override
                public void stop()
                {
                	c.getAgility1().doingAgility = false;
                    c.getAgility1().resetAgilityWalk(c);
                    if (c.ropeSwing && c.logBalance && c.obstacleNet && c.balancingLedge && c.Ladder)
                    {
                    	c.ropeSwing = false;
                    	c.logBalance = false;
                    	c.obstacleNet = false;
                    	c.balancingLedge = false;
                    	c.Ladder = false;
                    	c.getPA().addSkillXP((int) 59.9 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                    }
                    else
                    {
                    	c.getPA().addSkillXP((int) 13.7 * Config.AGILITY_EXPERIENCE, c.playerAgility);
                    }
                }
            }, 1);
        	}
        	
        	break;
        	
        }

    }
	
}
