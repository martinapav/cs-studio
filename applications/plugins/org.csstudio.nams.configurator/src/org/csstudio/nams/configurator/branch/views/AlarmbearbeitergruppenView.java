package org.csstudio.nams.configurator.branch.views;

import java.util.ArrayList;
import java.util.Collection;

import org.csstudio.nams.configurator.branch.actions.OpenConfigurationEditor;
import org.csstudio.nams.configurator.branch.composite.FilteredListVarianteA;
import org.csstudio.nams.configurator.treeviewer.model.AlarmbearbeitergruppenBean;
import org.csstudio.nams.configurator.treeviewer.model.ConfigurationModel;
import org.csstudio.nams.configurator.treeviewer.model.IConfigurationModel;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class AlarmbearbeitergruppenView extends ViewPart {

	public AlarmbearbeitergruppenView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {

		new FilteredListVarianteA(parent, SWT.None) {
			protected void openEditor(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				Object source = selection.getFirstElement();
				AlarmbearbeitergruppenBean alarmbearbeitergruppenBean = new AlarmbearbeitergruppenBean();
				alarmbearbeitergruppenBean.setName((String) source);
				IConfigurationModel model = new ConfigurationModel(null) {
					@Override
					public Collection<String> getSortgroupNames() {
						Collection<String> groupNames = new ArrayList<String>();
						groupNames.add("Kryo OPS");
						groupNames.add("C1-WPS");
						return groupNames;
					}
				};

				new OpenConfigurationEditor(alarmbearbeitergruppenBean, model)
						.run();
			}

			@Override
			protected Object[] getTableInput() {
				String[] input = new String[] { "AMS", "C1-WPS", "FLASH" };
				return input;
			}
		};

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
