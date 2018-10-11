package ie.moses.wraparoundtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;
import android.view.View;
import android.widget.TextView;

import ie.moses.caimito.draw.MeasurementUtils;
import ie.moses.caimito.text.TextUtils;

import static com.google.common.base.Preconditions.checkArgument;

public class WrapViewSpan implements LeadingMarginSpan.LeadingMarginSpan2 {

    private final Context _context;
    private final int _lineCount;
    private int _leadingMargin;
    private int _padding;

    public WrapViewSpan(View wrapeeView, TextView wrappingView) {
        this(wrapeeView, wrappingView, 0);
    }

    /**
     * @param padding Padding in DIP.
     */
    public WrapViewSpan(View wrapeeView, TextView wrappingView, @IntRange(from = 0) int padding) {
        _context = wrapeeView.getContext();
        setPadding(padding);

        int wrapeeHeight = wrapeeView.getHeight();
        float lineHeight = TextUtils.getLineHeight(wrappingView);

        int lineCnt = 0;
        float linesHeight = 0F;
        while ((linesHeight += lineHeight) <= wrapeeHeight) {
            lineCnt++;
        }

        _lineCount = lineCnt;
        _leadingMargin = wrapeeView.getWidth();
    }

    public void setPadding(@IntRange(from = 0) int paddingDp) {
        checkArgument(paddingDp >= 0, "padding cannot be negative");
        _padding = (int) MeasurementUtils.dpiToPixels(_context, paddingDp);
    }

    @Override
    public int getLeadingMarginLineCount() {
        return _lineCount;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (first) {
            return _leadingMargin + _padding;
        } else {
            return _padding;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline,
                                  int bottom, CharSequence text, int start, int end,
                                  boolean first, Layout layout) {

    }

}
