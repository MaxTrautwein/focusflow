package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;


public class TeamTest {

  @Test
  public void testTeamCreation() {
    Team team = new Team("Test team Name", "Test team description");
    assertEquals("Test team Name", team.getName(), "Test Team for name");
    assertEquals(
        "Test team description",
        team.getDescription(),
        "Test Team for description");
  }

  @Test
  public void testTeamAddMember() {
    Team team = new Team("Test team Name", "Test team description");
    User user = new User("test@example.com", "securePassword", "John", "Doe");
    team.addMember(user);

    List<User> members = team.getMembers();

    assertEquals("test@example.com", members.get(0).getEmail(), "failure");
    assertEquals("John", members.get(0).getFirstName());
    assertEquals("Doe", members.get(0).getLastName());
    assertNotNull(
        members.get(0).getCreatedAt(),
        "createdAt should be initialized");
  }

  @Test
  public void testTeamRemoveMember() {
    Team team = new Team("Test team Name", "Test team description");
    User user = new User("test@example.com", "securePassword", "John", "Doe");

    // Add member
    team.addMember(user);
    assertEquals(1, team.getMembers().size(), "Member has been added");

    // Remove member
    team.removeMember(user);
    assertEquals(0, team.getMembers().size(), "Member has been removed");
  }

  @Test
  public void testTeamHasMember() {
    Team team = new Team("Test team Name", "Test team description");
    User user = new User("test@example.com", "securePassword", "John", "Doe");

    team.addMember(user);
    assertTrue(team.hasMember(user), "User is not in member list");
  }

  /**
   * Test if team id exist, id shouldn't exist, till the team is stored to the
   * database
   * 
   */
  @Test
  public void testTeamGetId() {
    Team team = new Team("Test team Name", "Test team description");
    assertNull(team.getId(), "team id is null till persistence");
  }

  /**
   * Test if team id exist before persistence
   * 
   */
  @Test
  public void testTeamGetName() {
    Team team = new Team("Test team Name", "Test team description");
    assertNotNull(team.getName(), "Team name is NULL");
    assertEquals("Test team Name", team.getName(), "Team name is not correct");
  }

  /**
   * test getter from team description
   */
  @Test
  public void testTeamGetDescription() {
    Team team = new Team("Test team Name", "Test team description");
    assertNotNull(team.getName(), "Team name is NULL");
    assertEquals("Test team description", team.getDescription(), "Team description is not correct");
  }

  /**
   * test getter and setter methode from team entity Member attribute
   * 
   */
  @Test
  public void testGetTeamMember() {
    Team team = new Team("Test team Name", "Test team description");
    User user = new User("test@example.com", "securePassword", "John", "Doe");
    User nextUser = new User("moretest@example.com", "moresecurePassword", "Matilda", "Brown");

    team.addMember(user);
    team.addMember(nextUser);

    assertEquals(2, team.getMembers().size(), "Size of team member list is wrong");
    assertNotNull(team.getMembers(), "Team name is NULL");
    assertEquals("John", team.getMembers().get(0).getFirstName(),
        "Testing getTeamMember went wrong -> Isn't correct team member first name");
    assertEquals("Doe", team.getMembers().get(0).getLastName(),
        "Testing getTeamMember went wrong -> Isn't correct team member last name");
    assertEquals("Matilda", team.getMembers().get(1).getFirstName(),
        "Testing getTeamMember went wrong -> Isn't correct team member first name");
    assertEquals("Brown", team.getMembers().get(1).getLastName(),
        "Testing getTeamMember went wrong -> Isn't correct team member last name");
  }

  /*
   * Test team set name function
   */
  @Test
  public void testSetTeamName() {
    Team team = new Team("Test team Name", "Test team description");
    team.setName("New Test Name");
    assertEquals("New Test Name", team.getName(), "Team name hasn't been changed");
  }

  /**
   * Test team set description function
   * 
   */
  @Test
  public void testSetTeamDescription() {
    Team team = new Team("Test team Name", "Test team description");
    team.setDescription("New test team description");
    assertEquals("New test team description", team.getDescription(), "Team description hasn't been changed");
  }

  /*
   * Testing setter and getter method from team entity teamlead attribute
   * 
   */
  @Test
  public void testSetTeamLead() {
    Team team = new Team("Test team Name", "Test team description");
    User user = new User("test@example.com", "securePassword", "John", "Doe");
    team.setTeamLead(user);

    /** test if team leader is set */
    assertEquals(user, team.getTeamLead(), "Team leader doesn't exist");

    /** Test if team leader is correct user */
    User teamLeader = team.getTeamLead();
    assertEquals("test@example.com", teamLeader.getEmail(), "failure");
    assertEquals("John", teamLeader.getFirstName());
    assertEquals("Doe", teamLeader.getLastName());
    assertNotNull(teamLeader.getCreatedAt(), "created_at should be initialized");
  }

}