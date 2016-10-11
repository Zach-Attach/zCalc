/**
 * Write a description of class Display here.
 *
 * @author Zach-Attach
 * @version Alpha 0.5
 */

import java.util.ArrayList;

class Display {
	static String equation() {
		ArrayList<Object> list = func.list;

		String display = "";
		for (Object x : list) {
			if (x.getClass() == Percent.class) {
				display += '%';
			} else if (x.getClass() == Decimal.class) {
				display += '.';
			} else if (x.getClass() == Negative.class) {
				display += '-';
			} else if (x.getClass() == Exponent.class) {
				display += '²';
			} else if (x.getClass() == Root.class) {
				display += '√';
			} else {
				display += x;
			}
		}

		display = "\n\n" + display;
		return display;
	}
}
