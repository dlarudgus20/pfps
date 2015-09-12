package pfps;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class MyFrame extends JFrame implements GLEventListener
{
	private static final long serialVersionUID = 7067036476639086788L;

	public static void main(String[] args)
	{
		GLProfile.initSingleton();

		EventQueue.invokeLater(() -> {
			MyFrame f = new MyFrame();
			f.setVisible(true);
		});
	}

	private GLProfile glprof_;
	private GLCapabilities glcap_;

	private GLCanvas glcanvas_;
	private FPSAnimator animator_;

	private static final int MOVE_TIMER_FREQ = 100;
	private Timer moveTimer_;

	private Camera camera_;
	private Light light_;

	private Input input_;

	public MyFrame()
	{
		glprof_ = GLProfile.getDefault();
		glcap_ = new GLCapabilities(glprof_);

		initUI();
	}

	private void initUI()
	{
		setTitle("pfps");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		glcanvas_ = new GLCanvas(glcap_);
		glcanvas_.addGLEventListener(this);
		this.getContentPane().add(glcanvas_, BorderLayout.CENTER);

		animator_ = new FPSAnimator(glcanvas_, 60);
		animator_.start();

		moveTimer_ = new Timer(MOVE_TIMER_FREQ, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onMoveTimer();
			}
		});
		moveTimer_.start();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e)
			{
				MyFrame.this.windowOpened(e);
			}
		});
		this.addKeyListener(input_);
		this.addMouseMotionListener(input_);
	}

	private void windowOpened(WindowEvent e)
	{
		camera_ = new Camera();
		light_ = new Light();

		try
		{
			input_ = new Input(this);
		}
		catch (AWTException e)
		{
			
		}

		hideMouseCursor();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
	}
	
	@Override
	public void init(GLAutoDrawable drawable)
	{
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
	}
	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL2 gl2 = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU();

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glEnable(GL2.GL_DEPTH_TEST);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(60, drawable.getSurfaceWidth() /drawable.getSurfaceHeight(), 0.1f, 100);
		
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		camera_.apply(gl2, glu);
		//light_.apply(gl2);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);

		gl2.glBegin(GL2.GL_TRIANGLES);
		{
			gl2.glNormal3f(0, 0, 1);
			gl2.glVertex3f(0, 1, 0);
			gl2.glNormal3f(0, 0, 1);
			gl2.glVertex3f(-0.87f, -0.5f, 0);
			gl2.glNormal3f(0, 0, 1);
			gl2.glVertex3f(0.87f, -0.5f, 0);

			gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
			gl2.glVertex3f(0, 1, 0);
			gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
			gl2.glVertex3f(0.87f, -0.5f, 0);
			gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
			gl2.glVertex3f(0, 0, -0.75f);

			gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
			gl2.glVertex3f(-0.87f, -0.5f, 0);
			gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
			gl2.glVertex3f(0, 1, 0);
			gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
			gl2.glVertex3f(0, 0, -0.75f);
		}
		gl2.glEnd();
	}

	private void onMoveTimer()
	{
		int direction = input_.getDirection();
		if (direction != 0)
		{
			float dx = ((direction & Input.LEFT) != 0 ? -1 : 0) + ((direction & Input.RIGHT) != 0 ? 1 : 0);
			float dz = ((direction & Input.UP) != 0 ? -1 : 0) + ((direction & Input.DOWN) != 0 ? 1 : 0);
			camera_.move(dx, 0, dz);
		}
	}

	private void hideMouseCursor()
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Point hotSpot = new Point(0, 0);
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "invisibleCursor");
		setCursor(invisibleCursor);
	}

/*	private int smouseX_, smouseY_;
	private boolean rpressed_ = false;
	@Override
	public void mousePressed()
	{
		if (mouseButton == RIGHT)
		{
			smouseX_ = mouseX;
			smouseY_ = mouseY;
		}
		if (mouseButton == LEFT)
		{
			camera_.print(this);
		}
	}
	@Override
	public void mouseDragged()
	{
		if (mouseButton == RIGHT)
		{
			float dx = mouseX - smouseX_;
			float dy = mouseY - smouseY_;

			float angleX = (dx / (width / 2)) * (PConstants.PI / 6);
			float angleY = (dy / (height / 2)) * (PConstants.PI / 6);
 
			//if (Math.abs(angleX) > (PConstants.PI / 1000))
			{
				camera_.rotateX(angleX);
				smouseX_ = mouseX;
			}
			//if (Math.abs(angleY) > (PConstants.PI / 1000))
			{
				camera_.rotateY(angleY);
				smouseY_ = mouseY;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		input_.process(e.getKeyCode(), true);
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		input_.process(e.getKeyCode(), false);
	}*/

}
