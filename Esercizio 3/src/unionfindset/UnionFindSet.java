package unionfindset;

import java.util.*;

/**
 * UnionFindSet
 * 
 * @author VO1D
 * @param <T> type of the elements used in the structure
 */
public class UnionFindSet<T> {
    private List<Node<T>> trees;

    /**
     * It creates a structure UnionFindSet
     * It consists in sets of elements of T type that appear only once in the entire
     * structure
     * An element belongs only to one set and cannot appear in others
     */
    public UnionFindSet() {
        trees = new ArrayList<>();
    }

    /**
     * It creates a new set if the value of the parameter doesn't already exist in
     * the structure
     * If the value is already in the structure there is no set creation but a
     * message is displayed
     * 
     * @param rep is the representative of the set, it must not exist in any set
     */
    public void makeSet(T rep) {
        if (findNode(rep) == null) {
            trees.add(new Node<T>(rep));
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println(
                        "UnionFindSet method: makeSet -> the element " + rep
                                + " you are trying to insert in the structure is already existing");
            }
        }
    }

    /**
     * Searches for the set the element belongs to
     * 
     * @param x item of which we want to know the set
     * @return the representative Node of the set
     */
    public Node<T> findSet(T x) {
        Node<T> xNode = findNode(x);
        if (xNode == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println(
                        "UnionFindSet method: findSet -> Element " + x + " doesn't belong to any set");
            }
            return null;
        } else {
            return findSetOfNode(xNode);
        }
    }

    /**
     * It creates a new set union of x's set and y's set
     * x and y must not appear in the same set
     * 
     * @param x must be an element in the structure
     * @param y must be an element in the structure
     */
    public void union(T x, T y) {
        Node<T> xNode = findSet(x);
        Node<T> yNode = findSet(y);

        if (xNode == null || yNode == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("UnionFindSet method: union -> both elements must already exist in the structure");
            }
        } else {
            if (xNode == yNode) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("UnionFindSet method: union -> elements cannot belong to the same set");
                }
            } else {
                link(xNode, yNode);
            }
        }
    }

    /**
     * 
     * @return how many sets are in the structure
     */
    public long numberOfSets() {
        return trees.size();
    }

    /**
     * 
     * @return true if there are no sets
     */
    public boolean noSets() {
        return (trees.size() == 0) ? true : false;
    }

    /**
     * It creates the new set with the concept of union by rank
     * 
     * @param x is the representative of its set
     * @param y is the representative og its set
     */
    private void link(Node<T> x, Node<T> y) {
        if (x.getRange() > y.getRange()) {
            y.setParent(x);
            x.addChild(y);
            trees.remove(y);
        } else {
            x.setParent(y);
            y.addChild(x);
            trees.remove(x);
            if (x.getRange() == y.getRange()) {
                y.setRange(y.getRange() + 1);
            }
        }
    }

    /**
     * Iterative method that allows us to search for the element in all the sets
     * 
     * @param element is the element we are searching for
     * @return the Node of the element if existing in the structure, null otherwise
     */
    private Node<T> findNode(T element) {
        for (int i = 0; i < trees.size(); i++) {
            Node<T> temp = trees.get(i);

            if (elementIsInThisSet(temp, element) != null) {
                return temp;
            }
        }

        return null;
    }

    /**
     * Recursive method that allows us to search the element in a specific set
     * 
     * @param node    is the Node that we are considering
     * @param element is the element we are searching for
     * @return the Node of the element if existing in the structure, null otherwise
     */
    private Node<T> elementIsInThisSet(Node<T> node, T element) {
        if (node.getElement().equals(element)) {
            return node;
        }

        for (int j = 0; j < node.getChildren().size(); j++) {
            Node<T> child = node.getChildren().get(j);
            if (elementIsInThisSet(child, element) != null) {
                return node;
            }
        }

        return null;
    }

    private Node<T> findSetOfNode(Node<T> xNode) {
        if (xNode != xNode.getParent()) {
            xNode.setParent(findSetOfNode(xNode.getParent()));
        }

        return xNode.getParent();
    }
}