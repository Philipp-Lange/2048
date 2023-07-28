public class Tile {
    private int value;

    public Tile() {
        this.value = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (value != other.value)
            return false;
        return true;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
