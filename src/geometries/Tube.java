/**
 * The Tube class represents a tube-shaped 3D geometrical object.
 * It extends the RadialGeometry class and includes an axis ray for the tube.
 *
 * @author: Avigail Tenenbaum and Noa Landman
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/** @author Avigail and Noa */
public class Tube extends RadialGeometry {

    /**
     * The axis ray of the tube..
     */
    protected final Ray axisRay;

    /**
     * Constructs a Tube object with the specified radius and axis ray.
     * @param radius the radius of the tube
     * @param axisRay the axis ray of the tube
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }


    /**
     * Returns the axis ray of the tube.
     * @return the axis ray of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Computes and returns the normal vector to the given point on the tube's surface.
     * @param point a point on the surface of the tube
     * @return the normal vector to the given point on the tube's surface
     */
    @Override
    public Vector getNormal(Point point) {
        //TODO
        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        Vector P0_P = point.subtract(P0);

        double t = alignZero(v.dotProduct(P0_P));

        if (isZero(t)) {
            return P0_P.normalize();
        }

        Point o = P0.add(v.scale(t));

        if (point.equals(o)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }

        Vector n = point.subtract(o).normalize();

        return n;
    }

    /**
     * find intersection points between ray and 3D tube
     * @param ray ray towards the sphere
     * @return immutable list containing 0/1/2 intersection points as {@link GeoPoint}s objects
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector v = ray.getDir();
        Vector vt = axisRay.getDir();
        Point pa = axisRay.getP0();
        Point p0 = ray.getP0();

        // ray is parallel to tube
        if (v.equals(vt))
            return null;



        double vvt, a, b, c;
        // calculate (v,vt)
        vvt = v.dotProduct(vt);

        Vector vMinusVt =null;
        Vector delta = null;

        // scaling factor == 0 ->  A = v²
        if (isZero(vvt))
            a = v.lengthSquared();
        else { // scaling factor != 0 -> A = (v -(v,vt)*vt)²
            vMinusVt = v.subtract(vt.scale(vvt));
            a = vMinusVt.lengthSquared();
        }

        // origin points are equal -> ∆p == 0
        // -> B = 0, C = -radius²
        if(p0.equals(pa))
        {
            b = 0;
            c = -(radius * radius);
        }
        else {

            // calculate ∆p
            delta = p0.subtract(pa);

            // attempt to calculate B formula - if succeeded
            // B and C will be calculated appropriately
            // will fail if one of two occurs:
            // 1. (∆p, vt) == 0 , cannot scale vector with 0
            // 2. (∆p - (∆p , vt)vt) == 0 , dot product constructs vector(0,0,0)
            // if exception is thrown , catch block calculates B,C appropriately
            try {
                Vector deltaMinusVt = delta.subtract(vt.scale(delta.dotProduct(vt)));
                // vvt == 0 -> B = 2 * (v , ∆p - (∆p,vt)*vt)
                if(isZero(vvt))
                    b= 2*v.dotProduct(deltaMinusVt);
                    // vvt != 0
                else
                    b = 2 * (vMinusVt.dotProduct(deltaMinusVt));

                c = deltaMinusVt.lengthSquared() - (radius * radius);
            }
            catch (IllegalArgumentException ex) {
                double scaleFactor = delta.dotProduct(vt);

                // scale factor (∆p, vt) == 0
                if(isZero(scaleFactor)){
                    b = 2 * v.dotProduct(delta);
                    c = delta.lengthSquared() - (radius * radius);
                }
                // ∆p - (∆p , vt)vt == 0
                else {
                    b=0;
                    c = -(radius * radius);
                }
            }
        }


        // calculate roots of polynomial equation (-b+/- sqrt(b²-4ac))/2a
        double discriminant = alignZero(b * b - 4 * a * c);

        // results ∈ C -> no intersection points
        if (discriminant <= 0)
            return null;
        else {
            // calculate two roots - t1,t2
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

            // check that distance from ray origin to intersection points
            // is smaller than max distance parameter set by user
            boolean distanceT1 = alignZero(t1-maxDistance) <= 0;
            boolean distanceT2 = alignZero(t2-maxDistance) <= 0;

            // root> 0 indicates that scaling factor is in
            // forward direction of ray and , intersection occurs
            // root < 0 indicates scale factor is in opposite direction
            // no intersection occurs
            if (t1 > 0 && t2 > 0 && distanceT1 && distanceT2)
                return List.of(new GeoPoint(this,ray.getPoint(t2)), new GeoPoint(this,ray.getPoint(t1)));
            if (t1 > 0 && distanceT1)
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            if (t2 > 0 && distanceT2)
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
        }

        return null;
    }
}