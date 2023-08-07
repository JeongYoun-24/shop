

console.log("order axios방식 처리")


// 주문 버튼 클릭시
// 형식: await axios.post('url', 전송할 데이터 객체, { headers: { 데이터형식}} )
/* 
  ex)
  await axios.post(
    '/order',               // url
    {키:"값",...} or 객체,  // 데이터
    { headers: { 
        'Content-Type': "multipart/form-data" or 
                        "application/json;charset=utf-8" ,  // 데이터형식
        [_csrf_header]: _csrf                               // 토큰 전송
    }})

*/
async function cancelOrderId(orderIdData){  // 애매 취소

    const response = await axios.post('/tickting/order/'+orderIdData.orderId+'/cancel', orderIdData)

    // return response.data; 
    return response;
}
async function ticktingId(orderIdData){ //구매 티켓 조회

    const response = await axios.post('/tickting/order/'+orderIdData.orderId+'/find', orderIdData)

    // return response.data;
    return response;
}


// 장바구니 버튼 클릭시 
// async function orderItem(orderItem){

//     const response = await axios.post('/cart', orderItem)

//     console.log("요청 응답 결과:"+response.data)

//     return response.data;
// }