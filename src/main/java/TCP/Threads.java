package TCP;

import Models.*;
import Service.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
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
                        user = (User) sois.readObject();
                        userCheck = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                        soos.writeObject(userCheck);
                        break;
                    }
                    case "REGISTRATION": {
                        user = (User) sois.readObject();
                        userCheck = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());

                        if (userCheck.getUsername() == null) {
                            userService.saveUser(user);
                            soos.writeObject("OK");
                        } else {
                            soos.writeObject("ERROR");
                        }
                        break;
                    }
                    case "VIEW_USERS_ADMIN", "VIEW_ACCOUNT_USER": {
                        List<User> users = new ArrayList<>();
                        users = userService.findAll();
                        soos.writeObject(users);
                        break;
                    }
                    case "REDACT_ACCOUNT_USER": {
                        user = (User) sois.readObject();
                        userService.updateUser(user);
                        soos.writeObject("OK");
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
                    case "DELETE_USER_ADMIN", "DELETE_ACCOUNT_USER": {
                        user = (User) sois.readObject();

                        List<Ticket> tickets = ticketService.findAll();

                        for (Ticket ticketUser : tickets) {
                            if (ticketUser.getUser().getUser_id() == user.getUser_id()) {
                                ticketService.deleteTicket(ticketUser);
                            }
                        }

                        userService.deleteUser(user);
                        soos.writeObject("OK");
                        break;
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

                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        soos.writeObject(films);

                        Hall newHall = new Hall();
                        hall = (Hall) sois.readObject();
                        newHall = hallService.findHall(hall.getHall_id());
                        soos.writeObject(newHall);

                        screening = (Screening) sois.readObject();
                        System.out.println("from server \n" + screening.toString());

                        int check = 0;

                        for (Screening screeningCheck : screenings) {
                            if (screeningCheck.getHall().getHall_id() == screening.getHall().getHall_id()) {
                                if (screeningCheck.getDate().compareTo(screening.getDate()) == 0) {
                                    if (screeningCheck.getStartTime().compareTo(screening.getStartTime()) >= 0
                                            && (screeningCheck.getEndTime().compareTo(screening.getEndTime()) <= 0
                                            || screeningCheck.getEndTime().compareTo(screening.getStartTime()) == 0)) {
                                        check++;
                                    }
                                }
                            }
                        }

                        if (check == 0) {
                            soos.writeObject("OK");
                            screeningService.saveScreening(screening);
                        } else {
                            soos.writeObject("EXIST");
                        }
                        break;
                    }
                    case "VIEW_SCREENING_ADMIN", "VIEW_SCREENING_USER": {
                        List<Screening> screenings = new ArrayList<>();
                        screenings = screeningService.findAll();
                        soos.writeObject(screenings);
                        break;
                    }
                    case "REDACT_SCREENING_ADMIN": {
                        Screening screeningToRedactFromClient = (Screening) sois.readObject();

                        Screening screeningToRedact = screeningService.findByID(screeningToRedactFromClient.getScreening_id());

                        soos.writeObject(screeningToRedact);

                        List<Screening> screenings = new ArrayList<>();
                        screenings = screeningService.findAll();
                        soos.writeObject(screenings);

                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        soos.writeObject(films);

                        List<Hall> halls = new ArrayList<>();
                        halls = hallService.findAll();
                        soos.writeObject(halls);

                        Screening screeningToRedact1 = (Screening) sois.readObject();

                        int check = 0;

                        for (Screening screen : screenings) {
                            if (screeningToRedact1.getHall().getHall_id() == screen.getHall().getHall_id()) {
                                if (screeningToRedact1.getDate().compareTo(screen.getDate()) == 0) {
                                    if (screeningToRedact1.getStartTime().compareTo(screen.getStartTime()) >= 0
                                            || screeningToRedact1.getEndTime().compareTo(screen.getEndTime()) < 0) {
                                        check++;
                                    }
                                }
                            }
                        }

                        if (check == 0) {
                            soos.writeObject("OK");
                        } else {
                            soos.writeObject("EXIST");
                        }

                        break;
                    }
                    case "DELETE_SCREENING_ADMIN": {
                        screening = (Screening) sois.readObject();
                        screeningService.deleteScreening(screening);
                        break;
                    }
                    case "FIND_SCREENING_USER": {
                        screening = (Screening) sois.readObject();
                        Screening screeningToClient = screeningService.findByID(screening.getScreening_id());
                        soos.writeObject(screeningToClient);
                        break;
                    }
                    case "VIEW_FILM_ADMIN": {
                        List<Film> films = new ArrayList<>();
                        films = filmService.findAll();
                        soos.writeObject(films);
                        break;
                    }
                    case "CREATE_FILM_ADMIN": {
                        film = (Film) sois.readObject();
                        filmService.saveFilm(film);
                        break;
                    }
                    case "DELETE_FILM_ADMIN": {
                        film = (Film) sois.readObject();
                        filmService.deleteFilm(film);
                        break;
                    }
                    case "REDACT_FILM_ADMIN": {
                        film = (Film) sois.readObject();
                        Film filmToRedact = filmService.findFilm(film.getFilm_id());
                        Film newFilm = (Film) sois.readObject();

                        if (newFilm.getTitle() != null) {
                            String newTitle = newFilm.getTitle();
                            filmToRedact.setTitle(newTitle);
                        }

                        if (newFilm.getGenre() != null) {
                            String newGenre = newFilm.getGenre();
                            filmToRedact.setGenre(newGenre);
                        }

                        if (newFilm.getDirector() != null) {
                            String newDirector = newFilm.getDirector();
                            filmToRedact.setDirector(newDirector);
                        }

                        if (newFilm.getMainActor() != null) {
                            String newMainActor = newFilm.getMainActor();
                            filmToRedact.setMainActor(newMainActor);
                        }

                        if (newFilm.getDuration() != null) {
                            Time newTime = newFilm.getDuration();
                            filmToRedact.setDuration(newTime);
                        }

                        if (newFilm.getAge() != null) {
                            String newAge = newFilm.getAge();
                            filmToRedact.setAge(newAge);
                        }

                        filmService.updateFilm(filmToRedact);
                        soos.writeObject("OK");
                        break;
                    }
                    case "ORDER_TICKET_USER": {
                        ticket = (Ticket) sois.readObject();
                        ticketService.saveTicket(ticket);
                        soos.writeObject("OK");
                        break;
                    }
                    case "VIEW_TICKETS_USER": {
                        List<Ticket> tickets = new ArrayList<>();
                        tickets = ticketService.findAll();
                        soos.writeObject(tickets);
                        break;
                    }
                    case "FIND_TICKET_USER": {
                        ticket = (Ticket) sois.readObject();
                        Ticket ticketToClient = ticketService.findByID(ticket.getId());
                        soos.writeObject(ticketToClient);
                        break;
                    }
                    case "REDACT_TICKET_USER": {
                        ticket = (Ticket) sois.readObject();
                        ticketService.updateTicket(ticket);
                        soos.writeObject("OK");
                        break;
                    }
                    case "DELETE_TICKET_USER": {
                        ticket = (Ticket) sois.readObject();
                        ticketService.deleteTicket(ticket);
                        soos.writeObject("OK");
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

