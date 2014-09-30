import java.util.ArrayList;
import java.util.List;

public class MonotonePolygon {
    private final List<Point> myVertices;
    private final List<Diagonal> myDiagonals;
    private final List<Triangle> myTriangles;
    private final int myLeftMost;
    private final int myRightMost;

    public MonotonePolygon(List<Point> vertices) {
        myVertices = vertices;
        myDiagonals = new ArrayList<Diagonal>();
        myTriangles = new ArrayList<Triangle>();
        myLeftMost = 0;
        myRightMost = getRightMostVertex(vertices);
    }

    private int getRightMostVertex(final List<Point> vertices) {
        final int count = vertices.size();
        for (int current = 0; current < count; current++) {
            Point currentVertex = getVertex(current);
            Point prevVertex = getVertex(getPrev(current));
            Point nextVertex = getVertex(getNext(current));
            if (currentVertex.compareTo(prevVertex) > 0 && currentVertex.compareTo(nextVertex) > 0) {
                return current;
            }
        }

        return 0;
    }

    public Point getVertex(int number) {
        return myVertices.get(number);
    }

    public int getNext(int current) {
        int next = current + 1;

        return next == myVertices.size() ? 0 : next;
    }

    public int getPrev(int current) {
        int prev = current - 1;

        return prev == -1 ? myVertices.size() - 1 : prev;
    }

    public List<PointOnChain> getLowerChain() {
        List<PointOnChain> lowerChain = new ArrayList<PointOnChain>();
        for (int i = getNext(myLeftMost); i < myRightMost; i++) {
            lowerChain.add(new PointOnChain(getVertex(i), i, false));
        }

        return lowerChain;
    }

    public List<PointOnChain> getUpperChain() {
        List<PointOnChain> upperChain = new ArrayList<PointOnChain>();
        for (int i = getPrev(myLeftMost); i > myRightMost; i--) {
            upperChain.add(new PointOnChain(getVertex(i), i, true));
        }

        return upperChain;
    }

    public Point getLeftMost() {
        return getVertex(myLeftMost);
    }

    public Point getRightMost() {
        return getVertex(myRightMost);
    }

    public void addDiagonal(int vertexNumber1, int vertexNumber2) {
        myDiagonals.add(new Diagonal(vertexNumber1, vertexNumber2));
    }

    public List<PointOnChain> getSortedVertices() {
        List<PointOnChain> lowerChain = getLowerChain();
        List<PointOnChain> upperChain = getUpperChain();

        List<PointOnChain> sortedVertices = new ArrayList<PointOnChain>();
        sortedVertices.add(new PointOnChain(getLeftMost(), myLeftMost, false));
        sortedVertices.addAll(mergeChains(lowerChain, upperChain));
        sortedVertices.add(new PointOnChain(getRightMost(), myRightMost, true));

        return sortedVertices;
    }

    private List<PointOnChain> mergeChains(List<PointOnChain> lowerChain, List<PointOnChain> upperChain) {
        List<PointOnChain> sortedChain = new ArrayList<PointOnChain>();

        int i = 0, j = 0;
        while (i < lowerChain.size() && j < upperChain.size()) {
            PointOnChain lowerPoint = lowerChain.get(i);
            PointOnChain upperPoint = upperChain.get(j);
            if (lowerPoint.getPoint().compareTo(upperPoint.getPoint()) < 0) {
                sortedChain.add(lowerPoint);
                i++;
            }
            else {
                sortedChain.add(upperPoint);
                j++;
            }
        }
        while (i < lowerChain.size()) {
            sortedChain.add(lowerChain.get(i));
            i++;
        }
        while (j < upperChain.size()) {
            sortedChain.add(upperChain.get(j));
            j++;
        }

        return sortedChain;
    }

    public void addTriangle(int vertexNumber1, int vertexNumber2, int vertexNumber3) {
        myTriangles.add(new Triangle(vertexNumber1, vertexNumber2, vertexNumber3));
    }

    public void printTriangulation() {
        for (Triangle triangle: myTriangles) {
            System.out.println(triangle);
        }
    }

    class Diagonal {
        private final int myVertexNumber1;
        private final int myVertexNumber2;

        public Diagonal(int vertexNumber1, int vertexNumber2) {
            myVertexNumber1 = Math.min(vertexNumber1, vertexNumber2);
            myVertexNumber2 = Math.max(vertexNumber1, vertexNumber2);
        }
    }

    public class Triangle {
        private final int myV1;
        private final int myV2;
        private final int myV3;

        public Triangle(int v1, int v2, int v3) {
            int min = Math.min(v1, Math.min(v2, v3));
            int max = Math.max(v1, Math.max(v2, v3));
            int middle = v1 + v2 + v3 - min - max;

            myV1 = min;
            myV2 = middle;
            myV3 = max;
        }

        @Override
        public String toString() {
            return  "(" + myV1 + ", " + myV2 + ", " + myV3 + ")";
        }
    }
}
