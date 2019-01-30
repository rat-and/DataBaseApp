/**
 * For the time being it's made as a static class
 * just to make it work by the dead line :D
 * Helps in communication between Chart and PriceComparer
 * - stores the items meant to be in the chart
 * also stores sensitive info about login and password :(
 */
package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Observer {
    public static ObservableList<ItemPriceExtended> observableList = FXCollections.observableArrayList();
    public static String[] users = {"root", "employee", "customer", "viewer"};
    public static String[] passes = {"mysql", "ilovemyjob", "", ""};
    public static int priviliges = 3;

    public static void setPriviliges(int priviliges) {
        Observer.priviliges = priviliges;
    }
}
