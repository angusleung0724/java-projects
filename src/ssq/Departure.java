package ssq;

import simulation.Event;
import simulation.Simulation;

public class Departure implements Event {

  private static final double SERVICETIME = 0.25;

  @Override
  public void invoke(Simulation simulation) {
    simulation.decrementQueue();
    System.out.println("Departure at " + simulation.getCurrentTime() + ", new population = " + simulation.getLength());
    if (simulation.getLength() > 0) {
      simulation.schedule(new Departure(), SERVICETIME);
    }
  }
}