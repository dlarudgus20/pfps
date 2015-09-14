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
}
