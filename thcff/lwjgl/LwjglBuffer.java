package thcff.lwjgl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public class LwjglBuffer
{
	private static IntBuffer idBuffer = BufferUtils.createIntBuffer(1);
	
	private static int createBufferId()
	{
		GL15.glGenBuffers(LwjglBuffer.idBuffer);
		return LwjglBuffer.idBuffer.get(0);
	}
	
	public static int vertexBufferData(FloatBuffer buffer)
	{
		int id = createBufferId();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		return id;
	}
	
	public static int indexBufferData(IntBuffer buffer)
	{
		int id = createBufferId();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		return id;
	}
}
