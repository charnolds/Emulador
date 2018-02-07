/**
 * This file is part of Aion-Aanarchy <aion-anarchy.ddns.net>.
 *
 *  Aion-Aanarchy is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Aanarchy is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Aanarchy.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.league.League;
import com.aionemu.gameserver.services.BaseService;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;

import com.aionemu.gameserver.services.abyss.AbyssPointsService;
//import com.aionemu.gameserver.dao.BaseDAO;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.team2.TemporaryPlayerTeam;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.*;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;


/**
 *
 * @author Uriel
 */
@SuppressWarnings("rawtypes")
public class BossDeathListener extends OnDieEventCallback {

    private static final Logger log = LoggerFactory.getLogger(BossDeathListener.class);

    private final Base<?> base;

    public BossDeathListener(Base base) {
        this.base = base;
    }
	@Override
	public void onBeforeDie(AbstractAI obj) {
		Race race = null;
		Npc boss = base.getBoss();
		AionObject winner = base.getBoss().getAggroList().getMostDamage();
		if (winner instanceof Creature) {
			final Creature kill = (Creature) winner;
			//applyBaseBuff();
			giveBaseRewardsToPlayers((Player) kill); 
			if (kill.getRace().isPlayerRace()) {
				base.setRace(kill.getRace());
				race = kill.getRace();
			}
			announceCapture(null, kill);
		} else if (winner instanceof PlayerGroup) {
			final PlayerGroup team = (PlayerGroup) winner;
			//applyBaseBuff();
			giveBaseRewardsToPlayers((Player) winner);
			if (team.getRace().isPlayerRace()) {
				base.setRace(team.getRace());
				race = team.getRace();
			}
			announceCapture(team, null);
		} else {
		   base.setRace(Race.NPC);
		}
		BaseService.getInstance().capture(base.getId(), base.getRace());
	}
	
	/**@Override
	public void onBeforeDie(AbstractAI obj) {
		Race race = null;
		Npc boss = base.getBoss();
		AionObject winner = base.getBoss().getAggroList().getMostDamage();
		if (winner instanceof Creature) {
			final Creature kill = (Creature) winner;
			//applyBaseBuff();
			giveBaseRewardsToPlayers((Player) kill); 
			if (kill.getRace().isPlayerRace()) {
				base.setRace(kill.getRace());
				race = kill.getRace();
			}
			announceCapture(null, kill);
		} else if (winner instanceof TemporaryPlayerTeam) {
			final TemporaryPlayerTeam team = (TemporaryPlayerTeam) winner;
			//applyBaseBuff();
			//giveBaseRewardsToPlayers((Player) winner);
			if (team.getRace().isPlayerRace()) {
				base.setRace(team.getRace());
				race = team.getRace();
			}
			announceCapture(team, null);
		} else {
		   base.setRace(Race.NPC);
		}
		BaseService.getInstance().capture(base.getId(), base.getRace());
	}*/
	@Override
	public void onAfterDie(AbstractAI obj) {
	}
	public void announceCapture(final TemporaryPlayerTeam team, final Creature kill) {
        final String baseName = base.getBaseLocation().getName();
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                if (team != null && kill == null) {
					//%0 succeeded in conquering %1.
                    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1301039, team.getRace().getRaceDescriptionId(), baseName));
                } else {
					//%0 succeeded in conquering %1.
                    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1301039, kill.getRace().getRaceDescriptionId(), baseName));
                }
            }
        });
    }
	public void applyBaseBuff() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.getCommonData().getRace() == Race.ELYOS) {
				    SkillEngine.getInstance().applyEffectDirectly(12115, player, player, 0); //Kaisinel's Bane.
					//The power of Kaisinel's Protection surrounds you.
					PacketSendUtility.playerSendPacketTime(player, SM_SYSTEM_MESSAGE.STR_MSG_WEAK_RACE_BUFF_LIGHT_GAIN, 10000);
				} else if (player.getCommonData().getRace() == Race.ASMODIANS) {
				    SkillEngine.getInstance().applyEffectDirectly(12117, player, player, 0); //Marchutan's Bane.
					//The power of Marchutan's Protection surrounds you.
					PacketSendUtility.playerSendPacketTime(player, SM_SYSTEM_MESSAGE.STR_MSG_WEAK_RACE_BUFF_DARK_GAIN, 10000);
				}
			}
		});
	}
	protected void giveBaseRewardsToPlayers(Player player) {
		switch (player.getWorldId()) {
			case 210020000: //Eltnen.
			case 210040000: //Heiron.
			case 220020000: //Morheim.
			case 220040000: //Beluslan.
		        //HTMLService.sendGuideHtml(player, "adventurers_base1");
				ItemService.addItem(player, 186000242, 1);
				ItemService.addItem(player, 166030009, 1);
				AbyssPointsService.addGp(player, 200);
			break;
			case 600090000: //Kaldor.
			case 600100000: //Levinshor.
			case 600050000: //katalam
			case 600060000: //Danaria
		        //HTMLService.sendGuideHtml(player, "adventurers_base2");
				ItemService.addItem(player, 186000242, 1);
				ItemService.addItem(player, 166030009, 1);
				AbyssPointsService.addGp(player, 200);
			break;
			case 400020000: //Belus.
			case 400040000: //Aspida.
			case 400050000: //Atanatos.
			case 400060000: //Disillon.
		        //HTMLService.sendGuideHtml(player, "adventurers_base3");
				ItemService.addItem(player, 186000242, 1);
				ItemService.addItem(player, 166030009, 1);
				AbyssPointsService.addGp(player, 200);
			break;
		}
	}
}
