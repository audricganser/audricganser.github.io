/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 23 Oct 08; 27 Mar 09; 06 Aug 10
 *          30 Dec 13
 */

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }
    // access functions for expression representation
    public static Object op  (Cons x) { return first(x); }
    public static Object lhs (Cons x) { return first(rest(x)); }
    public static Object rhs (Cons x) { return first(rest(rest(x))); }
    public static boolean numberp (Object x)
       { return ( (x != null) &&
                  (x instanceof Integer || x instanceof Double) ); }
    public static boolean integerp (Object x)
       { return ( (x != null) && (x instanceof Integer ) ); }
    public static boolean floatp (Object x)
       { return ( (x != null) && (x instanceof Double ) ); }
    public static boolean stringp (Object x)
       { return ( (x != null) && (x instanceof String ) ); }

    // convert a list to a string for printing
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public boolean equals(Object other) { return equal(this,other); }

    // tree equality
public static boolean equal(Object tree, Object other) {
    if ( tree == other ) return true;
    if ( consp(tree) )
        return ( consp(other) &&
                 equal(first((Cons) tree), first((Cons) other)) &&
                 equal(rest((Cons) tree), rest((Cons) other)) );
    return eql(tree, other); }

    // simple equality test
public static boolean eql(Object tree, Object other) {
    return ( (tree == other) ||
             ( (tree != null) && (other != null) &&
               tree.equals(other) ) ); }

// member returns null if requested item not found
public static Cons member (Object item, Cons lst) {
  if ( lst == null )
     return null;
   else if ( item.equals(first(lst)) )
           return lst;
         else return member(item, rest(lst)); }

public static Cons union (Cons x, Cons y) {
  if ( x == null ) return y;
  if ( member(first(x), y) != null )
       return union(rest(x), y);
  else return cons(first(x), union(rest(x), y)); }

public static boolean subsetp (Cons x, Cons y) {
    return ( (x == null) ? true
             : ( ( member(first(x), y) != null ) &&
                 subsetp(rest(x), y) ) ); }

public static boolean setEqual (Cons x, Cons y) {
    return ( subsetp(x, y) && subsetp(y, x) ); }

    // combine two lists: (append '(a b) '(c d e))  =  (a b c d e)
public static Cons append (Cons x, Cons y) {
  if (x == null)
     return y;
   else return cons(first(x),
                    append(rest(x), y)); }

    // look up key in an association list
    // (assoc 'two '((one 1) (two 2) (three 3)))  =  (two 2)
public static Cons assoc(Object key, Cons lst) {
  if ( lst == null )
     return null;
  else if ( key.equals(first((Cons) first(lst))) )
      return ((Cons) first(lst));
          else return assoc(key, rest(lst)); }

    public static int square(int x) { return x*x; }
    public static int pow (int x, int n) {
        if ( n <= 0 ) return 1;
        if ( (n & 1) == 0 )
            return square( pow(x, n / 2) );
        else return x * pow(x, n - 1); }

public static Cons formulas = 
    list( list( "=", "s", list("*", new Double(0.5),
                               list("*", "a",
                                list("expt", "t", new Integer(2))))),
          list( "=", "s", list("+", "s0", list( "*", "v", "t"))),
          list( "=", "a", list("/", "f", "m")),
          list( "=", "v", list("*", "a", "t")),
          list( "=", "f", list("/", list("*", "m", "v"), "t")),
          list( "=", "f", list("/", list("*", "m",
                                         list("expt", "v", new Integer(2))),
                               "r")),
          list( "=", "h", list("-", "h0", list("*", new Double(4.94),
                                               list("expt", "t",
                                                    new Integer(2))))),
          list( "=", "c", list("sqrt", list("+",
                                            list("expt", "a",
                                                 new Integer(2)),
                                            list("expt", "b",
                                                 new Integer(2))))),
          list( "=", "v", list("*", "v0",
                               list("-", new Double(1.0),
                                    list("exp", list("/", list("-", "t"),
                                                     list("*", "r", "c"))))))
          );

    // Note: this list will handle most, but not all, cases.
    // The binary operators - and / have special cases.
public static Cons opposites = 
    list( list( "+", "-"), list( "-", "+"), list( "*", "/"),
          list( "/", "*"), list( "sqrt", "expt"), list( "expt", "sqrt"),
          list( "log", "exp"), list( "exp", "log") );

