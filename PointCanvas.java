//package graph;

import java.util.*;
import java.awt.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javax.swing.*;        

/**
 *  Implements a graphical canvas that displays a list of points.
 *
 *  @author  Nicholas R. Howe
 *  @version CSC 112, 18 April 2006
 */
public class PointCanvas extends JComponent {
    /** The points */
    
    Graph<Spoint, Integer> graph;
    //LinkedList<Line> lines;
    /** Constructor */
    //Graphics g;
    LinkedList<Graph<Spoint, Integer>.Edge> edgesBFT;
    LinkedList<Graph<Spoint, Integer>.Edge> edgesDFT;
    public PointCanvas() {
        
        graph = new Graph<Spoint, Integer> ();
        
    }
    public void reset(){
        LinkedList<Graph<Spoint, Integer>.Edge> edges = graph.getEdgeList();
        Iterator<Graph<Spoint, Integer>.Edge> iter = edges.iterator();

        while (iter.hasNext()) {
            Graph<Spoint, Integer>.Edge edge = iter.next();

            
            iter.remove();
        }
        LinkedList<Graph<Spoint, Integer>.Node> nodes = graph.getNodeList();
        Iterator<Graph<Spoint, Integer>.Node> iterN = nodes.iterator();

        while (iterN.hasNext()) {
            Graph<Spoint, Integer>.Node edge = iterN.next();

            
            iterN.remove();
        }
    }
    /** get the size of the nodeList in Graph class */
    public int getCanvasSize(){
        return graph.getNodeList().size();
    }
    /** get a point of index n*/
    public Point getPoint(int n){
        Graph<Spoint, Integer>.Node node = graph.getNode(n);
        Point p = node.getData().getPoint();
        //System.out.println("getPoint");
        return p;
    }
    /** return edge from two nodes */
    public Graph<Spoint, Integer>.Edge getEdge(Graph<Spoint, Integer>.Node head, Graph<Spoint, Integer>.Node tail){
        LinkedList<Graph<Spoint, Integer>.Edge> edges = graph.getEdgeList();
        for (Graph<Spoint, Integer>.Edge edge: edges){
            if ((edge.getHead().equals(head) && edge.getTail().equals(tail))
                    || (edge.getHead().equals(tail) && edge.getTail().equals(head))){
                return edge;
            }
        }
        return null;
    }
    
