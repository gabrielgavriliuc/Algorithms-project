package kruskal;

import java.util.Comparator;
import java.util.List;

import graph.Arch;
import graph.Graph;
import unionfindset.Node;
import unionfindset.UnionFindSet;

/**
 * Library that provides the Kruskal algorithm for computing the minimum spanning trees of a Graph
 * 
 * @param T1 is the type of the Nodes of the Graph
 * @param T2 is the type of the label of Graph's Arches, it must implement Comparable
 * @author VO1D
 */
public class Kruskal<T1, T2 extends Comparable<T2>> {
    private UnionFindSet<T1> ufs;
    private Graph<T1, T2> forest;

    /**
     * Kruskal Algorithm
     * It calculates the minimum spanning trees of a given Graph
     *  
     * @param graph must be not directed and must have at least 2 Nodes
     * @return a subgraph that is the minimum spanning tree of the Graph passed as parameter
     */
    public Graph<T1, T2> minimumSpanningTree(Graph<T1, T2> graph) {
        // PRELIMINAR CHECK
        // Kruskal works with not oriented Graphs
        if (graph.isDirect()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println(
                        "Kruskal method minimumSpanningTree: the graph passed as parameter is directed/oriented");
            }
        } else {

            // Kruskal works with Graphs that has at leas 2 Nodes
            if (graph.getNodesCount() < 2) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println(
                            "Kruskal method minimumSpanningTree: the graph passed as parameter must have at leas 2 Nodes");
                }
            } else {

                // KRUSKAL
                // Initializaion
                ufs = new UnionFindSet<>();
                forest = new Graph<>(true);
    
                // Make set on each node
                List<T1> nodesList = graph.getNodes();
                for (T1 t : nodesList) {
                    ufs.makeSet(t);
                }
    
                // Sorting the arches
                List<Arch<T1, T2>> orderedArches = graph.getArches();
                orderedArches.sort(new Comparator<Arch<T1, T2>>() {
    
                    @Override
                    public int compare(Arch<T1, T2> a1, Arch<T1, T2> a2) {
                        return a1.getLabel().compareTo(a2.getLabel());
                    }
    
                });
    
                // Creating the sets and the forest
                for (Arch<T1, T2> arch : orderedArches) {
                    if (!forest.existingNode(arch.getSource())) {
                        forest.addNode(arch.getSource());
                    }

                    if (!forest.existingNode(arch.getDestination())) {
                        forest.addNode(arch.getDestination());
                    }

                    Node<T1> source = ufs.findSet(arch.getSource());
                    Node<T1> destination = ufs.findSet(arch.getDestination());

                    if (source != destination) {
                        forest.addArch(arch.getSource(), arch.getDestination(), arch.getLabel());
                        ufs.union(arch.getSource(), arch.getDestination());
                    }
                }
            }
        }
        return forest;
    }
}
