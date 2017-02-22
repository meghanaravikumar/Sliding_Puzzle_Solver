
public class Rectangle {

    public final Corner topLeft;
    public final Corner bottomRight;
    public final Corner dimensions;

    public Rectangle(int[] coords) { //takes in a integer array of coords
        if (coords.length != 4) {
            System.out.println("Malformed coordinante string");
            System.exit(4);
            throw new IllegalArgumentException("SHUT UP JVM");
        } else {
            topLeft = new Corner(coords[0], coords[1]);
            bottomRight = new Corner(coords[2], coords[3]);
            dimensions = new Corner((bottomRight.x + 1) - topLeft.x, (bottomRight.y + 1) - topLeft.y);
        }
    }

    public Rectangle(Corner p, Corner dim) {
        topLeft = p;
        dimensions = dim;
        if (dim.equals(new Corner(1, 1))) {
            bottomRight = topLeft;

        } else {
            bottomRight = new Corner(topLeft.x + (dim.x - 1), topLeft.y + (dim.y - 1));

        }
    }

    @Override
    public int hashCode() {
        return topLeft.hashCode() + bottomRight.hashCode() + dimensions.hashCode();
    }

    @Override
    public boolean equals(Object rect) {
        if (rect.getClass().equals(this.getClass())) {
            if (this.topLeft.equals(((Rectangle) rect).topLeft)
                    && this.bottomRight.equals(((Rectangle) rect).bottomRight)
                    && this.dimensions.equals(((Rectangle) rect).dimensions)) {
                return true;
            }
        }
        return false;
    }

    

    @Override
    public String toString() {
        String str = (topLeft + " " + bottomRight);
        return str;
    }

}
