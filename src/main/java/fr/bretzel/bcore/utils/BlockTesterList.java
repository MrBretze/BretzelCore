package fr.bretzel.bcore.utils;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.function.Predicate;

public class BlockTesterList extends ArrayList<Predicate<Block>>
{
    public boolean contains(Block block)
    {
        return stream().anyMatch(blockPredicate -> blockPredicate.test(block));
    }
}
