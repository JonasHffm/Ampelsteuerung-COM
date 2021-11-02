package de.jhcomputing.ampel.utils;

import de.jhcomputing.ampel.autonom.AutonomExmaple;
import de.jhcomputing.ampel.obj.Lane;
import de.jhcomputing.ampel.serial_control.SerialControl;
import de.jhcomputing.ampel.window.MainWindow;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Data {

    public static Map<String, Lane> laneMap = new HashMap<>();
    public static boolean automationActive = true;

    public static String comPort = "";

    private MainWindow mainWindow;

    private AutonomExmaple autonomExmaple;
    private SerialControl serialControl;

    public Data() {
        this.mainWindow = new MainWindow();
        this.autonomExmaple = new AutonomExmaple();
    }

}
