// libtest.java      GSN    03 Oct 08; 21 Feb 12; 26 Dec 13
// 

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

@SuppressWarnings("unchecked")
public class libtest {

    // ****** your code starts here ******


public static Integer sumlist(LinkedList<Integer> lst) {
	Integer sum = 0;
	if (lst == null) {
		return sum;
	}
	Iterator<Integer> num = lst.iterator();
	while (num.hasNext()) {
	  sum += num.next();
	}
	return sum;
}

public static Integer sumarrlist(ArrayList<Integer> lst) {
	Integer sum = 0;
	if (lst == null) {
		return sum;
	}
	for (int i = 0; i < lst.size(); i++) { 
		sum += lst.get(i);
	}
	return sum;
}

public static LinkedList<Object> subset (Predicate p, LinkedList<Object> lst) {
	LinkedList<Object> answer = new LinkedList<Object>();
	if (lst == null) {
		return answer;
	}
	for (int i = 0; i < lst.size(); i++) {
		Object num = lst.get(i);
		if (p.pred(num)) {
			answer.add (lst.get(i));
		}
	}
	return answer;
}

public static LinkedList<Object> dsubset (Predicate p, LinkedList<Object> lst) { // use Iterator
	if (lst == null) {
		return lst;
	}
	for (int i = 0; i < lst.size(); i++) {
		Object num = lst.get(i);
		if (p.pred(num) == false) {
			lst.remove(lst.get(i));
		}
	}
	return lst;
}

public static Object some (Predicate p, LinkedList<Object> lst) {
	if (lst == null) {
		return null;
	}
	for (int i = 0; i < lst.size(); i++) {
		Object num = lst.get(i);
		if (p.pred(num)) {
			return lst.get(i);
		}
	}
	return null;
}

public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) {
	if (lst == null) {
		return null;
	}
	LinkedList<Object> answer = new LinkedList<Object>();
	Iterator<Object> index = lst.iterator();
	while (index.hasNext()) {
		Object newNum = f.fn(index.next());
		answer.add(newNum);
	}
	return answer;
}

public static LinkedList<Object> merge (LinkedList<Object> lsta, LinkedList<Object> lstb) {
	int i = 0, j = 0;
	LinkedList<Object> answer = new LinkedList<Object>();
	if (lsta == null) {
		return lstb;
	}
	if (lstb == null) {
		return lsta;
	}
	Iterator first = lsta.iterator();
	Iterator second = lstb.iterator();
	Object firstNext = first.hasNext() ? first.next() : null;
	Object secondNext = second.hasNext() ? second.next() : null;
	
	while (firstNext != null && secondNext != null) {
		if (((Comparable) firstNext).compareTo(secondNext) < 0) {
			answer.add(firstNext); 
			firstNext = first.hasNext() ? first.next() : null;
		}
		else {
			answer.add(secondNext);
			secondNext = second.hasNext() ? second.next() : null;
		}
	}
	if (firstNext != null) {
		answer.add(firstNext);
		while (first.hasNext()) {
			answer.add(first.next());
		}
	}
	if (secondNext != null) {
		answer.add(secondNext);
		while (second.hasNext()) {
			answer.add(second.next());
		}
	}
	
	
	
	/*while (i < lsta.size() && j < lstb.size()) {
		int order = ((Comparable) lsta.get(i)).compareTo(lstb.get(j));
		if (order < 0) {
			answer.add(lsta.get(i)); 
			i++; //add x[i] to new array
		}
		else {
			answer.add(lstb.get(j));
			j++; // add y[j] to new array
		}
	}
	int k = lsta.size() - i + 1;
	while (k != lsta.size()) {
		answer.add(lsta.get(k));
		k++;
	}*/
	return answer;
}

