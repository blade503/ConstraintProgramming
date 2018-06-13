package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMedian;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainRandom;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.search.strategy.selectors.variables.InputOrder;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class MagicSquareChoco {

    public static void main(String[] args) {
        System.out.println("Magic Square");
        solveMagicSquareV2(4);
    }

    static void solveMagicSquareV1(int n) {
        int sum = n*(n*n+1)/2;
        Model model = new Model("magicSquare");
        long start = System.currentTimeMillis();

        //Generates the square
        IntVar[] vars = new IntVar[n * n];
        for (int i =0; i<n;i++) {
            for (int j = 0; j<n; j++) {
                vars[i*n+j] = model.intVar(i+"-"+j,1, n*n) ;
            }
        }

        //All places have the same coefficient
        int[] coeffs = new int[n];
        for (int i = 0;i<n;i++) {
            coeffs[i] = 1;
        }

        //All cases must have a different number
        model.allDifferent(vars).post();

        //All rows and columns are linked by the sum
        for (int i = 0; i<n; i++) {
            IntVar[] col = new IntVar[n];
            IntVar[] row = new IntVar[n];
            for (int j = 0; j<n; j++) {
                col[j] = vars[i*n+j];
                row[j] = vars[j*n+i];
            }
            model.scalar(col, coeffs, "=", sum).post();
            model.scalar(row, coeffs, "=", sum).post();
        }

        //Coefficient for diagonals
        int[] diagonalsCoeffs = new int [n];
        for (int i = 0; i < n; i++) {
            diagonalsCoeffs[i] = 1;
        }

        //Diagonals are linked by the sum
        IntVar[] diagonalA = new IntVar[n];
        for (int i = 0, j = 0; i < n && j < n; i++, j++) {
            diagonalA[i] = vars[i*n+j];
        }
        model.scalar(diagonalA, diagonalsCoeffs, "=", sum).post();

        IntVar[] diagonalB = new IntVar[n];
        for (int i = 0, j = n; i < n && j > 0; i++, j--) {
            diagonalB[i] = vars[i*n+j-1];
        }
        model.scalar(diagonalB, diagonalsCoeffs, "=", sum).post();

        //Solve
        Solver solver = model.getSolver();
        
        //Affichage
        List<Solution> solutions = solver.findAllSolutions();
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println(solutions.get(i));
            System.out.println("------");
        }

        long end = System.currentTimeMillis();
        System.out.println(solver.getSolutionCount());
        System.out.println((end - start) + " ms");

    }

    static void solveMagicSquareV2(int n) {
        int sum = n*(n*n+1)/2;
        Model model = new Model("magicSquare");
        long start = System.currentTimeMillis();

        //Generates the square
        IntVar[] vars = new IntVar[n * n];
        for (int i =0; i<n;i++) {
            for (int j = 0; j<n; j++) {
                vars[i*n+j] = model.intVar(i+"-"+j,1, n*n) ;
            }
        }

        //All places have the same coefficient
        int[] coeffs = new int[n];
        for (int i = 0;i<n;i++) {
            coeffs[i] = 1;
        }

        //All cases must have a different number
        model.allDifferent(vars).post();

        //All rows and columns are linked by the sum
        for (int i = 0; i<n; i++) {
            IntVar[] col = new IntVar[n];
            IntVar[] row = new IntVar[n];
            for (int j = 0; j<n; j++) {
                col[j] = vars[i*n+j];
                row[j] = vars[j*n+i];
            }
            model.scalar(col, coeffs, "=", sum).post();
            model.scalar(row, coeffs, "=", sum).post();
        }

        //Coefficient for diagonals
        int[] diagonalsCoeffs = new int [n];
        for (int i = 0; i < n; i++) {
            diagonalsCoeffs[i] = 1;
        }

        //Diagonals are linked by the sum
        IntVar[] diagonalA = new IntVar[n];
        for (int i = 0, j = 0; i < n && j < n; i++, j++) {
            diagonalA[i] = vars[i*n+j];
        }
        model.scalar(diagonalA, diagonalsCoeffs, "=", sum).post();

        IntVar[] diagonalB = new IntVar[n];
        for (int i = 0, j = n; i < n && j > 0; i++, j--) {
            diagonalB[i] = vars[i*n+j-1];
        }
        model.scalar(diagonalB, diagonalsCoeffs, "=", sum).post();

        //Solve
        Solver solver = model.getSolver();

        solver.setSearch(
                    //2
                //Search.inputOrderLBSearch(vars)
                    //2'
                //Search.domOverWDegSearch(vars)

                    //3
                //Search.intVarSearch(new Smallest(), new IntDomainMiddle(true), vars)

                    //4
                //Search.intVarSearch(new InputOrder<>(model), new IntDomainMiddle(true), vars)

                    //5
                //Search.intVarSearch(new InputOrder<>(model), new IntDomainMiddle(true), diagonalA),
                //Search.intVarSearch(new InputOrder<>(model), new IntDomainRandom(1), vars)

                    //6
                //Search.intVarSearch(new InputOrder<>(model), new IntDomainRandom(1), diagonalA),
                //Search.intVarSearch(new InputOrder<>(model), new IntDomainRandom(1), vars)

                    //7
                //Search.intVarSearch(new FirstFail(model), new IntDomainRandom(n), vars)

                    //8
                Search.intVarSearch(new FirstFail(model), new IntDomainMedian(), diagonalA),
                Search.intVarSearch(new FirstFail(model), new IntDomainRandom(1000000), vars)

        );

        //Affichage
        List<Solution> solutions = solver.findAllSolutions();
        /*for (int i = 0; i < solutions.size(); i++) {
            System.out.println(solutions.get(i));
            System.out.println("------");
        }*/

        long end = System.currentTimeMillis();
        System.out.println(solver.getSolutionCount());
        System.out.println((end - start) + " ms");

    }

}
