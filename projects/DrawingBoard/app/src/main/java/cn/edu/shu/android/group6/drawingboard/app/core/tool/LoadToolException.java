package cn.edu.shu.android.group6.drawingboard.app.core.tool;

/**
 * Created by yy on 3/1/14.
 */
public class LoadToolException extends Exception {
    public LoadToolException() {
        super();
    }

    public LoadToolException(String detailMessage) {
        super(detailMessage);
    }

    public LoadToolException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public LoadToolException(Throwable throwable) {
        super(throwable);
    }
}
