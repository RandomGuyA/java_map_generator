package Model.Helper;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleLandGen {

    private int WIDTH;
    private int HEIGHT;
    private int overallAverage, difference;
    private int maximumHeight, minimumHeight, range;
    private int[][] binary_map;
    private float[][] map;

    public SimpleLandGen(int tile_count_x, int tile_count_y, int frequency, int seed) {

        this.WIDTH = tile_count_x;
        this.HEIGHT = tile_count_y;
        range = 2;
        map = new float[WIDTH][HEIGHT];

        //map = Noise_generator.get_noise(WIDTH, HEIGHT, seed, 0.0f, 1f);
        map = Noise_generator.get_noise(WIDTH, HEIGHT, seed, 0.0f, 1f);
        map = generate_height_map(map, frequency);

        /*binary_map = new int[WIDTH][HEIGHT];
        int loops = frequency;
        binary_map = generateRandomSeeds(binary_map);
        binary_map = generateHeightmap(binary_map, loops);
        setAverageAndMaximumValues(binary_map);

        difference = maximumHeight - minimumHeight;

        reduceArrayToZero(binary_map);
        //Util.printArray(binary_map);*/

    }

    private float[][] generate_height_map(float[][] array, int loops) {

        for (int i = 0; i < loops; i++) {
            for (int y = 0; y < array[0].length; y++) {
                for (int x = 0; x < array.length; x++) {

                    ArrayList<Cell> neighbourCells = new ArrayList<Cell>();
                    neighbourCells = get_neighbouring_cells(array, neighbourCells, x, y);

                    //calculate average of neighbouring cells
                    float sum = 0;
                    for (int c = 0; c < neighbourCells.size(); c++) {
                        sum += neighbourCells.get(c).getValue();
                    }
                    float average = sum / (float) neighbourCells.size();

                    //change target cell to average value
                    array[x][y] = average;
                }
            }
        }
        return array;
    }

    private ArrayList<Cell> get_neighbouring_cells(float[][] array, ArrayList<Cell> array_list, int a, int b) {

        int x = a, y = b, row = 3, col = 3;

        //if not zero set a and b to the left outer corner of the 3x3 block
        if (a == 0) {
            row = row - 1;
        } else {
            a = a - 1;
        }
        if (b == 0) {
            col = col - 1;
        } else {
            b = b - 1;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if ((a + i) == x && (b + j) == y) {

                } else {
                    try {
                        array_list.add(new Cell(new Coordinates((a + i), (b + j)), new Coordinates(x, y), array[a + i][b + j]));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return array_list;
    }

    private void reduceArrayToZero(int[][] array) {
        for (int a = 0; a < array.length; a++) {
            for (int b = 0; b < array[a].length; b++) {
                int loweredValue = (array[a][b] - minimumHeight);
                double factor = ((double) loweredValue) / difference * range;
                array[a][b] = (int) Math.floor(factor);
            }
        }
    }

    private void setAverageAndMaximumValues(int[][] array) {

        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        int sum = 0;
        for (int a = 0; a < array.length; a++) {
            for (int b = 0; b < array[a].length; b++) {

                sum += array[a][b];
                tempArray.add(array[a][b]);
            }
        }

        setMaximumHeight(Collections.max(tempArray));
        setMinimumHeight(Collections.min(tempArray));
        double avg = sum / (double) (array.length * array.length);
        setOverallAverage((int) (avg));
    }

    private int[][] generateHeightmap(int[][] array, int loops) {

        for (int i = 0; i < loops; i++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {

                    ArrayList<Cell> neighbourCells = new ArrayList<Cell>();
                    neighbourCells = getNeighbouringCells(array, neighbourCells, x, y);

                    //calculate average of neighbouring cells
                    int sum = 0;
                    for (int c = 0; c < neighbourCells.size(); c++) {
                        sum += neighbourCells.get(c).getValue();
                    }
                    double avg = sum / (double) neighbourCells.size();
                    int average = (int) (avg);

                    //change target cell to average value
                    array[x][y] = average;
                }
            }
        }
        return array;
    }

    //takes 2D array and x and y coordinates, returns arraylist of surrounding cells
    private ArrayList<Cell> getNeighbouringCells(int[][] array, ArrayList<Cell> arrayList, int a, int b) {

        int x = a, y = b, row = 3, col = 3;

        //if not zero set a and b to the left outer corner of the 3x3 block
        if (a == 0) {
            row = row - 1;
        } else {
            a = a - 1;
        }
        if (b == 0) {
            col = col - 1;
        } else {
            b = b - 1;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if ((a + i) == x && (b + j) == y) {

                } else {
                    try {
                        arrayList.add(new Cell(new Coordinates((a + i), (b + j)), new Coordinates(x, y), array[a + i][b + j]));
                    } catch (Exception e) {
                    }
                }
            }
        }
        return arrayList;
    }

    private int[][] generateRandomSeeds(int[][] array) {

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int seed = Util.randInt(0, 255);
                array[x][y] = seed;
            }
        }
        return array;

    }

    public int getOverallAverage() {
        return overallAverage;
    }

    public void setOverallAverage(int overallAverage) {
        this.overallAverage = overallAverage;
    }

    public int getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(int maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public int getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(int minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public int[][] getBinary_map() {
        return binary_map;
    }

    public void setBinary_map(int[][] binary_map) {
        this.binary_map = binary_map;
    }

    public float[][] get_map() {
        return map;
    }

    public class Coordinates {

        private int x, y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public class Cell {

        private Coordinates position;
        private Coordinates parent;
        private float value;

        public Cell(Coordinates position, Coordinates parent, float value) {
            this.position = position;
            this.parent = parent;
            this.value = value;
        }

        public Coordinates getPosition() {
            return position;
        }

        public void setPosition(Coordinates position) {
            this.position = position;
        }

        public Coordinates getParent() {
            return parent;
        }

        public void setParent(Coordinates parent) {
            this.parent = parent;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
}