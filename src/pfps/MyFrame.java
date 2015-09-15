package pfps;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.owens.oobjloader.builder.Build;
import com.owens.oobjloader.builder.Face;
import com.owens.oobjloader.builder.FaceVertex;
import com.owens.oobjloader.parser.Parse;

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

	private static final int MOVE_TIMER_FREQ = 25;
	private Timer moveTimer_;

	private Robot robot_;

	private Camera camera_;
	private Light light_;

	private Terrain terrain_;

	private Input input_;

	private long deltaTime;
	private long lastFrame;

	public MyFrame()
	{
		glprof_ = GLProfile.getDefault();
		glcap_ = new GLCapabilities(glprof_);

		camera_ = new Camera();
		light_ = new Light();

		terrain_ = new Terrain();

		input_ = new Input();

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
		glcanvas_.addKeyListener(input_);
		glcanvas_.addMouseMotionListener(input_);

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
	}

	private void windowOpened(WindowEvent e)
	{
		try
		{
			robot_ = new Robot();
		}
		catch (AWTException ex)
		{
			JOptionPane.showMessageDialog(this, "지원하지 않는 플랫픔입니다.", "에러", JOptionPane.ERROR_MESSAGE);
			dispose();
		}

		input_.init(this);

		hideMouseCursor();
	}

	public Robot getRobot()
	{
		return robot_;
	}

	public GLCanvas getRenderCanvas()
	{
		return glcanvas_;
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
		try
		{
			glock3__ = new Build();
			new Parse(glock3__, "res/glock3.obj");
		}
		catch (IOException e)
		{
			System.out.println("ㅇㄴ");
			e.printStackTrace();
		}
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable)
	{
	}

	private Build glock3__;
	private float angle__ = 0;
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL2 gl2 = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU();

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glDisable(GL2.GL_CULL_FACE);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(60, drawable.getSurfaceWidth() / drawable.getSurfaceHeight(), 0.1f, 100);
		
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		camera_.apply(gl2, glu);
		light_.apply(gl2, glu);

		terrain_.draw(gl2, glu);

		Runnable triangle = () -> {
			gl2.glBegin(GL2.GL_TRIANGLES);
			{
				gl2.glColor3f(1, 1, 1);
				gl2.glNormal3f(0, 0, 1);
				gl2.glVertex3f(0, 1, 0);
				gl2.glColor3f(1, 0, 0);
				gl2.glNormal3f(0, 0, 1);
				gl2.glVertex3f(-0.87f, -0.5f, 0);
				gl2.glColor3f(0, 0, 1);
				gl2.glNormal3f(0, 0, 1);
				gl2.glVertex3f(0.87f, -0.5f, 0);

				gl2.glColor3f(1, 1, 1);
				gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
				gl2.glVertex3f(0, 1, 0);
				gl2.glColor3f(0, 0, 1);
				gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
				gl2.glVertex3f(0.87f, -0.5f, 0);
				gl2.glColor3f(0, 1, 0);
				gl2.glNormal3f(0.71898836f, 0.4170133f, -0.5560177f);
				gl2.glVertex3f(0, 0, -0.75f);

				gl2.glColor3f(1, 0, 0);
				gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
				gl2.glVertex3f(-0.87f, -0.5f, 0);
				gl2.glColor3f(1, 1, 1);
				gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
				gl2.glVertex3f(0, 1, 0);
				gl2.glColor3f(0, 1, 0);
				gl2.glNormal3f(-0.7189883f, 0.41701326f, -0.5560177f);
				gl2.glVertex3f(0, 0, -0.75f);

				gl2.glColor3f(0, 0, 1);
				gl2.glNormal3f(0, -1.305f, -0.87f);
				gl2.glVertex3f(0.87f, -0.5f, 0);
				gl2.glColor3f(1, 0, 0);
				gl2.glNormal3f(0, -1.305f, -0.87f);
				gl2.glVertex3f(-0.87f, -0.5f, 0);
				gl2.glColor3f(0, 1, 0);
				gl2.glNormal3f(0, -1.305f, -0.87f);
				gl2.glVertex3f(0, 0,  -0.75f);
			}
			gl2.glEnd();
		};

		gl2.glPushMatrix();
		{
			gl2.glPushMatrix();
			gl2.glTranslatef(0, 0, 0);
			gl2.glRotatef(angle__, 0, 1 ,0);
			triangle.run();
			gl2.glPopMatrix();

			gl2.glPushMatrix();
			gl2.glTranslatef(0, 3.2f, 0);
			gl2.glRotatef(angle__+ 30, 0, 1 ,0);
			triangle.run();
			gl2.glPopMatrix();

			gl2.glPushMatrix();
			gl2.glTranslatef(3.2f, 0, 0);
			gl2.glRotatef(angle__ + 60, 0, 1 ,0);
			triangle.run();
			gl2.glPopMatrix();
		}
		gl2.glPopMatrix();
		angle__ += 1;

		gl2.glPushMatrix();
		gl2.glTranslatef(0.05f, -1.5f, 5);
		gl2.glScalef(0.05f, 0.05f, 0.05f);
		for (Face face : glock3__.faces)
		{
			gl2.glBegin(GL2.GL_POLYGON);
			for (FaceVertex vertex : face.vertices)
			{
				gl2.glColor3f(1, 1, 1);
				gl2.glNormal3f(vertex.n.x, vertex.n.y, vertex.n.z);
				gl2.glVertex3f(vertex.v.x, vertex.v.y, vertex.v.z);
			}
			gl2.glEnd();
		}
		gl2.glPopMatrix();
	}

	public void rotateByMouse(float angleX, float angleY)
	{
		camera_.rotateByMouse(angleX, angleY);
	}

	private void onMoveTimer()
	{
		final float UNIT = 0.08f;
		int direction = input_.getDirection();
		if (direction != 0)
		{
			float dx = ((direction & Input.LEFT) != 0 ? -UNIT : 0) + ((direction & Input.RIGHT) != 0 ? UNIT : 0);
			float dz = ((direction & Input.UP) != 0 ? -UNIT : 0) + ((direction & Input.DOWN) != 0 ? UNIT : 0);
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
}
