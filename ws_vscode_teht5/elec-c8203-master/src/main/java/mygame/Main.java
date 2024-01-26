package mygame;

import java.util.ArrayList;
import java.util.Collections;

import com.jme3.app.SimpleApplication;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;


public class Main extends SimpleApplication {

    private final boolean STACK = true;

    public static float floorHeight = -15;
    static RobotArm arm;
    static RobotArm arm2;
    static AssemblyStation ast;
    static LegoBuffer lb;
    static LegoBuffer lb2;

    static boolean freeze = false;
    static boolean moving = false;
    static boolean goingToLego = false;
    static boolean arm1Ready = false;
    static Lego lego;
    static int slotIndex = 0;
    static int colorIndex = 0;
    static int stacked = 0;
    static final int numColors = 4;
    static ArrayList<String> colors = new ArrayList<>(numColors);
    static ArrayList<Lego> legos = new ArrayList<>();

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
        arm = new RobotArm(assetManager, ColorRGBA.Orange);
        arm2 = new RobotArm(assetManager, ColorRGBA.Pink);
        rootNode.attachChild(arm.node);
        rootNode.attachChild(arm2.node);

        arm2.mastGeom.setLocalTranslation(18, 0, -10);
        arm2.zArmGeom.setLocalTranslation(18, 6, -8);
        arm2.xArmGeom.setLocalTranslation(4, 6, 0);
        arm2.yArmGeom.setLocalTranslation(17, 6, 0);
        arm2.tooltipNode.setLocalTranslation(17, -0.4f, 0);

        lb = new LegoBuffer(assetManager, rootNode, 5, -29, 10, 6, true);
        lb2 = new LegoBuffer(assetManager, rootNode, 5, 16, 10, 6, false);

        ast.arm = arm;

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
         if (!arm1Ready) this.arm1Work();
         else this.arm2Work();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // TODO: add render code
    }

    private void arm2Work() {
        if (!freeze && moving) {
            moving = ast.move();
        }

        if (!moving && !freeze) {
            if (goingToLego) {
                Vector3f v = lb2.getLegoCenterLocation(slotIndex++).add(new Vector3f(0, 0.4f, 0));
                ast.initMoveToStation(lego, v);
                goingToLego = false;
                moving = true;
            } else {
                if (lego != null) {
                    Vector3f loc = lego.node.getWorldTranslation();
                    arm2.node.detachChild(lego.node);
                    lego.node.setLocalTranslation(loc);
                    rootNode.attachChild(lego.node);
                }

                try {
                    lego = legos.get(slotIndex);
                    moving = true;
                } catch (IndexOutOfBoundsException e) {
                    freeze = true;
                    lego = null;
                }

                if (!freeze) {
                    ast.initMoveToLego(lego);
                }
                goingToLego = true;

            }
        }
    }

    private void arm1Work() {
        if (!freeze && moving) {
            moving = ast.move();
        }

        if (!moving && !freeze) {
            if (goingToLego) {
                Vector3f v = STACK ? ast.slotPosition(slotIndex).add(new Vector3f(0, (stacked++)*0.4f, 0)) 
                    : ast.slotPosition(slotIndex++); //t4
                ast.initMoveToStation(lego, v);
                lego.location = v.clone();
                legos.add(lego);
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
                    slotIndex = STACK ? slotIndex+1 : slotIndex; //t4
                    stacked = 0; //t4
                    if (colorIndex >= numColors) {
                        arm1Ready = true;
                        ast.arm = arm2;
                        arm2.step = 1f;
                        moving = false;
                        slotIndex = 0;
                        goingToLego = false;
                        if (STACK) {
                            Collections.reverse(legos);
                        }
                        return;
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
}