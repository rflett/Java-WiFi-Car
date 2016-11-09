package au.net.xtelco.ironbark.rflett.cse3mip.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

/**
 **
 ** Class name: IOManager - class providing methods for reading and writing files in DVDApp
 *
 ** @author Mary Martin
 ** @version 1.0
 *
 */
public class IOManager {

    /**
     * Reads a CSV database from a file and stores it in an ArrayList.
     * @param file to read from
     * @return ArrayList containing DVD objects
     */
    public static ArrayList<Car> readTextDatabase(File file) {
        //-------------------------------------
        ArrayList<Car> c = new ArrayList<>();
        FileReader fr;
        BufferedReader br;
        String line;
        Car newCar;

        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("No Car Database.");
            System.err.println(e);
            return c;
        }
        try {
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                newCar = Car.createCar(line);
                if (newCar != null) {
                    c.add(newCar);
                }
            }
        } catch (EOFException eof) {
            System.out.println("Finished reading file.\n");
        } catch (IOException e) {
            System.err.println("Error during read\n" + e.toString());
        }
        try {
            fr.close();
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
        return c;
    }

    /**
     * Writes the ArrayList to a file
     * @param file to write the ArrayList to
     * @param d the ArrayList containing the DVD objects to be written to a file
     */
    public static void writeTextDatabase(File file, ArrayList<Car> c) {
        //-----------------------------------

        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Car car : c) {
                    bw.write(car.toCSV() + "\n");
                }
                System.out.println("Finished writing.");
                bw.flush();
            }
        } catch (IOException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * Reads an XML file and stores it in an ArrayList
     * @param fileName of the XML database to read from
     * @return an ArrayList of DVD objects generated from the XML file
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Car> readXMLDatabase(String fileName) {
        //-------------------------------------
        ArrayList<Car> car = new ArrayList<>();
        XMLDecoder d;

        try {
            d = new XMLDecoder(
                    new BufferedInputStream(
                    new FileInputStream(fileName)));
            car = (ArrayList<Car>) d.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("No Car Database.");
            return car;
        }
        d.close();
        return car;
    }

    /**
     * Writes and ArrayList database to an XML file
     * @param fileName of the XML file to write to
     * @param c the ArrayList of DVDs to write to the XML file
     */
    public static void writeXMLDatabase(String fileName, ArrayList<Car> c) {
        //-----------------------------------

        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (Car car : c) {
                encoder.writeObject(car);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not closed properly\n" + e.toString());
            System.exit(1);
        }
    }
}