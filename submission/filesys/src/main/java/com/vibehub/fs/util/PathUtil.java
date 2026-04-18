package com.vibehub.fs.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PathUtil {

    public static List<String> segments(String absPath) {
        if (absPath == null || absPath.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(absPath.substring(1).split("/"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
