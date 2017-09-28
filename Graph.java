//package graph;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Graph<V, E>{
    
    private Graph<V, E>.Node head;
    private Graph<V, E>.Node tail;
    /** global variable for collection of all nodes */
    private LinkedList<Node> nodeList;
    /** global variable for collection of all edges */ 
    private LinkedList<Edge> edgeList;
    
    /** this is the Graph methods */
    public Graph(){
        nodeList = new LinkedList<Node> ();
        edgeList = new LinkedList<Edge> ();
    }
    /** add edge to the graph */
    public Edge addEdge(E data, Graph<V, E>.Node head, Graph<V, E>.Node tail){
        Edge newEdge = new Edge(data, head, tail);
        edgeList.add(newEdge);
        
        head.addEdgeRef(newEdge);
        tail.addEdgeRef(newEdge);
        return newEdge;
    }
    /** return all nodes */
    public LinkedList<Node> getNodeList(){
        return nodeList;
    }
    /** return all edges */
    public LinkedList<Edge> getEdgeList(){
        return edgeList;
    }
    /** another way to add an edge */
    public Edge addEdge(Node head, Node tail){
        Edge edge = new Edge(head, tail);
        edgeList.add(edge);
        
        head.addEdgeRef(edge);
        tail.addEdgeRef(edge);
        return edge;
    }
    /** print out the head and tail of all edges */
    public void printEdge(){
        for (Edge edge: edgeList){
            System.out.println("edge = " + edge.getHead() + " " + edge.getData()  + " " + edge.getTail());
        }
    }
    /** add a node to the graph */
    public void addNode(Node node){
        nodeList.add(node);
    }
    /** add a node with no set data */
    public Node addNode(){
        Node node = new Node();
        nodeList.add(node);
        return node;
    }
    /** add a new node with data */
    public Node addNode(V data){
        Node newNode = new Node(data);
        nodeList.add(newNode);
        return newNode;
    }
    /** helping method for check() */
    private boolean checkBool(){
        boolean check = false;
        for (Edge edge: edgeList){
            Node head = edge.getHead();
            Node tail = edge.getTail();
            if (head.getEdgeRef().contains(edge) && tail.getEdgeRef().contains(edge)){
                check = true;
            } else {
                return check = false;
                //check = true;
            }
            if (nodeList.contains(tail)&&nodeList.contains(head)){
                check = true;
            } else {
                return check = false;
                //check = true;
            }
        }
        for (Node node: nodeList){
            LinkedList<Edge> nodeEdge = node.getEdgeRef();
            for (Edge edge: nodeEdge){
                if (edge.getHead() == node || edge.getTail() == node){
                    check = true;
                } else {
                    return check = false;
                }
                if (edgeList.contains(edge)){
                    check = true;
                } else {
                    return check = false;
                }
            }
        }
        return check;
    }
    /** check if the graph is consistent */
    public void check(){
        if (checkBool() == true){
            System.out.println("Graph is checked.");
        } else {
            System.out.println("There's something wrong with the graph.");
        }
    }
    /** return a node at index i */
    public Node getNode(int i){
        return nodeList.get(i);
    }
    /** return the number of edges in graph */
    public int numEdges(){
        return edgeList.size();
    }
    /** get edge at index i */
    public Edge getEdge(int i){
        return edgeList.get(i);
    }
    /** return the number of nodes in graph */
    public int numNodes(){
        return nodeList.size();
    }
    /** remove a edge using edge */
    public void removeEdge(Edge edge){
        int index = edgeList.indexOf(edge);
        Edge indEdge = edgeList.get(index);
        
        edgeList.remove(indEdge);
        edge.getHead().removeEdgeRef(edge);
        edge.getTail().removeEdgeRef(edge);
    }
    /** remove edge using index */
    public void removeEdge(int i){
        Edge edge = getEdge(i);
        removeEdge(edge);
    }
    /** remove edge using nodes */
    public void removeEdge(Node head, Node tail){
        Edge edge = new Edge(null, head, tail);
        LinkedList<Edge> newEdges = new LinkedList<Edge> ();
        for (Edge newEdge: edgeList){
            //Edge indEdge = edgeList.get(edgeList.indexOf(edge));
            if (newEdge.equals(edge)){
                //head.removeEdgeRef(newEdge);
                //tail.removeEdgeRef(newEdge);
            //edgeList.remove(indEdge);
                //edgeList.remove(newEdge);
                newEdges.add(newEdge);
            }
        }
        for (Edge edges: newEdges){
            removeEdge(edges);
        }
        
    }
    /** remove a node */
    public void removeNode(Node node){
        
        LinkedList<Edge> newEdges = new LinkedList<Edge> ();
        
        for (Edge edge: edgeList){
            if (edge.getHead().equals(node) || edge.getTail().equals(node)){
                //edgeList.remove(edge);
                newEdges.add(edge);
                edge.getTail().removeEdgeRef(edge);
                edge.getHead().removeEdgeRef(edge);
            }
        }
        for (Edge edges: newEdges){
            edgeList.remove(edges);
        }
        int index = nodeList.indexOf(node);
        Node indNode = nodeList.get(index);
        nodeList.remove(indNode);
    }
    /** remove a node with index */
    public void removeNode(int i){
        Node node = getNode(i);
        removeNode(node);
    }
    /** print the graph */
    public void print(){
        for (int i = 0; i < nodeList.size(); i ++){
            System.out.println("Node" + Integer.toString(i+1)+": "+nodeList.get(i).getData());
        }
        for (int j = 0; j < edgeList.size(); j++){
            Edge edge = edgeList.get(j);
            System.out.println("Edge"+Integer.toString(j+1)+": " + edge.getData() + " with head of Node"+ Integer.toString(nodeList.indexOf(edge.getHead())+1)+ ", with tail of Node"+Integer.toString(nodeList.indexOf(edge.getTail())+1));
        }
    }
    /** get the connecting edge of nodes */
    public Edge getEdgeRef(Node head, Node tail){
        for (int i = 0; i < edgeList.size(); i++){
            Edge edge = edgeList.get(i);
            if (edge.getHead() == head && edge.getTail() == tail){
                return edge;
            }
           
        }
        return null;
    }
    /** get the end points of a group of edges */
    public HashSet<Node> endpoints(HashSet<Edge> edges){
        HashSet<Node> nodes = new HashSet<Node> ();
        for (Edge edge: edges){
            Node head = edge.getHead();
            Node tail = edge.getTail();
            if (!nodes.contains(head)){
                nodes.add(head);
            }
            if (!nodes.contains(tail)){
                nodes.add(tail);
            }
        }
        return nodes;
    }
    /** return the nodes not in the group */
    public HashSet<Node> otherNodes(HashSet<Node> group){
        HashSet<Node> nodes = new HashSet<Node>();
        for (Node node: nodeList){
            if (!group.contains(node)){
                nodes.add(node);
            }
        }
        return nodes;
    }
    /** depth first traversal helping method */
    private LinkedList<Edge> DFT(Node start, LinkedList<Edge> edges){
        
        LinkedList<Node> newNode = new LinkedList<Node>();
        start.visited();
        //System.out.println("1edge contains " + edges.toString());
        
        LinkedList<Node> neighbors = start.getNeighbors();
        if (!neighbors.isEmpty()){
            for (Node node: neighbors){
                if (!node.isVisited()){
                    newNode.add(node);
                    //System.out.println("added node = "+node.getData());
                }
            }
        }
        if (newNode.isEmpty()){
            return edges;
        } else {
            for (Node newStart: newNode){
            
                Edge newEdge = new Edge(start, newStart);
                
                //System.out.println("if else");
                for (Edge edge: edgeList){
                    //System.out.println("for loop");
                    if (edge.equals(newEdge)){
                        edges.add(edge);
                        //System.out.println("added edge = "+edge.getData());
                    }
                }
                edges = DFT(newStart, edges);
            }
            return edges;
        }
        
        //System.out.println("edge contains " + edges.toString());
        
    }
    /** depth first traversal */
    public LinkedList<Edge> DFT(Graph<V, E>.Node start){
        LinkedList<Graph<V, E>.Edge> edges = new LinkedList<Graph<V, E>.Edge> ();
        edges = DFT(start, edges);
        for (Graph<V, E>.Node node: nodeList){
            node.reset();
        }
        return edges;
    }
    /** breath first traversal */
    public LinkedList<Graph<V, E>.Edge> BFT(Graph<V, E>.Node start){
        LinkedList<Graph<V, E>.Edge> edges = new LinkedList<Graph<V, E>.Edge> ();
        Queue<Graph<V, E>.Node> q = new LinkedList<Graph<V, E>.Node>();
        start.seen();
        q.add(start);
        //System.out.println("BFT");
        while (!q.isEmpty()){
            Graph<V, E>.Node N = q.remove();
            N.visited();
            LinkedList<Graph<V, E>.Node> neighbors = N.getNeighbors();
            for (Graph<V, E>.Node newNode: neighbors){
                if (!newNode.isSeen()){
                    newNode.seen();
                    q.add(newNode);
                    Graph<V, E>.Edge newEdge = new Edge(N, newNode);
                    
                    for (Graph<V, E>.Edge edge: edgeList){
                        if (edge.equals(newEdge)){
                            edges.add(edge);
                        }
                    }
                }
                
            }
        }
        for (Graph<V, E>.Node node: nodeList){
            node.reset();
        }
        return edges;
    }
    
    
    /** Dijkstra's shortest path to nodes */
    
    public static <S, T extends Number> double[] shortestPath (Graph<S, T> graph, Graph<S, T>.Node start){
        LinkedList<Graph<S, T>.Node> nodeList = graph.getNodeList();
        LinkedList<Graph<S, T>.Edge> edgeList = graph.BFT(start);
        LinkedList<Graph<S, T>.Node> newNode = new LinkedList<Graph<S, T>.Node> ();
        LinkedList<Graph<S, T>.Node> newNode2 = new LinkedList<Graph<S, T>.Node> ();
        double[] sPath = new double[graph.getNodeList().size()];
        //Graph<S, T>.Node[] homeward = new Graph<S, T>.Node[nodeList.size()];
        for (int i = 0; i < nodeList.size(); i ++){
            if (nodeList.get(i).equals(start)){
                sPath[i] = 0;
            } else {
                sPath[i] = Integer.MAX_VALUE;
            }
        }
        for (Graph<S, T>.Edge edge: edgeList){
            Graph<S, T>.Node head = edge.getHead();
            Graph<S, T>.Node tail = edge.getTail();
            if (!newNode.contains(tail)){
                newNode.add(tail);
            }
            if (!newNode.contains(head)){
                newNode.add(head);
            }
            //System.out.println(edge.getData().doubleValue());
        }
        for (Graph<S, T>.Edge edge: edgeList){
            Graph<S, T>.Node head = edge.getHead();
            Graph<S, T>.Node tail = edge.getTail();
            if (!newNode2.contains(tail)){
                newNode2.add(tail);
            }
            if (!newNode2.contains(head)){
                newNode2.add(head);
            }
        }
        
        while (newNode2.size() > 0){
            int m = 0;
            double k = Integer.MAX_VALUE;
            for (int j = 0; j < sPath.length; j ++){
                if (sPath[j] < k && !nodeList.get(j).isVisited()){
                    k = sPath[j];
                    Graph<S, T>.Node node = nodeList.get(j);
                    m = newNode.indexOf(node);
                }
            }
            Graph<S, T>.Node node = newNode.get(m);
            //LinkedList<Double> doubles = new LinkedList<Double> ();
            LinkedList<Graph<S, T>.Node> nodes = node.getNeighbors();
            for (Graph<S, T>.Node nodee: nodes){
                Graph<S, T>.Edge edgee = node.edgeTo(nodee);
                double data;
                if (edgee.getData() == null){
                    data = 0;
                } else {
                    data = edgee.getData().doubleValue();
                    //System.out.println("data = " + data);
                }
                double newD = sPath[nodeList.indexOf(node)] + data;
                if ( newD < sPath[nodeList.indexOf(nodee)]){
                    sPath[nodeList.indexOf(nodee)] = newD;
                }
                
                
                //doubles.add(data);
            }
            node.visited();
            
            newNode2.remove(0);
            
            
        }
        
        
        return sPath;
    }
    
    /** Dijkstra's shortest path to nodes 
    public <V, E extends Number> double[] distance( Graph<V, E> graph, Node start){
        LinkedList<Edge> edges = graph.getEdgeList();
        LinkedList<Node> nodes = new LinkedList<Node> ();
        E num = graph.getEdge(1).getData();
        for (Edge edge: edges){
            if (!nodes.contains(edge.getHead())){
                nodes.add(edge.getHead());
            }
            if (!nodes.contains(edge.getTail())){
                nodes.add(edge.getTail());
            }
        }
        double[] dList = new double[nodes.size()]; 
        for (Graph<V, E>.Node node: nodes){
            
        }
    }
    */
    /*
    private enum Contents{
        Visited(false, "visited"),
        Seen(false, "seen");
        private boolean traversible;
        private String content;
        
        private Contents(boolean traversible, String content){
            this.traversible = traversible;
            this.content = content;
        }
        private boolean isVisited(){
            if (this.content.equals("visited")){
                return true;
            } else {
                return false;
            }
        }
        private boolean isSeen(){
            if (this.content.equals("seen")){
                return true;
            } else {
                return false;
            }
        }
    }
    */
    /** this is the edge class */
    public class Edge{
        private E data;
        private Node head;
        private Node tail;
        /** constructor */
        protected Edge(E data, Node head, Node tail){
            this.data = data;
            this.head = head;
            this.tail = tail;
        }
        /** special case constructor */
        protected Edge(Node head, Node tail){
            this.head = head;
            this.tail = tail;
        }
        /** return the data */
        public E getData(){
            return data;
        }
        /** return head node */
        public Node getHead(){
            return head;
        }
        /** return tail node */
        public Node getTail(){
            return tail;
        }
        /** check if two edges are equal */
        public boolean equals(Edge edge){
            if ((this.getHead() == edge.getHead() 
                    && this.getTail() == edge.getTail())
                    || (this.getTail() == edge.getHead() 
                    && this.getHead() == edge.getTail())){
                return true;
            } else {
                return false;
            }
        }
        /** return the hash code of edge */
        public int hashCode(){
            Node head = this.getHead();
            Node tail = this.getTail();
            int hashHead = head.hashCode();
            int hashTail = tail.hashCode();
            int hash = hashHead + hashTail;
            return hash;
        }
        /** get the opposite node */
        public Graph<V, E>.Node oppositeTo(Graph<V, E>.Node node){
            if (this.getHead() == node){
                return this.getTail();
            } else if (this.getTail() == node){
                return this.getHead();
            } else {
                return null;
            }
           
        }
        /** set data */
        public void setData(E data){
            this.data = data;
        }
    }
    /** this is the node class */
    public class Node{
        private V data;
        private boolean seen;
        private boolean visited;
        private E Edata;
        private LinkedList<Graph<V, E>.Edge> nodeEdge = new LinkedList<Graph<V, E>.Edge>();
        /** constructor */
        public Node(V data){
            this.data = data;
            this.seen = false;
            this.visited = false;
        }
        /** special case constructor */
        public Node(){
            this.data = null;
            this.seen = false;
            this.visited = false;
        }
        /** build edge */
        public Graph<V, E>.Edge edgeTo(Graph<V, E>.Node neighbor){
            LinkedList<Graph<V, E>.Edge> edgeTo = getEdgeRef();
            for (Edge edge: edgeTo){
                if (edge.getTail().equals(neighbor) && edge.getHead().equals(this)){
                    return edge;
                } else if (edge.getHead().equals(neighbor) && edge.getTail().equals(this)){
                    return edge;
                }
            }
            return null;
        }
        /** add edge to its connecting edge list */
        public void addEdgeRef(Graph<V, E>.Edge edge){
            nodeEdge.add(edge);
        }
        /** return data */
        public V getData(){
            return data;
        }
        /** return if two nodes are connected */
        public boolean isNeighbor(Graph<V, E>.Node node){
            for (int i = 0; i < nodeEdge.size(); i ++){
                if (nodeEdge.get(i).getHead() == node 
                        || nodeEdge.get(i).getTail() == node){
                    return true;
                } 
                
            }
            return false;
        }
        /** return connecting nodes */
        public LinkedList<Graph<V, E>.Node> getNeighbors(){
            LinkedList<Graph<V, E>.Node> neighbor = new LinkedList<Graph<V, E>.Node>();
            for (int i = 0; i < edgeList.size(); i ++){
                if (edgeList.get(i).getHead() == this){ 
                        
                    neighbor.add(edgeList.get(i).getTail());
                } else if (edgeList.get(i).getTail() == this){
                    neighbor.add(edgeList.get(i).getHead());
                }
                
            }
            return neighbor;
        }
        /** delete some edge */
        protected void removeEdgeRef(Graph<V, E>.Edge edge){
            nodeEdge.remove(edge);
        }
        /** return the connecting edge */
        private LinkedList<Graph<V, E>.Edge> getEdgeRef(){
            return nodeEdge;
        }
        /** set data */
        public void setData(V data){
            this.data = data;
        }
        /** set seen */
        private void seen(){
            this.seen = true;
        }
        /** return value for seen */
        private boolean isSeen(){
            if (seen){
                return true;
            } else {
                return false;
            }
        }
        /** set visited */
        private void visited(){
            this.visited = true;
        }
        /** return value for visited */
        private boolean isVisited(){
            if (visited){
                return true;
            } else {
                return false;
            }
        }
        /** reset the data to false */
        private void reset(){
            this.visited = false;
            this.seen = false;
        }
        
    }
    
}