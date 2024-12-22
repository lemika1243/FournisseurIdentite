import React from 'react';
import { Table,Card } from 'antd';
import FormFilter from '../filtres/FormFilter';
const TableResultWithoutAction = () => {
  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      render: (text) => <span>{text}</span>, 
    },
    {
      title: 'Date of birth',
      dataIndex: 'dateOfBirth',
    },
    {
      title: 'Salaire',
      className: 'column-money',
      dataIndex: 'salaire',
      align: 'right',
    },
    {
      title: 'Genre',
      dataIndex: 'genre',
    },
  ];

  const data = [
    {
      key: '1',
      name: 'John Brown',
      dateOfBirth:'1995-05-14',
      salaire: '￥300,000.00',
      genre: 'Homme',
    },
    {
      key: '2',
      name: 'Jim Green',
      dateOfBirth:'1995-05-14',
      salaire: '￥1,256,000.00',
      genre: 'Homme',
    },
    {
      key: '3',
      name: 'Joe Black',
      dateOfBirth:'1995-05-14',
      salaire: '￥120,000.00',
      genre: 'Gay',
    },
  ];

  return (
    <Card bordered={true} style={{marginBottom:24,marginTop:24}}>
      <Table
      columns={columns}
      dataSource={data}
      bordered
      title={() => (<FormFilter />)}
      footer={() => 'Fin du tableau'}
      scroll={{ y: 300 }} 
    />
    </Card>
    
  );
};

export default TableResultWithoutAction;
