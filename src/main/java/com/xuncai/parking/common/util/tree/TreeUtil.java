package com.xuncai.parking.common.util.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    protected TreeUtil() {

    }

    private final static String TOP_NODE_ID = "0";

    /**
     * 有根节点
     * @param nodes nodes
     * @param <T>   <T>
     * @return <T> Tree<T>
     */
    public static <T> Tree<T> build(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null)
                        n.initChildren();
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty())
                topNodes.add(node);
        });


        Tree<T> root = new Tree<>();
        root.setId("0");
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChildren(true);
        root.setChildren(topNodes);
        root.setText("root");
        return root;
    }


    /**
     * 没有根节点
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> List<Tree<T>> getTreeList(List<Tree<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId();
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (Tree<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null)
                        n.initChildren();
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
            }
            if (topNodes.isEmpty())
                topNodes.add(node);
        });
        return topNodes;
    }


}