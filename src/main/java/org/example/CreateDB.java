package org.example;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateDB {

    public static void dbCreate(){
        Statement stmt = null;
        System.out.println("ЭТО ЖЕЙСТВИЕ УДАЛИТ СУЩЕСТВУЮЩУЮ БАЗУ ДАННЫХ, ПРОДОЛЖИТЬ? (да/нет)");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        if(input.equals("да") || input.equals("ДА")){
            try{
                ConnectionDB.init();
                stmt = ConnectionDB.connection.createStatement();

                String drop1 = "DROP TABLE IF EXISTS users";
                String drop2 = "DROP TABLE IF EXISTS tickets";
                String drop3 = "DROP TABLE IF EXISTS screenings";
                String drop4 = "DROP TABLE IF EXISTS hall";
                String drop5 = "DROP TABLE IF EXISTS film";

                stmt.executeUpdate(drop1);
                stmt.executeUpdate(drop2);
                stmt.executeUpdate(drop3);
                stmt.executeUpdate(drop4);
                stmt.executeUpdate(drop5);

                System.out.println("Данные удалены");

                String usersTable = "CREATE TABLE users (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    login VARCHAR(50),\n" +
                        "    email VARCHAR(100),\n" +
                        "    password VARCHAR(100),\n" +
                        "    role VARCHAR(50),\n" +
                        "    UNIQUE (username)\n" +
                        ");";

                stmt.executeUpdate(usersTable);

                String insertTableUsers = "INSERT INTO users (id, login, email, password, role)\n" +
                        "VALUES (0, 'mainadmin', 'admin',"+ "admin".hashCode()+ ", 'admin');";

                stmt.executeUpdate(insertTableUsers);

                String ticketTable = "CREATE TABLE tickets (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    session_id INTEGER,\n" +
                        "    seat_number INTEGER,\n" +
                        "    price DOUBLE,\n" +
                        "    user_id INTEGER,\n" +
                        "    FOREIGN KEY (session_id) REFERENCES screenings(id),\n" +
                        "    FOREIGN KEY (user_id) REFERENCES users(id)\n" +
                        ");";

                stmt.executeUpdate(ticketTable);

                String screeningsTable = "CREATE TABLE screenings (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    movie_id INTEGER,\n" +
                        "    hall_id INTEGER,\n" +
                        "    date DATE,\n" +
                        "    start_time TIME,\n" +
                        "    end_time TIME,\n" +
                        "    price DOUBLE,\n" +
                        "    FOREIGN KEY (movie_id) REFERENCES film(id),\n" +
                        "    FOREIGN KEY (hall_id) REFERENCES hall(id)\n" +
                        ");";

                stmt.executeUpdate(screeningsTable);

                String hallTable = "CREATE TABLE hall (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    name VARCHAR(100),\n" +
                        "    capacity INTEGER\n" +
                        ");";

                stmt.executeUpdate(hallTable);

                String filmTable = "CREATE TABLE film (\n" +
                        "    id SERIAL PRIMARY KEY,\n" +
                        "    title VARCHAR(100),\n" +
                        "    genre VARCHAR(50),\n" +
                        "    director VARCHAR(100),\n" +
                        "    main_actor VARCHAR(100),\n" +
                        "    duration TIME,\n" +
                        "    age VARCHAR(10)\n" +
                        ");";

                stmt.executeUpdate(filmTable);
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                if(stmt != null){
                    ConnectionDB.close(stmt);
                }
            }
        }
    }
}
