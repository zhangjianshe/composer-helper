package cn.cangling.docker.composer.client.composer.model;

public class Point {
   public double x;
   public double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(){
        this(0, 0);
    }
    public void offset(double x, double y) {
        this.x += x;
        this.y += y;
    }
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point clone() {
        return new Point(this.x, this.y);
    }
    public void copyFrom(Point point) {
        this.x = point.x;
        this.y = point.y;
    }
    public double distanceSq(Point other) {
         return (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
     }


    /**
     * Calculates the two base coordinates of an arrowhead given a line segment.
     * The arrowhead tip is at 'endPoint'.
     *
     * @param startPoint    The starting point of the line segment.
     * @param endPoint      The ending point of the line segment (arrowhead tip).
     * @param arrowLength   The length of the arrowhead (distance from tip to base).
     * @param arrowHalfWidth The half-width of the arrowhead at its base.
     * @return An array of two Point objects representing the base corners of the arrowhead.
     * Returns null if the line segment has zero length (start and end points are the same).
     */
    public static Point[] calculateArrowheadPoints(Point startPoint, Point endPoint,
                                                   double arrowLength, double arrowHalfWidth) {

        double x1 = startPoint.x;
        double y1 = startPoint.y;
        double x2 = endPoint.x;
        double y2 = endPoint.y;

        // 1. Calculate the vector of the segment (P1 to P2)
        double Vx = x2 - x1;
        double Vy = y2 - y1;

        // 2. Calculate the length of the segment vector
        double segmentLength = Math.sqrt(Vx * Vx + Vy * Vy);

        // Handle zero-length segment to avoid division by zero
        if (segmentLength == 0) {
            return null;
        }

        // 3. Normalize the segment vector
        double Ux = Vx / segmentLength;
        double Uy = Vy / segmentLength;

        // 4. Calculate a perpendicular unit vector (rotated 90 degrees clockwise)
        double U_perp_x = -Uy;
        double U_perp_y = Ux;

        // 5. Calculate the intermediate point for the base (back from P2 along the segment)
        double P_base_x = x2 - arrowLength * Ux;
        double P_base_y = y2 - arrowLength * Uy;

        // 6. Calculate the two base points of the arrowhead
        Point B1 = new Point(
                P_base_x + arrowHalfWidth * U_perp_x,
                P_base_y + arrowHalfWidth * U_perp_y
        );

        Point B2 = new Point(
                P_base_x - arrowHalfWidth * U_perp_x, // Subtract for the other side
                P_base_y - arrowHalfWidth * U_perp_y
        );

        return new Point[]{endPoint,B1, B2};
    }
}
