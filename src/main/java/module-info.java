module cz.tskopec.marbles {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires org.locationtech.jts;


    opens cz.tskopec.marbles to javafx.fxml;
    exports cz.tskopec.marbles;
}