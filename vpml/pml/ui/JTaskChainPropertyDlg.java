package pml.ui;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.beans.*; //Property change stuff
import java.awt.*;
import java.awt.event.*;

class JTaskChainPropertyDlg extends PropertyDialog
{
    private String msgString1;
    private JLabel labelTaskName;
    private JTextField fieldTaskName;
    private JLabel labelTaskType;
    private JTextField fieldTask;

    private String strName = null;
    private String strType = null;
    
    public String getName() 
    {
       return strName;
    }

    public String getType() 
    {
      return strType;
    }
    
    public JTaskChainPropertyDlg(JFrame parent, String component, Icon icon ) 
    {
        super(parent, component, icon);

        final JFrame parentPanel = parent;
        final String btnString1 = "OK";
        final String btnString2 = "Cancel";

        msgString1 = "Enter the " + component + " Properties";
        labelTaskName = new JLabel( "Task Name:" );
        fieldTaskName = new JTextField(20);
        labelTaskType = new JLabel( "Task Type:" );
        fieldTask = new JTextField(20);

        Object[] array = { msgString1, labelTaskName, fieldTaskName, 
                                           labelTaskType, fieldTask };
        Object[] options = {btnString1, btnString2};

        optionPane = new JOptionPane(array,
                                      JOptionPane.QUESTION_MESSAGE,
                                      JOptionPane.YES_NO_OPTION,
                                      icon,
                                      options,
                                      options[0]);
         setContentPane(optionPane);
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

//       getContentPane().add( mAuthor, 1 );
//       getContentPane().add( new JLabel( "Author:", JLabel.LEADING ), 1 );
         setTitle("Action Property");

         addWindowListener(
         new WindowAdapter()
         {
                public void windowClosing(WindowEvent we)
                {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                 }
         });

         fieldTaskName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionPane.setValue(btnString1);
            }
         });
         optionPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                String prop = e.getPropertyName();
                if (isVisible() && (e.getSource() == optionPane)
                                && (prop.equals(JOptionPane.VALUE_PROPERTY) ||
                                prop.equals(JOptionPane.INPUT_VALUE_PROPERTY)))
                {
                    Object value = optionPane.getValue();
                    setVisible(false);
                    if (value == JOptionPane.UNINITIALIZED_VALUE) {
                       //ignore reset
                        return;
                    }

                    if ( value == "OK") {
                      strName = fieldTaskName.getText();
                      strType = fieldTask.getText();
                    }
                    else
                      return;

                    optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    setVisible(false);
                  }
              }
           });
    }
}

