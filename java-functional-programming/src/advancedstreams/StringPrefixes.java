package advancedstreams;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringPrefixes {

  /**
   * Return the number of strings in the "strings" stream that start with the "prefix" string.
   */
  public static int countStringsStartingWithPrefix(Stream<String> strings, String prefix) {
    return (int) strings.filter(item -> item.startsWith(prefix)).count();
  }

  /**
   * If the "strings" stream contains a string that starts with "prefix", return this string but
   * with the prefixed part emphasised by enclosing it with asterisks. For example, if the prefix is
   * "foo" and the stream starts with "fooobar" then "*foo*bar" should be returned. If no string in
   * the stream starts with the given prefix, "N/A" should be returned.
   */
  public static String emphasiseFirstStringStartingWithPrefix(
          Stream<String> strings, String prefix) {
    Optional<String> result = strings.filter(item -> item.startsWith(prefix)).findFirst();
    if (result.isEmpty()) {
      return "N/A";
    } else {
      return getResult(result.get(),prefix);
    }
  }

  private static String getResult(String string, String prefix) {
    return ("*" + prefix + "*" + string.split(prefix,2)[1]);
  }

  /**
   * Return a list containing the distinct strings in "strings" that start with prefix "prefix". The
   * resulting strings should be ordered according to their first occurrence in the input stream.
   */
  public static List<String> distinctStringsStartingWithPrefix(
          Stream<String> strings, String prefix) {
    return strings.filter(item -> item.startsWith(prefix)).distinct().collect(Collectors.toList());
  }
}
