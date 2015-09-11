package pfps;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

	private Camera camera_ = new Camera();
	private Light light_ = new Light();

	private Input input_ = new Input();

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
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		this.addKeyListener(input_);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL2 gl2 = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU();

		gl2.glViewport(0, 0, width, height);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(60, width / height, 0.1f, 100);
		
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
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
		/*int direction = input_.getDirection();
		if (direction != 0)
		{
			float dx = ((direction & Input.LEFT) != 0 ? -1 : 0) + ((direction & Input.RIGHT) != 0 ? 1 : 0);
			float dy = ((direction & Input.UP) != 0 ? -1 : 0) + ((direction & Input.DOWN) != 0 ? 1 : 0);
			camera_.move(dx, dy, 0);
		}*/
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
