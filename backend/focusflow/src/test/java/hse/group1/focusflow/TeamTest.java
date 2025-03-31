package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TeamTest{

    /** 
     * Test if a team can be created 
    */
    @Test
    public void testTeamCreation(){
        Team team = new Team("Test team Name", "Test team description");
        assertEquals("Test team Name", team.getName(), "Test Team for name");
        assertEquals("Test team description", team.getDescription(),"Test Team for description");
    }

    /*
     *Test if a team member can be added to the team  
     */
    @Test 
    public void testTeamAddMember(){
        Team team = new Team("Test team Name", "Test team description");
        User user = new User("test@example.com", "securePassword", "John", "Doe");
        team.addMember(user);
        
        List<User> members = team.getMembers();
        
        assertEquals("test@example.com", members.get(0).getEmail(), "failure");
        assertEquals("John", members.get(0).getFirst_name());
        assertEquals("Doe", members.get(0).getLast_name());
        assertNotNull(members.get(0).getCreated_at(), "created_at should be initialized");
    }

    /**
     * Test if a team member can be removed from the team
     */
    @Test 
    public void testTeamRemoveMember(){
        Team team = new Team("Test team Name", "Test team description");
        User user = new User("test@example.com", "securePassword", "John", "Doe");
        
        /**Add member to team */
        team.addMember(user);
        List<User> members = team.getMembers();
        int memberListSize = members.size();
        assertEquals(1, memberListSize, "Member has been added");


        /**Remove member from team*/
        team.removeMember(user);
        members = team.getMembers();
        memberListSize = members.size();
        assertEquals(0, memberListSize, "Member has been removed");
    }


    /**
     * Test if a user is memeber of the team 
     */
    @Test 
    public void testTeamHasMember(){
        Team team = new Team("Test team Name", "Test team description");
        User user = new User("test@example.com", "securePassword", "John", "Doe");
        
        /**Add member to team */
        team.addMember(user);

        assertEquals(true, team.hasMember(user), "User is not in member list");
    }
}