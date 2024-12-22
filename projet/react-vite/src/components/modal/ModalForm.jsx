import React, { useState } from 'react';
import { Modal, Form, Input, Button } from 'antd';

const ModalForm = ({ isOpen, onClose, onSubmit }) => {
  const handleFormSubmit = (values) => {
    onSubmit(values); 
    onClose(); 
  };

  return (
    <Modal
      title="Inscription"
      visible={isOpen}
      onCancel={onClose}
      footer={null} // Pas de footer pour que le bouton soit inclus dans le formulaire
    >
      <Form
        name="inscriptionForm"
        onFinish={handleFormSubmit}
        layout="vertical"
      >
        <Form.Item
          label="Nom"
          name="name"
          rules={[{ required: true, message: 'Veuillez entrer votre nom!' }]}
        >
          <Input placeholder="Entrer le nom" />
        </Form.Item>
        <Form.Item
          label="Adresse email"
          name="email"
          rules={[
            { required: true, message: 'Veuillez entrer votre adresse email!' },
            { type: 'email', message: 'Veuillez entrer une adresse email valide!' },
          ]}
        >
          <Input placeholder="Entrer l'email" />
        </Form.Item>
        <Form.Item
          label="Mot de passe"
          name="password"
          rules={[{ required: true, message: 'Veuillez entrer un mot de passe!' }]}
        >
          <Input.Password placeholder="Entrer le mot de passe" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Soumettre
          </Button>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ModalForm;
