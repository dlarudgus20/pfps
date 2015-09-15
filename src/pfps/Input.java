package pfps;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener
{
	private MyFrame frame_;

	private boolean W_ = false;
	private boolean A_ = false;
	private boolean S_ = false;
	private boolean D_ = false;

	public void init(MyFrame frame)
	{
		frame_ = frame;

		Point pt = getCenter();
		frame_.getRobot().mouseMove(pt.x, pt.y);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (frame_ != null)
			processKey(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (frame_ != null)
		processKey(e, false);
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (frame_ != null && frame_.getFocusOwner() != null)
			processMouseMove(e);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (frame_ != null && frame_.getFocusOwner() != null)
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
		final float magnify = (float)Math.PI / 6;

		Point ptCenter = getCenter();
		Point ptMouse = e.getLocationOnScreen();

		int dx = ptMouse.x - ptCenter.x;
		int dy = ptMouse.y - ptCenter.y;

		int width = frame_.getRenderCanvas().getWidth();
		int height = frame_.getRenderCanvas().getHeight();

		float angleX = (dx / (width / 2.f)) * magnify;
		float angleY = (dy / (height / 2.f)) * magnify;

		frame_.rotateByMouse(angleX, angleY);

		frame_.getRobot().mouseMove(ptCenter.x, ptCenter.y);
	}

	private Point getCenter()
	{
		Point pt = frame_.getRenderCanvas().getLocationOnScreen();
		pt.x += frame_.getRenderCanvas().getWidth() / 2;
		pt.y += frame_.getRenderCanvas().getHeight() / 2;
		return pt;
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
