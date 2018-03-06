package jp.lwjobj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import thcff.lwjgl.LwjglBuffer;

/**
 * Wavefront Object importer Based heavily off of the specifications found at http://en.wikipedia.org/wiki/Wavefront_.obj_file
 */
public class WavefrontObject
{
	private static Pattern vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
	private static Pattern vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
	private static Pattern textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
	private static Pattern face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
	private static Pattern face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
	private static Pattern face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
	private static Pattern face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
	private static Pattern groupObjectPattern = Pattern.compile("([go]( [\\w\\d]+) *\\n)|([go]( [\\w\\d]+) *$)");
	
	private static Matcher vertexMatcher, vertexNormalMatcher, textureCoordinateMatcher;
	private static Matcher face_V_VT_VN_Matcher, face_V_VT_Matcher, face_V_VN_Matcher, face_V_Matcher;
	private static Matcher groupObjectMatcher;
	
	public List<Vertex> vertices = new ArrayList();
	
	public List<Vector3f> vertexPositions = new ArrayList<>();
	public List<Vector3f> vertexNormals = new ArrayList<>();
	public List<Vector2f> textureCoordinates = new ArrayList<>();
	public List<GroupObject> groupObjects = new ArrayList<>();
	private GroupObject currentGroupObject;
	private String fileName;
	
	private int verticesBufferId, normalsBufferId, texCoordBufferId;
	
	public WavefrontObject(InputStream inputStream, String fileName) throws ModelFormatException
	{
		this.fileName = fileName;
		
		this.loadObjModel(inputStream);
		
		this.groupObjects.forEach(g -> g.compile(this));
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(this.vertices.size() * 3);
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(this.vertices.size() * 3);
		FloatBuffer texCoordBufer = BufferUtils.createFloatBuffer(this.vertices.size() * 2);
		
		for (Vertex vertex : this.vertices)
		{
			verticesBuffer.put(vertex.pos.x).put(vertex.pos.y).put(vertex.pos.z);
			normalBuffer.put(vertex.normal.x).put(vertex.normal.y).put(vertex.normal.z);
			texCoordBufer.put(vertex.tex.x).put(vertex.tex.y);
		}
		verticesBuffer.position(0);
		normalBuffer.position(0);
		texCoordBufer.position(0);
		
		this.verticesBufferId = LwjglBuffer.vertexBufferData(verticesBuffer);
		this.normalsBufferId = LwjglBuffer.vertexBufferData(normalBuffer);
		this.texCoordBufferId = LwjglBuffer.vertexBufferData(texCoordBufer);
		
		// なんとなく消しといたほうがいいかなと
		this.vertices.clear();
		this.vertexPositions.clear();
		this.vertexNormals.clear();
		this.textureCoordinates.clear();
	}
	
