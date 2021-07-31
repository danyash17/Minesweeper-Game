package sample.factory;

import java.io.File;

import javafx.scene.media.AudioClip;
import sample.MainApplication;
import sample.enums.Sound;
import sample.field.Field;

import java.net.MalformedURLException;
import java.util.HashMap;

public class SoundDispatcherFactory {
    public SoundDispatcherFactory() {
    }

    public HashMap<Sound, AudioClip> initDispatcher() {
        HashMap<Sound, AudioClip> map = new HashMap<>();
        try {
            map.put(Sound.OPEN,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/open.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.FLAG,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/flag.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.DETONATE,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/detonate.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.SUCCESS,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/success.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.NOVICE,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/novice.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.SOLIDER,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/solider.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.COMMANDER,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/commander.mp3").toURI().toURL().toExternalForm()));
            map.put(Sound.DOOMSLAYER,new AudioClip(new File("C:/Users/Даниил/IdeaProjects/Minesweeper/src/resources/sounds/doomslayer.mp3").toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return map;
    }
    public void calibrate(HashMap<Sound,AudioClip> map){
        map.get(Sound.OPEN).setVolume(1);
        map.get(Sound.FLAG).setVolume(1);
        map.get(Sound.DETONATE).setVolume(1);
        map.get(Sound.SUCCESS).setVolume(1);
        map.get(Sound.NOVICE).setVolume(1);
        map.get(Sound.SOLIDER).setVolume(1);
        map.get(Sound.COMMANDER).setVolume(1);
        map.get(Sound.DOOMSLAYER).setVolume(1);
    }
}
