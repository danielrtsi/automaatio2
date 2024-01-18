package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class AssemblyStation {
    private static final int yExtent = 6;

    Box box;
    Geometry geom;

    public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset) {
        box = new Box(20, yExtent, 20);
        geom = new Geometry("Box", box);
        rootNode.attachChild(geom);
        geom.setLocalTranslation(xOffset, Main.floorHeight + yExtent, zOffset);

        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);

        geom.setMaterial(mat);
    }
}
