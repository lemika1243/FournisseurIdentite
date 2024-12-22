import React from 'react';
import { Form, Input, Button } from 'antd';

const InscriptionForm = () => (
  <Form layout="vertical">
    <Form.Item label="Adresse e-mail" name="email">
      <Input placeholder="Entrer votre adresse e-mail" />
    </Form.Item>
    <Form.Item label="Créer un mot de passe" name="password">
      <Input.Password placeholder="Créer un mot de passe" />
    </Form.Item>
    <Form.Item>
      <Button type="primary" htmlType="submit">
        S'inscrire
      </Button>
    </Form.Item>
  </Form>
);

export default InscriptionForm;
