import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 邱润泽 bullock
 */
public class PoolTest {


    @Test
    public void testOldThreadStart() {

        for (int request = 0; request < 100; request++) {

            new Thread(() -> {
                System.out.println("转换文档 start!!!!!");
                try {
                    Thread.sleep(100L * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("转换文档 end!!!!!");
            }).start();
        }

        try {
            Thread.sleep(1000L * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    ExecutorService executors = Executors.newFixedThreadPool(10);
    @Test
    public void testNewThreadStart() throws InterruptedException {

        for (int request = 0; request < 100; request++) {

            executors.execute(() -> {
                System.out.println("转换文档 start!!!!!");
                try {
                    Thread.sleep(100L * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("转换文档 end!!!!!");
            });
        }
        try {
            Thread.sleep(1000L * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
