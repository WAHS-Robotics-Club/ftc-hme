package org.firstinspires.ftc.team9202hme;


import android.media.MediaPlayer;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * Manages playback of audio from a file.
 *
 * Simply place an audio file in Team9202HME/res/raw,
 * and then use R.raw.{file_name} as the Android resource ID.
 * Be sure to import the correct R class, there are multiple.
 *
 * @author Nathaniel Glover
 */
public class Sound {
    private MediaPlayer mediaPlayer;
    private Thread soundThread;
    private boolean isLoaded = false;

    /**
     * Loads the audio data from a file, accessed through an Android resource ID.
     * This function must be called before any other, and is usually done so in
     * {@link TeleOpProgram#init()} or in the beginning of {@link AutonomousProgram#run()}
     *
     * @param hardwareMap Is used to get a handle to the FTC Robot Controller app, so sound can
     *                    be played through it
     * @param resourceId The Android resource ID for the audio file to be played
     */
    public void load(HardwareMap hardwareMap, int resourceId) {
        mediaPlayer = MediaPlayer.create(hardwareMap.appContext, resourceId);

        soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(mediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        isLoaded = true;
    }

    /**
     * Plays the sound on a separate thread
     */
    public void play() {
        mediaPlayer.start();

        soundThread.start();
    }

    /**
     * Tells whether the sound is currently playing. Note that this
     * function's output may not update immediately after the sound has
     * stopped playing, but it probably will (according to Android documentation
     * anyways)
     *
     * @return Whether or not the sound is currently playing
     */
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /**
     * Enables or disables looping
     *
     * @param looping Whether or not to loop the sound
     */
    public void setLooping(boolean looping) {
        mediaPlayer.setLooping(looping);
    }

    /**
     * Checks if looping is enabled
     *
     * @return Whether or not looping is enabled
     */
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    /**
     * Sets the volume of the sound for both left and right speakers, if possible
     * @param leftVolume The left volume, from 0.0 to 1.0
     * @param rightVolume The right volume, from 0.0 to 1.0
     */
    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    /**
     * Stops playing the sound until {@link Sound#play()} is called again
     *
     * @throws InterruptedException
     */
    public void pause() throws InterruptedException {
        mediaPlayer.pause();

        soundThread.join();
    }

    /**
     * Completely stops the sound. Calling {@link Sound#play()} will restart the sound
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        mediaPlayer.stop();

        soundThread.join();
    }

    /**
     * Checks whether or not the sound has been properly loaded and initialized
     *
     * @return Whether or not the sound has been loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }
}
