import java.util.ArrayList;

public class Mesh {
    
    // variables
    private Triangle[] tris;
    private Vector[] points;

    // contstructors
    public Mesh() {}

    public Mesh(Vector[] points) {
        this.points = points;

        ArrayList<Triangle> newTris = this.generateMeshFromPoints(points);
        this.tris = new Triangle[newTris.size()];
        
        // keeps the references
        for (int t=0; t<this.tris.length; t++) {
            this.tris[t] = newTris.get(t);
        }
    }

    public Mesh(Triangle[] tris) {
        this.tris = tris;
    }

    public Mesh(Vector start, Vector end) {
        this.generateUnitMesh(start, end, 10, 10);
    }

    public Mesh(int width, int height) {
        this.generateUnitMesh(new Vector(0,0), new Vector(1,1), width, height);
    }

    public Mesh(int width, int height, Vector start, Vector end) {
        this.generateUnitMesh(start, end, width, height);
    }

    // getters / setters
    public Triangle[] getTris() {
        return this.tris;
    }

    public void setTris(Triangle[] tris) {
        this.tris = tris;
    }

    public Vector getTriPoint(int index, int num) {
        return this.tris[index].getPoint(num);
    }

    public Vector[] getTrPoints(int index) {
        return this.tris[index].getPoints();
    }

    public void setTriPoint(Vector p, int pointNum, int index) {
        if (index > this.tris.length) {return;}
        this.tris[index].setPoint(p, pointNum);
    }

    public void moveTriPoint(Vector p, int pointNum, int index) {
        if (index > this.tris.length) {return;}
        this.tris[index].movePoint(p, pointNum);
    }

    public void setTriPoints(Vector p1, Vector p2, Vector p3, int index) {
        if (index > this.tris.length) {return;}
        this.tris[index].setPoints(p1, p2, p3);
    }

    public void moveTriPoints(Vector p1, Vector p2, Vector p3, int index) {
        if (index > this.tris.length) {return;}
        this.tris[index].movePoints(p1, p2, p3);
    }

    public Vector[] getPoints() {
        return this.points;
    }

    public void setPoints(Vector[] points) {
        this.points = points;
    }

    public void setColor(int[] color) {
        for (Triangle tri : this.tris) {
            tri.setColor(color);
        }
    }

    public void setColor(int n, int[] color) {
        this.tris[n].setColor(color);
    }
    
    // methods
    public static Vector getLowestPoint(Vector[] points) {
        if (points.length == 0) {
            System.out.println("'points' array must have at least 1 value");
            return new Vector();
        }
        
        Vector lowest = points[0];
        for (Vector p : points) {
            if (p.getY() < lowest.getY()) {
                lowest = p;
            }
        }

        return lowest;
    }

    public int[] findTrisWithPoint(Vector p) {
        ArrayList<Integer> indexArrayList = new ArrayList<Integer>();

        for (int t=0; t<this.tris.length; t++) {
            if (this.tris[t].containsPoint(p) != -1) {
                indexArrayList.add(t);
            }
        }
        
        return indexArrayList.stream().mapToInt(i->i).toArray();
    }

    // generating methods
    public Vector[] generateConvexHull(Vector[] points) {
        ArrayList<Vector> convexStack = new ArrayList<Vector>();

        // Step 1: get the lowest point
        Vector lowestPoint = Mesh.getLowestPoint(points);
        
        double[] thetas = new double[points.length];
        for (int p=0; p<points.length; p++) {
            if (lowestPoint.equals(points[p])) {thetas[p] = 0.0; continue;}
            thetas[p] = new Vector(1, 0).getThetaWith( points[p].minus(lowestPoint) );
        }
        
        // Step 2: organize the points by their angles from (1, 0)
        Vector[] organizedByTheta = ArrayMethods.organizePoints(points, thetas);

        // Step 3: Add and remove points from a stack to form the final array
        convexStack.add(lowestPoint);
        convexStack.add(organizedByTheta[1]);

        Vector stackVect;
        Vector nextPointVect;

        for (int p=2; p<points.length; p++) {
            stackVect = convexStack.getLast().minus( convexStack.get(convexStack.size()-2) );
            nextPointVect = organizedByTheta[p].minus(convexStack.getLast());

            while (stackVect.isCCWwith(nextPointVect) == -1) {
                convexStack.removeLast();
                stackVect = convexStack.getLast().minus(convexStack.get(convexStack.size()-2));
                nextPointVect = organizedByTheta[p].minus(convexStack.getLast());
            }
            
            convexStack.add(organizedByTheta[p]);
        }

        // we have to do this the long way to keep the references
        Vector[] convexArray = new Vector[convexStack.size()];
        for (int p=0; p<convexArray.length; p++) {
            convexArray[p] = convexStack.get(p);
        }

        return convexArray;
    }

