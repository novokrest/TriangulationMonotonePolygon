import java.io.*;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Point> vertices = PointReader.readPoints(reader);
            MonotonePolygon polygon = new MonotonePolygon(vertices);
            triangulateMonotonePolygon(polygon);
            polygon.printTriangulation();
        } catch (IOException e) {
            System.out.println("Strange IOException happened during reading. Message: " + e.getMessage());
        } catch (PointReader.IncorrectInputDataException e) {
            System.out.println("Incorrect input data format.");
        }
    }

    private static void triangulateMonotonePolygon (MonotonePolygon polygon) {
        List<PointOnChain> sortedVertices = polygon.getSortedVertices();
        Stack<PointOnChain> stack = new Stack<PointOnChain>();
        stack.add(sortedVertices.get(0));
        stack.add(sortedVertices.get(1));
        int count = sortedVertices.size();

        for (int current = 2; current < count - 1; current++) {
            PointOnChain currentVertex = sortedVertices.get(current);
            PointOnChain lastPopped = stack.pop();
            if (currentVertex.isUpperChain() != lastPopped.isUpperChain()) {
                polygon.addDiagonal(currentVertex.getNumber(), lastPopped.getNumber());
                polygon.addTriangle(currentVertex.getNumber(), lastPopped.getNumber(), stack.peek().getNumber());
                while (stack.size() > 1) {
                    lastPopped = stack.pop();
                    polygon.addDiagonal(currentVertex.getNumber(), lastPopped.getNumber());
                    polygon.addTriangle(currentVertex.getNumber(), lastPopped.getNumber(), stack.peek().getNumber());
                }
                if (!stack.empty()) stack.pop();
                stack.push(sortedVertices.get(current - 1));
                stack.push(sortedVertices.get(current));
            }
            else {
                while (!stack.empty() && isDiagonalInsidePolygon(currentVertex, lastPopped, stack.peek())) {
                    polygon.addTriangle(currentVertex.getNumber(), lastPopped.getNumber(), stack.peek().getNumber());
                    lastPopped = stack.pop();
                    polygon.addDiagonal(currentVertex.getNumber(), lastPopped.getNumber());
                }

                stack.push(lastPopped);
                stack.push(currentVertex);
            }
        }

        PointOnChain rightMost = sortedVertices.get(count - 1);
        while (stack.size() > 1) {
            PointOnChain lastPopped = stack.pop();
            polygon.addDiagonal(rightMost.getNumber(), stack.peek().getNumber());
            polygon.addTriangle(rightMost.getNumber(), lastPopped.getNumber(), stack.peek().getNumber());
        }
    }

    private static boolean isDiagonalInsidePolygon(PointOnChain rightPoint, PointOnChain middlePoint, PointOnChain leftPoint) {
        boolean isClockWise = Util.clockWise(rightPoint.getPoint(), middlePoint.getPoint(), leftPoint.getPoint());
        boolean isCounterClockWise = Util.counterClockWise(rightPoint.getPoint(), middlePoint.getPoint(), leftPoint.getPoint());
        return rightPoint.isUpperChain() ? isCounterClockWise : isClockWise;
    }
}
