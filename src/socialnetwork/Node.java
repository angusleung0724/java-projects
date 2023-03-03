package socialnetwork;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {

  private Node<T> next;
  private Node<T> prev;
  private final T value;
  private int key;
  private Lock lock;

  public Node(T value, int key) {
    this.value = value;
    this.key = key;
    this.lock = new ReentrantLock();
  }

  public T getValue() { return value; }

  public int getKey() { return key; }

  public Node<T> getNext() { return next; }

  public Node<T> getPrev() { return prev; }

  public void setNext(Node<T> newNode) { this.next = newNode; }

  public void setPrev(Node<T> newNode) { this.prev = newNode; }

  public void lock() { lock.lock(); }

  public void unlock() { lock.unlock(); }
}
