package graph.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import graph.*;

/**
 * GraphTest
 * 
 * @author VO1D
 */
public class GraphTest {
    Graph<Integer, Integer> integerGraph;
    Graph<String, String> stringGraph;
    Graph<Double, Double> doubleGraph;
    Graph<Integer, Integer> integerGraphNO;
    Graph<String, String> stringGraphNO;
    Graph<Double, Double> doubleGraphNO;

    @Before
    public void initialization() {
        integerGraph = new Graph<>(true);
        stringGraph = new Graph<>(true);
        doubleGraph = new Graph<>(true);
        integerGraphNO = new Graph<>(false);
        stringGraphNO = new Graph<>(false);
        doubleGraphNO = new Graph<>(false);
    }

    @Test
    public void emptyGraph() {
        assertEquals(0, integerGraph.getNodesCount());
    }

    /**
     * Adding a single Node.
     */
    @Test
    public void addIntegerNode() {
        integerGraph.addNode(0);
        assertEquals(1, integerGraph.getNodesCount());
    }

    @Test
    public void addStringNode() {
        stringGraph.addNode("word");
        assertEquals(1, stringGraph.getNodesCount());
    }

    @Test
    public void addDoubleNode() {
        doubleGraph.addNode(0.0);
        assertEquals(1, doubleGraph.getNodesCount());
    }