	public void renderAll()
	{
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.verticesBufferId);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.normalsBufferId);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.texCoordBufferId);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		
		this.groupObjects.forEach(g -> g.render());
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void renderOnly(String... groupNames)
	{
		this.groupObjects.stream().filter(g -> Arrays.binarySearch(groupNames, g.name) < 0).forEach(g -> g.render());
	}
	
	private void loadObjModel(InputStream inputStream) throws ModelFormatException
	{
		String currentLine = null;
		int lineCount = 0;
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
		{
			while ((currentLine = reader.readLine()) != null)
			{
				lineCount++;
				currentLine = currentLine.replaceAll("\\s+", " ").trim();
				
				if (currentLine.startsWith("#") || currentLine.length() == 0)
				{
					continue;
				}
				else if (currentLine.startsWith("v "))
				{
					Vector3f vertex = this.parseVertex(currentLine, lineCount);
					if (vertex != null)
					{
						this.vertexPositions.add(vertex);
					}
				}
				else if (currentLine.startsWith("vn "))
				{
					Vector3f vertex = this.parseVertexNormal(currentLine, lineCount);
					if (vertex != null)
					{
						this.vertexNormals.add(vertex);
					}
				}
				else if (currentLine.startsWith("vt "))
				{
					Vector2f textureCoordinate = this.parseTextureCoordinate(currentLine, lineCount);
					if (textureCoordinate != null)
					{
						this.textureCoordinates.add(textureCoordinate);
					}
				}
				else if (currentLine.startsWith("f "))
				{
					if (this.currentGroupObject == null)
					{
						this.currentGroupObject = new GroupObject("Default");
					}
					
					List<Vertex> vertices = this.parseFace(currentLine, lineCount);
					this.currentGroupObject.vertices.addAll(vertices);
				}
				else if (currentLine.startsWith("g ") | currentLine.startsWith("o "))
				{
					GroupObject group = this.parseGroupObject(currentLine, lineCount);
					
					if (group != null)
					{
						if (this.currentGroupObject != null)
						{
							this.groupObjects.add(this.currentGroupObject);
						}
					}
					
					this.currentGroupObject = group;
				}
			}
			this.groupObjects.add(this.currentGroupObject);
		}
		catch (IOException e)
		{
			throw new ModelFormatException("IO Exception reading model format", e);
		}
		finally
		{
			try
			{
				inputStream.close();
			}
			catch (IOException e)
			{
				// hush
			}
		}
	}
	
	private Vector3f parseVertex(String line, int lineCount) throws ModelFormatException
	{
		Vector3f vertex = null;
		
		if (WavefrontObject.isValidVertexLine(line))
		{
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");
			
			try
			{
				if (tokens.length == 2)
				{
					return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), 0);
				}
				else if (tokens.length == 3) { return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]),
						Float.parseFloat(tokens[2])); }
			}
			catch (NumberFormatException e)
			{
				throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
			}
		}
		else
		{
			throw new ModelFormatException(
					"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
		}
		
		return vertex;
	}
	
	private Vector3f parseVertexNormal(String line, int lineCount) throws ModelFormatException
	{
		Vector3f vertexNormal = null;
		
		if (WavefrontObject.isValidVertexNormalLine(line))
		{
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");
			
			try
			{
				if (tokens.length == 3) { return new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]),
						Float.parseFloat(tokens[2])); }
			}
			catch (NumberFormatException e)
			{
				throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
			}
		}
		else
		{
			throw new ModelFormatException(
					"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
		}
		
		return vertexNormal;
	}
	
	private Vector2f parseTextureCoordinate(String line, int lineCount) throws ModelFormatException
	{
		Vector2f textureCoordinate = null;
		
		if (WavefrontObject.isValidTextureCoordinateLine(line))
		{
			line = line.substring(line.indexOf(" ") + 1);
			String[] tokens = line.split(" ");
			
			try
			{
				if (tokens.length <= 2)
				{
					textureCoordinate = new Vector2f(Float.parseFloat(tokens[0]), 1 - Float.parseFloat(tokens[1]));
				}
			}
			catch (NumberFormatException e)
			{
				throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), e);
			}
		}
		else
		{
			throw new ModelFormatException(
					"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
		}
		
		return textureCoordinate;
	}
	
	private List<Vertex> parseFace(String line, int lineCount) throws ModelFormatException
	{
		List<Vertex> vertices = new ArrayList();
		
		if (WavefrontObject.isValidFaceLine(line))
		{
			String trimmedLine = line.substring(line.indexOf(" ") + 1);
			String[] tokens = trimmedLine.split(" ");
			String[] subTokens = null;
			
			// f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3 ...
			if (WavefrontObject.isValidFace_V_VT_VN_Line(line))
			{
				for (String token : tokens)
				{
					Vertex vertex = new Vertex();
					vertices.add(vertex);
					
					subTokens = token.split("/");
					
					vertex.pos = this.vertexPositions.get(Integer.parseInt(subTokens[0]) - 1);
					vertex.tex = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
					vertex.normal = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
				}
			}
			// f v1/vt1 v2/vt2 v3/vt3 ...
			else if (WavefrontObject.isValidFace_V_VT_Line(line))
			{
				for (String token : tokens)
				{
					Vertex vertex = new Vertex();
					vertices.add(vertex);
					
					subTokens = token.split("/");
					
					vertex.pos = this.vertexPositions.get(Integer.parseInt(subTokens[0]) - 1);
					vertex.tex = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
				}
			}
			// f v1//vn1 v2//vn2 v3//vn3 ...
			else if (WavefrontObject.isValidFace_V_VN_Line(line))
			{
				for (String token : tokens)
				{
					Vertex vertex = new Vertex();
					vertices.add(vertex);
					
					subTokens = token.split("//");
					
					vertex.pos = this.vertexPositions.get(Integer.parseInt(subTokens[0]) - 1);
					vertex.normal = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
				}
			}
			// f v1 v2 v3 ...
			else if (WavefrontObject.isValidFace_V_Line(line))
			{
				for (String token : tokens)
				{
					Vertex vertex = new Vertex();
					vertices.add(vertex);
					vertex.pos = this.vertexPositions.get(Integer.parseInt(token) - 1);
				}
			}
			else
			{
				throw new ModelFormatException(
						"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
			}
		}
		else
		{
			throw new ModelFormatException(
					"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
		}
		
		return vertices;
	}
	
	private GroupObject parseGroupObject(String line, int lineCount) throws ModelFormatException
	{
		GroupObject group = null;
		
		if (WavefrontObject.isValidGroupObjectLine(line))
		{
			String trimmedLine = line.substring(line.indexOf(" ") + 1);
			
			if (trimmedLine.length() > 0)
			{
				group = new GroupObject(trimmedLine);
			}
		}
		else
		{
			throw new ModelFormatException(
					"Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
		}
		
		return group;
	}
	
	public static class Vertex
	{
		private Vector3f pos = new Vector3f();
		private Vector3f normal = new Vector3f();
		private Vector2f tex = new Vector2f();
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof Vertex)
			{
				Vertex v = (Vertex) obj;
				return this.pos.epsilonEquals(v.pos, 0.001F) && this.normal.epsilonEquals(v.normal, 0.001F) && this.tex.epsilonEquals(v.tex, 0.001F);
			}
			return false;
		}
	}
	
	/***
	 * Verifies that the given line from the model file is a valid vertex
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid vertex, false otherwise
	 */
	private static boolean isValidVertexLine(String line)
	{
		if (WavefrontObject.vertexMatcher != null)
		{
			WavefrontObject.vertexMatcher.reset();
		}
		
		WavefrontObject.vertexMatcher = WavefrontObject.vertexPattern.matcher(line);
		return WavefrontObject.vertexMatcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid vertex normal
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid vertex normal, false otherwise
	 */
	private static boolean isValidVertexNormalLine(String line)
	{
		if (WavefrontObject.vertexNormalMatcher != null)
		{
			WavefrontObject.vertexNormalMatcher.reset();
		}
		
		WavefrontObject.vertexNormalMatcher = WavefrontObject.vertexNormalPattern.matcher(line);
		return WavefrontObject.vertexNormalMatcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid texture coordinate
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid texture coordinate, false otherwise
	 */
	private static boolean isValidTextureCoordinateLine(String line)
	{
		if (WavefrontObject.textureCoordinateMatcher != null)
		{
			WavefrontObject.textureCoordinateMatcher.reset();
		}
		
		WavefrontObject.textureCoordinateMatcher = WavefrontObject.textureCoordinatePattern.matcher(line);
		return WavefrontObject.textureCoordinateMatcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices, texture coordinates, and vertex normals
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid face that matches the format "f v1/vt1/vn1 ..." (with a minimum of 3 points in the face, and a maximum of
	 *         4), false otherwise
	 */
	private static boolean isValidFace_V_VT_VN_Line(String line)
	{
		if (WavefrontObject.face_V_VT_VN_Matcher != null)
		{
			WavefrontObject.face_V_VT_VN_Matcher.reset();
		}
		
		WavefrontObject.face_V_VT_VN_Matcher = WavefrontObject.face_V_VT_VN_Pattern.matcher(line);
		return WavefrontObject.face_V_VT_VN_Matcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices and texture coordinates
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid face that matches the format "f v1/vt1 ..." (with a minimum of 3 points in the face, and a maximum of 4),
	 *         false otherwise
	 */
	private static boolean isValidFace_V_VT_Line(String line)
	{
		if (WavefrontObject.face_V_VT_Matcher != null)
		{
			WavefrontObject.face_V_VT_Matcher.reset();
		}
		
		WavefrontObject.face_V_VT_Matcher = WavefrontObject.face_V_VT_Pattern.matcher(line);
		return WavefrontObject.face_V_VT_Matcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid face that is described by vertices and vertex normals
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid face that matches the format "f v1//vn1 ..." (with a minimum of 3 points in the face, and a maximum of 4),
	 *         false otherwise
	 */
	private static boolean isValidFace_V_VN_Line(String line)
	{
		if (WavefrontObject.face_V_VN_Matcher != null)
		{
			WavefrontObject.face_V_VN_Matcher.reset();
		}
		
		WavefrontObject.face_V_VN_Matcher = WavefrontObject.face_V_VN_Pattern.matcher(line);
		return WavefrontObject.face_V_VN_Matcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid face that is described by only vertices
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid face that matches the format "f v1 ..." (with a minimum of 3 points in the face, and a maximum of 4), false
	 *         otherwise
	 */
	private static boolean isValidFace_V_Line(String line)
	{
		if (WavefrontObject.face_V_Matcher != null)
		{
			WavefrontObject.face_V_Matcher.reset();
		}
		
		WavefrontObject.face_V_Matcher = WavefrontObject.face_V_Pattern.matcher(line);
		return WavefrontObject.face_V_Matcher.matches();
	}
	
	/***
	 * Verifies that the given line from the model file is a valid face of any of the possible face formats
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid face that matches any of the valid face formats, false otherwise
	 */
	private static boolean isValidFaceLine(String line)
	{
		return WavefrontObject.isValidFace_V_VT_VN_Line(line) || WavefrontObject.isValidFace_V_VT_Line(line)
				|| WavefrontObject.isValidFace_V_VN_Line(line) || WavefrontObject.isValidFace_V_Line(line);
	}
	
	/***
	 * Verifies that the given line from the model file is a valid group (or object)
	 *
	 * @param line
	 *            the line being validated
	 * @return true if the line is a valid group (or object), false otherwise
	 */
	private static boolean isValidGroupObjectLine(String line)
	{
		if (WavefrontObject.groupObjectMatcher != null)
		{
			WavefrontObject.groupObjectMatcher.reset();
		}
		
		WavefrontObject.groupObjectMatcher = WavefrontObject.groupObjectPattern.matcher(line);
		return WavefrontObject.groupObjectMatcher.matches();
	}
}