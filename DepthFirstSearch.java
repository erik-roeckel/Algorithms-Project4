/******************************************************************************
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 ******************************************************************************/

/**
 *  The {@code DepthFirstSearch} class represents a data type for 
 *  determining the vertices connected to a given source vertex <em>s</em>
 *  in an undirected graph. For versions that find the paths, see
 *  {@link DepthFirstPaths} and {@link BreadthFirstPaths}.
 *  <p>
 *  This implementation uses depth-first search.
 *  See {@link NonrecursiveDFS} for a non-recursive version.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DepthFirstSearch {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int count;           // number of vertices connected to s

    /**
     * Computes the vertices in graph {@code G} that are
     * connected to the source vertex {@code s}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DepthFirstSearch(EdgeWeightedDigraph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    // constructor for depth first search of undirected network graph
    // s --> source vertex to start traversal from
    // params i and j used to represent vertices pairs to be removed (marked)
    public DepthFirstSearch(NetworkGraph G, int s, int i, int j)
    {
        String mode = "failure";   
        marked = new boolean[G.V()];
        validateVertex(s);
        marked[i] = true;
        marked[j] = true;
        dfs(G, s, mode);
    }

    // depth first search from v, used on weighted directed graph, with dual way edges
    // dfs used to check for copper connection
    private void dfs(EdgeWeightedDigraph G, int v) {
        count++;
        marked[v] = true;
        for (DirectedEdge w : G.adj(v)) {
            if (!marked[w.other(v)] && w.getType().equals("copper")) {
                dfs(G, w.other(v));
            }
        }
    }
    // depth first search from v, used for finding connection after removing every pair of vertices
    // extra parameter, mode, to ensure there is no confusion with other dfs method, uses undirected graph
    private void dfs(NetworkGraph G, int v, String mode)
    {
        count++;
        marked[v] = true;
        for(Edge w : G.adj(v))
        {
            if(!marked[w.other(v)])
                dfs(G, w.other(v), mode);
        }
    }

    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of vertices connected to the source vertex {@code s}.
     * @return the number of vertices connected to the source vertex {@code s}
     */
    public int count() {
        return count;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

}
