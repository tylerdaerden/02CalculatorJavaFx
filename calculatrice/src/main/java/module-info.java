module iramps {
    requires javafx.controls;
    requires javafx.fxml;

    opens iramps to javafx.fxml;
    exports iramps;
}
