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
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.DataBaseConnection;

/**
 * FXML Controller class
 *
 * @author M.Ridvan Ozcan
 */
public class AdminController implements Initializable {

    DataBaseConnection DB = new DataBaseConnection();
    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;
    private ResultSet rs = null;
    private Statement st = null;
    private String pageName;
    private String pageTitle;

    @FXML
    private Button btn_addins, btn_addass, btn_addstu, btn_delins, btn_delass, btn_delstu;

    @FXML
    private TextField ins_un, ins_up, ins_n, ins_sn, ass_un, ass_p, ass_n, ass_sn,
            stu_un, stu_p, stu_n, stu_sn, stu_num, dl_ins, dl_ass, dll_stu;
    @FXML
    private TextField tb_assNum;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            DB.connect();
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Addins(ActionEvent e) throws SQLException {
            try {
                String ins = "INSERT INTO tbl_instructor(userName,pass,name,surName) VALUES('" + ins_un.getText() + "','" + ins_up.getText() + "',"
                        + "'" + ins_n.getText() + "','" + ins_sn.getText() + "')";
                DB.Insert(ins);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @FXML
    public void Addass(ActionEvent e) throws SQLException {
            try {
                String ins = "INSERT INTO tbl_assistant(userName,pass,name,surName,assId) VALUES('" + ass_un.getText() + "','" + ass_p.getText() + "',"
                        + "'" + ass_n.getText() + "','" + ass_sn.getText() + "','" + tb_assNum.getText() + "')";
                DB.Insert(ins);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    public void Addstu(ActionEvent e) throws SQLException {
            try {
                String ins = "INSERT INTO tbl_student(userName,pass,name,surName,studentId) VALUES('" + stu_un.getText() + "','" + stu_p.getText() + "',"
                        + "'" + stu_n.getText() + "','" + stu_sn.getText() + "','" + stu_num.getText() + "')";
                DB.Insert(ins);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    public void Dllins(ActionEvent e) throws SQLException {
        try {
            DB.Delete("tbl_instructor", dl_ins.getText());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Dllass(ActionEvent e) throws SQLException {
        try {
            DB.Delete("tbl_assistant", dl_ass.getText());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Dllstu(ActionEvent e) throws SQLException {
        try {
            DB.Delete("tbl_student", dll_stu.getText());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean Check(String table, String userN) throws SQLException {
        boolean log = false;
        conn = (Connection) DriverManager.getConnection(sql);
        Statement st = (Statement) conn.createStatement();
        try {
            ResultSet rs = st.executeQuery("SELECT id FROM '" + table + "' WHERE userName = '" + userN + "' ");
            while (rs.next()) {
                log = rs.getBoolean("id");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        conn.close();
        return log;
    }

    public void switchPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pageName));
        sample.Main.STAGE.setTitle(pageTitle);
        sample.Main.STAGE.setScene(new Scene(root));
        sample.Main.STAGE.show();
    }

    public void logout(ActionEvent e) throws IOException {
        pageTitle = "Login";
        pageName = "/sample/Login.fxml";
        switchPage();
    }
}
