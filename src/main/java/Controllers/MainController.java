package Controllers;

import GUI.Chart;
import dbConnection.DataBaseConnector;
import dbConnection.ItemPrice;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    private DataBaseConnector dataBaseConnector;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="nazwa"
    private TableColumn<ItemPrice, String> nazwa;

    @FXML // fx:id="cena"
    private TableColumn<ItemPrice, Number> cena;

    @FXML // fx:id="opinia"
    private TableColumn<ItemPrice, Number> opinia;

    @FXML // fx:id="sklep"
    private TableColumn<ItemPrice, String> sklep;

    @FXML // fx:id="opiniaSkl"
    private TableColumn<ItemPrice, Number> opiniaSkl;

    @FXML // fx:id="opis"
    private TableColumn<ItemPrice, String> opis;

//    @FXML // fx:id="cena"
//    private TableColumn<ItemPrice, Number> cena;

//    @FXML // fx:id="promocja"
//    private TableColumn<ItemPrice, Number> promocja;

    @FXML // fx:id="IDProduktField"
    private TextField IDProduktField;

    @FXML // fx:id="IDMarketField"
    private TextField IDMarketField;

    @FXML // fx:id="nazwaField"
    private TextField nazwaField;

    @FXML // fx:id="opiniaField"
    private TextField opiniaField;

    @FXML // fx:id="opisField"
    private TextField opisField;

    @FXML // fx:id="cenaField"
    private TextField cenaField;

//    @FXML // fx:id="nazwaField"
//    private TextField nazwaField;

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
        nazwa.setOnEditCommit(e -> nazwa_OnEditCommit(e));
        cena.setOnEditCommit(e -> cena_OnEditCommit(e));
        opinia.setOnEditCommit(e -> opinia_OnEditCommit(e));
        sklep.setOnEditCommit(e -> sklep_OnEditCommit(e));
        opiniaSkl.setOnEditCommit(e -> opiniaSkl_OnEditCommit(e));
        opis.setOnEditCommit(e -> opis_OnEditCommit(e));

        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nazwa.setCellFactory(TextFieldTableCell.forTableColumn());
        cena.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        opinia.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        sklep.setCellFactory(TextFieldTableCell.forTableColumn());
        opiniaSkl.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        opis.setCellFactory(TextFieldTableCell.forTableColumn());

        //Calling a row
        itemTable.setRowFactory(new Callback<TableView<ItemPrice>, TableRow<ItemPrice>>() {
            @Override
            public TableRow<ItemPrice> call(TableView<ItemPrice> param) {
                final TableRow<ItemPrice> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    //Mouse click handling
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.SECONDARY && row.getItem() != null) {

                            final ContextMenu rowMenu = new ContextMenu();
                            MenuItem addToChart = new MenuItem("Dodaj do koszyka");
                            MenuItem deleteRow = new MenuItem("Usun z bazy");

                            addToChart.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    ItemPrice singleItemPrice = row.getItem();
                                    Observer.observableList.add(singleItemPrice.extend());
                                    System.out.println(singleItemPrice.getName() + " added to observable list (chart)");
                                }
                            });

                            deleteRow.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println("Not yet implemented...");
                                }
                            });

                            rowMenu.getItems().addAll(addToChart, deleteRow);
                            row.contextMenuProperty().bind(
                                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null)
                            );
                        }
                    }
                });
                return row;
            }
        });


        nazwa.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        cena.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        opinia.setCellValueFactory(cellData -> cellData.getValue().opinionProperty());
        sklep.setCellValueFactory(cellData -> cellData.getValue().marketProperty());
        opiniaSkl.setCellValueFactory(cellData -> cellData.getValue().marketOpinionProperty());
        opis.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
//        cena.setCellValueFactory(cellData -> cellData.getValue().cenaProperty());
//        promocja.setCellValueFactory(cellData -> cellData.getValue().promocjaProperty());

        addBtn.setDisable(true);
//        deleteBtn.setDisable(true);
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
//        itemTable.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (itemTable.isFocused()) {
//                    //TODO if admin or user
//                    deleteBtn.setDisable(false);
//                }
//            }
//        });
    }//end initializeo

    public void clearFields() {
        IDMarketField.clear();
        IDProduktField.clear();
        nazwaField.clear();
        opiniaField.clear();
        opisField.clear();
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
            singleItemPrice.setName(nazwaField.getText());
            singleItemPrice.setPrice(Double.parseDouble(cenaField.getText()));
            singleItemPrice.setOpinion(Double.parseDouble(opisField.getText()));
            singleItemPrice.setDescription(opisField.getText());

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
        if(nazwa == null || nazwa.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyUIN = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyUIN.setContentText("UIN is EMPTY");
            emptyUIN.initModality(Modality.APPLICATION_MODAL);
            emptyUIN.initOwner(owner);
            emptyUIN.showAndWait();
            if (emptyUIN.getResult() == ButtonType.OK) {
                emptyUIN.close();
                nazwaField.requestFocus();
            }
        }
        if(opisField == null || opisField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyNetID = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyNetID.setContentText("NetID is EMPTY");
            emptyNetID.initModality(Modality.APPLICATION_MODAL);
            emptyNetID.initOwner(owner);
            emptyNetID.showAndWait();
            if (emptyNetID.getResult() == ButtonType.OK) {
                emptyNetID.close();
                opisField.requestFocus();
            }
        }
        if(opiniaField == null || opiniaField.getText().trim().isEmpty()) {
            validInput = false;
            Alert emptyMajor = new Alert(Alert.AlertType.WARNING, "Warning", ButtonType.OK);
            Window owner = ((Node) event.getTarget()).getScene().getWindow();
            emptyMajor.setContentText("Major is EMPTY");
            emptyMajor.initModality(Modality.APPLICATION_MODAL);
            emptyMajor.initOwner(owner);
            emptyMajor.showAndWait();
            if (emptyMajor.getResult() == ButtonType.OK) {
                emptyMajor.close();
                opiniaField.requestFocus();
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
    public void nazwa_OnEditCommit(Event e) {
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
    public void cena_OnEditCommit(Event e) {
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
    public void opinia_OnEditCommit(Event e) {

    }
    public void sklep_OnEditCommit(Event e) {
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
//            singleItemPrice.setMarket(cellEditEvent.getNewValue());
        }
    }
    public void opiniaSkl_OnEditCommit(Event e) {

    }
    public void opis_OnEditCommit(Event e) {

    }
//    public void cena_OnEditCommit(Event e) {
//
//    }
//    public void promocja_OnEditCommit(Event e) {
//
//    }
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

    public void handleChartButtonClick(ActionEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Chart chart = new Chart();
                    chart.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
    public void saveFile(ObservableList<ItemPrice> observableStudentList, File file) {
//        try {
//            BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
//
//            for(ItemPrice ip : observableStudentList) {
//                outWriter.write(students.toString());
//                outWriter.newLine()i;
//            }
//            System.out.println(observableStudentList.toString());
//            outWriter.close();
//        } catch (IOException e) {
//            Alert ioAlert = new Alert(Alert.AlertType.ERROR, "OOPS!", ButtonType.OK);
//            ioAlert.setContentText("Sorry. An error has occurred.");
//            ioAlert.showAndWait();
//            if(ioAlert.getResult() == ButtonType.OK) {
//                ioAlert.close();
//            }
//        }
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









