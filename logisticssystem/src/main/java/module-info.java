module logisticssystem {
    exports lu.ics.se.models;
    exports lu.ics.se.controllers;
    exports lu.ics.se;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires transitive javafx.graphicsEmpty;

    opens lu.ics.se.controllers to javafx.fxml;
}
