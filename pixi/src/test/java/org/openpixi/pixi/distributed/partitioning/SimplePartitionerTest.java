package org.openpixi.pixi.distributed.partitioning;

import junit.framework.TestCase;

import java.util.List;

/**
 * General test for partitioning the simulation area in distributed simulation.
 * Can be applied to check any partitioning.
 * The test checks:
 * - correct number of partitions
 * - unique partitions (no two partitions with the same xmin, xmax, ymin, ymax)
 * - complete partitions
 *   1) largest xmax - smallest xmin = numCellsX; similarly for y
 *   2) they do not interleave
 * - no malformed partitions (xmax > xmin, ymax > ymin)
 */
public class SimplePartitionerTest extends TestCase {

	private static final int NUM_CELLS_X = 64;
	private static final int NUM_CELLS_Y = 32;
	private static final int NUM_PARTITIONS = 16;


	public void testPartition() throws Exception {
		SimplePartitioner partitioner = new SimplePartitioner();
		Box[] partitions = partitioner.partition(NUM_CELLS_X, NUM_CELLS_Y, NUM_PARTITIONS);

		assertEquals(NUM_PARTITIONS, partitions.length);

		int largestXmax = Integer.MIN_VALUE;
		int smallestXmin = Integer.MAX_VALUE;
		int largestYmax = Integer.MIN_VALUE;
		int smallestYmin = Integer.MAX_VALUE;

		for (int i = 0; i < partitions.length; ++i) {
			Box b = partitions[i];

			if (isMalformed(b)) {
				fail("Malformed partition! " + b);
			}

			if (b.xmax() > largestXmax) {
				largestXmax = b.xmax();
			}
			if (b.ymax() > largestYmax) {
				largestYmax = b.ymax();
			}
			if (b.xmin() < smallestXmin) {
				smallestXmin = b.xmin();
			}
			if (b.ymin() < smallestYmin) {
				smallestYmin = b.ymin();
			}

			for (int j = i + 1; j < partitions.length; ++j) {
				Box b2 = partitions[j];
				if (areEqual(b, b2)) {
					fail("Equal partitions: " + b + " " + b2);
				}
				if (interleave(b, b2)) {
					fail("Interleaving partitions: "  + b + " " + b2);
				}
			}
		}


		if (largestXmax - smallestXmin < NUM_CELLS_X || largestYmax - smallestYmin < NUM_CELLS_Y) {
			fail("The created partitions do not fill the original area!");
		}
		else if (largestXmax - smallestXmin > NUM_CELLS_X || largestYmax - smallestYmin > NUM_CELLS_Y) {
			fail("The created partitions cross the original area!");
		}
	}


	private boolean areEqual(Box b1, Box b2) {
		if (
				b1.xmin() == b2.xmin() &&
				b1.xmax() == b2.xmax() &&
				b1.ymin() == b2.ymin() &&
				b1.ymax() == b2.ymax()) {
			return true;
		}
		else {
			return false;
		}
	}


	private boolean interleave(Box b1, Box b2) {
		if (
				b1.xmin() < b2.xmax() &&
				b2.xmin() < b1.xmax() &&
				b1.ymin() < b2.ymax() &&
				b2.ymin() < b1.ymax()) {
			return true;
		}
		else {
			return false;
		}
	}


	private boolean isMalformed(Box b) {
		if (b.xmin() < b.xmax() && b.ymin() < b.ymax()) {
			return false;
		}
		else {
			return  true;
		}
	}
}
