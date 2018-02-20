/*
 *   <Student Affairs Information System>
 *
 *   Copyright (C) <2017>  <M.Ridvan Ozcan>
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author M.Ridvan Ozcan
 */
public class DataBaseConnection  extends  org.sqlite.JDBC{

    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;

    /**
     * SQl connection part
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     */
    public void connect() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            conn = DriverManager.getConnection(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * SQL data inserting
     *
     * @param ins
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     */
    public void Insert(String ins) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        conn = (Connection) DriverManager.getConnection(sql);
        Statement st = (Statement) conn.createStatement();
        try {
            st.executeUpdate(ins);
        } catch (SQLException e) {
        }

        conn.close();
    }

    /**
     * SQL data deleting
     *
     * @param table
     * @param id
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     */
    public void Delete(String table,String id) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        conn = (Connection) DriverManager.getConnection(sql);
        Statement st = (Statement) conn.createStatement();
        try {
            st.executeUpdate("DELETE FROM '"+table+"' WHERE id = '"+id+"'");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        conn.close();
    }

    /**
     *
     * @param tcl
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void Update(String tcl) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        conn = (Connection) DriverManager.getConnection(sql);
        Statement st = (Statement) conn.createStatement();
        try {
            st.executeUpdate(tcl);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        conn.close();
    }



}
