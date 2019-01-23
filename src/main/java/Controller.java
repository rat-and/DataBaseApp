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

    @FXML // fx:id="IDProduktField"
    private TextField IDProduktField;

    @FXML // fx:id="IDMarketField"
    private TextField IDMarketField;

    @FXML // fx:id="iloscField"
    private TextField iloscFieled;

    @FXML // fx:id="jednostkaField"
    private TextField jednostkaField;

    @FXML // fx:id="cenaField"
    private TextField cenaField;

    @FXML // fx:id="wagaField"
    private TextField wagaField;

    @FXML // fx:id="nazwaField"
    private TextField nazwaField;

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

        addBtn.setDisable(true);
        deleteBtn.setDisable(true);
        itemTable.setItems(observableItemList);
        itemTable.setEditable(true);
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemTable.setPlaceholder(new Label("Your Table is Empty"));

        IDProduktField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (IDProduktField.isFocused()) {
                    //TODO if admin
                    addBtn.setDisable(false);
                }
            }
        });
        itemTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (itemTable.isFocused()) {
                    //TODO if admin or user
                    deleteBtn.setDisable(false);
                }
            }
        });
    }//end initializeo

    public void clearFields() {
        IDMarketField.clear();
        IDProduktField.clear();
        iloscFieled.clear();
        wagaField.clear();
        nazwaField.clear();
        jednostkaField.clear();
        cenaField.clear();
    }

    /*------------------------------------------------Control handlers------------------------------------------------*/
    public void handleSearchButtonClick(ActionEvent event) {
        itemTable.getItems().clear();
        dataBaseConnector = new DataBaseConnector();
        if (filterInput.getText().equals("*")) {
//            System.out.println("Filtr wyszukiwan jest pusty!");
            List<ItemPrice> itemPrices = dataBaseConnector.getItemPrces();
            for (ItemPrice ip : itemPrices) {
                observableItemList.add(ip);
            }
        }else if (!filterInput.getText().equals("")) {
//            System.out.println("Filtr wyszukiwan: " + filterInput.getText());
            List<ItemPrice> itemPrices = dataBaseConnector.getItemPrcesOf(filterInput.getText());
            for (ItemPrice ip : itemPrices) {
                observableItemList.add(ip);

            }
        } else {
            Alert emptyFilter = new Alert(Alert.AlertType.WARNING, "Pusty filtr zapytan!", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyFilter.setContentText("Wpisz * by wyswietlic wszystkie krotki");
            emptyFilter.initModality(Modality.APPLICATION_MODAL);
            emptyFilter.initOwner(owner);
            emptyFilter.showAndWait();
        }
    }


    public void handleAddButtonClick(ActionEvent event) {

        if (isValidInput(event)) {
            ItemPrice singleItemPrice = new ItemPrice();
            singleItemPrice.setIDProdukt(Integer.parseInt(IDProduktField.getText()));
            singleItemPrice.setIDMarket(Integer.parseInt(IDMarketField.getText()));
            singleItemPrice.setWaga(Double.parseDouble(wagaField.getText()));
            singleItemPrice.setIlosc(Integer.parseInt(iloscFieled.getText()));
            singleItemPrice.setNazwa(nazwaField.getText());
            singleItemPrice.setJednostka(jednostkaField.getText());
            singleItemPrice.setCena(Double.parseDouble(cenaField.getText()));

            dataBaseConnector = new DataBaseConnector();
            dataBaseConnector.insertItemPrice(singleItemPrice);
            clearFields();

        } else {
           clearFields();
        }

    }
    private boolean isValidInput(ActionEvent event) {

        Boolean validInput = true;

        if(IDProduktField == null || IDProduktField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyIDProdukt = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyIDProdukt.setContentText("IDProdukt is EMPTY");
            emptyIDProdukt.initModality(Modality.APPLICATION_MODAL);
            emptyIDProdukt.initOwner(owner);
            emptyIDProdukt.showAndWait();
            if(emptyIDProdukt.getResult() == ButtonType.OK) {
                emptyIDProdukt.close();
                IDProduktField.requestFocus();
            }
        }
        if(IDMarketField == null || IDMarketField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyIDMarket = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyIDMarket.setContentText("IDMarket is EMPTY");
            emptyIDMarket.initModality(Modality.APPLICATION_MODAL);
            emptyIDMarket.initOwner(owner);
            emptyIDMarket.showAndWait();
            if (emptyIDMarket.getResult() == ButtonType.OK) {
                emptyIDMarket.close();
                IDMarketField.requestFocus();
            }
        }
        if(iloscFieled == null || iloscFieled.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyUIN = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyUIN.setContentText("UIN is EMPTY");
            emptyUIN.initModality(Modality.APPLICATION_MODAL);
            emptyUIN.initOwner(owner);
            emptyUIN.showAndWait();
            if (emptyUIN.getResult() == ButtonType.OK) {
                emptyUIN.close();
                iloscFieled.requestFocus();
            }
        }
        if(nazwaField == null || nazwaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyNetID = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyNetID.setContentText("NetID is EMPTY");
            emptyNetID.initModality(Modality.APPLICATION_MODAL);
            emptyNetID.initOwner(owner);
            emptyNetID.showAndWait();
            if (emptyNetID.getResult() == ButtonType.OK) {
                emptyNetID.close();
                nazwaField.requestFocus();
            }
        }
        if(wagaField == null || wagaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyMajor = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyMajor.setContentText("Major is EMPTY");
            emptyMajor.initModality(Modality.APPLICATION_MODAL);
            emptyMajor.initOwner(owner);
            emptyMajor.showAndWait();
            if (emptyMajor.getResult() == ButtonType.OK) {
                emptyMajor.close();
                wagaField.requestFocus();
            }
        }
        if(jednostkaField == null || jednostkaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyAge = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyAge.setContentText("Age is EMPTY");
            emptyAge.initModality(Modality.APPLICATION_MODAL);
            emptyAge.initOwner(owner);
            emptyAge.showAndWait();
            if (emptyAge.getResult() == ButtonType.OK) {
                emptyAge.close();
                jednostkaField.requestFocus();
            }
        }
        if(cenaField == null || cenaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyGPA = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyGPA.setContentText("GPA is EMPTY");
            emptyGPA.initModality(Modality.APPLICATION_MODAL);
            emptyGPA.initOwner(owner);
            emptyGPA.showAndWait();
            if (emptyGPA.getResult() == ButtonType.OK) {
                emptyGPA.close();
                cenaField.requestFocus();
            }
        }
        return validInput;
    }

    /*-------------------------------------------handle column edits---------------------------------------------------*/
    public void IDProdukt_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPrice, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPrice, Number>) e;
        ItemPrice singleItemPrice = cellEditEvent.getRowValue();
        System.out.println(singleItemPrice.getIDProdukt());
        System.out.println((cellEditEvent.getNewValue().intValue()));
        if (popupAlert("Potwierdzasz zmiane atrybutu: " + "IDProdukt z " + cellEditEvent.getOldValue().intValue()
                + " na " + cellEditEvent.getNewValue().intValue() + " ?")) {
            System.out.println("Potwierdzil");
            singleItemPrice.setIDProdukt(cellEditEvent.getNewValue().intValue());
        }
    }
    public void IDMarket_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPrice, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPrice, Number>) e;
        ItemPrice singleItemPrice = cellEditEvent.getRowValue();
