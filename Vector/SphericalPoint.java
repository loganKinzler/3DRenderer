import java.lang.Math;

public class SphericalPoint {
    
    // variables
    private double rho;
    private double theta;
    private double phi;

    // constructors
    public SphericalPoint() {
        this.rho = 0;
        this.phi = 0;
        this.theta = 0;
    }

    public SphericalPoint(double theta, double phi) {
        this.rho = 1;
        this.theta = theta;
        this.phi = phi;
    }

    public SphericalPoint(double rho, double theta, double phi) {
        this.rho = rho;
        this.theta = theta;
        this.phi = phi;
    }
    
    // getters / setters
    public double getDistance() {return this.rho;}
    public void setDistance(double dist) {this.rho = dist;}

    public double getAngleY() {return this.theta;}
    public void setAngleY(double angle) {this.theta = angle;}

    public double getAngleX() {return this.phi;}
    public void setAngleX(double angle) {this.phi = angle;}

    public String toString() {
        return String.format("p: %.1f Φ: %.1f Θ: %.1f", this.rho, this.phi, this.theta);
    }

    public static SphericalPoint fromCartesean(double x, double y, double z) {
        SphericalPoint newPoint = new SphericalPoint();

        // distance is zero
        if (x == 0 && y == 0 && z == 0) {
            newPoint.setAngleX(0);
            newPoint.setAngleY(0);
            return newPoint;
        }

        double xzRadius = Math.pow(x, 2) + Math.pow(z, 2);
        newPoint.setDistance( Math.sqrt(xzRadius + Math.pow(y, 2)) );
        xzRadius = Math.sqrt(xzRadius);

        newPoint.setAngleX( Math.atan2(y, xzRadius) );
        newPoint.setAngleY( Math.atan2(-x, z) );
        return newPoint;
    }

    public static SphericalPoint fromCartesian(Vector3D point) {
        SphericalPoint newPoint = new SphericalPoint();

        // distance is zero
        if (point == new Vector3D()) {
            newPoint.setAngleX(0);
            newPoint.setAngleY(0);
            return newPoint;
        }

        double xzRadius = Math.pow(point.getX(), 2) + Math.pow(point.getZ(), 2);
        newPoint.setDistance( Math.sqrt(xzRadius + Math.pow(point.getY(), 2)) );
        xzRadius = Math.sqrt(xzRadius);

        newPoint.setAngleX( Math.atan2(point.getY(), xzRadius) );
        newPoint.setAngleY( Math.atan2(-point.getX(), point.getZ()) );
        
        return newPoint;
    }

    public static SphericalPoint subtract(SphericalPoint p1, SphericalPoint p2) {
        Vector3D point1  = Vector3D.fromSpherical(p1);
        Vector3D point2  = Vector3D.fromSpherical(p2);
        SphericalPoint difference = SphericalPoint.fromCartesian( point2.minus(point1) );

        return difference;
    }
}
