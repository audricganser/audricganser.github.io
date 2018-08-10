/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
 *          01 Feb 12; 08 Feb 12; 22 Sep 13; 26 Dec 13
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;   // traditional name for first
    private Cons cdr;     // "could-er", traditional name for rest
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // Cons is the data type.
    // cons() is the method that makes a new Cons and puts two pointers in it.
    // cons("a", null) = (a)
    // cons puts a new thing on the front of an existing list.
    // cons("a", list("b","c")) = (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // consp is true if x is a Cons, false if null or non-Cons Object
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first returns the first thing in a list.
    // first(list("a", "b", "c")) = "a"
    // safe, first(null) = null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing.
    // rest(list("a", "b", "c")) = (b c)
    // safe, rest(null) = null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }

    // second thing in a list
    // second(list("+", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list
    // third(list("+", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively change the first() of a cons to be the specified object
    // setfirst(list("a", "b", "c"), 3) = (3 b c)
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively change the rest() of a cons to be the specified Cons
    // setrest(list("a", "b", "c"), null) = (a)     
    // setrest(list("a", "b", "c"), list("d","e")) = (a d e)
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of the specified items
    // list("a", "b", "c") = (a b c)
    // list() = null
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }
 

    // convert a list to a string in parenthesized form for printing
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    // ****** your code starts here ******

    // add up elements of a list of numbers
public static int sum (Cons lst) {
	return (lst == null) ? 0 : (int)first(lst) + sum(rest(lst));
}
    // mean = (sum x[i]) / n  
public static double mean (Cons lst) {
	int length = 0;
	int sum = 0;
	while (lst != null) {
		int number1 = (int)first(lst);
		sum += number1;
		length += 1;
		lst = rest(lst);
	}
	return (double)(sum)/length;
}
    // square of the mean = mean(lst)^2  
    // mean square = (sum x[i]^2) / n  
public static double meansq (Cons lst) {            
	int length = 0;
	int sum = 0;
	while (lst != null) {
		int number1 = (int)first(lst);
		sum += number1 * number1;
		length += 1;
		lst = rest(lst);
	}
	return (double)(sum) / length;
}

public static double variance (Cons lst) {            
	double mean = mean(lst);
	double subtractfrom = 0;
	int length = 0;
	while (lst != null) {
		int number1 = (int)first(lst);
		subtractfrom += Math.pow(number1 - mean, 2);
		length += 1;
		lst = rest(lst);
	}
	return (double)subtractfrom/ length;
}

public static double stddev (Cons lst) {
	double variation = variance(lst);
	double deviation = Math.sqrt(variation);
	return deviation;
}

public static double sine (double x) {
	return auxsine(x, x, x, 0, 20); 
}
public static double auxsine (double x, double prev, double sum, int i, int max) {
	if (i == max) {
		return sum;
	}
	double next = (x * x) / (2 * i + 3) / (2 * i + 2) * prev;
	if (i % 2 == 0) {
		return auxsine(x, next, sum - next, i + 1, max);
	}
	return auxsine(x, next, sum - next , i + 1, max);
}
public static Cons nthcdr (int n, Cons lst) {
	if (lst == null || n == 0) {
		return lst;
	}
		return nthcdr(n - 1, rest(lst));
}

public static Object elt (Cons lst, int n) {
	return auxelt (lst, n, 0);
}
public static Object auxelt (Cons lst, int n, int answer) {
	if (lst == null || n == 0) {
		return answer;
	}
		return auxelt(rest(lst), n - 1 , (int)first(rest(lst)));
}

public static double interpolate (Cons lst, double x) {
	return auxinterpolate(lst, x, 0);
}
public static double auxinterpolate (Cons lst, double x,int count) {
	int numerator = (int) x;
	double denominator = x % 1;
	if (lst == null) {
		return 0;
	}
	if (count >= numerator) {
		return (int)first(lst) + denominator * ((int)first(rest(lst)) - (int)first(lst));
	}
	return auxinterpolate(rest(lst), x, ++count);
}

