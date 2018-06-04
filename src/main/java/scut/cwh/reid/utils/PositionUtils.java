package scut.cwh.reid.utils;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import scut.cwh.reid.domain.Position;
import scut.cwh.reid.domain.SensorArea;
import scut.cwh.reid.domain.WifiInfo;

import java.util.Date;
import java.util.Set;

public class PositionUtils {
    public static Position calculatedPosition(double[][] positions, double[] distances){

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        // the answer
        double[] centroid = optimum.getPoint().toArray();

        return new Position(centroid[0],centroid[1]);
    }

    public static Boolean positionIsInArea(Position position, SensorArea sensorArea){
        return position.getX()>=sensorArea.getX0()
                &&position.getX()<=sensorArea.getX1()
                &&position.getY()>=sensorArea.getY0()
                &&position.getY()<=sensorArea.getY1();
    }

    public static Position centerOfSensorArea(SensorArea sensorArea){
        return new Position((sensorArea.getX0()+sensorArea.getX1())/2,(sensorArea.getY0()+sensorArea.getY1())/2);
    }
}
