package simulation;

import java.util.PriorityQueue;
import java.util.Queue;

public class Simulation {
  protected double currentTime;
  protected final Queue<ScheduledEvent> diary = new PriorityQueue<>();

  protected int queueLength = diary.size();

  protected boolean stop() { return false; }

  public void schedule(Event e, double offset) {
    diary.add(new ScheduledEvent(e, currentTime + offset));
  }

  public double getCurrentTime() {
    return currentTime;
  }

  public int getLength() { return queueLength; };

  public void incrementQueue() { queueLength++; };

  public void decrementQueue() { queueLength--; };

  public void simulate() {
    while (!diary.isEmpty()) {
      ScheduledEvent event = diary.poll();
      currentTime = event.getTime();
      if (!this.stop()) {
        event.getEvent().invoke(this);
      }
    }
  }
}


