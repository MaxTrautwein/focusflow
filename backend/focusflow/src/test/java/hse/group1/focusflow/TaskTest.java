package hse.group1.focusflow;

import hse.group1.focusflow.model.Task;

import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.temporal.ChronoUnit;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private User TestUser1;
    private User TestUser2;

    @BeforeEach
    public void beforeAll() {
        TestUser1 = new User("test@1.de","TestUser1","Test","1");
        TestUser2 = new User("test@2.de","TestUser2","Test","2");
    }


    @Test
    public void TestTaskCreation() {
        String title = "Test Title";
        String shortDescription = "Test Description";
        String longDescription = "Test Description";
        Instant due = Instant.now().plus(2, ChronoUnit.DAYS );
        TaskPriority priority = TaskPriority.LOW;
        TaskStatus status = TaskStatus.OPEN;


        Task task = new Task(title, shortDescription,longDescription,
              due, TestUser1, priority, status);

        assertEquals(title, task.getTitle());
        assertEquals(shortDescription, task.getShort_description());
        assertEquals(longDescription, task.getLong_description());
        assertEquals(due, task.getDue_date());
        assertEquals(TestUser1, task.getAssignee());
        assertEquals(priority, task.getPriority());
        assertEquals(status, task.getStatus());
    }

    @Test
    public void TestUpdateTitle(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals("title", task.getTitle());

        task.setTitle("new title");
        assertEquals("new title", task.getTitle());
    }

    @Test
    public void TestUpdateShortDescription(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals("shortDescription", task.getShort_description());

        task.setShort_description("new shortDescription");
        assertEquals("new shortDescription", task.getShort_description());
    }

    @Test
    public void TestUpdateLongDescription(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals("longDescription", task.getLong_description());

        task.setLong_description("new longDescription");
        assertEquals("new longDescription", task.getLong_description());
    }

    @Test
    public void TestUpdateDueDate(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now().minus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        // 1 day ago is in the past
        assertTrue(task.getDue_date().isBefore(Instant.now()));
        // set to 1 day in the future
        task.setDue_date(Instant.now().plus(1, ChronoUnit.DAYS));
        // tomorrow is in the future
        assertFalse(task.getDue_date().isBefore(Instant.now()));
    }

    @Test
    public void TestUpdateStatus(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals(TaskStatus.OPEN, task.getStatus());

        task.setStatus(TaskStatus.CLOSED);
        assertEquals(TaskStatus.CLOSED, task.getStatus());
    }

    @Test
    public void TestUpdatePriority(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals(TaskPriority.LOW, task.getPriority());

        task.setPriority(TaskPriority.MEDIUM);
        assertEquals(TaskPriority.MEDIUM, task.getPriority());
    }

    @Test
    public void TestIsOverdue(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now().minus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        // 1 day ago is in the past
        assertTrue(task.getDue_date().isBefore(Instant.now()));
        assertTrue(task.isOverdue());

        task = new Task("title", "shortDescription","longDescription",
                Instant.now().plus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertFalse(task.getDue_date().isBefore(Instant.now()));
        assertFalse(task.isOverdue());
    }

    @Test
    public void TestSetAssignee(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now().minus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertEquals(TestUser1, task.getAssignee());

        task.assignTo(TestUser2);
        assertEquals(TestUser2, task.getAssignee());
        assertNotEquals(TestUser1, task.getAssignee());
    }

    @Test
    public void TestIsAssigned(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), null, TaskPriority.LOW, TaskStatus.OPEN);

        assertNull(task.getAssignee());
        assertFalse(task.isAssigned());

        task.assignTo(TestUser1);

        assertTrue(task.isAssigned());
    }
}
