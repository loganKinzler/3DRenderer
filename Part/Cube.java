import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class Cube extends Part {

    // contructors
    public Cube() {
        this.generatePart(new Vector3D(0, 0, 0), new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), 1);
    }
    
    public Cube(Vector3D pos) {
        this.generatePart(pos, new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), 1);
    }
    
    public Cube(Vector3D pos, int res) {
        this.generatePart(pos, new Vector3D(1, 1, 1), new Vector3D(0, 0, 0), res);
    }

    public Cube(Vector3D pos, Vector3D size) {
        this.generatePart(pos, size, new Vector3D(0, 0, 0), 1);
    }

    public Cube(Vector3D pos, Vector3D size, int res) {
        this.generatePart(pos, size, new Vector3D(0, 0, 0), res);
    }

    public Cube(Vector3D pos, Vector3D size, Vector3D rot) {
        this.generatePart(pos, size, rot, 1);
    }

    public Cube(Vector3D pos, Vector3D size, Vector3D rot, int res) {
        this.generatePart(pos, size, rot, res);
    }
    
    protected void generatePart(Vector3D pos, Vector3D size, Vector3D rot, int res) {
        
        // variable setup
        this.position = pos;
        this.rotation = new Vector3D();

        this.size = size;

        if (res < 0) {throw new InvalidParameterException("Resoultion for object Cone, should be greater than 0.");}
        this.resolution = res;
        
        this.color = new int[]{128, 128, 128};
        size.setScalarMult(0.5);

        ArrayList<Triangle3D> tri3Ds = new ArrayList<Triangle3D>();
        ArrayList<Vector3D> points = new ArrayList<Vector3D>();
        Mesh3D tempMesh = new Mesh3D();
        

        // front
        Vector3D start = pos.add(new Vector3D(-size.getX(), -size.getY(), -size.getZ()));// -,-,-
        Vector3D end = pos.add(new Vector3D(size.getX(), size.getY(), -size.getZ()));// +,+,-
        Vector3D corner = pos.add(new Vector3D(size.getX(), -size.getY(), -size.getZ()));// +,-,-
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));
        

        // left
        start = pos.add(new Vector3D(size.getX(), -size.getY(), -size.getZ()));// +,-,-
        end = pos.add(new Vector3D(size.getX(), size.getY(), size.getZ()));// +,+,+
        corner = pos.add(new Vector3D(size.getX(), -size.getY(), size.getZ()));// +,-,+
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));


        // back
        start = pos.add(new Vector3D(size.getX(), -size.getY(), size.getZ()));// +,-,+
        end = pos.add(new Vector3D(-size.getX(), size.getY(), size.getZ()));// -,+,+
        corner = pos.add(new Vector3D(-size.getX(), -size.getY(), size.getZ()));// -,-,+
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));
        

        // right
        start = pos.add(new Vector3D(-size.getX(), -size.getY(), size.getZ()));// -,-,+
        end = pos.add(new Vector3D(-size.getX(), size.getY(), -size.getZ()));// -,+,-
        corner = pos.add(new Vector3D(-size.getX(), -size.getY(), -size.getZ()));// -,-,-
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));


        // top
        start = pos.add(new Vector3D(-size.getX(), size.getY(), -size.getZ()));// -,+,-
        end = pos.add(new Vector3D(size.getX(), size.getY(), size.getZ()));// +,+,+
        corner = pos.add(new Vector3D(size.getX(), size.getY(), -size.getZ()));// +,+,-
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));


        // bottom
        start = pos.add(new Vector3D(-size.getX(), -size.getY(), size.getZ()));// -,-,+
        end = pos.add(new Vector3D(size.getX(), -size.getY(), -size.getZ()));// +,-,-
        corner = pos.add(new Vector3D(size.getX(), -size.getY(), size.getZ()));// +,-,+
        
        tempMesh.generateUnitMesh(start, end, corner, res, res);
        tri3Ds.addAll(new ArrayList<Triangle3D>(Arrays.asList(tempMesh.getTris())));
        points.addAll(new ArrayList<Vector3D>(Arrays.asList(tempMesh.getPoints())));

        this.partMesh = new Mesh3D(tri3Ds.toArray(new Triangle3D[0]));
        this.vector3Ds = points.toArray(new Vector3D[0]);

        this.rotateTo(rot);
    }

    public void resize(Vector3D size) {
        this.generatePart(this.position, size, this.rotation, this.resolution);
    } 
}
