package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.math.Vector3f;

public class AssemblyStation {
    private float x;
    private float z;
    private static final int yExtent = 6;
    private static final float maxHeight = 4;
    private static final float legoSpacingX = 2;
    private static final float legoSpacingZ = 2;
    private boolean moving = false;
    
    float surcafeHeight;
    Box box;
    Geometry geom;
    Trajectory trajectory;

    public AssemblyStation(AssetManager assetManager, Node rootNode, float xOffset, float zOffset) {
        this.x = xOffset;
        this.z = zOffset;
        this.surcafeHeight = Main.floorHeight + 2*yExtent + 0.2f;

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

    public void initTestMove(Vector3f destination) {
        this.trajectory = new Trajectory();

        Vector3f v1 = Main.arm.getTooltipLocation();
        v1.setY(maxHeight);
        trajectory.addPoint(v1);
        trajectory.addPoint(destination.add(new Vector3f(0, maxHeight, 0)));
        trajectory.addPoint(destination);
        trajectory.initTrajectory();
    }

    public void initMoveToLego(Lego lego) {
        this.trajectory = new Trajectory();

        Vector3f v1 = Main.arm.getTooltipLocation();
        Vector3f v2 = lego.location.clone();
        v1.setY(maxHeight);
        v2.setY(maxHeight);
        trajectory.addPoint(v1);
        trajectory.addPoint(v2);
        trajectory.addPoint(lego.location);
    }

    public void initMoveToStation(Lego lego, Vector3f dest) {
        Main.arm.tooltipNode.attachChild(lego.node);
        lego.node.setLocalTranslation(0, -0.6f, 0);
        
        this.trajectory = new Trajectory();

        Vector3f v1 = Main.arm.getTooltipLocation();
        Vector3f v2 = dest.clone();
        v1.setY(maxHeight);
        v2.setY(maxHeight);
        trajectory.addPoint(v1);
        trajectory.addPoint(v2);
        trajectory.addPoint(dest);
    }

    public boolean move() {
        if (this.moving) {
            this.moving = Main.arm.move();
            return true;
        } else {
            Vector3f nextPoint = trajectory.nextPoint();
            if (nextPoint == null) {
                return false;
            }
            System.out.println(nextPoint.toString());
            this.moving = true;
            Main.arm.initMove(nextPoint);
            return true;
        }
    }

    public Vector3f slotPosition(int slot) {
        int rowSize = (int) ((16)/legoSpacingX);
    //    int columnSize = (int) ((12)/legoSpacingZ);

        int rowIndex = slot % rowSize;
        float xOffset = (rowIndex-1) * legoSpacingX;
        int columnIndex = slot / rowSize;
        float zOffset = (columnIndex+2) * legoSpacingZ;
        float yOffset = 0.4f;

        return new Vector3f(this.x + xOffset, this.surcafeHeight + yOffset, this.z + zOffset);
    }
}
