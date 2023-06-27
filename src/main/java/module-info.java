module com.example.javafxexemplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.javafxexemplo to javafx.fxml;
    opens com.example.javafxexemplo.controller to javafx.fxml;
    opens com.example.javafxexemplo.model to javafx.fxml;
    exports com.example.javafxexemplo;
}