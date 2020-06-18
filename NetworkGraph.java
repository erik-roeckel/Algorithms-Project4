import java.util.*;
import java.io.*;
import java.lang.*;

public class NetworkGraph
{
    private static final String NEWLINE = System.getProperty("line.separator");
    private EdgeList[] adjacencyList;
    private int numVertices;
    private int numEdges = 0;

    // constructor that makes undirected graph from command line specified file
    public NetworkGraph(String file) throws IOException
    {
        makeGraph(file);
    }

    private void makeGraph(String file) throws IOException
    {
        if(file == null)
        {
            System.out.println("You provided no file for inputing network data");
            return;
        }
        String edgeInfo;
        String[] organizeEdgeInfo;
        BufferedReader readFile; 
        try 
        {
            readFile = new BufferedReader(new FileReader(file));
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("This file was not found!");
            return;
        }
        numVertices = Integer.parseInt(readFile.readLine());
        adjacencyList = new EdgeList[numVertices];
        for(int i = 0; i < adjacencyList.length; i++)
            adjacencyList[i] = new EdgeList();

        while(true)
        {
            edgeInfo = readFile.readLine();
            if(edgeInfo == null)
                break;
            organizeEdgeInfo = edgeInfo.split(" ");
            int vertexSource = Integer.parseInt(organizeEdgeInfo[0]);
            int vertexDest = Integer.parseInt(organizeEdgeInfo[1]);
            String type = organizeEdgeInfo[2];
            int bandwith = Integer.parseInt(organizeEdgeInfo[3]);
            int length =  Integer.parseInt(organizeEdgeInfo[4]);
            Edge edgeSourceToDest = new Edge(vertexSource, vertexDest, type, bandwith, length);
            Edge edgeDestToSource = new Edge(vertexDest, vertexSource, type, bandwith, length);
            addEdge(edgeSourceToDest, edgeDestToSource);

        }
        readFile.close();
        
    }
    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int V() {
        return numVertices;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
     */
    public int E() {
        return numEdges;
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void addEdge(Edge e, Edge f) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adjacencyList[v].edgeListAtV().add(e);
        adjacencyList[w].edgeListAtV().add(f);
        numEdges++;
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adjacencyList[v].edgeListAtV();
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the degree of vertex {@code v}               
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adjacencyList[v].edgeListAtV().size();
    }

    /**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        LinkedList<Edge> list = new LinkedList<Edge>();
        for (int v = 0; v < numVertices; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // add only one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() 
    {
        StringBuilder s = new StringBuilder();
        s.append(numVertices + " " + numEdges + NEWLINE);
        for (int v = 0; v < numVertices; v++) 
        {
            s.append(v + ": ");
            s.append(adjacencyList[v].edgeListAtV() + "  ");
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
    private void validateVertex(int v) 
    {
        if (v < 0 || v >= numVertices)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (numVertices-1));
    }

    public void printGraph() 
    {
        System.out.println("Number of Vertices: " + numVertices);
        System.out.println("Number of Edges: " + numEdges);
        for(int i = 0; i < adjacencyList.length; i++)
            System.out.println(adjacencyList[i].edgeListAtV());
    }
}
