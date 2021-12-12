public class Page {
    private String PageName;
    private int counter;

    public Page(String str)
    {
        counter = 0;
        PageName = str;
    }
    public String getName()
    {
        return PageName;
    }
    public synchronized void increment()
    {
        counter++;
    }
    public int getCounter()
    {
        return counter;
    }
}
