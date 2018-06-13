package SquarePacking;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

/*
 * Square Packing
 *
 */
/**
 * @author Daniel Diaz <daniel.diaz@univ-paris1.fr>
 */
public class SquarePackingSkeleton extends SquarePackingAbstract {

    public SquarePackingSkeleton(SquarePackingInstance pData) {
        this(pData.toString(),
                pData.getRectXSize(),
                pData.getRectYSize(),
                pData.getSquaresSize());
    }

    public SquarePackingSkeleton(String problemName, int rectXSize, int rectYSize, int... squaresSize) {
        super(problemName, rectXSize, rectYSize, squaresSize);
    }

    @Override
    public boolean findSolution() {
        Model model = new Model("Square Packing: " + problemName);

        IntVar[] x = new IntVar[nbSquares];
        IntVar[] y = new IntVar[nbSquares];

        for (int i = 0; i < nbSquares; i++) {
            x[i] = model.intVar("x" + i, 0, rectXSize - squaresSize[i]);
            y[i] = model.intVar("y" + i, 0, rectYSize - squaresSize[i]);
        }

        // *** IMPOSE CONSTRAINTS ***
        // *** (INSERT YOUR CODE) ***
        // **************************

        Solver solver = model.getSolver();

        // *** SOLVE **

        if (!solver.solve()) {
            return false;
        }

        // *** STORE SOLUTION AS INTEGER VALUES ***

        for (int i = 0; i < nbSquares; i++) {
            solX[i] = x[i].getValue();
            solY[i] = y[i].getValue();
            solSize[i] = squaresSize[i];
        }
        return true;
    }

    public static void main(String... args) {
        new SquarePackingSkeleton(SquarePackingInstance.PS_2).solve("Choco primitive");
    }
}