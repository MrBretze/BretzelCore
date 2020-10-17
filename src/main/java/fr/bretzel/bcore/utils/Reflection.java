package fr.bretzel.bcore.utils;

import fr.bretzel.bcore.BCore;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Reflection
{

    public static Class<?> getClass(ClassType classType, String className)
    {
        try
        {
            return Class.forName(classType.getPackage() + className);
        } catch (ClassNotFoundException ex)
        {
            throw new RuntimeException("Cannot finding class: " + classType.getPackage() + className, ex);
        }
    }

    public static Field getField(Class<?> classes, String fieldName)
    {
        try
        {
            return classes.getField(fieldName);
        } catch (NoSuchFieldException e)
        {
            try
            {
                return classes.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored)
            {
                if (classes.getSuperclass() != Object.class)
                    return getField(classes.getSuperclass(), fieldName);
                return null;
            }
        }
    }

    protected static Method findMethod(Method[] methods, String methodName, Class<?>... argTypes)
    {
        if (argTypes == null)
            argTypes = new Class[0];

        Class<?>[] finalArgTypes = argTypes;

        return Stream.of(methods).filter(method ->
        {
            if (method.getName().equals(methodName) && method.getParameterTypes().length == finalArgTypes.length)
            {
                Class<?>[] methodsTypes = method.getParameterTypes();
                for (int i = 0; i < methodsTypes.length; i++)
                {
                    Class<?> methodClass = methodsTypes[i];
                    Class<?> wantedType = finalArgTypes[i];
                    if (methodClass.equals(wantedType) || methodClass.isAssignableFrom(wantedType))
                        return true;
                }
                return true;
            }
            return false;
        }).findFirst().orElseThrow(NoSuchMethodError::new);
    }

    public static Method getMethod(Class<?> classes, String methodName, Class<?>... argTypes)
    {
        return findMethod(classes.getMethods(), methodName, argTypes);
    }

    public static Method getDeclaredMethod(Class<?> classes, String methodName, Class<?>... argTypes)
    {
        return findMethod(classes.getDeclaredMethods(), methodName, argTypes);
    }

    protected static Constructor<?> findConstructor(Constructor<?>[] constructors, Class<?>... argTypes)
    {
        if (argTypes == null)
            argTypes = new Class[0];

        Class<?>[] finalArgTypes = argTypes;

        return Stream.of(constructors).filter(constructor ->
        {
            if (constructor.getParameterTypes().length == finalArgTypes.length)
            {
                Class<?>[] constructorsTypes = constructor.getParameterTypes();
                for (int i = 0; i < constructorsTypes.length; i++)
                {
                    Class<?> constructorType = constructorsTypes[i];
                    Class<?> wantedType = finalArgTypes[i];
                    if (constructorType.equals(wantedType) || constructorType.isAssignableFrom(wantedType))
                        return true;
                }
            }
            return false;
        }).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public static Constructor<?> getConstructor(Class<?> classes, Class<?>... argTypes)
    {
        return findConstructor(classes.getConstructors(), argTypes);
    }

    public static Constructor<?> getDeclaredConstructor(Class<?> classes, Class<?>... argTypes)
    {
        return findConstructor(classes.getDeclaredConstructors(), argTypes);
    }

    public static Object invoke(Method method, Object instance, Object... argTypes)
    {
        try
        {
            return method.invoke(instance, argTypes);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Constructor<?> constructor, Object... argTypes)
    {
        try
        {
            return constructor.newInstance(argTypes);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Object get(Field field, Object instance)
    {
        if (!field.isAccessible())
            field.setAccessible(true);
        try
        {
            return field.get(instance);
        } catch (IllegalAccessException e)
        {
            return null;
        }
    }

    public static Object getFieldIndex(int index, Object o)
    {
        try
        {
            return getAllField(o.getClass()).get(index).get(o);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return o;
        }
    }

    private static List<Field> getAllField(Class<?> clazz)
    {
        ArrayList<Field> arrayList = new ArrayList<>();

        arrayList.addAll(Arrays.asList(clazz.getFields()));
        arrayList.addAll(Arrays.asList(clazz.getDeclaredFields()));

        arrayList.forEach(field ->
        {
            if (!field.isAccessible())
                field.setAccessible(true);
        });

        return arrayList;
    }

    public enum ClassType
    {
        CRAFT_BUKKIT("org.bukkit.craftbukkit." + BCore.getVersion() + "."),
        CRAFT_BUKKIT_ENTITY(ClassType.CRAFT_BUKKIT, "entity."),
        CRAFT_BUKKIT_BLOCK(ClassType.CRAFT_BUKKIT, "block."),
        CRAFT_BUKKIT_UTIL(ClassType.CRAFT_BUKKIT, "util."),
        MINECRAFT_SERVER("net.minecraft.server." + BCore.getVersion() + ".");

        private final String dir;

        ClassType(ClassType classType, String dir)
        {
            this.dir = classType.dir + dir;
        }

        ClassType(String dir)
        {
            this.dir = dir;
        }

        public String getPackage()
        {
            return dir;
        }
    }
}
