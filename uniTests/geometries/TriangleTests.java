package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/** @author Avigail and Noa */
class TriangleTests {
 /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
@Test
 void testGetNormal() {
 // ============ Equivalence Partitions Tests ==============
 // TC01: There is a simple single test here
 Point p1 = new Point(0, 0, 1);
 Point p2 = new Point(1, 0, 0);
 Point p3 = new Point(0, 1, 0);
 Triangle t = new Triangle(p1, p2, p3);
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
 /**
  * Test method for {@link geometries.Triangle#findIntsersections(Ray)} (primitives.Ray)}.
  */
 @Test
 public void testFindIntersections(){
  Triangle tr = new Triangle(new Point(0, 3, -3),new Point(3, 0, -3),new Point(-3, 0, -3));
  Ray r;

  // ============ Equivalence Partitions Tests ==============
  // TC01: the ray goes through the triangle
  try {
   r = new Ray(new Point(1, 1, -2),new Vector(-2, 0.5, -1));
   assertEquals(((new Point(-1, 1.5, -3))), tr.findIntsersections(r).get(0),"the ray goes through the triangle");
  }
  catch(IllegalArgumentException e) //catch creation of new vectors at findIntersections- one might be zero vector
  {}
  // TC02: the ray is outside the triangle against edge
  r = new Ray(new Point(4, 4, -2),new Vector(1, 1, -4));

  assertNull( tr.findIntsersections(r),"the ray is outside the triangle against edge");
  // TC03: the ray is outside the triangle against vertex
  r = new Ray(new Point(-4, -1, -2),new Vector(-1, -1, -1));
  assertNull(tr.findIntsersections(r),"the ray is outside the triangle against vertex");


  // =============== Boundary Values Tests ==================
  // TC04: ray through edge
  r = new Ray(new Point(-2, 1, -1),new Vector(0, 0, -1));
  assertNull(tr.findIntsersections(r),"ray through edge");

  // TC05: ray through vertex
  r = new Ray(new Point(0, 3, -2),new Vector(0, 0, -1));
  assertNull(tr.findIntsersections(r),"ray through vertex");

  // TC06: ray goes through the continuation of side 1
  r = new Ray(new Point(-1, 4, -2),new Vector(0, 0, -1));
  assertNull(tr.findIntsersections(r),"ray goes through the continuation of side 1");
 }
}
