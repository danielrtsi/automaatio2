package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class RobotArm {
    private Vector3f targetLocation;
    private float step = 1f;

    Node node = new Node();
    Node tooltipNode = new Node();
    
    Geometry mastGeom;
    Geometry zArmGeom;
    Geometry xArmGeom;
    Geometry yArmGeom;
    Geometry tooltipGeom;


    public RobotArm(AssetManager assetManager) {
        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Orange);

        Box mast = new Box(0.2f, 6f, 0.2f);
        Box zArm = new Box(0.2f, 0.2f, 20f);
        Box xArm = new Box(18f, 0.2f, 0.2f);
        Box yArm = new Box(0.2f, 6f, 0.2f);
        Box tooltip = new Box(0.14f, 0.4f, 0.14f);

        this.mastGeom = new Geometry("Mast", mast);
        this.zArmGeom = new Geometry("zArm", zArm);
        this.xArmGeom = new Geometry("xArm", xArm);
        this.yArmGeom = new Geometry("yArm", yArm);
        this.tooltipGeom = new Geometry("Tooltip", tooltip);

        this.node.attachChild(mastGeom);
        this.node.attachChild(zArmGeom);
        this.node.attachChild(xArmGeom);
        this.node.attachChild(yArmGeom);
        
        this.tooltipNode.attachChild(tooltipGeom);
        this.node.attachChild(tooltipNode);

        this.mastGeom.setLocalTranslation(-8, 0, -10);
        this.zArmGeom.setLocalTranslation(-8, 6, -8);
        this.xArmGeom.setLocalTranslation(6, 6, 0);
        this.yArmGeom.setLocalTranslation(-7, 6, 0);
        tooltipNode.setLocalTranslation(-7, -0.4f, 0);

        mastGeom.setMaterial(mat);
        zArmGeom.setMaterial(mat);
        yArmGeom.setMaterial(mat);
        xArmGeom.setMaterial(mat);
        tooltipGeom.setMaterial(mat);
    }

    public void initMove(Vector3f target) {
        this.targetLocation = target;
    }

    public Vector3f getTooltipLocation() {
        return this.tooltipGeom.getWorldTranslation().add(new Vector3f(0, -0.2f, 0));
    }

    public boolean move() {
        Vector3f location = this.getTooltipLocation();
        float xDistance = this.targetLocation.getX() - location.getX();
        float yDistance = this.targetLocation.getY() - location.getY();
        float zDistance = this.targetLocation.getZ() - location.getZ();

        boolean xReady = false;
        boolean yReady = false;
        boolean zReady = false;

        float x;
        float y;
        float z;
        
        if (xDistance > this.step) {
            x = this.step;
        } else if ((-1*xDistance) > this.step) {
            x = -1*this.step;
        } else {
            xReady = true;
            x = xDistance;
        }

        if (yDistance > this.step) {
            y = this.step;
        } else if ((-1*yDistance) > this.step) {
            y = -1*this.step;
        } else {
            yReady = true;
            y = yDistance;
        }

        if (zDistance > this.step) {
            z = this.step;
        } else if ((-1*zDistance) > this.step) {
            z = -1*this.step;
        } else {
            zReady = true;
            z = zDistance;
        }

        Vector3f v = new Vector3f(0, 0, 0.5f*z);
        this.zArmGeom.setLocalTranslation(this.zArmGeom.getLocalTranslation().add(v));

        Vector3f v1 = new Vector3f(0, 0, z);
        this.xArmGeom.setLocalTranslation(this.xArmGeom.getLocalTranslation().add(v1));

        Vector3f v2 = new Vector3f(x, y, z);
        this.yArmGeom.setLocalTranslation(this.yArmGeom.getLocalTranslation().add(v2));
        this.tooltipNode.setLocalTranslation(this.tooltipNode.getLocalTranslation().add(v2));

        if ((yReady && xReady) && zReady) {
            return false;
        } else {
            return true;
        }
    }
}
