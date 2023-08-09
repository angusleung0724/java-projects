package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Set;

public class Numbers implements Expression {

  public final Double val;

  public Numbers(Double val) {
    this.val = val;
  }

  @Override
  public String toString() {
    return Double.toString(val);
  }

  public double evaluate(EvaluationContext context) { return val; }

  public void findCellReferences(Set<CellLocation> dependencies) { return; }
}
