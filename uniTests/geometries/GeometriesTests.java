package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
class GeometriesTests {

    @Test
    void testFindIntsersections() {
        try
        {

            //=====Empty body collection (BVA)=====//
            Geometries collection= new Geometries();
            assertNull( collection.findIntsersections(new Ray(new Point(-4, -3, 2),new Vector(9,5,-1))),"An empty body collection must return null");

            //=====No cut shape (BVA)=====//
            Sphere sphere = new Sphere(1,new Point(1, 0, 0));
            Triangle triangle = new Triangle(new Point(-4,0,0), new Point(0, 0, 5), new Point(0, -5, 0));
            Plane plane = new Plane (new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2));

            collection.add(sphere, triangle, plane/*, tube ,cylinder*/);

            assertNull(collection.findIntsersections(new Ray( new Point(0, -8, 0),new Vector(-10,-1,0))),"No cut shape must return 0");

            //=====Only one shape is cut (BVA)=====//
            //the plane cut
            assertEquals( 1, collection.findIntsersections(new Ray( new Point(-0.8, -3, 1),new Vector(3.4,3,1.57))).size(),"wrong number of intersactions");


            //=====Some (but not all) shapes are cut (EP)=====//
            //triangle and plane cut
            assertEquals( 2, collection.findIntsersections(new Ray(new Point(-4, -3, 2), new Vector(9,5,-1))).size(),"wrong number of intersactions");



            //=====All shapes are cut (BVA)=====//
            assertEquals( 4, collection.findIntsersections(new Ray( new Point(-4, -3, 0),new Vector(6,3,0.5))).size(),"wrong number of intersactions");

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("dont need throws exceptions!!!");
        }

    }
}