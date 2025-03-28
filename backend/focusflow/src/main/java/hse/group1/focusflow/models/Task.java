package hse.group1.focusflow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String short_description;
    private String long_description;
    private Date due_date;
    private TaskStatus status;
    private TaskPriority priority;




}
