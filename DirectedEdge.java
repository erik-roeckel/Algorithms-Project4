/******************************************************************************
 * Directed Edge class modified to hold extra variables for network
 *
 *  Immutable weighted directed edge.
 *
 ******************************************************************************/
import java.io.*;
/**
 *  The {@code DirectedEdge} class represents a weighted edge in an 
 *  {@link EdgeWeightedDigraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the directed edge and
 *  the weight.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class DirectedEdge { 
    private final int source;
    private final int dest;
    private String type;
    private int bandwith;
    private int length;
    private int speed;
    private final int COPPER_SPEED = 230000000;
    private final int FIBER_SPEED = 200000000;
    private double weight;

    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w} with
     * the given {@code weight}.
     * @param sourceVertex the tail vertex
     * @param destVertex the head vertex
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public DirectedEdge(int sourceVertex, int destVertex, String edgeType, int edgeBandwith, int edgeLength) 
    {
        if (sourceVertex < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (destVertex < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        //if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        source = sourceVertex;
        dest = destVertex;
        type = edgeType;
        bandwith = edgeBandwith;
        length = edgeLength;
        if(edgeType.equals("copper"))
        {
            speed = COPPER_SPEED; // if type field is copper then set speed to be copper
        }
        else
        {
            speed = FIBER_SPEED; // if type field is fiber optic then set speed to be fiber optic
        }
        weight = (double)length/(double)speed; // calculates weight of edges based on length/speed
    }
    public int getBandwith()
    {
        return bandwith;
    }

    public String getType()
    {
        return type;
    }

    /**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return source;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return dest;
    }

    public int either()
    {
        return source;
    }
    public int other(int vertex)
    {
        if(vertex == source) 
            return dest;
        else if(vertex == dest) 
            return source;
        else throw new IllegalArgumentException("Illegal Endpoint");
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return source + "->" + dest + " ";
    }

}
