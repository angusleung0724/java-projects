package spreadsheet;

import common.api.Expression;
import common.lexer.InvalidTokenException;
import common.lexer.Lexer;
import common.lexer.Token.Kind;
import common.lexer.Token;

import java.util.List;
import java.util.Stack;


public class Parser {

  private static void popAndPush(Stack<Expression> operands, Stack<Kind> operators) {
    Expression operand1 = operands.pop();
    Expression operand2 = operands.pop();
    operands.push(new BinaryOperator(operand2, operand1, operators.pop()));
  }
  static Expression parse(String input) throws InvalidSyntaxException, InvalidTokenException {
    if (input.equals("")) {
      return null;
    }
    List<Token> tokens;
    tokens = Lexer.tokenize(input);
    // Two stacks for shunting algorithm
    Stack<Expression> operands = new Stack<>();
    Stack<Kind> operators = new Stack<>();

    for (Token token : tokens) {
      switch (token.kind) {
        case NUMBER -> operands.push(new Numbers(token.numberValue));
        case CELL_LOCATION -> operands.push(new CellReferences(token.cellLocationValue));
        case PLUS, MINUS, STAR, CARET, SLASH -> {
          while (!operators.isEmpty() && token.kind.lowerThan(operators.peek())) {
            Parser.popAndPush(operands, operators);
          }
        operators.push(token.kind);
        }
        default -> throw new InvalidTokenException(token.kind.toString());
      }
    }
    while (!operators.isEmpty()) {
      Parser.popAndPush(operands, operators);
    }
    return operands.pop();
  }
}
