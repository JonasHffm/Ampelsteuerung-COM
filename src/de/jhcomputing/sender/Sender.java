package de.jhcomputing.sender;

import serialio.Serial;

import java.util.Scanner;

public class Sender {

    public Serial connection;

    public Sender(){
        System.out.println("Ich bin der Sender!");
        try {
            connection = new Serial("COM2", 9600, 8, 1, 0);
            if(!connection.open()){
                System.out.println("Die COM-Schnittstelle wurde nicht gefunden!");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Die COM-Schnittstelle konnte nicht geöffnet werden");
        }
        System.out.println("Der Port wurde geöffnet!");
    }

    public void senden() throws Exception {
        System.out.println("Warte auf Empfänger(DSR)!");
        while(!connection.isDSR()){
            Thread.sleep(1000);
        }
        System.out.println("Empfänger ist betriebsbereit!");
        System.out.println("Warte auf Empfänger(CTS)!");
        while(!connection.isCTS()){
            Thread.sleep(1000);
        }

        System.out.println("Empfänger ist betriebsbereit!");
        System.out.println("Sende ACK!");
        connection.write(6);
        System.out.println("ACK gesendet!");

        Scanner sc = new Scanner(System.in);
        System.out.println("Text:");
        String temp = "";
        do{
            connection.write(temp+"\n");
        }while(!(temp = sc.nextLine()).equals("over"));
        System.out.println("Sende ETX!");
        connection.write(3);
        System.out.println("ETX gesendet");
        System.out.println("Übertragung beendet!");
    }



    public static void main(String[]args) throws Exception {
        Sender send = new Sender();
        send.senden();
    }

}

