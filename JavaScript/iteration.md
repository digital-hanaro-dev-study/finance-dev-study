### 피보나치 수열 문제를 통한 iteration 개념 이해

### 개념 정리
아날로그 필기의 매력에 빠졌다..
사진으로 대체함.
![alt text](image.png)

``` JavaScript
function iter1() {
  const fibo = {
    [Symbol.iterator]() {
      let [pre, cur] = [0, 1];
      const max = 10;

      return {
        next() {
          [pre, cur] = [cur, pre + cur];
          return {
            value: cur,
            done: cur >= max,
          };
        },
      };
    },
  };

  //for ... of 는 객체의 iterator 메서드의 next 메서드에서 반환된 객체의 value 프로퍼티 값을 가져온다.
  //1. iterator = fibo[Symbol.iterator]()
  //2. res = iterator.next()
  //3. return res.value
  for (const num of fibo) {
    console.log(num);
  }

  const arr = [...fibo];
  console.log(arr);

  const [first, second, ...rest] = fibo;
  console.log(first, second, rest);
}

function iter2() {
  const fibo = (max) => {
    let [pre, cur] = [0, 1];

    return {
      [Symbol.iterator]() {
        return {
          next() {
            [pre, cur] = [cur, pre + cur];
            return {
              value: cur,
              done: cur >= max,
            };
          },
        };
      },
    };
  };

  for (const num of fibo(100000)) {
    console.log(num);
  }
}

iter2();
```