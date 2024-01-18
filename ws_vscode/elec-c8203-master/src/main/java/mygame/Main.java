package mygame;

import java.util.ArrayList;

import com.jme3.app.SimpleApplication;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;


public class Main extends SimpleApplication {

    public static float floorHeight = -15;
    static RobotArm arm;
    static AssemblyStation ast;
    static LegoBuffer lb;

    static boolean freeze = false;
    static boolean moving = false;
    static boolean goingToLego = false;
    static Lego lego;
    static int slotIndex = 0;
    static int colorIndex = 0;
    static final int numColors = 4;
    static ArrayList<String> colors = new ArrayList<>(numColors);

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        colors.add("yellow");
        colors.add("blue");
        colors.add("pink");
        colors.add("green");

        flyCam.setMoveSpeed(10);
        /*Lego yellowLego = new Lego(assetManager, "yellow");
        Lego blueLego = new Lego(assetManager, "blue");
        Lego pinkLego = new Lego(assetManager, "pink");
        Lego greenLego = new Lego(assetManager, "green");
        Lego redLego = new Lego(assetManager, "red");
        rootNode.attachChild(pinkLego.node);
        rootNode.attachChild(yellowLego.node);
        rootNode.attachChild(blueLego.node);
        rootNode.attachChild(greenLego.node);
        rootNode.attachChild(redLego.node);

        yellowLego.node.setLocalTranslation(2f, 0, 0);
        pinkLego.node.setLocalTranslation(4f, 0, 0);
        greenLego.node.setLocalTranslation(-2f, 0, 0);
        redLego.node.setLocalTranslation(-4f, 0, 0);
*/
        ast = new AssemblyStation(assetManager, rootNode, 5, -11);
        arm = new RobotArm(assetManager);
        rootNode.attachChild(arm.node);

        lb = new LegoBuffer(assetManager, rootNode, 5, -29, 10, 6);

        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(400f);
        lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
        rootNode.addLight(lamp_light);

        PointLight light2 = new PointLight();
        light2.setColor(ColorRGBA.White);
        light2.setRadius(600f);
        light2.setPosition(new Vector3f(10f, 8f, -35f));
        rootNode.addLight(light2);

      //  ast.initTestMove(new Vector3f(0, 0, -5));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (!freeze && moving) {
            moving = ast.move();
            //System.out.println(moving);
        }

        if (!moving && !freeze) {
            if (goingToLego) {
                Vector3f v = ast.slotPosition(slotIndex);
                slotIndex++;
                ast.initMoveToStation(lego, v);

                goingToLego = false;
                moving = true;
            } else {
                if (lego != null) {
                    Vector3f loc = lego.node.getWorldTranslation();
                    arm.node.detachChild(lego.node);
                    lego.node.setLocalTranslation(loc);
                    rootNode.attachChild(lego.node);
                }

                lego = lb.giveLego(colors.get(colorIndex));
                moving = true;
                if (lego == null) {
                    colorIndex++;
                    if (colorIndex >= numColors) {
                        freeze = true;
                    } else {
                        lego = lb.giveLego(colors.get(colorIndex));
                    }
                }
                if (!freeze) {
                    ast.initMoveToLego(lego);
                }
                goingToLego = true;
            }
        } 
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // TODO: add render code
    }
}