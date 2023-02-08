package museumvisit;

import java.util.Optional;

public class Turnstile {

  private final MuseumSite originRoom;
  private final MuseumSite destinationRoom;

  public Turnstile(MuseumSite originRoom, MuseumSite destinationRoom) {
    assert !originRoom.equals(destinationRoom);
    this.originRoom = originRoom;
    this.destinationRoom = destinationRoom;
    originRoom.addExitTurnstile(this);
  }

  public Optional<MuseumSite> passToNextRoom() {
    MuseumSite firstSiteToLock, secondSiteToLock;
    if (originRoom.hashCode() < destinationRoom.hashCode()) {
      firstSiteToLock = originRoom;
      secondSiteToLock = destinationRoom;
    } else {
      firstSiteToLock = destinationRoom;
      secondSiteToLock = originRoom;
    }
    synchronized (firstSiteToLock) {
      synchronized (secondSiteToLock) {
        if (destinationRoom.hasAvailability()) {
          originRoom.exit();
          destinationRoom.enter();
          return Optional.of(destinationRoom);
        }
        return Optional.empty();
      }
    }
  }

  public MuseumSite getOriginRoom() {
    return originRoom;
  }

  public MuseumSite getDestinationRoom() {
    return destinationRoom;
  }
}
