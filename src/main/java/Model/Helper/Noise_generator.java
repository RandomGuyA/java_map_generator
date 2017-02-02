package Model.Helper;

import java.util.Random;

public final class Noise_generator {

    public static float[][] get_perlin_noise(){

        return null;
    }

    public static float[][] get_custom_land_generation(int tile_count_x, int tile_count_y, int frequency, int seed){

        SimpleLandGen simpleLandGen = new SimpleLandGen(tile_count_x, tile_count_y, frequency, seed);

        return simpleLandGen.get_map();
    }

    public static float[][] get_noise(int width, int height, int seed, float min, float max) {

        Random random = new Random(seed);

        float[][] noise = new float[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = random.nextFloat() * (max - min) + min;
            }
        }
        return noise;
    }
}
