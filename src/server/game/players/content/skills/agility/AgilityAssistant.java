package server.game.players.content.skills.agility;
 
import server.game.players.Client;
 
public class AgilityAssistant {
        /**
         * if true, the player cannot perform any action and is performing agility.
         */
        public boolean doingAgility;
 
        private int[] gnomeCourseObjects = { 2295, 2285, 2286, 2313, 2312, 2314, 2315, 154, 4058 };
 
        /**
         * @return is the object is related to Gnome agility course?
         */
        public boolean checkGnomeCourseObjects(final Client c, final int objectType) {
                for (int i = 0; i < gnomeCourseObjects.length; i++) {
                        if (objectType == gnomeCourseObjects[i]) {
                                return true;
                        }
                }
                return false;
        }
 
        private int[] barbarianCourseObjects = { 2282, 2294, 2284, 2302, 3205, 1948, 2287 };
 
        /**
         * @return is the object is related to Barbarian agility course?
         */
        public boolean isBarbarianCourseObject(final Client c, final int objectType) {
                for (int i = 0; i < barbarianCourseObjects.length; i++) {
                        if (objectType == barbarianCourseObjects[i]) {
                                return true;
                        }
                }
                return false;
        }
 
        public void agilityWalk(final Client c, final int walkAnimation, final int x, final int y) {
                c.isRunning2 = false;
                c.getPA().sendFrame36(173, 0);
                c.playerWalkIndex = walkAnimation;
                c.getPA().requestUpdates();
                c.getPA().walkTo(x, y);
                c.postProcessing();
        }
 
        public void agilityRun(final Client c, final int walkAnimation, final int x, final int y) {
                if (!c.isRunning2) {
                        c.isRunning2 = true;
                        c.getPA().sendFrame36(173, 0);
                }
                c.playerRunIndex = walkAnimation;
                c.getPA().requestUpdates();
                c.getPA().walkTo(x, y);
        }
 
        public void resetAgilityWalk(final Client c) {
                c.isRunning2 = true;
                c.getPA().sendFrame36(173, 1);
                c.playerWalkIndex = 0x333;
                c.playerRunIndex = 0x338;
                c.getPA().requestUpdates();
        }
 
}