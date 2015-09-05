package server.game.players;

import java.sql.*;

public class Highscores {
	public static Connection con;
	public static Statement stm;
        public static boolean connected;
	
	public static String Host = "jdbc:mysql://pr-forum.net/prforumn_highscores";
	public static String User = "prforumn_hs";
	public static String Pass = "1122334455";
	
    public static void process() {
        try
        {
            Class.forName(Driver).newInstance();
	    Connection con = DriverManager.getConnection(Host, User, Pass);
	    stm = con.createStatement();
            connected = true;
        }
        catch(Exception e)
        {
            connected = false;
            e.printStackTrace();
        }
    }

    public static ResultSet query(String s)
        throws SQLException
    {
        if(s.toLowerCase().startsWith("select"))
        {
            ResultSet resultset = stm.executeQuery(s);
            return resultset;
        }
        try
        {
            stm.executeUpdate(s);
            return null;
        }
        catch(Exception e)
        {
            destroy();
        }
        process();
        return null;
    }

    public static void destroy() {
	try {
		if(stm != null)
			stm.close();
		if(con != null)
			con.close();
		connected = false;
System.out.println("Destroying Highscores Connection");
	} catch(Exception e) {
		e.printStackTrace();
	}
}

    public static boolean save(Client c) {
        try
        {
            query("DELETE FROM `skills` WHERE playerName = '"+c.playerName+"';");
	    query("DELETE FROM `skillsoverall` WHERE playerName = '"+c.playerName+"';");
	    query("INSERT INTO `skills` (`playerName`,`playerRights`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`) VALUES ('"+c.playerName+"',"+c.playerRights+","+c.playerLevel[0]+","+c.playerXP[0]+","+c.playerLevel[1]+","+c.playerXP[1]+","+c.playerLevel[2]+","+c.playerXP[2]+","+c.playerLevel[3]+","+c.playerXP[3]+","+c.playerLevel[4]+","+c.playerXP[4]+","+c.playerLevel[5]+","+c.playerXP[5]+","+c.playerLevel[6]+","+c.playerXP[6]+","+c.playerLevel[7]+","+c.playerXP[7]+","+c.playerLevel[8]+","+c.playerXP[8]+","+c.playerLevel[9]+","+c.playerXP[9]+","+c.playerLevel[10]+","+c.playerXP[10]+","+c.playerLevel[11]+","+c.playerXP[11]+","+c.playerLevel[12]+","+c.playerXP[12]+","+c.playerLevel[13]+","+c.playerXP[13]+","+c.playerLevel[14]+","+c.playerXP[14]+","+c.playerLevel[15]+","+c.playerXP[15]+","+c.playerLevel[16]+","+c.playerXP[16]+","+c.playerLevel[17]+","+c.playerXP[17]+","+c.playerLevel[18]+","+c.playerXP[18]+","+c.playerLevel[19]+","+c.playerXP[19]+","+c.playerLevel[20]+","+c.playerXP[20]+");");
	    query("INSERT INTO `skillsoverall` (`playerName`,`playerRights`,`lvl`,`xp`) VALUES ('"+c.playerName+"',"+c.playerRights+","+(c.getLevelForXP(c.playerXP[0]) + c.getLevelForXP(c.playerXP[1]) + c.getLevelForXP(c.playerXP[2]) + c.getLevelForXP(c.playerXP[3]) + c.getLevelForXP(c.playerXP[4]) + c.getLevelForXP(c.playerXP[5]) + c.getLevelForXP(c.playerXP[6]) + c.getLevelForXP(c.playerXP[7]) + c.getLevelForXP(c.playerXP[8]) + c.getLevelForXP(c.playerXP[9]) + c.getLevelForXP(c.playerXP[10]) + c.getLevelForXP(c.playerXP[11]) + c.getLevelForXP(c.playerXP[12]) + c.getLevelForXP(c.playerXP[13]) + c.getLevelForXP(c.playerXP[14]) + c.getLevelForXP(c.playerXP[15]) + c.getLevelForXP(c.playerXP[16]) + c.getLevelForXP(c.playerXP[17]) + c.getLevelForXP(c.playerXP[18]) + c.getLevelForXP(c.playerXP[19]) + c.getLevelForXP(c.playerXP[20]))+","+((c.playerXP[0]) + (c.playerXP[1]) + (c.playerXP[2]) + (c.playerXP[3]) + (c.playerXP[4]) + (c.playerXP[5]) + (c.playerXP[6]) + (c.playerXP[7]) + (c.playerXP[8]) + (c.playerXP[9]) + (c.playerXP[10]) + (c.playerXP[11]) + (c.playerXP[12]) + (c.playerXP[13]) + (c.playerXP[14]) + (c.playerXP[15]) + (c.playerXP[16]) + (c.playerXP[17]) + (c.playerXP[18]) + (c.playerXP[19]) + (c.playerXP[20]))+");");
		
		System.out.println("Saving Player to highscores!");
}
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	public static String Driver = "com.mysql.jdbc.Driver";
}