import java.lang.Math;

public class Vector {

    protected double x;
    protected double y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // getters / setters
    public double getX() {return x;}
    public void setX(double x) {this.x = x;}

    public double getY() {return y;}
    public void setY(double y) {this.y = y;}

    public void setPos(double x, double y) {this.setX(x); this.setY(y);}

    public void setScalarMult(double s) {
        this.x *= s;
        this.y *= s;
    }

    public Vector getScalarMult(double s) {
        return new Vector(this.x * s, this.y * s);
    }

    public double getLength() {
        return Math.sqrt( Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }


    public Vector getUnitVect() {
        if (this.getLength() == 0.0) {return new Vector(0, 0);}

        return new Vector( this.x / this.getLength(), this.y / this.getLength() );
    }

    public void setUnitVect() {
        if (this.getLength() == 0.0) {return;}

        this.x /= this.getLength();
        this.y /= this.getLength();
    }


    public Vector add(Vector v) {
        return new Vector(this.getX() + v.getX(), this.getY() + v.getY());
    }

    public Vector minus(Vector v) {
        return new Vector(this.getX() - v.getX(), this.getY() - v.getY());
    }


    public double dot(Vector v) {
        return this.getX() * v.getX() + this.getY() * v.getY();
    }

    public double cross(Vector v) {
        return this.getX() * v.getY() - this.getY() * v.getX();
    }

    public Vector project(Vector v) {
        return v.getScalarMult(this.dot(v.getUnitVect()) / v.getLength());
    }


    public double distanceFromLine(Vector linePoint, Vector lineDirection) {
        Vector lineToFree = this.minus(linePoint);
        return lineToFree.minus( lineToFree.project(lineDirection) ).getLength();
    }

    public double getThetaWith(Vector v) {
        return Math.acos(this.getUnitVect().dot(v.getUnitVect()));
    }

    public int isCCWwith(Vector v) {

        double cross = this.cross(v);

        if (cross > 0.0) {return 1;}// counter clockwise
        if (cross < 0.0) {return -1;}// clockwise
        return 0;// colinear
}
    
        public boolean isWithin(Triangle tri) {
        Vector p1 = tri.getPoint(1);
        Vector p2 = tri.getPoint(2);
        Vector p3 = tri.getPoint(3);

        double dX = this.getX() - p1.getX();
        double dY = this.getY() - p1.getY();

        double dX20 = p3.getX() - p1.getX();
        double dY20 = p3.getY() - p1.getY();

        double dX10 = p2.getX() - p1.getX();
        double dY10 = p2.getY() - p1.getY();
   
        double s_p = (dY20 * dX) - (dX20 * dY);
        double t_p = (dX10 * dY) - (dY10 * dX);
        double D = (dX10 * dY20) - (dY10 * dX20);
   
        if (D > 0.0)
            return (s_p >= 0) && (t_p >= 0) && (s_p + t_p <= D);
        return (s_p <= 0) && (t_p <= 0) && (s_p + t_p >= D);
    }

    public boolean equalsZero() {
        return this.x == 0.0 && this.y == 0.0;
    }

    public String toString() {
        return String.format("(%.2f %.2f)", this.x, this.y);
    }
}
