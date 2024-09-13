public class Part {

    // variables
    protected Mesh3D partMesh;
    protected Vector3D[] vector3Ds;
    
    protected Vector3D position;
    protected Vector3D size;
    protected Vector3D rotation;// uses XZY rotation

    protected int resolution;
    protected int[] color;

    // methods
    public Mesh3D getMesh() {
        return this.partMesh;
    }

    public Vector3D[] getPoints() {
        return this.vector3Ds;
    }

    public void setColor(int[] newColor) {
        this.color = newColor;

        for (Triangle3D tri3D : partMesh.getTris()) {
            tri3D.setColor(newColor);
        }
    }

    public void moveTowards(Vector3D pos) {
        for (int p=0; p<this.vector3Ds.length; p++) {
            this.vector3Ds[p].setPos(
                pos.getX() + this.vector3Ds[p].getX(),
                pos.getY() + this.vector3Ds[p].getY(),
                pos.getZ() + this.vector3Ds[p].getZ()
            );
        }

        this.position.setPos(
            this.position.getX() + pos.getX(),
            this.position.getY() + pos.getY(),
            this.position.getZ() + pos.getZ()
        );
    }

    public void moveTo(Vector3D pos) {
        this.moveTowards(pos.minus(this.position));
    }

    public void rotateTowards(Vector3D rot) {
        for (int i=0; i<this.vector3Ds.length; i++) {
            this.rotateX(i, rot.getX());
            this.rotateZ(i, rot.getZ());
            this.rotateY(i, rot.getY());
        }
    
        this.rotation.setPos(
            this.rotation.getX() + rot.getX(),
            this.rotation.getY() + rot.getY(),
            this.rotation.getZ() + rot.getZ()
        );
    }

    public void rotateTo(Vector3D rot) {
        this.rotateTowards(rot.minus(this.rotation));
    }

    private void rotateX(int n, double theta) {
        Vector3D delta = this.vector3Ds[n].minus(this.position);
        double xRot = Math.atan2(delta.getY(), delta.getZ());
        double radius = Math.sqrt(Math.pow(delta.getZ(), 2) + Math.pow(delta.getY(), 2));

        delta.setPos(
            delta.getX(),
            radius*Math.sin(xRot + theta),
            radius*Math.cos(xRot + theta)
        );
        
        delta = delta.add(this.position);
        this.vector3Ds[n].setPos(delta.getX(), delta.getY(), delta.getZ());
    }

    private void rotateY(int n, double theta) {
        Vector3D delta = this.vector3Ds[n].minus(this.position);
        double yRot = Math.atan2(-delta.getX(), delta.getZ());
        double radius = Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getZ(), 2));

        delta.setPos(
            -radius*Math.sin(yRot + theta),
            delta.getY(),
            radius*Math.cos(yRot + theta)
        );

        delta = delta.add(this.position);
        this.vector3Ds[n].setPos(delta.getX(), delta.getY(), delta.getZ());
    }

    private void rotateZ(int n, double theta) {
        Vector3D delta = this.vector3Ds[n].minus(this.position);
        double zRot = Math.atan2(delta.getY(), delta.getX());
        double radius = Math.sqrt(Math.pow(delta.getX(), 2) + Math.pow(delta.getY(), 2));

        delta.setPos(
            radius*Math.cos(zRot + theta),
            radius*Math.sin(zRot + theta),
            delta.getZ()
        );
        
        delta = delta.add(this.position);
        this.vector3Ds[n].setPos(delta.getX(), delta.getY(), delta.getZ());
    }

    protected void generatePart(Vector3D pos, Vector3D size, Vector3D rot, int res) {}
    
    public void resize(Vector3D size) {
        this.generatePart(this.position, size, this.rotation, this.resolution);
    }
}
