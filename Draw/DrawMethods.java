import java.util.ArrayList;

public class DrawMethods {

    // varaibles
    private static boolean filled = true;
    private static Camera cam;
    private static Vector canvasSize;

    public static void setUp() {
        StdDraw.enableDoubleBuffering();
        
        DrawMethods.filled = true;
        DrawMethods.cam = new Camera();

        DrawMethods.setCanvasSize(new Vector(500, 500));
    }

    public static void setCamera(Camera cam) {
        DrawMethods.cam = cam;
    }

    public static void setCanvasSize(Vector size) {
        DrawMethods.canvasSize = size;
        StdDraw.setCanvasSize((int) size.getX(), (int) size.getY());

        if ((int) size.getX() == (int) size.getY()) {
            StdDraw.setScale(-1, 1);
            return;
        }

        if (size.getX() == Math.max(size.getX(), size.getY())) {
            StdDraw.setXscale(-1, 1);
            StdDraw.setYscale(-size.getY() / size.getX(), size.getY() / size.getX());
            return;
        }

        StdDraw.setYscale(-1, 1);
        StdDraw.setXscale(-size.getX() / size.getY(), size.getX() / size.getY());
    }

    public static void setWireFrame() {
        DrawMethods.filled = false;
    }

    public static void unsetWireFrame() {
        DrawMethods.filled = true;
    }


    // draw methods
    public static void drawPoint(Vector p) {
        StdDraw.circle(p.getX(), p.getY(), 0.005);
    }

    public static void drawTri(Triangle tri) {
        int[] color = tri.getColor();

        StdDraw.setPenColor(color[0], color[1], color[2]);

        if (DrawMethods.filled) {
            StdDraw.filledPolygon(new double[]{tri.getPoint(1).getX(), tri.getPoint(2).getX(), tri.getPoint(3).getX()},
                new double[]{tri.getPoint(1).getY(), tri.getPoint(2).getY(), tri.getPoint(3).getY()});
        
            return;
        }

        StdDraw.polygon(new double[]{tri.getPoint(1).getX(), tri.getPoint(2).getX(), tri.getPoint(3).getX()},
            new double[]{tri.getPoint(1).getY(), tri.getPoint(2).getY(), tri.getPoint(3).getY()});
    }


    // 3d methods
    public static void drawPoint(Camera cam, Vector3D point) {
        Vector screenPoint = ArrayMethods.convertToScreenPoint(cam, point);
        DrawMethods.drawPoint(screenPoint);
    }

    public static void drawPoints(Vector3D[] points) {
        Vector[] screenPoints = ArrayMethods.convertToScreenPoints(DrawMethods.cam, points);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Vector p : screenPoints) {
            drawPoint(p);
        }
    }

    public static void drawPoints(ArrayList<Vector3D> points) {
        ArrayList<Vector> screenPoints = ArrayMethods.convertToScreenPoints(DrawMethods.cam, points);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Vector p : screenPoints) {
            drawPoint(p);
        }
    }

    public static void drawTri(Triangle3D tri3D) {
        Triangle screenTri = tri3D.toScreenTri(DrawMethods.cam);
        if (screenTri.equalsZero()) return;
        
        DrawMethods.drawTri(screenTri);
    }
    
    public static void drawMesh(Mesh mesh) {
        for (Triangle tri : mesh.getTris()) {
            DrawMethods.drawTri(tri);
        }
    }

    public static void drawMesh(Mesh3D mesh) {
        Mesh screenMesh = Mesh.from3DMesh(DrawMethods.cam, mesh);
        DrawMethods.drawMesh(screenMesh);
    }

    public static void drawMeshs(Mesh3D[] mesh3Ds) {
        for (Mesh3D m : mesh3Ds) {
            DrawMethods.drawMesh(m);
        }
    }

    public static void drawMesh3Ds(ArrayList<Mesh3D> mesh3Ds) {
        for (Mesh3D m : mesh3Ds) {
            DrawMethods.drawMesh(m);
        }
    }

    public static void drawPart(Part part) {
        DrawMethods.drawMesh(part.getMesh());
    }



    // light methods
    public static void drawTri(Triangle3D tri3D, Light light) {
        Triangle screenTri = tri3D.toScreenTri(DrawMethods.cam, light);
        if (screenTri.equalsZero()) return;
        
        screenTri.setColor(light.colorEffect(tri3D));
        DrawMethods.drawTri(screenTri);
    }
    
    public static void drawMesh(Mesh3D mesh, Light light) {
        Mesh screenMesh = Mesh.from3DMesh(DrawMethods.cam, mesh, light);
        DrawMethods.drawMesh(screenMesh);
    }
    
    public static void drawMeshs(Mesh3D[] mesh3Ds, Light light) {
        for (Mesh3D m : mesh3Ds) {
            DrawMethods.drawMesh(m, light);
        }
    }
    
    public static void drawMesh3Ds(ArrayList<Mesh3D> mesh3Ds, Light light) {
        for (Mesh3D m : mesh3Ds) {
            DrawMethods.drawMesh(m, light);
        }
    }
    
    public static void drawPart(Part part, Light light) {
        DrawMethods.drawMesh(part.getMesh(), light);
    }
}
