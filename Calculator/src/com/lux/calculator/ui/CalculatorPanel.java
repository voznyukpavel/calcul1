package com.lux.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.lux.calculator.logic.Action;
import com.lux.calculator.model.Model;

public class CalculatorPanel {
	
	private static final String CALCULATE = "Calculate";
	private static final String CALCULATE_ON_THE_FLY = "calculate on the fly";
	private static final String RESULT = "Result:";

	private CalculatorView calculatorView;
	private Text textArg1, textArg2;
	private Combo combo;
	private Button checkbox;
	private Button calculateButton;
	private StyledText resultText;

	public CalculatorPanel(Composite parent, CalculatorView calculatorView) {
		parent.setLayout(new GridLayout(3, false));
		this.calculatorView = calculatorView;
		initUI(parent);
		initListeners();
	}

	private void initUI(Composite parent) {
		GridData textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);

		textArg1 = new Text(parent, SWT.BORDER | SWT.RIGHT);
		textArg1.setLayoutData(textGridData);

		combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(new String[] { Action.ADD.getTitle(), Action.SUB.getTitle(), Action.MULT.getTitle(),
				Action.DIV.getTitle() });
		combo.setText(Action.ADD.getTitle());

		textArg2 = new Text(parent, SWT.BORDER | SWT.RIGHT);
		textArg2.setLayoutData(textGridData);

		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setText(CALCULATE_ON_THE_FLY);
		checkbox.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 0));

		calculateButton = new Button(parent, SWT.PUSH);
		calculateButton.setText(CALCULATE);
		calculateButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));

		initResultComposite(parent);
	}

	private void initResultComposite(Composite parent) {
		Composite resultComposite = new Composite(parent, SWT.NONE);
		resultComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 3, 0));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		resultComposite.setLayout(gridLayout);

		Label resultLabelTitle = new Label(resultComposite, SWT.NONE);
		resultLabelTitle.setText(RESULT);
		resultLabelTitle.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));

		resultText = new StyledText(resultComposite, SWT.BOLD | SWT.BORDER);
		resultText.setEditable(false);
		resultText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	}

	private void onFlychecker() {
		if (checkbox.getSelection()) {
			calculatorView.setResult(getModel());
		}
	}

	private class CalculateButtonSelectionListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			calculatorView.setResult(getModel());
		}
	}

	private class CheckBoxButtonSelectionListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent e) {
			calculateButton.setEnabled(!checkbox.getSelection());
			onFlychecker();
		}
	}

	private void initListeners() {
		TextModifyListener textModifyListener = new TextModifyListener();
		textArg1.addModifyListener(textModifyListener);
		textArg2.addModifyListener(textModifyListener);
		combo.addSelectionListener(new CheckBoxSelectionListener());
		checkbox.addSelectionListener(new CheckBoxButtonSelectionListener());
		calculateButton.addSelectionListener(new CalculateButtonSelectionListener());
	}

	private class TextModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			onFlychecker();
		}
	}

	private class CheckBoxSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			onFlychecker();
		}
	}

	public void setResultValue(String value) {
		resultText.setText(value);
	}

	private Model getModel() {
		return new Model(textArg1.getText(), combo.getText(), textArg2.getText(), checkbox.getSelection());
	}
}