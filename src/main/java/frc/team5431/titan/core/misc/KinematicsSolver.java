package frc.team5431.titan.core.misc;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;

public class KinematicsSolver {
    public final double l1; // Length of first arm segment
    public final double l2; // Length of second arm segment
    public boolean useTopByDefault = false;

    /**
     * Initalizes a solver with preset segment lengths.
     * @param armSegment1Length The length of your first segment, usually the elbow
     * @param armSegment2Length The length of your second segment, usually the shoulder
     */
    public KinematicsSolver(double armSegment1Length, double armSegment2Length) {
        this.l1 = armSegment1Length;
        this.l2 = armSegment2Length;
    }

    /**
     * @return Total of both arm segment lengths
     */
    public double getTotalLength() {
        return l1 + l2;
    }

    /**
     * Inverts whether or not to default to the top or bottom
     */
    public void toggleTopDefault() {
        useTopByDefault = !useTopByDefault;
    }

    /**
     * Solves the second segment of a kinematics arm, this is usually the elbow.
     * 
     * @param goal The position at the end of the arm
     * @return angle in radians of the second segment
     */
    private double solveSegment2Angle(Translation2d goal) {
        double p1 = Math.pow(goal.getX(), 2) + Math.pow(goal.getY(), 2) - Math.pow(l1, 2) - Math.pow(l2, 2);
        double p2 = 2 * l1 * l2;

        return Math.acos(p1 / p2);
    }

    /**
     * Solves the first segment of a kinematics arm, this is usually the shoulder.
     * 
     * @param q2 The angle of segment 2, usually the result of solveSegment2Angle
     * @param goal The position at the end of the arm
     * @return angle in radians of the first segment
     */
    private double solveSegment1Angle(double q2, Translation2d goal) {
        double p1 = Math.atan2(goal.getY(), goal.getX());
        double p2 = Math.atan2(l2 * Math.sin(q2), l1 + l2 * Math.cos(q2));

        return p1 - p2;
    }

    /**
     * This converts a goal to two angles for an arm. 
     * Essentially meaning we can say, "arm, go there." instead of saying, "Arm set angle to this and this."
     * 
     * @param goal The position at the end of the arm
     * @return A pair of angles in radians of both segments
     */
    public Pair<Double, Double> posToAngles(Translation2d goal) {
        return posToAngles(goal, useTopByDefault);
    }

    /**
     * This converts a goal to two angles for an arm. 
     * Essentially meaning we can say, "arm, go there." instead of saying, "Arm set angle to this and this."
     * 
     * (Warning: Contains side effect of setting default to the one used here)
     * 
     * @param goal The position at the end of the arm
     * @param useTopPossibility Whether or not the segment 1 is angled upwards or downwards
     * @return A pair of angles in radians of both segments
     */
    public Pair<Double, Double> posToAngles(Translation2d goal, boolean useTopPossibility) {
        double q2 = (useTopPossibility ? -1 : 1) * solveSegment2Angle(goal);
        double q1 = solveSegment1Angle(q2, goal);

        useTopByDefault = useTopPossibility;

        /*
            The extra calcs are for 2023 bot, so that q1 is angled down at (0 rad, 0 rad)
            Usually this would be 90 degrees forwards
            |   |   |            |       |
            | --o   | instead of | --o-- |
            |_______|            |_______|         
            
            I should probably have a way of having a dynamically doing this, but for now I'm keeping it as-is
        */
        //q2 is negative because ¯\_(ツ)_/¯
        return Pair.of(q1 + Math.PI / 2, -q2);
    }

    /**
     * Convert a set of angles to a position, reverse IK
     * @param q1Radians Segment 1 angle
     * @param q2Radians Segment 2 angle
     * @return The position at the end of the two segments
     */
    public Translation2d anglesToPos(double q1Radians, double q2Radians) {
        // Same extra calcs in these two vars as in posToAngles
        double offset_q1 = q1Radians - Math.PI / 2;
        double offset_q2 = -q2Radians;

        // Return to normal reverse ik calc
        double x = l1 * Math.cos(offset_q1) + l2 * Math.cos(offset_q1 + offset_q2);
        double y = l1 * Math.sin(offset_q1) + l2 * Math.sin(offset_q1 + offset_q2);
        return new Translation2d(x, y);
    }
}
