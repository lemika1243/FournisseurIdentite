import React, { useState } from 'react';
import {
  DesktopOutlined,
  FileOutlined,
  PieChartOutlined,
  TeamOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {Layout, Menu, theme } from 'antd';
import { Outlet, useNavigate } from 'react-router-dom';
import LogoComponent from '../logo/LogoComponent';
import adminLogo from '../../assets/images/building.svg';

const { Header, Content, Footer, Sider } = Layout;

function getItem(label, key, icon, children) {
  return {
    key,
    icon,
    children,
    label,
  };
}

const menuItems = [
  getItem('Tableau de Bord', '/admin/dashboard', <PieChartOutlined />),
  getItem('Liste', '/admin/liste', <FileOutlined />),
  getItem('Utilisateurs', '/admin/users', <UserOutlined />, [
    getItem('Liste des Utilisateurs', '/admin/users/list'),
    getItem('Ajouter un Utilisateur', '/admin/users/add'),
  ]),
  getItem('Équipes', '/admin/teams', <TeamOutlined />, [
    getItem('Équipe 1', '/admin/teams/1'),
    getItem('Équipe 2', '/admin/teams/2'),
  ]),
  getItem('Déconnexion', '/logout', <DesktopOutlined />),
];

const LayoutAdmin = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  
  const handleMenuClick = ({ key }) => {
    navigate(key);
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider   collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
        <div style={{ padding: '16px', textAlign: 'center' }}>
          <LogoComponent logoUrl={adminLogo} width={40} height={40} companyName="Admin Panel" />
        </div>
        <Menu
          theme="dark"
          defaultSelectedKeys={['/admin/dashboard']}
          mode="inline"
          items={menuItems}
          onClick={handleMenuClick}
        />
      </Sider>
      <Layout>
        <Header
          style={{
            padding: 0,
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
        </Header>
        <Content style={{margin: '0',padding: '24px',minHeight: '360px',overflowY: 'auto', maxHeight: 'calc(100vh - 64px - 70px)',}}>
            <Outlet />
        </Content>
        <Footer style={{ textAlign: 'center' }}>
          Ant Design ©{new Date().getFullYear()} Designed by Angelo
        </Footer>
      </Layout>
    </Layout>
  );
};

export default LayoutAdmin;
