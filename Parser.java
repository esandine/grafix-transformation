import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Parser{
    public static void readFile(String filename){
	File f = new File(filename);
	try{
	    Scanner s = new Scanner(f);
	    Grafix g = new Grafix();
	    String command;
	    Coor[] line;
	    Scanner args;
	    Pixel p = new Pixel(200,0,0);
	    while(s.hasNextLine()){
		command = s.nextLine();
		if(command.equals("line")){
		    args = new Scanner(s.nextLine());
		    line = new Coor[2];
		    line[0] = new Coor(args.nextInt(), args.nextInt(), args.nextInt());
		    line[1] = new Coor(args.nextInt(), args.nextInt(), args.nextInt());
		    g.addEdge(line);
		}else if(command.equals("save")){
		    g.writeCoors(p);
		    g.write(s.nextLine());
		}
	    }	
	}catch(FileNotFoundException e){
	    System.out.println("NO FILE HATH BEN FOUND");
	}
    }
    
    public static void main(String[] args){
	readFile("script");
    }
}
