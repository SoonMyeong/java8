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

## 인터페이스의 변화 (기본 메서드와 스테틱 메서드)
### 기본(default) 메서드
- 인터페이스에 구현체를 제공 함
- 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있음!!!!
- 구현체가 모르게 추가된 기능이므로 리스크가 존재
  - 구현체에 따라 런타임 에러 발생 우려
  - 반드시 문서화! (@ImplSpec 자바독 태그 사용)
- Object 가 제공하는 (equals, hasCode) 는 기본 메서드로 제공할 수 없다.
- 기본 메서드를 가진 인터페이스를 상속받는 인터페이스 에서 다시 추상 메서드로 변경 가능
- 본인이 수정할 수 있는 인터페이스에만 기본 메서드를 제공 할 수 있다.


### 스테틱 메서드
- 핼퍼 또는 유틸리티 메서드를 제공 할 때 인터페이스에 스태틱 메서드를 제공


### Iterable 의 기본 메서드
- foreach()
- spliterator()

### Collection 의 기본 메서드
- stream() / parallelStream()
- removeIf(Predicate)
- spliterator()

### Comparator 의 기본 메서드
- reversed()
- thenComparing()
- static reverseOrder() / naturalOrder()
- static nullsFirst() / nullsLast()
- static comparing()



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

## Concurrent Programing
### 자바에서 지원하는 컨커런트 프로그래밍
- 멀티프로세싱, 멀티스레드

## Executors
### 고수준 Concurrency Programing
- 스레드를 만들고 관리하는 작업을 애플리케이션에서 분리

### Executors 가 하는일
- 스레드 생성
- 스레드 관리
- 작업 처리 및 실행 : 스레드로 실행할 작업을 제공할 수있는 API 제공

### 주요 인터페이스
- Executor.execute(Runnable)
- ExecutorService : Callable 도 실행 가능하며 Executor 를 종료 시키거나, 여러 Callable 을
동시에 실행 하는 등의 기능 제공
- ScheduledExecutorService : ExecutorService 를 상속 받는 인터페이스로 특정 시간 이후 또는 주기적으로
작업을 실행할 수 있다.

### Fork/Join framework
- ExecutorService 의 구현체


## Callable & Future
### Callable
- Runnable 과 유사하지만 Runnable 과 다르게 작업 결과물을 받을 수 있음

### Future
- 비동기적인 작업의 현재 상태 조회 및 결과를 가져올 수 있음
- get() : 결과 가져오기 , 블로킹 콜임 (넌블로킹 아님)
- isDone() : 상태 확인 (true/false)
- cancel() : 취소하기 (true/false) , 파라미터로 true 전달 시 진행중인 스레드 interrupt, 그렇지 않으면 작업 끝날때 까지 기다림
- invokeAll() : 여러 작업 동시 실행
- invokeAny() : 여러 작업 중 하나라도 응답 오면 끝내기 (블로킹 콜)
