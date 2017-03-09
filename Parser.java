import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Parser{
    //runCMD runs a command using java magic, used for display and saving
    public static void runCMD(String cmd){
	try{
	    Process proc = Runtime.getRuntime().exec(cmd);
	}catch(IOException e){
	    System.out.println("We seem to have encountered an io exception");
	}	
    }
    //readFile reads a script
    //note it uses the file test.ppm as an intermediary for saving and displaying
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
		    line[0] = new Coor(args.nextDouble(), args.nextDouble(), args.nextDouble());
		    line[1] = new Coor(args.nextDouble(), args.nextDouble(), args.nextDouble());
		    g.addEdge(line);
		}else if(command.equals("save")){
		    g.writeCoors(p);
		    g.write("test.ppm");		    
		    runCMD("convert test.ppm "+s.nextLine());
		}else if(command.equals("display")){
		    g.writeCoors(p);
		    g.write("test.ppm");
		    runCMD("display test.ppm");
		}else if(command.equals("ident")){
		    g.setIdentityMatrix();
		}else if(command.equals("scale")){
		    args = new Scanner(s.nextLine());
		    g.scale(args.nextDouble(),args.nextDouble(),args.nextDouble());
		}else if(command.equals("move")){
		    args = new Scanner(s.nextLine());
		    g.translate(args.nextDouble(),args.nextDouble(),args.nextDouble());
		}else if(command.equals("rotate")){
		    args = new Scanner(s.nextLine());
		    g.rotate(args.next().charAt(0),args.nextDouble());
		}else if(command.equals("apply")){
		    g.applyTransformation();
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
