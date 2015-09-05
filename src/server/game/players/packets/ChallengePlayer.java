package server.game.players.packets;

import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		switch (packetType) {
		case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if (PlayerHandler.players[answerPlayer] == null) {
				return;
			}
            if(PlayerHandler.players[answerPlayer] == null || answerPlayer == c.playerId)
                return;
			if (c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return;
			}

			c.getTradeAndDuel().requestDuel(answerPlayer);
			break;
		}
	}
}
