package dndAI;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
//takes bytes from system out and appends them in to a string
public class TextOutputStream extends OutputStream {
	
	private StringBuilder buffer;
    private String outputPrefix;
    private UserView ui;
    private PrintStream old;
    
    public TextOutputStream(String prefix, UserView ui, PrintStream old) {
        this.outputPrefix = prefix;
        buffer = new StringBuilder(128);
        buffer.append("[").append(prefix).append("] ");
        this.old = old;
        this.ui = ui;
    }

    @Override
    //builds a string from output data
    public void write(int b) throws IOException {
        char c = (char) b;
        String value = String.valueOf(c);
        buffer.append(value);
        if (value.equals("\n")) {
            ui.appendText(buffer.toString());
            buffer.delete(0, buffer.length());
            buffer.append("[").append(outputPrefix).append("] ");
        }
        old.print(c);
    }        
	
}
