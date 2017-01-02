package org.ilintar.study.question;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

/**
 * Created by Konrad on 2017-01-02.
 */
public class MusicRadioQuestion extends RadioQuestion {

    // Do not use those questions. Does not work yet!

    public MusicRadioQuestion(Node renderedQuestion, String id, ToggleGroup group) {
        super(renderedQuestion, id, group);
    }

    String folderPath = "C://Users//Konrad//Desktop//STUDIA//Programowanie aplikacji//applications//ppaKasia//music//" ;
    // "ppaKasia" is a place where your git repository lives
    // Music files should be in mp3 format and songs should be named the same as corresponding question id (eg. '1.mp3')

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaPlayer mediaPlayer;

    public void runTrack(int i){
        String path =  folderPath + Integer.toString(i) + ".mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setMute(false);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        System.out.println(mediaPlayer);
    }

    public void terminateTrack(){
        mediaPlayer.setMute(true);
    }


}
