package com.energicube.eno.monitor.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetvideoSystemConfig implements Serializable {

    /**
     * 存放系统配置数据
     */
    private boolean UseCamereList;//是否使用摄像机列表
    private int PTZspeed;//云台速度,0默认
    private String Windowtitle;//窗口标题
    private boolean stickied;//是否置顶
    private int toolBarShow;//工具条展示
    private int controlKeyboard;//控制键盘展示
    private int openShow;//小牛启动后展示。。
    private int openCamera;//启动后显示摄像机，摄像机编号
    private int openRotation;//启动后显示轮显组，轮显组编号
    private int left;//左
    private int top;//上
    private int right;//右
    private int bottom;//下
    private boolean hiddenTitle;//是否隐藏标题
    private boolean hiddenMenu;//是否隐藏菜单
    private boolean hiddenStatusBar;//是否隐藏状态条
    private boolean controlKeyboardAutohide;//是否自动隐藏控制键盘

    public boolean isUseCamereList() {
        return UseCamereList;
    }

    public void setUseCamereList(boolean useCamereList) {
        UseCamereList = useCamereList;
    }

    public int getPTZspeed() {
        return PTZspeed;
    }

    public void setPTZspeed(int pTZspeed) {
        PTZspeed = pTZspeed;
    }

    public String getWindowtitle() {
        return Windowtitle;
    }

    public void setWindowtitle(String windowtitle) {
        Windowtitle = windowtitle;
    }

    public boolean isStickied() {
        return stickied;
    }

    public void setStickied(boolean stickied) {
        this.stickied = stickied;
    }

    public int getToolBarShow() {
        return toolBarShow;
    }

    public void setToolBarShow(int toolBarShow) {
        this.toolBarShow = toolBarShow;
    }

    public int getControlKeyboard() {
        return controlKeyboard;
    }

    public void setControlKeyboard(int controlKeyboard) {
        this.controlKeyboard = controlKeyboard;
    }

    public int getOpenShow() {
        return openShow;
    }

    public void setOpenShow(int openShow) {
        this.openShow = openShow;
    }

    public int getOpenCamera() {
        return openCamera;
    }

    public void setOpenCamera(int openCamera) {
        this.openCamera = openCamera;
    }

    public int getOpenRotation() {
        return openRotation;
    }

    public void setOpenRotation(int openRotation) {
        this.openRotation = openRotation;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public boolean isHiddenTitle() {
        return hiddenTitle;
    }

    public void setHiddenTitle(boolean hiddenTitle) {
        this.hiddenTitle = hiddenTitle;
    }

    public boolean isHiddenMenu() {
        return hiddenMenu;
    }

    public void setHiddenMenu(boolean hiddenMenu) {
        this.hiddenMenu = hiddenMenu;
    }

    public boolean isHiddenStatusBar() {
        return hiddenStatusBar;
    }

    public void setHiddenStatusBar(boolean hiddenStatusBar) {
        this.hiddenStatusBar = hiddenStatusBar;
    }

    public boolean isControlKeyboardAutohide() {
        return controlKeyboardAutohide;
    }

    public void setControlKeyboardAutohide(boolean controlKeyboardAutohide) {
        this.controlKeyboardAutohide = controlKeyboardAutohide;
    }

    public NetvideoSystemConfig() {
        super();
    }

    public NetvideoSystemConfig(boolean useCamereList,
                                int pTZspeed, String windowtitle, boolean stickied,
                                int toolBarShow, int controlKeyboard, int openShow,
                                int openCamera, int openRotation, int left, int top,
                                int right, int bottom, boolean hiddenTitle, boolean hiddenMenu,
                                boolean hiddenStatusBar, boolean controlKeyboardAutohide) {
        super();
        UseCamereList = useCamereList;
        PTZspeed = pTZspeed;
        Windowtitle = windowtitle;
        this.stickied = stickied;
        this.toolBarShow = toolBarShow;
        this.controlKeyboard = controlKeyboard;
        this.openShow = openShow;
        this.openCamera = openCamera;
        this.openRotation = openRotation;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.hiddenTitle = hiddenTitle;
        this.hiddenMenu = hiddenMenu;
        this.hiddenStatusBar = hiddenStatusBar;
        this.controlKeyboardAutohide = controlKeyboardAutohide;
    }

}
