
public class Util {
    public static boolean clockWise(Point p1, Point p2, Point p3) {
        return determinant(p1, p2, p3) < 0;
    }

    public static boolean counterClockWise(Point p1, Point p2, Point p3) {
        return determinant(p1, p2, p3) > 0;
    }

    private static int determinant(Point p1, Point p2, Point p3) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        int x3 = p3.getX(), y3 = p3.getY();

        return x1 * (y2 - y3) - y1 * (x2 - x3) + (x2 * y3 - y2 * x3);
    }
}
