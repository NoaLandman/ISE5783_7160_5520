package renderer;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static primitives.Util.*;

/**
 * Class Camera represent a view of the geometric world through the view plane (which represent the picture),
 * Through the view plane the camera captures the geometric world.
 * it produces graphic views of the objects using the view plane and rays and object intersections.
 * The rays converge according to the location of the pixel centers in the view plane.
 * directions of the camera to the right, up and front  (compared to the original x,y,z axis),
 * all vectors orthogonal to each other
 * **/
public class Camera {

    private Point position;//camera location
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;

    private double distance;
    private double height;
    private double width;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int antiAliasingFactor = 1;
    private int maxAdaptiveLevel = 2;
    private boolean useAdaptive = false;
    private boolean useSoftSHadows=false;
    private Point p0;

    private boolean isAntiAliasingOn = false;
    private boolean isSS=false;

    private int numOfRays=300;
    private int adaptiveMaxDepth = 4;
    private boolean isMultithreading=false;
    int numOfThreads=30;

    //    /*
//     * > The function calculates the center point of the pixel in the 3D space
//     *
//     * @param nX number of pixels in the width of the image
//     * @param nY number of pixels in the vertical direction
//     * @param j  the column number of the pixel in the image
//     * @param i  the row number of the pixel in the image
//     * @return the point in the middle of the screen.
//     */
    private Point CalculatCenterPointInPixel(int nX, int nY, double j, double i) {
        // Finding the point that is distance units away from the position in the direction of the vector v1.
        Point pC = p0.add(vTo.scale(distance));



        // Calculating the width and height of each cell in the grid.
        double rY = height / nY;
        double rX = width / nX;

        // Calculating the y and x coordinate of the pixel in the image.
        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;
        // Creating a new Point object and assigning it to pIJ.
        Point pIJ = pC;
        // Checking if the value of xJ is not equal to zero.
        if (!isZero(xJ)) {
            // Adding the vector v3 to the vector pIJ.
            pIJ = pIJ.add(vRight.scale(xJ));
        }

        // Checking if the value of yI is not equal to zero.
        if (!isZero(yI)) {
            // Adding the vector v2 to the vector pIJ.
            pIJ = pIJ.add(vUp.scale(yI));
        }

        // Creating a new array of size n and filling it with the values of the array pIJ.
        return pIJ;
    }

    public Ray constructRay(int nX, int nY, double j, double i)
    {
        // Calculate the center point of the pixel in the image plane
        Point Pij=CalculatCenterPointInPixel(nX,nY,j,i);

        // Construct a ray from the origin point (p0) to the center point of the pixel
        // The direction of the ray is the normalized vector from p0 to Pij
        return new Ray(p0,Pij.subtract(p0).normalize());

    }


    /*
     * Find a ray from p0 to the center of the pixel from the given resolution.
     * @return ray from p0 the center to the center of the pixel in row and column
     * @throws MissingResourceException if the camera is not initialized
     */
    public Camera renderImage1() throws MissingResourceException {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // Check if the imageWriter and rayTracer have been initialized
        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
        if (rayTracer == null) {
            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
        }

        // Check if multithreading is enabled
        if (isMultithreading) {
            // Render the image using parallel streams
            IntStream.range(0, imageWriter.getNx()).parallel().forEach(row -> {
                IntStream.range(0, imageWriter.getNy()).parallel().forEach(column -> {
                    Color color = castRay(nX, nY, row, column);
                    imageWriter.writePixel(row, column, color);
                });
            });
        } else {
            // Render the image using a regular loop
            for (int i = 0; i < nX; i++) {
                for (int j = 0; j < nY; j++) {
                    Color color = castRay(nX, nY, j, i);
                    imageWriter.writePixel(j, i, color);
                }


            }
        }

        return this;
    }

//    /*
//     * This function implements adaptive super-sampling
//     * @param nX
//     * @param nY
//     * @param j
//     * @param i
//     * @return
//     */
    private Color adaptiveAntiAliasing(int nX, int nY, double j, double i) {
        // Calculate the center point of the pixel
        Point center = CalculatCenterPointInPixel(nX, nY, j, i);

        // Calculate the size of each square in the adaptive anti-aliasing grid
        double squareSize = this.height / nY;

        // Call the recursive function for adaptive anti-aliasing
        return adaptiveAntiAliasing_(center, squareSize, adaptiveMaxDepth);
    }

