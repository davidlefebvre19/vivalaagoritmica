package fundamentals;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {

    private ArrayList<List<T>> segmented;
    private int size;

    // TODO: Implement the SegmentedList interface here
    SegmentedListImpl() {
        segmented = new ArrayList<>();
        size = 0;
    }


    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        segmented.add(segment);
        size += segment.size();
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {
        segmented.remove(index);
        size -= segmented.get(index).size();
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
         return size;
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {
        Iterator<T> it = iterator();
        int idx = 0;
        while (it.hasNext()) {
            it.next();
            idx++;
            if (idx == globalIndex-1) {
                return it.next();
            }
        }
        throw new NoSuchElementException();
    }



    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
         return new SegmentedListIterator();
    }

    private class SegmentedListIterator implements Iterator<T> {

        private int total_size = 0;
        private int segments_counter = 0;
        private int segment_idx = 0;
        private int curr_segment_size = 0;
        private int curr_segment_idx = 0;

        SegmentedListIterator() {
            if (size != 0) {
                total_size = size;
                segments_counter = segmented.size();
                curr_segment_size = segmented.get(0).size();
            }
        }

        @Override
        public boolean hasNext() {
            if (total_size != size) {
                throw new ConcurrentModificationException();
            }
            if (curr_segment_idx >= curr_segment_size) {
                int future_segment_idx = segment_idx + 1;
                if (future_segment_idx >= segments_counter) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public T next() {
            if (total_size != size) {
                throw new ConcurrentModificationException();
            }
            if (hasNext()) {

                if (curr_segment_idx >= curr_segment_size) {
                    segment_idx++;
                    curr_segment_idx = 0;
                    T cargo = segmented.get(segment_idx).get(curr_segment_idx);
                    curr_segment_idx++;
                    return cargo;
                } else {
                    T cargo = segmented.get(segment_idx).get(curr_segment_idx);
                    curr_segment_idx++;
                    return cargo;
                }
            } throw new NoSuchElementException();
        }
    }
    public static void test () {
        SegmentedListImpl<Integer> sl = new SegmentedListImpl<>();
        List<Integer> l1 = new ArrayList<>(Arrays.asList(1,2));
        List<Integer> l2 = new ArrayList<>(Arrays.asList(3));
        List<Integer> l3 = new ArrayList<>(Arrays.asList(5,6));
        sl.addSegment(l1);
        sl.addSegment(l2);
        sl.addSegment(l3);
        Iterator<Integer> it = sl.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static void main(String[] args) {
        test();
    }

}

