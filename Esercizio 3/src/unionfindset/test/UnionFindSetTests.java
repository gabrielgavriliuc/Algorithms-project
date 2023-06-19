package unionfindset.test;

import static org.junit.Assert.*;
import org.junit.*;
import unionfindset.UnionFindSet;

/**
 * UnionFindSetTests
 * 
 * @author VO1D
 */
public class UnionFindSetTests {
    private UnionFindSet<Integer> integerStructure;
    private UnionFindSet<String> stringStructure;
    private UnionFindSet<Double> doubleStructure;

    @Before
    public void initialization() {
        integerStructure = new UnionFindSet<Integer>();
        stringStructure = new UnionFindSet<String>();
        doubleStructure = new UnionFindSet<Double>();
    }

    @Test
    public void emptyListOfTrees() {
        assertTrue(integerStructure.noSets());
    }

    @Test
    public void nonEmptyListOfIntegerTrees() {
        integerStructure.makeSet(1);
        assertFalse(integerStructure.noSets());
    }

    @Test
    public void nonEmptyListOfStringTrees() {
        stringStructure.makeSet("word");
        assertFalse(stringStructure.noSets());
    }

    @Test
    public void nonEmptyListOfDoubleTrees() {
        doubleStructure.makeSet(1.0);
        assertFalse(doubleStructure.noSets());
    }

    @Test
    public void twoTreesInTheListOfIntegerTrees() {
        integerStructure.makeSet(1);
        integerStructure.makeSet(2);
        assertEquals(2, integerStructure.numberOfSets());
    }

    @Test
    public void twoTreesInTheListOfStringTrees() {
        stringStructure.makeSet("word1");
        stringStructure.makeSet("word2");
        assertEquals(2, stringStructure.numberOfSets());
    }

    @Test
    public void twoTreesInTheListOfDoubleTrees() {
        doubleStructure.makeSet(1.0);
        doubleStructure.makeSet(2.0);
        assertEquals(2, doubleStructure.numberOfSets());
    }

    @Test
    public void sameIntegerElementTwoTimes() {
        integerStructure.makeSet(1);
        integerStructure.makeSet(1);
        assertEquals(1, integerStructure.numberOfSets());
    }

    @Test
    public void sameStringElementTwoTimes() {
        stringStructure.makeSet("word");
        stringStructure.makeSet("word");
        assertEquals(1, stringStructure.numberOfSets());
    }

    @Test
    public void sameDoubleElementTwoTimes() {
        doubleStructure.makeSet(1.0);
        doubleStructure.makeSet(1.0);
        assertEquals(1, doubleStructure.numberOfSets());
    }

    @Test
    public void correctIntegerUnion() {
        integerStructure.makeSet(0);
        integerStructure.makeSet(1);
        integerStructure.union(0, 1);
        assertEquals(1, integerStructure.numberOfSets());
    }

    @Test
    public void correctStringUnion() {
        stringStructure.makeSet("word1");
        stringStructure.makeSet("word2");
        stringStructure.union("word1", "word2");
        assertEquals(1, stringStructure.numberOfSets());
    }

    @Test
    public void correctDoubleUnion() {
        doubleStructure.makeSet(0.0);
        doubleStructure.makeSet(1.0);
        doubleStructure.union(0.0, 1.0);
        assertEquals(1, doubleStructure.numberOfSets());
    }

    @Test
    public void unionIntegerElementNotInSet() {
        integerStructure.makeSet(0);
        integerStructure.makeSet(1);
        integerStructure.union(0, 2);
        assertEquals(2, integerStructure.numberOfSets());
    }

    @Test
    public void unionStringElementNotInSet() {
        stringStructure.makeSet("word1");
        stringStructure.makeSet("word2");
        stringStructure.union("word1", "word3");
        assertEquals(2, stringStructure.numberOfSets());
    }

    @Test
    public void unionDoubleElementNotInSet() {
        doubleStructure.makeSet(0.0);
        doubleStructure.makeSet(1.0);
        doubleStructure.union(0.0, 2.0);
        assertEquals(2, doubleStructure.numberOfSets());
    }

    @Test
    public void unionSameIntegerSet() {
        integerStructure.makeSet(0);
        integerStructure.makeSet(1);
        integerStructure.union(0, 1);
        integerStructure.union(0, 1);
        assertEquals(1, integerStructure.numberOfSets());
    }

    @Test
    public void unionSameStringSet() {
        stringStructure.makeSet("word1");
        stringStructure.makeSet("word2");
        stringStructure.union("word1", "word2");
        stringStructure.union("word1", "word2");
        assertEquals(1, stringStructure.numberOfSets());
    }

    @Test
    public void unionSameDoubleSet() {
        doubleStructure.makeSet(0.0);
        doubleStructure.makeSet(1.0);
        doubleStructure.union(0.0, 1.0);
        doubleStructure.union(0.0, 1.0);
        assertEquals(1, doubleStructure.numberOfSets());
    }
}
