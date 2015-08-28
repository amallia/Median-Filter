package it.antoniomallia.spm.stats;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Stats Class
 * @author antoniomallia
 *
 */
public class Stats {
	private static Stats instance = null;

	/** Singleton of the object Stats
	 * @return an istance of the class
	 */
	public static Stats getInstance() {
		if (instance == null) {
			instance = new Stats();
		}
		return instance;
	}
	
	/**
	 * Experiments collected
	 */
	@Getter
	private List<Experiment> tests = new ArrayList<Experiment>();

	
}
