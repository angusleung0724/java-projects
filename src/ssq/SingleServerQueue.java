package ssq;

import simulation.Simulation;

import java.util.Random;

public class SingleServerQueue extends Simulation {
  private final long seed;
  private final double time;

  private Random random;

  public SingleServerQueue(int seed, double time) {
    this.seed = seed;
    this.time = time;
    this.random = new Random(seed);
  }

  @Override
  public boolean stop() {
    return time < super.currentTime;
  }

  public double getRandom() {
    return random.nextDouble();
  }

  public static void main(String args[]) {
    SingleServerQueue ssqSimulation = new SingleServerQueue(1987281099, 4.0);
    ssqSimulation.schedule(new Arrival(), ssqSimulation.getRandom());
    ssqSimulation.simulate();
    System.out.println("SIMULATION COMPLETE");
  }
}
