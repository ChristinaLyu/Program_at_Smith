//package graph;


import java.awt.Point;

/*
class to store String and point
*/
public class Spoint{
    private Point p;
    private String s;
    /** constructor */
    public Spoint(Point p, String s){
        this.p = p;
        this.s = s;
    }
    /** special case */
    public Spoint(Point p){
        this.p = p;
        this.s = null;
    }
    /** set a point */
    public void setPoint(Point p){
        this.p = p;
    }
    /** set the data */
    public void setData(String s){
        this.s = s;
    }
    /** return data */
    public String getData(){
        return this.s;
    }
    /** return point */
    public Point getPoint(){
        return this.p;
    }
}
