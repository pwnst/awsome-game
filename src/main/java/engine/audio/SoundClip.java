package engine.audio;

import lombok.Data;
import lombok.SneakyThrows;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

@Data
public class SoundClip {

    private Clip clip;

    private FloatControl gainControl;

    @SneakyThrows
    public SoundClip(String path) {
        InputStream resourceAsStream = new BufferedInputStream(SoundClip.class.getResourceAsStream(path));
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resourceAsStream);
        AudioFormat baseFormat = audioInputStream.getFormat();
        AudioFormat decodeFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
        );

        AudioInputStream decodeAudioInputStream = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);

        clip = AudioSystem.getClip();
        clip.open(decodeAudioInputStream);

        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    public void play() {
        if (clip == null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        while (!clip.isRunning()) {
            clip.start();
        }
    }

    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(float value) {
        gainControl.setValue(value);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }
}
