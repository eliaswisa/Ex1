package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> graphNodes = new HashMap<Integer, node_info>();
    private int MC, edgesCounter, verticesCount;
    private HashMap<Integer, HashMap<Integer, Double>> edges = new HashMap<Integer, HashMap<Integer, Double>>();
    private static int nodesCounterDefault = 0;
    private Collection<node_info> nada = new ArrayList<node_info>();

    /***default consructor
     *
     *
     *
     */
    public WGraph_DS() {


    }

    /**
     * copy constructor
     *
     * @param g
     */
    public WGraph_DS(weighted_graph g) {
        for (node_info i : g.getV()){
            this.graphNodes.put(i.getKey(), new NodeData((NodeData) i));
        }

        for (node_info i : g.getV()){

            for(node_info j : g.getV(i.getKey())){

                if (g.hasEdge(i.getKey(), j.getKey())){
                    this.connect(i.getKey(), j.getKey(),g.getEdge(i.getKey(), j.getKey()));
                }
            }
        }
        this.edgesCounter = g.edgeSize();
        this.MC = g.getMC();
        this.verticesCount = g.nodeSize();
    }

    /***
     * checking if there is an edge between two nodes
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean flag = false;
        if (this.graphNodes.get(node1) == null && this.graphNodes.get(node2) == null) {
            return false;
        }
        if (this.edges != null) {
            if (this.edges.get(node1) != null && this.edges.get(node2) != null) {
                if (this.edges.get(node1).get(node2) != null && this.edges.get(node2).get(node1) != null)//checks if &only if
                {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public Set<Integer> getNiOfNode(int key) {
        if (graphNodes.get(key) == null) return new HashSet<Integer>();
        if (this.edges.get(key) == null) return new HashSet<Integer>();
        return this.edges.get(key).keySet();
    }

    /**
     * gets node from the Wgraph map by inputing key
     *
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        if (graphNodes != null) {
            if (graphNodes.containsKey(key)) return graphNodes.get(key);

        }

        return null;
    }

    /**
     * getting the edge weight between two nodes
     *
     * @param node1
     * @param node2
     * @return double edge weight
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) {
            return -1;
        }
        return this.edges.get(node1).get(node2);
    }

    /**
     * adding node to the Wgraph hashMap by her key
     *
     * @param key
     */
    @Override

    public void addNode(int key) {
        if (this.graphNodes != null) {
            if (this.graphNodes.get(key) == null) {
                graphNodes.put(key, new NodeData(key));
                this.MC++;
                this.verticesCount++;

            }
        } else {
            graphNodes.put(key, new NodeData(key));
            this.MC++;
            this.verticesCount++;
        }
    }

    /**
     *  Function that sets all the nodes in the graph to -1
     */
    public void setTagstoUnvisited() {
        for (node_info nod : this.getV()) {
            nod.setTag(-1);
            nod.setInfo("");
        }
    }

    /**
     * connect between two nodes an edge who got w as weight
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override

    public void connect(int node1, int node2, double w) {
        if (graphNodes.get(node1) == null || graphNodes.get(node2) == null) {
            return;
        }
        boolean direction1 = false;
        boolean direction2 = false;
        boolean directioncheck1 = true;
        boolean directioncheck2 = true;
        if (w < 0) {
            System.out.println("no distance between nodes");
            return;
        }
        if (this.edges.get(node1) != null) {
            if (this.edges.get(node1).containsKey(node2)) {
                directioncheck1 = false;
            }
            this.edges.get(node1).put(node2, w);
            direction1 = true && directioncheck1;

        } else {
            this.edges.put(node1, new HashMap<Integer, Double>());
            this.edges.get(node1).put(node2, w);
            direction1 = true;
        }

        if (this.edges.get(node2) != null) {
            if (this.edges.get(node2).containsKey(node1)) {
                directioncheck2 = false;
            }
            this.edges.get(node2).put(node1, w);
            direction2 = true && directioncheck2;


        } else {
            this.edges.put(node2, new HashMap<Integer, Double>());
            this.edges.get(node2).put(node1, w);
            direction2 = true;
        }
        if (direction1 && direction2) {
            edgesCounter++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        if (this.graphNodes != null) {
            return this.graphNodes.values();
        } else return nada;
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> niCollection = new ArrayList<>();

        if (this.graphNodes.get(node_id) == null) {
            return niCollection;
        }
        //use the edges for the list of the neighbours
        if (!this.edges.isEmpty()) {
            Collection<Integer> nodeNeighbours = this.edges.get(node_id).keySet();

            for (int iter : nodeNeighbours) {
                niCollection.add(this.graphNodes.get(iter));
            }
        }
        return niCollection;
    }

    @Override
    public node_info removeNode(int key) {
        node_info dynamicNode = this.graphNodes.get(key);
        if (this.graphNodes.get(key) == null) {
            return dynamicNode;
        }
        Collection<node_info> nodeNi = this.getV(key);
        for (node_info iterNode : nodeNi) {
            this.removeEdge(key, iterNode.getKey());
            edgesCounter--;
            //this.graphNodes.remove(key);
        }
        this.graphNodes.remove(key);
        verticesCount--;
        MC++;
        return dynamicNode;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (this.hasEdge(node1, node2)) {
            this.edges.get(this.graphNodes.get(node1).getKey()).remove(node2);
            this.edges.get(this.graphNodes.get(node2).getKey()).remove(node1);
        }
    }

    @Override
    public int nodeSize() {
        return this.graphNodes.values().size();
    }

    @Override
    public int edgeSize() {
        return this.edgesCounter;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    /////////////////////////inner class of node info
    protected class NodeData implements node_info,Serializable {
        ///////fill  the fields
        private int key;
        private String info;
        private double tag;


        public double getWeight(int kNode) {
            return 0;
        }

//        protected boolean isVisited() {
//            return visited;
//        }
//
//        protected void setVisited(boolean b) {
//            this.visited = b;
//        }

        /**
         * constructor by key
         *
         * @param key
         */
        public NodeData(int key) {
            this.key = key;
            this.tag = -1.0;
            this.info = "";

        }

        public NodeData() {
            this.key = nodesCounterDefault;
            nodesCounterDefault++;
            this.tag = -1.0;
            this.info = "";
        }

        /**
         * copy constructor
         *
         * @param nodeData
         */
        // TODO: 24/11/2020 write node_info copy constructor
        public NodeData(NodeData nodeData) {
            this.key = nodeData.getKey();
            this.info = nodeData.getInfo();
            this.tag = nodeData.getTag();


        }

//        public boolean hasNi(int key)// check if there's node between this node and a given node(by key)
//        {
//            return graphNodes.containsKey(key);
//        }

        @Override
        public int getKey() {
            return this.key;


        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }


    }
}