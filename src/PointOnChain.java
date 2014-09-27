
public class PointOnChain {
    private final Point myPoint;
    private final int myNumber;
    private final boolean myIsUpperChain;

    public PointOnChain(Point point, int number, boolean isUpperChain) {
        myPoint = point;
        myNumber = number;
        myIsUpperChain = isUpperChain;
    }

    public Point getPoint() {
        return myPoint;
    }

    public int getNumber() {
        return myNumber;
    }

    public boolean isUpperChain() {
        return myIsUpperChain;
    }

    @Override
    public String toString() {
        return myPoint.toString()
                + ", " + (myIsUpperChain ? "upper" : "lower")
                + ", " + myNumber;
    }
}
