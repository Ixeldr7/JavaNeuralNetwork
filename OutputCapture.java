package dndAI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputCapture extends JPanel implements UserView{

	static JTextArea textArea;
	//static FocusListener listener;
	
	public OutputCapture() 
	{
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        
        textArea.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	getInput(e);
              }

              public void focusLost(FocusEvent e) {
                
              }});
              
        add(new JScrollPane(textArea));
    }
	//gets user input from the gui
	public String getInput(FocusEvent e)
	{
		String input;
        input = textArea.getText();
        return input;
	}
	//appends the text on to the gui
	public void appendText(final String text) 
	{
            textArea.append(text);
            textArea.setCaretPosition(textArea.getText().length());
    } 
	
}
