import java.util.Arrays;

public class Cell {
    private float[] legoBufferCoords;
    private float[] assemblyStationCoords;

    public Cell() {
        this.legoBufferCoords = new float[3];
        this.assemblyStationCoords = new float[3];

        this.legoBufferCoords[0] = 0;
        this.legoBufferCoords[1] = 0;
        this.legoBufferCoords[2] = 0;

        this.assemblyStationCoords[0] = 0;
        this.assemblyStationCoords[1] = 0;
        this.assemblyStationCoords[2] = 0;
    }

    public float[] getBufferCoords() {
        return this.legoBufferCoords;
    }
    public float[] getStationCoords() {
        return this.assemblyStationCoords;
    }

    public void setBufferCoords(float[] coords) {
        this.legoBufferCoords = coords;
    }
    public void setStationCoords(float[] coords) {
        this.assemblyStationCoords = coords;
    }
    
    @Override
    public String toString() {
        return "Lego buffer coords: [" + Arrays.toString(this.legoBufferCoords)
        + "\nAssembly station coords: " + Arrays.toString(this.assemblyStationCoords);
    } 

}
