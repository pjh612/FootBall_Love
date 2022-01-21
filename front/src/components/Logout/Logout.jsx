import {useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = ({setKey}) => {
    const navigate = useNavigate();

    useEffect(() => {
        setKey(false);
        navigate('/');
    }, [])
    
    return(
        <>
        </>
    )
}

export default Logout;