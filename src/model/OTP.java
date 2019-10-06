package model;

import gui.SwingOTPView;
import javax.swing.JButton;
import javax.swing.JLabel;
import otp.HOTP;
import otp.TOTP;
import utils.Base32;

public class OTP
{
	public SwingOTPView	swingOTPView;
	
	public int			digits;
	public int			counter = 1;
	public byte[]		key;
	public String		otpType;
	public String		crypto;
	
	public JLabel 		name;
	public JLabel		otp;
	public JLabel		blank;
	
	public JButton		sync;
	public JButton		del;
	
	@SuppressWarnings("deprecation")
	public OTP(SwingOTPView swingOTPView, String name, String key, String otpType, String crypto, int digits)
	{
		this.swingOTPView = swingOTPView;
		this.digits = digits;
		this.key = Base32.decode(key);
		this.otpType = (otpType == "Time-based") ? "TOTP" : "HOTP";
		this.crypto = crypto;
		createLabels(name);
		createButtons();
		addComponents();
		
		this.swingOTPView.resize(this.swingOTPView.getWidth(), this.swingOTPView.getHeight() + 50);
	}
	
	@SuppressWarnings("deprecation")
	public void delete()
	{
		this.swingOTPView.getContentPane().remove(this.name);
		this.swingOTPView.getContentPane().remove(this.otp);
		this.swingOTPView.getContentPane().remove((this.otpType == "HOTP") ? this.sync : this.blank);
		this.swingOTPView.getContentPane().remove(this.del);
		
		this.swingOTPView.resize(this.swingOTPView.getWidth(), this.swingOTPView.getHeight() - 50);
		
		this.swingOTPView.otps.remove(this);
	}
	
	public void	sync(long epoch)
	{
		if (this.otpType == "HOTP")
			this.otp.setText("            HOTP : " + HOTP.generateHOTP(this.key, this.counter++, this.crypto, this.digits));
		else
			this.otp.setText("            TOTP : " + TOTP.generateTOTP(this.key, epoch, this.crypto, this.digits));
	}
	
	public void	createLabels(String name)
	{
		long epoch = Math.round(System.currentTimeMillis() / 1000.0);
		
		this.name = new JLabel(" Account : " + name);
		if (this.otpType == "HOTP")
			this.otp = new JLabel("            HOTP : " + HOTP.generateHOTP(this.key, this.counter++, this.crypto, this.digits));
		else
			this.otp = new JLabel("            TOTP : " + TOTP.generateTOTP(this.key, epoch, this.crypto, this.digits));
		this.blank = new JLabel("");
	}
	
	public void createButtons()
	{
		if (this.otpType == "HOTP")
		{
			this.sync = new JButton("Sync");
			this.sync.addActionListener(this.swingOTPView);
		}
		this.del = new JButton("Delete");
		this.del.addActionListener(this.swingOTPView);
	}
	
	public void addComponents()
	{
		this.swingOTPView.getContentPane().add(this.name);
		this.swingOTPView.getContentPane().add(this.otp);
		this.swingOTPView.getContentPane().add((this.otpType == "HOTP") ? this.sync : this.blank);
		this.swingOTPView.getContentPane().add(this.del);
	}
}