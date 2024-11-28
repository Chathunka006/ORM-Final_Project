package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Bo.BoFactory;
import lk.ijse.Bo.Custom.CourseBo;
import lk.ijse.Bo.Custom.StudentBo;
import lk.ijse.Bo.Custom.UserBo;
import lk.ijse.Dao.Custom.CourseDao;
import lk.ijse.Dao.Custom.StudentDao;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.CourseDto;
import lk.ijse.Dto.StudentDto;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Course;
import lk.ijse.Entity.User;
import lk.ijse.EntityTm.StudentTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentFormController {

    @FXML
    private AnchorPane anpStudent;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private ComboBox<String> comboCourse;

    @FXML
    private ComboBox<String> comboUser;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFree;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtcourseName;

    StudentBo studentBo = (StudentBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT);
    CourseBo courseBo = (CourseBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.COURSE);
    UserBo userBo = (UserBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.USER);
    CourseDao courseDao = (CourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT);
    ObservableList<StudentTm> studentTmObservableList = FXCollections.observableArrayList();
    ArrayList<Course> courseArrayList = new ArrayList<>();
    ArrayList<Student> studentArrayList = new ArrayList<>();

    public void initialize() throws IOException {
        generateNewId();
        getAllCourses();
        setCourseId();
        setUserId();
        getAllStudents();
        setDate();
        setCellValueFactory();
        setTable();
        filterStudent();
        selectTableRow();
        // clearFields();
    }

    private void getAllStudents() throws IOException {
        List<Student> studentList = studentBo.getStudentList();
        studentArrayList = (ArrayList<Student>) studentList;
    }

    private void getAllCourses() throws IOException {
        List<Course> courseList = courseBo.getCourseList();
        courseArrayList = (ArrayList<Course>) courseList;
    }


    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("stu_address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("stu_phone"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void setTable() throws IOException {
        studentTmObservableList.clear();
        List<Student> studentList = studentBo.getStudentList();
        for (Student student : studentList) {
            StudentTm studentTm = new StudentTm(
                            student.getStu_id(),
                            student.getStu_name(),
                            student.getStu_address(),
                            student.getStu_phone(),
                            student.getDate()
                            );
            studentTmObservableList.add(studentTm);
        }
        tblStudent.setItems(studentTmObservableList);
    }

    private void selectTableRow() {
        tblStudent.setOnMouseClicked(mouseEvent -> {
            int row = tblStudent.getSelectionModel().getSelectedIndex();
            StudentTm studentTm = tblStudent.getItems().get(row);
            txtId.setText(studentTm.getStu_id());
            txtName.setText(studentTm.getStu_name());
            txtAddress.setText(studentTm.getStu_address());
            txtContact.setText(studentTm.getStu_phone());
        });
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private String generateNewId() throws IOException {
        String nextId = studentDao.getCurrentId();
        txtId.setText(nextId);
        return nextId;
    }

    private void clearFields() {
        txtAddress.clear();
        txtContact.clear();
        txtDate.clear();
        txtId.clear();
        txtName.clear();
        txtSearch.clear();
        txtFree.clear();
        txtcourseName.clear();
        txtSearch.clear();
    }

    private void setUserId() {
        List<String> userIds = userDao.getUserId();
        ObservableList<String> users = FXCollections.observableArrayList();
        for (String userId : userIds) {
            users.add(userId);
        }
        comboUser.setItems(users);
    }

    public void setCourseId() {
        ObservableList<String> id = FXCollections.observableArrayList();
        for (Course course : courseArrayList) {
            id.add(course.getCourse_id());
        }
        comboCourse.setItems(id);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (studentBo.delete(txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Student Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "SQL Error").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String studentId = txtId.getText();
        Student existingStudent = studentDao.getStudentById(studentId);

        // If the student already exists, register them for the new course
        if (existingStudent != null) {
            registerStudentForCourse(existingStudent);
        } else {
            // Create and save a new student if not found
            User user = userDao.getUserById(comboUser.getValue());

            StudentDto studentDto = new StudentDto();
            studentDto.setStu_id(studentId);
            studentDto.setStu_name(txtName.getText());
            studentDto.setStu_address(txtAddress.getText());
            studentDto.setStu_phone(txtContact.getText());
            studentDto.setDate(Date.valueOf(txtDate.getText()));
            studentDto.setUser(user);

            studentBo.save(studentDto);

            // Retrieve the newly saved student and register them for the course
            Student newStudent = studentDao.getStudentById(studentId);
            registerStudentForCourse(newStudent);
        }
        setTable();
        new Alert(Alert.AlertType.INFORMATION, "Student Added With Course Successfully!").show();
    }

    private void registerStudentForCourse(Student student) throws IOException {
        String courseId = comboCourse.getValue();
        Course selectedCourse = null;

        // Find the course by ID
        for (Course course : courseArrayList) {
            if (course.getCourse_id().equals(courseId)) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse != null) {
            // Check if the student is already registered for this course
            if (!studentDao.isStudentRegisteredForCourse(student.getStu_id(), courseId)) {
                Student_Course studentCourse = new Student_Course();
                studentCourse.setStudent(student);
                studentCourse.setCourse(selectedCourse);
                studentCourse.setRegistration_date(new java.util.Date());

                // Save the student-course relationship
                studentDao.saveStudentCourseDetails(studentCourse);
            } else {
                System.out.println("Student is already registered for this course.");
            }
        } else {
            System.out.println("Selected course not found.");
        }
        setTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        Date date = Date.valueOf(txtDate.getText());

        User user = userDao.getUserById(comboUser.getValue());

        StudentDto studentDto = new StudentDto(id, name, address, contact, date, user);

        if (studentBo.update(studentDto)) {
            new Alert(Alert.AlertType.INFORMATION, "Student Updated Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error updating student details").show();
        }

        setTable();
        generateNewId();

    }

    @FXML
    void comboCourseOnAction(ActionEvent event) throws IOException {
        String courseId =  comboCourse.getValue();
        CourseDto course = courseBo.getCourse(courseId);
        if (course != null) {
            txtcourseName.setText(course.getCourse_name());
            txtDuration.setText(course.getDuration());
            txtFree.setText(String.valueOf(course.getCourse_fee()));
        }
    }

    @FXML
    void comboUserOnAction(ActionEvent event) {
        String userId = comboUser.getValue();
        userBo.getUser(userId);
    }

    private void filterStudent() {
        FilteredList<StudentTm> filterData = new FilteredList<>(studentTmObservableList, e -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterData.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();
                if (student.getStu_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (student.getStu_phone().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (student.getStu_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<StudentTm> studentTmSortedList = new SortedList<>(filterData);
        studentTmSortedList.comparatorProperty().bind(tblStudent.comparatorProperty());
        tblStudent.setItems(studentTmSortedList);
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtDate.requestFocus();
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtDateOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtContact.requestFocus();

    }

    public void txtStudentIdOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.ADDRESS,txtAddress);
    }

    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.CONTACT, txtContact);
    }

    public void txtStudentNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME, txtName);
    }
    public boolean isValidated() {
        if(!Regex.setTextColor(TextFieldType.NAME,txtName)) return false;
        if(!Regex.setTextColor(TextFieldType.ADDRESS,txtAddress)) return false;
        if(!Regex.setTextColor(TextFieldType.CONTACT,txtContact)) return false;
        return true;
    }
}
