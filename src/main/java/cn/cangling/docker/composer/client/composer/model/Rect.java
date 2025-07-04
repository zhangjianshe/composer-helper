package cn.cangling.docker.composer.client.composer.model;



public class Rect {
    public double x;
    public double y;
    public double width;
    public double height;
    public Rect(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Rect(){
        this(0, 0, 100, 100);
    }
    public void set(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void offset(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public boolean contains(double x, double y) {
        return this.x <= x && this.y <= y && (this.x+this.width) >= x && (this.y+this.height) >= y;
    }

    public Point center() {
        return new Point(this.x+this.width/2, this.y+this.height/2);
    }
    public void copyFrom(Rect rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }
    public Rect copyTo() {
        Rect rect = new Rect();
        rect.copyFrom(this);
        return rect;
    }

    public void expand(int dx, int dy) {
        this.x -= dx;
        this.y -= dy;
        this.width += dx*2;
        this.height += dy*2;
    }

    public void shrink(int top, int right, int bottom, int left) {
        this.x+=left;
        this.y+=top;
        this.width+=right-left;
        this.height+=bottom-top;
    }

    // gemini generator this code
    public boolean intersect(Rect rect) {
        // Check if the rectangles overlap on the X-axis
        boolean xOverlap = Math.max(this.x, rect.x) < Math.min(this.x + this.width, rect.x + rect.width);
        // Check if the rectangles overlap on the Y-axis
        boolean yOverlap = Math.max(this.y, rect.y) < Math.min(this.y + this.height, rect.y + rect.height);
        return xOverlap && yOverlap;
    }

    /**
     * 查找线段的相交点
     * Finds the intersection point of a line segment (p1 to p2) and another line segment (p3 to p4).
     * Returns null if no intersection or if lines are parallel/collinear and non-overlapping.
     * Otherwise, returns the intersection point.
     */
    public static Point lineLineIntersection(Point p1, Point p2, Point p3, Point p4) {
        double x1 = p1.x;
        double y1 = p1.y;
        double x2 = p2.x;
        double y2 = p2.y;

        double x3 = p3.x;
        double y3 = p3.y;
        double x4 = p4.x;
        double y4 = p4.y;

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        // Lines are parallel or collinear
        if (denominator == 0) {
            // Further checks can be done here for collinear overlap,
            // but for simple line-rectangle intersection, we typically
            // assume no intersection if parallel and not identical.
            return null;
        }

        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        // Check if the intersection point lies on both line segments
        if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
            double intersectX = x1 + t * (x2 - x1);
            double intersectY = y1 + t * (y2 - y1);
            return new Point(intersectX, intersectY);
        }

        return null; // No intersection within the segments
    }

    /**
     * 计算从 point 到center的连线与矩形相交点
     * @param point
     * @return
     */
    public Point checkIntersectionPointToCenter(Point point)
    {
        Point center = center();

        // If the starting point is inside the rectangle, and we're looking for an intersection
        // with the *boundary* from a point *to* the center, it's a bit ambiguous.
        // If the point is inside and the line goes to the center, it doesn't intersect the boundary.
        // You might define this case differently based on your needs (e.g., return the point itself).
        // For this method, we assume we're looking for an intersection *on the boundary*.
        if (contains(point.x, point.y)) {
            // The line starts inside. We might need to extend the line *from* the center *out*
            // to find an intersection if the goal is to find where a ray from the center
            // passes through the point and hits the boundary.
            // For a line *to* the center, if the start point is inside, there's no boundary intersection.
            return null;
        }

        Point closestIntersection = null;
        double minDistanceSq = Double.MAX_VALUE;

        // Define rectangle corners
        Point topLeft = new Point(x, y);
        Point topRight = new Point(x + width, y);
        Point bottomLeft = new Point(x, y + height);
        Point bottomRight = new Point(x + width, y + height);

        // Sides of the rectangle
        Point[] rectSides = new Point[]{
                topLeft, topRight,   // Top
                topRight, bottomRight, // Right
                bottomRight, bottomLeft, // Bottom
                bottomLeft, topLeft     // Left
        };

        for (int i = 0; i < rectSides.length; i += 2) {
            Point sideStart = rectSides[i];
            Point sideEnd = rectSides[i+1];

            Point intersection = lineLineIntersection(point, center, sideStart, sideEnd);

            if (intersection != null) {
                double distSq = point.distanceSq(intersection);
                if (distSq < minDistanceSq) {
                    minDistanceSq = distSq;
                    closestIntersection = intersection;
                }
            }
        }

        return closestIntersection;
    }

}

