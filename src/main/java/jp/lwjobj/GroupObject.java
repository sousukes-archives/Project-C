package jp.lwjobj;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import thcff.lwjgl.LwjglBuffer;

public class GroupObject
{
	public String name;
	public ArrayList<WavefrontObject.Vertex> vertices = new ArrayList<>();
	private int indicesBufferId, numberIndices;;
	
	public GroupObject()
	{
		this("");
	}
	
	public GroupObject(String name)
	{
		this.name = name;
	}
	
	public void compile(WavefrontObject obj)
	{
		this.numberIndices = this.vertices.size();
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(this.numberIndices);
		
		this.vertices.forEach(v -> String.valueOf(!obj.vertices.contains(v) && obj.vertices.add(v)));
		this.vertices.forEach(v -> indicesBuffer.put(obj.vertices.indexOf(v)));
		indicesBuffer.flip();
		
		this.indicesBufferId = LwjglBuffer.indexBufferData(indicesBuffer);
	}
	
	public void render()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indicesBufferId);
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.numberIndices, GL11.GL_UNSIGNED_INT, 0);
	}
}