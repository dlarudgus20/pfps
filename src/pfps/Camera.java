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
	
	public void apply(GL2 gl2, GLU glu)
	{
		float angleX = (float)Math.toRadians(mouseAngleX_);
		float angleY = (float)Math.toRadians(mouseAngleY_);

		// center는 방향을 계산 후 pos_를 더해줌
		float[] center = new float[] { 0, 0, -distance_ };
		float[] up = new float[] { 0, 1, 0 };

		// angleX는 Y축으로, angelY는 X축으로 회전해야 함.
		Utility.rotateY(center, -angleX);
		Utility.rotateY(up, -angleX);
		Utility.rotateX(center, -angleY);
		Utility.rotateX(up, -angleY);

		// center = eye + direction
		Utility.addVector(center, pos_);

		glu.gluLookAt(pos_[0], pos_[1], pos_[2],
				center[0], center[1], center[2],
				up[0], up[1], up[2]);
	}

	public void move(float dx, float dy, float dz)
	{
		/*
		pos_[0] += dx;
		pos_[1] += dy;
		pos_[2] += dz;
		//*/
		///*
		float angleX = (float)Math.toRadians(mouseAngleX_);
		float angleY = (float)Math.toRadians(mouseAngleY_);

		float[] dv = new float[] { dx, dy, dz };
		// apply 주석 참고
		Utility.rotateY(dv, -angleX);

		Utility.addVector(pos_, dv);
		//*/
	}

	public void rotateByMouse(float angleX, float angleY)
	{
		mouseAngleX_ = Utility.range(-MAX_ANGLE_X, mouseAngleX_ + angleX, MAX_ANGLE_X);
		mouseAngleY_ = Utility.range(-MAX_ANGLE_Y, mouseAngleY_ + angleY, MAX_ANGLE_Y);
	}
}
