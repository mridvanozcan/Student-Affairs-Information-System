/*   
 *   <Student Affairs Information System>  
 *
 *   Copyright (C) <2017>  <M.Ridvan Ozcan>
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, ors
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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sample.DataBaseConnection;

/**
 *
 * @author M.Ridvan Ozcan
 */
public class LoginController implements Initializable {

    DataBaseConnection DB = new DataBaseConnection();
    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;
    private String pageName;
    private String pageTitle;

    @FXML
    private Button Btn_login;

    @FXML
    private PasswordField Fld_Pass;
    @FXML
    private TextField Fld_Uname;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DB.connect();
            // TODO
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Problematic part
    public void switchPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(pageName));
        sample.Main.STAGE.setTitle(pageTitle);
        sample.Main.STAGE.setScene(new Scene(root));
        sample.Main.STAGE.show();
    }

    @FXML
    public void btnlogin(ActionEvent e) {
        try {
            boolean log=false;
            try {
                log = loginer("tbl_student", Fld_Uname.getText(),Fld_Pass.getText());
                if (log) {
                    pageTitle = "Student";
                    pageName = "/sample/Student.fxml";
                }
                log = loginer("tbl_assistant", Fld_Uname.getText(), Fld_Pass.getText());
                if (log) {
                    pageTitle = "Assistant";
                    pageName = "/sample/Assistant.fxml";
                }
                log = loginer("tbl_admin", Fld_Uname.getText(), Fld_Pass.getText());
                if (log) {
                    pageTitle = "Admin";
                    pageName = "/sample/Admin.fxml";
                }
                log = loginer("tbl_instructor", Fld_Uname.getText(), Fld_Pass.getText());
                if (log) {
                    pageTitle = "Instructor";
                    pageName = "/sample/Instructor.fxml";
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            switchPage();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean loginer(String table, String userN, String pass) throws SQLException {
        boolean log = false;

        conn = (Connection) DriverManager.getConnection(sql);
        Statement st = (Statement) conn.createStatement();
        try {
            ResultSet rs = st.executeQuery("SELECT id FROM '" + table + "' WHERE userName = '" + userN + "' AND pass = '" + pass + "' ");
            while (rs.next()) {
                log = rs.getBoolean("id");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        conn.close();
        return log;
    }
}
