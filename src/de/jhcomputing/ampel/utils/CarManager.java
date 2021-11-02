package de.jhcomputing.ampel.utils;

import de.jhcomputing.ampel.obj.Car;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@Getter
@Setter
public class CarManager {

    private JFrame frame;
    private ArrayList<Car> carArrayList;

    public CarManager(JFrame frame) {
        this.frame = frame;
    }

    public void spawn() {
        Car carRL = new Car(this.frame, "lr", frame.getWidth()+50, 190);
        Car carLR = new Car(this.frame, "rl", -200, 280);
        Car carBT = new Car(this.frame, "bt", 315, frame.getHeight()+50);
        Car carTB = new Car(this.frame, "tb", 220, -200);
        this.carArrayList = new ArrayList<>(){{
            add(carRL);
            add(carLR);
            add(carBT);
            add(carTB);
        }};

        carRL.move.accept(Car.Direction.LEFT);
        carLR.move.accept(Car.Direction.RIGHT);
        carTB.move.accept(Car.Direction.BOTTOM);
        carBT.move.accept(Car.Direction.TOP);

        this.carArrayList.forEach(car -> this.frame.add(car.getCarImage()));
    }

    public void movement() {
        carArrayList.forEach(car -> {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                boolean await = false;
                @Override
                public void run() {
                    int x = car.getCarImage().getX();
                    int y = car.getCarImage().getY();
                    if (x <= -200 || x >= frame.getWidth() || y <= -200 || y >= frame.getHeight()) {
                        if(!await) {
                            car.respawn();
                            await = true;
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    await = false;
                                }
                            }, 1000*3);
                        }
                    } else {
                        //System.out.println("DEBUG");
                    }
                }
            }, 1000*3, 10);
        });
    }

}
