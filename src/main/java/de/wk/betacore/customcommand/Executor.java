package de.wk.betacore.customcommand;

import java.io.*;
import java.util.*;

public class Executor {

    protected static ArrayList<Object[]> pubTokens;
    private static HashMap<String, Object[]> variables = new HashMap<String, Object[]>();

    public static boolean[] implemented = new boolean[]{true, true, true, true};

    protected static String fileSuffix = ".tec";

    protected static void setFileSuffix(String suffix) {
        if (suffix.matches("(\\.)((a-z)||(A-Z))*")) {
            fileSuffix = suffix;
        }
    }

    protected static void execute(ArrayList<Object[]> tokens) {
        int i = 0;
        pubTokens = tokens;

        ArrayList<Integer> backTrack = new ArrayList<Integer>();
        ArrayList<Integer> functions = new ArrayList<Integer>();

        while (i < tokens.size()) {
            if (tokens.get(i)[0].toString().equals("COD")) {
                if (tokens.get(i)[1].toString().equals("print")) {
                    executePrint(i);
                } else if (tokens.get(i)[1].toString().equals("eval")) {
                    executePrint(i, true);
                } else if (tokens.get(i)[1].toString().equals("var")) {
                    createVar(i);
                    i++;
                } else if (tokens.get(i)[1].toString().equals("if")) {
                    i = executeIf(i);
                } else if (tokens.get(i)[1].toString().equals("func")) {
                    i = getClosingBracket(i + 1);
                } else if (tokens.get(i)[1].toString().equals("execute")) {
                    int j = jumpToFunc(i);
                    int k = getClosingBracket(j) - 0;
                    if (functions.size() > 0 && functions.get(functions.size() - 1).equals(k)) {
                        i = j;
                    } else {
                        backTrack.add(i + 1);
                        i = j;
                        functions.add(getClosingBracket(i - 2) - 1);
                    }
                } else if (tokens.get(i)[1].toString().equals("sleep")) {
                    try {
                        if (tokens.get(i + 1)[0].toString().equals("num")) {
                            Thread.sleep(Integer.parseInt(tokens.get(i + 1)[1].toString()));
                        } else {
                            System.out.println("[ERROR] Unexpected Token");
                            System.exit(1);
                        }
                    } catch (InterruptedException e) {

                    }
                } else if (tokens.get(i)[1].toString().equals("return")) {
                    if (functions.size() > 0) {
                        deleteTempVars();
                        String string = getEnd(i);
                        if (pubTokens.get(i + 1)[0].toString().equals("num")) {
                            addVar("return:", string, "num");
                        } else {
                            addVar("return:", string, "str");
                        }
                        i = backTrack.get(backTrack.size() - 1);
                        functions.remove(functions.size() - 1);
                        backTrack.remove(backTrack.size() - 1);
                    } else {
                        System.exit(2);
                    }
                } else if (tokens.get(i)[1].toString().equals("file")) {
                    if (implemented[0]) {
                        fileManagement(i);
                    }
                } else if (tokens.get(i)[1].toString().equals("clear")) {
                    clear(i);
                } else if (tokens.get(i)[1].toString().equals("start")) {
                    if (implemented[1]) {
                        String[] codes;
                        if (isVariable(pubTokens.get(i + 1)[1].toString())) {
                            Scan.scan(new File(groundPath + pubTokens.get(i + 1)[1].toString() + fileSuffix));
                        } else {
                            Scan.scan(new File(groundPath + getVariable(pubTokens.get(i + 1)[1].toString() + fileSuffix)));
                        }
                    }
                } else if (tokens.get(i)[1].toString().equals("else")) {
                    i = getClosingBracket(i);
                } else if (tokens.get(i)[1].toString().equals("console")) {
                    if (implemented[2]) {
                        console(i);
                    }
                } else if (tokens.get(i)[1].toString().equals("dict")) {
                    if (implemented[3]) {
                        dictionary(i);
                    }
                } else if (executeCommand(i)) {

                }
                else {
                    assignVar(i);
                }
            }

            if (functions.size() > 0) {
                int j = functions.size() - 1;
                if (i == functions.get(j)) {
                    i = backTrack.get(backTrack.size() - 1);
                    functions.remove(functions.size() - 1);
                    backTrack.remove(backTrack.size() - 1);
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }

            //System.out.println(i);
            //System.out.println(i + " > " + tokens.get(i)[0] + " : " + tokens.get(i)[1].toString());

            i++;
        }
    }

    protected static void setImplemented(String name, boolean value) {
        if (name.equals("file")) {
            implemented[0] = value;
        } else if (name.equals("start")) {
            implemented[1] = value;
        } else if (name.equals("console")) {
            implemented[2] = value;
        } else if (name.equals("dict")) {
            implemented[3] = value;
        }
    }

    private static boolean executeCommand(int k) {
        int i = -1;
        for (Command cmd : commands) {
            if (cmd.command().equals(pubTokens.get(k)[1].toString()) && !(i >= 0)) {
                i = commands.indexOf(cmd);
            }
        }
        if (i >= 0) {
            commands.get(i).execute(k);
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<Command> commands = new ArrayList<Command>();

    protected static void addCommand(Command cmd) {
        int i = 0;
        for (Command cmd2 : commands) {
            if (cmd2.command().equals(cmd.command())) {
                i++;
            }
        }
        if (i == 0) {
            commands.add(cmd);
        }
    }

    protected static String tokenType(int i) {
        return pubTokens.get(i)[0].toString();
    }

    protected static Object tokenValue(int i) {
        return pubTokens.get(i)[1];
    }

    private static HashMap<String, ArrayList> dictionarys = new HashMap<String, ArrayList>();

    private static void dictionary(int i) {
        if (pubTokens.get(i + 1)[0].toString().equals("str") && pubTokens.get(i + 2)[0].toString().equals("COD")) {
            String name = pubTokens.get(i + 1)[1].toString();
            String mode = pubTokens.get(i + 2)[1].toString();
            if (mode.equals("c") && pubTokens.get(i + 3)[0].toString().equals("str")) {
                String type = pubTokens.get(i + 3)[1].toString();
                if (!(dictionarys.containsKey(name))) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    arrayList.add(type);
                    dictionarys.put(name, arrayList);
                }
            } else if (mode.equals("d")) {
                if (dictionarys.containsKey(name)) {
                    dictionarys.remove(name);
                }
            } else if (mode.equals("p")) {
                if (dictionarys.containsKey(name)) {
                    ArrayList<Object> arrayList = dictionarys.get(name);
                    String putType = pubTokens.get(i + 3)[0].toString();
                    if (arrayList.get(0).toString().equals(putType)) {
                        arrayList.add(pubTokens.get(i + 3)[1].toString());
                        dictionarys.remove(name);
                        dictionarys.put(name, arrayList);
                    } else if (variables.get(pubTokens.get(i + 3)[1].toString())[0].toString().equals(arrayList.get(0).toString())) {
                        arrayList.add(variables.get(pubTokens.get(i + 3)[1].toString())[1].toString());
                        dictionarys.remove(name);
                        dictionarys.put(name, arrayList);
                    } else {
                        System.out.println("[ERROR] Types are not the Same");
                        System.exit(1);
                    }
                } else {
                    System.out.println("[ERROR] Dictionary does not exist");
                    System.exit(1);
                }
            } else if (mode.equals("g")) {
                if (dictionarys.containsKey(name)) {
                    if (pubTokens.get(i + 3)[0].toString().equals("num")) {
                        int index = (int) Evaluator.eval(pubTokens.get(i + 3)[1].toString()) + 1;
                        ArrayList<Object> arrayList = dictionarys.get(name);
                        if (arrayList.size() > index && index > 0) {
                            deleteTempVars("!dict", false);
                            addVar("!dict", arrayList.get(index).toString(),arrayList.get(0).toString());
                        }
                    } else if (pubTokens.get(i + 3)[0].toString().equals("COD")) {
                        if (variables.containsKey(pubTokens.get(i + 3)[1].toString()) && variables.get(pubTokens.get(i + 3)[1].toString())[0].equals("num")) {
                            int index = (int) Evaluator.eval(variables.get(pubTokens.get(i + 3)[1].toString())[1].toString()) + 1;
                            ArrayList<Object> arrayList = dictionarys.get(name);
                            if (arrayList.size() > index && index > 0) {
                                deleteTempVars("!dict", false);
                                addVar("!dict", arrayList.get(index).toString(), arrayList.get(0).toString());
                            }
                        }
                    }
                }
            } else if (mode.equals("r")) {
                if (dictionarys.containsKey(name)) {
                    if (pubTokens.get(i + 3)[0].toString().equals("num")) {
                        int index = (int) Evaluator.eval(pubTokens.get(i + 3)[1].toString()) + 1;
                        ArrayList<Object> arrayList = dictionarys.get(name);
                        if (arrayList.size() > index && index > 0) {
                            arrayList.remove(index);
                            dictionarys.remove(name);
                            dictionarys.put(name, arrayList);
                        }
                    }
                } else if (pubTokens.get(i + 3)[0].toString().equals("COD")) {
                    if (variables.containsKey(pubTokens.get(i + 3)[1].toString()) && variables.get(pubTokens.get(i + 3)[1].toString())[0].equals("num")) {
                        int index = (int) Evaluator.eval(variables.get(pubTokens.get(i + 3)[1].toString())[1].toString()) + 1;
                        ArrayList<Object> arrayList = dictionarys.get(name);
                        if (arrayList.size() > index && index > 0) {
                            arrayList.remove(index);
                            dictionarys.remove(name);
                            dictionarys.put(name, arrayList);
                        }
                    }
                }
            } else if (mode.equals("l")) {
                if (dictionarys.containsKey(name)) {
                    deleteTempVars("!dictLenght", false);
                    addVar("!dictLenght", dictionarys.get(name).size() + "", "num");
                }
            }
        } else {
            System.out.println("[ERROR] Too few Tokens");
            System.exit(1);
        }
    }

    private static void clear(int i) {
        if (pubTokens.get(i + 1)[0].toString().equals("COD")) {
            String mode = pubTokens.get(i + 1)[1].toString();
            if (mode.equals("*")) {
                variables.clear();
            } else if (mode.equals(":")) {
                deleteTempVars();
            } else if (mode.equals("!")) {
                deleteTempVars(true);
            } else {
                if (variables.containsKey(mode) && variables.get(mode)[0].toString().equals("str")) {
                    deleteTempVars(variables.get(mode)[1].toString(), false);
                }
            }
        } else if (pubTokens.get(i + 1)[0].toString().equals("str")) {
            String mode = pubTokens.get(i + 1)[1].toString();
            deleteTempVars(mode, false);
        } else {
            deleteTempVars();
        }
    }

    private static void console(int i) {
        if (pubTokens.get(i + 1)[0].toString().equals("COD")) {
            String mode = pubTokens.get(i + 1)[1].toString();
            Scanner scanner = new Scanner(System.in);
            Object object = null;
            String type = "";
            if (mode.equals("s")) {
                object = scanner.next();
                type = "str";
            } else if (mode.equals("d")) {
                try {
                    object = scanner.nextDouble();
                    type = "num";
                } catch (InputMismatchException e) {
                    System.out.println("[WARNING] Wrong usage");
                }
            } else if (mode.equals("c")) {
                deleteTempVars("!console", false);
            }
            if (object != null) {
                deleteTempVars("!console", false);
                addVar("!console", object.toString(), type);
            }
        }
    }

    private static String groundPath = "";

    public static void setGroundPath(String name) { groundPath = name; }

    private static void fileManagement(int i) {
        if (pubTokens.get(i + 1)[0].toString().equals("str")) {
            String filename = pubTokens.get(i + 1)[1].toString();
            if (pubTokens.get(i + 2)[0].toString().equals("COD")) {
                String mode = pubTokens.get(i + 2)[1].toString();
                if (mode.equals("c")) {
                    try {
                        new File(groundPath + filename).createNewFile();
                    } catch (IOException e) {
                        System.out.println("[ERROR] An unexpected Error happened");
                        System.exit(1);
                    }
                } else if (mode.equals("r")) {
                    if (pubTokens.get(i + 3)[0].toString().equals("num")) {
                        double num = Evaluator.eval(pubTokens.get(i + 3)[1].toString());
                        if (num >= 0) {
                            String out = "";
                            try {
                                try {
                                    out = new java.util.Scanner(new File(groundPath + filename)).useDelimiter("\\A").next();
                                } catch (NoSuchElementException e) {
                                    System.out.println("[ERROR File is Empty");
                                    System.exit(1);
                                }
                            } catch (FileNotFoundException e) {
                                System.out.println("[ERROR] File not found");
                                System.exit(1);
                            }
                            deleteTempVars(filename + ":");
                            String[] readFile = out.split("\n");
                            addVar(filename + ":", readFile[(int) num], "str");
                        } else {
                            System.out.println("[ERROR] Line cannot be negative");
                            System.exit(1);
                        }
                    } else if (pubTokens.get(i + 3)[0].toString().equals("bol") && pubTokens.get(i + 3)[1].toString().equals("true")) {
                        String out = "";
                        try {
                            try {
                                out = new java.util.Scanner(new File(groundPath + filename)).useDelimiter("\\A").next();
                            } catch (NoSuchElementException e) {
                                System.out.println("[ERROR File is Empty");
                                System.exit(1);
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("[ERROR] File not found");
                            System.exit(1);
                        }
                        if (dictionarys.containsKey(filename)) {
                            dictionarys.remove(filename);
                        }
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        arrayList.add("str");
                        String[] readFile = out.split("\n");
                        for (String st : readFile) {
                            arrayList.add(st);
                        }
                        dictionarys.put(filename, arrayList);
                    } else {
                        String out = "";
                        try {
                            try {
                                out = new java.util.Scanner(new File(groundPath + filename)).useDelimiter("\\A").next();
                            } catch (NoSuchElementException e) {
                                System.out.println("[ERROR File is Empty");
                                System.exit(1);
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("[ERROR] File not found");
                            System.exit(1);
                        }
                        String[] readFile = out.split("\n");
                        int k = 0;
                        for (String st : readFile) {
                            addVar(filename + k + ":", st, "str");
                            k++;
                        }
                    }
                } else if (mode.equals("w")) {
                    OutputStream os = null;
                    try {
                        String out = "";
                        if (new File(groundPath + filename).exists()) {
                            try {
                                out = new Scanner(new File(groundPath + filename)).useDelimiter("\\A").next();
                            } catch (NoSuchElementException e) {

                            }
                        }
                        if (out.length() != 0) {
                            out = out + "\n" + getEnd(i + 2);
                        } else {
                            out = getEnd(i + 2);
                        }
                        os = new FileOutputStream(new File(groundPath + filename));
                        os.write(out.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            try {
                                os.close();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (mode.equals("d")) {
                    new File(groundPath + filename).delete();
                } else if (mode.equals("e")) {
                    new File(groundPath + filename).delete();
                    try {
                        new File(groundPath + filename).createNewFile();
                    } catch (IOException e) {
                        System.out.println("[ERROR] An unexpected Error happened");
                        System.exit(1);
                    }
                }
            }
        }
    }

    private static void deleteTempVars() {
        if (variables.size() > 0) {
            try {
                for (Object st : variables.keySet().toArray()) {
                    if (st.toString().endsWith(":")) {
                        variables.remove(st);
                    }
                }
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static void deleteTempVars(String st) {
        if (variables.size() > 0) {
            try {
                if (st.endsWith(":")) {
                    variables.remove(st);
                } else {
                    variables.remove(st + ":");
                }
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    protected static void deleteTempVars(String st, boolean bool) {
        if (bool) {
            if (variables.size() > 0) {
                try {
                    if (st.endsWith(":")) {
                        variables.remove(st);
                    } else {
                        variables.remove(st + ":");
                    }
                } catch (ConcurrentModificationException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        } else {
            if (variables.size() > 0) {
                try {
                    variables.remove(st);
                } catch (ConcurrentModificationException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }

    private static void deleteTempVars(boolean constant) {
        if (constant) {
            if (variables.size() > 0) {
                try {
                    for (Object st : variables.keySet().toArray()) {
                        if (st.toString().startsWith("!")) {
                            variables.remove(st);
                        }
                    }
                } catch (ConcurrentModificationException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        } else {
            deleteTempVars();
        }
    }

    private static int jumpToFunc(int k) {

        String name = "";

        if (pubTokens.get(k + 1)[1].toString().endsWith("()")) {
            name = pubTokens.get(k + 1)[1].toString();
        } else {
            name = pubTokens.get(k + 1)[1].toString() + pubTokens.get(getClosingStatement(k + 1))[1].toString();
        }

        StringBuilder stringBuilder = new StringBuilder();
        String type = "";
        ArrayList<Object[]> TempVars = new ArrayList<Object[]>();

        if (!(pubTokens.get(k + 1)[1].toString().endsWith("()")) && !(pubTokens.get(k + 2)[1].toString().equals(")"))) {
            for (int j = k + 2; j < getClosingStatement(k + 2) + 1; j++) {
                if (pubTokens.get(j)[0].toString().equals("COD") && pubTokens.get(j)[1].toString().equals(",")) {
                    Object[] objects = new Object[2];
                    objects[0] = type;
                    objects[1] = stringBuilder.toString();
                    TempVars.add(objects);
                    stringBuilder = new StringBuilder();
                } else if (pubTokens.get(j)[0].toString().equals("STe") && pubTokens.get(j)[1].toString().equals(")")) {
                    Object[] objects = new Object[2];
                    objects[0] = type;
                    objects[1] = stringBuilder.toString();
                    TempVars.add(objects);
                    stringBuilder = new StringBuilder();
                } else {
                    String varName = pubTokens.get(j)[1].toString();
                    if (pubTokens.get(j)[0].toString().equals("COD")) {
                        if (isVariable(varName)) {
                            System.out.println("[ERROR] Variable not defined");
                            System.exit(1);
                        }
                        stringBuilder.append(getVariable(varName));
                        type = getType(varName);
                    } else {
                        stringBuilder.append(pubTokens.get(j)[1].toString());
                        type = pubTokens.get(j)[0].toString();
                    }
                }
            }
        }

        int i = 0;
        while (i < pubTokens.size()) {
            if (pubTokens.get(i)[0].toString().equals("COD") && pubTokens.get(i)[1].toString().equals("func")) {
                if (pubTokens.get(i + 1)[0].toString().equals("COD") && pubTokens.get(i + 1)[1].toString().equals(name)) {
                    deleteTempVars("!args", false);
                    addVar("!args", TempVars.size() + "", "num");
                    if (TempVars.size() > 0) {
                        int j = 0;
                        for (Object[] objects : TempVars) {
                            String varName = pubTokens.get(i + 1)[1].toString().replaceAll("\\(\\)", "") + j + ":";
                            addVar(varName, objects[1].toString(), objects[0].toString());
                            j++;
                        }
                    }
                    return i + 3;
                }
            }
            i++;
        }
        return k;
    }

    private static String getEnd(int i) {
        i++;
        StringBuilder stringBuilder = new StringBuilder();
        while (!(pubTokens.get(i)[0].toString().contains("NNN")) && !(pubTokens.get(i)[1].toString().contains("NNN"))) {
            if (pubTokens.get(i)[0].toString().equals("COD")) {
                String name = pubTokens.get(i)[1].toString();
                if (isVariable(name)) {
                    System.out.println("[ERROR] Variable not defined");
                    System.exit(1);
                }
                stringBuilder.append(getVariable(name));
            } else {
                stringBuilder.append(pubTokens.get(i)[1]);
            }
            i++;
        }
        return stringBuilder.toString();
    }

    private static void executePrint(int i) {
        System.out.println(getEnd(i));
    }

    private static void executePrint(int i, Boolean bool) {
        if (bool) {
            System.out.println(Evaluator.eval(getEnd(i)));
        } else {
            System.out.println(getEnd(i));
        }
    }

    private static void createVar(int i) {
        i++;
        if (pubTokens.get(i)[0].toString().equals("COD")) {
            i++;
            if (pubTokens.get(i)[0].toString().equals("COM") && pubTokens.get(i)[1].toString().equals("=")) {
                String name = pubTokens.get(i - 1)[1].toString();
                String value = getEnd(i);
                String type = pubTokens.get(i + 1)[0].toString();
                addVar(name, value, type);
            }
        }
    }

    private static Boolean isVariable(String name) {
        if (!(variables.containsKey(name))) {
            return true;
        }
        return false;
    }

    private static Object getVariable(String name) {
        return variables.get(name)[1];
    }

    private static String getType(String name) {
        try {
            return variables.get(name)[0].toString();
        } catch (NullPointerException e) {

        }
        return "ooo";
    }

    protected static void addVar(String name, String value, String type) {
        if (variables.containsKey(name)) {
            System.out.println("[ERROR] The variable name (" + name + ") cannot be created twice");
            System.exit(1);
        }
        Object[] variable = new Object[2];
        variable[0] = type;
        if (type.equals("str")) {
            variable[1] = value;
        } else if (type.equals("bol")) {
            variable[1] = value;
        } else {
            variable[1] = Evaluator.eval(value);
        }
        variables.put(name, variable);
    }

    private static void assignVar(int i) {
        if (isVariable(pubTokens.get(i)[1].toString())) {
            return;
        }
        if (!(pubTokens.get(i + 1)[0].toString().equals("COM"))) {
            return;
        }
        if (pubTokens.get(i)[1].toString().startsWith("!")) {
            return;
        }

        if (pubTokens.get(i + 2)[0].toString().equals("COD"))   {
            if (isVariable(pubTokens.get(i + 2)[1].toString()) == false) {
                if (!(getType(pubTokens.get(i + 0)[1].toString()).equals(getType(pubTokens.get(i + 2)[1].toString())))) {
                    System.out.println("[ERROR] Wrong Type");
                    System.exit(1);
                }
                if (getType(pubTokens.get(i + 0)[1].toString()).equals("num")) {
                    try {
                        setVar(pubTokens.get(i)[1].toString(), Evaluator.eval(getEnd(i + 1)));
                    } catch (RuntimeException e) {

                    }
                } else {
                    setVar(pubTokens.get(i)[1].toString(), getEnd(i + 1));
                }
                return;
            }
        }

        if (!(pubTokens.get(i + 2)[0].toString().equals(variables.get(pubTokens.get(i)[1])[0]))) {
            System.out.println("[ERROR] Wrong Type");
            System.exit(1);
        }
        if (pubTokens.get(i + 1)[1].toString().equals("=")) {
            if (pubTokens.get(i + 2)[0].toString().equals("str")) {
                setVar(pubTokens.get(i)[1].toString(), getEnd(i + 1));
            } else {
                setVar(pubTokens.get(i)[1].toString(), Evaluator.eval(getEnd(i + 1)));
            }
        }
    }

    private static void setVar(String name, Object value) {
        Object[] var = variables.get(name);
        variables.remove(name);
        var[1] = value;
        variables.put(name, var);
    }

    private static int executeIf(int i) {
        if (!(pubTokens.get(i + 1)[0].equals("STs"))) {
            return getClosingBracket(i);
        }
        int j = getClosingStatement(i);
        int k = i + 2;
        //TODO ArrayList<Object[]> statementTokens = getTokens(k, j);
        if (!(pubTokens.get(k + 1)[0].equals("COM")) && !(pubTokens.get(k + 1)[0].equals("COD")) && !(pubTokens.get(k + 1)[0].equals("STe"))) {
            return getClosingBracket(i);
        }
        if (pubTokens.get(k + 1)[0].equals("STe")) {
            if ((pubTokens.get(k)[0].equals("bol") || getType(pubTokens.get(k)[1].toString()).equals("bol"))) {
                if (pubTokens.get(k)[1].toString().equals("true")) {
                    return i;
                } else if (pubTokens.get(k)[0].toString().equals("COD") && isVariable(pubTokens.get(k)[1].toString())) {
                    if (getVariable(pubTokens.get(k)[1].toString()).equals("true")) {
                        return i;
                    }
                }
            }
            return getClosingBracket(k, true);
        }
        if (pubTokens.get(k + 1)[1].toString().equals("==")) {
            if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                Object[] objects = getValues(k);
                if (objects[0].equals(objects[1])) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            } else if ((pubTokens.get(k)[0].equals("bol") || getType(pubTokens.get(k)[1].toString()).equals("bol")) && (pubTokens.get(k + 2)[0].equals("bol") || getType(pubTokens.get(k + 2)[1].toString()).equals("bol"))) {
                Object[] objects = getValues(k);
                if (objects[0].equals(objects[1])) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            } else {
                Object[] objects = getValues(k);
                if ((float) (Evaluator.eval(objects[0].toString())) == ((float) (Evaluator.eval(objects[1].toString())))) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            }
        }
        if (pubTokens.get(k + 1)[1].toString().equals("!=")) {
            if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                Object[] objects = getValues(k);
                if (!(objects[0].equals(objects[1]))) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            } else if ((pubTokens.get(k)[0].equals("bol") || getType(pubTokens.get(k)[1].toString()).equals("bol")) && (pubTokens.get(k + 2)[0].equals("bol") || getType(pubTokens.get(k + 2)[1].toString()).equals("bol"))) {
                Object[] objects = getValues(k);
                if (!(objects[0].equals(objects[1]))) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            } else {
                Object[] objects = getValues(k);
                if (Double.parseDouble(objects[0].toString()) != (Evaluator.eval(objects[1].toString()))) {
                    return i;
                } else {
                    return getClosingBracket(k, true);
                }
            }
        }
        try {
            if (pubTokens.get(k + 1)[1].toString().equals("exist")) {
                if ((pubTokens.get(k)[0].equals("COD")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    if (pubTokens.get(k)[1].equals("file")) {
                        String filename = "";
                        if (isVariable(pubTokens.get(k + 2)[1].toString())) {
                            filename = pubTokens.get(k + 2)[1].toString();
                        } else {
                            filename = getVariable(pubTokens.get(k + 2)[1].toString()).toString();
                        }
                        if (new File(groundPath + filename).exists()) {
                            return i;
                        } else {
                            return getClosingBracket(k, true);
                        }
                    } else if (pubTokens.get(k)[1].equals("var")) {
                        if (variables.containsKey(pubTokens.get(k + 2)[1].toString())) {
                            return i;
                        } else {
                            return getClosingBracket(i, true);
                        }
                    }
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!exist")) {
                if ((pubTokens.get(k)[0].equals("COD")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    if (pubTokens.get(k)[1].equals("file")) {
                        String filename = "";
                        if (isVariable(pubTokens.get(k + 2)[1].toString())) {
                            filename = pubTokens.get(k + 2)[1].toString();
                        } else {
                            filename = getVariable(pubTokens.get(k + 2)[1].toString()).toString();
                        }
                        if (new File(groundPath + filename).exists()) {
                            return getClosingBracket(k, true);
                        } else {
                            return i;
                        }
                    } else if (pubTokens.get(k)[1].equals("var")) {
                        if (variables.containsKey(pubTokens.get(k + 2)[1].toString())) {
                            return getClosingBracket(i, true);
                        } else {
                            return i;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {

        }
        try {
            if (pubTokens.get(k + 1)[1].toString().equals("equal")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().equals(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!equal")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().equals(objects[1].toString())) {
                        return getClosingBracket(k, true);
                    } else {
                        return i;
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("ignoreCase")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().equalsIgnoreCase(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!ignoreCase")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().equalsIgnoreCase(objects[1].toString())) {
                        return getClosingBracket(k, true);
                    } else {
                        return i;
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("contains")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().contains(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!contains")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().contains(objects[1].toString())) {
                        return getClosingBracket(k, true);
                    } else {
                        return i;
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("startWith")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().startsWith(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!startWith")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().startsWith(objects[1].toString())) {
                        return getClosingBracket(k, true);
                    } else {
                        return i;
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("endsWith")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().endsWith(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("!endsWith")) {
                if ((pubTokens.get(k)[0].equals("str") || getType(pubTokens.get(k)[1].toString()).equals("str")) && (pubTokens.get(k + 2)[0].equals("str") || getType(pubTokens.get(k + 2)[1].toString()).equals("str"))) {
                    Object[] objects = getValues(k);
                    if (objects[0].toString().endsWith(objects[1].toString())) {
                        return getClosingBracket(k, true);
                    } else {
                        return i;
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
        } catch (NullPointerException e) {

        }
        try {
            if (pubTokens.get(k + 1)[1].toString().equals("isPrime")) {
                if ((pubTokens.get(k)[0].equals("num") || getType(pubTokens.get(k)[1].toString()).equals("num"))) {
                    Object[] objects = getValues(k);
                    if (Evaluator.eval(objects[0].toString()) < 0) {
                        return getClosingBracket(k, true);
                    }
                    if (Evaluator.eval(objects[0].toString()) == 2) {
                        return i;
                    }
                    if (Evaluator.eval(objects[0].toString()) % 2 == 0) {
                        return getClosingBracket(k, true);
                    }
                    int checkNum = (int) Evaluator.eval(objects[0].toString());
                    checkNum = (int) Math.sqrt((double) checkNum);
                    for (int m = 2; m < checkNum - 1; m++) {
                        if (m % 2 == 0) {
                            m++;
                        }
                        if (Evaluator.eval(objects[0].toString()) % m == 0) {
                            return getClosingBracket(k, true);
                        }
                    }
                    return i;
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals(">")) {
                if ((pubTokens.get(k)[0].equals("num") || getType(pubTokens.get(k)[1].toString()).equals("num")) && (pubTokens.get(k + 2)[0].equals("num") || getType(pubTokens.get(k + 2)[1].toString()).equals("num"))) {
                    Object[] objects = getValues(k);
                    if (Evaluator.eval(objects[0].toString()) > Evaluator.eval(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals(">=")) {
                if ((pubTokens.get(k)[0].equals("num") || getType(pubTokens.get(k)[1].toString()).equals("num")) && (pubTokens.get(k + 2)[0].equals("num") || getType(pubTokens.get(k + 2)[1].toString()).equals("num"))) {
                    Object[] objects = getValues(k);
                    if (Evaluator.eval(objects[0].toString()) >= Evaluator.eval(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("<")) {
                if ((pubTokens.get(k)[0].equals("num") || getType(pubTokens.get(k)[1].toString()).equals("num")) && (pubTokens.get(k + 2)[0].equals("num") || getType(pubTokens.get(k + 2)[1].toString()).equals("num"))) {
                    Object[] objects = getValues(k);
                    if (Evaluator.eval(objects[0].toString()) < Evaluator.eval(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
            if (pubTokens.get(k + 1)[1].toString().equals("<=")) {
                if ((pubTokens.get(k)[0].equals("num") || getType(pubTokens.get(k)[1].toString()).equals("num")) && (pubTokens.get(k + 2)[0].equals("num") || getType(pubTokens.get(k + 2)[1].toString()).equals("num"))) {
                    Object[] objects = getValues(k);
                    if (Evaluator.eval(objects[0].toString()) <= Evaluator.eval(objects[1].toString())) {
                        return i;
                    } else {
                        return getClosingBracket(k, true);
                    }
                } else {
                    System.out.println("[ERROR] Types incompatable");
                    System.exit(1);
                }
            }
        } catch (NullPointerException e) {

        }
        return i;
    }

    private static Object[] getValues(int k) {
        Object[] objects = new Object[2];
        if (pubTokens.get(k)[0].equals("COD") && variables.containsKey(pubTokens.get(k)[1].toString())) {
            objects[0] = getVariable(pubTokens.get(k)[1].toString());
        } else {
            objects[0] = pubTokens.get(k)[1];
        }
        if (pubTokens.get(k + 2)[0].equals("COD") && variables.containsKey(pubTokens.get(k + 2)[1].toString())) {
            objects[1] = getVariable(pubTokens.get(k + 2)[1].toString());
        } else {
            objects[1] = pubTokens.get(k + 2)[1];
        }
        return objects;
    }

    private static ArrayList<Object[]> getTokens(int start, int stop) {
        ArrayList<Object[]> tokens = new ArrayList<Object[]>();
        for (int i = start; i < stop; i++) {
            tokens.add(pubTokens.get(i));
        }
        return tokens;
    }

    private static int getClosingStatement(int i) {
        i++;
        while (!(pubTokens.get(i)[0].toString().contains("STe")) && !(pubTokens.get(i)[1].toString().contains(")"))) {
            i++;
        }
        return i;
    }

    private static int getClosingBracket(int i) {
        int Brackets = 0;
        if (!(!(pubTokens.get(i)[0].toString().contains("BLs")) && !(pubTokens.get(i)[1].toString().contains("{")))) {
            i++;
            while ((!(pubTokens.get(i)[0].toString().contains("BLs")) && !(pubTokens.get(i)[1].toString().contains("{")))) {
                i++;
                System.out.println(i);
                System.out.println(pubTokens.get(i)[1].toString());
            }
            Brackets = 1;
        }
        i++;
        while (Brackets != 0 && pubTokens.size() > i) {
            if (pubTokens.get(i)[0].toString().contains("BLs") && pubTokens.get(i)[1].toString().contains("{")) {
                Brackets++;
            }
            if (pubTokens.get(i)[0].toString().contains("BLe") && pubTokens.get(i)[1].toString().contains("}")) {
                Brackets--;
            }
            i++;
        }
        if (i >= pubTokens.size()) {
            System.out.println("[ERROR] Statement did not close");
            System.exit(1);
        }
        return i;
    }

    private static int getClosingBracket(int i, boolean b) {
        if (!(b)) {
            return getClosingBracket(i);
        }
        i++;
        while ((!(pubTokens.get(i)[0].toString().contains("BLs")) && !(pubTokens.get(i)[1].toString().contains("{")))) {
            i++;
        }
        int Brackets = 1;
        i++;
        while (Brackets != 0 && pubTokens.size() > i) {
            if (pubTokens.get(i)[0].toString().contains("BLs") && pubTokens.get(i)[1].toString().contains("{")) {
                Brackets++;
            }
            if (pubTokens.get(i)[0].toString().contains("BLe") && pubTokens.get(i)[1].toString().contains("}")) {
                Brackets--;
            }
            i++;
        }
        if (i >= pubTokens.size()) {
            System.out.println("[ERROR] Statement did not close");
            System.exit(1);
        }
        return i;
    }

}
