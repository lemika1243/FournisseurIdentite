import React, { useState } from 'react';
import { Table, Popconfirm, Button } from 'antd';
import { PlusOutlined,EyeOutlined,EditOutlined,DeleteOutlined } from '@ant-design/icons';
import ModalInfo from '../modal/ModalInfo';
import { useNavigate } from 'react-router-dom';


const DataTableWithAction = () => {
  const navigate = useNavigate();
const [isModalOpen, setIsModalOpen] = useState(false);
const [selectedRecord, setSelectedRecord] = useState(null);


const showModal = (record) => {
    setSelectedRecord(record);
    setIsModalOpen(true);
};

const closeModal = () => {
    setIsModalOpen(false);
    setSelectedRecord(null);
};
  
  const handleAdd = () => {
    navigate('/admin/entity/add');
  }

  // Fonction pour gérer la suppression d'une ligne
  const handleDelete = (key) => {
    console.log('Deleted key:', key);
  };

  // Fonction pour gérer la modification d'une ligne
  const handleEdit = (key) => {
    console.log('Edit key:', key);
  };

  const columns = [
    {
      title: 'Name',
      dataIndex: 'name',
      render: (text) => <a>{text}</a>,
    },
    {
      title: 'Cash Assets',
      className: 'column-money',
      dataIndex: 'money',
      align: 'right',
    },
    {
      title: 'Address',
      dataIndex: 'address',
    },
    {
        title: 'Detail',
        dataIndex: 'detail',
        render: (_, record) => (
            <Button
              type="default"
              icon={<EyeOutlined />}
              onClick={() => showModal(record)}
            >
              Détails
            </Button>
          ),
    },
    {
      title: 'Operation',
      dataIndex: 'operation',
      render: (_, record) => (
        <div style={{ display: 'flex', gap: '10px' }}>
          {/* Bouton Modifier */}
          <Button
            type="primary"
            icon={<EditOutlined />}
            danger={false}
            onClick={() => handleEdit(record.key)}
            style={{ backgroundColor: '#ffc107', borderColor: '#ffc107', color: '#000' }}
          >
            Edit
          </Button>
          {/* Bouton Delete */}
          <Popconfirm
            title="Are you sure to delete this item?"
            onConfirm={() => handleDelete(record.key)}
          >
            <Button type="primary" danger icon={<DeleteOutlined />} >
              Delete
            </Button>
          </Popconfirm>
        </div>
      ),
    },
  ];

  const data = [
    {
      key: '1',
      name: 'John Brown',
      money: '￥300,000.00',
      address: 'New York No. 1 Lake Park',
    },
    {
      key: '2',
      name: 'Jim Green',
      money: '￥1,256,000.00',
      address: 'London No. 1 Lake Park',
    },
    {
      key: '3',
      name: 'Joe Black',
      money: '￥120,000.00',
      address: 'Sydney No. 1 Lake Park',
    },
  ];

  return (
    <div style={{marginBottom:24,marginTop:24}}>
      {/* Tableau */}
      <Table
        columns={columns}
        dataSource={data}
        bordered
        title={() => (
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            Add
          </Button>
        )}
        footer={() => 'footer'}
        scroll={{y:300}}
      />

       {/* Modal Info */}
       {selectedRecord && (
        <ModalInfo 
          record={selectedRecord} 
          visible={isModalOpen} 
          onClose={closeModal} 
        />
      )}
    </div>
  );
};

export default DataTableWithAction;
