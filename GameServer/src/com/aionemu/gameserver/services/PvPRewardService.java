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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

public class PvPRewardService {

	private static final Logger log = LoggerFactory.getLogger("PVP_LOG");
	private static final String templar = "166030009";
	private static final String gladiator = "166030009";
	private static final String cleric = "166030009";
	private static final String chanter = "166030009";
	private static final String assassin = "166030009";
	private static final String ranger = "166030009";
	private static final String sorcerer = "166030009";
	private static final String sm = "166030009";
	private static final String robot = "166030009";
	private static final String gunner = "166030009";
	private static final String bard = "166030009";

	private static List<Integer> getRewardList(PlayerClass pc) {
		List<Integer> rewardList = new ArrayList<Integer>();
		String rewardString = "";
		switch (pc) {
			case TEMPLAR:
				rewardString = templar;
				break;
			case GLADIATOR:
				rewardString = gladiator;
				break;
			case CLERIC:
				rewardString = cleric;
				break;
			case CHANTER:
				rewardString = chanter;
				break;
			case ASSASSIN:
				rewardString = assassin;
				break;
			case RANGER:
				rewardString = ranger;
				break;
			case SORCERER:
				rewardString = sorcerer;
				break;
			case SPIRIT_MASTER:
				rewardString = sm;
				break;
			case RIDER:
				rewardString = robot;
				break;
			case GUNNER:
				rewardString = gunner;
				break;
			case BARD:
				rewardString = bard;
				break;				
			default:
				rewardString = null;
		}

		if (rewardString != null) {
			String[] parts = rewardString.split(",");
			for (int i = 0; i < parts.length; i++)
				rewardList.add(Integer.valueOf(Integer.parseInt(parts[i])));
		}
		else {
			log.warn("[PvP][Reward] There is no reward list for the {PlayerClass: " + pc.toString() + "}");
		}
		return rewardList;
	}

	public static int getRewardId(Player winner, Player victim, boolean isAdvanced) {
		int itemId = 0;
		if (victim.getSpreeLevel() > 2)
			isAdvanced = true;
		if (!isAdvanced) {
			int lvl = victim.getLevel();
			if (lvl <= 50)
				itemId = 186000031;
			if ((lvl > 50) && (lvl <= 55))
				itemId = 186000030;
			if ((lvl > 55) && (lvl <= 60))
				itemId = 186000096;
			if (lvl >= 65)
				itemId = 186000242;
		}
		return itemId;
	}



	public static int getRewardIdAnnex(Player winner, Player victim, boolean isAdvanced) {
		int itemId = 0;
		if (victim.getSpreeLevel() > 2)
			isAdvanced = true;
		if (!isAdvanced) {
			int lvl = victim.getLevel();
			if (lvl <= 50)
				itemId = 186000031;
			if ((lvl > 50) && (lvl <= 55))
				itemId = 186000030;
			if ((lvl > 55) && (lvl <= 60))
				itemId = 186000096;
			if (lvl >= 65)
				itemId = 186000242;
		}
		return itemId;
	}
	
	public static int getRewardIdMember(Player member, Player victim, boolean isAdvanced) {
		int itemId = 0;
		if (victim.getSpreeLevel() > 2)
			isAdvanced = true;
		if (!isAdvanced) {
			int lvl = victim.getLevel();
			if (lvl <= 50)
				itemId = 186000031;
			if ((lvl > 50) && (lvl <= 55))
				itemId = 186000030;
			if ((lvl > 55) && (lvl <= 60))
				itemId = 186000096;
			if (lvl >= 65)
				itemId = 186000147;
		}
		return itemId;
	}
	
