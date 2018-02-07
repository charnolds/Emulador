/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */

package ai.worlds.kaldor;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @Author A7xatomic
 */
@AIName("anohasword")
public class AnohaswordAI2 extends NpcAI2 {
    @Override
    protected void handleDialogStart(Player player) {
        if (player.getInventory().getFirstItemByItemId(185000215) != null) { //Anoha sealing stone
		player.getInventory().decreaseByItemId(185000215, 1);
		Anohaspawnannounce();
	        switch (getNpcId()) {
                //Berseker Anoha
                case 804576:
                            spawn(855263, 789.2935f, 489.98657f, 143.47517f, (byte) 17); //Anoha
                            break;
                case 804577:
                            spawn(855263, 789.2935f, 489.98657f, 143.47517f, (byte) 17); //Anoha
                            break;
		    }
			AI2Actions.deleteOwner(this);
        } else {
            PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Necesitas 1 Anoha sealing stone para invocar a Anoha");
        }
    }
	
    private void Anohaspawnannounce() {
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Berseker Anoha was spawned on Kaldor!");
            }
        });
    }
}