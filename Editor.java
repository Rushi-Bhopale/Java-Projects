import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.*;
import java.io.*;

/*
1.	Add Font Settings
	Status:
		--> Done
		--> Make only one font frame available at one time
		--> Area of TextArea shouldn't change as per font size
2.	Add close tab
3.	Add checking for CANCEL or CLOSE option of JFileChooser in OPEN, SAVE and SAVE AS.
	i.e. It will delete all contents of file if Cancel is choosen in OPEN.
4.	Add Option to save the temporary(without name) files. Maybe while Opening.
*/

class FontController
{
	JFrame jfrm;
	JList <String> myListFontNames;
	JList <String> myListFontSize;
	JComboBox <String> jcbTextColor;
	JRadioButton jrb[] = new JRadioButton[3];
	String textColor[] = {"RED", "GREEN", "BLUE", "BLACK"};
	String fontSize[] = {"10", "15", "20", "25", "30", "35", "40", "45", "50"};
	String fontNames[] = {"Arial", "Comic Sans MS", "Times New Roman"};
	
	EventHandling eh;
	
	FontController()
	{
		jfrm = new JFrame("Font Controller");
		eh = new EventHandling();
		myListFontNames = new JList<String>(fontNames);
		myListFontSize = new JList<String>(fontSize);
		jcbTextColor = new JComboBox<String>(textColor);
		
		jrb[0] = new JRadioButton("Plain");
		jrb[1] = new JRadioButton("Bold");
		jrb[2] = new JRadioButton("Italic");
		
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < 3; i++)
			bg.add(jrb[i]);
		
		jfrm.setLayout(new FlowLayout());
		
		jfrm.add(myListFontNames);
		myListFontNames.addListSelectionListener(eh);
		jfrm.add(myListFontSize);
		myListFontSize.addListSelectionListener(eh);
		for (int i = 0; i < 3; i++)
		{
			jfrm.add(jrb[i]);
			jrb[i].addItemListener(eh);
		}
		jfrm.add(jcbTextColor);
		jcbTextColor.addActionListener(eh);
		
		EventHandling.fc = this;
		
		jfrm.setSize(400, 250);
		jfrm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jfrm.setResizable(false);
		jfrm.setVisible(true);
	}
}

class EventHandling implements ActionListener, ItemListener, ListSelectionListener
{
	// For Font Controller
	static String fName;
	static int fStyle;
	static int fSize = 12;
	static Color fColor;
	
	static MultiTabs mt1;
	static Vector <TabTextArea> tta = new Vector <TabTextArea>();
	static JTabbedPane tabbedPane;
	static FontController fc;
	
	static boolean isOpen = false;
	static File currentFileName = new File("");
	static boolean isEmptyCurrentFileName = true;
	
	EventHandling() {}
	EventHandling(MultiTabs temp) { mt1 = temp; }
	
	String fileReading(File fileName)
	{
		String wholeFileString = "";
		
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			
			String str = "";
			while ((str = br.readLine()) != null)
				wholeFileString += (str + "\n");
		}
		catch(Exception e)
		{
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JOptionPane.showMessageDialog(frame, "File doesn't exit.");
		}
		finally
		{
			try
			{
				if (br != null) br.close();
				if (fr != null) fr.close();
			}
			catch(Exception e){}
		}
		
