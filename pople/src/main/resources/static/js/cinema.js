
async function getReply(cinemaid){

    const response = await axios.get(`/cinemass/list/${cinemaid}`)

    return response.data;
}