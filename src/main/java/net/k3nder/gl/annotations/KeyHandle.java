package net.k3nder.gl.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyHandle {
    int value();
    int action() default GLFW_PRESS;
    int[] modifiers() default {};

    class Func{
        public static <T> void loadEvents(Class<T> clazz,Object obj , int scancode, int key, int action, int mods) {
            // Obtener todos los métodos de la clase que extiende (subclase)
            Method[] methods = clazz.getDeclaredMethods();

            // Iterar sobre los métodos para encontrar los anotados con @KeyHandler
            for (Method method : methods) {
                if (method.isAnnotationPresent(net.k3nder.gl.annotations.KeyHandle.class)) {
                    net.k3nder.gl.annotations.KeyHandle annotation = method.getAnnotation(net.k3nder.gl.annotations.KeyHandle.class);

                    boolean inValid = false;
                    for (int modifier : annotation.modifiers()) {
                        inValid = ((mods & modifier) == 0);
                    }
                    if (inValid) continue;

                    // Verificar si el código de la tecla coincide con el especificado en la anotación

                    if (annotation.value() == key && action == annotation.action()) {
                        try {
                            // Invocar el método anotado
                            var params = method.getParameters();

                            if ( params.length == 1 && params[0].getType().equals(int.class)) {
                                method.invoke(obj, scancode);
                                continue;
                            }
                            method.invoke(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
