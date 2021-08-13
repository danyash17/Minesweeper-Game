package sample.sound;

import javafx.scene.media.MediaPlayer;
import sample.enums.Sound;
import sample.factory.SoundDispatcherFactory;

import java.util.Map;

public class SoundDispatcher {
    private Map<Sound, MediaPlayer> soundMap;
    private final SoundDispatcherFactory FACTORY;

    public SoundDispatcher(SoundDispatcherFactory factory) {
        this.FACTORY =factory;
        this.soundMap = factory.initDispatcher();
    }

    public Map<Sound, MediaPlayer> getSoundMap() {
        return soundMap;
    }

    public void setSoundMap(Map<Sound, MediaPlayer> soundMap) {
        this.soundMap = soundMap;
    }

    public void playSound(Sound sound) {
        MediaPlayer clip = soundMap.get(sound);
        clip.play();
    }
    public void setVolume(Sound sound,double value){
        MediaPlayer clip=soundMap.get(sound);
        clip.setVolume(value/100);
    }
}
