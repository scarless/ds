package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.npcs.pet.DropPet;
import server.game.npcs.pet.PetData;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.game.players.content.skills.SkillHandler;
import server.game.players.saving.PlayerSave;

/**
 * Drop Item
 **/

public class DropItem implements PacketType {

	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();

		if(c.tradeTimer > 0) {
			c.sendMessage("The item disappears, once you are out the trade timer they will show on the floor.");
			c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			return;
		}
if (c.inTrade) {
			c.sendMessage("<shad=15695415><img=2>You can't drop items while trading!");
			return;
		}
		if (PlayerHandler.players[c.playerId].underAttackBy != 0) {
			if ((c.getShops().getItemShopValue(itemId)*.75) > 10000) {
			c.sendMessage("You can't drop items worth over 10k in combat.");
			return;
			}
		}
		if(c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}

		if(!c.getItems().playerHasItem(itemId,1,slot)) {
			return;
		}
	//	if(c.tradeTimer > 0) {
	//		c.sendMessage("You must wait until your starter timer is up to drop items.");
	//		return;
	//	}
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		
		 for (int i = 0; i < PetData.petData.length; i++)
        {
                if (PetData.petData[i][1] == itemId)
                {
                	if (c.getPetSummoned())
                    {
                            droppable = false;
                            break;
                    }
                }
        }
		 
		 DropPet.getInstance().dropPetRequirements(c, itemId, slot);
		if (c.playerItemsN[slot] != 0 && itemId != -1
				&& c.playerItems[slot] == itemId + 1) {
			if (droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				c.sendMessage("Your item dissapears when it touches the ground."); // drop
																					// dissapearing
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
			} else {
				c.sendMessage("You can't drop this item.");
			}
		}

	}
}
