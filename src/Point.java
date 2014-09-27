
public class Point implements Comparable<Point>{
    private final int myX;
    private final int myY;

    public Point(int x, int y) {
        myX = x;
        myY = y;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this ) {
            return true;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point otherPoint = (Point)other;
        return myX == otherPoint.myX && myY == otherPoint.myY;
    }

    @Override
    public int compareTo(Point other) {
        if (this.equals(other)) {
            return 0;
        }
        if (this.myX > other.myX) {
            return 1;
        }
        if (this.myX < other.myX) {
            return -1;
        }

        return this.myY > other.myY ? 1 : -1;
    }

    @Override
    public String toString() {
        return "(" + myX + ", " + myY + ")";
    }
}
