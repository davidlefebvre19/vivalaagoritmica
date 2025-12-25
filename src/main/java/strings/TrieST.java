package strings;


import java.util.LinkedList;
import java.util.Queue;

public class TrieST<Value> {

    private static int R=256;
    private Node root;
    private int n;

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    // Create symbol table
    public TrieST(){
        // Empty construtor
    }

    public void put(String key, Value value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == null) n++; // if we're adding a new key we have to increase size otherwise it's just an update of the val of the key
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }

    public Value get(String key){
        if (root == null) return null;
        Node data = get(root, key, 0);
        return (Value) data.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void delete(String key) {
        // Oof
    }

    public boolean contains(String key) {
        if (get(key) != null) return true;
        return false;
    }

    public boolean isEmpty() {
        return n ==0;
    }

    public int size() { // eager implementation, see also very eager imp and lazy implementation (= bad perf)
        return n;
    }

    public Iterable<String> keys() {
        return keysWithPrefix(""); // every key start with ""
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new LinkedList<>(); // will contains the prefixes
        collect(get(root, pre, 0), pre, q); // will collect the prefixes that starts with pre, this is why we start with a get on the prefix pre as argument of the collect method
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.add(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre+c, q);
        }
    }

    // Similarly we have keys that match a certain pattern, we will modify the collect method
    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where the character '.' is interpreted as a wildcard character.
     * @param pat the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new LinkedList<>();
        collect(root, "", pat, q);
        return q;
    }

    public void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) return;
        if (d == pat.length() && x.val != null) q.add(pre);
        if (d == pat.length()) return;

        char next = pat.charAt(d);
        for (char c = 0; c < R ; c++) {
            if (next == '.' || next == c) collect(x.next[c], pre+c, pat, q);
        }
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    // SEE ALSO DELETE METHOD
}
    

