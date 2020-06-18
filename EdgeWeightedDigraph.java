/******************************************************************************
 * 
 *  Graph modified to store a directed from vertex A to B as well as a directed edge from
 *  vertex B to A
 *  An edge-weighted digraph, implemented using adjacency lists.
 *  Also modified
 ******************************************************************************/
import java.util.*;
import java.io.*;
import java.lang.*;
/**
 *  The {@code EdgeWeightedDigraph} class represents a edge-weighted
 *  digraph of vertices named 0 through <em>V</em> - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which 
 *  is a vertex-indexed array of {@link Bag} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident from a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int numVertices;                // number of vertices in this digraph
    private int E;                      // number of edges in this digraph
    private DirectedEdgeList[] adjacencyList;    // adj[v] = adjacency list for vertex v
    private int[] indegree;             // indegree[v] = indegree of vertex v
    

    // constructor for making dual way Digraph from command line specified file
    public EdgeWeightedDigraph(String file) throws IOException
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
        this.indegree = new int[numVertices];
        adjacencyList = new DirectedEdgeList[numVertices];
        for(int i = 0; i < adjacencyList.length; i++)
            adjacencyList[i] = new DirectedEdgeList();
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

            addEdge(new DirectedEdge(vertexSource, vertexDest, type, bandwith, length));
            addEdge(new DirectedEdge(vertexDest, vertexSource, type, bandwith, length));
        }
        readFile.close();
        
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return numVertices;
    }

    /**
     * Returns the number of edges in this edge-weighted digraph.
     *
     * @return the number of edges in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= numVertices)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (numVertices-1));
    }

    /**
     * Adds the directed edge {@code e} to this edge-weighted digraph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between {@code 0}
     *         and {@code V-1}
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adjacencyList[v].edgeDirectedListAtV().add(e);
        indegree[w]++;
        E++;
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adjacencyList[v].edgeDirectedListAtV().size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }
     /**
     * Returns Linked list of directed edges incident from vertex {@code v}.
     *
     * @param  v the vertex
     * @return the directed edges incident from vertex {@code v} as iterable linked list
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public LinkedList<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adjacencyList[v].edgeDirectedListAtV();
    }

    /**
     * Returns all directed edges in this edge-weighted digraph.
     * To iterate over the edges in this edge-weighted digraph, use foreach notation:
     * {@code for (DirectedEdge e : G.edges())}.
     *
     * @return all edges in this edge-weighted digraph, as an iterable
     */
    public Iterable<DirectedEdge> edges() {
        LinkedList<DirectedEdge> list = new LinkedList<DirectedEdge>();
        for (int v = 0; v < numVertices; v++) {
            {
                ListIterator<DirectedEdge> iterate = adj(v).listIterator(0);
                while(iterate.hasNext())
                {
                    list.add(iterate.next());
                }
            }
        }
        return list;
    }
    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
         StringBuilder s = new StringBuilder();
         s.append(numVertices + " " + E + NEWLINE);
         for (int v = 0; v < numVertices; v++) 
         {
            s.append(v + ": ");
            s.append(adjacencyList[v].edgeDirectedListAtV() + "  ");
            s.append(NEWLINE);
        }
         return s.toString();
     }
    public void printGraph() 
    {
        System.out.println("Number of Vertices: " + numVertices);
        for(int i = 0; i < adjacencyList.length; i++)
            System.out.println(adjacencyList[i].edgeDirectedListAtV());
    }
}
