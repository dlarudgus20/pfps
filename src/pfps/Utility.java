package pfps;

public final class Utility
{
	private Utility() { }

	public static float range(float min, float val, float max)
	{
		if (val < min)
			return min;
		else if (max < val)
			return max;
		else
			return val;
	}

	public static void rotateX(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = vec[0];
		float y = c * vec[1] - s * vec[2];
		float z = s * vec[1] + c * vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}

	public static void rotateY(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = c * vec[0] + s * vec[2];
		float y = vec[1];
		float z = -s * vec[0] + c * vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}

	public static void rotateZ(float[] vec, float angle)
	{
		final float c = (float)Math.cos(angle);
		final float s = (float)Math.sin(angle);
		float x = c * vec[0] - s * vec[1];
		float y = s * vec[0] + c * vec[1];
		float z = vec[2];
		vec[0] = x; vec[1] = y; vec[2] = z;
	}

	public static void addVector(float[] vec1, float[] vec2)
	{
		vec1[0] += vec2[0];
		vec1[1] += vec2[1];
		vec1[2] += vec2[2];
	}

	public static void multVector(float[] vec, float a)
	{
		vec[0] *= a;
		vec[1] *= a;
		vec[2] *= a;
	}

	public static float length(float[] vec)
	{
		return (float)Math.sqrt(vec[0]*vec[0] + vec[1]*vec[1] + vec[2]*vec[2]);
	}

	public static void normalize(float[] vec)
	{
		float a = 1.f / length(vec);
		multVector(vec, a);
	}

	public static float[] cross(float[] v1, float[] v2)
	{
		float[] out = new float[3];
		cross(v1, v2, out);
		return out;
	}
	public static void cross(float[] v1, float[] v2, float[] out)
	{
		out[0] = v1[1]*v2[2] - v1[2]*v2[1];
		out[1] = v1[2]*v2[0] - v1[0]*v2[2];
		out[2] = v1[0]*v2[1] - v1[1]*v2[0];
	}
}
