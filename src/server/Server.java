package server;

import java.io.IOException;
//import org.Vote.*;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import server.clip.region.ObjectDef;
import server.clip.region.Region;
import server.event.CycleEventHandler;
import server.event.Task;
import server.event.TaskScheduler;
import server.game.minigames.FightCaves;
import server.game.minigames.FightPits;
import server.game.minigames.PestControl;
import server.game.npcs.NPCHandler;
import server.game.players.Highscores;
import server.game.players.PlayerHandler;
import server.game.players.content.ChallengePoints;
import server.game.players.content.MageKills;
import server.game.players.content.MeleeKills;
import server.game.players.content.RangeKills;
import server.net.PipelineFactory;
import server.util.HostBlacklist;
import server.util.SimpleTimer;
import server.util.log.Logger;
import server.world.ClanManager;
import server.world.ItemHandler;
import server.world.ObjectHandler;
import server.world.ObjectManager;
import server.world.PlayerManager;
import server.world.ShopHandler;
import server.world.StillGraphicsManager;
import server.world.WalkingCheck;

/**
 * The main class needed to start the server.
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30 Revised by Shawn Notes by Shawn
 */
public class Server {

	public static ClanManager clanManager = new ClanManager();
	public static PlayerManager playerManager = null;
	private static StillGraphicsManager stillGraphicsManager = null;
	public static boolean sleeping;
	public static final int cycleRate;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static long sleepTime;
	public static boolean shutdownServer = false;
	public static boolean shutdownClientHandler;
	public static final boolean SOUND = true;
	public static boolean canLoadObjects = false;
	public static int serverlistenerPort;
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static FightPits fightPits = new FightPits();
	public static PestControl pestControl = new PestControl();
	public static FightCaves fightCaves = new FightCaves();
	private static final TaskScheduler scheduler = new TaskScheduler();
	private static int loop = 1;
	public static boolean doubleEXPWeekend = true;
	//private static SimpleTimer engineTimer, debugTimer;
	private static long loopDuration;
	//private static DecimalFormat debugPercentFormat;
	public static long[] TIMES = new long[5];

	public static TaskScheduler getTaskScheduler() {
		return scheduler;
	}

	static {
		serverlistenerPort = 43594;
		cycleRate = 600;
		shutdownServer = false;
		//engineTimer = new SimpleTimer();
		//debugTimer = new SimpleTimer();
		sleepTime = 0;
		//debugPercentFormat = new DecimalFormat("0.0#%");
	}

	/**
	 * Starts the server.
	 */
	public static void main(java.lang.String args[])
			throws NullPointerException, IOException {

		System.currentTimeMillis();
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));
		ObjectDef.loadConfig();
		HostBlacklist.loadBlacklist();
		Region.load();
		//MysqlConnector.init();
		//HighscoreConnector.init();
		WalkingCheck.load();
		bind();
		//Highscores.process();
		//if (Highscores.connected) {
		//System.out.println("Connected to MySQL Database!");
		//} else {
		//System.out.println("Failed to connect to MySQL Database!");
		//}
		playerManager = PlayerManager.getSingleton();
		playerManager.setupRegionPlayers();
		stillGraphicsManager = new StillGraphicsManager();
		new ChallengePoints();
		new MageKills();
		new MeleeKills();
		new RangeKills();
		Connection.initialize();

		/**
		 * Main Server Tick
		 */
		scheduler.schedule(new Task() {
			@Override
			protected void execute() {
				loop++;
				if (loop == 250) {
					loopDuration = System.currentTimeMillis();
					loop = 0;
				}
				CycleEventHandler.getSingleton().process();
				itemHandler.process();
				playerHandler.process();
				npcHandler.process();
				shopHandler.process();
				objectManager.process();
				
				fightPits.process();
				pestControl.process();
				if (loop == 0) {
					System.out.println("-------------");
					System.out.println("Loop took: "
							+ (loopDuration = System.currentTimeMillis()
									- loopDuration) + " ms.");
					System.out.println("Players online: "
							+ PlayerHandler.getPlayerCount() + ".");
					System.out.println("-------------");
				}
			}
		});
	}

	/**
	 * Logging execution.
	 */
	public static boolean playerExecuted = false;

	/**
	 * Gets the sleep mode timer and puts the server into sleep mode.
	 */
	public static long getSleepTimer() {
		return sleepTime;
	}

	/**
	 * Gets the Graphics manager.
	 */
	public static StillGraphicsManager getStillGraphicsManager() {
		return stillGraphicsManager;
	}

	/**
	 * Gets the Player manager.
	 */
	public static PlayerManager getPlayerManager() {
		return playerManager;
	}

	/**
	 * Gets the Object manager.
	 */
	public static ObjectManager getObjectManager() {
		return objectManager;
	}

	/**
	 * Java connection. Ports.
	 */
	private static void bind() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new PipelineFactory(
				new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(serverlistenerPort));
	}

}
