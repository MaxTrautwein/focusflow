package hse.group1.focusflow.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private Timestamp created_at;

    @ManyToMany
    private List<User> members;

}
