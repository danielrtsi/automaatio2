package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;

public class Lego {
    Node node = new Node();
    Geometry geom;
    Box box;
    String color;
    Vector3f location;

    public Lego(AssetManager assetManager, String color) {
        this.color = color;

        box = new Box(0.8f, 0.2f, 0.4f);
        geom = new Geometry("Box", box);
        node.attachChild(geom);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        ColorRGBA c;

        switch (color) {
            case "blue":
                c = ColorRGBA.Blue;
                break;
            case "pink":
                c = ColorRGBA.Pink;
                break;
            case "yellow":
                c = ColorRGBA.Yellow;
                break;
            case "green":
                c = ColorRGBA.Green;
                break;
            case "red":
                c = ColorRGBA.Red;
                break;
            default:
                c = ColorRGBA.DarkGray;
        }

        mat.setColor("Diffuse", c);
        geom.setMaterial(mat);

        for (int i = 0; i < 8; i++) {
            Cylinder cyl = new Cylinder(20, 20, 0.1f, 0.1f, true);
            Geometry g = new Geometry("C", cyl);
            g.rotate(FastMath.HALF_PI, 0, 0);
            float x = 0;
            switch (i/2) {
                case 0:
                    x = -0.6f;
                    break;
                case 1:
                    x = -0.2f;
                    break;
                case 2:
                    x = 0.2f;
                    break;
                case 3:
                    x = 0.6f;
                    break;
            }
            float z = i % 2 == 0 ? 0.2f : -0.2f;
            g.setLocalTranslation(x, 0.2f, z);
            g.setMaterial(mat);
            node.attachChild(g);
        }
    }
}