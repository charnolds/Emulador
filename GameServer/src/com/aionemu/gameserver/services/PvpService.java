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
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.configs.main.LoggingConfig;
import com.aionemu.gameserver.configs.main.PunishmentConfig;
import com.aionemu.gameserver.controllers.attack.AggroInfo;
import com.aionemu.gameserver.controllers.attack.KillList;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RewardType;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.audit.AuditLogger;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;

/**
 * @author Sarynth
 */
public class PvpService {

    private static Logger log = LoggerFactory.getLogger("KILL_LOG");

    public static final PvpService getInstance() {
        return SingletonHolder.instance;
    }

    private FastMap<Integer, KillList> pvpKillLists;
	private ArrayList<String> antiZergMap;

    private PvpService() {
        pvpKillLists = new FastMap<>();
    }

    /**
     * @param winnerId
     * @param victimId
     * @return
     */
    private int getKillsFor(int winnerId, int victimId) {
        KillList winnerKillList = pvpKillLists.get(winnerId);

        if (winnerKillList == null) {
            return 0;
        }
        return winnerKillList.getKillsFor(victimId);
    }

    /**
     * @param winnerId
     * @param victimId
     */
    private void addKillFor(int winnerId, int victimId) {
        KillList winnerKillList = pvpKillLists.get(winnerId);
        if (winnerKillList == null) {
            winnerKillList = new KillList();
            pvpKillLists.put(winnerId, winnerKillList);
        }
        winnerKillList.addKillFor(victimId);
    }

