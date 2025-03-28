package hse.group1.focusflow.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String first_name;
    private String last_name;

    // Required by Task
    // Should be a Hashed & Salted Value for actual Use
    private String password;

    private Timestamp created_at;
    private Timestamp last_login;

    @ManyToMany(mappedBy = "user")
    private List<Team> teams;

}