    /** add new node */
    public void add(Point newPoint){
        graph.addNode(new Spoint(newPoint));
        
    }
    /** return node at index n */
    public Graph<Spoint, Integer>.Node getNode(int n){
        LinkedList<Graph<Spoint, Integer>.Node> nodes = graph.getNodeList();
        return nodes.get(n);
    }
    /** set text to a node */
    public void addText(String text, Point point){
        for (Graph<Spoint, Integer>.Node node: graph.getNodeList()){
            if (node.getData().getPoint().equals(point)){
                node.getData().setData(text);
            }
        }
        
    }
    /** pass back the LinkedList to paint BFT */
    private void resetBFT(LinkedList<Graph<Spoint, Integer>.Edge> edges){
        edgesBFT = edges;
    }
    /** pass back the LinkedList to paint DFT */
    private void resetDFT(LinkedList<Graph<Spoint, Integer>.Edge> edges){
        edgesDFT = edges;
    }
    /** return graph */
    public Graph<Spoint, Integer> getGraph(){
        return graph;
    }
    /** add data to a node */
    public void addText(String text, Graph<Spoint, String>.Edge edge){
        edge.setData(text);
    }
    /** add edges from two edges */
    public void addEdge(Graph<Spoint, Integer>.Node head, Graph<Spoint, Integer>.Node tail){
        Graph<Spoint, Integer>.Edge edge = graph.addEdge(head, tail);
    }
    /** add edge from two points */
    public void addEdge(Point p1, Point p2){
        LinkedList<Point> points = new LinkedList<Point> ();
        LinkedList<Graph<Spoint, Integer>.Node> nodes = graph.getNodeList();
        for (Graph<Spoint, Integer>.Node node: nodes){
            points.add(node.getData().getPoint());
        }
        int in1 = points.indexOf(p1);
        int in2 = points.indexOf(p2);
        Graph<Spoint, Integer>.Node head = nodes.get(in1);
        Graph<Spoint, Integer>.Node tail = nodes.get(in2);
        Graph<Spoint, Integer>.Edge edge = graph.addEdge(head, tail);
        
        //System.out.println("Edge = " + graph.getEdgeList().size());
    }
    /** remove node represented by point */
    public void remove(Point newPoint){
        Graph<Spoint, Integer>.Node node;
        LinkedList<Graph<Spoint, Integer>.Node> nodes = graph.getNodeList();
        for (int i = 0; i < nodes.size(); i ++){
            node = nodes.get(i);
            if (nodes.get(i).getData().getPoint().equals(newPoint)){
                graph.removeNode(node);
            }
        }
        
    }
    /** BFT method that calls BFT in Graph and print out result */
    public void BFT(Graph<Spoint, Integer>.Node start){
        LinkedList<Graph<Spoint, Integer>.Edge> edges = graph.BFT(start);
        
        LinkedList<Graph<Spoint, Integer>.Node> nodes = new LinkedList<Graph<Spoint, Integer>.Node> ();
        for (Graph<Spoint, Integer>.Edge edge: edges){
            if (edge.getHead().getData().getData() != null
                    && edge.getTail().getData().getData() != null){
                System.out.println("BFT going from Node " + edge.getHead().getData().getData()
                 + " to Node " + edge.getTail().getData().getData());
            }else if (edge.getData() != null){
                System.out.println("BFT traverse Edge " + edge.getData());
            }  else {
                System.out.println("BFT going from Node at " + edge.getHead().getData().getPoint()
                 + " to Node at " + edge.getTail().getData().getPoint());
            }
        }
        resetBFT(edges);
        
    }
    /** DFT traversal method */
    public void DFT(Graph<Spoint, Integer>.Node start){
        LinkedList<Graph<Spoint, Integer>.Edge> edges = graph.DFT(start);
        LinkedList<Graph<Spoint, Integer>.Node> nodes = new LinkedList<Graph<Spoint, Integer>.Node> ();
        for (Graph<Spoint, Integer>.Edge edge: edges){
            if (edge.getHead().getData().getData() != null
                    && edge.getTail().getData().getData() != null){
                System.out.println("DFT going from Node " + edge.getHead().getData().getData()
                 + " to Node " + edge.getTail().getData().getData());
            } else if (edge.getData() != null){
                System.out.println("DFT traverse Edge " + edge.getData());
            } else {
                System.out.println("DFT going from Node at " + edge.getHead().getData().getPoint()
                 + " to Node at " + edge.getTail().getData().getPoint());
            }
            
        }
        resetDFT(edges);
        
    }
    
    /** remove a node */
    public void remove(Graph<Spoint, Integer>.Node node){
        graph.removeNode(node);
    }
    /** remove an edge */
    public void remove(Graph<Spoint, Integer>.Edge edge){
        
        graph.removeEdge(edge);
    }
    
