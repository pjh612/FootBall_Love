import styled from 'styled-components';
import Button from '@mui/material/Button';
//Submit Button



const Div4items = ({newUserInfo, submitJoinData}) => {
    return(
        <>
            {/* <SubmitButton onClick={() => submitJoinData(newUserInfo)}>계속</SubmitButton> */}
            <Button
              type="submit"
              variant="contained"
              sx={{ mt: 0.4, mb: 2 }}
            >
              계속
            </Button>
        </>
    )
}

export default Div4items;