package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;

public class IdleLogout implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		// if (!c.playerName.equalsIgnoreCase("Sanity"))
		// c.logout();
        if(c.underAttackBy > 0 || c.underAttackBy2 > 0)
            return;
	}
}