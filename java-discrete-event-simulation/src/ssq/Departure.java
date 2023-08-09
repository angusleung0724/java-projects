package ssq;

import simulation.Event;

public class Departure implements Event<SingleServerQueue> {

  private static final double SERVICETIME = 0.25;

  @Override
  public void invoke(SingleServerQueue simulation) {
    simulation.decrementQueue();
    System.out.println("Departure at " + simulation.getCurrentTime() + ", new population = " + simulation.getLength());
    if (simulation.getLength() > 0) {
      simulation.schedule(new Departure(), SERVICETIME);
    }
  }
}