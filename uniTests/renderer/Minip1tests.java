package renderer;

import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import renderer.ImageWriter;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class Minip1tests {
    private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
    private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
    private Point spPL2 = new Point(50, 50, 25); // Sphere test Position of Light


    private Camera camera1 = new Camera(new Point(-100, 100, 80), new Vector(5, -5, -4), new Vector(4, -4, 10)) //
            .setVPSize(150, 150) //
            .setVPDistance(140);

    private Geometry sphere1 = new Sphere(6d,new Point(-30, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere2 = new Sphere(6d,new Point(-20, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere3 = new Sphere(6d,new Point(-10, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere4 = new Sphere(6d,new Point(0, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere5 = new Sphere(6d,new Point(10, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere6 = new Sphere(6d,new Point(20, 30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere7 = new Sphere(6d,new Point(-30, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere8 = new Sphere(6d,new Point(-20, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere9 = new Sphere(6d,new Point(-10, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere10 = new Sphere(6d,new Point(0, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere11 = new Sphere(6d,new Point(10, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere12 = new Sphere(6d,new Point(20, 20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere13 = new Sphere(6d,new Point(30, 20, 300)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere14 = new Sphere(6d,new Point(-30, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere15 = new Sphere(6d,new Point(-20, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere16 = new Sphere(6d,new Point(-10, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere17 = new Sphere(6d,new Point(0, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere18 = new Sphere(6d,new Point(10, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere19 = new Sphere(6d,new Point(20, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere20 = new Sphere(6d,new Point(30, 10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere21 = new Sphere(6d,new Point(-30, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere22 = new Sphere(6d,new Point(-20, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere23 = new Sphere(6d,new Point(-10, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere24 = new Sphere(6d,new Point(0, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));


    private Geometry sphere29 = new Sphere(6d,new Point(10, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    private Geometry sphere30 = new Sphere(6d,new Point(20, 0, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));


    private Geometry sphere25 = new Sphere(6d,new Point(-30, -10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere26 = new Sphere(6d,new Point(-20, -10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere27 = new Sphere(6d,new Point(-10, -10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere28 = new Sphere(6d,new Point(0, -10, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    private Geometry sphere31 = new Sphere(6d,new Point(-30, -20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere32 = new Sphere(6d,new Point(-20, -20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere33 = new Sphere(6d,new Point(-10, -20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere34 = new Sphere(6d,new Point(0, -20, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    private Geometry sphere35 = new Sphere(6d,new Point(-20, -30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere36 = new Sphere(6d,new Point(-10, -30, 0)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere37 = new Sphere(6d,new Point(40, -10, 26)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry sphere38 = new Sphere(6d,new Point(10, -40, 26)) //
            .setEmission(new Color(RED).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));


    private Geometry cylinder1 = new Cylinder(4,new Ray(new Point(40, -10, 0), new Vector(0, 0, 1)), 20)
            .setEmission(new Color(PINK).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry cylinder2 = new Cylinder(4,new Ray(new Point(10, -40, 0), new Vector(0, 0, 1)), 20)
            .setEmission(new Color(PINK).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

    /**
     */
    @Test
    public void MP1Test() {
        Vector trDL1 = new Vector(-13,-7,-10);

        Point floor1 = new Point(-100,100,-10);
        Point floor2 = new Point(-100,-100 ,-10);
        Point floor3 = new Point(100,-100,-10);
        Point floor4 = new Point(100,100,-10);


        Point up2 = new Point(-70,-50 ,80);
        Point up3 = new Point(50,-50,80);
        Point up4 = new Point(50,70,80);
        Geometry floor = new Polygon(floor1 ,floor2 ,floor3 ,floor4)
                .setEmission(new Color(PINK).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.9).setkR(0.3).setShininess(300));
        Geometry right = new Polygon(floor3, floor2, up2, up3)
                .setEmission(new Color(RED).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
        Geometry left = new Polygon(floor4, floor3, up3, up4)
                .setEmission(new Color(255,255,102).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
        Geometry triangle = new Triangle(new Point(10,-40,32), new Point(40,-10,32), new Point(28,-30,55))
                .setEmission(new Color(PINK).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.9).setkT(1).setShininess(300));


        Scene scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(PINK), new Double3(0.15)))
                .setGeometries(new Geometries(floor,left,right,triangle
                        ,sphere1,sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8,
                        sphere9,sphere10,sphere11, sphere12, sphere13, sphere14, sphere15,sphere16, sphere17, sphere18,
                        sphere19,sphere20,sphere21,sphere22,sphere23,sphere24,sphere25,sphere26,sphere27,sphere28,sphere29,sphere30,sphere31,
                        sphere32,sphere33,sphere34,sphere35,sphere36,cylinder1,cylinder2,sphere37,sphere38))
                .setLights(List.of(
                        (new DirectionalLight(new Color(200,50,80), trDL1)),
                        (new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002)),
                        (new PointLight(spCL, spPL2).setKl(0.001).setKq(0.0002)))).build();

        ImageWriter imageWriter = new ImageWriter("Test2Antialising", 1000, 1000);
        camera1.setImageWriter(imageWriter)
                .setUseAdaptive(true)
                .setImprovments(true,false,true)
                .setMultithreading(4)
                .setRayTracerBase(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
//    @Test
//    public void MP1TestSoftShadow() {
//        Vector trDL1 = new Vector(-13,-7,-10);
//
//        Point floor1 = new Point(-100,100,-10);
//        Point floor2 = new Point(-100,-100 ,-10);
//        Point floor3 = new Point(100,-100,-10);
//        Point floor4 = new Point(100,100,-10);
//
//
//        Point up2 = new Point(-70,-50 ,80);
//        Point up3 = new Point(50,-50,80);
//        Point up4 = new Point(50,70,80);
//        Geometry floor = new Polygon(floor1 ,floor2 ,floor3 ,floor4)
//                .setEmission(new Color(PINK).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.9).setKr(0.3).setnShininess(300));
//        Geometry right = new Polygon(floor3, floor2, up2, up3)
//                .setEmission(new Color(RED).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));
//        Geometry left = new Polygon(floor4, floor3, up3, up4)
//                .setEmission(new Color(255,255,102).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));
//        Geometry triangle = new Triangle(new Point(10,-40,32), new Point(40,-10,32), new Point(28,-30,55))
//                .setEmission(new Color(PINK).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.9).setKt(1).setnShininess(300));
//
//
//        scene.getGeometries().add(floor,left,right,triangle);
//        scene.getGeometries().add(sphere1,sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8,
//                sphere9,sphere10,sphere11, sphere12, sphere13, sphere14, sphere15,sphere16, sphere17, sphere18,
//                sphere19,sphere20,sphere21,sphere22,sphere23,sphere24,sphere25,sphere26,sphere27,sphere28,sphere29,sphere30,sphere31,
//                sphere32,sphere33,sphere34,sphere35,sphere36,cylinder1,cylinder2,sphere37,sphere38);
//        scene.getLights().add(new DirectionalLight(new Color(200,50,80), trDL1));
//        scene.getLights().add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));
//        scene.getLights().add(new PointLight(spCL, spPL2).setKl(0.001).setKq(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("Soft Shadow", 1000, 1000);
//        camera1.setImageWriter(imageWriter)
//                .setRayTracer(new RayTracerBasic(scene))
//                .setSoftShadow(true)
//                .setMultithreading(4)
//                .renderImage()
//                .writeToImage();
//    }
//    @Test
//    public void MP1TestSuperSampling() {
//        Vector trDL1 = new Vector(-13,-7,-10);
//
//        Point floor1 = new Point(-100,100,-10);
//        Point floor2 = new Point(-100,-100 ,-10);
//        Point floor3 = new Point(100,-100,-10);
//        Point floor4 = new Point(100,100,-10);
//
//
//        Point up2 = new Point(-70,-50 ,80);
//        Point up3 = new Point(50,-50,80);
//        Point up4 = new Point(50,70,80);
//        Geometry floor = new Polygon(floor1 ,floor2 ,floor3 ,floor4)
//                .setEmission(new Color(PINK).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.9).setKr(0.3).setnShininess(300));
//        Geometry right = new Polygon(floor3, floor2, up2, up3)
//                .setEmission(new Color(RED).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));
//        Geometry left = new Polygon(floor4, floor3, up3, up4)
//                .setEmission(new Color(255,255,102).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));
//        Geometry triangle = new Triangle(new Point(10,-40,32), new Point(40,-10,32), new Point(28,-30,55))
//                .setEmission(new Color(PINK).reduce(2)) //
//                .setMaterial(new Material().setKd(0.5).setKs(0.9).setKt(1).setnShininess(300));
//
//
//        scene.getGeometries().add(floor,left,right,triangle);
//        scene.getGeometries().add(sphere1,sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8,
//                sphere9,sphere10,sphere11, sphere12, sphere13, sphere14, sphere15,sphere16, sphere17, sphere18,
//                sphere19,sphere20,sphere21,sphere22,sphere23,sphere24,sphere25,sphere26,sphere27,sphere28,sphere29,sphere30,sphere31,
//                sphere32,sphere33,sphere34,sphere35,sphere36,cylinder1,cylinder2,sphere37,sphere38);
//        scene.getLights().add(new DirectionalLight(new Color(200,50,80), trDL1));
//        scene.getLights().add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));
//        scene.getLights().add(new PointLight(spCL, spPL2).setKl(0.001).setKq(0.0002));
//
//        ImageWriter imageWriter = new ImageWriter("Super Sampling", 1000, 1000);
//        camera1.setImageWriter(imageWriter)
//                .setRayTracer(new RayTracerBasic(scene))
//                .setMultithreading(4)
//                .setSuperSampling(true)
//                .renderImage()
//                .writeToImage();
//    }
}