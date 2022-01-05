# JAVA 8 코드 정리

## JAVA 8 에 나온 개념에 대해 간단히 정리 해보기

---
## Functional Interface
- 추상 메서드를 딱 하나만 가지고 있는 인터페이스
- SAM (Single Abstract Method) 인터페이스 (그냥 해석.. 하나의 추상 메서드 인터페이스)
- @FunctionalInterface 애노테이션을 가지고 있는 인터페이스

### 기본으로 제공하는 함수형 인터페이스
- Function<T,R> : T 타입을 받아서 R 타입을 리턴, R apply(T t) 
- BiFunction<T,U,R> : T, U 값을 받아 R 타입을 리턴 R apply(T t U u)
- Consumer<T> : T 타입을 받아 아무 값도 리턴 안함 void Accept(T t)
- Supplier<T> : T 타입의 값을 제공
- Predicate<T> : T 타입을 받아 boolean 을 리턴
- UnaryOperator<T> : Function<T,R> 의 특수한 형태, 입력 값과 동일한 타입을 리턴할 때 사용
- BinaryOperator<T> : BiFunction<T,U,R>의 특수한 형태, 동일한 타입의 입력값 두개를 받아 동일한 타입을 리턴할 때 사용


## Lambda 
- 함수형 인터페이스의 인스턴스를 만드는 방법
- 코드 라인 수 감소 이점
- 메서드 매개변수, 리턴 타입, 변수로 만들어 사용 가능

### 메서드 레퍼런스
- 스태틱 메서드 참조 = 타입::스태틱메서드
- 특정 객체의 인스턴스 메서드 참조 = 객체 레퍼런스::인스턴스 메서드
- 임의 객체의 인스턴스 메서드 참조 = 타입::인스턴스 메서드
- 생성자 참조 = 타입::new

---
## Optional
- 오직 값 한 개가 들어있을 수도 없을 수도 있는 컨테이너
- 메서드에서 작업 중 특별한 상황에서 값을 제대로 리턴 할 수 없는 경우 선택
### 주의사항
- 리턴 값으로만 쓰기를 권장 (메서드 매개변수 타입, 맵의 key 타입 , 인스턴스 필드 타입으로 쓰지 말것!)
- Optional 을 리턴하는 메서드에서 null 리턴 하지 말 것
- primitive type 용 Optional 은 따로 존재함 (OptionalInt , OptionalLong ...)
- Collection , Map, Stream Array , Optional 은 Optional 로 감싸지 말 것

### Optional EXample
Optional 만들기
- Optional.of()
- Optional.ofNullable()
- Optional.empty()

Optional에 값이 있는지 없는지 확인
- isPresent()
- isEmpty() (JAVA 11 이상)

Optional 에 있는 값 가져오기
- get()

Optional 에 값이 있는 경우 그 값을 가지고 ~~를 하라.
- ifPresent(Consumer)
- 예) Spring 으로 시작하는 수업이 있을 시 ID 출력

Optional 에 값이 있으면 가져오고 없는 경우에 ~~ 를 리턴
- orElse(T)
- 예) JPA로 시작하는 수업이 없다면 비어 있는 수업을 리턴하라.
- orElse 의 경우 값이 있던 없던 모두 체크 하기에 대체로 orElseGet 을 사용

Optional 에 값이 있으면 가져오고 없는 경우에 ~를 하라.
- orElseGet(Supplier)
- 예) JPA로 시작하는 수업이 없다면 새로 만들어 리턴
- orElseGet 의 경우 값이 없는 경우에만 동작

Optional 에 값이 있으면 가져오고 없으면 에러를 던져라.
- orElseThrow()

Optional 에 들어있는 값 걸러내기
- Optional filter(Predicate)

Optional 에 들어있는 값 변환
- Optional map(Function)
- Optional flatMap(Function) : Optional 안에 들어있는 인스턴스가 Optional 인 경우 사용 시 편리


---
## Stream
- 데이터를 담고 있는 저장소 X
- 스트림이 처리하는 데이터 소스를 변경 하진 않는다.
- 스트림으로 처리하는 데이터는 오직 "한번" 만 처리
- 무제한 일 수 있음 (Short Circuit 메서드 이용 하여 제한 가능)
- 손쉽게 병렬처리 가능

### Stream PipeLine (오퍼레이션의 집합)
-  0 또는 다수의 "중개 오퍼레이션"과 한개의 "종료 오퍼레이션" 으로 구성
- filter, map, limit, sorted ...

### 종료 오퍼레이션
- Stream 을 리턴하지 않음!
- collect, allMatch, count, forEach ...



### Stream 예제
걸러내기
- Filter(Predicate)

변경하기
- Map(Function) 또는 FlatMap(Function)
- FlatMap 예) List<Stream<String>> 을 String 의 스트림으로 변경

생성하기
- generate(Supplier) 또는 Iterate(T seed, UnaryOperator)
- 예) 10부터 1씩 증가하는 무제한 스트림
- 예) 랜덤 int 무한 스트림

제한하기
- limit(long) , skip(long)
- 스트림 개수 제한 or 스트림 데이터 몇개 스킵

스트림에 있는 데이터가 특정 조건 만족하는지 확인
- anyMatch() , allMatch(), nonMatch()

개수세기
- count()

스트림 하나로 합치기
- reduce(identity, BiFunction) , collect(), sum(), max()