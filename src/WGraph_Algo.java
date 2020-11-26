package ex1.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WGraph_Algo implements weighted_graph_algorithms {
    private WGraph_DS graph;

    @Override
    public void init(weighted_graph g) {
        this.graph = (WGraph_DS) g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        return new WGraph_DS((WGraph_DS) graph);
    }

    @Override
    public boolean isConnected() {
        //get all the nodes to iterator
        //if (graph.getV().isEmpty()){return false;}
        Iterator it=graph.getV().iterator();
        if(it.hasNext()) {
            node_info node = (node_info) it.next();
            //choose one node and then operate fbs on it(it will try to each node to tag=1.
            bfsNodesTagging(node);
        }
        //then we will check if any one of the tags is unvisited. if it unvisited so return false and the graph is not connected
        Iterator isTaggedNode=graph.getV().iterator();
        while(isTaggedNode.hasNext()){
            if(((node_info) isTaggedNode.next()).getTag()==-1)return false;
        }
        return true;
    }

    /**
     * source idea code that i used for help: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
     *
     * @param src
     * @return
     */
    private int bfsNodesTagging(node_info src) {

        //choosed 1 because we start the algorithm from first node.(so counting starts from 1)

        // set as false)
        // boolean visited[] = new boolean[V];
        // Mark all the vertices as not visited(By default)
        for (node_info tempNode : this.graph.getV()) {
            tempNode.setTag(-1);
        }
        // Create a queue for BFS
        Queue<node_info> unvisitedQueue = new LinkedList<node_info>();
        // Mark the current first node as visited and enqueue it
        src.setTag(1);
        unvisitedQueue.add(src);
        int pathLength = 1;
        while (!unvisitedQueue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            node_info tempN = unvisitedQueue.poll();
            System.out.print(tempN.getKey() + " ");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it

            for (int keyIter : this.graph.getNiOfNode(tempN.getKey())) {
                node_info n = this.graph.getNode(keyIter);
                //if not visited tag as 1
                if (n.getTag() == -1) {
                    n.setTag(1);
                    //and add it to the queue
                    unvisitedQueue.add(n);
                    pathLength++;
                }
            }
        }
        //after we finished we restarted the graph nodes tags to "-1"
        //graph.setTagstoUnvisited();
        return pathLength;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
//