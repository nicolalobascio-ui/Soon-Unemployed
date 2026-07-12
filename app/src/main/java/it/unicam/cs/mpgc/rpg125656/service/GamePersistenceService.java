package it.unicam.cs.mpgc.rpg125656.service;

import it.unicam.cs.mpgc.rpg125656.entity.GameState;
import it.unicam.cs.mpgc.rpg125656.persistence.GameStateSnapshot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class GamePersistenceService implements GamePersistencePort {

    private final Path saveFile;

    public GamePersistenceService() {
        this(defaultSaveFile());
    }

    public GamePersistenceService(Path saveFile) {
        this.saveFile = saveFile;
    }

    private static Path defaultSaveFile() {
        return Path.of(System.getProperty("user.home"), ".soon-unemployed", "savegame.txt");
    }

    @Override
    public void save(GameState state) {
        GameStateSnapshot snapshot = GameStateSnapshot.from(state);
        try {
            Files.createDirectories(saveFile.getParent());
            Files.writeString(saveFile, snapshot.toFileFormat(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to save game", exception);
        }
    }

    /**
     * Reads the save file and rebuilds the game state.
     * Returns null if the file is missing or empty, not if it is corrupted.
     */
    @Override
    public GameState load() {
        if (!Files.exists(saveFile)) {
            return null;
        }

        try {
            String line = Files.readString(saveFile, StandardCharsets.UTF_8).trim();
            if (line.isEmpty()) {
                return null;
            }

            GameStateSnapshot snapshot = GameStateSnapshot.fromFileFormat(line);
            return snapshot.toGameState();
        } catch (IOException exception) {
            throw new RuntimeException("Unable to load game", exception);
        }
    }

    @Override
    public void deleteSave() {
        try {
            Files.deleteIfExists(saveFile);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to delete save", exception);
        }
    }

    @Override
    public boolean hasSave() {
        return Files.exists(saveFile);
    }
}