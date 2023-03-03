package socialnetwork;

import socialnetwork.domain.Backlog;
import socialnetwork.domain.Worker;

import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    Backlog backlog = new FineBacklog();
    SocialNetwork socialNetwork = new SocialNetwork(backlog);
    Worker[] workers = new Worker[30];
    Arrays.setAll(workers, i -> new Worker(backlog));
    Arrays.stream(workers).forEach(i -> i.start());
    User[] users = new User[30];
    Arrays.setAll(users, i -> new User("User" + i, socialNetwork));
    Arrays.stream(users).forEach(i ->
    {
      socialNetwork.register(i, new FineBoard());
      i.start();
    });

    // wait for users to finish, call join
    Arrays.stream(users)
            .forEach(
                    u -> {
                      try {
                        u.join();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    });

    // wait for backlog to be empty
    while (backlog.numberOfTasksInTheBacklog() != 0) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // interrupt workers, wait to terminate
    Arrays.stream(workers).forEach(w -> w.interrupt());
    Arrays.stream(workers)
            .forEach(
                    w -> {
                      try {
                        w.join();
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }
                    });
  }
}
