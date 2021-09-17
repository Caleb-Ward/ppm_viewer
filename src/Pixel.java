public class Pixel {
  private int[] rgb;

  public Pixel(int r, int g, int b) {
    rgb = new int[3];
    rgb[0] = r;
    rgb[1] = g;
    rgb[2] = b;
  }

  public int[] getRgb() { return rgb; }
  public int getR() { return rgb[0]; }
  public int getG() { return rgb[1]; }
  public int getB() { return rgb[2]; }

  public String toString() {
    return "(" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")";
  }

}
