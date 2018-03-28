package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.variables.IntVar;

public class timeTableChoco {

    public static void main(String[] args) {
        System.out.println("decomposition");
        solveTimeTable(4);
    }

    static void solveTimeTable(int n) {
        // 30 Hours per week
        // A day has 10 1hour slots -> 5 in the morning and 5 in the afternoon
        // If there is more than 2 hours


        Model model = new Model("TimeTable");
        //Range is 0 to N;
        IntVar[] vars = model.intVarArray("Q", n, 0, n, false);
        model.post(

        );
        Solver solver = model.getSolver();
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);
    }
}
