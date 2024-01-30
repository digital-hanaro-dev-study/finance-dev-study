### this

this는 실행 컨텍스트가 생성될 때 결정된다. 실행컨텍스트는 함수를 호출될 때 생성되므로, 바꿔 말하면 this는 함수를 호출할 때 결정된다.

#### 전역 공간에서의 this

전역공간에서 this는 전역 객체를 가리킨다.
브라우저 환경에서 전역 객체는 window이고 Node.js 환경에서는 global이다.

#### 메서드로서 호출할 때 그 메서드 내부에서의 this

- 함수 vs. 메서드
  함수와 메서드를 구분하는 유일한 차이는 독립성에 있다. 함수는 그 자체로 독립적인 기능을 수행하고, 메서드는 자신을 호출한 대상 객체에 관한 동작을 수행한다.
  객체의 메서드로서 호출할 경우에만 메서드로서 동작한다. 그 외에는 모두 함수로서 동작함.
  메서드에서의 this는 호출한 주체가 된다. 즉, this는 . 앞의 객체를 가리킴.

메서드의 내부함수에서의 this 또한, 함수로서 호출될 경우 전역 객체를 가르키고 메서드로 호출되어야 .앞의 객체를 가리킨다. 이때 메서드 내부함수에서 this를 우회하는 방법으로 메서드 내에서 var self = this;와 같은 변수를 선언해두고 내부 함수에서 self를 활용하면 된다.

```
var func = function () {
  console.log(this);
};

func(); // Window {... }

var obj = {
  method: func,
};

obj.method(); //{ method: f }
```

node에서 실행할 경우 모듈의 변수로 잡하기 때문에 this가 가르키는 대상이 없다

화살표 함수는 bind로 this를 설정할 수 없다. 당연. 애초에 this가 없음 그러므로 스코프 체이닝에 의해 부모 스코프에서 this를 찾게 되고
화살표 함수는 this로 부모의 this가 된다.
화살표 함수에서 this를 사용하는 경우 임의로 this를 생성하여 접근할 수 있다.

객체 내의 function으로 생성된 프로퍼티는 function table 에 등록되고 화살표 함수로 등록된 프로퍼티는 function table에 등록이 안된다.![](https://velog.velcdn.com/images/duddn2012/post/31249e99-1f1c-40cc-ace6-86aee21c40d4/image.png)

apply: 매개변수를 배열로 전달
call: 매개변수 ,로 구분하여 전달
