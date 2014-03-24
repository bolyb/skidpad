/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skidpadprogram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Brian
 */
public class Process {

    ArrayList list1 = new ArrayList();
    ArrayList list2 = new ArrayList();
    ArrayList list1ev = new ArrayList(); //2nd and 4th laps with outliers omitted
    ArrayList list2ev = new ArrayList();
    ArrayList list1av = new ArrayList(); //averages of 2nd and 4th laps
    ArrayList list2av = new ArrayList();
    double max = 0;
    double min1 = 0;
    double min2 = 0;
    double max1 = 0;
    double max2 = 0;
    double your1 = 0;
    double your2 = 0;

    public void imp1(String s) throws FileNotFoundException, IOException {
        String csvFileToRead = s;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {
                String[] list = line.split(splitBy);
                if (list[0].equals("Totals") || list[0].equals("total")) {
                } else {
                    double d = Double.parseDouble(list[1]);
                    list1.add(d);
                }
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void imp2(String s) throws FileNotFoundException, IOException {
        String csvFileToRead = s;
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {
                String[] list = line.split(splitBy);
                if (list[0].equals("Totals") || list[0].equals("total")) {
                } else {
                    double d = Double.parseDouble(list[1]);
                    list2.add(d);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double getMin1() {
        double min = 1000;
        for (int i = 0; i < list1.size(); i++) {
            if ((double) list1.get(i) < min) {
                min = (double) list1.get(i);
            }
        }
        this.min1 = min;
        this.max1 = min * 1.25;
        return min;
    }

    public double getMin2() {
        double min = 1000;
        for (int i = 0; i < list2.size(); i++) {
            if ((double) list2.get(i) < min) {
                min = (double) list2.get(i);
            }
        }
        this.min2 = min;
        this.max2 = min * 1.25;
        return min;
    }

    public void findev1() {
        for (int i = 0; i <= list1.size() - 1; i++) {
            if ((double) list1.get(i) > max) {
                list1.remove(i);
            } else if (i % 2 == 1) {
                list1ev.add((double) list1.get(i));
            }
        }
    }

    public void findev2() {
        for (int i = 0; i <= list2.size() - 1; i++) {
            if ((double) list2.get(i) > max) {
                list2.remove(i);
            } else if (i % 2 == 1) {
                list2ev.add((double) list2.get(i));
            }
        }
    }

    public void findav1() {
        for (int i = 0; i <= list1ev.size() - 1; i += 2) {
            double num1 = (double) list1ev.get(i);
            double num2 = (double) list1ev.get(i + 1);
            double av = (num1 + num2) / 2;
            list1av.add(av);
        }
        double min = 1000;
        for (int i = 0; i < list1av.size(); i++) {
            if ((double) list1av.get(i) < min) {
                min = (double) list1av.get(i);
            }
        }
        this.your1 = min;
    }

    public void findav2() {
        for (int i = 0; i <= list2ev.size() - 1; i += 2) {
            double num1 = (double) list2ev.get(i);
            double num2 = (double) list2ev.get(i + 1);
            double av = (num1 + num2) / 2;
            list2av.add(av);
        }
        double min = 1000;
        for (int i = 0; i < list2av.size(); i++) {
            if ((double) list2av.get(i) < min) {
                min = (double) list2av.get(i);
            }
        }
        this.your2 = min;
    }

    public double getScore1() {
        return (47.5 * Math.pow((this.max1 / this.your1), 2) - 1) / ((Math.pow((this.max1 / this.min1), 2) - 1) + 2.5);
    }

    public double getScore2() {
        return (47.5 * Math.pow((this.max2 / this.your2), 2) - 1) / ((Math.pow((this.max2 / this.min2), 2) - 1) + 2.5);
    }
    
    public void makeList1(String path) throws IOException{
        String newPath1 = path.substring(0,path.lastIndexOf(File.separator));
        File file = new File(newPath1 + "\\list1edited.csv");
        FileWriter write = new FileWriter(file, false);
        for(int i = 0; i <= list1av.size() - 1; i++){
            write.write(list1av.get(i).toString() + System.getProperty("line.separator"));
        }
        write.close();
    }
    
    public void makeList2(String path) throws IOException{
        String newPath2 = path.substring(0,path.lastIndexOf(File.separator));
        File file = new File(newPath2 + "\\list2edited.csv");
        FileWriter write = new FileWriter(file, false);
        for(int i = 0; i <= list2av.size() - 1; i++){
            write.write(list2av.get(i).toString() + System.getProperty("line.separator"));
        }
        write.close();
    }

    public ArrayList getList1() {
        return list1;
    }

    public ArrayList getList2() {
        return list2;
    }

    public ArrayList getList1ev() {
        return list1ev;
    }

    public ArrayList getList2ev() {
        return list2ev;
    }

    public ArrayList getList1av() {
        return list1av;
    }

    public ArrayList getList2av() {
        return list2av;
    }

    public double getMax() {
        return max1;
    }

    public void setMax(double max) {
        this.max = max;
    }

}
