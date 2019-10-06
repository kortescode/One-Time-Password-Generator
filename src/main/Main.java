package main;

import gui.SwingOTPView;

public class Main
{	
	public SwingOTPView		swingFrame;
		
    public static void main(String argv[])
    {
        new Main(); 	
    }

    public Main()
    {
    	try
        {	
    		this.swingFrame = new SwingOTPView();
        }
        catch (Exception e)
        {
        	handleException(e);
        }
    }
	
    public void handleException(Exception e)
    {
		System.out.println("exception " + e.toString());
		e.printStackTrace();	
    }
}
