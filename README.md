#JAVA 8 코드 정리

## JAVA 8 에 나온 개념에 대해 간단히 정리 해보기

---
##Lambda 

---
##Optional

---
##Stream
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