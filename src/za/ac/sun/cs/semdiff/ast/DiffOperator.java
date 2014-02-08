package za.ac.sun.cs.semdiff.ast;

import za.ac.sun.cs.semdiff.matcher.DiffASTMatcher;
import za.ac.sun.cs.semdiff.visitors.DiffVisitor;

// THE DUPLICATES WILL BE LEFT OUT
//
// AssignmentOperator:
//    = ASSIGN
//    += PLUS_ASSIGN
//    -= MINUS_ASSIGN
//    *= TIMES_ASSIGN
//    /= DIVIDE_ASSIGN
//    &= BIT_AND_ASSIGN
//    |= BIT_OR_ASSIGN
//    ^= BIT_XOR_ASSIGN
//    %= REMAINDER_ASSIGN
//    <<= LEFT_SHIFT_ASSIGN
//    >>= RIGHT_SHIFT_SIGNED_ASSIGN
//    >>>= RIGHT_SHIFT_UNSIGNED_ASSIGN
//
//InfixOperator:
//    *    TIMES
//    /  DIVIDE
//    %  REMAINDER
//    +  PLUS
//    -  MINUS
//    <<  LEFT_SHIFT
//    >>  RIGHT_SHIFT_SIGNED
//    >>>  RIGHT_SHIFT_UNSIGNED
//    <  LESS
//    >  GREATER
//    <=  LESS_EQUALS
//    >=  GREATER_EQUALS
//    ==  EQUALS
//    !=  NOT_EQUALS
//    ^  XOR
//    &  AND
//    |  OR
//    &&  CONDITIONAL_AND
//    ||  CONDITIONAL_OR
//
//PrefixOperator:
//    ++  INCREMENT
//    --  DECREMENT
//    +  PLUS
//    -  MINUS
//    ~  COMPLEMENT
//    !  NOT
//
//PostfixOperator:
//    ++  INCREMENT
//    --  DECREMENT
public class DiffOperator extends DiffNode {

	private String operator = null;

	public DiffOperator(String operator) {
		super(null);
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator;
	}

	@Override
	public boolean subtreeMatch0(DiffASTMatcher matcher, Object other) {
		return matcher.match(this, other);
	}

	@Override
	public void accept0(DiffVisitor visitor) {
		visitor.visit(this);
	};

	@Override
	public String toString() {
		return this.operator;
	}

}
