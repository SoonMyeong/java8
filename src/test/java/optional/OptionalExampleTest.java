package optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import stream.OnlineClass;

import java.util.*;

public class OptionalExampleTest {

    private List<OnlineClass> onlineClassList;

    @BeforeEach
    void init() {
        this.onlineClassList  = new ArrayList<>();
        this.onlineClassList.add(new OnlineClass(1,"spring boot",true));
        this.onlineClassList.add(new OnlineClass(5,"rest api development",false));
    }

    @DisplayName("옵셔널 예제#1 isPresent")
    @Test
    void optionalExam1() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("jpa"))
                .findFirst();
        boolean present = optional.isPresent();
        System.out.println(present);
    }

    @DisplayName("옵셔널 예제#2 get, 잘 쓰이지 않음.. 값 있는지 체크해야되서")
    @Test
    void optionalExam2() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .findFirst();

        OnlineClass onlineClass = optional.get();
        System.out.println(onlineClass.getTitle());
    }

    @DisplayName("옵셔널 예제#3-1 orElse , 데이터 없는 경우")
    @Test
    void optionalExam3_1() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("jpa"))
                .findFirst();
        OnlineClass onlineClass = optional.orElse(createNewClass());
        System.out.println(onlineClass.getTitle());
    }

    @DisplayName("옵셔널 예제#3-2 orElse , 데이터 있는 경우, 있어도 createNewClass 메서드를 탄다.")
    @Test
    void optionalExam3_2() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .findFirst();
        OnlineClass onlineClass = optional.orElse(createNewClass());
        System.out.println(onlineClass.getTitle());
    }

    private OnlineClass createNewClass() {
        System.out.println("Create new Class");
        return new OnlineClass(10,"New Class",false);
    }

    @DisplayName("옵셔널 예제#4-1 orElseGet , 데이터 없을 때만 createNewClass 메서드를 탐, 현재 있어서 안탐")
    @Test
    void optionalExam4_1() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .findFirst();
        OnlineClass onlineClass = optional.orElseGet(this::createNewClass); //orElseGet 은 파라미터가 Supplier 임
        System.out.println(onlineClass.getTitle());
    }

    @DisplayName("옵셔널 예제#4-2 orElseGet , 데이터 없을 때만 createNewClass 메서드를 탐, 현재 없어서 탐")
    @Test
    void optionalExam4_2() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("jpa"))
                .findFirst();
        OnlineClass onlineClass = optional.orElseGet(this::createNewClass); //orElseGet 은 파라미터가 Supplier 임
        System.out.println(onlineClass.getTitle());
    }

    @DisplayName("옵셔널 예제#5 orElseThrow , 데이터 없을 시 에러 던짐")
    @Test
    void optionalExam5() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("jpa"))
                .findFirst();
        OnlineClass onlineClass = optional.orElseThrow(NoSuchElementException::new);
        System.out.println(onlineClass.getTitle());
    }

    @DisplayName("옵셔널 예제#6 filter , Optional 안에 데이터 있다는 가정 하에 작동")
    @Test
    void optionalExam6() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .findFirst();
        Optional<OnlineClass> onlineClass = optional
                .filter(o-> o.isClosed());

        System.out.println(onlineClass.isPresent());
    }


    /**
     *
     * Stream 의 FlatMap 의 경우 제네릭 타입 까지 껍질 을 다 까주는데,
     * Optional 의 FlatMap 은 Optional <타입> 까지만 껍질 을 까줌.
     *  : 이 말은 결국 Optional 의 경우 FlatMap 써도 결국 Optional 로 리턴된다.
     *
     *
     */
    @DisplayName("옵셔널 예제#7 map ")
    @Test
    void optionalExam7() {
        Optional<OnlineClass> optional  = onlineClassList.stream()
                .filter(s->s.getTitle().startsWith("spring"))
                .findFirst();

        Optional<Integer> onlineClass = optional.map(OnlineClass::getId);

        System.out.println(onlineClass.isPresent());
    }


}
