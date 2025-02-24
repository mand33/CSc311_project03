module com.example.csc311_assignment03 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.csc311_assignment03 to javafx.fxml;
    exports com.example.csc311_assignment03;
}