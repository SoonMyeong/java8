package functional;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.*;

public class FunctionalExampleTest {


    @DisplayName("람다 예제#1 Something 인터페이스 사용")
    @Test
    void lambdaTest_1() {
        Something something = new Something() {
            @Override
            public void doIt() {
                System.out.println("Hi?");
            }
        }; //람다로 만들기 전, 익명 내부 클래스 모습

        Something something1 = () -> {
            System.out.println("I'm Lambda");
        }; //람다

        something.doIt();
        something1.doIt();
    }

    @DisplayName("함수형 프로그래밍 특징 # 순수 함수")
    @Test
    void functionalTest_2() {
        SomeOne someOne = number -> number + 10;

        System.out.println(someOne.doInt(1));
    }

    @DisplayName("함수형 프로그래밍 특징 # 값 참조 가능, 변수 캡처")
    @Test
    void functionalTest_3() {
        int num = 10;
        SomeOne someOne = new SomeOne() {
            @Override
            public int doInt(int number) {
                return number+num; // 단순 외부 변수를 참조만 할 경우엔 가능하다 (final 처럼 인식함)
            }
        };
        //근데 만약 아래 처럼 익명 내부클래스 안에서 참조한 변수의 값을 변경하려 한다면? 빨간줄!
        //num++;
    }

    @DisplayName("함수형 인터페이스 # Function<T,R>")
    @Test
    void functionalTest_4() {
        PlusTen plusTen = new PlusTen();
        System.out.println("plusTen = " + plusTen.apply(1));

        Function<Integer,Integer> minusTen = i -> i-10;
        System.out.println("minusTen = " + minusTen.apply(1));

        Function<Integer,Integer> multiply2 = i -> i*2;
        System.out.println("multiply2 = " + multiply2.apply(2));

        Function<Integer,Integer> multiply2AndMinusThen = minusTen.compose(multiply2);

        // compose 의 경우 실행 순서 : multiply2 -> minusTen
        System.out.println("multiply2AndMinusThen = " + multiply2AndMinusThen.apply(2));

        //andThen 의 경우 순차적, 실행순서 : minusTen -> multiply2
        System.out.println("minusTen (andThen) = " + minusTen.andThen(multiply2).apply(2));
    }

    @DisplayName("함수형 인터페이스 # Consumer<T>")
    @Test
    void functionalTest_5() {
        Consumer<Integer> printT = System.out::println;
        printT.accept(10);
    }

    @DisplayName("함수형 인터페이스 # Supplier<T>")
    @Test
    void functionalTest_6() {
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10.get());
    }

    @DisplayName("함수형 인터페이스 # Predicate<T>")
    @Test
    void functionalTest_7() {
        Predicate<String> startsWithSoon = s-> s.startsWith("soon");
    }

    @DisplayName("함수형 인터페이스 # UnaryOperator")
    @Test
    void functionalTest_8() {
        UnaryOperator<Integer> plusTen = i-> i+10;  //Function 의 파라미터 타입과 리턴타입이 같을 경우에 쓸 수 있음
        Function<Integer,Integer> minusTen = i -> i-10;
    }

}
