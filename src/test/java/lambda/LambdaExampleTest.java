package lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

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

}
