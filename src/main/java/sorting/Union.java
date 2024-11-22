package sorting;
import java.util.ArrayList;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        if (intervals.length == 0) return new Interval[0];
        if (intervals.length == 1) return intervals;
        ArrayList<Interval> inters = new ArrayList<>();
        for (Interval i : intervals) {
            inters.add(i);
        }

        inters.sort(Interval::compareTo);

        // remove equals
        ArrayList<Integer> eq = new ArrayList<>();
        for (int i = 0; i < inters.size()-1; i++) {
            if (inters.get(i).equals(inters.get(i + 1))) {
                eq.add(i);
            }
        }
        for (int i = 0; i < eq.size(); i++) {
            inters.set(eq.get(i), new Interval(Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        ArrayList<Interval> ninters = new ArrayList<>();
        for (Interval i : inters) {
            if (i.min != Integer.MIN_VALUE && i.max != Integer.MAX_VALUE) {
                ninters.add(i);
            }
        }

        // union
        ArrayList<Interval> res = new ArrayList<>();
        for (int i = 0; i < ninters.size()-1; i++) {
            if (ninters.get(i).max >= ninters.get(i+1).min || ninters.get(i).max == ninters.get(i+1).min) {
                ninters.set(i+1, new Interval(ninters.get(i).min, Math.max(ninters.get(i+1).max, ninters.get(i).max)));
                ninters.set(i, new Interval(Integer.MIN_VALUE, Integer.MAX_VALUE));
            }
        }
        for (int i = 0; i < ninters.size(); i++) {
            if (ninters.get(i).min != Integer.MIN_VALUE && ninters.get(i).max != Integer.MAX_VALUE) {
                res.add(ninters.get(i));
            }
        }
        return res.toArray(new Interval[res.size()]);
    }

}
