public class Light {
    
    protected double lightStrength;
    protected int[] lightColor;

    public Light() {
        lightStrength = 1.0;
        lightColor = new int[] {255, 255, 255};
    }

    public Light(double strength) {
        lightStrength = strength;
        lightColor = new int[] {255, 255, 255};
    }

    public Light(int r, int g, int b) {
        lightStrength = 1.0;
        lightColor = new int[] {r, g, b};
    }
    
    public Light(int[] color) {
        lightStrength = 1.0;
        lightColor = color;
    }

    public Light(double strength, int r, int g, int b) {
        lightStrength = strength;
        lightColor = new int[] {r, g, b};
    }

    public Light(double strength, int[] color) {
        lightStrength = strength;
        lightColor = color;
    }


    public double difusion(Triangle3D tri) {return 1.0;}
    public int[] colorEffect(Triangle3D tri) {return tri.getColor();}

    public String toString() {return "Light";}
}
