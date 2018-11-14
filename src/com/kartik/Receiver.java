package com.kartik;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Receiver {
    private ServerSocket receiver;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String packet, data = "";
    private int i = 0;
    private StringBuilder tempString = new StringBuilder();
    private Random rand = new Random(5);
    private void run() {
        try {
            receiver = new ServerSocket(9999);
            System.out.println("Waiting for connection with Sender ...");
            Socket connection = receiver.accept();
            System.out.println("Connected with Sender. \n");
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            out.writeObject("connected.");

            int sequence = 0;
            do {
                try {
                    packet = (String) in.readObject();
                    if (Integer.valueOf(packet.substring(0, 1)) == sequence) {//takes 1st char of pkt to check seq
                        //concat input received into data string, leaving out the 1st character that denotes seq
                        tempString.append(packet.substring(1));
                        data = tempString.toString();
                        sequence = (sequence == 0) ? 1 : 0; //seq 1 then 0 and vice versa
                        System.out.println("Receiver - " + packet);
                    } else {
                        System.out.println("Receiver - " + packet + " ( Duplicate Data )");
                    }
                    //below code is written to intentionally wrong seq to give wrong or no acknowledgement. Done for demo purpose
                    int randNum = rand.nextInt(3) + 1;
                    if (i < randNum) {
                        out.writeObject(String.valueOf(sequence));
                        i++;
                    } else {
                        out.writeObject(String.valueOf((sequence + 1) % 2));
                        i = 0;
                    }
                } catch (Exception ignored) {
                }
            } while (!packet.equals("end"));
            System.out.println("Data received - " + data);
        } catch (Exception ignored) {
        } finally {
            try {
                in.close();
                out.close();
                receiver.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void main(String args[]) {
        Receiver s = new Receiver();
        s.run();
    }
}

