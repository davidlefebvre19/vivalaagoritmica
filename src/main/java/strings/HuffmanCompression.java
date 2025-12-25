package strings;
import sorting.MinPQ;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class HuffmanCompression {

    private static int R = 256; // number of elements in ASCII table

    public static void compress(String s) {
        char[] input = s.toCharArray();

        // Read frequencies of different chars
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++; // Every non null element of freq will now contain the associated freq : remember we convert char to int and use it as index for constant access
        }

        // Huffman trie construction
        Node root = buildTrie(freq);

        // Build code table from the huffman Trie (simple array where index is (int) char and value is bitstring retrieved from huffman trie)
        String[] st = buildCode(root);

        // It now necessary to serialize the huffman trie for decoding op
        List<Integer> q = new LinkedList<>();
        writeTrie(root, q);
        int[] deco = new int[q.size()];
        for (int i = 0; i < q.size(); i++) {
            deco[i] = q.get(i);
        }
        System.out.println("Huffman trie serialization: " + q);

        // encode op
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                System.out.println(code.charAt(j));
            }
        }
    }

    private static class Node implements Comparable<Node>{
        private char ch;
        private int freq;
        private final Node left, right;

        private Node(char ch, int freq, Node left, Node right) {
            this.left = left;
            this.right = right;
            this.freq = freq;
            this.ch = ch;
        }

        public boolean isLeaf(){
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    private static Node buildTrie(int[] freq) {
        // First, forest of trees inserted in a pq
        MinPQ<Node> pq = new MinPQ<>(freq.length);

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0) pq.insert(new Node((char) i, freq[i], null, null));
        }

        while (pq.size() > 1) { // merging trees together (see pg 831 of the book)
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node newNode = new Node('\0', x.freq+y.freq, x, y);
            pq.insert(newNode);
        }

        return pq.delMin(); //we retrieve the one and only root
    }

    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if(x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s+'0');
        buildCode(st, x.right, s+'1');
    }

    // serialize trie : we use preorder traversal to encode the trie as a bitstream
    // When an internal node is encountered we write a single 0, when a leaf is incountered, we write a 1 followed by the ASCII encoding of the char contained in the node
    private static void writeTrie(Node x, List<Integer> q) {
        if (x.isLeaf()) {
            q.add(1);
            q.add((int) x.ch);
            return;
        }
        q.add(0);
        writeTrie(x.left, q);
        writeTrie(x.right, q);
    }

    public static void main(String[] args) {
        compress("AB");
    }
}
