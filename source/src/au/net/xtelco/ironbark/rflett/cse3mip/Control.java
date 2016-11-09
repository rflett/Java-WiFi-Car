package au.net.xtelco.ironbark.rflett.cse3mip;

import static au.net.xtelco.ironbark.rflett.cse3mip.Interact.DF_PIN;
import au.net.xtelco.ironbark.rflett.cse3mip.model.Car;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ryan Flett
 */
public class Control {
    
    public static OutputStream fout;
    public static InputStream fin;
    private static String user;
    private static String host;
    private static int port;
    private static String pass;

    public static void init() throws IOException, Exception {
        Control c = new Control();
        Interact window = new Interact();
        
        // Initiate the SSH session
        c.initSSHSession();

        // Begin reading the Input Stream
        c.startReadThread();
        
        // Initiate pins and camera feed
        c.setUp();
        
        // Initiate the display
        window.initDisplay();
    }
    
    /**
     * Sets the connection details from the car selected in the FXML window.
     * 
     * @param car1 is the selected car
     * @throws IOException 
     */
    public static void takeOver(Car car1) throws IOException, Exception {
        user = car1.getUser();
        pass = car1.getPass();
        host = car1.getIp();
        port = car1.getPort();

        init();
    }

    /**
     * Initiates the SSH connection to the RPi using the credentials from takeOver()
     */
    private void initSSHSession() {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(user, host, port);
            session.setPassword(pass);

            UserInfo ui = new MyUserInfo() {
                @Override
                public void showMessage(String message) {
                    JOptionPane.showMessageDialog(null, message);
                }

                @Override
                public boolean promptYesNo(String message) {
                    return true;
                }
            };

            session.setUserInfo(ui);

            // Timeout of 5 seconds
            session.connect(50000);

            Channel channel = session.openChannel("shell");
            fout = channel.getOutputStream();
            fin = channel.getInputStream();

            channel.connect(3 * 1000);

        } catch (JSchException | IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Begins the read thread for the input stream.
     */
    private void startReadThread() {
        new readInput().start();
    }

    /**
     * Initiates the GPIO pins and begins camera feed
     */
    private void setUp() {
        
        String start = "sudo bash";
        
        try {
            Control.fout.write(start.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
                
        
        String Start = "cd /home/pi/Arduino";
        
        try {
            Control.fout.write(Start.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String Arduino = "./program328 a.cpp.hex";
        
        try {
            Control.fout.write(Arduino.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String data = "sudo echo 5 > /sys/class/gpio/export";
        
        try {
            Control.fout.write(data.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data2 = "sudo echo \"out\" > /sys/class/gpio/gpio5/direction";
        
        try {
            Control.fout.write(data2.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data3 = "sudo echo 6 > /sys/class/gpio/export";
        
        try {
            Control.fout.write(data3.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data4 = "sudo echo \"out\" > /sys/class/gpio/gpio6/direction";
        
        try {
            Control.fout.write(data4.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data5 = "sudo echo 19 > /sys/class/gpio/export";
        
        try {
            Control.fout.write(data5.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data6 = "sudo echo \"out\" > /sys/class/gpio/gpio19/direction";
        
        try {
            Control.fout.write(data6.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data7 = "sudo echo 26 > /sys/class/gpio/export";
        
        try {
            Control.fout.write(data7.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
        
        String data8 = "sudo echo \"out\" > /sys/class/gpio/gpio26/direction";
        
        try {
            Control.fout.write(data8.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    /**
     * Details UserInfo for the SSH session.
     */
    public static abstract class MyUserInfo
            implements UserInfo, UIKeyboardInteractive {

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptYesNo(String str) {
            return false;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return false;
        }

        @Override
        public boolean promptPassword(String message) {
            return false;
        }

        @Override
        public void showMessage(String message) {
        }

        @Override
        public String[] promptKeyboardInteractive(String destination,
                String name,
                String instruction,
                String[] prompt,
                boolean[] echo) {
            return null;
        }
    }

    /**
     * Reads input from the InputStream.
     */
    private class readInput extends Thread {

        @Override
        public void run() {
            int b;
            try {
                while (true) {
                    b = fin.read();

                    if (b != -1) {
                        System.out.print((char) (b));

                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException intEx) {
                            System.err.println(intEx);
                        }
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
