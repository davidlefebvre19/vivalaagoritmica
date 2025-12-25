package strings;

import java.util.*;

/**
 * Implement the class WordCounter that counts the number of occurrences
 * of each word in a given piece of text.
 * Feel free to use existing java classes.
 */
public class WordCounter implements Iterable<String> {

    TreeMap<String, Integer> tm;


    public WordCounter() {
        tm = new TreeMap<>();
    }

    /**
     * Add the word so that the counter of the word is increased by 1
     */
    public void addWord(String word) {
        Integer curr = tm.get(word);
        if (curr == null) {
            tm.put(word, 1);
        }
        else {
            tm.put(word, ++curr);
        }
    }

    /**
     * Return the number of times the word has been added so far
     */
    public int getCount(String word) {
         if (tm.get(word) != null) return tm.get(word);
         else return 0;
    }

    // iterate over the words in ascending lexicographical order
    @Override
    public Iterator<String> iterator() {
         return new MapIterator<>(tm);
    }

    private class MapIterator<String> implements Iterator<String> {

        List<Map.Entry<String, Integer>> entrylist;
        int idx = 0;
        int size = 0;

        public MapIterator(TreeMap<String, Integer> tm) {
            Set<Map.Entry<String, Integer>> entrySet = tm.entrySet();
            entrylist = new ArrayList<>(entrySet);
            size = entrylist.size();
        }

        @Override
        public boolean hasNext() {
            return idx < size;
        }

        @Override
        public String next() {
            return entrylist.get(idx++).getKey();
        }
    }

    public static void main(String[] args) {
        WordCounter wc = new WordCounter();
        wc.addWord("cat");
        wc.addWord("dog");
        wc.addWord("cat");
        wc.addWord("bird");
        wc.addWord("cat");
        Iterator<String> ite = wc.iterator();
        while (ite.hasNext()) {
            System.out.println(ite.next());
        }
        System.out.println(wc.getCount("cat"));
    }
}
