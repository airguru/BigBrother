package me.taylorkelly.bigbrother.datablock.explosions;

import java.util.HashMap;
import java.util.List;
import me.taylorkelly.bigbrother.datablock.BBDataBlock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TNTLogger {

    private static final double THRESHOLD = 1.0;
    private static HashMap<Location, String> tntMap = new HashMap<Location, String>();

    public static void log(String player, Block block) {
        tntMap.put(block.getLocation(), player);
        System.out.println("Logging... " + block.getLocation().toString());

    }

    public static void createTNTDataBlock(List<Block> blockList, Location location) {
        System.out.println("Searching for... " + location.toString());
        String player = BBDataBlock.ENVIRONMENT;
        Location bestLocation = null;
        double bestDistance = THRESHOLD;
        for (Location loc : tntMap.keySet()) {
            double distance = distance(loc, location);
            System.out.println(distance);
            if (distance < bestDistance) {
                bestLocation = loc;
                bestDistance = distance;
                break;
            }
        }
        if (bestLocation != null) {
            player = tntMap.get(bestLocation);
            System.out.println("found from" + player);
        } else {
            System.out.println("not found");
        }
        for (Block block : blockList) {
            BBDataBlock dataBlock = new TNTExplosion(player, block, location.getWorld().getName());
            dataBlock.send();
            if (block.getType() == Material.TNT) {
                TNTLogger.log(player, block);
            }
        }
    }

    public static double distance(Location from, Location to) {
        return Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2) + Math.pow(from.getZ() - to.getZ(), 2));
    }
}
