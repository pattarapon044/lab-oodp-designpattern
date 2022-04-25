package lab.oodp.test;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.UnmappableCharacterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestReflectionUtils {

    public static <T> T instantiate(String className, Object... args) {

        try {
            Class<?> clazz = Class.forName(className);

//            Constructor<?> ctor = clazz.getConstructor(getParameterTypes(args));
            Constructor<?> ctor = getAppropriateConstructor(clazz, args);

            return (T) ctor.newInstance(args);

        } catch (ClassNotFoundException e) {
            Assertions.fail("There is no class named " + className);

        } catch (NoSuchMethodException e) {
            Assertions.fail("Class " + className + " does not have an appropriate constructor. " +
                    "Requires " + className + "(" + Arrays.toString(getParameterTypeNames(args)) + ")");

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Assertions.fail("Unexpected error when instantiating class " + className, e);

        }
        return null;
    }

    public static <T> T callMethod(Object obj, String methodName, Object... args) {

        Class<?> clazz = obj.getClass();

        try {
            Method method = clazz.getMethod(methodName, getParameterTypes(args));
//            Method method = getAppropriateMethod(clazz, methodName, args);
            return (T) method.invoke(obj, args);

        } catch (NoSuchMethodException e) {
            Assertions.fail("Class " + clazz.getSimpleName() + " does not have an appropriate method. " +
                    "Requires " + methodName + "(" + Arrays.toString(getParameterTypeNames(args)) + ")");

        } catch (ClassCastException e) {
            Assertions.fail("When calling " + obj.getClass().getSimpleName() + "." + methodName + ", result was the wrong type!");

        } catch (IllegalAccessException | InvocationTargetException e) {
            Assertions.fail("Unexpected error when calling " + obj.getClass().getSimpleName() + "." + methodName, e);
        }

        return null;
    }

    private static Class<?>[] getParameterTypes(Object... args) {
        List<Class<?>> parameterTypes = new ArrayList<>();
        Arrays.stream(args).map(Object::getClass).forEach(parameterTypes::add);
        return parameterTypes.toArray(new Class[args.length]);
    }

    private static String[] getParameterTypeNames(Object... args) {
        List<String> paramTypeNames = new ArrayList<>();
        Class<?>[] paramTypes = getParameterTypes(args);
        Arrays.stream(paramTypes).map(Class::getSimpleName).forEach(paramTypeNames::add);
        return paramTypeNames.toArray(new String[args.length]);
    }

    private static Constructor<?> getAppropriateConstructor(Class<?> c, Object[] initArgs) throws NoSuchMethodException {
        if (initArgs == null)
            initArgs = new Object[0];
        for (Constructor<?> con : c.getDeclaredConstructors()) {
            Class<?>[] types = con.getParameterTypes();
            if (types.length != initArgs.length)
                continue;
            boolean match = true;
            for (int i = 0; i < types.length; i++) {
                Class<?> need = types[i], got = initArgs[i].getClass();
                if (!need.isAssignableFrom(got)) {
                    if (need.isPrimitive()) {
                        match = (int.class.equals(need) && Integer.class.equals(got))
                                || (long.class.equals(need) && Long.class.equals(got))
                                || (char.class.equals(need) && Character.class.equals(got))
                                || (short.class.equals(need) && Short.class.equals(got))
                                || (boolean.class.equals(need) && Boolean.class.equals(got))
                                || (byte.class.equals(need) && Byte.class.equals(got));
                    } else {
                        match = false;
                    }
                }
                if (!match)
                    break;
            }
            if (match)
                return con;
        }

        throw new NoSuchMethodException();
    }

    private static Method getAppropriateMethod(Class<?> c, String methodName, Object[] args) throws NoSuchMethodException {
        if (args == null)
            args = new Object[0];

        for (Method mthd : c.getMethods()) {

            if (!mthd.getName().equals(methodName))
                continue;

            Class<?>[] types = mthd.getParameterTypes();
            if (types.length != args.length)
                continue;

            boolean match = true;
            for (int i = 0; i < types.length; i++) {
                Class<?> need = types[i], got = args[i].getClass();
                if (!need.isAssignableFrom(got)) {
                    if (need.isPrimitive()) {
                        match = (int.class.equals(need) && Integer.class.equals(got))
                                || (long.class.equals(need) && Long.class.equals(got))
                                || (char.class.equals(need) && Character.class.equals(got))
                                || (short.class.equals(need) && Short.class.equals(got))
                                || (boolean.class.equals(need) && Boolean.class.equals(got))
                                || (byte.class.equals(need) && Byte.class.equals(got));
                    } else {
                        match = false;
                    }
                }
                if (!match)
                    break;
            }
            if (match)
                return mthd;
        }

        throw new NoSuchMethodException();
    }

}
