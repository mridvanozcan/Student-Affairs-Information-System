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
public class InstructorController implements Initializable {

    DataBaseConnection DB = new DataBaseConnection();
    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;
    private ResultSet rs = null;
    private ObservableList<CourseList> data;
    private ObservableList<AssistantList> data2;
    private String pageName;
    private String pageTitle;

    @FXML
    private TextField cn, ca, as, ti, ci, dci;

    @FXML
    public Button btn_dll, btn_oc, btn_ref;

    @FXML
    private TableView<CourseList> tbl_view;
    @FXML
    private TableColumn<?, ?> c_id;
    @FXML
    private TableColumn<?, ?> c_na;
    @FXML
    private TableColumn<?, ?> c_ca;
    @FXML
    private TableColumn<?, ?> c_as;
    @FXML
    private TableColumn<?, ?> c_t;
    @FXML
    private TableView<AssistantList> tbl_assistant;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_assistantName;

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
            Logger.getLogger(InstructorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        try {
            setCell();
            setAssistantCell();
            loadAssistantData();
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(InstructorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Ref() throws SQLException {
        setCell();
        loadData();
        setAssistantCell();
        loadAssistantData();
    }

    @FXML
    public void OpenCourse(ActionEvent ac) {
        try {
            String ins = "INSERT INTO tbl_course(courseName,capacity,assitantId,time,info) VALUES('" + cn.getText() + "','" + ca.getText() + "',"
                    + "'" + as.getText() + "','" + ti.getText() + "','" + ci.getText() + "')";
            DB.Insert(ins);
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(InstructorController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("Hata");
        }
    }

    @FXML
    public void DellCourse(ActionEvent ac) {
        try {
            DB.Delete("tbl_course", dci.getText());
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(InstructorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setCell() {
        c_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_na.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        c_ca.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        c_as.setCellValueFactory(new PropertyValueFactory<>("AssistantId"));
        c_t.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadData() throws SQLException {
        try {
            data.clear();
            conn = (Connection) DriverManager.getConnection(sql);
            Statement st = (Statement) conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tbl_course");
            while (rs.next()) {
                data.add(new CourseList("" + rs.getInt(1), rs.getString(2), "" + rs.getInt(3), rs.getString(4), "" + rs.getInt(5)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        tbl_view.setItems(data);
        conn.close();

    }

    private void setAssistantCell() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_assistantName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadAssistantData() throws SQLException {
        try {
            data2.clear();
            conn = (Connection) DriverManager.getConnection(sql);
            Statement st = (Statement) conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tbl_assistant");
            while (rs.next()) {
                data2.add(new AssistantList("" + rs.getInt(1), rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        tbl_assistant.setItems(data2);
        conn.close();
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
