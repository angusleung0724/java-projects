package picture;

import java.util.ArrayList;

public class PictureProcessor {

  public static Picture invert(Picture input) {
    final int OFFSET = 255;
    for (int i = 0; i < input.getWidth(); i++) {
      for (int j = 0; j < input.getHeight(); j++) {
        var initial = input.getPixel(i, j);
        int red = OFFSET - initial.getRed();
        int green = OFFSET - initial.getGreen();
        int blue = OFFSET - initial.getBlue();
        var result = new Color(red, green, blue);
        input.setPixel(i, j, result);
      }
    }
    return input;
  }

  public static Picture grayScale(Picture input) {
    for (int i = 0; i < input.getWidth(); i++) {
      for (int j = 0; j < input.getHeight(); j++) {
        var initial = input.getPixel(i, j);
        var average = (initial.getBlue() + initial.getRed() + initial.getGreen()) / 3;
        var result = new Color(average, average, average);
        input.setPixel(i, j, result);
      }
    }
    return input;
  }

  public static Picture rotate(Picture input, String angle) {
    final Picture result = switch (angle) {
      case "90", "270" -> new Picture(input.getHeight(), input.getWidth());
      default -> new Picture(input.getWidth(), input.getHeight());
    };
    var width = result.getWidth();
    var height = result.getHeight();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        switch (angle) {
          case "90" -> result.setPixel(x, y, input.getPixel(y, width - x - 1));
          case "180" -> result.setPixel(x, y, input.getPixel(width - x - 1, height - y - 1));
          case "270" -> result.setPixel(x, y, input.getPixel(height - y - 1, x));
        }
      }
    }
    return result;
  }

  public static Picture flip(Picture input, String direction) {
    int width = input.getWidth();
    int height = input.getHeight();
    final var result = new Picture(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (direction == "H") {
          result.setPixel(i, j, input.getPixel(width - i - 1, j));
        } else {
          result.setPixel(i, j, input.getPixel(i, height - j - 1));
        }
      }
    }
    return result;
  }

  private static Color findBlend(int x, int y, ArrayList<Picture> pics) {
    int red = 0;
    int green = 0;
    int blue = 0;
    int count = pics.size();
    for (Picture pic : pics) {
      var color = pic.getPixel(x, y);
      red += color.getRed();
      green += color.getGreen();
      blue += color.getBlue();
    }
    return new Color(red / count, green / count, blue / count);
  }

  public static Picture blend(ArrayList<Picture> pictures, int width, int height) {
    var result = new Picture(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        result.setPixel(i, j, findBlend(i, j, pictures));
      }
    }
    return result;
  }

  private static Color getBlur(int x, int y, Picture input) {
    final int NTOTAL = 9;
    int red = 0;
    int green = 0;
    int blue = 0;
    for (int i = x-1; i < x+2; i++) {
      for (int j = y-1; j < y+2; j++) {
        var color = input.getPixel(i, j);
        red += color.getRed();
        green += color.getGreen();
        blue += color.getBlue();
      }
    }
    return new Color(red / NTOTAL, green / NTOTAL, blue / NTOTAL);
  }

  public static Picture blur(Picture input) {
    int width = input.getWidth();
    int height = input.getHeight();
    var result = new Picture(width, height);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
          result.setPixel(i, j, input.getPixel(i, j));
        } else {
          result.setPixel(i, j, getBlur(i, j, input));
        }
      }
    }
    return result;
  }

  public static void main(String[] args) {
    switch (args[0]) {
      case "invert" -> invert(new Picture(args[1])).saveAs(args[2]);
      case "grayscale" -> grayScale(new Picture(args[1])).saveAs(args[2]);
      case "rotate" -> rotate(new Picture(args[2]), args[1]).saveAs(args[3]);
      case "flip" -> flip(new Picture(args[2]), args[1]).saveAs(args[3]);
      case "blend" -> {
        int argLength = args.length - 1;
        var result = new ArrayList();
        var initialPic = new Picture(args[1]);
        int width = initialPic.getWidth();
        int height = initialPic.getHeight();
        result.add(initialPic);
        for (int i = 2; i < argLength; i++) {
          var pic = new Picture(args[i]);
          result.add(pic);
          width = Math.min(width, pic.getWidth());
          height = Math.min(height, pic.getHeight());
        }
        blend(result, width, height).saveAs(args[argLength]);
      }
      case "blur" -> blur(new Picture(args[1])).saveAs(args[2]);
    }
  }
}
