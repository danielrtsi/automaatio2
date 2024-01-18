package mygame;
import java.util.ArrayList;

import com.jme3.math.Vector3f;

public class Trajectory {
    private ArrayList<Vector3f> points;
    private int index;
    private int size;
    
    public Trajectory() {
        this.points = new ArrayList<>();
        this.index = 0;
        this.size = 0;
    }

    public void addPoint(Vector3f v) {
        this.points.add(v);
        this.size++;
    }

    public void initTrajectory() {
        this.index = 0;
        this.size = this.points.size();
    }

    public Vector3f nextPoint() {
        if (this.points.size() == this.index) {
            return null;
        }
        this.index++;
        return this.points.get(this.index-1);
    }
} 
