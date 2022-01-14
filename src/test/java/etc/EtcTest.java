package etc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Coffee("아메리카노")
@Coffee("카레라떼")
@Coffee("카페모카")
public class EtcTest {

    @DisplayName("애노테이션 #1")
    @Test
    void etc_test_1() {
        Coffee[] annotationsByType = EtcTest.class.getAnnotationsByType(Coffee.class);
        Arrays.stream(annotationsByType).forEach(System.out::println);
    }

    @DisplayName("애노테이션 #2 컨테이너타입으로 받기")
    @Test
    void etc_test_2() {
        CoffeeContainer annotation = EtcTest.class.getAnnotation(CoffeeContainer.class);
        Arrays.stream(annotation.value()).forEach(System.out::println);
    }

    @DisplayName("병렬 정렬, 오우?!")
    @Test
    void etc_sort() {
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        IntStream.range(0,size).forEach(i-> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sort : " + (System.nanoTime()-start));

        IntStream.range(0,size).forEach(i-> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers); //병합정렬과 비슷하게 스레드를 더 사용해서 정렬함
        System.out.println("parallel sort : " + (System.nanoTime()-start));
    }

}
