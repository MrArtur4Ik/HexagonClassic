package ru.mrartur.hexmc.world.generator;

import ru.mrartur.hexmc.world.World;

public class FlatGenerator implements WorldGenerator {
    @Override
    public void generate(World world) {
        short grassLevel = (short) (world.getHeight() / 2);
        for (short x = 0; x < world.getWidth(); x++) {
            for (short z = 0; z < world.getDepth(); z++) {
                for (short y = 0; y < grassLevel; y++) {
                    world.setBlock(x, y, z, (byte) 3);
                }
                world.setBlock(x, grassLevel, z, (byte) 2);
            }
        }
    }
}
