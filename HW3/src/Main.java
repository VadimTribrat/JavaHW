import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Site st = new Site(10);
        int sum = 0;
        ExecutorService fixedPool[] = new ExecutorService[50];
        for (int i = 0; i<50; ++i)
            fixedPool[i] = Executors.newFixedThreadPool(1);
        for (int j = 0; j<50; j++)
            for (int i = 0; i < 100; ++i) {
                fixedPool[j].execute(st.getPages(i%10)::increment);
        }
        for (int i = 0; i<50; ++i)
            fixedPool[i].shutdown();
        for (int i = 0; i<50; ++i)
            while (!fixedPool[i].awaitTermination(10, TimeUnit.SECONDS));
        for (int i = 0; i<10; ++i)
        {
//            System.out.println(st.getPages(i).getName() + " " + st.getPages(i).getCounter());
            sum += st.getPages(i).getCounter();
        }
        System.out.println(sum);
    }
}
