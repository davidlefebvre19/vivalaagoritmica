package searching;

public class HashTables {
    public class SequentialSearchST<Key, Value> {
        private Node first;

        private class Node {
            Key key;
            Value value;
            Node next;

            public Node(Key key, Value value, Node next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        public Value get(Key key) {
            for (Node x = first; x != null ; x = x.next) {
                if (x.key.equals(key)) return x.value;
            }
            return null;
        }

        public void put(Key key, Value value) {
            for (Node x = first; x != null ; x = x.next) {
                if (x.key.equals(key)) {
                    x.value = value;
                    return;
                }
            }
            first = new Node(key, value, first);
        }
    }

/*    public static class Hashing { // Hashing compound types example
        private final String who;
        private final Date when;
        private final double amount;
        public int hashCode()
        {
            int hash = 17;
            hash = 31 * hash + who.hashCode();
            hash = 31 * hash + when.hashCode();
            hash = 31 * hash
                    + ((Double) amount).hashCode();
            return hash;
        }
    }*/

    public class SeparateChainingHashST<Key, Value> {
        private int N; //Number of key val pairs
        private int M; // hash table size, preferably a prime number and not a power of 2
        private SequentialSearchST<Key, Value>[] st;

        public SeparateChainingHashST(int M) {
            this.M = M;
            // Create M linked list
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
            for (int i = 0; i < M; i++) {
                st[i] = new SequentialSearchST<>();
            }
        }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % this.M;
        }

        public Value get(Key key) {
            return st[hash(key)].get(key);
        }

        public void put(Key key, Value value) {
            st[hash(key)].put(key, value);
        }
    }

    public class LinearProbingST<Key, Value> {
        private int N; // num
        private int M = 16; // Table size, default val at 16
        private Key[] keys;
        private Value[] vals;

        public LinearProbingST(int cap){
            this.M = cap;
            keys = (Key[]) new Object[cap];
            vals = (Value[]) new Object[cap];
        }

        public LinearProbingST(){
            keys = (Key[]) new Object[M];
            vals = (Value[]) new Object[M];
        }

        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % this.M;
        }

        private void resize(int cap) {
            LinearProbingST<Key, Value> t;
            t = new LinearProbingST<Key, Value>(cap);
            for (int i = 0; i < M; i++)
                if (keys[i] != null)
                    t.put(keys[i], vals[i]);
            keys = t.keys;
            vals = t.vals;
            M = t.M;
        }

        public void put(Key key, Value value) {
            if (N >= M/2) resize(2*M);
            int i;
            for (i = hash(key); keys[i] != null; i = (i+1)%M) {
                if(keys[i].equals(key)) {
                    vals[i] = value;
                    return;
                }
            }
            keys[i] = key;
            vals[i] = value;
            N++;
        }

        public Value get(Key key) {
            int i;
            for (i = hash(key); keys[i] != null ; i = (i+1)%M) {
                if (keys[i].equals(key)) return vals[i];
            }
            return null;
        }
    }
}