    /**
     * @param victim
     */
    public void doReward(Player victim) {
        // winner is the player that receives the kill count
        final Player winner = victim.getAggroList().getMostPlayerDamage();

        int totalDamage = victim.getAggroList().getTotalDamage();

        if (totalDamage == 0 || winner == null || winner.getRace() == victim.getRace()) {
            return;
        }

        // Add Player Kill to record.
        if (this.getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
            winner.getAbyssRank().setAllKill();
            int kills = winner.getAbyssRank().getAllKill();
            // Pvp Kill Reward.
            if (CustomConfig.ENABLE_KILL_REWARD) {
                if (kills % CustomConfig.KILLS_NEEDED1 == 1) {
                    ItemService.addItem(winner, CustomConfig.REWARD1, 1);
                    PacketSendUtility.sendMessage(winner, "Congratulations, you have won " + "[item: " + CustomConfig.REWARD1
                            + "] for having killed " + CustomConfig.KILLS_NEEDED1 + " players !");
                    log.info("[REWARD] Player [" + winner.getName() + "] win 2 [" + CustomConfig.REWARD1 + "]");
                }
                if (kills % CustomConfig.KILLS_NEEDED2 == 3) {
                    ItemService.addItem(winner, CustomConfig.REWARD2, 1);
                    PacketSendUtility.sendMessage(winner, "Congratulations, you have won " + "[item: " + CustomConfig.REWARD2
                            + "] for having killed " + CustomConfig.KILLS_NEEDED2 + " players !");
                    log.info("[REWARD] Player [" + winner.getName() + "] win 4 [" + CustomConfig.REWARD2 + "]");
                }
                if (kills % CustomConfig.KILLS_NEEDED3 == 5) {
                    ItemService.addItem(winner, CustomConfig.REWARD3, 1);
                    PacketSendUtility.sendMessage(winner, "Congratulations, you have won " + "[item: " + CustomConfig.REWARD3
                            + "] for having killed " + CustomConfig.KILLS_NEEDED3 + " players !");
                    log.info("[REWARD] Player [" + winner.getName() + "] win 6 [" + CustomConfig.REWARD3 + "]");
                }
            }
        }

        // Announce that player has died.
        PacketSendUtility.broadcastPacketAndReceive(victim, SM_SYSTEM_MESSAGE.STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(victim.getName(), winner.getName()));

        // Pvp Kill Reward.
        int reduceap = PunishmentConfig.PUNISHMENT_REDUCEAP;
        if (reduceap < 0) {
            reduceap *= -1;
        }
        if (reduceap > 100) {
            reduceap = 100;
        }

        //Kill-log
        if (LoggingConfig.LOG_KILL) {
            log.info("[KILL] Player [" + winner.getName() + "] killed [" + victim.getName() + "]");
        }

        if ((LoggingConfig.LOG_PL) || (reduceap > 0)) {
            String ip1 = winner.getClientConnection().getIP();
            String mac1 = winner.getClientConnection().getMacAddress();
            String ip2 = victim.getClientConnection().getIP();
            String mac2 = victim.getClientConnection().getMacAddress();
            if ((mac1 != null) && (mac2 != null) && winner.getAccessLevel() < 3 && victim.getAccessLevel() < 3) {
                if ((ip1.equalsIgnoreCase(ip2)) && (mac1.equalsIgnoreCase(mac2))) {
                    AuditLogger.info(winner, "Power Leveling : " + winner.getName() + " with " + victim.getName() + ", They have the sames ip=" + ip1 + " and mac=" + mac1 + ".");
                    if (reduceap > 0) {
                        int win_ap = winner.getAbyssRank().getAp() * reduceap / 100;
                        int vic_ap = victim.getAbyssRank().getAp() * reduceap / 100;
                        AbyssPointsService.addAp(winner, -win_ap);
                        AbyssPointsService.addAp(victim, -vic_ap);
		        PacketSendUtility.sendMessage(winner, "[PL-AP] You lost " + reduceap + "% of your total ap");
			    PacketSendUtility.sendMessage(victim, "[PL-AP] You lost " + reduceap + "% of your total ap");
                    }
                    return;
                }
                if (ip1.equalsIgnoreCase(ip2)) {
                    AuditLogger.info(winner, "Possible Power Leveling : " + winner.getName() + " with " + victim.getName() + ", They have the sames ip=" + ip1 + ".");
                    AuditLogger.info(winner, "Check if " + winner.getName() + " and " + victim.getName() + " are Brothers-Sisters-Lovers-dogs-cats...");
                }
            }
        }
		if (winner.getLevel() - victim.getLevel() <= CustomConfig.MAX_AUTHORIZED_LEVEL_DIFF) {
			if (getKillsFor(winner.getObjectId().intValue(), victim.getObjectId().intValue()) < CustomConfig.CHAIN_KILL_NUMBER_RESTRICTION) {
				if (CustomConfig.ENABLE_MEDAL_REWARDING) {
					//if (Rnd.get() * 100 < PvPRewardService.getMedalRewardChance(winner, victim)) {
						int medalId = PvPRewardService.getRewardId(winner, victim, false);
						long medalCount = PvPRewardService.getRewardQuantity(winner, victim);
						ItemService.addItem(winner, medalId, medalCount);
						if (winner.getInventory().getItemCountByItemId(medalId) > 0) {
							if (medalCount == 1)
								PacketSendUtility.sendPacket(winner, new SM_SYSTEM_MESSAGE(1390000, new DescriptionId(medalId)));
							else {
								PacketSendUtility.sendPacket(winner, new SM_SYSTEM_MESSAGE(1390005, medalCount, new DescriptionId(medalId)));
							}
						}
					}
				//}
				if ((CustomConfig.ENABLE_TOLL_REWARDING) && (Rnd.get() * 100.0F < PvPRewardService.getTollRewardChance(winner, victim))) {
					int qt = PvPRewardService.getTollQuantity(winner, victim);
					InGameShopEn.getInstance().addToll(winner, qt);
					if (qt == 1)
						PacketSendUtility.sendBrightYellowMessage(winner, LanguageHandler.translate(CustomMessageId.PVP_TOLL_REWARD1) + qt + LanguageHandler.translate(CustomMessageId.PVP_TOLL_REWARD2));
					else {
						PacketSendUtility.sendBrightYellowMessage(winner, LanguageHandler.translate(CustomMessageId.PVP_TOLL_REWARD1) + qt + LanguageHandler.translate(CustomMessageId.PVP_TOLL_REWARD2));
					}
				}
				if (CustomConfig.GENOCIDE_SPECIAL_REWARDING != 0) {
					switch (CustomConfig.GENOCIDE_SPECIAL_REWARDING) {
						case 1:
							if ((winner.getSpreeLevel() <= 2) || (Rnd.get() * 100 >= CustomConfig.SPECIAL_REWARD_CHANCE))
								break;
							int abyssId = PvPRewardService.getRewardId(winner, victim, true);
							ItemService.addItem(winner, abyssId, 1L);
							log.info("[PvP][Advanced] {Player : " + winner.getName() + "} has won " + abyssId + " for killing {Player : " + victim.getName() + "}");
							break;
						default:
							if ((winner.getSpreeLevel() <= 2) || (Rnd.get() * 100 >= CustomConfig.SPECIAL_REWARD_CHANCE))
								break;
							ItemService.addItem(winner, CustomConfig.GENOCIDE_SPECIAL_REWARDING, 1L);
							log.info("[PvP][Advanced] {Player : " + winner.getName() + "} has won " + CustomConfig.GENOCIDE_SPECIAL_REWARDING + " for killing {Player : " + victim.getName() + "}");
							break;
					}
				}
			}
			else {
				PacketSendUtility.sendMessage(winner, LanguageHandler.translate(CustomMessageId.PVP_NO_REWARD1) + victim.getName() + LanguageHandler.translate(CustomMessageId.PVP_NO_REWARD2));
			}

		}
        // Keep track of how much damage was dealt by players
        // so we can remove AP based on player damage...
        int playerDamage = 0;
        boolean success;

        // Distribute AP to groups and players that had damage.
        for (AggroInfo aggro : victim.getAggroList().getFinalDamageList(true)) {
            success = false;
            if (aggro.getAttacker() instanceof Player) {
                success = rewardPlayer(victim, totalDamage, aggro);
            } else if (aggro.getAttacker() instanceof PlayerGroup) {
                success = rewardPlayerGroup(victim, totalDamage, aggro);
            } else if (aggro.getAttacker() instanceof PlayerAlliance) {
                success = rewardPlayerAlliance(victim, totalDamage, aggro);
            }

            // Add damage last, so we don't include damage from same race. (Duels, Arena)
            if (success) {
                playerDamage += aggro.getDamage();
            }
        }

        //notify Quest engine for winner + his group
        notifyKillQuests(winner, victim);

        // Apply lost AP to defeated player
        final int apLost = StatFunctions.calculatePvPApLost(victim, winner);
        final int apActuallyLost = apLost * playerDamage / totalDamage;

		if (apActuallyLost > 0) {
			AbyssPointsService.addAp(victim, -apActuallyLost);
            //AbyssPointsService.addGp(victim, -CustomConfig.GPL);
       // Custom Lost GP by Player Ranks 			
        int OFCR1 = AbyssRankEnum.STAR1_OFFICER.getId();
		int OFCR2 = AbyssRankEnum.STAR2_OFFICER.getId();
		int OFCR3 = AbyssRankEnum.STAR3_OFFICER.getId();
		int OFCR4 = AbyssRankEnum.STAR4_OFFICER.getId();
		int OFCR5 = AbyssRankEnum.STAR5_OFFICER.getId();
		int GNRL = AbyssRankEnum.GENERAL.getId();
		int G_GNRL = AbyssRankEnum.GREAT_GENERAL.getId();
		int CMNDR = AbyssRankEnum.COMMANDER.getId();
		int GVRNR = AbyssRankEnum.SUPREME_COMMANDER.getId();
		
		if (victim.getAbyssRank().getRank().getId() == OFCR1) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL);
		 }
		 if (victim.getAbyssRank().getRank().getId() == OFCR2) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL1);
		 }
		 if (victim.getAbyssRank().getRank().getId() == OFCR3) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL2);
		 }
		 if (victim.getAbyssRank().getRank().getId() == OFCR4) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL3);
		 }
		 if (victim.getAbyssRank().getRank().getId() == OFCR5) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL4);
		 }
		 if (victim.getAbyssRank().getRank().getId() == GNRL) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL5);
		 }
		 if (victim.getAbyssRank().getRank().getId() == G_GNRL) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL6);
		 }
		 if (victim.getAbyssRank().getRank().getId() == CMNDR) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL7);
		 }
	     if (victim.getAbyssRank().getRank().getId() == GVRNR) {
			AbyssPointsService.addGp(victim, -CustomConfig.GPL8);
			}
	    }
		PacketSendUtility.broadcastPacketAndReceive(victim, SM_SYSTEM_MESSAGE.STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(victim.getName(), winner.getName()));
		if (LoggingConfig.LOG_KILL)
			log.info("[KILL] Player [" + winner.getName() + "] killed [" + victim.getName() + "]");
    }

    /**
     * @param victim
     * @param totalDamage
     * @param aggro
     * @return true if group is not same race
     */
    private boolean rewardPlayerGroup(Player victim, int totalDamage, AggroInfo aggro) {
        // Reward Group
        PlayerGroup group = ((PlayerGroup) aggro.getAttacker());

        // Don't Reward Player of Same Faction.
        if (group.getRace() == victim.getRace()) {
            return false;
        }

        // Find group members in range
        List<Player> players = new ArrayList<>();

        // Find highest rank and level in local group
        int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
        int maxLevel = 0;

        for (Player member : group.getMembers()) {
            if (MathUtil.isIn3dRange(member, victim, GroupConfig.GROUP_MAX_DISTANCE)) {
                // Don't distribute AP to a dead player!
                if (!member.getLifeStats().isAlreadyDead()) {
                    players.add(member);
                    if (member.getLevel() > maxLevel) {
                        maxLevel = member.getLevel();
                    }
                    if (member.getAbyssRank().getRank().getId() > maxRank) {
                        maxRank = member.getAbyssRank().getRank().getId();
                    }
                }

                if (Zerg(member, victim) > CustomConfig.ANTI_ZERG_COUNT && !victim.isInGroup2()) {
                    PlayerGroup grp = member.getPlayerGroup2();
                    for (final Player members : grp.getMembers()) {
                        PacketSendUtility.sendWhiteMessageOnCenter(members, "Zerg system : No AP won ! Do not be more than "+ CustomConfig.ANTI_ZERG_COUNT +" of the same person!");
                        members.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
                        members.getEffectController().updatePlayerEffectIcons();
                        members.getEffectController().broadCastEffects();
                        ThreadPoolManager.getInstance().schedule(new Runnable() {


                            @Override
                            public void run() {
                                members.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                                members.getEffectController().updatePlayerEffectIcons();
                                members.getEffectController().broadCastEffects();
                            }

                        }, 13 * 1000);

                    }
                    return false;
                }
            }
        }

        // They are all dead or out of range.
        if (players.isEmpty()) {
            return false;
        }

        int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
        int baseXpReward = StatFunctions.calculatePvpXpGained(victim, maxRank, maxLevel);
        int baseDpReward = StatFunctions.calculatePvpDpGained(victim, maxRank, maxLevel);
        float groupPercentage = (float) aggro.getDamage() / totalDamage;
        int apRewardPerMember = Math.round(baseApReward * groupPercentage / players.size());
        int xpRewardPerMember = Math.round(baseXpReward * groupPercentage / players.size());
        int dpRewardPerMember = Math.round(baseDpReward * groupPercentage / players.size());

        for (Player member : players) {
            int memberApGain = 1;
            int memberXpGain = 1;
            int memberDpGain = 1;
            if (this.getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
                if (apRewardPerMember > 0) {
                    memberApGain = Math.round(RewardType.AP_PLAYER.calcReward(member, apRewardPerMember));
                }
                if (xpRewardPerMember > 0) {
                    memberXpGain = Math.round(xpRewardPerMember * member.getRates().getXpPlayerGainRate());
                }
                if (dpRewardPerMember > 0) {
                    memberDpGain = Math.round(StatFunctions.adjustPvpDpGained(dpRewardPerMember, victim.getLevel(), member.getLevel()) * member.getRates().getDpPlayerRate());
                }
				if (CustomConfig.ENABLE_KILLING_SPREE_SYSTEM) {
					Player luckyPlayer = players.get(Rnd.get(players.size()));
					ItemService.addItem(luckyPlayer, 186000147, 1);
					InGameShopEn.getInstance().addToll(luckyPlayer, 2);
				}
            }
            Player partner = member.findPartner();
            if (member.isMarried() && member.getPlayerGroup2().getMembers() == partner && member.getPlayerGroup2().getMembers().size() == 2) {
                AbyssPointsService.addAp(member, victim, memberApGain + (memberApGain * 20 / 100)); //20% more AP for weddings
            } else {
                AbyssPointsService.addAp(member, victim, memberApGain);
            }
			if (CustomConfig.GROUP_GP_KILL) {
			AbyssPointsService.addGp(member, 300);
			}
				if (CustomConfig.ENABLE_MEDAL_REWARDING) {
					PlayerClass playerClass = member.getPlayerClass();
					if(playerClass == PlayerClass.CLERIC) {
			        //if (Rnd.get() * 100 < PvPRewardService.getMedalRewardChance(member, victim)) {
				        int M_member = PvPRewardService.getRewardIdSupportMember(member, victim, false);
				        long MC_member = PvPRewardService.getRewardQuantitygp(member, victim);
				        ItemService.addItem(member, M_member, MC_member);
						if (member.getInventory().getItemCountByItemId(M_member) > 0) {
							if (MC_member == 1)
								PacketSendUtility.sendPacket(member, new SM_SYSTEM_MESSAGE(1390000, new DescriptionId(M_member)));
							else {
								PacketSendUtility.sendPacket(member, new SM_SYSTEM_MESSAGE(1390005, MC_member, new DescriptionId(M_member)));
							}
						}
			        //}
				  }
		        }
            member.getCommonData().addExp(memberXpGain, RewardType.PVP_KILL, victim.getName());
            member.getCommonData().addEventExp(memberXpGain);
            member.getCommonData().addDp(memberDpGain);
            this.addKillFor(member.getObjectId(), victim.getObjectId());
        }

        return true;
    }

    /**
     * @param victim
     * @param totalDamage
     * @param aggro
     * @return true if group is not same race
     */
    private boolean rewardPlayerAlliance(Player victim, int totalDamage, AggroInfo aggro) {
        // Reward Alliance
        PlayerAlliance alliance = ((PlayerAlliance) aggro.getAttacker());

        // Don't Reward Player of Same Faction.
        if (alliance.getLeaderObject().getRace() == victim.getRace()) {
            return false;
        }

        // Find group members in range
        List<Player> players = new ArrayList<>();

        // Find highest rank and level in local group
        int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
        int maxLevel = 0;

        for (Player member : alliance.getMembers()) {
            if (!member.isOnline()) {
                continue;
            }
            if (MathUtil.isIn3dRange(member, victim, GroupConfig.GROUP_MAX_DISTANCE)) {
                // Don't distribute AP to a dead player!
                if (!member.getLifeStats().isAlreadyDead()) {
                    players.add(member);
                    if (member.getLevel() > maxLevel) {
                        maxLevel = member.getLevel();
                    }
                    if (member.getAbyssRank().getRank().getId() > maxRank) {
                        maxRank = member.getAbyssRank().getRank().getId();
                    }
                }
            }
        }

        // They are all dead or out of range.
        if (players.isEmpty()) {
            return false;
        }

        int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
        int baseXpReward = StatFunctions.calculatePvpXpGained(victim, maxRank, maxLevel);
        int baseDpReward = StatFunctions.calculatePvpDpGained(victim, maxRank, maxLevel);
        float groupPercentage = (float) aggro.getDamage() / totalDamage;
        int apRewardPerMember = Math.round(baseApReward * groupPercentage / players.size());
        int xpRewardPerMember = Math.round(baseXpReward * groupPercentage / players.size());
        int dpRewardPerMember = Math.round(baseDpReward * groupPercentage / players.size());

        for (Player member : players) {
            int memberApGain = 1;
            int memberXpGain = 1;
            int memberDpGain = 1;
            if (this.getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
                if (apRewardPerMember > 0) {
                    memberApGain = Math.round(RewardType.AP_PLAYER.calcReward(member, apRewardPerMember));
                }
                if (xpRewardPerMember > 0) {
                    memberXpGain = Math.round(xpRewardPerMember * member.getRates().getXpPlayerGainRate());
                }
                if (dpRewardPerMember > 0) {
                    memberDpGain = Math.round(StatFunctions.adjustPvpDpGained(dpRewardPerMember, victim.getLevel(), member.getLevel()) * member.getRates().getDpPlayerRate());
                }
				if (CustomConfig.ENABLE_KILLING_SPREE_SYSTEM) {
					Player luckyPlayer = players.get(Rnd.get(players.size()));
					ItemService.addItem(luckyPlayer, 186000147, 1);//item for rewarded
					InGameShopEn.getInstance().addToll(luckyPlayer, 2);
				}
            }
            AbyssPointsService.addAp(member, victim, memberApGain);
			if (CustomConfig.GROUP_GP_KILL) {
			AbyssPointsService.addGp(member, victim, 300);
			}
            member.getCommonData().addExp(memberXpGain, RewardType.PVP_KILL, victim.getName());
            member.getCommonData().addEventExp(memberXpGain);
            member.getCommonData().addDp(memberDpGain);
            this.addKillFor(member.getObjectId(), victim.getObjectId());
        }

        return true;
    }

    /**
     * @param victim
     * @param totalDamage
     * @param aggro
     * @return true if player is not same race
     */
    private boolean rewardPlayer(Player victim, int totalDamage, AggroInfo aggro) {
        // Reward Player
        Player winner = ((Player) aggro.getAttacker());

        // Don't Reward Player out of range/dead/same faction
        if (winner.getRace() == victim.getRace() || !MathUtil.isIn3dRange(winner, victim, GroupConfig.GROUP_MAX_DISTANCE) || winner.getLifeStats().isAlreadyDead()) {
            return false;
        }

        int baseApReward = 1;
        int baseXpReward = 1;
        int baseDpReward = 1;

        if (this.getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
            baseApReward = StatFunctions.calculatePvpApGained(victim, winner.getAbyssRank().getRank().getId(), winner.getLevel());
            baseXpReward = StatFunctions.calculatePvpXpGained(victim, winner.getAbyssRank().getRank().getId(), winner.getLevel());
            baseDpReward = StatFunctions.calculatePvpDpGained(victim, winner.getAbyssRank().getRank().getId(), winner.getLevel());
			if (CustomConfig.ENABLE_KILLING_SPREE_SYSTEM) {
				PvPSpreeService.increaseRawKillCount(winner);
			}
        }

        int apPlayerReward = Math.round(baseApReward * aggro.getDamage() / totalDamage);
        apPlayerReward = (int) RewardType.AP_PLAYER.calcReward(winner, apPlayerReward);
        int xpPlayerReward = Math.round(baseXpReward * winner.getRates().getXpPlayerGainRate() * aggro.getDamage() / totalDamage);
        int dpPlayerReward = Math.round(baseDpReward * winner.getRates().getDpPlayerRate() * aggro.getDamage() / totalDamage);

        AbyssPointsService.addAp(winner, victim, apPlayerReward);
		//-------------------
		int G1 = AbyssRankEnum.GRADE1_SOLDIER.getId();
		int G2 = AbyssRankEnum.GRADE2_SOLDIER.getId();
		int G3 = AbyssRankEnum.GRADE3_SOLDIER.getId();
		int G4 = AbyssRankEnum.GRADE4_SOLDIER.getId();
		int G5 = AbyssRankEnum.GRADE5_SOLDIER.getId();
		int G6 = AbyssRankEnum.GRADE6_SOLDIER.getId();
		int G7 = AbyssRankEnum.GRADE7_SOLDIER.getId();
		int G8 = AbyssRankEnum.GRADE8_SOLDIER.getId();
		int G9 = AbyssRankEnum.GRADE9_SOLDIER.getId();
        int OFCR1 = AbyssRankEnum.STAR1_OFFICER.getId();
		int OFCR2 = AbyssRankEnum.STAR2_OFFICER.getId();
		int OFCR3 = AbyssRankEnum.STAR3_OFFICER.getId();
		int OFCR4 = AbyssRankEnum.STAR4_OFFICER.getId();
		int OFCR5 = AbyssRankEnum.STAR5_OFFICER.getId();
		int GNRL = AbyssRankEnum.GENERAL.getId();
		int G_GNRL = AbyssRankEnum.GREAT_GENERAL.getId();
		int CMNDR = AbyssRankEnum.COMMANDER.getId();
		int GVRNR = AbyssRankEnum.SUPREME_COMMANDER.getId();
		//---------------------
		if (victim.getAbyssRank().getRank().getId() == G1){
		    AbyssPointsService.addGp(winner, 101);
		}
		if (victim.getAbyssRank().getRank().getId() == G2){
		    AbyssPointsService.addGp(winner, 102);
		}
		if (victim.getAbyssRank().getRank().getId() == G3){
		    AbyssPointsService.addGp(winner, 103);
		}
		if (victim.getAbyssRank().getRank().getId() == G4){
		    AbyssPointsService.addGp(winner, 104);
		}
		if (victim.getAbyssRank().getRank().getId() == G5){
		    AbyssPointsService.addGp(winner, 105);
		}
		if (victim.getAbyssRank().getRank().getId() == G6){
		    AbyssPointsService.addGp(winner, 106);
		}
		if (victim.getAbyssRank().getRank().getId() == G7){
		    AbyssPointsService.addGp(winner, 107);
		}
		if (victim.getAbyssRank().getRank().getId() == G8){
		    AbyssPointsService.addGp(winner, 108);
		}
		if (victim.getAbyssRank().getRank().getId() == G9){
		    AbyssPointsService.addGp(winner, 109);
		}
		
		if (victim.getAbyssRank().getRank().getId() == OFCR1){
		    AbyssPointsService.addGp(winner, 300);
		}
		if (victim.getAbyssRank().getRank().getId() == OFCR2){
		    AbyssPointsService.addGp(winner, 300);
		}
		if (victim.getAbyssRank().getRank().getId() == OFCR3){
		    AbyssPointsService.addGp(winner, 300);
		}
		if (victim.getAbyssRank().getRank().getId() == OFCR4){
		    AbyssPointsService.addGp(winner, 300);
		}
		if (victim.getAbyssRank().getRank().getId() == OFCR5){
		    AbyssPointsService.addGp(winner, 300);
		}
		
		if (victim.getAbyssRank().getRank().getId() == GNRL){
		    AbyssPointsService.addGp(winner, 400);
		}
		if (victim.getAbyssRank().getRank().getId() == G_GNRL){
		    AbyssPointsService.addGp(winner, 500);
		}
		if (victim.getAbyssRank().getRank().getId() == CMNDR){
		    AbyssPointsService.addGp(winner, 600);
		}
		if (victim.getAbyssRank().getRank().getId() == GVRNR){
		    AbyssPointsService.addGp(winner, 700);
		}
		//------------------
        winner.getCommonData().addExp(xpPlayerReward, RewardType.PVP_KILL, victim.getName());
        winner.getCommonData().addEventExp(xpPlayerReward);
        winner.getCommonData().addDp(dpPlayerReward);
        this.addKillFor(winner.getObjectId(), victim.getObjectId());
        return true;
    }

    private void notifyKillQuests(Player winner, Player victim) {
        if (winner.getRace() == victim.getRace()) {
            return;
        }

        List<Player> rewarded = new ArrayList<>();
        int worldId = victim.getWorldId();

        if (winner.isInGroup2()) {
            rewarded.addAll(winner.getPlayerGroup2().getOnlineMembers());
        } else if (winner.isInAlliance2()) {
            rewarded.addAll(winner.getPlayerAllianceGroup2().getOnlineMembers());
        } else {
            rewarded.add(winner);
        }

        for (Player p : rewarded) {
            if (!MathUtil.isIn3dRange(p, victim, GroupConfig.GROUP_MAX_DISTANCE) || p.getLifeStats().isAlreadyDead()) {
                continue;
            }

            QuestEngine.getInstance().onKillInWorld(new QuestEnv(victim, p, 0, 0), worldId);
            QuestEngine.getInstance().onKillRanked(new QuestEnv(victim, p, 0, 0), victim.getAbyssRank().getRank());
        }
        rewarded.clear();
    }

    private int Zerg(Player winner, Player victim) {
        if(!CustomConfig.ANTI_ZERG_ENABLED){
            return 0;
        }

        antiZergMap = null;

        if(!Objects.equals(CustomConfig.ANTI_ZERG_MAP, "")) {
            try {

                Collections.addAll(antiZergMap, CustomConfig.ANTI_ZERG_MAP.split(","));

                if (!antiZergMap.contains(winner.getWorldId()) && !antiZergMap.contains(victim.getWorldId())) {
                    return 0;
                }

            }catch(Exception ex){
                log.error(ex.getMessage());
                return 0;
            }
        }

        int zergeurs = 0;

        for (AggroInfo ai : victim.getAggroList().getList()) {

            Creature master = ((Creature) ai.getAttacker()).getMaster();
            if (master instanceof Player) {
                Player player = (Player) master;
                if (player.isInGroup2()) {
                    if (player.isInSameTeam(winner)) {
                        zergeurs++;
                    }
                }
            }

        }
        return zergeurs;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final PvpService instance = new PvpService();
    }
}
