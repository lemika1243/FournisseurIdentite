import React from 'react';
import { Card, Tabs, Typography,Flex } from 'antd';
import AdminForm from '../../components/forms/admin/AdminForm';
import './LandingPage.css';

const { Title } = Typography;

const LandingPage = () => {
  return (
    <div className='landing'>
    <Flex justify="center" style={{ padding: '40px 0' }}>
      <Card hoverable className='landing-card '>
        <Title level={2} className="landing-title" >CryptoMonnaie</Title>
        {/* <Flex direction="column" align="center" justify="center" style={{ marginBottom: 24 }}>
             <img alt="Building" src={buildingImage} className='landing-image' />
        </Flex> */}
        <Tabs
          type="card"
          items={[
            { label: '', key: '1', children: <AdminForm /> },
          ]}
        />
      </Card>
    </Flex>
    </div>
  );
};

export default LandingPage;
