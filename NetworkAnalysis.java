import java.util.*;
import java.io.*;
import java.lang.*;

/*
* Erik Roeckel
* CS 1501 
* Project 4
*/
public class NetworkAnalysis
{
    private static Scanner userInput = new Scanner(System.in);
    private static NetworkGraph networkGraph; // undirected graph, with modifications for storing extra fields relating to network
    private static EdgeWeightedDigraph dualWayDiGraph; // directed graph, with edges that point both ways for every edge

    public static void main(String args[]) throws IOException
    {
        networkGraph = new NetworkGraph(args[0]); //create new undirected graph based on file
        dualWayDiGraph = new EdgeWeightedDigraph(args[0]); // create new directed (dual way) graph based on file
        int userChoice;
        System.out.println("Welcome to my network analysis program!");
        
        while(true)
        {
            menuOptions();
            userChoice = userInput.nextInt();
            while(userChoice <= 0 || userChoice > 5) //ensures the user enters a valid menu option
            {
                System.out.println("You did not enter a valid menu option please choose again (1-5)");
                menuOptions();
                userChoice = userInput.nextInt();
            }
            menuChoice(userChoice);
  
        }
        
    }
    //displays menu options for user to chose from
    public static void menuOptions()
    {
        System.out.println("Select an option (1-5):");
        System.out.println("------------------------------------------------------");
        System.out.println("(1) Find the lowest latency path between two points");
        System.out.println("(2) Determine whether or not the network is copper-only connected");
        System.out.println("(3) Find the lowest average latency spanning tree");
        System.out.println("(4) Determine whether or not the graph remains connected if any two vertices in network fail");
        System.out.println("(5) Quit this network analysis program!");
        System.out.println("------------------------------------------------------");

    }

    public static void menuChoice(int option)
    {
        switch(option)
        {
            case 1: //get shortest path from user specified vertices
            {
                System.out.println("You chose option 1:\n");
                getShortestPath();
                break;
            }
            case 2: //check if graph is connected through all copper links
            {
                System.out.println("You chose option 2:\n");
                copperConnected();
                break;
            }
            case 3: // obtain min avg spanning tree for graph
            {
                System.out.println("You chose option 3:\n");
                averageMinLatencyTree();
                break;
            }
            case 4: // check for connection after removing any pair of vertices in graph
            {
                System.out.println("You chose option 4:\n");
                connectedIfRemoved();
                break;
            }
            case 5:
            {
                System.out.println("You chose to quit, thanks for using my network analysis program!");
                System.exit(0);
                break;
            }
        }
    }
    
    public static void getShortestPath()
    {
        System.out.println("Please enter the vertices which you wish to find the shortest path for: ");
        System.out.print("Enter first vertice: ");
        int vertice1 = userInput.nextInt();
        System.out.print("Enter second vertice: ");
        int vertice2 = userInput.nextInt();
        DijkstraAllPairsSP shortestPath = new DijkstraAllPairsSP(dualWayDiGraph);
        if(shortestPath.hasPath(vertice1, vertice2))
        {
            boolean setMinBandwith = true;
            int minBandwith = 0;
            Iterable<DirectedEdge> iterateSP = shortestPath.path(vertice1, vertice2);
            String path = shortestPath.path(vertice1, vertice2).toString(); 
            double latency = shortestPath.dist(vertice1, vertice2);
            System.out.println("\nLowest Latency Path: ");
            System.out.println("-------------------------------------------");
            System.out.println("Edges of Lowest Latency Path from " + vertice1 + " to " + vertice2 + ":");
            System.out.println(path);
            System.out.println("-------------------------------------------");
            System.out.print("Total Latency along path: ");
            System.out.printf("%6.10f " + "seconds\n", latency);
                    
            //loops for every edge in shortest path, adding the bandwith each time
            for(DirectedEdge e: iterateSP)
            {
                int currBandwith = e.getBandwith();
                if(currBandwith < minBandwith)
                {
                    minBandwith = currBandwith;
                }
                if(setMinBandwith == true)
                {
                    minBandwith = currBandwith;
                    setMinBandwith = false;
                }

            }
            
            System.out.print("Min bandwith along path: " + minBandwith + "\n");
        }
    }

