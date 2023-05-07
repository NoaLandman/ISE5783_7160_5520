/**

 The Camera class represents a camera in a 3D scene.
 It provides methods for constructing rays through pixels on a virtual image plane.
 The camera is defined by its position, its "up" and "to" vectors, and the dimensions and distance of the virtual image plane.
 */
package renderer;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    /**
     * The position of the camera in the scene.
     */
    private Point p0;

    /**
     * The "up" vector of the camera.
     */
    private Vector vUp;

    /**
     * The "to" vector of the camera.
     */
    private Vector vTo;

    /**
     * The "right" vector of the camera, calculated from the "up" and "to" vectors.
     */
    private Vector vRight;

    /**
     * The height of the virtual image plane.
     */
    private double height;

    /**
     * The width of the virtual image plane.
     */
    private double width;

    /**
     * The distance between the camera and the virtual image plane.
     */
    private double distance;

    /**
     * Construct a new Camera object with the specified position, "up" vector and "to" vector.
     * The "right" vector is calculated automatically.
     *
     * @param p0 The position of the camera.
     * @param vUp The "up" vector of the camera.
     * @param vTo The "to" vector of the camera.
     * @throws IllegalArgumentException If the "up" and "to" vectors are not vertical to each other.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("Vectors are not vertical");
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight=vTo.crossProduct(vUp).normalize();
    }

    /**
     * Set the dimensions of the virtual image plane.
     *
     * @param width The width of the virtual image plane.
     * @param height The height of the virtual image plane.
     * @return This Camera object, to enable chaining of methods.
     */
    public Camera setVPSize(double width, double height) {
        this.width=width;
        this.height=height;
        return this;
    }

    /**
     * Set the distance between the camera and the virtual image plane.
     *
     * @param distance The distance between the camera and the virtual image plane.
     * @return This Camera object, to enable chaining of methods.
     */
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }

    /**
     * Construct a Ray that passes through the specified pixel on the virtual image plane.
     *
     * @param nX The number of pixels in the horizontal direction.
     * @param nY The number of pixels in the vertical direction.
     * @param j The horizontal index of the pixel.
     * @param i The vertical index of the pixel.
     * @return A Ray that passes through the specified pixel on the virtual image plane.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        //view plane center Point
        Point Pc = p0.add(vTo.scale(distance));

        //pixels ratios
        double Rx = width / nX;
        double Ry = height / nY;

        //Pij point[i,j] in view-plane coordinates
        Point Pij = Pc;

        //delta values for moving on the view=plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));

        }
        // vector from camera's eye in the direction of point(i,j) in the viewplane
        Vector Vij = Pij.subtract(p0).normalize();

        return new Ray(p0, Vij);
    }
}
