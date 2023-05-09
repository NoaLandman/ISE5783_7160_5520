package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/** @author Avigail and Noa */
class TubeTests {

    /** Test method for {@link geometries.Tube#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube = new Tube(1,new Ray( new Point(0, 0, 1),new Vector(0, 0, 1)));

        // Check that the normal is correct
        assertEquals(new Point(1, 0, 0), tube.getNormal(new Point(1, 0, 6)),
                "getNormal(Point3D) -The normal to the Tube is not correct ");

        // =============== Boundary Values Tests ==================
        // Check when the point is in front of the head Ray
        assertEquals(new Point(0, 1, 0),
                tube.getNormal(new Point(0, 1, 1)),
                "getNormal() faild - point is in front of the head Ray!");
}
}
