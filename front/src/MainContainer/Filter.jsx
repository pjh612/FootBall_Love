import styled from "styled-components";

const MainFilter = styled.div`
`;

const MainMatchFilter = styled.div`
`;

const Ul = styled.ul`
    max-width: 1024px;
    margin: 0 auto;
    padding: 10px 10px 20px 10px;
    border-bottom: 1px solid #ddd;
    display: flex;
`;

const Li = styled.li`
    font-size: 14px;
    color: #222836;
    padding: 5px 10px 5px 10px;
    cursor: pointer;
`;

export default function Filter() {
    const local = ['전체', '서울', '경기', '인천', '대전', '충북', '충남', '대구/경산', '부산', '울산', '경남', '경북', '광주' , '전북', '강원', '제주', '전남'];
    const listComponents = [];

    (function() {
        for(let i = 0; i < local.length; i++) {
            listComponents.push(<Li style={i === 0 ? {marginLeft: '30px'} : null}>{local[i]}</Li>)
        }
    })()

    return(
    <MainFilter>
        <MainMatchFilter>
            <Ul>
                {listComponents}
            </Ul>
        </MainMatchFilter>
    </MainFilter>
    )

}