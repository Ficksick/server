package TCP;

import Models.*;
import Service.*;
import org.w3c.dom.ls.LSOutput;

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
        Hall hall = new Hall();
        Ticket ticket = new Ticket();
        Screening screening = new Screening();
        Film film = new Film();

        UserService userService = new UserService();
        HallService hallService = new HallService();
        TicketService ticketService = new TicketService();
        ScreeningService screeningService = new ScreeningService();
        FilmService filmService = new FilmService();

        User userCheck = new User();
        try {
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("Клиент " + getName());

            while (clientAccepted.isConnected()) {
                String operation = (String) sois.readObject();
                switch (operation) {
                    case "LOGIN": {
                        System.out.println(user.toString());
                        user = (User) sois.readObject();
                        System.out.println(user.toString());
                        userCheck = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                        soos.writeObject(userCheck);
                        System.out.println(userCheck.toString());
                        break;
                    }
                    case "REGISTRATION": {
                        //user = new User();
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
                    case "VIEW_USERS_ADMIN": {
                        List<User> users = new ArrayList<>();
                        users = userService.findAll();
                        soos.writeObject(users);
                        break;
                    }
                    case "REDACT_USER_ADMIN": {
                        user = (User) sois.readObject();
                        User userToRedact = new User();
                        userToRedact = userService.findUser(user.getUser_id());

                        User newUser = new User();
                        newUser = (User) sois.readObject();

                        if (newUser.getUsername() != null) {
                            String username = newUser.getUsername();
                            userToRedact.setUsername(username);
                        }
                        if (newUser.getEmail() != null) {
                            String email = newUser.getEmail();
                            userToRedact.setEmail(email);
                        }
                        if (newUser.getPassword() != null) {
                            String password = newUser.getPassword();
                            userToRedact.setPassword(password);
                        }
                        if (newUser.getRole() != null) {
                            String role = newUser.getRole();
                            userToRedact.setRole(role);
                        }

                        userService.updateUser(userToRedact);
                        soos.writeObject("OK");

                        break;
                    }
                    case "DELETE_USER_ADMIN": {
                        user = (User) sois.readObject();
                        userService.deleteUser(user);
                    }
                    case "VIEW_HALL_ADMIN": {
                        List<Hall> halls = new ArrayList<>();
                        halls = hallService.findAll();
                        //System.out.println(halls);
                        soos.writeObject(halls);
                        break;
                    }
                    case "CREATE_HALL_ADMIN": {
                        hall = (Hall) sois.readObject();
                        System.out.println(hall.toString());
                        hallService.saveHall(hall);
                        soos.writeObject("OK");
                        break;
                    }
                    case "REDACT_HALL_ADMIN": {
                        hall = (Hall) sois.readObject();

                        Hall hallToRedact = hallService.findHall(hall.getHall_id());

                        Hall newHall = (Hall) sois.readObject();

                        if (newHall.getHallName() != null) {
                            String newName = newHall.getHallName();
                            hallToRedact.setHallName(newName);
                        }
                        if (newHall.getCapacity() != 0) {
                            int newCapacity = newHall.getCapacity();
                            hallToRedact.setCapacity(newCapacity);
                        }

                        hallService.updateHall(hallToRedact);

                        soos.writeObject("OK");
                        break;
                    }
                    case "DELETE_HALL_ADMIN": {
                        hall = (Hall) sois.readObject();
                        hallService.deleteHall(hall);
                        break;
                    }
                    case "CREATE_SCREENING_ADMIN": {
                        List<Screening> screenings = new ArrayList<>();
                        screenings = screeningService.findAll();
                        //System.out.println(screenings);
                        soos.writeObject(screenings);

                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        soos.writeObject(films);

                        Hall newHall = new Hall();
                        hall = (Hall) sois.readObject();
                        newHall = hallService.findHall(hall.getHall_id());
                        soos.writeObject(newHall);

                        screening = (Screening) sois.readObject();
                        System.out.println(screening.toString());

                        if(screening.getDate() == null){
                            soos.writeObject("EXIST");
                        }else{
                            soos.writeObject("OK");
                            screeningService.saveScreening(screening);
                        }

                        break;
                    }
                    case "VIEW_SCREENING_ADMIN": {
                        List<Screening> screenings = new ArrayList<>();
                        screenings = screeningService.findAll();
                        soos.writeObject(screenings);
                        break;
                    }
                    case "REDACT_SCREENING_ADMIN": {
                        Screening screeningToRedactFromClient = (Screening) sois.readObject();
                        System.out.println(screeningToRedactFromClient);

                        Screening screeningToRedact = screeningService.findByID(screeningToRedactFromClient.getScreening_id());
                        System.out.println(screeningToRedact);

                        List<Screening> screenings = new ArrayList<>();
                        screenings = screeningService.findAll();
                        System.out.println(screenings);
                        soos.writeObject(screenings);

                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        //System.out.println(films);
                        soos.writeObject(films);

                        Hall newHall = new Hall();
                        hall = (Hall) sois.readObject();
                        newHall = hallService.findHall(hall.getHall_id());
                        soos.writeObject(newHall);

                        screening = (Screening) sois.readObject();
                        //System.out.println(screening.toString());

                        if(screening.getDate() == null){
                            soos.writeObject("EXIST");
                        }else{
                            soos.writeObject("OK");
                            screeningService.saveScreening(screening);
                        }
                        break;
                    }
                    case "DELETE_SCREENING_ADMIN": {
                        screening = (Screening) sois.readObject();
                        screeningService.deleteScreening(screening);
                        break;
                    }
                    case "VIEW_FILMS_ADMIN": {
                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        soos.writeObject(films);
                        break;
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

