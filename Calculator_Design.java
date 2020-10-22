import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//class for event handling
class Events implements ActionListener
{
	TxtPanel t; 
	No_format nf;
	public Events(){}
	public Events(TxtPanel np){	
		t=np;
	}	
	public Events(No_format nf){	
		this.nf=nf;
	}	

	static int operator[]=new int[10];
	static double op[]=new double[10];
	String val;
	static int i=0,c=0;
 	public void actionPerformed(ActionEvent ae)
	{
		String display;
		String src=ae.getActionCommand();
		
		//event handling on operators
		if(src.equals("+"))
		{
			val=t.textfield.getText();
			op[i]=Double.parseDouble(val);
			i++;
			operator[c]=1;
			c++;
			t.textfield.setText("");	
		}
		else if(src.equals("-"))
		{
			val=t.textfield.getText();
			op[i]=Double.parseDouble(val);
			i++;
			operator[c]=2;
			c++;
			t.textfield.setText("");		
		}
		else if(src.equals("*"))
		{
			val=t.textfield.getText();
			op[i]=Double.parseDouble(val);
			i++;
			operator[c]=3;
			c++;
			t.textfield.setText("");		
		}
		else if(src.equals("/"))
		{
			val=t.textfield.getText();
			op[i]=Double.parseDouble(val);
			i++;
			operator[c]=4;
			c++;
			t.textfield.setText("");		
		}
		else if(src.equals("="))
		{
			val=t.textfield.getText();
			op[i]=Double.parseDouble(val);
			i++;
			double display1=op[0];
		
			//for addition ,substraction ,multiplication ,division
			int k=0;
			display1=op[0];
			for(int j=1;j<i;j++)
			{
				if(operator[k]==1)              //addition
				{	
					display1=display1+op[j];
					k++;
				}
				else if(operator[k]==2)		//subtraction
				{			
					display1=display1-op[j];
					k++;
				}
				else if(operator[k]==3)              //multiplication
				{	
					display1=display1*op[j];
					k++;
				}
				else if(operator[k]==4)		//division
				{			
					display1=display1/op[j];
					k++;
				}

			}
			
			t.textfield.setText(""+display1);
			i=0;
			c=0;		
		}

		//event handling on numbers
		for(int j=0;j<10;j++)
		{
			if(src.equals(""+j))
			{
				display=t.textfield.getText();
				display=display+(""+j);
				t.textfield.setText(display);
			}
		}
		if(src.equals("."))
		{
			display=t.textfield.getText();
			display=display+".";
			t.textfield.setText(display);	
		}
		else if(src.equals("DELETE"))
		{
			display=t.textfield.getText();
			t.textfield.setText("");
			for(int i=0;i<display.length()-1;i++)
			{
				t.textfield.setText(t.textfield.getText()+display.charAt(i));
			}
		}
		else if(src.equals("CLEAR"))
		{
			t.textfield.setText("");
			i=0;
			c=0;			
		}
		else if(src.equals("Decimal to Hexadecimal")){
			String n=t.textfield.getText();
			int no=Integer.parseInt(n);
			char hexchar[]={'F','E','D','C','B','A'};
			String result="";
			int rem=0;
			while(no>0){
				rem=no%16;
				if(rem>9){
					result=hexchar[15-rem]+result;		
				}
				else{
					result=rem+result;
				}
				no=no/16;
			}
			nf.hex.setText(result);
		}	
		else if(src.equals("Decimal to Octal")){
			String n=t.textfield.getText();
			int no=Integer.parseInt(n);
			int rem=0;
			String octal="";
			char oct[]={'0','1','2','3','4','5','6','7','8','9'};
			while(no>0){
				rem=no%8;
				octal=oct[rem]+octal;
				no=no/8;
			}
			nf.octal.setText(octal);
		}
		else if(src.equals("Decimal to Binary")){
			String n=t.textfield.getText();
			int no=Integer.parseInt(n);
			int a=0;
			String x="";
			while(no>0){
				a=no%2;
				x=a+""+x;
				no=no/2;
			}
			nf.binary.setText(x);
		}
	}
}
//class for TextField
class TxtPanel extends JPanel
{
	static JTextField textfield;
	public TxtPanel()
	{
		Font font = new Font("Arial",Font.BOLD,13);
		textfield=new JTextField(25);
		textfield.setFont(font);
		textfield.setHorizontalAlignment(JTextField.RIGHT);
		this.add(textfield);	
		Events e= new Events(this);
	}	
}
//class for number buttons
class NoPanel extends JPanel
{
	JButton []no;
	JButton zero,dot,equalTo;
	JButton delete,reset;
	public NoPanel()
	{
		GridLayout gl=new GridLayout(5,3);				
		this.setLayout(gl);
	
		no=new JButton[9];
		int j=7,count=0;
		Events n=new Events();

		for(int i=0;i<9;i++)
		{
			if(i==3){ j=4; }
			else if(i==6){ j=1; }
			this.add(no[i]=new JButton(""+j));				
			no[i].addActionListener(n);
			j++;		
		}
		this.add(dot=new JButton("."));
		dot.addActionListener(n);
		
		this.add(zero=new JButton("0"));
		zero.addActionListener(n);

		this.add(equalTo=new JButton("="));
		equalTo.addActionListener(n);
		
		this.add(delete=new JButton("DELETE"));
		delete.addActionListener(n);

		this.add(reset=new JButton("CLEAR"));
		reset.addActionListener(n);
	}
}
//class for operator buttons
class Operation extends JPanel{

