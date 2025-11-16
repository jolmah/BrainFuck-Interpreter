package ng.jolmah.bfi;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class BrainfuckSyntaxHighlighter implements TextWatcher {

    private final EditText editText;
    private boolean internalChange = false;
    private final Map<Character, Integer> bfColors = new HashMap<>();

    public BrainfuckSyntaxHighlighter(EditText editText) {
        this.editText = editText;
        initColors();
    }

    private void initColors() {
        bfColors.put('>', Color.parseColor("#FF9800")); // orange
        bfColors.put('<', Color.parseColor("#FF9800"));
        bfColors.put('+', Color.parseColor("#4CAF50")); // green
        bfColors.put('-', Color.parseColor("#4CAF50"));
        bfColors.put('[', Color.parseColor("#42A5F5")); // blue
        bfColors.put(']', Color.parseColor("#42A5F5"));
        bfColors.put('.', Color.parseColor("#E91E63")); // pink
        bfColors.put(',', Color.parseColor("#9C27B0")); // purple
    }

    public void attach() {
        editText.addTextChangedListener(this);
        highlightAll(editText.getText());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (internalChange) return;
        highlightAll(s);
    }

    private void highlightAll(Editable s) {
        internalChange = true;

        // remove previous color spans
        ForegroundColorSpan[] oldSpans = s.getSpans(0, s.length(), ForegroundColorSpan.class);
        for (ForegroundColorSpan span : oldSpans) {
            s.removeSpan(span);
        }

        // apply new spans only for BF characters
        for (int i = 0; i < s.length(); i++) {
            Integer color = bfColors.get(s.charAt(i));
            if (color != null) {
                s.setSpan(new ForegroundColorSpan(color), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        internalChange = false;
    }
}
