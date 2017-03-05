import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Grafix{
    //instance variables of Grafix
    private int width;
    private int height;
    private Pixel[][] data;
    private LinkedList<PointList> edges;
    public Grafix(int width, int height){
	setWidth(width);
	setHeight(height);
	data = new Pixel[width][height];
	for(int i = 0; i< width; i++){
	    for(int j = 0; j< height; j++){
		data[i][j]=new Pixel(100,100,100);
	    }
	}
	edges = new LinkedList<PointList>();
    }
    public Grafix(){
	this(500,500);
    }
    //mutators
    public void setWidth(int w){
	width = w;
    }
    public void setHeight(int h){
	height = h;
    }
    public void plot(int x, int y, Pixel p){
	data[x][y]=p;
    }
    public void plot(Coor c, Pixel p){
	plot((int)c.getX(), (int)c.getY(), p);
    }
    public void addEdge(PointList p){
	edges.add(p);
    }
    public void addEdge(Coor[] vals){
	PointList p = new PointList();
	for(int i = 0; i < vals.length; i++){
	    p.addCoor(vals[i]);
	}
	addEdge(p);
    }
    //accessors
    public int getWidth(){
        return width;
    }
    public int getHeight(){
	return height;
    }
    public Pixel getPixel(int x, int y){
	return data[x][y];
    }
    public int getSize(){
	int total = 0;
	int i = edges.size();
	PointList edge;
	while(i>0){
	    edge = edges.poll();
	    edges.add(edge);
	    total+=edge.len();
	    i--;
	}
	return total;
    }
    //printEdgeList prints out the entire edgelist
    public void printEdgeList(){
	String[] rows = new String[4];
        for(int i = 0; i<4; i++){
            rows[i]="";
        }
	LinkedList<PointList> newEdgeList = new LinkedList<PointList>();
	PointList next = edges.poll();
	while(next!=null){
	    newEdgeList.add(next);
	    rows = next.toRows(rows);
	    next = edges.poll();
	}
	edges=newEdgeList;
	for(int i = 0; i < 4; i++){
            System.out.println(rows[i]);
        }
    }
    //getData converts all of the pixel data to a string
    public String printData(){
	String retStr = "P3 ";
	retStr+=getWidth();
	retStr+=" ";
	retStr+=getHeight();
	retStr+=" 255\n";
	for(int i = 0; i< getWidth(); i++){
	    for(int j = 0; j< getHeight(); j++){
		//System.out.println(data[i][j].toString());
		retStr+=data[i][j].toString();
	    }
	}
	return retStr;
    }
    //fun was the draw function for the first assignment
    public void fun(){
	for(int i = 0; i < getWidth(); i++){
	    for(int j = 0; j< getHeight();j++){
		data[i][j].setR((i+j)%256);
		data[i][j].setG((i-j)%256);
		data[i][j].setB((i*j)%256);
		//It looks pretty cool
	    }
	}
    }
    //bresLine1 draws the line in quadrant I using bresenham's line algorithm
    public void bresLine1(int xi, int yi, int xf, int yf, Pixel color){
	int x = xi;
	int y = yi;
	int a = 2*(yf-yi);
	int b = 2*(xi-xf);
	int d=a+xi-xf;
	while(x<=xf){
	    plot(x,y,color);
	    if(d>0){
		y++;
		d+=b;
	    }
	    x++;
	    d+=a;
	}
    }
    //bresLine2 draws the line in quadrant I using bresenham's line algorithm
    //it uses x=my+b as starting equation, reflecting the line about y=x
    public void bresLine2(int xi, int yi, int xf, int yf, Pixel color){
        int x = xi;
        int y = yi;
        int a = 2*(xf-xi);
        int b = 2*(yi-yf);
        int d=a+yi-yf;
        while(y<=yf){
            plot(x,y,color);
            if(d>0){
                x++;
                d+=b;
            }
            y++;
            d+=a;
        }
    }

    //bresLine8 draws the line in quadrant I using bresenham's line algorithm
    //it uses y=-(mx+b) as starting equation, reflecting the line
    public void bresLine8(int xi, int yi, int xf, int yf, Pixel color){
        int x = xi;
        int y = yi;
        int a = 2*(yi-yf);
        int b = 2*(xi-xf);
        int d=a+xi-xf;
        while(x<=xf){
            plot(x,y,color);
            if(d>0){
                y--;
                d+=b;
            }
            x++;
            d+=a;
        }
    }

    //bresLine7 draws the line in quadrant I using bresenham's line algorithm
    //it uses x=-(my+b) as starting equation, reflecting the line
    //this is kinda unintuitive but reflecting the equation is funner
    public void bresLine7(int xi, int yi, int xf, int yf, Pixel color){
        int x = xi;
        int y = yi;
	int a = 2*(xf-xi);
        int b = 2*(yf-yi);
        int d=a+yf-yi;
        while(y>=yf){
            plot(x,y,color);
            if(d>0){
                x++;
                d+=b;
            }
            y--;
            d+=a;
        }
    }
    //bresLine is a wrapper for all of the other functions
    public void bresLine(int xi, int yi, int xf, int yf, Pixel color){
	int temp;
	//swaps to take care of octants 3-6
	if(xi>xf){
	    temp=xi;
	    xi=xf;
	    xf=temp;
	    temp=yi;
	    yi=yf;
	    yf=temp;
	}
	//checks if quad I or quad IV
	if(yf>yi){
	    //checks oct II or oct I
	    if(yf-yi>xf-xi){
		bresLine2(xi,yi,xf,yf,color);
	    }else{
		bresLine1(xi,yi,xf,yf,color);
	    }
	}else{
	    //checks if oct VII or oct VIII
	    if(yi-yf>xf-xi){
		bresLine7(xi,yi,xf,yf,color);
	    }else{
		bresLine8(xi,yi,xf,yf,color);
	    }
	}

    }
    public void bresLine(Coor start, Coor end, Pixel p){
	bresLine((int)start.getX(),(int)start.getY(),(int)end.getX(),(int)end.getY(),p);
    }
    //writeEdge writes an edge by connecting the dots
    public void writeEdge(PointList p, Pixel color){
	Coor start = p.getCoor();
	plot(start,color);
	Coor end = p.getCoor();
	for(int i = 0; i < p.len()-1; i++){
	    bresLine(start,end,color);
	    start = end;
	    end = p.getCoor();
	}
    }
    //writeCoors uses th einstructions to draw an image
    //it is one time use only
    public void writeCoors(Pixel color){
	PointList edge = edges.poll();
	while(edge!=null){
	    writeEdge(edge, color);
	    edge = edges.poll();
	}
    }
    //scale handles scalar multiplication of edge matrices
    public void scale(double d){
	LinkedList<PointList> newEdges = new LinkedList<PointList>();
	PointList edge = edges.poll();
	while(edge!=null){
	    edge.scale(d);
	    newEdges.add(edge);
	    edge = edges.poll();
	}
	edges = newEdges;
    }
    //makeMatrix returns a new empty 4x4 matrix
    public double[][] makeMatrix(){
	double[][] ret = new double[4][4];
	for(int i = 0; i < ret.length; i++){
	    for(int j = 0; j < ret.length; j++){
		ret[i][j] = 0.0;
	    }
	}
	return ret;
    }
    //makeIdentityMatrix makes an identity matrix
    public double[][] makeIdentityMatrix(){
	double[][] ret = makeMatrix();
	for(int i = 0; i < ret.length; i++){
	    for(int j = 0; j < ret.length; j++){
		if(i==j)
		    ret[i][j]=1.0;
	    }
	}
	return ret;
    }
    //displays a matrix
    public void displayMatrix(double[][] mat){
	for(int i = 0; i < mat.length; i++){
	    for(int j = 0; j < mat.length; j++){
		System.out.print(mat[i][j]+" ");
	    }
	    System.out.println("");
	}
    }
    //multipies edgelist by transformation matrix
    //theres no way this long function works
    //it didn't on the first try but I tihnk it works now
    public void multMatrices(double[][] mat){
	double[] mults;
	PointList points;
	Coor point;
	Coor newpoint;
	for(int i = 0; i<edges.size(); i++){
		points=edges.poll();
		edges.add(points);
		for(int j = 0; j < points.len(); j++){
		    point = points.getCoor();
		    newpoint = new Coor();
		    newpoint.setX(mat[0][0]*point.getX()+
			       mat[1][0]*point.getY()+
			       mat[2][0]*point.getZ()+
			       mat[3][0]*point.getL());
		    newpoint.setY(mat[0][1]*point.getX()+
			       mat[1][1]*point.getY()+
			       mat[2][1]*point.getZ()+
			       mat[3][1]*point.getL());
		    newpoint.setZ(mat[0][2]*point.getX()+
			       mat[1][2]*point.getY()+
			       mat[2][2]*point.getZ()+
			       mat[3][2]*point.getL());
		    /*point.setL(mat[0][3]*point.getX()+
			       mat[1][3]*point.getY()+
			       mat[2][3]*point.getZ()+
			       mat[3][3]*point.getL());*/
		    point.setX(newpoint.getX());
		    point.setY(newpoint.getY());
		    point.setZ(newpoint.getZ());
		}
	}
    }
    //Write function copies the pixels to image file
    public void write(String name){
	try{
	    File f = new File(name);
	    f.delete();
	    f.createNewFile();
	    FileWriter w = new FileWriter(f, true);
	    w.write("P3 "+getWidth()+" "+getHeight()+" 255\n");
	    for(int i = 0; i< getHeight(); i++){
		for(int j = 0; j<getWidth(); j++){
		    //the [j][bleh] stuff serves to rotate the coordinates
		    w.write(data[j][getHeight()-1-i].toString());
		}
	    }
	    w.close();
	}catch(IOException e){
	    System.out.println("IO Error PANIC");
	}
    }
}
