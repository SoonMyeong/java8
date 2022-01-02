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
