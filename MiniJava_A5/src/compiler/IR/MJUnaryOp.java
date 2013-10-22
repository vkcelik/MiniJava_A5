package compiler.IR;

public abstract class MJUnaryOp extends MJExpression {

	protected MJExpression arg;

	public MJUnaryOp(MJExpression l) {
		this.arg = l;
	}

}
