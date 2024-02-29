{ //동기
  console.log('시작');
  console.log('실행중');
  let sum = 0;
  for (let i = 0; i < 990000000; i++);
  sum += i;
}
console.log('종료');
console.log(`sum=${sum}`);


{    //비동기1
  console.log('시작');
  console.log('실행중');
  const method1 = async () {
    try {
      const result = await new Promise((resolve, reject) => {
        //비동기처리로직. 보통 시간이 소요되는 작업 EX)서버와의 통신
        let sum = 0;
        for (let i = 0; i < 990000000; i++) {
          sum += i;
        }
        if (true) {
          resolve(sum);
        } else {
          reject(new Error('오류발생'))
        };
      });
      console.log(result);
    } catch (err) {
      console.log(err);
    } finally {
      console.log('성공유무관계없이 수행됨');
    }
  }
  method1(); S
  console.log('종료');
}
