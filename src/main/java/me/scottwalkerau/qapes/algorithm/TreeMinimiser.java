package me.scottwalkerau.qapes.algorithm;

import static me.scottwalkerau.qapes.block.BlockType.*;

/**
 * Class to minimise an algorithm tree, by removing dead code and redundant expressions
 */
public class TreeMinimiser {

    // This class is a mess. Not used in production, simply here to make the output prettier and less redundant.
    // TODO if(condition) ... else if(!condition), should simplify to the true statement for second.
    // TODO nested whiles with same condition for similar reasons ^

    /** The tree we're going to minimise */
    private AlgorithmTree tree;
    /** How many passes did minimisation take */
    private int passes;

    /**
     * Constructor
     * @param tree The tree to minimise, but duplicate it
     */
    public TreeMinimiser(AlgorithmTree tree) {
        this.tree = tree.duplicate();
    }

    /**
     * Perform the minimisation
     */
    public AlgorithmTree run() {
        int passes = 1;
        // While there was a change, run simplification again
        while(simplify(tree.getRoot())) {
            passes++;
        }
        this.passes = passes;
        return tree;
    }

    /**
     * Get the resultant tree
     * @return Duplicated and minimised tree
     */
    public AlgorithmTree getTree() {
        return tree;
    }

    /**
     * Get how many passes the minimisation took
     * @return passes
     */
    public int getPasses() {
        return passes;
    }

    /**
     * Simplify a particular node
     * @param node The node to simplify
     * @return Whether a change has occurred
     */
    private boolean simplify(AlgorithmNode node) {
        switch (node.getBlockType()) {
            case NOT:
                return notBlock(node);
            case AND:
                return andBlock(node);
            case OR:
                return orBlock(node);
            case LESS_THAN:
                return lessThanBlock(node);
            case GREATER_THAN:
                return greaterThanBlock(node);
            case IF:
                return ifBlock(node);
            case IF_ELSE:
                return ifElseBlock(node);
            case WHILE:
                return whileBlock(node);
            case VOID_LIST:
                return voidListBlock(node);
            default:
                return simplifyChildren(node);
        }
    }

    /**
     * If it's not a known block, we'll just simplify the children
     * @param node Parent node
     * @return If there was a change
     */
    private boolean simplifyChildren(AlgorithmNode node) {
        boolean updated = false;
        for (AlgorithmNode child : node.getChildren())
            updated |= simplify(child);
        return updated;
    }

    // -- Blocks --

