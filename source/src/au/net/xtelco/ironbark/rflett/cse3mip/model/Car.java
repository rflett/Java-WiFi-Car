package au.net.xtelco.ironbark.rflett.cse3mip.model;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 
 * @author Ryan Flett
 */
public class Car {
    
    private static final int DEFAULT_PORT = 22;
    
    private String ip;
    private String user;
    private String pass;
    private String name;
    private int port;
    
    /**
     * Constructor.
     * 
     * @param name of the car (descriptive purposes)
     * @param user for the SSH connection
     * @param pass for the SSH connection
     * @param ip to connect to
     * @param port to connect to
     */
    public Car(String name, String user, String pass, String ip, int port) {
        this.name = name;
        this.user = user;
        this.pass = pass;
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * Constructor using DEFAULT_PORT.
     * 
     * @param name of the car (descriptive purposes)
     * @param user for the SSH connection
     * @param pass for the SSH connection
     * @param ip to connect to
     */
    public Car(String name, String user, String pass, String ip) {
        this(name, user, pass, ip, DEFAULT_PORT);
    }
    
    /**
     * Default constructor.
     */
    public Car() {
        this("","","","", DEFAULT_PORT);
    }

    /**
     * @return the IP of the car.
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the username of the car
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password of the car
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the name of the car
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of the car to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the port number of the car
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     * Compares two objects to see if they are equal.
     * 
     * @param obj2 to compare to
     * @return if they are equal, boolean.
     */
    @Override
    public boolean equals(Object obj2) {
        Car car2;
        boolean equal;
        
        equal = false;
        
        if (obj2 != null && obj2.getClass() == this.getClass()) {
            car2 = (Car) obj2;
            if (super.equals(car2) 
                && car2.name.equals(this.name)
                && car2.user.equals(this.user)
                && car2.pass.equals(this.pass)
                && car2.ip.equals(this.ip)
                && car2.port == this.port) {
                equal = true;
            }
        }
        
        return equal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.ip);
        hash = 59 * hash + Objects.hashCode(this.user);
        hash = 59 * hash + Objects.hashCode(this.pass);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.port;
        return hash;
    }
    
    /**
     * @return String representation of the car
     */
    @Override
    public String toString() {
        return "Car{" + "name=" + name + ", user=" + user + ", pass=" + pass + ", ip=" + ip + ", port=" + port + '}';
    }
    
    /**
     * @return CSV representation of the car
     */
    public String toCSV() {
        return name + "," + user + "," + pass + "," + ip + "," + port;
    }
    
    /**
     * Creates a car from a CSV line
     * 
     * @param line of the car in CSV format
     * @return the car
     */
    public static Car createCar(String line) {
        StringTokenizer theTokens = new StringTokenizer(line, ",");
        
        Car newCar = new Car();
        
        if (theTokens.countTokens() == 5) {
            newCar.setName(theTokens.nextToken().trim());
            newCar.setUser(theTokens.nextToken().trim());
            newCar.setPass(theTokens.nextToken().trim());
            newCar.setIp(theTokens.nextToken().trim());
            newCar.setPort(Integer.parseInt(theTokens.nextToken().trim()));
            
            return newCar;
        } else {
            return null;
        }
    }
}
