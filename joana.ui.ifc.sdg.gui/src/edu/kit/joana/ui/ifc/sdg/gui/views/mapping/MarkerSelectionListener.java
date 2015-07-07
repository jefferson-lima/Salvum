/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
package edu.kit.joana.ui.ifc.sdg.gui.views.mapping;

import org.eclipse.core.resources.IMarker;

public interface MarkerSelectionListener {
	void markerSelectionChanged(IMarker marker);
}
