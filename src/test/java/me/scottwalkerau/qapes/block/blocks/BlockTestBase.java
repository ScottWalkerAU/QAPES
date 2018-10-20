package me.scottwalkerau.qapes.block.blocks;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import org.junit.Assert;

import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

public class BlockTestBase {

    protected long monitor = 0;

    protected AlgorithmNode fromBlocks(BlockType type, Block... children) {
        AlgorithmNode node = new AlgorithmNode(null, type.getBlock());
        for (int i = 0; i < children.length; i++) {
            node.setChild(i, children[i]);
        }
        return node;
    }

    protected AlgorithmNode fromNodes(BlockType type, AlgorithmNode... children) {
        AlgorithmNode node = new AlgorithmNode(null, type.getBlock());
        for (int i = 0; i < children.length; i++) {
            node.setChild(i, children[i]);
        }
        return node;
    }

    protected AlgorithmNode makeConstant(long amount) {
        Block block = new Block(NUMBER) {
            @Override
            protected Object evaluate() {
                return amount;
            }
        };
        return new AlgorithmNode(null, block);
    }

    protected AlgorithmNode makeAdder(long amount) {
        Block block = new Block(VOID) {
            @Override
            protected Object evaluate() {
                return monitor += amount;
            }
        };
        return new AlgorithmNode(null, block);
    }

    protected AlgorithmNode makeFail() {
        Block block = new Block(VOID) {
            @Override
            protected Object evaluate() {
                Assert.fail("Node should not be evaluated");
                return null;
            }
        };
        return new AlgorithmNode(null, block);
    }

}