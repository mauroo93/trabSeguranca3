import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KeypadGUI  implements ActionListener{
	JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();
    JButton b5 = new JButton();
    JButton b6 = new JButton();
    JButton conf = new JButton("OK");
    JButton lim = new JButton("LIMPA");
    ArrayList<String> senha = new ArrayList<String>();
    
	public KeypadGUI(){
		 
		//Creating the Frame
	        JFrame frame = new JFrame("Keypad");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(400, 200);

	        //Creating the panel at bottom and adding components
	        JPanel panel = new JPanel(); // the panel is not visible in output
	        update();
	        
	        b1.addActionListener(this); 
	        b2.addActionListener(this); 
	        b3.addActionListener(this); 
	        b4.addActionListener(this); 
	        b5.addActionListener(this); 
	        b6.addActionListener(this); 
	        conf.addActionListener(this);
	        lim.addActionListener(this);
	        panel.add(b1);
	        panel.add(b2);
	        panel.add(b3);
	        panel.add(b4);
	        panel.add(b5);
	        panel.add(b6);
	        panel.add(conf);
	        panel.add(lim);
	        

	        // Text Area at the Center
	        

	        //Adding Components to the frame.
	        frame.getContentPane().add(panel);
	        frame.setVisible(true);
   }
	
	public void update() {
		ArrayList<String> b = Keypad.createButtons();
		b1.setText(b.get(0));
        b2.setText(b.get(1));
        b3.setText(b.get(2));
        b4.setText(b.get(3));
        b5.setText(b.get(4));
        b6.setText(b.get(5));
        System.out.println(senha.toString());
	}
	
	 public void actionPerformed(ActionEvent e){  
         if(e.getSource() == b1) {
        	 senha.add(b1.getText());
        	 update();
         }
         else if(e.getSource() == b2) {
        	 senha.add(b2.getText());
        	 update();
         }
         else if(e.getSource() == b3) {
        	 senha.add(b3.getText());
        	 update();
         }
         else if(e.getSource() == b4) {
        	 senha.add(b4.getText());
        	 update();
         }
         else if(e.getSource() == b5) {
        	 senha.add(b5.getText());
        	 update();
         }
         else if(e.getSource() == b6) {
        	 senha.add(b6.getText());
        	 update();
         }
         else if(e.getSource() == conf) {
        	 
        	 
         }
         else if(e.getSource() == lim) {
        	 senha.clear();
        	 update();
         }
}  
}
