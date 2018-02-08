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
package instance;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.world.zone.ZoneInstance;

@InstanceID(301130000)
public class SauroSupplyBaseInstance extends GeneralInstanceHandler {
	
	Npc bossBrigadeGeneralSheba = null;
    Npc bossGuardCaptainAhuradim = null;
	Npc bossInterrogatingOfficerJadaraka = null;
	
    private Map<Integer, StaticDoor> doors;
    private boolean isInstanceDestroyed;
    private boolean isStartTimer4 = false;
	private boolean isStartTimer5 = false;
    private Future<?> cancelDeleteSpawn;
    private Future<?> CheckBuff;
    
    @Override
	public void onEnterInstance(final Player player) {
	    sendMsg(1402084);
	}

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
        int rnd = Rnd.get(1, 5);
		switch (rnd) {
		    case 1:
				spawn(230846, 464.07788f, 401.3575f, 182.15321f, (byte) 10);
			break;
			case 2:
				spawn(230846, 496.30792f, 412.814f, 182.13792f, (byte) 73);
			break;
			case 3:
				spawn(230846, 497.15717f, 392.34656f, 182.14955f, (byte) 75);
			break;
			case 4:
				spawn(230846, 496.2902f, 358.0765f, 182.14955f, (byte) 48);
			break;
			case 5:
				spawn(230846, 464.15985f, 389.7157f, 182.15321f, (byte) 109);
			break;
        }
		
