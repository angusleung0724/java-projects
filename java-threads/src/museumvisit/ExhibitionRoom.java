package museumvisit;

public class ExhibitionRoom extends MuseumSite {  // extends abstract class MuseumSite; inherits fields, methods

  private final int capacity;

  public ExhibitionRoom(String name, int capacity) {
    super(name);
    assert capacity > 0;   // make sure capacity is non-negative using "assert"
    this.capacity = capacity;

  }
  public int getCapacity() {
    return capacity;
  }

  @Override
  public boolean hasAvailability() {
    return (super.occupancy < capacity);
  }
}
