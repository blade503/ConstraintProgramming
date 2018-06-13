package SquarePacking;

import java.util.Arrays;
import org.chocosolver.util.tools.ArrayUtils;

/*
 * Square Packing
 *
 */
/**
 * @author Daniel Diaz <daniel.diaz@univ-paris1.fr>
 */
public abstract class SquarePackingAbstract {

    protected final String problemName;
    protected final int rectXSize;
    protected final int rectYSize;
    protected final int rectArea;
    protected final int[] squaresSize;
    protected final int nbSquares;
    protected final int sumSquareArea;
    protected final boolean perfectPacking;

    /* solution vars */
    protected final int[] solX;
    protected final int[] solY;
    protected final int[] solSize;

    public SquarePackingAbstract(SquarePackingInstance pData) {
        this(pData.getProblemName(), pData.getRectXSize(), pData.getRectYSize(), pData.getSquaresSize());
    }

    public SquarePackingAbstract(String problemName, int rectXSize, int rectYSize, int... squaresSize) {
        this.problemName = problemName;
        this.rectXSize = rectXSize;
        this.rectYSize = rectYSize;
        this.rectArea = rectXSize * rectYSize;
        this.squaresSize = squaresSize;
        this.nbSquares = squaresSize.length;
        int sumArea = 0;
        for (int sz : squaresSize) {
            sumArea += sz * sz;
        }
        this.sumSquareArea = sumArea;
        this.perfectPacking = rectArea == sumSquareArea;
        this.solX = new int[nbSquares];
        this.solY = new int[nbSquares];
        this.solSize = new int[nbSquares];
        // sort square size array in descending order:
        Arrays.sort(this.squaresSize); // sort increasing,
        for (int i = 0, j = nbSquares - 1; i < j; i++, j--) { // reverse
            int tmp = this.squaresSize[i];
            this.squaresSize[i] = this.squaresSize[j];
            this.squaresSize[j] = tmp;
        }
    }

    public void solve(String method) {
        System.out.println("Problem: " + problemName);
        System.out.println("Master rectangle: " + rectXSize + "x" + rectYSize + " area: " + rectArea);
        System.out.println("Square sizes: " + Arrays.toString(squaresSize));
        System.out.println("#squares: " + nbSquares + " covered area: " + sumSquareArea + ((perfectPacking) ? " (perfect packing)" : " (partial packing)"));
        System.out.println("Solving method: " + method);
        System.out.println("--------------------");
        long t0 = System.currentTimeMillis();
        boolean solved = findSolution();
        long time = System.currentTimeMillis() - t0;

        if (solved) {
            System.out.println("Solution");
            System.out.println(textSolution());
        } else {
            System.out.println("No solution !");
        }
        System.out.printf("Time: %.3f sec\n", (double) time / 1000);
    }

    public abstract boolean findSolution();

    public String textSolution() {
//        System.out.println(Arrays.toString(solX));
//        System.out.println("---");
//        for(int i = 0; i < nbSquares; i++)
//            solX[i] = rectXSize - solSize[i] - solX[i];
//        System.out.println(Arrays.toString(solX));
//        System.out.println("================");
        StringBuilder res = new StringBuilder();
        res.append(Arrays.toString(solX)).append("\n");
        res.append(Arrays.toString(solY)).append("\n");
        res.append(Arrays.toString(solSize)).append("\n");

        char[][] asciiRepres = new char[rectXSize][rectYSize];
        for (char[] t : asciiRepres) {
            Arrays.fill(t, '.'); // mark all cell as unused
        }

        // also checks the validity of a solution 
        for (int numSq = 0; numSq < solX.length; numSq++) {
            if (!drawSquare(solX[numSq], solY[numSq], solSize[numSq], asciiRepres, '.')) {
                System.err.println(res.toString());
                System.exit(1);
            }
        }

        if (rectXSize * rectYSize <= 200 * 200) {
//        for (int y = rectYSize - 1; y >= 0; y--) {
            for (int y = 0; y < rectYSize; y++) {
                for (int x = 0; x < rectXSize; x++) {
                    res.append(asciiRepres[x][y]);
                }
                res.append("\n");
            }
        }
        return res.toString();
    }

    private static final char WHITE_SQUARE = '\u25a1'; // 1x1
    // Box-drawing unicode characters (aka line-drawing characters)
    private static final char HORIZ = '\u2500';
    private static final char VERT = '\u2502';
    private static final char LEFT_UP = '\u250c';
    private static final char RIGHT_UP = '\u2510';
    private static final char LEFT_DN = '\u2514';
    private static final char RIGHT_DN = '\u2518';

    private boolean drawSquare(int posX, int posY, int sz, char[][] asciiRepres, char toCheck) {
        for (int x = 0; x < sz; x++) {
            for (int y = 0; y < sz; y++) {
                if (asciiRepres[posX + x][posY + y] != toCheck) {
                    System.err.println("ERROR in solution: at " + x + "/" + y);
                    return false;
                }
                asciiRepres[posX + x][posY + y] = ' ';
            }
        }
        if (sz == 1) {
            asciiRepres[posX][posY] = WHITE_SQUARE;
            return true;
        }
        asciiRepres[posX][posY] = LEFT_UP;
        asciiRepres[posX + sz - 1][posY] = RIGHT_UP;
        asciiRepres[posX][posY + sz - 1] = LEFT_DN;
        asciiRepres[posX + sz - 1][posY + sz - 1] = RIGHT_DN;
        for (int k = 1; k < sz - 1; k++) {
            asciiRepres[posX + k][posY] = asciiRepres[posX + k][posY + sz - 1] = HORIZ;
            asciiRepres[posX][posY + k] = asciiRepres[posX + sz - 1][posY + k] = VERT;
        }
        // write the size of the square in the center of the square
        String s = String.valueOf(sz);
        int k = (sz - s.length()) / 2;
        if (k > 0) { // 0 is not allowed to avoid to write over vertical bars at posX, ok when sz > 2
            for (int i = 0; i < s.length(); i++) {
                asciiRepres[posX + k + i][posY + k] = s.charAt(i);
            }
        }
        return true;
    }

    public static void main(String... args) {
        //new SquarePackingChoco(SquarePackingInstance.PS_DD_2).solve("Choco primitive");
    }
}