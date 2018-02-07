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

import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.AbyssBossesConfig;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhserionService {

    private static final Logger log = LoggerFactory.getLogger("ABYSS_BOSS_LOG");

    /**
     * Balaurea race protector spawn schedule.
     */
    private static final String AHSERION_SPAWN_SCHEDULE = AbyssBossesConfig.AHSERION_SPAWN_SCHEDULE;

    private FastMap<Integer, VisibleObject> ahserionAbyssBoss = new FastMap<Integer, VisibleObject>();

    /**
     * Singleton that is loaded on the class initialization.
     */
    private static final AhserionService instance = new AhserionService();

    public static AhserionService getInstance() {
        return instance;
    }

    public void initAhserion() {
        if (!AbyssBossesConfig.AHSERION_ENABLE) {
            log.info("[BSunayaka_Service] disabled...");
        }
        else {
            log.info("[BSunayaka_Service] actived...");
            startAhserion();
        }
    }

    public void startAhserion() {
        // Transidium annex start
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (ahserionAbyssBoss.containsKey(219312) && ahserionAbyssBoss.get(219312).isSpawned()) {
                    log.warn("BSunayaka was already spawned...");
                } else {//AHSERION,PORTAL ELY, PORTAL ASMO
                 ahserionAbyssBoss.put(219312, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(600040000,219312, 759.0958f, 765.6816f, 1226.5004f, (byte) 0), 1));
                 ahserionAbyssBoss.put(283074, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(600040000, 283074, 754.84625f, 819.98828f, 1223.957f, (byte) 0), 1));
                 ahserionAbyssBoss.put(283076, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(600040000, 283076, 754.90234f, 712.99042f, 1223.957f, (byte) 0), 1));
                    log.info("BSunayaka spawned in the Tiamaranta Eye");
                    World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                        @Override
                        public void visit(Player player) {
                            //PacketSendUtility.sendBrightYellowMessage(player, "Ahserion has appeared in Transidium Annex");
							PacketSendUtility.sendSys2Message(player, "RaidBoss", "Berseker Sunayaka Ha aparecido en Tiamaranta Eye");
                        }
                    });
                    //Ahserion despawned after 1 hr if not killed
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            for (VisibleObject vo : ahserionAbyssBoss.values()) {
                                if (vo != null) {
                                    Npc npc = (Npc) vo;
                                    if (!npc.getLifeStats().isAlreadyDead()) {
                                        npc.getController().onDelete();
                                    }
                                }
                                ahserionAbyssBoss.clear();
                                log.info("BSunayaka dissapeared");
                                World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                                    @Override
                                    public void visit(Player player) {
                                        //PacketSendUtility.sendBrightYellowMessage(player, "Ahserion has Dissapeared");
										PacketSendUtility.sendSys2Message(player, "RaidBoss", "Berseker Sunayaka Ha Desaparecido en Tiamaranta Eye");
                                    }
                                });
                            }
                        }
                    }, 3600 * 1000);
                }
            }
        }, AHSERION_SPAWN_SCHEDULE);
    }
}