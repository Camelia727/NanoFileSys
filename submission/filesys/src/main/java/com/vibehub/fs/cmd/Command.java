package com.vibehub.fs.cmd;

import com.vibehub.fs.FileSystem;

public interface Command {
    void execute(String[] args, FileSystem fs);
}
