package server.world;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

import server.game.players.Client;
import server.game.players.PlayerHandler;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClanManager {
	public LinkedList<Clan> clans;

	public ClanManager() {
		clans = new LinkedList();
	}

	public int getActiveClans() {
		return this.clans.size();
	}

	public int getTotalClans() {
		File localFile = new File("/Data/clan/");
		return localFile.listFiles().length;
	}

	public void create(Client paramClient) {
		if (paramClient.clan != null) {
			paramClient
					.sendMessage("You must leave your current clan-chat before making your own.");
			return;
		}
		if (paramClient.inEazyCC) {
			return;
		}
		Clan localClan = new Clan(paramClient);
		this.clans.add(localClan);
		localClan.addMember(paramClient);
		localClan.save();
		paramClient.getPA().setClanData();
		paramClient
				.sendMessage("<col=FF0000>You may change your clan settings by clicking the 'Clan Setup' button.");
	}

	public Clan getClan(String paramString) {
		for (int i = 0; i < this.clans.size(); i++) {
			if (this.clans.get(i).getFounder().equalsIgnoreCase(paramString)) {
				return this.clans.get(i);
			}

		}

		Clan localClan = read(paramString);
		if (localClan != null) {
			this.clans.add(localClan);
			return localClan;
		}
		return null;
	}

	public void delete(Clan paramClan) {
		if (paramClan == null) {
			return;
		}
		File localFile = new File("Data/clan/" + paramClan.getFounder()
				+ ".cla");
		if (localFile.delete()) {
			Client localClient = (Client) PlayerHandler.getPlayer(paramClan
					.getFounder());
			if (localClient != null) {
				localClient.sendMessage("Your clan has been deleted.");
			}
			this.clans.remove(paramClan);
		}
	}

	public void save(Clan paramClan) {
		if (paramClan == null) {
			return;
		}
		File localFile = new File("Data/clan/" + paramClan.getFounder()
				+ ".cla");
		try {
			RandomAccessFile localRandomAccessFile = new RandomAccessFile(
					localFile, "rwd");

			localRandomAccessFile.writeUTF(paramClan.getTitle());
			localRandomAccessFile.writeByte(paramClan.whoCanJoin);
			localRandomAccessFile.writeByte(paramClan.whoCanTalk);
			localRandomAccessFile.writeByte(paramClan.whoCanKick);
			localRandomAccessFile.writeByte(paramClan.whoCanBan);
			if ((paramClan.rankedMembers != null)
					&& (paramClan.rankedMembers.size() > 0)) {
				localRandomAccessFile
						.writeShort(paramClan.rankedMembers.size());
				for (int i = 0; i < paramClan.rankedMembers.size(); i++) {
					localRandomAccessFile.writeUTF(paramClan.rankedMembers
							.get(i));
					localRandomAccessFile.writeShort(paramClan.ranks.get(i)
							.intValue());
				}
			} else {
				localRandomAccessFile.writeShort(0);
			}

			localRandomAccessFile.close();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	private Clan read(String paramString) {
		File localFile = new File("Data/clan/" + paramString + ".cla");
		if (!localFile.exists()) {
			return null;
		}
		try {
			RandomAccessFile localRandomAccessFile = new RandomAccessFile(
					localFile, "rwd");

			Clan localClan = new Clan(localRandomAccessFile.readUTF(),
					paramString);
			localClan.whoCanJoin = localRandomAccessFile.readByte();
			localClan.whoCanTalk = localRandomAccessFile.readByte();
			localClan.whoCanKick = localRandomAccessFile.readByte();
			localClan.whoCanBan = localRandomAccessFile.readByte();
			int i = localRandomAccessFile.readShort();
			if (i != 0) {
				for (int j = 0; j < i; j++) {
					localClan.rankedMembers
							.add(localRandomAccessFile.readUTF());
					localClan.ranks.add(Integer.valueOf(localRandomAccessFile
							.readShort()));
				}
			}
			localRandomAccessFile.close();

			return localClan;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
		return null;
	}

	public boolean clanExists(String paramString) {
		File localFile = new File("Data/clan/" + paramString + ".cla");
		return localFile.exists();
	}

	public LinkedList<Clan> getClans() {
		return this.clans;
	}
}