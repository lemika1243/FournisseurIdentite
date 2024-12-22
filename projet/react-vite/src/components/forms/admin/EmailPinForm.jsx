import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { MailOutlined, NumberOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom'; // Importer useNavigate
import DashboardPage from '../../../pages/admin/dashboard/DashboardPage';

const EmailPinForm = ({ email }) => {
  const [pin, setPin] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate(); // Initialiser navigate

  const onFinish = async () => {
    setLoading(true);
    try {
      // Envoyer l'email et le PIN au backend pour validation
      console.log('Email:', email);
      console.log('PIN:', pin);

      // Simulez la validation du PIN
      message.success('PIN validé avec succès');

      // Rediriger vers /admin/dashboard si l'utilisateur est authentifié
      navigate('/admin/dashboard');
    } catch (error) {
      message.error('Erreur lors de la validation du PIN');
    } finally {
      setLoading(false);
    }
  };

  return (
        <Form layout="vertical" onFinish={onFinish}>
          <Form.Item
            label="Email"
            name="email"
            initialValue={email} // Pré-remplit l'email
            rules={[
              {
                required: true,
                message: "L'email est requis",
              },
            ]}
          >
            <Input prefix={<MailOutlined />} placeholder="Votre email" disabled />
          </Form.Item>

          <Form.Item
            label="Code PIN"
            name="pin"
            rules={[
              {
                required: true,
                message: 'Veuillez entrer votre code PIN',
              },
              {
                len: 6,
                message: 'Le code PIN doit être de 6 chiffres',
              },
            ]}
          >
            <Input
              prefix={<NumberOutlined />}
              placeholder="Entrer votre code PIN"
              value={pin}
              onChange={(e) => setPin(e.target.value)}
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" block loading={loading}>
              Valider le PIN
            </Button>
          </Form.Item>
        </Form>
      
   
  );
};

export default EmailPinForm;
