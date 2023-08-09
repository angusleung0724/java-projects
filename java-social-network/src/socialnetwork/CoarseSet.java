package socialnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class CoarseSet<T> {

  protected Node<T> head, tail;
  protected int size;
  protected Lock lock;

  public CoarseSet() {
    lock = new ReentrantLock();
    size = 0;
    head = new Node<>(null, Integer.MAX_VALUE);
    tail = new Node<>(null, Integer.MIN_VALUE);
    head.setNext(tail);
    tail.setPrev(head);
  }

  protected Position<T> find(Node<T> start, int key) {
    lock.lock();
    // locks whole set to make sure no interference by other threads
    try {
      Node<T> curr = start;
      Node<T> pred;
      do {
        pred = curr;
        curr = curr.getNext();
      } while (curr.getKey() > key);
      // set ordered from head (highest key) to tail (lowest key)
      return new Position<>(pred, curr);
    } finally {
      lock.unlock();
    }
  }

  protected boolean addItem(T item, int key) {
    lock.lock();
    try {
      Node<T> node = new Node<>(item, key);
      Position<T> where = find(head, node.getKey());
      if (where.curr.getKey() == node.getKey()) {
        return false;
      } else {
        node.setNext(where.curr);
        node.setPrev(where.pred);
        where.pred.setNext(node);
        where.curr.setPrev(node);
        size++;
        return true;
      }
    } finally {
      lock.unlock();
    }
  }

  protected boolean removeItem(T item, int key) {
    lock.lock();
    try {
      Node<T> node = new Node<>(item, key);
      Position<T> where = find(head, node.getKey());
      if (where.curr.getKey() > node.getKey()) {
        return false;
      } else {
        where.pred.setNext(where.curr.getNext());
        where.curr.getNext().setPrev(where.pred);
        size -= 1;
        return true;
      }
    } finally {
      lock.unlock();
    }
  }

  protected List<T> getSnapshot() {
    lock.lock();
    try {
      Node<T> curr = head.getNext();
      List<T> output = new ArrayList<>();
      while (curr != tail) {
        output.add(curr.getValue());
        curr = curr.getNext();
      }
      return output;
    } finally {
      lock.unlock();
    }
  }

  protected int size() { return size; }
}
