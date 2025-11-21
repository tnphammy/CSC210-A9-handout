/**
 * Implements binary search trees.
 *
 * @author Tammy Pham
 * @version Fall 2025
 */
public class BST<E extends Comparable<E>> extends BinaryTree<E> implements BST_Ops<E> {

    /**
     * Binary Search Tree three-value constructor
     * 
     * @param value the node's value
     * @param left  the node to the left
     * @param right the node to the right
     */
    public BST(E value, BST<E> left, BST<E> right) {
        super(value, left, right);
        // no value super()???
    }

    /**
     * Constructor for a singular node
     * 
     * @param value
     */
    public BST(E value) {
        super(value);
    }

    /** Accessor for left child */
    public BST<E> getLeft() {
        return (BST<E>) super.getLeft();
    }

    /** Accessor for right child */
    public BST<E> getRight() {
        return (BST<E>) super.getRight();
    }

    /**
     * Checks to see if a BST has a left child
     * @return true or false
     */
    public boolean hasLeft() {
        return (this.getLeft() != null);
    }

    /**
     * Checks to see if a BST has a left child
     * @return true or false
     */
    public boolean hasRight() {
        return (this.getRight() != null);
    }

    /**
     * Overrides inherited manipulator to only accept BST type
     * 
     * @param left the node to insert to the left
     */
    public void setLeft(BinaryTree<E> left) {
        if ((left == null) || (left instanceof BST<E>)) {
            super.setLeft(left);
        } else {
            throw new UnsupportedOperationException("Only BST Type children are allowed.");
        }
    }

    /**
     * Overrides inherited manipulator to only accept BST type
     * 
     * @param right the node to insert to the right
     */
    public void setRight(BinaryTree<E> right) {
        if ((right == null) || (right instanceof BST<E>)) {
            super.setRight(right);
        } else {
            throw new UnsupportedOperationException("Only BST Type children are allowed.");
        }
    }

    /**
     * Returns the node of the given element, or null if not found
     *
     * @param query The element to search
     * @return the node of the given element, or null if not found
     */
    public BST<E> lookup(E data) {
        // Base cases
        if (this.getData() == null) {
            return null; /* failed */
        }
        if (this.getData() == data) {
            return this; /* success */
        }
        // Recursive step
        if (data.compareTo(this.getData()) < 0) { /* data is smaller */
            if (this.hasLeft()) {
                return this.getLeft().lookup(data);
            }
            else {
                return null;
            }
        } else {
            if(this.hasRight()) {
                return this.getRight().lookup(data); /* data is larger */
            }
            else {
                return null;
            }
        }
    }

    /**
     * Inserts a new node into the tree
     *
     * @param data The element to insert
     */
    public void insert(E data) {
        if (this.getData() == data) {
            return;
        }
        // Recursive step
        if (data.compareTo(this.getData()) < 0) { /* data is smaller */
            if (this.getLeft() == null) {
                BST<E> newLeft = new BST<E>(data);
                this.setLeft(newLeft);
            }
            this.getLeft().insert(data);
        } else {
            if (this.getRight() == null) {
                BST<E> newRight = new BST<E>(data);
                this.setRight(newRight);
            }
        }
        return;

    }

    /**
     * Returns the parent node of the given element, or null if not found
     *
     * @param query The element whose parent to search for
     * @return the parent node of the given element, or null if not found
     */
    public BST<E> lookupParent(E data) {
        // Base cases
        if (this.getData() == null || this.getData() == data) {
            return null; /* failed or is root */
        }
        if ((this.getLeft() != null) && (this.getLeft().getData() == data)) {
            return this; /* success */
        } else if ((this.getRight() != null) && (this.getRight().getData() == data)) {
            return this; /* success */
        }
        // Recursive step
        if (data.compareTo(this.getData()) < 0) { /* data is smaller */
            return this.getLeft().lookupParent(data);
        } else {
            return this.getRight().lookupParent(data); /* data is larger */
        }
    }

    /**
     * Deletes the specified element from the tree
     * Returns the modified tree because the root node
     * may have changed
     * 
     * @param evictee The element to delete
     * @return tree as modified
     */
    public BST<E> deleteWithCopyLeft(E evictee) {
        // Edge case
        if (evictee == null) {
            return null;
        }
        BST evicteeNode = lookup(evictee);

        // Happy Case: Evictee has no children
        if (evicteeNode.isLeaf()) {
            BST evicteeParent = lookupParent(evictee); /* get parent for deletion */
            // Delete the evictee 
            if ((evicteeParent.hasLeft()) && (evicteeParent.getLeft().getData() == evictee)) {
                evicteeParent.setLeft(null);
                return this;
            } else if ((evicteeParent.hasRight()) && (evicteeParent.getRight().getData() == evictee)) {
                evicteeParent.setRight(null);
                return this;
            }
        }
        // Complex Case: Evictee has children
        else {
            // 1. Replace evictee with best candidate
            // Does evictee have left part?
            if (evicteeNode.hasLeft()) {
                // Does left part have a rightmost?
                if (evicteeNode.getLeft().hasRight()) {
                    // YES
                    // 1. Find rightmost parent
                    BST temp = evicteeNode.getLeft();
                    while (temp.getRight().hasRight()) {
                        temp = temp.getRight();
                    }
                    System.out.println("replacement parent is: " + temp.getData());
                    // 2. Replace
                    E replacementData = (E)temp.getRight().getData();
                    evicteeNode.setData(replacementData);
                    // 3. Delete
                    temp.setRight(null);
                }
                else {
                    // NO
                    evicteeNode.setData(evicteeNode.getLeft().getData()); /* use left data */
                    evicteeNode.setLeft(null); 
                }
            }
            else {
                evicteeNode.setData(evicteeNode.getRight().getData()); /* use right data */
                evicteeNode.setRight(null); 
            }
        }
        return this;
    }

    /**
     * Apply left rotation
     * Returns the modified tree because the root node
     * may have changed
     *
     * @return tree as modified
     */
    public BST<E> rotateLeft() {
        return new BST<E>(getData());
    }

    /**
     * Apply right rotation
     * Returns the modified tree because the root node
     * may have changed
     *
     * @return tree as modified
     */
    public BST<E> rotateRight() {
        return new BST<E>(getData());
    }

    public static void main(String[] args) {
        BST<Integer> tree = new BST<Integer>(11);
        tree.insert(8);
        tree.insert(9);
        tree.insert(4);
        tree.insert(1);
        tree.insert(6);
        tree.insert(2);
        tree.insert(12);
        System.out.println(tree);
        System.out.println(tree.lookup(20));

    }

}
