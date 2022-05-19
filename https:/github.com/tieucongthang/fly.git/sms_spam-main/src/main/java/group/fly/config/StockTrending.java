package group.fly.config;

public class StockTrending {

	public enum Trending{
		INCREASE(1),DECREASE(0);
		
		private int value;

		private Trending(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
