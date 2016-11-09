package javassh;

import com.jcraft.jsch.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static java.lang.Thread.sleep;
import javax.swing.JOptionPane;

public class Shell {
  public static void main(String[] arg){
    
    try{
      JSch jsch=new JSch();
 
      //jsch.setKnownHosts("/home/foo/.ssh/known_hosts");
 
      String host=null;
      
      if(arg.length>0){
        host=arg[0];
      }
 
      Session session=jsch.getSession("pi", "139.216.153.157", 22);
      session.setPassword("cse3mip");
 
      UserInfo ui = new MyUserInfo() {
        @Override
        public void showMessage(String message){
          JOptionPane.showMessageDialog(null, message);
        }
        @Override
        public boolean promptYesNo(String message){
          return true;
        }
 
      };
 
      session.setUserInfo(ui);
      session.connect(30000);   // making a connection with timeout.
 
      Channel channel=session.openChannel("shell");
      
      File shellScript = createShellScript();
      FileInputStream fin = new FileInputStream(shellScript);
      byte fileContent[] = new byte[(int) shellScript.length()];
      fin.read(fileContent);
      InputStream in = new ByteArrayInputStream(fileContent);
      
      channel.setInputStream(in);
 
      channel.setOutputStream(System.out);
 
      channel.connect(3*1000);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
 
  public static File createShellScript() {
      String filename = "shellscript.sh";
      File fstream = new File(filename);
      
      try {
          PrintStream out = new PrintStream(new FileOutputStream(fstream));
          out.println("echo \"1\" > /sys/class/gpio/gpio5/value");
          sleep(1);
          out.println("echo \"0\" > /sys/class/gpio/gpio5/value");
          out.println("nano value");
          out.println("exit");
          out.close();
      } catch (Exception e) {
          System.err.println("Error: " + e.getMessage());
      }
      
      return fstream;
  }
  
  public static abstract class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    @Override
    public String getPassword(){ return null; }
    @Override
    public boolean promptYesNo(String str){ return false; }
    @Override
    public String getPassphrase(){ return null; }
    @Override
    public boolean promptPassphrase(String message){ return false; }
    @Override
    public boolean promptPassword(String message){ return false; }
    @Override
    public void showMessage(String message){ }
    @Override
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }
}
