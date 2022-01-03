package functional;

@FunctionalInterface
public interface Something {
    
    void doIt(); // 추상 메서드!! 단 한개일 경우 @FunctionalInterface 애노테이션을 붙일 수 있음
    
    static void printName() { //Java8 부터는 static 메서드 가능
        System.out.println("SoonWorld");
    }

    default void printNumber() { // default 메서드 역시 인터페이스에 선언 가능!
        System.out.println("12345");
    }
    
}
