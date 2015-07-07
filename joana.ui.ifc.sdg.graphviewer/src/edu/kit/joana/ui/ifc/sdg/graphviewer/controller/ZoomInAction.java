/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
/*
 * @(c)ZoomInAction.java
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

import edu.kit.joana.ui.ifc.sdg.graphviewer.view.GraphPane;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jgraph.JGraph;

/**
 * @author <a href="mailto:wellner@fmi.uni-passau.de">Tobias Wellner </a>
 * @version 1.0
 */
public class ZoomInAction extends AbstractGVAction implements PropertyChangeListener {
	private static final long serialVersionUID = 3414304639627565929L;
	private static final double ZOOM_FACTOR = 2;
    protected static final Double MAX_SCALE = Double.valueOf(4);

    private GraphPane pane;

    /**
     * Constructs a new <code>ZoomInAction</code> object.
     */
    public ZoomInAction(GraphPane pane) {
        super("zoomIn.name", "ZoomIn.png", "zoomIn.description", "zoomIn");
        this.pane = pane;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event) {
    	JGraph graph = pane.getSelectedJGraph();
    	double oldScale = graph.getScale();
        double newScale = oldScale * ZOOM_FACTOR;
        graph.setScale(newScale);
        if (graph.getSelectionCell() != null) {
            graph.scrollCellToVisible(graph.getSelectionCell());
        }
//    	return new CommandStatusEvent(this, CommandStatusEvent.SUCCESS,
//                new Resource(COMMANDS_BUNDLE,
//                        (oldScale > newScale) ? "zoomOut.success.status"
//                                : "zoomOut.success.status"));
    }
    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(JGraph.SCALE_PROPERTY)) {
            this.setEnabled(((Double)event.getNewValue()).compareTo(MAX_SCALE) < 0);
        }
    }
}
