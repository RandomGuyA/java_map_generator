package Other;
import core.Color;
import core.Program;
import core.Texture;
import core.VBO;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static Model.Helper.BlendComposite.Color;

public class Block {

    static public int blockCount = 0;
    private Vector3f size;
    protected Vector3f position;
    private Color color;
    // make a Vertex Buffer Object
    private VBO square = new VBO();
    // make a Matrix4f for positioning of the square
    private Matrix4f currentMatrix = new Matrix4f();
    // define texture
    private Texture marble;

    public Block(Vector3f size, Color color, Vector3f position, String filename) {
        // increment block count
        blockCount++;

        this.size = size;
        this.color = color;
        this.position = position;
        marble = new Texture(filename);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        marble.bind();

        construct();
    }

// create a Vertex Buffer Object for rendering this Block

    private void construct() {
        // define halfSize as half the value of size
        float x = size.x / 2;
        float y = size.y / 2;
        float z = size.z / 2;

        // define the four vertices
        float[] vertices = {
                // front face
                x, y, z,
                -x, y, z,
                x, -y, z,
                x, -y, z,
                -x, y, z,
                -x, -y, z,
                // back face

                -x, y, -z,
                x, y, -z,
                -x, -y, -z,
                - x, -y, -z,
                x, y, -z,
                x, -y, -z,
                //  top
                x, y, z,
                x, y, -z,
                -x, y, z,
                -x, y, z,
                x, y, -z,
                -x, y, -z,
                // bottom
                x, -y, z,
                -x, -y, z,
                x, -y, -z,
                x, -y, -z,
                -x, -y, z,
                -x, -y, -z,
                // left
                x, y, z,
                x, -y, z,
                x, y, -z,
                x, y, -z,
                x, -y, z,
                x, -y, -z,
                // right
                -x, y, z,
                -x, y, -z,
                -x, -y, z,
                -x, -y, z,
                -x, y, -z,
                -x, -y, -z
        };
        // vertex texture Coordinates
        float[] textureCoord = {
                // front face
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                // back face
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                // top face
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                // bottom face
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                // left
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
                // right
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                0.0f, 0.0f
        };

        // vertex normals
        float[] normals = {
                // front face
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                // back face
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                // front face
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                // back face
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                // left
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                // right
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f
        };



        square.setAttributes("vertexPosition", 3, vertices);
        square.setAttributes("vertexTextureCoord", 2, textureCoord);
        square.setAttributes("vertexNormal", 3, normals);
        square.construct(GL11.GL_TRIANGLES);
    }

// render this Block

    public void render(Matrix4f matrix) {
        Program program = Program.current;
        // set the color of our square to color
        program.setUniform("color", color.getRed(), color.getGreen(), color.getBlue(), 1.0f);
        // translate the square by position
        currentMatrix.load(matrix);
        currentMatrix.translate(position);
        program.setUniform("modelviewMatrix", false, currentMatrix);

        // setup texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        marble.bind();

        // render the square
        square.render();
    }
}