package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
import it.unibo.mvc.view.DrawNumberStandardOutputView;
import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws SecurityException security violations
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) 
    throws ClassNotFoundException, 
        NoSuchMethodException, 
        InstantiationException, 
        IllegalAccessException, 
        InvocationTargetException {

        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);

        final String viewClassNameCLI = "it.unibo.mvc.view.DrawNumberStandardOutputView";
        final String viewClassNameGUI = "it.unibo.mvc.view.DrawNumberSwingView";

        final Class<?> clsCLI = Class.forName(viewClassNameCLI);
        final Class<?> clsGUI = Class.forName(viewClassNameGUI);

        final Constructor<?> constructorCLI = clsCLI.getDeclaredConstructor(new Class[0]);
        final Constructor<?> constructorGUI = clsGUI.getDeclaredConstructor(new Class[0]);

        for (int i = 0; i < 3; i++) {
            app.addView((DrawNumberStandardOutputView) constructorCLI.newInstance(new Object[0]));
            app.addView((DrawNumberSwingView) constructorGUI.newInstance(new Object[0]));
        }

    }
}
