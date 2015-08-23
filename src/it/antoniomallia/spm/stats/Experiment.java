package it.antoniomallia.spm.stats;

public class Experiment {

	public enum Type {
		SEQUENTIAL("Sequential"), SKANDIUM_MAPEDUCE("Skandium-MapReduce"), J8_MAPREDUCE("J8-MapReduce"), SKANDIUM_FARM(
				"Skandium-Farm"), J8_FARM("J8-Farm");
		private String title;

		private Type(String title) {
			this.title = title;
		}
		public String getTitle() {
			return title;
		}
	}

	public int streamsize;
	public int thread;
	public int sizerow;
	public long time;
	public Type type;

	public Experiment(Type type, int streamsize, int thread, int sizerow, long time) {
		this.type=type;
		this.streamsize = streamsize;
		this.thread = thread;
		this.sizerow = sizerow;
		this.time = time;
	}

	@Override
	public String toString() {
		return "[type:" + type.getTitle() + "thread: " + thread + ", sizerow: " + sizerow
				+ ", time: " + time + "]";
	}

}
