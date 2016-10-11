/**
 * Write a description of class results here.
 *
 * @author Zach-Attach
 * @version Alpha 0.5
 */

import javafx.scene.control.TextArea;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;

import java.util.ArrayList;
import java.util.Iterator;

class results {
	//private static final ArrayList<Object> list = new ArrayList<>();

	private static final ComplexFormat complexForm = new ComplexFormat();
	static ArrayList<Double> root = new ArrayList<Double>();
	static ArrayList<Object> list = new ArrayList<Object>();
	private static Complex complex1;
	private static Complex complex2;
	private static Complex complexResult;

	public static void equate(TextArea output) {
		int y = 0;
		char c;
		int p = 0;
		String out = "";
		boolean percent = false;

		for (Iterator<Object> listIter = func.list.iterator(); listIter.hasNext(); y++) {
			Object o = listIter.next();
			if (o.getClass() == Integer.class ||
					o.getClass() == Double.class) {
				String a = o.toString();
				list.add(complexForm.parse(a));
				while (root.size() > 0) {
					list.add('^');
					Complex r = new Complex(1 / root.get(root.size() - 1));
					list.add(r);
					y++;
					root.remove(root.size() - 1);
				}
				percent = false;
			} else if (o.getClass() == Character.class) {
				char b = (char) o;
				list.add(b);
				percent = false;
			} else if (o.getClass() == Percent.class) {
				list.add('÷');
				list.add(new Complex(100));
				percent = true;
				p = y;
				y++;
			} else if (o.getClass() == Exponent.class) {
				if (percent) {
					list.add(p, '^');
					list.add(p + 1, new Complex(2.0));
					y += 2;
				}
				list.add('^');
				list.add(new Complex(2.0));
				y++;
			} else if (o.getClass() == Root.class) {
				root.add(2.0);
				percent = false;
			}
		}

		while (list.contains('^')) {
			complexRoutine('^');
		}

		while (list.contains('×') || list.contains('÷')) // × not x
		{
			if (list.contains('×') && list.contains('÷')) {
				c = list.indexOf('×') < list.indexOf('÷') ? '×' : '÷';
			} else if (list.contains('×')) {
				c = '×';
			} else {
				c = '÷';
			}
			complexRoutine(c);
		}

		while (list.contains('＋') || list.contains('－')) //＋－ not +-
		{
			if (list.contains('＋') && list.contains('－')) {
				c = list.indexOf('＋') < list.indexOf('÷') ? '＋' : '－';
			} else if (list.contains('＋')) {
				c = '＋';
			} else {
				c = '－';
			}
			complexRoutine(c);
		}

		if (list.size() == 1) {
			out = "\n" + complexForm.format((Complex) list.get(0));

			if (out.length() > 2 && out.substring(out.length() - 2).equals(".0")) {
				out = out.substring(0, out.length() - 2);
			}

			output.appendText(out);
			list.remove(0);
		} else if (func.list.size() == 0) {
			output.clear();
		} else {
			output.appendText("\n ERROR list is " + list);
		}
	}

	private static void complexRoutine(char oP) {
		int x = list.indexOf(oP);
		complex2 = (Complex) list.get(x + 1);
		complex1 = (Complex) list.get(x - 1);

		switch (oP) {
			case '^':
				complexResult = complex1.pow(complex2);
				break;

			case '×':
				complexResult = complex1.multiply(complex2);
				break;
			case '÷':
				complexResult = complex1.divide(complex2);
				break;
			case '＋':
				complexResult = complex1.add(complex2);
				break;
			case '－':
				complexResult = complex1.subtract(complex2);
				break;
			default:
				break;
		}

		list.set(x, complexResult);
		list.remove(x + 1);
		list.remove(x - 1);
	}
}