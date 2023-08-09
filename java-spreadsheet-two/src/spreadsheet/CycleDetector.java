package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;

import java.util.HashSet;
import java.util.Set;

/** Detects dependency cycles. */
public class CycleDetector {
  /**
   * Constructs a new cycle detector.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet, used for resolving cell locations.
   */

  private final BasicSpreadsheet spreadsheet;
  private final Set<CellLocation> visited;
  CycleDetector(BasicSpreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
    this.visited = new HashSet<>();
  }

  /**
   * Checks for a cycle in the spreadsheet, starting at a particular cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param start The cell location where cycle detection should start.
   * @return Whether a cycle was detected in the dependency graph starting at the given cell.
   */
  public boolean hasCycleFrom(CellLocation start) {
    if (visited.contains(start)) {
      return true;
    }
    Set<CellLocation> next = new HashSet<>();
    spreadsheet.findCellReferences(start, next);
    visited.add(start);
    boolean result = next.stream().anyMatch(i -> hasCycleFrom(i));
    visited.remove(start);
    return result;
  }
}
