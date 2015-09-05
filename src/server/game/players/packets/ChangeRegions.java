package server.game.players.packets;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		// Server.objectHandler.updateObjects(c);
		c.getPA().removeObjects();
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		c.getPA().castleWarsObjects();

		c.saveFile = true;

		if (c.skullTimer > 0) {
			c.isSkulled = true;
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

	}

}
