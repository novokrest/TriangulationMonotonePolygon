import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PointReader {
    public static List<Point> readPoints(BufferedReader reader) throws IOException, IncorrectInputDataException {
        List<Point> vertices = new LinkedList<Point>();
        int vertexCount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < vertexCount; i++) {
            Point vertex = readPoint(reader);
            if (vertex == null) {
                throw new IncorrectInputDataException();
            }
            vertices.add(vertex);
        }

        return vertices;
    }

    public static Point readPoint(BufferedReader reader) throws IOException {
        String pointData = reader.readLine();
        if (pointData.length() > 2 && pointData.charAt(0) == '(' && pointData.charAt(pointData.length() - 1) == ')') {
            String[] coordinates = pointData.substring(0, pointData.length() - 1).substring(1).split(",");
            if (coordinates.length == 2) {
                return new Point(Integer.parseInt(coordinates[0].trim()), Integer.parseInt(coordinates[1].trim()));
            }
        }
        return null;
    }

    public static class IncorrectInputDataException extends Exception{
    }
}
