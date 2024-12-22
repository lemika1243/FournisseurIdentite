import React from 'react';
import {Link} from 'react-router-dom';
import PropTypes from 'prop-types';
import { Typography } from 'antd';

const { Text } = Typography;

const LogoComponent = ({
logoUrl,
width=40,
height=40,
companyName = 'Entreprise',
linkTo='/'
}) => {
    return (
     <Link to={linkTo} style={{display:'flex',alignItems:'center',textDecoration:'none'}} >
        <img src={logoUrl} alt="Logo entreprise" style={{
            width:`${width}px`,height:`${height}px`,marginRight:'10px'
        }} />
        <Text style={{color:'white',fontWeight:'bold'}}>
            {companyName}
        </Text>
     </Link>
     
    );
};

LogoComponent.propTypes = {
  logoUrl: PropTypes.string.isRequired,
  width: PropTypes.number,
  height: PropTypes.number,
  companyName: PropTypes.string,
  linkTo: PropTypes.string
};
export default LogoComponent;