package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;

public class TeamTest{

    /** 
     * Test if a team can be created 
    */
    @Test
    public void testTeamCreation(){
        Team team = new Team("Test team Name", "Test team description");
        assertNotNull(team.getName(),"Team name is Null");
        assertNotNull(team.getDescription(),"Team description is Null");
        assertEquals("Test team Name", team.getName(), "Team name is not correct");
        assertEquals("Test team description", team.getDescription(),"Team description is not correct");
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
        
        assertEquals("test@example.com", members.get(0).getEmail(), "Adding user as team member went wrong, problem with user email");
        assertEquals("John", members.get(0).getFirst_name(),"Adding user as team member went wrong, problem with user first name");
        assertEquals("Doe", members.get(0).getLast_name(),"Adding user as team member went wrong, problem with user last name");
        assertNotNull(members.get(0).getCreated_at(), "Adding user as team member went wrong, problem with user -> created_at should be initialized");
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
        assertEquals(1, memberListSize, "Member hasn't been added");


        /**Remove member from team*/
        team.removeMember(user);
        members = team.getMembers();
        memberListSize = members.size();
        assertEquals(0, memberListSize, "Member hasn't been removed");
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

        assertEquals(true, team.hasMember(user), "User is not in team member list");
    }

    /**
     * Test if team id exist, id shouldn't exist, till the team is stored to the database
     * 
     */
     @Test
    public void testTeamGetId(){
        Team team = new Team("Test team Name", "Test team description");
        assertNull(team.getId(), "team id is null till persistence");
    }

    /**
     * Test if team id exist before persistence
     * 
     */
    @Test 
    public void testTeamGetName(){
        Team team = new Team("Test team Name", "Test team description");
        assertNotNull(team.getName(), "Team name is NULL");
        assertEquals("Test team Name",team.getName(), "Team name is not correct");
    }

    /**
     * test getter from team description 
     */
    @Test 
    public void testTeamGetDescription(){
        Team team = new Team("Test team Name", "Test team description");
        assertNotNull(team.getName(), "Team name is NULL");
        assertEquals("Test team description", team.getDescription(),"Team description is not correct");
    }

    /**
     * test getter and setter methode from team entity Member attribute
     * 
     */
    @Test
    public void testGetTeamMember(){
        Team team = new Team("Test team Name", "Test team description");
        User user = new User("test@example.com", "securePassword", "John", "Doe");
        User nextUser = new User("moretest@example.com", "moresecurePassword", "Matilda", "Brown");

        team.addMember(user);
        team.addMember(nextUser);

        assertEquals(2,team.getMembers().size(), "Size of team member list is wrong");
        assertNotNull(team.getMembers(), "Team name is NULL");
        assertEquals("John", team.getMembers().get(0).getFirst_name(),"Testing getTeamMember went wrong -> Isn't correct team member first name");
        assertEquals("Doe", team.getMembers().get(0).getLast_name(),"Testing getTeamMember went wrong -> Isn't correct team member last name");
        assertEquals("Matilda", team.getMembers().get(1).getFirst_name(),"Testing getTeamMember went wrong -> Isn't correct team member first name");
        assertEquals("Brown", team.getMembers().get(1).getLast_name(),"Testing getTeamMember went wrong -> Isn't correct team member last name");
    }

    /*
     * Test team set name function 
     */
    @Test
    public void testSetTeamName(){
        Team team = new Team("Test team Name", "Test team description");
        team.setName("New Test Name");
        assertEquals("New Test Name", team.getName(), "Team name hasn't been changed");
    }

    /**
     * Test team set description function 
     * 
     */
    @Test 
    public void testSetTeamDescription(){
        Team team = new Team("Test team Name", "Test team description");
        team.setDescription("New test team description");
        assertEquals("New test team description", team.getDescription(),"Team description hasn't been changed");
    }


    /*
     * Testing setter and getter method from team entity teamlead attribute  
     * 
     */
    @Test
    public void testSetTeamLead(){
        Team team = new Team("Test team Name", "Test team description");
        User user = new User("test@example.com", "securePassword", "John", "Doe");
        team.setTeamLead(user);


        /** test if team leader is set  */
        assertEquals(user, team.getTeamLead(),"Team leader doesn't exist");


        /**Test if team leader is correct user  */
        User teamLeader = team.getTeamLead();
        assertEquals("test@example.com", teamLeader.getEmail(), "failure");
        assertEquals("John", teamLeader.getFirst_name());
        assertEquals("Doe", teamLeader.getLast_name());
        assertNotNull(teamLeader.getCreated_at(), "created_at should be initialized");
    }

}