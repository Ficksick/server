package TCP;

import Service.UserService;
import Models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Threads extends Thread {
    Socket clientAccepted;

    public Threads(Socket clientSocket) {
        this.clientAccepted = clientSocket;
    }

    @Override
    public void run() {
        ObjectInputStream sois;
        ObjectOutputStream soos;

        User user = new User();
        UserService userService = new UserService();
        User userCheck = new User();
        try {
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Клиент " + getName());

            while (true) {
                String operation = (String) sois.readObject();
                switch (operation) {
                    case "LOGIN":{
                        System.out.println(user.toString());
                        user = (User) sois.readObject();
                        userCheck = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                        soos.writeObject(userCheck);
                        System.out.println(userCheck.toString());
                        break;
                    }
                    case "REGISTRATION":{
                        user = new User();
                        user = (User) sois.readObject();
                        System.out.println(user.toString());
                        userCheck = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());

                        if (userCheck.getUsername() == null) {
                            userService.saveUser(user);
                            soos.writeObject("OK");
                        } else {
                            soos.writeObject("ERROR");
                        }
                        break;
                    }
                    case "VIEW_USERS_ADMIN":{
                        List<User> users = new ArrayList<>();
                        users = userService.findAll();
                        soos.writeObject(users);
                        break;
                    }
                    case "REDACT_USER_ADMIN":{

                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            try {
                clientAccepted.close();
                sois.close();
                soos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

