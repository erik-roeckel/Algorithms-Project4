import java.util.*;
import java.io.*;
import java.lang.*;

// method to return linked list of edges ajacent to a specified vertice
public class EdgeList
{
    private LinkedList<Edge> edgeList;
    
    public EdgeList()
    {
        edgeList = new LinkedList<Edge>();
    }
    // returns linked list of edges stored at certain vertice
    public LinkedList<Edge> edgeListAtV()
    {
        return edgeList;
    }
}