package callable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CallableTest {

    @DisplayName("Callable 테스트 #1")
    @Test
    void callable_test_1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = ()-> {
            Thread.sleep(2000L);
            return "Hello";
        };

        Future<String> helloFuture = executorService.submit(hello);
        System.out.println("Started..");

        helloFuture.get(); //blocking

        System.out.println("End...");

        executorService.shutdown();
    }

    @DisplayName("Callable 테스트 여러 건 #2")
    @Test
    void callable_test_2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = ()-> {
            Thread.sleep(2000L);
            return "Hello";
        };
        Callable<String> hello2 = ()-> {
            Thread.sleep(3000L);
            return "Hello2";
        };
        Callable<String> hello3 = ()-> {
            Thread.sleep(1000L);
            return "Hello3";
        };
        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello,hello2,hello3));
        System.out.println("Started..");

        futures.forEach(s-> {
            try {
                System.out.println(s.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println("End...");

        executorService.shutdown();
    }

    @DisplayName("Callable 테스트 여러 건 #3")
    @Test
    void callable_test_3() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Callable<String> hello = ()-> {
            Thread.sleep(2000L);
            return "Hello";
        };
        Callable<String> hello2 = ()-> {
            Thread.sleep(3000L);
            return "Hello2";
        };
        Callable<String> hello3 = ()-> {
            Thread.sleep(1000L);
            return "Hello3";
        };
        String result = executorService.invokeAny(Arrays.asList(hello,hello2,hello3));
        System.out.println("Started..");
        System.out.println(result);

        System.out.println("End...");

        executorService.shutdown();
    }

}
