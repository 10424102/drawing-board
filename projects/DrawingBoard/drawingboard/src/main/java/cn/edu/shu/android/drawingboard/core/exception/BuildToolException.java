package cn.edu.shu.android.drawingboard.core.exception;

/**
 * Created by yy on 1/25/14.
 */
public class BuildToolException extends Exception{
    public BuildToolException(Throwable throwable) {
        super(throwable);
    }

    public BuildToolException(String detailMessage) {
        super(detailMessage);
    }
}
