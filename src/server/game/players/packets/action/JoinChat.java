package server.game.players.packets.action;

import server.Server;
import server.game.players.Client;
import server.game.players.PacketType;
import server.util.Misc;
import server.world.Clan;

public class JoinChat implements PacketType {

	@Override
	public void processPacket(Client player, int packetType, int packetSize) {
		String owner = Misc.longToPlayerName2(player.getInStream().readQWord())
				.replaceAll("_", " ");
		if (owner != null && owner.length() > 0) {
			if (player.clan == null) {
				Clan clan = Server.clanManager.getClan(owner);
				if (clan != null) {
					clan.addMember(player);
				} else if (owner.equalsIgnoreCase(player.playerName)) {
					Server.clanManager.create(player);
				} else {
					player.sendMessage(Misc.formatPlayerName(owner)
							+ " has not created a clan yet.");
				}
				player.getPA().refreshSkill(21);
				player.getPA().refreshSkill(22);
				player.getPA().refreshSkill(23);
			}
		}
	}

}