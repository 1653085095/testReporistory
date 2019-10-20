package com.tools.spring;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
/**
 * description spring el 表达式解析
 */
public class SpelParser {
	private SpelParser() {};
	
	private static ExpressionParser parser = new SpelExpressionParser();
	
	public static String getId(String id, String[] paramNames, Object[] args) {
		Expression exp = parser.parseExpression(id);
		EvaluationContext context = new StandardEvaluationContext();
		if(args.length<1) {
			return null;
		}
		for(int i=0 ; i<args.length ; i++) {
			context.setVariable(paramNames[i], args[i]);
		}
		return exp.getValue(context, String.class);
	}
}
