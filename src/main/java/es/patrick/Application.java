package es.patrick;

import es.patrick.ui.MainMenu;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Application {
    static void main() {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainMenu mainMenu = container.select(MainMenu.class).get();
            MainMenu.run();

        }
    }
}
