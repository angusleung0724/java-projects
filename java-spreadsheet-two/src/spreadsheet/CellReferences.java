package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Set;

public class CellReferences implements Expression {

  public final CellLocation targetCell;

  public CellReferences(CellLocation targetCell) {
    this.targetCell = targetCell;
  }

  @Override
  public String toString() {
    return targetCell.toString();
  }

  public double evaluate(EvaluationContext context) { return context.getCellValue(targetCell); }
  public void findCellReferences(Set<CellLocation> dependencies) { dependencies.add(targetCell); }
}
