package de.jhcomputing.ampel.obj;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class TrafficlightWalk {

    private int locX, locY;
    private Mode mode;

    private JLabel main_part;
    private JLabel red_light;
    private JLabel green_light;

    private JFrame frame;

    public TrafficlightWalk(JFrame frame, int locX, int locY) {
        this.locX = locX;
        this.locY = locY;

        this.mode = Mode.RED;

        ImageIcon punkt_rot = new ImageIcon("img/ampel_rechts/PunktRot.png");
        ImageIcon punkt_gruen = new ImageIcon("img/ampel_rechts/PunktGruen.png");

        this.main_part = new JLabel();
        this.red_light = new JLabel();
        this.green_light = new JLabel();

        red_light.setIcon(punkt_rot);
        green_light.setIcon(punkt_gruen);

        this.frame = frame;

        updateLight();
    }

    public void spawn() {
        ImageIcon imageIcon = new ImageIcon("img/ampel_walk/AmpelWalkDefault.png");
        main_part.setIcon(imageIcon);

        red_light.setSize(23, 69);
        red_light.setVisible(true);
        green_light.setSize(23, 69);
        green_light.setVisible(true);

        main_part.setLocation(locX, locY);
        main_part.setSize(25, 50);
        main_part.setVisible(true);

        red_light.setLocation((locX-1), (locY+4));
        green_light.setLocation((locX-1), (locY-24));

        frame.add(red_light);
        frame.add(green_light);
        frame.add(main_part);
    }

    public void updateLight() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mode.equals(Mode.RED)) {
                    green_light.setVisible(false);
                    red_light.setVisible(true);
                }else if(mode.equals(Mode.GREEN)) {
                    green_light.setVisible(true);
                    red_light.setVisible(false);
                }
            }
        }, 0, 10);
    }

    public enum Mode {
        GREEN, RED
    }
}
