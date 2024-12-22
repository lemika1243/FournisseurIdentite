import React, { useState } from 'react';
import { Modal, Button, Descriptions } from 'antd';

const ModalInfo = ({ record, visible, onClose }) => {
  return (
    <Modal
      title="Détails Complets"
      visible={visible}
      onCancel={onClose}
      footer={[
        <Button key="back" onClick={onClose}>
          Fermer
        </Button>
      ]}
    >
      <Descriptions bordered column={1}>
        <Descriptions.Item label="Nom">{record.name}</Descriptions.Item>
        <Descriptions.Item label="Argent">{record.money}</Descriptions.Item>
        <Descriptions.Item label="Adresse">{record.address}</Descriptions.Item>
        {record.detail && (
          <Descriptions.Item label="Détails supplémentaires">
            {record.detail}
          </Descriptions.Item>
        )}
      </Descriptions>
    </Modal>
  );
};

export default ModalInfo;