package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class decompositionChoco {

    public static void main(String[] args) {
        System.out.println("decomposition");
        solveDecomposition(4);
    }


    /*
    Decomposition

    1 => (1)
    2 => (1 1) (2)
    3 => (1 1 1) (2 1) (1 2) (3)
    4 => (1 1 1 1) (2 1 1) (1 2 1) (1 1 2) (2 2) (4) (1 3) (3 1)
     */
    static void decomposition(int n){
        int totalSolution = 2^(n-1);
        //TODO
    }

    static void solveDecomposition(int n) {
        //Create array of n
        Model model = new Model("Decomposition");
        //Range is 0 to N;
        IntVar[] vars = model.intVarArray("Q", n, 0, n, false);
        model.post(
                //Sum equal to 5
                model.sum(vars, "=", n),
                model.regular(vars, new FiniteAutomaton("[1-"+ n +"]+0*"))
        );
        Solver solver = model.getSolver();
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);
    }
}
