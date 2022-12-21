module com.example.bp_is_restaurace {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.bp_is_restaurace to javafx.fxml;
    exports com.example.bp_is_restaurace;
}