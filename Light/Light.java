public class Light {
    
    protected double lightStrength;
    protected int[] lightColor;
    protected boolean localLight;

    public Light() {
        lightStrength = 1.0;
        lightColor = new int[] {255, 255, 255};
        localLight = true;
    }

    public Light(double strength) {
        lightStrength = strength;
        lightColor = new int[] {255, 255, 255};
        localLight = true;
    }

    public Light(int r, int g, int b) {
        lightStrength = 1.0;
        lightColor = new int[] {r, g, b};
        localLight = true;
    }
    
    public Light(int[] color) {
        lightStrength = 1.0;
        lightColor = color;
        localLight = true;
    }

    public Light(boolean localLight) {
        lightStrength = 1.0;
        lightColor = new int[] {255, 255, 255};
        this.localLight = localLight;
    }    

    public Light(double strength, int r, int g, int b) {
        lightStrength = strength;
        lightColor = new int[] {r, g, b};
        localLight = true;
    }

    public Light(double strength, boolean localLight) {
        lightStrength = strength;
        lightColor = new int[] {255, 255, 255};
        this.localLight = localLight;
    }

    public Light(int r, int g, int b, boolean localLight) {
        lightStrength = 1.0;
        lightColor = new int[] {r, g, b};
        this.localLight = localLight;
    }

    public Light(int[] color, boolean localLight) {
        lightStrength = 1.0;
        lightColor = color;
        this.localLight = localLight;
    }

    public Light(double strength, int r, int g, int b, boolean localLight) {
        lightStrength = strength;
        lightColor = new int[] {r, g, b};
        this.localLight = localLight;
    }

    public Light(double strength, int[] color) {
        lightStrength = strength;
        lightColor = color;
        localLight = true;
    }

    public Light(double strength, int[] color, boolean localLight) {
        lightStrength = strength;
        lightColor = color;
        this.localLight = localLight;
    }

    public double difusion(Triangle3D tri) {return 1.0;}
    public int[] colorEffect(Triangle3D tri) {return tri.getColor();}

    public String toString() {return "Light";}
}
