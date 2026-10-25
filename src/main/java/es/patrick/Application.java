package es.patrick;

import es.patrick.ui.MainMenu;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Application {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainMenu mainMenu = container.select(MainMenu.class).get();
            mainMenu.run();
        }
    }
}
