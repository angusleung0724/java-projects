package ssq;

import simulation.Event;

public class Arrival implements Event<SingleServerQueue> {

  private static final double SERVICETIME = 0.25;

  @Override
  public void invoke(SingleServerQueue simulation) {
    simulation.incrementQueue();
    System.out.println("Arrival at " + simulation.getCurrentTime() + ", new population = " + simulation.getLength());
    simulation.schedule(new Arrival(), simulation.getRandom());
    if (simulation.getLength() == 1) {
      simulation.schedule(new Departure(), SERVICETIME);
    }
  }
}
