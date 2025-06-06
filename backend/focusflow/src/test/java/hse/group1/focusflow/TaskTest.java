package hse.group1.focusflow;

import hse.group1.focusflow.model.Task;

import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.temporal.ChronoUnit;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private User TestUser1;
    private User TestUser2;

    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        TestUser1 = new User("test@1.de","TestUser1","Test","1");
        TestUser2 = new User("test@2.de","TestUser2","Test","2");

        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

        assertTrue(validator.validate(task).isEmpty());
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

        assertThat(validator.validate(task))
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("due_date must be in the future");

        // set to 1 day in the future
        task.setDue_date(Instant.now().plus(1, ChronoUnit.DAYS));
        // tomorrow is in the future
        assertFalse(task.getDue_date().isBefore(Instant.now()));
        assertTrue(validator.validate(task).isEmpty());
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

        assertThat(validator.validate(task))
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("due_date must be in the future");

        task = new Task("title", "shortDescription","longDescription",
                Instant.now().plus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        assertFalse(task.getDue_date().isBefore(Instant.now()));
        assertFalse(task.isOverdue());

        assertTrue(validator.validate(task).isEmpty());
    }

    /**
     * Test border case for exact exact on second after due date of task
     *
     */
    @Test 
    public void TestIsOverdueEdgeCase(){
         Task task = new Task("title", "shortDescription","longDescription",
                Instant.now().minus(1, ChronoUnit.SECONDS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        

        // Due date is now
        assertFalse(task.getDue_date().isAfter(Instant.now()));
        assertTrue(task.isOverdue());

        /**Check if due date is in the past */
        assertFalse(validator.validate(task).isEmpty());
    }

    /**
     * Test border case for exact exact on second before due date of task
     * 
     */
    @Test 
    public void TestIsNotOverdueEdgeCass(){

        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now().plus(1, ChronoUnit.SECONDS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        // Due date is now
        assertFalse(task.getDue_date().isBefore(Instant.now()));
        assertFalse(task.isOverdue());

        assertTrue(validator.validate(task).isEmpty());
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

    @Test
    public void TestIdIsNull(){
        Task task = new Task("title", "shortDescription","longDescription",
                Instant.now(), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);
        // The ID Is assigned by the DB, should be unset at this point
        assertNull(task.getId());
    }

    @Test
    public void TestTitleMustBeSet(){
        Task task = new Task("", "shortDescription","longDescription",
                Instant.now().plus(1, ChronoUnit.DAYS), TestUser1, TaskPriority.LOW, TaskStatus.OPEN);

        assertThat(validator.validate(task))
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("title must be set");

        task.setTitle(null);

        assertThat(validator.validate(task))
                .extracting(ConstraintViolation::getMessage)
                .containsOnly("title must be set");

        task.setTitle("a");

        assertTrue(validator.validate(task).isEmpty());
    }
}
