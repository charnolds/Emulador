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
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class CustomConfig
{
	/**
	 * Droplist Master Data Source
	 */
  @Property(key = "gameserver.droplist.master.source", defaultValue = "sql")
  public static String GAMESERVER_DROPLIST_MASTER_SOURCE;
  @Property(key="gameserver.webshop.enable", defaultValue="false")
  public static boolean WEBSHOP_ENABLED;
  @Property(key="gameserver.kinah.cap.value", defaultValue="999999999")
  public static long KINAH_CAP_VALUE;
  @Property(key="gameserver.instances.mob.aggro", defaultValue="300080000,300090000,300060000")
  public static String INSTANCES_MOB_AGGRO;
  @Property(key="gameserver.pvp.toll.reward.chance", defaultValue="50")
  public static int TOLL_CHANCE;
  @Property(key="gameserver.stats.maxphysicalaccuracy", defaultValue="3600")
  public static int MAX_PHYSICAL_ACCURACY;
  @Property(key="gameserver.pvp.toll.rewarding.enable", defaultValue="false")
  public static boolean ENABLE_TOLL_REWARD;
  @Property(key="gameserver.kills.needed1", defaultValue="5")
  public static int KILLS_NEEDED1;
  @Property(key="gameserver.kill.reward.enable", defaultValue="false")
  public static boolean ENABLE_KILL_REWARD;
  @Property(key="gameserver.dispute.time", defaultValue="5")
  public static int DISPUTE_LAND_TIME;
  @Property(key="gameserver.pvp.dayduration", defaultValue="86400000")
  public static long PVP_DAY_DURATION;
  @Property(key="gameserver.vortex.duration", defaultValue="1")
  public static int VORTEX_DURATION;
  @Property(key="gameserver.rift.duration", defaultValue="1")
  public static int RIFT_DURATION;
  @Property(key="gameserver.item.reward1", defaultValue="186000031")
  public static int REWARD1;
  @Property(key="gameserver.worldchannel.costs", defaultValue="50000")
  public static int WORLD_CHANNEL_AP_COSTS;
  @Property(key="gameserver.basic.cubesize.limit", defaultValue="9")
  public static int BASIC_CUBE_SIZE_LIMIT;
  @Property(key="gameserver.commands.admin.dot.enable", defaultValue="false")
  public static boolean ENABLE_ADMIN_DOT_COMMANDS;
  @Property(key="gameserver.serialkiller.1st.rank.kills", defaultValue="25")
  public static int KILLER_1ST_RANK_KILLS;
  @Property(key="gameserver.ap.cap.value", defaultValue="1000000")
  public static long AP_CAP_VALUE;
  @Property(key="gameserver.pvp.toll.reward.quantity", defaultValue="5")
  public static int TOLL_QUANTITY;
  @Property(key="gameserver.kisk.restriction.enable", defaultValue="true")
  public static boolean ENABLE_KISK_RESTRICTION;
  @Property(key="gameserver.enable.gp.cap", defaultValue="false")
  public static boolean ENABLE_GP_CAP;
  @Property(key="gameserver.oldnames.command.disable", defaultValue="true")
  public static boolean OLD_NAMES_COMMAND_DISABLED;
  @Property(key="gameserver.kills.needed2", defaultValue="10")
  public static int KILLS_NEEDED2;
  @Property(key="gameserver.reward.service.enable", defaultValue="false")
  public static boolean ENABLE_REWARD_SERVICE;
  @Property(key="gameserver.item.reward4", defaultValue="186000147")
  public static int REWARD4;
  @Property(key="gameserver.dispute.enable", defaultValue="true")
  public static boolean DISPUTE_ENABLED;
  @Property(key="gameserver.serialkiller.kills.decrease", defaultValue="1")
  public static int SERIALKILLER_DECREASE;
  @Property(key="gameserver.enable.kinah.cap", defaultValue="false")
  public static boolean ENABLE_KINAH_CAP;
  @Property(key="gameserver.stats.maxboostmagicalskill", defaultValue="4500")
  public static int MAX_BOOST_MAGICAL_SKILL;
  @Property(key="gameserver.ffa.enable", defaultValue="false")
  public static boolean FFA_ENABLE;
  @Property(key="gameserver.stats.maxflyspeed", defaultValue="16000")
  public static int MAX_FLY_SPEED;
  @Property(key="gameserver.instances.enable", defaultValue="true")
  public static boolean ENABLE_INSTANCES;
  @Property(key="gameserver.instance.duel.enable", defaultValue="true")
  public static boolean INSTANCE_DUEL_ENABLE;
  @Property(key="gameserver.limits.enable", defaultValue="true")
  public static boolean LIMITS_ENABLED;
  @Property(key="gameserver.search.player.level", defaultValue="10")
  public static int LEVEL_TO_SEARCH;
  @Property(key="gameserver.search.factions.mode", defaultValue="false")
  public static boolean FACTIONS_SEARCH_MODE;
  @Property(key="gameserver.chat.factions.enable", defaultValue="false")
  public static boolean SPEAKING_BETWEEN_FACTIONS;
  @Property(key="gameserver.hotspot.teleport.cooldown.delay", defaultValue="600")
  public static int HOTSPOT_TELEPORT_COOLDOWN_DELAY;
  @Property(key="gameserver.premium.notify", defaultValue="false")
  public static boolean PREMIUM_NOTIFY;
  @Property(key="gameserver.stats.maxphysicalcritical", defaultValue="2500")
  public static int MAX_PHYSICAL_CRITICAL;
  @Property(key="gameserver.faction.cmdchannel", defaultValue="true")
  public static boolean FACTION_CMD_CHANNEL;
  @Property(key="gameserver.pvp.maxkills", defaultValue="5")
  public static int MAX_DAILY_PVP_KILLS;
  @Property(key="gameserver.skill.chain.triggerrate", defaultValue="true")
  public static boolean SKILL_CHAIN_TRIGGERRATE;
  @Property(key="gameserver.serialkiller.handledworlds", defaultValue="")
  public static String SERIALKILLER_WORLDS;
  @Property(key="gameserver.dispute.random3.schedule", defaultValue="0 0 2 ? * *")
  public static String DISPUTE_RND3_SCHEDULE;
  @Property(key="gameserver.enable.exp.cap", defaultValue="false")
  public static boolean ENABLE_EXP_CAP;
  @Property(key="gameserver.simple.secondclass.enable", defaultValue="false")
  public static boolean ENABLE_SIMPLE_2NDCLASS;
  @Property(key="gameserver.enable.ap.cap", defaultValue="false")
  public static boolean ENABLE_AP_CAP;
  @Property(key="gameserver.serialkiller.level.diff", defaultValue="10")
  public static int SERIALKILLER_LEVEL_DIFF;
  @Property(key="gameserver.unstuck.delay", defaultValue="3600")
  public static int UNSTUCK_DELAY;
  @Property(key="gameserver.exp.cap.value", defaultValue="48000000")
  public static long EXP_CAP_VALUE;
  @Property(key="gameserver.serialkiller.kills.refresh", defaultValue="5")
  public static int SERIALKILLER_REFRESH;
  @Property(key="gameserver.admin.dye.price", defaultValue="1000000")
  public static int DYE_PRICE;
  @Property(key="gameserver.dispute.random.schedule", defaultValue="0 0 11 ? * *")
  public static String DISPUTE_RND_SCHEDULE;
  @Property(key="gameserver.dispute.fixed.schedule", defaultValue="0 0 16 ? * *")
  public static String DISPUTE_FXD_SCHEDULE;
  @Property(key="gameserver.hotspot.teleport.delay", defaultValue="10000")
  public static int HOTSPOT_TELEPORT_DELAY;
  @Property(key="gameserver.instances.cooldown.rate", defaultValue="1")
  public static int INSTANCES_RATE;
  @Property(key="gameserver.noap.mentor.group", defaultValue="false")
  public static boolean MENTOR_GROUP_AP;
  @Property(key="gameserver.dispute.random.chance", defaultValue="50")
  public static int DISPUTE_RND_CHANCE;
  @Property(key="gameserver.base.flytime", defaultValue="60")
  public static int BASE_FLYTIME;
  @Property(key="gameserver.chat.text.length", defaultValue="150")
  public static int MAX_CHAT_TEXT_LENGHT;
  @Property(key="gameserver.ride.restriction.enable", defaultValue="true")
  public static boolean ENABLE_RIDE_RESTRICTION;
  @Property(key="gameserver.item.reward2", defaultValue="186000030")
  public static int REWARD2;
  @Property(key="gameserver.oldnames.coupon.disable", defaultValue="false")
  public static boolean OLD_NAMES_COUPON_DISABLED;
  @Property(key="gameserver.cross.faction.binding", defaultValue="false")
  public static boolean ENABLE_CROSS_FACTION_BINDING;
  @Property(key="gameserver.friendlist.size", defaultValue="90")
  public static int FRIENDLIST_SIZE;
  @Property(key="gameserver.dialog.showid", defaultValue="true")
  public static boolean ENABLE_SHOW_DIALOGID;
  @Property(key="gameserver.vortex.brusthonin.schedule", defaultValue="0 0 16 ? * SAT")
  public static String VORTEX_THEOBOMOS_SCHEDULE;
  @Property(key="gameserver.rift.enable", defaultValue="true")
  public static boolean RIFT_ENABLED;
  @Property(key="gameserver.enchant.announce.enable", defaultValue="true")
  public static boolean ENABLE_ENCHANT_ANNOUNCE;
  @Property(key="gameserver.stats.maxmagicalaccuracy", defaultValue="3600")
  public static int MAX_MAGICAL_ACCURACY;
  @Property(key="gameserver.basic.questsize.limit", defaultValue="40")
  public static int BASIC_QUEST_SIZE_LIMIT;
  @Property(key="gameserver.challenge.tasks.enabled", defaultValue="false")
  public static boolean CHALLENGE_TASKS_ENABLED;
  @Property(key="gameserver.dispute.random4.schedule", defaultValue="0 0 7 ? * *")
  public static String DISPUTE_RND4_SCHEDULE;
  @Property(key="gameserver.enchant.bonus.enabled", defaultValue="false")
  public static boolean ENABLE_ENCHANT_BONUS;
  @Property(key="gameserver.quest.questdatakills", defaultValue="true")
  public static boolean QUESTDATA_MONSTER_KILLS;
  @Property(key="gameserver.fatigue.system.enabled", defaultValue="false")
  public static boolean FATIGUE_SYSTEM_ENABLED;
  @Property(key="gameserver.serialkiller.enable", defaultValue="true")
  public static boolean SERIALKILLER_ENABLED;
  @Property(key="gameserver.vortex.enable", defaultValue="true")
  public static boolean VORTEX_ENABLED;
  @Property(key="gameserver.npcexpands.limit", defaultValue="5")
  public static int NPC_CUBE_EXPANDS_SIZE_LIMIT;
  @Property(key="gameserver.search.gm.list", defaultValue="false")
  public static boolean SEARCH_GM_LIST;
  @Property(key="gameserver.stats.maxspeed", defaultValue="12000")
  public static int MAX_SPEED;
  @Property(key="gameserver.pve.tag", defaultValue="⛨ %s")
  public static String TAG_PVE;
  @Property(key="gameserver.instances.cooldown.filter", defaultValue="0")
  public static String INSTANCES_COOL_DOWN_FILTER;
  @Property(key="gameserver.chat.whisper.level", defaultValue="10")
  public static int LEVEL_TO_WHISPER;
  @Property(key="gameserver.dispute.random2.schedule", defaultValue="0 0 21 ? * *")
  public static String DISPUTE_RND2_SCHEDULE;
  @Property(key="gameserver.pk.tag", defaultValue="☠ %s")
  public static String TAG_PK;
  @Property(key="gameserver.gp.cap.value", defaultValue="1000000")
  public static long GP_CAP_VALUE;
  @Property(key="gameserver.kills.needed3", defaultValue="15")
  public static int KILLS_NEEDED3;
  @Property(key="gameserver.dispute.weekend.random.chance", defaultValue="75")
  public static int DISPUTE_WEEKEND_RND_CHANCE;
  @Property(key="gameserver.faction.free", defaultValue="true")
  public static boolean FACTION_FREE_USE;
  @Property(key="gameserver.item.reward3", defaultValue="186000096")
  public static int REWARD3;
  @Property(key="gameserver.serialkiller.2nd.rank.kills", defaultValue="50")
  public static int KILLER_2ND_RANK_KILLS;
  @Property(key="gameserver.vortex.theobomos.schedule", defaultValue="0 0 16 ? * SUN")
  public static String VORTEX_BRUSTHONIN_SCHEDULE;
  @Property(key="gameserver.abyssxform.afterlogout", defaultValue="false")
  public static boolean ABYSSXFORM_LOGOUT;
  @Property(key="gameserver.limits.update", defaultValue="0 0 0 * * ?")
  public static String LIMITS_UPDATE;
  @Property(key="gameserver.faction.prices", defaultValue="10000")
  public static int FACTION_USE_PRICE;
  @Property(key="gameserver.faction.chatchannels", defaultValue="false")
  public static boolean FACTION_CHAT_CHANNEL;
  
 	/**
	 *#################################################################
	 ##################################################################
	 pvp spree system
	 #################################################################
	 */
	@Property(key = "gameserver.pvp.chainkill.time.restriction", defaultValue = "0")
	public static int CHAIN_KILL_TIME_RESTRICTION;

	@Property(key = "gameserver.pvp.chainkill.number.restriction", defaultValue = "30")
	public static int CHAIN_KILL_NUMBER_RESTRICTION;

	@Property(key = "gameserver.pvp.max.leveldiff.restriction", defaultValue = "9")
	public static int MAX_AUTHORIZED_LEVEL_DIFF;

	@Property(key = "gameserver.pvp.medal.rewarding.enable", defaultValue = "false")
	public static boolean ENABLE_MEDAL_REWARDING;

	@Property(key = "gameserver.pvp.medal.reward.chance", defaultValue = "75")
	public static float MEDAL_REWARD_CHANCE;

	@Property(key = "gameserver.pvp.medal.reward.quantity", defaultValue = "1")
	public static int MEDAL_REWARD_QUANTITY;

	@Property(key = "gameserver.pvp.toll.rewarding.enable", defaultValue = "false")
	public static boolean ENABLE_TOLL_REWARDING;

	@Property(key = "gameserver.pvp.toll.reward.chance", defaultValue = "50")
	public static float TOLL_REWARD_CHANCE;

	@Property(key = "gameserver.pvp.toll.reward.quantity", defaultValue = "1")
	public static int TOLL_REWARD_QUANTITY;

	@Property(key = "gameserver.pvp.killingspree.enable", defaultValue = "false")
	public static boolean ENABLE_KILLING_SPREE_SYSTEM;

	@Property(key = "gameserver.pvp.raw.killcount.spree", defaultValue = "20")
	public static int SPREE_KILL_COUNT;

	@Property(key = "gameserver.pvp.raw.killcount.rampage", defaultValue = "35")
	public static int RAMPAGE_KILL_COUNT;

	@Property(key = "gameserver.pvp.raw.killcount.genocide", defaultValue = "50")
	public static int GENOCIDE_KILL_COUNT;

	@Property(key = "gameserver.pvp.special_reward.type", defaultValue = "0")
	public static int GENOCIDE_SPECIAL_REWARDING;

	@Property(key = "gameserver.pvp.special_reward.chance", defaultValue = "2")
	public static float SPECIAL_REWARD_CHANCE;

    
    /**
	 * Advanced PvP System
	 */
    @Property(key = "gameserver.pvp.adv.enable", defaultValue = "true")
	public static boolean ADVANCED_PVP_SYSTEM;

    @Property(key = "gameserver.pvp.special.adv.ap", defaultValue = "1000")
	public static int ADVANCED_AP_REWARD;
	
    @Property(key = "gameserver.pvp.special.adv.gp1", defaultValue = "10")
	public static int ADVANCED_GP_REWARD_SPREE1;
	
    @Property(key = "gameserver.pvp.special.adv.gp2", defaultValue = "20")
	public static int ADVANCED_GP_REWARD_SPREE2;
	
	    @Property(key = "gameserver.pvp.special.adv.gp3", defaultValue = "30")
	public static int ADVANCED_GP_REWARD_SPREE3;
	
	@Property(key = "gameserver.pvp.special.adv.gpl", defaultValue = "50")
	public static int GPL;
	@Property(key = "gameserver.pvp.special.adv.gpl1", defaultValue = "50")
	public static int GPL1;
	@Property(key = "gameserver.pvp.special.adv.gpl2", defaultValue = "50")
	public static int GPL2;
	@Property(key = "gameserver.pvp.special.adv.gpl3", defaultValue = "50")
	public static int GPL3;
	@Property(key = "gameserver.pvp.special.adv.gpl4", defaultValue = "50")
	public static int GPL4;
	@Property(key = "gameserver.pvp.special.adv.gpl5", defaultValue = "50")
	public static int GPL5;
	@Property(key = "gameserver.pvp.special.adv.gpl6", defaultValue = "50")
	public static int GPL6;
	@Property(key = "gameserver.pvp.special.adv.gpl7", defaultValue = "50")
	public static int GPL7;
	@Property(key = "gameserver.pvp.special.adv.gpl8", defaultValue = "50")
	public static int GPL8;
	
	@Property(key = "gameserver.pvp.special.adv.gpkillreward", defaultValue = "25")
	public static int GP_KILL_REWARD;
	
	@Property(key = "gameserver.pvp.special.adv.groupgpkill.enable", defaultValue = "true")
	public static boolean GROUP_GP_KILL;

    @Property(key = "gameserver.pvp.special.adv.reward", defaultValue = "186000147")
    public static int ADVANCED_ITEM_REWARD;

    @Property(key = "gameserver.pvp.special.adv.count", defaultValue = "1")
    public static int ADVANCED_ITEM_COUNT;
	//Base Rewards
	@Property(key = "gameserver.base.rewards.enable", defaultValue = "true")
	public static boolean ENABLE_BASE_REWARDS;
    /**
     * Anti-Zerg system
     */
    @Property(key = "gameserver.antizerg.enable", defaultValue = "false")
    public static boolean ANTI_ZERG_ENABLED;
    @Property(key = "gameserver.antizerg.map", defaultValue = "")
    public static String ANTI_ZERG_MAP;
    @Property(key = "gameserver.antizerg.playercount", defaultValue = "3")
    public static int ANTI_ZERG_COUNT;
	@Property(key = "gameserver.magic_resist", defaultValue = "true")
	public static boolean MAGIC_RESIST_50;
}
