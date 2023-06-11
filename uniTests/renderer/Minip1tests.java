package renderer;

import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import renderer.ImageWriter;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class Minip1tests {
    @Test
    void MiniProject_1() {
        Scene scene = new Scene.SceneBuilder("TestScene").build();
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000) //
                .setRayTracerBase(new RayTracerBasic(scene));


        Intersectable sphere = new Sphere(60d,new Point(0, 0, -200)) //
                .setEmission(new Color(BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Geometry triangle = new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4))
                .setEmission(new Color(BLUE)).setMaterial(trMaterial);


        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new PointLight(new Color(GREEN), new Point(-70, -125, 200)).setKl(1E-5).setKq(1.5E-7));  //, new Vector(1, 1, -3)) //

        camera.setImageWriter(new ImageWriter("softShadowTest", 500, 500)) //
                .renderImage() //
                .writeToImage();
    }
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
