/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.logfilereader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author binar
 */
public class FileReader {

    private int numberOfDetailsLine;

    public static void main(String[] args) {
        readFile(new File("ServerLog.log"));
    }

    private static void readFile(File logFile) {
        try {
            Scanner scanner = new Scanner(logFile);
            List<String> errorLine = new ArrayList<>();
            List<String> detailLine = new ArrayList<>();
            Set<Integer> sessionID = new TreeSet<>();
            FileWriter report = new FileWriter("Report.txt");

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                int sessionNumber = Integer.parseInt(line.split(" ")[2].replace("[", "").replace("]", ""));
                // sessionID.add(sessionNumber);

                if (line.split("]")[1].trim().contains("ERROR:")) {
                    errorLine.add(line);
                    sessionID.add(sessionNumber);
                } else {
                    detailLine.add(line);
                }
            }

            for (Integer i :
                    sessionID) {

                List<String> errorSessionDetails = new ArrayList<>();
                List<String> errorDetails = new ArrayList<>();
                getReportDetails(detailLine, i, errorSessionDetails);

                getReportDetails(errorLine, i, errorDetails);

                int numberOfDetailsLine = 2;
                if(errorSessionDetails.size()<3){
                    numberOfDetailsLine = errorSessionDetails.size()-1;
                }

                for (int j = numberOfDetailsLine; j >= 0 ; j--) {
                    System.out.println(errorSessionDetails.get(j));
                    report.write(errorSessionDetails.get(j)+"\n");
                }
                for (String e:
                errorDetails) {
                    if(errorSessionDetails.size()<3){
                        e = e + " // There are only "+errorSessionDetails.size()+" messages before this error";
                    }
                    System.out.println(e+" -----");
                    report.write(e+" -----"+"\n");
                }
            }

            report.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getReportDetails(List<String> errorLine, Integer i, List<String> errorDetails) {
        for (int j = errorLine.size() - 1; j >= 0 ; j--) {
            String err = errorLine.get(j);
            if (i == Integer.parseInt(err.split(" ")[2].replace("[", "").replace("]", ""))) {
                errorDetails.add(err);
            }
        }
    }
}
