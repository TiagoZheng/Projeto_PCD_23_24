package remote;

public enum Direction {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private final String directionString;

    Direction(String directionString) {
        this.directionString = directionString;
    }

    public String getDirectionString() {
        return directionString;
    }

    // Helper method to convert a string to Direction
    public static Direction fromString(String directionString) {
        for (Direction direction : values()) {
            if (direction.directionString.equalsIgnoreCase(directionString)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction string: " + directionString);
    }
}

