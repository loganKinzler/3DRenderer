import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Cone extends Part {

    // contructors
    public Cone() {
        this.generatePart(new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), 1);
    }
    
    public Cone(Vector3D pos) {
        this.generatePart(pos, new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), 1);
    }
    
    public Cone(Vector3D pos, int res) {
        this.generatePart(pos, new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), res);
    }

    public Cone(Vector3D pos, Vector3D size) {
        this.generatePart(pos, size, new Vector3D(0, 0, 0), 1);
    }

    public Cone(Vector3D pos, Vector3D size, int res) {
        this.generatePart(pos, size, new Vector3D(0, 0, 0), res);
    }

    public Cone(Vector3D pos, Vector3D size, Vector3D rot) {
        this.generatePart(pos, size, rot, 1);
    }

    public Cone(Vector3D pos, Vector3D size, Vector3D rot, int res) {
        this.generatePart(pos, size, rot, res);
    }
    
    protected void generatePart(Vector3D pos, Vector3D size, Vector3D rot, int res) {
        
        // variable setup
        this.position = pos;
        this.rotation = new Vector3D();

        this.size = size;

        if (res < 3) {throw new InvalidParameterException("Resoultion for object Cone, should be greater than 3.");}
        this.resolution = res;
        
        this.color = new int[]{128, 128, 128};
        size.setScalarMult(0.5);

        ArrayList<Triangle3D> tri3Ds = new ArrayList<Triangle3D>();
        ArrayList<Vector3D> points = new ArrayList<Vector3D>();
        
        // top / bottom points
        points.add(new Vector3D(pos.getX(), pos.getY() + size.getY(), pos.getZ()));
        points.add(new Vector3D(pos.getX(), pos.getY() - size.getY(), pos.getZ()));

        // outer points
        double rads = 0;
        for (int i=0; i<res; i++) {
            points.add(new Vector3D(
                pos.getX() - size.getX()*Math.sin(rads),
                pos.getY() - size.getY(),
                pos.getZ() + size.getZ()*Math.cos(rads)
            ));

            rads = Math.TAU / res * (i + 1);
        }

        // connect tris
        for (int i=2; i<points.size()-1; i++) {
            tri3Ds.add(new Triangle3D(
                points.get(i+1),
                points.get(0),
                points.get(i)
            ));

            tri3Ds.add(new Triangle3D(
                points.get(i),
                points.get(1),
                points.get(i+1)
            ));
        }

        // final tris
        tri3Ds.add(new Triangle3D(
            points.get(2),
            points.get(0),
            points.get(res + 1)
        ));

        tri3Ds.add(new Triangle3D(
            points.get(res + 1),
            points.get(1),
            points.get(2)
        ));

        this.partMesh = new Mesh3D(tri3Ds.toArray(new Triangle3D[0]));
        this.vector3Ds = points.toArray(new Vector3D[0]);

        this.rotateTo(rot);
    }

    public void resize(Vector3D size) {
        this.generatePart(this.position, size, this.rotation, this.resolution);
    } 
}
