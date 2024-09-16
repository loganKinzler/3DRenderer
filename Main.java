public class Main {
    public static void main(String[] args) {
        DrawMethods.setUp();
        DrawMethods.setCanvasSize(new Vector(500, 500));
        // DrawMethods.setWireFrame();

        Light dLight = new DirectionalLight(new int[] {
            (int) (Math.random()*255),
            (int) (Math.random()*255),
            (int) (Math.random()*255),
        }, true);

        Camera cam = new Camera();
        cam.setFOV(Math.toRadians(70));
        DrawMethods.setCamera(cam);

        cam.setAngles(Math.toRadians(0), Math.toRadians(0));
        cam.setPosition(new Vector3D(0, 0, 0));

        int res = 25;
        int frameNum = 0;

        //TEST 1: CUBE
        // Cube part = new Cube(new Vector3D(0, 0, 3), new Vector3D(2, 2, 2), res);
        // part.setColor(new int[]{200, 100, 0});
        
        // TEST 2: CONE
        // Cone part = new Cone(new Vector3D(0, 0, 3), new Vector3D(2, 2, 2), res);
        // part.setColor(new int[]{40, 210, 72});

        // TEST 3: CYLINDER
        Cylinder part = new Cylinder(new Vector3D(0, 0, 10), new Vector3D(2, 2, 2), res);
        part.setColor(new int[]{175, 175, 175});


        // TEST 4: HELP ME
        // Triangle3D tri = new Triangle3D(new Vector3D(0,0,5), new Vector3D(0,0,5), new Vector3D(3,0,5));
        // tri.setColor(new int[]{100, 20, 90});
        // DrawMethods.drawTri(tri);
        // StdDraw.show();
        // StdDraw.pause(2000);

        while (true) {
            
            // framewise functions
            frameNum++;
            Vector3D movement = Input.getMovement(cam.getForwardVect());


            // test 1
            // part.rotateTo(new Vector3D(0, Math.toRadians(j), 0));
            // part.moveTo(new Point3D(0, 2*Math.sin(Math.toRadians(j)), 2));
            
            // test 2
            part.rotateTo(new Vector3D(Math.toRadians(frameNum % 360), -Math.toRadians(frameNum), Math.toRadians(frameNum % 360)));
            part.moveTo(new Vector3D(Math.cos(Math.toRadians(2*(frameNum % 360))), 1.5*Math.sin(Math.toRadians(frameNum % 360)), 3+0.5*Math.sin(Math.toRadians(frameNum % 360))));


            // Draw the screen
            StdDraw.clear();
            DrawMethods.drawPart(part, dLight);
            StdDraw.show();
            StdDraw.pause(35);
        }
    }
}
