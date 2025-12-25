package sorting;

public class UF {
    private int[] id;
    private int count;

    public UF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        return id[p];
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) {
            return;
        }

        for (int i = 0; i < id.length; i++) {
            if (find(i) == pID ) {
                id[i] = qID;
            }
        }
        count--;
    }

    public static void main(String[] args) {
        int [][] graph = {
                {4,3},
                {3,8},
                {6,5},
                {9,4},
                {2,1},
                {5,0},
                {7,2},
                {6,1}
        };
        UF uf = new UF(10);
        for (int i = 0; i < graph.length; i++) {
            int p = graph[i][0];
            int q = graph[i][1];
            uf.union(p,q);
        }
        for (int i = 0; i < uf.id.length; i++) {
            System.out.println(uf.id[i]);
        }
        System.out.println(uf.count());
    }
}

class QuickUF {
    private int[] id;
    private int count;
    private int[] sz;

    public QuickUF(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;

/*        // Quick union
        id[pRoot] = qRoot;*/

        // Weighted union
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }

        count--;
    }

    public static void main(String[] args) {
        int [][] graph = {
                {4,3},
                {3,8},
                {6,5},
                {9,4},
                {2,1},
                {5,0},
                {7,2},
                {6,1}
        };
/*        UF uf = new UF(10);
        for (int i = 0; i < graph.length; i++) {
            int p = graph[i][0];
            int q = graph[i][1];
            uf.union(p,q);
        }
        for (int i = 0; i < uf.id.length; i++) {
            System.out.println(uf.id[i]);
        }
        System.out.println(uf.count());*/
    }
}