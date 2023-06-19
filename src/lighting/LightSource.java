package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * The LightSource interface represents a light source in a scene.
 * <p>
 * It provides methods to calculate the intensity of the light at a given point,
 * <p>
 * and the direction from the light source to the given point.
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at the given point.
     *
     * @param p The point at which to calculate the intensity.
     * @return The intensity of the light.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction from the light source to the given point.
     *
     * @param p The point for which to calculate the direction.
     * @return The direction vector.
     */
    public Vector getL(Point p);

    /**
     * Returns the distance from the light source to the given point.
     *
     * @param point The point for which to calculate the distance.
     * @return The distance to the point.
     */
    public double getDistance(Point point);

    public List<Vector> getLCircle(Point p, double r, int amount);


    }
