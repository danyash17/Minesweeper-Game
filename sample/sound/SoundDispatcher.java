package sample.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import sample.enums.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundDispatcher {
    private Map<Sound, AudioClip> soundMap;
    private SoundDispatcherFactory factory;

    public SoundDispatcher(SoundDispatcherFactory factory) {
        this.factory=factory;
        this.soundMap = factory.initDispatcher();
    }

    public Map<Sound, AudioClip> getSoundMap() {
        return soundMap;
    }

    public void setSoundMap(Map<Sound, AudioClip> soundMap) {
        this.soundMap = soundMap;
    }

    public void playSound(Sound sound) {
        AudioClip clip = soundMap.get(sound);
        clip.play();
    }
    public void setVolume(Sound sound,double value){
        AudioClip clip=soundMap.get(sound);
        clip.setVolume(value/100);
    }
}
