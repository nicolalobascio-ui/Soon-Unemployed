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
        this(Path.of("savegame.txt"));
    }

    public GamePersistenceService(Path saveFile) {
        this.saveFile = saveFile;
    }

    @Override
    public void save(GameState state) {
        GameStateSnapshot snapshot = GameStateSnapshot.from(state);
        try {
            Files.writeString(saveFile, snapshot.toFileFormat(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to save game", exception);
        }
    }

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