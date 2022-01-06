package inter;

public interface Soon {

    void printName();

    /**
     * @implSpec 이 구현체는 getName() 으로 가져온 문자열을 대문자로 바꿔 출력
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    static void printAnything() {
        System.out.println("Anything!!");
    }


    /**
     * 아..이거 신세계네...
     * 기존에 java8 쓰면서 이 기능 모르고 설계한 내 자신에게 반성합니다 ㅠ_ㅠ
     */

    default void priceK() {

    }

    default void priceS() {

    }
    
    default void priceM() {

    }

    String getName();

}
