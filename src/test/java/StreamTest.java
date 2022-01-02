import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {


    /**
     *
     * 1. 스트림은 데이터 소스를 변경 하는 것이 아니다.
     *
     */
    @DisplayName("Steam API 는 데이터 소스를 변경하지 않음")
    @Test
    void streamApiTest() {
        List<String> names = Arrays.asList("soon","world","stream","test");

        Stream<String> stringStream = names.stream().map(String::toUpperCase);
        System.out.println("[Stream API 사용 한 결과]");
        stringStream.forEach(System.out::println);

        System.out.println("-------스트림 API 를 사용했지만, 데이터 소스(원본 컬렉션)에는 변화가 없음!");
        System.out.println("[원본 출력]");
        names.forEach(System.out::println);
    }


    @DisplayName("중개 오퍼레이션과 종료 오퍼레이션")
    @Test
    void streamApiTest2() {
        List<String> names = Arrays.asList("soon","world","stream","test");

        names.stream().map((s)-> {
            System.out.println(s);
            return s.toUpperCase();
        }); //스트림만 정의 했을 경우 (중개 오퍼레이션) 출력되지 않음!, 종료 오퍼레이션이 아직 설정 되지 않아서

        List<String> collect = names.stream().map((s)-> {
            System.out.println(s);
            return s.toUpperCase();
        }).collect(Collectors.toList()); //종료 오퍼레이션 collect 추가

        collect.forEach(System.out::println); // 스트림 안의 소문자 출력과 스트림 결과(대문자) 출력 잘 됨.


        System.out.println("-------스트림 API 를 사용했지만, 데이터 소스(원본 컬렉션)에는 변화가 없음!");
        System.out.println("[원본 출력]");
        names.forEach(System.out::println);

    }


    /**
     *
     * parallel 로 동작 시 내부적으로 ForkJoinPool 이 사용됨을 알 수 있다.
     * 그냥 스트림으로 쓰면 main 스레드만 쓰인다는거
     * 페러럴 스트림의 경우 절대적으로 성능이 좋아진다고 표현 하기 어렵다. (스레드를 만드는 만큼 비용이 드는 것이므로)
     *
     *
     */
    @DisplayName("손쉬운 병렬처리")
    @Test
    void streamApiParallel() {
        List<String> names = Arrays.asList("soon","world","stream","test");

        List<String> collect = names.parallelStream().map((s)-> {
            System.out.println(s + " [" + Thread.currentThread().getName() + "]");
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

}
