package inter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SoonInterfaceTest {
    
    @DisplayName("디폴트 메서드 #1")
    @Test
    void default_method_interface_test() {
        SSoon sSoon = new SSoon("soonm");
        sSoon.printName();
        sSoon.printNameUpperCase();
    }


    @DisplayName("스테틱 메서드 #1")
    @Test
    void static_method_interface_test() {
        Soon.printAnything();
    }

    @DisplayName("Iterable #1")
    @Test
    void java8_iterable_api() {
        List<String> names = Arrays.asList("soon","soonWorld","james","hello");
        names.forEach(System.out::println);
    }

    @DisplayName("spliterator #1 , 쪼갤 수 있는 Iterator")
    @Test
    void java8_spliterator_api() {
        List<String> names = Arrays.asList("soon","soonWorld","james","hello");
        Spliterator<String> spliterator = names.spliterator();
        Spliterator<String> spliterator1 = spliterator.trySplit();

        while(spliterator.tryAdvance(System.out::println));
        System.out.println("=======================================");
        while(spliterator1.tryAdvance(System.out::println));
    }

    @DisplayName("removeIf #1")
    @Test
    void java8_removeIf_api() {
        List<String> names = new ArrayList<>();
        names.add("soon");
        names.add("soonWorld");
        names.add("james");

        names.removeIf(s->s.startsWith("s"));
        names.forEach(System.out::println);
    }

    @DisplayName("Comparator #1")
    @Test
    void java8_comparator_test1() {
        List<String> names = Arrays.asList("soon","soonWorld","james","hello");
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        names.sort(compareToIgnoreCase.reversed()); //역순
        names.forEach(System.out::println);
    }


    /**
     * 이거 JAVA8 전에 하려면 interface 구현체(추상클래스) 에다가 메서드 내용 비워놓고 상속받아서 처리했었어야 함...
     */
    @DisplayName("디폴트 인터페이스 관련 #2")
    @Test
    void java8_default_method() {
        K k = new K();
        S s = new S();
        M m = new M();

        k.priceK();
        s.priceS();
        m.priceM();
    }

}
