package advancedstreams;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

interface Supplier<I> {
  Integer get();
}
public class CubeSupplier implements Supplier<Integer> {
  private static int cube;
  public CubeSupplier() {
    this.cube = 1;
  }

  public CubeSupplier(Integer num) {
    this.cube = num;
  }


  public Integer get() {
    int result = cube*cube*cube;
    if (result < 0) {
      throw new NoSuchElementException();
    }
    cube += 1;
    return result;
  }

  public static Stream<Integer> cubeStream() {
    return Stream.generate(new CubeSupplier()::get);
  }

  public static Stream<Integer> boundedCubeStream(Integer start, Integer end) {
    return Stream.generate(new CubeSupplier(start+1)::get).limit(end-start);
  }

  public static Stream<Integer> palindromicCubes(Integer start, Integer end) {
    return boundedCubeStream(start, end).filter(item -> IsPalindrome.isPalindrome(Integer.toString(item)));
  }


}
