package socialnetwork;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FineSet<T> {

  protected AtomicInteger size = new AtomicInteger(0);
  protected Node<T> head, tail;

  public FineSet() {
    head = new Node<>(null, Integer.MAX_VALUE);
    tail = new Node<>(null, Integer.MIN_VALUE);
    head.setNext(tail);
    tail.setPrev(head);
    head.setPrev(tail);
    tail.setNext(head);
  }

  protected Position<T> find(Node<T> start, int key) {
    Node<T> pred, curr;
    pred = start;
    pred.lock();
    curr = start.getNext();
    curr.lock();
    while (curr.getKey() > key) {
      pred.unlock();
      pred = curr;
      curr = curr.getNext();
      curr.lock();
    }
    return new Position<>(pred, curr);
  }

  protected boolean addItem(T item, int key) {
    Node<T> node = new Node<>(item, key);
    Node<T> pred = null, curr = null;
    try {
      Position<T> where = find(head, node.getKey());
      pred = where.pred;
      curr = where.curr;
      if (where.curr.getKey() == node.getKey()) {
        return false;
      } else {
        node.setNext(where.curr);
        node.setPrev(where.pred);
        where.pred.setNext(node);
        where.curr.setPrev(node);
        size.incrementAndGet();
        return true;
      }
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  protected boolean removeItem(T item, int key) {
    Node<T> pred = null, curr = null;
    try {
      Position<T> where = find(head, key);
      pred = where.pred;
      curr = where.curr;
      if (where.curr.getKey() > key) {
        return false;
      } else {
        where.pred.setNext(where.curr.getNext());
        where.curr.getNext().setPrev(where.pred);
        size.decrementAndGet();
        return true;
      }
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  protected List<T> getSnapshot() {
    Node<T> pred = null, curr = null;
    List<T> output = new ArrayList<>();
    try {
      pred = head;
      pred.lock();
      curr = head.getNext();
      curr.lock();
      while (curr != tail) {
        pred.unlock();
        pred = curr;
        output.add(curr.getValue());
        curr = curr.getNext();
        curr.lock();
      }
      return output;
    } finally {
      pred.unlock();
      curr.unlock();
    }
  }

  protected int size() {
    return size.get();
  }
}
