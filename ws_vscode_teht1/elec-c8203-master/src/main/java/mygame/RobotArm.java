package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class RobotArm {
    Node node = new Node();

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

        Geometry mastGeom = new Geometry("Mast", mast);
        Geometry zArmGeom = new Geometry("zArm", zArm);
        Geometry xArmGeom = new Geometry("xArm", xArm);
        Geometry yArmGeom = new Geometry("yArm", yArm);
        Geometry tooltipGeom = new Geometry("Tooltip", tooltip);

        this.node.attachChild(mastGeom);
        this.node.attachChild(zArmGeom);
        this.node.attachChild(xArmGeom);
        this.node.attachChild(yArmGeom);
        
        Node tooltipNode = new Node();
        tooltipNode.attachChild(tooltipGeom);
        this.node.attachChild(tooltipNode);

        mastGeom.setLocalTranslation(-8, 0, -10);
        zArmGeom.setLocalTranslation(-8, 6, -8);
        xArmGeom.setLocalTranslation(6, 6, 0);
        yArmGeom.setLocalTranslation(-7, 6, 0);
        tooltipNode.setLocalTranslation(-7, 0.4f, 0);

        mastGeom.setMaterial(mat);
        zArmGeom.setMaterial(mat);
        yArmGeom.setMaterial(mat);
        xArmGeom.setMaterial(mat);
        tooltipGeom.setMaterial(mat);
    }
}
