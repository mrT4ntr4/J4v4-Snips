import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.*;
import java.util.*;


class menu
{

	public void listEXE(Path dir)
	{

    	try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.txt")){
    		for (Path file : stream){
    			System.out.println(file.getFileName());
    		}
    	}
    	catch (DirectoryIteratorException | IOException x){
    		System.err.println(x);
    	}
	}
	public void help(){
	   	System.out.println("####    NIO-Impl   ####\n");
    	System.out.println("Usage : ");
    	System.out.println("first arg : Directory Path");
    	System.out.println("Also append File Names in dirPath for -cpjpg ,-mvexe, -sym");
    	System.out.println("second arg : Option\n");

    	System.out.println("-listexe    list all exe");
    	System.out.println("-meta       display metadata of every image");
    	System.out.println("-cpjpg      copy a jpg to destination dir");
    	System.out.println("-mvexe      move an exe to destination dir");
    	System.out.println("-sym        convert full path into symbolic link");
    	System.out.println("-hid        search for all hidden files");
    }

    public void showMeta(Path dir)
	{

    	try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,"*.jpg")){
    		for (Path file : stream){
    			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
    			System.out.println("\n## File Name : " +file.getFileName());
    			System.out.println("Size (in KB): " + (float)attr.size()/1024);
		        System.out.println("Creation TimeStamp: " + attr.creationTime());
		        System.out.println("Last Access TimeStamp: " + attr.lastAccessTime());
		        System.out.println("Last Modified TimeStamp: " + attr.lastModifiedTime());
    		}
    	}
		catch (NotDirectoryException ex) {
			System.out.println("Specified Directory not present");	
		}
		catch (NoSuchFileException ex) {
			System.out.println("No Such File Found");	
		}   					
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void copyImage(Path srcPath){
			try{
				String fileType = Files.probeContentType(srcPath);
	    		if(fileType.equals("image/jpeg"))
	    		{
					Scanner sc = new Scanner(System.in);
					System.out.println("Enter destination path with filename as well");
					String dst = sc.nextLine();
			    	Path dstPath = Paths.get(dst);
					Files.copy(srcPath,dstPath,StandardCopyOption.REPLACE_EXISTING);

					System.out.println("File Copied Successfully");
				}
				else
				{
					System.out.println("Given image is not jpeg");
				}
			}
			catch (NotDirectoryException ex) {
				System.out.println("Specified Directory not present");	
			}
			catch (NoSuchFileException ex) {
				System.out.println("No Such File Found");	
    		}   					
			catch(IOException e){
				e.printStackTrace();
			}

	}

	public void moveEXE(Path srcPath){
			try{
				String fileType = Files.probeContentType(srcPath);

	    		if(fileType.equals("application/x-msdos-program"))
				{				
					Scanner sc = new Scanner(System.in);
					System.out.println("Enter destination path with filename as well");
					String dst = sc.nextLine();
			    	Path dstPath = Paths.get(dst);
					Files.move(srcPath,dstPath,StandardCopyOption.REPLACE_EXISTING);

					System.out.println("File Moved Successfully");
				}
				else
				{
					System.out.println("Given file is not an exe");
				}			
			}
			catch (NotDirectoryException ex) {
				System.out.println("Specified Directory not present");	
			}
			catch (NoSuchFileException ex) {
				System.out.println("No Such File Found");	
    		}   					
			catch(IOException e){
				e.printStackTrace();
			}

	}


	public void symLink(Path srcPath){
		try{
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter destination path with fileName as well");
			String dst = sc.nextLine();
			Path dstPath = Paths.get(dst);
	        Path symLink = Files.createSymbolicLink(dstPath, srcPath);
	        System.out.println("File sym symLink created: " + symLink);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public void chkHid(Path dir){

    	try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
    		int c=0;
    		for (Path file : stream){
    			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
    			boolean chkhd = file.toFile().isHidden();
    			if(chkhd)
    			{	
    				System.out.println("\n## File Name : " +file.getFileName());
    				c++;	
    			}
    		}
    		if(c == 0)
    		{
    			System.out.println("No Hidden files found in the directory");
    		}
    		else
    		{
    			System.out.println("\n" + c + " Hidden file were found in this directory");
    		}
    	}
		catch (NotDirectoryException ex) {
			System.out.println("Specified Directory not present");	
		}
		catch (NoSuchFileException ex) {
			System.out.println("No Such File Found");	
		}   					
		catch(IOException e){
			System.out.println("Something doesn't seem right");
		}
		
	}


}


public class nioImpl {

    public static void main(String[] args) {


    	menu m = new menu();
    
    	try{

	    	Scanner sc = new Scanner(System.in);
	    	String op = args[0];
	    	String dirPath = args[1];
	    	Path dir = Paths.get(dirPath);

			if(op.equals("-listexe")){
	    		m.listEXE(dir);
	    	}
	    	else if (op.equals("-meta")){
	    		m.showMeta(dir);
	    	}
	    	else if (op.equals("-cpjpg")){
	    		m.copyImage(dir);
	    	}
	    	else if (op.equals("-mvexe")){
	    		m.moveEXE(dir);
	    	}
	    	else if (op.equals("-sym")){
	    		m.symLink(dir);
	    	}
	    	else if (op.equals("-hid")){
	    		m.chkHid(dir);
	    	}	    		    	
    	}

    	catch(ArrayIndexOutOfBoundsException e){
    		m.help();
    	}



    }

}
