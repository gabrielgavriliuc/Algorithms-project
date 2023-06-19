package unionfindset;

import java.util.ArrayList;
import java.util.List;

/**
 * Node
 * 
 * @author VO1D
 * @param <T> type of the element in the Node
 */
public class Node<T> {
    private T element;
    private Node<T> parent;
    private int range;
    private List<Node<T>> children;

    public Node(T element) {
        this.element = element;
        this.parent = this;
        this.range = 0;
        children = new ArrayList<>();
    }

    public T getElement() {
        return this.element;
    }

    public List<Node<T>> getChildren() {
        return this.children;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setParent(Node<T> newParent) {
        this.parent = newParent;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void addChild(Node<T> newChild) {
        this.children.add(newChild);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
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
        Node other = (Node) obj;
        if (element == null) {
            if (other.element != null)
                return false;
        } else if (!element.equals(other.element))
            return false;
        return true;
    }

}
