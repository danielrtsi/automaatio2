package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * 
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static final float floorHeight = -15;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10);
        Lego yellowLego = new Lego(assetManager, "yellow");
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

       /*  AssemblyStation ast = new AssemblyStation(assetManager, rootNode, 5, -11);
        RobotArm arm = new RobotArm(assetManager);
        rootNode.attachChild(arm.node);
        */
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(ColorRGBA.White);
        lamp_light.setRadius(400f);
        lamp_light.setPosition(new Vector3f(2f, 8.0f, 10.0f));
        rootNode.addLight(lamp_light);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // TODO: add render code
    }
}