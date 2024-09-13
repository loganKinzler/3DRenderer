import java.util.ArrayList;

public class ArrayMethods {

    // print differnt array types
    public static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void printArray(double[] array) {
        for (double d : array) {
            System.out.print(d + " ");
        }
        System.out.println();
    }

    public static void printArray(Vector[] array) {
        for (Vector p : array) {
            if (p == null) {continue;}
            System.out.print(p.toString());
        }
        System.out.println();
    }

    public static void printArray(Vector3D[] array) {
        for (Vector3D p : array) {
            if (p == null) {continue;}
            System.out.print(p.toString());
        }
        System.out.println();
    }
    
    public static double[] section(double[] array, int begin, int end) {
        if (begin >= end) {
            System.out.println("Within ArrayMenthods.section(), begin must be lesser than end");
            return new double[0];
        }
        
        int len = end-begin;
        double[] arraySection = new double[len];

        for (int i=begin; i<end; i++) {
            arraySection[i-begin] = array[i];
        }

        return arraySection;
    }

    // regular merge sort
    public static double[] mergeSort(double[] array) {
        // base case
        if (array.length == 1) {
            return array;
        }
        
        int mid = array.length / 2;
        double[] firstHalf = mergeSort(ArrayMethods.section(array, 0, mid));
        double[] lastHalf = mergeSort(ArrayMethods.section(array, mid, array.length));
        
        return ArrayMethods.merge(firstHalf, lastHalf);
    }

    // regular merge
    public static double[] merge(double[] array1, double[] array2) {
        double[] merged = new double[array1.length + array2.length];

        int index1 = 0;
        int index2 = 0;

        while (index1 < array1.length && index2 < array2.length) {
            if (array1[index1] < array2[index2]) {
                merged[index1 + index2] = array1[index1];
                index1++;
                continue;
            }

            if (array2[index2] < array1[index1]) {
                merged[index1 + index2] = array2[index2];
                index2++;
                continue;
            }

            // default to entering array1's value if equal
            merged[index1 + index2] = array1[index1];
            index1++;
        }

        while (index1 < array1.length) {
            merged[index1 + index2] = array1[index1];
            index1++;
        }
        
        while (index2 < array2.length) {
            merged[index1 + index2] = array2[index2];
            index2++;
        }

        return merged;
    }

    // reference merge sort
    public static void referenceMergeSort(double[] array, int begin, int end) {
        // base case
        if (end - begin <= 1) {return;}

        int mid = begin + (end - begin)/2;
        referenceMergeSort(array, begin, mid);
        referenceMergeSort(array, mid, end);

        ArrayMethods.referenceMerge(array, begin, end);
    }

    // reference merge
    public static void referenceMerge(double[] array, int begin, int end) {
        int mid = begin + (end - begin)/2;
        int index1 = 0;
        int index2 = 0;

        double[] merged = new double[end - begin];

        while (index1 < mid - begin && index2 < end - mid) {
            if (array[index1 + begin] < array[index2 + mid]) {
                merged[index1 + index2] = array[index1 + begin];
                index1++;
                continue;
            }

            if (array[index2 + mid] < array[index1 + begin]) {
                merged[index1 + index2] = array[index2 + mid];
                index2++;
                continue;
            }

            // default to entering array1's value if equal
            merged[index1 + index2] = array[index1 + begin];
            index1++;
        }

        while (index1 < mid-begin) {
            merged[index1 + index2] = array[index1 + begin];
            index1++;
        }

        while (index2 < end-mid) {
            merged[index1 + index2] = array[index2 + mid];
            index2++;
        }

        // set the values
        for (int m=0; m<merged.length; m++) {
            array[begin+m] = merged[m];
        }
    }

    // linked merge sort
    public static void linkedMergeSort(double[] array, double[] linkedArray, int begin, int end) {
        if (array.length != linkedArray.length) {
            System.out.println("Linked Merge Sort's array and linkedArray must be of equal size.");
            return;
        }

        // base case
        if (end - begin <= 1) {return;}

        int mid = begin + (end - begin)/2;
        linkedMergeSort(array, linkedArray, begin, mid);
        linkedMergeSort(array, linkedArray, mid, end);

        ArrayMethods.linkedMerge(array, linkedArray, begin, end);
    }