    /**
     *  Paints a red circle ten pixels in diameter at each point.
     *
     *  @param g The graphics object to draw with
     */
    /** help method to paint the lines */
    private void paintLine(Graphics g, Point p1, Point p2){
        int Sx = (int) p1.getX();
        int Sy = (int) p1.getY();
        int Ex = (int) p2.getX();
        int Ey = (int) p2.getY();
        //g.setColor(Color.black);
        g.drawLine(Sx, Sy, Ex, Ey);
        //System.out.println("draw line");
    }
    /** calls shortest path method in graph */
    public double[] path(Graph<Spoint, Integer> graph, Graph<Spoint, Integer>.Node start){
        double[] path = graph.shortestPath(graph, start);
        LinkedList<Graph<Spoint, Integer>.Node> nodeList = graph.getNodeList();
        for (int m = 0; m < path.length; m ++){
            if (path[m] == 2.147483647E9){
                System.out.println("Node " + start.getData().getPoint() +
                        " and Node " + nodeList.get(m).getData().getPoint()
                         + " are not connected!");
            } else if (nodeList.get(m).getData().getData() != null && start.getData().getData() != null){
                System.out.println("The shortest path from Node " + start.getData().getData() + " to Node " + nodeList.get(m).getData().getData()
                 + " is " + path[m]);
            } else if (nodeList.get(m).getData().getData() == null && start.getData().getData() != null){
                System.out.println("The shortest path from Node " + start.getData().getData() + " to Node " + nodeList.get(m).getData().getPoint()
                 + " is " + path[m]);
            } else if (nodeList.get(m).getData().getData() == null && start.getData().getData() == null){
                System.out.println("The shortest path from Node " + start.getData().getPoint() + " to Node " + nodeList.get(m).getData().getPoint()
                 + " is " + path[m]);
            }
                    
        }
        return path;
    }
    
    /** paints the canvas */
    public void paintComponent(Graphics g) {
        // FILL IN
        LinkedList<Graph<Spoint, Integer>.Node> nodes = graph.getNodeList();
        int size = nodes.size();
        for (int i = 0; i < size; i ++){
            Point singlePoint = nodes.get(i).getData().getPoint();
            int x = (int) singlePoint.getX();
            int y = (int) singlePoint.getY();
            g.setColor(Color.yellow);
            g.drawOval(x-10, y-10, 20, 20);
            g.fillOval(x-10, y-10, 20, 20);
            String text = nodes.get(i).getData().getData();
            if (text != null){
                g.setColor(Color.darkGray);
                g.drawString(text, x, y);
            }
        }
        LinkedList<Graph<Spoint, Integer>.Edge> edges = graph.getEdgeList();
        for (Graph<Spoint, Integer>.Edge edge: edges){
            Point p1 = edge.getHead().getData().getPoint();
            Point p2 = edge.getTail().getData().getPoint();
            double x1 = p1.getX();
            double y1 = p1.getY();
            double x2 = p2.getX();
            double y2 = p2.getY();
            //System.out.println("p1, p2" + p1 + p2);
            g.setColor(Color.black);
            paintLine(g, p1, p2);
            //System.out.println("paint component");
            
            if (edge.getData() != null){
                String text = Integer.toString(edge.getData());
            
                if (text != null){
                    g.drawString(text, (int)(x1 + x2)/2, (int)(y1 + y2)/2);
                }
            }
        }
        if (edgesBFT != null){
            for (Graph<Spoint, Integer>.Edge edge: edgesBFT){
                Point p1 = edge.getHead().getData().getPoint();
                Point p2 = edge.getTail().getData().getPoint();
                double x1 = p1.getX();
                double y1 = p1.getY();
                double x2 = p2.getX();
                double y2 = p2.getY();
            //System.out.println("p1, p2" + p1 + p2);
                g.setColor(Color.red);
                paintLine(g, p1, p2);
            }
        }
        edgesBFT = null;
        if (edgesDFT != null){
            for (Graph<Spoint, Integer>.Edge edge: edgesDFT){
                Point p1 = edge.getHead().getData().getPoint();
                Point p2 = edge.getTail().getData().getPoint();
                double x1 = p1.getX();
                double y1 = p1.getY();
                double x2 = p2.getX();
                double y2 = p2.getY();
            //System.out.println("p1, p2" + p1 + p2);
                g.setColor(Color.green);
                paintLine(g, p1, p2);
            }
        }
        edgesDFT = null;
        
    }


    /**
     *  The component will look bad if it is sized smaller than this
     *
     *  @returns The minimum dimension
     */
    public Dimension getMinimumSize() {
        return new Dimension(500,300);
    }

    /**
     *  The component will look best at this size
     *
     *  @returns The preferred dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(500,300);
    }
}
