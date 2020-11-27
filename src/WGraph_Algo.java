package ex1.src;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms{
    private WGraph_DS graph;
    private List<node_info> emptyList = new ArrayList<node_info>();

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
        Iterator it = graph.getV().iterator();
        if (it.hasNext()) {
            node_info node = (node_info) it.next();
            //choose one node and then operate fbs on it(it will try to each node to tag=1.
            bfsNodesTagging(node);
        }
        //then we will check if any one of the tags is unvisited. if it unvisited so return false and the graph is not connected
        Iterator isTaggedNode = graph.getV().iterator();
        while (isTaggedNode.hasNext()) {
            if (((node_info) isTaggedNode.next()).getTag() == -1) return false;
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


        for (node_info tempNode : this.graph.getV()) {
            tempNode.setTag(-1);
        }

        Queue<node_info> unvisitedQueue = new LinkedList<node_info>();
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

    private Queue<node_info> tagNodesToMaxValue(node_info src) {
        //the queue that we will use
        Queue<node_info> notVisited = new LinkedList<>();
        double tempTag = src.getTag();
        for (node_info i : this.graph.getV()) {
            if (i.getKey() != src.getKey()) {
                i.setTag(Integer.MAX_VALUE);
                i.setInfo("Not connected");
            } else {
                i.setTag(0);
                i.setInfo("" + i.getKey());
                notVisited.add(i);
            }
        }

        return notVisited;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        //edge condition if one  of them is not exist
        if (this.graph.getNode(dest) == null) return -1;
        if (this.graph.getNode(src) == null) return -1;

        //function that sets all tags to -1
        graph.setTagstoUnvisited();

        //sets all tags to MAX INTEGER (infinity)and sets the src tag to 0.  -->sets info to not connected and sets the src node info field to lists of nodes in the path(adds new one)
        Queue<node_info> notVisited = tagNodesToMaxValue(graph.getNode(src));

        while (!notVisited.isEmpty()) {
            node_info tempNode = notVisited.poll();
            double tempNodeTagDistance = tempNode.getTag();
            for (int nodeKey : this.graph.getNiOfNode(tempNode.getKey())) {
                node_info temp = this.graph.getNode(nodeKey);
                double nodeKeyDistance = temp.getTag();
                double weight = this.graph.getEdge(temp.getKey(), tempNode.getKey());
                if (nodeKeyDistance > tempNodeTagDistance) {
                    temp.setTag(tempNodeTagDistance + weight);
                    temp.setInfo(tempNode.getInfo() + "-" + temp.getKey());
                    notVisited.add(temp);
                }
            }
        }
        double srcSumWeight = this.graph.getNode(dest).getTag();
        if (srcSumWeight == Integer.MAX_VALUE) {
            return -1;
        }
        return srcSumWeight;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
//        //edge condition check
        if (this.graph.getNode(src) == null) return emptyList;
        if (this.graph.getNode(dest) == null) return emptyList;
        double a = shortestPathDist(src, dest);
        node_info node1 = this.graph.getNode(dest);
        String path = this.graph.getNode(dest).getInfo();
        String[] arr = path.split("(?<=-)");
        List<Integer> num = new ArrayList<Integer>();
        List<node_info> li = new ArrayList<node_info>();
        for (int i = 0; i < arr.length; i++) {
            String temp = arr[i];
            temp = temp.replace("-", "");
            int ab = Integer.parseInt(temp);
            num.add(ab);
            li.add(this.graph.getNode(ab));
        }
        return li;
    }
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fOutput = new FileOutputStream(file);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fOutput);
            objectOutput.writeObject(this.graph);

        }
        catch (FileNotFoundException er){
            return false;
        }

        catch (IOException er) {
            er.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean load(String file) {

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.init((weighted_graph) ois.readObject());

        } catch (IOException | ClassNotFoundException er) {
            er.printStackTrace();
            return false;

        }
        return true;
    }
}