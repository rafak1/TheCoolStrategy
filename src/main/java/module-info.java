module project {
    requires javafx.controls;
    requires javafx.media;
    requires java.prefs;
    exports project;
    exports project.gameObjects.Enemies;
    exports project.Levels;
    exports project.gameObjects.Turrets;
}