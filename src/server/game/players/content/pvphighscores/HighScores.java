package server.game.players.content.pvphighscores;



/**
 * @author Thee Legacy
 */
public class HighScores
{
        private String username;
        private int playerKills;
        private int playerDeaths;
        private int kdr;

        public HighScores()
        {
                this.username = "";
                this.playerKills = -1;
                this.playerDeaths = -1;
        }

        public HighScores(String username, int playerKills, int playerDeaths, int kdr)
        {
                this.username = username;
                this.playerKills = playerKills;
                this.playerDeaths = playerDeaths;
                this.kdr = kdr;
        }

        public String getUsername()
        {
                return username;
        }

        public int getPlayerKills()
        {
                return playerKills;
        }
        public int getKdr() {
        	return kdr;
        }
        public int getPlayerDeaths()
        {
                return playerDeaths;
        }

        public void setUsername(String value)
        {
                username = value;
        }

        public void setPlayerKills(int value)
        {
                playerKills = value;
        }

        public void setPlayerDeaths(int value)
        {
                playerDeaths = value;
        }

        public void resetValues()
        {
                this.username = "";
                this.playerKills = -1;
                this.playerDeaths = -1;
        }

	
		
}