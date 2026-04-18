package com.vibehub.fs.cmd;

import com.vibehub.fs.FileSystem;
import com.vibehub.fs.node.DirectoryNode;
import com.vibehub.fs.node.FileNode;
import com.vibehub.fs.node.Node;
import com.vibehub.fs.util.PathUtil;

import java.util.List;

public class TouchCommand implements Command {
    @Override
    public void execute(String[] args, FileSystem fs) {
        if (args.length < 2) {
            return;
        }
        String path = args[0];
        if (!path.startsWith("/")) {
            return;
        }
        long size;
        try {
            size = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            return;
        }
        if (size < 0) {
            size = 0;
        }
        List<String> segments = PathUtil.segments(path);
        if (segments.isEmpty()) {
            return;
        }

        Node parent = fs.root;
        for (int i = 0; i < segments.size() - 1; i++) {
            if (!(parent instanceof DirectoryNode)) {
                return;
            }
            DirectoryNode dir = (DirectoryNode) parent;
            Node child = dir.getChild(segments.get(i));
            if (child == null) {
                return;
            }
            parent = child;
        }

        if (!(parent instanceof DirectoryNode)) {
            return;
        }
        DirectoryNode dir = (DirectoryNode) parent;
        String fileName = segments.get(segments.size() - 1);
        dir.putChild(fileName, new FileNode(fileName, size));
    }
}