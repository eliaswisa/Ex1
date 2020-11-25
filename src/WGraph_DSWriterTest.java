package ex1.src;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSWriterTest {

    @Test
    void hasEdge() {
        weighted_graph g = new WGraph_DS();
        int a = 0;
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0, 1, 5);
        g.connect(0, 2, 5);
        if (g.hasEdge(0, 1)) {
            a++;
        }
        if (g.hasEdge(1, 0)) {
            a++;
        }
        if (g.hasEdge(0, 3)) {
            a++;
        }


        assertEquals(2, a);

    }

    @Test
    void getEdge() {
        weighted_graph g = new WGraph_DS();

        g.addNode(0);
        g.addNode(1);
        g.connect(0, 1, 25.3);
        double a = g.getEdge(0, 1);
        assertEquals(a, 25.3);
    }

    @Test
    void addNode() {
        weighted_graph g = new WGraph_DS();

        g.addNode(0);
        g.addNode(1);
        g.addNode(1);
        g.removeNode(1);
        g.addNode(1);
        g.removeNode(0);
        boolean flag1 = false;
        boolean flag2 = false;
        try {
            int temp2 = g.getNode(0).getKey();
        } catch (Exception e) {
            flag1 = true;
        }
        if (g.getNode(1).getKey()==1){flag2=true;}
        assertTrue(flag1&&flag2);
        {
        }
    }

    @Test
    void connect() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0,1,1);
        g.connect(0,2,1);

        g.connect(3,5,56);
        System.out.println(g.hasEdge(3,5)+" ,no edge between 3 and 5");
        boolean flag=false;
        boolean flag2=false;
        boolean flag3=g.hasEdge(3,5);
        if (g.hasEdge(0,1))
        {flag=true;}
        if (g.hasEdge(1,0)){flag2=true;}

        assertTrue(flag&&flag2&&!flag3);

    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        Collection<node_info> collection = g.getV();
        Iterator<node_info> iterator = collection.iterator();
        while (iterator.hasNext()) {
            node_info ans = iterator.next();
            if (g.getNode(ans.getKey()) != null) {
            }
            System.out.println(ans.getKey());
            boolean flag3=true;

            assertTrue (collection.contains(g.getNode(0))&&collection.contains(g.getNode(1))&&collection.contains(g.getNode(2)));

        }
    }


    @Test
    void removeNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);

        g.removeNode(5);
        g.removeNode(0);
        assertTrue(g.getNode(0)==null);
    }

    @Test
    void removeEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        g.removeEdge(0,1);
        assertTrue(!g.hasEdge(0,1));
    }

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(0);

        g.removeNode(56);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);

        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(2, e_size);

    }


}