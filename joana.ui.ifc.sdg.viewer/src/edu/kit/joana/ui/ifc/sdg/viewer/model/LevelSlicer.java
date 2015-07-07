/**
 * This file is part of the Joana IFC project. It is developed at the
 * Programming Paradigms Group of the Karlsruhe Institute of Technology.
 *
 * For further details on licensing please read the information at
 * http://joana.ipd.kit.edu or contact the authors.
 */
package edu.kit.joana.ui.ifc.sdg.viewer.model;

import java.util.Map;
import java.util.TreeSet;

import edu.kit.joana.ifc.sdg.graph.SDGNode;


/** This class wraps common slicing algorihtms.
 * It is suitable for all slicing algorithms that only need a slicing criterion
 * and return a set of nodes as result.
 *
 * @author giffhorn
 *
 */
public class LevelSlicer extends Run {
	/** Kind models the different kinds of criteria.
	 * In this case, it only contains one kind: the slicing criterion.
	 *
	 * @author giffhorn
	 *
	 */
	public static enum Kind {
		// the slicing criterion
        SLICING_CRITERION("Slicing Criterion");

        private final String value;

        Kind(String s) { value = s; }

        public String toString() {
            return value;
        }
    }

	// the slicer
	private edu.kit.joana.ifc.sdg.graph.slicer.barrier.LevelSlicer slicer;
	private TreeSet<SDGNode> criteria;
	private Map<SDGNode, Integer> result;

	/** Creates a new Slicer.
	 *
	 * @param alg    The algorithm to use.
	 * @param graph  The graph to slice.
	 */
	public LevelSlicer(String className, Graph g) {
		// load the slicing algorithm
		Object o = AlgorithmFactory.loadClass(className, g);
		slicer = (edu.kit.joana.ifc.sdg.graph.slicer.barrier.LevelSlicer)o;

		// initialize the result set
		criteria = new TreeSet<SDGNode>(SDGNode.getIDComparator());
	}

	/** Returns the result or null, if computation is not finished.
	 *
	 */
	public Map<SDGNode, Integer> getResult() {
        return result;
	}

	/** Executes the slice and saves the result.
	 * It notifies the observer when the slice is finished.
	 */
	public void execute(){
	    result = slicer.slice(criteria);
	    //observer.update();
	}

	/** Returns the different kinds of criteria.
	 *
	 */
	@SuppressWarnings("rawtypes")
    public Enum[] getKindsOfCriteria() {
		return LevelSlicer.Kind.values();
	}

	/** Adds new criteria to the slicing criterion.
	 *
	 * @param crit  The criteria to add.
	 * @param kind  Must be the kind SLICING_CRITERION.
	 */
	public void convertCriteria() {
		for (CriteriaCategory cc : this.getChildren()) {
			for (Criteria crit : cc.getChildren()) {
				criteria.addAll(crit.retrieveCriteria());
			}
		}
	}
}
