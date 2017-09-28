//package graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package pointcanvas;

/**
 *
 * @author ChristinaLyu
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javafx.scene.shape.Line;
import javax.swing.*;        

/**
 *  Implements a GUI for inputting points.
 *
 *  @author  Nicholas R. Howe
 *  @version CSC 112, 18 April 2006
 */
public class GraphGUI {
    /** The graph to be displayed */
    private PointCanvas canvas;

    /** Label for the input mode instructions */
    private JLabel instr;

    /** The input mode */
    InputMode mode = InputMode.ADD_POINTS;
    String getText;
    /** Remembers point where last mousedown event occurred */
    Point pointUnderMouse;
    private static JFrame frame;
    private static JTextField textField;
    /**
     *  Schedules a job for the event-dispatching thread
     *  creating and showing this application's GUI.
     */
    public static void main(String[] args) {
        final GraphGUI GUI = new GraphGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
    }

    /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components
        createComponents(frame);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /** Puts content in the GUI window */
    public void createComponents(JFrame frame) {
        // graph display
        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        canvas = new PointCanvas();
        PointMouseListener pml = new PointMouseListener();
        canvas.addMouseListener(pml);
        canvas.addMouseMotionListener(pml);
        
        panel1.add(canvas);
        instr = new JLabel("Click to add new points; drag to move.");
        panel1.add(instr,BorderLayout.NORTH);
        pane.add(panel1);

        // controls
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(8,1));
        JButton addPointButton = new JButton("Add/Move Points");
        panel2.add(addPointButton);
        JButton edge = new JButton ("Add Edges");
        edge.addActionListener(new AddEdgeListener());
        panel2.add(edge);
        JButton path = new JButton ("Dijkstra");
        path.addActionListener(new AddPathListener());
        panel2.add(path);
        addPointButton.addActionListener(new AddPointListener());
        JButton rmvPointButton = new JButton("Remove Points");
        panel2.add(rmvPointButton);
        rmvPointButton.addActionListener(new RmvPointListener());
        JButton DFT = new JButton("DFT");
        DFT.addActionListener(new DFTPointL());
        JButton BFT = new JButton("BFT");
        BFT.addActionListener(new BFTListener());
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ResetListener());
        textField = new JTextField(20);
        
        textField.addActionListener(new TextListener());
        
        
        panel2.add(DFT);
        panel2.add(BFT);
        panel2.add(textField);
        panel2.add(reset);
        pane.add(panel2);
    }

    /** 
     * Returns a point found within the drawing radius of the given location, 
     * or null if none
     *
     *  @param x  the x coordinate of the location
     *  @param y  the y coordinate of the location
     *  @return  a point from the canvas if there is one covering this location, 
     *  or a null reference if not
     */
    
    public Point findNearbyPoint(int x, int y) {
        // FILL IN:
		// Loop over all points in the canvas and see if any of them
		// overlap with the location specified.  You may wish to use
		// the .distance() method of class Point.
        int n = canvas.getCanvasSize();
        Point given = new Point(x, y);
        for (int i = 0; i < n; i ++){
            Point newPoint = canvas.getPoint(i);
            Double distance = newPoint.distance(given);
            if (distance <= 10){
                //System.out.println("found");
                //System.out.println(newPoint);
                return newPoint;
            }
        }
        return null;
    }
    /** find nearby node that takes a point */
    public Graph<Spoint, Integer>.Node findNearbyNode(Point p){
        
        for (int k = 0; k < canvas.getCanvasSize(); k ++){
            Point newP = canvas.getPoint(k);
            Point p1 = findNearbyPoint((int) p.getX(), (int) p.getY());
            if (newP.equals(p1)){
                //System.out.println("Node = " + canvas.getNode(k).getData().getPoint());
                return canvas.getNode(k);
            }
        }
        //System.out.println("null");
        return null;
    }
    /** find nearby node that is not the same node at before */
    public Graph<Spoint, Integer>.Node findNearbyNode(Graph<Spoint, Integer>.Node node, Point p){
        
        for (int k = 0; k < canvas.getCanvasSize(); k ++){
            Point newP = canvas.getPoint(k);
            Point p1 = findNearbyPoint((int) p.getX(), (int) p.getY());
            if (newP.equals(p1)){
                //System.out.println("Node = " + canvas.getNode(k).getData().getPoint());
                Graph<Spoint, Integer>.Node got = canvas.getNode(k);
                if (!got.equals(node)){
                    return canvas.getNode(k);
                }
            }
        }
        //System.out.println("null");
        return null;
    }
    
    
    /** Constants for recording the input mode */
    enum InputMode {
        ADD_POINTS, RMV_POINTS, DFT, BFT, SET_DATA, ADD_EDGE, PATH
    }
    /** Listener for AddEdge button */
    private class AddEdgeListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_EDGE;
            instr.setText("Click to add new edges.");
        }
    }
    /** Listener for Reset button */
    private class ResetListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            canvas.reset();
            canvas.repaint();
        }
    }
    /** Listener for Dijkstra button */
    private class AddPathListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.PATH;
            instr.setText("Click to find shortest path.");
        }
    }
    /** Listener for AddPoint button */
    private class AddPointListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_POINTS;
            instr.setText("Click to add new points or change their location.");
        }
    }
    /** Listener for DFT button */
    private class DFTPointL implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mode = InputMode.DFT;
            instr.setText("Click here to do depth-fgetText = textField.getText();irst traversal");
        }
    }
    /** Listener for BFT button */
    private class BFTListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mode = InputMode.BFT;
            instr.setText("Click here to do breath-fgetText = textField.getText();irst traversal");
        }
    }
    /** Listener for textField */
    private class TextListener implements ActionListener {
        /** Event handler for AddPoint button */
        public void actionPerformed(ActionEvent e) {
            
            mode = InputMode.SET_DATA;
            getText = textField.getText();
           
        }
    }

    /** Listener for RmvPoint button */
    private class RmvPointListener implements ActionListener {
        /** Event handler for RmvPoint button */
        public void actionPerformed(ActionEvent e) {
		    // FILL IN:
			// Model on the AddPointListener above.  Should change both mode and label text.
            mode = InputMode.RMV_POINTS;
            instr.setText("Click to remove points from the window.");
        
        }
    }

    /** Mouse listener for PointCanvas element */
    private class PointMouseListener extends MouseAdapter
            
        implements MouseMotionListener {
        //Point record;
        Graph<Spoint, Integer>.Node node;
        Point record0;
        Line line;
        int[] points = new int[2];
        /** Responds to click event depending on mode */
        public void mouseClicked(MouseEvent e) {
            //System.out.println("ffffffff");
            Point clickPoint = e.getPoint() ;
            Spoint newS = new Spoint(clickPoint);
            //Graph<Spoint, String>.Node node = new Graph<Spoint, String>.Node (newS);
            //System.out.println("clicked");
            Graph<Spoint, Integer>.Node found = findNearbyNode(e.getPoint());
            //line = findNearbyLine(e.getPoint());
            
            switch (mode) {
                
                case PATH:
                    if (found != null){
                        double[] path = canvas.path(canvas.getGraph(), found);
                    }
                    break;
                case SET_DATA:
                    if (getText != null){
                        if (found != null){
                            found.getData().setData(getText);
                            System.out.println("The data for node at point " + found.getData().getPoint() + " is " + getText);
                        } 
                        
                        
                        
                    }else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                    
                case DFT:
                    if (found != null){
                        canvas.DFT(found);
                    }else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                    
                case BFT:
                    if (found != null){
                        canvas.BFT(found);
                    }else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                case ADD_POINTS:
				// FILL IN
				// If the click is not on top of an existing point, create a new one and add it to the canvas.
				// Otherwise, emit a beep, as shown below:
                    
                    if (found == null){
                        //System.out.println("found = " + found);
                        canvas.getGraph().addNode(newS);
                        //System.out.println("add Point");
                    }else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                case RMV_POINTS:
				// FILL IN
				// If the click is on top of an existing point, remove it from the canvas's list of points.
				// Otherwise, emit a beep.
                
                    if (found != null){
                        canvas.remove(found);
                        //System.out.println("removing");
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
            }
            
            canvas.repaint();
            
        }

        /** Records point under mousedown event in anticipation of possible drag */
        public void mousePressed(MouseEvent e){
            // FILL IN:  Record point under mouse, if any
            //Point p = findNearbyPoint((int) e.getPoint().getX(), (int) e.getPoint().getY());
            node = findNearbyNode(e.getPoint());
            //System.out.println("lalllallalaa");
            if (node != null){
                //System.out.println("foudnnnnnn");
                points[0] = (int) node.getData().getPoint().getX();
                points[1] = (int) node.getData().getPoint().getY();
            //System.out.println("Point = " + p);
                //record0.setLocation(p.getX(), p.getY());
                //System.out.println("record0 = " + record0);
                //record = node.getData().getPoint();
                //System.out.println(points[0]);
            }   
        }

        /** Responds to mouseup event */
        public void mouseReleased(MouseEvent e) {
            
            int i = e.getX();
            int j = e.getY();
            Point p0 = new Point(i, j);
            Graph<Spoint, Integer>.Node node2 = findNearbyNode(node, p0);
            if (node != null){
                    
                //System.out.println("New nnnnnnode = " + node2);
                
                if (node2 != null && !node2.equals(node) ) {
                    
                    Point p = new Point(points[0], points[1]);
                    //System.out.println("Point p" + p);
                    node.getData().setPoint(p);
                    Graph<Spoint, Integer>.Node node1 = findNearbyNode(p);
                    
                    if (!p.equals(p0)){
                        
                        switch (mode){
                            case ADD_EDGE:
                                canvas.addEdge(node1, node2);
                                break;
                            case RMV_POINTS:
                                Graph<Spoint, Integer>.Edge edgeee = canvas.getEdge(node1, node2);
                                canvas.remove(edgeee);
                                break;
                            case SET_DATA:
                                Graph<Spoint, Integer>.Edge edgeeee = canvas.getEdge(node1, node2);
                                if (getText != null && edgeeee != null){
                                    edgeeee.setData(Integer.valueOf(getText));
                                    
                                }
                        }
                        //System.out.println("node1, node2" + p+p0);
                        
                    }
                
                } else if (node2 == null){
                    switch (mode){
                        case ADD_POINTS:
                            node.getData().setPoint(p0);
                            break;
                    }
                //record = null;
                } 
                node = null;
            }
            
            canvas.repaint();
        }

        /** Responds to mouse drag event */
        public void mouseDragged(MouseEvent e) {
                if (mode == InputMode.ADD_POINTS && node != null){
                    
                    node.getData().setPoint(new Point(e.getX(), e.getY()));
                    
                   
                
            }
              
            
            canvas.repaint();
        }

		// Empty but necessary to comply with MouseMotionListener interface.
        public void mouseMoved(MouseEvent e) {}
    }
    /*
    private static class TextListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String data = textField.getText();
	    
            
	}

    }
*/
}
