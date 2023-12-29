module logisticssystem {
    exports lu.ics.se.models.classes;
    exports lu.ics.se.controllers;
    exports lu.ics.se;
    exports lu.ics.se.models.enums;

    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens lu.ics.se.controllers to javafx.fxml;
}
