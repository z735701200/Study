package chapter4;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockFreeVector<E> extends Vector<E>{
	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	private final AtomicReference<Descriptor<E>> descriptor;
	private final int N_BUCKET = 30;
	private final int FIRST_BUCKET_SIZE = 8;
	
	private int zeroNumFirst = 0;
	private boolean debug = false;
	public LockFreeVector() {
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
	}
	
	static class Descriptor<E>{
		public int size;
		volatile WriteDescript<E> writeop;
		public Descriptor(int size, WriteDescript<E> writeop) {
			this.size = size;
			this.writeop = writeop;
		}
		
		public void compleWrite(){
			WriteDescript<E> tmpOp = writeop;
			if(tmpOp!=null) {
				tmpOp.doIt();
				writeop = null;
			}
		}
	}
	static class WriteDescript<E>{
		public E oldV;
		public E newV;
		public AtomicReferenceArray<E> addr;
		public int addr_ind;
		public WriteDescript(AtomicReferenceArray<E> addr,int addr_ind,E oldV, E newV) {
			this.addr = addr;
			this.addr_ind = addr_ind;
			this.oldV = oldV;
			this.newV = newV;
		}
		public void doIt() {
			addr.compareAndSet(addr_ind, oldV, newV);
		}
	}
	
	public void push_back(E e) {
		Descriptor<E> desc;
		Descriptor<E> newd;
		do {
			desc = descriptor.get();
			desc.compleWrite();
			int pos = desc.size + FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);
			int bucketInd = zeroNumFirst -zeroNumPos;
			if(buckets.get(bucketInd) == null) {
				int newLen = 2 * buckets.get(bucketInd -1).length();
				if(debug)
					System.out.println("New Length is:" + newLen);
				buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));
			}
			int idx = (0x80000000>>>zeroNumPos) ^ pos;
			newd = new Descriptor<E>(desc.size +1,new WriteDescript<E>(buckets.get(bucketInd), idx, null, e));
		}while(!descriptor.compareAndSet(desc, newd));
		descriptor.get().compleWrite();
	}
	@Override
	public E get(int index) {
		int pos = index + FIRST_BUCKET_SIZE;
		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
		int bucketInd = zeroNumFirst -zeroNumPos;
		int idx = (0x80000000>>>zeroNumPos) ^ pos;
		return buckets.get(bucketInd).get(idx);
	}
	
}
