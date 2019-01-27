package Controllers;

import dbConnection.DataBaseConnector;
import dbConnection.ItemPrice;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    ObservableList<ItemPriceExtended> observableList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resourceBundle;

    @FXML
    private URL location;

    @FXML // fx:id="nazwa"
    private TableColumn<ItemPriceExtended, String> nazwa;

    @FXML // fx:id="cena"
    private TableColumn<ItemPriceExtended, Number> cena;

    @FXML // fx:id="liczba"
    private TableColumn<ItemPriceExtended, Number> liczba;

    @FXML // fx:id="sklep"
    private TableColumn<ItemPriceExtended, String> sklep;

    @FXML // fx:id="wartosc"
    private TableColumn<ItemPriceExtended, Number> wartosc;

    @FXML
    private TableView<ItemPriceExtended> chartTable;

    @FXML
    private MenuBar fileMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chartTable.setEditable(true);
        chartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        nazwa.setEditable(false);
        cena.setEditable(false);
        liczba.setEditable(true);
        liczba.setOnEditCommit(e -> liczba_OnEditCommit(e));
        sklep.setEditable(false);
        wartosc.setEditable(false);

        nazwa.setCellFactory(TextFieldTableCell.forTableColumn());
        cena.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        liczba.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
//        liczba.setCellFactory(new Callback<TableColumn<ItemPriceExtended, Number>, TableCell<ItemPriceExtended, Number>>() {
//            @Override
//            public TableCell<ItemPriceExtended, Number> call(TableColumn<ItemPriceExtended, Number> param) {
//                final TableCell<ItemPriceExtended >
//            }
//        });
        sklep.setCellFactory(TextFieldTableCell.forTableColumn());
        wartosc.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        nazwa.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        cena.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        liczba.setCellValueFactory(cellData -> cellData.getValue().liczbaProperty());
        sklep.setCellValueFactory(cellData -> cellData.getValue().marketProperty());
        wartosc.setCellValueFactory(cellData -> cellData.getValue().wartoscProperty());

        chartTable.setItems(Observer.observableList);
        chartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        chartTable.setPlaceholder(new Label("Your Chart is empty :("));
    }

    public void liczba_OnEditCommit(Event e) {
        TableColumn.CellEditEvent<ItemPriceExtended, Number> cellEditEvent;
        cellEditEvent = (TableColumn.CellEditEvent<ItemPriceExtended, Number>) e;
        ItemPriceExtended singleItemPrice = cellEditEvent.getRowValue();
        singleItemPrice.setLiczba(cellEditEvent.getNewValue().intValue());
        singleItemPrice.setWartosc(singleItemPrice.getLiczba() * singleItemPrice.getPrice());
        chartTable.refresh();
    }

    public void handleSave(ActionEvent event) {
//        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
//        Stage stage = (Stage) fileMenu.getScene().getWindow();
//        exitAlert.setContentText("Are you sure you want to exit?");
//        exitAlert.initModality(Modality.APPLICATION_MODAL);
//        exitAlert.initOwner(stage);
//        exitAlert.showAndWait();
//
//        if(exitAlert.getResult() == ButtonType.OK) {
//            Platform.exit();
//        }
//        else {
//            exitAlert.close();
//        }
    }

    public void handleOpen(ActionEvent event) {
//        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm", ButtonType.OK, ButtonType.CANCEL);
//        Stage stage = (Stage) fileMenu.getScene().getWindow();
//        exitAlert.setContentText("Are you sure you want to exit?");
//        exitAlert.initModality(Modality.APPLICATION_MODAL);
//        exitAlert.initOwner(stage);
//        exitAlert.showAndWait();
//
//        if(exitAlert.getResult() == ButtonType.OK) {
//            Platform.exit();
//        }
//        else {
//            exitAlert.close();
//        }
    }

    public void handleCommitButtonClick(ActionEvent event) {
        System.out.println("Zaakceptowano zamowienie");
    }

    public void handleAffordButtonClick(ActionEvent event) {
        System.out.println("Dupa");
    }

    public void handleCancelButtonClick(ActionEvent event) {
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

}
