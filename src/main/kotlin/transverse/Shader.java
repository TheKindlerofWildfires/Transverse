package transverse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;
public class Shader {

    private int program;
    private int vs; //indices
    private int fs; //color effects

    public Shader(String filename){
        program = glCreateProgram();
        vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, readFile(filename+".vs"));
        glCompileShader(vs);
        if(glGetShaderi(vs, GL_COMPILE_STATUS)!=1){
            System.err.println(glGetShaderInfoLog(vs));
        }
        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, readFile(filename+".fs"));
        glCompileShader(fs);
        if(glGetShaderi(fs, GL_COMPILE_STATUS)!=1){
            System.err.println(glGetShaderInfoLog(fs));
        }

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glBindAttribLocation(program, 0,"vertices");
        glLinkProgram(program);
        if((glGetProgrami(program, GL_LINK_STATUS)!=1)){
            System.err.println(glGetProgramInfoLog(program));
        }
        glValidateProgram(program);
        if((glGetProgrami(program, GL_VALIDATE_STATUS)!=1)){
            System.err.println(glGetProgramInfoLog(program));
        }
    }
    public void bind(){
        glUseProgram(program);
    }
    private String readFile(String filename){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(new File("src/main/shaders/" +filename)));
            String line;
            while ((line = br.readLine())!=null){
                string.append(line);
                string.append("\n");
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return string.toString();
    }
}
