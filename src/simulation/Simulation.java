package simulation;

import java.util.PriorityQueue;
import java.util.Queue;

public class Simulation<S> {
  protected double currentTime;
  protected final Queue<ScheduledEvent> diary = new PriorityQueue<>();



  protected boolean stop() { return false; }

  protected S getState() { return ((S) this); }

  public void schedule(Event e, double offset) {
    diary.add(new ScheduledEvent(e, currentTime + offset));
  }

  public double getCurrentTime() {
    return currentTime;
  }

  public void simulate() {
    while (!diary.isEmpty ()) {
      ScheduledEvent event = diary.poll();
      currentTime = event.getTime();
      if (!this.stop()) {
        event.getEvent().invoke(getState());
      }
    }
  }
}


