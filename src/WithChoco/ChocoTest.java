package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class ChocoTest {

    public static void main(String[] args) {
        System.out.println("TWO+TWO=FOUR");
        solveTwoPlusTwo();
        System.out.println("\nSEND+MORE=MONEY");
        solveSendMoreMoney();
    }

    static void solveTwoPlusTwo() {
        Model model = new Model("TWO+TWO=FOUR");

        IntVar T = model.intVar("T", 1, 9);
        IntVar W = model.intVar("W", 0, 9);
        IntVar O = model.intVar("O", 0, 9);
        IntVar F = model.intVar("F", 1, 9);
        IntVar U = model.intVar("U", 0, 9);
        IntVar R = model.intVar("R", 0, 9);

        model.allDifferent(T, W, O, F, U, R).post();

        IntVar[] vars = new IntVar[]{
                T, W, O,
                T, W, O,
                F, O, U, R};

        int[] coeffs = new int[]{
                100, 10, 1,
                100, 10, 1,
                -1000, -100, -10, -1};


        model.scalar(vars, coeffs, "=", 0).post();
        Solver solver = model.getSolver();


        //System.out.println(solver.findAllSolutions());
        // OR
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);
    }

    static void solveSendMoreMoney() {
        Model model = new Model("SEND+MORE=MONEY");

        IntVar S = model.intVar("S", 1, 9);
        IntVar E = model.intVar("E", 0, 9);
        IntVar N = model.intVar("N", 0, 9);
        IntVar D = model.intVar("D", 0, 9);
        IntVar M = model.intVar("M", 1, 9);
        IntVar O = model.intVar("O", 0, 9);
        IntVar R = model.intVar("R", 0, 9);
        IntVar Y = model.intVar("Y", 0, 9);

        model.allDifferent(S, E, N, D, M, O, R, Y).post();

        IntVar[] vars = new IntVar[]{
                S, E, N, D,
                M, O, R, E,
                M, O, N, E, Y};

        int[] coeffs = new int[]{
                1000, 100, 10, 1,
                1000, 100, 10, 1,
                -10000, -1000, -100, -10, -1};

        model.scalar(vars, coeffs, "=", 0).post();
        Solver solver = model.getSolver();


        //System.out.println(solver.findAllSolutions());
        // OR
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);
    }
}