	JButton operator[];
	public Operation()
	{	
		Events n=new Events();			
		GridLayout gl=new GridLayout(5,1);
		this.setLayout(gl);
		
		operator=new JButton[4];
		this.add(operator[0]=new JButton("+"));
		this.add(operator[1]=new JButton("-"));
		this.add(operator[2]=new JButton("*"));
		this.add(operator[3]=new JButton("/"));

		for(int i=0;i<4;i++){
			operator[i].addActionListener(n);
		}
	}
}



class No_format extends JPanel{

	JTextField binary,octal,hex;
	JButton b,o,h;	
	public No_format(){
		Events n = new Events(this);
		Font font = new Font("Arial",Font.BOLD,13);
		
		GridLayout gl=new GridLayout(3,2);
		this.setLayout(gl);

		binary=new JTextField(10);
		binary.setFont(font);
		octal=new JTextField(10);
		octal.setFont(font);
		hex=new JTextField(10);
		hex.setFont(font);

		b=new JButton("Decimal to Binary");
		b.addActionListener(n);
		o=new JButton("Decimal to Octal");
		o.addActionListener(n);
		h=new JButton("Decimal to Hexadecimal");
		h.addActionListener(n);
	
		this.add(b);
		this.add(binary);
		this.add(o);
		this.add(octal);
		this.add(h);
		this.add(hex);	
	}
} 
//class for adding panels on frame
class Calculator extends JFrame
{
	TxtPanel text,t;
	NoPanel number;
	Operation op;	
	No_format nf;
	public Calculator()
	{
		GridBagLayout gridbag = new GridBagLayout();
		this.setLayout(gridbag);
       		GridBagConstraints c = new GridBagConstraints();

		this.setTitle("Calculator");

		c.fill = GridBagConstraints.HORIZONTAL;	
		c.gridx = 0;  
   		c.gridy = 0;
		this.add(text=new TxtPanel(),c);
		
		c.fill = GridBagConstraints.HORIZONTAL;	
		c.gridx = 0;  
   		c.gridy = 1;
		this.add(number=new NoPanel(),c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;  
   		c.gridy = 1;
		this.add(op=new Operation(),c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;  
   		c.gridy = 2;
		this.add(nf=new No_format(),c);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(450,300);
		this.setVisible(true);	
	}
}
class Calculator_Design
{
	public static void main(String [] args)
	{
		Calculator c = new Calculator();		
	}
}