    /*
     * Recursive function for adaptive super-sampling anti-aliasing
     *
     * @param center      The center point of the current square
     * @param squareSize  The size of the current square
     * @param depth       The current recursion depth
     * @return The color computed for the current square
     */
    private Color adaptiveAntiAliasing_(Point center, double squareSize, int depth) {
        Color result = Color.BLACK;
        Point newCenter = null;
        ArrayList<Color> quartersColors = new ArrayList<Color>(); // Stores the colors of the four quarters of the square

        // Calculate the four corners of the current square
        Point p1 = center.add(vUp.scale(0.5 * squareSize)).add(vRight.scale(-0.5 * squareSize));
        Point p2 = center.add(vUp.scale(0.5 * squareSize)).add(vRight.scale(0.5 * squareSize));
        Point p3 = center.add(vUp.scale(-0.5 * squareSize)).add(vRight.scale(-0.5 * squareSize));
        Point p4 = center.add(vUp.scale(-0.5 * squareSize)).add(vRight.scale(0.5 * squareSize));

        // Calculate the colors at the four corners and center of the square
        Color p1Color = rayTracer.traceRay(new Ray(p0, p1.subtract(p0)));
        Color p2Color = rayTracer.traceRay(new Ray(p0, p2.subtract(p0)));
        Color p3Color = rayTracer.traceRay(new Ray(p0, p3.subtract(p0)));
        Color p4Color = rayTracer.traceRay(new Ray(p0, p4.subtract(p0)));
        Color centerColor = rayTracer.traceRay(new Ray(p0, center.subtract(p0)));

        // If the recursion depth is 0 or the colors at all corners and center are the same,
        // return the average color of the five points
        if (depth == 0 || p1Color.equals(p2Color) && p2Color.equals(p3Color)
                && p3Color.equals(p4Color) && p4Color.equals(centerColor)) {
            return p1Color.add(p2Color, p3Color, p4Color, centerColor).scale(0.20);
        } else {
            // Recursively compute the colors for the four sub-squares and accumulate the results
            newCenter = center.add(vRight.scale(-0.25 * squareSize)).add(vUp.scale(0.25 * squareSize));
            result = result.add(adaptiveAntiAliasing_(newCenter, 0.5 * squareSize, depth - 1));

            newCenter = center.add(vRight.scale(0.25 * squareSize)).add(vUp.scale(0.25 * squareSize));
            result = result.add(adaptiveAntiAliasing_(newCenter, 0.5 * squareSize, depth - 1));

            newCenter = center.add(vRight.scale(-0.25 * squareSize)).add(vUp.scale(-0.25 * squareSize));
            result = result.add(adaptiveAntiAliasing_(newCenter, 0.5 * squareSize, depth - 1));

            newCenter = p4.add(vRight.scale(0.25 * squareSize)).add(vUp.scale(-0.25 * squareSize));
            result = result.add(adaptiveAntiAliasing_(newCenter, 0.5 * squareSize, depth - 1));

            // Return the average color of the four sub-squares
            return result.scale(0.25);
        }
    }

    //
    private Color castRay(int nX, int nY, double j, double i) {
        // Check if adaptive super sampling is turned on
        if (useAdaptive) {
            // Call adaptiveAntiAliasing method and return the resulting color
            return adaptiveAntiAliasing(nX, nY, j, i);
        } else if (isAntiAliasingOn) {
            // Anti-aliasing is turned on

            // Construct multiple rays for anti-aliasing
            List<Ray> rays = constructRays(nX, nY, j, i);

            // Initialize color with black
            Color color = Color.BLACK;

            // Loop through each ray
            for (Ray ray : rays) {
                // Trace the ray and get the color
                Color color1 = rayTracer.traceRay(ray);

                // Add the color to the accumulated color
                color = color.add(color1);
            }

            // Reduce the accumulated color by dividing it by the number of rays
            return color.reduce(numOfRays);
        }
        else {
            // Anti-aliasing is not enabled

            // Construct a single ray
            Ray ray = constructRay(nX, nY, j, i);

            // Trace the ray and return the resulting color
            return rayTracer.traceRay(ray);
        }
    }

