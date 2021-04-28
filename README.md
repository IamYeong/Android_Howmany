# 하우매니   

## 소개   

- 월급 -> 소비 -> 잔여금액 이라는 일정한 패턴에서 착안해서 만든 앱입니다.   
결국 직장인들의 '저축'이란 월급 - 소비 의 결과가 쌓이는 것이죠.   
하지만 눈에 보이지 않으면 목표도 효과가 흐려지기에 이 앱을 만들었습니다.   

1. 지금 가진 돈을 입력합니다.
2. 목표날짜와 금액을 설정합니다.
3. 목표달성을 위해 일할계산하여 오늘 얼마나 써야하는지 보여줍니다.
4. 어제에 비해 오늘 얼마나 쓸 수 있는지(How many?) 화살표로 주식처럼 나타내줍니다.
5. 관련 정보를 통계 탭에서 확인할 수 있습니다.

***

## 스크린샷   

<!--
![스플래시](https://user-images.githubusercontent.com/59534301/116383218-9a54d400-a851-11eb-990c-c422f1a6f690.jpg)
![초기화면](https://user-images.githubusercontent.com/59534301/116383487-d8ea8e80-a851-11eb-9ae0-c705121a3028.jpg)
![목표설정](https://user-images.githubusercontent.com/59534301/116383513-dee06f80-a851-11eb-9088-7b314cf66f39.jpg)
![수입지출입력화면](https://user-images.githubusercontent.com/59534301/116383526-e142c980-a851-11eb-9eb5-d47e57fd6317.jpg)
![입력완료](https://user-images.githubusercontent.com/59534301/116383543-e56ee700-a851-11eb-9bad-b6427f79bbc3.jpg)
![상승](https://user-images.githubusercontent.com/59534301/116383558-eacc3180-a851-11eb-9276-3b82f0f4b289.jpg)
![삭제예시](https://user-images.githubusercontent.com/59534301/116383596-f1f33f80-a851-11eb-9112-39f5a68ca3a2.jpg)
![통계화면](https://user-images.githubusercontent.com/59534301/116383618-f586c680-a851-11eb-9fb3-8a19da8d0088.jpg)
![설정화면](https://user-images.githubusercontent.com/59534301/116383621-f7e92080-a851-11eb-8009-f59458d5494f.jpg)
-->

<img src="https://user-images.githubusercontent.com/59534301/116383218-9a54d400-a851-11eb-990c-c422f1a6f690.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383487-d8ea8e80-a851-11eb-9ae0-c705121a3028.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383513-dee06f80-a851-11eb-9088-7b314cf66f39.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383526-e142c980-a851-11eb-9eb5-d47e57fd6317.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383543-e56ee700-a851-11eb-9bad-b6427f79bbc3.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>   
<img src="https://user-images.githubusercontent.com/59534301/116383558-eacc3180-a851-11eb-9276-3b82f0f4b289.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383596-f1f33f80-a851-11eb-9112-39f5a68ca3a2.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383618-f586c680-a851-11eb-9fb3-8a19da8d0088.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
<img src="https://user-images.githubusercontent.com/59534301/116383621-f7e92080-a851-11eb-8009-f59458d5494f.jpg" width="100px" height="" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>


***

## 분석   

- TabLayout : 3가지의 탭으로 분리했습니다.
- RecyclerView : 첫 번째 탭의 기본 리스트입니다.
- Dialog : Builder 형식으로 생성했습니다.
- Radio Button : 지출/수입을 선택하면 값을 가져와서 음수인지 양수인지 판단합니다.
- EditText : 입력한 값을 가져옵니다.
- Image View : 어제의 값과 비교하여 if문을 통해 그에 맞는 이미지를 set 합니다.
- CardView : 보기 좋게 리스트를 꾸밀 수 있었습니다.
- Intent : 외부 링크와 연결시켰습니다.
- SQLiteHelper : 내부db를 활용하여 CRUD를 구현했습니다.
- Animation : FloatingActionButton 이 45도 회전하며   
다른 버튼을 나타나게(투명도 / 크기 / Clickable 옵션 컨트롤) 합니다.
- Banner Ad : 배너광고를 출력합니다.

***
