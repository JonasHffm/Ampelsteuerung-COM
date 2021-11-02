package de.jhcomputing.ampel.obj;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class Trafficlight {

    private int locX, locY;
    private Mode mode;

    private JLabel main_part;
    private JLabel red_light;
    private JLabel yellow_light;
    private JLabel green_light;

    private JFrame frame;

    public Trafficlight(JFrame frame, int locX, int locY) {
        this.locX = locX;
        this.locY = locY;

        this.mode = Mode.RED;

        ImageIcon punkt_gelb = new ImageIcon("img/ampel_rechts/PunktGelb.png");
        ImageIcon punkt_rot = new ImageIcon("img/ampel_rechts/PunktRot.png");
        ImageIcon punkt_gruen = new ImageIcon("img/ampel_rechts/PunktGruen.png");

        this.main_part = new JLabel();
        this.red_light = new JLabel();
        red_light.setIcon(punkt_rot);
        this.yellow_light = new JLabel();
        yellow_light.setIcon(punkt_gelb);
        this.green_light = new JLabel();
        green_light.setIcon(punkt_gruen);

        this.frame = frame;

        updateLight();
    }

    public void spawn(Place place) {
        switch (place) {
            case RIGHT -> {
                ImageIcon imageIcon = new ImageIcon("img/ampel_rechts/AmpelDefault.png");
                main_part.setIcon(imageIcon);

                red_light.setSize(200, 200);
                red_light.setVisible(true);
                yellow_light.setSize(200, 200);
                yellow_light.setVisible(true);
                green_light.setSize(23, 69);
                green_light.setVisible(true);

                main_part.setLocation(locX, locY);
                main_part.setSize(200, 200);
                main_part.setVisible(true);

                red_light.setLocation((locX+5), (locY+23));
                green_light.setLocation((locX+45), (locY+42));
                yellow_light.setLocation((locX+25), (locY+2));

                frame.add(red_light);
                frame.add(yellow_light);
                frame.add(green_light);
                frame.add(main_part);
            }
            case LEFT -> {
                ImageIcon imageIcon = new ImageIcon("img/ampel_rechts/AmpelDefault.png");
                main_part.setIcon(imageIcon);

                red_light.setSize(200, 200);
                red_light.setVisible(true);
                yellow_light.setSize(200, 200);
                yellow_light.setVisible(true);
                green_light.setSize(23, 69);
                green_light.setVisible(true);

                main_part.setLocation(locX, locY);
                main_part.setSize(200, 200);
                main_part.setVisible(true);

                red_light.setLocation((locX+45), (locY+22));
                green_light.setLocation((locX+5), (locY+42));
                yellow_light.setLocation((locX+25), (locY+2));

                frame.add(red_light);
                frame.add(yellow_light);
                frame.add(green_light);
                frame.add(main_part);
            }
            case TOP -> {
                ImageIcon imageIcon = new ImageIcon("img/ampel_top/AmpelDefault.png");
                main_part.setIcon(imageIcon);

                red_light.setSize(23, 69);
                red_light.setVisible(true);
                yellow_light.setSize(23, 69);
                yellow_light.setVisible(true);
                green_light.setSize(23, 69);
                green_light.setVisible(true);

                main_part.setLocation(locX, locY);
                main_part.setSize(200, 200);
                main_part.setVisible(true);

                red_light.setLocation((locX), (locY+110));
                green_light.setLocation((locX-1), (locY+20));
                yellow_light.setLocation((locX), (locY+67));

                frame.add(red_light);
                frame.add(yellow_light);
                frame.add(green_light);
                frame.add(main_part);
            }
            case BOTTOM -> {
                ImageIcon imageIcon = new ImageIcon("img/ampel_top/AmpelDefault.png");
                main_part.setIcon(imageIcon);

                red_light.setSize(23, 69);
                red_light.setVisible(true);
                yellow_light.setSize(23, 69);
                yellow_light.setVisible(true);
                green_light.setSize(23, 69);
                green_light.setVisible(true);

                main_part.setLocation(locX, locY);
                main_part.setSize(200, 200);
                main_part.setVisible(true);

                red_light.setLocation((locX-1), (locY+70));
                green_light.setLocation((locX-1), (locY+20*3+5));
                yellow_light.setLocation((locX-1), (locY+70));

                frame.add(red_light);
                frame.add(yellow_light);
                frame.add(green_light);
                frame.add(main_part);
            }
        }

    }

    public void updateLight() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mode.equals(Mode.RED)) {
                    yellow_light.setVisible(false);
                    green_light.setVisible(false);
                    red_light.setVisible(true);
                }else if(mode.equals(Mode.RED_YELLOW)) {
                    yellow_light.setVisible(true);
                    green_light.setVisible(false);
                    red_light.setVisible(true);
                }else if(mode.equals(Mode.YELLOW)) {
                    yellow_light.setVisible(true);
                    green_light.setVisible(false);
                    red_light.setVisible(false);
                }else if(mode.equals(Mode.GREEN)) {
                    yellow_light.setVisible(false);
                    green_light.setVisible(true);
                    red_light.setVisible(false);
                }
            }
        }, 0, 10);
    }

    public enum Place {
        TOP, BOTTOM, RIGHT, LEFT
    }

    public enum Mode {
        GREEN, YELLOW, RED, RED_YELLOW
    }
}
