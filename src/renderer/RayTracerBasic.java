package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * A basic implementation of a ray tracer that traces rays through a scene and calculates the color of the closest intersection point.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;

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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        // No intersections
        return scene.background;
    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param geoPoint The point in the scene.
     * @param ray      The ray from the camera.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcLocalEffect(geoPoint, ray));
    }

    /**
     * Calculates the light contribution from all light sources at a given point on a geometry body.
     *
     * @param intersection The point on the geometry body.
     * @param ray          The ray from the camera.
     * @return The calculated light contribution from all light sources.
     */
    private Color calcLocalEffect(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v)); // nv = n * v
        if (nv == 0) {
            return Color.BLACK;
        }

        int nShininess = intersection.geometry.getMaterial().nshininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK; // base color

        // For each light source in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point); // The direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); // nl = n * l

            // If sign(nl) == sign(nv) (if the light hits the point add it, otherwise don't add this light)
            if (nl * nv > 0) {
                if (unshaded(lightSource, intersection, l, n)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(
                            calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity)
                    );
                }
            }
        }
        return color;
    }

    /**
     * Calculates the specular light according to Phong's model.
     *
     * @param ks             The coefficient for specular.
     * @param l              The vector from the light source.
     * @param n              The normal to the point.
     * @param v              The camera vector.
     * @param nShininess     The exponent.
     * @param lightIntensity The light intensity.
     * @return The specular light.
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n)); // ln = l * n
        Vector r = l.subtract(n.scale(2 * ln)).normalize(); // r = l - 2 * (l * n) * n
        double vr = alignZero(v.dotProduct(r)); // vr = v * r
        double vrnsh = pow(max(0, -vr), nShininess); // vrnsh = max(0, -vr) ^ nShininess
        return lightIntensity.scale(ks.scale(vrnsh)); // Ks * (max(0, - v * r) ^ Nsh) * Il
    }

    /**
     * Calculates the diffusive light according to Phong's model.
     *
     * @param kd              The coefficient for diffusive.
     * @param l               The vector from the light source.
     * @param n               The normal to the point.
     * @param lightIntensity  The light intensity.
     * @return The diffusive light.
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(abs(l.dotProduct(n))); // ln = |l * n|
        return lightIntensity.scale(kd.scale(ln)); // Kd * |l * n| * Il
    }

    /**
     * Checks if a point on a geometry body is unshaded by a given light source.
     *
     * @param light       The light source.
     * @param gp          The point on the geometry body.
     * @param l           The light direction vector.
     * @param n           The normal vector.
     * @return True if the point is unshaded by the light source, false otherwise.
     */
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // From point to light source
        double nl = n.dotProduct(lightDirection);

        Vector delta = n.scale(nl > 0 ? DELTA : -DELTA);
        Point pointRay = gp.point.add(delta);
        Ray lightRay = new Ray(pointRay, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {
            return true;
        }

        for (var item : intersections) {
            for (var item2 : item.geometry.findIntersections(lightRay)) {
                if (light.getDistance(gp.point) > item2.distance(gp.point)) {
                    return false;
                }
            }
        }

        return true;
    }
}
