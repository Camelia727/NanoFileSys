package com.vibehub.fs.node;

import com.vibehub.fs.ctx.SizeContext;

public class FileNode extends Node {
    private final String name;
    private final long size;

    public FileNode(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public NodeType type() {
        return NodeType.FILE;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size(SizeContext ctx) {
        return size;
    }
}
