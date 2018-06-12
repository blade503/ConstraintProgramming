package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class graphChoco {

    public static void main(String[] args) {
        System.out.println("graph");
        solveGraph(4);
    }

    static void solveGraph(int n) {
        // What we know is that
        // We will declare variable that will give the weight of each arc
        // We will declare variable to tell if a node is in the graph or not

        Model model = new Model("Graph");
        /*
        model.post(

        );
        Solver solver = model.getSolver();
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);*/
    }
}
