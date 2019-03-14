import java.util.Scanner;
import java.util.regex.*;

class Checker{

   String contact;
   String email;

   public void accept(){
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter your Phone Number");
      contact = sc.nextLine();
      System.out.println("Enter your Email ID");
      email = sc.nextLine();
   }
   public boolean validateContact(){
      Pattern pattern1 = Pattern.compile("\\+(91-)\\(\\d{3}\\)\\(\\d{3}\\)\\(\\d{4}\\)");
      Matcher m1 = pattern1.matcher(contact);
      
      if (m1.matches()) {
         return true;
      }
   return false;
   }

   public boolean validateEmail(){
      Pattern pattern2 = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
      Matcher m2 = pattern2.matcher(email);
      
      if (m2.matches()) {
         return true;
      }

      return false;

      
   }   
}



public class regexImpl {
  public static void main(String[] argv) {
      
      Checker c = new Checker();
      c.accept();
      if(c.validateContact()==true)
      {
               System.out.println("Phone Number Valid");
      }
      else{
               System.out.println("Phone Number must be in the format +91-XXX-XXX-XXXX");
      }

      if(c.validateEmail()==true)
      {
               System.out.println("Email Address Valid ");
      }
      else{
               System.out.println("Email must be in the format hello@world.com");       
      }

 }
}