package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/** @author Avigail and Noa */
class PlaneTests {
    /**
     * Test method for {@link Plane#getNormal()} .
     */
    @Test
    void testGetNormal() {
        Point p1= new Point(1,2,0);
        Point p2= new Point(4,-9,0);
        Point p3= new Point(1,0,8);
        Plane p= new Plane(p1,p2, p3);
        Vector v1=p1.subtract(p2);
        Vector v2=p2.subtract(p3);
        Vector v3=p3.subtract(p1);
        Vector n=p.getNormal(p1);
        assertTrue(isZero(v1.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v2.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v3.dotProduct(n)),"ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
    }
    /** Test method for {@link geometries.Plane#Plane(primitives.Point,primitives.Point,primitives.Point)}. */
    @Test
    public void testConstructor(){
        // =============== Boundary Values Tests ==================
        // TC01:The points are on the same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(4, 8, 12)), //
                "Constructed a plane with points that are in the same line");
        //TC02:The first and second points are converge
              assertThrows(IllegalArgumentException.class, //
                   () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 8, 12)), //
                      "Constructed a plane with first and second points are converge");
    }
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)} (primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray into plane
        assertEquals(List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: Ray in plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");


        // TC13: Orthogonal ray start into plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, -1), new Vector(0, 0, 1))),
                "Must not be plane intersection");

        // TC14: Orthogonal ray start after of plane

        assertNull(pl.findIntersections(new Ray(new Point(1,1,3), new Vector(0, 0, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray start before of plane
        assertEquals(List.of(new Point(1, 1, -1)),pl.findIntersections(new Ray(new Point(1, 1, -3),
                new Vector(0, 0, 1))),"Bad plane intersection");

        // TC16: Ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

        // TC17: Ray from plane's Q point
        assertNull(pl.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0))),
                "Must not be plane intersection");
    }
    }