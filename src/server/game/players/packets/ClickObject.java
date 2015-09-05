package server.game.players.packets;

import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.objects.Doors;
import server.game.players.Client;
import server.game.players.PacketType;
import server.game.players.PlayerHandler;
import server.util.Misc;

/**
 * Click Object
 */
public class ClickObject implements PacketType {

	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252,
			THIRD_CLICK = 70;

	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		c.clickObjectType = c.objectX = c.objectId = c.objectY = 0;
		c.objectYOffset = c.objectXOffset = 0;
		c.getPA().resetFollow();
		if (c.getAgility1().doingAgility)
		{
			return;
		}
		if (c.hasPin) {
            return;
            }
		if (c.chatreveal == 1){
	{
for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					Client c2 = (Client)PlayerHandler.players[j];
			if (c2.playerRights == 3){
			c2.sendMessage("*"+c.playerName+": clicked object: "+c.objectId+" (X:"+c.objectX+", Y: "+c.objectY+").");
			}
			}
		}
}
	}
		switch (packetType) {

		case FIRST_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndianA();
			c.objectId = c.getInStream().readUnsignedWord();
			c.objectY = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;
			if (c.goodDistance(c.getX(), c.getY(), c.objectX, c.objectY, 1)) {
				if (Doors.getSingleton().handleDoor(c.objectId, c.objectX,
						c.objectY, c.heightLevel)) {
				}
			}

			if (c.playerRights == 3 && c.playerName.equalsIgnoreCase("Sanity")) {
				Misc.println("1objectId: " + c.objectId + "  ObjectX: "
						+ c.objectX + "  objectY: " + c.objectY + " Xoff: "
						+ (c.getX() - c.objectX) + " Yoff: "
						+ (c.getY() - c.objectY));
			} else if (c.playerRights == 3) {
				c.sendMessage("objectId: " + c.objectId + " objectX: "
						+ c.objectX + " objectY: " + c.objectY);
			}
			if (Math.abs(c.getX() - c.objectX) > 25
					|| Math.abs(c.getY() - c.objectY) > 25) {
				c.resetWalkingQueue();
				break;
			}
			switch (c.objectId) {
			case 12536:
				c.objectXOffset = 2;
				c.objectYOffset = 1;
				break;

				case 12537:
				c.objectXOffset = 1;
				c.objectYOffset = 2;
				break;

				case 12538:
				c.objectYOffset = 1;
				break;
				case 4483:
			c.getPA().openUpBank();
				break;
				case 2623:
					c.sendMessage("This door is locked, use a muddy key to open it.");
						break;
				case 1738:
				if (c.objectX == 3204 && c.objectY == 3207) {
				c.objectXOffset = 1;
				c.objectYOffset = 2;
				}
				if (c.objectX == 3204 && c.objectY == 3229) {
				c.objectXOffset = 2;
				}
				break;

				case 1739:
				if (c.objectX == 3204 && c.objectY == 3207) {
				c.objectXOffset = 2;
				c.objectYOffset = 3;
				}
				if (c.objectX == 3204 && c.objectY == 3229) {
				c.objectXOffset = 2;
				}
				break;

				case 1740:
				if (c.objectX == 3205 && c.objectY == 3208) {
				c.objectXOffset = 1;
				c.objectYOffset = 2;
				}
				if (c.objectX == 3205 && c.objectY == 3229) {
				c.objectXOffset = 1;
				}
				break;
			case 9398:// deposit
				c.getPA().sendFrame126("The Bank of eazy - Deposit Box", 7421);
				c.getPA().sendFrame248(4465, 197);// 197 just because you can't
													// see it =\
				c.getItems().resetItems(7423);
				break;
		/*	case 1738:
			c.objectYOffset = 1;
		c.getPA().movePlayer(2840, 3539, 2);
		c.startAnimation(828);
		break;*/
			case 2282: // Ropeswing
				c.objectDistance = 4;
				break;
			case 1733:
				c.objectYOffset = 2;
				break;

			case 3044:
			case 21764:
			case 17010:
				c.objectDistance = 3;
				break;

			case 245:
				c.objectYOffset = -1;
				c.objectDistance = 0;
				break;

			case 272:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 273:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 246:
				c.objectYOffset = 1;
				c.objectDistance = 0;
				break;

			case 4493:
				c.objectXOffset = 4;
				c.objectYOffset = 1;
				break;
			case 4494:
				c.objectXOffset = -1;
				c.objectYOffset = 1;
				break;
			case 4496:
				c.objectXOffset = 2;
				break;
			case 4495:
				break;
			case 10229:
			case 6522:
				c.objectDistance = 2;
				break;
			case 8959:
				c.objectYOffset = 1;
				break;
			case 4417:
				if (c.objectX == 2425 && c.objectY == 3074)
					c.objectYOffset = 2;
				break;
			case 4420:
				if (c.getX() >= 2383 && c.getX() <= 2385) {
					c.objectYOffset = 1;
				} else {
					c.objectYOffset = -2;
				}
			case 6552:
			case 409:
				c.objectDistance = 2;
				break;
			case 410:
				c.objectXOffset = 2;
                c.objectYOffset = 2;
                break;
			case 2879:
			case 2878:
				c.objectDistance = 3;
				break;
			case 3785:
			case 3786:
				c.objectDistance = 1;
				break;
			case 3782:
			case 3783:
				c.objectDistance = 1;
				break;
			case 2558:
				c.objectDistance = 0;
				if (c.absX > c.objectX && c.objectX == 3044)
					c.objectXOffset = 1;
				if (c.absY > c.objectY)
					c.objectYOffset = 1;
				if (c.absX < c.objectX && c.objectX == 3038)
					c.objectXOffset = -1;
				break;
			case 9356:
				c.objectDistance = 2;
				break;
				
			case 5959:
			case 1815:
			case 5960:
			case 1816:
				c.objectDistance = 0;
				break;

			case 9293:
				c.objectDistance = 2;
				break;
			case 4418:
				if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2380 && c.objectY == 3127)
					c.objectYOffset = 2;
				else if (c.objectX == 2369 && c.objectY == 3126)
					c.objectXOffset = 2;
				else if (c.objectX == 2374 && c.objectY == 3131)
					c.objectYOffset = -2;
				break;
			case 9706:
				c.objectDistance = 0;
				c.objectXOffset = 1;
				break;
			case 9707:
				c.objectDistance = 0;
				c.objectYOffset = -1;
				break;
			case 4419:
			case 6707: // verac
				c.objectYOffset = 3;
				break;
			case 6823:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6706: // torag
				c.objectXOffset = 2;
				break;
			case 6772:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6705: // karils
				c.objectYOffset = -1;
				break;
			case 6822:
				c.objectDistance = 2;
				c.objectYOffset = 1;
				break;

			case 6704: // guthan stairs
				c.objectYOffset = -1;
				break;
			case 6773:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;

			case 6703: // dharok stairs
				c.objectXOffset = -1;
				break;
			case 6771:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;

			case 6702: // ahrim stairs
				c.objectXOffset = -1;
				break;
			case 6821:
				c.objectDistance = 2;
				c.objectXOffset = 1;
				c.objectYOffset = 1;
				break;
			case 1276:
			case 1278:// trees
			case 1281: // oak
			case 1308: // willow
			case 1307: // maple
			case 1309: // yew
			case 1306: // yew
				c.objectDistance = 3;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY
					+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().firstClickObject(c.objectId, c.objectX,
						c.objectY);
			} else {
				c.clickObjectType = 1;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.clickObjectType == 1
								&& c.goodDistance(c.objectX + c.objectXOffset,
										c.objectY + c.objectYOffset, c.getX(),
										c.getY(), c.objectDistance)) {
							c.getActions().firstClickObject(c.objectId,
									c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType > 1 || c.clickObjectType == 0)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;

		case SECOND_CLICK:
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();
			c.objectY = c.getInStream().readSignedWordBigEndian();
			c.objectX = c.getInStream().readUnsignedWordA();
			c.objectDistance = 1;

			if (c.playerRights == 3) {
				Misc.println("objectId: " + c.objectId + "  ObjectX: "
						+ c.objectX + "  objectY: " + c.objectY + " Xoff: "
						+ (c.getX() - c.objectX) + " Yoff: "
						+ (c.getY() - c.objectY));
			}

			switch (c.objectId) {
			case 2561:
			case 2562:
			case 2563:
			case 2564:
			case 2565:
			case 2478:
			case 2483:
			case 2484:
				c.objectDistance = 3;
				break;
			case 6163:
			case 6165:
			case 6166:
			case 6164:
			case 6162:
				c.objectDistance = 2;
				break;
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;

			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY
					+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().secondClickObject(c.objectId, c.objectX,
						c.objectY);
			} else {
				c.clickObjectType = 2;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.clickObjectType == 2
								&& c.goodDistance(c.objectX + c.objectXOffset,
										c.objectY + c.objectYOffset, c.getX(),
										c.getY(), c.objectDistance)) {
							c.getActions().secondClickObject(c.objectId,
									c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 2 || c.clickObjectType > 2)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;

		case THIRD_CLICK:
			c.objectX = c.getInStream().readSignedWordBigEndian();
			c.objectY = c.getInStream().readUnsignedWord();
			c.objectId = c.getInStream().readUnsignedWordBigEndianA();

			if (c.playerRights == 3) {
				Misc.println("objectId: " + c.objectId + "  ObjectX: "
						+ c.objectX + "  objectY: " + c.objectY + " Xoff: "
						+ (c.getX() - c.objectX) + " Yoff: "
						+ (c.getY() - c.objectY));
			}

			switch (c.objectId) {
			default:
				c.objectDistance = 1;
				c.objectXOffset = 0;
				c.objectYOffset = 0;
				break;
			}
			if (c.goodDistance(c.objectX + c.objectXOffset, c.objectY
					+ c.objectYOffset, c.getX(), c.getY(), c.objectDistance)) {
				c.getActions().secondClickObject(c.objectId, c.objectX,
						c.objectY);
			} else {
				c.clickObjectType = 3;
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (c.clickObjectType == 3
								&& c.goodDistance(c.objectX + c.objectXOffset,
										c.objectY + c.objectYOffset, c.getX(),
										c.getY(), c.objectDistance)) {
							c.getActions().thirdClickObject(c.objectId,
									c.objectX, c.objectY);
							container.stop();
						}
						if (c.clickObjectType < 3)
							container.stop();
					}

					@Override
					public void stop() {
						c.clickObjectType = 0;
					}
				}, 1);
			}
			break;
		}

	}

	public void handleSpecialCase(Client c, int id, int x, int y) {

	}

}
