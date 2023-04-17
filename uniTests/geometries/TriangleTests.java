package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTests {
 /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
@Test
 void testGetNormal(){
 // ============ Equivalence Partitions Tests ==============
 // TC01: There is a simple single test here
 Point p1=new Point(0, 0, 1);
 Point p2=new Point(1, 0, 0);
 Point p3=new Point(0, 1, 0);
 Triangle t = new Triangle(p1,p2,p3);
 // ensure there are no exceptions
 assertDoesNotThrow(() -> t.getNormal(new Point(0, 0, 1)), "");
 // generate the test result
 Vector result = t.getNormal(new Point(0, 0, 1));
 // ensure |result| = 1
 assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
 // ensure the result is orthogonal to all the edges
 assertTrue(isZero(result.dotProduct(p1.subtract(p2))), "Triangle's normal is not orthogonal to one of the Edges");
 assertTrue(isZero(result.dotProduct(p2.subtract(p3))), "Triangle's normal is not orthogonal to one of the Edges");
 assertTrue(isZero(result.dotProduct(p3.subtract(p1))), "Triangle's normal is not orthogonal to one of the Edges");
}
}