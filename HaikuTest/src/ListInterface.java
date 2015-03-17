
public interface ListInterface<T> {
	int size() ;
	// Returns the number of elements on this list.
	
	void add(T element) ;
	// Adds element to this list.
	
	boolean contains (T element) ;
	// Returns true if this list contains an element e such that
	// e. equals(element) ; otherwise,  returns false.
	
	boolean remove (T element) ;
	// Removes an element e from this list such that e. equals(element)
	// and returns true; if no such element exists, returns false.
	
	T get(T element) ;
	// Returns an element e from this list such that e. equals(element) ;
	// if no such element exists, returns null.
	
	String toString() ;
	// Returns a nicely formatted string that represents this list.

	void reset() ;
	// Initializes current position for an iteration through this list,
	// to the first element on this list.
	
	T getNext() ;
	// Preconditions: The list is not empty
//	                The list has been reset
//	                The list has not been modified since most recent reset
	//   
	// Returns the element at the current position on this list.
	// If the current position is the last element, then it advances the value
	// of the current position to the first element; otherwise, it advances
	// the value of the current position to the next element.	
	
	
	public int removeAll (T element);
	//removes all instances of the element and returns the number removed
	
	void addRear(T element);
	// adds to end of list 


}
