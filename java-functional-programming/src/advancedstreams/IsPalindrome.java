package advancedstreams;

public class IsPalindrome {

  public static boolean isPalindrome(String string) {
    StringBuilder stringTwo = new StringBuilder();
    stringTwo.append(string).reverse();
    return string.equals(stringTwo.toString());
  }
}
