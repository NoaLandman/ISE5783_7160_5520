package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint,ray);
        }
        // No intersections
        return scene.background;

    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param geoPoint The point in the scene.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint geoPoint,Ray ray)
    {
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcLocalEffect(geoPoint,ray));
    }
    /**
            * help to calculate "calcColor" - calculated light contribution from all light
     * sources
     *
             * @param intersection - point on geometry body
     * @param ray          - ray from the camera
     * @return calculated light contribution from all light sources
     */
    private Color calcLocalEffect(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v)); //nv=n*v
        if (nv == 0) {
            return Color.BLACK;
        }

        int nShininess = intersection.geometry.getMaterial().nshininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK; //base color

        //for each light source in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if sign(nl) == sign(nv) (if the light hits the point add it, otherwise don't add this light)
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }
//    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
//        Color col= intersection.geometry.getEmission();
//        Vector v = ray.getDir();
//        Vector n = intersection.geometry.getNormal(intersection.point);
//        double nv= alignZero(n.dotProduct(v));
//        if (nv== 0) return col;
//        Material material=intersection.geometry.getMaterial();
//        //int nShininess= intersection.geometry.getMaterial().nShininess;
//        //double kd= intersection.geometry.getMaterial().kD;
//        //double ks = intersection.geometry.getMaterial().kS;
//        for (LightSource lightSource: scene.lights)
//        {
//            Vector l = lightSource.getL(intersection.point);
//            double nl= alignZero(n.dotProduct(l));
//            if (nl * nv> 0)
//            { // sign(nl) == sing(nv)
//                Color lightIntensity= lightSource.getIntensity(intersection.point);
//                col = lightIntensity.scale(calcDiffusive(material, nl)+calcSpecular(material, n, l,nl, v)).add(col);
//            }
//        }
//        return col;
//    }
    /**
            * calculate the specular light according to Phong's model
            * @param ks - Coefficient for specular
     * @param l - vector from light source
     * @param n - normal to the point
     * @param v - camera vector
     * @param nShininess - exponent
     * @param lightIntensity - Light intensity
     * @return the specular light
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n)); //ln=l*n
        Vector r = l.subtract(n.scale(2 * ln)).normalize(); //r=l-2*(l*n)*n
        double vr = alignZero(v.dotProduct(r)); //vr=v*r
        double vrnsh = pow(max(0, -vr), nShininess); //vrnsh=max(0,-vr)^nshininess
        return lightIntensity.scale(ks.scale(vrnsh)); //Ks * (max(0, - v * r) ^ Nsh) * Il
    }

//    private double calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
//
//        Vector r = l.subtract(n.scale(2 * nl));
//        double minusVR= alignZero(-v.dotProduct(r));
//        if (minusVR <= 0)
//            return 0;
//        //color = ks * max(0, -v.r)^nSh @ppt 7 theoretical course
//        return material.kS* (Math.pow(Math.max(0, minusVR), material.nShininess));
//    }
    //    /
//     * Calculate color of the diffusive effects of the light
//     *
//     * @param kD diffusive ratio
//     * @param l light's direction vector
//     * @param n normal vector
//     * @param lightIntensity intensity of the light
//     * @return color of the diffusive effect
//     */
//    private double calcDiffusive(Material material, double nl) {
//        return material.kD*(nl > 0 ? nl : -nl);
//
//    }
    /**
            * calculate the diffusive light according to Phong's model
            * @param kd - Coefficient for diffusive
     * @param l - vector from light source
     * @param n - normal to the point
     * @param lightIntensity - Light intensity
     * @return the diffusive light
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(abs(l.dotProduct(n))); //ln=|l*n|
        return lightIntensity.scale(kd.scale(ln)); //Kd * |l * n| * Il
    }
}



