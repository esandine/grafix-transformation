import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Parser{
    public static void readFile(String filename){
	File f = new File(filename);
	while(s.hasNextLine()){
            System.out.println("ELY "+s.nextLine());
        }

	try{
	    Scanner s = new Scanner(f);
	}catch(FileNotFoundException e){
	    System.out.println("NO FILE HATH BEN FOUND");
	}
    }
    
    public static void main(String[] args){
	System.out.println("We got us here a parser");
	readFile("display.sh");
    }
}
