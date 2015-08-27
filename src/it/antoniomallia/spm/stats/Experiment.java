package it.antoniomallia.spm.stats;

import lombok.Getter;

public class Experiment {

	public enum ExperimentType {
		SEQUENTIAL("Sequential"), SKANDIUM_MAPEDUCE("Skandium-MapReduce"), J8_MAPREDUCE("J8-MapReduce"), SKANDIUM_FARM(
				"Skandium-Farm"), J8_FARM("J8-Farm");
		private String title;

		private ExperimentType(String title) {
			this.title = title;
		}
		public String getTitle() {
			return title;
		}
	}

	@Getter
	private int streamsize;
	@Getter
	private int thread;
	@Getter
	private int sizerow;
	@Getter
	private long time;
	@Getter
	private ExperimentType type;

	public Experiment(ExperimentType type, int streamsize, int thread, int sizerow, long time) {
		this.type=type;
		this.streamsize = streamsize;
		this.thread = thread;
		this.sizerow = sizerow;
		this.time = time;
	}
}
