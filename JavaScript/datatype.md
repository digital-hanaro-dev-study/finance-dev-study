### 개요

자바스크립트는 동적 타이핑을 통해 변수의 타입을 추론하는 언어이다. 줄곧 변수의 타입을 코딩 시점에 지정하는 정적 타이핑 기반의 언어(Java, C++ )의 경험만 있었던 나로서는 생소한 개념이였다. 이제 자바스크립트의 데이터 타입 시스템에 대한 이해를 통해 궁금증을 해결해보자.

### Premitive vs Reference

자바스크립트는 원시타입과 참조타입 두가지를 통해 변수의 타입을 관리한다. 흔히 원시타입은 call by value, 참조타입은 call by reference라 하여 값을 접근하는 방법에 따라 다른것으로 이해하고 있었다. 그러나 엄밀히 말하면 원시타입 역시 참조타입이다. 결국 변수가 갖고있는 값은 데이터에 대한 주소값이라는 말과 동일하다. 그러나 원시타입과 참조타입의 차이점은 데이터가 저장되는 메모리 장소에 있다고 볼 수 있다. 원시타입은 스택 메모리에 데이터가 생성되고 변수는 그 주소를 참조하는 반면 참조타입의 경우 힙 메모리에 데이터를 참조하는 공간을 생성하고 변수는 힙 메모리의 주소를 참조하는 것이다. 즉, 두번의 참조가 있어야하는 것이다.

### 가변성과 불변성

가변성은 주소의 주소를 참조하면서 발생하는 현상이다. 가령 a 참조 타입 변수를 b라는 변수에 대입하였을 때 b.data = changeddata 와 같은 방법으로 값이 수정되면 a 또한 같은 주소를 가리키고 있으므로 changeddata를 반환하게된다. 이런 경우를 가변성이라고 한다.
그러나 객체의 변경 가능성을 제한하기 위하여 불변 객체를 생성하는 것을 지향해야 한다. 그러기 위해선 아래의 코드를 통해 참조형 객체를 복사하여 새로 할당한다면 불변 객체를 만들 수 있다.

```
var copyObject = function (target) {
  var result = {};
  for (var prop in target) {
    result[prop] = target[prop];
  }
  return result;
};
```

그러나 위 코드는 여러가지 문제점이 있다.
객체의 프로퍼티에 객체를 관리하고 있는 경우를 생각해보자. 해당 객체를 위의 방법으로 복제된 객체 copyObject.property.data를 수정할 경우 원본 객체가 수정된다.

```
copyObject.property.data = changedData;
console.log(originalObject.property.data === copyObject.property.data);  //true
```

이를 해결하는 방법은 재귀적으로 객체 내부의 프로퍼티들을 모두 복제하여 할당해주면 해결된다.

```
var copyObject = function (target) {
  var result = {};
  if (typeof tartget === "object" && target !== null) {
    for (var prop in target) {
      result[prop] = copyObject(target[prop]);
    }
  } else {
    return target;
  }
  return result;
};
```
