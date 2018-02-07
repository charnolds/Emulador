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
 */
package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.DropListDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.model.drop.Drop;
import com.aionemu.gameserver.model.drop.DropList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dr2co
 * 
 */
public class MySQL5DropListDAO extends DropListDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5DropListDAO.class);

    @Override
    public DropList load() {
        final DropList dropList = new DropList();
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection(); con.setReadOnly(false);
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM `droplist`");

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int mobId = resultSet.getInt("mobId");
                int itemId = resultSet.getInt("itemId");
                int minAmount = resultSet.getInt("min");
                int maxAmount = resultSet.getInt("max");
                float chance = resultSet.getFloat("chance");

                if (chance > 0) {
                    Drop dropTemplate = new Drop(mobId, itemId, minAmount, maxAmount, chance);
                    dropList.addDropTemplate(mobId, dropTemplate);
                }
            }

            resultSet.close();
            stmt.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            DatabaseFactory.close(con);
        }

        return dropList;
    }

    /** 
     * {@inheritDoc} 
     */
    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
