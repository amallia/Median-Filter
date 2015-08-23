package it.antoniomallia.spm.stats;

import java.util.ArrayList;
import java.util.List;

public class Stats {
	private static Stats instance = null;

	public static Stats getInstance() {
		if (instance == null) {
			instance = new Stats();
		}
		return instance;
	}

	public List<Experiment> tests = new ArrayList<Experiment>();

	
}
