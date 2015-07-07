/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
package edu.kit.joana.ui.ifc.sdg.threadviewer.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import edu.kit.joana.ui.ifc.sdg.threadviewer.controller.Controller;
import edu.kit.joana.ui.ifc.sdg.threadviewer.view.Activator;


public class ViewContextMenu_CenterParallelRegionAction extends Action {
	public ViewContextMenu_CenterParallelRegionAction() {
		super();
		setText("Center Region");
		setToolTipText("Centers the selected region in ThreadViewer.");


		ImageRegistry ir = Activator.getDefault().getImageRegistry();
		ImageDescriptor desc = ir.getDescriptor(Activator.IMAGE_VIEW_CONTEXTMENU_CENTER_REGION);
		setImageDescriptor(desc);
	}

	@Override
	public void run() {
		Controller.getInstance().runViewCenterParallelRegion();
	}
}
