package searching;

import java.util.*;

public class TwoThreeTreeRefacto {

    // === Résultat de construction : nœud + combien d’éléments consommés ===
    private static class BuildResult {
        final Node23 node;
        final int consumed; // nombre d’éléments de 'preorder' consommés
        BuildResult(Node23 node, int consumed) {
            this.node = node;
            this.consumed = consumed;
        }
    }

    // === Classe interne représentant un nœud 2-3 (taille passée explicitement) ===
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
        public String toString() { return key.toString(); }
    }

    // === Attribut racine de l'arbre ===
    private Node23 root;

    // === Fonction principale de reconstruction ===
    public Node23 preorderTo23Tree(List<Object> preorder) {
        BuildResult res = build(preorder, 0, null, null);
        root = res.node;
        return root;
    }

    // === Fonction récursive: prend un index 'i' et renvoie (node, consumed) ===
    private BuildResult build(List<Object> preorder, int i, String min, String max) {
        if (i >= preorder.size()) return new BuildResult(null, 0);

        Object current = preorder.get(i);
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

        // Vérification des bornes
        String firstKey = keys.get(0);
        if (min != null && firstKey.compareTo(min) <= 0) return new BuildResult(null, 0);
        String lastKey = keys.get(keys.size() - 1);
        if (max != null && lastKey.compareTo(max) >= 0) return new BuildResult(null, 0);

        int consumed = 1; // on consomme l’élément courant à l’index i
        int next = i + consumed;

        List<Node23> children = new ArrayList<>();
        int size = 1; // compte ce nœud

        if (keys.size() == 1) {
            // 2-node : gauche, droite
            BuildResult left  = build(preorder, next, min, keys.get(0));
            next += left.consumed;
            if (left.node != null) { children.add(left.node); size += left.node.size; }

            BuildResult right = build(preorder, next, keys.get(0), max);
            next += right.consumed;
            if (right.node != null) { children.add(right.node); size += right.node.size; }

            consumed = next - i;

        } else { // keys.size() == 2 : 3-node : gauche, milieu, droite
            String k1 = keys.get(0), k2 = keys.get(1);

            BuildResult left   = build(preorder, next, min, k1);
            next += left.consumed;
            if (left.node != null) { children.add(left.node); size += left.node.size; }

            BuildResult middle = build(preorder, next, k1,  k2);
            next += middle.consumed;
            if (middle.node != null) { children.add(middle.node); size += middle.node.size; }

            BuildResult right  = build(preorder, next, k2,  max);
            next += right.consumed;
            if (right.node != null) { children.add(right.node); size += right.node.size; }

            consumed = next - i;
        }

        return new BuildResult(new Node23(keys, children, size), consumed);
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
        TwoThreeTreeRefacto treeBuilder = new TwoThreeTreeRefacto();

        List<Object> preorder1 = Arrays.asList("2", "1", Arrays.asList("3", "4"));
        System.out.println("\n=== Tree 1 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder1), 0);

        List<Object> preorder2 = Arrays.asList(Arrays.asList("2", "4"), "1", "3", "5");
        System.out.println("\n=== Tree 2 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder2), 0);

        List<Object> preorder3 = Arrays.asList(Arrays.asList("2", "4"), "1", "3", Arrays.asList("5", "6"));
        System.out.println("\n=== Tree 3 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder3), 0);

        List<Object> preorder4 = Arrays.asList("4", "2", "1", "3", "6", "5", "7");
        System.out.println("\n=== Tree 4 ===");
        treeBuilder.printPreorder(treeBuilder.preorderTo23Tree(preorder4), 0);
    }
}
