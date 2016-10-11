import java.util.ArrayList;

abstract class Variable {

	int NUM;
	int NEG;
	double NEGDOUBLE = 0;
	char IT;
	int place;
	String val;

	public Variable(int s) {
		ArrayList<Object> list = func.list;
		int size = list.size();

		NUM = s;
		IT = (char) s;

		NEG = 0 - NUM;
		place = size == 0 ? 0 : size - 1;

		if
				(size == 0 ||
				func.listVal(list, 1, Character.class) ||
				func.listVal(list, 1, Root.class)) {
			val = "add num";
		} else if (func.listVal(list, 1, Integer.class)) {

			val = "combine";
		} else if (func.listVal(list, 1, Decimal.class)) {
			func.list.remove(place);
			place = (func.list.size() > 0 ? func.list.size() - 1 : 0);
			val = "after decimal";
		} else if (func.listVal(list, 1, Double.class)) {
			String str = list.get(size - 1).toString();
			int index = str.indexOf(".");
			index = index - str.length();
			val = String.valueOf(index);
		} else if (func.listVal(list, 1, Negative.class)) {
			val = "negatize";
		} else if (func.listVal(list, 1, Percent.class)) {
			val = "no";
		} else {
			val = "no";
		}

	}

	Variable(char s) {
		ArrayList<Object> list = func.list;
		int size = list.size();
		IT = s;
		place = size == 0 ? 0 : size - 1;


		if (size == 0) {
			val = "no";
		} else if (func.listVal(list, 1, Character.class)) {
			val = "no";
		} else if (func.listVal(list, 1, Decimal.class)) {
			val = "replace";
		} else {
			val = "add operator";
		}


	}

	Variable(String s) {
		ArrayList<Object> list = func.list;
		int size = list.size();
		place = size == 0 ? 0 : size;
		IT = s.charAt(0);

		switch (s) {
////////////////////////////////////////////////////////////////////
			case "%":

				if (size == 0) {
					val = "no";
				} else if (func.listVal(list, 1, Integer.class) ||
						func.listVal(list, 1, Double.class)) {
					val = "add";
				} else if (func.listVal(list, 1, Decimal.class)) {
					val = "replace";
				} else {
					val = "no";
				}

				break;
//////////////////////////////////////////////////////////////////////
			case "∓":

				if (size == 0) {
					place = 0;
					val = "add";
				} else if (func.listVal(list, 1, Percent.class) ||
						func.listVal(list, 1, Exponent.class)) {
					int count = 1;
					while (func.listVal(list, count, Percent.class) ||
							func.listVal(list, count, Exponent.class)) {
						count++;
					}
					if (size > count) {
						if (func.listVal(list, 1, Negative.class)) {
							place = size - 1 - count;
							val = "remove";
						} else if (list.get(size - 1 - count).equals('－')) {
							IT = '+';
							place = size - 1 - count;
							val = "replace";
						} else if (list.get(size - 1 - count).equals('+')) {
							IT = '－';
							place = size - 1 - count;
							val = "replace";
						} else {
							place = size - count;
							val = "add";
						}
					} else {
						place = size - 2;
						val = "add";
					}
				} else if (func.listVal(list, 1, Character.class)) {
					if (list.get(size - 1).equals('＋')) {
						IT = '+';
						place = size - 1;
						val = "replace";
					} else if (list.get(size - 1).equals('－')) {
						IT = '－';
						place = size - 1;
						val = "replace";
					} else {
						place = size;
						val = "add";
					}
				} else if (func.listVal(list, 1, Integer.class) ||
						func.listVal(list, 1, Double.class)) {
					if (size > 1) {
						if (func.listVal(list, 2, Negative.class)) {
							place = size - 2;
							val = "remove";
						} else if (list.get(size - 2).equals('－')) {
							IT = '+';
							place = size - 2;
							val = "replace";
						} else if (list.get(size - 2).equals('+')) {
							IT = '－';
							place = size - 2;
							val = "replace";
						} else {
							place = size - 1;
							if (func.listVal(list, 1, Integer.class)) {
								NEG = 0 - (int) (list.get(size - 1));
								val = "negatize";
							}
							if (func.listVal(list, 1, Double.class)) {
								NEGDOUBLE = 0.0 - (double) (list.get(size - 1));
								val = "negatize double";
							}

						}
					} else {
						place = size - 1;
						val = "add";
					}
				} else if (func.listVal(list, 1, Decimal.class)) {
					if (size != 1 &&
							func.listVal(list, 2, Negative.class)) {
						place = size - 2;
						val = "remove";
					} else {
						place = size - 1;
						val = "add";
					}
				} else if (func.listVal(list, 1, Negative.class)) {
					place = size - 1;
					val = "remove";
				} else if (func.listVal(list, 1, Root.class)) {
					place = size;
					val = "add";
				} else {
					val = "no";
				}
				break;
/////////////////////////////////////////////////////////////
			case ".":

				if (size == 0) {
					val = "add";
				} else if (func.listVal(list, 1, Percent.class) ||
						func.listVal(list, 1, Decimal.class) ||
						func.listVal(list, 1, Double.class)) {
					val = "no";
				} else {
					val = "add";
				}
				break;
///////////////////////////////////////////////////////////
			case "x²":
				NUM = 2;
				IT = 2;

				if (
						size == 0 ||
								func.listVal(list, 1, Character.class) ||
								func.listVal(list, 1, Decimal.class) ||
								func.listVal(list, 1, Negative.class) ||
								func.listVal(list, 1, Root.class)
						) {
					val = "no";
				} else {
					val = "add";
				}

				break;
////////////////////////////////////////////////////////////
			case "√":

				if (size == 0) {
					place = 0;
					val = "add";
				} else if (func.listVal(list, 1, Exponent.class)) {
					if (size > 2 && func.listVal(list, 3, Root.class)) {
						place = size - 3;
						val = "remove";
					} else if (size > 1 && func.listVal(list, 2, Percent.class)) {
						place = size - 3;
						val = "add";
					} else {
						place = size - 2;
						val = "add";
					}
				} else if (func.listVal(list, 1, Percent.class)) {
					if (size > 2 && func.listVal(list, 3, Root.class)) {
						place = size - 3;
						val = "remove";
					} else if (size > 1 && func.listVal(list, 2, Exponent.class)) {
						place = size - 3;
						val = "add";
					} else {
						place = size - 2;
						val = "add";
					}
				} else if (func.listVal(list, 1, Character.class)) {
					place = size - 1;
					val = "add";
				} else if
						(func.listVal(list, 1, Integer.class) ||
								func.listVal(list, 1, Double.class)) {
					if (size > 1 && func.listVal(list, 2, Root.class)) {
						place = size - 2;
						val = "remove";
					} else {
						place = size - 1;
						val = "add";
					}
				} else if (func.listVal(list, 1, Decimal.class)) {
					if (size > 1 && func.listVal(list, 2, Negative.class)) {
						if (size > 2 && func.listVal(list, 3, Root.class)) {
							place = size - 3;
							val = "remove";
						} else {
							place = size - 2;
							val = "add";
						}
					} else {
						place = size - 1;
						val = "add";
					}
				} else if (func.listVal(list, 1, Negative.class)) {
					if (size > 1 && func.listVal(list, 2, Root.class)) {
						if (size > 2 && func.listVal(list, 3, Negative.class))
							place = size - 2;
						val = "remove";

					} else {
						val = "add";
					}
				} else {
					val = "no";
				}

				break;
		}
	}

}
