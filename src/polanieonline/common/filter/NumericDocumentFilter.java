package polanieonline.common.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {
	private final int MAX_VALUE = 32767;

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
		if (isNumeric(newText)) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
		newText = newText.substring(0, offset) + text + newText.substring(offset + length);
		if (isNumeric(newText)) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		super.remove(fb, offset, length);
	}

	private boolean isNumeric(String text) {
		if (text == null || text.isEmpty()) {
			return false;
		}
		try {
			int value = Integer.parseInt(text);
			return value >= 1 && value <= MAX_VALUE;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
