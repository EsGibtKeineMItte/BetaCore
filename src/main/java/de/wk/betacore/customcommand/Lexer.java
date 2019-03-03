package de.wk.betacore.customcommand;

import java.util.ArrayList;

public class Lexer {

    protected static void createTokens(String[] code) {
        ArrayList<Object[]> tokens = new ArrayList<Object[]>();

        for (String st : code) {
            while (st.startsWith("  ")) {
                st = st.substring(2);
            }
            char[] chars = st.toCharArray();
            boolean inString = false;
            boolean backspace = false;
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : chars) {

                if (c == ' ' && inString == false && backspace == false) {
                    Object[] token = new Object[2];
                    token[1] = stringBuilder.toString();
                    if (stringBuilder.toString().startsWith("\"") && stringBuilder.toString().endsWith("\"")) {
                        token[1] = token[1].toString().substring(1, token[1].toString().length() - 1);
                        token[0] = "str";
                    } else if (stringBuilder.toString().matches("\\{")) {
                        token[0] = "BLs";
                    } else if (stringBuilder.toString().matches("\\}")) {
                        token[0] = "BLe";
                    } else if (stringBuilder.toString().matches("\\(")) {
                        token[0] = "STs";
                    } else if (stringBuilder.toString().matches("\\)")) {
                        token[0] = "STe";
                    } else if (stringBuilder.toString().matches("-?(0|[1-9]\\d*)(\\.([0-9]*))?")) {
                        token[0] = "num";
                    } else if (stringBuilder.toString().matches("[+\\-*/%#]||([=<>!][=]?)")) {
                        token[0] = "COM";
                    } else if (stringBuilder.toString().matches("(true)||(false)")) {
                        token[0] = "bol";
                    } else {
                        token[0] = "COD";
                    }
                    tokens.add(token);

                    //System.out.println(tokens.size() + " >> " + token[0].toString() + " : " + token[1].toString());

                    stringBuilder = new StringBuilder();
                } else if (c == '"') {
                    if (!(backspace)) {
                        if (inString) {
                            inString = false;
                        } else {
                            inString = true;
                        }
                    } else {
                        backspace = false;
                    }
                    stringBuilder.append(c);
                } else if (c == '\\') {
                    backspace = true;
                } else {
                    stringBuilder.append(c);
                }

            }

            Object[] token = new Object[2];
            token[0] = "NNN";
            token[1] = "NNN";
            tokens.add(token);

        }
        Executor.execute(tokens);
    }

}
