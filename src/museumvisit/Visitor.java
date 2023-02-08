package museumvisit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Visitor implements Runnable {

  private final String name;
  private MuseumSite currentRoom;

  public Visitor(String name, MuseumSite initialRoom) {
    this.name = name;
    this.currentRoom = initialRoom;
    currentRoom.enter();
  }

  public void run() {
    while (thereAreMoreSitesToVisit()) {
      simulateVisitToCurrentRoom();
      Turnstile chosenTurnstile = pickRandomTurnstile();
      Optional<MuseumSite> result = chosenTurnstile.passToNextRoom();
      while (result.isEmpty()) {
        waitSomeTimeBeforeRetrying();
        result = pickRandomTurnstile().passToNextRoom();
      }
      currentRoom = result.get();
    }
  }

  private boolean thereAreMoreSitesToVisit() {
    return !currentRoom.getExitTurnstiles().isEmpty();
  }

  private Turnstile pickRandomTurnstile() {
    List<Turnstile> exitTurnstiles = currentRoom.getExitTurnstiles();
    assert !exitTurnstiles.isEmpty();

    Collections.shuffle(exitTurnstiles); // Random shuffle of the list of
    // turnstiles
    return exitTurnstiles.stream().findAny().get();
  }

  private void simulateVisitToCurrentRoom() {
    System.out.println("Visitor " + name + ": visiting room: " + currentRoom.getName());
    final int randomVisitTimeInMillis = (int) (Math.random() * 200) + 1;
    // wait between 1  and  200 millis
    try {
      Thread.sleep(randomVisitTimeInMillis);
    } catch (InterruptedException e) {
    }
  }

  private void waitSomeTimeBeforeRetrying() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
    }
  }

  @Override
  public String toString() {
    return "Visitor " + name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Visitor) {
      return this.name.equals(((Visitor) obj).name);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), name);
  }
}