    public ArrayList<Triangle> generateMeshFromPoints(Vector[] points) {
        ArrayList<Triangle> newTris = new ArrayList<Triangle>();
        
        // not enough points
        if (points.length < 3) {
            System.out.println("Not enough points, generateMeshFromPoints() must have at least 3 given.");
            return newTris;
        }

        // only 1 tri can be made
        if (points.length == 3) {
            newTris.add( new Triangle(
                points[0],
                points[1],
                points[2]
            ));
            
            return newTris;
        }
        

        // genereate convex hull and remove values from points array
        Vector[] convexHullPoints = this.generateConvexHull(points);
        
        ArrayList<Vector> hullArrayList = new ArrayList<Vector>();
        ArrayList<Vector> remainingPoints = new ArrayList<Vector>();

        // turn convexHullPoints into hullArrayList and keep references
        for (int h=0; h<convexHullPoints.length; h++) {
            hullArrayList.add(convexHullPoints[h]);
        }

        // turn points into remainingPoints and keep references
        for (int r=0; r<points.length; r++) {
            remainingPoints.add(points[r]);
        }
        
        // remove the hull's points from remainingPoints
        remainingPoints.removeAll(hullArrayList);


        // needed variables for between points
        Vector endPoint;
        Vector lineVect;
        Vector prevConnected = new Vector();
        Vector firstConnected = new Vector();
        
        ArrayList<Vector> closeConnections = new ArrayList<Vector>();

        // while there's still points to connect to outside of the convex hull
        while (remainingPoints.size() > 0) {
            for (int h=0; h<hullArrayList.size(); h++) {
                endPoint = hullArrayList.get(h);

                if (h == hullArrayList.size()-1) {
                    // the last point is connected to the first
                    lineVect = hullArrayList.get(0).minus( hullArrayList.get(h) );
                }else {
                    lineVect = hullArrayList.get(h+1).minus( hullArrayList.get(h) );
                }

                if (remainingPoints.size() == 0) break;

                // find the nearest
                Vector nearest = remainingPoints.getFirst();
                double nearLength = nearest.distanceFromLine(endPoint, lineVect);

                // run through the remaining points to find the closes one to the current line on the hull
                for (int r=1; r<remainingPoints.size(); r++) {
                    if (remainingPoints.get(r).distanceFromLine(endPoint, lineVect) >= nearLength) {continue;}
                    nearest = remainingPoints.get(r);

                    // set nearLength to the new length
                    nearLength = remainingPoints.get(r).distanceFromLine(endPoint, lineVect);
                }

                // add the connected to the next "convex hull" (if it isn't already)
                if (h == 0) {
                    closeConnections.add(nearest);
                } else {
                    if ( !(closeConnections.getLast().equals(nearest)) ) {
                        closeConnections.add(nearest);
                    }
                }

                if (h == hullArrayList.size()-1) {
                    // the last point is connected to the first
                    newTris.add(new Triangle(hullArrayList.get(h), hullArrayList.get(0), nearest));

                    // the last point in step 2 is connected to the firstConnected and the first 
                    if (firstConnected == nearest) continue;
                    newTris.add(new Triangle(hullArrayList.get(0), firstConnected, nearest));

                    // check for and gather points within the triangle
                    ArrayList<Vector> recurseArrayList = new ArrayList<Vector>();
                
                    for (Vector remain : remainingPoints) {
                        if (remain.isWithin( newTris.getLast() ) && newTris.getLast().containsPoint(remain) == -1) {
                            recurseArrayList.add(remain);
                        }
                    }

                    // add the points on the triangle
                    recurseArrayList.add(newTris.getLast().getPoint(1));
                    recurseArrayList.add(newTris.getLast().getPoint(2));
                    recurseArrayList.add(newTris.getLast().getPoint(3));

                    // if there is points within the triangle (except for the points on the triangle)
                    if (recurseArrayList.size() > 3) {
                        // remove the last triangle for the new triangles within
                        newTris.removeLast();

                        Vector[] recursePoints = new Vector[recurseArrayList.size()];
                        
                        for (int r=0; r<recursePoints.length; r++) {
                            recursePoints[r] = recurseArrayList.get(r);

                            // eliminate these points from the remaining point list
                            if (closeConnections.indexOf(recurseArrayList.get(r)) != -1) continue;

                            remainingPoints.remove(recurseArrayList.get(r));
                        }

                        // use recursion to connect those points together in a mini mesh
                        ArrayList<Triangle> miniMesh = this.generateMeshFromPoints(recursePoints);

                        // add the mini mesh to the top mesh
                        newTris.addAll(miniMesh);
                    }
                }else {

                    // add new triangle as step 1
                    newTris.add(new Triangle(hullArrayList.get(h), hullArrayList.get(h+1), nearest));
                }
                
                // set up the first connected and the first previously connected point
                if (h == 0) {
                    firstConnected = nearest;
                    prevConnected = nearest;
                    continue;
                }
                
                // dont connect a triangle that would only have 2 points
                if (prevConnected != nearest) {

                    // add new triangle as step 2
                    newTris.add(new Triangle(hullArrayList.get(h), prevConnected, nearest));

                    // check for and gather points within the triangle
                    ArrayList<Vector> recurseArrayList = new ArrayList<Vector>();
                    
                    for (Vector remain : remainingPoints) {
                        if (remain.isWithin( newTris.getLast() ) && newTris.getLast().containsPoint(remain) == -1) {
                            recurseArrayList.add(remain);
                        }
                    }

                    // add the points on the triangle
                    recurseArrayList.add(newTris.getLast().getPoint(1));
                    recurseArrayList.add(newTris.getLast().getPoint(2));
                    recurseArrayList.add(newTris.getLast().getPoint(3));

                    // if there is points within the triangle (except for the points of the triangle)
                    if (recurseArrayList.size() > 3) {
                        // remove the last triangle for the new triangles within
                        newTris.removeLast();

                        Vector[] recursePoints = new Vector[recurseArrayList.size()];
                        
                        for (int r=0; r<recursePoints.length; r++) {
                            recursePoints[r] = recurseArrayList.get(r);
                            
                            // eliminate these points from the remaining point list
                            if (closeConnections.indexOf(recurseArrayList.get(r)) != -1) continue;

                            remainingPoints.remove(recurseArrayList.get(r));
                        }

                        // use recursion to connect those points together in a mini mesh
                        ArrayList<Triangle> miniMesh = this.generateMeshFromPoints(recursePoints);

                        // add the mini mesh to the top mesh
                        newTris.addAll(miniMesh);
                    }
                }

                // set up the previously connected point
                prevConnected = nearest;
            }

            // set up the next round
            hullArrayList = closeConnections;
            
            // remove the connected points from potential connections
            remainingPoints.removeAll(hullArrayList);

            // reset the close connections arraylist
            closeConnections = new ArrayList<Vector>();
        }

        // when there isn't any more points in the middle, connect the hull points together (final step)

        // only 1 triangle can be made
        if (hullArrayList.size() == 3) {
            newTris.add(new Triangle(
                hullArrayList.get(0),
                hullArrayList.get(1),
                hullArrayList.get(2)
            ));
        }

        return newTris;
    }

