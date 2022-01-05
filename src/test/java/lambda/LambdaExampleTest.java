package lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.*;

public class LambdaExampleTest {

    @DisplayName("람다 # 변수 캡처")
    @Test
    void lambdaTest_1() {
        int baseNumber = 10;

        //로컬 클래스
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber); //로컬 클래스 내부 메서드 안에 정의된 baseNumber 값 11이 나옴, 쉐도잉 됨
            }
        }

        //익명 내부 클래스
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                System.out.println(baseNumber); // 파라미터 baseNumber 값이 출력, 쉐도잉 됨
            }
        };

        //람다
        IntConsumer printInt = i-> { // i값을 baseNumber 로 바꿀 수 없다!
            // 람다의 경우 위에 있는 baseNumber 와 스코프가 같다!, 쉐도잉 안됌
            System.out.println(i + baseNumber);
        };

    }

    @DisplayName("스태틱 메서드 참조")
    @Test
    void static_method_test() {
        UnaryOperator<String> hello = Soon::hello;
        System.out.println(hello.apply("soon"));
    }

    @DisplayName("특정 객체의 인스턴스 메서드 참조")
    @Test
    void instance_method_test() {
        Soon soon = new Soon();
        UnaryOperator<String> hello = soon::world;
        System.out.println(hello.apply("instance"));
    }

    @DisplayName("임의 객체의 인스턴스 메서드 참조")
    @Test
    void instance_method_test2() {
        String[] test = {"soon","world","time is gone"};
        Arrays.sort(test, String::compareToIgnoreCase); //대소문자 구분없이 정렬
        System.out.println(Arrays.toString(test));

    }


    @DisplayName("파라미터 있는 생성자 참조")
    @Test
    void constructor_method_test() {
        Function<String,Soon> soon = Soon::new;
        System.out.println(soon.apply("Soon").getName());
    }

    @DisplayName("파라미터 없는 생성자 참조")
    @Test
    void constructor_method_test2() {
        Supplier<Soon> soon = Soon::new;
        System.out.println(soon.get().getName());
    }

}