public static LinkedList<Object> sort (LinkedList<Object> lst) {
	LinkedList<Object> answer = new LinkedList<Object>();
	if (lst.size() == 1) {
		return lst;
	}
	
	LinkedList<Object> firstHalf = sort(new LinkedList<Object>(lst.subList(0, lst.size()/2)));
	LinkedList<Object> secondHalf = sort(new LinkedList<Object>(lst.subList(lst.size()/2, lst.size())));
	Iterator first = firstHalf.iterator();
	Iterator second = secondHalf.iterator();
	Object firstNext = first.hasNext() ? first.next() : null;
	Object secondNext = second.hasNext() ? second.next() : null;
	
	while (firstNext != null && secondNext != null) {
		if (((Comparable) firstNext).compareTo(secondNext) < 0) {
			answer.add(firstNext); 
			firstNext = first.hasNext() ? first.next() : null;
		}
		else {
			answer.add(secondNext);
			secondNext = second.hasNext() ? second.next() : null;
		}
	}
	// load any remaining elements two sublists on to answer
	if (firstNext != null) {
		answer.add(firstNext);
		while (first.hasNext()) {
			answer.add(first.next());
		}
	}
	if (secondNext != null) {
		answer.add(secondNext);
		while (second.hasNext()) {
			answer.add(second.next());
		}
	}
	return answer;
}

public static LinkedList<Object> intersection (LinkedList<Object> lsta, LinkedList<Object> lstb) {
	LinkedList<Object> answer = new LinkedList<Object>();
	LinkedList<Object> sorta = sort(lsta);
	LinkedList<Object> sortb = sort(lstb);
	int i = 0, j = 0;
	while (i < sorta.size() && j < sortb.size()) {
		int order = ((Comparable) sorta.get(i)).compareTo(sortb.get(j));
		if (order < 0) {
			i++; //add x[i] to new array
		}
		else if (order > 0) {
			j++; // add y[j] to new array
		}
		else {
			answer.add(sorta.get(i));
			i++;
			j++;
		}
	}
	
	return answer;
}

public static LinkedList<Object> reverse (LinkedList<Object> lst) {
	LinkedList<Object> answer = new LinkedList<Object>();
	if (lst == null) {
		return answer;
	}
	Iterator<Object> reverse = lst.descendingIterator();
	while (reverse.hasNext()) {
		answer.add(reverse.next());
	}
	return answer;
}

    // ****** your code ends here ******

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst);
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb);
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc);
        System.out.println("subset = " + subset(myp, lstc));

        System.out.println("lstc     = " + lstc);
        System.out.println("dsubset  = " + dsubset(myp, lstc));
        System.out.println("now lstc = " + lstc);

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd);
        System.out.println("some = " + some(myp, lstd));

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return new Integer( (Integer) x + 2); }};

        System.out.println("mapcar = " + mapcar(myf, lstd));

        LinkedList<Object> lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(6));
        lste.add(new Integer(9));
        lste.add(new Integer(11));
        lste.add(new Integer(23));
        System.out.println("lste = " + lste);
        LinkedList<Object> lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        System.out.println("lstf = " + lstf);
        System.out.println("merge = " + merge(lste, lstf));

        lste = new LinkedList<Object>();
        lste.add(new Integer(1));
        lste.add(new Integer(3));
        lste.add(new Integer(5));
        lste.add(new Integer(7));
        System.out.println("lste = " + lste);
        lstf = new LinkedList<Object>();
        lstf.add(new Integer(2));
        lstf.add(new Integer(3));
        lstf.add(new Integer(6));
        lstf.add(new Integer(6));
        lstf.add(new Integer(7));
        lstf.add(new Integer(10));
        lstf.add(new Integer(12));
        lstf.add(new Integer(17));
        System.out.println("lstf = " + lstf);
        System.out.println("merge = " + merge(lste, lstf));

        LinkedList<Object> lstg = new LinkedList<Object>();
        lstg.add(new Integer(39));
        lstg.add(new Integer(84));
        lstg.add(new Integer(5));
        lstg.add(new Integer(59));
        lstg.add(new Integer(86));
        lstg.add(new Integer(17));
        System.out.println("lstg = " + lstg);

        System.out.println("intersection(lstd, lstg) = "
                           + intersection(lstd, lstg));
        System.out.println("reverse lste = " + reverse(lste));

        System.out.println("sort(lstg) = " + sort(lstg));

   }
}