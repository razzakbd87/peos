/*
		A basic implementation of the JDialog class.
*/

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class execSysCommand extends javax.swing.JDialog
{
    private int retCode;
    private boolean bEdit = true;
    
	public execSysCommand(Frame parent)
	{
		super(parent);
		
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		getContentPane().setLayout(null);
		setSize(436,587);
		setVisible(false);
		JScrollPane1.setBorder(bevelBorder1);
		JScrollPane1.setOpaque(true);
		getContentPane().add(JScrollPane1);
		JScrollPane1.setBounds(16,324,404,204);
		JTextArea1.setEditable(false);
		JTextArea1.setWrapStyleWord(true);
		JTextArea1.setTabSize(4);
		JTextArea1.setLineWrap(true);
		JScrollPane1.getViewport().add(JTextArea1);
		JTextArea1.setBounds(0,0,400,200);
		JLabel1.setText("Input/Output:");
		getContentPane().add(JLabel1);
		JLabel1.setBounds(12,300,84,24);
		JLabel2.setText("Waiting for finishing the execution...");
		getContentPane().add(JLabel2);
		JLabel2.setBounds(12,540,228,28);
		JLabel2.setVisible(false);
		JButton1.setText("Close");
		JButton1.setActionCommand("jbutton");
		JButton1.setMnemonic((int)'C');
		JButton1.setEnabled(false);
		getContentPane().add(JButton1);
		JButton1.setBounds(171,540,94,24);
		JButton1.setVisible(false);
		JExecLable.setText("Executing ...");
		getContentPane().add(JExecLable);
		JExecLable.setBounds(12,12,128,24);
		JButtonEdit.setText("Edit");
		JButtonEdit.setActionCommand("Edit");
		getContentPane().add(JButtonEdit);
		JButtonEdit.setBounds(171,276,94,24);
		JScrollPane2.setBorder(bevelBorder1);
		JScrollPane2.setOpaque(true);
		getContentPane().add(JScrollPane2);
		JScrollPane2.setBounds(16,36,404,228);
		JTextArea2.setEditable(false);
		JTextArea2.setDoubleBuffered(true);
//		JTextArea2.setWrapStyleWord(true);
		JTextArea2.setTabSize(4);
		JTextArea2.setLineWrap(true);
		JScrollPane2.getViewport().add(JTextArea2);
		JTextArea2.setBounds(0,0,400,224);
		//$$ bevelBorder1.move(0,588);
		//}}
	    
		//{{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		JButton1.addActionListener(lSymAction);
		JButtonEdit.addActionListener(lSymAction);
		//}}
	}

	public execSysCommand()
	{
		this((Frame)null);
	}

	public execSysCommand(String sTitle)
	{
		this();
		setTitle(sTitle);
	}

	public void setVisible(boolean b)
	{
		if (b)
			setLocation(50, 50);
		super.setVisible(b);
	}

	public void addNotify()
	{
		// Record the size of the window prior to calling parents addNotify.
		Dimension size = getSize();

		super.addNotify();

		if (frameSizeAdjusted)
			return;
		frameSizeAdjusted = true;

		// Adjust size of frame according to the insets
		Insets insets = getInsets();
		setSize(insets.left + insets.right + size.width, insets.top + insets.bottom + size.height);
	}
	// Used by addNotify
	boolean frameSizeAdjusted = false;
    
    public int getExitCode()
    {
        return retCode;
    }
    
    public void Exec(String procID, String cmd, String Type)
    {
        setTitle(procID);
        JExecLable.setText(new String("Executing " + Type));
		JTextArea2.setText(cmd );
		ExecCmd(cmd);
	}	
    private void ExecCmd(String cmd)
    {
        try
        {
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
            while ((line = is.readLine()) != null)
            {
				JTextArea1.append(line);
            }
		    JLabel2.setVisible(true);
            Dimension sz = JLabel2.getSize();
		    JLabel2.paintImmediately(0, 0, sz.width, sz.height);
            proc.waitFor();
            retCode = proc.exitValue();
            Integer iRet = new Integer(retCode);
			String msg = new String("\nReturn code is "+iRet.toString()+". Press <Close> to continue.");
			JTextArea1.append(msg);
        }
        catch (Exception e)
        {
            String msg = new String("Get exception "+e+"\nCannot preceed. Press <Close> to continue.");
            JTextArea1.setText(msg);
        }
        finally
        {
		    JLabel2.setVisible(false);
            Dimension sz = JLabel2.getSize();
		    JLabel2.paintImmediately(0, 0, sz.width, sz.height);
    		JButton1.setEnabled(true);
		    JButton1.setVisible(true);
        }
    }

	//{{DECLARE_CONTROLS
	javax.swing.JScrollPane JScrollPane1 = new javax.swing.JScrollPane();
	javax.swing.JTextArea JTextArea1 = new javax.swing.JTextArea();
	javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
	javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
	javax.swing.JButton JButton1 = new javax.swing.JButton();
	javax.swing.JLabel JExecLable = new javax.swing.JLabel();
	javax.swing.JButton JButtonEdit = new javax.swing.JButton();
	javax.swing.JScrollPane JScrollPane2 = new javax.swing.JScrollPane();
	javax.swing.JTextArea JTextArea2 = new javax.swing.JTextArea();
	javax.swing.border.Border bevelBorder1 = BorderFactory.createLoweredBevelBorder();
	//}}


	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == JButton1)
				JButton1_actionPerformed(event);
			else if (object == JButtonEdit)
				JButtonEdit_actionPerformed(event);
			
		}
	}

	class SymWindow extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			Object object = event.getSource();
			if (object == execSysCommand.this)
				exeSysCommand_windowClosing(event);
		}
	}

	void exeSysCommand_windowClosing(java.awt.event.WindowEvent event)
	{
		try {
			// JLoginDialog Hide the JLoginDialog
			this.setVisible(false);
		} catch (Exception e) {
			String msg = "Get exception: " + e;
            JOptionPane.showMessageDialog(this, msg, "execSysCommand",JOptionPane.ERROR_MESSAGE);
		}
	}

	void JButton1_actionPerformed(java.awt.event.ActionEvent event)
	{
		try {
			this.setVisible(false);
		} catch (Exception e) {
			String msg = "Get exception: " + e;
            JOptionPane.showMessageDialog(this, msg, "execSysCommand",JOptionPane.ERROR_MESSAGE);
		}
	}

	void JButtonEdit_actionPerformed(java.awt.event.ActionEvent event)
	{
		JTextArea2.setEditable(bEdit);
	    if (bEdit)
		{
		    bEdit = false;
		    JButtonEdit.setText("Exec");
    		JButton1.setEnabled(false);
		    JButton1.setVisible(false);
		}
		else
		{
		    bEdit = true;
		    ExecCmd(JTextArea2.getText());
		    JButtonEdit.setText("Edit");
		}
	}
}
