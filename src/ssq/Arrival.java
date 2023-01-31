package ssq;

import simulation.Event;
import simulation.Simulation;

public class Arrival implements Event {

  private static final double SERVICETIME = 0.25;

  @Override
  public void invoke(Simulation simulation) {
    simulation.incrementQueue();
    System.out.println("Arrival at " + simulation.getCurrentTime() + ", new population = " + simulation.getLength());
    simulation.schedule(new Arrival(), ((SingleServerQueue) simulation).getRandom());
    if (simulation.getLength() == 1) {
      simulation.schedule(new Departure(), SERVICETIME);
    }
  }
}
