import java.io.*;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class FindHaiku
{
    Hashtable syllableTable;      	    // words and syllable counts
    ArrayUnsortedList<String> current;  // the current line of pairs
    int syllables;            			// number of syllables in the current line
    ArrayUnsortedList<String> lines;    // accumulated lines
    BufferedReader fileIn;       		// file reading
    StringTokenizer tokens;  			// tokens read


   
    public FindHaiku() 
    {
    	syllableTable = new Hashtable();
    	ListInterface<String> current = new ArrayUnsortedList();
    	syllables = 0;
    	ListInterface<String> lines = new ArrayUnsortedList();

    	String filename ="bin/TheBrothersGrimmFairytales.txt";
    	try
    	{
    		readDictionary(filename);
    	}
    	catch(Exception e)
    	{
    		System.out.println ("Couldn't read the dictionary: " + filename);
    		e.printStackTrace();
    		System.exit (-1);
    	}
    }

    //--------------------------------------------------------MAIN--------------------------------------------------------------------------------

    public static void main(String[] args)
    {
    	String filename = "bin/TheBrothersGrimmFairytales.txt";
    	
    	if(filename.isEmpty())
    		if(args.length == 0) 
    	{
            System.out.println("You must specify a filename.");
            System.exit(0);
        }
    	


        FindHaiku haiku = new FindHaiku();

        int[] foundOne = {5, 7, 5};
        haiku.getForms (filename, foundOne);
    }//end main
    
    
    
    
    
    
 //--------------------------------------------------------METHODS--------------------------------------------------------------------------------   
    
    //read file and build syllableTable
    public void readDictionary(String filename) throws FileNotFoundException, IOException 
    {
	   	FileReader fileReader = new FileReader(filename);
		BufferedReader in = new BufferedReader(fileReader);
		String string;
		while((string = in.readLine()) != null) // this is the standard pattern for reading a file line by line
		{
			System.out.println("DEBUG: " + string); // remove later -- make sure every line is being read for now
			// removed the null check since its been moved to the loop condition
			if (string.length() > 0 && string.charAt(0) == '#') continue;
			wordEntry (string);
		}
    }

    
    //make an entry in syllableTable
    public void wordEntry(String string)
    {
        StringTokenizer sToke = new StringTokenizer(string);
        if(!sToke.hasMoreTokens())
        {
        	return;
        }
        
        String word = sToke.nextToken().toLowerCase();

        int numSyll = 0;
        while(sToke.hasMoreTokens())
        {
            String number = sToke.nextToken();
            if (hasDigit(number))
            {
            	numSyll++;
            }
        }
        System.out.println(word + " " + numSyll);
        syllableTable.put(word, new Integer(numSyll));
    }

    
    //return true if the string contains a digit
    public boolean hasDigit(String string)
    {
    	for (int i=0; i<string.length(); i++) 
    	{
    		if (Character.isDigit(string.charAt (i)))
    		{
    			return true;
    		}
    	}	
    	return false;
    }

    //get the next word-syllable pair from the file
    public Pair getPair()
    {
    	String word = getWord();
    	Integer numSyll = (Integer)syllableTable.get(clean(word));
    	if(numSyll == null)
    	{
    		return new Pair(word, -1);
    	} 
    	else 
    	{
    		return new Pair(word, numSyll.intValue());
    	}
    }

    
    //clean string by tokenizing and taking first token that begins with a letter
    public String clean(String string)
    {
    	StringTokenizer st = new StringTokenizer(string,"0123456789@#$%^&*\"`'()<>[]{}.,:;?!+=/\\");
        while (st.hasMoreTokens()) 
        {
            String word = st.nextToken();
            if(Character.isLetter(word.charAt(0))) 
            {
        		return word.toLowerCase();
            }

        }	    
        return "";
    }

    
    //get the next word from the file
    public String getWord()
    {
    	while(tokens == null || tokens.hasMoreTokens() == false) 
    	{
    		tokens = getTokens();
    	}
    	return tokens.nextToken();
    }

    
    
    
    //read a line from the file and tokenize it
    public StringTokenizer getTokens()
    {
    	try 
    	{
    		String s = fileIn.readLine();
    		if(s == null)
    		{
    			System.exit(0);
    		}
    		return new StringTokenizer(s, " -");
    	} 
    	catch(Exception e) 
    	{
    		System.out.println("I/O Error.");
    		System.exit(-1);
    	}
    	return null;
    }

    
    
    //remove the first word from the current line
    public void shift() 
    {
    	if(current.size() > 0)
    	{
    		current.remove(0);
    		//Pair  p = (Pair) v.;// v.remove(0);
    		//syllables -= p.numSyll;
    	}
    }
    


    
    // getSyllables: keep adding word pairs to the current line
    // until the total syllables gets to numSyll
    public void getSyllables(int numSyll) 
    {
    	while(syllables < numSyll)
    	{
    		Pair pair = getPair();
    		if (pair.numSyll == -1)
    		{
    			syllables = 0;
    			current.removeAllElements();
    			return;
    		}
    		else
    		{
    			syllables += pair.numSyll;
    			current.addRear(pair);
    		}
    	}
    }

    
    
    // getForm: fileInd the next set of words that can be assembled
    // into lines with the given form.  The form is an array of
    // integers specifying the number of syllables in each line
    public void getForm(int[] possHaiku) 
    {
    	for(int i = 0; i < possHaiku.length; i++)
    	{
    		getSyllables(possHaiku[i]);
    		if (syllables == 0 || syllables > possHaiku[i] || lines.size() == 0 )
    		{
    			// word with unknown syllables or too many syllables, need to start again
    			shift();
    			lines.removeAllElements();
    			i = 0;

    		}
    		else 
    		{
    			//the current line is good, save it and start the next
    			lines.addRear(current);
    			current = new ArrayUnsortedList();
    			syllables = 0;
    			i++;
    		}
    	}
    }
    
  
 
    
    
    //get all the forms from the given file.
    public void getForms(String filename, int[] form) 
    {
    	try 
    	{
    		FileReader fileReader = new FileReader(filename);
    		BufferedReader fileIn = new BufferedReader(fileReader);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error opening file: " + filename);
    		System.exit(-1);
    	}
    	while(true) 
    	{
    		getForm(form);
    		lines.toString();
    		lines.removeAllElements();
    	}
    }
    
    
    
}//end class




