package Controllers;

import dbConnection.DataBaseConnector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private boolean isUsernameFiledFocused = false;
    private boolean isPasswordFiledFocused = false;

    @FXML
    private ResourceBundle resourceBundle;

    @FXML
    private URL location;

    @FXML
    private TextField usernameFiled;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setDisable(true);

        usernameFiled.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (usernameFiled.isFocused()) {
                    setUsernameFiledFocused(true);
                    if (isPasswordFiledFocused && isUsernameFiledFocused) {
                        loginBtn.setDisable(false);
                    }
                }
            }
        });

        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (passwordField.isFocused()) {
                    setPasswordFiledFocused(true);
                    if (isPasswordFiledFocused && isUsernameFiledFocused) {
                        loginBtn.setDisable(false);
                    }
                }
            }
        });
    }

    public void setPasswordFiledFocused(boolean passwordFiledFocused) {
        isPasswordFiledFocused = passwordFiledFocused;
    }

    public boolean isPasswordFiledFocused() {
        return isPasswordFiledFocused;
    }

    public void setUsernameFiledFocused(boolean usernameFiledFocused) {
        isUsernameFiledFocused = usernameFiledFocused;
    }

    public boolean isUsernameFiledFocused() {
        return isUsernameFiledFocused;
    }

    public void handleLogin() {
        if (usernameFiled.getText() != null) {
            System.out.println(Observer.users.length);
            for (int i = 0; i < Observer.users.length; i++) {
                if (usernameFiled.getText().equals(Observer.users[i]) && passwordField.getText().equals(Observer.passes[i])) {
                    DataBaseConnector.setDBUSER(Observer.users[i]);
                    DataBaseConnector.setDBPASS(Observer.passes[i]);
                    Observer.setPriviliges(i);
                    if (popupAlert("Now you are logged as a " + Observer.users[i], "Login success")) {
                        Stage stage = (Stage) loginBtn.getScene().getWindow();
                        stage.close();
                    } else {
                        Stage stage = (Stage) loginBtn.getScene().getWindow();
                        stage.close();
                    }
                    return;
                }
            }
            popupAlert("You may try again", "Login failure");
        }
    }



    public boolean popupAlert(String msg, String head) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Information", ButtonType.OK, ButtonType.CANCEL);
        Stage owner = (Stage) loginBtn.getScene().getWindow();
        alert.setTitle("Login information");
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
