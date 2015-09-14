package pfps;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Terrain
{
	private static final float MAP_BLOCK_SIZE = 5;
	private static final float DEPTH_UNIT = 1;
	private static final float[] MAP_ORIGIN = { -20, -20, 0 };
	
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
		for (int y = 0; y < depthMap.length - 1; y++)
		{
			for (int x = 0; x < depthMap[y].length - 1; x++)
			{
				final float x0 = MAP_ORIGIN[0] + (x + 0) * MAP_BLOCK_SIZE;
				final float y0 = MAP_ORIGIN[1] + (y + 0) * MAP_BLOCK_SIZE;
				final float x1 = MAP_ORIGIN[0] + (x + 1) * MAP_BLOCK_SIZE;
				final float y1 = MAP_ORIGIN[1] + (y + 1) * MAP_BLOCK_SIZE;
				gl2.glBegin(GL2.GL_QUADS);
				{
					gl2.glVertex3f(x0, y0, MAP_ORIGIN[2] + depthMap[y + 0][x + 0] * DEPTH_UNIT);
					gl2.glVertex3f(x1, y0, MAP_ORIGIN[2] + depthMap[y + 0][x + 1] * DEPTH_UNIT);
					gl2.glVertex3f(x1, y1, MAP_ORIGIN[2] + depthMap[y + 1][x + 1] * DEPTH_UNIT);
					gl2.glVertex3f(x0, y1, MAP_ORIGIN[2] + depthMap[y + 1][x + 0] * DEPTH_UNIT);
				}
				gl2.glEnd();
			}
		}
		gl2.glPopAttrib();
	}
}
