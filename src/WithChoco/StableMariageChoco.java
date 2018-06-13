package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class StableMariageChoco {

    public static void main(String[] args) {
        System.out.println("decomposition");
        solveTimeTable(4);
    }

    static void solveTimeTable(int n) {



        Model model = new Model("StableMariage");
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
