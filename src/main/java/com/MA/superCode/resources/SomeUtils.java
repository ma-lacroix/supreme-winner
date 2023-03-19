package com.MA.superCode.resources;

import java.util.ArrayList;
import java.util.List;

public class SomeUtils {
    // Section 1 exercises to get warmed up

    public String userName;

    public SomeUtils() {
        this.userName = "Default";
    }

    public SomeUtils(String userName) {
        this.userName = userName;
    }

    public void printInitials() {
        List<String> data = drawInitials();
        for (String line: data) {
            System.out.println(line);
        }
    }

    public List<String> drawInitials() {
        List<String> stuffToPrint = new ArrayList<>();
        stuffToPrint.add("");
        stuffToPrint.add("");
        stuffToPrint.add("");
        stuffToPrint.add("");
        stuffToPrint.add("");
        for (char letter: userName.toCharArray()) {
            if (letter == 'M') {
                stuffToPrint.set(0, stuffToPrint.get(0) + "M    M");
                stuffToPrint.set(1, stuffToPrint.get(1) + "MM  MM");
                stuffToPrint.set(2, stuffToPrint.get(2) + "M MM M");
                stuffToPrint.set(3, stuffToPrint.get(3) + "M    M");
                stuffToPrint.set(4, stuffToPrint.get(4) + "M    M");
            }
            else if (letter == 'A') {
                stuffToPrint.set(0, stuffToPrint.get(0) + "  AA  ");
                stuffToPrint.set(1, stuffToPrint.get(1) + " A  A ");
                stuffToPrint.set(2, stuffToPrint.get(2) + "A AA A");
                stuffToPrint.set(3, stuffToPrint.get(3) + "A    A");
                stuffToPrint.set(4, stuffToPrint.get(4) + "A    A");
            } else {
                System.out.println("Sorry too lazy to build other letters");
            }
        }
        return stuffToPrint;
    }

}
