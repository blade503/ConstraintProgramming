package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

import java.util.stream.IntStream;

public class NQueensChoco {

    public static void main(String[] args) {
        System.out.println("N-Queens");
        solveNQueens(8);
    }

    //ONLINE SOLUTION
    static void solveNQueens(int n) {
        Model model = new Model(n + "-queens problem");
        IntVar[] vars = model.intVarArray("Q", n, 1, n, false);
        IntVar[] diag1 = IntStream.range(0, n).mapToObj(i -> vars[i].sub(i).intVar()).toArray(IntVar[]::new);
        IntVar[] diag2 = IntStream.range(0, n).mapToObj(i -> vars[i].add(i).intVar()).toArray(IntVar[]::new);
        model.post(
                model.allDifferent(vars),
                model.allDifferent(diag1),
                model.allDifferent(diag2)
        );
        Solver solver = model.getSolver();
        solver.setSearch(Search.domOverWDegSearch(vars));
        Solution solution = solver.findSolution();
        if (solution != null) {
            System.out.println(solution);
        }
    }


}
