import java.io.*;
import java.util.*;

class ProgressBar
{
    private int max;

    public void ProgressDisplay() throws Exception {
		String anim= "|/-\\";
        for (int x =0 ; x < 100 ; x++) {
            String data = "\r" + anim.charAt(x % anim.length()) + " " + x;
            System.out.write(data.getBytes());
            Thread.sleep(100);
        }
    }
}

class copy{

	public void txt(String srcPath, String dstPath){
    	try{
			FileReader fr = new FileReader(srcPath);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(dstPath);
			String line;

			while( (line = br.readLine()) != null ){
				fw.write(line);
				fw.write('\n');
				fw.flush();
			}
			br.close();
			fw.close();
			System.out.println("File Copied Successfully");
		}
		catch(IOException e){
			e.printStackTrace();
		}		
	}

	public void other(String srcPath,String dstPath){
				try{
					FileInputStream fin = new FileInputStream(srcPath);
					FileOutputStream fout = new FileOutputStream(dstPath);
					int i;

					do{
						i=fin.read();
						fout.write(i);
					}while(i != -1);

					fin.close();
					fout.close();
					System.out.println("File Copied Successfully");
				}
				catch(IOException e){
					e.printStackTrace();
				}

	}
}


public class fileio {

    public static void main(String[] args){

    	Scanner sc = new Scanner(System.in);
    	System.out.println("Enter the path of source file you want to copy:");
    	String srcPath = sc.nextLine();
    	File srcfile = new File(srcPath);
		String ext = "";
		if(srcfile.exists()){
   			String name = srcfile.getName();
        	ext = name.substring(name.lastIndexOf(".")+1);
    	}
    	else{
    		System.out.println("Source File Not Found ");
    		System.exit(0);
    	}


    	System.out.println("Enter the path of the destination file:");
    	String dstPath = sc.nextLine();
    	File dstFile = new File(dstPath);


    	String[] exts = {"txt","rtf","text"};


    	boolean extInArr = Arrays.stream(exts).anyMatch(ext::equals);

    	copy c = new copy();
    	ProgressBar pb = new ProgressBar();

        if(extInArr){
			
        	  Thread thread = new Thread(){
    				public void run()
    				{
      					c.txt(srcPath,dstPath);
    				}
  				};
  			thread.start();
			
			while(thread.isAlive())
				{	
					try{
						thread.start();
						pb.ProgressDisplay();
					}catch(Exception e){

					}
				}

			}

		else{

				
        	  Thread thread = new Thread(){
    				public void run()
    				{
      					c.other(srcPath,dstPath);
    				}
  				};

				try{
						thread.start();
						pb.ProgressDisplay();
						
					}catch(Exception e){

					}


		}

    }
}
