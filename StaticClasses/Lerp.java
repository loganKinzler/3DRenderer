public class Lerp {

    // regular lerps
    public static int ints(int x, int y, double p) {return (int) (x + p * (y-x));}
    public static double doubles(double x, double y, double p) {return (x + p * (y-x));}

    public static int[] colors(int[] c1, int[] c2, double p) {
        return new int[]{
            Math.clamp(Lerp.ints(c1[0], c2[0], p), 0, 255),
            Math.clamp(Lerp.ints(c1[1], c2[1], p), 0, 255),
            Math.clamp(Lerp.ints(c1[2], c2[2], p), 0, 255)
        };
    }

    public static Vector Vectors(Vector start, Vector end, double p) {
        return new Vector( Lerp.doubles(start.getX(), end.getX(), p), Lerp.doubles(start.getY(), end.getY(), p) );
    }

    public static Vector3D Vector3Ds(Vector3D start, Vector3D end, double p) {
        return new Vector3D( Lerp.doubles(start.getX(), end.getX(), p), Lerp.doubles(start.getY(), end.getY(), p), Lerp.doubles(start.getZ(), end.getZ(), p) );
    }

    // useful getters
    public static double getSlope(Vector p1, Vector p2) {
        // in dy/dx
        if (p1.getX() == p2.getX()) {
            return Double.NaN;
        }

        Vector subtracted = p2.minus(p1);
        return subtracted.getY() / subtracted.getX();
    }

    public static double getPercent(double start, double end, double x) {
        return (x - start) / (end - start);
    }

    public static double getPercent(Vector start, Vector end, Vector x) {
        Vector SE = end.minus(start);
        Vector SX = x.minus(start);
        
        Vector SY = SX.project(SE);
        
        // solve the division by 0 issue
        if (SE.getX() != 0.0) {
            return SY.getX() / SE.getX();
        }
        if (SE.getY() != 0.0) {
            return SY.getY() / SE.getY();
        }
        return 0.0;
    }

    public static double fade(double x) {
        return 6*Math.pow(x, 5) - 15*Math.pow(x, 4) + 10*Math.pow(x, 3);
    }
}