public static void printanswer(String str, Object answer) {
    System.out.println(str +
                       ((answer == null) ? "null" : answer.toString())); }

    // ****** your code starts here ******


public static Cons findpath(Object item, Object cave) { // output: (rest first done)
	if (!consp(cave)) {
		if (item.equals(cave)) {
			return cons("done", null);
		}
		return null;
	}
	if(consp(cave)) {
		Cons first = findpath(item, first((Cons)cave));
		Cons rest = findpath(item, rest((Cons)cave));
		if (first != null) {
			return cons("first", first);
		}
		if (rest != null) {
			return cons("rest", rest);
		}
	}
	return null;
}

public static Object follow(Cons path, Object cave) { // output: gold
	String compass = (String)first(path);
	if(compass.equals("first")) {
		return follow(rest(path), first((Cons)cave)); 
	}
	if(compass.equals("rest")) {
		return follow(rest(path), rest((Cons)cave));
	}
	if(compass.equals("done")) {
		return cave;
	}
	return null;
 }

public static Object corresp(Object item, Object tree1, Object tree2) { // output: music
	Cons treePath = findpath(item, tree1);
	return correspaux(item, tree2, treePath);
}
public static Object correspaux(Object item, Object tree2, Cons treePath) {
	String compass = (String)first(treePath);
	if(compass.equals("first")) {
		return follow(rest(treePath), first((Cons)tree2)); 
	}
	if(compass.equals("rest")) {
		return follow(rest(treePath), rest((Cons)tree2));
	}
	if(compass.equals("done")) {
		return tree2;
	}
	return null;
}

