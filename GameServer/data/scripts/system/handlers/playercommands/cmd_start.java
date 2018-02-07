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
package playercommands;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

/**
 * @author Maestross
 */
public class cmd_start extends PlayerCommand {

    public cmd_start() {
        super("celeste1");
    }

	@Override
	public void execute(final Player player, String... params) {
		if (player.getPlayerClass() == PlayerClass.ASSASSIN) {
			ItemService.addItem(player, 100001507, 2);
			ItemService.addItem(player, 114301464, 1);
			ItemService.addItem(player, 113301428, 1);
			ItemService.addItem(player, 125003356, 1);
			ItemService.addItem(player, 110301463, 1);
			ItemService.addItem(player, 112301347, 1);
			ItemService.addItem(player, 111301403, 1);
			ItemService.addItem(player, 123001412, 1);
			ItemService.addItem(player, 120001494 , 2);
			ItemService.addItem(player, 121001366, 1);
			ItemService.addItem(player, 122001635 , 2);
			ItemService.addItem(player, 110900136, 1);
			ItemService.addItem(player, 110900839, 1);
			ItemService.addItem(player, 187000145, 1);//wings
			} 
   else if (player.getPlayerClass() == PlayerClass.SPIRIT_MASTER) {
			ItemService.addItem(player, 111101411, 1);
			ItemService.addItem(player, 169650000, 1);
			ItemService.addItem(player, 125003357, 1);
			ItemService.addItem(player, 113101429, 1);
			ItemService.addItem(player, 112101365, 1);
			ItemService.addItem(player, 110101566, 1);
			ItemService.addItem(player, 114101458, 1);
			ItemService.addItem(player, 123001413, 1);
			ItemService.addItem(player, 120001495, 2);
			ItemService.addItem(player, 121001367, 1);
			ItemService.addItem(player, 110900839, 1);
			ItemService.addItem(player, 186000242, 1000);
			ItemService.addItem(player, 187000145, 1);//wings
		}
		player.setCommandUsed(true);

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				player.setCommandUsed(false);
			}
		}, 500 * 500 * 100000);
		PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.DEFAULT_FINISH_MESSAGE));
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Syntax: .start ");
	}
}
