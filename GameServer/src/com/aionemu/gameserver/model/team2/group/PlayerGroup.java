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
package com.aionemu.gameserver.model.team2.group;

import com.aionemu.gameserver.model.team2.*;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import javolution.util.FastMap;
import java.util.Collection;

/**
 * @author ATracer
 */
public class PlayerGroup extends TemporaryPlayerTeam<PlayerGroupMember> {

    private TeamType type;
	private int bgIndex = -1;
	private int killCount = 0;
    private final PlayerGroupStats playerGroupStats;
    //private FastMap<Integer, Player> groupMembers = new FastMap<Integer, Player>().shared();

    public PlayerGroup(PlayerGroupMember leader, TeamType type) {
        super(IDFactory.getInstance().nextId());
        this.playerGroupStats = new PlayerGroupStats(this);
        this.type = type;
        initializeTeam(leader);
    }

    @Override
    public void addMember(PlayerGroupMember member) {
        super.addMember(member);
        playerGroupStats.onAddPlayer(member);
        member.getObject().setPlayerGroup2(this);
    }

    @Override
    public void removeMember(PlayerGroupMember member) {
        super.removeMember(member);
        playerGroupStats.onRemovePlayer(member);
        member.getObject().setPlayerGroup2(null);
    }

    @Override
    public boolean isFull() {
        return size() == 6;
    }

    @Override
    public int getMinExpPlayerLevel() {
        return playerGroupStats.getMinExpPlayerLevel();
    }

    @Override
    public int getMaxExpPlayerLevel() {
        return playerGroupStats.getMaxExpPlayerLevel();
    }

    public TeamType getTeamType() {
        return type;
    }
    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setBgIndex(int bgIndex) {
        this.bgIndex = bgIndex;
    }

    public int getBgIndex() {
        return bgIndex;
    }
    /*public Collection<Integer> getMemberObjIds() {
        return groupMembers.keySet();
    }*/
    public int getGroupId() {
        return this.getObjectId();
    }
}