public static Cons solve(Cons e, String v) {
	Object right = rhs(e);
	Object left = lhs(e);
	if (left == null || right == null || !consp(right) && !right.equals(v)) {
		return null;
	}
	if (left.equals(v)) {
		return e;
	}
	if (right.equals(v)) {
		return list("=", right, left);
	}
	Object operator = op((Cons)right);
	Cons solution = null;
	if (operator.equals("+")) {
		solution = solve(list("=",list("-", left, lhs((Cons)right)), rhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		solution = solve(list("=",list("-", left, rhs((Cons)right)), lhs((Cons)right)),v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("-") && rhs((Cons)right) == null) {
		solution = solve(list("=",list("-", left), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("-")) {
		solution = solve(list("=",list("-", lhs((Cons)right), left), rhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		solution = solve(list("=",list("+", left, rhs((Cons)right)), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("*")) {
		solution = solve(list("=",list("/", left, lhs((Cons)right)), rhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		solution = solve(list("=",list("/", left, rhs((Cons)right)), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("/")) {
		solution = solve(list("=",list("/", lhs((Cons)right), left), rhs((Cons)right)), v); // add left to the end of the list
		if (solution != null) {
			return solution;
		}
		solution = solve(list("=",list("*", left, rhs((Cons)right)), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("sqrt")) {
		solution = solve(list("=",list("expt", left, 2), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("expt")) {
		solution = solve(list("=",list("sqrt", left), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("log")) {
		solution = solve(list("=",list("exp", left), lhs((Cons) right)),  v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	if (operator.equals("exp")) {
		solution = solve(list("=",list("log", left), lhs((Cons)right)), v);
		if (solution != null) {
			return solution;
		}
		return null;
	}
	return null;
}

public static Double solveit (Cons equations, String var, Cons values) {

	if (!consp(equations)) {
		return null;
	}
	Object first = op(values);
	Object left = lhs(values);
	//Object right = rhs(values);
	if (occurs(var, first(equations)) && occurs(first((Cons)first), first(equations))
			&& occurs(first((Cons)left), first(equations))){
		Object formula = solve((Cons)first(equations), var);
		return eval(formula, values);
	}
	return solveit(rest(equations), var, values);
}

public static boolean occurs(Object value, Object tree) { 
	if (tree == null) {
		return false;
	}
	if (stringp(tree) || integerp(tree) || floatp(tree)) {
		return value.equals(tree); // found || not found
	}
	return occurs(value, lhs((Cons)tree)) || occurs(value, rhs((Cons) tree));
}	
 
    // Include your functions vars and eval from the previous assignment.
    // Modify eval as described in the assignment.
public static Cons vars (Object expr) {
	if (consp(expr)) {
		Object first = op((Cons)expr);
		Object left = lhs((Cons)expr);
		Object right = rhs((Cons)expr);
		if (numberp(right)) {
			return vars(left);
		}
		if (numberp(left)) {
			return vars(right);
		}
		if (right == null) {
			right = left;
		}
		if (first != null) {
			return union(vars(left),vars(right));
		}
		else {
			return vars(right);
		}
	}
	return cons(expr, null);
}

public static Double eval (Object tree, Cons bindings) { // make more arguments need for the assignment
	if (consp(tree)) {
		 Object operator = op((Cons) tree);
		 Object right = rhs((Cons) tree);
		 Object left = lhs((Cons) tree);
		 if (operator.equals("expt")) {
			 return Math.pow(eval(left, bindings), eval(right, bindings));
		 }
		 else if (operator.equals("*")) {
			 return eval(left, bindings) * eval(right, bindings);
		 }
		 else if (operator.equals("/")) {
			 return eval(left, bindings) / eval(right, bindings);
		 }
		 else if (operator.equals("-") && right == null) {
			 return -eval(left, bindings);
		 }
		 else if (operator.equals("-")) {
			 return eval(left, bindings) - eval(right, bindings);
		 }
		 else if (operator.equals("exp")) {
			 return Math.exp(eval(left, bindings));
		 }
		 else if (operator.equals("sqrt")) {
			 return Math.sqrt(eval(left, bindings));
		 }
		 else if (operator.equals("log")) {
			 return Math.log(eval(left, bindings));
		 }
		 return eval(left, bindings) + eval(right, bindings);
	}
	if (stringp(tree)) {
		while (tree != null) {
			Cons stringBinding = assoc(tree, bindings);
			if (lhs(stringBinding) instanceof String) {
				return eval(rhs(stringBinding), bindings);
			}
			return eval(lhs(stringBinding), bindings);
		}
	}
	if (numberp(tree)) {
		if (tree instanceof java.lang.Integer) {
			return ((Integer) tree).doubleValue();
		}
		return (Double) tree;
	}
	 return 0.0;
}


    // ****** your code ends here ******

    public static void main( String[] args ) {

        Cons cave = list("rocks", "gold", list("monster"));
        Cons path = findpath("gold", cave);
        printanswer("cave = " , cave);
        printanswer("path = " , path);
        printanswer("follow = " , follow(path, cave));

        Cons caveb = list(list(list("green", "eggs", "and"),
                               list(list("ham"))),
                          "rocks",
                          list("monster",
                               list(list(list("gold", list("monster"))))));
        Cons pathb = findpath("gold", caveb);
        printanswer("caveb = " , caveb);
        printanswer("pathb = " , pathb);
        printanswer("follow = " , follow(pathb, caveb));

        Cons treea = list(list("my", "eyes"),
                          list("have", "seen", list("the", "light")));
        Cons treeb = list(list("my", "ears"),
                          list("have", "heard", list("the", "music")));
        printanswer("treea = " , treea);
        printanswer("treeb = " , treeb);
        printanswer("corresp = " , corresp("light", treea, treeb));
        System.out.println("formulas = ");
        Cons frm = formulas;
        Cons vset = null;
        while ( frm != null ) {
            printanswer("   "  , ((Cons)first(frm)));
            vset = vars((Cons)first(frm));
            while ( vset != null ) {
                printanswer("       "  ,
                    solve((Cons)first(frm), (String)first(vset)) );
                vset = rest(vset); }
            frm = rest(frm); }

        Cons bindings = list( list("a", (Double) 32.0),
                              list("t", (Double) 4.0));
        printanswer("Eval:      " , rhs((Cons)first(formulas)));
        printanswer("  bindings " , bindings);
        printanswer("  result = " , eval(rhs((Cons)first(formulas)), bindings));

        printanswer("Tower: " , solveit(formulas, "h0",
                                            list(list("h", new Double(0.0)),
                                                 list("t", new Double(4.0)))));

        printanswer("Car: " , solveit(formulas, "a",
                                            list(list("v", new Double(88.0)),
                                                 list("t", new Double(8.0)))));
        
        printanswer("Capacitor: " , solveit(formulas, "c",
                                            list(list("v", new Double(3.0)),
                                                 list("v0", new Double(6.0)),
                                                 list("r", new Double(10000.0)),
                                                 list("t", new Double(5.0)))));

        printanswer("Ladder: " , solveit(formulas, "b",
                                            list(list("a", new Double(6.0)),
                                                 list("c", new Double(10.0)))));


      }

}
