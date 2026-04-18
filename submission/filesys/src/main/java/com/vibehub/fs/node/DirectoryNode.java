package com.vibehub.fs.node;

import com.vibehub.fs.ctx.SizeContext;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DirectoryNode extends Node {
    private final String name;
    private final TreeMap<String, Node> children = new TreeMap<>();

    public DirectoryNode(String name) {
        this.name = name;
    }

    @Override
    public NodeType type() {
        return NodeType.DIRECTORY;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size(SizeContext ctx) {
        return children.values().stream()
                .mapToLong(child -> child.size(ctx))
                .sum();
    }

    public void putChild(String name, Node node) {
        children.put(name, node);
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public List<Node> listChildren() {
        return children.values().stream().collect(Collectors.toList());
    }
}
