package polanieonline.common.listener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleDocumentListener implements DocumentListener {
	private final Runnable updateAction;

	public SimpleDocumentListener(Runnable updateAction) {
		this.updateAction = updateAction;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateAction.run();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateAction.run();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateAction.run();
	}
}
