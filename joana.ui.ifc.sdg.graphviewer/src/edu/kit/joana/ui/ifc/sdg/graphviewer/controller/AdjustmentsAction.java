/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
/*
 * @(c)AdjustmentsAction.java
 *
 * Project: GraphViewer
 *
 * Chair for Softwaresystems
 * Faculty of Informatics and Mathematics
 * University of Passau
 *
 * Created on 13.12.2004 at 17:44:21
 */
package edu.kit.joana.ui.ifc.sdg.graphviewer.controller;

import edu.kit.joana.ui.ifc.sdg.graphviewer.translation.BundleConstants;
import edu.kit.joana.ui.ifc.sdg.graphviewer.view.AdjustmentsDialog;


import java.awt.event.ActionEvent;

/**
 * @author <a href="mailto:wellner@fmi.uni-passau.de">Tobias Wellner </a>
 * @version 1.0
 */
public class AdjustmentsAction extends AbstractGVAction implements BundleConstants {
	private static final long serialVersionUID = -5611806355661341213L;
	private AdjustmentsDialog adjustmentsDialog = null;

    /**
     * Constructs a new <code>AdjustmentsAction</code> object.
     */
    public AdjustmentsAction(AdjustmentsDialog adjustmentsDialog) {
        super("adjustments.name", "Adjustments.png", "adjustments.description", "preferences");
        this.adjustmentsDialog = adjustmentsDialog;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event) {
        this.adjustmentsDialog.showAdjustmentDialog();
    }
}
