package Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

    @DisplayName("Executor #1")
    @Test
    void executor_test_1() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //종료 안하면 계속 살아있음, (대기)
        executorService.execute(() -> System.out.println("Thread " + Thread.currentThread().getName()));
        executorService.shutdown(); //graceful kill
//        executorService.shutdownNow(); //바로 Kill
    }

    @DisplayName("Executor #2")
    @Test
    void executor_test_2() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(getRunnable("Soon"));
        executorService.execute(getRunnable("World"));
        executorService.execute(getRunnable("Apple"));
        executorService.execute(getRunnable("Java"));
        executorService.execute(getRunnable("Github"));

        executorService.shutdown();
    }

    @DisplayName("Executor #3")
    @Test
    void executor_test_3() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(getRunnable("Hello"),1,2, TimeUnit.SECONDS);
    }


    private Runnable getRunnable(String message) {
        return () -> System.out.println(message +" " + Thread.currentThread().getName());
    }



}
