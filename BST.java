/**
 * Implements binary search trees.
 *
 * @author Tammy Pham
 * @version Fall 2025
 */
public class BST<E extends Comparable<E>> extends BinaryTree<E> implements BST_Ops<E> {

    /**
     * Binary Search Tree three-value constructor
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
     * @param value
     */
    public BST(E value) {
        super(value);
    }

    /** Accessor for left child */
    public BST<E> getLeft() {
        return (BST<E>)super.getLeft();
    }

    /** Accessor for right child */
    public BST<E> getRight() {
        return (BST<E>)super.getRight();
    }

    /** Overrides inherited manipulator to only accept BST type 
     * @param left the node to insert to the left
    */
    public void setLeft(BinaryTree<E> left) {
        if ((left == null) || (left instanceof BST<E>)) {
            super.setLeft(left);
        }
        else {
            throw new UnsupportedOperationException("Only BST Type children are allowed.");
        }
    }

    /** Overrides inherited manipulator to only accept BST type
     *  @param right the node to insert to the right
     */
    public void setRight(BinaryTree<E> right) {
        if ((right == null) || (right instanceof BST<E>)) {
            super.setRight(right);
        }
        else {
            throw new UnsupportedOperationException("Only BST Type children are allowed.");
        }
    }

    /**
     *  Returns the node of the given element, or null if not found
     *
     *  @param query The element to search
     *  @return the node of the given element, or null if not found
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
            this.getLeft().lookup(data);
        }
        else {
            this.getRight().lookup(data); /* data is larger */
        }
        return this;
    }

    /**
     *  Inserts a new node into the tree
     *
     *  @param data The element to insert
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
        }
        else {
            if (this.getLeft() == null) {
                BST<E> newRight = new BST<E>(data);
                this.setRight(newRight);
            }
            else {
                this.getRight().insert(data); /* data is larger */
            }
        }
        return;

    }

    /**
     *  Deletes the specified element from the tree
     *  Returns the modified tree because the root node 
     *  may have changed
     *  
     *  @param evictee The element to delete
     *  @return tree as modified
     */
    public BST<E> deleteWithCopyLeft(E evictee) {
        return new BST<E>(evictee);
    }

    /**
     *  Apply left rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateLeft() {
        return new BST<E>(getData());
    }

    /**
     *  Apply right rotation
     *  Returns the modified tree because the root node 
     *  may have changed
     *
     *  @return tree as modified
     */
    public BST<E> rotateRight() {
        return new BST<E>(getData());
    }

    // public static void main(String[] args) {
    //     BST<Integer> tree = new BST<Integer>(5);
    //     tree.insert(7);
    //     System.out.println(tree);
    // }

}
