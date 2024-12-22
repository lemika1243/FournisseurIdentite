import React from 'react';
import { Layout as AntLayout, Menu, Spin } from 'antd';
import { DashboardOutlined, LogoutOutlined, CreditCardOutlined } from '@ant-design/icons';
import { Outlet, useNavigate } from 'react-router-dom';
import LogoComponent from '../logo/LogoComponent';
import buildingImage from '../../assets/images/building.svg'; 
import { useState } from 'react';

const { Header, Content, Footer } = AntLayout;

const menuItems  = [
    {
      label: 'Tableau de Bord',
      key: '/client/dashboard', 
      icon: <DashboardOutlined />,
    },
    {
      label: 'Paiement',
      key: '/client/paiement', 
      icon: <CreditCardOutlined />,
    },
    {
      label: 'Déconnexion',
      key: '/logout', 
      icon: <LogoutOutlined />,
    },
  ];


  


function LayoutParentClient(){
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const handleMenuClick = (e) => {
        setLoading(true); 
        navigate(e.key);
        setLoading(false); 
    };

    return(
    <AntLayout  style={{ minHeight: '100vh' }}>
      <Header style={{position: 'sticky',top: 0,zIndex: 1,width: '100%',display: 'flex',alignItems: 'center', justifyContent:'space-between'}}>
        <div style={{display:'flex',alignItems:'center'}}>
            <LogoComponent logoUrl={buildingImage} width={50} height={50} companyName="Mada Immo" />
        </div>
        <Menu onClick={handleMenuClick} className="custom-menu" theme="dark" mode="horizontal" defaultSelectedKeys={['/client/dashboard']} items={menuItems} style={{flex: 1,minWidth: 0,}}/>
      </Header>
      
      <Content style={{ padding: '0 48px',}}>
        <div style={{ padding: 24, minHeight: '80vh',}}>
            <Outlet />
        </div>
      </Content>
      <Footer style={{ textAlign: 'center',}}>
        Ant Design ©{new Date().getFullYear()} Designed by Angelo
      </Footer>
    </AntLayout >
    );
}

export default LayoutParentClient;