package socialnetwork;

import socialnetwork.domain.Backlog;
import socialnetwork.domain.Task;

import java.util.Optional;

public class FineBacklog extends FineSet<Task> implements Backlog {

  public FineBacklog() { super(); }

  @Override
  public boolean add(Task task) {
    return super.addItem(task, task.getId());
  }

  @Override
  public Optional<Task> getNextTaskToProcess() {
    Node<Task> first = null, second = null, third = null;
    try {
      first = head;
      first.lock();
      second = first.getNext();
      second.lock();
      third = second.getNext();
      third.lock();
      if (third == head) {
        return Optional.empty();
      }
      while (third != tail) {
        first.unlock();
        first = second;
        second = third;
        third = third.getNext();
        third.lock();
      }
      first.setNext(third);
      third.setPrev(first);
      size.getAndDecrement();
      return Optional.of(second.getValue());
    } finally {
      first.unlock();
      second.unlock();
      third.unlock();
    }
  }

  @Override
  public int numberOfTasksInTheBacklog() {
    return super.size();
  }

}
