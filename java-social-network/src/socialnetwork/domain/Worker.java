package socialnetwork.domain;

import java.util.Optional;

public class Worker extends Thread {

  private final Backlog backlog;
  private boolean interrupted = false;

  public Worker(Backlog backlog) {
    this.backlog = backlog;
  }

  @Override
  public void run() {
    while (!interrupted) {
      Optional<Task> nextTask = backlog.getNextTaskToProcess();
      if (nextTask.isPresent()) {
        process(nextTask.get());
      } else {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public void interrupt() {
    this.interrupted = true;
  }

  public void process(Task nextTask) {
    switch (nextTask.getCommand()) {
      case POST -> { nextTask.getBoard().addMessage(nextTask.getMessage()); }
      case DELETE -> {
        // if condition has side effect if true
        if (!nextTask.getBoard().deleteMessage(nextTask.getMessage())) {
          backlog.add(nextTask);
        }
      }
    }
  }
}