public static int sumtr (Cons lst) {
	return auxsum(lst);
}
public static int auxsum(Object lst) {
	if (lst == null) {
		return 0;
	}
	if (consp(lst)) {
		return auxsum(first((Cons) lst)) + auxsum(rest((Cons) lst));
	}
	return (int)lst;
}

    // use auxiliary functions as you wish.
public static Cons subseq (Cons lst, int start, int end) {
	if (start > 0 && lst != null) { // skipping until start
		return subseq(rest(lst), start-1, end-1);
	}
	if (end > 0 && lst != null) { // adding to newList
		return cons(first(lst), subseq(rest(lst), start-1, end-1));
	}
	return null;
}

public static Cons posfilter (Cons lst) {
	if (lst == null) {
		return null;
	}
	if ((int)first(lst) >= 0) {
		return cons(first(lst), posfilter(rest(lst)));
	}
	return posfilter(rest(lst));
}

public static Cons subset (Predicate p, Cons lst) {
	if (lst == null) {
		return null;
	}
	if (p.pred((int)first(lst))) {
		return cons(first(lst), subset(p, rest(lst)));
	}
	return subset(p, rest(lst));
}

public static Cons mapcar (Functor f, Cons lst) {
	if (lst == null) {
		return null;
	}
	return cons(f.fn(first(lst)), mapcar(f, rest(lst)));
}

public static Object some (Predicate p, Cons lst) {
	if (lst == null) {
		return null;
	}
	if (p.pred((int)first(lst))) {
		return first(lst);
	}
	return some(p, rest(lst));
}

public static boolean every (Predicate p, Cons lst) {
	if (lst == null) {
		return true;
	}
	if (p.pred((int)first(lst))) {
		return false;
	}
	return every(p, rest(lst));
}

public static Cons binomial(int n) {
	if (n == 0) {
		return list(1);
	}
	Cons list = binomial(n - 1);
	Cons newList = list(1);
	Object first = first(list);
	Object second = first(rest(list));
	while (first != null && second != null) {
		newList = cons((Integer)first + (Integer)second, newList);
		list = rest(list);
		first = first(list);
    	second = first(rest(list));
	}
	return cons(1, newList);
	
}

    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons mylist =
            list(95, 72, 86, 70, 97, 72, 52, 88, 77, 94, 91, 79,
                 61, 77, 99, 70, 91 );
        System.out.println("mylist = " + mylist.toString());
        System.out.println("sum = " + sum(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist));
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.print("nthcdr 5 = ");
        System.out.println(nthcdr(5, mylist));
        System.out.print("nthcdr 18 = ");
        System.out.println(nthcdr(18, mylist));
        System.out.println("elt 5 = " + elt(mylist,5));

        Cons mylistb = list(0, 30, 56, 78, 96);
        System.out.println("mylistb = " + mylistb.toString());
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom.toString());
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(1, list(2, 3), list(list(list(list(4)),
                                                     list(5)),
                                                6));
        System.out.println("mylistc = " + mylistc.toString());
        System.out.println("sumtr = " + sumtr(mylistc));
        Cons mylistcc = list(1, list(7, list(list(2), 3)),
                             list(list(list(list(list(list(list(4)))), 9))),
                             list(list(list(list(5), 4), 3)),
                             list(6));
        System.out.println("mylistcc = " + mylistcc.toString());
        System.out.println("sumtr = " + sumtr(mylistcc));

        Cons mylistd = list(0, 1, 2, 3, 4, 5, 6 );
        System.out.println("mylistd = " + mylistd.toString());
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(3, 17, -2, 0, -3, 4, -5, 12 );
        System.out.println("myliste = " + myliste.toString());
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return  (Integer) x + 2; }};

        System.out.println("mapcar = " + mapcar(myf, mylistd).toString());

        System.out.println("some = " + some(myp, myliste).toString());

        System.out.println("every = " + every(myp, myliste));

      }
}
