import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver{
    public static void main(String[] args){
	Coor c1 = new Coor(1,2,3);
	Coor c2 = new Coor(5,4,6);
	Coor c3 = new Coor(7,8,9);
	Coor c4 = new Coor(10,11,12);
	Coor c5 = new Coor(14,13,15);
	Coor c6 = new Coor(16,17,18);
	Grafix g = new Grafix();
	Coor[] coors = {c1,c2,c3};
	Coor[] coors2 = {c4,c5,c6};
	g.addEdge(coors);
	g.addEdge(coors2);
	Pixel p = new Pixel(200,0,0);
	System.out.println("Initial Coordinates:");
	g.printEdgeList();
	double[][] matrix = g.makeMatrix();
	for(int i = 0; i < 4; i++){	
	    for(int j = 0; j < 4; j++){	
		matrix[i][j]=1;
	    }
	}
	matrix[1][0] = 2;
	matrix[0][1] = 3;
	System.out.println("Matrix to multiply coordinates: ");
	g.displayMatrix(matrix);
	g.multMatrices(matrix);
	System.out.println("Resultant Matrix (checked by hand for accuracy: ");
	g.printEdgeList();
	matrix = g.makeIdentityMatrix();
	System.out.println("Identity matrix: ");
	g.displayMatrix(matrix);
	System.out.println("Resultant after multiplying by identity, its the same!");
	g.multMatrices(matrix);
	g.printEdgeList();
	System.out.println("Scale by 5, resultant matrix: ");
	g.scale(5);
	g.printEdgeList();
	g.writeCoors(p);
	g.write("edgy.ppm");
	System.out.println("Drawn");

    }
}
