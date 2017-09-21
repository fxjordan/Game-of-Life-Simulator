package de.fjobilabs.libgdx.graphics;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * @author Felix Jordan
 * @version 1.0
 * @since 21.09.2017 - 12:59:35
 */
public class ShaderProgrammAccessor {
    
    private ShaderProgram shaderProgram;
    
    // ShaderProgram fields
    private Field programField;
    private Field vertexShaderHandleField;
    private Field fragmentShaderHandleField;
    private Field isCompiledField;
    
    // ShaderProgram methods
    private Method loadShaderMethod;
    private Method linkProgramMethod;
    
    public ShaderProgrammAccessor(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
        
        this.programField = accessField("program");
        this.vertexShaderHandleField = accessField("vertexShaderHandle");
        this.fragmentShaderHandleField = accessField("fragmentShaderHandle");
        this.isCompiledField = accessField("isCompiled");
        
        this.loadShaderMethod = accessMethod("loadShader", int.class, String.class);
        this.linkProgramMethod = accessMethod("linkProgram", int.class);
    }
    
    public int loadShader(int type, String source) {
        return (int) invokeShaderProgramMethod(this.loadShaderMethod, type, source);
    }
    
    public int linkProgram(int program) {
//        return (int) invokeShaderProgramMethod(this.linkProgramMethod, program);
        Method method = this.linkProgramMethod;
        try {
            System.out.println("arg: " + program);
            int parameterCount = method.getParameterCount();
            System.out.println("parameter count: " + parameterCount);
            return (int) this.loadShaderMethod.invoke(this.shaderProgram, program);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot invoke ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception in ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        }
    }
    
    public int getProgramm() {
        return getIntFieldValue(this.programField);
    }
    
    public int getVertexShaderHandle() {
        return getIntFieldValue(this.vertexShaderHandleField);
    }
    
    public int getFragmentShaderHandle() {
        return getIntFieldValue(this.fragmentShaderHandleField);
    }
    
    public void setIsCompiled(boolean isCompiled) {
        setShaderProgramFieldValue(this.isCompiledField, isCompiled);
    }
    
    private Field accessField(String name) {
        Field field;
        try {
            field = ShaderProgram.class.getDeclaredField(name);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("ShaderProgram field '" + name + "' does not exist", e);
        } catch (SecurityException e) {
            throw new RuntimeException("Cannot access ShaderProgram field '" + name + "'", e);
        }
        return field;
    }
    
    private Method accessMethod(String name, Class<?>... parameterTypes) {
        Method method;
        try {
            method = ShaderProgram.class.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    "ShaderProgram method '" + name + Arrays.toString(parameterTypes) + "' does not exist",
                    e);
        } catch (SecurityException e) {
            throw new RuntimeException(
                    "Cannot access ShaderProgram method '" + name + Arrays.toString(parameterTypes) + "'", e);
        }
        return method;
    }
    
    private int getIntFieldValue(Field field) {
        try {
            return field.getInt(this.shaderProgram);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot get value of ShaderProgram field '" + field.getName() + "'",
                    e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access ShaderProgram field '" + field.getName() + "'", e);
        }
    }
    
    private void setShaderProgramFieldValue(Field field, Object value) {
        try {
            field.set(this.shaderProgram, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot set value of ShaderProgram field '" + field.getName() + "'",
                    e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access ShaderProgram field '" + field.getName() + "'", e);
        }
    }
    
    private Object invokeShaderProgramMethod(Method method, Object arg) {
        try {
            System.out.println("arg: " + arg);
            int parameterCount = method.getParameterCount();
            System.out.println("parameter count: " + parameterCount);
            return this.loadShaderMethod.invoke(this.shaderProgram, arg);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot invoke ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception in ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        }
    }
    
    private Object invokeShaderProgramMethod(Method method, Object arg0, Object arg1) {
        try {
            return this.loadShaderMethod.invoke(this.shaderProgram, arg0, arg1);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Cannot invoke ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception in ShaderProgram method '" + method.getName()
                    + Arrays.toString(method.getParameterTypes()) + "'", e);
        }
    }
}
