package WithChoco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;

public class cumulativeChoco {

    public static void main(String[] args) {
        System.out.println("cumulative");
        solveCumulative();
    }

    static void solveCumulative() {
        Model model = new Model("Cumulative");
        int n = 4;
        IntVar[] starts = model.intVarArray("start", 4, 1, 9, false);
        IntVar[] ends = model.intVarArray("end", 4, 1, 8, false);
        int[] durationArray = new int[]{1,2,3,4};
        int[] heights = new int[]{1,2,5,7};
        IntVar limit = model.intVar(7);
        Task[] tasks = new Task[n];
        IntVar[] durations = model.intVarArray(n, durationArray);

        for(int i = 0; i<n; i++){
            tasks[i] = new Task(starts[i], durations[i], ends[i]);
        }

        model.post(
                model.cumulative(
                        tasks,
                        model.intVarArray(n, heights),
                        limit ,
                        false
                )
        );

        Solver solver = model.getSolver();
        Solution res = solver.findSolution();
        do {
            System.out.println(res);
            res = solver.findSolution();
        } while (res != null);
    }
}
