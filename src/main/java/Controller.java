/**
 * Created by IRGeekSauce on 11/26/15.
 */

import dbConnection.DataBaseConnector;
import dbConnection.ItemPrice;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.NumberStringConverter;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    private DataBaseConnector dataBaseConnector;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="IDProdukt"
    private TableColumn<ItemPrice, Number> IDProdukt;

    @FXML // fx:id="IDMarket"
    private TableColumn<ItemPrice, Number> IDMarket;

    @FXML // fx:id="nazwa"
    private TableColumn<ItemPrice, String> nazwa;

    @FXML // fx:id="ilosc"
    private TableColumn<ItemPrice, Number> ilosc;

    @FXML // fx:id="waga"
    private TableColumn<ItemPrice, Number> waga;

    @FXML // fx:id="jednostka"
    private TableColumn<ItemPrice, String> jednostka;

    @FXML // fx:id="cena"
    private TableColumn<ItemPrice, Number> cena;

    @FXML // fx:id="promocja"
    private TableColumn<ItemPrice, Number> promocja;

    @FXML // fx:id="firstNameField"
    private TextField firstNameField;

    @FXML // fx:id="lastNameField"
    private TextField lastNameField;

    @FXML // fx:id="netIDField"
    private TextField netIDField;

    @FXML // fx:id="ageField"
    private TextField ageField;

    @FXML // fx:id="gpaField"
    private TextField gpaField;

    @FXML // fx:id="majorField"
    private TextField majorField;

    @FXML // fx:id="uinField"
    private TextField uinField;

    @FXML
    private TextField filterInput;

//    @FXML // fx:id="genderBox"
//    private ComboBox<String> genderBox;
//    ObservableList<String> genderBoxData = FXCollections.observableArrayList();

    @FXML
    private TableView<ItemPrice> itemTable;

    @FXML // fx:id="addBtn"
    private Button addBtn;

    @FXML // fx:id="deleteBtn"
    private Button deleteBtn;

    @FXML
    private MenuBar fileMenu;

    ObservableList<ItemPrice> observableItemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add Listener to filterField
        filterInput.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterStudentList((String) oldValue, (String) newValue);

            }
        });
        //initialize editable attributes
        itemTable.setEditable(true);
        IDProdukt.setOnEditCommit(e -> IDProdukt_OnEditCommit(e));
        IDMarket.setOnEditCommit(e -> IDMarket_OnEditCommit(e));
        nazwa.setOnEditCommit(e -> nazwa_OnEditCommit(e));
        ilosc.setOnEditCommit(e -> ilosc_OnEditCommit(e));
        waga.setOnEditCommit(e -> waga_OnEditCommit(e));
        jednostka.setOnEditCommit(e -> jednostka_OnEditCommit(e));
        cena.setOnEditCommit(e -> cena_OnEditCommit(e));
        promocja.setOnEditCommit(e -> promocja_OnEditCommit(e));

        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        IDProdukt.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        IDMarket.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        nazwa.setCellFactory(TextFieldTableCell.forTableColumn());
        ilosc.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        waga.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        jednostka.setCellFactory(TextFieldTableCell.forTableColumn());
        cena.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        promocja.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));


        IDProdukt.setCellValueFactory(cellData -> cellData.getValue().getIDProduktProperty());
        IDMarket.setCellValueFactory(cellData -> cellData.getValue().getIDMarketProperty());
        nazwa.setCellValueFactory(cellData -> cellData.getValue().getNazwaProperty());
        ilosc.setCellValueFactory(cellData -> cellData.getValue().iloscProperty());
        waga.setCellValueFactory(cellData -> cellData.getValue().wagaProperty());
        jednostka.setCellValueFactory(cellData -> cellData.getValue().jednostkaProperty());
        cena.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());
        promocja.setCellValueFactory(cellData -> cellData.getValue().promocjaProperty());


        //initialize gender ComboBox