    // linked merge
    public static void linkedMerge(double[] array, double[] linkedArray, int begin, int end) {
        int mid = begin + (end - begin)/2;
        int index1 = 0;
        int index2 = 0;

        double[] merged = new double[end - begin];
        double[] linkedMerge = new double[end - begin];

        while (index1 < mid - begin && index2 < end - mid) {
            if (array[index1 + begin] < array[index2 + mid]) {
                merged[index1 + index2] = array[index1 + begin];
                linkedMerge[index1 + index2] = linkedArray[index1 + begin];

                index1++;
                continue;
            }

            if (array[index2 + mid] < array[index1 + begin]) {
                merged[index1 + index2] = array[index2 + mid];
                linkedMerge[index1 + index2] = linkedArray[index2 + mid];

                index2++;
                continue;
            }

            // default to entering array1's value if equal
            merged[index1 + index2] = array[index1 + begin];
            linkedMerge[index1 + index2] = linkedArray[index1 + begin];
            index1++;
        }

        while (index1 < mid-begin) {
            merged[index1 + index2] = array[index1 + begin];
            linkedMerge[index1 + index2] = linkedArray[index1 + begin];
            index1++;
        }

        while (index2 < end-mid) {
            merged[index1 + index2] = array[index2 + mid];
            linkedMerge[index1 + index2] = linkedArray[index2 + mid];
            index2++;
        }

        // set the values
        for (int m=0; m<merged.length; m++) {
            array[begin+m] = merged[m];
            linkedArray[begin+m] = linkedMerge[m];
        }
    }

    public static Vector[] organizePoints(Vector[] points, double[] pointEvals) {
        
        // generate indexChanges arary
        double[] indexChanges = new double[points.length];
        for (int i=0; i<points.length; i++) {indexChanges[i] = i;}

        // organize based off evals
        ArrayMethods.linkedMergeSort(pointEvals, indexChanges, 0, points.length);
        
        // put the oranized points in another array
        Vector[] pointsReturn = new Vector[points.length];
        for (int i=0; i<points.length; i++) {
            pointsReturn[i] = points[(int) indexChanges[i]];
        }

        // return the sorted point array
        return pointsReturn;
    }

    public static Vector[] convertToScreenPoints(Camera cam, Vector3D[] points) {
        Vector[] screenPoints = new Vector[points.length];

        for (int i=0; i<points.length; i++) {
            screenPoints[i] = ArrayMethods.convertToScreenPoint(cam, points[i]);
        }

        return screenPoints;
    }    
    
    public static ArrayList<Vector> convertToScreenPoints(Camera cam, ArrayList<Vector3D> points) {
        ArrayList<Vector> screenPoints = new ArrayList<Vector>();
        
        for (Vector3D p : points) {
            screenPoints.add(ArrayMethods.convertToScreenPoint(cam, p));
        }
        
        return screenPoints;
    }

    public static Vector convertToScreenPoint(Camera cam, Vector3D point) {
        SphericalPoint sphereDiff = SphericalPoint.fromCartesian( point.minus(cam.getPosition()) );

        double deltaY = sphereDiff.getAngleY() - cam.getYRotation();
        double deltaX = sphereDiff.getAngleX() - cam.getXRotation();

        deltaY = deltaY % Math.PI - (Math.TAU - cam.getFOV()) * Math.signum(deltaY) * Math.floor( Math.abs( deltaY / Math.PI ) );
        deltaX = deltaX % Math.PI - (Math.TAU - cam.getFOV()) * Math.signum(deltaX) * Math.floor( Math.abs( deltaX / Math.PI ) );

        return new Vector( -deltaY / cam.getFOV(), deltaX / cam.getFOV() );
    }

    public static Triangle[] convertToScreenTris(Camera cam, Triangle3D[] tri3Ds) {
        ArrayList<Triangle> triList = new ArrayList<Triangle>();
        Vector[] points;

        for (int i=0; i<tri3Ds.length; i++) {

            // if it isn't facing the camera, don't draw it
            if ( !(tri3Ds[i].facingCamera(cam)) ) continue;

            triList.add(new Triangle());

            points = ArrayMethods.convertToScreenPoints(cam, tri3Ds[i].getPoints());
            triList.getLast().setPoints(points[0], points[1], points[2]);
            triList.getLast().setColor(tri3Ds[i].getColor());
        }

        Triangle[] tris = new Triangle[triList.size()];
        tris = triList.toArray(tris);
        return tris;
    }

    public static Triangle[] convertToScreenTris(Camera cam, Triangle3D[] tri3Ds, Light light) {
        ArrayList<Triangle> triList = new ArrayList<Triangle>();
        Vector[] points;

        for (int i=0; i<tri3Ds.length; i++) {

            // if it isn't facing the camera, don't draw it
            if ( !(tri3Ds[i].facingCamera(cam)) ) continue;

            triList.add(new Triangle());

            points = ArrayMethods.convertToScreenPoints(cam, tri3Ds[i].getPoints());
            triList.getLast().setPoints(points[0], points[1], points[2]);
            triList.getLast().setColor(light.colorEffect(tri3Ds[i]));
        }

        Triangle[] tris = new Triangle[triList.size()];
        tris = triList.toArray(tris);
        return tris;
    }
}
