package generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

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

     static <T> void printList(List<T> list) {
         list.forEach(s-> System.out.println(s));
     }

    /**
     * 사용 할수 있는 메서드 들.. (Object 메서드)
     * //         list.size();
     * //         list.clear();
     * //         Iterator<?> it = list.iterator();
     * //         list.equals()
     *
     */
    static void printList2(List<?> list) {
         list.forEach(s-> System.out.println(s));
         list.add(null); // null 만 넣을 수 있다.

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
        List<? extends A> la = lb; // 와일드카드의 경우는 됨
        List<? super B> la2 = lb;
    }


    static <T> boolean isEmpty(List<T> list) {
        return list.size() == 0;
    }
    static boolean isEmpty2(List<?> list) { // List 의 기능(매서드) 만 사용할 경우, <> 타입에 따른 처리 안할 경우
        return list.size() == 0;
    }

    @DisplayName("제네릭 와일드카드#2")
    @Test
    void generic_Test_6() {
        List<Integer> list = Arrays.asList(1,2,3);
        System.out.println(isEmpty(list));
    }


    /**
     * 오라클의 공식적인 자바 스팩 문서에서는 T 제네릭보단 와일드카드를 권장
     * [이유]
     * 1. T 타입을 내부 구현에서 사용하므로 외부 노출
     * 2. 의도를 바르게 드러내기 못하는 코드
     *
     */
    static <T> long frequency(List<T> list, T el) {
        return list.stream().filter(s-> s.equals(el)).count();
    }
    static long frequency2(List<?> list, Object el ) { // Object 메서드 사용할 경우 ? 사용 가능
        return list.stream().filter(s-> s.equals(el)).count();
    }

    @DisplayName("제네릭 와일드카드#3")
    @Test
    void generic_Test_7() {
        List<Integer> list = Arrays.asList(1,2,3,3,3,4,5);
        System.out.println(frequency(list,3));
        System.out.println(frequency2(list,3));
    }


    static <T extends Comparable<T>> T max(List<T> list) {
        return list.stream().reduce((a,b) -> a.compareTo(b) >0 ? a : b).get();
    }

    /**
     * 소비자 : extends
     * 공급자 : super
     * 대표적인 예 : Collections.copy 메서드 내용
     */
    static <T extends Comparable<? super T>> T max2(List<? extends T> list) {
        return list.stream().reduce((a,b) -> a.compareTo(b) >0 ? a : b).get();
    }

    @DisplayName("제네릭 와일드카드#4")
    @Test
    void generic_Test_8() {
        List<Integer> list = Arrays.asList(1,2,3,3,3,4,5);
        System.out.println(max(list));
        System.out.println(Collections.max(list, (a,b)-> a+b));
        System.out.println(Collections.max(list, (Comparator<Object>)(a,b)-> a.toString().compareTo(b.toString())));
    }


    static <T> void reverse(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for(int i=0; i<list.size(); i++) {
            list.set(i,temp.get(list.size()-i-1));
        }
    }

    static void reverse2(List<?> list) {
//        List<T> temp = new ArrayList<>(list);
//        for(int i=0; i<list.size(); i++) {
//            list.set(i,temp.get(list.size()-i-1));
//        } -->
//        컴파일 에러를 피하기 위해서는 캡쳐를 위해 헬퍼 메서드를 이용, (제네릭타입 으로 변환시켜서 캡쳐화)
//        이러면 API 클라이언트에게는 제네릭 타입이 노출이 안되서 장점이긴 한데... 어.. 굳이...? 이럴 필요는 없긴하다..(우리가 자바 라이브러리 개발자가 아닌이상..)
        reverseHelper(list); //제네릭 캡쳐
    }

    static void reverse3(List<?> list) { //제네릭 캡쳐 아니면 로타입을 이용하는 방법
        List temp = new ArrayList<>(list);
        List list2 = list;
        for(int i=0; i<list.size(); i++) {
            list2.set(i,temp.get(list2.size()-i-1));
        }
    }

    private static <T> void reverseHelper(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for(int i=0; i<list.size(); i++) {
            list.set(i,temp.get(list.size()-i-1));
        }
    }

    /**
     * 상당히 어렵다. 자바 라이브러리 개발자들이란.. 크..
     */
    @DisplayName("제네릭 와일드카드 캡쳐")
    @Test
    void generic_Test_9() {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
//        reverse(list);
        reverse2(list);
        System.out.println(list);
    }
    
    

}
