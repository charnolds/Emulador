package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.IdianStone;
import com.aionemu.gameserver.model.items.ManaStone;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author ATracer modified by Wakizashi
 * @rework FrozenKiller
 */
public abstract class ItemStoneListDAO implements DAO {

    /**
     * Loads stones of item
     *
     * @param items list of items to load stones
     */
    public abstract void load(Collection<Item> items);

    public abstract void storeManaStones(Set<ManaStone> manaStones);

    public abstract void storeFusionStones(Set<ManaStone> fusionStones);

    public abstract void storeIdianStones(IdianStone idianStone);

    /**
     * Saves stones of player
     *
     * @param player whos stones we need to save
     */
    public void save(Player player) {
        save(player.getAllItems());
    }

    public abstract void save(List<Item> items);

    @Override
    public String getClassName() {
        return ItemStoneListDAO.class.getName();
    }
}
