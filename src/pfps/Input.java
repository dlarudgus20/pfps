package pfps;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener
{
	private Robot robot_;

	private boolean W_ = false;
	private boolean A_ = false;
	private boolean S_ = false;
	private boolean D_ = false;

	private int prevMouseX_ = 0, prevMouseY_ = 0;

	public Input(MyFrame frame) throws AWTException
	{
		// initialize mouse coordinate
		robot_ = new Robot();
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		processKey(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		processKey(e, false);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		processMouseMove(e);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		processMouseMove(e);
	}
	
	private void processKey(KeyEvent e, boolean pressed)
	{
		switch (e.getKeyCode())
		{
			case 'W':
				W_ = pressed;
				break;
			case 'A':
				A_ = pressed;
				break;
			case 'S':
				S_ = pressed;
				break;
			case 'D':
				D_ = pressed;
				break;
		}
	}

	private void processMouseMove(MouseEvent e)
	{
		
	}
	
	public static final int UP = 1 << 0;
	public static final int DOWN = 1 << 1;
	public static final int LEFT = 1 << 2;
	public static final int RIGHT = 1 << 3;
	public int getDirection()
	{
		return (W_ ? UP : 0) | (S_ ? DOWN  : 0) | (A_ ? LEFT : 0) | (D_ ? RIGHT : 0);
	}
}
