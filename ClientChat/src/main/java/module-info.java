module pl.limescode {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.limescode.clientchat to javafx.graphics;
    opens pl.limescode.clientchat.controllers to javafx.fxml;
    exports pl.limescode.clientchat;
}