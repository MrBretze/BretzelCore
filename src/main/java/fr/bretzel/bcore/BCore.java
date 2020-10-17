package fr.bretzel.bcore;

import com.google.common.collect.ImmutableList;
import fr.bretzel.bcore.listener.BCoreListener;
import fr.bretzel.bcore.nms.raytrace.BlockCollisionOption;
import fr.bretzel.bcore.nms.raytrace.FluidCollisionOption;
import fr.bretzel.bcore.player.BPlayer;
import fr.bretzel.bcore.raytracing.RayTrace;
import fr.bretzel.bcore.raytracing.RayTraceResult;
import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.MultipleBoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.PointingBox;
import fr.bretzel.bcore.utils.maths.Edge;
import fr.bretzel.bcore.utils.maths.Point3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class BCore extends JavaPlugin
{
    public static final ArrayList<Player> taskHashMap = new ArrayList<>();
    public static BCore INSTANCE;
    private static String version;
    private final ArrayList<Long> arrayList = new ArrayList<>();

    public BCore()
    {
        String[] versionArray = Bukkit.getServer().getClass().getName().replace('.', ',').split(",");
        if (versionArray.length >= 4)
        {
            version = versionArray[3];
        } else
        {
            version = "";
        }

        INSTANCE = this;
    }

    public static String getVersion()
    {
        return version;
    }

    @Override
    public void onEnable()
    {
        System.out.println("Registering all Listener");
        BCoreListener.registerAllListener();

        for (Player player : ImmutableList.copyOf(Bukkit.getOnlinePlayers()))
            BPlayer.register(player);
    }

    @Override
    public void onDisable()
    {
        BPlayer.clearCash();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (label.equalsIgnoreCase("intercept") && sender instanceof Player)
        {
            Player player = (Player) sender;
            BPlayer.getBPlayer(player);

            if (taskHashMap.contains(player))
            {
                taskHashMap.remove(player);
            } else
            {
                taskHashMap.add(player);

                Bukkit.getScheduler().runTaskTimer(this, bukkitTask ->
                {
                    if (!player.isOnline() || !taskHashMap.contains(player))
                    {
                        bukkitTask.cancel();
                        taskHashMap.remove(player);
                        return;
                    }

                    RayTraceResult result;

                    long start = System.currentTimeMillis();
                    result = RayTrace.rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), 250,
                            FluidCollisionOption.NONE, BlockCollisionOption.OUTLINE, location -> location.getBlock().getType().isAir());
                    arrayList.add((System.currentTimeMillis() - start));
                    //RayTraceResult result = RayTrace.rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 0, 200, 0.01,
                    //        location -> location.getBlock().getType().isAir());

                    player.sendTitle("", "Avg RT calcul time = " + new DecimalFormat("#.###").format(calculateAverage(arrayList)) + "ms ", 0, 10, 0);

                    /*BoundingBox boundingBox = BoundingBoxManager.get(result.getHitLocation().getBlock()).translate(result.getHitLocation().getBlock());
                    //player.sendMessage(result.getHitLocation().toString());

                    if (arrayList.size() > (20 * 5))
                        arrayList.remove(0);

                    //player.sendMessage("Hit Block = " + result.getHitLocation().getBlock().getType());

                    player.spawnParticle(Particle.REDSTONE, result.getHitLocation(), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.AQUA, 0.2F));
                    //Particle.SUSPENDED_DEPTH
                    for (Player pl : ImmutableList.copyOf(Bukkit.getOnlinePlayers()))
                    {
                        pl.spawnParticle(Particle.REDSTONE, result.getHitLocation(), 0, 0, 0, 0, 0, new Particle.DustOptions(Color.AQUA, 0.2F));
                        showHitBox(boundingBox, pl, Particle.REDSTONE, new Particle.DustOptions(Color.FUCHSIA, 0.1F));
                    }*/

                }, 0, 0);
            }
            return false;
        }
        return false;
    }

    private double calculateAverage(List<Long> marks)
    {
        Long sum = 0L;
        if (!marks.isEmpty())
        {
            for (Long mark : marks)
            {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    private void showHitBox(BoundingBox boundingBox, Player player, Particle particle, Object data)
    {
        if (boundingBox instanceof MultipleBoundingBox)
        {
            MultipleBoundingBox multipleBoundingBox = (MultipleBoundingBox) boundingBox;
            for (BoundingBox box : multipleBoundingBox.getBoundingBoxes())
                showHitBox(box, player, particle, data);
        } else if (boundingBox instanceof PointingBox)
        {
            World world = player.getWorld();
            PointingBox pointingBox = (PointingBox) boundingBox;
            for (Point3D point : pointingBox.getPoints())
            {
                player.spawnParticle(particle, new Location(world, point.getX(),
                        point.getY(),
                        point.getZ()), 1, 0, 0, 0, 0, data);
            }
        } else
        {
            for (Point3D point : boundingBox.getPoints())
                player.spawnParticle(particle, point.toLocation(player.getWorld()), 0, 0, 0, 0, 0, data);

            for (Edge edge : boundingBox.getEdges())
                drawEdge(edge, particle, data, player);
        }
    }

    private void drawEdge(Edge edge, Particle particle, Object data, Player player)
    {
        for (Location location : edge.getPointListBetween(player.getWorld()))
        {
            player.spawnParticle(particle, location, 0, 0, 0, 0, 0, data);
        }
    }
}
