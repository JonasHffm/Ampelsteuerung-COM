package de.jhcomputing.ampel.serial_control;

import de.jhcomputing.ampel.obj.Lane;
import de.jhcomputing.ampel.obj.Trafficlight;
import de.jhcomputing.ampel.obj.TrafficlightWalk;
import de.jhcomputing.ampel.utils.Data;
import lombok.Getter;
import lombok.Setter;
import serialio.Serial;

import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class SerialControl extends Thread{

    private Lane tb_l;
    private Lane tb_r;
    private Lane rl_l;
    private Lane rl_r;

    private final int baud = 9600;
    private final int dataBits = 8;
    private final int stopBits = 1;
    private final int parity = 0;

    private ArrayList<Serial> serials;
    public ArrayList<Runnable> abfolge;

    public SerialControl() {
        abfolge = new ArrayList<>();
        serials = new ArrayList<>();

        //abfolge festlegen
        abfolge.add(new Thread(sendeBetriebsbereitschaft));
        abfolge.add(new Thread(sendeEmpfangsbereitschaft));
        abfolge.add(new Thread(warteAufACK));
        abfolge.add(new Thread(warteAufNachricht));

        tb_l = Data.laneMap.get("TB-L");
        tb_r = Data.laneMap.get("TB-R");
        rl_l = Data.laneMap.get("RL-L");
        rl_r = Data.laneMap.get("RL-R");
    }

    @Override
    public void run() {
        open();
        abfolge.forEach(thread -> {
            Thread threadObj = (Thread) thread;
            threadObj.start();
            try {
                threadObj.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void open() {
        try {
            Serial serial = new Serial(Data.comPort, baud, dataBits, stopBits, parity);
            serial.open();
            serials.add(serial);
            System.out.println("Port geöffnet: " + Data.comPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Runnable sendeBetriebsbereitschaft = () -> {
        serials.get(0).setDTR(true);
        System.out.println("Info: Betriebsbereitschaft wurde gesendet!");
    };

    public Runnable sendeEmpfangsbereitschaft = () -> {
        serials.get(0).setRTS(true);
        System.out.println("Info: Empfangsbereitschaft wurde gesendet!");
    };

    public Runnable warteAufACK = () -> {
        System.out.println("Info: Warte auf ACK...");
        try {
            while (serials.get(0).read() != 6);
        } catch (IOException e) {
        }
        System.out.println("Info: ACK Empfangen");
    };

    public Runnable warteAufNachricht = () -> {
        System.out.println("Info: Warte auf Text...");
        System.out.println();
        int readByte = 0;
        try {
            while ((readByte = serials.get(0).read()) != -1) {
                char input = (char)readByte;
                System.out.println(input);
                if(input == 'A') {
                    tb_l.getTrafficlight().setMode(Trafficlight.Mode.GREEN);
                }else if(input == 'B') {
                    tb_l.getTrafficlight().setMode(Trafficlight.Mode.YELLOW);
                }else if(input == 'C') {
                    tb_l.getTrafficlight().setMode(Trafficlight.Mode.RED_YELLOW);
                }else if(input == 'D') {
                    tb_l.getTrafficlight().setMode(Trafficlight.Mode.RED);
                }else if(input == 'a') {
                    tb_l.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.RED);
                }else if(input == 'b') {
                    tb_l.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.GREEN);
                }

                if(input == 'E') {
                    tb_r.getTrafficlight().setMode(Trafficlight.Mode.GREEN);
                }else if(input == 'F') {
                    tb_r.getTrafficlight().setMode(Trafficlight.Mode.YELLOW);
                }else if(input == 'G') {
                    tb_r.getTrafficlight().setMode(Trafficlight.Mode.RED_YELLOW);
                }else if(input == 'H') {
                    tb_r.getTrafficlight().setMode(Trafficlight.Mode.RED);
                }else if(input == 'c') {
                    tb_r.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.RED);
                }else if(input == 'd') {
                    tb_r.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.GREEN);
                }

                if(input == 'I') {
                    rl_l.getTrafficlight().setMode(Trafficlight.Mode.GREEN);
                }else if(input == 'J') {
                    rl_l.getTrafficlight().setMode(Trafficlight.Mode.YELLOW);
                }else if(input == 'K') {
                    rl_l.getTrafficlight().setMode(Trafficlight.Mode.RED_YELLOW);
                }else if(input == 'L') {
                    rl_l.getTrafficlight().setMode(Trafficlight.Mode.RED);
                }else if(input == 'e') {
                    rl_l.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.RED);
                }else if(input == 'f') {
                    rl_l.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.GREEN);
                }

                if(input == 'M') {
                    rl_r.getTrafficlight().setMode(Trafficlight.Mode.GREEN);
                }else if(input == 'N') {
                    rl_r.getTrafficlight().setMode(Trafficlight.Mode.YELLOW);
                }else if(input == 'O') {
                    rl_r.getTrafficlight().setMode(Trafficlight.Mode.RED_YELLOW);
                }else if(input == 'P') {
                    rl_r.getTrafficlight().setMode(Trafficlight.Mode.RED);
                }else if(input == 'g') {
                    rl_r.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.RED);
                }else if(input == 'h') {
                    rl_r.getTrafficlightWalk().setMode(TrafficlightWalk.Mode.GREEN);
                }
            }
        } catch (IOException e) {
        }
        System.out.println();
        System.out.println("ETX Empfangen!");
        System.out.println("Die Verbindung wurde abgebrochen!");
    };

    public void sendWalkTrafficlightCode(String trafficlight) {
        try {
            if(trafficlight.equals("T")) {
                serials.get(0).write("W");
            }else if(trafficlight.equals("R")) {
                serials.get(0).write("X");
            }else if(trafficlight.equals("B")) {
                serials.get(0).write("Y");
            }else if(trafficlight.equals("L")) {
                serials.get(0).write("Z");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            serials.get(0).close();
        } catch (IOException e) {
        }
    }
}
