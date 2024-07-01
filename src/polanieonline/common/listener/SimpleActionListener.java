package polanieonline.common.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleActionListener implements ActionListener {
	private final Runnable firstAction;
	private final Runnable secondAction;

	public SimpleActionListener(Runnable firstAction) {
		this(firstAction, null);
	}

	public SimpleActionListener(Runnable firstAction, Runnable secondAction) {
		this.firstAction = firstAction;
		this.secondAction = secondAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (firstAction != null) {
			firstAction.run();
		}
		if (secondAction != null) {
			secondAction.run();
		}
	}
}
