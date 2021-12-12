import java.sql.Array;
import java.util.ArrayList;

public class Site {
    private Page[] pages;
    public Site(int n)
    {
        pages = new Page[n];
        for (int i = 0; i<n; ++i)
        {
            String str = "Site";
            str += i;
            pages[i] = new Page(str);
        }

    }
    public Page getPages(int i)
    {
        return pages[i];
    }
}
