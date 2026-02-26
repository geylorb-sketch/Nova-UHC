package net.novaproject.novauhc.world.generation;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.task.LoadingChunkTask;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.TreeType;

public enum CenterType {
        ROOFT {
            @Override
            public void generate(WorldPopulator populator) {
                populator.generateForest(TreeType.DARK_OAK, TreeType.BROWN_MUSHROOM, TreeType.RED_MUSHROOM,4,98,99);
            }
        },
        TAIGA {
            @Override
            public void generate(WorldPopulator populator) {
                populator.generateForest(TreeType.TALL_REDWOOD, TreeType.REDWOOD, TreeType.REDWOOD,4,98,99);
            }
        },
        FOREST {
            @Override
            public void generate(WorldPopulator populator) {
                populator.generateForest(TreeType.TREE, TreeType.BIRCH, TreeType.JUNGLE,1,2,-1);
            }
        },
        FLAT {
            @Override
            public void generate(WorldPopulator populator) {
                LoadingChunkTask.create(Common.get().getArena(), Common.get().getNether(), (int) (Common.get().getArena().getWorldBorder().getSize() / 2));
            }
        };

        public abstract void generate(WorldPopulator populator);
    }