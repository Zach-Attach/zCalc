/**
 * Write a description of class func here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * <p>
 * TODO - errors
 * fix error: √-2%²
 * decimals are allowed to be used multiple times in a row (ex: 0.... or 0.0.0), but do not effect results
 * negatives stack if opIsLast
 * C deletes (ex: 9x or 9- or 9+) entirely, but not (ex: -9 or .9 or 9.9)
 * null equals brings an Error NumArray is 0
 * <p>
 * fix up the errorArray system and the error system in general
 * consider making a method to calculate the repetitive algorthygms in calculate() method
 * consider moving equate() and calculate() methods to new class
 * add beeps to null if statements
 * make AC happen after "=" is pressed
 * possibly make "+/-" work before a number is put in (this would likely involve messing with the numbers input instead)
 */

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.ArrayList;

class func {
	static final ArrayList<Object> list = new ArrayList<>();
	static String pastLines = "";
	private static String input;

	public static void display(String s, TextArea txtfld, GridPane g) {
		input = txtfld.getText();
		boolean change2AC = false;

		if ("0123456789".contains(s)) {
			Number n = new Number(s);
			in(n);
		} else if ("＋－×÷".contains(s)) //＋－ not +-
		{
			Operator op = new Operator(s);
			in(op);
		} else {
			switch (s) {
				case "∓":
					Negative n = new Negative(s);
					in(n);
					break;

				case "%":
					Percent p = new Percent(s);
					in(p);
					break;

				case ".":
					Decimal d = new Decimal(s);
					in(d);
					break;

				case "√":
					Root r = new Root(s);
					in(r);
					break;

				case "x²":
					Exponent e = new Exponent(s);
					in(e);
					break;

				case "AC":
					list.clear();
					break;

				case "C":
					remove();
					change2AC = true;
					break;

				case "newLine":
					pastLines = input;
					list.clear();
					break;

				default:
					break;

			}
		}


		txtfld.setText(pastLines + Display.equation());

		if (list.size() > 1) {
			if
					(listVal(list, 1, Negative.class) ||
					listVal(list, 1, Decimal.class) ||
					listVal(list, 1, Root.class) ||
					listVal(list, 1, Character.class)) {
				//txtfld.appendText("\n" + lineFinder[lineFinder.length - 1]);
			} else //Exponent, Number, or Percent //Make a boolean for this?
			{
				results.equate(txtfld);
			}
		}

		// Try to move to Calculator as a method

		input = txtfld.getText();

		String b = (change2AC || input.length() == 0) ? "AC" : "C";

		Button newB = new Button(b);
		newB.getStyleClass().add("buttonFunc");
		newB.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		newB.setOnAction(e -> func.display(b, txtfld, g));
		g.add(newB, 0, 2);

		newB = new Button("1");
		newB.getStyleClass().add("buttonNum");
		newB.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		newB.setOnAction(e -> func.display("1", txtfld, g));
		g.add(newB, 0, 3);

		txtfld.appendText("");
		txtfld.setScrollTop(Double.MAX_VALUE);

		/**
		 String[] lineFinder = txtfld.getText().split("\n");
		 lineFinder[0] = String.format("%100s", lineFinder[0]);

		 for (int line = 1; lineFinder.length > line; line++)
		 {
		 lineFinder[0] += String.format("\n%100s", lineFinder[line]) ;
		 }

		 txtfld.setText(lineFinder[0]);
		 */
	}

	static void in(Variable x) {
		boolean d;
		int power;
		double answer = 0.0;

		switch (x.val) {
			case "no":
				Toolkit.getDefaultToolkit().beep();
				break;

			case "add":
				list.add(x.place, x);
				break;

			case "add operator":
				list.add(x.IT);
				break;

			case "add num":
				list.add(x.NUM);
				break;

			case "combine": // Numbers only

				int y = (int) list.get(x.place) * 10;
				y += ((y < 0) ? x.NEG : x.NUM);
				list.set(x.place, y);
				break;

			case "replace": //non-numbers
				list.set(x.place, x);
				break;

			case "remove": //non-numbers
				list.remove(x.place);
				break;

			case "remove twice": //non-numbers
				list.remove(x.place);
				list.remove(x.place);
				break;

			case "negatize": //Numbers only
				list.set(x.place, x.NEG);
				break;

			case "negatize double":
				list.set(x.place, x.NEGDOUBLE);
				break;

			case "after decimal": //Numbers only
				power = -1;

				if (list.size() == 0 || listVal(list, 1, Root.class)) {
					d = true;
					list.add(answer);
				} else if (listVal(list, 1, Negative.class)) {
					d = false;
				} else {
					answer = 1.0 * (int) list.get(x.place);
					d = answer >= 0;
				}

				answer += (d ? x.NUM : x.NEG) * Math.pow(10, power);
				list.set(x.place, answer);
				break;

			default: // Numbers only
				power = Integer.parseInt(x.val);
				answer = (double) list.get(x.place);
				d = answer >= 0;
				answer += ((d ? x.NUM : x.NEG) * Math.pow(10, power));
				list.set(list.size() - 1, answer);
				break;

		}
	}

	static void remove() {
		int c = 1;
		while (list.size() > c &&
				(listVal(list, c, Percent.class) || listVal(list, c, Exponent.class))) {
			c++;
		}

		c++;

		while (list.size() > c &&
				(listVal(list, c, Negative.class) || listVal(list, c, Root.class))) {
			c++;
		}

		c--;

		while (c > 0) {
			list.remove(list.size() - 1);
			c--;
		}
	}

	static boolean listVal(ArrayList<Object> a, int i, Class<?> c) {
		return (a.get(a.size() - i).getClass() == c);
	}

}


