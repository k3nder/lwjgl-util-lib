## LWJGL-UTIL-LIB
lwjgl-util-lib is a library to handle lwjgl functionalities more fluently and without so many complications.

### Quickstart
To use lwjgl-util-lib you need to have the lwjgl natives and lwjgl libraries, version 3.0.0 or higher, and the JOML library used for calculations.

You can get the libraries at [here](https://www.lwjgl.org/customize)

### Creating a window
To create a window there is an abstract class that is used for this, an example:
```java
import net.kn3der.gl.Window;

public class MainWindow extends Window {
    public MainWindow() {
        // width, height, title
        super(800, 600, "My first window");
    }
    public static void main(String[] args) {
        new MainWindow().init();
    }
}
```
Here a window is being created with a width of 800 and a height of 600 and the title "My first window", then in the main a new window is being created and then it is being started

To draw things you have to override the draw method, this method is in a loop until the window is closed, which can be done with the close method
```java

@Override
public void draw() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
}

```
This clears the depth and color buffers of the window and then paints it blue (RGBA is used)

## Rendering Graphical Objects
To render objects more efficiently there is another class called GraphicalObject, with this you can create your own objects and render them, but that will be seen in the complete wiki, here we will use an already defined class called Cube

To create a GraphicObject you have to do it in a specific way, e.g.:
```java
private GraphicalObject mycube;
@Override
public void createComponents() {
    mycube = new Cube( new Vector3f(0, 0,0),
            Texture.builder()
                    .colorChanel(GL_RGB)
                    .flipV()
                    .configuration(GL_TEXTURE_WRAP_S, GL_REPEAT)
                    .configuration(GL_TEXTURE_WRAP_T, GL_REPEAT)
                    .configuration(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                    .configuration(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                    .source(MainWindow.class.getResourceAsStream("/container.jpg"))
                    .create()
    );
}
@Override
public void initComponents() {
    mycube.init();
}
```
Here a cube is being created that is located on the axis x = 0 y = 0 z = 0, and it is using the texture located in the resources called container.jpg

> [!WARNING]
> Only works for now with jpg textures

```java
@Override
public void draw() {
    mycube.render(null);
}
```
With this call to render we will get an error, since the render function requires that we pass a Shader, here we have the complete and functional class:

```java
import net.k3nder.gl.GraphicalObject;
import net.k3nder.gl.Window;
import net.k3nder.gl.objects.Cube;
import net.k3nder.gl.shader.Shader;
import net.k3nder.gl.shader.Shaders;
import net.k3nder.gl.visual.Texture;
import net.k3nder.test.Main;
import org.joml.Vector3f;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class MainWindow extends Window {
    public MainWindow() {
        super(800, 600, "My first window");
    }
    private GraphicalObject mycube;
    private Shader shader;
    @Override
    public void createComponents() {
        mycube = new Cube( new Vector3f(0, 0,0),
                Texture.builder()
                        .colorChanel(GL_RGB)
                        .flipV()
                        .configuration(GL_TEXTURE_WRAP_S, GL_REPEAT)
                        .configuration(GL_TEXTURE_WRAP_T, GL_REPEAT)
                        .configuration(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                        .configuration(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                        .source(MainWindow.class.getResourceAsStream("/container.jpg"))
                        .create()
        );
    }
    @Override
    public void initComponents() {
        mycube.init();
        shader = Shaders.getDefaultShader("static_model");
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        shader.use();
        mycube.render(shader);
    }
}
```

Here, apart from everything we have seen, a shader is also used, which is created in InitComponents, since it uses OpenGL functions in its creation, whereas the other objects do not use OpenGL functions in their creation, in CreateComponents OpenGL has not yet been started, apart from that the shader is an example shader that is by default in the library, this is called 'static_model', which indicates that it is an object that is going to be loaded on the user's screen, and not in the world, the loading in the world will be seen in the wiki.