    public static void copperConnected()
    {   
        int amountCopper = 0;
        DepthFirstSearch searchCopper = new DepthFirstSearch(dualWayDiGraph, 0);
        amountCopper = searchCopper.count();
        if(amountCopper == dualWayDiGraph.V())
            System.out.println("Network is connected if using only copper links!");
        else
            System.out.println("Network is not connected if using only copper links!");
    }

    public static void averageMinLatencyTree()
    {
        double totalLatency = 0; // keeps track of total latency for min spanning tree
        int minEdges = 0; // keep track of number of edges making up min spanning tree
        PrimMST mst = new PrimMST(dualWayDiGraph); // instantiate new PrimMST to find avg minimum spanning tree
        System.out.println("Average Min Latency Spanning Tree (Edges that make up this tree): ");
        for (DirectedEdge e : mst.edges()) 
        {
            minEdges++;
            System.out.print("  " + e); // print the edges that make up this avg min spanning tree
        }
        totalLatency = mst.weight(); // stores the total weight of all the edges in the MST
        System.out.println("\n");
        System.out.print("Total Latency of this Min Spanning Tree: ");
        System.out.printf("%.10f\n\n", totalLatency); // formats total latency to print with 10 decimals
        System.out.print("Min Average Latency Per Edge: ");
        System.out.printf("%.11f\n\n", (totalLatency/minEdges)); // formats avg latency to print with 11 decimals

    }

    public static void connectedIfRemoved()
    {
        // ArrayList used to store strings of failed pairs in form: (i, k)
        ArrayList<String> failedPairs = new ArrayList<String>();
        int count = 0;
        int start = 0; // starting index to dfs from
        //double nested for loop to depth first search through every unmarked vertice
        for(int i = 0; i < (networkGraph.V() - 1); i++)
        {
            for(int k = i + 1; k < networkGraph.V(); k++)
            {
                if(i != 0)
                    start = 0;
                else
                {
                    if(k - i != 1)
                        start = k - 1;
                    else if(k != networkGraph.V() -1)
                        start = k + 1;
                    else // handles the corner case that if there are only 2 vertices in graph both will be
                        {
                            System.out.println("This graph only contains 2 vertices, therefore failure of any pair of these veritces will disconnect network");
                            System.out.print("Pair of Vertices causing disconnection: " + "(" + i + ", " + k + ")\n");
                            return;
                        }

                }
                //Instantiates new depth first search on undirected network graph
                //starts the traversal at set start index specified by coniditional statements above
                // searches through all unmarked pairs of vertices (i,k)
                DepthFirstSearch dfs = new DepthFirstSearch(networkGraph, start, i, k);

                // if the count of vertices after performing dfs is less than that of the
                //total amount of vertices minus the two removed
                if(dfs.count() < networkGraph.V() -2)
                {
                    //adds a string to arraylist to represent pairs of vertices that cause graph connection to fail
                    String addFailedPair = "(" + i + ", " + k + ")";
                    failedPairs.add(addFailedPair);
                }
                count = dfs.count();

            }
        }

        if(failedPairs.size() > 0) // if there is anything contained in arrayList this means connection would fail
        {
            System.out.println("With failure of any of the following vertice pairs, the network is no longer connected: ");
            System.out.println("-------------------------------------------------------------------");
            for(int i = 0; i < failedPairs.size(); i++)
                System.out.print(failedPairs.get(i) + "  "); //prints the pairs of vertices that if removed would disconnect graph
            System.out.println("\n");
            System.out.println("---------------------------------------------------------------");
        }
        else // if there is nothing in arrayList this means no pairs of vertices being removed would fail connection
        {
            System.out.println("With failure of any pair of vertices, this network is still connnected!");
        }

    }

}