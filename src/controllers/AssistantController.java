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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DataBaseConnection;

/**
 * FXML Controller class
 *
 * @author M.Ridvan Ozcan
 */
public class AssistantController implements Initializable {

    DataBaseConnection DB = new DataBaseConnection();
    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;
    private ResultSet rs = null;
    private Statement st = null;
    private ResultSet rs2 = null;
    private Statement st2 = null;
    private ResultSet rs3 = null;
    private Statement st3 = null;
    private String ass_id;
    private ObservableList<AssNote> data;
    private String pageName;
    private String pageTitle;

    @FXML
    private TableView<AssNote> tbl_note;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_sname;
    @FXML
    private TableColumn<?, ?> col_note;
    @FXML
    private TableColumn<?, ?> col_courseName;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_surname;
    @FXML
    private Label lb_username;
    @FXML
    private Label lb_id;
    @FXML
    private TextField tx_id;
    @FXML
    private Button btn_ok;
    @FXML
    private TextField tx_note;
    @FXML
    private Button btn_save;
    @FXML
    private TextField tx_idn;
    @FXML
    private Button btn_logout;
    @FXML
    private TableColumn<?, ?> col_info;
    @FXML
    private TextField tx_infobox;

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
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AssistantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = FXCollections.observableArrayList();
        noteCell();
    }

    @FXML
    public void callinfo(ActionEvent e) {

        try {

            conn = (Connection) DriverManager.getConnection(sql);
            st = (Statement) conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tbl_assistant WHERE assId = '" + tx_id.getText() + "' ");
            while (rs.next()) {
                ass_id = "" + rs.getInt("id");
                lb_username.setText(rs.getString("userName"));
                lb_name.setText(rs.getString("name"));
                lb_surname.setText(rs.getString("surName"));
                lb_id.setText("" + rs.getInt("assId"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        noteLoad();

    }

    @FXML
    public void save(ActionEvent e) {
        try {
            if (Integer.parseInt(tx_note.getText()) < 101) {
                try {
                    DB.Update("UPDATE tbl_enroled set id = '" + tx_idn.getText() + "', note = '" + tx_note.getText() + "' WHERE id='" + tx_idn.getText() + "'");
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                    Logger.getLogger(AssistantController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            DB.Update("UPDATE tbl_enroled set id = '" + tx_idn.getText() + "', info = '" + tx_infobox.getText() + "' WHERE id='" + tx_idn.getText() + "'");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(AssistantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        noteLoad();
    }

    private void noteCell() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col_sname.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("Note"));
        col_courseName.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("info"));
    }

    private void noteLoad() {
        try {
            try {
                data.clear();
                conn = (Connection) DriverManager.getConnection(sql);
                st = (Statement) conn.createStatement();
                st2 = (Statement) conn.createStatement();
                st3 = (Statement) conn.createStatement();
                rs = st.executeQuery("SELECT * FROM tbl_course WHERE assitantId = '" + ass_id + "'");
                while (rs.next()) {
                    rs2 = st2.executeQuery("SELECT * FROM tbl_enroled WHERE courseId = '" + rs.getInt(1) + "' ");
                    while (rs2.next()) {
                        rs3 = st3.executeQuery("SELECT * FROM tbl_student WHERE id = '" + rs2.getInt(3) + "'");
                        while (rs3.next()) {
                            data.add(new AssNote("" + rs2.getInt(1), rs3.getString(4), "" + rs2.getInt(4), rs.getString(2), rs2.getString(5)));
                        }
                    }
                }
            } catch (SQLException e) {
            }
            tbl_note.setItems(data);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AssistantController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
