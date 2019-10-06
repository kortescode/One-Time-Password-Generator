package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import model.OTP;

public class SwingOTPView extends JFrame implements IOTPConstants, ActionListener, FocusListener, WindowListener
{
	private static final long serialVersionUID = 1L;

	public Timer			timer;
	public ActionListener 	timeListener;
	public int				timeCounter;
	
	public JButton 			add, exit;
	public JLabel			title, desc, blank1, blank2, blank3, blank4;
	
	public SwingOTPAddView	popUp;
	
	public ArrayList<OTP>	otps = new ArrayList<OTP>();
			
    public SwingOTPView()
    {
    	long epoch = Math.round(System.currentTimeMillis() / 1000.0);
    	this.popUp = new SwingOTPAddView(this);
    	
    	this.timeCounter = (int) (30 - (epoch % 30));
    	
    	Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    	int OTPFrameWidthPosition = (screenDimension.width - 800) / 2;		
    	int OTPFrameHeightPosition = (screenDimension.height - 100) / 2;
    	
    	createWindow();
		createLabels();
		createButtons();
		addComponents();
		
		setTitle("Requireris");
    	setSize(800, 100);
    	setLocation(OTPFrameWidthPosition, OTPFrameHeightPosition);
    	setVisible(true);
    	
    	this.timeListener = new ActionListener() { public void actionPerformed(ActionEvent e) { updateTimeCounter(); } };
    	this.timer = new Timer(1000, this.timeListener);
    	this.timer.start();
    }

	private void createWindow()
	{
		setLayout(new GridLayout(0,4));
		setResizable(false);
		addWindowListener(this);
	}

    public void createLabels()
    {
    	this.title = new JLabel(" One Time Password Generator");
    	if (this.timeCounter <= 5)
    		this.desc.setForeground(Color.red);
    	else if (this.timeCounter == 30)
    		this.desc.setForeground(Color.black);
    	this.desc  = new JLabel("             Sync in " + this.timeCounter + " sec");
    	
    	this.blank1 = new JLabel();
    	this.blank2 = new JLabel();
    	this.blank3 = new JLabel();
    	this.blank4 = new JLabel();
    }
	
    public void createButtons()
    {
    	this.add = new JButton("Add");
    	this.add.addActionListener(this);
		
    	this.exit = new JButton("Exit");
		this.exit.addActionListener(this);
    }
    
    public void addComponents()
    {
    	getContentPane().add(this.title);
 		getContentPane().add(this.desc);
 		getContentPane().add(this.add);
 		getContentPane().add(this.exit);
 		getContentPane().add(this.blank1);
 		getContentPane().add(this.blank2);
 		getContentPane().add(this.blank3);
 		getContentPane().add(this.blank4);
	}
    
    public void actionPerformed(ActionEvent e)
    {
    	long epoch = Math.round(System.currentTimeMillis() / 1000.0);
    	
    	if (e.getSource() == this.add)
    		this.popUp.setVisible(true);
        if (e.getSource() == this.exit)
        	bye();
        for (OTP otp : this.otps)
        {
        	if (e.getSource() == otp.del)
        		otp.delete();
        	if (otp.otpType == "HOTP" && e.getSource() == otp.sync)
        		otp.sync(epoch);
        }
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
    	this.setVisible(false);
    	this.dispose();
        System.exit(0);
    }
    
    public void updateTimeCounter()
    {
		long epoch = Math.round(System.currentTimeMillis() / 1000.0);
		
		this.timeCounter = (int) (30 - (epoch % 30));
    	if (this.timeCounter % 30 == 0)
    	{
    		for (OTP otp : this.otps)
    			if (otp.otpType == "TOTP")
    				otp.sync(epoch);
    	}
    	if (this.timeCounter <= 5)
    		this.desc.setForeground(Color.red);
    	else if (this.timeCounter == 30)
    		this.desc.setForeground(Color.black);
    	this.desc.setText("             Sync in " + this.timeCounter + " sec");
    }
}