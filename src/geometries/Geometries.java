package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Geometries' class.
 * <p>
 * Represents a collection of geometries.
 */
public class Geometries extends Intersectable {

    private List<Intersectable> geometries;

    /**
     * A default constructor that creates a new empty ArrayList of intersectable geometries.
     */
    public Geometries() {
        geometries = new ArrayList<>();
    }

    /**
     * Constructor that receives a list of geometries and puts them in a new ArrayList.
     *
     * @param geometries The list of geometries to add.
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList<>(Arrays.asList(geometries));
    }

    /**
     * Adds the specified geometries to the list.
     *
     * @param geometries The geometries to add.
     */
    public void add(Intersectable... geometries) {
        if (geometries != null) {
            this.geometries.addAll(Arrays.asList(geometries));
        }
    }

    /**
     * Finds the geometric intersections of the geometries in the collection with the given ray.
     *
     * @param ray The ray to intersect with the geometries.
     * @return A list of GeoPoint objects representing the intersections, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (this.geometries.isEmpty())
            return null;
        List<GeoPoint> temp = new ArrayList<>();
        for (Intersectable intersectable : geometries) {
            List<GeoPoint> intersections = intersectable.findGeoIntersections(ray);
            if (intersections != null)
                temp.addAll(intersections);
        }

        if (temp.isEmpty())
            return null;
        return temp;
    }
}

