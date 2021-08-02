package sample.factory;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.enums.Sound;
import sample.path.SoundPath;

import java.util.HashMap;

public class SoundDispatcherFactory {
    public SoundDispatcherFactory() {
    }

    public HashMap<Sound, MediaPlayer> initDispatcher() {
        HashMap<Sound, MediaPlayer> map = new HashMap<>();
        map.put(Sound.OPEN,new MediaPlayer(new Media(getClass().getResource(SoundPath.OPEN).toExternalForm())));
        map.put(Sound.FLAG,new MediaPlayer(new Media(getClass().getResource(SoundPath.FLAG).toExternalForm())));
        map.put(Sound.DETONATE,new MediaPlayer(new Media(getClass().getResource(SoundPath.DETONATE).toExternalForm())));
        map.put(Sound.SUCCESS,new MediaPlayer(new Media(getClass().getResource(SoundPath.SUCCESS).toExternalForm())));
        map.put(Sound.NOVICE,new MediaPlayer(new Media(getClass().getResource(SoundPath.NOVICE).toExternalForm())));
        map.put(Sound.SOLIDER,new MediaPlayer(new Media(getClass().getResource(SoundPath.SOLIDER).toExternalForm())));
        map.put(Sound.COMMANDER,new MediaPlayer(new Media(getClass().getResource(SoundPath.COMMANDER).toExternalForm())));
        map.put(Sound.DOOMSLAYER,new MediaPlayer(new Media(getClass().getResource(SoundPath.DOOMSLAYER).toExternalForm())));
        calibrate(map);
        return map;
    }
    public void calibrate(HashMap<Sound,MediaPlayer> map){
        map.get(Sound.OPEN).setVolume(0.75);
        map.get(Sound.FLAG).setVolume(0.75);
        map.get(Sound.DETONATE).setVolume(1);
        map.get(Sound.SUCCESS).setVolume(0.8);
        map.get(Sound.NOVICE).setVolume(0.8);
        map.get(Sound.SOLIDER).setVolume(0.8);
        map.get(Sound.COMMANDER).setVolume(0.8);
        map.get(Sound.DOOMSLAYER).setVolume(0.8);
    }
}
