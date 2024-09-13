public class Triangle {
    
    // variables
    private Vector point1;
    private Vector point2;
    private Vector point3;

    protected int[] color;

    // constructors
    public Triangle() {
        this.point1 = new Vector();
        this.point2 = new Vector();
        this.point3 = new Vector();

        this.color = new int[3];
        this.color[0] = 255;
        this.color[1] = 255;
        this.color[2] = 255;
    }

    public Triangle(Vector point1, Vector point2, Vector point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.color = new int[3];
        this.color[0] = 255;
        this.color[1] = 255;
        this.color[2] = 255;
    }

    public Triangle(int r, int g, int b) {
        this.point1 = new Vector();
        this.point2 = new Vector();
        this.point3 = new Vector();

        this.color = new int[3];
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
    }

    public Triangle(Vector point1, Vector point2, Vector point3, int r, int g, int b) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;

        this.color = new int[3];
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;  
    }

    // getters / setters
    public int[] getColor() {return this.color;}
    public void setColor(int r, int g, int b) {
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
    }
    
    public void setColor(int[] color) {
        this.color[0] = color[0];
        this.color[1] = color[1];
        this.color[2] = color[2];
    }
    
    public Vector getCenter() {
        double midX = ( this.point1.getX() + this.point2.getX() + this.point3.getX() ) / 3.0;
        double midY = ( this.point1.getY() + this.point2.getY() + this.point3.getY() ) / 3.0;
        return new Vector(midX, midY);
    }

    public Vector[] getPoints() {return new Vector[]{point1, point2, point3};}
    public void setPoints(Vector point1, Vector point2, Vector point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public void setPoint(Vector p, int pointNum) {
        if (pointNum == 1) {
            point1.setPos(p.getX(), p.getY());
            return;
        }
        if (pointNum == 2) {
            point2.setPos(p.getX(), p.getY());
            return;
        }
        if (pointNum == 3) {
            point3.setPos(p.getX(), p.getY());
            return;
        }
    }

    public void movePoint(Vector p, int pointNum) {
        if (pointNum == 1) {
            point1.setPos(point1.getX() + p.getX(), point1.getX() + p.getX());
            return;
        }
        if (pointNum == 2) {
            point2.setPos(point2.getX() + p.getX(), point2.getX() + p.getX());
            return;
        }
        if (pointNum == 3) {
            point3.setPos(point3.getX() + p.getX(), point3.getX() + p.getX());
            return;
        }
    }

    public void movePoints(Vector p1, Vector p2, Vector p3) {
            p1.setPos(p1.getX() + point1.getX(), p1.getY() + point1.getY());
            p2.setPos(p2.getX() + point2.getX(), p2.getY() + point2.getY());
            p3.setPos(p3.getX() + point3.getX(), p3.getY() + point3.getY());
    }

    public Vector getPoint(int pointNum) {
        if (pointNum == 1) {return point1;}
        if (pointNum == 2) {return point2;}
        if (pointNum == 3) {return point3;}

        System.out.println("getPoint() only accepts inputs of 1, 2, or 3");
        return new Vector();
    }


    public int containsPoint(Vector p) {
        if (this.point1.equals(p)) {return 1;}
        if (this.point2.equals(p)) {return 2;}
        if (this.point3.equals(p)) {return 3;}
        return -1;
    }

    public boolean equalsZero() {
        return this.point1.equalsZero() && this.point2.equalsZero() && this.point3.equalsZero();
    }
}
