module com.goclient.goclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.goclient.goclient to javafx.fxml;
    exports com.goclient.goclient;
}