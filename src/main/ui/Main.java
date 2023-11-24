package ui;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ModifierManagerApp app = new ModifierManagerApp();
                new ModifierManagerGUI(app);
            }
        });
    }
}

