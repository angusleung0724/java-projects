package ticks;

import simulation.Event;
import simulation.Simulation;

public class Tick implements Event{

  @Override
  public void invoke(Simulation simulation) {
    simulation.schedule(new Tick(), 1.0);
    System.out.println("Tick at: " + simulation.getCurrentTime());
  }
}
