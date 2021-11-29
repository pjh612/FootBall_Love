import styled from "styled-components";

const MainFilter = styled.div`
-webkit-box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    max-width: 1024px;
    padding: 15px 20px;
    flex-wrap: wrap;
    margin: 0 auto;
`;

const FilterResult = styled.div`
    margin-left: 20px;
`;

const FilterWrapper = styled.div`
`;

const CheckBoxInput = styled.input`
    width: 20px;
    height: 20px;
    margin-right: 5px;
    background: #FFF;
    border: 1px solid #DDD;
    border-radius: 4px;
    margin-top: -1.5px;
    vertical-align: text-top;
`;

const HideLabel = styled.label`
    font-size: 14px;
    cursor: pointer;
`;

const Span = styled.span`
    font-size: 15px;
    margin-left: 10px;
    padding: 2px 15px 2px 3px;
    cursor: pointer;
    position: relative;

`;

export default function DeadlineFilter() {
    return(
        <MainFilter>
            <FilterResult>
                    <CheckBoxInput type="checkbox" id="deadlineInput"></CheckBoxInput>
                    <HideLabel htmlFor="deadlineInput">마감 가리기</HideLabel>
            </FilterResult>
            <FilterWrapper>
                <Span>매치 필터</Span>
            </FilterWrapper>
        </MainFilter>
    )
}