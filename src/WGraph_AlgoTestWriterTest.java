package ex1.src;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTestWriterTest {

    @Test
    void init() {
        weighted_graph g0=new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.connect(1,2,1);
        g0.connect(1,3,2);
        weighted_graph_algorithms wg0 = new WGraph_Algo();
        wg0.init(g0);
    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
        weighted_graph g0=new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.connect(1,2,1);
        g0.connect(1,3,2);
        g0.connect(2,5,1000);
        g0.connect(1,4,25);
        g0.connect(2,5,0.3);
        weighted_graph_algorithms wg0 = new WGraph_Algo();
        wg0.init(g0);
        wg0.shortestPathDist(1,5);
    }

    @Test
    void shortestPath() {
        weighted_graph g0=new WGraph_DS();
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.connect(1,2,1);
        g0.connect(1,3,2);
        g0.connect(2,5,1000);
        g0.connect(1,4,25);
        g0.connect(2,5,0.3);
        weighted_graph_algorithms wg0 = new WGraph_Algo();
        wg0.init(g0);
        wg0.shortestPath(1,5);
        List<node_info> le = new ArrayList<node_info>();
        List<node_info> lv = new ArrayList<node_info>();
        lv.add(g0.getNode(1));
        lv.add(g0.getNode(2));
        lv.add(g0.getNode(5));
        le=wg0.shortestPath(1,5);
    assertEquals(le,lv);
    }
}