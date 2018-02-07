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

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author A7xatomic!
 */
public class Stigma extends PlayerCommand {

    public Stigma() {
        super("stigma");
    }
   int newAdvancedSlotSize = 6;
    @Override
    public void execute(Player player, String... params) {
		if (player.getRace() == Race.ELYOS) {
		 //player.getCommonData().setStigmaSlotSize(newAdvancedSlotSize);
	     player.getCommonData().setAdvancedStigmaSlotSize(newAdvancedSlotSize);
		 //PacketSendUtility.sendPacket(player, SM_CUBE_UPDATE.stigmaSlots(player.getCommonData().getStigmaSlotSize()));
		 PacketSendUtility.sendPacket(player, SM_CUBE_UPDATE.stigmaSlots(player.getCommonData().getAdvancedStigmaSlotSize()));
        //completeQuest(player, 1929);
		//completeQuest(player, 11550);
		//completeQuest(player, 30217);
		//completeQuest(player, 11276);
		//completeQuest(player, 11049);
		//completeQuest(player, 3932);
		//completeQuest(player, 3931);
		//completeQuest(player, 3930);
		//PacketSendUtility.sendMessage(player, "Relogea para Activar los Stigma Slots");
	 } else {
		 //player.getCommonData().setStigmaSlotSize(newAdvancedSlotSize);
	     player.getCommonData().setAdvancedStigmaSlotSize(newAdvancedSlotSize);
		 //PacketSendUtility.sendPacket(player, SM_CUBE_UPDATE.stigmaSlots(player.getCommonData().getStigmaSlotSize()));
		 PacketSendUtility.sendPacket(player, SM_CUBE_UPDATE.stigmaSlots(player.getCommonData().getAdvancedStigmaSlotSize()));
		//completeQuest(player, 2900);
		//completeQuest(player, 21550);
		//completeQuest(player, 30317);
		//completeQuest(player, 21278);
		//completeQuest(player, 21049);
		//completeQuest(player, 4936);
		//completeQuest(player, 4935);
		//completeQuest(player, 4934);
		//PacketSendUtility.sendMessage(player, "Relogea para Activar los Stigma Slots");
	      }
	  }
    private static void completeQuest(Player player, int questId) {
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            player.getQuestStateList().addQuest(questId, new QuestState(questId, QuestStatus.COMPLETE, 0, 0, null, 0, null));
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(questId, QuestStatus.COMPLETE.value(), 0));
        } else {
            qs.setStatus(QuestStatus.COMPLETE);
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(questId, qs.getStatus(), qs.getQuestVars()
                    .getQuestVars()));
        }
    }
    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax .stigma");
    }	
}
