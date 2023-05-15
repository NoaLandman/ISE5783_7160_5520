package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**

 The RayTracerBase class is an abstract base class for ray tracing algorithms.

 It provides a method for tracing rays and requires the implementation of the traceRay method.
 **/
public abstract class RayTracerBase {
    /*

The scene to be rendered.
*/
    protected Scene scene;

    /**

     Traces a ray and returns the color of the intersection point with the scene.
     This method must be implemented by concrete subclasses.
     @param ray The ray to trace.
     @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);

    /**

     Constructs a RayTracerBase object with the specified scene.
     @param scene The scene to be rendered.
     */

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

}
