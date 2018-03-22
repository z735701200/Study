package chapter5;

import java.util.concurrent.RecursiveTask;
import org.ujmp.core.Matrix;
/**
 * 矩阵jar包未知
 * @author resound
 *
 */
public class MatrixMulTask extends RecursiveTask<Matrix>{
	Matrix m1;
	Matrix m2;
	String pos;
	public MatrixMulTask(Matrix m1,Matrix m2,String pos) {
		this.m1=m1;
		this.m2=m2;
		this.pos = pos;
	}
	@Override
	protected Matrix compute() {
		return null;
	}
}
