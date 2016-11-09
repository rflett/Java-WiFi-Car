package au.net.xtelco.ironbark.rflett.cse3mip;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * 
 * @author Ryan Flett
 */
public class Interact extends Application {
    /* RPi GPIO pins */
    public static final int DF_PIN = 6;
    public static final int DB_PIN = 5;
    public static final int TL_PIN = 19;
    public static final int TR_PIN = 26;
    
    /* Textures */
    private static String forwardT = "forward_inactive";
    private static String reverseT = "reverse_inactive";
    private static String leftT = "left_inactive";
    private static String rightT = "right_inactive";
    private static final String carT = "car";
    
    /**
     * Default constructor
     */
    public Interact() {}
    
    /**
     * Initiates the display window and draws objects
     * @throws IOException 
     */
    public void initDisplay() throws IOException, Exception {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Wifi Car Control");
            Display.setResizable(false);

            Display.create();
            setUpOpenGL();
        } catch (LWJGLException e) {
            System.err.println(e);
        }

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glLoadIdentity();
            
            draw(192,112,256,256,carT);
            draw(288,384,64,64,forwardT);
            draw(288,32,64,64,reverseT);
            draw(176,256,64,64,leftT);
            draw(400,256,64,64,rightT);

            pollKeyboard();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
        start(Main.stage);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/landing.fxml"));
        Main.stage = stage;

        Scene scene = new Scene(root);

        Main.stage.setScene(scene);
        Main.stage.show();
    }
    
    /**
     * Set up the OpenGL parameters
     */
    private void setUpOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 640, 0, 480, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        glClearColor(255, 255, 255, 1);

        glDisable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }
    
    /**
     * Loads a JPEG texture from the textures folder
     * 
     * @param key of the texture
     * @return the texture JPEG
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private static Texture loadTextureJPG(String key) throws FileNotFoundException, IOException {
        return TextureLoader.getTexture(".jpg", new FileInputStream(new File("src/au/net/xtelco/ironbark/rflett/cse3mip/view/textures/" + key + ".jpg")));
    }
    
    /**
     * Draws a rectangle in the OpenGL window
     * 
     * @param x coordinate of the rectangle
     * @param y coordinate of the rectangle
     * @param width of the rectangle
     * @param height of the rectangle
     * @param texture to apply to the rectangle
     * @throws IOException 
     */
    private void draw(float x, float y, float width, float height, String texture) throws IOException {
        glPushMatrix();
            {
                Texture t = loadTextureJPG(texture);
                t.bind();

                glTranslatef(x, y, 0);

                glBegin(GL_QUADS);
                {
                    glTexCoord2f(0, 0);
                    glVertex2f(0, 0);

                    glTexCoord2f(0, -1);
                    glVertex2f(0, height);

                    glTexCoord2f(1, -1);
                    glVertex2f(width, height);

                    glTexCoord2f(1, 0);
                    glVertex2f(width, 0);
                }
                glEnd();

            }
            glPopMatrix();
    }
    
    /**
     * Listens to keyboard events and calls methods when appropriate keys are
     * pressed.
     */
    private void pollKeyboard() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {

                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_NUMPAD8:
                        //driveForward();
                        pinVoltage(1, DF_PIN);
                        forwardT = "forward_active";
                        break;
                    case Keyboard.KEY_NUMPAD5:
                        //driveBackward();
                        pinVoltage(1, DB_PIN);
                        reverseT = "reverse_active";
                        break;
                    case Keyboard.KEY_NUMPAD4:
                        //turnLeft();
                        pinVoltage(1, TL_PIN);
                        leftT = "left_active";
                        break;
                    case Keyboard.KEY_NUMPAD6:
                        //turnRight();
                        pinVoltage(1, TR_PIN);
                        rightT = "right_active";
                }

            } else {

                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_NUMPAD8:
                        //stopF();
                        pinVoltage(0, DF_PIN);
                        forwardT = "forward_inactive";
                        break;
                    case Keyboard.KEY_NUMPAD5:
                        //stopB();
                        pinVoltage(0, DB_PIN);
                        reverseT = "reverse_inactive";
                        break;
                    case Keyboard.KEY_NUMPAD4:
                        //stopL();
                        pinVoltage(0, TL_PIN);
                        leftT = "left_inactive";
                        break;
                    case Keyboard.KEY_NUMPAD6:
                        //stopR();
                        pinVoltage(0, TR_PIN);
                        rightT = "right_inactive";
                }

            }
        }
    }

    /**
     * Sends out pin voltage
     * 
     * @param value of the pin, either 0 for LOW or 1 for HIGH
     * @param pin to adjust the voltage of
     */
    private void pinVoltage(int value, int pin) {
        String data = "echo \"" + value + "\" > /sys/class/gpio/gpio" + pin + "/value";
                
        try {
            Control.fout.write(data.getBytes());
            Control.fout.write(KeyEvent.VK_ENTER);
            Control.fout.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
}
