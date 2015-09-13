package pfps;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera
{
	private static final float MAX_ANGLE_X = 60;
	private static final float MAX_ANGLE_Y = 60;

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
		rotateY(center, -angleX);
		rotateY(up, -angleX);
		rotateX(center, -angleY);
		rotateX(up, -angleY);

		// center = eye + direction
		addVector(center, pos_);

		glu.gluLookAt(pos_[0], pos_[1], pos_[2],
				center[0], center[1], center[2],
				up[0], up[1], up[2]);

		gl2.glRotatef(mouseAngleX_, 0, -1, 0);
		gl2.glRotatef(mouseAngleY_, 1, 0, 0);
	}

	public void move(float dx, float dy, float dz)
	{
		///*
		pos_[0] += dx;
		pos_[1] += dy;
		pos_[2] += dz;
		//*/
		/*
		float angleX = (float)Math.toRadians(mouseAngleX_);
		float angleY = (float)Math.toRadians(mouseAngleY_);

		float[] dv = new float[] { dx, dy, dz };
		// apply 주석 참고
		rotateY(dv, -angleX);
		rotateX(dv, -angleY);

		addVector(pos_, dv);
		//*/
	}

	public void rotateByMouse(float angleX, float angleY)
	{
		mouseAngleX_ = range(-MAX_ANGLE_X, mouseAngleX_ + angleX, MAX_ANGLE_X);
		mouseAngleY_ = range(-MAX_ANGLE_Y, mouseAngleY_ + angleY, MAX_ANGLE_Y);
	}

	private static float range(float min, float val, float max)
	{
		if (val < min)
			return min;
		else if (max < val)
			return max;
		else
			return val;
	}

	private static void rotateX(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = vec[0];
		float y = c * vec[1] - s * vec[2];
		float z = s * vec[1] + c * vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}
	private static void rotateY(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = c * vec[0] + s * vec[2];
		float y = vec[1];
		float z = -s * vec[0] + c * vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}
	private static void rotateZ(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = c * vec[0] - s * vec[1];
		float y = s * vec[0] + c * vec[1];
		float z = vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}

	private static void addVector(float[] vec1, float[] vec2)
	{
		vec1[0] += vec2[0];
		vec1[1] += vec2[1];
		vec1[2] += vec2[2];
	}
}
