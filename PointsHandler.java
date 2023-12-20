//Author: Trevor Arcieri
//Date: 6/12/23

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class PointsHandler {
  Point[] points;

  public PointsHandler() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Number of points (n): ");
    int n = scanner.nextInt();
    System.out.print("Size of the square (s): ");
    int s = scanner.nextInt();
    Random random = new Random();
    points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = random.nextInt(s);
      int y = random.nextInt(s);
      points[i] = new Point(x, y);
    }
    scanner.close();
    System.out.println(pointsToString(points));
  }

  public void findClosestPairBF() {
    Pair closest = bruteForceClosestPair(points);
    System.out.println("Closest pair: " + pointsToString(new Point[] { closest.p1, closest.p2 }) + " has a distance of "
        + closest.distance);
  }

  public void findClosestPairEff() {
    Point[] pointsX = arrCopy(points, 0, points.length);
    Point[] pointsY = arrCopy(points, 0, points.length);
    quicksortX(pointsX, 0, pointsX.length - 1);
    quicksortY(pointsY, 0, pointsY.length - 1);
    Pair closest = efficientClosestPair(pointsX, pointsY);
    System.out.println("Closest pair: " + pointsToString(new Point[] { closest.p1, closest.p2 }) + " has a distance of "
        + closest.distance);
  }

  public static String pointsToString(Point[] points) {
    String ret = "";
    for (int i = 0; i < points.length; i++) {
      ret += points[i].toString() + ", ";
    }
    ret = ret.substring(0, ret.length() - 2);
    return ret;
  }

  public static Pair efficientClosestPair(Point[] P, Point[] Q) {
    int n = P.length;
    if (n <= 3) {
      return bruteForceClosestPair(P);
    }
    int mid = (n + 1) / 2;
    Point[] Pl = arrCopy(P, 0, mid);
    Point[] Pr = arrCopy(P, mid, n);
    Point[] Ql = new Point[mid];
    Point[] Qr = new Point[n - mid];
    int leftIndex = 0;
    int rightIndex = 0;
    for (Point q : Q) {
      if (Arrays.asList(Pl).contains(q)) {
        Ql[leftIndex++] = q;
      } else {
        Qr[rightIndex++] = q;
      }
    }
    Pair dl = efficientClosestPair(Pl, Ql);
    Pair dr = efficientClosestPair(Pr, Qr);
    Pair d = (dl.distance < dr.distance) ? dl : dr;
    int num = 0;
    Point[] S = new Point[n];
    int m = P[mid - 1].x;
    for (Point q : Q) {
      if (Math.abs(q.x - m) < d.distance) {
        S[num++] = q;
      }
    }
    for (int i = 0; i < num - 1; i++) {
      int k = i + 1;
      while (k < num && (S[k].y - S[i].y) * (S[k].y - S[i].y) < d.distance * d.distance) {
        double distance = calculateDistance(S[i], S[k]);
        if (distance < d.distance) {
          d = new Pair(distance, S[i], S[k]);
        }
        k++;
      }
    }
    return d;
  }

  public static Pair bruteForceClosestPair(Point[] points) {
    Pair minDist = new Pair(Double.MAX_VALUE, new Point(-1, -1), new Point(-1, -1));
    int n = points.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        double distance = calculateDistance(points[i], points[j]);
        if (distance < minDist.distance) {
          minDist = new Pair(distance, points[i], points[j]);
        }
      }
    }
    return minDist;
  }

  public static Point[] arrCopy(Point[] arr, int beg, int end) {
    Point[] ret = new Point[end - beg];
    for(int i = beg; i < end; i++) {
      ret[i - beg] = arr[i];
    }
    return ret;
  }

  public static double calculateDistance(Point p1, Point p2) {
    int deltaX = p2.x - p1.x;
    int deltaY = p2.y - p1.y;
    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  public static void quicksortX(Point[] points, int low, int high) {
    if (low < high) {
      int pi = partitionX(points, low, high);
      quicksortX(points, low, pi - 1);
      quicksortX(points, pi + 1, high);
    }
  }

  public static int partitionX(Point[] points, int low, int high) {
    Point pivot = points[low];
    int i = low - 1;
    int j = high + 1;
    while (true) {
      do {
        i++;
      } while (i <= high && points[i].x < pivot.x);
      do {
        j--;
      } while (j >= low && points[j].x > pivot.x);
      if (i >= j) {
        swap(points, low, j);
        return j;
      }
      swap(points, i, j);
    }
  }

  public static void quicksortY(Point[] points, int low, int high) {
    if (low < high) {
      int pi = partitionY(points, low, high);
      quicksortY(points, low, pi - 1);
      quicksortY(points, pi + 1, high);
    }
  }

  public static int partitionY(Point[] points, int low, int high) {
    Point pivot = points[low];
    int i = low - 1;
    int j = high + 1;
    while (true) {
      do {
        i++;
      } while (i <= high && points[i].y < pivot.y);
      do {
        j--;
      } while (j >= low && points[j].y > pivot.y);
      if (i >= j) {
        swap(points, low, j);
        return j;
      }
      swap(points, i, j);
    }
  }

  public static void swap(Point[] points, int i, int j) {
    Point temp = points[i];
    points[i] = points[j];
    points[j] = temp;
  }

}