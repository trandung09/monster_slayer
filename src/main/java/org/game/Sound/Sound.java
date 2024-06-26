package org.game.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.net.URL;

public class Sound {

    public Clip clip;
    public URL[] soundURL = new URL[30]; // or file path

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/Dungeon.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/levelup.wav");
        soundURL[8] = getClass().getResource("/sound/tape-machine-button-press-metal-84484.wav");
        soundURL[9] = getClass().getResource("/sound/gameover.wav");
        soundURL[10] = getClass().getResource("/sound/blocked.wav");
        soundURL[11] = getClass().getResource("/sound/parry.wav");
    }

    public void setFile(int index) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() { if (clip != null) clip.start(); }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() { 
        if (clip.isRunning()) {
            clip.stop();
        }
    }
}
