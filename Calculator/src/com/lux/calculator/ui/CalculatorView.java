package com.lux.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.lux.calculator.logic.Controller;
import com.lux.calculator.model.Model;

public class CalculatorView {
	private static final int MIN_WINDOW_HEIGHT = 200;
	private static final int MIN_WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 300;
	private static final int WINDOW_WIDTH = 500;
	private static final String CALC = "Calculator";
	private static final String HISTORY = "History";
	private static final String INCORECT_INSERTION = "Incorect insertion";
	private static final String DIVIDING_BY_ZERO = "Dividing by zero";

	private Display display;
	private Shell shell;
	private TabFolder tabFolder;

	private CalculatorPanel calculatorPanel;
	private HistoryPanel historyPanel;

	private void initUICalculator() {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		Composite calcCompoiste = new Composite(tabFolder, SWT.NONE);
		tabItem.setText(CALC);
		tabItem.setControl(calcCompoiste);
		calculatorPanel = new CalculatorPanel(calcCompoiste, this);
	}

	private void initHistory() {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		Composite historyComposite = new Composite(tabFolder, SWT.NONE);
		tabItem.setText(HISTORY);
		tabItem.setControl(historyComposite);
		historyPanel = new HistoryPanel(historyComposite);
	}

	private void openWindow() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void setResult(Model model) {
		String arg1 = model.getArg1();
		String arg2 = model.getArg2();
		String actionTitle = model.getActionTitle();
		
		Controller controller = new Controller();

		String errorMessage = controller.isValid(arg1, arg2);
		if (checkMessage(model, INCORECT_INSERTION, errorMessage)) {
			return;
		}
		
		errorMessage = controller.checkDividingByZero(arg2, actionTitle);
		if (checkMessage(model, DIVIDING_BY_ZERO, errorMessage)) {
			return;
		}
		
		String value = controller.callCalculate(arg1, arg2, actionTitle);
		calculatorPanel.setResultValue(value);
		historyPanel.addTableItem(new String[] { arg1, actionTitle, arg2, value });
	}

	private boolean checkMessage(Model model, String title, String errorMessage) {
		if (!errorMessage.isEmpty()) {
			showErrorMessage(errorMessage, title, model.isOnFly());
			return true;
		}
		return false;
	}

	private void showErrorMessage(String invalidvariable, String title, boolean onFly) {
		if (!onFly) {
			MessageBox dialog = new MessageBox(shell, SWT.ERROR | SWT.OK);
			dialog.setText(title);
			dialog.setMessage(invalidvariable);
			dialog.open();
		} else {
			calculatorPanel.setResultValue(invalidvariable);
		}
	}

	public void open() {
		initShell();

		tabFolder = new TabFolder(shell, SWT.NONE);

		initUICalculator();
		initHistory();
		openWindow();
	}

	private void initShell() {
		display = new Display();
		shell = new Shell(display);
		shell.setText(CALC);
		shell.setMinimumSize(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
		shell.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		shell.setLayout(new FillLayout());
	}
}