//        genderBoxData.add(new String("Male"));
//        genderBoxData.add(new String("Female"));
//
//        genderBox.setItems(genderBoxData);


        addBtn.setDisable(true);
        deleteBtn.setDisable(true);
        itemTable.setItems(observableItemList);
        itemTable.setEditable(true);
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemTable.setPlaceholder(new Label("Your Table is Empty"));

        firstNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (firstNameField.isFocused()) {
                    addBtn.setDisable(false);
                }
            }
        });
        itemTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (itemTable.isFocused()) {
                    deleteBtn.setDisable(false);
                }
            }
        });
    }//end initialize

    /*----------------------------------------------Control handlers---------------------------------------------*/
    public void handleAddButtonClick(ActionEvent event) {
//        /*
//        Get input from user and add to Table
//         */
//        if (observableItemList.size() < 10) {
//            if (isValidInput(event)) {
//                if (genderBox.getValue().equals("Male")) {
//                    Student student = new Student();
//                    student.setFirstName(firstNameField.getText());
//                    student.setLastName(lastNameField.getText());
//                    student.setAge(Integer.parseInt(ageField.getText()));
//                    student.setMajor(majorField.getText());
//                    student.setNetID(netIDField.getText());
//                    student.setUin(uinField.getText());
//                    student.setGradepoint(Double.parseDouble(gpaField.getText()));
//                    student.setGender(genderBox.getValue());
//                    observableItemList.add(/*TODO*/);
//                    System.out.println(student.toString());
//                    firstNameField.clear();
//                    lastNameField.clear();
//                    uinField.clear();
//                    netIDField.clear();
//                    majorField.clear();
//                    ageField.clear();
//                    gpaField.clear();
//                    genderBox.setValue("Gender");
//                }
//                if (genderBox.getValue().equals("Female")) {
//                    Student student = new Student();
//                    student.setFirstName(firstNameField.getText());
//                    student.setLastName(lastNameField.getText());
//                    student.setAge(Integer.parseInt(ageField.getText()));
//                    student.setMajor(majorField.getText());
//                    student.setNetID(netIDField.getText());
//                    student.setUin(uinField.getText());
//                    student.setGradepoint(Double.parseDouble(gpaField.getText()));
//                    student.setGender(genderBox.getValue());
//                    observableStudentList.add(student);
//                    System.out.println(student.toString());
//                    firstNameField.clear();
//                    lastNameField.clear();
//                    uinField.clear();
//                    netIDField.clear();
//                    majorField.clear();
//                    ageField.clear();
//                    gpaField.clear();
//                    genderBox.setValue("Gender");
//                }
//            }
//        } else {
//            Alert sizeAlert = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
//            Window owner = ((Node) event.getTarget()).getScene().getWindow();
//            sizeAlert.setContentText("You may only hold 10 Students at this time");
//            sizeAlert.initModality(Modality.APPLICATION_MODAL);
//            sizeAlert.initOwner(owner);
//            sizeAlert.showAndWait();
//            if (sizeAlert.getResult() == ButtonType.OK) {
//                sizeAlert.close();
//                firstNameField.clear();
//                lastNameField.clear();
//                uinField.clear();
//                netIDField.clear();
//                majorField.clear();
//                ageField.clear();
//                gpaField.clear();
//                genderBox.setValue("Gender");
//            }
//        }
    }
    /*
    In case of empty fields. Gives alert for respective empty field and requests focus on that field.
     */
    private boolean isValidInput(ActionEvent event) {

        Boolean validInput = true;

        if(firstNameField == null || firstNameField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyFirstName = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyFirstName.setContentText("First name is EMPTY");
            emptyFirstName.initModality(Modality.APPLICATION_MODAL);
            emptyFirstName.initOwner(owner);
            emptyFirstName.showAndWait();
            if(emptyFirstName.getResult() == ButtonType.OK) {
                emptyFirstName.close();
                firstNameField.requestFocus();
            }
        }
        if(lastNameField == null || lastNameField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyLastName = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyLastName.setContentText("Last name is EMPTY");
            emptyLastName.initModality(Modality.APPLICATION_MODAL);
            emptyLastName.initOwner(owner);
            emptyLastName.showAndWait();
            if (emptyLastName.getResult() == ButtonType.OK) {
                emptyLastName.close();
                lastNameField.requestFocus();
            }
        }
        if(uinField == null || uinField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyUIN = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyUIN.setContentText("UIN is EMPTY");
            emptyUIN.initModality(Modality.APPLICATION_MODAL);
            emptyUIN.initOwner(owner);
            emptyUIN.showAndWait();
            if (emptyUIN.getResult() == ButtonType.OK) {
                emptyUIN.close();
                uinField.requestFocus();
            }
        }
        if(netIDField == null || netIDField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyNetID = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyNetID.setContentText("NetID is EMPTY");
            emptyNetID.initModality(Modality.APPLICATION_MODAL);
            emptyNetID.initOwner(owner);
            emptyNetID.showAndWait();
            if (emptyNetID.getResult() == ButtonType.OK) {
                emptyNetID.close();
                netIDField.requestFocus();
            }
        }
        if(majorField == null || majorField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyMajor = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyMajor.setContentText("Major is EMPTY");
            emptyMajor.initModality(Modality.APPLICATION_MODAL);
            emptyMajor.initOwner(owner);
            emptyMajor.showAndWait();
            if (emptyMajor.getResult() == ButtonType.OK) {
                emptyMajor.close();
                majorField.requestFocus();
            }
        }
        if(ageField == null || ageField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyAge = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyAge.setContentText("Age is EMPTY");
            emptyAge.initModality(Modality.APPLICATION_MODAL);
            emptyAge.initOwner(owner);
            emptyAge.showAndWait();
            if (emptyAge.getResult() == ButtonType.OK) {
                emptyAge.close();
                ageField.requestFocus();
            }
        }
        if(gpaField == null || gpaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyGPA = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyGPA.setContentText("GPA is EMPTY");
            emptyGPA.initModality(Modality.APPLICATION_MODAL);
            emptyGPA.initOwner(owner);
            emptyGPA.showAndWait();
            if (emptyGPA.getResult() == ButtonType.OK) {
                emptyGPA.close();
                gpaField.requestFocus();
            }
        }
//        if(genderBox == null || genderBox.getValue().isEmpty()) {
//            validInput = false;
//            Alert emptyGender = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
//            Window owner = ((Node) event.getTarget()).getScene().getWindow();
//            emptyGender.setContentText("Gender is EMPTY");
//            emptyGender.initModality(Modality.APPLICATION_MODAL);
//            emptyGender.initOwner(owner);
//            emptyGender.showAndWait();
//            if (emptyGender.getResult() == ButtonType.OK) {
//                emptyGender.close();
//                genderBox.requestFocus();
//            }
//        }
        return validInput;
    }
    /*
    handle column edits
     */
    public void IDProdukt_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPrice, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPrice, Number>) e;
        ItemPrice singleItemPrice = cellEditEvent.getRowValue();
        singleItemPrice.setIDProdukt((int)cellEditEvent.getNewValue());
    }
    public void IDMarket_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPrice, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPrice, Number>) e;
        ItemPrice singleItemPrice = cellEditEvent.getRowValue();
        singleItemPrice.setIDMarket((int)cellEditEvent.getNewValue());
    }
    public void nazwa_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setUin(cellEditEvent.getNewValue());
    }
    public void ilosc_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setNetID(cellEditEvent.getNewValue());
    }
    public void waga_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setMajor(cellEditEvent.getNewValue());
    }
    public void jednostka_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, Integer> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, Integer>) e;
        Student student = cellEditEvent.getRowValue();
        student.setAge(cellEditEvent.getNewValue());
    }
    public void cena_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, Double> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, Double>) e;
        Student student = cellEditEvent.getRowValue();
        student.setGradepoint(cellEditEvent.getNewValue());
    }
    public void promocja_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setGender(cellEditEvent.getNewValue());
    }
    public void handleDeleteButtonClick(ActionEvent event) {
//        if(!observableStudentList.isEmpty()) {
//            System.out.println("Delete button clicked");
//            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Confirm", ButtonType.OK, ButtonType.CANCEL);
//            Window owner = ((Node) event.getTarget()).getScene().getWindow();
//            deleteAlert.setContentText("Are you sure you want to delete this?\n\nTHIS CANNOT BE UNDONE.");
//            deleteAlert.initModality(Modality.APPLICATION_MODAL);
//            deleteAlert.initOwner(owner);
//            deleteAlert.showAndWait();
//            if(deleteAlert.getResult() == ButtonType.OK) {
//                observableStudentList.removeAll(itemTable.getSelectionModel().getSelectedItems());
//                itemTable.getSelectionModel().clearSelection();
//            }
//            else {
//                deleteAlert.close();
//            }
//        }
    }
    public void handleSearchButtonClick(ActionEvent event) {
        dataBaseConnector = new DataBaseConnector();
        if (!filterInput.getText().equals("")) {
            System.out.println("Filtr wyszukiwan: " + filterInput.getText());
            List<ItemPrice> itemPrices = dataBaseConnector.getItemPrcesOf(filterInput.getText());
            for (ItemPrice ip : itemPrices) {
                observableItemList.add(ip);

            }
        } else {
            System.out.println("Filtr wyszukiwan jest pusty!");
            List<ItemPrice> itemPrices = dataBaseConnector.getItemPrces();
            for (ItemPrice ip : itemPrices) {
                observableItemList.add(ip);
        }


        }



    }
    //filter table by first or last name
    public void filterStudentList(String oldValue, String newValue) {
//        ObservableList<Student> filteredList = FXCollections.observableArrayList();
//        if(filterInput == null || (newValue.length() < oldValue.length()) || newValue == null) {
//            itemTable.setItems(observableStudentList);
//        }
//        else {
//            newValue = newValue.toUpperCase();
//            for(Student students : itemTable.getItems()) {
//                String filterFirstName = students.getFirstName();
//                String filterLastName = students.getLastName();
//                if(filterFirstName.toUpperCase().contains(newValue) || filterLastName.toUpperCase().contains(newValue)) {
//                    filteredList.add(students);
//                }
//            }
//            itemTable.setItems(filteredList);
//        }
    }
    public void handleSave(ActionEvent event) {
//        Stage secondaryStage = new Stage();
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save Student Table");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        if(observableStudentList.isEmpty()) {
//            secondaryStage.initOwner(this.fileMenu.getScene().getWindow());
//            Alert emptyTableAlert = new Alert(Alert.AlertType.ERROR, "EMPTY TABLE", ButtonType.OK);
//            emptyTableAlert.setContentText("You have nothing to save");
//            emptyTableAlert.initModality(Modality.APPLICATION_MODAL);
//            emptyTableAlert.initOwner(this.fileMenu.getScene().getWindow());
//            emptyTableAlert.showAndWait();
//            if(emptyTableAlert.getResult() == ButtonType.OK) {
//                emptyTableAlert.close();
//            }
//        }
//        else {
//            File file = fileChooser.showSaveDialog(secondaryStage);
//            if(file != null) {
//                saveFile(itemTable.getItems(), file);
//            }
//        }
    }
    public void saveFile(ObservableList<Student> observableStudentList, File file) {
        try {
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));

            for(Student students : observableStudentList) {
                outWriter.write(students.toString());
                outWriter.newLine();
            }
            System.out.println(observableStudentList.toString());
            outWriter.close();
        } catch (IOException e) {
            Alert ioAlert = new Alert(Alert.AlertType.ERROR, "OOPS!", ButtonType.OK);
            ioAlert.setContentText("Sorry. An error has occurred.");
            ioAlert.showAndWait();
            if(ioAlert.getResult() == ButtonType.OK) {
                ioAlert.close();
            }
        }
    }
    public void closeApp(ActionEvent event) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
        Stage stage = (Stage) fileMenu.getScene().getWindow();
        exitAlert.setContentText("Are you sure you want to exit?");
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.initOwner(stage);
        exitAlert.showAndWait();

        if(exitAlert.getResult() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            exitAlert.close();
        }
    }
}









