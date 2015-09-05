package server.game.players.pets;

import server.game.npcs.*;
import server.game.players.*;


public class Pets {

	public Pets(Client Client) {
	}
public void pickUp(Client c, int Type) {
for (int i = 0; i < NPCHandler.maxNPCs; i++) {
if (NPCHandler.npcs[i] == null)
				continue;	
	}       
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (NPCHandler.npcs[i].npcType == Type) {
					if (NPCHandler.npcs[i].spawnedBy == c.playerId && NPCHandler.npcs[i].spawnedBy > 0) {
				NPCHandler.npcs[i].absX = 0;
				NPCHandler.npcs[i].absY = 0;
					NPCHandler.npcs[i] = null;
						break;
					
					}
				}
			}			
		}
	}
int[][] Pets = { 
{3505, 7583}, /*Hell Kitten*/		
{3506, 7584}, /*Lazy Hellcat*/
{766, 1560}, /*Hell Kitten*/	
{3507, 7585}, /*Wily hellcat*/	
{765, 1559}, /*Pet Kitten*/	
{764, 1558}, /*Pet Kitten*/	
{763, 1557}, /*Pet Kitten*/	
{762, 1556}, /*Pet Kitten*/	
{761, 1555}, /*Pet Kitten*/	
{768, 1561}, /*Pet Kitten*/	
{769, 1562}, /*Pet Kitten*/	
{770, 1563},/*Pet Kitten*/	
{771, 1564}, /*Pet Kitten*/	
{772, 1565}, /*Pet Kitten*/	
{773, 1566}, /*Pet Kitten*/	
{6901, 12470}, /*Red Baby Dragon*/	
{6903, 12472}, /*Blue Baby Dragon*/	
{6905, 12474}, /*Green Baby Dragon*/	
{6907, 12476}, /*Black Baby Dragon*/	
{6959, 12513}, /*Terrier*/	
{6961, 12515}, /*GreyHound*/	
{6963, 12517}, /*Labrador*/	
{6965, 12519}, /*Dalmation*/	
{6967, 12521}, /*SheepDog*/	
{6968, 12523}, /*Bulldog*/	
{6909, 12482}, /*Penguin*/	

};
	
	public void pickUpClean(Client c, int id) {
		for (int i = 0; i < Pets.length; i++)
			if (Pets[i][0] == id)
				c.getItems().addItem(Pets[i][1], 1);
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null)
				continue;
			if (NPCHandler.npcs[i].npcType == id) {
				NPCHandler.npcs[i].absX = 0;
				NPCHandler.npcs[i].absY = 0;
			}
		}
		c.hasNpc = false;
	}

}