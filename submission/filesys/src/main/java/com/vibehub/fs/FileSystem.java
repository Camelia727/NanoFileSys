package com.vibehub.fs;

import com.vibehub.fs.cmd.*;
import com.vibehub.fs.node.DirectoryNode;
import com.vibehub.fs.node.Node;
import com.vibehub.fs.util.PathUtil;

import java.util.List;
import java.util.Map;

public class FileSystem {
    private static final Map<String, Command> COMMANDS = Map.of(
            "MKDIR", new MkdirCommand(),
            "TOUCH", new TouchCommand(),
            "LS", new LsCommand(),
            "INFO", new InfoCommand()
    );

    public final DirectoryNode root = new DirectoryNode("/");

    public Node resolve(String absPath) {
        if (absPath.equals("/")) {
            return root;
        }
        List<String> segments = PathUtil.segments(absPath);
        Node current = root;
        for (String segment : segments) {
            if (!(current instanceof DirectoryNode)) {
                return null;
            }
            DirectoryNode dir = (DirectoryNode) current;
            Node child = dir.getChild(segment);
            if (child == null) {
                return null;
            }
            current = child;
        }
        return current;
    }

    public void executeCommand(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length == 0) {
            return;
        }
        String cmdName = parts[0];
        Command command = COMMANDS.get(cmdName);
        if (command != null) {
            String[] args = parts.length > 1
                    ? java.util.Arrays.copyOfRange(parts, 1, parts.length)
                    : new String[0];
            command.execute(args, this);
        }
    }
}
