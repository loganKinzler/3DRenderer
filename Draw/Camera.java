public class Camera {

    // variables
    private Vector3D cameraPos;

    private double theta;
    private double phi;

    private double FOV;
    
    // constructors    
    public Camera() {
        this.cameraPos = new Vector3D(0, 0, 0);

        this.theta = 0.0;
        this.phi = 0.0;

        this.FOV = Math.toRadians(140);
    }

    public Camera(Vector3D position) {
        this.cameraPos = position;

        this.theta = 0.0;
        this.phi = 0.0;

        this.FOV = Math.toRadians(140);
    }

    public Camera(Vector3D position, double xRotation, double yRotiation) {
        this.cameraPos = position;

        this.theta = xRotation;
        this.phi = yRotiation;

        this.FOV = Math.toRadians(140);
    }

    public Camera(double xRotation, double yRotiation, double FOV) {
        this.cameraPos = new Vector3D(0, 0, 0);

        this.theta = xRotation;
        this.phi = yRotiation;

        this.FOV = Math.toRadians(FOV);
    }


    // getters / setters
    public void setPosition(Vector3D position) {this.cameraPos = position;}
    public Vector3D getPosition() {return this.cameraPos;}

    public void setXRotation(double xRotation) {this.phi = (((xRotation - Math.PI) % Math.TAU) + Math.TAU) % Math.TAU - Math.PI;}
    public double getXRotation() {return this.phi;}

    public void setYRotation(double yRotation) {this.theta = (((yRotation - Math.PI) % Math.TAU) + Math.TAU) % Math.TAU - Math.PI;}
    public double getYRotation() {return this.theta;}
    
    public Vector3D getForwardVect() {
        return new Vector3D(
            -Math.cos(this.phi) * Math.sin(this.theta),
            Math.sin(this.phi),
            Math.cos(this.phi) * Math.cos(this.theta)
        );
    }

    public void setAngles(double yRotation, double xRotation) {
        setXRotation(xRotation);
        setYRotation(yRotation);
    }

    public void setFOV(double FOV) {this.FOV = FOV;}
    public double getFOV() {return this.FOV;}

    public String toString() {return String.format("%f, %f", theta, phi);}
}