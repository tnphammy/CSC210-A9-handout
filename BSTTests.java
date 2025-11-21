import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Binary Search Tree (BST) class.
 *
 * @author YOUR_NAME_HERE
 * @version Fall 2025
 */
public class BSTTests {
    /** Helper method: verifies that a BinaryTree has the expected structure and contents. */
    private static <T> void verifyBT(BinaryTree<? extends T> t, T[][] contents) {
        for (int i = 0; i <= contents.length; i++) {
            int nj = (int) Math.pow(2, i);
            for (int j = 0; j < nj; j++) {
                int h = (int) Math.pow(2, i - 1);
                int k = j;
                BinaryTree<?> node = t;

                while (h > 0 && node != null) {
                    if (k >= h) node = node.getRight();
                    else node = node.getLeft();
                    k = k % h;
                    h /= 2;
                }

                // Compare expected and actual structure
                if ((i == contents.length || contents[i][j] == null) && node != null) {
                    fail("Row " + i + " position " + j +
                         " should be null but found data: " + node.getData());
                } else if (i < contents.length && contents[i][j] != null) {
                    if (node == null) {
                        fail("Row " + i + " position " + j +
                             " should be " + contents[i][j] + " but found null");
                    } else {
                        assertEquals("Row " + i + " position " + j + 
                                     " expected " + contents[i][j] + 
                                     " but got " + node.getData(),
                                     contents[i][j], node.getData());
                    }
                }
            }
        }
    }

    // Sample tests...
    @Test
    /**
     * Test for correct insertion placement in BST
     */
    public void testBSTInsertions() {
        Integer[][] gt1 = {{5}};
        Integer[][] gt2 = {{5},{null,7}};

        BST<Integer> tree = new BST<>(5);
        verifyBT(tree, gt1);

        tree.insert(7);
        verifyBT(tree, gt2);
    }

    @Test
    /**
     * Checks to see if lookup() gets the right elements
     */
    public void testBSTLookup() {

        Integer[][] gt1 = {{5},{null,7}};
        Integer[][] gt3 = {{7}};

        BST<Integer> tree = new BST<>(5);
        tree.setRight(new BST(7));
        verifyBT(tree, gt1);

        verifyBT((tree.lookup(7)), gt3);

    }

    @Test
    /**
     * Checks to see if lookup() returns null when element cannot be found
     */
    public void testBSTLookupNull() {

        Integer[][] gt1 = {{7}};
        Integer[][] gtn = {{null}};

        BST<Integer> tree = new BST<>(7);
        verifyBT(tree, gt1);

        verifyBT((tree.lookup(5)), gtn);

    }

    @Test
    /**
     * Checks to see if deleteWithCopyLeft() returns null when element cannot be found
     */
    public void testBSTDelete() {

        Integer[][] gt1 = {{11},{4,12}};
        Integer[][] gt2 = {{4},{null, 12}};

        BST<Integer> tree = new BST<Integer>(11);
        tree.insert(12);
        tree.insert(4);
        verifyBT(tree, gt1);
        verifyBT(tree.deleteWithCopyLeft(11), gt2);
    }

    @Test
    /**
     * Checks to see if rotateLeft() returns the correct tree
     */
    public void testBSTRotateLeft() {

        Integer[][] before = {
            {12},
            {6, 18},
            {3, 9, 16, 24},
            {null, null, null, null, 15, 17, null, null}
        };
        Integer[][] after = {
            {18},
            {12, 24},
            {6, 16, null, null},
            {3, 9, 15, 17, null, null, null, null}
        };

        BST<Integer> tree = new BST<Integer>(12);
        tree.insert(6);
        tree.insert(18);
        tree.insert(3);
        tree.insert(9);
        tree.insert(16);
        tree.insert(24);
        tree.insert(15);
        tree.insert(17);

        verifyBT(tree, before);

        tree = tree.rotateLeft();

        verifyBT(tree, after);
    }

    @Test
    /**
     * Checks to see if rotateRight() returns the correct tree
     */
    public void testBSTRotateRight() {

        Integer[][] before = {
            {12},
            {6, 18},
            {3, 9, 16, 24},
            {null, null, null, null, 15, 17, null, null}
        };
        Integer[][] after = {
            {6},
            {3, 12},
            {null, null, 9, 18},
            {null, null, null, null, null, null, 16, 24}
        };

        BST<Integer> tree = new BST<Integer>(12);
        tree.insert(6);
        tree.insert(18);
        tree.insert(3);
        tree.insert(9);
        tree.insert(16);
        tree.insert(24);
        tree.insert(15);
        tree.insert(17);

        verifyBT(tree, before);

        tree = tree.rotateRight();

        verifyBT(tree, after);
    }
}