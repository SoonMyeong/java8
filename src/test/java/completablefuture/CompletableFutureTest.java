package completablefuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CompletableFutureTest {

    @DisplayName("CompletableFuture #1")
    @Test
    void completablefuture_test_1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Soon");

        System.out.println(future.get());
    }

    @DisplayName("CompletableFuture #2")
    @Test
    void completablefuture_test_2() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(()-> {
            System.out.println("Hello " + Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("CompletableFuture #3")
    @Test
    void completablefuture_test_3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        System.out.println(future.get());
    }

    @DisplayName("CompletableFuture #4 Callback")
    @Test
    void completablefuture_test_4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenApply((s)-> {
            System.out.println("Hello " + Thread.currentThread().getName());
            return s.toUpperCase();
        });

        System.out.println(future.get());
    }

    @DisplayName("CompletableFuture #5 Callback")
    @Test
    void completablefuture_test_5() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenAccept((s)-> {
            System.out.println("Hello " + Thread.currentThread().getName());
            System.out.println(s.toUpperCase());
        });

        future.get();
    }


    @DisplayName("CompletableFuture #6 Callback")
    @Test
    void completablefuture_test_6() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).thenRun(()-> {
            System.out.println(Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("CompletableFuture #7 기본 값 ForkJoinPool -> Executor 로 바꾸고 싶을 때")
    @Test
    void completablefuture_test_7() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }, executorService).thenRun(()-> {
            System.out.println(Thread.currentThread().getName());
        });

        future.get();
    }

    @DisplayName("CompletableFuture #8 여러 비동기작업 조합하기")
    @Test
    void completablefuture_test_8() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> future = hello.thenCompose(this::getWorld);

        System.out.println(future.get());


    }

    private CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() ->{
            System.out.println("World " + Thread.currentThread().getName());
            return message + " World";
        });
    }


    @DisplayName("CompletableFuture #9 두 작업을 독립적으로 실행")
    @Test
    void completablefuture_test_9() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() ->{
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        CompletableFuture<String> future = hello.thenCombine(world, (h, w) -> h + " " + w);

        System.out.println(future.get());
    }


    @DisplayName("CompletableFuture #10 두 작업을 독립적으로 실행, 블로킹 없음")
    @Test
    void completablefuture_test_10() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() ->{
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        List<CompletableFuture<String>> futures = Arrays.asList(hello,world);

        CompletableFuture<List<String>> results = CompletableFuture.allOf(hello, world)
                .thenApply(v-> {
                   return futures.stream()
                           .map(CompletableFuture::join) //join 과 get 은 같지만 join 은 unCheckedException 발생시켜서 바로 쓸 수 있음, catch 없이도
                           .collect(Collectors.toList());
                });

        results.get().forEach(System.out::println);
    }

    @DisplayName("CompletableFuture #11 여러 작업 중 빨리 끝나는 걸로")
    @Test
    void completablefuture_test_11() throws ExecutionException, InterruptedException {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> world = CompletableFuture.supplyAsync(() ->{
            System.out.println("World " + Thread.currentThread().getName());
            return "World";
        });

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.anyOf(hello, world).thenAccept(System.out::println);

        voidCompletableFuture.get();
    }

    @DisplayName("CompletableFuture #12 예외처리")
    @Test
    void completablefuture_test_12() throws ExecutionException, InterruptedException {
        boolean throwError = true;
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            if(throwError) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(ex-> {
            System.out.println(ex);
            return "Error";
        });

        System.out.println(hello.get());
    }

    @DisplayName("CompletableFuture #13 예외처리2")
    @Test
    void completablefuture_test_13() throws ExecutionException, InterruptedException {
        boolean throwError = false;
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() ->{
            if(throwError) {
                throw new IllegalArgumentException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((result,ex)-> {
            if(ex !=null) {
                System.out.println(ex);
                return "Error";
            }
            return result;
        });

        System.out.println(hello.get());
    }


}
