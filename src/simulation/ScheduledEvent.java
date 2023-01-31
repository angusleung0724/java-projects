package simulation;

public class ScheduledEvent<S> implements Comparable<ScheduledEvent<S>>{

  private final Event<S> event;
  private final double time;

  public ScheduledEvent(Event<S> event, double time) {
    this.event = event;
    this.time = time;
  }

  @Override
  public int compareTo(ScheduledEvent<S> other) {
    return Double.compare(time, other.getTime());
  }

  public double getTime() {
    return time;
  }

  public Event<S> getEvent() {
    return event;
  }

}
