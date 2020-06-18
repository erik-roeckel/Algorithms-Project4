
import java.util.*;
import java.io.*;
import java.lang.*;

// class to store linked list of vertices adjacent to specified vertice in adjaceny list
public class DirectedEdgeList
{
    private LinkedList<DirectedEdge> edgeList;
    
    public DirectedEdgeList()
    {
        edgeList = new LinkedList<DirectedEdge>();
    }
    // returns linked list of edges stored at certain vertice
    public LinkedList<DirectedEdge> edgeDirectedListAtV()
    {
        return edgeList;
    }
}