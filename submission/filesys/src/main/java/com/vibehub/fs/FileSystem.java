package com.vibehub.fs;

import com.vibehub.fs.node.DirectoryNode;
import com.vibehub.fs.node.Node;

public class FileSystem {
    public final DirectoryNode root = new DirectoryNode("/");

    public Node resolve(String absPath) {
        if (absPath.equals("/")) {
            return root;
        }
        return null;
    }

    public void executeCommand(String line) {
        // Placeholder for command execution
    }
}
