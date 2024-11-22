package sorting;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Arrays;

/**
 * The Olympic Games organizers need to allocate facilities for the athletes' training sessions.
 * Each team has a schedule of training sessions with a start and end time
 * 
 * To organize the athletes' training smoothly, the organizing committee must know
 * the minimum number of facilities they need so that the teams can train without overlap.
 * Each team has given the organizers their training slots,
 * represented by two integers timestamps: the start (included) and end time (not included!) of their session,
 * Given the training sessions' time, write the `minFacilitiesRequired` function,
 * which returns the minimum number of required facilities.
 *
 * Example Input with its visual representation:
 *
 * int[][] sessions = {
 *     {12, 20},//   --------
 *     {14, 25},//     -----------
 *     {19, 22},//          ---
 *     {25, 30},//                -----
 *     {26, 30},//                 ----
 * };
 *
 * In this example, the minimum number of facilities required is 3
 * as at time 19, there are 3 sessions (intervals) overlapping,
 * namely [12, 20), [14, 25), and [19, 22).
 *
 *
 * More formally, the goal is to minimize k such that for all time t,
 * the number of sessions that overlap at time t is at most k
 *
 * The computation must be performed in O(n.log(n)) time complexity
 * where n is the number of training sessions.
 *
 *
 */
public class TrainingSessions {

    /**
     * Determines the minimum number of facilities required to accommodate
     * all the training sessions without overlap.
     *
     * @param sessions a non-null array of int arrays where each int array represents
     *                 a session with start time and end time.
     * @return the minimum number of facilities required.
     */
/*    public int minFacilitiesRequired(int[][] sessions) {
        int start = sessions[0][0];
        int end = 0;
        for (int i = 0; i < sessions.length; i++) {
            if (sessions[i][0] < start) {
                start = sessions[i][0];
            }
            if (sessions[i][1] > end) {
                end = sessions[i][1];
            }
        }
        int[] slots = new int[end];
        for (int i = 0; i < sessions.length; i++) {
            int intstart = sessions[i][0];
            int intend = sessions[i][1];
            for (int j = intstart; j < intend; j++) {
                slots[j]++;
            }
        }
        Arrays.sort(slots);
        return slots[slots.length - 1];
    }*/


    public int minFacilitiesRequired(int[][] sessions) {

        ArrayList<int[]> events = new ArrayList<>();
        for (int[] session : sessions) {
            events.add(new int[]{session[0], 1});
            events.add(new int[]{session[1], -1});
        }

        events.sort(new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[0] - o2[0];
                }
            }
        });

        int curr = 0;
        int maxf = 0;

        for (int[] event : events) {
            curr += event[1];
            if (curr > maxf) {
                maxf = curr;
            }
        }
        return maxf;
    }


}
