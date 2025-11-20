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
            System.out.println("I found you!" + this.getData());
            return this; /* success */
        }
        // Recursive step
        if (data.compareTo(this.getData()) < 0) { /* data is smaller */
            return this.getLeft().lookup(data);
        } else {
            return this.getRight().lookup(data); /* data is larger */
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
        // Happy Case: Evictee has no children
        BST evicteeNode = lookup(evictee);
        System.out.println("evicteeNode is: " + evicteeNode);
        if (evicteeNode.isLeaf()) {
            System.out.println("I was here 1");
            BST evicteeParent = lookupParent(evictee);
            System.out.println("evictee's parent is: " + evicteeParent.getData());
            // Locate where the evictee is relative to its parent -> Delete
            if ((evicteeParent.getLeft() != null) && (evicteeParent.getLeft().getData() == evictee)) {
                evicteeParent.setLeft(null);
                return this;
            } else if ((evicteeParent.getRight() != null) && (evicteeParent.getRight().getData() == evictee)) {
                evicteeParent.setRight(null);
                return this;
            }
        }
        // Complex Case: Evictee has children
        else {
            // 1. Replace evictee with best candidate
            // 1.1 Find rightmost leaf of left side (stops at its parent called 'temp')
            BST temp = evicteeNode.getLeft();
            while (temp.getRight().getRight() != null) {
                temp = temp.getRight();
            }
            System.out.println("the replacement's parent is: " + temp.getData());
            E replacementData = (E) temp.getRight().getData();
            evicteeNode.setData(replacementData);
            // 2. Erase temp's old link to 'replacement' Node
            temp.setRight(null);
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
        System.out.println(tree);
        //System.out.println(tree.lookup(8));
        System.out.println(tree.lookupParent(9).getData());
        //System.out.println(tree.deleteWithCopyLeft(9));
    }

}
