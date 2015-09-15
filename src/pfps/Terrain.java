package pfps;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Terrain
{
	private static final float MAP_BLOCK_SIZE = 5;
	private static final float DEPTH_UNIT = 1;
	private static final float[] MAP_ORIGIN = { -20, -5, -20 };
	
	private static final int[][] depthMap ={
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 1, 3, 2, 3, 3, 4, 2, 1, 1 },
		{ 1, 2, 1, 2, 2, 2, 2, 1, 1 },
		{ 1, 2, 1, 2, 2, 2, 2, 1, 1 },
		{ 1, 2, 1, 2, 2, 2, 2, 1, 1 },
		{ 1, 2, 2, 3, 3, 3, 3, 2, 1 },
		{ 1, 2, 2, 3, 4, 4, 3, 3, 1 },
		{ 1, 2, 1, 1, 1, 1, 1, 1, 1 },
		{ 0, 1, 1, 1, 1, 1, 1, 1, 1 }
	};
	
	public Terrain()
	{
		// TODO 자동 생성된 생성자 스텁
	}

	public void draw(GL2 gl2, GLU glu)
	{
		gl2.glPushAttrib(GL2.GL_POLYGON_BIT);
		gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		for (int z = 0; z < depthMap.length - 1; z++)
		{
			for (int x = 0; x < depthMap[z].length - 1; x++)
			{
				final float x0 = MAP_ORIGIN[0] + (x + 0) * MAP_BLOCK_SIZE;
				final float z0 = MAP_ORIGIN[2] + (z + 0) * MAP_BLOCK_SIZE;
				final float x1 = MAP_ORIGIN[0] + (x + 1) * MAP_BLOCK_SIZE;
				final float z1 = MAP_ORIGIN[2] + (z + 1) * MAP_BLOCK_SIZE;
				gl2.glBegin(GL2.GL_QUADS);
				{
					gl2.glVertex3f(x0, MAP_ORIGIN[1] + depthMap[z + 0][x + 0] * DEPTH_UNIT, z0);
					gl2.glVertex3f(x1, MAP_ORIGIN[1] + depthMap[z + 0][x + 1] * DEPTH_UNIT, z0);
					gl2.glVertex3f(x1, MAP_ORIGIN[1] + depthMap[z + 1][x + 1] * DEPTH_UNIT, z1);
					gl2.glVertex3f(x0, MAP_ORIGIN[1] + depthMap[z + 1][x + 0] * DEPTH_UNIT, z1);
				}
				gl2.glEnd();
			}
		}
		gl2.glPopAttrib();
	}
}
