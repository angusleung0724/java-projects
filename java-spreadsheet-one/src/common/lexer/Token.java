package common.lexer;

import common.api.CellLocation;

import java.util.Map;
import java.util.Objects;

import static common.lexer.Token.Kind.*;

/**
 * Representation of a token from the input string.
 *
 * <p>If `kind` if NUMBER or CELL_LOCATION, then the represented value may be found respectively in
 * `numberValue` and `cellLocationValue`.
 */
public class Token {
  public final Kind kind;
  public final CellLocation cellLocationValue;
  public final double numberValue;

  Token(Kind kind) {
    this(kind, null, 0);
    assert (kind != NUMBER && kind != CELL_LOCATION);
  }

  Token(double value) {
    this(NUMBER, null, value);
  }

  Token(CellLocation cellLocation) {
    this(CELL_LOCATION, cellLocation, 0);
  }

  private Token(Kind kind, CellLocation cellLocationValue, double numberValue) {
    this.kind = kind;
    this.cellLocationValue = cellLocationValue;
    this.numberValue = numberValue;
  }

  @Override
  public String toString() {
    switch (kind) {
      case CELL_LOCATION:
        return "CELL(" + cellLocationValue.toString() + ")";
      case NUMBER:
        return "NUMBER(" + numberValue + ")";
      default:
        return kind.name();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Token other = (Token) obj;
    if (this.kind != other.kind) {
      return false;
    }
    switch (this.kind) {
      case NUMBER:
        return this.numberValue == other.numberValue;
      case CELL_LOCATION:
        return this.cellLocationValue.equals(other.cellLocationValue);
      default:
        return true;
    }
  }

  @Override
  public int hashCode() {
    switch (kind) {
      case NUMBER:
        return Objects.hash(kind, numberValue);
      case CELL_LOCATION:
        return Objects.hash(kind, cellLocationValue);
      default:
        return Objects.hash(kind);
    }
  }

  public static final Map<Kind,Integer> precedenceMap = Map.of(
          PLUS, 1,
          MINUS, 1,
          STAR, 2,
          SLASH, 2,
          CARET,3,
          LPARENTHESIS, 0,
          RPARENTHESIS, 0
  );

  public static final Map<Kind,String> associativityMap = Map.of(
          PLUS, "L",
          MINUS, "L",
          STAR, "L",
          SLASH, "L",
          CARET,"R",
          LPARENTHESIS, "R",
          RPARENTHESIS, "R"
  );

  public enum Kind {
    PLUS,
    MINUS,
    STAR,
    SLASH,
    CARET,
    LPARENTHESIS,
    RPARENTHESIS,
    LANGLE,
    RANGLE,
    EQUALS,
    NUMBER,
    CELL_LOCATION;


    @Override
    public String toString() {
      String result = switch(this) {
        case PLUS -> "+";
        case MINUS -> "-";
        case STAR -> "*";
        case SLASH -> "/";
        case CARET -> "^";
        case LPARENTHESIS -> "(";
        case RPARENTHESIS -> ")";
        case LANGLE -> "<";
        case RANGLE -> ">";
        case EQUALS -> "=";
        default -> "";
      };
      return result;
    }

    public boolean lowerThan(Kind other) {
      if (precedenceMap.get(this) < precedenceMap.get(other)) {
        return true;
      } else if (precedenceMap.get(this) > precedenceMap.get(other)) {
        return false;
      } else {
        return (associativityMap.get(other).equals("L"));
      }
    }
  }
}
