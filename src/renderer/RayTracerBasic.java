package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Extends the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {
    // Recursion level factors
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Declares whether the ray tracer should create soft shadow rays
     */
    private boolean isSoftShadow = false;
    /**
     * Declares how many soft shadow rays per point to create, if applicable
     */
    private int numOfSSRays = 10;
    /**
     * The radius of the beam for rays of soft shadow
     */
    private double radiusBeamSS = 10;



    /**
     * constructor that called the constructor of RayTracerBase
     *
     * @param scene ,the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }




    /**
     * Sets the isSoftShadow used
     *
     * @param flag true or false
     * @return RayTracerBase Object
     */
    public RayTracerBasic useSoftShadow(boolean flag) {
        this.isSoftShadow = flag;
        return this;
    }

    /**
     * Sets the number of soft shadow rays
     *
     * @param num number of rays
     * @return RayTracerBase Object
     */
    public RayTracerBasic setNumOfSSRays(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number of rays must be greater than 0");
        }

        this.numOfSSRays = num;
        return this;
    }

    /**
     * Sets the radius for the beam
     *
     * @param r the radius
     * @return RayTracerBase Object
     */
    public RayTracerBasic setRadiusBeamSS(double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("Radius of beam must be greater than 0");
        }

        this.radiusBeamSS = r;
        return this;
    }

    /**
     * Returns the color of the closest point which the ray hits
     *
     * @param ray the ray to check
     * @return The color of the point
     */
    @Override
    public Color traceRay(Ray ray) {
        //get the closest intersection of the ray with the scene
        GeoPoint intersection = findClosestIntersection(ray);

        //if no intersections were found return the background color of the scene
        if (intersection == null)
            return scene.background;

        //return the color at that point
        return calcColor(intersection, ray);
    }

    /**
     * Returns the average of colors of all the points the rays hit
     *
     * @param rays rays to check the color
     * @return The average color
     */
    public Color traceRays(List<Ray> rays) {
        Color avgColor = Color.BLACK;

        //for each ray in the list
        for (Ray ray : rays) {
            //return the color at that point
            avgColor = avgColor.add(traceRay(ray));
        }

        //return the color at that point
        return avgColor.reduce(rays.size()); //reduce by number of rays, in order to get average color
    }

    /**
     * Returns the color of the point the ray hits using adaptive super sampling
     *
     * @param rays rays to check the color
     * @return the color
     */
    public Color adaptiveTraceRays(List<Ray> rays) {
        int numOfSampleRays = (int)sqrt(rays.size());
        int ray1Index = (numOfSampleRays - 1) * numOfSampleRays + (numOfSampleRays - 1); //the index of the up and right ray
        int ray2Index = (numOfSampleRays - 1) * numOfSampleRays;  //the index of the up and left ray
        int ray3Index = 0;  //the index of the button and left ray
        int ray4Index = (numOfSampleRays - 1);  // the index of the button and right ray

        Color color = adaptiveSuperSampling(rays, 3, ray1Index, ray2Index, ray3Index, ray4Index, numOfSampleRays); //calculate the color for the pixel
        return color;
    }

    /**
     * Helper method for adaptive super sampling
     * recursively checks if the corners around a center point are the same and if yes doesn't check the rest of the rays and just pick the color if the center
     * @param rays all the rays that were shot
     * @param level_of_adaptive declares how many levels of adaptiveness to go
     * @param ray1Index the index of top right corner ray
     * @param ray2Index the index of top left corner ray
     * @param ray3Index the index of bottom right corner ray
     * @param ray4Index the index of bottom left corner ray
     * @param numOfSampleRays number of rays to sample
     * @return the color
     */
    private Color adaptiveSuperSampling(List<Ray> rays, int level_of_adaptive, int ray1Index, int ray2Index, int ray3Index, int ray4Index, int numOfSampleRays) {
        int numOfAdaptiveRays = 5;

        Ray centerRay = rays.get(rays.size() - 1); //get the center screen ray
        Color centerColor = traceRay(centerRay); //get the color of the center
        Ray ray1 = rays.get(ray1Index);  //get the up and right screen ray
        Color color1 = traceRay(ray1); //get the color of the up and right
        Ray ray2 = rays.get(ray2Index); //get the up and left ray
        Color color2 = traceRay(ray2);//get the color of the up and left
        Ray ray3 = rays.get(ray3Index);//get the bottom and left ray
        Color color3 = traceRay(ray3);//get the color of the bottom and left
        Ray ray4 = rays.get(ray4Index);//get the bottom and right ray
        Color color4 = traceRay(ray4);//get the color of the bottom and right

        if (level_of_adaptive == 0) {
            //Calculate the average color of the corners and the center
            centerColor = centerColor.add(color1, color2, color3, color4);
            return centerColor.reduce(numOfAdaptiveRays);
        }

        //If the corner color is the same as the center color, returns the center color
        if (color1.equals(centerColor) && color2.equals(centerColor) && color3.equals(centerColor) && color4.equals(centerColor)) {
            return centerColor;
        }

        //Otherwise, for each color that is different from the center, the recursion goes down to the depth of the pixel and sums up
        // the colors until it gets the same color as the center color,
        else {
            if (!color1.equals(centerColor)) {
                color1 = color1.add(adaptiveSuperSampling(rays, level_of_adaptive - 1, ray1Index - (numOfSampleRays + 1), ray2Index, ray3Index, ray4Index, numOfSampleRays));
                color1 = color1.reduce(2);
            }
            if (!color2.equals(centerColor)) {
                ;
                color2 = color2.add(adaptiveSuperSampling(rays, level_of_adaptive - 1, ray1Index, ray2Index - (numOfSampleRays - 1), ray3Index, ray4Index, numOfSampleRays));
                color2 = color2.reduce(2);
            }
            if (!color3.equals(centerColor)) {
                color3 = color3.add(adaptiveSuperSampling(rays, level_of_adaptive - 1, ray1Index, ray2Index, ray3Index + (numOfSampleRays + 1), ray4Index, numOfSampleRays));
                color3 = color3.reduce(2);
            }
            if (!color4.equals(centerColor)) {
                color4 = color4.add(adaptiveSuperSampling(rays, level_of_adaptive - 1, ray1Index, ray2Index, ray3Index, ray4Index + (numOfSampleRays - 1), numOfSampleRays));
                color4 = color4.reduce(2);
            }
            //Calculate and return the average color
            centerColor = centerColor.add(color1, color2, color3, color4);

            return centerColor.reduce(numOfAdaptiveRays);
        }
    }

    /**
     * Returns the color at a certain point
     *
     * @param gp  the point with the geometry
     * @param ray the ray from the viewer
     * @return Color of the point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        // Calculating the color at a point according to Phong Reflection Model
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).
                add(scene.ambientLight.getIntensity());
    }

    /**
     * recursive function to calculate the color at a certain point
     *
     * @param intersection the point
     * @param ray          the ray from the viewer
     * @param level        level of recursion
     * @param k            the kR or kT factor at this point
     * @return Color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        // Calculating the color at a point according to Phong Reflection Model

        //calculated light contribution from all light sources
        Color color = calcLocalEffect(intersection, ray.getDir(), k);

        if (level == 1) {
            return color; //end recursion
        }

        //+calculated light contribution from global effect (kR*ir+kT*it recursively)
        return color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }

    /**
     * Calculate the local effect of light sources on a point
     *
     * @param intersection the point
     * @param v            the direction of the ray from the viewer
     * @param k            the kR or kT factor at this point
     * @return the color
     */
    private Color calcLocalEffect(GeoPoint intersection, Vector v, Double3 k) {
        Vector n = intersection.geometry.getNormal(intersection.point);

        double nv = alignZero(n.dotProduct(v)); //nv=n*v
        if (isZero(nv)) {
            return Color.BLACK;
        }

        int nShininess = intersection.geometry.getMaterial().nshininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;

        Color color = intersection.geometry.getEmission(); //base color

        //for each light source in the scene
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point); //the direction from the light source to the point
            double nl = alignZero(n.dotProduct(l)); //nl=n*l

            //if sign(nl) == sign(nv) (if the light hits the point add it, otherwise don't add this light)
            if (nl * nv > 0) {
                //ktr is the level of shade on the point (according to transparency of material)
                Double3 ktr;
                if (!isSoftShadow) { //if soft shadow is not activated, get the regular transparency
                    ktr = transparency(intersection, lightSource, l, n);
                } else { //otherwise get the transparency level according to soft shadow
                    ktr = transparencySS(intersection, lightSource, n);
                }
                if (!(ktr.product(k)).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * Calculate the diffuse light effect on the point
     *
     * @param kd             diffuse attenuation factor
     * @param l              the direction of the light
     * @param n              normal from the point
     * @param lightIntensity the intensity of the light source at this point
     * @return the color
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = alignZero(abs(l.dotProduct(n))); //ln=|l*n|
        return lightIntensity.scale(kd.scale(ln)); //Kd * |l * n| * Il
    }

    /**
     * Calculate the specular light at this point
     *
     * @param ks             specular attenuation factor
     * @param l              the direction of the light
     * @param n              normal from the point
     * @param v              direction of the viewer
     * @param nShininess     shininess factor of the material at the point
     * @param lightIntensity the intensity of the light source at the point
     * @return the color of the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double ln = alignZero(l.dotProduct(n)); //ln=l*n
        Vector r = l.subtract(n.scale(2 * ln)).normalize(); //r=l-2*(l*n)*n
        double vr = alignZero(v.dotProduct(r)); //vr=v*r
        double vrnsh = pow(max(0, -vr), nShininess); //vrnsh=max(0,-vr)^nshininess
        return lightIntensity.scale(ks.scale(vrnsh)); //Ks * (max(0, - v * r) ^ Nsh) * Il
    }

    /**
     * Returns wether a certain point is shaded by other objects
     *
     * @param gp the point
     * @param l  the direction of the light
     * @param n  normal to the point
     * @param ls the loght source
     * @return true if the point is shaded
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls, double nv) {
        Vector lightDirection = l.scale(-1); //vector from the point to the light source

        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point p = gp.point.add(deltaVector);
        Ray lightRay = new Ray(p, lightDirection);

        double lightDistance = ls.getDistance(gp.point);
        //finding only points that are closer to the point than the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightDistance);

        //if there are no intersections return true (there is no shadow)
        if (intersections == null) {
            return true;
        }

        //for each intersection
        for (GeoPoint intersection : intersections) {
            //if the material is not transparent return false
            if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
                return false;
            }

        }
        return true;
    }

    /**
     * calculates the transparency level at a certain point
     *
     * @param gp the point
     * @param ls the light source
     * @param l  direction of the light
     * @param n  normal to the point
     * @return transparency level
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
        Vector lightDir = l.scale(-1);// from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDir) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, n, lightDir);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        Double3 ktr = new Double3(1);
        if (intersections != null) {
            for (GeoPoint gp2 : intersections) {
                if (point.distance(gp2.point) < ls.getDistance(point)) {
                    ktr=gp2.geometry.getMaterial().kT.product(ktr);
                }
            }
        }
        return ktr;
    }

    /**
     * Returns transparency level with soft shadow effect
     * Constructs random rays around the light and gets the average of all the levels
     *
     * @param gp the point to check
     * @param ls the current light source
     * @param n  normal to the point
     * @return average ktr
     */
    private Double3 transparencySS(GeoPoint gp, LightSource ls, Vector n) {
        Double3 ktr = Double3.ZERO;
        List<Vector> vecs = ls.getLCircle(gp.point, radiusBeamSS, numOfSSRays);

        for (Vector v : vecs) { //for each vector, add the transparency level there
            ktr = ktr.add(transparency(gp, ls, v, n));
        }

        ktr = ktr.reduce(vecs.size()); //get the average of all the transparency levels of all the vectors

        return ktr;
    }

    /**
     * recursive function calculates the global effects of objects on a certain point
     *
     * @param intersection the point
     * @param inRay        direction of ray from the camera to point
     * @param level        level of recursion
     * @param k            the level of light
     * @return the color
     */
    private Color calcGlobalEffects(GeoPoint intersection, Vector inRay, int level, Double3 k) {
        Color color = Color.BLACK; //base color
        Vector n = intersection.geometry.getNormal(intersection.point); //normal

        Material material = intersection.geometry.getMaterial();

        //reflection attenuation of the material
        Double3 kr = material.kR;
        //reflection level as affected by k
        Double3 kkr = kr.product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) { //if the reflection level is not lower than the minimum
            //construct a reflection  ray from the point
                Ray reflectedRay = constructReflectedRay(n, inRay, intersection.point);

                //add this color to the point by recursively calling calcGlobalEffect
                color = color.add(calcGlobalEffect(reflectedRay, level, kr, kkr));

        }




        //transparency  attenuation factor of the material
        Double3 kt = material.kT;
        //transparency level
        Double3 kkt = kt.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {//if the transparency level is not lower than the minimum
                Ray refractedRay = constructRefractedRay(n, inRay, intersection.point);

                //add to the color to the point by recursively calling calcGlobalEffect
                color = color.add(calcGlobalEffect(refractedRay, level, kt, kkt));
            }
        return color;
    }

    /**
     * calculates global effects recursively
     *
     * @param ray   the ray from the viewer
     * @param level level of recursion
     * @param kx    k attenuation
     * @param kkx   k times attenuation
     * @return the color
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);

        if (gp == null) {
            return scene.background;
        }

        return calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Constructs a reflection ray from an intersection
     *
     * @param n normal to the point
     * @param v direction of the ray to the point
     * @param p the [point
     * @return new Ray
     */
    private Ray constructReflectedRay(Vector n, Vector v, Point p) {
        double vn = alignZero(v.dotProduct(n)); //v*n

        if (isZero(vn)) {
            return null;
        }

        Vector vnn = n.scale(2 * vn);// n*2*vn
        Ray r = new Ray(p, v.subtract(vnn), n); //new Ray{point,v-2*(v*n)*n}
        return r;
    }



    /**
     * Construct a refractive ray from a point
     *
     * @param n normal to the point
     * @param v direction of ray to the point
     * @param p point
     * @return new Ray
     */
    private Ray constructRefractedRay(Vector n, Vector v, Point p) {
        return new Ray(p, v, n);
    }


    /**
     * Finds the closest intersection in the scene to a given ray
     *
     * @param ray the ray
     * @return the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        //get the intersections of the ray with the scene
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }

        //find the closest GeoPoint among the intersections
        GeoPoint result = ray.findClosestGeoPoint(intersections);

        return result;
    }
}
