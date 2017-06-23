package com.dower.demo.comm.util.num;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * 计算价格
 * @author NiuXueJun
 * 2015-7-31 下午3:25:19
 */
public class PriceUtil {

	private static Stack<BigDecimal> numbers = new Stack<BigDecimal>();

	private static Stack<Character> chs = new Stack<Character>();

	/**
	 * 比较当前操作符与栈顶元素操作符优先级，如果比栈顶元素优先级高，则返回true，否则返回false
	 * 
	 * @param str
	 *            需要进行比较的字符
	 * @return 比较结果 true代表比栈顶元素优先级高，false代表比栈顶元素优先级低
	 */
	private static boolean compare(char str) {
		if (chs.empty()) {
			// 当为空时，显然 当前优先级最低，返回高
			return true;
		}
		char last = (char) chs.lastElement();
		switch (str) {
		case '*': {
			// '*/'优先级只比'+-'高
			if (last == '+' || last == '-')
				return true;
			else
				return false;
		}
		case '/': {
			if (last == '+' || last == '-')
				return true;
			else
				return false;
		}
		// '+-'为最低，一直返回false
		case '+':
			return false;
		case '-':
			return false;
		}
		return true;
	}
	/**
	 * 不带括号的混合运算
	 * @param st 
	 * @return BigDecimal
	 */
	public static BigDecimal caculate(String st) {
		StringBuffer sb = new StringBuffer(st);
		StringBuffer num = new StringBuffer();
		String tem = null;
		char next;
		while (sb.length() > 0) {
			tem = sb.substring(0, 1);// 获取字符串的第一个字符
			sb.delete(0, 1);
			if (isNum(tem.trim())) {
				num.append(tem);// 如果是数字，将其放入num当中
			} else {

				if (num.length() > 0 && !"".equals(num.toString().trim())) {// 当截取的字符不是数字时，则认为num中放置的时一个完整的数字，
					// 如123+1,当获取到+时，前面的123可以认为是一个完整的数
					BigDecimal bd = new BigDecimal(num.toString().trim());
					numbers.push(bd);
					num.delete(0, num.length());
				}
				// 如果chs为空，这认为这时第一个字符直接放入
				if (!chs.isEmpty()) {
					// 当当前的运算符优先级等于或者小于栈顶得预算符时，做运算.
					// 例如,1+2+3,当截取到2,3之间的“+”与1,2之间的"+"优先级相等时，可以先计算1+2，使其变成3+3
					// 同样，1*2+3,当截取到2,3之间的“+”与1,2之间的"*"优先级小，可以先计算1*2，使其变成2+3

					while (!compare(tem.charAt(0))) {
						caculate();
					}
				}
				// 当数字栈也为空时,既运算式的第一个数字为负数时
				if (numbers.isEmpty()) {
					num.append(tem);
				} else {
					chs.push(new Character(tem.charAt(0)));
				}
				// 判断后一个字符是否为“-”号，为"-"号时，认为数字为负数
				// 例如 1*2*(-5)，因为此运算不计算()，因此将被改写为1*2*-5,如此情况，须将"-"认为是负数表达式而非减号
				next = sb.charAt(0);
				if (next == '-') {
					num.append(next);
					sb.delete(0, 1);
				}

			}
		}
		// 由于前面将数字放入栈时，是通过获取符号为时处理，导致最后一个数字没有放入栈中，因此将最后的数字放入栈中
		BigDecimal bd = new BigDecimal(num.toString().trim());
		numbers.push(bd);
		// 此时符号栈上最多只有2个符号，并且栈顶得符号优先级高，做运算
		while (!chs.isEmpty()) {
			caculate();
		}
		return numbers.pop();
	}

	private static void caculate() {
		BigDecimal b = numbers.pop();// 第二个运算数
		BigDecimal a = null;// 第一个运算数
		a = numbers.pop();
		char ope = chs.pop();
		BigDecimal result = null;// 运算结果
		switch (ope) {
		// 如果是加号或者减号，则
		case '+':
			result = a.add(b);
			// 将操作结果放入操作数栈
			numbers.push(result);
			break;
		case '-':
			// 将操作结果放入操作数栈
			result = a.subtract(b);
			numbers.push(result);
			break;
		case '*':
			result = a.multiply(b);
			// 将操作结果放入操作数栈
			numbers.push(result);
			break;
		case '/':
			result = a.divide(b);// 将操作结果放入操作数栈
			numbers.push(result);
			break;
		}
	}

	private static boolean isNum(String num) {
		return num.matches("[0-9]");
	}

	/**
	 * 带有括号的混合运算
	 * @param st 
	 * @return BigDecimal
	 */
	 
	public static BigDecimal parse(String st) {
		int start = 0;
		StringBuffer sts = new StringBuffer(st);
		int end = -1;
		while ((end = sts.indexOf(")")) > 0) {
			String s = sts.substring(start, end + 1);
			int first = s.lastIndexOf("(");
			BigDecimal value = caculate(sts.substring(first + 1, end));
			sts.replace(first, end + 1, value.toString());
		}
		return caculate(sts.toString());
	}


}
