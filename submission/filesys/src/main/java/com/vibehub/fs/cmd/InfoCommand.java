package com.vibehub.fs.cmd;

import com.vibehub.fs.FileSystem;
import com.vibehub.fs.ctx.SizeContext;
import com.vibehub.fs.node.Node;

public class InfoCommand implements Command {
    @Override
    public void execute(String[] args, FileSystem fs) {
        if (args.length < 1) {
            return;
        }
        String path = args[0];
        if (!path.startsWith("/")) {
            return;
        }
        Node node = fs.resolve(path);
        if (node == null) {
            return;
        }
        System.out.println(node.size(new SizeContext()));
    }
}