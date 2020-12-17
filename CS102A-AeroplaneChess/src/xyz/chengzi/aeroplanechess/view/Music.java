package xyz.chengzi.aeroplanechess.view;
import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
public class Music extends Frame {
    String name="飞行棋BGM.wav";//可以用来选择你想要播放的音乐
    File f = new File("D:\\Synapse2002\\AeroPlaneChess\\"+name); //放音乐文件
    URL url;
    URI uri;
    AudioClip clip;

    public Music(){
        try
        {
            uri=f.toURI();
            url = uri.toURL();
            clip = Applet.newAudioClip(url);
            clip.loop();//循环播放
            //clip.play();//播放
            //clip.stop();//停止播放
            System.out.println("音乐文件已经打开");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("播放错误！");
        }
    }
    public void stopMusic()//停止播放
    {
        clip.stop();
    }
    public void playMusic()//播放
    {
        clip.play();
    }
    public void loopMusic()//循环播放
    {
        clip.loop();
    }
}