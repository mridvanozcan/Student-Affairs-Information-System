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
public class StudentController implements Initializable {

    DataBaseConnection DB = new DataBaseConnection();
    private final String sql = "jdbc:sqlite:SAISystem.db";
    Connection conn = null;
    private ResultSet rs = null;
    private Statement st = null;
    private ResultSet rs2 = null;
    private Statement st2 = null;
    private ObservableList<Enrol> data1;
    private ObservableList<Drop> data2;
    private ObservableList<Note> data3;
    private int stu_id;
    private String pageName;
    private String pageTitle;

    @FXML
    private TextField lb_stunumber;
    @FXML
    private Button btn_call;
    @FXML
    private Label stu_name;
    @FXML
    private Label stu_surname;
    @FXML
    private Label stu_number;
    @FXML
    private Label stu_username;
    @FXML
    private TableView<Enrol> tbl_EnroleList;
    @FXML
    private TableColumn<?, ?> col_coursId;
    @FXML
    private TableColumn<?, ?> col_courName;
    @FXML
    private TextField tb_courseId;
    @FXML
    private Button btn_enroll;
    @FXML
    private TableView<Drop> tbl_DropList;
    @FXML
    private TableColumn<?, ?> col_lessonId;
    @FXML
    private TableColumn<?, ?> col_lesName;
    @FXML
    private TableView<Note> tbl_notes;
    @FXML
    private TableColumn<?, ?> col_name;
    @FXML
    private TableColumn<?, ?> col_note;
    @FXML
    private Button btn_drop;
    @FXML
    private TextField tb_drop;
    @FXML
    private Button btn_logout;
    @FXML
    private TableColumn<?, ?> col_cinfo;
    @FXML
    private TableColumn<?, ?> col_info;

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
            //  Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data1 = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();
        data3 = FXCollections.observableArrayList();
        EnrolCell();
        DropCell();
        NoteCell();
    }

    @FXML
    public void callinfo(ActionEvent e) {

        try {
            conn = (Connection) DriverManager.getConnection(sql);
            st = (Statement) conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tbl_student WHERE studentId = '" + lb_stunumber.getText() + "' ");
            while (rs.next()) {
                stu_id = +rs.getInt("id");
                stu_username.setText(rs.getString("userName"));
                stu_name.setText(rs.getString("name"));
                stu_surname.setText(rs.getString("surName"));
                stu_number.setText("" + rs.getInt("studentId"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            EnrolLoad();
            DropLoad();
            NoteLoad();

        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void enroll(ActionEvent e) {
        try {
            try {
                String ins = "INSERT INTO tbl_enroled(courseId,studentId) VALUES('" + tb_courseId.getText() + "','" + stu_id + "')";
                DB.Insert(ins);
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DropLoad();
            NoteLoad();

        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void drop(ActionEvent e) {
        try {
            data2.clear();
            data3.clear();
            try {
                DB.Delete("tbl_enroled", tb_drop.getText());
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DropLoad();
            NoteLoad();

        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void EnrolCell() {
        col_coursId.setCellValueFactory(new PropertyValueFactory<>("CourseId"));
        col_courName.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        col_cinfo.setCellValueFactory(new PropertyValueFactory<>("CourseInfo"));
    }

    private void EnrolLoad() throws SQLException {
        try {
            data1.clear();
            conn = (Connection) DriverManager.getConnection(sql);
            st = (Statement) conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tbl_course");
            while (rs.next()) {
                data1.add(new Enrol("" + rs.getInt(1), rs.getString(2), rs.getString(6)));
            }
        } catch (SQLException e) {
        }
        tbl_EnroleList.setItems(data1);
        conn.close();

    }

    private void DropCell() {
        col_lessonId.setCellValueFactory(new PropertyValueFactory<>("LessonId"));
        col_lesName.setCellValueFactory(new PropertyValueFactory<>("LessonName"));

    }

    private void DropLoad() throws SQLException {
        try {
            data2.clear();
            conn = (Connection) DriverManager.getConnection(sql);
            st = (Statement) conn.createStatement();
            st2 = (Statement) conn.createStatement();
            rs2 = st2.executeQuery("SELECT * FROM tbl_enroled WHERE studentId = '" + stu_id + "'");
            while (rs2.next()) {
                rs = st.executeQuery("SELECT * FROM tbl_course WHERE id = '" + rs2.getInt(2) + "' ");
                while (rs.next()) {
                    data2.add(new Drop("" + rs2.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
        }
        tbl_DropList.setItems(data2);
        conn.close();

    }

    private void NoteCell() {
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_note.setCellValueFactory(new PropertyValueFactory<>("Note"));
        col_info.setCellValueFactory(new PropertyValueFactory<>("Info"));

    }

    private void NoteLoad() throws SQLException {
        try {
            data3.clear();
            conn = (Connection) DriverManager.getConnection(sql);
            st = (Statement) conn.createStatement();
            st2 = (Statement) conn.createStatement();
            rs2 = st2.executeQuery("SELECT * FROM tbl_enroled WHERE studentId = '" + stu_id + "'");
            while (rs2.next()) {
                rs = st.executeQuery("SELECT * FROM tbl_course WHERE id = '" + rs2.getInt(2) + "' ");
                while (rs.next()) {
                    data3.add(new Note(rs.getString(2), "" + rs2.getInt(4),rs2.getString(5)));
                }
            }
        } catch (SQLException e) {
        }
        tbl_notes.setItems(data3);
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
