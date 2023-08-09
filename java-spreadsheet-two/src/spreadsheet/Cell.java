package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.api.Expression;
import common.lexer.InvalidTokenException;

import java.util.HashSet;
import java.util.Set;

import static spreadsheet.Parser.parse;

/**
 * A single cell in a spreadsheet, tracking the expression, value, and other parts of cell state.
 */
public class Cell {

  private final BasicSpreadsheet spreadsheet;
  private final CellLocation location;
  private Double value;
  private Expression expression;
  private Set<CellLocation> dependents;
  private boolean empty;
  /**
   * Constructs a new cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet,
   * @param location The location of this cell in the spreadsheet.
   */
  Cell(BasicSpreadsheet spreadsheet, CellLocation location) {
    this.spreadsheet = spreadsheet;
    this.location = location;
    this.expression = null;
    this.dependents = new HashSet<>();
    this.value = 0.0;
    empty = true;
  }

  /**
   * Gets the cell's last calculated value.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return the cell's value.
   */
  public double getValue() {
    return value;
  }

  /**
   * Gets the cell's last stored expression, in string form.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return a string that parses to an equivalent expression to that last stored in the cell; if no
   *     expression is stored, we return the empty string.
   */
  public String getExpression() {
    return empty ? "" : expression.toString();
  }

  /**
   * Sets the cell's expression from a string.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param input The string representing the new cell expression.
   * @throws InvalidSyntaxException if the string cannot be parsed.
   */
  public void setExpression(String input) throws InvalidSyntaxException {
    try {
      expression = Parser.parse(input);
      empty = expression == null;
    } catch (InvalidTokenException e) {
      expression = null;
      empty = true;
    }
  }

  /** @return a string representing the value, if any, of this cell. */
  @Override
  public String toString() {
    return empty ? "" : value.toString();
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void addDependent(CellLocation location) {
    dependents.add(location);
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void removeDependent(CellLocation location) {
    dependents.remove(location);
  }

  /**
   * Adds this cell's expression's references to a set.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param target The set that will receive the dependencies for this
   */
  public void findCellReferences(Set<CellLocation> target) {
    if (!empty) {
      expression.findCellReferences(target);
    }
  }

  /**
   * Recalculates this cell's value based on its expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void recalculate() throws InvalidSyntaxException {
    value = empty? 0.0 : expression.evaluate(spreadsheet);
    for (CellLocation dependent : dependents) {
      spreadsheet.recalculate(dependent);
    }
  }
}
