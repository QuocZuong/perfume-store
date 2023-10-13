package Lib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class DatabaseUtils {

    public static int getLastIndentityOf(String tableName) {
        ResultSet rs;
        String sql = "SELECT IDENT_CURRENT(?) as LastID";
        try {
            PreparedStatement ps = DB.DBContext.getConnection().prepareStatement(sql);
            ps.setNString(1, tableName);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("LastID");
            }

        } catch (SQLException sQLException) {
        }

        return -1;
    }

}
