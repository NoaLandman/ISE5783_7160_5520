package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source.
 * <p>
 * A point light emits light in all directions from a specific position in space.
 * <p>
 * The intensity of the light decreases with distance from the light source.
 */
public class PointLight extends Light implements LightSource {

    private double kL = 0d;
    private double kC = 1d;
    private double kQ = 0d;
    private Point position;

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The intensity (color and brightness) of the light.
     * @param position  The position of the light source.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the linear attenuation factor of the point light.
     *
     * @param kL The linear attenuation factor.
     * @return This PointLight object for method chaining.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the constant attenuation factor of the point light.
     *
     * @param kC The constant attenuation factor.
     * @return This PointLight object for method chaining.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the point light.
     *
     * @param kQ The quadratic attenuation factor.
     * @return This PointLight object for method chaining.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Returns the intensity of the point light at the given point.
     * The intensity is calculated based on the distance between the point and the light source,
     * using the attenuation factors (kL, kC, kQ) to decrease the intensity with distance.
     *
     * @param p The point at which to calculate the intensity.
     * @return The intensity of the light.
     */
    @Override
    public Color getIntensity(Point p) {
        double distance = p.distance(position);
        Color iC = getIntensity();
        double factor = kC + (kL * distance) + (kQ * distance * distance);
        return iC.reduce(factor);
    }

    /**
     * Returns the direction from the light source to the given point.
     *
     * @param p The point for which to calculate the direction.
     * @return The direction vector.
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }


    /**
     * Returns the distance from the light source to the given point.
     *
     * @param point The point for which to calculate the distance.
     * @return The distance to the point.
     */
    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }
}
