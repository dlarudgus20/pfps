package pfps;

public class Camera
{
	/*// camera
	private PVector eye_ = new PVector(0, 0, -100);
	private PVector center_ = new PVector(0, 0, 0);
	private PVector up_ = new PVector(0, -1, 0);

	// projection
	private float fovy_ = PConstants.PI / 4;
	private float aspect_ = 1.5f;
	private float near_ = -1200;
	private float far_ = 100;

	public Camera()
	{
		// TODO 자동 생성된 생성자 스텁
	}
	
	public void apply(PGraphics g)
	{
		g.camera(eye_.x, eye_.y, eye_.z,
				center_.x, center_.y, center_.z,
				up_.x, up_.y, up_.z);
		g.perspective(fovy_, aspect_, near_, far_);
	}

	public void move(PVector dx)
	{
		eye_.add(dx);
		center_.add(dx);
		near_ += dx.z;
		far_ += dx.z;
	}

	public void rotateX(float angle)
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
	}

	public PVector getEye() { return eye_; }
	public PVector getCenter() { return center_; }
	public PVector getUp() { return up_; }

	public void print(PApplet applet)
	{
		applet.println("eye: (" + eye_.x + ", " + eye_.y + ", " + eye_.z + ")");
		applet.println("center: (" + center_.x + ", " + center_.y + ", " + center_.z + ")");
		applet.println("up: (" + up_.x + ", " + up_.y + ", " + up_.z + ")");
	}*/

}
