/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
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
package com.aionemu.gameserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;

import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

public class PvPSpreeService {

	private static final Logger log = LoggerFactory.getLogger("PVP_LOG");
	private static final String STRING_SPREE1 = LanguageHandler.translate(CustomMessageId.SPREE1);
	private static final String STRING_SPREE2 = LanguageHandler.translate(CustomMessageId.SPREE2);
	private static final String STRING_SPREE3 = LanguageHandler.translate(CustomMessageId.SPREE3);

	public static void increaseRawKillCount(Player winner) {
		int currentRawKillCount = winner.getRawKillCount();
		winner.setRawKillCount(currentRawKillCount + 1);
		int newRawKillCount = currentRawKillCount + 1;
		//PacketSendUtility.sendWhiteMessageOnCenter(winner, LanguageHandler.translate(CustomMessageId.KILL_COUNT) + newRawKillCount);
		PacketSendUtility.sendSys2Message(winner, "SPREE", LanguageHandler.translate(CustomMessageId.KILL_COUNT) + newRawKillCount);

		if ((newRawKillCount == CustomConfig.SPREE_KILL_COUNT) || (newRawKillCount == CustomConfig.RAMPAGE_KILL_COUNT) || (newRawKillCount == CustomConfig.GENOCIDE_KILL_COUNT)) {
			if (newRawKillCount == CustomConfig.SPREE_KILL_COUNT)
				updateSpreeLevel(winner, 1);
			if (newRawKillCount == CustomConfig.RAMPAGE_KILL_COUNT)
				updateSpreeLevel(winner, 2);
			if (newRawKillCount == CustomConfig.GENOCIDE_KILL_COUNT)
				updateSpreeLevel(winner, 3);
		}
	}

	private static void updateSpreeLevel(Player winner, int level) {
		winner.setSpreeLevel(level);
		sendUpdateSpreeMessage(winner, level);
	}

	private static void sendUpdateSpreeMessage(Player winner, int level) {
		Iterator<Player> iter = World.getInstance().getPlayersIterator();
		for (Player p : World.getInstance().getAllPlayers()) {
			if (level == 1)
				PacketSendUtility.sendBrightYellowMessageOnCenter(p, winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE1) + STRING_SPREE1 + LanguageHandler.translate(CustomMessageId.MSG_SPREE1_1));
					while (iter.hasNext()) {
						PacketSendUtility.sendBrightYellowMessage(iter.next(), winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE1) + STRING_SPREE1 + LanguageHandler.translate(CustomMessageId.MSG_SPREE1_1));
					}
					//AbyssPointsService.addGp(winner, CustomConfig.ADVANCED_GP_REWARD_SPREE1);
			if (level == 2)
				PacketSendUtility.sendBrightYellowMessageOnCenter(p, winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE2) + STRING_SPREE2 + LanguageHandler.translate(CustomMessageId.MSG_SPREE2_1));
					while (iter.hasNext()) {
						PacketSendUtility.sendBrightYellowMessage(iter.next(), winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE2) + STRING_SPREE2 + LanguageHandler.translate(CustomMessageId.MSG_SPREE2_1));
					}
					//AbyssPointsService.addGp(winner, CustomConfig.ADVANCED_GP_REWARD_SPREE2);
			if (level == 3)
				PacketSendUtility.sendBrightYellowMessageOnCenter(p, winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE3) + STRING_SPREE3
					+ LanguageHandler.translate(CustomMessageId.MSG_SPREE3_1));
					while (iter.hasNext()) {
						PacketSendUtility.sendBrightYellowMessage(iter.next(), winner.getName() + LanguageHandler.translate(CustomMessageId.CUSTOM_MSG1) + winner.getCommonData().getRace().toString().toLowerCase() + LanguageHandler.translate(CustomMessageId.MSG_SPREE3) + STRING_SPREE3
					+ LanguageHandler.translate(CustomMessageId.MSG_SPREE3_1));
					}
					//AbyssPointsService.addGp(winner, CustomConfig.ADVANCED_GP_REWARD_SPREE3);
		}
		log.info("[PvP][Spree] {Player : " + winner.getName() + "} have now " + level + " Killing Spree Level");
	}

	public static void cancelSpree(Player victim, Creature killer, boolean isPvPDeath) {
		int killsBeforeDeath = victim.getRawKillCount();
		victim.setRawKillCount(0);
		if (victim.getSpreeLevel() > 0) {
			victim.setSpreeLevel(0);
			sendEndSpreeMessage(victim, killer, isPvPDeath, killsBeforeDeath);
		}
	}

	private static void sendEndSpreeMessage(Player victim, Creature killer, boolean isPvPDeath, int killsBeforeDeath) {
		String spreeEnder = isPvPDeath ? ((Player) killer).getName() : LanguageHandler.translate(CustomMessageId.SPREE_MONSTER_MSG);
		for (Player p : World.getInstance().getAllPlayers()) {
			PacketSendUtility.sendWhiteMessageOnCenter(p, LanguageHandler.translate(CustomMessageId.SPREE_END_MSG1) + victim.getName() + LanguageHandler.translate(CustomMessageId.SPREE_END_MSG2) + spreeEnder + LanguageHandler.translate(CustomMessageId.SPREE_END_MSG3) + killsBeforeDeath + LanguageHandler.translate(CustomMessageId.SPREE_END_MSG4));
		}
		log.info("[PvP][Spree] {The Spree from " + victim.getName() + "} was ended by " + spreeEnder + "}");
	}
}
