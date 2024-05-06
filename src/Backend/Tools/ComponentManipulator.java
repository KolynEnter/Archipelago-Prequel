package Backend.Tools;

import javax.swing.*;
import java.awt.*;

public class ComponentManipulator {

    public static JComponent findJComponent(JComponent parent, String childName) {
        JComponent childPanel = null;
        for (Component component : parent.getComponents()) {
            if (component.getName() != null && component.getName().equals(childName)) {
                childPanel = (JComponent) component;
            }
        }

        if (childPanel == null) {
            System.out.println("Error, Child Panel ["+ childName +"] is Null");
        }
        return childPanel;
    }

    public static void removeComponent(JComponent parent, String childName) {
        for (Component component : parent.getComponents()) {
            if (component.getName() != null && component.getName().equals(childName)) {
                parent.remove(component);
                break;
            }
        }
    }

    public static void removeComponentsContainsGivenString(JComponent parent, String givenString) {
        for (Component component : parent.getComponents()) {
            if (component.getName() != null && component.getName().contains(givenString)) {
                parent.remove(component);
            }
        }
    }
}