    private boolean notBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);

        switch (childA.getBlockType()) {
            // Simply swap around TRUE/FALSE
            case TRUE:
                node.overwriteWith(FALSE);
                return true;
            case FALSE:
                node.overwriteWith(TRUE);
                return true;
            // Remove any NOT(NOT())
            case NOT:
                AlgorithmNode childAA = childA.getChild(0);
                node.overwriteWith(childAA);
                return true;
        }

        return simplifyChildren(node);
    }

    private boolean andBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // If one is false, it's always false
        if (childA.getBlockType() == FALSE || childB.getBlockType() == FALSE) {
            node.overwriteWith(FALSE);
            return true;
        }
        // If left is true, set with the right. If right is also true it will be dealt with on the next pass
        if (childA.getBlockType() == TRUE) {
            node.overwriteWith(childB);
            return true;
        }
        // If right is true, set with the left.
        if (childB.getBlockType() == TRUE) {
            node.overwriteWith(childA);
            return true;
        }
        // Both sides of the && are the same
        if (childA.equivalentTo(childB)) {
            node.overwriteWith(childA);
            simplify(node);
            return true;
        }
        // Two arguments are the exact opposite of each other, make it false
        if (childA.getBlockType() == NOT && childB.getBlockType() != NOT) {
            if (childA.getChild(0).equivalentTo(childB)) {
                node.overwriteWith(FALSE);
                return true;
            }
        }
        if (childA.getBlockType() != NOT && childB.getBlockType() == NOT) {
            if (childA.equivalentTo(childB.getChild(0))) {
                node.overwriteWith(FALSE);
                return true;
            }
        }

        return simplifyChildren(node);
    }

    private boolean orBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // If one is true, it's always true
        if (childA.getBlockType() == TRUE || childB.getBlockType() == TRUE) {
            node.overwriteWith(TRUE);
            return true;
        }
        // If left is false, set with the right. If right is also false it will be dealt with on the next pass
        if (childA.getBlockType() == FALSE) {
            node.overwriteWith(childB);
            simplify(node);
            return true;
        }
        // If right is false, set with the left.
        if (childB.getBlockType() == FALSE) {
            node.overwriteWith(childA);
            simplify(node);
            return true;
        }
        // Both sides of the || are the same
        if (childA.equivalentTo(childB)) {
            node.overwriteWith(childA);
            simplify(node);
            return true;
        }
        // Two arguments are the exact opposite of each other, make it true
        if (childA.getBlockType() == NOT && childB.getBlockType() != NOT) {
            if (childA.getChild(0).equivalentTo(childB)) {
                node.overwriteWith(TRUE);
                return true;
            }
        }
        if (childA.getBlockType() != NOT && childB.getBlockType() == NOT) {
            if (childA.equivalentTo(childB.getChild(0))) {
                node.overwriteWith(TRUE);
                return true;
            }
        }

        return simplifyChildren(node);
    }

    private boolean lessThanBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // Both sides of the < are the same
        if (childA.equivalentTo(childB)) {
            node.overwriteWith(FALSE);
            return true;
        }

        return simplifyChildren(node);
    }

    private boolean greaterThanBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // Both sides of the > are the same
        if (childA.equivalentTo(childB)) {
            node.overwriteWith(FALSE);
            return true;
        }

        return simplifyChildren(node);
    }

    private boolean ifBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // if (true), use the second child
        if (childA.getBlockType() == TRUE) {
            node.overwriteWith(childB);
            simplify(node);
            return true;
        }
        // if (false), or the statement is void, it's now a void statement
        if (childA.getBlockType() == FALSE || childB.getBlockType() == VOID) {
            node.overwriteWith(VOID);
            return true;
        }

        return simplifyChildren(node);
    }

    private boolean ifElseBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);
        AlgorithmNode childC = node.getChild(2);

        // if (true), use the second child
        if (childA.getBlockType() == TRUE) {
            node.overwriteWith(childB);
            simplify(node);
            return true;
        }
        // if (false), use the third child
        if (childA.getBlockType() == FALSE) {
            node.overwriteWith(childC);
            simplify(node);
            return true;
        }
        // True and false branches are the same
        if (childB.equivalentTo(childC)) {
            node.overwriteWith(childB);
            simplify(node);
            return true;
        }

        return simplifyChildren(node);
    }

    private boolean whileBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // While (false) makes no sense. Set to void and handle it on the next pass.
        // Similarly if the statement is void
        if (childA.getBlockType() == FALSE || childB.getBlockType() == VOID) {
            node.overwriteWith(VOID);
            return true;
        }

        // If it's a while with a while or if as the very first statement of the exact same condition.
        if (childB.getBlockType() == WHILE || childB.getBlockType() == IF || childB.getBlockType() == IF_ELSE) {
            AlgorithmNode childBA = childB.getChild(0);
            AlgorithmNode childBB = childB.getChild(1);
            if (childA.equivalentTo(childBA)) {
                childB.overwriteWith(childBB);
            }
        }

        // We could test for true, but that is a valid loop. The thread will just end up being terminated however.
        return simplifyChildren(node);
    }

    private boolean voidListBlock(AlgorithmNode node) {
        AlgorithmNode childA = node.getChild(0);
        AlgorithmNode childB = node.getChild(1);

        // If left is void, set it to be the right. (If right is also void, it will be taken care of on the next pass)
        if (childA.getBlockType() == VOID) {
            node.overwriteWith(childB);
            return true;
        }
        // If right is void, set it to be the left.
        if (childB.getBlockType() == VOID) {
            node.overwriteWith(childA);
            return true;
        }

        return simplifyChildren(node);
    }
}
