import React, { useState } from 'react';
import { Table, Popconfirm, Button } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ModalForm from '../modal/ModalForm'; 

const TableResultWithAction = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  // Fonction pour afficher le modal
  const showModal = () => {
    setIsModalOpen(true);
  };

  // Fonction pour fermer le modal
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // Fonction pour gérer la soumission du formulaire
  const handleFormSubmit = (formData) => {
    console.log('Données soumises :', formData);
    
  };

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
      title: 'Operation',
      dataIndex: 'operation',
      render: (_, record) => (
        <div style={{ display: 'flex', gap: '10px' }}>
          {/* Bouton Modifier */}
          <Button
            type="primary"
            danger={false}
            onClick={() => handleEdit(record.key)}
            style={{ backgroundColor: '#ffc107', borderColor: '#ffc107', color: '#000' }}
          >
            Modifier
          </Button>
          {/* Bouton Delete */}
          <Popconfirm
            title="Are you sure to delete this item?"
            onConfirm={() => handleDelete(record.key)}
          >
            <Button type="primary" danger>
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
          <Button type="primary" icon={<PlusOutlined />} onClick={showModal}>
            Ajouter
          </Button>
        )}
        footer={() => 'footer'}
        scroll={{y:300}}
      />

      {/* Modal Form */}
      <ModalForm
        isOpen={isModalOpen} // État pour ouvrir ou fermer le modal
        onClose={closeModal} // Fonction pour fermer le modal
        onSubmit={handleFormSubmit} // Fonction pour traiter les données soumises
      />
    </div>
  );
};

export default TableResultWithAction;
