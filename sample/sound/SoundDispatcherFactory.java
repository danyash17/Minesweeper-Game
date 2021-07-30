package sample.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import javafx.scene.media.AudioClip;
import sample.enums.Difficulty;
import sample.enums.Sound;

import java.io.File;
import java.util.HashMap;

public class SoundDispatcherFactory {
    public SoundDispatcherFactory() {
    }

    public HashMap<Sound, AudioClip> initDispatcher() {
        HashMap<Sound, AudioClip> map = new HashMap<>();
        map.put(Sound.MENUMUSIC,new AudioClip(new File("src/resources/sounds/bitsurf.mp3").toURI().toString()));
        map.put(Sound.OPEN,new AudioClip(new File("src/resources/sounds/open.mp3").toURI().toString()));
        map.put(Sound.FLAG,new AudioClip(new File("src/resources/sounds/flag.mp3").toURI().toString()));
        map.put(Sound.DETONATE,new AudioClip(new File("src/resources/sounds/detonate.mp3").toURI().toString()));
        map.put(Sound.NOVICE,new AudioClip(new File("src/resources/sounds/novice.mp3").toURI().toString()));
        map.put(Sound.SOLIDER,new AudioClip(new File("src/resources/sounds/solider.mp3").toURI().toString()));
        map.put(Sound.COMMANDER,new AudioClip(new File("src/resources/sounds/commander.mp3").toURI().toString()));
        map.put(Sound.DOOMSLAYER,new AudioClip(new File("src/resources/sounds/doomslayer.mp3").toURI().toString()));
        return map;
    }
    public void calibrate(HashMap<Sound,AudioClip> map){
        map.get(Sound.MENUMUSIC).setVolume(0.5);
        map.get(Sound.OPEN).setVolume(1);
        map.get(Sound.FLAG).setVolume(1);
        map.get(Sound.DETONATE).setVolume(1);
        map.get(Sound.NOVICE).setVolume(1);
        map.get(Sound.SOLIDER).setVolume(1);
        map.get(Sound.COMMANDER).setVolume(1);
        map.get(Sound.DOOMSLAYER).setVolume(1);
    }
}
