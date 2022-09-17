module dev.carter.financetracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;

    opens dev.carter.financetracker to javafx.fxml;
    exports dev.carter.financetracker;
    exports dev.carter.financetracker.controllers;
    opens dev.carter.financetracker.controllers to javafx.fxml;
}