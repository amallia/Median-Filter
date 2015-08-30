package it.antoniomallia.spm.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean containing an experiment
 * 
 * @author antoniomallia
 *
 */
@AllArgsConstructor
public class Experiment {

	/**
	 * Enum of experiment type
	 * 
	 * @author antoniomallia
	 *
	 */
	public enum ExperimentType {
		SEQUENTIAL("Sequential"), SKANDIUM_MAP("Skandium-Map"), J8_MAP(
				"J8-Map"), SKANDIUM_FARM("Skandium-Farm"), J8_FARM(
				"J8-Farm");
		
		/**
		 * Title of the experiment
		 */
		@Getter
		private String title;

		private ExperimentType(String title) {
			this.title = title;
		}
	}

	/**
	 * Size of the stream (n. of matrices)
	 */
	@Getter
	private int streamsize;
	/**
	 * Thread used for the experiment
	 */
	@Getter
	private int thread;
	/**
	 * Matrix Dimension
	 */
	@Getter
	private int sizerow;
	
	/**
	 * Execution time
	 */
	@Getter
	private long time;
	
	/**
	 * Experiment Type
	 */
	@Getter
	private ExperimentType type;


}
