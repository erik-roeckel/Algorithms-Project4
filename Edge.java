import java.util.*;
import java.io.*;
import java.lang.*;

// orginial edge class modified to hold variables for network
// to be used for undirected graph
public class Edge
{
    private int source;
    private int dest;
    private String type;
    private int bandwith;
    private int length;
    private int speed;
    private final int COPPER_SPEED = 230000000;
    private final int FIBER_SPEED = 200000000;
    private double weight;

    public Edge(int sourceVertex, int destVertex, String edgeType, int edgeBandwith, int edgeLength)
    {
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
    public int getSource()
    {
        return source;
    }
    public int getDest()
    {
        return dest;
    }
    public String getType()
    {
        return type;
    }
    public int getBandwith()
    {
        return bandwith;
    }
    public int getLength()
    {
        return length;
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
     * Returns a string representation of the undirected edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return source + "->" + dest + " ";
    }

}