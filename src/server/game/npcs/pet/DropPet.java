package server.game.npcs.pet;

import server.game.players.Client;


/**
 * Drop the pet.
 * @author MGT Madness, created on 11-12-2013.
 */
public class DropPet 
{
	
	/**
     * The class instance.
     */
    private static final DropPet instance = new DropPet();

    /**
     * Returns a visible encapsulation of the class instance.
     *
     * @return The returned encapsulated instance.
     */
    public final static DropPet getInstance()
    {
            return instance;
    }
	
    /**
     * Check if the player has requirements to drop the pet.
     * @param player
     * 			The player dropping the pet.
     * @param itemId
     * 			The pet item identity being dropped.
     * @param slot
     * 			The slot of the item being dropped.
     */
	public void dropPetRequirements(Client player, int itemId, int slot)
	{
		if (player.getPetSummoned())
        {
			player.sendMessage("You already have a pet following you.");
			return;
        }
		for (int i = 0; i < PetData.petData.length; i++)
        {
				if (PetData.petData[i][1] == itemId)
                {

                	dropPet(player, itemId, slot);
                }
        }

	}
	
	/**
	 * Summon the pet and delete inventory pet item.
	 * @param player
	 * 			The player dropping the pet.
	 * @param itemId
	 * 			The pet identity item in inventory.
	 * @param slot
	 * 			The slot of the pet item in inventory.
	 */
	private void dropPet(Client player, int itemId, int slot)
	{
		for (int i = 0; i < PetData.petData.length; i++)
        {
                if (PetData.petData[i][1] == itemId)
                {
                	player.sendMessage("You drop your pet and it starts following you.");
                	Pet.summonPet(player, PetData.petData[i][0], player.absX, player.absY - 1, player.heightLevel);
                }
        }
        player.getItems().deleteItem(itemId, slot, player.playerItemsN[slot]);
	}

}
