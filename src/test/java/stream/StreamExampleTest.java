package stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExampleTest {

    private List<OnlineClass> onlineClassList;
    private List<OnlineClass> javaClassList;
    private List<List<OnlineClass>> soonEvents;

    @BeforeEach
    void init() {
        this.onlineClassList  = new ArrayList<>();
        this.onlineClassList.add(new OnlineClass(1,"spring boot",true));
        this.onlineClassList.add(new OnlineClass(2,"spring data jpa",true));
        this.onlineClassList.add(new OnlineClass(3,"spring mvc",false));
        this.onlineClassList.add(new OnlineClass(4,"spring core",false));
        this.onlineClassList.add(new OnlineClass(5,"rest api development",false));

        this.javaClassList = new ArrayList<>();
        javaClassList.add(new OnlineClass(6, "The java, Test",true));
        javaClassList.add(new OnlineClass(7, "The java, Code manipulation",true));
        javaClassList.add(new OnlineClass(8, "The java, 8 to 11",false));

        this.soonEvents = new ArrayList<>();
        soonEvents.add(onlineClassList);
        soonEvents.add(javaClassList);
    }

    @DisplayName("스트림 예제#1 [onlineClassList] , spring 으로 시작 하는 수업")
    @Test
    void streamExam1() {
        List<OnlineClass> list = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .collect(Collectors.toList());

        list.forEach(System.out::println);
    }

    @DisplayName("스트림 예제#2 [onlineClassList] , close 되지 않은 수업")
    @Test
    void streamExam2() {
        List<OnlineClass> list = onlineClassList.stream()
                .filter(s->!s.isClosed())
                .collect(Collectors.toList());

        list.forEach(System.out::println);

        //요렇게 쓸라면 자바 11..
//        onlineClassList.stream()
//                .filter(Predicate.not(OnlineClass::isClosed))
//                .forEach(System.out::println);
    }

    @DisplayName("스트림 예제#3 [onlineClassList] , 수업 이름만 모아서 스트림 만들기")
    @Test
    void streamExam3() {
        List<String> list = onlineClassList.stream()
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList());

        list.forEach(System.out::println);
    }


    /**
     * flatMap 이용
     * : 컬렉션 안에 컬렉션을 하나의 제네릭 단위로 풀어 준다 라고 할 수 있음
     *   아래 예의 경우 soonEvents 리스트 안에 있는 리스트의 제네릭 타입인 OnlineClass 단위로 풀어 줌
     *
     *
     */
    @DisplayName("두 수업 목록에 들어있는 모든 수업 아이디 출력")
    @Test
    void streamExam4() {
        soonEvents.stream()
                .flatMap(Collection::stream)
                .forEach(s-> System.out.println(s.getId()));
    }

    @DisplayName("10부터 1씩 증가하는 무제한 스트림 중에 앞에 10개 빼고 최대 10개까지만 출력")
    @Test
    void streamExam5() {
        Stream.iterate(10, i->i+1) //무제한 스트림
                .skip(10)
                .limit(10)
                .forEach(System.out::println);
    }

    @DisplayName("자바 수업 중 Test 가 들어가 있는 수업 있는지 확인")
    @Test
    void streamExam6() {
        boolean result = javaClassList.stream().anyMatch(s->s.getTitle().contains("Test"));
        System.out.println(result);
    }

    @DisplayName("스프링 수업 중 spring 이 들어간 제목만 모아서 List 로 만들기")
    @Test
    void streamExam7() {
        List<String> list = onlineClassList.stream()
                .filter(s->s.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList());

        list.forEach(System.out::println);
    }


}
