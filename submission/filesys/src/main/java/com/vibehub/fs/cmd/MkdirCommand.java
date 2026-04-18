package com.vibehub.fs.cmd;

import com.vibehub.fs.FileSystem;
import com.vibehub.fs.node.DirectoryNode;
import com.vibehub.fs.node.Node;
import com.vibehub.fs.util.PathUtil;

import java.util.List;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args, FileSystem fs) {
        if (args.length < 1) {
            return;
        }
        String path = args[0];
        List<String> segments = PathUtil.segments(path);
        if (segments.isEmpty()) {
            return;
        }

        Node current = fs.root;
        for (int i = 0; i < segments.size() - 1; i++) {
            if (!(current instanceof DirectoryNode)) {
                return;
            }
            DirectoryNode dir = (DirectoryNode) current;
            Node child = dir.getChild(segments.get(i));
            if (child == null) {
                return;
            }
            current = child;
        }

        if (!(current instanceof DirectoryNode)) {
            return;
        }
        DirectoryNode parentDir = (DirectoryNode) current;
        String lastName = segments.get(segments.size() - 1);
        if (parentDir.getChild(lastName) == null) {
            parentDir.putChild(lastName, new DirectoryNode(lastName));
        }
    }
}
