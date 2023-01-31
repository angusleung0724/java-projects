package ssq;

import simulation.Simulation;

import java.util.Random;

public class SingleServerQueue extends Simulation {
  private final double time;

  private Random random;

  private int queueLength = super.diary.size();

  private double meanCollect = 0;
  private double prevTime = 0;

  public SingleServerQueue(int seed, double time) {
    this.time = time;
    this.random = new Random(seed);
  }

  @Override
  public boolean stop() {
    return time < super.currentTime;
  }

  @Override
  protected SingleServerQueue getState() { return this; }

  public double getRandom() {
    return random.nextDouble();
  }

  public int getLength() { return queueLength; };

  public void incrementQueue() {
    meanCollect += (currentTime - prevTime)*(queueLength)/(time);
    queueLength++;
    prevTime = currentTime;
  }

  public void decrementQueue() {
    meanCollect += (currentTime - prevTime)*(queueLength)/(time);
    queueLength--;
    prevTime = currentTime;
  }

  public double getMeanQueueLength() {
    return meanCollect += (time - prevTime)*(queueLength)/(time);
  }


  public static void main(String args[]) {
    SingleServerQueue ssqSimulation = new SingleServerQueue(1987281099, 4.0);
    ssqSimulation.schedule(new Arrival(), ssqSimulation.getRandom());
    ssqSimulation.simulate();
    System.out.println("SIMULATION COMPLETE - the mean queue length was " + ssqSimulation.getMeanQueueLength());
  }
}
