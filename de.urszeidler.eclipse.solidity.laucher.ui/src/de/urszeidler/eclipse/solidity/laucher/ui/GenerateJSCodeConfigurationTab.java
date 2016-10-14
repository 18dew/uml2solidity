package de.urszeidler.eclipse.solidity.laucher.ui;

import static de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

/**
 * @author uzeidler
 *
 */
public class GenerateJSCodeConfigurationTab extends AbstractUml2SolidityLaunchConfigurationTab {
	private Text jsDirectoryText;
	private Text testDirectoryText;
	private Text jsHeaderText;
	private Button btnGenerateJsCode;
	private Button btnGenerateJsTestcode;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, true));		
		setControl(mainComposite);
		
		Group grpGenerateJsCode = new Group(mainComposite, SWT.NONE);
		grpGenerateJsCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpGenerateJsCode.setText("generate js code");
		grpGenerateJsCode.setLayout(new GridLayout(3, false));
		
		btnGenerateJsCode = new Button(grpGenerateJsCode, SWT.CHECK);
		btnGenerateJsCode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		btnGenerateJsCode.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnGenerateJsCode.setText("generate js code");
		new Label(grpGenerateJsCode, SWT.NONE);
		
		Label lblDirectoryForJs = new Label(grpGenerateJsCode, SWT.NONE);
		lblDirectoryForJs.setText("directory for js code");
		
		jsDirectoryText = new Text(grpGenerateJsCode, SWT.BORDER);
		jsDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSelect = new Button(grpGenerateJsCode, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContainer initialRoot = ResourcesPlugin.getWorkspace().getRoot();
				ContainerSelectionDialog containerSelectionDialog = new ContainerSelectionDialog(getShell(), initialRoot, false, "select js folder");
				containerSelectionDialog.open();
				Object[] result = containerSelectionDialog.getResult();
				if (result != null && result.length==1) {	
					IPath container = (IPath) result[0];				
					jsDirectoryText.setText(container.toString());
					setDirty(true);
					updateLaunchConfigurationDialog();
				}

			}
		});
		btnSelect.setText("select");
		
		btnGenerateJsTestcode = new Button(grpGenerateJsCode, SWT.CHECK);
		btnGenerateJsTestcode.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		btnGenerateJsTestcode.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnGenerateJsTestcode.setText("generate js testcode");
		new Label(grpGenerateJsCode, SWT.NONE);
		
		Label lblNewLabel = new Label(grpGenerateJsCode, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("directory for test code");
		
		testDirectoryText = new Text(grpGenerateJsCode, SWT.BORDER);
		testDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(grpGenerateJsCode, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContainer initialRoot = ResourcesPlugin.getWorkspace().getRoot();
				ContainerSelectionDialog containerSelectionDialog = new ContainerSelectionDialog(getShell(), initialRoot, false, "select test folder");
				containerSelectionDialog.open();
				Object[] result = containerSelectionDialog.getResult();
				if (result != null && result.length==1) {	
					IPath container = (IPath) result[0];				
					testDirectoryText.setText(container.toString());
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
			}
		});
		btnNewButton.setText("select");
		
		Label lblJsFileHeader = new Label(grpGenerateJsCode, SWT.NONE);
		lblJsFileHeader.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		lblJsFileHeader.setText("js file header");
		
		jsHeaderText = new Text(grpGenerateJsCode, SWT.BORDER | SWT.MULTI);
		jsHeaderText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1);
		gd_text_2.heightHint = 97;
		jsHeaderText.setLayoutData(gd_text_2);

		

	}

	
	
	/* (nicht-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore(null);
		
		configuration.setAttribute(JS_FILE_HEADER, store.getString(JS_FILE_HEADER));
		configuration.setAttribute(GENERATE_JS_CONTROLLER, store.getBoolean(GENERATE_JS_CONTROLLER));
		configuration.setAttribute(GENERATE_JS_CONTROLLER_TARGET,store.getString(GENERATE_JS_CONTROLLER_TARGET));
		configuration.setAttribute(GENERATE_JS_TEST, store.getBoolean(GENERATE_JS_TEST));
		configuration.setAttribute(GENERATE_JS_TEST_TARGET, store.getString(GENERATE_JS_TEST_TARGET));
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore(null);
		try {
			jsHeaderText.setText(configuration.getAttribute(JS_FILE_HEADER, store.getString(JS_FILE_HEADER)));
			jsDirectoryText.setText(configuration.getAttribute(GENERATE_JS_CONTROLLER_TARGET, store.getString(GENERATE_JS_CONTROLLER_TARGET)));
			testDirectoryText.setText(configuration.getAttribute(GENERATE_JS_TEST_TARGET, store.getString(GENERATE_JS_TEST_TARGET)));
			btnGenerateJsCode.setSelection(configuration.getAttribute(GENERATE_JS_CONTROLLER, true));
			btnGenerateJsTestcode.setSelection(configuration.getAttribute(GENERATE_JS_TEST, true));
			
			IResource member = findResource(configuration, jsDirectoryText.getText());
			if (member != null)
				jsDirectoryText.setText(member.getFullPath().toString());
			member = findResource(configuration, testDirectoryText.getText());
			if (member != null)
				testDirectoryText.setText(member.getFullPath().toString());

		} catch (CoreException e) {
			//TODO: log error
		}

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(GENERATE_JS_CONTROLLER, btnGenerateJsCode.getSelection());
		configuration.setAttribute(GENERATE_JS_CONTROLLER_TARGET, jsDirectoryText.getText());
		configuration.setAttribute(GENERATE_JS_TEST, btnGenerateJsTestcode.getSelection());
		configuration.setAttribute(GENERATE_JS_TEST_TARGET, testDirectoryText.getText());
		configuration.setAttribute(JS_FILE_HEADER, jsHeaderText.getText());
	}

	@Override
	public String getName() {
		return "generate js code";
	}
}
