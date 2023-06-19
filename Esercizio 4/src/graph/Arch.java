package graph;

/**
 * Arch
 * 
 * @author VO1D
 * @param T1 type of the elements of the Arch
 * @param T2 type of the label of the Arch
 */
public class Arch<T1, T2> {
    private T1 source;
    private T1 destination;
    private T2 label;

    public Arch(T1 source, T1 destination, T2 label) {
        this.source = source;
        this.destination = destination;
        this.label = label;
    }

    /**
     * 
     * @return the source element of the Arch
     */
    public T1 getSource() {
        return source;
    }

    /**
     * 
     * @return the destination element of the Arch
     */
    public T1 getDestination() {
        return destination;
    }

    /**
     * 
     * @return the label of the Arch
     */
    public T2 getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
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
        Arch<?, ?> other = (Arch<?, ?>) obj;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }

}
