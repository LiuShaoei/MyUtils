package xst.app.com.myutils.gesture;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


/**
 * Created by LiuZhaowei on 2018/10/25 0025.
 */

public class GesturePoint {

    //*/ 手势密码点的状态
    public static final int POINT_STATE_NORMAL = 0; // 正常状态

    public static final int POINT_STATE_SELECTED = 1; // 按下状态

    public static final int POINT_STATE_WRONG = 2; // 错误状态



    public GesturePoint(int leftX, int rightX, int topY, int bottomY, int num,ImageView image,
                        Drawable pointNormal,Drawable pointSelected,Drawable pointWrong) {
        super();
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
        this.image = image;
        this.centerX = (leftX + rightX) / 2;
        this.centerY = (topY + bottomY) / 2;
        this.num = num;
        this.pointNormal = pointNormal;
        this.pointSelected = pointSelected;
        this.pointWrong = pointWrong;
    }

    /**
     * 左边的值
     */
    private int leftX;
    /**
     * 右边的值
     */
    private int rightX;
    /**
     * 上边的y值
     */
    private int topY;


    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getPointState() {
        return pointState;
    }

    public void setPointState(int pointState) {
        pointState = pointState;
        switch (pointState){
            case POINT_STATE_NORMAL:
                this.image.setImageDrawable(pointNormal);
                break;
            case POINT_STATE_SELECTED:
                this.image.setImageDrawable(pointSelected);
                break;
            case POINT_STATE_WRONG:
                this.image.setImageDrawable(pointWrong);
                break;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getBottomY() {

        return bottomY;
    }

    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }

    /**
     * 下边的y值
     */
    private int bottomY;
    /**
     * 这个点对应的ImageView控件
     */
    private ImageView image;
    /**
     * 中心的y值
     */
    private int centerY;
    /**
     * 中心的x值
     */
    private int centerX;
    /**
     * 状态值
     */
    private int pointState;
    /**
     * 选中的图片
     */
    private Drawable pointSelected;
    /**
     * 正常的图片
     */
    private Drawable pointNormal;
    /**
     * 错误的图片
     */
    private Drawable pointWrong;

    /**
     * 代表这个point对象代表的数字,从1开始
     */
    private int num;


 @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        GesturePoint point = (GesturePoint) o;
        if (bottomY != point.bottomY) return false;
        if (image == null) {
            if (point.image != null) return false;
        } else if (!image.equals(point.image)) return false;

        if (leftX != point.leftX) return false;
        if (rightX != point.rightX) return false;
        if (topY != point.topY) return false;
        return true;
    }
}

