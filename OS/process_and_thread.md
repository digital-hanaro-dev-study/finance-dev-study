### 개요
컴퓨터의 OS는 프로세스 관리, 메모리 관리, 파일관리, I/O 장치 관리, 네트워킹 등 다양한 서비스를 제공한다.
이 포스팅에서는 OS의 프로세스 관리 서비스를 통해 프로세스와 쓰레드를 비교하여 그 차이를 알아보고자 한다.

### 프로세스란?
![](https://velog.velcdn.com/images/duddn2012/post/88cbc771-f98b-48bf-ba0b-de0693a3bf78/image.png)

CPU는 하드디스크에 저장된 파일 형태의 프로그램은 실행할 수 없다. CPU는 Main Memory에 올라와 있는 프로그램들에 대해서만 처리를 할 수 있는데 이때 **Main Memory에 올라와 있는 프로그램**들을 프로세스라고 한다.

#### 프로세스 스케쥴링
![](https://velog.velcdn.com/images/duddn2012/post/0a4c73c6-b731-4ce1-b3a0-b5627814a100/image.png)

Main Memory에 올라온 프로세스들은 new, ready, running, wating, terminated의 상태값을 가지며 Queue에 저장되어 운영체제의 프로세스 스케쥴링을 통해 처리된다.
> new - 하드디스크에서 Main Memory로 막 올라온 상태(Job Queue)
ready - Main Memory에서 초기화를 끝내 CPU의 처리를 기다리고 있는 상태(Ready Queue)
running - CPU가 처리 중인 상태(CPU)
waiting - 운영체제의 프로세스 스케쥴링에 의해 CPU가 다른 프로세스에 대한 처리를 진행 중인 경우, CPU의 처리를 대기 중인 상태(I/O Waiting Queue)

### 쓰레드란?
쉽게 말해서 프로그램 내부의 흐름이라고 말한다. 즉, 쓰레드는 **프로세스 내에서 실행되는 실행 단위**이다.
그렇다면 한번쯤 들어봤던 멀티 쓰레드라는 것은 한 프로그램에 2개 이상의 흐름이 존재한다는 것인데 엄밀히 말하면 이는 스위칭이 빠르게 일어남으로써 여러 흐름이 동시에 일어나는 것 처럼 보이게 하는것이다. 이는 Concurrent하다고 하며, 실제로 동시간에 여러 처리가 일어나는 것은 Simultaneous이다. Concurrent 방식은 하나의 자원에 대하여 다수의 쓰레드가 접근할 경우 의도하지 않는 결과를 도출하곤 한다. 이를 해결하기 위한 방법인 공유 자원에 대한 동기화 처리에 대해서 알아보자.

### 동기화
동기화는 자원을 동시에 접근할 수 없도록 하는 lock 매커니즘을 기반으로 해결된다. 대표적인 동기화 솔루션으로는 mutex, semaphore, monitor 등이 있는데 이는 lock 매커니즘에서 확장된 기술들이므로 lock을 이해한다면 쉽게 이해할 수 있다.
lock 매커니즘은 다음과 같은 순서로 동작한다. 화장실에 들어갈 때 문을 잠그고 들어가면 다른 사용자는 이전 사용자가 나올 때까지 대기하는 것과 비슷하다.
```
1. Race Condition이 발생하는 Critical Section을 파악한다.
2. 쓰레드가 Critical Section 즉, 공유자원에 접근 할 때 lock을 소유한다.
3. 이후 공유자원에 접근하는 쓰레드는 lock이 release 될 때까지 대기한다.
4. lock이 release 될 경우 나중에 접근한 쓰레드가 공유자원에 접근하며 lock을 소유하게된다.
```
#### 스핀락
위 방법은 기본적인 lock 매커니즘을 설명하고 있지만 생각해볼 문제가 있다. 현재의 방법으로는 대기 중인 쓰레드가 lock이 release 된 사실을 알 수 있는 방법이 없다. 
이를 위해서 **release가 될 때까지 공유자원의 상태를 지속적으로 체크하는 방식**이 스핀락이다.
#### 뮤텍스
그렇다면 **뮤텍스는** 무엇일까? 
뮤텍스는 스핀락과 유사하나 락이 걸려있을 때 cpu가 대기하고 있지 않고 다른 프로세스 처리를 진행할 수 있다. 이것이 가능한 이유는 **공유자원에 접근하는 쓰레드를 위한 큐가 존재하여 cpu가 락의 release를 대기 하지 않을 수 있기 때문**이다. 결론적으로 뮤텍스에서 발생하는 컨텍스트 스위칭 비용과 스핀락의 락 release 대기 비용을 비교하여 더 효율적인 동기화 방식을 선택할 수 있을 것이다.



### 결론
정리하면, 프로세스는 Main Memory에 올라온 실행 중인 프로그램이고 쓰레드는 프로세스 내에서 실행되는 실행 단위이다. 하나의 프로세스는 여러개의 실행흐름 즉, 쓰레드를 가질 수 있는데 이때 공유자원에 대한 Race Condition이 발생하게되고 이를 해결하기 위해 다양한 동기화 솔루션을 적용할 수 있다.

>
#### 참조
https://www.tutorialspoint.com/operating_system/os_process_scheduling.htm