package org.example;

import TCP.Threads;

import java.net.*;

public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;
        System.out.println("Запуск сервера");
        try {
            serverSocket = new ServerSocket(2525);
            Threads t;

            do {
                clientAccepted = serverSocket.accept();
                t = new Threads(clientAccepted);
                if (clientAccepted != null) {
                    t.start();
                }
            } while (t.isAlive());
        } catch (Exception e) {
        } finally {
            try {
                serverSocket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
