package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.lexer.InvalidTokenException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Spreadsheet implements BasicSpreadsheet {

  private final Map<CellLocation, Cell> spreadsheet;


  /**
   * Construct an empty spreadsheet.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  Spreadsheet() {
    spreadsheet = new HashMap<>();
  }

  /**
   * Parse and evaluate an expression, using the spreadsheet as a context.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public double evaluateExpression(String expression) throws InvalidSyntaxException, InvalidTokenException {
    return (Parser.parse(expression).evaluate(this));
  }

  /**
   * Assign an expression to a cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void setCellExpression(CellLocation location, String input) throws InvalidSyntaxException {
    Cell newCell = spreadsheet.getOrDefault(location, new Cell(this, location));
    Set<CellLocation> previousDeps = new HashSet<>();
    findCellReferences(location, previousDeps);
    newCell.setExpression(input);
    spreadsheet.put(location, newCell);
    Set<CellLocation> newDeps = new HashSet<>();
    findCellReferences(location, newDeps);
    for (CellLocation pdeps : previousDeps) {
      removeDependency(pdeps, location);
    }
    for (CellLocation ndeps : newDeps) {
      addDependency(ndeps, location);
    }
    recalculate(location);
  }

  @Override
  public double getCellValue(CellLocation location) {
    return spreadsheet.containsKey(location)? spreadsheet.get(location).getValue() : 0.0;
  }

  @Override
  public String getCellExpression(CellLocation location) {
    return spreadsheet.containsKey(location)? spreadsheet.get(location).getExpression() : "";
  }

  @Override
  public String getCellDisplay(CellLocation location) {
    return getCellExpression(location).equals("")? "" : Double.toString(getCellValue(location));
  }

  @Override
  public void addDependency(CellLocation dependent, CellLocation dependency) {
    Cell newCell = spreadsheet.getOrDefault(dependent, new Cell(this, dependent));
    newCell.addDependent(dependency);
    spreadsheet.put(dependent, newCell);
  }

  @Override
  public void removeDependency(CellLocation dependent, CellLocation dependency) {
    spreadsheet.get(dependent).removeDependent(dependency);
  }

  @Override
  public void recalculate(CellLocation location) throws InvalidSyntaxException{
    CycleDetector cycleDetector = new CycleDetector(this);
    if (cycleDetector.hasCycleFrom(location)) {
      setCellExpression(location, "" + spreadsheet.get(location).getValue());
    }
    spreadsheet.get(location).recalculate();
  }


  @Override
  public void findCellReferences(CellLocation subject, Set<CellLocation> target) {
    if (spreadsheet.containsKey(subject)) {
      spreadsheet.get(subject).findCellReferences(target);
    }
  }



}
