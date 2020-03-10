package com.tramp.word.port;

/**
 * Created by Administrator on 2019/1/9.
 */

public interface MainAnimInterFace  {
    void StartAnim();
    void StopAnim();
    void ShowAnimLayout();
    void HideAnimLayout();
    void ShowTaskLayout();
    void HideTaskLayout();
    void ShowMusicDownLayout();
    void HideMusicDownLayout();
    void ShowSignLayout(int status);
    void HideSignLayout();
}
