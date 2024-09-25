module com.usta {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.usta.controllers to javafx.fxml;



    exports com.usta;
    exports com.usta.controllers;
    exports com.usta.models.usuarios;
    exports com.usta.models.mascota;    

    //exports com.usta.models;
}
