package com.lux.calculator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class HistoryPanel {
	
	private static final String ALL_DATA_WILL_BE_DELETED="All data will be deleted. Are you shure?";	
	private static final String CLEAN="Clean";
	private static final String[] TABLE_HEADER = { "first num", "action", "second num", " result " };

	private Shell shell;
	private Table table;
	private Button cleanButton;

	public HistoryPanel(Composite parent) {
		shell = parent.getShell();
		parent.setLayout(new GridLayout(1, true));
		initUI(parent);
	}

	private void initUI(Composite parent) {
		
		table = new Table(parent, SWT.BORDER | SWT.V_SCROLL);
		table.setLayoutData(new GridData( GridData.FILL, GridData.FILL,true,true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		for (int i = 0; i < TABLE_HEADER.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(TABLE_HEADER[i]);
		}
		
		for (int i = 0; i < TABLE_HEADER.length; i++) {
			table.getColumn(i).pack();
		}

		GridData gridDataClean = new GridData();
		gridDataClean.widthHint = 80;
		gridDataClean.heightHint = 30;
		gridDataClean.horizontalAlignment = GridData.END;

		cleanButton = new Button(parent, SWT.PUSH);
		cleanButton.setText(CLEAN);
		cleanButton.setLayoutData(gridDataClean);
		cleanButton.setEnabled(false);
		cleanButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearHistory();
			}
		});

	}

	public  void addTableItem(String[] items) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(items);
		cleanButton.setEnabled(true);
		resizeColumns();
	}

	private  void resizeColumns() {
		for (int i = 0; i < TABLE_HEADER.length; i++) {
			table.getColumn(i).pack();
		}
	}

	private void clearHistory() {
		if (table.getItems().length > 0 && confirmClear()) {
			table.removeAll();
			cleanButton.setEnabled(false);
		}
	}

	private boolean confirmClear() {
		MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		dialog.setText(CLEAN);
		dialog.setMessage(ALL_DATA_WILL_BE_DELETED);
		int buttonID = dialog.open();
		return buttonID == SWT.OK;
	}
}
