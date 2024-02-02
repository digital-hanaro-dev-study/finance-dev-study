### 개요

자바스크립트를 활용한 프로덕션 개발을 하기에 앞서 자바스크립트 코드가 엔진, 더 나아가서 메모리에서 어떤 과정을 통해 실행되고 있는지에 대한 이해가 필요하다.
이를 이해하기 위한 첫 걸음은 바로 실행 컨텍스트에 있다고 할 수 있다. 그 이후에 클로져, 프로토 타입과 같은 응용 개념들을 쌓아 올릴 예정이다.

### 실행 컨텍스트

실행 컨텍스트는 자바스크립트 런타임 환경에서 실행할 코드들에 제공할 환경 정보를 담고있는 객체이다. 실행 컨텍스트를 구성하는 방법은 자동으로 생성되는 전역 컨텍스트와 함수 실행, eval() 함수 실행을 통해 구성할 수 있다(ES6 이후부터는 블록에 의해서도 새로운 실행 컨텍스트가 생성됨). 이렇게 구성된 객체들은 call stack이라 불리는 stack메모리에 적재된다. LIFO구조인 스택임을 생각하면 가장 먼저 구성된 전역 실행 컨텍스트는 프로그램 종료 시까지 유지된다. 또한, stack 영역에는 가변적인 데이터를 저장할 수 없으므로 Heap 영역의 주소를 참조할 것이다. 실행 컨테스트가 활성화되는 시점에 호이스팅, 외부 환경 정보 구성, this값 설정 등의 동작을 수행한다.

그럼 이제 실행 컨텍스트에는 어떤 데이터가 저장되어 있는지 알아보자.

1. VariableEnvironment: LexicalEnvironment의 스냅샷
2. LexicalEnvironment: 현재 컨텍스트 내의 식별자들에 대한 정보 + 외부 환경 정보로 구성됨. 변경사항이 실시간으로 반영된다.
3. ThisBinding: this 식별자가 바라봐야할 대상 객체

#### LexicalEnvironment

LexicalEnvironment는 실행환경에서 실질적인 정보를 관리하는 environmentRecord와 outerEnvironmentReference가 있다. 간략하게 정리하면 environmentRecord는 현재 실행 중인 함수의 지역 변수와 함수 수집, outerEnvironmentReference는 외부 스코프 즉, 부모 실행 환경의 정보를 참조하여 외부에서 선언된 정보들에 접근할 수 있기 위한 포인터이다.
그럼 각각의 개념들을 좀 더 자세히 알아보자.

**1. environmentRecord**
현재 컨텍스트와 관련된 코드의 식별자 정보들이 순서대로 저장된다. 이때 호이스팅 또한 처리된다.
호이스팅은 environmentRecord의 수집 과정을 추상화한 개념이며 엔진은 호이스팅을 통해 코드의 해석을 용이하게 할 수 있다.
또한, 호이스팅을 알고있다면 함수 선언문 보다 함수 표현식이 더 안전한 코드임을 알 수 있다.
이유는 선언문으로 정의된 동일한 명의 함수는 이후에 작성된 내용으로 덮어씌어지기 때문에 의도하지 않은 동작을 유발할 수 있기 때문이다.
environmnetRecord는 Declarative Record와 Object Record 영역이 있는데 블록스코프에서 생성된 Lexical Environment는 블록스코프인 let과 const만 저장되므로 Declarative Record만 참조한다.

**2.outerEnvironmentReference**
outerEnvironmentReference는 해당 함수가 실행된 시점의 LexicalEnvironment를 참조한다. 그러므로 만약 함수 속의 함수에서 전역 변수에 접근하기 위해서는 두번의 outerEnvironmentReference 접근을 통해 접근할 수 있다.

### 결론

이로서 자바스크립트의 실행 컨텍스트가 런타임 환경에서 어떻게 구성되는지 알아보았다.
앞으로 해당 개념을 숙지한 상태로 클로져, this와 같은 개념들을 공부해나가보자.
