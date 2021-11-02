package de.jhcomputing.ampel.obj;

import de.jhcomputing.ampel.utils.LaneType;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import static de.jhcomputing.ampel.obj.Trafficlight.Mode.RED;
import static de.jhcomputing.ampel.obj.Trafficlight.Mode.RED_YELLOW;

@Getter
@Setter
public class Lane {

    private LaneType laneType;
    private Car car;
    private Trafficlight trafficlight;
    private TrafficlightWalk trafficlightWalk;

    public Lane(JFrame jFrame, LaneType laneType, Car car) {
        this.laneType = laneType;
        this.car = car;

        switch (laneType) {
            case RIGHT_LEFT_LEFT -> {
                this.trafficlight = new Trafficlight(jFrame, 100, 280);
                trafficlight.spawn(Trafficlight.Place.LEFT);

                this.trafficlightWalk = new TrafficlightWalk(jFrame, 423, 242);
                this.trafficlightWalk.spawn();
            }
            case RIGH_LEFT_RIGHT -> {
                this.trafficlight = new Trafficlight(jFrame, 430, 50);
                trafficlight.spawn(Trafficlight.Place.RIGHT);

                this.trafficlightWalk = new TrafficlightWalk(jFrame, 170, 244);
                this.trafficlightWalk.spawn();
            }
            case TOPBOTTOM_RIGHT -> {
                this.trafficlight = new Trafficlight(jFrame, 410, 300);
                trafficlight.spawn(Trafficlight.Place.BOTTOM);

                this.trafficlightWalk = new TrafficlightWalk(jFrame, 292, 374);
                this.trafficlightWalk.spawn();
            }
            case TOPBOTTOM_LEFT -> {
                this.trafficlight = new Trafficlight(jFrame, 180, 20);
                trafficlight.spawn(Trafficlight.Place.TOP);

                this.trafficlightWalk = new TrafficlightWalk(jFrame, 292, 121);
                this.trafficlightWalk.spawn();
            }
        }



        checkTrafficlightMode();
    }

    public void checkTrafficlightMode() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (trafficlight.getMode().equals(RED) || trafficlight.getMode().equals(RED_YELLOW)) {
                    car.stopAtLine();
                }else {
                    car.driveAgain();
                }
            }
        }, 0, 1);
    }

}
