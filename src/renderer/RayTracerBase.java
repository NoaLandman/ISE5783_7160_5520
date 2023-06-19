package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * The RayTracerBase class is an abstract base class for ray tracing algorithms.
 * <p>
 * It provides a method for tracing rays and requires the implementation of the traceRay method.
 **/
public abstract class RayTracerBase {
    /*

The scene to be rendered.
*/
    protected Scene scene;

    /**
     * Constructs a RayTracerBase object with the specified scene.
     *
     * @param scene The scene to be rendered.
     */

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color of the intersection point with the scene.
     * This method must be implemented by concrete subclasses.
     *
     * @param ray The ray to trace.
     * @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);
    public abstract Color traceRays(List<Ray> rays);
    public abstract Color adaptiveTraceRays(List<Ray> rays);
    }