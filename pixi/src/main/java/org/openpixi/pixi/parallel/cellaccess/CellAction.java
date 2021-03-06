package org.openpixi.pixi.parallel.cellaccess;

import org.openpixi.pixi.physics.grid.Grid;

/**
 * Required in cell iterator, so that we are able to use the cell iterator
 * for different operations upon cells.
 */
public interface CellAction {
	/**
	 * Executes the action on a cell with particular index.
	 */
	void execute(Grid grid, int index);
}
