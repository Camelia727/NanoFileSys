package com.vibehub.fs.cmd;

import com.vibehub.fs.FileSystem;
import com.vibehub.fs.node.DirectoryNode;
import com.vibehub.fs.node.Node;

public class LsCommand implements Command {
    @Override
    public void execute(String[] args, FileSystem fs) {
        if (args.length < 1) {
            return;
        }
        String path = args[0];
        Node node = fs.resolve(path);
        if (node == null) {
            return;
        }

        if (node instanceof DirectoryNode) {
            DirectoryNode dir = (DirectoryNode) node;
            for (Node child : dir.listChildren()) {
                System.out.println(child.name());
            }
        } else {
            System.out.println(node.name());
        }
    }
}