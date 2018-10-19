package me.scottwalkerau.qapes.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Static class to store all blocks for easy fetching
 * @author Scott Walker
 */
public class BlockCatalogue {

    /** All blocks with their respective return type */
    private static final HashMap<ReturnType, List<Block>> blocks = new HashMap<>();
    /** All terminal blocks */
    private static final HashMap<ReturnType, List<Block>> terminalBlocks = new HashMap<>();
    /** All blocks that can have a terminal following them */
    private static final HashMap<ReturnType, List<Block>> demiTerminalBlocks = new HashMap<>();

    static {
        // Initialise blocks with empty lists
        for (ReturnType type : ReturnType.values()) {
            blocks.put(type, new ArrayList<>());
            terminalBlocks.put(type, new ArrayList<>());
            demiTerminalBlocks.put(type, new ArrayList<>());
        }

        // Catalogue the blocks accordingly
        for (BlockType type : BlockType.values()) {
            if (type.isCatalogued())
                catalogueBlock(type, type.getBlock().getReturnType());
        }

        // Add the demiTerminals (Needs to be done separately)
        for (BlockType type : BlockType.values()) {
            if (type.isCatalogued())
                catalogueDemi(type, type.getBlock().getReturnType());
        }
    }

    private static void catalogueBlock(BlockType type, ReturnType ret) {
        blocks.get(ret).add(type.getBlock());
        if (type.getBlock().isTerminal())
            terminalBlocks.get(ret).add(type.getBlock());
    }

    private static void catalogueDemi(BlockType type, ReturnType ret) {
        if (type.getBlock().isTerminal())
            return;

        for (ReturnType param : type.getBlock().getParamTypes()) {
            if (terminalBlocks.get(param).isEmpty()) {
                return;
            }
        }
        // All children are terminals, use this as the demiTerminal
        demiTerminalBlocks.get(ret).add(type.getBlock());
    }

    public static Block getRandomBlock(ReturnType returnType) {
        List<Block> list = blocks.get(returnType);
        return list.get(new Random().nextInt(list.size()));
    }

    public static Block getRandomBlockChildless(ReturnType returnType) {
        // Check if it's a terminal
        List<Block> terminals = terminalBlocks.get(returnType);
        if (!terminals.isEmpty()) {
            return terminals.get(new Random().nextInt(terminals.size()));
        }

        // Check if it's a demi terminal instead
        List<Block> demiTerminals = demiTerminalBlocks.get(returnType);
        if (!demiTerminals.isEmpty()) {
            return demiTerminals.get(new Random().nextInt(demiTerminals.size()));
        }

        // Neither terminal nor demi
        return getRandomBlock(returnType);
    }
}
