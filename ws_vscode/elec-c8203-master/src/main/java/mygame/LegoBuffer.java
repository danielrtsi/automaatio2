package mygame;

import java.util.ArrayList;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class LegoBuffer {
    private Box box;
    private Geometry geom;
    private float surfaceHeight;
    private final float legoSpacingX = 2;
    private final float legoSpacingZ = 2;

    float x;
    float z;
    int rowSize;
    int columnSize;

    ArrayList<Lego> legos = new ArrayList<>();

    public LegoBuffer(AssetManager assetManager, Node rootNode, float xOffset, float zOffset, int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.z = zOffset;
        this.x = xOffset;

        float yExtent = 7;

        this.box = new Box(16f, yExtent, 8f);
        this.geom = new Geometry("Box", box);
        rootNode.attachChild(geom);

        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.LightGray);

        this.geom.setMaterial(mat);

        this.surfaceHeight = Main.floorHeight + yExtent;
        
        this.geom.setLocalTranslation(this.x, this.surfaceHeight, this.z);

        String colorLego = "red";
        for (int i = 0; i < ((this.rowSize*this.columnSize)/4); i++) {
            for (int j = 0; j < 4; j++) {
                switch(j) {
                    case 0:
                        colorLego = "yellow";
                        break;
                    case 1:
                        colorLego = "blue";
                        break;
                    case 2:
                        colorLego = "pink";
                        break;
                    case 3:
                        colorLego = "green";
                        break;
                }

                Lego lego = new Lego(assetManager, colorLego);
                rootNode.attachChild(lego.node);
                this.legos.add(lego);
            }
        }
        for (int i = 0; i < legos.size(); i++) {
            this.legos.get(i).node.setLocalTranslation(this.getLegoCenterLocation(i));
        }
    }

    private float xCoord(int i) {
        int rowIndex = i % this.rowSize;
        return (rowIndex - this.rowSize/2) * this.legoSpacingX;
    }

    private float zCoord(int i) {
        int rowIndex = i / this.rowSize;
       // System.out.println((i /10));
        return (rowIndex - this.columnSize/2) * this.legoSpacingZ;
    }

    private Vector3f getLegoCenterLocation(int i) {
        return new Vector3f(this.x+this.xCoord(i), Main.floorHeight+2*7+0.2f, this.z+this.zCoord(i));
    }

    private Vector3f getLegoTopLocation(int i) {
        return new Vector3f(this.x+this.xCoord(i), Main.floorHeight+2*7+0.4f, this.z+this.zCoord(i));
    }

    public Lego giveLego(String color) {
        Lego lego;
        for (int i = 0; i<(this.rowSize*this.columnSize); i++) {
            lego = this.legos.get(i);
            if (lego == null) continue;
            if (lego.color.equalsIgnoreCase(color)) {
                lego.location = this.getLegoTopLocation(i);
                this.legos.set(i, null);
                return lego;
            }
        }
        return null;
    }


}