		/**
		 * Secret Key Box:
		 * There are various secret key boxes placed inside the instance.
		 * Each box has a key that can be used to select which boss you will fight at the end of the instance.
		 * You do not need to get all the keys and there is a maximum of five keys you can get in your inventory.
		 * The location of each key changes each time.
		 */
		switch (Rnd.get(1, 7)) {
		    case 1:
			    spawn(230847, 263.3429f, 463.5408f, 156.70581f, (byte) 90);
			    spawn(230847, 322.1051f, 388.41913f, 159.1054f, (byte) 60);
			break;
			case 2:
			    spawn(230847, 653.2666f, 372.9493f, 204.14606f, (byte) 72);
			    spawn(230847, 498.09406f, 436.55927f, 182.11913f, (byte) 42);
			break;
			case 3:
			    spawn(230847, 498.09406f, 436.55927f, 182.11913f, (byte) 42);
			    spawn(230847, 257.0982f, 364.2677f, 159.13574f, (byte) 119);
			break;
			case 4:
				spawn(230847, 263.3429f, 463.5408f, 156.70581f, (byte) 90);
				spawn(230847, 257.0982f, 364.2677f, 159.13574f, (byte) 119);
			break;
			case 5:
				spawn(230847, 653.2666f, 372.9493f, 204.14606f, (byte) 72);
				spawn(230847, 322.7196f, 363.9511f, 159.13863f, (byte) 59);
			break;
			case 6:
				spawn(230847, 498.09406f, 436.55927f, 182.11913f, (byte) 42);
				spawn(230847, 322.1051f, 388.41913f, 159.1054f, (byte) 60);
			break;
			case 7:
				spawn(230847, 653.2666f, 372.9493f, 204.14606f, (byte) 72);
				spawn(230847, 262.72226f, 396.23676f, 156.83209f, (byte) 30);
			break;
		}
    }
    
    public void onDropRegistered(Npc npc) {
    	Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		int npcId = npc.getNpcId();
		switch (npcId) {
			case 230847: //Secret Key Box.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000179, 1)); //Danuar Stone Room Key.
			break;
		}
	}
    
    private void cancelTask()
	{
		if (cancelDeleteSpawn != null && !cancelDeleteSpawn.isCancelled())
		{
			cancelDeleteSpawn.cancel(true);
		}
		if (CheckBuff != null) {
			CheckBuff.cancel(false);
		}
	}

    @Override
    public void onDie(Npc npc) {
        int npcId = npc.getNpcId();
        switch (npcId) {

        	case 284455:
    		case 284457:
    		case 233285:
    		case 284435:
    			despawnNpc(npc);
    			break;
        	case 230849: //Guard Captain Rohuka
                sendMsg(1401914);
                doors.get(383).setOpen(true);
                break;

            case 230851: //Chief Cannoneer Kurmata
            	spawn(230797, 611.1872f, 452.91882f, 191.2776f, (byte) 39);
				spawn(230797, 610.7328f, 518.80884f, 191.2776f, (byte) 75);
                break;
            case 230797: //Sheban Legion Elite Ambusher
                sendMsg(1401916);
                doors.get(59).setOpen(true);
                break;

            case 230818: //Sheban Legion Elite Gunner.
                sendMsg(1401916);
                doors.get(372).setOpen(true);
                break;
            case 230850: //Research Teselik.
                sendMsg(1401917);
                doors.get(375).setOpen(true);
                spawn(284455, 489.2027f, 326.21017f, 182.02086f, (byte) 43);
				spawn(284457, 468.10175f, 326.5099f, 181.98676f, (byte) 15);
				spawn(284457, 469.7723f, 347.24435f, 181.78821f, (byte) 104);
				spawn(284457, 489.35037f, 347.90756f, 182.0165f, (byte) 76);
                break;

            case 233255: //Gate Sentry Slurt
                sendMsg(1401918);
                doors.get(378).setOpen(true);
                break;
            case 230852: //Supplies Commander Ranodim
                sendMsg(1401919);
                doors.get(388).setOpen(true);
                break;

            case 230790: // Sheban Legion Elite Assaulter
                sendMsg(1401920);
                doors.get(376).setOpen(true);
                break;
            case 230853: // Staff Commander Moriata
                sendMsg(1401921);
                spawn(730872, 129.16f, 432.33f, 153.33f, (byte) 0, 3); //portal to boss
                break;

            case 230857: // Guard Leader Achradim , 1 key boss
                spawn(801967, 689.85376f, 903.41785f, 411.45676f, (byte) 105);
                cancelTask();
                scheduleExit();
                Npc generator1 = getNpc(284446);
				Npc generator2 = getNpc(284445);
				Npc generator3 = getNpc(284437);
				despawnNpc(generator1);
				despawnNpc(generator2);
				despawnNpc(generator3);
                break;
            case 230858: // Brigadian General Sheba , 2 key boss
                spawn(801967, 886.4798f, 876.16693f, 411.45676f, (byte) 15); // exit portal
                spawn(702659, 897.20306f, 886.9926f, 411.57693f, (byte) 15); // Noble Abbey Box
                cancelTask();
                scheduleExit();
                break;
            case 284437:
				despawnNpc(npc);
				spawn(284437, 688.79f, 889.9065f, 411.45673f, (byte) 0);
				remove(bossGuardCaptainAhuradim);
				performSkillToTarget(284437, 284437, 21195);
				break;
			case 284445:
				despawnNpc(npc);
				spawn(284445, 709.76355f, 874.6016f, 411.32886f, (byte) 18);
				remove(bossGuardCaptainAhuradim);
				performSkillToTarget(284445, 284445, 21195);
				break;
			case 284446:
				despawnNpc(npc);
				spawn(284446, 709.9149f, 904.41016f, 411.32886f, (byte) 102);
				remove(bossGuardCaptainAhuradim);
				performSkillToTarget(284446, 284446, 21195);
				break;
        }
    }
    
    private void remove(Npc npc) {	
		npc.getEffectController().removeEffect(21191);
		npc.getEffectController().removeEffect(21195);
	}

    private void scheduleExit() {
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 180));
            }
        });
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                instance.doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        onExitInstance(player);
                    }
                });
                onInstanceDestroy();
            }
        }, 300000);
    }
    
    /**
	 * After you choose the level of difficulty, the portal will remain open for "5 mins" then disappear.
	 */
	@Override
    public void onEnterZone(Player player, ZoneInstance zone) {
		if (zone.getAreaTemplate().getZoneName().name().equals("NO_GLIDE_AREA_BOSS_4")) { //moveto 301130000 703.3685 889.8764 411.537
		    if (!isStartTimer4) {
			    isStartTimer4 = true;
			    System.currentTimeMillis();
			    PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 303));
			    bossGuardCaptainAhuradim = (Npc)spawn(230857, 703.3685f, 889.8764f, 411.537f, (byte) 60); //Guard Captain Ahuradim's.
			    performSkillToTarget(284446, 284446, 21195);
		        performSkillToTarget(284445, 284445, 21195);
		        performSkillToTarget(284437, 284437, 21195);
		        startCheck();
		        cancelDeleteSpawn = ThreadPoolManager.getInstance().schedule(new Runnable() {
				    @Override
				    public void run() {
					    sendMsg(1401813);
					    Npc PortalD = instance.getNpc(730876);
						despawnNpc(PortalD);
				        SkillEngine.getInstance().applyEffectDirectly(19049, bossGuardCaptainAhuradim, bossGuardCaptainAhuradim, 0); //Devour Soul.
				        ThreadPoolManager.getInstance().schedule(new Runnable() {
				        	@Override
				        	public void run() {
				        		despawnNpc(bossGuardCaptainAhuradim);
				        		ProtectiveSpawmFail();
						    }
					    }, 5 * 1000);
				    }
			    }, 300000);
		    }
        } else if (zone.getAreaTemplate().getZoneName().name().equals("NO_GLIDE_AREA_BOSS_5")) {
		    if (!isStartTimer5) {
			    isStartTimer5 = true;
			    System.currentTimeMillis();
			    PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 304));
			    ShebanSpawn2K();
			    bossBrigadeGeneralSheba = (Npc)spawn(230858, 899.7731f, 889.8521f, 411.5367f, (byte) 75); //Brigade General Sheba's.
			    cancelDeleteSpawn = ThreadPoolManager.getInstance().schedule(new Runnable() {
				    @Override
				    public void run() {
					    sendMsg(1401814);
						Npc PortalE = instance.getNpc(730877);
						despawnNpc(PortalE);
				        SkillEngine.getInstance().applyEffectDirectly(19049, bossBrigadeGeneralSheba, bossBrigadeGeneralSheba, 0); //Devour Soul.
				        
				        ThreadPoolManager.getInstance().schedule(new Runnable() {
				        	@Override
				        	public void run() {
				        		despawnNpc(bossBrigadeGeneralSheba);
				        		spawn(801967, 900.43726f, 889.94135f, 411.5362f, (byte) 75);// Exit
						    }
					    }, 5 * 1000);
				        
				    }
			    }, 300000);
		    }
        }
	}
	
	private void startCheck(){   
		if (CheckBuff == null){			
				CheckBuff = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {					
					Check();					
				}
			}, 40*1000*1, 40*1000*1);
		}	
    }
	
	private void Check(){
    	Npc generator1 = getNpc(284446);
		Npc generator2 = getNpc(284445);
		Npc generator3 = getNpc(284437);
		if ((generator1 != null && generator1.getEffectController().hasAbnormalEffect(21195)) &&
			(generator2 != null && generator2.getEffectController().hasAbnormalEffect(21195)) &&
			(generator3 != null && generator3.getEffectController().hasAbnormalEffect(21195))){
			SkillEngine.getInstance().getSkill(bossGuardCaptainAhuradim, 21191, 1, bossGuardCaptainAhuradim).useNoAnimationSkill();
			switch ((int)Rnd.get(1, 3)) {
			case 1:
				remove(generator1);
				break;
			case 2:
				remove(generator2);
				break;
			case 3:
				remove(generator3);
				break;
			}
		}
    }

    @Override
    public void onExitInstance(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }
    
    private void despawnNpc(Npc npc) {
		if (npc != null) {
			npc.getController().onDelete();
		}
	}
    
    private void performSkillToTarget(int npcId, int targetId, int skillId) {	 
		 final Npc npc = instance.getNpc(npcId);
		 final Npc target = instance.getNpc(targetId);
		 SkillEngine.getInstance().getSkill(npc, skillId, 60, target).useSkill();
	}
    
    public void ProtectiveSpawmFail() {
		spawn(284431, 696.70435f, 883.09656f, 411.53317f, (byte) 77);
		spawn(284431, 710.0879f, 883.04913f, 411.53317f, (byte) 105);
		spawn(284431, 709.8998f, 896.1839f, 411.53317f, (byte) 16);
		spawn(284431, 703.71747f, 898.9598f, 411.45676f, (byte) 33);
		spawn(284431, 703.50714f, 881.19073f, 411.45676f, (byte) 89);
	}
    
    public void ShebanSpawn2K() {
		spawn(233286, 906.23456f, 896.0439f, 411.53317f, (byte) 15);
		spawn(233286, 906.2929f, 883.62164f, 411.53317f, (byte) 105);
		spawn(233286, 893.88257f, 883.90594f, 411.53317f, (byte) 75);
	}

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }

    @Override
    public void onInstanceDestroy() {
        doors.clear();
        isInstanceDestroyed = true;
    }
}
