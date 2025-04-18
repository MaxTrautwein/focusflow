package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.*;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TeamTest {

  @Test
  public void testTeamCreation() {
    Team team = new Team("Test team Name", "Test team description");
    assertEquals("Test team Name", team.getName(), "Test Team for name");
    assertEquals(
      "Test team description",
      team.getDescription(),
      "Test Team for description"
    );
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
      "createdAt should be initialized"
    );
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

  @Test
  public void testTeamLeadAssignment() {
    Team team = new Team("Lead team", "Lead description");
    User leader = new User("lead@example.com", "pw", "Alice", "Smith");

    team.setTeamLead(leader);
    User teamLeader = team.getTeamLead();

    assertEquals("Alice", teamLeader.getFirstName());
    assertEquals("Smith", teamLeader.getLastName());
    assertNotNull(
      teamLeader.getCreatedAt(),
      "createdAt should be initialized on leader"
    );
  }
}
