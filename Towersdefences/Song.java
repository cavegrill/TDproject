package Towersdefences;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import java.io.File;
import java.util.Random;

public class Song {
    private Clip song;
    private String[] songFiles;
    private Random random;

    public Song(String[] songFiles) {
        this.songFiles = songFiles;
        this.random = new Random();
        selectRandomSong();
    }

    void selectRandomSong() {
        int randomIndex = random.nextInt(songFiles.length);
        String selectedSongFile = songFiles[randomIndex];
        loadSong(selectedSongFile);
    }

    private void loadSong(String songFile) {
        try {
            File audioFile = new File(songFile);
            AudioInputStream songInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat audioFormat = songInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            song = (Clip) AudioSystem.getLine(info);
            song.open(songInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSong() {
        if (song != null) {
            if (song.isRunning())
                song.stop();
            song.setFramePosition(0);
            song.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
     
   
    }
