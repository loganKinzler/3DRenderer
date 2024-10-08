public class DirectionalLight extends Light {
    
    private Vector3D lightDirection;

    public DirectionalLight() {
        super(0.25);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(double strength) {
        super(strength);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(int[] color) {
        super(0.25, color);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(int r, int b, int g) {
        super(0.25, r, g, b);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(boolean localLight) {
        super(0.25, localLight);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(int[] color, boolean localLight) {
        super(0.25, color, localLight);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(int r, int b, int g, boolean localLight) {
        super(0.25, r, g, b, localLight);
        lightDirection = new Vector3D(0, -1, 0);
    }
    
    public DirectionalLight(double strength, int[] color) {
        super(strength, color);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(double strength, int[] color, boolean localLight) {
        super(strength, color, localLight);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(double strength, int r, int g, int b) {
        super(strength, r, g, b);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(double strength, int r, int g, int b, boolean localLight) {
        super(strength, r, g, b, localLight);
        lightDirection = new Vector3D(0, -1, 0);
    }

    public DirectionalLight(double strength, Vector3D direction) {
        super(strength);
        lightDirection = direction;
    }

    public DirectionalLight(double strength, Vector3D direction, boolean localLight) {
        super(strength, localLight);
        lightDirection = direction;
    }

    public DirectionalLight(int[] color, Vector3D direction) {
        super(0.25, color);
        lightDirection = direction;
    }

    public DirectionalLight(int[] color, Vector3D direction, boolean localLight) {
        super(0.25, color, localLight);
        lightDirection = direction;
    }

    public DirectionalLight(int r, int g, int b, Vector3D direction) {
        super(0.25, r, g, b);
        lightDirection = direction;
    }

    public DirectionalLight(int r, int g, int b, Vector3D direction, boolean localLight) {
        super(0.25, r, g, b, localLight);
        lightDirection = direction;
    }

    public DirectionalLight(double strength, int[] color, Vector3D direction) {
        super(strength, color);
        lightDirection = direction;
    }

    public DirectionalLight(double strength, int[] color, Vector3D direction, boolean localLight) {
        super(strength, color, localLight);
        lightDirection = direction;
    }

    public DirectionalLight(double strength, int r, int b, int g, Vector3D direction) {
        super(strength, r, g, b);
        lightDirection = direction;
    }

    public DirectionalLight(double strength, int r, int b, int g, Vector3D direction, boolean localLight) {
        super(strength, r, g, b, localLight);
        lightDirection = direction;
    }


    public double difusion(Triangle3D tri) {
        return tri.getNormal().dot(lightDirection);
    }

    public int[] colorEffect(Triangle3D tri) {
        int[] triColor = tri.getColor();
        double triDiffusion = difusion(tri);

        if (triDiffusion < 0) {
            if (localLight) return triColor;
            return Lerp.colors(triColor, new int[]{255, 255, 255}, lightStrength * triDiffusion);
        }
        return Lerp.colors(triColor, lightColor, lightStrength * triDiffusion);
    }

    public String toString() {return "Directional Light";}
}
