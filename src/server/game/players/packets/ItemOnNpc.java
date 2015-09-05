package server.game.players.packets;

import server.game.items.UseItem;
import server.game.npcs.NPCHandler;
import server.game.players.Client;
import server.game.players.PacketType;

public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = NPCHandler.npcs[i].npcType;
		if (!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}
        if(!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }
		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}
