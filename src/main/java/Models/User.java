package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "password")
    private String password;
    private static final long serialVersionUID = 123456789L;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public User() {}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public User(int user_id, String username, String email, String password, List<Ticket> tickets) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.role = "user";
        this.password = password;
        //this.tickets = tickets;
    }

    public int getUser_id(){
        return user_id;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){return email;}
    public String getRole() {return role;}

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString(){
        return "id = " + user_id +
                "\nusername = " + username +
                "\nemail = " + email +
                "\nrole = " + role +
                "\npassword = " + password; //+
                //"\ntickets = " + tickets;
    }
}
