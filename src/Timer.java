import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Timer {
    AudioPlayer audioPlayer;
    private final static int PLAY_SOUND_ONCE_TIME = 1000;
    private final static int PLAY_SOUND_TWICE_TIME = 2 * PLAY_SOUND_ONCE_TIME + 500;


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

    public void executeExercise(int[] input) {
        executeExercise(input[0], input[1], input[2], input[3], input[4]);
    }

    public void executeExercise(int exerciseTime, int breakTime, int roundsBeforeLargerBreak,
                                int largeBreakTime, int totalRounds) {
        try {
            UI.popUp("Timer will start 5 seconds after dismissing this window", "");
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < totalRounds; i++) {
            try {
                playSoundTwice();
                if (i % roundsBeforeLargerBreak == 0 && i > 0) {
                    Thread.sleep((largeBreakTime - breakTime) * 1000L - PLAY_SOUND_TWICE_TIME);
                    playSoundTwice();
                }
                Thread.sleep(exerciseTime * 500L - PLAY_SOUND_TWICE_TIME);
                playSoundOnce();
                Thread.sleep(exerciseTime * 500L - PLAY_SOUND_ONCE_TIME);
                playSoundTwice();
                Thread.sleep(breakTime * 1000L - PLAY_SOUND_TWICE_TIME);

            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

    }

    private void playSoundOnce() {
        try {
            audioPlayer.playAndReset(1);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
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
            return;
        }
    }
}
