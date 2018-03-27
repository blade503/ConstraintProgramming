package FirstPart;

public class MagicSquare {

    final int square[][];
    final boolean used[];
    final int n;
    final int magicSum;

    public MagicSquare(int n) {
        square = new int[n][n];
        this.n = n;
        used = new boolean[n * n + 1];
        magicSum = n * (n * n + 1) / 2;
    }

    public static void main(String[] args) {
        MagicSquare m = new MagicSquare(4);
        m.solve(0);
        m.printSquare();
        System.out.println(m.isAMagicSquare());
    }

    boolean isValid() {
        int sumD1 = 0;
        int sumD2 = 0;
        for (int i = 0; i < n; i++) {
            int sumR = 0;
            int sumC = 0;
            sumD1 += square[i][i];
            sumD2 += square[i][n - i - 1];
            for (int j = 0; j < n; j++) {
                sumR += square[i][j];
                sumC += square[j][i];
            }
            if (sumR != magicSum || sumC != magicSum) {
                return false;
            }
        }

        // diagonals
        return (sumD1 == magicSum && sumD2 == magicSum);
    }

    boolean solve(int step) {
        if (step == n * n) {
            return isValid();
        }

        for (int val = 1; val <= n * n; val++) {
            if (used[val]) {
                continue;
            }

            used[val] = true;
            square[step / n][step % n] = val;
            if (solve(step + 1)) {
                return true;
            }
            square[step / n][step % n] = 0;
            used[val] = false;
        }

        return false;
    }

    void printSquare() {
        // print results
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.square[i][j] < 10) System.out.print(" ");
                if (this.square[i][j] < 100) System.out.print(" ");
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    boolean isAMagicSquare() {
        return this.isAMagicSquareByRow(this.square, this.n) == this.isAMagicSquareByCol(this.square, this.n) && this.isAMagicSquareByDiagNE(this.square, this.n) == this.isAMagicSquareByDiagSE(this.square, this.n) && this.isAMagicSquareByRow(this.square, this.n) == this.isAMagicSquareByDiagSE(this.square, this.n);
    }

    int isAMagicSquareByRow(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum = sum + square[i][j];
            }
        }

        return sum;
    }

    int isAMagicSquareByCol(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum = sum + square[j][i];
            }
        }

        return sum;
    }

    int isAMagicSquareByDiagNE(int[][] square, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + square[i][i];
        }

        return sum;
    }

    int isAMagicSquareByDiagSE(int[][] square, int n) {
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
