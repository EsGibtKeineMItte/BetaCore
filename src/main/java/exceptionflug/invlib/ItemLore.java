package exceptionflug.invlib;

import java.util.ArrayList;

public class ItemLore {

    private ArrayList<String> lines = new ArrayList<>();
    
    public ItemLore addLine(String line) {
        lines.add(line);
        return this;
    }
    
    public ArrayList<String> getLines()
    {
        return lines;
    }
    
    @Override
    public ItemLore clone() {
	ItemLore out = new ItemLore();
	out.lines = (ArrayList<String>) lines.clone();
        return out;
    }
    
}
