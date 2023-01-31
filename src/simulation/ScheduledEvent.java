package simulation;

public class ScheduledEvent<S> implements Comparable<ScheduledEvent<S>>{

  private final Event event;
  private final double time;

  public ScheduledEvent(Event event, double time) {
    this.event = event;
    this.time = time;
  }

  @Override
  public int compareTo(ScheduledEvent other) {
    return Double.compare(time, other.getTime());
  }

  public double getTime() {
    return time;
  }

  public Event getEvent() {
    return event;
  }

}
