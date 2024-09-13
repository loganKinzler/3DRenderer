public class Triangle3D {

   // variables
   private Vector3D point1;
   private Vector3D point2;
   private Vector3D point3;

   private int[] color;


    // constructors
    public Triangle3D() {
        this.point1 = new Vector3D();
        this.point1 = new Vector3D();
        this.point1 = new Vector3D();

        this.color = new int[3];
        this.color[0] = 255;
        this.color[1] = 255;
        this.color[2] = 255;
    }

    public Triangle3D(Vector3D point1, Vector3D point2, Vector3D point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.color = new int[3];
        this.color[0] = 255;
        this.color[1] = 255;
        this.color[2] = 255;
    }

    public Triangle3D(int r, int g, int b) {
        this.point1 = new Vector3D();
        this.point2 = new Vector3D();
        this.point3 = new Vector3D();

        this.color = new int[3];
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
    }

    public Triangle3D(Vector3D point1, Vector3D point2, Vector3D point3, int r, int g, int b) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.color = new int[3];
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;  
    }

    // getters / setters
    public int[] getColor() {return this.color;}
    public void setColor(int r, int g, int b) {
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
    }
    
    public void setColor(int[] color) {
        this.color[0] = color[0];
        this.color[1] = color[1];
        this.color[2] = color[2];
    }
    
    public Vector3D getCenter() {
        double midX = ( this.point1.getX() + this.point2.getX() + this.point3.getX() ) / 3.0;
        double midY = ( this.point1.getY() + this.point2.getY() + this.point3.getY() ) / 3.0;
        double midZ = ( this.point1.getZ() + this.point2.getZ() + this.point3.getZ() ) / 3.0;
        return new Vector3D(midX, midY, midZ);
    }

    public Vector3D[] getPoints() {return new Vector3D[]{point1, point2, point3};}
    public void setPoints(Vector3D point1, Vector3D point2, Vector3D point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public void setPoint(Vector3D p, int pointNum) {
        if (pointNum == 1) {
            point1.setPos(p.getX(), p.getY(), p.getZ());
            return;
        }
        if (pointNum == 2) {
            point2.setPos(p.getX(), p.getY(), p.getZ());
            return;
        }
        if (pointNum == 3) {
            point3.setPos(p.getX(), p.getY(), p.getZ());
            return;
        }
    }

    public void movePoint(Vector3D p, int pointNum) {
        if (pointNum == 1) {
            p.setPos(point1.getX() + p.getX(), point1.getX() + p.getX());
            return;
        }
        if (pointNum == 2) {
            p.setPos(point2.getX() + p.getX(), point2.getX() + p.getX());
            return;
        }
        if (pointNum == 3) {
            p.setPos(point3.getX() + p.getX(), point3.getX() + p.getX());
            return;
        }
    }

    public void addToPoints(Vector3D p1, Vector3D p2, Vector3D p3) {
        p1.setPos(point1.getX() + p1.getX(), point1.getX() + p1.getX());
        p2.setPos(point2.getX() + p2.getX(), point2.getX() + p2.getX());
        p3.setPos(point3.getX() + p3.getX(), point3.getX() + p3.getX());
    }

    public Vector3D getPoint(int pointNum) {
        if (pointNum == 1) {return point1;}
        if (pointNum == 2) {return point2;}
        if (pointNum == 3) {return point3;}

        System.err.println("getVector() only accepts inputs of 1, 2, or 3");
        return new Vector3D();
    }

    public int containsVector(Vector3D p) {
        if (this.point1.equals(p)) {return 1;}
        if (this.point2.equals(p)) {return 2;}
        if (this.point3.equals(p)) {return 3;}
        return -1;
    }

    public Triangle toScreenTri(Camera cam) {
        Triangle screenTri = new Triangle();

        if ( !this.facingCamera(cam) )  return screenTri;
        Vector[] screenVectors = ArrayMethods.convertToScreenPoints(cam, this.getPoints());
        
        screenTri.setPoints(screenVectors[0], screenVectors[1], screenVectors[2]);
        screenTri.setColor(color);
        return screenTri;
    }

    public Triangle toScreenTri(Camera cam, Light light) {
        Triangle screenTri = new Triangle();

        if ( !(this.facingCamera(cam)) ) return screenTri;
        Vector[] screenVectors = ArrayMethods.convertToScreenPoints(cam, this.getPoints());
        
        screenTri.setPoints(screenVectors[0], screenVectors[1], screenVectors[2]);
        screenTri.setColor(light.colorEffect(this));
        return screenTri;
    }

    public Vector3D getNormal() {
        return point3.minus(point2).cross(point1.minus(point2)).getUnitVect();
    }

    public boolean facingCamera(Camera cam) {
        return this.getNormal().dot( this.getCenter().minus( cam.getPosition() ) ) > 0.0;
    }
}