package com.foxxite.oxidiser;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public final class Oxidiser extends JavaPlugin {

    static final List<Material> COPPER_BLOCKS = Arrays.asList(
            Material.COPPER_BLOCK,
            Material.EXPOSED_COPPER,
            Material.WEATHERED_COPPER,
            Material.OXIDIZED_COPPER);

    static final List<Material> CUT_COPPER_BLOCKS = Arrays.asList(
            Material.CUT_COPPER,
            Material.EXPOSED_CUT_COPPER,
            Material.WEATHERED_CUT_COPPER,
            Material.OXIDIZED_CUT_COPPER);

    static final List<Material> COPPER_STAIRS = Arrays.asList(
            Material.CUT_COPPER_STAIRS,
            Material.EXPOSED_CUT_COPPER_STAIRS,
            Material.WEATHERED_CUT_COPPER_STAIRS,
            Material.OXIDIZED_CUT_COPPER_STAIRS);

    static final List<Material> COPPER_SLABS = Arrays.asList(
            Material.CUT_COPPER_SLAB,
            Material.EXPOSED_CUT_COPPER_SLAB,
            Material.WEATHERED_CUT_COPPER_SLAB,
            Material.OXIDIZED_CUT_COPPER_SLAB);

    static final List<Material> COPPER_CHISELED = Arrays.asList(
            Material.CHISELED_COPPER,
            Material.EXPOSED_CHISELED_COPPER,
            Material.WEATHERED_CHISELED_COPPER,
            Material.OXIDIZED_CHISELED_COPPER);

    static final List<Material> COPPER_BULBS = Arrays.asList(
            Material.COPPER_BULB,
            Material.EXPOSED_COPPER_BULB,
            Material.WEATHERED_COPPER_BULB,
            Material.OXIDIZED_COPPER_BULB);

    static final List<Material> COPPER_DOORS = Arrays.asList(
            Material.COPPER_DOOR,
            Material.EXPOSED_COPPER_DOOR,
            Material.WEATHERED_COPPER_DOOR,
            Material.OXIDIZED_COPPER_DOOR);

    static final List<Material> COPPER_TRAPDOORS = Arrays.asList(
            Material.COPPER_TRAPDOOR,
            Material.EXPOSED_COPPER_TRAPDOOR,
            Material.WEATHERED_COPPER_TRAPDOOR,
            Material.OXIDIZED_COPPER_TRAPDOOR);

    static final List<Material> COPPER_GRATES = Arrays.asList(
            Material.COPPER_GRATE,
            Material.EXPOSED_COPPER_GRATE,
            Material.WEATHERED_COPPER_GRATE,
            Material.OXIDIZED_COPPER_GRATE);

    static final List<Material> ALL_COPPER_MATERIALS = mergeCopperLists();

    @Contract(" -> new")
    private static @NotNull List<Material> mergeCopperLists() {
        Set<Material> mergedSet = new HashSet<>();
        Collections.addAll(mergedSet, COPPER_BLOCKS.toArray(new Material[0]));
        Collections.addAll(mergedSet, CUT_COPPER_BLOCKS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_STAIRS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_SLABS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_CHISELED.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_BULBS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_DOORS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_TRAPDOORS.toArray(new Material[0]));
        Collections.addAll(mergedSet, COPPER_GRATES.toArray(new Material[0]));
        return new ArrayList<>(mergedSet);
    }



    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, oxidise, 0L, 200L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScheduler().cancelTasks(this);
    }

    Runnable oxidise = new Runnable() {
        @Override
        public void run() {
            Item[] items = new Item[0];
            World[] worlds = Bukkit.getWorlds().toArray(new World[0]);
            Random random = new Random();

            for (World world : worlds) {
                List<Item> itemList = world.getEntities().stream()
                        .filter(entity -> entity.getType() == EntityType.ITEM)
                        .map(entity -> (Item) entity)
                        .filter(item -> ALL_COPPER_MATERIALS.contains(item.getItemStack().getType()))
                        .filter(item -> item.isInRain() || item.isInWater())
                        .filter(item -> random.nextBoolean())
                        .toList();

                List<Item> itemsList = new ArrayList<>(Arrays.asList(items));
                itemsList.addAll(itemList);
                items = itemsList.toArray(new Item[0]);
            }

            for (Item item : items) {
                ItemStack itemStack = item.getItemStack();
                Material material = itemStack.getType();
                int amount = itemStack.getAmount();

                ItemStack newItemStack = new ItemStack(Material.AIR, amount);

                // Check in which collection the item belongs, then get  it's index in that collection
                // Check if there's a next material in the collection, if there is, set the new itemstack to that material
                // If there isn't, don't do anything

                if (COPPER_BLOCKS.contains(material)) {
                    int index = COPPER_BLOCKS.indexOf(material);
                    if (index + 1 < COPPER_BLOCKS.size()) {
                        newItemStack = itemStack.withType(COPPER_BLOCKS.get(index+1));
                    }
                } else if (CUT_COPPER_BLOCKS.contains(material)) {
                    int index = CUT_COPPER_BLOCKS.indexOf(material);
                    if (index + 1 < CUT_COPPER_BLOCKS.size()) {
                        newItemStack = itemStack.withType(CUT_COPPER_BLOCKS.get(index+1));
                    }
                } else if (COPPER_STAIRS.contains(material)) {
                    int index = COPPER_STAIRS.indexOf(material);
                    if (index + 1 < COPPER_STAIRS.size()) {
                        newItemStack = itemStack.withType(COPPER_STAIRS.get(index+1));
                    }
                } else if (COPPER_SLABS.contains(material)) {
                    int index = COPPER_SLABS.indexOf(material);
                    if (index + 1 < COPPER_SLABS.size()) {
                        newItemStack = itemStack.withType(COPPER_SLABS.get(index+1));
                    }
                } else if (COPPER_CHISELED.contains(material)) {
                    int index = COPPER_CHISELED.indexOf(material);
                    if (index + 1 < COPPER_CHISELED.size()) {
                        newItemStack = itemStack.withType(COPPER_CHISELED.get(index+1));
                    }
                } else if (COPPER_BULBS.contains(material)) {
                    int index = COPPER_BULBS.indexOf(material);
                    if (index + 1 < COPPER_BULBS.size()) {
                        newItemStack = itemStack.withType(COPPER_BULBS.get(index+1));
                    }
                } else if (COPPER_DOORS.contains(material)) {
                    int index = COPPER_DOORS.indexOf(material);
                    if (index + 1 < COPPER_DOORS.size()) {
                        newItemStack = itemStack.withType(COPPER_DOORS.get(index+1));
                    }
                } else if (COPPER_TRAPDOORS.contains(material)) {
                    int index = COPPER_TRAPDOORS.indexOf(material);
                    if (index + 1 < COPPER_TRAPDOORS.size()) {
                        newItemStack = itemStack.withType(COPPER_TRAPDOORS.get(index+1));
                    }
                } else if (COPPER_GRATES.contains(material)) {
                    int index = COPPER_GRATES.indexOf(material);
                    if (index + 1 < COPPER_GRATES.size()) {
                        newItemStack = itemStack.withType(COPPER_GRATES.get(index+1));
                    }
                }

                if (newItemStack.getType() != Material.AIR) {
                    item.setItemStack(newItemStack);
                    item.setTicksLived(1);
                    item.getLocation().getWorld().spawnParticle(Particle.BUBBLE, item.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
                    item.getLocation().getWorld().playSound(item.getLocation(), Sound.BLOCK_COPPER_HIT, 1, 1);
                }
            }
        }
    };
}
