/*
 * This file is part of NextGenCore <Ver:3.7>.
 *
 *  NextGenCore is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  NextGenCore is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with NextGenCore. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package playercommands;

import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

/**
 * @author A7xatomic!
 */
public class CoinXToll extends PlayerCommand {

    public CoinXToll() {
        super("coinxtoll");

    }

@Override
    public void execute(Player player, String... params) {
        int gp = 50;
		int toll = 10;
		int coinxtoll = 182005367;
		int cantidad = 10;
		Storage bag = player.getInventory();
		long itemsInBag = bag.getItemCountByItemId(coinxtoll);
		if (itemsInBag < cantidad) {
            PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Necesitas 10 Broken Coins");
            return;
        }
        //if (player.getAbyssRank().getGp() < gp) {
          //  PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_ENOUGH_AP) + gp);
            //return;
        //}
        Item item = bag.getFirstItemByItemId(coinxtoll);
        bag.decreaseByObjectId(item.getObjectId(), cantidad);
        InGameShopEn.getInstance().addToll(player, toll);
        PacketSendUtility.sendBrightYellowMessageOnCenter(player, toll + " Tolls" + " Acreditados a tu cuenta!");
        //AbyssPointsService.addGp(player, -gp);

    }
}
