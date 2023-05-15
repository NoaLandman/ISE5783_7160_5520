package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray through the scene and calculates the color of the closest intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color of the closest intersection point, or the background color if no intersections occur.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntsersections(ray);
        if (intersections != null) {
            Point closestPoint = ray.findClosestPoint(intersections);
            return calculateColor(closestPoint);
        }
        // No intersections
        return scene.background;
    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The color at the given point.
     */
    private Color calculateColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
