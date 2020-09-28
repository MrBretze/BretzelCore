package fr.bretzel.bcore.block;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class BBlock implements Block
{
    /*
     * START OF STATIC MEMBERS
     */

    //From PaperMC

    private final Block block;

    public BBlock(Block block)
    {
        this.block = block;
    }

    public BBlock(long key, World world)
    {
        int x, y, z;
        x = getBlockKeyX(key);
        y = getBlockKeyY(key);
        z = getBlockKeyZ(key);
        this.block = world.getBlockAt(x, y, z);
    }

    public BBlock(Location location)
    {
        this.block = location.getBlock();
    }

    /*
     * END OF STATIC MEMBERS
     */

    /**
     * Returns the specified block coordinates packed into a long value
     * <p>
     * The return value can be computed as follows:
     * <br>
     * {@code long value = ((long)x & 0x7FFFFFF) | (((long)z & 0x7FFFFFF) << 27) | ((long)y << 54);}
     * </p>
     *
     * <p>
     * And may be unpacked as follows:
     * <br>
     * {@code int x = (int) ((packed << 37) >> 37);}
     * <br>
     * {@code int y = (int) (packed >>> 54);}
     * <br>
     * {@code int z = (int) ((packed << 10) >> 37);}
     * </p>
     *
     * @return This block's x, y, and z coordinates packed into a long value
     */
    protected long getBlockKey(int x, int y, int z)
    {
        return ((long) x & 0x7FFFFFF) | (((long) z & 0x7FFFFFF) << 27) | ((long) y << 54);
    }

    /**
     * Returns the x component from the packed value.
     *
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @return The x component from the packed value.
     * @see BBlock#getBlockKey(int, int, int)
     */
    protected int getBlockKeyX(long packed)
    {
        return (int) ((packed << 37) >> 37);
    }

    /**
     * Returns the y component from the packed value.
     *
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @return The y component from the packed value.
     * @see BBlock#getBlockKey(int, int, int)
     */
    protected int getBlockKeyY(long packed)
    {
        return (int) (packed >>> 54);
    }

    /**
     * Returns the z component from the packed value.
     *
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @return The z component from the packed value.
     * @see BBlock#getBlockKey(int, int, int)
     */
    protected int getBlockKeyZ(long packed)
    {
        return (int) ((packed << 10) >> 37);
    }

    /**
     * @return The Bukkit Block
     */
    public Block getBlock()
    {
        return block;
    }

    /**
     * #From PaperMC
     *
     * @return a compressed coordinate
     */
    public long getBlockKey()
    {
        return getBlockKey(getX(), getY(), getZ());
    }

    /*
     * DEFAULT OF BUKKIT
     */

    /**
     * Gets the metadata for this block
     *
     * @return block specific metadata
     * @deprecated Magic value
     */
    @Override
    public byte getData()
    {
        return block.getData();
    }

    /**
     * Gets the complete block data for this block
     *
     * @return block specific data
     */
    @Override
    public BlockData getBlockData()
    {
        return block.getBlockData();
    }

    /**
     * Sets the complete data for this block
     *
     * @param data new block specific data
     */
    @Override
    public void setBlockData(BlockData data)
    {
        block.setBlockData(data);
    }

    /**
     * Gets the block at the given offsets
     *
     * @param modX X-coordinate offset
     * @param modY Y-coordinate offset
     * @param modZ Z-coordinate offset
     * @return Block at the given offsets
     */
    @Override
    public Block getRelative(int modX, int modY, int modZ)
    {
        return block.getRelative(modX, modY, modZ);
    }

    /**
     * Gets the block at the given face
     * <p>
     * This method is equal to getRelative(face, 1)
     *
     * @param face Face of this block to return
     * @return Block at the given face
     * @see #getRelative(BlockFace, int)
     */
    @Override
    public Block getRelative(BlockFace face)
    {
        return block.getRelative(face);
    }

    /**
     * Gets the block at the given distance of the given face
     * <p>
     * For example, the following method places water at 100,102,100; two
     * blocks above 100,100,100.
     *
     * <pre>
     * Block block = world.getBlockAt(100, 100, 100);
     * Block shower = block.getRelative(BlockFace.UP, 2);
     * shower.setType(Material.WATER);
     * </pre>
     *
     * @param face     Face of this block to return
     * @param distance Distance to get the block at
     * @return Block at the given face
     */
    @Override
    public Block getRelative(BlockFace face, int distance)
    {
        return block.getRelative(face, distance);
    }

    /**
     * Gets the type of this block
     *
     * @return block type
     */
    @Override
    public Material getType()
    {
        return block.getType();
    }

    /**
     * Sets the type of this block
     *
     * @param type Material to change this block to
     */
    @Override
    public void setType(Material type)
    {
        block.setType(type);
    }

    /**
     * Gets the light level between 0-15
     *
     * @return light level
     */
    @Override
    public byte getLightLevel()
    {
        return block.getLightLevel();
    }

    /**
     * Get the amount of light at this block from the sky.
     * <p>
     * Any light given from other sources (such as blocks like torches) will
     * be ignored.
     *
     * @return Sky light level
     */
    @Override
    public byte getLightFromSky()
    {
        return block.getLightFromSky();
    }

    /**
     * Get the amount of light at this block from nearby blocks.
     * <p>
     * Any light given from other sources (such as the sun) will be ignored.
     *
     * @return Block light level
     */
    @Override
    public byte getLightFromBlocks()
    {
        return block.getLightFromBlocks();
    }

    /**
     * Gets the world which contains this Block
     *
     * @return World containing this block
     */
    @Override
    public World getWorld()
    {
        return block.getWorld();
    }

    /**
     * Gets the x-coordinate of this block
     *
     * @return x-coordinate
     */
    @Override
    public int getX()
    {
        return block.getX();
    }

    /**
     * Gets the y-coordinate of this block
     *
     * @return y-coordinate
     */
    @Override
    public int getY()
    {
        return block.getY();
    }

    /**
     * Gets the z-coordinate of this block
     *
     * @return z-coordinate
     */
    @Override
    public int getZ()
    {
        return block.getZ();
    }

    /**
     * Gets the Location of the block
     *
     * @return Location of block
     */
    @Override
    public Location getLocation()
    {
        return block.getLocation();
    }

    /**
     * Stores the location of the block in the provided Location object.
     * <p>
     * If the provided Location is null this method does nothing and returns
     * null.
     *
     * @param loc the location to copy into
     * @return The Location object provided or null
     */
    @Override
    public Location getLocation(Location loc)
    {
        return block.getLocation(loc);
    }

    /**
     * Gets the chunk which contains this block
     *
     * @return Containing Chunk
     */
    @Override
    public Chunk getChunk()
    {
        return block.getChunk();
    }

    /**
     * Sets the complete data for this block
     *
     * <br>
     * Note that applyPhysics = false is not in general safe. It should only be
     * used when you need to avoid triggering a physics update of neighboring
     * blocks, for example when creating a {@link Bisected} block. If you are
     * using a custom populator, then this parameter may also be required to
     * prevent triggering infinite chunk loads on border blocks. This method
     * should NOT be used to "hack" physics by placing blocks in impossible
     * locations. Such blocks are liable to be removed on various events such as
     * world upgrades. Furthermore setting large amounts of such blocks in close
     * proximity may overload the server physics engine if an update is
     * triggered at a later point. If this occurs, the resulting behavior is
     * undefined.
     *
     * @param data         new block specific data
     * @param applyPhysics false to cancel physics from the changed block
     */
    @Override
    public void setBlockData(BlockData data, boolean applyPhysics)
    {
        block.setBlockData(data, applyPhysics);
    }

    /**
     * Sets the type of this block
     *
     * <br>
     * Note that applyPhysics = false is not in general safe. It should only be
     * used when you need to avoid triggering a physics update of neighboring
     * blocks, for example when creating a {@link Bisected} block. If you are
     * using a custom populator, then this parameter may also be required to
     * prevent triggering infinite chunk loads on border blocks. This method
     * should NOT be used to "hack" physics by placing blocks in impossible
     * locations. Such blocks are liable to be removed on various events such as
     * world upgrades. Furthermore setting large amounts of such blocks in close
     * proximity may overload the server physics engine if an update is
     * triggered at a later point. If this occurs, the resulting behavior is
     * undefined.
     *
     * @param type         Material to change this block to
     * @param applyPhysics False to cancel physics on the changed block.
     */
    @Override
    public void setType(Material type, boolean applyPhysics)
    {
        block.setType(type, applyPhysics);
    }

    /**
     * Gets the face relation of this block compared to the given block.
     * <p>
     * For example:
     * <pre>{@code
     * Block current = world.getBlockAt(100, 100, 100);
     * Block target = world.getBlockAt(100, 101, 100);
     *
     * current.getFace(target) == BlockFace.Up;
     * }</pre>
     * <br>
     * If the given block is not connected to this block, null may be returned
     *
     * @param block Block to compare against this block
     * @return BlockFace of this block which has the requested block, or null
     */
    @Override
    public BlockFace getFace(Block block)
    {
        return block.getFace(block);
    }

    /**
     * Captures the current state of this block. You may then cast that state
     * into any accepted type, such as Furnace or Sign.
     * <p>
     * The returned object will never be updated, and you are not guaranteed
     * that (for example) a sign is still a sign after you capture its state.
     *
     * @return BlockState with the current state of this block.
     */
    @Override
    public BlockState getState()
    {
        return block.getState();
    }

    /**
     * Returns the biome that this block resides in
     *
     * @return Biome type containing this block
     */
    @Override
    public Biome getBiome()
    {
        return block.getBiome();
    }

    /**
     * Sets the biome that this block resides in
     *
     * @param bio new Biome type for this block
     */
    @Override
    public void setBiome(Biome bio)
    {
        block.setBiome(bio);
    }

    /**
     * Returns true if the block is being powered by Redstone.
     *
     * @return True if the block is powered.
     */
    @Override
    public boolean isBlockPowered()
    {
        return block.isBlockPowered();
    }

    /**
     * Returns true if the block is being indirectly powered by Redstone.
     *
     * @return True if the block is indirectly powered.
     */
    @Override
    public boolean isBlockIndirectlyPowered()
    {
        return block.isBlockIndirectlyPowered();
    }

    /**
     * Returns true if the block face is being powered by Redstone.
     *
     * @param face The block face
     * @return True if the block face is powered.
     */
    @Override
    public boolean isBlockFacePowered(BlockFace face)
    {
        return block.isBlockFacePowered(face);
    }

    /**
     * Returns true if the block face is being indirectly powered by Redstone.
     *
     * @param face The block face
     * @return True if the block face is indirectly powered.
     */
    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face)
    {
        return block.isBlockFaceIndirectlyPowered(face);
    }

    /**
     * Returns the redstone power being provided to this block face
     *
     * @param face the face of the block to query or BlockFace.SELF for the
     *             block itself
     * @return The power level.
     */
    @Override
    public int getBlockPower(BlockFace face)
    {
        return block.getBlockPower(face);
    }

    /**
     * Returns the redstone power being provided to this block
     *
     * @return The power level.
     */
    @Override
    public int getBlockPower()
    {
        return block.getBlockPower();
    }

    /**
     * Checks if this block is empty.
     * <p>
     * A block is considered empty when {@link #getType()} returns {@link
     * Material#AIR}.
     *
     * @return true if this block is empty
     */
    @Override
    public boolean isEmpty()
    {
        return block.isEmpty();
    }

    /**
     * Checks if this block is liquid.
     * <p>
     * A block is considered liquid when {@link #getType()} returns {@link
     * Material#WATER} or {@link Material#LAVA}.
     *
     * @return true if this block is liquid
     */
    @Override
    public boolean isLiquid()
    {
        return block.isLiquid();
    }

    /**
     * Gets the temperature of this block.
     *
     * @return Temperature of this block
     */
    @Override
    public double getTemperature()
    {
        return block.getTemperature();
    }

    /**
     * Gets the humidity of the biome of this block
     *
     * @return Humidity of this block
     */
    @Override
    public double getHumidity()
    {
        return block.getHumidity();
    }

    /**
     * Returns the reaction of the block when moved by a piston
     *
     * @return reaction
     */
    @Override
    public PistonMoveReaction getPistonMoveReaction()
    {
        return block.getPistonMoveReaction();
    }

    /**
     * Breaks the block and spawns items as if a player had digged it regardless
     * of the tool.
     *
     * @return true if the block was destroyed
     */
    @Override
    public boolean breakNaturally()
    {
        return block.breakNaturally();
    }

    /**
     * Breaks the block and spawns items as if a player had digged it with a
     * specific tool
     *
     * @param tool The tool or item in hand used for digging
     * @return true if the block was destroyed
     */
    @Override
    public boolean breakNaturally(ItemStack tool)
    {
        return block.breakNaturally(tool);
    }

    /**
     * Returns a list of items which would drop by destroying this block
     *
     * @return a list of dropped items for this type of block
     */
    @Override
    public Collection<ItemStack> getDrops()
    {
        return block.getDrops();
    }

    /**
     * Returns a list of items which would drop by destroying this block with
     * a specific tool
     *
     * @param tool The tool or item in hand used for digging
     * @return a list of dropped items for this type of block
     */
    @Override
    public Collection<ItemStack> getDrops(ItemStack tool)
    {
        return block.getDrops(tool);
    }

    /**
     * Returns a list of items which would drop by the entity destroying this
     * block with a specific tool
     *
     * @param tool   The tool or item in hand used for digging
     * @param entity the entity destroying the block
     * @return a list of dropped items for this type of block
     */
    @Override
    public Collection<ItemStack> getDrops(ItemStack tool, Entity entity)
    {
        return block.getDrops(tool, entity);
    }

    /**
     * Checks if this block is passable.
     * <p>
     * A block is passable if it has no colliding parts that would prevent
     * players from moving through it.
     * <p>
     * Examples: Tall grass, flowers, signs, etc. are passable, but open doors,
     * fence gates, trap doors, etc. are not because they still have parts that
     * can be collided with.
     *
     * @return <code>true</code> if passable
     */
    @Override
    public boolean isPassable()
    {
        return block.isPassable();
    }

    /**
     * Performs a ray trace that checks for collision with this specific block
     * in its current state using its precise collision shape.
     *
     * @param start              the start location
     * @param direction          the ray direction
     * @param maxDistance        the maximum distance
     * @param fluidCollisionMode the fluid collision mode
     * @return the ray trace hit result, or <code>null</code> if there is no hit
     */
    @Override
    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode
            fluidCollisionMode)
    {
        //TODO: Adapts to BCore RayTrace
        return block.rayTrace(start, direction, maxDistance, fluidCollisionMode);
    }

    /**
     * Gets the approximate bounding box for this block.
     * <p>
     * This isn't exact as some blocks {@link Stairs}
     * contain many bounding boxes to establish their complete form.
     * <p>
     * Also, the box may not be exactly the same as the collision shape (such as
     * cactus, which is 16/16 of a block with 15/16 collisional bounds).
     * <p>
     * This method will return an empty bounding box if the geometric shape of
     * the block is empty (such as air blocks).
     *
     * @return the approximate bounding box of the block
     */
    @Override
    public BoundingBox getBoundingBox()
    {
        //TODO: Adapts to BCore RayTrace
        return block.getBoundingBox();
    }

    /**
     * Sets a metadata value in the implementing object's metadata store.
     *
     * @param metadataKey      A unique key to identify this metadata.
     * @param newMetadataValue The metadata value to apply.
     * @throws IllegalArgumentException If value is null, or the owning plugin
     *                                  is null
     */
    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
    {
        block.setMetadata(metadataKey, newMetadataValue);
    }

    /**
     * Returns a list of previously set metadata values from the implementing
     * object's metadata store.
     *
     * @param metadataKey the unique metadata key being sought.
     * @return A list of values, one for each plugin that has set the
     * requested value.
     */
    @Override
    public List<MetadataValue> getMetadata(String metadataKey)
    {
        return block.getMetadata(metadataKey);
    }

    /**
     * Tests to see whether the implementing object contains the given
     * metadata value in its metadata store.
     *
     * @param metadataKey the unique metadata key being queried.
     * @return the existence of the metadataKey within subject.
     */
    @Override
    public boolean hasMetadata(String metadataKey)
    {
        return block.hasMetadata(metadataKey);
    }

    /**
     * Removes the given metadata value from the implementing object's
     * metadata store.
     *
     * @param metadataKey  the unique metadata key identifying the metadata to
     *                     remove.
     * @param owningPlugin This plugin's metadata value will be removed. All
     *                     other values will be left untouched.
     * @throws IllegalArgumentException If plugin is null
     */
    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin)
    {
        block.removeMetadata(metadataKey, owningPlugin);
    }
}
