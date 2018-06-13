package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class SudokuChocoV2 {


    public static int n = 9;

    public static int sum = 45;



    public static void main(String[] args)
    {
        //solveSudokuV1(); //Ne fonctionne pas du tout

        int[][] grid1 = new int[][]{
                {0, 0, 0, 2, 0, 0, 0, 3, 0},
                {0, 6, 0, 0, 8, 0, 9, 0, 4},
                {0, 0, 8, 4, 0, 7, 0, 0, 0},
                {1, 8, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 6, 0, 1, 0, 2, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 9, 5},
                {0, 0, 0, 1, 0, 8, 4, 0, 0},
                {4, 0, 7, 0, 3, 0, 0, 5, 0},
                {0, 1, 0, 0, 0, 4, 0, 0, 0}
        };

        int[][] grid2 = new int[][]{
                {0, 0, 9, 0, 0, 0, 0, 4, 0},
                {7, 5, 0, 0, 0, 0, 0, 9, 0},
                {4, 3, 0, 0, 9, 1, 0, 0, 0},
                {0, 0, 2, 5, 0, 0, 1, 8, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 7, 5, 0, 0, 3, 6, 0, 0},
                {0, 0, 0, 2, 6, 0, 0, 7, 5},
                {0, 6, 0, 0, 0, 0, 0, 1, 3},
                {0, 8, 0, 0, 0, 0, 9, 0, 0}
        };
        //solveSudokuV2(grid1);
        solveSudokuV2(grid2);
    }

    public static void solveSudokuV1(){
        Model model = new Model("sudoku");

        //Generates the square
        IntVar[][] vars = new IntVar[n][n];
        for (int i =0; i<n;i++) {
            for (int j = 0; j<n; j++) {
                vars[i][j] = model.intVar(i+"-"+j ,1, n) ;
            }
        }

        for(int i = 0; i < n; i++) {
            IntVar[] lignes = new IntVar[n];
            IntVar[] colonnes = new IntVar[n];
            IntVar[] carres = new IntVar[n];
            for(int j = 0; j < n; j++) {
                lignes[j] = vars[i][j];
                colonnes[j] = vars[j][i];
                int sqrtn=(int) Math.sqrt(n);
                carres[j] = vars[j%sqrtn + (i % sqrtn) * sqrtn][j/sqrtn + (i / sqrtn) * sqrtn];
            }
            model.allDifferent(lignes);
            model.allDifferent(colonnes);
            model.allDifferent(carres);
        }

        Solver solver = model.getSolver();

        //Affichage
        List<Solution> solutions = solver.findAllSolutions();
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println(solutions.get(i));
            System.out.println("------");
        }
    }

    public static void solveSudokuV2(int[][] grid)
    {
        int n = 9; // la taille de la grille
        Model model = new Model(); // une nouvelle instance de la classe Problem

        long start = System.currentTimeMillis();

        // déclaration des lignes (rows) et colonnes (cols)
        IntVar[][] rows = new IntVar[n][]; // les n lignes
        IntVar[][] cols = new IntVar[n][]; // les n colonnes


        int[] coeffs = new int[n];
        for (int i = 0;i<n;i++) {
            coeffs[i] = 1;
        }

        // déclaration des variables
        for (int i = 0; i < n; i++) {
            rows[i] = new IntVar[n];
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) {
                    rows[i][j] = model.intVar(grid[i][j]);
                } else {
                    rows[i][j] = model.intVar("(" + i + "," + j + ")", 1, n);
                }
            }
        }

        // correspondance ligne-colonne
        for (int i = 0; i < n; i++) {
            cols[i] = new IntVar[n];
            for (int j = 0; j < n; j++) {
                cols[i][j] = rows[j][i];
            }
        }

        // pose des contraintes définissant le sudoku
        for (int i = 0; i < n; i++) {
            model.post(model.allDifferent(cols[i]));
            model.post(model.allDifferent(rows[i]));
            //Peut etre supprimé mais est censé aider à améliorer la vitesse de la résolution du probleme
            model.scalar(cols[i], coeffs, "=", 45).post();
            model.scalar(rows[i], coeffs, "=", 45).post();
        }

        // définition des blocs
        IntVar[][] blocks = new IntVar[n][];
        for (int i = 0; i < n; i++) {
            blocks[i] = new IntVar[n];
        }

        // un peu de réflexion
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    blocks[j + k * 3][i] = rows[0 + k * 3][i + j * 3];
                    blocks[j + k * 3][i + 3] = rows[1 + k * 3][i + j * 3];
                    blocks[j + k * 3][i + 6] = rows[2 + k * 3][i + j * 3];
                }
            }
        }

        // pose des contraintes par bloc
        for (int i = 0; i < n; i++) {
            model.post(model.allDifferent(blocks[i]));
        }

        // demande de résolution du problème au solveur
        Solver solver = model.getSolver();

        List<Solution> solutions = solver.findAllSolutions();

        long end = System.currentTimeMillis();
        System.out.println("Solution count : "+ solver.getSolutionCount());
        System.out.println("Solved in " + (end - start) + " ms");

        displaySquareHomeMade(rows);
    }

    private static void displaySquareHomeMade(IntVar[][] t){
        StringBuilder st = new StringBuilder("Sudoku -- solver\n");
        st.append("\t");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                st.append(t[i][j].getValue()).append(" ");
            }
            st.append("\n\t");
        }

        System.out.println(st.toString());
    }

    private static void displaySquare(int n, IntVar[][] t) {
        int n2 = n * n;
        int width = String.valueOf(n2).length();
        System.out.println("--- SOL --- " + n2 + "x" + n2);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append("+");
            for (int j = 0; j < n * 2 * width + 1; j++) {
                s.append("-");
            }
        }
        s.append("+");
        for (int i = 0; i < n2; i++) {
            if (i % n == 0) {
                System.out.println(" " + s);
            }
            for (int j = 0; j < n2; j++) {
                if (j % n == 0) {
                    System.out.print(" |");
                }
                System.out.printf(" %" + width + "d", t[i][j].getValue());
            }
            System.out.println(" |");
        }
        System.out.println(" " + s);
    }
}
