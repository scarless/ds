package server.game.players.packets;

import server.Config;
import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;
import server.world.Clan;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();
		if (!c.getItems().playerHasItem(c.wearId, 1)) {
			return;
		}
		if (c.wearId == 5509) {
			c.getPA().removeSmallPouch();
			return;
		}
		if (c.wearId == 5510) {
			c.getPA().removeMediumPouch();
			return;
		}
		if (c.wearId == 5511) {
			c.getPA().removeMediumPouch();
			return;
		}
		if (c.wearId == 5512) {
			c.getPA().removeLargePouch();
			return;
		}
		if (c.wearId == 5513) {
			c.getPA().removeLargePouch();
			return;
		}
		if (c.wearId == 5514) {
			c.getPA().removeGiantPouch();
			return;
		}
		if (c.wearId == 5515) {
			c.getPA().removeGiantPouch();
			return;
		}
        if(!c.getItems().playerHasItem(c.wearId, 1, c.wearSlot)) {
            return;
        }
		int oldCombatTimer = c.attackTimer;
		if ((c.playerIndex > 0 || c.npcIndex > 0) && c.wearId != 4153)
			c.getCombat().resetPlayerAttack();
		if (c.canChangeAppearance) {
			c.sendMessage("You can't wear an item while changing appearence.");
			return;
		}
		
		if (c.wearId >= 5509 && c.wearId <= 5515) {
			int pouch = -1;
			int a = c.wearId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().emptyPouch(pouch);
			return;
		}
		// c.attackTimer = oldCombatTimer;
		c.getItems().wearItem(c.wearId, c.wearSlot);
		boolean godArmour = false;
		for (int i = 20135; i < 20167; i++) {
			if (c.playerEquipment[c.playerHat] == i || c.playerEquipment[c.playerChest] == i ||
					c.playerEquipment[c.playerLegs] == i)
				godArmour = true;
		}
		c.getItems().wearItem(c.wearId, c.wearSlot);
		if (godArmour && c.playerLevel[3] > c.calculateMaxLifePoints()) {
			c.playerLevel[3] = c.calculateMaxLifePoints();
			c.getPA().refreshSkill(3);
		}
	}
}
