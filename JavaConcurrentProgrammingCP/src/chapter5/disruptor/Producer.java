package chapter5.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class Producer {
	private final RingBuffer<PCData> ringBuffer;
	public Producer(RingBuffer<PCData> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}
	public void pushData(ByteBuffer bb) {
		long sequence = ringBuffer.next();
		try {
			PCData event = ringBuffer.get(sequence);
			event.setValue(bb.getLong(0));
		}finally {
			ringBuffer.publish(sequence);
		}
	}
}