    //
    private List<Ray> constructRays(int nX, int nY, double j, double i) {
        List<Ray> rays=new ArrayList<>(numOfRays+1);
        Point pInPixel;

        // Calculating the width and height of each cell in the grid
        double rX = width / nX;
        double rY = height / nY;

        // Declaring a variable of type double called randX and randY.
        double randX, randY;
        // Calculating the center point of the pixel in the image.
        Point pCenterPixel = CalculatCenterPointInPixel(nX, nY, j, i);
        //adding the original ray to the rays list
        rays.add(constructRay(nX,nY,j,i));
        for (int k = 0; k < numOfRays; k++) {

            // Generating a random number between -rX/2 and rX/2.
            randX = random(-rX / 2, rX / 2);

            // Generating a random number between -rY/2 and rY/2.
            randY = random(-rY / 2, rY / 2);

            // Creating a new point that is a random distance from the center point.
            pInPixel = new Point(pCenterPixel.getX() + randX, pCenterPixel.getY() + randY, pCenterPixel.getZ());

            // Creating a ray from the camera to the pixel.
            rays.add(new Ray(p0, pInPixel.subtract(p0).normalize()));
        }
        return rays;
        //return List.of(constructRay(nX,nY,j,i));
    }

    /* Setter of builder patters
     * sets the multithreading
     * If set to 1, the render won't use the thread pool.
     * If set to 0, the thread pool will pick the number of threads.
     *
     * @param threads number of threads to use
     * @return the current render
     * @throws IllegalArgumentException when threads is less than 0
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0) // threads cannot be less than zero
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");

        isMultithreading = true; // Enable multithreading

        if (threads != 0)
            numOfThreads = threads; // Set the number of threads to the provided value

        else {
            int cores = Runtime.getRuntime().availableProcessors() - 2; // Calculate the available CPU cores, leaving 2 spare threads
            numOfThreads = cores <= 2 ? 1 : cores; // Set the number of threads to cores if available cores is greater than 2, otherwise set it to 1
        }

        return this; // Return the current camera instance
    }

    public Camera setImprovments(boolean multiThreading, boolean isAntiAliasingOn,boolean isSoftShadows) {
        this.isAntiAliasingOn=isAntiAliasingOn;
        this.isMultithreading = multiThreading; // Set the multithreading flag to the provided value
        this.useSoftSHadows=isSoftShadows;

        return this; // Return the current camera instance
    }




    /**
     * function that gets the position of the camera
     *
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * function that gets the vTo vector
     *
     * @return the vTo vector
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * function that gets the vUp vector
     *
     * @return the vUp vector
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * function that gets the vRight vector
     *
     * @return the vRight vector
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * function that gets the distance
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * function that gets the height
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * function that gets the width
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * function that constructs the camera
     *
     * @param position the position
     * @param vTo      the vTo vector
     * @param vUp      the vUp vector
     */
    public Camera(Point position, Vector vTo, Vector vUp) {
        if (vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException("vTo and vUp must be orthogonal");
        this.position = position;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    /**
     * function that sets the width and height
     *
     * @param width  of the view plane
     * @param height of the view plane
     * @return this
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * function that sets the distance
     *
     * @param distance value to set
     * @return this
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * function that sets imageWriter
     *
     * @param imageWriter object to set
     * @return camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * function that sets the rayTracer
     *
     * @param rayTracer object to set
     * @return camera itself
     */
    public Camera setRayTracerBase(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * function that sets the antiAliasingFactor
     *
     * @param antiAliasingFactor value to set
     * @return camera itself
     */
    public Camera setAntiAliasingFactor(int antiAliasingFactor) {
        this.antiAliasingFactor = antiAliasingFactor;
        return this;
    }

    /**
     * setter for UseAdaptive
     * @param useAdaptive- the number of pixels in row/col of every pixel
     * @return camera itself
     */
    public Camera setUseAdaptive(boolean useAdaptive) {
        this.useAdaptive = useAdaptive;
        return this;
    }

    /**
     * setter for maxAdaptiveLevel
     * @param maxAdaptiveLevel- The depth of the recursion
     * @return camera itself
     */
    public Camera setMaxAdaptiveLevel(int maxAdaptiveLevel) {
        this.maxAdaptiveLevel = maxAdaptiveLevel;
        return this;
    }

    /**
     * function that calculates the pixels location
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the ray
     */
    private Point findPixelLocation(int nX, int nY, int j, int i) {
        double rY = height / nY; // Calculate the height of each pixel in the image
        double rX = width / nX; // Calculate the width of each pixel in the image

        double yI = -(i - (nY - 1d) / 2) * rY; // Calculate the y-coordinate of the pixel in the image
        double jX = (j - (nX - 1d) / 2) * rX; // Calculate the x-coordinate of the pixel in the image

        Point pIJ = position.add(vTo.scale(distance)); // Calculate the initial point position

        if (yI != 0)
            pIJ = pIJ.add(vUp.scale(yI)); // Adjust the point position based on the y-coordinate

        if (jX != 0)
            pIJ = pIJ.add(vRight.scale(jX)); // Adjust the point position based on the x-coordinate

        return pIJ; // Return the final pixel location
    }


    /**
     * function that returns the ray from the camera to the point
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return new Ray(position, findPixelLocation(nX, nY, j, i).subtract(position));
    }

    /**
     * function that returns the rays from the camera to the point
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param i  the x coordinate
     * @param j  the y coordinate
     * @return the ray
     */
    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        Point centralPixel = findPixelLocation(nX, nY, j, i);
        double rY = height / nY / antiAliasingFactor;
        double rX = width / nX / antiAliasingFactor;
        double x, y;

        for (int rowNumber = 0; rowNumber < antiAliasingFactor; rowNumber++) {
            for (int colNumber = 0; colNumber < antiAliasingFactor; colNumber++) {
                y = -(rowNumber - (antiAliasingFactor - 1d) / 2) * rY;
                x = (colNumber - (antiAliasingFactor - 1d) / 2) * rX;
                Point pIJ = centralPixel;
                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
                rays.add(new Ray(position, pIJ.subtract(position)));
            }
        }
        return rays;
    }


    /**
     * function that gets the color of the pixel and renders in to image
     */
    public Camera renderImage() {
        if (position == null || vTo == null || vUp == null || vRight == null || distance == 0 || height == 0 || width == 0 || imageWriter == null || rayTracer == null)
            throw new MissingResourceException("", "", "Camera is not initialized");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i++)
            for (int j = 0; j < nY; j++)
                imageWriter.writePixel(j, i, this.castRay(nX, nY, j, i));
        return this;
    }

    /**
     * function that prints grid on top of image
     *
     * @param interval of grid
     * @param color    of grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("", "", "Camera is not initialized");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if ((i % interval == 0) || (j % interval == 0))
                    imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * function that calls write to image function
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("", "", "Camera is not initialized");
        imageWriter.writeToImage();
    }

    /**
     * function that casts ray and returns color
     *
     * @param nX the x resolution
     * @param nY the y resolution
     * @param j  the x coordinate
     * @param i  the y coordinate
     * @return the color
     */
    private Color castRay(int nX, int nY, int i, int j) {
        if (useAdaptive)
            return adaptiveHelper(findPixelLocation(nX, nY, i, j), nX, nY);
        else if (antiAliasingFactor == 1)
            return rayTracer.traceRay(constructRay(nX, nY, i, j));
        else
            return rayTracer.traceRays(constructRays(nX, nY, i, j));
    }

    /**
     * get the point and return the color of the ray to this point
     *
     * @param p- point on the view plane
     * @return color of this point
     */
    private Color calcPointColor(Point p) {
        return rayTracer.traceRay(new Ray(position, p.subtract(position)));
    }

    /**
     * calculate average color of the pixel by using adaptive Super-sampling
     *
     * @param center- the center of the pixel
     * @param nY-     number of pixels to width
     * @param nX-     number of pixels to length
     * @return- the average color of the pixel
     */
    private Color adaptiveHelper(Point center, double nY, double nX) {
        // Create a Hashtable to store point-color mappings
        Hashtable<Point, Color> pointColorTable = new Hashtable<Point, Color>();

        // Calculate half the height and width of each pixel
        double rY = height / nY / 2;
        double rX = width / nX / 2;

        // Calculate the colors of the four corners of the pixel
        Color upRight = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(rX)));
        Color upLeft = calcPointColor(center.add(vUp.scale(rY)).add(vRight.scale(-rX)));
        Color downRight = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(rX)));
        Color downLeft = calcPointColor(center.add(vUp.scale(-rY)).add(vRight.scale(-rX)));

        // Call the adaptive method with an initial depth of 1
        return adaptive(1, center, rX, rY, pointColorTable, upLeft, upRight, downLeft, downRight);
    }


    /**
     * recursive method that return the average color of the pixel- by checking the color of the four corners
     *
     * @param max-         the depth of the recursion
     * @param center-      the center of the pixel
     * @param rX-          the width of the pixel
     * @param rY-          the height of the pixel
     * @param upLeftCol-   the color of the vUp left corner
     * @param upRightCol-  the color of the vUp vRight corner
     * @param downLeftCol- the color of the down left corner
     * @param downRightCol - the color of the down vRight corner
     * @return the average color of the pixel
     */
    private Color adaptive(int max, Point center, double rX, double rY, Hashtable<Point, Color> pointColorTable,
                           Color upLeftCol, Color upRightCol, Color downLeftCol, Color downRightCol) {
        // Base case: maximum depth reached
        if (max == maxAdaptiveLevel) {
            // Return the average of the corner colors
            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);
        }

        // Check if the corner colors are equal
        if (upRightCol.equals(upLeftCol) && downRightCol.equals(downLeftCol) && downLeftCol.equals(upLeftCol))
            return upRightCol;
        else {
            // Retrieve colors from the pointColorTable if available, or calculate them
            Color rightPCol = getPointColorFromTable(center.add(vRight.scale(rX)), pointColorTable);
            Color leftPCol = getPointColorFromTable(center.add(vRight.scale(-rX)), pointColorTable);
            Color upPCol = getPointColorFromTable(center.add(vUp.scale(rY)), pointColorTable);
            Color downPCol = getPointColorFromTable(center.add(vUp.scale(-rY)), pointColorTable);
            Color centerCol = calcPointColor(center);

            // Reduce the size of the pixel
            rX = rX / 2;
            rY = rY / 2;

            // Recursive calls to compute the average colors of sub-pixels
            upLeftCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
                    upLeftCol, upPCol, leftPCol, centerCol);
            upRightCol = adaptive(max + 1, center.add(vUp.scale(rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
                    upPCol, upRightCol, centerCol, leftPCol);
            downLeftCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(-rX / 2)), rX, rY, pointColorTable,
                    leftPCol, centerCol, downLeftCol, downPCol);
            downRightCol = adaptive(max + 1, center.add(vUp.scale(-rY / 2)).add(vRight.scale(rX / 2)), rX, rY, pointColorTable,
                    centerCol, rightPCol, downPCol, downRightCol);

            // Return the average color of the pixel
            return downRightCol.add(upLeftCol).add(upRightCol).add(downLeftCol).reduce(4);}

    }


    /**
     * check if this point exist in the HashTable return his color otherwise calculate the color and save it
     *
     * @param point-           certain point in the pixel
     * @param pointColorTable- dictionary that save points and their color
     * @return the color of the point
     */
    private Color getPointColorFromTable(Point point, Hashtable<Point, Color> pointColorTable) {
        // Check if the point is already present in the table
        if (!(pointColorTable.containsKey(point))) {
            // If not present, calculate the color for the point
            Color color = calcPointColor(point);
            // Store the color in the table for future use
            pointColorTable.put(point, color);
            // Return the calculated color
            return color;
        }
        // If the point is already present in the table, return the stored color
        return pointColorTable.get(point);
    }

}


