package com.vibehub.fs.node;

import com.vibehub.fs.ctx.SizeContext;

public abstract class Node {
    public abstract NodeType type();
    public abstract String name();
    public abstract long size(SizeContext ctx);
}
