package ru.buduschev.graph;

import java.util.List;

public interface AbstractEnumeratedGraph {
    int countVertices();
    List<Edge> getEdges();
}
