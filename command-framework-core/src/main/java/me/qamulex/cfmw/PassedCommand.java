package me.qamulex.cfmw;

import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PassedCommand implements Iterable<String> {

    private final String command;
    private final List<String> pieces;
    @Accessors(fluent = true)
    private final boolean hasSpaceAtTheEnd;

    PassedCommand(String command) {
        this.command = command;
        this.pieces = Collections.unmodifiableList(Arrays.asList(command.trim().split(" ")));
        this.hasSpaceAtTheEnd = command.endsWith(" ");
    }

    public int countPieces(boolean includingSpaceAtTheEnd) {
        return pieces.size() + (includingSpaceAtTheEnd && hasSpaceAtTheEnd ? 1 : 0);
    }

    public int countPieces() {
        return countPieces(false);
    }

    public String getPiece(int index) {
        return pieces.get(index);
    }

    public String getRemaining(int index) {
        return String.join(" ", pieces.subList(index, pieces.size() - 1));
    }

    @Override
    public Iterator<String> iterator() {
        return pieces.iterator();
    }

    @Override
    public String toString() {
        return command;
    }

}
