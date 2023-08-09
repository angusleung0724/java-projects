package spreadsheet;

import common.api.Expression;
import common.lexer.Lexer;
import common.lexer.Token;

import java.util.List;
import java.util.Stack;


public class Parser {

  static Expression parse(String input) throws InvalidSyntaxException {
    List<Token> tokens;
    try {
      tokens = Lexer.tokenize(input);
    } catch (Exception InvalidTokenException) {
      throw new InvalidSyntaxException(input);
    }
    // Two stacks for shunting algorithm
    Stack<Expression> expressionStack = new Stack<>();
    Stack<Token> operandStack = new Stack<>();

    for (Token token : tokens) {
      switch (token.kind) {
        case NUMBER ->
          expressionStack.push(new Numbers(token.numberValue));
        case CELL_LOCATION ->
          expressionStack.push(new CellReferences(token.cellLocationValue));
        case PLUS, MINUS, STAR, SLASH, CARET, LPARENTHESIS, RPARENTHESIS -> {
          while (!(operandStack.empty()) && token.kind.lowerThan(operandStack.peek().kind)) {
            Expression subOne = expressionStack.pop();
            Expression subTwo = expressionStack.pop();
            Token.Kind op = operandStack.pop().kind;
            expressionStack.push(new BinaryOperator(subTwo, subOne, op));
            }
          operandStack.push(token);
      }
        default ->
          throw new InvalidSyntaxException(token.toString());
      }
    }
    while (!operandStack.empty()) {
      Expression subOne = expressionStack.pop();
      Expression subTwo = expressionStack.pop();
      Token.Kind op = operandStack.pop().kind;
      expressionStack.push(new BinaryOperator(subTwo, subOne, op));
    }
    return expressionStack.peek();
  }
}
