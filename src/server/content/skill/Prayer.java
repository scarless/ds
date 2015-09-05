package server.content.skill;

import server.Config;
import server.event.CycleEvent;
import server.event.CycleEventContainer;
import server.event.CycleEventHandler;
import server.game.players.Client;

public class Prayer
{

        Client c;

        public int[][] bonesExp = {
                { 526, 5 },
                { 532, 15 },
                { 534, 80 },
                { 536, 190 },
                { 6729, 230 },
                { 18830, 330 },
                { 4834, 250 },
                { 6812, 300 },
                { 11943, 330 }
        };

        public Prayer(Client c)
        {
                this.c = c;
        }

        public void buryBone(int id, int slot)
        {
                if (System.currentTimeMillis() - c.buryDelay > 1500)
                {
                        c.getItems().deleteItem(id, slot, 1);
                        c.sendMessage("You bury the bones.");
                        c.getPA().addSkillXP(getExp(id) * Config.PRAYER_EXPERIENCE, 5);
                        c.buryDelay = System.currentTimeMillis();
                        c.startAnimation(827);
                        if (c.absX == 3106 && c.absY == 3496)
                        {
                                getBone(id);
                        }
                }
        }

        private int boneAmount;
        public void bonesOnAltar2(final int id, int amount)
        {
                if (amount >= 1 && c.usingAltar && c.getItems().playerHasItem(id, 1)) {
                        c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
                        c.sendMessage("The gods are pleased with your offering.");
                        c.getPA().addSkillXP(getExp(id) * 2 * Config.PRAYER_EXPERIENCE, 5);
                        c.startAnimation(896);
                        c.gfx100(624);
                        if (c.absX == 3091 && c.absY == 3506)
                        {
                                c.getPA().stillGfx(624, 3092, 3506, 0, 0);
                        }
                        else if (c.absX == 3092 && c.absY == 3505)
                        {
                                c.getPA().stillGfx(624, 3092, 3506, 0, 0);
                        }
                        else if (c.absX == 3093 && c.absY == 3505)
                        {
                                c.getPA().stillGfx(624, 3093, 3506, 0, 0);
                        }
                        else if (c.absX == 3094 && c.absY == 3506)
                        {
                                c.getPA().stillGfx(624, 3093, 3506, 0, 0);
                        }
                }
                amount--;
                boneAmount = amount;
                CycleEventHandler.getSingleton().addEvent(this, new CycleEvent()
                {@
                        Override
                        public void execute(CycleEventContainer altar)
                        {
                                if (boneAmount > 0 && c.usingAltar && c.getItems().playerHasItem(id, 1))
                                {
                                        boneAmount--;
                                        c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);

                                        c.sendMessage("The gods are pleased with your offering.");
                                        c.getPA().addSkillXP(getExp(id) * 2 * Config.PRAYER_EXPERIENCE, 5);
                                        c.startAnimation(896);
                                        c.gfx100(624);
                                       // System.out.println("making.");
                                }
                                else
                                        altar.stop();
                        }@
                        Override
                        public void stop()
                        {
                                c.usingAltar = false;
                                //System.out.println("stopped.");
                        }
                }, 4);

        }

        public void getBone(int id)
        {
                if (c.absX == 3091 && c.absY == 3506)
                {
                        c.getItems().bonesId(id, 1);
                }
        }

        public boolean isBone(int id)
        {
                for (int j = 0; j < bonesExp.length; j++)
                        if (bonesExp[j][0] == id)
                                return true;
                return false;
        }

        public int getExp(int id)
        {
                for (int j = 0; j < bonesExp.length; j++)
                {
                        if (bonesExp[j][0] == id)
                                return bonesExp[j][1];
                }
                return 0;
        }
}