package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		
		if (c.nextChat > 0) {
			c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
		} else {
			c.getDH().sendDialogues(0, -1);
		}
		if (c.nextItem > 0) {
			c.getDH().sendItems(c.nextItem, c.itemShown);
		} else {
			c.getDH().sendItems(0, -1);
		}
	}
}
