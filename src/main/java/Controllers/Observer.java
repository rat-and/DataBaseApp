/**
 * For the time being it's made as a static class
 * just to make it work by the dead line :D
 * Helps in communication between Chart and PriceComparer
 * - stores the items meant to be in the chart
 */
package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Observer {
    public static ObservableList<ItemPriceExtended> observableList = FXCollections.observableArrayList();

}
