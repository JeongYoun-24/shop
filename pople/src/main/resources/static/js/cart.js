// 장바구니

async function cancelOrderId(orderIdData){  // 장바구니 삭제

    const response = await axios.post('/tickting/order/'+orderIdData.orderId+'/cancel', orderIdData)

    // return response.data;
    return response;
}


async function updateCartItemcount(orderIdData){  // 장바구니 수량 체크 변동

    const response = await axios.post('/tickting/order/'+orderIdData.orderId+'/cancel', orderIdData)

    // return response.data;
    return response;
}

async function cancelOrderId(orderIdData){  // 장바구니에서 상품 구매

    const response = await axios.post('/tickting/order/'+orderIdData.orderId+'/cancel', orderIdData)

    // return response.data;
    return response;
}












