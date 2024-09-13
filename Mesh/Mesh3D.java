import java.util.ArrayList;

public class Mesh3D {
    
    // variables
    private Vector3D[] point3Ds;
    private Triangle3D[] tri3Ds;

    public Mesh3D() {}

    public Mesh3D(Triangle3D[] tris) {
        this.tri3Ds = tris;
        ArrayList<Vector3D> point3DArray = new ArrayList<Vector3D>();

        for (Triangle3D tri : tris) {
            for (Vector3D point3D : tri.getPoints()) {
                if (point3DArray.contains(point3D)) continue;
                point3DArray.add(point3D);
            }
        }

        this.point3Ds = new Vector3D[point3DArray.size()];

        // keep references
        for (int p=0; p<this.point3Ds.length; p++) {
            this.point3Ds[p] = point3DArray.get(p);
        }
    }

    // getters / setters
    public Vector3D[] getPoints() {
        return this.point3Ds;
    }

    public Triangle3D[] getTris() {
        return this.tri3Ds;
    }

    public void setColor(int[] color) {
        for (Triangle3D tri : this.tri3Ds) {
            tri.setColor(color);
        }
    }

    public void setColor(int n, int[] color) {
        this.tri3Ds[n].setColor(color);
    }

    // methods
    public void generateUnitMesh(Vector3D start, Vector3D end, Vector3D corner, int width, int height) {
        
        if (width == 1 && height == 1) {

            // vector math for other corner (flip corner across SE line at half point)
            Vector3D m = Lerp.Vector3Ds(start, end, 0.5);

            this.point3Ds = new Vector3D[]{
                start,
                corner,
                end,
                m.getScalarMult(2).minus(corner)
            };

            this.tri3Ds = new Triangle3D[]{
                new Triangle3D(
                    this.point3Ds[0], // bottom left
                    this.point3Ds[1], // bottom right
                    this.point3Ds[2]  // top right
                ),

                new Triangle3D(
                    this.point3Ds[2], // top right
                    this.point3Ds[3], // top left
                    this.point3Ds[0]  // bottom left
                )
            };

            return;
        }

        // local variables
        Vector3D u = corner.minus(start);
        Vector3D v = end.minus(corner);
        u.setScalarMult(1 / (double) width);
        v.setScalarMult(1 / (double) height);
        
        this.point3Ds = new Vector3D[(width+1) * (height+1)];
        this.tri3Ds = new Triangle3D[2 * width*height];
        int tri_index = 0;

        for (int i=0; i<width+1; i++) {
            for (int j=0; j<height+1; j++) {
                Vector3D delta = u.getScalarMult(i).add(v.getScalarMult(j));
                point3Ds[i + (width+1)*j] = start.add(delta);
            }
        }

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                tri3Ds[tri_index] = new Triangle3D(
                    this.point3Ds[i + (width+1)*j],      // bottom left
                    this.point3Ds[i+1 + (width+1)*j],  // bottom right
                    this.point3Ds[i+1 + (width+1)*(j+1)] // top right
                );

                tri3Ds[tri_index + 1] = new Triangle3D(
                    this.point3Ds[i+1 + (width+1)*(j+1)], // top right
                    this.point3Ds[i + (width+1)*(j+1)],   // top left
                    this.point3Ds[i + (width+1)*j]        // bottom left
                );

                tri_index += 2;
            }
        }
    }
}