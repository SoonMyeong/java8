package generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTest<T> {

    static class Hello<T> { // T -> type parameter

    }

    void print(T t) {
        System.out.println(t.toString());
    }

    static <T> void print2(T t) {
        System.out.println(t.toString());
    }

    @DisplayName("제네릭을 쓰는이유 , 컴파일 시점에서 타입 체크를 할 수있음")
    @Test
    void generic_Test_1() {
        //new Hello<String>(); // type argument

        List list = new ArrayList();
        list.add("str"); //만일 이 list 에 Integer만 넣으려고 만든 list 라도 컴파일러는 알 수가 없다. (빨간 줄 안생김)
    }


//    @DisplayName("제네릭, 로 타입 문제")
//    @Test
//    void generic_rowType() {
//        List<Integer> ints = Arrays.asList(1,2,3);
//        List rawInts = ints; //일반 제네릭을 로타입에 대입
//        List<Integer> ints2 = rawInts; // 로타입을 일반 제네릭에 대입
//        List<String> strs = rawInts;
//        String str = strs.get(0); // Integer 컬렉션을 String 컬렉션에 초기화 하는데 빨간줄이 발생하지 않는 위험성
//    }


    /**
     * static 에서도 쓸 수 있으나 static 메서드의 경우 클래스에 적은 제네릭 타입이 적용되지 않음
     *                            (static 메서드란게 오브젝트를 인스턴스화 시킨 후 사용하는게 아니니까)
     * 따라서 static 메서드의 경우 메서드에 <T> 명시를 해줘야 함
     * 또한 생성자에도 쓸 수 있다.
     *
     *
     */
    @DisplayName("제네릭 메서드")
    @Test
    void generic_method_1() {
        new GenericTest().print("Hello");
        new GenericTest().print(1);
        print2(2);
    }

    //-----------------------------------------------
    //제네릭은 bounded 정의 시 여러개도 & 를 통해 선언 가능
    //static <T extends List & Serializable & Comparable> void print3(T t) {}


    static <T extends Comparable<T>> long countGreaterThan(T[] arr, T elem) {
        return Arrays.stream(arr).filter(s -> s.compareTo(elem) > 0).count();
    }

    @DisplayName("Bounded Type")
    @Test
    void generic_Test_2() {
        //Bounded Type Parameter
        Integer[] arr = new Integer[] {1,2,3,4,5,6,7};
        System.out.println(countGreaterThan(arr,4));
        String[] strs = new String[] {"a","b","c","d","e"};
        System.out.println(countGreaterThan(strs,"c"));
    }

    /**
     * 제네릭 타입의 경우 상속관계에 영향을 주지 않는다.
     *
     */
    @DisplayName("제네릭과 상속")
    @Test
    void generic_Test_3() {
        Integer i = 10;
        Number n = i; //여긴 되나 제네릭에서는 안됨.

        List<Integer> intList = new ArrayList<>();
//        List<Number> numberList = intList; //컴파일 에러 발생
        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Integer> intList2 = arrayList; //이런 경우는 가능, (제네릭 타입은 동일함)
    }


    static <T> void method(T t, List<T> list) {

    }

    @DisplayName("타입 추론화")
    @Test
    void generic_Test_4() {
        method(1,Arrays.asList(1,2,3));
    }


    /**
     *
     * 아래 두 메서드의 차이는 무엇인가?
     * static <T extends Comparable> void method2(List<T> t) {}
     * static void method3(List<? extends Comparable> t) {}
     * List<?> list; ,  ?: 와일드카드
     *
     */

     static void printList(List<Object> list) {
         list.forEach(s-> System.out.println(s));
     }
     static void printList2(List<?> list) {
         list.forEach(s-> System.out.println(s));
     }

     static class A{}
     static class B extends A {}

    @DisplayName("제네릭, 와일드카드#1")
    @Test
    void generic_Test_5() {
        List<Integer> list = Arrays.asList(1,2,3);
//        printList(list); //컴파일 에러
        printList2(list); // 정상동작, 여기서 와일드카드는 제네릭타입을 아무 타입이나 받을 수 있다는 의미

        System.out.println("-----------------------------------");
        List<B> lb = new ArrayList<B>();
//        List<A> la = listB; // 컴파일 에러
        List<? extends B> la = lb; // 와일드카드의 경우는 됨
        List<? super B> la2 = lb;
    }

}
