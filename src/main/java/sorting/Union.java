package sorting;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public class UnionHelper {

        int[] id;
        int[] sz;
        int count;

        public UnionHelper(int N) {
            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
            count = N;
        }

        public void union(int p, int q) {
            int proot = find(p);
            int qroot = find(q);

            if (sz[p] < sz[q]) {
                id[proot] = qroot;
                sz[qroot] += sz[proot];
            } else {
                id[qroot] = proot;
                sz[proot] += sz[qroot];
            }

            count--;
        }

        public int find (int p) {
            while (p != id[p]) p = id[p];
            return p;
        }

        public boolean connected (int p, int q) {
            return find(p) == find(q);
        }

        public int count() {
            return count;
        }

    }

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
        Interval[] ints = Arrays.copyOf(intervals, intervals.length);
        Arrays.sort(ints);

        List<Interval> data = new ArrayList<>();
        data.add(ints[0]);

        for (int i = 0; i < intervals.length-1; i++) {


            Interval curr = data.get(data.size()-1);

            if(curr.max >= ints[i+1].min) {
                Interval newint = new Interval(curr.min, Math.max(ints[i+1].max, curr.max)  );
                data.remove(data.size()-1);
                data.add(newint);
            }
            else data.add(ints[i+1]);
        }

        if (ints[ints.length-1].min > data.get(data.size()-1).max) data.add(ints[ints.length-1]);

        Interval[] ret = new Interval[data.size()];
        for (int i = 0; i < data.size(); i++) {
            ret[i] = data.get(i);
        }

        return ret;

    }

    public static void main(String[] args) {
        Union.Interval i0 = new Union.Interval(7, 9);
        Union.Interval i1 = new Union.Interval(1, 3);
        Union.Interval i2 = new Union.Interval(1, 3);
        Union.Interval[] result = Union.union(new Union.Interval[]{i1, i2});

        i0 = new Union.Interval(10, 10);
        i1 = new Union.Interval(2, 4);
        i2 = new Union.Interval(3, 4);
        Union.Interval i3 = new Union.Interval(5, 6);
        Union.Interval i4 = new Union.Interval(6, 9);
        Union.Interval i5 = new Union.Interval(6, 8);
        result = Union.union(new Union.Interval[]{i0, i1, i2, i3, i4, i5});

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i].toString());
        }
    }

}
