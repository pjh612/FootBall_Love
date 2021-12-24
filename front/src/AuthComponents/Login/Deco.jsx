// 이미지 , 글씨를 넣어 빈 로그인창을 꾸며주는 컴포넌트
// 부모박스 flex, flex-direction : row
// 가운데 정렬
import styled from 'styled-components';

const Div = styled.div`
    width: 20%;
    height: 50vh;
    border: 1px solid blue;
    margin-top: 100px;
    background-image: url("img/soccer.jpg");
    background-size: contain;  
    background-repeat: no-repeat;
`;


const Deco = () => {
    return(
        <>
            <Div></Div>
        </>
    )
}

export default Deco;