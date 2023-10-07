/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import java.sql.Connection;
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
            PreparedStatement ps = DB.DataManager.getConnection().prepareStatement(sql);
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
