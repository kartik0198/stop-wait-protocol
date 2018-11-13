package com.kartik;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {
    private Socket sender;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String msg;
    private int i = 0;


    private void run() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Waiting for connection with Receiver...");
            sender = new Socket("localhost", 9999);
            out = new ObjectOutputStream(sender.getOutputStream());
            out.flush();
            in = new ObjectInputStream(sender.getInputStream());
            String str = (String) in.readObject();
            System.out.println("Receiver is now " + str);
            System.out.println("Enter the data to send : ");
            String packet = sc.nextLine();
            int n = packet.length();
            int sequence = 0;
            do {
                try {
                    if (i < n) {
                        msg = String.valueOf(sequence); //concatenating first with seq num
                        msg = msg.concat(packet.substring(i, i + 1));
                    } else if (i == n) {
                        msg = "end";
                        out.writeObject(msg);
                        break;
                    }
                    out.writeObject(msg); //stores/writes the msg in buffer
                    sequence = (sequence == 0) ? 1 : 0;
                    out.flush(); //actually writes and clears the buffer
                    System.out.println("Data sent - " + msg);
                    String acknowledgement = (String) in.readObject();
                    System.out.println("Waiting for acknowledgement from Receiver...");
                    if (acknowledgement.equals(String.valueOf(sequence))) {
                        i++;
                        System.out.println("Receiver - " + "Packet received \n");
                    } else {
                        System.out.println("Time out. Sending same data again.\n");
                        sequence = (sequence == 0) ? 1 : 0;
                    }
                } catch (Exception ignored) {
                }
            } while (i < n + 1);
            System.out.println("Data transmission completed");
        } catch (IOException | ClassNotFoundException ignored) {
        } finally {
            try {
                in.close();
                out.close();
                sender.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void main(String args[]) {
        Sender s = new Sender();
        s.run();
    }
}
