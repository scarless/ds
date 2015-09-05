package server.music;

import server.game.players.Client;
import server.util.Misc;

public class Music {

    private static int[][] MUSIC_DATA = {     //all the songs added
            {50, 51, 2},
            {48, 50, 3},
            {46, 56, 8},
            {51, 60, 9},
            {46, 50, 12},
            {52, 57, 14},
            {47, 52, 15},
            {45, 53, 18},
            {45, 54, 18},
            {47, 50, 35},
            //{47, 50, 36},
            {51, 50, 36},
            {47, 51, 49},
            {51, 49, 50},
            {44, 75, 52},
            {46, 53, 54},
            {49, 54, 56},
            {45, 75, 57},
            {47, 47, 62},//Newbie Melody, Tutorial Island
            {47, 48, 62},//Newbie Melody, Tutorial Island
            {48, 47, 62},//Newbie Melody, Tutorial Island
            {48, 48, 62},//Newbie Melody, Tutorial Island
            {48, 148, 62},//Newbie Melody, Tutorial Island
            {49, 48, 62},//Newbie Melody, Tutorial Island
            {50, 49, 64},
            {49, 49, 64},
            {43, 75, 65},//Miracle Dance, Mind Altar
            //{51, 48, 69}, // this song is currently corrupt! Do not play!
            {46, 52, 72},
            {50, 50, 76},
            {48, 49, 85},
            //{46, 49, 92},
            {51, 54, 93}, //Edgeville
            //{46, 55, 96}, //this song is currently corrupt! Do not play!
            {48, 54, 98},
            {47, 49, 105},
            {50, 52, 106},
            {44, 53, 119},
            {51, 51, 123},
            {50, 53, 125}, //Varrock But Laggyyyy
            // {50, 53, 157}, //Varrock
            {46, 51, 127},
            {48, 53, 141},
            {41, 75, 143},//Down to Earth, Earth Altar
            {42, 75, 146},//Zealot, Water Altar
            {48, 51, 151},
            {51, 52, 157},
            {51, 53, 157},
            {52, 51, 122}, //Duel Arena Hospital - Shine
            {41, 40, 588}, //Pest Control - In-game
            {41, 41, 587}, //Pest Control - Lobby
            {40, 75, 158},//Quest, Fire Altar
            {55, 51, 380},//Barrows
            {50, 55, 169},
            {50, 154, 144}, //Varrock Sewers
            {48, 55, 169}, //Wilderness North of Edge
            {38, 80, 469}, //Tzhaar cave
            {49, 53, 175},
            {50, 54, 177},
            {46, 49, 180},
            {45, 52, 186},
            {39, 75, 190},//Heart and Mind, Body Altar
            {47, 153, 325},//Cave Background, Dwarven Mines
            {46, 153, 325},//Cave Background, Dwarven Mines
            {47, 152, 325},//Cave Background, Dwarven Mines
            {49, 50, 327},
            {47, 53, 49}, //Wander
            {48, 52, 333},
            {50, 56, 337},
            {40, 74, 419},
            {47, 56, 435},
            {38, 48, 80}, //Soundscape, Castle Wars
            {37, 148, 28}, //Attack 5 - Castle Wars, Sara Portal
            {43, 54, 60}, //Lightwalk - Camelot
            {40, 53, 328}, //March
            {29, 81, 537},//Dogs of War - Stronghold of Security - Vault of War
            {31, 81, 558},//Food for Thought - Stronghold of Security - Catacomb of Famine
            {33, 82, 559},//Malady - Stronghold of Security - Pit of Pestilence
            {36, 81, 560},//Dance of Death - Stronghold of Security - Sepulchre of Death
            {41, 58, 141}, //Relleka Rock Crabs
            {41, 53, 140} //Ranging Guild
    };

    /**
     * Checks how many areas we have loaded.
     * @param c Client.
     */
    public static void checkMusic(Client c) {
        if ((c.playerRights == 3 || c.playerRights == 5)) {
            for (int[] aMUSIC_DATA : MUSIC_DATA) {
                if (c.getX() / 64 == aMUSIC_DATA[0] && c.getY() / 64 == aMUSIC_DATA[1]/* && player.getPlayList().auto*/) {
                     if (c.getTemporary("CURRENT_SONG") == null || (Integer) c.getTemporary("CURRENT_SONG") != aMUSIC_DATA[2]) {
                         c.sendMessage("@gre@Play Music Has Music:@bla@ " +aMUSIC_DATA[0]+" : " + aMUSIC_DATA[1]);
                     }
                }
            }
            c.sendMessage("@red@Play Music No Music:@bla@ " +c.getX() / 64+" : " + c.getY() / 64);
        }
    }

    /**
     * Checks which song is played in which region.
     * @param player the player.
     */
    public static void playMusic(Client player) {
        for (int[] aMUSIC_DATA : MUSIC_DATA) {
            if (player.getX() / 64 == aMUSIC_DATA[0] && player.getY() / 64 == aMUSIC_DATA[1]/* && player.getPlayList().auto*/) {
                if (player.getTemporary("CURRENT_SONG") == null || (Integer) player.getTemporary("CURRENT_SONG") != aMUSIC_DATA[2]) {
                    player.getPlayList();
                    if (player.getPlayList().auto) {
                        player.addTemporary("CURRENT_SONG", aMUSIC_DATA[2]);
                        player.playMusic(aMUSIC_DATA[2]);
                    }
                    int[] edgeVilleSongs = {98, 111, 127, 157, 106};
                    int toPlay = Misc.random(4);
                    if (aMUSIC_DATA[0] == 48 && aMUSIC_DATA[1] == 54) {
                        aMUSIC_DATA[2] = edgeVilleSongs[toPlay];
                    }
                    player.getPlayList().playSong(aMUSIC_DATA[2]);
                }
            }
        }
    }
}
