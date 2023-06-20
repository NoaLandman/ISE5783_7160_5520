package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationTests {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * test integration ray from camera with sphere
     */
    @Test
    public void testIntegrationWithSphere() {
        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);

        // TC01: Two intersection points
        assertEquals(2, calcSumIntersection(camera, sphere, 3, 3), "First test case: should be 2 intersection points");

        // TC02: 18 intersection points
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);
        assertEquals(18, calcSumIntersection(camera, sphere, 3, 3),
                "Second test case: should be 18 intersection points");

        // TC03: 10 intersection points
        sphere = new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, calcSumIntersection(camera, sphere, 3, 3),
                "Third test case: should be 10 intersection points");

        // TC04: 9 intersection points
        sphere = new Sphere(4, new Point(0, 0, -1));
        assertEquals(9, calcSumIntersection(camera, sphere, 3, 3),
                "Fourth test case: should be 9 intersection points");

        // TC05: 0 intersection points
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, calcSumIntersection(camera, sphere, 3, 3),
                "Fifth test case: should be 0 intersection points");
    }

    /**
     * test integration ray from camera with plane
     */
    @Test
    public void testIntegrationWithPlane() {
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPDistance(1).setVPSize(3, 3);

        // TC01: 9 intersection points
        assertEquals(9, calcSumIntersection(camera, plane, 3, 3),
                "First test case: should be 9 intersection points");

        // TC02: 9 intersection points
        plane = new Plane(new Point(0, 0, -2.5), new Vector(0, -0.9, 1));
        assertEquals(9, calcSumIntersection(camera, plane, 3, 3),
                "Second test case: should be 9 intersection points");

        // TC03: 6 intersection points
        plane = new Plane(new Point(0, 0, -3), new Vector(0, -1, 1));
        assertEquals(6, calcSumIntersection(camera, plane, 3, 3),
                "Third test case: should be 6 intersection points");

    }

    /**
     * test integration ray from camera with triangle
     */
    @Test
    public void testIntegrationWithTriangle() {
        Triangle tri1 = new Triangle(
                new Point(1, -1, -2),
                new Point(0, 1, -2),
                new Point(-1, -1, -2));

        Camera camera = new Camera(
                ZERO_POINT,
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: 1 intersection point
        assertEquals(1, calcSumIntersection(camera, tri1, 3, 3),
                "First test case: should be 1 intersection point");

        // TC02: 2 intersection points
        Triangle tri2 = new Triangle(
                new Point(1, -1, -2),
                new Point(0, 20, -2),
                new Point(-1, -1, -2));


        assertEquals(2, calcSumIntersection(camera, tri2, 3, 3),
                "Second test case: should be 2 intersection points");
    }

    /**
     * The function doing
     * <li>Generate rays through all pixels of View Plane
     * <li>Summarize amount of intersections of all the rays
     *
     * @param cam  -the current camera
     * @param body -geometry body that implements {@link geometries.Intersectable}
     * @param nX   - sum of columns in view plane
     * @param nY   - sum of lines in view plane
     * @return sum of intersections between "body" and every ray from "cam"
     */
    private int calcSumIntersection(Camera cam, Intersectable body, int nX, int nY) {
        List<Ray> rays = new LinkedList<Ray>();
        for (int row = 0; row < nX; row++)
            for (int column = 0; column < nY; column++) {
                rays.add(cam.constructRay(nX, nY, column, row));
            }

        var result2 = rays
                .stream()
                .map(x -> body.findIntersections(x))
                .filter(x -> x != null)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .size();

        return result2;
    }
}
