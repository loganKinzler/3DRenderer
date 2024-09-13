public class Vector3D extends Vector {
    
    // new vairables
    public double z;
    
    // constructors
    public Vector3D() {super();}
    
    public Vector3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public static Vector3D fromSpherical(SphericalPoint p) {
        Vector3D point = new Vector3D();
        point.setX( p.getDistance() * Math.cos(p.getAngleX()) * Math.sin(p.getAngleY()) );
        point.setY( Math.cos(p.getAngleX()) );
        point.setZ( p.getDistance() * Math.cos(p.getAngleX()) * Math.cos(p.getAngleY()) );
        
        return point;
    }

    // getters / setters
    public double getZ() {return this.z;}
    public void setZ(double z) {this.z = z;}

    public void setPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return String.format("(%.1f, %.1f, %.1f)", this.x, this.y, this.z);
    }
    

    public Vector3D add(Vector3D v) {
        return new Vector3D(this.getX() + v.getX(), this.getY() + v.getY(), this.getZ() + v.getZ());
    }


    public Vector3D minus(Vector3D v) {
        return new Vector3D(this.getX() - v.getX(), this.getY() - v.getY(), this.getZ() - v.getZ());
    }


    public void setScalarMult(double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }

    public Vector3D getScalarMult(double s) {
        return new Vector3D(this.x * s, this.y * s, this.z * s);
    }

    public double getLength() {
        return Math.sqrt( Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }


    public Vector3D getUnitVect() {
        if (this.getLength() == 0.0) {return new Vector3D(0, 0, 0);}

        return new Vector3D( this.x / this.getLength(), this.y / this.getLength(), this.z / this.getLength());
    }

    public void setUnitVect() {
        if (this.getLength() == 0.0) {return;}

        this.x /= this.getLength();
        this.y /= this.getLength();
        this.z /= this.getLength();
    }


    public double dot(Vector3D v) {
        return this.getX() * v.getX() + this.getY() * v.getY() + this.getZ() * v.getZ();
    }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(
            this.getY()*v.getZ() - v.getY()*this.getZ(),
            v.getX()*this.getZ() - this.getX()*v.getZ(),
            this.getX()*v.getY() - v.getX()*this.getY()
        );
    }

    public Vector3D project(Vector3D v) {
        return v.getScalarMult(this.dot(v.getUnitVect()) / v.getLength());
    }

    public static double distanceFromLine (Vector3D freePoint, Vector3D linePoint, Vector3D lineDirection) {
        Vector3D lineToFree = freePoint.minus(linePoint);
        return lineToFree.minus( lineToFree.project(lineDirection) ).getLength();
    }

    public double getThetaWith(Vector3D v) {
        return Math.acos(this.getUnitVect().dot(v.getUnitVect()));
    }
}
