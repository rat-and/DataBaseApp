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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ChartController implements Initializable {
    ObservableList<ItemPriceExtended> observableList = FXCollections.observableArrayList();
    private double totalValue = 0.0;
    private Date date;

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
    private TextField moneyField;

    @FXML
    private Button orderBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableView<ItemPriceExtended> chartTable;

    @FXML
    private MenuBar fileMenu;

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void addToTotalValue(double add) {
        this.totalValue += add;
    }

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
        sklep.setCellFactory(TextFieldTableCell.forTableColumn());
        wartosc.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        nazwa.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        cena.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        liczba.setCellValueFactory(cellData -> cellData.getValue().liczbaProperty());
        sklep.setCellValueFactory(cellData -> cellData.getValue().marketProperty());
        wartosc.setCellValueFactory(cellData -> cellData.getValue().wartoscProperty());

        orderBtn.setDisable(true);

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
        if (Observer.priviliges <= 2) {
            DataBaseConnector dataBaseConnector = new DataBaseConnector();
            DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String nameNdate = "backup_" + df.format(new Date()).toString();
            String orderNdate =  "tmp_order_" + df.format(new Date()).toString();

            if (popupAlert("Are sure you want to back up your chart?", "Please confirm")) {
                dataBaseConnector.dropTableIfExists(orderNdate);
                dataBaseConnector.createTableForChart(orderNdate);

                dataBaseConnector.dumpToCSV(orderNdate, "chart_" + nameNdate);

            }
        } else {
            popupAlert("You're not permitted make a chart backup.", "You're not a customer!");
        }
    }

    public void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog((Stage) fileMenu.getScene().getWindow());
        if (file != null) {
//            openFile(file);
        }
    }


    public void handleCommitButtonClick(ActionEvent event) {
        if (Observer.priviliges <= 2) {
            DataBaseConnector dataBaseConnector = new DataBaseConnector();
            DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String orderNdate =  "tmp_order_" + df.format(new Date()).toString();

            dataBaseConnector.dropTableIfExists(orderNdate);
            dataBaseConnector.createTableForChart(orderNdate);
            for (ItemPriceExtended ipe : Observer.observableList) {
                dataBaseConnector.insertTmpOrder(orderNdate, ipe);
            }
            dataBaseConnector.finalizeOrder(moneyField.getText(), orderNdate);
            popupAlert("Przekazano zamowienie do realizacji. Nazwa zamowienia: " + orderNdate, "Realizacja zamowienia");
        } else {
            popupAlert("Jesteś widzem! W celu złożenia zamowienia zaloguj sie jako customer ", "Realizacja zamowienia");
        }
    }

    public void handleAffordButtonClick(ActionEvent event) {

        if (moneyField.getText() == null || moneyField.getText().equals("")) {
            popupAlert("Brak srodkow!", "Dostepne srodki");
            return;
        } else {
            setTotalValue(0.0);
            for (ItemPriceExtended ipe : Observer.observableList) {
                addToTotalValue(ipe.getWartosc());
            }
            if (Double.parseDouble(moneyField.getText()) >= getTotalValue()) {
                popupAlert("Srodki sa wystarczajace. Mozna przejsc do zamowienia", "Dostepne srodki");
                orderBtn.setDisable(false);
            } else {
                popupAlert("Niewystarczajce srodki", "Dostepne srodki");
                orderBtn.setDisable(true);
            }
        }
    }


    public void handleCancelButtonClick(ActionEvent event) {
        if (popupAlert("Na pewno chcesz zrezygnowac?", "Rezygnacja z zamowienia")) {
            Observer.observableList.clear();
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }

    public boolean popupAlert(String msg, String head) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK, ButtonType.CANCEL);
        Stage owner = (Stage) fileMenu.getScene().getWindow();
        alert.setTitle("Information");
        alert.setHeaderText(head);
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
