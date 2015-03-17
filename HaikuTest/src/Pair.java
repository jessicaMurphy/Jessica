// the pair class is used to store words and their syllable counts

public class Pair {
    String word;
    int numSyll;

    public Pair (String word, int numSyll) {
	this.word = word;
	this.numSyll = numSyll;
    }

    public String toString () {
	return word + " " + numSyll;
    }
}
