package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        var ball = new Sphere(1,new Point(0, 0, 0));

        // Check that the normal is correct
        assertEquals( new Vector(1, 0, 0),
                ball.getNormal(new Point(1, 0, 0)),"getNormal(Point) -The normal to the Sphere is not correct ");
    }

}