package chapter5.producerToConsumner;

public final class PCData {			//任务相关数据
	private final int intData;		//数据
	public PCData(int d) {
		intData = d;
	}
	public int getIntData() {
		return intData;
	}
	@Override
	public String toString() {
		return "data:"+intData;
	}
}