		return wholeFileString;
	}
	
	void fileWriting(File fileName, String wholeFileString)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			
			bw.write(wholeFileString);
			bw.flush();
		}
		catch(Exception e)
		{
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JOptionPane.showMessageDialog(frame, "File doesn't exit.");
		}
		finally
		{
			try
			{
				if (bw != null) bw.close();
				if (fw != null) fw.close();
			}
			catch(Exception e){}
		}
	}
	
	public void itemStateChanged (ItemEvent ie)
	{
		if (fc.jrb[0].isSelected()) fStyle = Font.PLAIN;
		else if (fc.jrb[1].isSelected()) fStyle = Font.BOLD;
		else if (fc.jrb[2].isSelected()) fStyle = Font.ITALIC;
		
		Font font = new Font(fName, fStyle, fSize);
		try
		{
			for (int i = 0; i < tta.size(); i++)
				tta.elementAt(i).txtArea.setFont(font);
		}
		catch (Exception e) {}
	}
	public void valueChanged(ListSelectionEvent lse)
	{
		Font font = new Font(fName, fStyle, fSize);
		
		int i = fc.myListFontNames.getSelectedIndex();
		
		if (i == 0) fName = "Arial";
		else if (i == 1) fName = "Comic Sans MS";
		else if (i == 2) fName = "Times New Roman";
		
		int j = fc.myListFontSize.getSelectedIndex();
		
		if (j == 0) fSize = 10;
		else if (j == 1) fSize = 15;
		else if (j == 2) fSize = 20;
		else if (j == 3) fSize = 25;
		else if (j == 4) fSize = 30;
		else if (j == 5) fSize = 35;
		else if (j == 6) fSize = 40;
		else if (j == 7) fSize = 45;
		else if (j == 8) fSize = 50;
		
		try
		{
			for (int x = 0; x < tta.size(); x++)
				tta.elementAt(x).txtArea.setFont(font);
		}
		catch (Exception e) {}
	}
	public void actionPerformed(ActionEvent ae)
	{
		String source = ae.getActionCommand();
		
		try
		{
			int i = fc.jcbTextColor.getSelectedIndex();
			
			if (i == 0) fColor = Color.RED;
			else if (i == 1) fColor = Color.GREEN;
			else if (i == 2) fColor = Color.BLUE;
			else if (i == 3) fColor = Color.BLACK;
		}
		catch (Exception e) {}
		
		try
		{
			for (int i = 0; i < tta.size(); i++)
				tta.elementAt(i).txtArea.setForeground(fColor);
		}
		catch (Exception e) {}
		
		if (source.equals("New"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			mt1.addTabs();
		}
		else if (source.equals("Open"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			if(!(tempTTA.isCurrentFileNameEmpty))
			{
				String wholeFileString = "";
				wholeFileString = tempTTA.txtArea.getText();
				
				fileWriting(tempTTA.currentFileName, wholeFileString);
			}

			
			// Pure code for opening a file

			File fileName = new File("");
			
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = chooser.showOpenDialog(null);
			
			if (returnValue == JFileChooser.APPROVE_OPTION)
			{
				fileName = chooser.getSelectedFile();
				tempTTA.currentFileName = fileName;
				String title = chooser.getSelectedFile().getName();
				tabbedPane.setTitleAt(tabNumber, title);
				tempTTA.isCurrentFileNameEmpty = false;
			}
			
			String wholeFileString = fileReading(fileName);
			
			tempTTA.txtArea.setText(wholeFileString);
			tempTTA.isOpen = true;

		}
		else if (source.equals("Save"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			String wholeFileString = "";
			wholeFileString = tempTTA.txtArea.getText();
			
			if (!(tempTTA.isOpen))
			{
				File fileName = new File("");
				
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = chooser.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					fileName = chooser.getSelectedFile();
					tempTTA.currentFileName = fileName;
					String title = chooser.getSelectedFile().getName();
					tabbedPane.setTitleAt(tabNumber, title);
					tempTTA.isCurrentFileNameEmpty = false;
					tempTTA.isOpen = true;
				}
			}
			
			fileWriting(tempTTA.currentFileName, wholeFileString);
			
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JOptionPane.showMessageDialog(frame, "File is successfully saved.");
		}
		else if (source.equals("Save As"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			String wholeFileString = "";
			wholeFileString = tempTTA.txtArea.getText();
			
			File fileName = new File("");
			
			JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = chooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION)
			{
				fileName = chooser.getSelectedFile();
				tempTTA.currentFileName = fileName;
				String title = chooser.getSelectedFile().getName();
				tabbedPane.setTitleAt(tabNumber, title);
				tempTTA.isCurrentFileNameEmpty = false;
			}
			
			fileWriting(fileName, wholeFileString);
			
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JOptionPane.showMessageDialog(frame, "File is successfully saved.");			
		}
		else if (source.equals("Cut"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			tempTTA.txtArea.cut();
		}
		else if (source.equals("Copy"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			tempTTA.txtArea.copy();
		}
		else if (source.equals("Paste"))
		{
			int tabNumber = tabbedPane.getSelectedIndex();
			TabTextArea tempTTA = tta.elementAt(tabNumber);
			
			tempTTA.txtArea.paste();
		}
		else if (source.equals("Font Controller"))
		{
			FontController fc = new FontController();
		}
		
	}
}

class TabTextArea extends JPanel
{
	JTextArea txtArea;
	JScrollPane scrollPane;
	int tabNumber;
	
	boolean isOpen = false;
	File currentFileName = new File("");
	boolean isCurrentFileNameEmpty = true;
	
	TabTextArea(int t_tabNumber)
	{
		tabNumber = t_tabNumber;
		Font font = new Font(EventHandling.fName, EventHandling.fStyle, EventHandling.fSize);
		
		txtArea = new JTextArea(15, 40);
		txtArea.setFont(font);
		txtArea.setForeground(EventHandling.fColor);
		scrollPane = new JScrollPane(txtArea);
		
		this.add(scrollPane);
		
		EventHandling.tta.add(this);
	}
}

class MultiTabs extends JTabbedPane
{
	static int count = 0;
	int tabNumber;
	
	MultiTabs()
	{
		tabNumber = count;
		this.addTab("" + count++, new TabTextArea(tabNumber));
		
		EventHandling.tabbedPane = this;
		EventHandling eh = new EventHandling(this);
	}
	
	void addTabs()
	{
		tabNumber = count;
		this.addTab("" + count++, new TabTextArea(tabNumber));
	}
}

class TextEditor extends JFrame
{
	MultiTabs mt1;
	JButton button;
	EventHandling eh1;
	JMenuBar menuBar1;
	
	TextEditor()
	{
		mt1 = new MultiTabs();
		menuBar1 = new JMenuBar();
		
		addMenus("File");
		addMenus("Edit");
		addMenus("Font");
		
		this.add(menuBar1, BorderLayout.NORTH);
		this.add(mt1);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1280, 720);
		this.setTitle("Editor");
		this.setVisible(true);
	}
	
	void addMenus(String menuName)
	{
		eh1 = new EventHandling();
		JMenu menu1 = new JMenu(menuName);
		
		if (menuName.equals("File"))
		{
			JMenuItem menuItem1[] = new JMenuItem[4];
			menuItem1[0] = new JMenuItem("New");
			menuItem1[1] = new JMenuItem("Open");
			menuItem1[2] = new JMenuItem("Save");
			menuItem1[3] = new JMenuItem("Save As");
			
			for (int i = 0; i < 4; i++)
			{
				menu1.add(menuItem1[i]);
				menuItem1[i].addActionListener(eh1);
			}
		}
		else if (menuName.equals("Edit"))
		{
			JMenuItem menuItem1[] = new JMenuItem[3];
			menuItem1[0] = new JMenuItem("Cut");
			menuItem1[1] = new JMenuItem("Copy");
			menuItem1[2] = new JMenuItem("Paste");
			
			for (int i = 0; i < 3; i++)
			{
				menu1.add(menuItem1[i]);
				menuItem1[i].addActionListener(eh1);
			}
		}
		else if (menuName.equals("Font"))
		{
			JMenuItem menuItem = new JMenuItem("Font Controller");
			menu1.add(menuItem);
			menuItem.addActionListener(eh1);
		
		}
		menuBar1.add(menu1);
	}
}

public class Editor
{
	public static void main(String args [])
	{
		TextEditor te1 = new TextEditor();
	}
}
