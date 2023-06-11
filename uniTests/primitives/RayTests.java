package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTests {
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(Double3.ZERO), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: A point in the middle of the list is closest to the beginning of the
        // Ray
        assertEquals(new Point(1.5, 0, 0),
                ray.findClosestPoint(List.of(new Point(2, 0, 0),
                        new Point(1.5, 0, 0),
                        new Point(2, -1, 0))),
                "The middle point in the list is closest");

        // =============== Boundary Values Tests ==================

        // TC02: empty list
        assertNull(ray.findClosestPoint(List.of()), "empty list: expect to return null");

        // TC03: The first point is closest to the beginning of the Ray
        assertEquals(new Point(2, 0, 0),
                ray.findClosestPoint(List.of(new Point(2, 0, 0),
                        new Point(2, 1, 0),
                        new Point(2, -1, 0))),
                "The first point is closest");

        // TC04: The last point is closest to the beginning of the Ray
        assertEquals(new Point(2, 0, 0),
                ray.findClosestPoint(List.of(new Point(2, -1, 0),
                        new Point(2, 1, 0),
                        new Point(2, 0, 0))),
                "The last point is closest");
    }
}