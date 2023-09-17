module org.dmiitr3iy {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;
    requires org.apache.logging.log4j.slf4j;
    requires org.slf4j;

    opens org.dmiitr3iy to javafx.fxml;
    exports org.dmiitr3iy;
    exports org.dmiitr3iy.controller;
    opens org.dmiitr3iy.controller to javafx.fxml;
}
