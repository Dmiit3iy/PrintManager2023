module org.dmiitr3iy {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;

    opens org.dmiitr3iy to javafx.fxml;
    exports org.dmiitr3iy;
    exports org.dmiitr3iy.controller;
    opens org.dmiitr3iy.controller to javafx.fxml;
}