	public static int getRewardIdSupportMember(Player member, Player victim, boolean isAdvanced) {
		int itemId = 0;
		if (victim.getSpreeLevel() > 2)
			isAdvanced = true;
		if (!isAdvanced) {
			int lvl = victim.getLevel();
			if (lvl <= 50)
				itemId = 186000031;
			if ((lvl > 50) && (lvl <= 55))
				itemId = 186000030;
			if ((lvl > 55) && (lvl <= 60))
				itemId = 186000096;
			if (lvl >= 65)
				itemId = 186000147;
		}
		return itemId;
	}
	public static float getMedalRewardChance(Player winner, Player victim) {
		float chance = CustomConfig.MEDAL_REWARD_CHANCE;
		chance += 1.5F * winner.getRawKillCount();
		int diff = victim.getLevel() - winner.getLevel();
		if (diff * diff > 100) {
			if (diff < 0)
				diff = -10;
			else
				diff = 10;
		}
		chance += 2.0F * diff;

		if ((victim.getSpreeLevel() > 0) || (chance > 100.0F)) {
			chance = 100.0F;
		}

		return chance;
	}

	public static int getRewardQuantity(Player winner, Player victim) {
		int rewardQuantity = winner.getSpreeLevel() + 1;
		switch (victim.getSpreeLevel()) {
			case 1:
				rewardQuantity = 1;
				break;
			case 2:
				rewardQuantity = 1;
				break;
			case 3:
				rewardQuantity += 2;
		}

		return rewardQuantity;
	}

	public static int getRewardQuantityANNEX(Player winner, Player victim) {
		int rewardQuantity = winner.getSpreeLevel() + 1;
		switch (victim.getSpreeLevel()) {
			case 1:
				rewardQuantity = 1;
				break;
			case 2:
				rewardQuantity = 1;
				break;
			case 3:
				rewardQuantity = 2;
		}

		return rewardQuantity;
	}
	
	public static int getRewardQuantitygp(Player member, Player victim) {
		int rewardQuantity = member.getSpreeLevel() + 1;
		switch (victim.getSpreeLevel()) {
			case 1:
				rewardQuantity = 1;
				break;
			case 2:
				rewardQuantity = 1;
				break;
			case 3:
				rewardQuantity = 1;
		}

		return rewardQuantity;
	}
	
	public static float getTollRewardChance(Player winner, Player victim) {
		float chance = CustomConfig.TOLL_REWARD_CHANCE;
		chance += 1.5F * winner.getRawKillCount();
		int diff = victim.getLevel() - winner.getLevel();
		if (diff * diff > 100) {
			if (diff < 0)
				diff = -10;
			else
				diff = 10;
		}
		chance += 2.0F * diff;

		if ((victim.getSpreeLevel() > 0) || (chance > 100.0F)) {
			chance = 100.0F;
		}
		return chance;
	}
	
	
	public static int getTollQuantity(Player winner, Player victim) {
		int tollQuantity = winner.getSpreeLevel() + 1;
		switch (victim.getSpreeLevel()) {
			case 1:
				tollQuantity = 1;
				break;
			case 2:
				tollQuantity = 1;
				break;
			case 3:
				tollQuantity += 2;
		}

		return tollQuantity;
	}

	
	private static List<Integer> getAdvancedReward(Player winner) {
		int lvl = winner.getLevel();
		PlayerClass pc = winner.getPlayerClass();
		List<Integer> rewardList = new ArrayList<Integer>();

		if ((lvl >= 10) && (lvl < 70)) {
			rewardList.addAll(getFilteredRewardList(pc, 10, 70));
		}
		return rewardList;
	}

	private static List<Integer> getFilteredRewardList(PlayerClass pc, int minLevel, int maxLevel) {
		List<Integer> filteredRewardList = new ArrayList<Integer>();
		List<Integer> rewardList = getRewardList(pc);

		for (Iterator<Integer> i = rewardList.iterator(); i.hasNext();) {
			int id = i.next();
			ItemTemplate itemTemp = DataManager.ITEM_DATA.getItemTemplate(id);
			if (itemTemp == null) {
				log.warn("[PvP][Reward] Incorrect {Item ID: " + id + "} reward for {PlayerClass: " + pc.toString() + "}");
			}
			int itemLevel = itemTemp.getLevel();

			if (itemLevel >= minLevel && itemLevel < maxLevel)
				filteredRewardList.add(id);
		}
		return filteredRewardList.size() > 0 ? filteredRewardList : new ArrayList<Integer>();
	}
}
