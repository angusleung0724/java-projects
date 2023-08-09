package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import common.lexer.Token.Kind;
import java.util.Set;

public class BinaryOperator implements Expression {

  public final Expression subOne, subTwo;

  public final Kind op;

  public BinaryOperator(Expression subOne, Expression subTwo, Kind op) {
    this.subOne = subOne;
    this.subTwo = subTwo;
    this.op = op;
  }

  @Override
  public String toString() {
    return ("(" + subOne + op + subTwo + ")");
  }

  public double evaluate(EvaluationContext context) {
    return switch (op) {
      case PLUS -> subOne.evaluate(context) + subTwo.evaluate(context);
      case MINUS -> subOne.evaluate(context) - subTwo.evaluate(context);
      case STAR -> subOne.evaluate(context) * subTwo.evaluate(context);
      case SLASH -> subOne.evaluate(context) / subTwo.evaluate(context);
      case CARET -> Math.pow (subOne.evaluate(context), subTwo.evaluate(context));
      default -> throw new IllegalArgumentException("Not provided a valid operator");
    };
  }
  public void findCellReferences(Set<CellLocation> dependencies) {
    subOne.findCellReferences(dependencies);
    subTwo.findCellReferences(dependencies);
  }
}
