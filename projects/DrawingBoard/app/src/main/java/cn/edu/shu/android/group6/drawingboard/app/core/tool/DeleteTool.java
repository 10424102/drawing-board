package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.PaintCanvas;

/**
 * Created by yy on 3/1/14.
 */
public class DeleteTool implements Generable {
    private static final App app = App.getInstance();
    private static final PaintCanvas paintCanvas = app.getPaintCanvas();

    @Override
    public void generate() {
        List<CanvasElement> elements = paintCanvas.getSelectedElements();
        if (elements.isEmpty()) {
            paintCanvas.getArtwork().removeAllViews();
            paintCanvas.getArtwork().invalidate();
        } else {
            for (CanvasElement e : elements) {
                paintCanvas.getArtwork().removeView(e);
            }
        }
    }
}