    public void generateUnitMesh(Vector start, Vector end, int width, int height) {

        if (width == 1 && height == 1) {
            this.points = new Vector[]{
                start,
                new Vector(start.getX(), end.getY()),
                new Vector(end.getX(), start.getY()),
                end
            };

            this.tris = new Triangle[]{
                new Triangle(
                    this.points[3], // top right
                    this.points[2], // top left
                    this.points[0]// bottom left
                ),

                new Triangle(
                    this.points[0], // bottom left
                    this.points[1], // bottom right
                    this.points[3]// top right
                )
            };
            
            return;
        }

        // local variables
        this.points = new Vector[(width+1) * (height+1)];
        this.tris = new Triangle[2 * width*height];
        int tri_index = 0;

        double x;
        double x1;
        double y;
        double y1;

        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {

                // assumes we're in 0 to 1 scale
                // generate the array as it needs to

                // bottom left
                if (this.points[i + (width+1)*j] == null) {
                    x = Lerp.doubles(start.getX(), end.getX(),  i / ( (double) width ) );
                    y = Lerp.doubles(start.getY(), end.getY(),  j / ( (double) height ) );

                    this.points[i + (width+1)*j] = new Vector(x, y);
                }

                // bottomRight
                if (this.points[i+1 + (width+1)*j] == null) {
                    x1 = Lerp.doubles(start.getX(), end.getX(),  (i+1) / ( (double) width ) );
                    y = Lerp.doubles(start.getY(), end.getY(),  j / ( (double) height ) );

                    this.points[i+1 + (width+1)*j] = new Vector(x1, y);
                }

                // top left
                if (this.points[i + (width+1)*(j+1)] == null) {
                    x = Lerp.doubles(start.getX(), end.getX(),  i / ( (double) width ) );
                    y1 = Lerp.doubles(start.getY(), end.getY(),  (j+1) / ( (double) height ) );

                    this.points[i + (width+1)*(j+1)] = new Vector(x, y1);
                }

                // top right
                if (this.points[i+1 + (width+1)*(j+1)] == null) {
                    x1 = Lerp.doubles(start.getX(), end.getX(),  (i+1) / ( (double) width ) );
                    y1 = Lerp.doubles(start.getY(), end.getY(),  (j+1) / ( (double) height ) );

                    this.points[i+1 + (width+1)*(j+1)] = new Vector(x1, y1);
                }                
                
                // generate a quad at a time
                tris[tri_index] = new Triangle(
                    this.points[i+1 + (width+1)*(j+1)], // top right
                    this.points[i + (width+1)*(j+1)], // top left
                    this.points[i + (width+1)*j]// bottom left
                );

                tris[tri_index + 1] = new Triangle(
                    this.points[i + (width+1)*j], // bottom left
                    this.points[i+1 + (width+1)*j], // bottom right
                    this.points[i+1 + (width+1)*(j+1)]// top right
                );

                tri_index += 2;
            }
        }
    }

    public void gradient(Vector p1, Vector p2, int[] color1, int[] color2) {
        for (int t = 0; t<this.tris.length; t++) {

            double percent = Lerp.getPercent(p1, p2, this.tris[t].getCenter());
            int[] gradientColor = Lerp.colors(color1, color2, percent);
            this.tris[t].setColor(gradientColor);
        }
    }

    public void randColors() {
        for (Triangle tri : this.tris) {
            int r = (int) (Math.random() * 225 + 1);
            int g = (int) (Math.random() * 225 + 1);
            int b = (int) (Math.random() * 225 + 1);

            tri.setColor(new int[]{r, g, b});
        }
    }

    // drawing methods
    public void drawConvexHull() {
        
        Vector[] hull = this.generateConvexHull(this.points);

        double[] x = new double[hull.length];
        double[] y = new double[hull.length];

        for (int h=0; h<hull.length; h++) {
            x[h] = hull[h].getX();
            y[h] = hull[h].getY();
        }
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.polygon(x, y);

        StdDraw.setPenColor(StdDraw.BLACK);
        this.drawPoints();
    }

    public void drawPoints() {
        for (Vector p : this.points) {
            StdDraw.circle(p.getX(), p.getY(), 0.005);
        }
    }

    public static Mesh from3DMesh(Camera cam, Mesh3D mesh) {
        Mesh screenMesh = new Mesh();
        screenMesh.setPoints( ArrayMethods.convertToScreenPoints(cam, mesh.getPoints()) );
        screenMesh.setTris( ArrayMethods.convertToScreenTris(cam, mesh.getTris()) );
        return screenMesh;
    }

    public static Mesh from3DMesh(Camera cam, Mesh3D mesh, Light light) {
        Mesh screenMesh = new Mesh();
        screenMesh.setPoints( ArrayMethods.convertToScreenPoints(cam, mesh.getPoints()) );
        screenMesh.setTris( ArrayMethods.convertToScreenTris(cam, mesh.getTris(), light) );
        return screenMesh;
    }
}
