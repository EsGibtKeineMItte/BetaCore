package de.wk.betacore.customcommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Scan extends Executor {

    public static void scan(File file) {

        String suffix = fileSuffix;

        char[] chars = file.getAbsolutePath().toCharArray();
        int i = chars.length - 1;
        while (chars[i] != '/' && chars[i] != '\\') {
            i--;
        }
        i++;
        String string = file.getAbsolutePath().substring(0, i);

        String out = "";
        try {
            out = new Scanner(file + fileSuffix).useDelimiter("\\A").next();
        } catch (NoSuchElementException e) {
            System.out.println("[SCANNER] File is Empty");
            System.exit(3);
        }
        if (!(out.endsWith(" "))) {
            out += " ";
        }

        Executor.setGroundPath(string);

        String[] codes = out.replaceAll("\r", "").replaceAll("\n", " \n").split("\n");
        ArrayList<String> newCode = new ArrayList<String>();

        for (String st : codes) {
            if (st.startsWith("import ")) {
                String st2 = st.substring(7).replaceAll(" ", "");
                String out2 = "";
                try {
                    try {
                        out2 = new Scanner(new File(string + st2 + fileSuffix)).useDelimiter("\\A").next();
                    } catch (NoSuchElementException e) {
                        System.out.println("[SCANNER] File is Empty");
                        System.exit(3);
                    }
                } catch (FileNotFoundException e) {

                }
                if (!(out2.endsWith(" "))) {
                    out2 += " ";
                }
                String[] imports = out2.replaceAll("\r", "").replaceAll("\n", " \n").split("\n");
                for (String st1 : imports) {
                    newCode.add(st1);
                }
            } else {
                newCode.add(st);
            }
        }

        for (int j = newCode.size() - 1; j >= 0; j--) {
            if (newCode.get(j).startsWith("//")) {
                newCode.remove(j);
            }
        }

        codes = new String[newCode.size()];

        for (int j = 0; j < newCode.size(); j++) {

            codes[j] = newCode.get(j);
        }
        Lexer.createTokens(codes);
    }

}