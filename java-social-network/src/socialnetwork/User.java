package socialnetwork;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class User extends Thread {

  private static final AtomicInteger nextId = new AtomicInteger(0);
  // same variable for all instances of object user

  protected final SocialNetwork socialNetwork;
  private final int id;
  private final String name;

  public User(String username, SocialNetwork socialNetwork) {
    this.name = username;
    this.id = User.nextId.getAndIncrement();
    // gets next available id
    this.socialNetwork = socialNetwork;
  }

  public int getUserId() {
    return id;
  }

  @Override
  public void run() {
    Set<User> otherUsers = socialNetwork.getAllUsers();
    Random random = new Random();
    Set<User> recipientUsers = otherUsers
            .stream()
            .filter(i -> random.nextInt(10) < 5)
            .collect(Collectors.toSet());
    socialNetwork.postMessage(this, recipientUsers, "hi");
    socialNetwork.getBoards().get(this).getBoardSnapshot()
            .stream()
            .filter(i -> random.nextInt(10) < 5)
            .forEach(socialNetwork::deleteMessage);
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", name='" + name + '\'' + '}';
  }

  @Override
  public int hashCode() {
    return id;
  }
}
