### 개요

사용자가 웹 주소를 입력하고 웹 페이지를 보게되는 과정을 정리해보면 사용자가 입력한 웹 주소가 가리키는 서버에서 네트워크를 통해 html을 전달 받고 브라우저의 랜더링 과정을 통해 화면을 구성하고 표현하게 된다. 이 과정 중 오늘은 브라우저의 랜더링 메커니즘에 대하여 알아볼 것이다.

### 브라우저 랜더링 메커니즘

브라우저는 서버로부터 바이트 형태의 HTML, JS, CSS 데이터를 받고 역직렬화를 통해 각 파일들을 복원한다.
그후, 아래의 랜더링 메커니즘을 통해 화면을 사용자에게 보여준다.

>

1. HTML-> DOM
2. CSS -> CSSOM
3. JavaScript -> AST -> Execute(DOM API)
4. Layout(Render tree)
5. Paint

![](https://velog.velcdn.com/images/duddn2012/post/faad2ae9-a22b-49a5-bba1-4fa7aa216459/image.png)
랜더링 엔진의 종류로는 Gecko, WebKit, Blink 등이 있으며 Chrome은 현재 Blink 랜더링 엔진을 사용 중이다.

![](https://velog.velcdn.com/images/duddn2012/post/e9044481-5901-4f71-bc0e-ecb1b4461c3f/image.png)
공부하다보니 랜더링 성능은 어떻게 체크할 수 있을지 궁금하여 찾아보았는데 크롬 브라우저의 개발자 모드에서 Rendering 관련 상태를 확인하는 기능을 제공하는데 선의 색에 따라 어떤 작업을 진행하였는지 표현해준다. 랜더링 관련 문제가 발생할 때 유용할 것 같다. 색상 구분과 관련해서는 [debug_colors.cc](https://source.chromium.org/chromium/chromium/src/+/main:cc/debug/debug_colors.cc) 에 정리되어 있다.

#### HTML Parse

![](https://velog.velcdn.com/images/duddn2012/post/279b2f32-6584-4340-add3-6931baeeff23/image.png)

HTML 구문을 분석하여 DOM Tree를 생성하는데 일반적인 Lexical Analysis - Syntax Analysis로 이어지는 파싱 알고리즘과 유사하지만 차이가 있다. 다른 언어의 경우 파싱하는 동안 소스가 변경되지 않지만 **HTML에서는 동적 코드가 토큰을 더 추가할 수 있기 때문**에 파싱 프로세스에서 입력을 수정하는 로직이 포함되어 있다.
![](https://velog.velcdn.com/images/duddn2012/post/3ca62a64-1c98-446a-b7b5-400bc33f9eb8/image.png)

파싱 알고리즘에 대한 구체적인 사항은 [해당 링크](https://html.spec.whatwg.org/multipage/parsing.html)에 정리되어 있다.

#### CSS Parse

![](https://velog.velcdn.com/images/duddn2012/post/9b97e002-6147-492d-9c11-c0ac799b88e3/image.png)

HTML과 유사한 방식으로 Parsing이 진행되며 CSSOM tree를 생성한다.
추가적으로 Preload scanner가 CSS가 분석되고 CSSOM를 생성하는 동안 JavaScript 파일과 같은 다른 자원의 다운로드를 진행한다.

#### JavaScript Parse And Execute

랜더링 엔진과 분리되어 있으며 브라우저 별로 JavaScript Engine 구현체가 다르다. V8(Chrome 브라우저를 위한 엔진), SpiderMonkey와 같은 JavaScript Engine에서 JavaScript 코드를 컴파일(AST) 및 인터프리팅(엔진 별로 과정은 조금씩 다름)을 하여 메인 쓰레드에서 실행되는 바이트 코드가 생성된다.

#### Style

CSSOM과 DOM 트리가 렌더 트리로 합성되며 렌더 트리는 DOM 트리의 루트 부터 시작하여 각 노도들을 순회하며 만들어진다.

#### Layout

Layout은 렌더 트리의 모든 노드의 너비, 높이, 위치를 결정하는 프로세스이다. 추가로 페이지에서 각 객체의 크기와 위치를 계산한다.
**리플로우**는 레이아웃 이후에 있는 페이지의 일부분이나 전체 문서에 대한 크기나 위치에 대한 결정이다.
정리하면 처음 노드의 사이즈와 위치가 결정되는 것이 Layout이고 이후에 동적으로 변경된 노드의 크기와 위치를 다시 계산하는 것은 리플로우라고 한다.

#### Paint

렌더링의 최종 단계는 각 노드를 화면에 페인팅하는 것이다. 각각의 요소들을 실제 화면의 픽셀로 변환하여 시각적인 부분을 화면에 그리는 작업이 포함된다.
이때 화면에 그려지는 작업은 매우 빠르게 처리되어야 하는데 이를 위해 작업을 n개의 레이어로 구분하여 처리한다. 해당 작업이 일어날 경우 합성 처리를 해줘야한다.

### 그 이후

메인 쓰레드가 페이지를 그리는 것을 완료했다고 해서 모든 준비가 완료된 것은 아니다. 만약 지연된 JavaScript를 다운했다면, onload 이벤트가 발생할 때 코드가 실행된다면, 메인쓰레드는 아직 처리해야 할 작업들이 남아 있다. Time to Interactive(TTI)는 50ms 이내가 될 수 있도록 코드를 작성하는 것이 권장된다.

> 출처
> https://developer.mozilla.org/ko/docs/Web/Performance/How_browsers_work > https://dev.to/arikaturika/how-web-browsers-work-parsing-the-html-part-3-with-illustrations-45fi > https://web.dev/articles/howbrowserswork?hl=ko
