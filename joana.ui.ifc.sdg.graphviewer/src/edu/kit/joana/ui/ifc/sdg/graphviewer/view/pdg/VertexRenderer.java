/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
/*
 * VertexRenderer.java
 *
 * Created on 21. Oktober 2005, 13:49
 */

package edu.kit.joana.ui.ifc.sdg.graphviewer.view.pdg;

import edu.kit.joana.ui.ifc.sdg.graphviewer.layout.PDGConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexView;

/**
 * This class renders a vertex.
 * @author Siegfried Weber
 */
public class VertexRenderer implements CellViewRenderer {

    /**
     * the panel to draw the vertex
     */
    private JPanel panel;
    /**
     * the area for the text of the vertex
     */
    private JTextArea textArea;

    /** Creates a new instance of VertexRenderer */
    public VertexRenderer() {
        panel = new JPanel(new GridLayout(1, 1)) {
			private static final long serialVersionUID = -7940726436777807332L;

			@Override
			public JToolTip createToolTip() {

                JToolTip retValue;

                retValue = super.createToolTip();
                return retValue;
            }
        };
        textArea = new JTextArea();
        panel.add(textArea);
    }

    /**
     * Returns a vertex as an AWT component.
     * @param graph a JGraph component
     * @param view the JGraph view
     * @param sel true if vertex is selected (unused)
     * @param focus true if vertex has the focus (unused)
     * @param preview true to render only a preview (unused)
     * @return an AWT component
     */
    public Component getRendererComponent(JGraph graph, CellView view, boolean sel, boolean focus, boolean preview) {
        if (view instanceof VertexView) {
            textArea.setComponentOrientation(graph.getComponentOrientation());

            final String label = graph.convertValueToString(view);
            if (label != null && graph.getEditingCell() != view.getCell()) {
                textArea.setText(label);
            } else {
                textArea.setText(null);
            }

            Map<?, ?> map = view.getAllAttributes();
            final Color foreground = GraphConstants.getForeground(map);
            if(foreground != null) {
                textArea.setForeground(foreground);
            }

            final Color background = GraphConstants.getBackground(map);
            if(background != null) {
                textArea.setBackground(background);
            }

            textArea.setFont(GraphConstants.getFont(map));
            panel.setBorder(GraphConstants.getBorder(map));
            panel.setToolTipText(PDGConstants.getToolTip(map));

            return panel;
        } else {
            return null;
        }
    }
}
