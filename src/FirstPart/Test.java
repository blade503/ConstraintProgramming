package FirstPart;

public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Test app = new Test();
        //System.out.println("TWO + TWO = FOUR");
        //app.two();
        //System.out.println("\n\n");
        //System.out.println("Magic Square V1");
        //app.magicSquare(n);
        //System.out.println("Magic Square V2");
        //app.magicSquare2(n);
        //System.out.println("Magic Square V3");
        //app.magicSquare3(n);
        int n = 5;
        int[][] square5 = new int[][]{
                {25, 13, 1, 9, 7},
                {16, 9, 22, 15, 3},
                {12, 5, 18, 6, 24},
                {8, 21, 14, 2, 20},
                {4, 17, 10, 23, 11}
        };
        app.printSquare(square5, n);
        System.out.print("Is Magic Square ? -> ");
        System.out.println(app.isAMagicSquare(square5, 5) + "\n");

        System.out.println("MagicSquare1 \n");
        int[][] squareGenerate = app.magicSquare1(7);
        app.printSquare(squareGenerate, 7);
        System.out.print("Is Magic Square ? -> ");
        System.out.println(app.isAMagicSquare(squareGenerate, 7));
    }

    public void two() {
        int T = 1, W = 0, O = 0, F = 1, U = 0, R = 0;

        for (T = 1; T < 10; T++) {
            for (W = 0; W < 10; W++) {
                for (O = 0; O < 10; O++) {
                    for (F = 1; F < 10; F++) {
                        for (U = 0; U < 10; U++) {
                            for (R = 0; R < 10; R++) {
                                if ((T == W) || (T == O) || (T == F) || (T == U) || (T == R) || (W == O) || (W == F) || (W == U) || (W == R) || (O == F) || (O == U) || (O == R) || (F == U) || (F == R) || (U == R)) {
                                    continue;
                                } else if (200 * T + 20 * W + 2 * O == 1000 * F + 100 * O + 10 * U + R) {
                                    System.out.println(Integer.toString(T) + Integer.toString(W) + Integer.toString(O) + " + " + Integer.toString(T) + Integer.toString(W) + Integer.toString(O) + " = " + Integer.toString(F) + Integer.toString(O) + Integer.toString(U) + Integer.toString(R));

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int[][] magicSquare1(int n) {
        if (n % 2 == 0) throw new RuntimeException("n must be odd");

        int[][] square = new int[n][n];

        int row = n - 1;
        int col = n / 2;
        square[row][col] = 1;

        for (int i = 2; i <= n * n; i++) {
            if (square[(row + 1) % n][(col + 1) % n] == 0) {
                row = (row + 1) % n;
                col = (col + 1) % n;
            } else {
                row = (row - 1 + n) % n;
                // don't change col
            }
            square[row][col] = i;
        }

        return square;

    }

    public void magicSquare2(int n) {

        int[][] arr = new int[n][n];
        int i, j;

        // filling matrix with its count value
        // starting from 1;
        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                arr[i][j] = (n * i) + j + 1;

        this.printSquare(arr, n);

        // change value of Array elements
        // at fix location as per rule
        // (n*n+1)-arr[i][j]
        // Top Left corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 0; i < n / 4; i++)
            for (j = 0; j < n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];
        this.printSquare(arr, n);

        // Top Right corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 0; i < n / 4; i++)
            for (j = 3 * (n / 4); j < n; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];
        this.printSquare(arr, n);

        // Bottom Left corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 3 * n / 4; i < n; i++)
            for (j = 0; j < n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];
        this.printSquare(arr, n);

        // Bottom Right corner of Matrix
        // (order (n/4)*(n/4))
        for (i = 3 * n / 4; i < n; i++)
            for (j = 3 * n / 4; j < n; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];
        this.printSquare(arr, n);

        // Centre of Matrix (order (n/2)*(n/2))
        for (i = n / 4; i < 3 * n / 4; i++)
            for (j = n / 4; j < 3 * n / 4; j++)
                arr[i][j] = (n * n + 1) - arr[i][j];

        this.printSquare(arr, n);
        this.isAMagicSquare(arr, n);

    }

    public void printSquare(int[][] square, int n) {
        // print results
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (square[i][j] < 10) System.out.print(" ");
                if (square[i][j] < 100) System.out.print(" ");
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }


    public boolean isAMagicSquare(int[][] square, int n) {
        return this.isAMagicSquareByRow(square, n) == this.isAMagicSquareByCol(square, n) && this.isAMagicSquareByDiagNE(square, n) == this.isAMagicSquareByDiagSE(square, n) && this.isAMagicSquareByRow(square, n) == this.isAMagicSquareByDiagSE(square, n);
    }

    public int isAMagicSquareByRow(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum = sum + square[i][j];
            }
        }

        return sum;
    }

    public int isAMagicSquareByCol(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum = sum + square[j][i];
            }
        }

        return sum;
    }

    public int isAMagicSquareByDiagNE(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + square[i][i];
        }

        return sum;
    }

    public int isAMagicSquareByDiagSE(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = n - 1; j >= 0; j--) {
                sum = sum + square[i][j];
            }
        }

        return sum;
    }
}
