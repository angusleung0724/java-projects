package ticks;

import simulation.Simulation;


public class Ticks extends Simulation{
  private final double maxTime;

  @Override
  protected boolean stop(){
    return this.getCurrentTime() > maxTime;
  }

  @Override
  protected Ticks getState() { return this; }

  public Ticks(double maxTime) {
    this.maxTime = maxTime;
  }


  public static void main(String args[]) {
    Ticks ticks = new Ticks(10.0);
    ticks.schedule(new Tick(), 1.0);
    ticks.simulate();
  }

}
