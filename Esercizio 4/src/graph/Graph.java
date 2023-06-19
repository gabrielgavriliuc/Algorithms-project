package graph;

import java.util.*;

/**
 * Graph
 * 
 * @author VO1D
 * @param <T1> type of the elements in the Graph
 * @param <T2> type of the label of the Arches
 */
public class Graph<T1, T2> {
    private HashMap<T1, LinkedList<Arch<T1, T2>>> map;
    private boolean oriented;

    /**
     * Constructor of the structure Graph
     * It creates an empty Graph with no Nodes and no Arches
     * 
     * @param oriented define if the graph is direct or not -> True = direct | False = non direct
     */
    public Graph(boolean oriented) {
        map = new HashMap<>();
        this.oriented = oriented;
    }

    /**
     * Adds a new Node to the graph
     * 
     * @param node must not already exist in the graph, if it exists there's no addition
     */
    public void addNode(T1 node) {
        map.put(node, new LinkedList<Arch<T1, T2>>());
    }

    /**
     * Adds a new Arch to the Graph
     * If the Graph is oriented the Arch is added to the Node source 
     * If the Graph is not oriented the Arch is added also to the Node destination
     * 
     * @param source      must be an existing Node in the Graph and
     *                    must be different from destination
     * @param destination must be an existing Node in the Graph and
     *                    must be different from source
     * @param label       is the label of the Arch
     */
    public void addArch(T1 source, T1 destination, T2 label) {
        // PRELIMINAR CHECK
        // source and destination must be different nodes
        if (source.equals(destination)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.err.println("Graph method addArch: cannot add arch between the same node");
            }
        } else {
            // source must exist in the graph
            if (!map.containsKey(source)) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("Graph method addArch: node (" + source + ") does not exist yet");
                }
                // destination must exist in the graph
            } else if (!map.containsKey(destination)) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("Graph method addArch: node (" + destination + ") does not exist yet");
                }

            } else {
                // INSERTION
                // The arch must not exist yet
                if (!existingArch(source, destination)) {
                    map.get(source).add(new Arch<T1, T2>(source, destination, label));

                    // If not oriented we need to add the arch also to destination
                    if (!oriented) {
                        map.get(destination).add(new Arch<T1, T2>(destination, source, label));
                    }
                }
            }
        }
    }

    /**
     * 
     * @return True if the Graph is direct
     */
    public boolean isDirect() {
        return oriented;
    }

    /**
     * 
     * @param node is the Node of which we want to verify the existence
     * @return True if the Graph contains node
     */
    public boolean existingNode(T1 node) {
        return map.containsKey(node);
    }

    /**
     * 
     * @param source      is the Node that we want to make sure it contains the Arch (source, destination, label)
     * @param destination is the Node that we want to find in source's Arches
     * @param label       is the label of the Arch
     * @return True if the Arch between source and destination exists
     */
    public boolean existingArch(T1 source, T1 destination) {
        // source must exists
        if (map.containsKey(source)) {
            for (Arch<T1, T2> arch : map.get(source)) {
                if (arch.getSource().equals(source) && arch.getDestination().equals(destination)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Deletes a node in the graph and all the arches that involve it
     * 
     * @param node must exist in the Graph
     */
    public void deleteNode(T1 node) {
        // PRELIMINAR CHECK
        // The Graph must contain node
        if (!map.containsKey(node)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Graph method deleteNode: the node " + node + " does not exist in this graph");
            }

        } else {
            // REMOVAL
            // Remove Node
            map.remove(node);

            // Remove Arches
            for (LinkedList<Arch<T1, T2>> arches : map.values()) {
                removeArches(arches, node);
            }
        }
    }

    /**
     * It deletes the arch between source and destination if it exists.
     * If the graph is directed it also removes the arch from destination to source
     * 
     * @param source      must exist in the graph
     * @param destination must exist in the graph
     */
    public void deleteArch(T1 source, T1 destination) {
        // PRELIMINAR CHECK
        // We can remove an Arch only if it exists
        Arch<T1, T2> arch = getArch(source, destination);
        if (arch != null) {
            // REMOVAL
            map.get(source).remove(arch);

            if (!oriented) {
                Arch<T1, T2> archNO = new Arch<T1, T2>(arch.getDestination(), arch.getSource(), arch.getLabel());
                map.get(destination).remove(archNO);
            }
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println(
                        "Graph method deleteArch: the arch (" + source + ", " + destination + ") does not exist");
            }
        }
    }

    /**
     * 
     * @return the number of the Nodes
     */
    public int getNodesCount() {
        return map.size();
    }

    /**
     * 
     * @return the number of the arches in the Graph
     */
    public int getArchesCount() {
        int count = 0;
        for (LinkedList<Arch<T1, T2>> i : map.values()) {
            count += i.size();
        }

        return count;
    }

    /**
     * 
     * @return an ArrayList object that contains all the nodes in the Graph
     *         zero nodes -> empty list
     */
    public List<T1> getNodes() {
        List<T1> nodes = new ArrayList<>();

        for (T1 node : map.keySet()) {
            nodes.add(node);
        }

        return nodes;
    }

    /**
     * 
     * @return a List of Arch objects representing all the arches of the graph
     *         zero arches -> empty list 
     */
    public List<Arch<T1, T2>> getArches() {
        List<Arch<T1, T2>> arches = new ArrayList<>();

        for (T1 node : map.keySet()) {
            for (int i = 0; i < map.get(node).size(); i++) {
                arches.add(new Arch<T1, T2>(map.get(node).get(i).getSource(), map.get(node).get(i).getDestination(), map.get(node).get(i).getLabel()));
            }
        }

        return arches;
    }

    
    /**
     * 
     * @param node must exist in the Graph
     * @return a List of the adjacent Nodes of node
     */
    public List<T1> getAdjacentList(T1 node) {
        // PRELIMINAR CHECK
        // node must exist
        if (!map.containsKey(node)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Graph method getAdjacentList: node (" + node + ") doesn't exist in the Graph");
            }
            return null;
        }

        //LIST CREATION
        List<T1> adjacentNodes = new ArrayList<>();

        for (int i = 0; i < map.get(node).size(); i++) {
            adjacentNodes.add(map.get(node).get(i).getDestination());
        }

        return adjacentNodes;
    }
    
    /**
     * 
     * @param source      must exist in the Graph
     * @param destination must exist in the Graph
     * @return the label of the arch containing source and destination nodes
     */
    public T2 getArchLabel(T1 source, T1 destination) {
        // PRELIMINAR CHECK
        // source and destination must exist
        // the arch must exist
        Arch<T1, T2> arch = getArch(source, destination);
        if (arch != null) {
            return arch.getLabel();
        }

        return null;
    }

    /**
     * 
     * @param node must exist in the graph
     * @return the number of Arches of node
     */
    public int getAdjacentCount(T1 node) {
        // node must exist in the graph
        if (!map.containsKey(node)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Graph method getAdjacentCount: node (" + node + ") does not exist");
                return 0;
            }
        } else {
            return map.get(node).size();
        }
    }

    /**
     * 
     * @param archesList is the list where we are going to delete the arches
     * @param node       if it appears as destination Node of an Arch requires its elimination
     */
    private void removeArches(LinkedList<Arch<T1, T2>> archesList, T1 node) {
        for (int i = 0; i < archesList.size(); i++) {
            if (archesList.get(i).getDestination().equals(node)) {
                archesList.remove(i);
            }
        }
    }

    /**
     * 
     * @param source      must exist in the Graph
     * @param destination must exist in the Graph
     * @return the Arch (source, destination, label) if it exists
     */
    private Arch<T1, T2> getArch(T1 source, T1 destination) {
        // source and destination must exist
        if (map.containsKey(source) && map.containsKey(destination)) {
            for (Arch<T1, T2> arch : map.get(source)) {
                if (arch.getSource().equals(source) && arch.getDestination().equals(destination)) {
                    return arch;
                }
            }
        }

        return null;
    }
}