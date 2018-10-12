package me.scottwalkerau.qapes.block;

import me.scottwalkerau.qapes.block.blocks.*;
import me.scottwalkerau.qapes.block.blocks.bool.*;
import me.scottwalkerau.qapes.block.blocks.control.*;
import me.scottwalkerau.qapes.block.blocks.qap.*;
import me.scottwalkerau.qapes.block.blocks.qap.facility.*;

/**
 * All the available blocks
 * @author Scott Walker
 */
public enum BlockType {

    // -- Generic blocks --

    // Boolean
    NOT(new NotBlock()),
    AND(new AndBlock()),
    OR(new OrBlock()),
    //EQUAL(new EqualBlock()), // DISABLED
    LESS_THAN(new LessThanBlock()),
    GREATER_THAN(new GreaterThanBlock()),

    // Void controls
    IF(new IfBlock()),
    IF_ELSE(new IfElseBlock()),
    WHILE(new WhileBlock()),

    // Void statements
    VOID_LIST(new VoidListBlock()),

    // Blocks that can't be automatically selected (Used in tree minimisation over multiple passes)
    VOID(new VoidBlock(), false),
    TRUE(new TrueBlock(), false),
    FALSE(new FalseBlock(), false),

    // -- QAP specific blocks --

    // Functions
    SWAP_FACILITY(new SwapFacilityBlock()),

    // Facility pair terminals
    FIRST_BAD_MOVE(new FirstBadMoveBlock()),
    FIRST_GOOD_MOVE(new FirstGoodMoveBlock()),
    GREEDY_MOVE(new GreedyMoveBlock()),
    RANDOM_FACILITY_PAIR(new RandomFacilityPairBlock()),

    // Terminals
    GOOD_MOVE_EXISTS(new GoodMoveExistsBlock()),
    LAST_MOVE_IMPROVED(new LastMoveImprovedBlock()),
    BEST_KNOWN_VALUE(new BestKnownValueBlock()),
    FITNESS(new FitnessBlock()),
    SIZE(new SizeBlock());

    // -- Internal enum data --

    /** The block object this references */
    private final Block block;
    /** If the block is to be automatically selected during generation */
    private final boolean catalogued;

    /**
     * Constructor for a catalogued block
     * @param block Block to catalogue
     */
    BlockType(Block block) {
        this(block, true);
    }

    /**
     * Constructor
     * @param block Block to use
     * @param catalogued If the block is catalogued
     */
    BlockType(Block block, boolean catalogued) {
        this.block = block;
        this.catalogued = catalogued;
        block.setBlockType(this);
    }

    /**
     * Get the block
     * @return Block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * If the block is catalogued
     * @return True if catalogued
     */
    public boolean isCatalogued() {
        return catalogued;
    }
}
