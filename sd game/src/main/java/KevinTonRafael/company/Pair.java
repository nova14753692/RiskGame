package KevinTonRafael.company;

public class Pair<u, v> {

    private u first;
    private v second;

    /**
     * Constructs a new <code>Pair</code> with the given values.
     *
     * @param first  the first element
     * @param second the second element
     */
    public Pair(u first, v second) {

        this.first = first;
        this.second = second;
    }

    public u getFirst() {
        return first;
    }

    public void setFirst(u first) {
        this.first = first;
    }

    public v getSecond() {
        return second;
    }

    public void setSecond(v second) {
        this.second = second;
    }
}