    /**
     * Adding two Nodes.          
     */
    @Test
    public void addTwoIntegerNodes() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        assertEquals(2, integerGraph.getNodesCount());
    }

    @Test
    public void addTwoStringNodes() {
        stringGraph.addNode("word");
        stringGraph.addNode("word1");
        assertEquals(2, stringGraph.getNodesCount());
    }

    @Test
    public void addTwoDoubleNodes() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        assertEquals(2, doubleGraph.getNodesCount());
    }

    /**
     * Adding same Node two times
     */
    @Test
    public void addSameIntegerNodes() {
        integerGraph.addNode(0);
        integerGraph.addNode(0);
        assertEquals(1, integerGraph.getNodesCount());
    }

    @Test
    public void addSameStringNodes() {
        stringGraph.addNode("word");
        stringGraph.addNode("word");
        assertEquals(1, stringGraph.getNodesCount());
    }

    @Test
    public void addSameDoubleNodes() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(0.0);
        assertEquals(1, doubleGraph.getNodesCount());
    }

    /**
     * Adding an Arch.
     * - Oriented
     * - Not Oriented
     */
    @Test
    public void addIntegerArchOriented() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addArch(0, 1, 0);
        assertEquals(1, integerGraph.getAdjacentCount(0));
        assertEquals(0, integerGraph.getAdjacentCount(1));
    }

    @Test
    public void addStringArchOriented() {
        stringGraph.addNode("word");
        stringGraph.addNode("word1");
        stringGraph.addArch("word", "word1", "0");
        assertEquals(1, stringGraph.getAdjacentCount("word"));
        assertEquals(0, stringGraph.getAdjacentCount("word1"));
    }

    @Test
    public void addDoubleArchOriented() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        doubleGraph.addArch(0.0, 1.0, 0.0);
        assertEquals(1, doubleGraph.getAdjacentCount(0.0));
        assertEquals(0, doubleGraph.getAdjacentCount(1.0));
    }

    @Test
    public void addIntegerArchNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addArch(0, 1, 0);
        assertEquals(1, integerGraphNO.getAdjacentCount(0));
        assertEquals(1, integerGraphNO.getAdjacentCount(1));
    }

    @Test
    public void addStringArchNotOriented() {
        stringGraphNO.addNode("word");
        stringGraphNO.addNode("word1");
        stringGraphNO.addArch("word", "word1", "0");
        assertEquals(1, stringGraphNO.getAdjacentCount("word"));
        assertEquals(1, stringGraphNO.getAdjacentCount("word1"));
    }

    @Test
    public void addDoubleArchNotOriented() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addNode(1.0);
        doubleGraphNO.addArch(0.0, 1.0, 0.9);
        assertEquals(1, doubleGraphNO.getAdjacentCount(0.0));
        assertEquals(1, doubleGraphNO.getAdjacentCount(1.0));
    }

    /**
     * Adding two Arches.
     * - Oriented
     * - Not Oriented
     */
    @Test
    public void addTwoIntegerArchOriented() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addArch(0, 1, 0);
        integerGraph.addArch(0, 2, 0);
        assertEquals(2, integerGraph.getAdjacentCount(0));
        assertEquals(0, integerGraph.getAdjacentCount(1));
        assertEquals(0, integerGraph.getAdjacentCount(2));
    }

    @Test
    public void addTwoStringArchOriented() {
        stringGraph.addNode("word");
        stringGraph.addNode("word1");
        stringGraph.addNode("word2");
        stringGraph.addArch("word", "word1", "0");
        stringGraph.addArch("word", "word2", "0");
        assertEquals(2, stringGraph.getAdjacentCount("word"));
        assertEquals(0, stringGraph.getAdjacentCount("word1"));
        assertEquals(0, stringGraph.getAdjacentCount("word2"));
    }

    @Test
    public void addTwoDoubleArchOriented() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        doubleGraph.addNode(2.0);
        doubleGraph.addArch(0.0, 1.0, 0.0);
        doubleGraph.addArch(0.0, 2.0, 0.0);
        assertEquals(2, doubleGraph.getAdjacentCount(0.0));
        assertEquals(0, doubleGraph.getAdjacentCount(1.0));
        assertEquals(0, doubleGraph.getAdjacentCount(2.0));
    }

    @Test
    public void addTwoIntegerArchNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addNode(2);
        integerGraphNO.addArch(0, 1, 0);
        integerGraphNO.addArch(0, 2, 0);
        assertEquals(2, integerGraphNO.getAdjacentCount(0));
        assertEquals(1, integerGraphNO.getAdjacentCount(1));
        assertEquals(1, integerGraphNO.getAdjacentCount(2));
    }

    @Test
    public void addTwoStringArchNotOriented() {
        stringGraphNO.addNode("word");
        stringGraphNO.addNode("word1");
        stringGraphNO.addNode("word2");
        stringGraphNO.addArch("word", "word1", "0");
        stringGraphNO.addArch("word", "word2", "0");
        assertEquals(2, stringGraphNO.getAdjacentCount("word"));
        assertEquals(1, stringGraphNO.getAdjacentCount("word1"));
        assertEquals(1, stringGraphNO.getAdjacentCount("word2"));
    }

    @Test
    public void addTwoDoubleArchNotOriented() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addNode(1.0);
        doubleGraphNO.addNode(2.0);
        doubleGraphNO.addArch(0.0, 1.0, 0.0);
        doubleGraphNO.addArch(0.0, 2.0, 0.0);
        assertEquals(2, doubleGraphNO.getAdjacentCount(0.0));
        assertEquals(1, doubleGraphNO.getAdjacentCount(1.0));
        assertEquals(1, doubleGraphNO.getAdjacentCount(2.0));
    }

    /**
     * Adding same arch two times.
     * - Oriented
     * - Not oriented
     */
    @Test
    public void addSameIntegerArchOriented() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addArch(0, 1, 0);
        integerGraph.addArch(0, 1, 0);
        assertEquals(1, integerGraph.getAdjacentCount(0));
    }

    @Test
    public void addSameStringArchOriented() {
        stringGraph.addNode("word");
        stringGraph.addNode("word1");
        stringGraph.addArch("word", "word1", "0");
        stringGraph.addArch("word", "word1", "0");
        assertEquals(1, stringGraph.getAdjacentCount("word"));
    }

    @Test
    public void addSameDoubleArchOriented() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        doubleGraph.addArch(0.0, 1.0, 0.0);
        doubleGraph.addArch(0.0, 1.0, 0.0);
        assertEquals(1, doubleGraph.getAdjacentCount(0.0));
    }

    @Test
    public void addSameIntegerArchNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addArch(0, 1, 0);
        integerGraphNO.addArch(1, 0, 0);
        assertEquals(1, integerGraphNO.getAdjacentCount(0));
        assertEquals(1, integerGraphNO.getAdjacentCount(1));
    }

    @Test
    public void addSameStringArchNotOriented() {
        stringGraphNO.addNode("word");
        stringGraphNO.addNode("word1");
        stringGraphNO.addArch("word", "word1", "0");
        stringGraphNO.addArch("word1", "word", "0");
        assertEquals(1, stringGraphNO.getAdjacentCount("word"));
        assertEquals(1, stringGraphNO.getAdjacentCount("word1"));
    }

    @Test
    public void addSameDoubleArchNotOriented() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addNode(1.0);
        doubleGraphNO.addArch(0.0, 1.0, 0.0);
        doubleGraphNO.addArch(1.0, 0.0, 0.0);
        assertEquals(1, doubleGraphNO.getAdjacentCount(0.0));
        assertEquals(1, doubleGraphNO.getAdjacentCount(1.0));
    }

    /**
     * Adding arch with no existing source as Node in the Graph
     */
    @Test
    public void addIntegerArchWithNoExistingSource() {
        integerGraphNO.addNode(0);
        integerGraphNO.addArch(2, 0, 0);
        assertEquals(0, integerGraphNO.getAdjacentCount(0));
    }

    @Test
    public void addStringArchWithNoExistingSource() {
        stringGraphNO.addNode("word");
        stringGraphNO.addArch("word1", "word", "0");
        assertEquals(0, stringGraphNO.getAdjacentCount("word"));
    }

    @Test
    public void addDoublearchWithNoExistingSource() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addArch(1.0, 0.0, 0.0);
        assertEquals(0, doubleGraphNO.getAdjacentCount(0.0));
    }

    /**
     * Adding arch with no existing destination as Node in the Graph
     */
    @Test
    public void addIntegerArchWithNoExistingDestination() {
        integerGraph.addNode(0);
        integerGraph.addArch(0, 1, 0);
        assertEquals(0, integerGraph.getAdjacentCount(0));
    }

    @Test
    public void addStringArchWithNoExistingDestination() {
        stringGraph.addNode("word");
        stringGraph.addArch("word", "word1", "0");
        assertEquals(0, stringGraph.getAdjacentCount("word"));
    }

    @Test
    public void addDoublearchWithNoExistingDestination() {
        doubleGraph.addNode(0.0);
        doubleGraph.addArch(0.0, 1.0, 0.0);
        assertEquals(0, doubleGraph.getAdjacentCount(0.0));
    }

    /**
     * Adding Arch where source = destination
     */
    @Test
    public void addIntegerArchBetweenSameElement() {
        integerGraph.addNode(0);
        integerGraph.addArch(0, 0, 0);
        assertEquals(0, integerGraph.getAdjacentCount(0));
    }

    @Test
    public void addStringArchBetweenSameElement() {
        stringGraph.addNode("word");
        stringGraph.addArch("word", "word", "0");
        assertEquals(0, stringGraph.getAdjacentCount("word"));
    }

    @Test
    public void addDoubleArchBetweenSameElement() {
        doubleGraph.addNode(0.0);
        doubleGraph.addArch(0.0, 0.0, 0.0);
        assertEquals(0, doubleGraph.getAdjacentCount(0.0));
    }

    /**
     * Direct or NOT direct Graph
     */
    @Test
    public void graphIsDirect() {
        assertTrue(integerGraph.isDirect());
    }

    @Test
    public void graphIsNotDirect() {
        assertFalse(integerGraphNO.isDirect());
    }

    /**
     * Existing or NOT existing Node in the Graph
     */
    @Test
    public void existingNode() {
        integerGraph.addNode(0);
        assertTrue(integerGraph.existingNode(0));
    }

    @Test
    public void notExistingNode() {
        assertFalse(integerGraph.existingNode(0));
    }

    /**
     * Existing or NOT existing Arch in the Graph
     */
    @Test
    public void existingArch() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addArch(0, 1, 0);
        assertTrue(integerGraph.existingArch(0, 1));
        assertFalse(integerGraph.existingArch(1, 0));
    }

    @Test
    public void nonExistingArch() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        assertFalse(integerGraph.existingArch(1, 0));
    }

    /**
     * Controlling the existence of an Arch with source not in Graph
     */
    @Test
    public void nonExistingArchNonExistingSource() {
        integerGraph.addNode(0);
        assertFalse(integerGraph.existingArch(1, 0));
    }

    /**
     * Removing a Node.
     * - Oriented
     * - Not oriented
     */
    @Test
    public void removeIntegerNode() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addNode(3);
        integerGraph.addArch(1, 0, 0);
        integerGraph.addArch(2, 0, 0);
        integerGraph.addArch(3, 0, 0);
        integerGraph.deleteNode(0);
        assertEquals(3, integerGraph.getNodesCount());
        assertEquals(0, integerGraph.getAdjacentCount(1));
        assertEquals(0, integerGraph.getAdjacentCount(2));
        assertEquals(0, integerGraph.getAdjacentCount(3));
    }

    @Test
    public void removeStringNode() {
        stringGraph.addNode("word");
        stringGraph.addNode("word1");
        stringGraph.addNode("word2");
        stringGraph.addNode("word3");
        stringGraph.addArch("word1", "word", "0");
        stringGraph.addArch("word2", "word", "0");
        stringGraph.addArch("word3", "word", "0");
        stringGraph.deleteNode("word");
        assertEquals(3, stringGraph.getNodesCount());
        assertEquals(0, stringGraph.getAdjacentCount("word1"));
        assertEquals(0, stringGraph.getAdjacentCount("word2"));
        assertEquals(0, stringGraph.getAdjacentCount("word3"));
    }

    @Test
    public void removeDooubleNode() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        doubleGraph.addNode(2.0);
        doubleGraph.addNode(3.0);
        doubleGraph.addArch(1.0, 0.0, 0.0);
        doubleGraph.addArch(2.0, 0.0, 0.0);
        doubleGraph.addArch(3.0, 0.0, 0.0);
        doubleGraph.deleteNode(0.0);
        assertEquals(3, doubleGraph.getNodesCount());
        assertEquals(0, doubleGraph.getAdjacentCount(1.0));
        assertEquals(0, doubleGraph.getAdjacentCount(2.0));
        assertEquals(0, doubleGraph.getAdjacentCount(3.0));
    }

    @Test
    public void removeIntegerNodeNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addNode(2);
        integerGraphNO.addNode(3);
        integerGraphNO.addArch(1, 0, 0);
        integerGraphNO.addArch(2, 0, 0);
        integerGraphNO.addArch(3, 0, 0);
        integerGraphNO.deleteNode(0);
        assertEquals(3, integerGraphNO.getNodesCount());
        assertEquals(0, integerGraphNO.getAdjacentCount(1));
        assertEquals(0, integerGraphNO.getAdjacentCount(2));
        assertEquals(0, integerGraphNO.getAdjacentCount(3));
    }

    @Test
    public void removeStringNodeNotOriented() {
        stringGraphNO.addNode("word");
        stringGraphNO.addNode("word1");
        stringGraphNO.addNode("word2");
        stringGraphNO.addNode("word3");
        stringGraphNO.addArch("word1", "word", "0");
        stringGraphNO.addArch("word2", "word", "0");
        stringGraphNO.addArch("word3", "word", "0");
        stringGraphNO.deleteNode("word");
        assertEquals(3, stringGraphNO.getNodesCount());
        assertEquals(0, stringGraphNO.getAdjacentCount("word1"));
        assertEquals(0, stringGraphNO.getAdjacentCount("word2"));
        assertEquals(0, stringGraphNO.getAdjacentCount("word3"));
    }

    @Test
    public void removeDooubleNodeNotOriented() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addNode(1.0);
        doubleGraphNO.addNode(2.0);
        doubleGraphNO.addNode(3.0);
        doubleGraphNO.addArch(1.0, 0.0, 0.0);
        doubleGraphNO.addArch(2.0, 0.0, 0.0);
        doubleGraphNO.addArch(3.0, 0.0, 0.0);
        doubleGraphNO.deleteNode(0.0);
        assertEquals(3, doubleGraphNO.getNodesCount());
        assertEquals(0, doubleGraphNO.getAdjacentCount(1.0));
        assertEquals(0, doubleGraphNO.getAdjacentCount(2.0));
        assertEquals(0, doubleGraphNO.getAdjacentCount(3.0));
    }

    /**
     * Remove inexistent Node
     */
    @Test
    public void removeInexistentNode() {
        integerGraph.addNode(1);
        integerGraph.deleteNode(0);
        assertEquals(1, integerGraph.getNodesCount());
    }

    /**
     * Remove Arch.
     * - Oriented
     * - Not oriented
     */
    @Test
    public void removeArch() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addArch(0, 1, 0);
        integerGraph.addArch(0, 2, 0);
        integerGraph.deleteArch(0, 1);
        assertEquals(1, integerGraph.getAdjacentCount(0));
    }

    @Test
    public void removeArchNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addNode(2);
        integerGraphNO.addArch(0, 1, 0);
        integerGraphNO.addArch(0, 2, 0);
        integerGraphNO.deleteArch(0, 1);
        assertEquals(1, integerGraphNO.getAdjacentCount(0));
        assertEquals(1, integerGraphNO.getAdjacentCount(2));
        assertEquals(0, integerGraphNO.getAdjacentCount(1));
    }

    /**
     * Remove Arch with not existent source or destination in the Graph
     */
    @Test
    public void removeArchNonExistentSource() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(2);
        integerGraphNO.addArch(0, 2, 0);
        integerGraphNO.deleteArch(1, 0);
        assertEquals(1, integerGraphNO.getAdjacentCount(0));
    }

    @Test
    public void removeArchNonExistentDestination() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(2);
        integerGraphNO.addArch(0, 2, 0);
        integerGraphNO.deleteArch(0, 1);
        assertEquals(1, integerGraphNO.getAdjacentCount(0));
    }

    @Test
    public void numberofArches() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addNode(3);
        assertEquals(0, integerGraph.getArchesCount());

        integerGraph.addArch(0, 1, 0);
        assertEquals(1, integerGraph.getArchesCount());

        integerGraph.addArch(1, 2, 0);
        integerGraph.addArch(1, 3, 0);
        assertEquals(3, integerGraph.getArchesCount());

        integerGraph.deleteArch(1, 2);
        assertEquals(2, integerGraph.getArchesCount());
    }

    @Test
    public void numberOfArchesNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addNode(2);
        integerGraphNO.addNode(3);
        assertEquals(0, integerGraphNO.getArchesCount());

        integerGraphNO.addArch(0, 1, 0);
        assertEquals(2, integerGraphNO.getArchesCount());

        integerGraphNO.addArch(1, 2, 0);
        integerGraphNO.addArch(1, 3, 0);
        assertEquals(6, integerGraphNO.getArchesCount());

        integerGraphNO.deleteArch(1, 2);
        assertEquals(4, integerGraphNO.getArchesCount());
    }

    /**
     * Get all the Nodes from a Graph
     */
    @Test
    public void getIntegerNodes() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);

        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);

        assertEquals(expected, integerGraph.getNodes());
    }

    @Test
    public void getIntegerZeroNodes() {
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, integerGraph.getNodes());
    }

    /**
     * Get all the adjacent Nodes of a Node from the Graph
     */
    @Test
    public void getAdjacentNodes() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addNode(3);

        integerGraph.addArch(0, 1, 0);
        integerGraph.addArch(0, 2, 0);
        integerGraph.addArch(0, 3, 0);

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);

        assertEquals(expected, integerGraph.getAdjacentList(0));
    }
    
    /**
     * Get all the Arches.
     * - Oriented
     * - Not oriented
     */
    @Test
    public void getAllTheArches() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addNode(3);

        integerGraph.addArch(0, 1, 0);
        integerGraph.addArch(0, 2, 0);
        integerGraph.addArch(0, 3, 0);

        List<Arch<Integer, Integer>> expected = new ArrayList<>();
        expected.add(new Arch<Integer, Integer>(0, 1, 0));
        expected.add(new Arch<Integer, Integer>(0, 2, 0));
        expected.add(new Arch<Integer, Integer>(0, 3, 0));

        assertEquals(expected, integerGraph.getArches());
    }

    @Test
    public void getAllTheArchesNotOriented() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addNode(2);
        integerGraphNO.addNode(3);

        integerGraphNO.addArch(0, 1, 0);
        integerGraphNO.addArch(0, 2, 0);
        integerGraphNO.addArch(0, 3, 0);

        List<Arch<Integer, Integer>> expected = new ArrayList<>();
        expected.add(new Arch<Integer, Integer>(0, 1, 0));
        expected.add(new Arch<Integer, Integer>(1, 0, 0));
        expected.add(new Arch<Integer, Integer>(0, 2, 0));
        expected.add(new Arch<Integer, Integer>(2, 0, 0));
        expected.add(new Arch<Integer, Integer>(0, 3, 0));
        expected.add(new Arch<Integer, Integer>(3, 0, 0));

        assertEquals(expected.size(), integerGraphNO.getArches().size());
    }

    @Test
    public void getZeroArches() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addNode(2);
        integerGraph.addNode(3);

        List<Arch<Integer, Integer>> expected = new ArrayList<>();

        assertEquals(expected, integerGraph.getArches());
    }

    /**
     * Get Arch label.
     * - Oriented
     * - Not oriented
     */
    @Test
    public void getIntegerLabel() {
        integerGraph.addNode(0);
        integerGraph.addNode(1);
        integerGraph.addArch(0, 1, 3);

        assertEquals(Integer.valueOf(3), integerGraph.getArchLabel(0, 1));
    }

    @Test
    public void getStringLabel() {
        stringGraph.addNode("word1");
        stringGraph.addNode("word2");
        stringGraph.addArch("word1", "word2", "3");

        assertEquals("3", stringGraph.getArchLabel("word1", "word2"));
    }

    @Test
    public void getDoubleLabel() {
        doubleGraph.addNode(0.0);
        doubleGraph.addNode(1.0);
        doubleGraph.addArch(0.0, 1.0, 3.0);

        assertEquals(Double.valueOf(3.0), doubleGraph.getArchLabel(0.0, 1.0));
    }

    @Test
    public void getIntegerLabelNO() {
        integerGraphNO.addNode(0);
        integerGraphNO.addNode(1);
        integerGraphNO.addArch(0, 1, 3);

        assertEquals(Integer.valueOf(3), integerGraphNO.getArchLabel(1, 0));
    }

    @Test
    public void getStringLabelNO() {
        stringGraphNO.addNode("word1");
        stringGraphNO.addNode("word2");
        stringGraphNO.addArch("word1", "word2", "3");

        assertEquals("3", stringGraphNO.getArchLabel("word2", "word1"));
    }

    @Test
    public void getDoubleLabelNO() {
        doubleGraphNO.addNode(0.0);
        doubleGraphNO.addNode(1.0);
        doubleGraphNO.addArch(0.0, 1.0, 3.0);

        assertEquals(Double.valueOf(3.0), doubleGraphNO.getArchLabel(1.0, 0.0));
    }

}
