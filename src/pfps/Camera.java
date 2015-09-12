package pfps;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera
{
	// camera
	private float[] eye_ = { 0, 0, 10 };
	private float[] center_ = { 0, 0, 0 };
	private float[] up_ = { 0, 1, 0 };

	public Camera()
	{
		// TODO 자동 생성된 생성자 스텁
	}
	
	public void apply(GL2 gl2, GLU glu)
	{
		glu.gluLookAt(eye_[0], eye_[1], eye_[2],
				center_[0], center_[1], center_[2],
				up_[0], up_[1], up_[2]);
	}

	public void move(float dx, float dy, float dz)
	{
		eye_[0] += dx; eye_[1] += dy; eye_[2] += dz;
		center_[0] += dx; center_[1] += dy; center_[2] += dz;
	}

	/*public void rotateX(float angle)
	{
		PVector axis = PVector.mult(up_, -1);

		PMatrix3D mat = new PMatrix3D(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1);
		mat.rotate(angle, axis.x, axis.y, axis.z);
		
		PVector tmp = new PVector();
		tmp.set(center_);
		tmp.sub(eye_);
		tmp = mat.mult(tmp, null);
		tmp.add(eye_);
		center_ = tmp;

		up_ = mat.mult(up_, null);
	}

	public void rotateY(float angle)
	{
		PVector axis = new PVector(up_.z, up_.z, -(up_.x + up_.y));
		axis.normalize();

		PMatrix3D mat = new PMatrix3D(
				1, 0, 0, 0,
				0, 1, 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1);
		mat.rotate(angle, axis.x, axis.y, axis.z);
		
		PVector tmp = new PVector();
		tmp.set(center_);
		tmp.sub(eye_);
		tmp = mat.mult(tmp, null);
		tmp.add(eye_);
		center_ = tmp;

		up_ = mat.mult(up_, null);
	}*/

	public float[] getEye() { return eye_; }
	public float[] getCenter() { return center_; }
	public float[] getUp() { return up_; }

	public void print()
	{
		System.out.println("eye:    (" + eye_[0] + ", " + eye_[1] + ", " + eye_[2] + ")");
		System.out.println("center: (" + center_[0] + ", " + center_[1] + ", " + center_[2] + ")");
		System.out.println("up:     (" + up_[0] + ", " + up_[1] + ", " + up_[2] + ")");
	}
}
