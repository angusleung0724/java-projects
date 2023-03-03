package socialnetwork;

import socialnetwork.domain.Backlog;
import socialnetwork.domain.Task;

import java.util.Optional;

public class CoarseBacklog extends CoarseSet<Task> implements Backlog {

  public CoarseBacklog() { super(); }

  @Override
  public boolean add(Task task) {
    return super.addItem(task, task.getId());
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    lock.lock();
    try {
      if (super.size() == 0) {
        return Optional.empty();
      }
      Node<Task> last = tail.getPrev();
      tail.setPrev(last.getPrev());
      last.getPrev().setNext(tail);
      size--;
      return Optional.of(last.getValue());
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return super.size();
  }
}
