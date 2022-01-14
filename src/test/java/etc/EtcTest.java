package etc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
}
