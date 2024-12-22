import React from 'react';
import { Col, Row, Statistic,Card } from 'antd';
import CountUp from 'react-countup';

import './stat.css';
const formatter = (value) => <CountUp end={value} separator="," />;
const Stat = ({data}) => {
  return(
    <Row justify={'space-around'} style={{marginBottom:24,marginTop:24}}   >
    {data.map((stat,index)=>(
    
       <Card bordered={true} style={{width:240}} key={index} >
       <Col span={6}>
         <Statistic 
         value={stat.value} 
         title={<span className='statistic-title'>{stat.title}</span>} 
         formatter={formatter} 
         />
       </Col>
       </Card>
    ))} 
  </Row>
  );
 
};
export default Stat;