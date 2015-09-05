package server.game.players.packets;

import server.Config;
import server.game.players.Client;
import server.game.players.PacketType;
/**
 * Bank X Items
 **/
public class BankX2 implements PacketType {
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
			int Xamount = c.getInStream().readDWord();
		if (Xamount <= 0) {
			c.sendMessage("Amount must be greater than 0!");
			return;
		}

			
			
			if(c.attackSkill) {
				if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 0;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
c.getPA().requestUpdates();
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
				c.attackSkill = false;
				c.sendMessage("@red@Attack level set to " +level+ ".");
				} catch (Exception e){}
		}
		
		if(c.defenceSkill) {
						if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 1;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.defenceSkill = false;
				c.sendMessage("@red@Defence level set to " +level+ ".");
				} catch (Exception e){}
		}
				if(c.strengthSkill) {
						if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 2;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.strengthSkill = false;
				c.sendMessage("@red@Strength level set to " +level+ ".");
				} catch (Exception e){}
		}
				if(c.healthSkill) {
						if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 3;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 50)
					level = 50;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.healthSkill = false;
				c.sendMessage("@red@Hitpoints level set to " +level+ ".");
				} catch (Exception e){}
		}
				if(c.rangeSkill) {
						if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 4;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.rangeSkill = false;
				c.sendMessage("@red@Range level set to" +level+ ".");
				} catch (Exception e){}
		}
				if(c.prayerSkill) {
					if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				if(c.prayerActive[25])
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 5;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.prayerSkill = false;
				c.sendMessage("@red@Prayer level set to " +level+ ".");
				} catch (Exception e){}
		}
				if(c.mageSkill) {
						if (c.inWild() || c.isInHighRiskPK()) 
					return;
			if (c.inDuelArena()) 
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("@red@Please remove all your equipment before using this command.");
						return;
					}
				}
				try {	
				int skill = 6;
				int level = Xamount;
				if (level > 99)
					level = 99;
				else if (level < 0)
					level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
c.getPA().requestUpdates();
				c.getPA().refreshSkill(skill);
				c.mageSkill = false;
				c.sendMessage("@red@Magic level set to " +level+ ".");
				} catch (Exception e){}
				
				
}
				if(c.exchange == 1) {
								if(Xamount > 10000)
					Xamount = 10000;
					try{
					if(Xamount <= c.pkPoints){
					c.pkPoints = c.pkPoints - Xamount;
					c.getItems().addItem(995,Xamount*250000);
					c.sendMessage("Successfully exchanged "+Xamount+" credits for "+Xamount*250000+" coins.");
					c.exchange = 0;
					} else {
					c.sendMessage("Successfully exchanged "+c.pkPoints+" credits for "+c.pkPoints*2500+" coins.");
					c.getItems().addItem(995,c.pkPoints*2500);
					c.pkPoints = 0;
					c.exchange = 0;
					}
					} catch (Exception e){}
				}
				
				if(c.exchange == 2) {//c.getItems().deleteItem(995,c.getItems().getItemSlot(995),1);
				if(Xamount > 10000)
					Xamount = 10000;
					try{
					if(c.getItems().playerHasItem(995,(Xamount*10000))){
					c.getItems().deleteItem(995,c.getItems().getItemSlot(995),(Xamount*10000));
					c.pkPoints = c.pkPoints + Xamount;
					c.sendMessage("Successfully exchanged "+Xamount*10000+" coins for "+Xamount+" credits.");
					c.exchange = 0;
					} else {
					c.sendMessage("Please enter the amount of times you want to exchange!");
					c.exchange = 0;
					c.getItems().deleteItem(995,c.getItems().getItemSlot(995),10000);
					}
					} catch (Exception e){}
				}
		switch(c.xInterfaceId) {
			case 5064:
			c.getItems().bankItem(c.playerItems[c.xRemoveSlot] , c.xRemoveSlot, Xamount);
			break;
				
			case 5382:
			c.getItems().fromBank(c.bankItems[c.xRemoveSlot] , c.xRemoveSlot, Xamount);
			break;
				
			case 3322:
			if(c.duelStatus <= 0) {
            	c.getTradeAndDuel().tradeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
            } else {				
				c.getTradeAndDuel().stakeItem(c.xRemoveId, c.xRemoveSlot, Xamount);
			}  
			break;
				
			case 3415: 
			if(c.duelStatus <= 0) { 
            	c.getTradeAndDuel().fromTrade(c.xRemoveId, c.xRemoveSlot, Xamount);
			} 
			break;
				
			case 6669:
			c.getTradeAndDuel().fromDuel(c.xRemoveId, c.xRemoveSlot, Xamount);
			break;			
		}
	}
}