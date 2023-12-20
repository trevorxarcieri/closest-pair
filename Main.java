//Author: Trevor Arcieri
//Date: 6/12/23

class Main {
  public static void main(String[] args) {
    PointsHandler handler = new PointsHandler();
    // long avgNano = 0;
    // for (int i = 0; i < 50; i++) {
    //   long start = System.nanoTime();
    //   handler.findClosestPairEff();
    // //   handler.findClosestPairBF();
    //   avgNano = (avgNano * (i) + System.nanoTime() - start) / (i + 1);
    // }
    // System.out.println(avgNano);
    handler.findClosestPairBF();
  handler.findClosestPairEff();
  }
}