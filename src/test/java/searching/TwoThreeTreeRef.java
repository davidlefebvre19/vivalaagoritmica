package searching;

import java.util.*;

public class TwoThreeTreeRef {

    // === Classe interne représentant un nœud 2-3 ===
    public static class Node23 {
        List<String> key;       // 1 clé (2-node) ou 2 clés (3-node)
        List<Node23> children;  // 0, 2 ou 3 enfants
        int size;               // taille du sous-arbre (passée explicitement)

        public Node23(List<String> key, List<Node23> children, int size) {
            this.key = key;
            this.children = (children == null) ? new ArrayList<>() : children;
            this.size = size;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    // === Attribut racine de l'arbre ===
    private Node23 root;

    // === Fonction principale de reconstruction ===
    public Node23 preorderTo23Tree(List<Object> preorder) {
        int[] index = {0};
        root = build(preorder, index, null, null);
        return root;
    }

    // === Fonction récursive de construction ===
    private Node23 build(List<Object> preorder, int[] index, String min, String max) {
        if (index[0] >= preorder.size()) return null;

        Object current = preorder.get(index[0]);
        List<String> keys = new ArrayList<>(2);

        if (current instanceof String) {
            keys.add((String) current); // 2-node
        } else if (current instanceof List<?>) {
            for (Object o : (List<?>) current) {
                keys.add((String) o);   // 3-node
            }
        } else {
            throw new IllegalArgumentException("Élément invalide dans la liste préordre");
        }

        // bornes d'ordre
        String firstKey = keys.get(0);
        if (min != null && firstKey.compareTo(min) <= 0) return null;
        String lastKey = keys.get(keys.size() - 1);
        if (max != null && lastKey.compareTo(max) >= 0) return null;

        index[0]++; // consommer cet élément

        List<Node23> children = new ArrayList<>();
        int size = 1; // compte le nœud courant

        if (keys.size() == 1) { // 2-node
            Node23 left  = build(preorder, index, min, keys.get(0));
            Node23 right = build(preorder, index, keys.get(0), max);

            if (left  != null) { children.add(left);  size += left.size;  }
            if (right != null) { children.add(right); size += right.size; }
        } else if (keys.size() == 2) { // 3-node
            String k1 = keys.get(0);
            String k2 = keys.get(1);

            Node23 left   = build(preorder, index, min, k1);
            Node23 middle = build(preorder, index, k1,  k2);
            Node23 right  = build(preorder, index, k2,  max);

            if (left   != null) { children.add(left);   size += left.size;   }
            if (middle != null) { children.add(middle); size += middle.size; }
            if (right  != null) { children.add(right);  size += right.size;  }
        }

        return new Node23(keys, children, size);
    }

    private void printPreorder(Node23 node, int depth) {
        if (node == null) return;
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println(node.key + " (size=" + node.size + ")");
        for (Node23 child : node.children) {
            printPreorder(child, depth + 1);
        }
    }

    // === Exemple d’utilisation ===
    public static void main(String[] args) {
        TwoThreeTreeRef treeBuilder = new TwoThreeTreeRef();

        // === Arbre 1 ===
        List<Object> preorder1 = Arrays.asList(
                "2",
                "1",
                Arrays.asList("3", "4")
        );
        System.out.println("\n=== Tree 1 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder1), 0);

        // === Arbre 2 ===
        List<Object> preorder2 = Arrays.asList(
                Arrays.asList("2", "4"),
                "1",
                "3",
                "5"
        );
        System.out.println("\n=== Tree 2 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder2), 0);

        // === Arbre 3 ===
        List<Object> preorder3 = Arrays.asList(
                Arrays.asList("2", "4"),
                "1",
                "3",
                Arrays.asList("5", "6")
        );
        System.out.println("\n=== Tree 3 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder3), 0);

        // === Arbre 4 ===
        List<Object> preorder4 = Arrays.asList(
                "4",
                "2",
                "1",
                "3",
                "6",
                "5",
                "7"
        );
        System.out.println("\n=== Tree 4 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder4), 0);
    }
}
