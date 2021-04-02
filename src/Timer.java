import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Timer {
    AudioPlayer audioPlayer;

    public Timer(File audioFile) {
        try {
            audioPlayer = new AudioPlayer(audioFile);
        }
        catch (IOException e) {
            System.err.println("Audio file reading failed");
            e.printStackTrace();
        }
        catch (UnsupportedAudioFileException e) {
            System.err.println("Audio File not supported (This should never ever happen)");
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void executeExercise(int exerciseTime, int breakTime, int roundsBeforeLargerBreak,
                                int largeBreakTime, int totalRounds) {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        playSoundTwice();
        for (int i = 0; i < totalRounds; i++) {
            try {
                if (i % roundsBeforeLargerBreak == 0 && i > 0) {
                    Thread.sleep(largeBreakTime * 1000L);
                }
                Thread.sleep(exerciseTime * 500L);
                playSoundOnce();
                Thread.sleep(exerciseTime * 500L);
                playSoundTwice();
                Thread.sleep(breakTime * 1000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void playSoundOnce() {
        try {
            audioPlayer.playAndReset(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playSoundTwice() {
        try {
            audioPlayer.playAndReset(1);
            sleep(500);
            audioPlayer.playAndReset(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
