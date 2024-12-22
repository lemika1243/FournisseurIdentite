import React from 'react';
import {  UserOutlined } from '@ant-design/icons';
import { Breadcrumb } from 'antd';
const Bread = ({user,page}) => (
  <Breadcrumb
    items={[
      {
        href: '',
        title: <UserOutlined />,
      },
      {
        title:user,
      },
      {
        title: page,
      },
    ]}
  />
);
export default Bread;