//        System.out.println(singleItemPrice.getIDMarket());
//        System.out.println((cellEditEvent.getNewValue().intValue()));
        if (popupAlert("Potwierdzasz zmiane atrybutu: " + "IDMarket z " + cellEditEvent.getOldValue().intValue()
                + " na " + cellEditEvent.getNewValue().intValue() + " ?")) {
//            System.out.println("Potwierdzil dla id" + singleItemPrice.getIDProdukt());
            dataBaseConnector = new DataBaseConnector();
            //updateItemPrice(Object, column_number, new_value)
            dataBaseConnector.updateItemPrice_IDMarket(singleItemPrice, cellEditEvent.getNewValue().intValue());
            singleItemPrice.setIDMarket(cellEditEvent.getNewValue().intValue());
        }
    }
    public void nazwa_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<Student, String> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<Student, String>) e;
        Student student = cellEditEvent.getRowValue();
        student.setUin(cellEditEvent.getNewValue());
    }
    public void ilosc_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPrice, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPrice, Number>) e;
        ItemPrice singleItemPrice = cellEditEvent.getRowValue();
//        System.out.println(singleItemPrice.getIDMarket());
//        System.out.println((cellEditEvent.getNewValue().intValue()));
        if (popupAlert("Potwierdzasz zmiane atrybutu: " + "Ilosc z " + cellEditEvent.getOldValue().intValue()
                + " na " + cellEditEvent.getNewValue().intValue() + " ?")) {
            dataBaseConnector = new DataBaseConnector();
            //updateItemPrice(Object, column_number, new_value)
            dataBaseConnector.updateItemPrice_ilosc(singleItemPrice, cellEditEvent.getNewValue().intValue());
            singleItemPrice.setIDMarket(cellEditEvent.getNewValue().intValue());
        }
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

    public boolean popupAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
        Stage owner = (Stage) fileMenu.getScene().getWindow();
        alert.setContentText(msg);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.OK) {
            alert.close();
            return true;
        }
        else {
            alert.close();
            return false;
        }
    }
}









