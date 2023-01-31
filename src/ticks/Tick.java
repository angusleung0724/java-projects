package ticks;

import simulation.Event;

public class Tick implements Event<Ticks>{

  @Override
  public void invoke(Ticks simulation) {
    simulation.schedule(new Tick(), 1.0);
    System.out.println("Tick at: " + simulation.getCurrentTime());
  }
}
