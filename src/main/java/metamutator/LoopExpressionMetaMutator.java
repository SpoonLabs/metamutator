package metamutator;
import spoon.reflect.visitor.Filter;
import java.util.EnumSet;

import spoon.processing.AbstractProcessor;

import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtLoop;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.ReturnOrThrowFilter;
import spoon.reflect.code.CtCFlowBreak;
/**
 * inserts a mutation hotspot for DO statement
 */
public class LoopExpressionMetaMutator 
				extends AbstractProcessor<CtLoop> {

	public static final String PREFIX = "_doExpressionMetaMutator";
	public enum NbRound {
		NoRound,
		Rounds3,
		Rounds100
		
	};
	private static final EnumSet<NbRound> roundsSet = EnumSet
			.of(NbRound.NoRound,NbRound.Rounds3, NbRound.Rounds100);
	
	public static int thisIndex = 0;
	
	/**
	 * Stop Do loop on 3 or 100 Rounds
	 */
	@Override
	public void process(CtLoop candidate) {
		thisIndex++;
		String constanteName = "_doExpressionMetaMutator"+thisIndex+"_Constante";
		String expression = "int "+ constanteName +" = 1";
		CtCodeSnippetStatement DeclareRoundStatement = getFactory().Core()
				.createCodeSnippetStatement();
		DeclareRoundStatement.setValue(expression);
		
		
		
		
		String expression2 = "if((" + PREFIX + thisIndex + ".is("+  NbRound.Rounds3.getClass().getCanonicalName() + '.' + NbRound.Rounds3.toString() + ")) && "+ constanteName +" == 3) "
							+ "{" + this.breakOrReturn(candidate) + "}"
							+ "else if((" + PREFIX + thisIndex + ".is("+NbRound.NoRound.getClass().getCanonicalName() + '.' + NbRound.NoRound.toString() + "))) "
							+ "{" + this.breakOrReturn(candidate) + "}"
							+ "else if("+ constanteName +" == 100)"
							+ "{" + this.breakOrReturn(candidate) + "}"
							+ " "+ constanteName +"++";
		CtCodeSnippetStatement ifRoundStatement = getFactory().Core()
				.createCodeSnippetStatement();
		ifRoundStatement.setValue(expression2);
		
		candidate.insertBefore(DeclareRoundStatement);
		candidate.getBody().insertAfter(ifRoundStatement);
		Selector.generateSelector(candidate, NbRound.NoRound, thisIndex, roundsSet, PREFIX);
	}
	
	public String breakOrReturn(CtLoop candidate) {
		Filter<CtCFlowBreak> filter = new ReturnOrThrowFilter();
		if(candidate.getBody().getElements(filter).size() > 0) {
			return candidate.getBody().getElements(filter).get(0).toString() + ";";
		}else {
			return "break;";
		}
	}
}
