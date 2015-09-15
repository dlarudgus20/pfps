package pfps;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera
{
	private static final float MAX_ANGLE_X = Float.MAX_VALUE;
	private static final float MAX_ANGLE_Y = 45;

	private float distance_ = 10;
	private float[] pos_ = { 0, 0, distance_ };
	private float mouseAngleX_ = 0;
	private float mouseAngleY_ = 0;

	// calculated vectors by calculate()
	private float[] front_ = new float[3];
	private float[] right_ = new float[3];
	private float[] up_ = new float[3];

	public Camera()
	{
		calculate();
	}

	public void apply(GL2 gl2, GLU glu)
	{
		glu.gluLookAt(pos_[0], pos_[1], pos_[2],
				pos_[0] + front_[0], pos_[1] + front_[1], pos_[2] + front_[2],
				up_[0], up_[1], up_[2]);
	}

	public void move(float dx, float dy, float dz)
	{
		/*
		pos_[0] += dx;
		pos_[1] += dy;
		pos_[2] += dz;
		//*/
		///*
		float[] dv = new float[] { dx, dy, dz };
		Utility.rotateY(dv, -mouseAngleX_);

		Utility.addVector(pos_, dv);
		//*/

		calculate();
	}

	public void rotateByMouse(float angleX, float angleY)
	{
		mouseAngleX_ = Utility.range(-MAX_ANGLE_X, mouseAngleX_ + angleX, MAX_ANGLE_X);
		mouseAngleY_ = Utility.range(-MAX_ANGLE_Y, mouseAngleY_ + angleY, MAX_ANGLE_Y);
		calculate();
	}

	private void calculate()
	{
		// ref: http://www.learnopengl.com/#!Getting-started/Camera
		// warning: direction cannot be parallel with (0,1,0)
		
		front_[0] = distance_ * (float)(Math.cos(-mouseAngleY_) * Math.cos(mouseAngleX_ - Math.PI / 2));
		front_[1] = distance_ * (float)(Math.sin(-mouseAngleY_));
		front_[2] = distance_ * (float)(Math.cos(-mouseAngleY_) * Math.sin(mouseAngleX_ - Math.PI / 2));

		Utility.cross(front_, new float[] { 0, 1, 0 }, right_);
		Utility.normalize(front_);

		Utility.cross(right_, front_, up_);
		Utility.normalize(up_);
	}
}
