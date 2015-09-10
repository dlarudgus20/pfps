package pfps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{
	private boolean W_ = false;
	private boolean A_ = false;
	private boolean S_ = false;
	private boolean D_ = false;

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		process(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		process(e, false);
	}
	
	private void process(KeyEvent e, boolean pressed)
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
	
	public static final int UP = 1 << 0;
	public static final int DOWN = 1 << 1;
	public static final int LEFT = 1 << 2;
	public static final int RIGHT = 1 << 3;
	public int getDirection()
	{
		return (W_ ? UP : 0) | (S_ ? DOWN  : 0) | (A_ ? LEFT : 0) | (D_ ? RIGHT : 0);
	}
}
