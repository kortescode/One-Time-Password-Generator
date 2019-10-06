package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.OTP;

public class SwingOTPAddView extends JFrame implements IOTPConstants, ActionListener, FocusListener, WindowListener
{
	private static final long serialVersionUID = 1L;

	public SwingOTPView		swingOTPView;
	
	public String[] 		otpStrings = {"Time-based", "Counter-based"};
	public String[] 		cryptoStrings = {"HmacSHA1", "HmacSHA256", "HmacSHA512"};
	public String[] 		digitsStrings = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public JButton 			add, cancel;
	public JLabel			nameLabel, keyLabel, otpLabel, cryptoLabel, digitsLabel;
	public JTextField		name, key;
	public JComboBox		otp, crypto, digits;
	
    public SwingOTPAddView(SwingOTPView swingOTPView)
    {
    	this.swingOTPView = swingOTPView;
    	
    	Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	int OTPFrameWidthPosition = (screenDimension.width - 350) / 2;		
    	int OTPFrameHeightPosition = (screenDimension.height - 200) / 2;
    	
    	createWindow();
		createLabels();
		createComboBoxes();
		createButtons();
		createTextFields();
		addComponents();
		
		setTitle("Add a One Time Password generator");
    	setSize(350, 200);
    	setLocation(OTPFrameWidthPosition, OTPFrameHeightPosition);
    	setVisible(false);
    }

	private void createWindow()
	{
		setLayout(new GridLayout(6,2));
		setResizable(false);
		addWindowListener(this);
	}

    public void createLabels()
    {
    	this.nameLabel = new JLabel("   Account name : ");
    	this.keyLabel = new JLabel("   Key : ");
    	this.otpLabel = new JLabel("   OTP type : ");
    	this.cryptoLabel = new JLabel("   Crypto type : ");
    	this.digitsLabel = new JLabel("   Digits number : ");
    }
	
    public void createButtons()
    {
    	this.add = new JButton("Add");
		this.add.addActionListener(this);
    	
    	this.cancel = new JButton("Cancel");
    	this.cancel.addActionListener(this);
    }
    
    public void createComboBoxes()
    {
    	this.otp = new JComboBox(this.otpStrings);
    	this.otp.setSelectedIndex(0);
    	this.crypto = new JComboBox(this.cryptoStrings);
    	this.crypto.setSelectedIndex(0);
    	this.digits = new JComboBox(this.digitsStrings);
    	this.digits.setSelectedIndex(5);
    }

    public void createTextFields()
    {
    	this.name = new JTextField();						
		this.name.addFocusListener(this);
		
		this.key = new JTextField();						
		this.key.addFocusListener(this);
    }
    
    public void addComponents()
    {
    	getContentPane().add(this.nameLabel);
 		getContentPane().add(this.name);
 		getContentPane().add(this.keyLabel);
 		getContentPane().add(this.key);
 		getContentPane().add(this.otpLabel);
 		getContentPane().add(this.otp);
 		getContentPane().add(this.cryptoLabel);
 		getContentPane().add(this.crypto);
 		getContentPane().add(this.digitsLabel);
 		getContentPane().add(this.digits);
 		getContentPane().add(this.add);
 		getContentPane().add(this.cancel);
	}
    
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource() == this.add)
    	{
    		if (!this.name.getText().isEmpty() && !this.key.getText().isEmpty())
    		{
    			this.swingOTPView.otps.add(new OTP(this.swingOTPView, this.name.getText(), this.key.getText(), this.otpStrings[this.otp.getSelectedIndex()], this.cryptoStrings[this.crypto.getSelectedIndex()], this.digits.getSelectedIndex() + 1));
    			bye();
    		}
    	}
        if (e.getSource() == this.cancel)
        	bye();
    }

    public void focusGained(FocusEvent e)
    {
    }

    public void focusLost(FocusEvent e)
    {
    }
    
    public void windowActivated(WindowEvent e)
    {
    }
    
    public void windowClosed(WindowEvent e)	
    {
    }
    
    public void windowDeactivated(WindowEvent e)
    {
    }
    
    public void windowDeiconified(WindowEvent e)	
    {
    }
    
    public void windowIconified(WindowEvent e)
    {
    }
    
    public void windowOpened(WindowEvent e)
    {
    }

    public void windowClosing(WindowEvent e)
    {
    	bye();
    }
    
    public void bye()
    {
    	this.name.setText("");
    	this.key.setText("");
    	this.otp.setSelectedIndex(0);
    	this.crypto.setSelectedIndex(0);
    	this.digits.setSelectedIndex(5);
    	this.setVisible(false);
    }
}